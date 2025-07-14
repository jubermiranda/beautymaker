package com.doo.finalActv.beautymaker.serivce.db;

import com.doo.finalActv.beautymaker.exception.IllegalSignupException;
import com.doo.finalActv.beautymaker.exception.InvalidPasswordException;
import com.doo.finalActv.beautymaker.exception.UserNotFoundException;
import com.doo.finalActv.beautymaker.model.Client;
import com.doo.finalActv.beautymaker.model.Employee;
import com.doo.finalActv.beautymaker.model.User;
import com.doo.finalActv.beautymaker.serivce.utils.PasswordUtils;

import java.sql.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AuthService {

  public static User login(String email, char[] password)
          throws SQLException, UserNotFoundException, InvalidPasswordException {

    Connection conn = null;
    long userId;

    try {
      conn = ConnectionService.getConnection();

      String sql = "SELECT id, username, email, birthDate, password_hash, salt FROM "
              + DatabaseManager.DB_SCHEMA + ".users WHERE email = ?";

      try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, email);
        ResultSet rs = stmt.executeQuery();
        if (!rs.next()) {
          throw new UserNotFoundException("User not found with email: " + email);
        } else {
          userId = rs.getLong("id");
          String username = rs.getString("username");
          String dbEmail = rs.getString("email");
          LocalDate birthDate = rs.getDate("birthDate").toLocalDate();
          String passwordHash = rs.getString("password_hash");
          String salt = rs.getString("salt");

          if (!PasswordUtils.verifyPassword(password, salt, passwordHash)) {
            throw new InvalidPasswordException("Invalid password for user: " + email);
          }
        }
      }
    } catch (SQLException e) {
      throw new SQLException("Error logging in user: " + e.getMessage(), e);
    } finally {
      if (conn != null) {
        ConnectionService.closeConnection();
      }
    }

    return createApropriateUser(userId);
  }

  public static User signup(
          String username,
          String email,
          char[] password,
          LocalDate birthDate
  ) throws SQLException, IllegalSignupException {

    Connection conn = ConnectionService.getConnection();
    conn.setAutoCommit(false);
    long userId;

    // signup made in a transaction to ensure atomicity
    // user creation happens in two steps:
    try {

      // Step 1: Insert user into the users table
      String sql = "INSERT INTO " + DatabaseManager.DB_SCHEMA + ".users (username, email, birthDate, password_hash, salt) "
              + "VALUES (?, ?, ?, ?, ?) RETURNING id";
      try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, username);
        stmt.setString(2, email);
        stmt.setDate(3, Date.valueOf(birthDate));

        String salt = PasswordUtils.generateSalt();
        String passwordHash = PasswordUtils.hashPassword(password, salt);

        stmt.setString(4, passwordHash);
        stmt.setString(5, salt);

        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
          userId = rs.getLong("id");
        } else {
          throw new SQLException("Failed to create user");
        }
      }

      // Step 2: Insert user into the appropriate user type table (client)
      // Note: employee type is only set by admin, so we don't handle it here
      String sql2 = "INSERT INTO " + DatabaseManager.DB_SCHEMA + ".clients (user_id) VALUES (?)";
      try (PreparedStatement stmt = conn.prepareStatement(sql2)) {
        stmt.setLong(1, userId);
        stmt.executeUpdate();
      }

    } catch (SQLException e) {
      try {
          conn.rollback();
      } catch (SQLException rollbackEx) {
        e.printStackTrace();
      } finally {
        throw new SQLException("Error signing up user: " + e.getMessage(), e);
      }
    }

    conn.commit();
    ConnectionService.closeConnection();

    return createApropriateUser(userId);
  }

  private static User createApropriateUser(long userId) throws SQLException {
    String userType;

    try {
      userType = DatabaseManager.getUserType(userId);
    } catch (SQLException | UserNotFoundException e) {
      throw new SQLException("user not found with ID: " + userId, e);
    }

    try (Connection conn = ConnectionService.getConnection()) {
      String userSql = "SELECT id, username, email, birth_date FROM "
              + DatabaseManager.DB_SCHEMA + ".users WHERE id = ?";
      try (PreparedStatement stmt = conn.prepareStatement(userSql)) {
        stmt.setLong(1, userId);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
          String username = rs.getString("username");
          String email = rs.getString("email");
          LocalDate birthDate = rs.getDate("birthDate").toLocalDate();

          switch (userType) {
            case "client" -> {
              return new Client((int) userId, username, email, birthDate);
            }

            case "employee" -> {
              // Another query to get employee-specific data
              String employeeSql = "SELECT hire_date FROM " + DatabaseManager.DB_SCHEMA + ".employees WHERE user_id = ?";
              try (PreparedStatement empStmt = conn.prepareStatement(employeeSql)) {
                empStmt.setLong(1, userId);
                ResultSet empRs = empStmt.executeQuery();
                if (empRs.next()) {
                  LocalDate hireDate = empRs.getDate("hire_date").toLocalDate();
                  return new Employee((int) userId, username, email, birthDate, hireDate);
                } else {
                  throw new SQLException("Employee not found with user ID: " + userId);
                }
              }

            }

            default ->
              throw new SQLException("Unknown user type: " + userType);
          }
        } else {
          throw new SQLException("User not found with ID: " + userId);
        }
      }
    }
  }
}

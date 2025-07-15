package com.doo.finalActv.beautymaker.serivce.db;

import com.doo.finalActv.beautymaker.exception.IllegalSignupException;
import com.doo.finalActv.beautymaker.exception.InvalidPasswordException;
import com.doo.finalActv.beautymaker.exception.UserNotFoundException;
import com.doo.finalActv.beautymaker.model.User;

import java.sql.*;
import java.time.LocalDate;

public class DatabaseManager {
  
  public static final String DB_SCHEMA = "beautymaker";
  private static DatabaseManager instance;
  

  private DatabaseManager() {
    // 
  }

  public static synchronized DatabaseManager getInstance() {
    if (instance == null) {
      instance = new DatabaseManager();
    }
    return instance;
  }

  public boolean dbIsAvailable() {
    try (Connection conn = ConnectionService.getConnection()) {
      return conn != null && !conn.isClosed();
    } catch (SQLException e) {
      return false;
    }
  }

  public static User getUser(String email, char[] password) throws SQLException, UserNotFoundException, InvalidPasswordException {
    return AuthService.login(email, password);
  }

  public static User register(String username, String email, char[] password, LocalDate birthDate)
      throws SQLException, InvalidPasswordException, IllegalSignupException {

    return AuthService.signup(username, email, password, birthDate);
  }

  public static String getUserType(long id)
      throws SQLException, UserNotFoundException {
    try (Connection conn = ConnectionService.getConnection()) {
      String sql = "SELECT " + DB_SCHEMA + ".get_user_type(?)";
      try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, (int)id);
        try (ResultSet rs = stmt.executeQuery()) {
          if (rs.next()) {
            return rs.getString(1);
          } else {
            throw new UserNotFoundException("User not found with ID: " + id);
          }
        }
      }
    } catch (SQLException e) {
      throw new SQLException("Error retrieving user type for ID: " + id, e);
    }
  }

}

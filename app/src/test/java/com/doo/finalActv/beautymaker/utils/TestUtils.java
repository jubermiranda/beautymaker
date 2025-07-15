package com.doo.finalActv.beautymaker.utils;

import com.doo.finalActv.beautymaker.model.Client;
import com.doo.finalActv.beautymaker.model.NotificationType;
import com.doo.finalActv.beautymaker.model.User;
import com.doo.finalActv.beautymaker.serivce.db.ConnectionService;
import com.doo.finalActv.beautymaker.serivce.db.DatabaseManager;
import com.doo.finalActv.beautymaker.serivce.event.EventManager;
import com.doo.finalActv.beautymaker.serivce.event.model.NotificationEvent;
import com.doo.finalActv.beautymaker.serivce.event.model.RequestLoginEvent;
import com.doo.finalActv.beautymaker.serivce.event.model.RequestLogoutEvent;
import com.doo.finalActv.beautymaker.serivce.event.model.RequestSignupEvent;
import com.doo.finalActv.beautymaker.serivce.utils.PasswordUtils;
import com.doo.finalActv.beautymaker.session.SessionManager;
import java.sql.*;
import java.sql.SQLException;

import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestUtils {

  private static final String testUserName = "testUser";
  private static final String testUserEmail = "user_test@email.com";
  private static final char[] testPassword = "testPassword123".toCharArray();
  private static final LocalDate testBirthDate = LocalDate.of(1990, 1, 1);

  // examples of how to trigger the login/signup events
  public static void loginTestUser() {

    if (userExists()) {
      loginExistingUser();

    } else {
      signupNewUser();
    }
  }

  private static void loginExistingUser() {
    // example of how to trigger the login event (if user exists)
    EventManager.getInstance().publish(new RequestLoginEvent(
            testUserEmail,
            testPassword
    ));
  }

  private static void signupNewUser() {
    // example of how to trigger the signup event (if user does not exist)
    EventManager.getInstance().publish(new RequestSignupEvent(
            testUserName,
            testUserEmail,
            testUserEmail, // confirm email
            testPassword,
            testPassword, // confirm password
            testBirthDate
    ));
  }

  // a trick to check if the user exists in the database
  // this is not a good practice for production code, but can be useful for testing
  // try ge user from db, and a exception will be thrown if user does not exist
  private static boolean userExists() {
    try {
      DatabaseManager.getUser(testUserEmail, testPassword);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public static User getTestUser() {
    User testUser = new Client(
            1,
            testUserName,
            testUserEmail,
            testBirthDate
    );
    return testUser;
  }

  public static void logoutTestUser() {
    if (SessionManager.getInstance().userIsLoggedIn()) {
      EventManager.getInstance().publish(new RequestLogoutEvent());
    }
  }


  // function to create employee users 
  public static void createTestEmployee(
      String username,
      String email,
      char[] password,
      LocalDate birthDate,
      LocalDate hireDate
  ) {
    Connection conn;
    try {
      conn = ConnectionService.getConnection();
      
    } catch (SQLException ex) {
      EventManager.getInstance().publish(new NotificationEvent(
          NotificationType.ERROR,
          "Connection error",
          "Failed to get database connection"
      ));
      return;
    }
    
    try {
      conn.setAutoCommit(false);
    } catch (SQLException ex) {
      ex.printStackTrace();
      return;
    }
    
    long userId;

    try {

      // Step 1: Insert user into the users table
      String sql = "INSERT INTO " + DatabaseManager.DB_SCHEMA + ".users (username, email, birth_date, password_hash, salt) "
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

      // Step 2: Insert user into the employees table
      String sql2 = "INSERT INTO " + DatabaseManager.DB_SCHEMA + ".employees (user_id, hire_date) VALUES (?, ?)";
      try (PreparedStatement stmt = conn.prepareStatement(sql2)) {
        stmt.setLong(1, userId);
        stmt.setDate(2, Date.valueOf(hireDate));
        stmt.executeUpdate();
      }

    } catch (SQLException e) {
      EventManager.getInstance().publish(new NotificationEvent(
          NotificationType.ERROR,
          "Signup failed. rollbacking transaction.",
          e.getMessage()
      ));
      try {
          conn.rollback();
      } catch (SQLException rollbackEx) {
        EventManager.getInstance().publish(new NotificationEvent(
            NotificationType.ERROR,
            "Rollback failed",
            rollbackEx.getMessage()
        ));

      } finally {
        e.printStackTrace();
        EventManager.getInstance().publish(new NotificationEvent(
            NotificationType.ERROR,
            "Database Error",
            "Failed to create employee user: " + e.getMessage()
        ));
      }
    }

    EventManager.getInstance().publish(new NotificationEvent(
        NotificationType.WARNING,
        "Employee created",
        "Employee user created successfully."
    ));

    try {
      conn.commit();
    } catch (SQLException ex) {
      //
    }
    ConnectionService.closeConnection();
  }

  // function to add test ratings for a employee
  // this add a reting for the given employee as if the test user rated the employee
  // TIP: calling loginTestUser() before this function will ensure that the test user exists
  public static void addTestRatingForEmployee(
      String employeeUsername,
      float rating,
      String comment
  ) {
    int testUserId = getTestUserId();

    // check if employee exists/get employee id by username
    int employeeId = getEmployeeIdByUsername(employeeUsername);
    if (employeeId == -1) {
      EventManager.getInstance().publish(new NotificationEvent(
          NotificationType.ERROR,
          "Employee not found",
          "Failed to find employee with username: " + employeeUsername
      ));
      return;
    }

    Connection conn;
    try {
      conn = ConnectionService.getConnection();
    } catch (SQLException ex) {
      EventManager.getInstance().publish(new NotificationEvent(
          NotificationType.ERROR,
          "Connection error",
          "Failed to get database connection"
      ));
      return;
    }

    String sql = "INSERT INTO " + DatabaseManager.DB_SCHEMA 
      + ".employee_ratings (employee_id, client_id, rating, rate_comment) VALUES (?, ?, ?, ?)";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setInt(1, employeeId);
      stmt.setInt(2, testUserId);
      stmt.setFloat(3, rating);
      stmt.setString(4, comment);
      stmt.executeUpdate();
    } catch (SQLException e) {
      EventManager.getInstance().publish(new NotificationEvent(
          NotificationType.ERROR,
          "Failed to add rating",
          e.getMessage()
      ));
      e.printStackTrace();
    } finally {
      ConnectionService.closeConnection();
    }

    EventManager.getInstance().publish(new NotificationEvent(
        NotificationType.WARNING,
        "Rating added",
        "Rating for employee " + employeeUsername + " added successfully."
    ));
  }

  private static int getTestUserId() {
    int testUserId = -1;

    String sql = "SELECT id FROM " + DatabaseManager.DB_SCHEMA + ".users WHERE email = ?";
    try (Connection conn = ConnectionService.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

      stmt.setString(1, testUserEmail);
      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          testUserId = rs.getInt("id");
        }
      }
    } catch (SQLException e) {
      EventManager.getInstance().publish(new NotificationEvent(
          NotificationType.ERROR,
          "Failed to get test user ID",
          e.getMessage()
      ));
      e.printStackTrace();
    }

    return testUserId;
  }

  private static int getEmployeeIdByUsername(String username) {
    int employeeId = -1;
    int userId = -1;

    Connection conn;
    try {
      conn = ConnectionService.getConnection();
    } catch (SQLException ex) {
      EventManager.getInstance().publish(new NotificationEvent(
          NotificationType.ERROR,
          "Connection error",
          "Failed to get database connection"
      ));
      return employeeId;
    }

    // check if user exists/get user id by username
    String sql = "SELECT id FROM " + DatabaseManager.DB_SCHEMA + ".users WHERE username = ?";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {

      stmt.setString(1, username);
      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          userId = rs.getInt("id");
        } else {
          EventManager.getInstance().publish(new NotificationEvent(
              NotificationType.ERROR,
              "User not found",
              "Failed to find user with username: " + username
          ));
          return employeeId;
        }
      }

    } catch (SQLException e) {
      EventManager.getInstance().publish(new NotificationEvent(
          NotificationType.ERROR,
          "Failed to get employee ID",
          e.getMessage()
      ));
      e.printStackTrace();
      return employeeId;
    }


    // check if user is employee/get employee id by user id
    String isUserEmployeeSql = "SELECT user_id FROM " + DatabaseManager.DB_SCHEMA + ".employees WHERE user_id = ?";
    try (PreparedStatement stmt = conn.prepareStatement(isUserEmployeeSql)) {

      stmt.setInt(1, userId);
      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {

          employeeId = rs.getInt("user_id");

        } else {
          EventManager.getInstance().publish(new NotificationEvent(
              NotificationType.ERROR,
              "User is not an employee",
              "User with username: " + username + " is not an employee."
          ));
        }
      }

    } catch (SQLException e) {
      EventManager.getInstance().publish(new NotificationEvent(
          NotificationType.ERROR,
          "Failed to get employee ID",
          e.getMessage()
      ));
      e.printStackTrace();
    } finally {
      ConnectionService.closeConnection();
    }

    return employeeId;
  }

}

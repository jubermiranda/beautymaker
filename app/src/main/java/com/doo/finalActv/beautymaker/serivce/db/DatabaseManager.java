package com.doo.finalActv.beautymaker.serivce.db;

import com.doo.finalActv.beautymaker.exception.IllegalSignupException;
import com.doo.finalActv.beautymaker.exception.InvalidPasswordException;
import com.doo.finalActv.beautymaker.exception.UserNotFoundException;
import com.doo.finalActv.beautymaker.model.NotificationType;
import com.doo.finalActv.beautymaker.model.ServiceData;
import com.doo.finalActv.beautymaker.model.StaffData;
import com.doo.finalActv.beautymaker.model.User;
import com.doo.finalActv.beautymaker.serivce.event.EventManager;
import com.doo.finalActv.beautymaker.serivce.event.model.NotificationEvent;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

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

  public ArrayList<StaffData> getStaffs() {
    ArrayList<StaffData> staffs = new ArrayList<>();
    try (Connection conn = ConnectionService.getConnection()) {

      String sql = "SELECT username, rating, rating_count, hire_date FROM " + DB_SCHEMA + ".staffs";
      try (PreparedStatement stmt = conn.prepareStatement(sql);
           ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
          StaffData staff = new StaffData();
          staff.name = rs.getString("username");
          staff.rating = rs.getFloat("rating");
          staff.ratingCount = rs.getInt("rating_count");
          staff.experience = rs.getDate("hire_date").toLocalDate();
          staffs.add(staff);
        }
      }

    } catch (SQLException e) {
      EventManager.getInstance().publish(new NotificationEvent(
          NotificationType.ERROR,
          "Database Error",
          "Failed to retrieve staff data: " + e.getMessage()
      ));
    }
    return staffs;
  }

  public ArrayList<ServiceData> getServices() {
    ArrayList<ServiceData> services = new ArrayList<>();
    try (Connection conn = ConnectionService.getConnection()) {

      String sql = "SELECT name, description, price, duration_seconds FROM " + DB_SCHEMA + ".services";
      try (PreparedStatement stmt = conn.prepareStatement(sql);
           ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
          ServiceData service = new ServiceData();
          service.name = rs.getString("name");
          service.description = rs.getString("description");
          service.price = rs.getInt("price");
          service.duration = rs.getInt("duration_seconds");
          services.add(service);
        }
      }

    } catch (SQLException e) {
      EventManager.getInstance().publish(new NotificationEvent(
          NotificationType.ERROR,
          "Database Error",
          "Failed to retrieve services data: " + e.getMessage()
      ));
    }
    return services;
  }

}

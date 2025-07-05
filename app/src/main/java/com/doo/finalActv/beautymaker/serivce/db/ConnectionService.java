package com.doo.finalActv.beautymaker.serivce.db;

import io.github.cdimascio.dotenv.Dotenv;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionService {
  // Database connection parameters
  // TODO: get from env

  private static Connection connection = null;

  private ConnectionService() {}

  public static synchronized Connection getConnection() throws SQLException {
    if (connection == null || connection.isClosed()) {
      try {
        String userDir = System.getProperty("user.dir");
        Path dotenvPath = Paths.get(userDir).getParent();

        Dotenv dotenv = Dotenv.configure()
          .directory(dotenvPath.toString())
          .load();

        String URL = dotenv.get("DATABASE_URL");
        String USER = dotenv.get("DATABASE_USER");
        String PASSWORD = dotenv.get("DATABASE_PASS");
        connection = DriverManager.getConnection(URL, USER, PASSWORD);
      } catch (SQLException e) {
        throw new SQLException("Failed to connect to the database", e);
      }
    }
    return connection;
  }

  public static void closeConnection() {
    if (connection != null) {
      try {
        connection.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }
}

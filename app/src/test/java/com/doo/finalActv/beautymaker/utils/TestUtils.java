package com.doo.finalActv.beautymaker.utils;

import com.doo.finalActv.beautymaker.exception.UserNotFoundException;
import com.doo.finalActv.beautymaker.model.Client;
import com.doo.finalActv.beautymaker.model.User;
import com.doo.finalActv.beautymaker.serivce.db.DatabaseManager;
import com.doo.finalActv.beautymaker.serivce.event.EventManager;
import com.doo.finalActv.beautymaker.serivce.event.model.RequestLoginEvent;
import com.doo.finalActv.beautymaker.serivce.event.model.RequestLogoutEvent;
import com.doo.finalActv.beautymaker.serivce.event.model.RequestSignupEvent;
import com.doo.finalActv.beautymaker.session.SessionManager;
import java.sql.SQLException;

import java.time.LocalDate;

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
    // exemple of how to trigger the login event (if user exists)
    EventManager.getInstance().publish(new RequestLoginEvent(
            testUserEmail,
            testPassword
    ));
  }

  private static void signupNewUser() {
    // exemple of how to trigger the signup event (if user does not exist)
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
}

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

import java.time.LocalDate;

public class TestUtils {

  private static final String testUserName = "testUser";
  private static final String testUserEmail = "user_test@email.com";
  private static final char[] testPassword = "testPassword123".toCharArray();
  private static final LocalDate testBirthDate = LocalDate.of(1990, 1, 1);


  public static void loginTestUser() {
    try {
      DatabaseManager.getUser(testUserEmail, testPassword);
      EventManager.getInstance().publish(new RequestLoginEvent(
          testUserEmail,
          testPassword
      ));
    } catch (Exception e){
      if(e instanceof UserNotFoundException) {
        EventManager.getInstance().publish(new RequestSignupEvent(
            testUserName,
            testUserEmail,
            testUserEmail,
            testPassword,
            testPassword,
            testBirthDate
        ));
      } else {
        e.printStackTrace();
      }
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
    if(SessionManager.getInstance().userIsLoggedIn()) {
      EventManager.getInstance().publish(new RequestLogoutEvent());
    }
  }
}

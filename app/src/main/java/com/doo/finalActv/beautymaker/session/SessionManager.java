package com.doo.finalActv.beautymaker.session;

import com.doo.finalActv.beautymaker.exception.IllegalLoginRequest;
import com.doo.finalActv.beautymaker.exception.IllegalLogoutRequest;
import com.doo.finalActv.beautymaker.exception.IllegalSignupRequest;
import com.doo.finalActv.beautymaker.model.NotificationType;
import com.doo.finalActv.beautymaker.model.User;
import com.doo.finalActv.beautymaker.serivce.db.AuthService;
import com.doo.finalActv.beautymaker.serivce.event.EventManager;
import com.doo.finalActv.beautymaker.serivce.event.model.RequestLoginEvent;
import com.doo.finalActv.beautymaker.serivce.event.model.RequestLogoutEvent;
import com.doo.finalActv.beautymaker.serivce.event.model.RequestSignupEvent;
import com.doo.finalActv.beautymaker.serivce.event.model.NotificationEvent;
import com.doo.finalActv.beautymaker.serivce.event.model.SuccessfulLoginEvent;
import com.doo.finalActv.beautymaker.serivce.event.model.UserLoggedOutEvent;
import com.doo.finalActv.beautymaker.serivce.db.DatabaseManager;
import com.doo.finalActv.beautymaker.serivce.utils.Validator;
import java.util.Arrays;

public class SessionManager {
  private static SessionManager instance;
  private static EventManager eventManager;

  private static User user;
  
  private SessionManager() {
    eventManager = EventManager.getInstance();
    this.initialize();
  }
  public static synchronized SessionManager getInstance() {
    if(instance == null) {
      instance = new SessionManager();
    }
    return instance;
  }

  public User getUser() {
    return user;
  }
  
  public boolean userIsLoggedIn() {
    return user != null;
  }

  // ------

  private void initialize() {
    user = null;

    this.initializeEvents();
  }

  private void initializeEvents() {
    eventManager.subscribe(RequestLoginEvent.class, this::onLoginRequest);
    eventManager.subscribe(RequestSignupEvent.class, this::onSignupRequest);
    eventManager.subscribe(RequestLogoutEvent.class, this::onLogoutRequest);
  }

  private void onLoginRequest(RequestLoginEvent event) {
    if(userIsLoggedIn()){
      eventManager.publish(new NotificationEvent(
          NotificationType.WARNING,
          "Cant login",
          "User is already logged in."
      ));
      return;
    }

    try {
      user = DatabaseManager.getUser(event.username, event.password);
      eventManager.publish(new SuccessfulLoginEvent());

    } catch (Exception e) {
      eventManager.publish(new NotificationEvent(
          NotificationType.ERROR,
          "Login failed",
          e.getMessage()
      ));
    }
  }

  private void onSignupRequest(RequestSignupEvent event) {
    if(userIsLoggedIn()){
      eventManager.publish(new NotificationEvent(
          NotificationType.WARNING,
          "Cant signup",
          "User is already logged in."
      ));
      return;
    }

    try {
      this.validateSignupRequest(event);

    } catch (IllegalSignupRequest e) {
      eventManager.publish(new NotificationEvent(
          NotificationType.ERROR,
          "Signup failed",
          e.getMessage()
      ));
      return;
    }

    try {
      this.user = DatabaseManager.register(
          event.username,
          event.email,
          event.password,
          event.birthDate
      );

      eventManager.publish(new SuccessfulLoginEvent());

    } catch (Exception e) {
      eventManager.publish(new NotificationEvent(
          NotificationType.ERROR,
          "Signup failed",
          e.getMessage()
      ));
    }
  }

  private void onLogoutRequest(RequestLogoutEvent event) {
    if(!userIsLoggedIn()){
      eventManager.publish(new NotificationEvent(
          NotificationType.WARNING,
          "Cant logout",
          "User is not logged in."
      ));
      return;
    }

    this.user = null;
    eventManager.publish(new UserLoggedOutEvent());
  }


  private void validateSignupRequest(RequestSignupEvent event) throws IllegalSignupRequest {
    if(!(event.email.equals(event.confirmEmail))) {
      throw new IllegalSignupRequest("Emails do not match.");
    }
    if(!(Arrays.equals(event.password, event.confirmPassword))) {
      throw new IllegalSignupRequest("Passwords do not match.");
    }
    if(!Validator.isValidUsername(event.username)) {
      throw new IllegalSignupRequest("Username is not valid.");
    }
    if(!Validator.isValidEmail(event.email)) {
      throw new IllegalSignupRequest("Email is not valid.");
    }
    if(!Validator.isValidPassword(event.password)) {
      throw new IllegalSignupRequest(
          "Password needs to be at least 8 characters long, "
          +"contain at least one digit and one special character."
      );
    }
  }
}

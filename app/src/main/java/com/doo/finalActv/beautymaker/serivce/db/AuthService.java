package com.doo.finalActv.beautymaker.serivce.db;

import com.doo.finalActv.beautymaker.model.User;
import java.time.LocalDate;


public class AuthService {
  public static User login(String username, char[] password) {
    // TODO: Logic to authenticate user
    // If successful, return User object
    // If failed, return null or throw an exception
    return null;
  }

  public static User signup(
      String username, 
      String email,
      String confirmEmail,
      char[] password,
      char[] confirmPassword,
      LocalDate birthDate
  ) {
    // TODO: Logic to create a new user
    // If successful, return new User object
    // If failed, return null or throw an exception
    return null;
  }
}

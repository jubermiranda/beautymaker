package com.doo.finalActv.beautymaker.serivce.utils;

public class Validator {
  
  private Validator() {}

  public static boolean isValidEmail(String email) {
    if (email == null || email.isEmpty()) {
      return false;
    }
    String emailRegex = "^[a-zA-Z0-9._]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    return email.matches(emailRegex);
  }

  public static boolean isValidUsername(String username) {
    if (username == null || username.isEmpty()) {
      return false;
    }
    String usernameRegex = "^[a-zA-Z ]{3,}$";
    return username.matches(usernameRegex);
  }

  public static boolean isValidPassword(char[] password) {
    return (password != null && password.length >= 8);
  }
}

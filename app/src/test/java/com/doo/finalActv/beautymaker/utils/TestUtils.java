package com.doo.finalActv.beautymaker.utils;

public class TestUtils {
  public static void createUserTest(String username, String email)
      throws IllegalArgumentException 
  {
    if(this.userAlreadyExists(username)) {
      return;
    }

    User user = this.createUser(username, email);
    try {
      AuthService.signup(
          user.getName(),
          user.getEmail(),
          user.getEmail(),
          "usertest123".toCharArray(),
          "usertest123".toCharArray(),
          LocalDate.of(1990, 1, 1)
      );
    } catch (Exception e) {
      System.out.println("Error creating user: " + e.getMessage());
      throw new RuntimeException("User creation failed", e);
    }
    return user;
  }

}

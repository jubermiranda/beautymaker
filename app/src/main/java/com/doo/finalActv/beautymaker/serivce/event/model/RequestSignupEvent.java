package com.doo.finalActv.beautymaker.serivce.event.model;

public class RequestSignupEvent {
  public final String username;
  public final String email;
  public final String confirmEmail;
  public final char[] password;
  public final char[] confirmPassword;
  public final LocalDate birthDate;

  public RequestSignupEvent(String username, String email, String confirmEmail, char[] password, char[] confirmPassword, LocalDate birthDate) {
    this.username = username;
    this.email = email;
    this.confirmEmail = confirmEmail;
    this.password = password;
    this.confirmPassword = confirmPassword;
    this.birthDate = birthDate;
  }
}

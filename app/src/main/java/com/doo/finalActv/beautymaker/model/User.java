package com.doo.finalActv.beautymaker.model;

import java.time.LocalDate;

public abstract class User {
  private String name;
  private String email;
  private LocalDate birthDate;

  public User(String name, String email, LocalDate birthDate) {
    this.name = name;
    this.email = email;
    this.birthDate = birthDate;
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

}

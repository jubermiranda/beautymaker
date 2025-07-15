package com.doo.finalActv.beautymaker.model;

import java.time.LocalDate;

public abstract class User {
  private int id;
  private String name;
  private String email;
  private LocalDate birthDate;

  public User(int id, String name, String email, LocalDate birthDate) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.birthDate = birthDate;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  @Override
  public  boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof User)) return false;
    User user = (User) o;
    return (
      name.equals(user.name) && 
      email.equals(user.email) &&
      birthDate.equals(user.birthDate)
    );
  }

}

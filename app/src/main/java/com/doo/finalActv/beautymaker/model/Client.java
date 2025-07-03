package com.doo.finalActv.beautymaker.model;

import java.time.LocalDate;

public class Client extends User {
  private String phoneNumber;

  public Client(String name, String email, LocalDate birthDate, String phoneNumber) {
      super(name, email, birthDate);
      this.phoneNumber = phoneNumber;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

}

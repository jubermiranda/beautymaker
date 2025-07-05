package com.doo.finalActv.beautymaker.model;

import java.time.LocalDate;

public class Employee extends User {

  public Employee(int id, String name, String email, LocalDate birthDate) {
    super(id, name, email, birthDate);
  }
}

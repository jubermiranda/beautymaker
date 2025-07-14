package com.doo.finalActv.beautymaker.model;

import java.time.LocalDate;

public class Employee extends User {
  private LocalDate hireDate;

  public Employee(int id, String name, String email, LocalDate birthDate, LocalDate hireDate) {
    super(id, name, email, birthDate);
    this.hireDate = hireDate;
  }
}

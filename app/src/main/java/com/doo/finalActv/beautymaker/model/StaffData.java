package com.doo.finalActv.beautymaker.model;

import java.time.LocalDate;


public class StaffData {
  public String name;
  public float rating;
  public LocalDate experience;

  public StaffData(String name, float rating, LocalDate experience) {
    this.name = name;
    this.rating = rating;
    this.experience = experience;
  }
}

package com.doo.finalActv.beautymaker.model;

import java.time.LocalDate;


public class StaffData {
  public String name;
  public float rating;
  public int ratingCount;
  public LocalDate experience;

  public StaffData(String name, float rating, int ratingCount, LocalDate experience) {
    this.name = name;
    this.rating = rating;
    this.ratingCount = ratingCount;
    this.experience = experience;
  }

  public StaffData() {
    this.name = "";
    this.rating = 0.0f;
    this.experience = LocalDate.now();
  }
}

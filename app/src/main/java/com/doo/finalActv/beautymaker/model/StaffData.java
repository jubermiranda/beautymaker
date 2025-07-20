package com.doo.finalActv.beautymaker.model;

import java.time.LocalDate;


public class StaffData implements SelectableAppointmentElement {
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

  public StaffData(StaffData other) {
    this.name = other.name;
    this.rating = other.rating;
    this.ratingCount = other.ratingCount;
    this.experience = other.experience;
  }

  public StaffData() {
    this.name = "";
    this.rating = 0.0f;
    this.experience = LocalDate.now();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof StaffData)) return false;
    StaffData that = (StaffData) o;

    return (
        Float.compare(that.rating, rating) == 0 &&
        ratingCount == that.ratingCount &&
        name.equals(that.name) &&
        experience.equals(that.experience)
    );
  }
}

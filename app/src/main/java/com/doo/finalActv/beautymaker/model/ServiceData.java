package com.doo.finalActv.beautymaker.model;

import java.time.LocalDate;


public class ServiceData  implements SelectableAppointmentElement {
  public String name;
  public String description;
  public int price; // in cents
  public int duration; // in seconds

  public ServiceData(String name, String description, int price, int duration) {
      this.name = name;
      this.description = description;
      this.price = price;
      this.duration = duration;
  }

  public ServiceData() {
      this.name = "";
      this.description = "";
      this.price = 0;
      this.duration = 0;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ServiceData)) return false;
    ServiceData that = (ServiceData) o;
    return (
        price == that.price &&
        duration == that.duration &&
        name.equals(that.name) &&
        description.equals(that.description)
    );
  }
}

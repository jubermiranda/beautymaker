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
}

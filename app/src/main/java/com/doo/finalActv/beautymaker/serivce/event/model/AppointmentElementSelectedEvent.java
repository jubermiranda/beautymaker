package com.doo.finalActv.beautymaker.serivce.event.model;

public class AppointmentElementSelectedEvent<T extends AppointmentElementSelectedEvent> {
  public final T selectedElement;

  public AppointmentElementSelectedEvent(T selectedElement) {
    this.selectedElement = selectedElement;
  }
}

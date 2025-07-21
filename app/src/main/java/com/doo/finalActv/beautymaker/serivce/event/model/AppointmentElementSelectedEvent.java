package com.doo.finalActv.beautymaker.serivce.event.model;

import com.doo.finalActv.beautymaker.model.SelectableAppointmentElement;

public class AppointmentElementSelectedEvent<T extends SelectableAppointmentElement> {
  public final T selectedElement;

  public AppointmentElementSelectedEvent(T selectedElement) {
    this.selectedElement = selectedElement;
  }
}

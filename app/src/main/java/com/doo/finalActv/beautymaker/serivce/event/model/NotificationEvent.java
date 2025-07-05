package com.doo.finalActv.beautymaker.serivce.event.model;

import com.doo.finalActv.beautymaker.model.NotificationType;

public class NotificationEvent {
  public String title;
  public String message;
  public NotificationType type;

  public NotificationEvent(
      NotificationType type,
      String title,
      String message
  ) {
    this.title = title;
    this.message = message;
    this.type = type;
  }
}

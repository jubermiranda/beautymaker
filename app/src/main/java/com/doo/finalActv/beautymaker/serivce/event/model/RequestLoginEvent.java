package com.doo.finalActv.beautymaker.serivce.event.model;

import java.util.function.Consumer;

public class RequestLoginEvent {
  public final String username;
  public char[] password;


  public RequestLoginEvent(
      String username,
      char[] password
  ) {

    this.username = username;
    this.password = password;
  }
}

package com.doo.finalActv.beautymaker.exception;

public class IllegalLogoutRequest extends Exception {
  public IllegalLogoutRequest (String message){
      super(message);
  }

  public IllegalLogoutRequest(){
      super("Illegal login request");
  }
}

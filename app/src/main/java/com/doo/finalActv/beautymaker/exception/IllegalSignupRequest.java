package com.doo.finalActv.beautymaker.exception;


public class IllegalSignupRequest extends Exception {
  public IllegalSignupRequest (String message){
      super(message);
  }

  public IllegalSignupRequest(){
      super("Illegal login request");
  }
}

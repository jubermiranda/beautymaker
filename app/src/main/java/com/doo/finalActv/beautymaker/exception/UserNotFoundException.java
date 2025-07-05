package com.doo.finalActv.beautymaker.exception;

public class UserNotFoundException extends Exception{
  public UserNotFoundException (String message){
      super(message);
  }

  public UserNotFoundException(){
      super("User not found");
  }
}

package com.doo.finalActv.beautymaker.exception;


public class IllegalSignupException extends Exception {
  public IllegalSignupException (String message){
      super(message);
  }

  public IllegalSignupException(){
      super("Signup exception");
  }
}
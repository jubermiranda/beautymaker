package com.doo.finalActv.beautymaker.exception;

public class IllegalLoginRequest  extends Exception {
  public IllegalLoginRequest(String message){
      super(message); 
  }

  public IllegalLoginRequest(){
      super("Illegal login request");
  }
}

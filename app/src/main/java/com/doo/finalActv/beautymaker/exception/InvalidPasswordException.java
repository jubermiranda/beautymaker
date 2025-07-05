package com.doo.finalActv.beautymaker.exception;

public class InvalidPasswordException extends Exception{
  public InvalidPasswordException (String message){
      super(message);
  }

  public InvalidPasswordException(){
      super("Illegal login request");
  }
}

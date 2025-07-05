package com.doo.finalActv.beautymaker.model;

import java.time.LocalDate;

public class Client extends User {
  

  public Client(int id, String name, String email, LocalDate birthDate) {
      super(id, name, email, birthDate);
  }

}

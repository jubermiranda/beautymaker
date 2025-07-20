package com.doo.finalActv.beautymaker.serivce.appdata;

import com.doo.finalActv.beautymaker.model.ServiceData;
import com.doo.finalActv.beautymaker.model.StaffData;
import com.doo.finalActv.beautymaker.model.User;
import java.util.ArrayList;

public class AppData {
  public ArrayList<StaffData> staffs;
  public ArrayList<ServiceData> services;

  public AppData() {
    this.staffs = new ArrayList<>();
    this.services = new ArrayList<>();
  }
}

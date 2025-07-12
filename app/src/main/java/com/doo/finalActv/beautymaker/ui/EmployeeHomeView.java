package com.doo.finalActv.beautymaker.ui;

import com.doo.finalActv.beautymaker.model.MenuEntry;
import javax.swing.JPanel;


public class EmployeeHomeView extends BaseHomeView {

  public EmployeeHomeView() {
    super(
      new MenuEntry[]{
        new MenuEntry("Hello", new JPanel()),
      }
    );
  }

}

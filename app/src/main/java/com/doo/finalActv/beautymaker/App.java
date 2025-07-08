package com.doo.finalActv.beautymaker;

import com.doo.finalActv.beautymaker.model.NotificationType;
import com.doo.finalActv.beautymaker.ui.MainWindow;
import javax.swing.JFrame;

public class App {

  public static void main(String[] args) {
    MainWindow mainWindow = new MainWindow();
    mainWindow.setExtendedState(JFrame.MAXIMIZED_BOTH);
    mainWindow.setVisible(true);
  }
}

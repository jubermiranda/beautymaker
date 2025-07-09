package com.doo.finalActv.beautymaker;

import com.doo.finalActv.beautymaker.model.NotificationType;
import com.doo.finalActv.beautymaker.serivce.event.EventManager;
import com.doo.finalActv.beautymaker.serivce.event.model.NotificationEvent;
import com.doo.finalActv.beautymaker.ui.MainWindow;
import javax.swing.JFrame;

public class App {

  public static void main(String[] args) {
    MainWindow mainWindow = new MainWindow();
    mainWindow.setExtendedState(JFrame.MAXIMIZED_BOTH);
    mainWindow.setVisible(true);

    // Test the notification system
    testNotification(mainWindow);

  }

  private static void testNotification(MainWindow mainWindow) {
    try {
      EventManager.getInstance().publish(new NotificationEvent(
          NotificationType.WARNING,
          "Test Notification",
          "This is a test notification message.\nIt can span multiple lines."
      ));
      Thread.sleep(2000);
      EventManager.getInstance().publish(new NotificationEvent(
          NotificationType.WARNING,
          "Another Notification",
          "This is another test notification message."
      ));
      Thread.sleep(2000);
      // a large text message notification
      EventManager.getInstance().publish(new NotificationEvent(
          NotificationType.ERROR,
          "Large Notification",
          "This is a large notification message that is intended to test the wrapping and display of long text in the notification panel. It should handle multiple lines correctly and not overflow the panel boundaries. Let's see how it performs with this lengthy message."
      ));
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}

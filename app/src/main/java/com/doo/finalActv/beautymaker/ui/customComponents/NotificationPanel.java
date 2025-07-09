package com.doo.finalActv.beautymaker.ui.customComponents;

import com.doo.finalActv.beautymaker.model.NotificationType;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import javax.swing.Box;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class NotificationPanel extends JPanel {
  public static final int WIDTH = 300;
  public static final int HEIGHT = 100;
  private static final int STD_DISPLAY_TIME = 5000;
  private static final NotificationType STD_TYPE = NotificationType.WARNING;

  private JPanel container;
  private int displayTime;
  Timer timer;

  public NotificationPanel(String title, String message, JPanel mainContainer, int display_time, NotificationType type) {
    this.container = mainContainer;
    this.displayTime = display_time > 0 ? display_time : STD_DISPLAY_TIME;

    setLayout(new BorderLayout());
    setPreferredSize(new Dimension(WIDTH, HEIGHT));
    setMaximumSize(new Dimension(WIDTH, HEIGHT));
    //setOpaque(false);

    JLabel titleLabel = new JLabel(title);

    JLabel messageLabel = new JLabel("<html>" + message.replace("\n", "<br>") + "</html>");
    

    JPanel content = new JPanel();
    content.setOpaque(false);
    content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
    content.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    content.add(titleLabel);
    content.add(Box.createVerticalStrut(6));
    content.add(messageLabel);

    add(content, BorderLayout.CENTER);
    startLifecycle();
  }

  public NotificationPanel(String title, String message, JPanel mainContainer) {
    this(title, message, mainContainer, STD_DISPLAY_TIME, STD_TYPE);
  }

  public NotificationPanel(String title, String message, JPanel mainContainer, NotificationType type) {
    this(title, message, mainContainer, STD_DISPLAY_TIME, type);
  }

  public NotificationPanel(String title, String message, JPanel mainContainer, int display_time) {
    this(title, message, mainContainer, display_time, STD_TYPE);
  }

  private void startLifecycle() {
    this.timer = new Timer(this.displayTime, (ActionEvent e) -> dispose());
    timer.setRepeats(false);
    timer.start();
  }

  private void dispose() {
    if (this.container != null) {
      container.remove(this);
      container.revalidate();
      container.repaint();
    }
  }
}

package com.doo.finalActv.beautymaker.model;

import com.doo.finalActv.beautymaker.ui.customComponents.MenuCardPanel;
import javax.swing.JPanel;

public class MenuEntry {
  private JPanel menuPanel;
  private JPanel viewPanel;
  private String key;
  private boolean isSelected = false;

  public MenuEntry(String key, JPanel viewPanel) {
    this.menuPanel = new MenuCardPanel(key);
    this.viewPanel = viewPanel;
    this.key = key;
  }

  public JPanel getMenuPanel() {
    return this.menuPanel;
  }

  public JPanel getViewPanel() {
    return this.viewPanel;
  }

  public String getKey() {
    return this.key;
  }

  public boolean isSelected() {
    return this.isSelected;
  }


  public void select() {
    this.isSelected = true;
    this.updateSelection();
  }
  public void unselect() {
    this.isSelected = false;
    this.updateSelection();
  }

  private void updateSelection() {
    if (this.menuPanel instanceof MenuCardPanel menuCardPanel) {
      menuCardPanel.setSelected(this.isSelected);
    }
  }

}

package com.doo.finalActv.beautymaker.ui.client.cards;

import com.doo.finalActv.beautymaker.model.SelectableAppointmentElement;
import com.doo.finalActv.beautymaker.serivce.event.EventManager;
import com.doo.finalActv.beautymaker.serivce.event.model.AppointmentElementSelectedEvent;
import java.awt.Color;

public abstract class AppointmentElementCard<T extends SelectableAppointmentElement> extends javax.swing.JPanel {

  private Color selectedColor;
  private Color unselectedColor;
  private Color hoverColor;
  private boolean isSelected = false;
  private boolean isHovered = false;

  protected T element;

  public AppointmentElementCard(T element) {
    this.unselectedColor = new java.awt.Color(204, 204, 204);
    this.selectedColor = new java.awt.Color(153, 153, 153);
    this.hoverColor = new java.awt.Color(192, 192, 192);
    this.element = element;
    initializeEvents();
  }

  public void setSelected(boolean selected) {
    this.isSelected = selected;
    this.updateAppearance();
  }

  public void toggleSelected(){
    this.isSelected = !(this.isSelected);
  }


  // private functions

  private void onMouseClick() {
    EventManager.getInstance().publish(
        new AppointmentElementSelectedEvent<>(this.element)
    );
  }

  private void initializeEvents() {
    this.addMouseListener(new java.awt.event.MouseAdapter() {
      @Override
      public void mouseEntered(java.awt.event.MouseEvent evt) {
        AppointmentElementCard.this.isHovered = true;
        AppointmentElementCard.this.updateAppearance();
      }

      @Override
      public void mouseExited(java.awt.event.MouseEvent evt) {
        AppointmentElementCard.this.isHovered = false;
        AppointmentElementCard.this.updateAppearance();
      }

      @Override
      public void mouseClicked(java.awt.event.MouseEvent evt) {
        AppointmentElementCard.this.onMouseClick();
      }
    });

    EventManager.getInstance().subscribe(
        AppointmentElementSelectedEvent.class,
        (event) -> {
          if (event.selectedElement.equals(this.element)) {
            this.setSelected(true);
          } else {
            this.setSelected(false);
          }
        }
    );
  }

  private void updateAppearance() {
    if (this.isHovered) {
      this.setBackground(this.hoverColor);
    } else {
      if( this.isSelected) {
        this.setBackground(this.selectedColor);
      } else {
        this.setBackground(this.unselectedColor);
      }
    }
    this.repaint();
  }
}

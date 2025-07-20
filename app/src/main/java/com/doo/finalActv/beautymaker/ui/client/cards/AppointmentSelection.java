package com.doo.finalActv.beautymaker.ui.client.cards;

import com.doo.finalActv.beautymaker.model.ServiceData;
import com.doo.finalActv.beautymaker.model.StaffData;
import com.doo.finalActv.beautymaker.serivce.appdata.ContentProvider;
import com.doo.finalActv.beautymaker.serivce.appdata.DataChangeListener;
import java.awt.Color;

public class AppointmentSelection extends javax.swing.JPanel implements DataChangeListener {
  private StaffData staff;
  private ServiceData service;
  
  public AppointmentSelection() {
    initialize();

    this.staff = null;
    this.service = null;
    this.updateView();
  }

  public AppointmentSelection(StaffData staff, ServiceData service) {
    initialize();

    this.staff = staff;
    this.service = service;
    this.updateView();
  }

  public AppointmentSelection(StaffData staff) {
    initialize();

    this.staff = staff;
    this.service = null;
    this.updateView();
  }

  public AppointmentSelection(ServiceData service) {
    initialize();

    this.staff = null;
    this.service = service;
    this.updateView();
  }

  private void updateSelection(StaffData staff, ServiceData service) {
    this.staff = staff;
    this.service = service;
    this.updateView();
  }

  private void updateSelection(StaffData staff) {
    this.staff = staff;
    this.service = null;
    this.updateView();
  }

  private void updateSelection(ServiceData service) {
    this.staff = null;
    this.service = service;
    this.updateView();
  }

  private void updateSelection() {
    this.staff = null;
    this.service = null;
    this.updateView();
  }

  private void initialize() {
    this.setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.X_AXIS));
    this.setPreferredSize(new java.awt.Dimension(1000, 40));
    this.setMaximumSize(new java.awt.Dimension(1000, 40));
    this.setBorder(javax.swing.BorderFactory.createLineBorder(Color.GREEN, 3));
    this.setOpaque(false);
    this.setVisible(true);

    ContentProvider.getInstance().addListener(this, "appointment");
  }

  private void updateView() {
    this.removeAll();

    if(this.staff == null && this.service == null) {
      this.setPreferredSize(new java.awt.Dimension(1000, 40));
      this.setMaximumSize(new java.awt.Dimension(1000, 40));
      this.setVisible(false);
      
    } else {
      this.setPreferredSize(new java.awt.Dimension(1000, 240));
      this.setMaximumSize(new java.awt.Dimension(1000, 240));
      this.setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.X_AXIS));
      this.setBorder(javax.swing.BorderFactory.createLineBorder(Color.GREEN, 3));
      this.setVisible(true);
      this.setOpaque(false);
      
      if(this.staff != null){
        StaffCard staffCard = new StaffCard(new StaffData(this.staff));
        //staffCard.setMaximumSize(new java.awt.Dimension(490, 240));
        this.add(staffCard);
      }
      if(this.service != null){
        ServiceCard serviceCard = new ServiceCard(new ServiceData(this.service));
        //serviceCard.setMaximumSize(new java.awt.Dimension(490, 240));
        this.add(serviceCard);
      }
    }

    this.revalidate();
    this.repaint();
  }

  @Override
  public void onDataChanged() {
    ServiceData serviceData = ContentProvider.getInstance().getSelectedService();
    StaffData staffData = ContentProvider.getInstance().getSelectedStaff();
    this.updateSelection(staffData, serviceData);
  }
}

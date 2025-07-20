package com.doo.finalActv.beautymaker.ui;

import com.doo.finalActv.beautymaker.model.MenuEntry;
import com.doo.finalActv.beautymaker.ui.client.AppointmentsView;
import com.doo.finalActv.beautymaker.ui.client.ProfileView;
import com.doo.finalActv.beautymaker.ui.client.ServicesView;
import com.doo.finalActv.beautymaker.ui.client.StaffView;
import com.doo.finalActv.beautymaker.ui.client.cards.AppointmentSelection;
import javax.swing.JPanel;

public class ClientHomeView extends BaseHomeView {

  public ClientHomeView() {
    super(
            new MenuEntry[]{
              new MenuEntry("Staff", new StaffView()),
              new MenuEntry("Services", new ServicesView()),
              new MenuEntry("Appointments", new AppointmentsView()),
              new MenuEntry("Profile", new ProfileView())
            }
    );
  }

}

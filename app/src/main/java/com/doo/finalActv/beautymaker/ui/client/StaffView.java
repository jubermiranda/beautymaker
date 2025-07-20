package com.doo.finalActv.beautymaker.ui.client;

import com.doo.finalActv.beautymaker.serivce.appdata.ContentProvider;
import com.doo.finalActv.beautymaker.serivce.appdata.DataChangeListener;
import com.doo.finalActv.beautymaker.ui.client.cards.AppointmentSelection;
import com.doo.finalActv.beautymaker.ui.client.cards.StaffCard;
import java.util.ArrayList;
import javax.swing.JPanel;

public class StaffView extends CardListView implements DataChangeListener {

  public StaffView() {
    super("Beauty Agents");
    super.updateSelectionPanel(new AppointmentSelection());
    this.revalidate();
    this.repaint();
    this.initialize();
  }

  private void initialize() {
    ContentProvider.getInstance().addListener(this, "staffs");
  }

  @Override
  public void onDataChanged() {
    ArrayList<StaffCard> staffCards = new ArrayList<>();

    ContentProvider.getInstance().getStaffs().forEach(staffData -> {
      StaffCard staffCard = new StaffCard(staffData);
      staffCards.add(staffCard);
    });

    super.updateCardList(staffCards);
    this.revalidate();
    this.repaint();
  }
}

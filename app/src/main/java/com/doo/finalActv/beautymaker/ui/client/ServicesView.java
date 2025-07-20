package com.doo.finalActv.beautymaker.ui.client;

import com.doo.finalActv.beautymaker.serivce.appdata.ContentProvider;
import com.doo.finalActv.beautymaker.serivce.appdata.DataChangeListener;
import com.doo.finalActv.beautymaker.ui.client.cards.ServiceCard;
import java.util.ArrayList;


public class ServicesView extends CardListView implements DataChangeListener {

  public ServicesView() {
    super("Beauty Services");
    this.initialize();
  }

  private void initialize() {
    ContentProvider.getInstance().addListener(this, "services");
  }

  @Override
  public void onDataChanged() {
    ArrayList<ServiceCard> serviceCards = new ArrayList<>();

    ContentProvider provider = ContentProvider.getInstance();

    ContentProvider.getInstance().getServices().forEach(serviceData -> {
      ServiceCard serviceCard = new ServiceCard(serviceData);
      serviceCards.add(serviceCard);
    });

    super.updateCardList(serviceCards);
    this.revalidate();
    this.repaint();
  }

}

package com.doo.finalActv.beautymaker.serivce.appdata;

import com.doo.finalActv.beautymaker.model.ServiceData;
import com.doo.finalActv.beautymaker.model.StaffData;
import com.doo.finalActv.beautymaker.model.User;
import com.doo.finalActv.beautymaker.serivce.db.DatabaseManager;
import com.doo.finalActv.beautymaker.serivce.event.EventManager;
import com.doo.finalActv.beautymaker.serivce.event.model.AppointmentElementSelectedEvent;
import com.doo.finalActv.beautymaker.session.SessionManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class ContentProvider {

  private static ContentProvider instance;

  private Map<String, List<DataChangeListener>> listeners;
  private AppData appData;
  private SessionManager sessionManager;

  private ContentProvider() {
    this.appData = new AppData();
    this.listeners = new HashMap<>();
    this.sessionManager = SessionManager.getInstance();
    this.initializeEvents();
  }

  public static synchronized ContentProvider getInstance() {
    if (instance == null) {
      instance = new ContentProvider();
    }
    return instance;
  }

  public User getUser() {
    return this.sessionManager.getUser();
  }

  public ArrayList<StaffData> getStaffs() {
    return this.appData.staffs;
  }

  public ArrayList<ServiceData> getServices() {
    return this.appData.services;
  }

  public StaffData getSelectedStaff() {
    return this.appData.selectedStaff;
  }

  public ServiceData getSelectedService() {
    return this.appData.selectedService;
  }

  // --
  public void addListener(DataChangeListener listener, String key) {
    listeners.computeIfAbsent(key, k -> new ArrayList<>());
    List<DataChangeListener> keyListeners = listeners.get(key);
    if (!keyListeners.contains(listener)) {
      keyListeners.add(listener);
    }

    if (listeners.get(key).size() == 1) {
      notifyListenersOf(key);
    }
  }

  public void removeListener(DataChangeListener listener, String key) {
    List<DataChangeListener> keyListeners = listeners.get(key);
    if (keyListeners != null) {
      keyListeners.remove(listener);
      if (keyListeners.isEmpty()) {
        listeners.remove(key);
      }
    }
  }

  public void notifyListeners(String key) {
    this.notifyListenersOf(key);
  }

  public void notifyListeners() {
    this.notifyAllListeners();
  }

  private void notifyListenersOf(String key) {
    List<DataChangeListener> keyListeners = listeners.get(key);
    if (keyListeners != null) {
      List<DataChangeListener> copy = new ArrayList<>(keyListeners);
      for (DataChangeListener listener : copy) {
        listener.onDataChanged();
      }
    }
  }

  private void notifyAllListeners() {
    for (List<DataChangeListener> keyListeners : listeners.values()) {
      List<DataChangeListener> copy = new ArrayList<>(keyListeners);
      for (DataChangeListener listener : copy) {
        listener.onDataChanged();
      }
    }
  }

  private void initializeEvents() {
    // async load some data
    CompletableFuture.runAsync(() -> {
      ArrayList<StaffData> staffs = DatabaseManager.getInstance().getStaffs();
      ArrayList<ServiceData> services = DatabaseManager.getInstance().getServices();

      this.appData.staffs = staffs;
      this.appData.services = services;
      this.notifyListenersOf("staffs");
      this.notifyListenersOf("services");
    });

    // listen to client selection changes
    EventManager.getInstance().subscribe(AppointmentElementSelectedEvent.class, event -> {
      switch (event.selectedElement) {
        case StaffData staffData -> {
          if (staffData.equals(this.appData.selectedStaff)) {
            this.appData.selectedStaff = null;
          } else {
            this.appData.selectedStaff = new StaffData(staffData);
          }
          this.notifyListenersOf("appointment");
        }
        case ServiceData serviceData -> {
          if (serviceData.equals(this.appData.selectedService)) {
            this.appData.selectedService = null;
          } else {
            this.appData.selectedService = new ServiceData(serviceData);
          }
          this.notifyListenersOf("appointment");
        }
        default -> {
          // do nothing
        }
      }
    });
  }
}

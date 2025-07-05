package com.doo.finalActv.beautymaker.serivce.event;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EventManager {
  private EventManager() {}
  
  private static final EventManager instance = new EventManager();

  private final Map<Class<?>, List<EventListener<?>>> listeners = new ConcurrentHashMap<>();

  public static EventManager getInstance() {
    return instance;
  }

  public <T> void subscribe(Class<T> eventType, EventListener<T> listener) {
    listeners.computeIfAbsent(eventType, k -> new ArrayList<>()).add(listener);
  }

  public <T> void unsubscribe(Class<T> eventType, EventListener<T> listener) {
    List<EventListener<?>> eventListeners = listeners.get(eventType);
    if (eventListeners != null) {
      eventListeners.remove(listener);
      if (eventListeners.isEmpty()) {
        listeners.remove(eventType);
      }
    }
  }

  @SuppressWarnings("unchecked")
  public <T> void publish(T event) {
    Class<?> eventType = event.getClass();
    List<EventListener<?>> eventListeners = listeners.get(eventType);
    if (eventListeners != null) {
      for (EventListener<?> listener : eventListeners){
        ((EventListener<T>) listener).onEvent(event);
      }
    }
  }
}

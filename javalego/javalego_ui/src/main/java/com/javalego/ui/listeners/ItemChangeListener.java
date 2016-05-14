package com.javalego.ui.listeners;

import java.util.EventListener;

public interface ItemChangeListener<T> extends EventListener {

  public void propertyChange(ItemChangeEvent<T> event);
}

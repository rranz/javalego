package com.javalego.ui.listeners;

public class ItemChangeEvent<T> {
	
	private T itemChange;

	public ItemChangeEvent(T itemChange) {
        this.setItemChange(itemChange);
    }

	public T getItemChange() {
		return itemChange;
	}

	public void setItemChange(T itemChange) {
		this.itemChange = itemChange;
	}

}

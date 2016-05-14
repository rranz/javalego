package com.javalego.ui.vaadin.component.search;

import java.util.Collection;

import com.vaadin.ui.ListSelect;

/**
 * Ejemplo de implementación de componente de lista de objetos.
 * @author ROBERTO RANZ
 *
 */
public class ListSelectSearch extends ListSelect implements ISearch {
	 
	private static final long serialVersionUID = 5934535258349016389L;

	public ListSelectSearch(String caption, Collection<?> values) {
		super(caption, values);
	}
	
	@Override
	public void find(String text) {
		
		Object value = SearchUtil.findObject(getItemIds(), text, getValue());
		
		if (value != null) {
			setValue(value);
		}
	}

	@Override
	public boolean isFilter() {
		return false;
	}
	
}

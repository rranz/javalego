package com.javalego.ui.vaadin.view.finder;

import com.javalego.exception.LocalizedException;
import com.javalego.ui.mvp.finder.IFinderView;
import com.javalego.ui.vaadin.component.listview.ListViewGridUI;
import com.vaadin.ui.CustomComponent;

/**
 * Seleccionar un bean utilizando el componente ListViewGridUI.
 * 
 * EN CONSTRUCCIÓN
 * 
 * @author ROBERTO RANZ
 *
 */
public class ListViewFinderBeanView<T> extends CustomComponent implements IFinderView<T> {

	private static final long serialVersionUID = -5098768667750015378L;

	private IFinderViewListener<T> listener;
	
	@Override
	public void load() throws LocalizedException {
		
		ListViewGridUI view = new ListViewGridUI(false, false, false, null);
		
		for(T object : listener.getObjects()) {
			view.addItem(object.toString());
		}
		
		setCompositionRoot(view);
	}

	@Override
	public void setListener(IFinderViewListener<T> listener) {
		this.listener = listener;
	}

	@Override
	public void show() {
	}

}

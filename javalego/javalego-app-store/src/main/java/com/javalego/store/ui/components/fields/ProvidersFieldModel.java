package com.javalego.store.ui.components.fields;

import java.util.ArrayList;
import java.util.List;

import com.javalego.model.keys.Key;
import com.javalego.store.ui.locale.LocaleStore;
import com.javalego.ui.field.FieldModel;
import com.javalego.ui.field.impl.AbstractFieldModel;
import com.javalego.ui.field.impl.TextFieldModel;

/**
 * Campo que muestra la lista de tecnologías soportadas por el componente
 * (proyecto, producto, ...) de la tienda (Spring, Vaadin, Shiro, ...).
 * 
 * @author ROBERTO RANZ
 *
 */
public class ProvidersFieldModel extends AbstractFieldModel {

	private static final long serialVersionUID = 6208085177762838167L;

	private List<FieldModel> fields;

	public ProvidersFieldModel() {
	}

	/**
	 * Constructor
	 * 
	 * @param title
	 */
	public ProvidersFieldModel(Key title) {
		super(title);
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param title
	 */
	public ProvidersFieldModel(String name, Key title) {
		super(name, title);
	}

	/**
	 * Lista de tecnologías disponibles.
	 * @return
	 */
	public synchronized List<FieldModel> getFields() {
		
		if (fields == null) {
			fields = new ArrayList<FieldModel>();
			fields.add(new TextFieldModel(LocaleStore.CATEGORY).setMaxSize(200));
		}
		return fields;
	}

}

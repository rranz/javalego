package com.javalego.ui.vaadin.component;

import java.util.Collection;

import com.javalego.model.locales.LocaleEditor;
import com.vaadin.data.Container;
import com.vaadin.ui.ComboBox;

/**
 * Extensión del ComboBox de Vaadin para incluir funcionalidades adicionales.
 * 
 * @author ROBERTO RANZ
 */
public class BooleanComboBox extends ComboBox {

	private static final long serialVersionUID = 1L;

	public void initialize() {
		addItem(LocaleEditor.YES);
		addItem(LocaleEditor.NO);
	}

	public BooleanComboBox(String caption, Collection<?> options) {
		super(caption, options);
		initialize();
	}

	public BooleanComboBox(String caption, Container dataSource) {
		super(caption, dataSource);
		initialize();
	}

	public BooleanComboBox(String caption) {
		super(caption);
		initialize();
	}

	public BooleanComboBox() {
		initialize();
	}

}

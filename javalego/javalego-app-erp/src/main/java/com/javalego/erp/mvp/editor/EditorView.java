package com.javalego.erp.mvp.editor;

import com.javalego.exception.LocalizedException;
import com.javalego.ui.mvp.editor.beans.IBeansEditorModel;
import com.javalego.ui.vaadin.component.util.MessagesUtil;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.VerticalLayout;

/**
 * Vista para incluye el formulario de editor de beans.
 * 
 * @author ROBERTO RANZ
 */
public class EditorView extends VerticalLayout implements View {

	private Class<?> model;

	/**
	 * Constructor
	 * 
	 * @param navigator
	 */
	public EditorView(Class<?> model) {
		this.model = model;
	}

	protected void addEditor(String name) throws LocalizedException {

		setWidth("100%");
		
		if (model != null) {
			FormEditor ed;
			try {
				
				ed = new FormEditor((IBeansEditorModel<?>) model.newInstance());
				ed.load();
				addComponent(ed);
				
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void enter(ViewChangeEvent event) {

		if (getComponentCount() == 0) {
			try {
				addEditor(event.getViewName());
			}
			catch (LocalizedException e) {
				MessagesUtil.error(e);
			}
		}

	}

}

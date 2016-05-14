package com.javalego.ui.vaadin.component;

import com.javalego.exception.LocalizedException;
import com.javalego.ui.field.impl.state.ItemStateFieldModel;
import com.javalego.ui.field.impl.state.StateFieldModel;
import com.javalego.ui.vaadin.UIContextVaadin;
import com.javalego.util.StringUtils;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

/**
 * Constructor de funcionalidades asociadas a una propiedad de tipo StateProperty
 * 
 * @author ROBERTO RANZ
 */
public class StateFieldUtil {

	/**
	 * Mostrar en una ventana la lista de estados posibles y su descripción.
	 * 
	 * @param property
	 * @param application
	 * @throws LocalizedException 
	 */
	public static void showItems(StateFieldModel property) throws LocalizedException {

		Window window = new Window("Values");

		UI.getCurrent().addWindow(window);

		GridLayout hl = new GridLayout(2, 1);
		hl.setMargin(true);
		hl.setSpacing(true);
		window.setContent(hl);

		for (ItemStateFieldModel item : property.getItems()) {

			Label label = new Label("<strong>" + item.getTitle() + "</strong>" + (item.getDescription() != null ? "<br><i>" + StringUtils.getHtmlCarriageReturn(80, item.getDescription(), false) + "</i>" : ""), ContentMode.HTML);
			Component icon = UIContextVaadin.getComponent(item.getIconBig());

			hl.addComponent(icon);
			hl.addComponent(label);

			hl.setComponentAlignment(label, Alignment.TOP_LEFT);
			hl.setComponentAlignment(icon, Alignment.TOP_CENTER);

		}

		window.setModal(true);
		window.center();
	}

	/**
	 * Crear un layout para mostrar una ventana de selección de estados posibles.
	 * 
	 * @param property
	 * @param application
	 * @throws LocalizedException 
	 */
	public static GridLayout getLayoutSelectItems(StateFieldModel property) throws LocalizedException {

		ListValuesLayout list = new ListValuesLayout(true, true);

		for (ItemStateFieldModel item : property.getItems())
			list.addItem("<strong>" + item.getTitle() + "</strong><br><i>" + StringUtils.getHtmlCarriageReturn(80, item.getDescription(), false) + "</i>", item.getIconBig(), true, item);

		return list;
	}

}

package com.javalego.ui.vaadin.component.util;

import com.javalego.model.locales.LocaleEditor;
import com.javalego.ui.UIContext;
import com.javalego.ui.vaadin.component.Dialog;
import com.javalego.ui.vaadin.component.Dialog.ConfirmedListener;
import com.javalego.ui.vaadin.component.util.MessageBox.EventListener;
import com.vaadin.ui.Component;

/**
 * Métodos de utilidad relacionados con diálogos.
 * 
 * @author ROBERTO RANZ
 */
public class DialogUtil {

	private DialogUtil() {
	}

	/**
	 * Mostrar un diálogo que incluye un componente
	 * 
	 * @param component
	 * @param title
	 */
	public static void dialog(Component component, String title) {

		new Dialog(component, title, true, false, true, null);
	}

	/**
	 * Mostar un diálogo que incluye un componente con una dimensión concreta y
	 * con la opción de mostrarlo modalmente.
	 * 
	 * @param component
	 * @param title
	 * @param modal
	 * @param dimensionUtil
	 */
	public static void dialog(Component component, String title, boolean modal, DimensionUtil dimensionUtil) {

		new Dialog(component, title, modal, false, dimensionUtil, null);
	}

	/**
	 * Mostar un diálogo que incluye un componente con la opción de mostrarlo
	 * modalmente.
	 * 
	 * @param component
	 * @param title
	 * @param modal
	 */
	public static void dialog(Component component, String title, boolean modal) {

		new Dialog(component, title, modal, false, true, null);
	}

	/**
	 * Mostar un diálogo de confirmación
	 * 
	 * @param component
	 * @param title
	 * @param confirmListener
	 */
	public static void dialog(Component component, String title, ConfirmedListener confirmListener) {

		new Dialog(component, title, true, true, true, confirmListener);
	}

	/**
	 * Mostar un diálogo de confirmación con una dimensión específica.
	 * 
	 * @param component
	 * @param title
	 * @param dimensionUtil
	 * @param confirmListener
	 */
	public static void dialog(Component component, String title, DimensionUtil dimensionUtil, ConfirmedListener confirmListener) {

		new Dialog(component, title, true, true, dimensionUtil, confirmListener);
	}

	/**
	 * Diálogo de selección OK o Cancel. Listener instancia gestionará la
	 * validación y cierre de la ventana.
	 * 
	 * @param message
	 * @param listener
	 * @return
	 */
	public static void getConfirm(final String message, EventListener listener) {

		new MessageBox(UIContext.getText(LocaleEditor.WARN), MessageBox.Icon.QUESTION, message, MessageBox.getButtonYes(), MessageBox.getButtonNo()).show(listener);
	}

}

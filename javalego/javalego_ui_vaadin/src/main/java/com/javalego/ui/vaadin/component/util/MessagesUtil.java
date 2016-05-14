package com.javalego.ui.vaadin.component.util;

import com.javalego.exception.LocalizedException;
import com.javalego.model.keys.Key;
import com.javalego.model.locales.LocaleWarnings;
import com.javalego.ui.UIContext;
import com.vaadin.server.Page;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

/**
 * Clase encargada de mostrar mensajes al usuario.
 * 
 * @author ROBERTO RANZ
 * 
 */
public class MessagesUtil {

	private MessagesUtil() {
	}

	/**
	 * Mostrar un aviso al usuario.
	 * 
	 * @param message
	 */
	public static void warning(String message) {
		
		Notification.show(UIContext.getText(LocaleWarnings.WARNING), formatMessage(message), Type.WARNING_MESSAGE);
	}

	/**
	 * Mostrar un aviso al usuario.
	 * 
	 * @param title
	 * @param message
	 */
	public static void warning(String title, String message) {
		
		Notification.show(title, formatMessage(message), Type.WARNING_MESSAGE);
	}

	private static String formatMessage(String message) {
		return message;
	}

	/**
	 * Mostrar un mensaje informativo al usuario.
	 * 
	 * @param message
	 */
	public static void information(String message) {
		
		new Notification(null, formatMessage(message), Type.HUMANIZED_MESSAGE, true).show(Page.getCurrent());
	}

	/**
	 * Mostrar un mensaje informativo al usuario.
	 * 
	 * @param title
	 * @param message
	 */
	public static void information(String title, String message) {
		
		new Notification(title, formatMessage(message), Type.HUMANIZED_MESSAGE, true).show(Page.getCurrent());
	}

	/**
	 * Mostrar un mensaje de error al usuario.
	 * 
	 * @param message
	 */
	public static void error(String message) {

		error(UIContext.getText(LocaleWarnings.ERROR), formatMessage(message));
	}

	/**
	 * Mostrar un mensaje de error al usuario.
	 * 
	 * @param exception
	 */
	public static void error(LocalizedException exception) {
		
		if (exception.isError()) {
			exception.printStackTrace();
		}

		error(UIContext.getText(LocaleWarnings.ERROR), formatMessage(exception.getLocalizedMessage()));
	}

	/**
	 * Mostrar un mensaje de error al usuario.
	 * 
	 * @param exception
	 */
	public static void error(String message, Exception exception) {
		
		exception.printStackTrace();

		error(UIContext.getText(LocaleWarnings.ERROR), (message + ". ") + (exception instanceof NullPointerException ? "NULL POINTER" : formatMessage(exception.getLocalizedMessage())));
	}

	/**
	 * Mostrar un mensaje de error al usuario.
	 * 
	 * @param title
	 * @param message
	 */
	public static void error(String title, String message) {
		
		Notification notification = new Notification(title, message, Notification.Type.ERROR_MESSAGE, true);
		notification.setDelayMsec(1000);
		notification.show(Page.getCurrent());		
		
		//Notification.show(title, getMessageError(message), Type.ERROR_MESSAGE);
	}

	/**
	 * Mostrar un mensaje de error al usuario.
	 * 
	 * @param title
	 * @param message
	 */
	public static void error(Key title, String message) {
		
		error(UIContext.getText(title), formatMessage(message));
	}
	
	/**
	 * Mensaje de opción no disponible
	 */
	public static void optionNotAvailable() {
		
		Notification.show(UIContext.getText(LocaleWarnings.DISABLED_OPTION), Type.WARNING_MESSAGE);
	}

	/**
	 * Mensaje de aviso para que el usuario seleccionad un registro u objeto.
	 * 
	 * @param application
	 */
	public static void warningSelectedObject() {
		
		Notification.show(UIContext.getText(LocaleWarnings.SELECT_RECORD), Type.WARNING_MESSAGE);
	}
	
}

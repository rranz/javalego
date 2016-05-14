package com.javalego.ui.vaadin.component.util;

import com.vaadin.server.Page;

/**
 * Métodos de utilidad relativos a la pantalla del dispositivo del usuario donde
 * se ejecuta la aplicación.
 * 
 * @author ROBERTO RANZ
 *
 */
public class ScreenSize {

	/**
	 * Número de pixels menor para considerar un tipo SMALL
	 */
	private int small = 600;

	/**
	 * Número de pixels mayor para considerar un tipo LARGE.
	 */
	private int large = 1050;

	/**
	 * Constructor
	 * 
	 * @param small
	 * @param large
	 */
	public ScreenSize(int small, int large) {
		this.small = small;
		this.large = large;
	}

	/**
	 * Constructor.
	 * <p>
	 * Valores por defecto: small = 600 y large = 1050
	 * </p>
	 */
	public ScreenSize() {
	}

	/**
	 * Type width screen
	 * 
	 * @author ROBERTO RANZ
	 *
	 */
	public enum Type {
		SMALL, MEDIUM, LARGE;
	}

	/**
	 * A helper method to get the categorized size for the currently active
	 * client.
	 *
	 * @return the screen size category for currently active client
	 */
	public Type getScreenSize() {

		int width = Page.getCurrent().getBrowserWindowWidth();
		if (width < small) {
			return Type.SMALL;
		}
		else if (width > large) {
			return Type.LARGE;
		}
		else {
			return Type.MEDIUM;
		}
	}

	public int getSmall() {
		return small;
	}

	public void setSmall(int small) {
		this.small = small;
	}

	public int getLarge() {
		return large;
	}

	public void setLarge(int large) {
		this.large = large;
	}
}

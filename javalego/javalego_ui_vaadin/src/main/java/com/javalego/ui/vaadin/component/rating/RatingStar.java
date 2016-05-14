package com.javalego.ui.vaadin.component.rating;

import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;

/**
 * Estrella de una evaluación que contiene su estado y cambia de color si se
 * activa.
 * 
 * @author ROBERTO RANZ
 *
 */
public class RatingStar extends Label {

	private static final long serialVersionUID = -863499634694418056L;

	private boolean checked;

	/**
	 * Constructor
	 * 
	 * @param checked
	 */
	public RatingStar(boolean checked) {

		super(FontAwesome.STAR.getHtml());

		setChecked(checked);
		
		//addStyleName("fa-4x");
		// addStyleName("fa-lg"); fa-2x 6x fa-border colored fa-spin ... ver
		// addon fontawesomelabel scss para ver opciones o en la web
		// fontawesome.

		setSizeUndefined();

		setContentMode(ContentMode.HTML);
	}

	public boolean isChecked() {
		return checked;
	}

	/**
	 * Marcar o desmarcar estrella
	 * 
	 * @param checked
	 */
	public void setChecked(boolean checked) {

		this.checked = checked;

		if (checked) {
			addStyleName("color-star-on");
			removeStyleName("color-star-off");
		}
		else {
			removeStyleName("color-star-on");
			addStyleName("color-star-off");
		}
	}

}

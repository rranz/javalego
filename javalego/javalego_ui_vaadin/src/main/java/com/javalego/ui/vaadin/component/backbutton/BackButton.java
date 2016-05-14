package com.javalego.ui.vaadin.component.backbutton;

import org.apache.log4j.Logger;

import com.javalego.model.keys.Icon;
import com.javalego.ui.vaadin.icons.ResourceIconsVaadin;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

/**
 * Botón de retorno
 * 
 * @author ROBERTO RANZ
 */
public class BackButton extends VerticalLayout {

	private static final long serialVersionUID = -9054418779230436084L;

	private static final Logger logger = Logger.getLogger(BackButton.class);

	/**
	 * Css utilizado por defecto.
	 */
	private static final IBackButtonCss defaultCss = new BackButtonCss();

	/**
	 * Css
	 */
	private IBackButtonCss css;

	/**
	 * Constructor
	 * 
	 * @param iconName
	 *            Nombre clave del icono o imagen cargada en la aplicación que
	 *            deseamos mostrar como botón de retorno. El icono tiene que
	 *            tener 32px como máximo.
	 * 
	 */
	public BackButton(Icon icon) {
		this(icon, null);
	}

	/**
	 * Constructor
	 * 
	 * @param iconName
	 *            Nombre clave del icono o imagen cargada en la aplicación que
	 *            deseamos mostrar como botón de retorno. El icono tiene que
	 *            tener 32px como máximo.
	 * @param width
	 * 
	 */
	public BackButton(Icon eicon, String width) {

		if (css == null) {
			css = defaultCss;
		}

		setDescription("Volver");

		addStyleName(css.getLayout());

		if (eicon != null) {
			Component icon = null;
			try {
				icon = ResourceIconsVaadin.getCurrent().getComponent(eicon);
				addComponent(icon);
				setComponentAlignment(icon, Alignment.MIDDLE_CENTER);
			}
			catch (Exception e1) {
				logger.error("ERROR LOAD ICON '" + icon + "'", e1);
			}
		}

		setHeight("100%");
		setWidth(width == null ? "32px" : width);
	}

}

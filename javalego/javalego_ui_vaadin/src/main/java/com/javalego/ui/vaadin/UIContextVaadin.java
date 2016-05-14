package com.javalego.ui.vaadin;

import java.util.Locale;

import com.javalego.exception.LocalizedException;
import com.javalego.model.keys.Icon;
import com.javalego.ui.UIContext;
import com.vaadin.ui.Component;

/**
 * Contexto UI para Vaadin
 * 
 * @author ROBERTO RANZ
 *
 */
public class UIContextVaadin extends UIContext {

	protected UIContextVaadin() {
		super();
	}
	
	/**
	 * Obtener el componente UI a partir de la clave de un icono registrado en
	 * los repositorios de imágenes.
	 * 
	 * @param icon
	 * @return
	 * @throws LocalizedException
	 */
	public static Component getComponent(Icon icon) throws LocalizedException {
		return (Component)getCurrent().getIconComponent(icon);
	}
	
	/**
	 * Obtener el componente UI a partir de la clave de un icono registrado en
	 * los repositorios de imágenes.
	 * 
	 * @param icon
	 * @param locale
	 * @return
	 * @throws LocalizedException
	 */
	public static Component getComponent(Icon icon, Locale locale) throws LocalizedException {
		return (Component)getCurrent().getIconComponent(icon, locale);
	}
	
	/**
	 * Obtener el componente UI a partir de la clave de un icono registrado en
	 * los repositorios de imágenes.
	 * 
	 * @param icon
	 * @return
	 * @throws LocalizedException
	 */
	@Override
	public Component getIconComponent(Icon icon) throws LocalizedException {
		return (Component)super.getIconComponent(icon);
	}	
}

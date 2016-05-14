package com.javalego.ui.vaadin.icons;

import java.util.HashMap;
import java.util.Locale;

import com.javalego.exception.LocalizedException;
import com.javalego.model.AbstractBaseModel;
import com.javalego.model.keys.Icon;
import com.javalego.ui.icons.RepositoryIconsUI;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Repositorio de Iconos basados en la librería JS FontAwesome que integra el
 * theme Valo.
 * 
 * @author ROBERTO RANZ
 * 
 */
public class FontAwesomeIcons extends AbstractBaseModel implements RepositoryIconsUI<Label, Icon> {

	private static final long serialVersionUID = 4571564274393696058L;

	/**
	 * Lista de iconos disponibles para su acceso a partir de una clave única
	 * que usaremos en nuestro modelo de datos para crear menús, editores, ...
	 */
	private HashMap<Icon, FontAwesome> icons = new HashMap<Icon, FontAwesome>();

	private Locale locale;

	/**
	 * Instancia actual
	 */
	private static FontAwesomeIcons current = null;

	private FontAwesomeIcons() {
	}

	/**
	 * Añadir icono a la lista
	 * 
	 * @param icon
	 * @param fontAwesome
	 */
	public void addIcon(Icon icon, FontAwesome fontAwesome) {
		icons.put(icon, fontAwesome);
	}

	/**
	 * Eliminar icono de la lista
	 * 
	 * @param icon
	 */
	public void removeIcon(Icon icon) {
		icons.remove(icon);
	}

	/**
	 * Obtener la instancia actual de los servicios de iconos.
	 * 
	 * @return
	 */
	public synchronized static FontAwesomeIcons getCurrent() {

		if (current == null) {
			current = new FontAwesomeIcons();
		}
		return current;
	}

	@Override
	public Label getComponent(String caption, String description, Icon icon) {

		FontAwesome key = icons.get(icon);

		if (key != null) {
			Label label = new Label(key.getHtml() + (caption != null ? " " + caption : ""), ContentMode.HTML);
			label.addStyleName(ValoTheme.LABEL_HUGE);

			label.setSizeUndefined();
			if (description != null) {
				label.setDescription(description);
			}
			return label;
		}
		else {
			return null;
		}
	}

	@Override
	public Label getComponent(String caption, Icon icon) {

		return getComponent(caption, null, icon);
	}

	@Override
	public Label getComponent(Icon icon) {

		return getComponent(null, icon);
	}

	@Override
	public byte[] getBytes(Icon icon) {

		return null;
	}

	@Override
	public Label getComponent(Icon icon, int size) throws LocalizedException {

		return getComponent(icon);
	}

	@Override
	public boolean exist(Icon key) {
		return icons.get(key) != null;
	}

	@Override
	public Locale getLocale() {
		return locale;
	}

	/**
	 * Establecer la localización de los iconos del respositorio
	 * 
	 * @param locale
	 */
	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	@Override
	public byte[] getBytes(String key) throws LocalizedException {
		return null;
	}

	@Override
	public boolean exist(String key) {
		
		for (String item : getNames()) {
			if (item.equals(key)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String[] getNames() {

		String[] items = new String[icons.size()];
		int index = 0;
		for (Icon icon : icons.keySet()) {
			items[index++] = icon.name();
		}

		return items;
	}

}

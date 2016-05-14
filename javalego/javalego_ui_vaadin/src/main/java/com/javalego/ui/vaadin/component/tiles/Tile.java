package com.javalego.ui.vaadin.component.tiles;

import org.apache.log4j.Logger;

import com.javalego.exception.LocalizedException;
import com.javalego.model.keys.Icon;
import com.javalego.ui.vaadin.UIContextVaadin;
import com.javalego.ui.vaadin.css.CssVaadin;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 * Tile = baldosa
 * 
 * @author ROBERTO RANZ
 * 
 */
public class Tile extends CssLayout {

	private static final long serialVersionUID = -2552246079789395814L;

	private static final Logger logger = Logger.getLogger(Tile.class);

	private Label label;

	private VerticalLayout iconLayout;

	/**
	 * Constructor
	 * 
	 * @param icon
	 * @param title
	 */
	public Tile(String title) {
		this(null, title, true);
	}

	/**
	 * Constructor
	 */
	public Tile() {
		this(true);
	}
	
	/**
	 * Constructor
	 */
	public Tile(boolean shadow) {
		
		addStyleName(shadow ? CssVaadin.getShadow() : CssVaadin.getBorder());
		addStyleName(CssVaadin.getTile());
	}

//	@Override
//	protected String getCss(Component c) {
//		return "display: block; text-align: center;";
//	}

	/**
	 * Constructor
	 * 
	 * @param icon
	 * @param title
	 */
	public Tile(Icon icon, String title) {

		this(icon, title, true);
	}

	/**
	 * Constructor
	 * 
	 * @param component
	 *            Personalización del componente incluido en el tile.
	 * @param width
	 */
	public Tile(Component component, boolean shadow) {

		addStyleName(shadow ? CssVaadin.getShadow() : CssVaadin.getBorder());
		addStyleName(CssVaadin.getTile());

		addComponent(component);
	}

	/**
	 * Constructor
	 * 
	 * @param icon
	 * @param title
	 * @param shadow
	 */
	public Tile(Icon icon, String title, boolean shadow) {

		addStyleName(shadow ? CssVaadin.getShadow() : CssVaadin.getBorder());
		addStyleName(CssVaadin.getTile());

		if (icon != null) {
			try {
				// Se incluye verticallayout para poder alinear la imagen ya que
				// cuando se define un tamaño, la imagen no queda centrada.
				iconLayout = new VerticalLayout();

				addIcon(icon);

				addComponent(iconLayout);

			}
			catch (Exception e1) {
				logger.error("ERROR LOAD ICON '" + icon + "'", e1);
			}
		}

		// Título
		label = new Label(title, ContentMode.HTML);
		addComponent(label);
	}

	/**
	 * Añadir icono
	 * 
	 * @param icon
	 * @throws LocalizedException
	 */
	private void addIcon(Icon icon) throws LocalizedException {

		iconLayout.removeAllComponents();

		Component component = UIContextVaadin.getComponent(icon);

		iconLayout.addComponent(component);
		iconLayout.setComponentAlignment(component, Alignment.TOP_CENTER);
		iconLayout.setWidth("100%");
	}

	/**
	 * Título
	 * 
	 * @param title
	 */
	public void setTitle(String title) {

		if (label != null) {
			label.setValue(title);
		}
	}

	/**
	 * Icon
	 * 
	 * @param icon
	 * @throws LocalizedException
	 */
	public void setIcon(Icon icon) throws LocalizedException {

		if (icon != null && iconLayout != null) {
			addIcon(icon);
		}
	}

}

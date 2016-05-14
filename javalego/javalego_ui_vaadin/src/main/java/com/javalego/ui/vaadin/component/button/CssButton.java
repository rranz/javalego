package com.javalego.ui.vaadin.component.button;

import org.apache.log4j.Logger;

import com.javalego.exception.LocalizedException;
import com.javalego.model.keys.Colors;
import com.javalego.model.keys.Icon;
import com.javalego.ui.vaadin.UIContextVaadin;
import com.javalego.ui.vaadin.component.button.css.CssButtonCss;
import com.javalego.ui.vaadin.component.button.css.ICssButtonCss;
import com.javalego.ui.vaadin.css.CssVaadin;
import com.javalego.ui.vaadin.icons.ResourceIconsVaadin;
import com.vaadin.server.Resource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Label;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Botón creado a partir de una configuración de hoja de estilos (css) y un
 * layout de tipo CssLayout básico.
 * 
 * @author ROBERTO RANZ
 */
public class CssButton extends CssLayout {

	private static final Logger logger = Logger.getLogger(CssButton.class);

	private static final long serialVersionUID = 1L;

	private Colors color;

	private Label label;

	/**
	 * Clases Css utilizadas.
	 */
	protected ICssButtonCss css;

	/**
	 * Componente del icono.
	 */
	private Component iconComponent;

	private boolean loaded;

	/**
	 * Aplicar css reducido: padding 2px en lugar de 10px.
	 */
	private boolean small;

	public CssButton() {
	}

	/**
	 * Constructor
	 * 
	 * @param caption
	 */
	public CssButton(String caption, Colors color, boolean shadow) {

		initComponent(caption, null, color, null, null, shadow);
	}

	/**
	 * Constructor
	 * 
	 * @param caption
	 */
	public CssButton(String caption, Colors color) {

		initComponent(caption, null, color, null, null, false);
	}

	/**
	 * Constructor
	 * 
	 * @param caption
	 */
	public CssButton(String caption, Colors color, ICssButtonCss css) {

		initComponent(caption, null, color, null, css, false);
	}

	/**
	 * Constructor
	 * 
	 * @param caption
	 * @param icon
	 */
	public CssButton(String caption, Icon icon) {

		initComponent(caption, null, color, icon, null, false);
	}

	/**
	 * Constructor
	 * 
	 * @param caption
	 * @param icon
	 * @param css
	 */
	public CssButton(String caption, Icon icon, ICssButtonCss css) {

		initComponent(caption, null, color, icon, css, false);
	}

	/**
	 * Constructor
	 * 
	 * @param icon
	 */
	public CssButton(Icon icon) {

		initComponent(null, null, color, icon, null, false);
	}

	/**
	 * Constructor
	 * 
	 * @param icon
	 * @param description
	 */
	public CssButton(Icon icon, String description) {

		initComponent(null, description, color, icon, null, false);
	}

	/**
	 * Constructor
	 * 
	 * @param icon
	 * @param description
	 */
	public CssButton(String caption, String description) {

		initComponent(caption, description, color, null, null, false);
	}

	/**
	 * Constructor
	 * 
	 * @param day
	 */
	public CssButton(Icon icon, ICssButtonCss css) {

		initComponent(null, null, color, icon, css, false);
	}

	/**
	 * Constructor
	 * 
	 * @param caption
	 */
	public CssButton(String caption, String description, Colors color) {

		initComponent(caption, description, color, null, null, false);
	}

	/**
	 * Constructor
	 * 
	 * @param caption
	 * @param description
	 * @param color
	 * @param css
	 */
	public CssButton(String caption, String description, Colors color, ICssButtonCss css) {

		initComponent(caption, description, color, null, css, false);
	}

	/**
	 * Constructor
	 * 
	 * @param caption
	 * @param description
	 * @param color
	 * @param icon
	 */
	public CssButton(String caption, String description, Colors color, Icon icon) {

		initComponent(caption, description, color, icon, null, false);
	}

	/**
	 * Constructor
	 * 
	 * @param caption
	 * @param description
	 * @param color
	 * @param icon
	 * @param resourceIcon
	 */
	public CssButton(String caption, String description, Colors color, Icon icon, Resource resourceIcon) {

		initComponent(caption, description, color, icon, null, false, false, resourceIcon);
	}

	/**
	 * Constructor
	 * 
	 * @param caption
	 * @param description
	 * @param color
	 * @param icon
	 */
	public CssButton(String caption, String description, Colors color, Resource icon) {

		initComponent(caption, description, color, null, null, false, false, icon);
	}

	/**
	 * Constructor
	 * 
	 * @param caption
	 * @param description
	 * @param color
	 * @param icon
	 * @param colorLabel
	 */
	public CssButton(String caption, String description, Colors color, Icon icon, boolean colorLabel) {

		initComponent(caption, description, color, icon, null, false, colorLabel, null);
	}

	/**
	 * Constructor
	 * 
	 * @param caption
	 * @param description
	 * @param color
	 * @param icon
	 * @param colorLabel
	 * @param shadow
	 */
	public CssButton(String caption, String description, Colors color, Icon icon, boolean colorLabel, boolean shadow) {

		initComponent(caption, description, color, icon, null, shadow, colorLabel, null);
	}

	/**
	 * Constructor
	 * 
	 * @param caption
	 * @param description
	 * @param color
	 * @param icon
	 * @param css
	 */
	public CssButton(String caption, String description, Colors color, Icon icon, ICssButtonCss css) {

		initComponent(caption, description, color, icon, css, false);
	}

	/**
	 * Constructor
	 * 
	 * @param caption
	 * @param description
	 * @param shadow
	 */
	public CssButton(String caption, String description, boolean shadow) {

		initComponent(caption, description, null, null, null, true);
	}

	/**
	 * Inicializar componente
	 */
	public void initComponent(String caption, String description, Colors color, Icon icon, ICssButtonCss cssbutton, boolean shadow) {

		initComponent(caption, description, color, icon, cssbutton, shadow, false, null);
	}

	/**
	 * Crear buton en base a la configuración definida en los parámetros de este
	 * método.
	 * 
	 * @param caption
	 *            Label
	 * @param description
	 * @param color
	 *            Color {@link Colors}
	 * @param icon
	 *            Referencia al icono con enumerados {@link ResourceIconsVaadin}
	 * @param cssbutton
	 *            Css que queremos aplicar al definido por defecto (defaultCss).
	 * @param shadow
	 *            Generar efecto sombreado y redondeado al contorno de botón.
	 * @param colorLabel
	 *            Permite definir si aplicamos el color definido a la parte del
	 *            label cuando se ha definido un icono. Por defecto, si hay
	 *            icono y label, el icono toma el color y el label se deja sin
	 *            aplicar color.
	 * @param resourceIcon
	 * @param fontawesome
	 */
	protected void initComponent(String caption, String description, Colors color, Icon icon, ICssButtonCss cssbutton, boolean shadow, boolean colorLabel, Resource resourceIcon) {

		if (loaded) {
			return;
		}

		loaded = true;

		ICssButtonCss css = getCss();

		if (description != null) {
			setDescription(description);
		}

		if (icon != null) {
			try {
				iconComponent = getIconComponent(color, icon);
				addComponent(iconComponent);

			}
			catch (Exception e1) {
				logger.error("ERROR LOAD ICON '" + icon + "'", e1);
			}
		}
		else if (resourceIcon != null) {
			try {
				iconComponent = getIconComponent(color, resourceIcon);
				addComponent(iconComponent);
				addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
			}
			catch (Exception e1) {
				logger.error("ERROR LOAD ICON '" + icon + "'", e1);
			}
		}

		// Label
		if (caption != null) {
			label = getLabelComponent(caption, icon);
			addComponent(label);
		}

		if (css.getLayout() != null) {
			addStyleName(css.getLayout());
		}

		if (shadow) {
			addStyleName(CssVaadin.getShadowRounded());
		}

		if (color != null && (icon == null || colorLabel)) {
			setColor(this, color);
		}

	}

	/**
	 * Label component
	 * 
	 * @param caption
	 * @param icon
	 * @return
	 */
	protected Label getLabelComponent(String caption, Icon icon) {

		Label label = new Label(caption, ContentMode.HTML);

		if (icon != null) {
			label.setSizeUndefined();
		}

		if (small) {
			label.addStyleName(css.getSmall());
		}
		else if (getCss().getIconText() != null || getCss().getText() != null) {
			label.addStyleName(icon != null ? getCss().getIconText() : getCss().getText());
		}

		return label;
	}

	/**
	 * Icono del componente
	 * 
	 * @param color
	 * @param icon
	 * @return
	 * @throws LocalizedException
	 */
	protected Component getIconComponent(Colors color, Icon icon) throws LocalizedException {

		Component iconComponent = UIContextVaadin.getComponent(icon);

		if (iconComponent != null && getCss().getIcon() != null) {

			iconComponent.addStyleName(small ? getCss().getIconSmall() : getCss().getIcon());

			if (color != null) {
				setColor(iconComponent, color);
			}
		}

		return iconComponent;
	}

	/**
	 * Icono del componente
	 * 
	 * @param color
	 * @param icon
	 * @return
	 * @throws LocalizedException
	 */
	protected Component getIconComponent(Colors color, Resource icon) throws LocalizedException {

		Component iconComponent = new Embedded(null, icon);

		if (getCss().getIcon() != null) {

			iconComponent.addStyleName(small ? getCss().getIconSmall() : getCss().getIcon());

			if (color != null) {
				setColor(iconComponent, color);
			}
		}

		return iconComponent;
	}

	/**
	 * Establecer el color
	 * 
	 * @param color
	 */
	public void setColor(Component component, Colors color) {

		if (this.color != null) {
			component.removeStyleName(getCss().getColor(this.color.toString().toLowerCase()));
		}

		component.addStyleName(getCss().getColor(color.toString().toLowerCase()));

		this.color = color;
	}

	/**
	 * Eliminar color
	 */
	public void removeColor() {

		if (color != null) {
			removeStyleName(getCss().getColor(color.toString().toLowerCase()));
		}

		color = null;
	}

	public Label getLabel() {
		return label;
	}

	/**
	 * Obtiene las clases css utilizadas para este componente.
	 * 
	 * @return
	 */
	public ICssButtonCss getCss() {

		if (css == null) {
			css = new CssButtonCss();
		}

		return css;
	}

	/**
	 * Aplicar estilo de selección de item.
	 */
	public void addCssSelected() {

		addStyleName(getCss().getSelected(color != null ? color.name().toLowerCase() : null));
	}

	/**
	 * Eliminar estilo de selección de item.
	 */
	public void removeCssSelected() {

		removeStyleName(getCss().getSelected(color != null ? color.name().toLowerCase() : null));
	}

	/**
	 * Aplicar estilo de selección de item.
	 */
	public void addCssSelectedBackground() {

		addStyleName(getCss().getSelectedBackground());
	}

	/**
	 * Eliminar estilo de selección de item.
	 */
	public void removeCssSelectedBackground() {

		removeStyleName(getCss().getSelectedBackground());
	}

	/**
	 * Aplicar sombre al estilo del botón.
	 */
	public void addCssShadow() {

		addStyleName(getCss().getShadow());
	}

	/**
	 * Aplicar sombre al estilo del botón.
	 */
	public void addCssDisabled() {

		addStyleName(getCss().getDisabled());
	}

	/**
	 * Aplicar estilo IC DRAWER Android ---.
	 */
	public void addCssIcDrawer() {

		addStyleName(getCss().getIcDrawer());
	}

	/**
	 * Aplicar border al estilo actual.
	 */
	public void addCssBorder() {

		addStyleName(getCss().getBorder());
	}

	/**
	 * Quitar border al estilo actual.
	 */
	public void removeCssBorder() {

		removeStyleName(getCss().getBorder());
	}

	/**
	 * Establecer el icono
	 * 
	 * @param icon
	 * @throws LocalizedException
	 */
	public void setIcon(Icon icon) {

		try {
			if (iconComponent != null) {
				removeComponent(iconComponent);
			}
			iconComponent = getIconComponent(color, icon);
			addComponent(iconComponent, 0);

		}
		catch (LocalizedException e) {
			logger.error("ERROR CHANGE ICON LOGIN.", e);
		}
	}

	/**
	 * Componente icon
	 * 
	 * @return
	 */
	public Component getIconComponent() {
		return iconComponent;
	}

	/**
	 * Aplicar estilo de padding reducido 2px en lugar de los 10 por defecto.
	 * 
	 * @param small
	 */
	public void setSmall(boolean small) {

		this.small = small;

		if (label != null && css != null) {
			label.addStyleName(css.getSmall());
		}
	}

}

package com.javalego.ui.vaadin.component.tiles;

import com.javalego.model.keys.Icon;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;

/**
 * Layout para incluir los tiles = baldosas.
 * 
 * Ejemplo:
 * 
 * CssLayout layout = new TilesLayout(); setContent(layout);
 * 
 * for (int i = 0; i < 10; i++) layout.addComponent(new Tile("Hola<br>
 * hola"));
 * 
 * 
 * @author ROBERTO RANZ
 * 
 */
public class TilesLayout extends CssLayout {

	private static final long serialVersionUID = 5630955459228760063L;

	/**
	 * Ancho máximo por item
	 */
	private String maxWidth;

	private boolean shadow = true;

	/**
	 * Css personalizado para el layout
	 */
	private String css;

	/**
	 * Constructo
	 * 
	 * @param maxWidth
	 *            Ancho máximo por componente
	 */
	public TilesLayout(String maxWidth) {
		this.maxWidth = maxWidth;
	}

	public TilesLayout() {
	}

	/**
	 * Constructor
	 * 
	 * @param shadow
	 *            Incluir style shadow a cada tile.
	 */
	public TilesLayout(boolean shadow) {
		this.shadow = shadow;
	}

	@Override
	protected String getCss(Component c) {
		if (css != null) {
			return css;
		}
		else {
			return "margin-bottom: 10px; vertical-align: top; " + (maxWidth != null ? "max-width: " + maxWidth + ";" : "") + " display: inline-block;";
			//return "margin-bottom: 10px; vertical-align: top; display: inline-block;";
		}
	}

	/**
	 * Añadir Tile al layout
	 * 
	 * @param text
	 * @param icon
	 * @param width
	 *            ancho del componente en formato html Ej.: 100px 100%
	 */
	public Tile addTile(String text, Icon icon) {

		Tile tile = new Tile(icon, text, shadow);

		addComponent(tile);

		return tile;
	}

	/**
	 * Añadir Tile al layout
	 * 
	 * @param component
	 *            Personalización del componente a incluir en el tile
	 * @param width
	 *            ancho del componente en formato html Ej.: 100px 100%
	 */
	public Tile addTile(Component component) {

		Tile tile = new Tile(component, shadow);

		addComponent(tile);

		return tile;
	}

	/**
	 * Tamaño máximo de ancho por componente
	 * 
	 * @return
	 */
	public String getMaxWidth() {
		return maxWidth;
	}

	/**
	 * Tamaño máximo de ancho por componente
	 * 
	 * @param maxWidth
	 */
	public void setMaxWidth(String maxWidth) {
		if (maxWidth != null) {
			this.maxWidth = maxWidth;
		}
	}

	/**
	 * Mostrar sombra en cada componente
	 * 
	 * @return
	 */
	public boolean isShadow() {
		return shadow;
	}

	/**
	 * Mostrar sombra en cada componente
	 * 
	 * @param shadow
	 */
	public void setShadow(boolean shadow) {
		this.shadow = shadow;
	}

	public String getCss() {
		return css;
	}

	public void setCss(String css) {
		this.css = css;
	}

}

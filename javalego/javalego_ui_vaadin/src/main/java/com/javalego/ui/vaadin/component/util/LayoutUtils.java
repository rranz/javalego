package com.javalego.ui.vaadin.component.util;

import com.javalego.ui.vaadin.css.CssVaadin;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;

/**
 * Utilidades de generación de layouts
 * @author ROBERTO RANZ
 *
 */
public final class LayoutUtils {

	private LayoutUtils() {}
	
	/**
	 * Obtener un CssLayout
	 * @param horizontal
	 * @param margin
	 * @param padding
	 * @param shadow
	 * @return
	 */
	public static CssLayout getCssLayout(final boolean horizontal, final int margin, final int padding, boolean shadow) {
		
		CssLayout layout = new CssLayout() {
			private static final long serialVersionUID = 707339034247050199L;

			@Override
			protected String getCss(Component c) {
				
				String css = "";
				
				if (margin > 0) {
					css += "margin: " + margin + "px;";
				}
				if (margin > 0) {
					css += " padding: " + padding + "px;";
				}
				css += horizontal ? " display: inline-block;" : " display: block;";
				
				if ("".equals(css)) {
					return super.getCss(c);
				}
				else {
					return css;
				}
				
			}
		};		
		
		if (shadow) {
			layout.addStyleName(CssVaadin.getShadowRounded());
		}
		return layout;
	}

	/**
	 * Obtener un CssLayout
	 * @param horizontal
	 * @param css Incluir un css personalizado.
	 * @param shadow
	 * @return
	 */
	public static CssLayout getCssLayout(final boolean horizontal, final String css, boolean shadow) {
		
		CssLayout layout = new CssLayout() {

			private static final long serialVersionUID = -477971310940633838L;

			@Override
			protected String getCss(Component c) {
				
				String _css = css != null ? css : "";
				
				_css += horizontal ? " display: inline-block;" : " display: block;";
				
				if ("".equals(_css)) {
					return super.getCss(c);
				}
				else {
					return _css;
				}
				
			}
		};		
		
		if (shadow) {
			layout.addStyleName(CssVaadin.getShadow());
		}
		return layout;
	}
	
	
}

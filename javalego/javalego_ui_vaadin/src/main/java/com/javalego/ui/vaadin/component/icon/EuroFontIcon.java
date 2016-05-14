package com.javalego.ui.vaadin.component.icon;

import com.vaadin.server.FontIcon;

/**
 * Font icon definition with a single symbol
 * 
 * @author ROBERTO RANZ
 *
 */
public enum EuroFontIcon implements FontIcon {
	
	EURO(0x20AC);

	private int codepoint;

	EuroFontIcon(int codepoint) {
		this.codepoint = codepoint;
	}

	@Override
	public String getMIMEType() {
		throw new UnsupportedOperationException(FontIcon.class.getSimpleName() + " should not be used where a MIME type is needed.");
	}

	@Override
	public String getFontFamily() {
		return "sans-serif";
	}

	@Override
	public int getCodepoint() {
		return codepoint;
	}

	@Override
	public String getHtml() {
		return "<span class=\"v-icon\" style=\"font-family: " + getFontFamily() + ";\">&#x" + Integer.toHexString(codepoint) + ";</span>";
	}
}

package com.javalego.icons.types;


/**
 * Imagen existente en el theme de la aplicación (actualmente utilizado para framework Vaadin).
 * 
 * @author ROBERTO RANZ
 * 
 */
public class ThemeIcon extends BaseIconItem {

	/**
	 * Uri
	 */
	private String uri;

	/**
	 * Constructor
	 * 
	 * @param url
	 */
	public ThemeIcon(String uri) {
		this.uri = uri;
	}

	@Override
	public byte[] getBytes() throws Exception {
		return data;
	}

	/**
	 * Uri
	 * 
	 * @return
	 */
	public String getUri() {
		return uri;
	}

}

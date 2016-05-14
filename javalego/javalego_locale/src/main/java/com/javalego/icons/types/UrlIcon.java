package com.javalego.icons.types;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.net.URL;

import javax.imageio.ImageIO;

/**
 * Imagen obtenida de una URL
 * 
 * @author ROBERTO RANZ
 * 
 */
public class UrlIcon extends BaseIconItem {

	/**
	 * Url
	 */
	private String url;

	/**
	 * Constructor
	 * 
	 * @param url
	 */
	public UrlIcon(String url) {
		this.url = url;
	}

	@Override
	public byte[] getBytes() throws Exception {

		if (data != null) {
			return data;
		}

		URL _url = new URL(url);

		BufferedImage image = ImageIO.read(_url);

		ByteArrayOutputStream os = new ByteArrayOutputStream();

		ImageIO.write(image, "gif", os);

		data = os.toByteArray();

		return data;
	}

	/**
	 * Url
	 * 
	 * @return
	 */
	public String getUrl() {
		return url;
	}

}

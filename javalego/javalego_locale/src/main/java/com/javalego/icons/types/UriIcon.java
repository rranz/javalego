package com.javalego.icons.types;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URI;

import javax.imageio.ImageIO;

/**
 * Imagen obtenida de una URI
 * 
 * @author ROBERTO RANZ
 * 
 */
public class UriIcon extends BaseIconItem {

	/**
	 * Uri
	 */
	private String uri;

	/**
	 * Constructor
	 * 
	 * @param url
	 */
	public UriIcon(String uri) {
		this.uri = uri;
	}

	@Override
	public byte[] getBytes() throws Exception {

		if (data != null) {
			return data;
		}

		URI uri = new URI(this.uri);
		File fnew = new File(uri);
		BufferedImage originalImage = ImageIO.read(fnew);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(originalImage, "jpg", baos);

		data = baos.toByteArray();

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

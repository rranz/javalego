package com.javalego.icons.types;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;

import javax.imageio.ImageIO;

/**
 * Imagen obtenida de un fichero
 * 
 * @author ROBERTO RANZ
 * 
 */
public class FileIcon extends BaseIconItem {

	/**
	 * File
	 */
	private File file;

	/**
	 * Constructor
	 * 
	 * @param url
	 */
	public FileIcon(File file) {
		this.file = file;
	}

	@Override
	public byte[] getBytes() throws Exception {

		if (data != null) {
			return data;
		}

		BufferedImage image = ImageIO.read(file);

		ByteArrayOutputStream os = new ByteArrayOutputStream();

		ImageIO.write(image, "gif", os);

		data = os.toByteArray();

		return data;
	}

	public File getFile() {
		return file;
	}

}

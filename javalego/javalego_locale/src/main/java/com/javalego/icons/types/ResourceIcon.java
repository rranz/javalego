package com.javalego.icons.types;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import com.javalego.util.SystemUtils;

/**
 * Imagen obtenida de un recurso de la aplicación.
 * 
 * @author ROBERTO RANZ
 * 
 */
public class ResourceIcon extends BaseIconItem {

	/**
	 * path del Recurso
	 */
	private String resource;

	/**
	 * Constructor
	 * 
	 * @param url
	 */
	public ResourceIcon(String resource) {
		this.resource = resource;
	}

	@Override
	public synchronized byte[] getBytes() throws Exception {

		if (data != null) {
			return data;
		}

		InputStream in = SystemUtils.getResource(resource);

		if (in == null) {
			throw new Exception("RESOURCE '" + resource + "' NOT FOUND.");
		}

		ByteArrayOutputStream out = new ByteArrayOutputStream();

	// you can configure the buffer size
		byte[] buffer = new byte[1024]; 
		while (in.read(buffer) != -1) {
			out.write(buffer); 
		}

		data = out.toByteArray();

		return data;
	}

	/**
	 * Recurso
	 * 
	 * @return
	 */
	public String getResource() {
		return resource;
	}

}

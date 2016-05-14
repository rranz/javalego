package com.javalego.ui.vaadin.component;

import java.util.Date;

import com.javalego.exception.LocalizedException;
import com.javalego.ui.vaadin.component.image.ImageField;

/**
 * Método relativos a la gestión de archivo de subida. (BlobProperty imágenes y
 * documentos).
 * 
 * @see ImageField
 * @see DocumentField
 * @author ROBERTO RANZ
 */
public interface UploadEvents {

	public void succeeded(byte[] data, String fileName) throws LocalizedException;

	public byte[] getStream() throws Exception;

	public String getFileName();

	public Date getDate();
}

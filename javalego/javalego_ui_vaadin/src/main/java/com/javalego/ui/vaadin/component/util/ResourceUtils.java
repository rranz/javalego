package com.javalego.ui.vaadin.component.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.javalego.util.FileUtils;
import com.javalego.util.SystemUtils;
import com.vaadin.server.Page;
import com.vaadin.server.StreamResource;

/**
 * Clase de utilidades relacionadas con los recursos del sistema.
 * 
 * @author ROBERTO RANZ
 * 
 */
public class ResourceUtils {

	public static final String OPEN_WINDOW = "_blank"; // _new _blank _self

	private ResourceUtils() {
	}

	/**
	 * Mostrar un fichero en una nueva ventana.
	 * 
	 * @param fileName
	 */
	public static void showFileName(String fileName) throws Exception {

		showStreamResource(FileUtils.getBytesFromFile(new File(fileName)), FileUtils.getFileExtension(fileName));
	}

	/**
	 * Mostrar un fichero Excel en una nueva ventana.
	 * 
	 * @param fileName
	 */
	public static void showExcelFileName(String fileName, boolean xlsx) throws Exception {

		showExcelResource(FileUtils.getBytesFromFile(new File(fileName)), xlsx);
	}

	/**
	 * Mostrar un fichero Excel en formato zip en una nueva ventana.
	 * 
	 * @param fileName
	 */
	public static void showZipExcelFileName(String fileName) throws Exception {

		showStreamResource(getZipFile(fileName), ".zip");
	}

	/**
	 * Obtener un Stream de un array de bytes.
	 * 
	 * @param data
	 * @return
	 */
	public static StreamResource getFileResource(String fileName) throws Exception {

		return getStreamResource(FileUtils.getBytesFromFile(new File(fileName)), FileUtils.getFileExtension(fileName));
	}

	/**
	 * Obtener un Stream de un array de bytes incluyendo la extesión .png porque los datos corresponden a un fichero .png.
	 * 
	 * @param data
	 * @return
	 */
	public static StreamResource getStreamResourcePng(final byte[] data) {
		return getStreamResource(data, ".png");
	}

	/**
	 * Obtener un Stream de un array de bytes.
	 * 
	 * @param data
	 * @param fileName
	 * @return
	 */
	private static StreamResource getStreamResourceFromFile(final byte[] data, String fileName) {

		return getStreamResource(data, fileName);
	}

	/**
	 * Mostrar un recurso en una ventana nueva.
	 * 
	 * @param resourceName
	 * @throws Exception
	 */
	public static void showStreamResource(final String resourceName) throws Exception {

		showStreamResource(SystemUtils.getBytesFromResource(resourceName), FileUtils.getFileExtension(resourceName));
	}

	/**
	 * Obtener un Stream Resource desde un nombre de recurso
	 * 
	 * NOTA: no funciona correctamente para obtener el resource de un icono a partir de la definición de ICollectionImages ya que tiene un ruta diferente adaptada a Vaadin.
	 * 
	 * @param resourceName
	 * @return
	 */
	public static StreamResource getStreamResource(final String resourceName) {

		final StreamResource stream = new StreamResource(new StreamResource.StreamSource() {
			private static final long serialVersionUID = 9201116316098015461L;

			@Override
			public InputStream getStream() {
				try {
					return SystemUtils.getResource(resourceName);
				}
				catch (Exception e) {
					e.printStackTrace();
					return null;
				}
			}
		}, resourceName);

		return stream;
	}

	/**
	 * Mostrar un recurso recuperado como array de bytes.
	 * 
	 * @param streamFile
	 * @param extension
	 */
	@SuppressWarnings("deprecation")
	public static void showStreamResource(byte[] streamFile, String extension) {

		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String filename = "file-tmp-" + df.format(new Date()) + extension;

		Page.getCurrent().open(getStreamResourceFromFile(streamFile, filename), OPEN_WINDOW, false);
	}

	/**
	 * Abrir una nueva ventana apuntando a una dirección web pasada como parámetro.
	 * 
	 * @param uri
	 */
	public static void openUrl(String uri) {
		
		Page.getCurrent().open(uri, OPEN_WINDOW, false);
		//Page.getCurrent().setLocation(uri); // OPEN_WINDOW);
	}

	/**
	 * Mostrar un recurso recuperado como array de bytes.
	 * 
	 * @param streamFile
	 * @param application
	 */
	@SuppressWarnings("deprecation")
	public static void showExcelResource(byte[] streamFile, boolean xlsx) {

		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");

		String extension = xlsx ? "xlsx" : "xls";

		String filename = "file-tmp-" + df.format(new Date()) + "." + extension;

		StreamResource resource = getStreamResource(streamFile, filename);
		resource.getStream().setParameter("Content-Disposition", "attachment;filename=\"" + filename + "\"");
		resource.setMIMEType("application/" + extension);
		// Cache que espera antes de la obtención del archivo para cancelar la carga desde el servidor.
		// resource.setCacheTime(20000);

		Page.getCurrent().open(resource, OPEN_WINDOW, false);
	}

	/**
	 * Obtener un Stream de un array de bytes.
	 * 
	 * @param data
	 * @param extension Nombre de la extensión del archivo sin incluir el .
	 * @return
	 */
	public static StreamResource getStreamResource(final byte[] data, String extension) {

		// Generate a filename with a timestamp.
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String filename = "file-tmp-" + df.format(new Date()) + "." + extension;

		final StreamResource stream = new StreamResource(new StreamResource.StreamSource() {
			private static final long serialVersionUID = 2791484461477227893L;

			@Override
			public InputStream getStream() {
				try {
					if (data != null)
						return new ByteArrayInputStream(data);
					else
						return null;
				}
				catch (Exception e) {
					e.printStackTrace();
					return null;
				}
			}
		}, filename);

		return stream;
	}

	/**
	 * Obtener un array de bytes en formato zip. Ej.: ResourceUtils.showStreamResource(ResourceUtils.getZipResourceString("datos.xls"), ".zip");
	 * 
	 * @param resource
	 * @return
	 */
	public static byte[] getZipResourceString(String resource) throws Exception {

		ZipOutputStream zos = null;
		InputStream is = null;
		ByteArrayOutputStream ba = null;

		try {
			ba = new ByteArrayOutputStream();

			zos = new ZipOutputStream(ba);

			// Create input stream to read file from resource folder.
			Class<ResourceUtils> clazz = ResourceUtils.class;

			is = clazz.getResourceAsStream((resource.indexOf("/") != 0 ? "/" : "") + resource);

			// Put a new ZipEntry in the ZipOutputStream
			zos.putNextEntry(new ZipEntry(resource));
			int size;
			byte[] buffer = new byte[1024];

			// Read data to the end of the source file and write it
			// to the zip output stream. //
			while ((size = is.read(buffer, 0, buffer.length)) > 0) {
				zos.write(buffer, 0, size);
			}
		}
		finally {
			if (zos != null)
				zos.closeEntry();

			if (is != null)
				is.close();

			if (zos != null)
				zos.close();
		}

		return ba != null ? ba.toByteArray() : null;
	}

	/**
	 * Obtener un array de bytes en formato zip. Ej.: ResourceUtils.showStreamResource(ResourceUtils.getZipResourceString("datos.xls"), ".zip");
	 * 
	 * @param resource
	 * @return
	 */
	public static byte[] getZipFile(String filename) throws Exception {

		ZipOutputStream zos = null;
		InputStream is = null;
		ByteArrayOutputStream ba = null;

		try {
			ba = new ByteArrayOutputStream();

			zos = new ZipOutputStream(ba);

			is = new FileInputStream(filename);

			// Put a new ZipEntry in the ZipOutputStream
			zos.putNextEntry(new ZipEntry(new File(filename).getName()));
			int size;
			byte[] buffer = new byte[1024];

			// Read data to the end of the source file and write it
			// to the zip output stream. //
			while ((size = is.read(buffer, 0, buffer.length)) > 0) {
				zos.write(buffer, 0, size);
			}
		}
		finally {
			if (zos != null)
				zos.closeEntry();

			if (is != null)
				is.close();

			if (zos != null)
				zos.close();
		}

		return ba != null ? ba.toByteArray() : null;
	}

}

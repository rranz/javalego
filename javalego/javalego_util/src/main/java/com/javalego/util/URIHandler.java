package com.javalego.util;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.io.IOUtils;

import com.javalego.exception.JavaLegoException;

/**
 * Clase de utilidad para trabajar con diferentes tipos de par&aacute;metros por
 * referencia usando URI.
 * 
 */
public abstract class URIHandler {
	
	protected static final int RESOURCE_LENGTH_UNKNOWN = -1;
	
	protected static final int UNDEFINED_LIMIT = -1;
	
	protected static final int NO_LIMIT = 0; // Un valor 0 indica que no tiene límite.
	
	protected static final String NULL_URI = "(null URI)";
	
	protected static final String PFE_CONFIGURATION_KEY_BYTE_ARRAY_SIZE_LIMIT = "byte.array.size.limit";

	private URI uri;
	
	private int resourceLimit = NO_LIMIT;

	/**
	 * Constructor por defecto. Construye un objeto para ayudar a manejar una
	 * URI. La URI es inv&aacute;lida.
	 */
	protected URIHandler() {
		super();
		this.uri = null;
	}

	/**
	 * Construye un objeto para ayudar a manejar una URI.
	 * 
	 * @param uri
	 *            Referencia al recurso externo.
	 */
	public URIHandler(URI uri) {
		super();
		this.uri = uri;
	}

	/**
	 * Construye un objeto para ayudar a manejar una URI.
	 * 
	 * @param uri
	 *            Referencia al recurso externo.
	 * @param limit
	 *            L&iacute;mite del recurso externo en bytes
	 */
	public URIHandler(URI uri, int limit) {
		super();
		this.uri = uri;
		this.resourceLimit = (limit > 0) ? limit : 0;
	}

	/**
	 * Actualiza la URI que maneja el objeto.
	 * 
	 * @param uri
	 *            Nueva referencia a un recurso externo.
	 */
	protected void setURI(URI uri) {
		this.uri = uri;
	}

	/**
	 * Devuelve la URI que maneja le objeto.
	 */
	public URI getURI() {
		return uri;
	}

	public boolean isNull() {
		return this.uri == null;
	}

	/**
	 * Devuelve la URI que maneja como una cadena de texto.
	 */
	@Override
	public String toString() {
		return (this.isNull()) ? NULL_URI : this.uri.toString();
	}

	/**
	 * Devuelve si es recurso referenciado por la URI existe.
	 */
	public boolean exists() {
		boolean found = false;
		InputStream is = null;

		try {
			if (!this.isNull()) {
				URL url = this.getURI().toURL();
				URLConnection uc = url.openConnection();
				if (uc != null) {
					is = uc.getInputStream();
					if (is != null) {
						found = true;
					}
				}
			}
		}
		catch (MalformedURLException e) {
			found = false;
		}
		catch (IOException e) {
			found = false;
		}
		finally {
			IOUtils.closeQuietly(is);
		}

		return found;
	}

	/**
	 * Obtiene el nombre completo del recurso de una URI.
	 */
	public String getFullResourcePath() {
		if (this.isNull())
			return NULL_URI;

		String fullPath = "";
		String uriStr = this.getURI().toString();
		int pos = uriStr.lastIndexOf("/");
		if (pos > -1) {
			fullPath = uriStr.substring(0, pos);
		}
		return fullPath;
	}

	/**
	 * Obtiene el nombre del recurso de una URI.
	 */
	public String getResourcePath() {
		if (this.isNull())
			return NULL_URI;

		return this.getURI().getPath();
	}

	/**
	 * Obtiene el nombre del recurso de una URI.
	 */
	public String getResourceName() {
		if (this.isNull())
			return NULL_URI;

		String name = "";
		String uriStr = this.getURI().toString();
		int pos = uriStr.lastIndexOf("/");
		if (pos > -1) {
			name = uriStr.substring(pos + 1);
		}
		return name;
	}

	/**
	 * Obtiene la extensi&oacute;n de un recurso de una URI.
	 */
	public String getResourceExtension() {
		if (this.isNull())
			return "";

		String ext = "";
		String uriStr = this.getURI().toString();
		int pos = uriStr.lastIndexOf(".");
		if (pos > -1) {
			ext = uriStr.substring(pos + 1);
		}
		return ext;
	}

	/**
	 * Obtiene el nombre sin extensi&oacute;n de un recurso de una URI.
	 * 
	 * @param uri
	 *            Referencia al recurso en formato URI.
	 */
	public String getResourceNameWithoutExtension() {
		if (this.isNull())
			return "";

		String base = "";
		String name = this.getResourceName();
		int pos = name.lastIndexOf(".");
		if (pos > -1) {
			base = name.substring(0, pos);
		}
		return base;
	}

	public int getResourceLimit() {
		return this.resourceLimit;
	}

	/**
	 * Cambia el l&iacute;mite del recurso
	 * 
	 * @param limit
	 *            Nuevo valor del l&iacute;mite.
	 */
	protected void setResourceLimit(int limit) {
		this.resourceLimit = limit;
	}

	/**
	 * Comprueba si aplica l&iacute;mites de tama�o de un recurso.
	 * 
	 * @return Devuelve <code>true</code> si el recurso est&aacute; dentro de
	 *         los l&iacute;mites y <code>false</code> en otro caso.
	 */
	protected boolean resourceIsWithinLimits(int size) {
		if (this.getResourceLimit() == 0)
			return true; // El recurso no tiene l�mites.

		return RESOURCE_LENGTH_UNKNOWN < size && size <= this.getResourceLimit();
	}

	/**
	 * Obtiene la lista de URI de los recursos que contiene la URI. Si no hay
	 * recursos devuelve un array vac&iacute;o.
	 * 
	 * @return URI[] array de {@link URI}s que corresponden a los recursos
	 *         obtenidos en este {@link URIHandler}
	 * 
	 * @throws JavaLegoException
	 *             Si no es posible acceder a la URI.
	 */
	public abstract URI[] getURIResources() throws JavaLegoException;

	/**
	 * Obtiene un array de bytes a partir de una referencia a un recurso externo
	 * mediante URI.
	 * 
	 * @param uri
	 *            Referencia al recurso en formato URI.
	 * @return Array de bytes con el recurso.
	 * @throws JavaLegoException
	 *             Si no es posible acceder al recurso referenciado.
	 */
	public byte[] getBytes() throws JavaLegoException {
		InputStream is = null;

		if (getURI() == null) {
			throw new JavaLegoException("ERROR_INVALID_URI");
		}

		try {
			URL url = this.getURI().toURL();
			URLConnection uc = url.openConnection();
			int cl = uc.getContentLength();

			if (!this.resourceIsWithinLimits(cl)) {
				// TODO: parametrizar mensaje de excepci�n con tama�o y umbral.
				// throw new JavaLegoException
				// (ERROR_RESOURCE_OUT_OF_LIMITS, cl,
				// this.getResourceLimit());
				throw new JavaLegoException("ERROR_RESOURCE_OUT_OF_LIMITS");
			}

			is = uc.getInputStream();
			byte[] bytes = IOUtils.toByteArray(is);

			if (!this.resourceIsWithinLimits(bytes.length)) {
				throw new JavaLegoException("ERROR_RESOURCE_OUT_OF_LIMITS");
			}
			return bytes;
		}
		catch (IOException e) {
			this.closeStreamsHandler(is, null, null, e);
		}
		catch (IllegalArgumentException e) {
			throw new JavaLegoException("ERROR_INVALID_URI");
		}
		finally {
			IOUtils.closeQuietly(is);
		}
		return new byte[0];
	}

	/**
	 * Escribe un recurso en un sitio externo referenciado por una URI.
	 * 
	 * @param bytes
	 *            Datos del recurso en bytes.
	 * @param destinationURI
	 *            URI de destino donde se dejar� el recurso.
	 * 
	 * @throws JavaLegoException
	 *             Si no se puede acceder a la URI.
	 */
	public void writeBytes(byte[] bytes) throws JavaLegoException {
		if (!resourceIsWithinLimits(bytes.length)) {
			throw new JavaLegoException("ERROR_RESOURCE_OUT_OF_LIMITS");
		}

		FileOutputStream fos = null;
		BufferedOutputStream bos = null;

		try {
			fos = new FileOutputStream(this.getURI().getPath());
			bos = new BufferedOutputStream(fos);
			bos.write(bytes);
			bos.flush();
		}
		catch (IOException e) {
			this.closeStreamsHandler(null, fos, bos, e);
		}
		finally {
			IOUtils.closeQuietly(fos);
			IOUtils.closeQuietly(bos);
		}
	}

	/**
	 * Escribe un recurso en un sitio externo referenciado por una URI.
	 * 
	 * @param byteStream
	 *            Datos del recurso en bytes.
	 * @param destinationURI
	 *            URI de destino donde se dejar� el recurso.
	 * 
	 * @throws JavaLegoException
	 *             Si no se puede acceder a la URI.
	 */
	public void writeBytes(ByteArrayInputStream byteStream) throws JavaLegoException {
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;

		try {
			fos = new FileOutputStream(this.getURI().getPath());
			bos = new BufferedOutputStream(fos);
			int b = 0;

			while ((b = byteStream.read()) > -1) {
				bos.write((byte) b);
			}
			bos.flush();
		}
		catch (IOException e) {
			this.closeStreamsHandler(null, fos, bos, e);
		}
		finally {
			IOUtils.closeQuietly(fos);
			IOUtils.closeQuietly(bos);
		}
	}

	/**
	 * Manejador de excepci&oacute;n para liberar recursos uno a uno. Los
	 * par&aacute;metros a <code>null</code> se consideran opcionales. Se
	 * intentan liberar todos los recursos por separado. La excepci&oacute;n
	 * mostrada corresponde a la pasada por par&aacute;metro, y puede enmascarar
	 * que otros recursos no pudieron ser liberados finalmente, si bien en ese
	 * momento poco se puede hacer ya.
	 * 
	 * @param is
	 *            Stream de entrada.
	 * @param fos
	 *            Stream de fichero.
	 * @param bos
	 *            Stream de datos.
	 * @param error
	 *            Error indicado por la excepci&oacute;n.
	 * @param ex
	 *            Excepci&oacute;n producida para llamar a este manejador.
	 * 
	 * @throws JavaLegoException
	 *             Si no se puede acceder a la URI.
	 */
	protected void closeStreamsHandler(InputStream is, FileOutputStream fos, BufferedOutputStream bos, Exception ex) throws JavaLegoException {
		Exception exFound = null;

		try {
			if (is != null)
				is.close();
		}
		catch (IOException e) {
			exFound = new JavaLegoException("UNABLE_TO_DISPOSE_RESOURCES", e);
		}

		try {
			if (bos != null)
				bos.close();
		}
		catch (IOException e) {
			exFound = new JavaLegoException("UNABLE_TO_DISPOSE_RESOURCES", e);
		}

		try {
			if (fos != null)
				fos.close();
		}
		catch (IOException e) {
			exFound = new JavaLegoException("UNABLE_TO_DISPOSE_RESOURCES", e);
		}

		// Favorecer devolver la excepción original en vez de las de los
		// recursos a eliminar, en caso de error, dado que
		// la primera es más significativa para identificar el error producido.

		if (ex != null || exFound != null)
			throw new JavaLegoException((ex != null) ? ex.getLocalizedMessage() : exFound.getLocalizedMessage());
	}

}
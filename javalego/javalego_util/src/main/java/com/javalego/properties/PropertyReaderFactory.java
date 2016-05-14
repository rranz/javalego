package com.javalego.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.javalego.exception.JavaLegoException;
import com.javalego.util.SystemUtils;

/**
 * Devuelve una instancia de un objeto para leer propiedades de configuracin.
 * 
 * @author ROBERTO RANZ
 */
public class PropertyReaderFactory {

	/**
	 * Devuelve una instancia de lector de propiedades obtenidas desde una
	 * referencia a una URL.
	 * 
	 * @param urlRef
	 *            Nombre de la referencia a URL del fichero de propiedades.
	 * @return Instancia de lector de propiedades.
	 */
	public static PropertyReader getURLPropertyReader(String urlRef) throws JavaLegoException {

		PropertyReaderImpl propertyReader = null;

		try {
			Properties configProperties = new Properties();
			InitialContext ctx = new InitialContext();

			URL configFileURL = (java.net.URL) ctx.lookup(urlRef);
			configProperties.load(configFileURL.openConnection().getInputStream());
			propertyReader = new PropertyReaderImpl(configProperties);
		}
		catch (NamingException e) {
			throw new JavaLegoException("PROPERTY URL " + urlRef + " NOT FOUND", e);
		}
		catch (IOException e) {
			throw new JavaLegoException("PROPERTY URL " + urlRef + " NOT FOUND", e);
		}
		return propertyReader;
	}

	/**
	 * Devuelve una instancia de lector de propiedades obtenidas desde una
	 * referencia a un fichero.
	 * 
	 * @param fileRef
	 *            Nombre de la referencia a URL del fichero de propiedades.
	 * @return Instancia de lector de propiedades.
	 */
	public static PropertyReader getFilePropertyReader(String fileRef) throws JavaLegoException {

		PropertyReaderImpl propertyReader = null;

		try {
			Properties configProperties = new Properties();
			configProperties.load(new FileInputStream(fileRef));
			propertyReader = new PropertyReaderImpl(configProperties);
		}
		catch (IOException e) {
			throw new JavaLegoException("PROPERTY FILE " + fileRef + " NOT FOUND", e);
		}
		return propertyReader;
	}

	/**
	 * Obtener informacin de un recurso Java.
	 * 
	 * @param resource
	 * @return
	 * @throws JavaLegoException
	 */
	public static PropertyReaderImpl getStreamPropertyReader(InputStream stream) throws JavaLegoException {

		PropertyReaderImpl propertyReader = null;

		try {
			Properties configProperties = new Properties();
			configProperties.load(stream);
			propertyReader = new PropertyReaderImpl(configProperties);
		}
		catch (IOException e) {
			throw new JavaLegoException("PROPERTY STREAM NOT FOUND", e);
		}
		return propertyReader;
	}

	/**
	 * Obtener informaci�n de un recurso JNDI del servidor
	 * 
	 * @param resource
	 * @return
	 * @throws JavaLegoException
	 */
	public static String getJNDIResource(String resource) throws JavaLegoException {

		String resourceValue = null;
		try {
			InitialContext ctx = new InitialContext();
			resourceValue = (String) ctx.lookup(resource);
		}
		catch (NamingException e) {
			throw new JavaLegoException("PROPERTY FILE " + resource + " NOT FOUND", e);
		}
		return resourceValue;
	}

	/**
	 * Buscar un reader válido para el recurso de propiedades.
	 * 
	 * <p>
	 * Buscará el recurso en sus diferentes tipologías (url, file, ...)
	 * 
	 * @param resourceProperties
	 * @return
	 * @throws JavaLegoException
	 */
	public static PropertyReader getReader(String resourceProperties) throws JavaLegoException {

		// Configuración por entornos
		if (resourceProperties != null) {
			// Leer de recurso URL en el servidor de aplicaciones.
			PropertyReader propReader = null;

			if (resourceProperties.indexOf("url/") == 0) {
				propReader = PropertyReaderFactory.getURLPropertyReader(resourceProperties);
			}
			// Recurso aplicación en classpath
			else if (resourceProperties.toLowerCase().indexOf("classpath:") == 0) {
				propReader = PropertyReaderFactory.getStreamPropertyReader(SystemUtils.getResource(resourceProperties.substring("classpath:".length())));
			}
			// Leer de archivo en disco
			else if (new File(resourceProperties).exists()) {
				propReader = PropertyReaderFactory.getFilePropertyReader(resourceProperties);
			}
			// Leer de archivo de recurso
			else {
				propReader = PropertyReaderFactory.getStreamPropertyReader(SystemUtils.getResource(resourceProperties));
			}
			return propReader;
		}
		return null;
	}

}

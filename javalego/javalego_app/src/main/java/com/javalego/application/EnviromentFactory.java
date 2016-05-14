package com.javalego.application;

import com.javalego.exception.JavaLegoException;
import com.javalego.exception.LocalizedException;
import com.javalego.properties.PropertyReader;
import com.javalego.properties.PropertyReaderFactory;
import com.javalego.util.ReflectionUtils;

/**
 * Factoría para obtener el entorno de ejecución de nuestra aplicación usando
 * diferentes canales o recursos.
 * 
 * @author ROBERTO RANZ
 *
 */
public class EnviromentFactory {

	private static final String ENVIRONMENT = "environment";

	private EnviromentFactory() {
	}

	/**
	 * Obtener el entorno a partir de un archivo de configuración de propiedades
	 * que incluya el valor "environment=Class name full path".
	 * 
	 * @param resource
	 * @return
	 * @throws LocalizedException
	 */
	public static Environment getEnvironment(String resource) throws LocalizedException {
		return getEnvironment(resource, ENVIRONMENT);
	}

	/**
	 * Obtener el entorno a partir de un archivo de configuración de propiedades
	 * que incluya el valor "environment=Class name full path".
	 * 
	 * @param resource
	 * @param propertyName
	 *            Nombre de la propiedad incluida en el archivo de propiedades
	 * @return
	 * @throws LocalizedException
	 */
	public static Environment getEnvironment(String resource, String propertyName) throws LocalizedException {

		Environment environment = null;

		try {
			PropertyReader reader = PropertyReaderFactory.getReader(resource);
			if (reader != null) {
				String className = reader.getString(propertyName);
				return (Environment) ReflectionUtils.createObject(className);
			}
		}
		catch (JavaLegoException e) {
			throw new LocalizedException(e);
		}

		return environment;
	}
}

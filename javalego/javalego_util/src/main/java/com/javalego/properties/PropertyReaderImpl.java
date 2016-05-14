package com.javalego.properties;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;

import com.javalego.exception.JavaLegoException;

/**
 * Clase da utilidad para obtener propiedades de configuracion. Se definen
 * propiedades obligatorias y opcionales. El objetivo es asegurar que la
 * aplicacion se inicia con las propiedades imprescindibles para su ejecucion.
 * 
 * @author ROBERTO RANZ
 */
public class PropertyReaderImpl implements PropertyReader {
	public static final String RUNTIME_ENVIRONMENT = "runtime.environment";
	public static final String JAVA_NAMESPACE = "java:comp/env/";

	private static final Logger logger = Logger.getLogger(PropertyReaderImpl.class);

	private Properties properties = null;

	/**
	 * Chequear que las propiedades existan. Si deseamos evitar la excepción y
	 * devolver null cuando no exista un valor, poner a false.
	 */
	private boolean checkProperties = true;

	/**
	 * Crea un lector de propiedades a partir de una colección de propiedades.
	 * El trazador es inyectado para facilitar la traza automótica de
	 * propiedades leidas en modo de depuración.
	 * 
	 * @param properties
	 *            Colección de propiedades.
	 * @throws JavaLegoException
	 *             Si la colección de propiedades es nula.
	 */
	public PropertyReaderImpl(Properties properties) throws JavaLegoException {
		if (properties == null) {
			throw new JavaLegoException("PROPS NOT LOADED");
		}
		else {
			this.properties = properties;
		}
	}

	/**
	 * Comprueba la existencia de una propiedad en el archivo de configuración.
	 * En caso contrario lanza una excepción.
	 * 
	 * @param propertyName
	 *            Nombre de la propiedad
	 * @return el valor asignado a la propiedad
	 * @throws JavaLegoException
	 *             Si la propiedad es nula o vacia.
	 */
	private String checkProperty(String propertyName) throws JavaLegoException {
		String propertyValue = this.properties.getProperty(propertyName);

		// Eliminar espacios en blanco antes de la validación.
		if (propertyValue != null) {
			propertyValue = propertyValue.trim();
		}

		if (checkProperties && ((propertyValue == null) || (propertyValue.length() == 0))) {
			throw new JavaLegoException("PROPERTY " + propertyName + " NOT FOUND");
		}

		return propertyValue;
	}

	/**
	 * Devuelve el valor de una propiedad obligatoria de tipo cadena que depende
	 * del entorno de ejecución (definida con la propiedad
	 * <code>runtime.environment</code>). El nombre completo de la propiedad en
	 * la configuración es:
	 * <p>
	 * <code><i>runtime.environment</i>.<i>propertyName</i></code>
	 * 
	 * @param propertyName
	 *            Nombre de la propiedad.
	 * @return Valor de la propiedad para el entorno de ejecución actual.
	 * @throws JavaLegoException
	 *             Si la propiedad no existe.
	 */
	@Override
	public String getRuntimeString(String propertyName) throws JavaLegoException {
		String runtime = this.getString(RUNTIME_ENVIRONMENT);
		return this.getString(runtime + "." + propertyName);
	}

	/**
	 * Devuelve el valor de una propiedad opcional de tipo cadena que depende
	 * del entorno de ejecución (definida con la propiedad
	 * <code>runtime.environment</code>). El nombre completo de la propiedad en
	 * la configuración es:
	 * <p>
	 * <code><i>runtime.environment</i>.<i>propertyName</i></code>
	 * 
	 * @param propertyName
	 *            Nombre de la propiedad.
	 * @return Valor de la propiedad para el entorno de ejecución actual.
	 * @throws JavaLegoException
	 *             Si la propiedad no existe.
	 */
	@Override
	public String getOptRuntimeString(final String propertyName) throws JavaLegoException {
		String runtime = this.getString(RUNTIME_ENVIRONMENT);
		return this.getOptString(runtime + "." + propertyName);
	}

	/**
	 * Devuelve una referencia a un recurso Java en el espacio de nombres
	 * <code>java:comp/env</code> a partir de una propieadad obligatoria, que
	 * siempre es una cadena. Las referencias no varian según el entorno de
	 * ejecución.
	 * 
	 * @param propertyName
	 *            Nombre de la propiedad que almacena el nombre del recurso.
	 * @return Cadena con el nombre de la referencia al recurso.
	 * @throws JavaLegoException
	 *             Si la propiedad no existe.
	 */
	@Override
	public String getResourceRef(String propertyName) throws JavaLegoException {
		return JAVA_NAMESPACE + this.getString(propertyName);
	}

	/**
	 * Devuelve una referencia a un recurso Java en el espacio de nombres
	 * <code>java:comp/env</code> a partir de una propieadad opcional que, de
	 * existir, siempre es una cadena. Las referencias no varian según el
	 * entorno de ejecución.
	 * 
	 * @param propertyName
	 *            Nombre de la propiedad que almacena el nombre del recurso.
	 * @return Cadena con el nombre de la referencia al recurso.
	 * @throws JavaLegoException
	 *             Si la propiedad no existe.
	 */
	@Override
	public String getOptResourceRef(String propertyName) throws JavaLegoException {
		String val = this.getOptString(propertyName);
		return (val != null) ? JAVA_NAMESPACE + val : null;
	}

	/**
	 * Devuelve el valor de una propiedad obligatoria de tipo cadena que depende
	 * del entorno de ejecución (definida con la propiedad
	 * <code>runtime.environment</code>). El nombre completo de la propiedad en
	 * la configuración es:
	 * <p>
	 * <code><i>runtime.environment</i>.<i>propertyName</i></code>
	 * 
	 * @param propertyName
	 *            Nombre de la propiedad.
	 * @return Valor de la propiedad para el entorno de ejecución actual.
	 * @throws JavaLegoException
	 *             Si la propiedad no existe.
	 */
	@Override
	public int getRuntimeInt(String propertyName) throws JavaLegoException {
		String runtime = this.getString(RUNTIME_ENVIRONMENT);
		return this.getInt(runtime + "." + propertyName);
	}

	/**
	 * Devuelve el valor de una propiedad opcional de tipo cadena que depende
	 * del entorno de ejecución (definida con la propiedad
	 * <code>runtime.environment</code>). El nombre completo de la propiedad en
	 * la configuración es:
	 * <p>
	 * <code><i>runtime.environment</i>.<i>propertyName</i></code>
	 * 
	 * @param propertyName
	 *            Nombre de la propiedad.
	 * @return Valor de la propiedad para el entorno de ejecución actual.
	 * @throws JavaLegoException
	 *             Si la propiedad no existe.
	 */
	@Override
	public int getOptRuntimeInt(String propertyName) throws JavaLegoException {
		String runtime = this.getString(RUNTIME_ENVIRONMENT);
		return this.getOptInt(runtime + "." + propertyName);
	}

	/**
	 * Devuelve el valor de una propiedad obligatoria de tipo cadena de texto.
	 * 
	 * @param propertyName
	 *            Nombre de la propiedad.
	 * @return Valor de la propiedad.
	 * @throws JavaLegoException
	 *             Si la propiedad no existe.
	 */
	@Override
	public String getString(String propertyName) throws JavaLegoException {
		return checkProperty(propertyName);
	}

	/**
	 * Devuelve el valor de una propiedad obligatoria de tipo entero.
	 * 
	 * @param propertyName
	 *            Nombre de la propiedad.
	 * @return Valor de la propiedad.
	 * @throws JavaLegoException
	 *             Si la propiedad no existe o si está mal formada.
	 */
	@Override
	public int getInt(String propertyName) throws JavaLegoException {
		String property = checkProperty(propertyName);

		try {
			return Integer.parseInt(property);
		}
		catch (NumberFormatException e) {
			throw new JavaLegoException("ERROR PARSING INT", e);
		}
	}

	/**
	 * Devuelve el valor de una propiedad obligatoria de tipo entero largo.
	 * 
	 * @param propertyName
	 *            Nombre de la propiedad.
	 * @return Valor de la propiedad.
	 * @throws JavaLegoException
	 *             Si la propiedad no existe o si está mal formada.
	 */
	@Override
	public long getLong(String propertyName) throws JavaLegoException {
		String property = checkProperty(propertyName);

		try {
			return Long.parseLong(property);
		}
		catch (NumberFormatException e) {
			throw new JavaLegoException("ERROR PARSING LONG", e);
		}
	}

	/**
	 * Devuelve el valor de una propiedad optativa de tipo cadena de texto.
	 * 
	 * @param propertyName
	 *            Nombre de la propiedad.
	 * @return Valor de la propiedad.
	 * @throws JavaLegoException
	 *             Si la propiedad no existe.
	 */
	@Override
	public String getOptString(String propertyName) throws JavaLegoException {
		return this.properties.getProperty(propertyName);
	}

	/**
	 * Devuelve el valor de una propiedad optativa de tipo entero.
	 * 
	 * @param propertyName
	 *            Nombre de la propiedad.
	 * @return Valor de la propiedad.
	 * @throws JavaLegoException
	 *             Si la propiedad no existe o si está mal formada.
	 */
	@Override
	public int getOptInt(String propertyName) throws JavaLegoException {
		String property = this.properties.getProperty(propertyName);

		try {
			return Integer.parseInt(property);
		}
		catch (NumberFormatException e) {
			return 0;
		}
	}

	/**
	 * Devuelve el valor de una propiedad optativa de tipo entero largo.
	 * 
	 * @param propertyName
	 *            Nombre de la propiedad.
	 * @return Valor de la propiedad.
	 * @throws JavaLegoException
	 *             Si la propiedad no existe o si está mal formada.
	 */
	@Override
	public long getOptLong(String propertyName) throws JavaLegoException {
		String property = this.properties.getProperty(propertyName);

		try {
			return Long.parseLong(property);
		}
		catch (NumberFormatException e) {
			return 0;
		}
	}

	/**
	 * Carga en memoria las propiedades almacenadas en un fichero.
	 * 
	 * @return Un objeto {@link Properties} con las propiedades existentes en el
	 *         fichero o <code>null</code> si ha ocurrido algún error.
	 * @param filename
	 *            Ruta del fichero que contiene las propiedades.
	 * @throws JavaLegoException
	 *             No se puede leer el fichero de propiedades.
	 */
	public Properties loadProperties(String filename) throws JavaLegoException {
		Properties props = null;
		try {
			props = new Properties();
			props.load(new FileInputStream(filename));
			logger.debug("Fichero de propiedades cargado: " + filename);

			return props;
		}
		catch (IOException e) {
			throw new JavaLegoException("PROPERTY FILE " + filename + " NOT FOUND", e);
		}
	}

	/**
	 * Escribe en un traza las propiedades de la JVM que esta ejecutando la
	 * aplicacion.
	 */
	public void printSystemProperties() {
		this.printProperties(System.getProperties());
	}

	/**
	 * Escribe una traza de depuración con el nombre y el valor de una
	 * propiedad.
	 * 
	 * @param propertyName
	 *            Nombre de la propiedad.
	 * @param propertyValue
	 *            Valor de la propiedad.
	 */
	public void printProperty(String propertyName, String propertyValue) {
		logger.debug(propertyName + ": '" + propertyValue + "'.");
	}

	public void printProperties(Properties props) {

		Set<Object> set = (Set<Object>) props.keySet();
		Iterator<Object> setIt = set.iterator();
		Object name;
		while (setIt.hasNext()) {
			name = setIt.next();
			this.printProperty(name.toString(), props.getProperty(name.toString()));
		}

	}

	public boolean isCheckProperties() {
		return checkProperties;
	}

	@Override
	public void setCheckProperties(boolean checkProperties) {
		this.checkProperties = checkProperties;
	}

	@Override
	public void applySystemProperties() {

		if (properties == null) {
			return;
		}

		for (Object property : properties.keySet()) {

			String value = properties.getProperty(property.toString());

			if (value != null) {
				while (value != null && value.indexOf("${") > -1) {
					int init = value.indexOf("${");

					int end = value.indexOf("}", init + 1);

					String system = System.getProperty(value.substring(init + 2, end));

					if (system != null) {
						value = value.substring(0, init) + system + value.substring(end + 1);
					}
					else {
						break;
					}
				}

				// Modificar valor de la propiedad
				if (!value.equals(properties.getProperty(property.toString()))) {
					properties.setProperty(property.toString(), value);
				}
			}

		}

	}

}

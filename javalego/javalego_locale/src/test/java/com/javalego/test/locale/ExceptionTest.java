package com.javalego.test.locale;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.javalego.errors.CommonErrors;
import com.javalego.errors.Error;
import com.javalego.errors.ErrorLevel;
import com.javalego.errors.ErrorManager;
import com.javalego.locale.LocaleContext;
import com.javalego.model.keys.ErrorKey;
import com.javalego.model.resources.locale.Locale;

/**
 * Test de localización de excepciones
 * 
 * @author ROBERTO RANZ
 *
 */
public class ExceptionTest {

	public static final Logger logger = Logger.getLogger(ExceptionTest.class);

	/**
	 * Lista de errores de ejemplo multiidioma
	 * 
	 * @author ROBERTO RANZ
	 * 
	 */
	public enum TestExceptions implements ErrorKey {

		@Error(level = ErrorLevel.ERROR, locales = { @Locale(value = "EL TRACER DE APLICACION: {0} - {1} YA ESTA REGISTRADO."),
				@Locale(value = "TRACE APPLICATION: {0} - {1} REGISTERED.", locale = "en") })
		EXIST_TRACER,

		@Error(level = ErrorLevel.ERROR, locales = { @Locale(value = "ERROR DE CONEXION CON BASE DE DATOS."), @Locale(value = "DATABASE CONNECT ERROR.", locale = "en") })
		CONNECT_ERROR,

		// Ejemplo de localización de excepciones definidas con ResourceBundles
		// (ver recursos .properties)
		LOGIN_ERROR,
	}

	/**
	 * Crea una excepción localizada a partir de locale de la aplicación y un
	 * código de excepción que incluye anotaciones multiidioma
	 */
	@Test
	public void test() throws Exception {

		// Excepción con 2 parámetros y locale = default
		logger.info(LocaleContext.getException(TestExceptions.EXIST_TRACER, "TEST", "V1"));
		// Excepción locale = en.
		logger.info(LocaleContext.getException(TestExceptions.CONNECT_ERROR, java.util.Locale.ENGLISH));

	}

	@Test
	public void testError() throws Exception {
		ErrorManager em = new ErrorManager(CommonErrors.REQUIRED_FIELD);
		logger.info(em.code());
		logger.info(em.message("nombre"));
		logger.info(em.level());
	}
	
	/**
	 * Crea una excepción localizada usando archivos de paquetes de recursos.
	 */
	@Test
	public void testResources() throws Exception {

		LocaleContext.setTranslatorResources(true, "locales/messages", "locales/messages2");

		// Excepción locale = us
		logger.info(LocaleContext.getException(TestExceptions.LOGIN_ERROR, java.util.Locale.US));
		// Excepción locale = default
		logger.info(LocaleContext.getException(TestExceptions.LOGIN_ERROR));

	}

}

package com.javalego.test.ui;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.javalego.locale.LocaleContext;
import com.javalego.model.keys.Key;
import com.javalego.model.resources.locale.Languages;
import com.javalego.model.resources.locale.Locale;
import com.javalego.ui.field.impl.TextFieldModel;

/**
 * Test de localización de textos.
 * 
 * @author ROBERTO RANZ
 *
 */
public class TranslatorTest {

	public static final Logger logger = Logger.getLogger(TranslatorTest.class);

	/**
	 * Códigos de textos multiidioma.
	 * 
	 * @author ROBERTO RANZ
	 * 
	 */
	public enum TestLocale implements Key {

		@Languages(locales = { @Locale(value = "Eliminar {0}", description = "Eliminar {0} de la base de datos"), @Locale(locale = "en", value = "Delete {0} database") })
		DELETE,

		@Languages(locales = { @Locale(value = "Domicilio", description = "Domicilio habitual"), @Locale(locale = "en", value = "Address", description = "Current address") })
		ADDRESS,

		PEOPLE

	}

	/**
	 * Traducción de textos usando códigos de enumerados y anotaciones
	 * localizadas en varios idiomas.
	 * 
	 * @see TestLocale
	 */
	@Test
	public void test() throws Exception {

		// Localizar un texto con un parámetro
		logger.info(LocaleContext.getText(TestLocale.DELETE, "registro"));
		logger.info(LocaleContext.getDescription(TestLocale.DELETE, "registro"));

		// Traduce el título de un modelo de datos en varios idiomas.
		// TestLocale contiene la lista de claves enumeradas donde se han
		// definido los diferentes textos en varios idiomas.
		TextFieldModel text = new TextFieldModel();
		text.setTitle(TestLocale.ADDRESS);

		logger.info(LocaleContext.getText(text.getTitle(), java.util.Locale.US));
		logger.info(LocaleContext.getText(text.getTitle()));

		// Traducir atributo description de la anotación @Locale
		logger.info(LocaleContext.getDescription(text.getTitle(), java.util.Locale.US));
		logger.info(LocaleContext.getDescription(text.getTitle()));

	}

	/**
	 * Traducción de textos usando archivos de paquetes de recursos. (estándar
	 * Java).
	 */
	@Test
	public void testResources() throws Exception {

		// Establecer el tipo de traductor basado en archivos de recursos
		// incluidos en el directorio /locales.
		LocaleContext.setTranslatorResources(true, "locales/messages", "locales/messages2");

		// Localizar un texto con un parámetro
		logger.info(LocaleContext.getText(TestLocale.PEOPLE));

		// Traduce el título de un modelo de datos en varios idiomas.
		// TestLocale contiene la lista de claves enumeradas donde se han
		// definido los diferentes textos en varios idiomas.
		TextFieldModel text = new TextFieldModel();
		text.setTitle(TestLocale.PEOPLE);

		logger.info(LocaleContext.getText(text.getTitle(), java.util.Locale.US));
		logger.info(LocaleContext.getText(text.getTitle()));

	}
}

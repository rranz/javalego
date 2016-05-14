package com.javalego.test.locale;

import java.util.Arrays;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.javalego.icons.impl.ResourceIcons;
import com.javalego.locale.LocaleContext;
import com.javalego.model.keys.Icon;

/**
 * Test de localización de imágenes e iconos.
 * 
 * @author ROBERTO RANZ
 *
 */
public class IconTest {

	public static final Logger logger = Logger.getLogger(IconTest.class);

	/**
	 * Códigos de iconos existentes en el directorio de recursos /icons.
	 * 
	 * @author ROBERTO RANZ
	 * 
	 */
	public enum TestIcons implements Icon {
		CATEGORIES, HOME, PRODUCTS
	}

	@Test
	public void test() throws Exception {

		logger.info("Loading US Icons in  /icons ...");

		String name = "test-icons";

		// Creación de un repositorio de iconos
		// El directorio /icons contiene la lista de recursos de ficheros con imágenes cuyos
		// nombres deben de coincidir con la lista de códigos enumerados.
		ResourceIcons rep = new ResourceIcons().load(name, TestIcons.class, Locale.US, "/icons");

		LocaleContext.addRepositoryIcons(rep);

		logger.info("Categories icon US value: " + LocaleContext.getIcon(TestIcons.CATEGORIES, Locale.US));

		logger.info("Icon US names: " + Arrays.toString(LocaleContext.findRepositoryIcons(name).getNames()));

		logger.info("Loaded US icons successful");

	}

}

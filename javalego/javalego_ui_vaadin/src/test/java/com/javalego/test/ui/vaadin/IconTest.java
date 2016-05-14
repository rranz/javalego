package com.javalego.test.ui.vaadin;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Locale;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.javalego.locale.LocaleContext;
import com.javalego.model.keys.Icon;
import com.javalego.ui.vaadin.UIContextVaadin;
import com.javalego.ui.vaadin.icons.FontAwesomeIcons;
import com.javalego.ui.vaadin.icons.ResourceIconsVaadin;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Component;

/**
 * Test de localización de imágenes e iconos usando las diferentes tipologías de
 * repositorios de iconos existentes para Vaadin.
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

		// Configurar repositorio de imágenes.
		LocaleContext.addRepositoryIcons(new ResourceIconsVaadin().load(TestIcons.class, Locale.US, "/icons"));

		// Obtener el componente que representa un icono con locale = US.
		Component component = UIContextVaadin.getComponent(TestIcons.CATEGORIES);

		logger.info("Component for CATEGORIES icon: " + component.getClass().getCanonicalName());

		assertNotNull(component);

		// Obtener el componente que representa un icono con locale = US.
		component = UIContextVaadin.getComponent(TestIcons.CATEGORIES, Locale.US);

		assertNotNull(component);

		logger.info("Component for CATEGORIES US icon: " + component.getClass().getCanonicalName());

		logger.info("Loaded US icons successful");
	}

	@Test
	public void testFontawesome() {

		try {
			logger.info("Loading US Icons in  /icons ...");

			// Establecer equivalencias de enumerados con iconos FontAwesome.
			FontAwesomeIcons fa = FontAwesomeIcons.getCurrent();
			fa.setLocale(Locale.US);
			fa.addIcon(TestIcons.HOME, FontAwesome.HOME);
			fa.addIcon(TestIcons.PRODUCTS, FontAwesome.SHOPPING_CART);
			fa.addIcon(TestIcons.CATEGORIES, FontAwesome.GEARS);

			// Añadir repositorio al contexto
			LocaleContext.addRepositoryIcons(FontAwesomeIcons.getCurrent());

			// Obtener el componente que representa un icono con locale = US.
			Component component = UIContextVaadin.getComponent(TestIcons.HOME);

			logger.info("Component for HOME icon: " + component.getClass().getCanonicalName());

			assertNotNull(component);

			// Obtener el componente que representa un icono con locale = US.
			component = UIContextVaadin.getComponent(TestIcons.CATEGORIES, Locale.US);

			assertNotNull(component);

			logger.info("Component for CATEGORIES US icon: " + component.getClass().getCanonicalName());

			logger.info("Loaded US icons successful");
		}
		catch (Exception e) {
			assertTrue(false);
		}

	}

}

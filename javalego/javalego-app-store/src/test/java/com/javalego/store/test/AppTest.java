package com.javalego.store.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.javalego.application.AppContext;
import com.javalego.application.EnvironmentImpl;
import com.javalego.exception.LocalizedException;
import com.javalego.icons.RepositoryIcons;
import com.javalego.model.keys.Icon;
import com.javalego.security.shiro.SecurityShiro;
import com.javalego.store.ui.icons.MenuIcons;
import com.javalego.store.ui.icons.MenuIcons2;
import com.javalego.store.ui.icons.ProviderIcons;
import com.javalego.store.ui.locale.LocaleStore;
import com.javalego.ui.vaadin.icons.FontAwesomeIcons;
import com.javalego.ui.vaadin.icons.ResourceIconsVaadin;
import com.vaadin.server.FontAwesome;

/**
 * Test de contexto de aplicación.
 * 
 * @author ROBERTO RANZ
 *
 */
public class AppTest {

	@Before
	public void BeforeTest() throws LocalizedException {
	}

	public static final Logger logger = Logger.getLogger(AppTest.class);

	@Test
	public void environment() throws LocalizedException {

		EnvironmentImpl environment = new EnvironmentImpl(LocaleStore.TEST.name(), LocaleStore.TEST);

		// Establecer los servicios de la aplicación.

		// Acceso a datos
		//environment.setDataProvider(new SpringDataProvider(StoreApplicationContext.class));

		// Repositorio de imágenes
		List<RepositoryIcons<Icon>> icons = new ArrayList<RepositoryIcons<Icon>>();

		// Iconos de redes sociales usando FonAwesome icons.
		FontAwesomeIcons fa = FontAwesomeIcons.getCurrent();
		fa.addIcon(MenuIcons2.BOOK, FontAwesome.BOOK);
		fa.addIcon(MenuIcons2.MONITOR, FontAwesome.DESKTOP);

		// Resto de iconos
		ResourceIconsVaadin rep = ResourceIconsVaadin.getCurrent();
		// rep.setLocale(Locale.US);
		rep.load(MenuIcons.class, "/menu_icons");
		rep.load(ProviderIcons.class, "/provider_icons");

		icons.add(fa);
		icons.add(rep);

		environment.setRepositoriesIcons(icons);

		// Servicios de seguridad
		environment.setSecurity(new SecurityShiro("classpath:shiro.ini"));

		// Cargar contextos de aplicación en base a la configuración de los
		// servicios del entorno de ejecución.
		AppContext.getCurrent().load(environment);

		logger.info("Loaded environment " + AppContext.getCurrent().getName());
	}

}

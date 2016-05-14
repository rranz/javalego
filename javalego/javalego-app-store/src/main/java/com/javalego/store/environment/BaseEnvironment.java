package com.javalego.store.environment;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.stereotype.Component;

import com.javalego.application.Environment;
import com.javalego.exception.LocalizedException;
import com.javalego.icons.RepositoryIcons;
import com.javalego.locale.translator.Translator;
import com.javalego.model.keys.Icon;
import com.javalego.model.keys.Key;
import com.javalego.security.SecurityServices;
import com.javalego.security.session.UserSession;
import com.javalego.security.shiro.SecurityShiro;
import com.javalego.session.UserSessionVaadin;
import com.javalego.store.model.ModelService;
import com.javalego.store.ui.components.fields.ProvidersField;
import com.javalego.store.ui.components.fields.ProvidersFieldModel;
import com.javalego.store.ui.components.fields.RatingField;
import com.javalego.store.ui.components.fields.RatingFieldModel;
import com.javalego.store.ui.icons.MenuIcons;
import com.javalego.store.ui.icons.MenuIcons2;
import com.javalego.store.ui.icons.ProviderIcons;
import com.javalego.store.ui.locale.LocaleStore;
import com.javalego.ui.icons.enums.IconEditor;
import com.javalego.ui.vaadin.factory.FieldsFactoryVaadin;
import com.javalego.ui.vaadin.icons.FontAwesomeIcons;
import com.javalego.ui.vaadin.icons.ResourceIconsVaadin;
import com.vaadin.server.FontAwesome;

/**
 * Entorno de pruebas.
 * 
 * @author ROBERTO RANZ
 *
 */
@Component
public abstract class BaseEnvironment implements Environment {

	private SecurityShiro security;

	private UserSessionVaadin userSession;

	private ArrayList<RepositoryIcons<Icon>> repositories;

	public BaseEnvironment() {

		// Añadir campos específicos del Store.
		FieldsFactoryVaadin.getCurrent().addField(ProvidersFieldModel.class, ProvidersField.class);
		FieldsFactoryVaadin.getCurrent().addField(RatingFieldModel.class, RatingField.class);
	}

	
	@Override
	public String getName() {
		return LocaleStore.TEST.name();
	}

	@Override
	public Key getTitle() {
		return LocaleStore.TEST;
	}

	@Override
	public Key getDescription() {
		return null;
	}

	@Override
	public synchronized Collection<RepositoryIcons<Icon>> getRepositoriesIcons() throws LocalizedException {

		if (repositories != null) {
			return repositories;
		}

		repositories = new ArrayList<RepositoryIcons<Icon>>();

		// Iconos de redes sociales usando FonAwesome icons.
		FontAwesomeIcons fa = FontAwesomeIcons.getCurrent();
		fa.addIcon(MenuIcons2.BOOK, FontAwesome.BOOK);
		fa.addIcon(MenuIcons2.MONITOR, FontAwesome.DESKTOP);
		fa.addIcon(MenuIcons2.SOURCECODE, FontAwesome.CODE);
		fa.addIcon(MenuIcons2.GITHUB, FontAwesome.GITHUB);
		fa.addIcon(MenuIcons2.METRICS, FontAwesome.BAR_CHART_O);
		fa.addIcon(MenuIcons2.WEB, FontAwesome.GLOBE);
		fa.addIcon(MenuIcons2.TWITTER, FontAwesome.TWITTER);
		fa.addIcon(MenuIcons2.FORUM, FontAwesome.USERS);
		fa.addIcon(MenuIcons2.BLOG, FontAwesome.WORDPRESS);
		fa.addIcon(MenuIcons2.LINKEDIN, FontAwesome.LINKEDIN);
		fa.addIcon(MenuIcons2.FACEBOOK, FontAwesome.FACEBOOK);
		fa.addIcon(MenuIcons2.GOOGLEPLUS, FontAwesome.GOOGLE_PLUS);
		fa.addIcon(MenuIcons2.EMAIL, FontAwesome.ENVELOPE);
		fa.addIcon(MenuIcons2.TOOLS, FontAwesome.COGS);
		fa.addIcon(MenuIcons2.LICENSE, FontAwesome.FILE);
		
		// Resto de iconos
		ResourceIconsVaadin rep = ResourceIconsVaadin.getCurrent();
		//rep.setLocale(Locale.US);
		rep.load(MenuIcons.class, "/menu_icons");
		rep.load(MenuIcons2.class, "/menu_icons2");
		rep.load(ProviderIcons.class, "/provider_icons");
		rep.load(IconEditor.class, "/icons_editor");
		
		repositories.add(fa);
		repositories.add(rep);

		return repositories;
	}

	@Override
	public synchronized SecurityServices getSecurity() {
		if (security == null) {
			security = new SecurityShiro("classpath:shiro.ini");
		}
		return security;
	}

	@Override
	public synchronized UserSession getUserSession() {
		if (userSession == null) {
			userSession = new UserSessionVaadin();
			// userSession.getScreenSize().setLarge(801);
			// userSession.getScreenSize().setSmall(800);
		}
		return userSession;
	}

	/**
	 * Modelo de datos usado por el entorno
	 * 
	 * @return
	 */
	public abstract ModelService getModelService();
	
	@Override
	public Translator getTranslator() {
		return null;
	}
}

package com.javalego.ui.field.impl;

import com.javalego.model.keys.Key;
import com.javalego.model.resources.locale.Languages;
import com.javalego.model.resources.locale.Locale;

/**
 * Redes sociales
 * 
 * @author ROBERTO RANZ
 */
public class SocialFieldModel extends TextFieldModel {

	private static final long serialVersionUID = -6506218664425552370L;

	private Social social;

	public static enum Social implements Key {
		@Languages(locales = { 
				@Locale(value = "Google+"),
				@Locale(locale = "en", value = "Google+")
				})
		GOOGLE_PLUS, 
		@Languages(locales = { 
				@Locale(value = "Twitter"),
				@Locale(locale = "en", value = "Twitter")
				})
		TWITTER, 
		@Languages(locales = { 
				@Locale(value = "Linkedin"),
				@Locale(locale = "en", value = "Linkedin")
				})
		LINKEDIN, 
		@Languages(locales = { 
				@Locale(value = "Facebook"),
				@Locale(locale = "en", value = "Facebook")
				})
		FACEBOOK, 
		@Languages(locales = { 
				@Locale(value = "WordPress"),
				@Locale(locale = "en", value = "WordPress")
				})
		WORDPRESS, 
		@Languages(locales = { 
				@Locale(value = "Email"),
				@Locale(locale = "en", value = "Email")
				})
		EMAIL, 
		@Languages(locales = { 
				@Locale(value = "Web"),
				@Locale(locale = "en", value = "Web")
				})
		WEB, 
		@Languages(locales = { 
				@Locale(value = "Forum"),
				@Locale(locale = "en", value = "Forum")
				})
		FORUM, 
		@Languages(locales = { 
				@Locale(value = "Blog"),
				@Locale(locale = "en", value = "Blog")
				})
		BLOG
	}

	public SocialFieldModel() {
		init();
	}

	public SocialFieldModel(String name, Social social) {
		super(name, social);
		this.social = social;
		init();
	}

	public SocialFieldModel(Social social) {
		super(social);
		this.social = social;
		init();
	}

	private void init() {
		setColumns(50);
		setMaxSize(200);
	}

	public Social getSocial() {
		return social;
	}

	public void setSocial(Social social) {
		this.social = social;
	}

}

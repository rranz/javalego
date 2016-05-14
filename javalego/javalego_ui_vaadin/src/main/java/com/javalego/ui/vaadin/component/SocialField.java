package com.javalego.ui.vaadin.component;

import com.javalego.ui.field.impl.SocialFieldModel;
import com.javalego.ui.field.impl.SocialFieldModel.Social;
import com.vaadin.server.FontAwesome;

/**
 * Campo de url de acceso a redes sociales (ver tipologías en enumerado).
 * 
 * @author ROBERTO RANZ
 */
public class SocialField extends TextFieldExt {

	private static final long serialVersionUID = 8242096641062282939L;

	private SocialFieldModel.Social social;
	
	public SocialField() {
	}
	
	public SocialField(String caption) {
		super(caption);
	}

	public SocialField(String caption, Social social) {
		super(caption);
		setSocial(social);
	}

	public SocialFieldModel.Social getSocial() {
		return social;
	}

	public void setSocial(SocialFieldModel.Social social) {
		this.social = social;
		
		if (social == Social.FACEBOOK) {
			setIcon(FontAwesome.FACEBOOK);
		}
		else if (social == Social.GOOGLE_PLUS) {
			setIcon(FontAwesome.GOOGLE_PLUS);
		}
		else if (social == Social.TWITTER) {
			setIcon(FontAwesome.TWITTER);
		}
		else if (social == Social.EMAIL) {
			setIcon(FontAwesome.ENVELOPE);
		}
		else if (social == Social.WORDPRESS) {
			setIcon(FontAwesome.WORDPRESS);
		}
		else if (social == Social.LINKEDIN) {
			setIcon(FontAwesome.LINKEDIN);
		}
		else if (social == Social.WEB) {
			setIcon(FontAwesome.EXTERNAL_LINK);
		}
		else {
			setIcon(FontAwesome.USERS);
		}
		
	}
}

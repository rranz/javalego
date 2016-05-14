package com.javalego.store.model.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.javalego.exception.LocalizedException;
import com.javalego.store.items.Type;
import com.javalego.store.model.ModelService;
import com.javalego.store.ui.StoreAppContext;
import com.javalego.store.ui.components.fields.ProvidersFieldModel;
import com.javalego.store.ui.components.fields.RatingFieldModel;
import com.javalego.store.ui.locale.LocaleStore;
import com.javalego.ui.field.FieldModel;
import com.javalego.ui.field.impl.DateFieldModel;
import com.javalego.ui.field.impl.ImageFieldModel;
import com.javalego.ui.field.impl.ListFieldModel;
import com.javalego.ui.field.impl.SocialFieldModel;
import com.javalego.ui.field.impl.TextAreaFieldModel;
import com.javalego.ui.field.impl.TextFieldModel;
import com.javalego.ui.field.impl.SocialFieldModel.Social;
import com.javalego.ui.mvp.editor.IBaseEditorModel;
import com.javalego.ui.mvp.editor.layout.LayoutEditorModel;
import com.javalego.ui.vaadin.component.rating.LocaleRating;

/**
 * Implementación del servicio de obtención de datos del modelo (pattern MVP)
 * 
 * @author ROBERTO RANZ
 *
 */
public class ModelServiceImpl implements ModelService {

	private static final String SOCIAL_BLOG = "social.blog";
	private static final String CODE_METRICS = "code.metrics";
	private static final String SOCIAL_FORUM = "social.forum";
	private static final String CODE_JAVADOC = "code.javadoc";
	private static final String SOCIAL_WEB = "social.web";
	private static final String CODE_DOC = "code.doc";
	private static final String SOCIAL_EMAIL = "social.email";
	private static final String SOCIAL_GOOGLEPLUS = "social.googleplus";
	private static final String CODE_URL_REPOSITORY = "code.urlRepository";
	private static final String CODE_REPOSITORY = "code.repository";
	private static final String SOCIAL_FACEBOOK = "social.facebook";
	private static final String SOCIAL_LINKEDIN = "social.linkedin";
	private static final String SOCIAL_TWITTER = "social.twitter";
	private static final String CURRENT_VERSION_NOTES = "currentVersion.notes";
	private static final String CURRENT_VERSION_NAME = "currentVersion.name";
	private static final String PROJECT_AUTHOR = "project.author.title";
	private static final String DEMO_VIEW = "demo.view";
	private static final String DEMO_URL_CODE = "demo.urlCode";
	private static final String DEMO_URL = "demo.url";
	private static final String AUTHOR_TITLE = "author.title";

	private Collection<FieldModel> projectFields;

	private Collection<FieldModel> commentFields;

	private Collection<FieldModel> screenShotFields;

	public ModelServiceImpl() {
	}

	@Override
	public synchronized Collection<FieldModel> getProductFields(Type type) throws LocalizedException {

		Collection<FieldModel> productFields = new ArrayList<FieldModel>();

		productFields.add(new ListFieldModel(LocaleStore.CATEGORY, StoreAppContext.getCurrent().getDataServices().getCategories(type)).setRequired(true));
		productFields.add(new TextFieldModel(LocaleStore.NAME, true).setMaxSize(30).setRequired(true));
		productFields.add(new TextFieldModel(LocaleStore.TITLE, true).setMaxSize(50).setRequired(true));
		productFields.add(new TextAreaFieldModel(LocaleStore.DESCRIPTION));
		productFields.add(new ListFieldModel(LocaleStore.LICENSE, StoreAppContext.getCurrent().getDataServices().getAllLicenses()).setRequired(true));
		productFields.add(new TextFieldModel(PROJECT_AUTHOR, LocaleStore.AUTHOR).setReadOnly(true));

		// Demo
		productFields.add(new TextFieldModel(DEMO_URL, LocaleStore.DEMO_URL));
		productFields.add(new TextFieldModel(DEMO_URL_CODE, LocaleStore.DEMO_URLCODE));
		productFields.add(new TextFieldModel(DEMO_VIEW, LocaleStore.DEMO_VIEW));

		return productFields;
	}

	@Override
	public LayoutEditorModel getProductLayoutEditor(IBaseEditorModel model, boolean readOnly) {

		LayoutEditorModel lp = new LayoutEditorModel(model, LocaleStore.CATEGORY, LocaleStore.NAME, LocaleStore.TITLE, LocaleStore.DESCRIPTION, LocaleStore.LICENSE);
		lp.addField(PROJECT_AUTHOR);
		lp.addChildLayout(LocaleStore.DEMO, DEMO_URL, DEMO_URL_CODE, DEMO_VIEW);

		return lp;
	}

	@Override
	public synchronized Collection<FieldModel> getProjectFields(boolean readOnly) throws LocalizedException {

		if (projectFields != null) {
			return projectFields;
		}

		projectFields = new ArrayList<FieldModel>();

		projectFields.add(new ListFieldModel(LocaleStore.TYPE, new Type[] { Type.ARCHITECTURE, Type.BUSINESS }).setReadOnly(true));

		projectFields.add(new TextFieldModel(LocaleStore.NAME, true).setMaxSize(30).setRequired(true));
		projectFields.add(new TextFieldModel(LocaleStore.TITLE, true).setMaxSize(50).setRequired(true));
		projectFields.add(new TextAreaFieldModel(LocaleStore.DESCRIPTION));
		projectFields.add(new ListFieldModel(LocaleStore.LICENSE, StoreAppContext.getCurrent().getDataServices().getAllLicenses()).setRequired(true));
		projectFields.add(new TextFieldModel(CURRENT_VERSION_NAME, LocaleStore.VERSION));
		projectFields.add(new TextAreaFieldModel(CURRENT_VERSION_NOTES, LocaleStore.VERSION_NOTES));
		projectFields.add(new ProvidersFieldModel(LocaleStore.PROVIDERS));
		projectFields.add(new TextAreaFieldModel(LocaleStore.COMMENT_PROVIDERS));
		projectFields.add(new TextFieldModel(AUTHOR_TITLE, LocaleStore.AUTHOR).setReadOnly(true));

		if (!readOnly) {

			projectFields.add(new ListFieldModel(CODE_REPOSITORY, LocaleStore.REPOSITORY, StoreAppContext.getCurrent().getDataServices().getAllRepositories()).setRequired(true));
			projectFields.add(new TextFieldModel(CODE_URL_REPOSITORY, LocaleStore.URL_REPOSITORY).setRequired(true));
			projectFields.add(new TextFieldModel(CODE_DOC, LocaleStore.DOC));
			projectFields.add(new TextFieldModel(CODE_JAVADOC, LocaleStore.JAVADOC));
			projectFields.add(new TextFieldModel(CODE_METRICS, LocaleStore.METRICS));

			projectFields.add(new SocialFieldModel(SOCIAL_TWITTER, Social.TWITTER));
			projectFields.add(new SocialFieldModel(SOCIAL_LINKEDIN, Social.LINKEDIN));
			projectFields.add(new SocialFieldModel(SOCIAL_FACEBOOK, Social.FACEBOOK));
			projectFields.add(new SocialFieldModel(SOCIAL_GOOGLEPLUS, Social.GOOGLE_PLUS));
			projectFields.add(new SocialFieldModel(SOCIAL_EMAIL, Social.EMAIL));
			projectFields.add(new SocialFieldModel(SOCIAL_WEB, Social.WEB));
			projectFields.add(new SocialFieldModel(SOCIAL_FORUM, Social.FORUM));
			projectFields.add(new SocialFieldModel(SOCIAL_BLOG, Social.WORDPRESS));
		}

		return projectFields;
	}

	@Override
	public LayoutEditorModel getProjectLayoutEditor(IBaseEditorModel model, boolean readOnly) {

		LayoutEditorModel lp = new LayoutEditorModel(model, LocaleStore.TYPE, LocaleStore.NAME, LocaleStore.TITLE, LocaleStore.DESCRIPTION, LocaleStore.LICENSE, LocaleStore.PROVIDERS,
				LocaleStore.COMMENT_PROVIDERS);
		lp.addField(AUTHOR_TITLE, 0);
		if (!readOnly) {
			lp.addChildLayout(new LayoutEditorModel(LocaleStore.CODE, model, CURRENT_VERSION_NAME, CURRENT_VERSION_NOTES, CODE_REPOSITORY, CODE_URL_REPOSITORY, CODE_DOC, CODE_JAVADOC, CODE_METRICS));
			lp.addChildLayout(new LayoutEditorModel(LocaleStore.SOCIAL, model, SOCIAL_TWITTER, SOCIAL_LINKEDIN, SOCIAL_FACEBOOK, SOCIAL_GOOGLEPLUS, SOCIAL_EMAIL, SOCIAL_WEB, SOCIAL_FORUM, SOCIAL_BLOG));
		}
		return lp;
	}

	@Override
	public synchronized Collection<FieldModel> getCommentFields() {

		if (commentFields != null) {
			return commentFields;
		}

		commentFields = new ArrayList<FieldModel>();

		commentFields.add(new TextAreaFieldModel(LocaleStore.COMMENT).setRequired(true));
		commentFields.add(new RatingFieldModel(LocaleRating.RATING).setRequired(true));
		commentFields.add(new DateFieldModel(LocaleStore.DATE).setReadOnly(true));
		commentFields.add(new TextFieldModel(AUTHOR_TITLE, LocaleStore.AUTHOR).setReadOnly(true));

		return commentFields;
	}

	@Override
	public Collection<FieldModel> getProviderFields() {
		return getCommonFields();
	}

	@Override
	public Collection<FieldModel> getNewsFields() {
		return getCommonFields();
	}

	@Override
	public Collection<FieldModel> getLicenseFields() {
		return getCommonFields();
	}

	@Override
	public Collection<FieldModel> getRepositoryFields() {
		return getCommonFields();
	}

	@Override
	public Collection<FieldModel> getCommonFields() {

		List<FieldModel> fields = new ArrayList<FieldModel>();
		fields.add(new TextFieldModel(LocaleStore.NAME).setRequired(true).setMaxSize(100));
		fields.add(new TextFieldModel(LocaleStore.TITLE).setRequired(true));
		fields.add(new TextFieldModel(LocaleStore.DESCRIPTION));
		return fields;
	}

	@Override
	public synchronized Collection<FieldModel> getScreenShotFields() {

		if (screenShotFields != null) {
			return screenShotFields;
		}

		screenShotFields = new ArrayList<FieldModel>();

		screenShotFields.add(new TextFieldModel(LocaleStore.TITLE).setMaxSize(200).setRequired(true));
		screenShotFields.add(new TextFieldModel(LocaleStore.DESCRIPTION));
		screenShotFields.add(new ImageFieldModel(LocaleStore.IMAGE).setRequired(true));

		return screenShotFields;
	}

}

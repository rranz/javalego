package com.javalego.store.ui.login;

import com.javalego.exception.CommonErrors;
import com.javalego.exception.LocalizedException;
import com.javalego.model.locales.LocaleEditor;
import com.javalego.model.locales.LocaleSecurity;
import com.javalego.security.model.Profile;
import com.javalego.ui.UIContext;
import com.javalego.ui.editor.data.bean.IDataBean;
import com.javalego.ui.editor.data.impl.DataBean;
import com.javalego.ui.field.impl.EmailFieldModel;
import com.javalego.ui.field.impl.ImageFieldModel;
import com.javalego.ui.field.impl.PasswordFieldModel;
import com.javalego.ui.field.impl.PhoneNumberFieldModel;
import com.javalego.ui.mvp.editor.beans.IBeansEditorModel;
import com.javalego.ui.mvp.editor.impl.BaseEditorPresenter.EditorListener;
import com.javalego.ui.mvp.editor.impl.EditorModel;
import com.javalego.ui.mvp.editor.impl.EditorPresenter;
import com.javalego.ui.mvp.editor.layout.LayoutEditorModel;
import com.javalego.ui.mvp.register.IRegisterView;
import com.javalego.ui.vaadin.component.util.Html;
import com.javalego.ui.vaadin.view.editor.EditorView;
import com.vaadin.ui.VerticalLayout;

/**
 * Registro de usuarios
 * 
 * @author ROBERTO RANZ
 *
 */
public class RegisterView extends VerticalLayout implements IRegisterView {

	private static final long serialVersionUID = -8438248648656582901L;

	private RegisterViewListener listener;

	@Override
	public void load() throws LocalizedException {

		setSpacing(true);

		addComponent(new Html.H2(UIContext.getText(LocaleSecurity.CREATE_ACCOUNT)).colored());

		// MODEL
		EditorModel model = new EditorModel();

		// Bean Profile
		IDataBean<Profile> databean = new DataBean<Profile>(listener.getModel());
		databean.addField(LocaleEditor.USER, true).getFieldModel().setMinSize(5);
		databean.addField(LocaleEditor.FIRSTNAME, true);
		databean.addField(LocaleEditor.LASTNAME, true);
		databean.addFields(new EmailFieldModel(LocaleEditor.EMAIL, true), new PhoneNumberFieldModel(LocaleEditor.PHONE), new ImageFieldModel(LocaleEditor.PHOTO));
		model.add(databean);

		// Campos de contraseña, independientes del bean profile.
		model.add(new PasswordFieldModel(LocaleEditor.PASSWORD, true), new String(""));
		model.add(new PasswordFieldModel(LocaleEditor.PASSWORD_AGAIN, true), new String(""));

		LayoutEditorModel layout = new LayoutEditorModel(model, LocaleEditor.USER, LocaleEditor.PASSWORD, LocaleEditor.PASSWORD_AGAIN);
		layout.addChildLayout(LocaleEditor.PERSONAL_DATA, LocaleEditor.FIRSTNAME, LocaleEditor.LASTNAME, LocaleEditor.EMAIL, LocaleEditor.PHOTO);

		// VIEW
		EditorView view = new EditorView().fullWidth().light().setToolbar(true);
		
		// PRESENTER
		final EditorPresenter presenter = new EditorPresenter(model, view, false, false);
		presenter.setLayoutEditorModel(layout);
		presenter.load();
		
		// Eventos del editor para registrar o descartar registro.
		presenter.setEditorListener(new EditorListener() {
			
			@Override
			public void remove() throws LocalizedException {
			}

			@Override
			public void loadDetailBeanEditor(IBeansEditorModel<?> detail) {
			}

			@Override
			public void discard() throws LocalizedException {
				listener.discard();
			}

			@Override
			public void commit() throws LocalizedException {
				
				if (!presenter.getValue(LocaleEditor.PASSWORD).equals(presenter.getValue(LocaleEditor.PASSWORD_AGAIN))) {
					throw UIContext.getException(CommonErrors.DISTINCT_PASSWORDS);
				}
				
				listener.register(String.valueOf(presenter.getValue(LocaleEditor.USER)), String.valueOf(presenter.getValue(LocaleEditor.PASSWORD)));
			}
		});
		
		addComponent(view);
	}

	@Override
	public void setListener(RegisterViewListener listener) {
		this.listener = listener;
	}

}

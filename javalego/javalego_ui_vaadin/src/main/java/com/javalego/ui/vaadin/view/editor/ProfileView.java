package com.javalego.ui.vaadin.view.editor;

import com.javalego.exception.CommonErrors;
import com.javalego.exception.LocalizedException;
import com.javalego.model.locales.LocaleEditor;
import com.javalego.security.SecurityContext;
import com.javalego.security.model.Profile;
import com.javalego.ui.UIContext;
import com.javalego.ui.editor.data.bean.IDataBean;
import com.javalego.ui.editor.data.impl.DataBean;
import com.javalego.ui.field.impl.EmailFieldModel;
import com.javalego.ui.field.impl.ImageFieldModel;
import com.javalego.ui.field.impl.PhoneNumberFieldModel;
import com.javalego.ui.mvp.editor.impl.EditorModel;
import com.javalego.ui.mvp.editor.impl.EditorPresenter;
import com.javalego.ui.mvp.login.LoginPresenter.AutenticatedListener;
import com.javalego.ui.mvp.profile.IProfileView;
import com.javalego.ui.mvp.profile.ProfilePresenter;
import com.javalego.ui.vaadin.component.button.ButtonExt;
import com.javalego.ui.vaadin.component.util.MessagesUtil;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.Reindeer;

/**
 * Mostrar los datos del perfil de usuario.
 * 
 * @author ROBERTO RANZ
 *
 */
public class ProfileView extends CustomComponent implements IProfileView {

	private static final long serialVersionUID = -7887603648433217699L;

	private IPerfilViewListener listener;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void load() throws LocalizedException {

		Profile profile = listener.getModel();

		if (profile == null) {
			throw UIContext.getException(CommonErrors.NO_PROFILE_DATA);
		}

		EditorModel model = new EditorModel();

		IDataBean databean = new DataBean(profile);
		databean.addFields(LocaleEditor.USER, LocaleEditor.COUNTRY, LocaleEditor.FIRSTNAME, LocaleEditor.LASTNAME);
		databean.addFields(new EmailFieldModel(LocaleEditor.EMAIL), new PhoneNumberFieldModel(LocaleEditor.PHONE), new ImageFieldModel(LocaleEditor.PHOTO));

		model.add(databean);

		EditorView view = new EditorView().light().setToolbar(false);

		EditorPresenter p = new EditorPresenter(model, view, true, false);
		p.load();

		setCompositionRoot(view);
	}

	@Override
	public void setListener(IPerfilViewListener listener) {
		this.listener = listener;
	}

	/**
	 * Editar en ventana los datos del perfil de usuario
	 * 
	 * @param event
	 * @param listener
	 *            Evento que se ejecuta al pulsar el botón de logout
	 */
	public static void dialog(LayoutClickEvent event, final AutenticatedListener listener) {

		final Window window = new Window(UIContext.getText(LocaleEditor.PROFILE));

		ProfilePresenter p = new ProfilePresenter(SecurityContext.getCurrent().getUserSession().getProfile(), new ProfileView());
		try {
			p.load();

			VerticalLayout v = new VerticalLayout();
			v.setMargin(true);

			v.addComponent((Component) p.getView());

			// Logout
			final ButtonExt btn = new ButtonExt(LocaleEditor.LOGOUT).primaryColor();
			btn.setClickShortcut(ShortcutAction.KeyCode.ENTER);
			btn.setDisableOnClick(true);
			btn.addClickListener(new ClickListener() {
				private static final long serialVersionUID = 4702231845658497509L;

				@Override
				public void buttonClick(ClickEvent event) {
					btn.setEnabled(true);
					if (listener != null) {
						listener.valid();
					}
					window.close();
				}
			});

			v.addComponent(btn);

			window.setContent(v);
			window.setWidth("340px");
			window.setStyleName(Reindeer.WINDOW_LIGHT);
			window.setClosable(true);
			window.setResizable(false);
			window.setDraggable(false);
			if (event != null) {
				window.setPositionX(event.getClientX() - event.getRelativeX() - 260);
				window.setPositionY((event.getClientY() - event.getRelativeY()) + 40);
			}
			else {
				window.setModal(true);
				window.center();
			}
			window.setCloseShortcut(KeyCode.ESCAPE, null);

			window.setVisible(true);

			UI.getCurrent().removeWindow(window);
			UI.getCurrent().addWindow(window);
		}
		catch (LocalizedException e) {
			MessagesUtil.error(e);
		}
	}

}

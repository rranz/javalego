package com.javalego.ui.mvp.register;

import com.javalego.exception.LocalizedException;
import com.javalego.security.model.Profile;
import com.javalego.ui.mvp.register.IRegisterView.RegisterViewListener;
import com.javalego.ui.patterns.IPresenter;
import com.javalego.ui.patterns.IService;

/**
 * Presenter de registro de usuarios
 * 
 * @author ROBERTO RANZ
 */
public class RegisterPresenter implements RegisterViewListener, IPresenter {

	private IRegisterServices services;

	private IRegisterView view;

	private RegisterListener listener;

	/**
	 * Constructor
	 * 
	 * @param controller
	 * @param services
	 * @param view
	 */
	public RegisterPresenter(IRegisterServices services, IRegisterView view) {
		this.services = services;
		this.view = view;
		view.setListener(this);
	}

	@Override
	public IRegisterView getView() {
		return view;
	}

	public IService getServices() {
		return services;
	}

	@Override
	public void load() throws LocalizedException {
		view.load();
	}

	/**
	 * Eventos de validación de las credenciales de usuario.
	 * 
	 * @author ROBERTO RANZ
	 */
	public interface RegisterListener {
		/**
		 * Usuario registrado
		 * 
		 * @return
		 */
		void registered();

		/**
		 * Descartar proceso de registro
		 */
		void discard();
	}

	/**
	 * Eventos de validación de las credenciales de usuario.
	 * 
	 * @return
	 */
	public RegisterListener getListener() {
		return listener;
	}

	/**
	 * Eventos de validación de las credenciales de usuario.
	 * 
	 * @param listener
	 */
	public void setListener(RegisterListener listener) {
		this.listener = listener;
	}

	@Override
	public void register(String userName, String password) throws LocalizedException {

		services.register(userName, password);

		if (listener != null) {
			listener.registered();
		}
	}

	@Override
	public Profile getModel() {

		return services.getModel();
	}

	@Override
	public void discard() {

		if (listener != null) {
			listener.discard();
		}
	}

}

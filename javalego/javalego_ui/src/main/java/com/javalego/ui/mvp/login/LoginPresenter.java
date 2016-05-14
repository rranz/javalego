package com.javalego.ui.mvp.login;

import com.javalego.exception.LocalizedException;
import com.javalego.ui.mvp.login.ILoginView.LoginViewListener;
import com.javalego.ui.mvp.register.IRegisterView;
import com.javalego.ui.mvp.register.RegisterPresenter;
import com.javalego.ui.patterns.IPresenter;

/**
 * Presenter del login de usuario
 * 
 * @author ROBERTO RANZ
 */
public class LoginPresenter implements LoginViewListener, IPresenter {

	private ILoginServices services;

	private ILoginView view;

	private AutenticatedListener listener;

	/**
	 * Registro de usuarios
	 */
	private RegisterPresenter registerPresenter;

	/**
	 * Constructor
	 * 
	 * @param controller
	 * @param services
	 * @param view
	 */
	public LoginPresenter(ILoginServices services, ILoginView view) {
		this.services = services;
		this.view = view;
		view.setListener(this);
	}

	@Override
	public void login(String user, String password) throws LocalizedException {

		services.login(user, password);

		if (listener != null) {
			listener.valid();
		}
	}

	@Override
	public ILoginView getView() {
		return view;
	}

	public ILoginServices getServices() {
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
	public interface AutenticatedListener {
		/**
		 * Validación correcta.
		 * 
		 * @return
		 */
		void valid();
		
		/**
		 * Mostrar ventana de registro de usuarios.
		 */
		void register(IRegisterView view);
	}

	/**
	 * Eventos de validación de las credenciales de usuario.
	 * 
	 * @return
	 */
	public AutenticatedListener getListener() {
		return listener;
	}

	/**
	 * Eventos de validación de las credenciales de usuario.
	 * 
	 * @param listener
	 */
	public void setListener(AutenticatedListener listener) {
		this.listener = listener;
	}

	@Override
	public boolean hasRegister() {
		return registerPresenter != null;
	}
	
	/**
	 * Incluir opción de registro de usuarios.
	 * 
	 * @param register
	 */
	public LoginPresenter setRegister(RegisterPresenter registerPresenter) {
		this.registerPresenter = registerPresenter;
		return this;
	}

	/**
	 * Registro de usuarios
	 * @return
	 */
	public RegisterPresenter getRegisterPresenter() {
		return registerPresenter;
	}

	/**
	 * Registro de usuarios
	 * @param registerPresenter
	 */
	public void setRegisterPresenter(RegisterPresenter registerPresenter) {
		this.registerPresenter = registerPresenter;
	}

	@Override
	public void register() throws LocalizedException {
		listener.register(registerPresenter.getView());
	}
}

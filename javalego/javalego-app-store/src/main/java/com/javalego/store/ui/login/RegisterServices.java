package com.javalego.store.ui.login;

import java.util.Date;

import org.apache.shiro.crypto.hash.Sha256Hash;

import com.javalego.exception.CommonErrors;
import com.javalego.exception.LocalizedException;
import com.javalego.security.SecurityContext;
import com.javalego.security.model.Profile;
import com.javalego.store.items.impl.Developer;
import com.javalego.store.items.impl.User;
import com.javalego.store.ui.StoreAppContext;
import com.javalego.ui.UIContext;
import com.javalego.ui.mvp.login.LoginServices;
import com.javalego.ui.mvp.profile.impl.BasicProfile;
import com.javalego.ui.mvp.register.IRegisterServices;

/**
 * Servicios de registro de usuarios
 * 
 * @author ROBERTO RANZ
 *
 */
public class RegisterServices implements IRegisterServices {

	private BasicProfile profile = new BasicProfile();

	/**
	 * Registrar usuario con los datos de nombre y contraseña.
	 * 
	 * @param userName
	 * @param password
	 * @throws LocalizedException
	 */
	@Override
	public void register(String userName, String password) throws LocalizedException {

		// Validar datos del modelo
		validate();

		LoginServices ls = new LoginServices();
		
		// Si ya existe, no se genera excepción y sí que debe generarse
		// excepción en el registro ya que el usuario ya existe.
		if (ls.exist(userName)) {
			throw UIContext.getException(CommonErrors.USER_EXISTS);
		}
		else {
			// Añadir miembro a la tienda
			addMember(password);
		}
	}

	/**
	 * Añadir miembro a la tienda.
	 * @throws LocalizedException 
	 */
	private void addMember(String password) throws LocalizedException {

		String hash = new Sha256Hash(password).toBase64();
		
		BasicProfile p = (BasicProfile) getModel();
		p.setPassword(hash);

		// Crear miembro con los datos del perfil actual editados en el registro.
		Developer member = new Developer(p.getUser(), p.getFirstname(), p.getLastname(), new Date());
		member.setEmail(p.getEmail());
		member.setPhone(p.getPhone());
		member.setPhoto(p.getPhoto());

		// Añadir usuario al servicio de gestión de usuarios de la aplicación.
		StoreAppContext context = StoreAppContext.getCurrent();
		
		SecurityContext.getCurrent().getUserServices().addUser(new User(p.getUser(), hash));
		
		// Realizar el login para validar que el usuario se ha registrado correctamente en el Realm.
		SecurityContext.getCurrent().getServices().login(p.getUser(), password);
		
		// Añadir a la tienda el nuevo usuario registrado.
		context.getDataServices().addMember(member);

		// Establecer el profile de la sesión de usuario para poder visualizar sus datos en la opción del menú principal.
		SecurityContext.getCurrent().getUserSession().setProfile(p);
	}

	/**
	 * Validar los datos del modelo o profile.
	 */
	private void validate() {

		Profile profile = getModel();

		if (profile != null) {
		}
	}

	/**
	 * Datos del perfil de usuario
	 * 
	 * @return
	 */
	@Override
	public Profile getModel() {
		return profile;
	}
}

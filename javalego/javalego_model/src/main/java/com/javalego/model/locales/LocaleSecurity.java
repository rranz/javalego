package com.javalego.model.locales;

import com.javalego.model.keys.Key;
import com.javalego.model.resources.locale.Languages;
import com.javalego.model.resources.locale.Locale;

/**
 * Códigos de textos relacionados con la seguridad.
 *  
 * @author ROBERTO RANZ
 * 
 */
public enum LocaleSecurity implements Key {

	@Languages(locales = { 
			@Locale(value = "Acceso"),
			@Locale(locale = "en", value = "Login")
			})
	LOGIN,

	@Languages(locales = { 
			@Locale(value = "Registrarse"),
			@Locale(locale = "en", value = "Register")
			})
	REGISTER,

	@Languages(locales = { 
			@Locale(value = "Crear cuenta"),
			@Locale(locale = "en", value = "Create account")
			})
	CREATE_ACCOUNT,

	@Languages(locales = { 
			@Locale(value = "Añadir"),
			@Locale(locale = "en", value = "New")
			})
	INSERT,

	@Languages(locales = { 
			@Locale(value = "Aceptar"),
			@Locale(locale = "en", value = "OK")
			})
	OK,

	@Languages(locales = { 
			@Locale(value = "Cancelar"),
			@Locale(locale = "en", value = "Cancel")
			})
	CANCEL,

	@Languages(locales = { 
			@Locale(value = "Domicilio habitual"),
			@Locale(locale = "en", value = "Address city")
			})
	ADDRESS, 
	
	@Languages(locales = { 
			@Locale(value = "Usuario"),
			@Locale(locale = "en", value = "User")
			})
	USER,

	@Languages(locales = { 
			@Locale(value = "Contraseña"),
			@Locale(locale = "en", value = "Password")
			})
	PASSWORD,

	@Languages(locales = { 
			@Locale(value = "Entrar"),
			@Locale(locale = "en", value = "Sign in")
			})
	SIGN_IN, 
	
	@Languages(locales = { 
			@Locale(value = "Usuario o contraseña incorrecta."),
			@Locale(locale = "en", value = "Invalid username or password.")
			})
	INVALID_LOGIN,

	@Languages(locales = { 
			@Locale(value = "Datos correctos. Acceso concedido."),
			@Locale(locale = "en", value = "OK access system.")
			})
	LOGIN_OK,

	@Languages(locales = { 
			@Locale(value = "La cuenta del usuario {0} está bloqueada."),
			@Locale(locale = "en", value = "The account for username {0} is locked.")
			})
	LOCKED_ACCOUNT,

	@Languages(locales = { 
			@Locale(value = "La contraseña para la cuenta de {0} es incorrecta."),
			@Locale(locale = "en", value = "Password for account {0} was incorrect!.")
			})
	INCORRECT_CREDENTIALS,

	@Languages(locales = { 
			@Locale(value = "No existe el usuario {0}."),
			@Locale(locale = "en", value = "There is no user with username of {0}.")
			})
	UNKNOWN_ACCOUNT,

	@Languages(locales = { 
			@Locale(value = "Perfil de usuario"),
			@Locale(locale = "en", value = "Profile")
			})
	PROFILE,	
}

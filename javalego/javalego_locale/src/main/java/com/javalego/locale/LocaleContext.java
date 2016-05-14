package com.javalego.locale;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import com.javalego.exception.LocalizedException;
import com.javalego.icons.RepositoryIcons;
import com.javalego.icons.ResourceRepositoryIcons;
import com.javalego.locale.translator.Translator;
import com.javalego.locale.translator.TranslatorFactory;
import com.javalego.model.BaseModel;
import com.javalego.model.context.Context;
import com.javalego.model.keys.ErrorKey;
import com.javalego.model.keys.Icon;
import com.javalego.model.keys.Key;
import com.javalego.model.resources.Language;

/**
 * Contexto de localización de aplicaciones.
 * 
 * Soporta las siguientes funcionalidades relativas a la localización de
 * aplicaciones: 1. Traducción de textos. 2. Excepciones 3. Iconos o imágenes
 * 
 * <p>
 * Por defecto se activa la traductor basado en enumerados y anotaciones de
 * localización. Método {@link #setTranslatorDefault()}.
 * 
 * <p>
 * Puede usar el método de archivos de paquetes de recursos con el método
 * {@link #setTranslatorResources(boolean, String...)}.
 * 
 * <p>
 * Puede definir sus propios métodos de localización con el método
 * {@link #setTranslator(Translator)}
 * 
 * @author ROBERTO RANZ
 *
 */
public class LocaleContext implements Context {

	private LocaleContext() {
	};

	/**
	 * Contexto actual
	 */
	private static LocaleContext current;

	/**
	 * Traductor activo para el contexto actual
	 */
	private Translator translator;

	/**
	 * Lista de repositorios
	 */
	private Collection<RepositoryIcons<Icon>> repositoriesIcons = new ArrayList<RepositoryIcons<Icon>>();

	/**
	 * Inicialización por defecto
	 */
	static {
		current = new LocaleContext();
		setTranslatorDefault();
	}

	/**
	 * Establecer el tipo de traductor usando para localizar aplicaciones.
	 * 
	 * @param translator
	 */
	public static void setTranslator(Translator translator) {
		current.translator = translator;
	}

	/**
	 * Establecer el traductor basado en archivos de recursos.
	 * 
	 * @param lowercase
	 *            Cambiar a minúsculas la clave de búsqueda.
	 * @param resources
	 *            Lista de archivos de paquetes de recursos
	 */
	public static void setTranslatorResources(boolean lowercase, String... resources) {

		current.translator = TranslatorFactory.getTranslatorResources(lowercase, resources);
	}

	/**
	 * Establecer el traductor basado en archivos de recursos.
	 * 
	 * @param resources
	 *            Lista de archivos de paquetes de recursos
	 */
	public static void setTranslatorResources(String... resources) {

		current.translator = TranslatorFactory.getTranslatorResources(true, resources);
	}

	/**
	 * Establecer el traductor basado en enumerados y anotaciones localizadas.
	 */
	public static void setTranslatorDefault() {

		current.translator = TranslatorFactory.getTranslatorDefault();
	}

	/**
	 * Obtiene la traducción de un enumerado que incluye anotaciones para
	 * obtener este texto en varios idiomas. Obtiene su valor del atributo value
	 * de la enumeración Locale.
	 * 
	 * @param code
	 * @param params
	 * @throws Exception
	 */
	public static String getText(Key code, Object... params) {

		return current.translator != null ? current.translator.getValue(code, getDefaultLocale(), params) : (code != null ? code.name() : "");
	}

	/**
	 * Obtiene la traducción de un enumerado que incluye anotaciones para
	 * obtener este texto en varios idiomas. Obtiene su valor del atributo value
	 * de la enumeración Locale.
	 * 
	 * @param code
	 * @param locale
	 * @param params
	 * @throws Exception
	 */
	public static String getText(Key code, Locale locale, Object... params) {

		return current.translator != null ? current.translator.getValue(code, locale, params) : (code != null ? code.name() : "");
	}

	/**
	 * Obtiene la traducción de un enumerado que incluye anotaciones para
	 * obtener este texto en varios idiomas. Obtiene su valor del atributo
	 * description de la enumeración Locale.
	 * 
	 * @param code
	 * @param locale
	 * @param params
	 * @throws Exception
	 */
	public static String getDescription(Key code, Locale locale, Object... params) {

		return current.translator != null ? current.translator.getDescription(code, locale, params) : (code != null ? code.name() : "");
	}

	/**
	 * Obtiene la traducción de un enumerado que incluye anotaciones para
	 * obtener este texto en varios idiomas. Obtiene su valor del atributo
	 * description de la enumeración Locale.
	 * 
	 * @param code
	 * @param params
	 * @throws Exception
	 */
	public static String getDescription(Key code, Object... params) {

		return current.translator != null ? current.translator.getDescription(code, getDefaultLocale(), params) : (code != null ? code.name() : "");
	}

	/**
	 * Obtiene la traducción del título de un objeto BaseModel.
	 * 
	 * @param baseModel
	 * @param locale
	 * @throws Exception
	 */
	public static String getTitle(BaseModel baseModel, Locale locale, Object... params) {

		return current.translator != null ? current.translator.getTitle(baseModel, locale, params) : (baseModel != null ? baseModel.getName() : "");
	}

	/**
	 * Obtiene la traducción de la descripción de un objeto BaseModel.
	 * 
	 * @param baseModel
	 * @param locale
	 * @throws Exception
	 */
	public static String getDescription(BaseModel baseModel, Locale locale, Object... params) {

		return current.translator != null ? current.translator.getDescription(baseModel, locale, params) : (baseModel != null ? baseModel.getName() : "");
	}

	/**
	 * Obtiene una excepción cuyo texto se obtiene de un enumerado (Key) que
	 * define dicho texto en varios idiomas.
	 * 
	 * @param exception
	 *            Excepción
	 * @param key
	 *            Texto de la excepción multiidioma
	 * @param locale
	 * @param params
	 *            Lista de parámetros a incluir en el texto (Ej.: Campo {0} y
	 *            campo {1} sin valor).
	 * @return
	 */
	public static LocalizedException getException(Exception exception, ErrorKey key, Locale locale, String... params) {

		return new LocalizedException(exception, locale, key, params);
	}

	/**
	 * Obtiene una excepción cuyo texto se obtiene de un enumerado (Key) que
	 * define dicho texto en varios idiomas.
	 * 
	 * @param exception
	 *            Excepción
	 * @param key
	 *            Texto de la excepción multiidioma
	 * @param params
	 *            Lista de parámetros a incluir en el texto (Ej.: Campo {0} y
	 *            campo {1} sin valor).
	 * @return
	 */
	public static LocalizedException getException(Exception exception, ErrorKey key, String... params) {

		return new LocalizedException(exception, null, key, params);
	}

	/**
	 * Obtiene una excepción cuyo texto se obtiene de un enumerado (Key) que
	 * define dicho texto en varios idiomas.
	 * 
	 * @param key
	 *            Texto de la excepción multiidioma
	 * @param locale
	 * @param params
	 *            Lista de parámetros a incluir en el texto (Ej.: Campo {0} y
	 *            campo {1} sin valor).
	 * @return
	 */
	public static LocalizedException getException(ErrorKey key, Locale locale, String... params) {

		return new LocalizedException(null, locale, key, params);
	}

	/**
	 * Obtiene una excepción cuyo texto se obtiene de un enumerado (Key) que
	 * define dicho texto en varios idiomas.
	 * 
	 * @param key
	 *            Texto de la excepción multiidioma
	 * @param params
	 *            Lista de parámetros a incluir en el texto (Ej.: Campo {0} y
	 *            campo {1} sin valor).
	 * @return
	 */
	public static LocalizedException getException(ErrorKey key, String... params) {

		return new LocalizedException(key, params);
	}

	/**
	 * Establecer el idioma por defecto.
	 * 
	 * @param defaultLanguage
	 */
	public static void setDefaultLanguage(Language defaultLanguage) {

		if (current.translator != null) {
			current.translator.setDefaultLanguage(defaultLanguage);
		}
	}

	/**
	 * Obtener el idioma por defecto.
	 * 
	 * @param defaultLanguage
	 */
	public static Language getDefaultLanguage() {

		return current.translator != null ? current.translator.getDefaultLanguage() : null;
	}

	/**
	 * Obtener el locale por defecto
	 * 
	 * @return
	 */
	public static Locale getDefaultLocale() {

		return current.translator != null ? current.translator.getDefaultLocale() : Locale.getDefault();
	}

	/**
	 * Contexto de localización de la aplicación.
	 * 
	 * @return
	 */
	public static LocaleContext getCurrent() {

		return current;
	}

	/**
	 * Añadir un repositorio de iconos al contexto de localización de
	 * aplicaciones
	 * 
	 * @param repositoryIcons
	 */
	public static void addRepositoryIcons(RepositoryIcons<Icon> repositoryIcons) {

		current.repositoriesIcons.add(repositoryIcons);
	}

	/**
	 * Obtener una imagen buscando por su código.
	 * 
	 * @param key
	 * @throws LocalizedException
	 */
	public static byte[] getIcon(String key) throws LocalizedException {

		return getIcon(key, null);
	}

	/**
	 * Obtener una imagen buscando por su código e idioma.
	 * 
	 * @param key
	 * @param locale
	 * @throws LocalizedException
	 */
	public static byte[] getIcon(String key, Locale locale) throws LocalizedException {

		if (key == null) {
			return null;
		}

		for (RepositoryIcons<?> repository : current.repositoriesIcons) {

			if (locale != null && repository.getLocale() != null && !locale.getLanguage().equals(repository.getLocale().getLanguage())) {
				continue;
			}

			return repository.getBytes(key);
		}
		return null;
	}

	/**
	 * Obtener un icono buscando por su código.
	 * 
	 * @param key
	 * @throws LocalizedException
	 */
	public static byte[] getIcon(Icon key) throws LocalizedException {

		return getIcon(key, null);
	}

	/**
	 * Comprobar si existe el icono buscando por su código.
	 * 
	 * @param key
	 * @throws LocalizedException
	 */
	public static boolean existIcon(Icon key) throws LocalizedException {

		return getIcon(key, null) != null;
	}

	/**
	 * Obtener una imagen buscando por su código.
	 * 
	 * @param key
	 * @throws LocalizedException
	 */
	public static byte[] getIcon(Icon key, Locale locale) throws LocalizedException {

		if (key == null) {
			return null;
		}
		else {
			return getIcon(key.getClass().getSimpleName() + "." + key.name());
		}
	}

	/**
	 * Comprueba si existe un icono con una clave.
	 * 
	 * @param key
	 * @throws LocalizedException
	 */
	public static boolean existIcon(String key) throws LocalizedException {

		if (key == null) {
			return false;
		}

		for (RepositoryIcons<?> repository : current.repositoriesIcons) {
			if (repository.exist(key)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Repositorios de iconos
	 * 
	 * @return
	 */
	public Collection<RepositoryIcons<Icon>> getRepositoriesIcons() {
		return repositoriesIcons;
	}

	/**
	 * Establecer los repositorios de iconos
	 * 
	 * @param repositoriesIcons
	 */
	public void setRepositoriesIcons(Collection<RepositoryIcons<Icon>> repositoriesIcons) {
		this.repositoriesIcons = repositoriesIcons;
	}

	/**
	 * Buscar un repositorio de iconos
	 * 
	 * @param name
	 * @throws LocalizedException
	 */
	public static RepositoryIcons<?> findRepositoryIcons(String name) {

		if (name == null) {
			return null;
		}

		for (RepositoryIcons<?> repository : current.repositoriesIcons) {
			if (name.equals(repository.getName())) {
				return repository;
			}
		}
		return null;
	}

	/**
	 * Buscar un repositorio de iconos buscando por uno de sus código de icono.
	 * 
	 * @param key
	 * @throws LocalizedException
	 */
	public static RepositoryIcons<?> findRepositoryIcons(Icon key) {

		if (key == null) {
			return null;
		}

		for (RepositoryIcons<?> repository : current.repositoriesIcons) {
			if (repository.exist(key.getClass().getSimpleName() + "." + key.name())) {
				return repository;
			}
		}
		return null;
	}

	/**
	 * Buscar un repositorio de iconos buscando por uno de sus código de icono.
	 * 
	 * @param key
	 * @param locale
	 * @throws LocalizedException
	 */
	public static RepositoryIcons<?> findRepositoryIcons(Icon key, Locale locale) {

		if (key == null) {
			return null;
		}

		for (RepositoryIcons<?> repository : current.repositoriesIcons) {
			if ((locale == null || (repository.getLocale() != null && repository.getLocale().getLanguage().equals(locale.getLanguage())))) {

				String name = repository instanceof ResourceRepositoryIcons ? key.getClass().getSimpleName() + "." + key.name() : key.name();

				if (repository.exist(name)) {
					return repository;
				}
			}
		}
		return null;
	}

	/**
	 * Comprueba si existen repositorios de iconos definidos en el contexto de
	 * localización
	 * 
	 * @return
	 */
	public static boolean hasRepositoriesIcons() {
		return current.getRepositoriesIcons() != null && current.getRepositoriesIcons().size() > 0;
	}

}

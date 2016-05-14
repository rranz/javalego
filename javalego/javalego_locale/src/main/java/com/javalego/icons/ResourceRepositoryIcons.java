package com.javalego.icons;

import java.util.Locale;

import com.javalego.exception.LocalizedException;

/**
 * Repositorio de iconos basado en recursos de imágenes accesibles por la
 * aplicación como archivos .png, .jpg, ... ubicados preferiblemente en el
 * directorio /src/main/resources.
 * 
 * <p>
 * Nota: es recomendable utilizar enumerados para buscar las imágenes por clave
 * y no textos.
 * 
 * <p>
 * La propiedad Locale permite establecer la localización de los iconos,
 * permitiéndonos establecer las imágenes específicas para cada sesión de
 * usuario.
 * 
 * <p>
 * Puede utilizar el método load() para cargar todas las imágenes de una carpeta
 * o recurso para que sean accesibles por la aplicación.
 * 
 * @author ROBERTO
 * 
 * @param <T>
 */
public interface ResourceRepositoryIcons<U> extends RepositoryIcons<U> {

	/**
	 * Añadir un recurso de imagen existente en una dirección web.
	 * 
	 * @param key
	 * @param url
	 * @throws LocalizedException
	 */
	public abstract void addUrl(String key, String url) throws LocalizedException;

	/**
	 * Añadir un recurso de imagen existente en una uri.
	 * 
	 * @param key
	 * @param uri
	 * @throws LocalizedException
	 */
	public abstract void addUri(String key, String uri) throws LocalizedException;

	/**
	 * Añadir un recurso de imagen
	 * 
	 * @param key
	 * @param resource
	 * @throws LocalizedException
	 */
	public abstract void addResource(String key, String resource) throws LocalizedException;

	/**
	 * Cargar una lista de iconos definida mediante una enumeración y cuyos
	 * recursos se encuentran en una ruta física.
	 * <p>
	 * La lista de nombres enumerados debe coincidir con los nombres de la lista
	 * de iconos existentes en el path, excluyendo la extensión.
	 * 
	 * @param enumIcons
	 * @param path
	 * @throws LocalizedException
	 */
	public abstract ResourceRepositoryIcons<U> load(Class<?> enumIcons, String path) throws LocalizedException;

	/**
	 * Cargar una lista de iconos definida mediante una enumeración y cuyos
	 * recursos se encuentran en una ruta física.
	 * <p>
	 * La lista de nombres enumerados debe coincidir con los nombres de la lista
	 * de iconos existentes en el path, excluyendo la extensión.
	 * 
	 * @param enumIcons
	 * @param locale
	 *            Localización de la lista
	 * @param path
	 * @throws LocalizedException
	 */
	public abstract ResourceRepositoryIcons<U> load(Class<?> enumIcons, Locale locale, String path) throws LocalizedException;

	/**
	 * Cargar una lista de iconos definida mediante una enumeración y cuyos
	 * recursos se encuentran en una ruta física.
	 * <p>
	 * La lista de nombres enumerados debe coincidir con los nombres de la lista
	 * de iconos existentes en el path, excluyendo la extensión.
	 * 
	 * @param name
	 * @param enumIcons
	 * @param locale
	 *            Localización de la lista
	 * @param path
	 * @throws LocalizedException
	 */
	public abstract ResourceRepositoryIcons<U> load(String name, Class<?> enumIcons, Locale locale, String path) throws LocalizedException;

	/**
	 * Cargar una lista de iconos definida mediante una enumeración y cuyos
	 * recursos se encuentran en una ruta física.
	 * <p>
	 * La lista de nombres enumerados debe coincidir con los nombres de la lista
	 * de iconos existentes en el path, excluyendo la extensión.
	 * 
	 * @param enumIcons
	 * @param path
	 * @param ext
	 *            filtro por extensiones
	 * @throws LocalizedException
	 */
	public abstract ResourceRepositoryIcons<U> load(Class<?> enumIcons, String path, String ext) throws LocalizedException;

}

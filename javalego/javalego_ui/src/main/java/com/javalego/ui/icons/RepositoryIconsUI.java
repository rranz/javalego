package com.javalego.ui.icons;

import com.javalego.exception.LocalizedException;
import com.javalego.icons.RepositoryIcons;

/**
 * Servicios de repositorio de Iconos accesible por las aplicaciones para su uso
 * en componentes de interface de usuario.
 * 
 * Nota: es recomendable utilizar enumerados para buscar las imágenes por clave
 * y no textos.
 * 
 * Puede utilizar el método load() para cargar todas las imágenes de una carpeta
 * o recurso para que sean accesibles por la aplicación.
 * 
 * @author ROBERTO
 * 
 * @param <T>
 */
public interface RepositoryIconsUI<T, U> extends RepositoryIcons<U> {

	/**
	 * Obtener el componente UI para mostrar el icono o imagen del repositorio.
	 * 
	 * @param caption
	 * @param key
	 * @return
	 * @throws LocalizedException
	 */
	public abstract T getComponent(String caption, U key) throws LocalizedException;

	/**
	 * Obtener el componente UI para mostrar el icono o imagen del repositorio.
	 * 
	 * @param caption
	 * @param description
	 * @param key
	 * @return
	 * @throws LocalizedException
	 */
	public abstract T getComponent(String caption, String description, U key) throws LocalizedException;

	/**
	 * Obtener el componente UI para mostrar el icono o imagen del repositorio.
	 * 
	 * @param key
	 *            Enumerado de donde se obtiene el nombre para localizar la
	 *            imagen.
	 * @return
	 * @throws LocalizedException
	 */
	public abstract T getComponent(U key) throws LocalizedException;

	/**
	 * Obtener el componente UI para mostrar el icono o imagen del repositorio.
	 * 
	 * @param key
	 *            Enumerado de donde se obtiene el nombre para localizar la
	 *            imagen.
	 * @param size
	 *            Tamaño del icono. Recomendado solo para imágenes vectoriales
	 *            (.svg).
	 * @return
	 * @throws LocalizedException
	 */
	public abstract T getComponent(U key, int size) throws LocalizedException;

}

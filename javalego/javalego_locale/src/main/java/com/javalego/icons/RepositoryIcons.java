package com.javalego.icons;

import java.util.Locale;

import com.javalego.exception.LocalizedException;
import com.javalego.model.BaseModel;

/**
 * Servicios de repositorio de Iconos accesible por las aplicaciones para su uso
 * en componentes de interface de usuario.
 * 
 * La propiedad Locale permite establecer la localización de los iconos,
 * permitiéndonos establecer las imágenes específicas para cada sesión de
 * usuario.
 * 
 * Nota: es recomendable utilizar enumerados para buscar las imágenes por clave
 * y no textos. Evitamos errores en la codificación y podemos refactorizar
 * nuestro código.
 * 
 * @author ROBERTO
 * 
 * @param <U>
 */
public interface RepositoryIcons<U> extends BaseModel {

	/**
	 * Obtiene el array de bytes de la image o icono
	 * 
	 * @param key
	 *            Clave del icono o imagen
	 * @return
	 * @throws LocalizedException
	 */
	public abstract byte[] getBytes(U key) throws LocalizedException;

	/**
	 * Verificar que existe la clave de icono en el respositorio.
	 * 
	 * @param key
	 * @return
	 */
	public abstract boolean exist(U key);

	/**
	 * Obtiene el array de bytes de la image o icono
	 * 
	 * @param key
	 *            Clave del icono o imagen
	 * @return
	 * @throws LocalizedException
	 */
	public abstract byte[] getBytes(String key) throws LocalizedException;

	/**
	 * Verificar que existe la clave de icono en el respositorio.
	 * 
	 * @param key
	 * @return
	 */
	public abstract boolean exist(String key);

	/**
	 * Lista de los nombres de los iconos existentes en el repositorio.
	 * 
	 * @return
	 */
	public abstract String[] getNames();

	/**
	 * Obtiene la localización a la que está vinculada la lista de iconos o
	 * imágenes del repositorio.
	 * 
	 * @return
	 */
	public abstract Locale getLocale();

}

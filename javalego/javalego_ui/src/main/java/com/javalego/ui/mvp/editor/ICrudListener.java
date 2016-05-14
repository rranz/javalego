package com.javalego.ui.mvp.editor;

import com.javalego.exception.LocalizedException;

/**
 * Interface que define los métodos básicos de commit, discard y remove básico de mantenimientos
 * 
 * @author ROBERTO RANZ
 */
public interface ICrudListener {
	
	/**
	 * Grabar los cambios realizados
	 * 
	 * @throws LocalizedException
	 */
	boolean commit() throws LocalizedException;
	
	/**
	 * Validar datos del editor. Podemos usar este método, previo al commit, si
	 * necesitamos realizar alguna tarea previa. Después puede realizar el
	 * commit con el parámetro validate = false ya que se ha realizado
	 * anteriormente.
	 */
	public boolean validate();	
	
	/**
	 * Descartar cambios realizados.
	 * 
	 * @throws LocalizedException
	 */
	boolean discard() throws LocalizedException;

	/**
	 * Eliminar
	 */
	boolean remove() throws LocalizedException;
	
	/**
	 * Comprueba si el editor es de solo lectura
	 * 
	 * @return
	 */
	boolean isReadOnly();

	/**
	 * Incluir la opción de borrado.
	 * 
	 * @return
	 */
	boolean hasRemove();	
}

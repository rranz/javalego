package com.javalego.icons.types;

/**
 * Definición básica de los datos de una imagen o icono
 * 
 * @author ROBERTO RANZ
 * 
 */
public interface IconItem {

	/**
	 * Nombre clave.
	 * 
	 * @return
	 */
	String getName();

	/**
	 * Datos en bytes de la imagen o icono.
	 * 
	 * @return
	 * @throws Exception
	 */
	byte[] getBytes() throws Exception;

}

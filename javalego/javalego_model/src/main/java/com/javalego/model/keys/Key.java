package com.javalego.model.keys;

/**
 * Nombre clave usado para definir propiedades del modelo y evitar textos
 * simples que puedan generar errores.
 * 
 * @author ROBERTO RANZ
 *
 */
public interface Key extends java.io.Serializable {

	/**
	 * Nombre de la clave
	 * 
	 * @return
	 */
	String name();

}

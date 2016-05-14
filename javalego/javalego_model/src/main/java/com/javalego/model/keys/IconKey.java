package com.javalego.model.keys;


/**
 * Nombre clave usado para definir propiedades del modelo y evitar textos simples que puedan generar errores.
 * 
 * Incluye además la posibilidad de definir un icono asociado al nombre.
 *  
 * @author ROBERTO RANZ
 *
 */
public interface IconKey extends Key {

	/**
	 * Icono asociado a la clave que se podrá usar, por ejemplo, para añadir un imagen al caption de un campo.
	 * @return
	 */
	Icon Icon(Key key);

}

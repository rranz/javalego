package com.javalego.ui.field.frontend;

/**
 * Modelo de datos de un campo que será usado en la aplicación cliente.
 * <p>
 * Su información debe ser simple ya que se transmitirá en formato json o xml
 * desde una servicio REST o SOA.
 * 
 * @author ROBERTO RANZ
 */
public interface FieldFrontEnd {

	/**
	 * Nombre del campo
	 * 
	 * @return
	 */
	public String getName();

	/**
	 * Etiqueta a mostra en el campo de edición.
	 * 
	 * @return
	 */
	public String getLabel();

	/**
	 * Descripción o tooltip a mostrar al usuario como información descriptiva
	 * del campo.
	 * 
	 * @return
	 */
	public String getDescription();

	/**
	 * Tipo de campo
	 * 
	 * @return
	 */
	public String getType();

	/**
	 * Requerido
	 * 
	 * @return
	 */
	public boolean isRequired();

	/**
	 * Visible
	 * 
	 * @return
	 */
	public boolean isVisible();

}

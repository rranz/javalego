package com.javalego.ui.field.impl;


/**
 * Propiedad que representa al acceso a un archivo ubicado en un path local o
 * remoto. Se utiliza la librería jcifs de java para acceder a archivos en
 * servidores remotos. (ver environment.xml donde se configuran los accesos y
 * servidores). definidas mediante propiedades.
 * 
 * @author ROBERTO RANZ
 */
public class FileFieldModel extends AbstractFieldModel {

	private static final long serialVersionUID = -3511897455835208517L;

	/**
	 * Nombre de la localización utilizada dentro de la gestión documental para
	 * gestionar los archivos dentro de los servidores disponibles y los
	 * protocolos de acceso y persistencia de los archivos.
	 */
	private String locationName;

	/**
	 * Nombre del campo que contiene el código del nombre de la carpeta
	 */
	private String folderFieldName;

	/**
	 * Nombre del campo que contiene el nombre del fichero (sin path).
	 */
	private String fileFieldName;

	/**
	 * Nombre del campo que representa la fecha de actualización del archivo.
	 */
	private String dateFieldName;

	/**
	 * Nombre del campo donde se almacena el tamaño del archivo.
	 */
	private String sizeFieldName;

	/**
	 * Validación realizada mediante sentencia sql para prevenir borrar un
	 * documento que pueda ser utilizado en otras tablas relacionadas y así
	 * prevenir el borrado del documento.
	 */
	private String validateBeforeDeleteSqlExpression;

	/**
	 * Mensaje de error o aviso que deseamos mostrar al usuario si se cumple la
	 * validación beforeDelete.
	 */
	private String validateBeforeDeleteMessage;

	public FileFieldModel() {
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getFolderFieldName() {
		return folderFieldName;
	}

	public void setFolderFieldName(String folderFieldName) {
		this.folderFieldName = folderFieldName;
	}

	public String getFileFieldName() {
		return fileFieldName;
	}

	public void setFileFieldName(String fileFieldName) {
		this.fileFieldName = fileFieldName;
	}

	public String getDateFieldName() {
		return dateFieldName;
	}

	public void setDateFieldName(String dateFieldName) {
		this.dateFieldName = dateFieldName;
	}

	public String getSizeFieldName() {
		return sizeFieldName;
	}

	public void setSizeFieldName(String sizeFieldName) {
		this.sizeFieldName = sizeFieldName;
	}

	public String getValidateBeforeDeleteSqlExpression() {
		return validateBeforeDeleteSqlExpression;
	}

	public void setValidateBeforeDeleteSqlExpression(String validateBeforeDeleteSqlExpression) {
		this.validateBeforeDeleteSqlExpression = validateBeforeDeleteSqlExpression;
	}

	public String getValidateBeforeDeleteMessage() {
		return validateBeforeDeleteMessage;
	}

	public void setValidateBeforeDeleteMessage(String validateBeforeDeleteMessage) {
		this.validateBeforeDeleteMessage = validateBeforeDeleteMessage;
	}

}

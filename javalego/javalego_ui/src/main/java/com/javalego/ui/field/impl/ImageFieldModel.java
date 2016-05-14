package com.javalego.ui.field.impl;

import com.javalego.model.keys.Key;
import com.javalego.util.StringUtils;

/**
 * Propiedad que representa un campo Blob con una serie de características de
 * edición definidas mediante propiedades.
 * 
 * @author ROBERTO RANZ
 */
public class ImageFieldModel extends AbstractFieldModel {

	private static final long serialVersionUID = 6008230083313232165L;

	/**
	 * Variable única que establece el tamaño máximo en KB's de los documentos
	 * por defecto.
	 */
	public static int MAX_SIZE_DOCUMENT = 1000;

	/**
	 * Nombre del documento sin path
	 */
	private String fieldName;

	/**
	 * Fecha original del documento en el momento de la captura.
	 */
	private String fieldDate;

	/**
	 * Campo de la vista que contiene el título que el usuario desea guardar
	 * como descripción del archivo almacenado.
	 */
	private String fieldTitle;

	/**
	 * Determina si el contenido del blob es una image para representarla en su
	 * componente visual apropiado.
	 */
	private boolean image;

	/**
	 * CMS que gestionará el almacenamiento del documento.
	 */
	private String cms;

	/**
	 * Campo de la vista que contiene la URI donde está almacenado el documento
	 * en el CMS.
	 */
	private String fieldUri;

	/**
	 * Número máximo del tamaño del archivo en KB. Por defecto 500KB.
	 */
	//private int maxSize = MAX_SIZE_DOCUMENT;

	public ImageFieldModel() {
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param title
	 * @param description
	 */
	public ImageFieldModel(String name, Key title, Key description) {
		super(name, title, description);
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 */
	public ImageFieldModel(Key name) {
		super(name);
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param title
	 */
	public ImageFieldModel(String name, Key title) {
		super(name, title);
	}

	public String getFieldDate() {
		return fieldDate;
	}

	public void setFieldDate(String fieldDate) {
		this.fieldDate = fieldDate;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldTitle() {
		return fieldTitle;
	}

	public void setFieldTitle(String fieldTitle) {
		this.fieldTitle = fieldTitle;
	}

	/**
	 * Mensaje a visualizar en el componente visual y que representa la
	 * información contenida en el documento.
	 * 
	 * @return
	 */
	public String getTitleDocument() {
		String value = StringUtils.isEmpty(fieldTitle) ? fieldName : fieldTitle;
		return value == null ? "" : value;
	}

	public boolean isImage() {
		return image;
	}

	public void setImage(boolean image) {
		this.image = image;
	}

	public String getCms() {
		return cms;
	}

	public void setCms(String cms) {
		this.cms = cms;
	}

	public String getFieldUri() {
		return fieldUri;
	}

	public void setFieldUri(String fieldUri) {
		this.fieldUri = fieldUri;
	}

	public boolean hasCMS() {
		return cms != null;
	}

}

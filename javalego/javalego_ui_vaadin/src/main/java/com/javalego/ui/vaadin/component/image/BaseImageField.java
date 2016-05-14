package com.javalego.ui.vaadin.component.image;

import java.util.Date;

import com.javalego.exception.LocalizedException;
import com.javalego.ui.field.impl.ImageFieldModel;
import com.javalego.ui.vaadin.component.UploadEvents;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;

/**
 * Componente que gestionar campos blob (imágenes y documentos)
 * 
 * @see ImageField
 * @author ROBERTO RANZ
 * 
 */
public abstract class BaseImageField extends CssLayout implements UploadEvents {

	private static final long serialVersionUID = -5948964893613243785L;

	protected ImmediateUpload upload;

	protected ImageFieldModel model;

	protected byte[] stream;

	private String fileName;

	private Date date;
	
	protected HorizontalLayout toolbar; 

	@Override
	protected String getCss(Component c) {
		return "margin-top: 8px; display: block;";
	}	
	
	/**
	 * Constructor
	 * 
	 * @param model
	 * @param editorFactory
	 */
	public BaseImageField() {

		toolbar = new HorizontalLayout();
		toolbar.setSpacing(true);
		addComponent(toolbar);
		
	}

	@Override
	public void focus() {
		super.focus();
	}

	/**
	 * Incluir botón de upload
	 */
	protected void includeUpload() {

		if (!model.isReadOnly()) {
			upload = new ImmediateUpload(this);
			toolbar.addComponent(upload);
		}
	}

	/**
	 * Obtener una nuevo documento o imagen.
	 */
	protected void remove() throws LocalizedException {

		stream = null;
		fileName = null;
		date = null;
	}

	@Override
	public void succeeded(byte[] data, String fileName) throws LocalizedException {

		this.stream = data;
		this.fileName = fileName;
		this.date = new Date();
	}

	@Override
	public byte[] getStream() throws LocalizedException {
		return stream;
	}

	@Override
	public String getFileName() {
		return fileName;
	}

	@Override
	public Date getDate() {
		return date;
	}
}

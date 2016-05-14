package com.javalego.msoffice.word;

import com.jacob.com.Dispatch;
import com.javalego.exception.JavaLegoException;

/**
 * Campos de formulario de un documento.
 * @author ROBERTO RANZ
 */
public class FormFields extends BasicElementDocument {

	private Document document;
	
	public FormFields(Document document) {
		this.document = document;
	}
	
	@Override
	protected String getInternalName() {
		return "FormFields";
	}

	@Override
	protected void setPointer() throws Exception {
		pointer = Dispatch.get(document.getPointer(),getInternalName()).toDispatch();
	}
	
	/**
	 * Buscar un Bookmark por el nombre definido en el documento.
	 * @param name
	 * @return
	 */
	public CheckBox getCheckBox(String name) throws Exception {

		int count = Dispatch.get(getPointer(), "Count").getInt();
		for(int i = 1; i <= count; i++) {
			Dispatch checkBox = Dispatch.call(pointer, "Item", i).toDispatch();
			if (checkBox != null && Dispatch.get(checkBox, "Name").toString().equals(name)) {
				Dispatch bookmark2 = Dispatch.get(checkBox, "CheckBox").toDispatch();
				CheckBox cb = new CheckBox(this, name);
				cb.setPointer(bookmark2);
				return cb;
			}
		}
		throw new JavaLegoException("El nombre del marcado '" + name + "' no existe.", JavaLegoException.ERROR);
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

}

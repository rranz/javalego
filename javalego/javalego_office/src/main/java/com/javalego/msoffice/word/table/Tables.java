package com.javalego.msoffice.word.table;

import com.jacob.com.Dispatch;
import com.javalego.exception.JavaLegoException;
import com.javalego.msoffice.word.BasicElementDocument;
import com.javalego.msoffice.word.Document;

public class Tables extends BasicElementDocument {
	
	private Document document;
	
	public Tables(Document document) {
		this.document = document;
	}
	
	@Override
	protected String getInternalName() {
		return "Tables";
	}

	@Override
	protected void setPointer() throws Exception {
		pointer = Dispatch.get(document.getPointer(), getInternalName()).toDispatch();
	}
	
	/**
	 * Recuperar una tabla de la lista.
	 * @param index
	 * @return
	 */
	public Table getTable(int index) throws Exception {
		try {
			Dispatch pointer = Dispatch.call(getPointer(), "Item", new Integer(index)).toDispatch();
			if (pointer != null) {
				 Table table = new Table();
				 table.setPointer(pointer);
				 return table;
			}
			else
				throw new JavaLegoException("El número de la tabla " + index + " no existe.", JavaLegoException.ERROR);
		}
		catch(Exception e) {
			throw new JavaLegoException("El número de la tabla " + index + " no existe.", JavaLegoException.ERROR);
		}
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

}

package com.javalego.msoffice.word;

import com.jacob.com.Dispatch;
import com.javalego.exception.JavaLegoException;

/**
 * Bookmarks de un documento.
 * @author ROBERTO RANZ
 */
public class Bookmarks extends BasicElementDocument {

	private Document document;
	
	public Bookmarks(Document document) {
		this.document = document;
	}
	
	@Override
	protected String getInternalName() {
		return "Bookmarks";
	}

	@Override
	protected void setPointer() throws Exception {
		pointer = document.getProperty(getInternalName()); 
		//pointer = (Dispatch)document.getPointer().g.get(getInternalName());
	}
	
	/**
	 * Buscar un Bookmark por el nombre definido en el documento.
	 * @param name
	 * @return
	 */
	public Bookmark getBookmark(String name) throws Exception {

		int count = Dispatch.get(getPointer(), "Count").getInt();
		for(int i = 1; i <= count; i++) {
			Dispatch bookmark = Dispatch.call(getPointer(),"Item", i).getDispatch();
			if (bookmark != null && Dispatch.get(bookmark, "Name").toString().equals(name)) {
				 Bookmark bm = new Bookmark(this, name);
				 bm.setPointer(bookmark);
				 return bm;
			}
		}
		throw new JavaLegoException("El nombre del marcador '" + name + "' no existe.", JavaLegoException.ERROR);
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

}

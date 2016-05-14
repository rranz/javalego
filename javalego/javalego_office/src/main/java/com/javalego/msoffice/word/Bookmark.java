package com.javalego.msoffice.word;

import com.jacob.com.Dispatch;

/**
 * Bookmark de un documento.
 * @author ROBERTO RANZ
 */
public class Bookmark extends BasicElementDocument {

	/**
	 * Nombre del bookmark
	 */
	private String name;
	
	private String text;
	
	private Bookmarks bookmarks;
	
	public Bookmark(Bookmarks bookmarks) {
		this.bookmarks = bookmarks;
	}

	public Bookmark(Bookmarks bookmarks, String name) throws Exception {
		this.bookmarks = bookmarks;
		setName(name);
	}

	/**
	 * Seleccionar marcador.
	 * @throws Exception
	 */
	public void select() throws Exception {
		if (pointer != null)
			Dispatch.call(pointer, "Select");
	}

	@Override
	protected String getInternalName() {
		return "Bookmark";
	}

	/**
	 * Obtener el texto del Bookmark
	 * @return
	 * @throws Exception
	 */
	public String getText() throws Exception {
		if (text == null) {
			Dispatch range = Dispatch.get(pointer, "Range").toDispatch(); Dispatch.call(pointer, "Range");
			text = Dispatch.get(range, "Text").toString();
		}
		return text;
	}

	/**
	 * Obtener el texto del Bookmark
	 * @return
	 * @throws Exception
	 */
	public void setText(String text) throws Exception {
		Dispatch range = Dispatch.get(pointer, "Range").toDispatch();
		Dispatch.put(range, "Text", text == null || text.indexOf("null") > -1 ? "" : text);
		this.text = text == null ? "" : text;
	}

	public Bookmarks getBookmarks() {
		return bookmarks;
	}

	public void setBookmarks(Bookmarks bookmarks) {
		this.bookmarks = bookmarks;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) throws Exception {
		setPointer();
		this.name = name;
	}

	@Override
	protected void setPointer() throws Exception {
		pointer = Dispatch.call(bookmarks.getPointer(), "Item", new Integer(3)).toDispatch();
	}

	public void setPointer(Dispatch pointer) {
		this.pointer = pointer;
	}

}

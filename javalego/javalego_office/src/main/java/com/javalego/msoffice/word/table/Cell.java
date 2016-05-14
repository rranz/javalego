package com.javalego.msoffice.word.table;

import com.jacob.com.Dispatch;
import com.javalego.msoffice.word.BasicElementDocument;

/**
 * Celda de una fila de una tabla.
 * @author ROBERTO RANZ
 */
public class Cell extends BasicElementDocument {

	private Row row;
	
	private String text;
	
	public Cell(Row row) {
		this.row = row;
	}

	@Override
	protected String getInternalName() {
		return "Cell";
	}

	@Override
	protected void setPointer() throws Exception {
		//pointer = (DispatchPtr)bookmarks.getPointer().invoke("Item", new Integer(3));
	}

	public void setPointer(Dispatch pointer) {
		this.pointer = pointer;
	}

	/**
	 * Seleccionar la celda.
	 * @throws Exception
	 */
	public void select() throws Exception {
		if (pointer != null)
			Dispatch.call(pointer,"Select");
	}
	
	public Row getRow() {
		return row;
	}

	public void setRow(Row row) {
		this.row = row;
	}

	/**
	 * Obtener el texto del Bookmark
	 * @return
	 * @throws Exception
	 */
	public String getText() throws Exception {
		
		if (text == null) {
			Dispatch range = Dispatch.get(pointer,"Range").toDispatch();
			text = Dispatch.get(range, "Text").getString();
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
		Dispatch.put(range,"Text", text == null ? "" : text);
		this.text = text;
	}

	/**
	 * Font de la celda.
	 * @return
	 */
	public Font getFont() throws Exception {
		
		Dispatch range = Dispatch.get(pointer,"Range").toDispatch();
		Dispatch pfont = Dispatch.get(range, "Font").toDispatch();
		Font font = new Font();
		font.setPointer(pfont);
		return font;
	}

}

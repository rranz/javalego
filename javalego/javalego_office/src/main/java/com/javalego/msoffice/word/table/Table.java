package com.javalego.msoffice.word.table;

import com.jacob.com.Dispatch;
import com.javalego.msoffice.word.BasicElementDocument;

/**
 * Tabla de un documento.
 * @author ROBERTO RANZ
 */
public class Table extends BasicElementDocument {

	@Override
	protected String getInternalName() {
		return "Table";
	}

	@Override
	protected void setPointer() throws Exception {
		//pointer = (DispatchPtr)bookmarks.getPointer().invoke("Item", new Integer(3));
	}

	public void setPointer(Dispatch pointer) {
		this.pointer = pointer;
	}

	/**
	 * Añadir una fila a la tabla.
	 * @return
	 * @throws Exception
	 */
	public Row addRow() throws Exception {
		
		Dispatch rows = Dispatch.get(getPointer(), "Rows").toDispatch();
		
		Dispatch row = Dispatch.call(rows, "Add").toDispatch();
		
		Row newRow = new Row(this);
		
		newRow.setPointer(row);
		
		return newRow;
	}

	/**
	 * Recuperar una fila.
	 * @param index
	 */
	public Row getRow(int index) throws Exception {
		
		Dispatch rows = Dispatch.get(getPointer(), "Rows").toDispatch();
		
		Dispatch row = Dispatch.call(rows, "Item", index).toDispatch();

		Row newRow = new Row(this);
		
		newRow.setPointer(row);
		
		return newRow;
	}

}

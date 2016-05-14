package com.javalego.msoffice.word.table;

import com.jacob.com.Dispatch;
import com.javalego.msoffice.word.BasicElementDocument;

/**
 * Fila de una tabla.
 * @author ROBERTO RANZ
 *
 */
public class Row extends BasicElementDocument {

	private Table table;
	
	public Row(Table table) {
		this.table = table;
	}

	@Override
	protected String getInternalName() {
		return "Row";
	}

	@Override
	protected void setPointer() throws Exception {
		//pointer = (DispatchPtr)bookmarks.getPointer().invoke("Item", new Integer(3));
	}

	public void setPointer(Dispatch pointer) {
		this.pointer = pointer;
	}

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}

	/**
	 * Recuperar una celda de una tabla.
	 * @param i
	 * @return
	 */
	public Cell getCell(int index) throws Exception {
		
		Dispatch cells = Dispatch.get(getPointer(), "Cells").toDispatch();
		
		Dispatch cell = Dispatch.call(cells, "Item", new Integer(index)).toDispatch();
		
		Cell newCell = new Cell(this);
		
		newCell.setPointer(cell);
		
		return newCell;
	}

}

package com.javalego.xls.report;

/**
 * Datos de configuración del posicionamiento de una celda de una hoja de Excel (POI).
 * @author ROBERTO RANZ
 */
public class DataCell {

	private String column;
	private int row;
	
	public DataCell(String column, int row) {
		this.column = column;
		this.row = row;
	}
	
	public DataCell(String column) {
		
		this.column = "";
		String row = "";
		for(byte b : column.getBytes()) {
			
			if (Character.isDigit(b))
				row += Character.toString((char)b);
			else
				this.column += Character.toString((char)b);
		}

		this.row = new Integer(row).intValue();
		
	}

	public static void main(String[] args) {
		
		System.out.println(new DataCell("AB344"));
		
	}

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}
	
	@Override
	public String toString() {
		return column + row;
	}
	
}

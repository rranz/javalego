package com.javalego.excel.jxl;

import com.javalego.excel.ExcelReader;

import jxl.BooleanCell;
import jxl.Cell;
import jxl.CellType;
import jxl.DateCell;
import jxl.LabelCell;
import jxl.NumberCell;
import jxl.Sheet;
import jxl.Workbook;

/**
 * Lector de ficheros excel xls (no xlsx) con api JExcelApi.
 * 
 * <br>
 * <br>
 * Dependencia Maven:<br>
 * <dependency> <groupId>net.sourceforge.jexcelapi</groupId>
 * <artifactId>jxl</artifactId> <version>2.6.12</version> </dependency>
 * 
 * @author ROBERTO RANZ
 * 
 */
public class ExcelReaderJxl implements ExcelReader {

	/**
	 * Workbook
	 */
	private Workbook workbook;

	/**
	 * Sheet activo dentro del workbook
	 */
	private Sheet sheet;

	/**
	 * Constructor
	 * 
	 * @param workbook
	 */
	public ExcelReaderJxl(Workbook workbook) {

		this.workbook = workbook;

		if (workbook != null) {
			sheet = workbook.getSheet(0);
		}
	}

	@Override
	public Object getValue(int row, int column) {
		Cell cell = sheet.getCell(column, row);
		return getValue(cell);
	}

	@Override
	public Object getValue(int row, String column) {
		Cell cell = sheet.getCell(ExcelUtil.getIndexColumn(column), row);
		return getValue(cell);
	}

	@Override
	public int getRows() {
		return sheet.getRows();
	}

	@Override
	public int getColumns() {
		return sheet.getColumns();
	}

	/**
	 * Obtener el valor de una celda.
	 * 
	 * @param cell
	 * @return
	 */
	private Object getValue(Cell cell) {

		if (cell.getType() == CellType.LABEL) {
			LabelCell lc = (LabelCell) cell;
			return lc.getString();

		} else if (cell.getType() == CellType.NUMBER) {
			NumberCell nc = (NumberCell) cell;
			return nc.getValue();

		} else if (cell.getType() == CellType.DATE) {
			DateCell dc = (DateCell) cell;
			return dc.getDate();

		} else if (cell.getType() == CellType.BOOLEAN) {
			BooleanCell dc = (BooleanCell) cell;
			return dc.getValue();
		} else {
			return null;
		}
	}

	@Override
	public void setActiveSheet(int index) {
		sheet = workbook.getSheet(index);
	}

	@Override
	public int getIndexColumn(int row, String name) {

		for(int i = 0; i < sheet.getColumns(); i++) {
			Object value = getValue(row, i);
			if (name.equals(value)) {
				return i; 
			}
		}
		return -1;
	}

	@Override
	public boolean hasWoorkbook() {
		return workbook != null && sheet != null;
	}

}

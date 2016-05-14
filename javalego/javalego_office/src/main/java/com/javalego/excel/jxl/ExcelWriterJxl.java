package com.javalego.excel.jxl;

import java.util.Date;

import jxl.Cell;
import jxl.CellView;
import jxl.format.CellFormat;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import com.javalego.excel.ExcelWriter;
import com.javalego.exception.LocalizedException;

/**
 * Escribir datos en ficheros excel xls (no xlsx) con api JExcelApi.
 * 
 * @author ROBERTO RANZ
 * 
 */
public class ExcelWriterJxl implements ExcelWriter {

	private WritableWorkbook workbook;

	private WritableSheet activeSheet;

	private WritableCellFormat boldCellFormat;

	/**
	 * Constructor
	 * 
	 * @param workbook
	 */
	public ExcelWriterJxl(WritableWorkbook workbook) {

		this.workbook = workbook;

		if (workbook != null) {
			activeSheet = workbook.createSheet("DataSheet", 0);
		}
	}

	/**
	 * Establecer el valor de una celda.
	 * 
	 * @param row
	 * @param colum
	 * @param value
	 * @return
	 */
	@Override
	public void setValue(int row, int column, Object value) throws LocalizedException {

		try {
			if (value instanceof java.lang.Number) {
				activeSheet.addCell(new jxl.write.Number(column, row, ((java.lang.Number) value).doubleValue()));

			} else if (value instanceof Date) {
				activeSheet.addCell(new jxl.write.DateTime(column, row, (Date) value));

			} else if (value instanceof java.lang.Boolean) {
				activeSheet.addCell(new jxl.write.Boolean(column, row, (java.lang.Boolean) value));

			} else if (value != null) {
				activeSheet.addCell(new Label(column, row, value.toString()));
			}
		} catch (Exception e) {
			throw new LocalizedException(e);
		}

	}

	/**
	 * Establecer el valor de una celda.
	 * 
	 * @param row
	 * @param colum
	 * @param value
	 * @param cellFormat
	 * 
	 * @return
	 */
	private void setValue(int row, int column, Object value, CellFormat cellFormat) throws Exception {

		if (value instanceof java.lang.Number) {
			activeSheet.addCell(new jxl.write.Number(column, row, ((java.lang.Number) value).doubleValue(), cellFormat));

		} else if (value instanceof Date) {
			activeSheet.addCell(new jxl.write.DateTime(column, row, (Date) value, cellFormat));

		} else if (value instanceof java.lang.Boolean) {
			activeSheet.addCell(new jxl.write.Boolean(column, row, (java.lang.Boolean) value, cellFormat));

		} else if (value != null) {
			activeSheet.addCell(new Label(column, row, value.toString(), cellFormat));
		}
	}

	@Override
	public void setActiveSheet(int index) {

		activeSheet = workbook.getSheet(index);
	}

	@Override
	public void write() throws LocalizedException {
		try {
			workbook.write();
			workbook.close();
		} catch (Exception e) {
			throw new LocalizedException(e);
		}
	}

	@Override
	public void setValueBold(int row, int column, Object value) throws LocalizedException {
		try {
			setValue(row, column, value, getFontBold());
		} catch (Exception e) {
			throw new LocalizedException(e);
		}
	}

	/**
	 * Formato bold aplicado a una celda.
	 * 
	 * @return
	 * @throws WriteException
	 */
	private WritableCellFormat getFontBold() throws WriteException {

		if (boldCellFormat == null) {
			WritableFont cellFont = new WritableFont(WritableFont.ARIAL, WritableFont.DEFAULT_POINT_SIZE);
			cellFont.setBoldStyle(WritableFont.BOLD);
			boldCellFormat = new WritableCellFormat(cellFont);
		}
		return boldCellFormat;

	}

	@Override
	public void setValueBold(int row, String column, Object value) throws LocalizedException {

		setValueBold(row, ExcelUtil.getIndexColumn(column), value);
	}

	@Override
	public void sheetAutoFitColumns() {

		WritableSheet sheet = activeSheet;
		for (int i = 0; i < sheet.getColumns(); i++) {
			Cell[] cells = sheet.getColumn(i);
			int longestStrLen = -1;

			if (cells.length == 0)
				continue;

			/* Find the widest cell in the column. */
			for (int j = 0; j < cells.length; j++) {
				if (cells[j].getContents().length() > longestStrLen) {
					String str = cells[j].getContents();
					if (str == null || str.isEmpty())
						continue;
					longestStrLen = str.trim().length();
				}
			}

			/* If not found, skip the column. */
			if (longestStrLen == -1)
				continue;

			/* If wider than the max width, crop width */
			if (longestStrLen > 255)
				longestStrLen = 255;

			CellView cv = sheet.getColumnView(i);
			//cv.setAutosize(true);
			cv.setSize((longestStrLen * 256) + 800); /* Every character is 256 units wide, so scale it. */
			sheet.setColumnView(i, cv);
		}
	}

	@Override
	public boolean hasWoorkbook() {
		return workbook != null;
	}
}

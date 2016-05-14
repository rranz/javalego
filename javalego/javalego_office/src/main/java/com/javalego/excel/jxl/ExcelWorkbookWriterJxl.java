package com.javalego.excel.jxl;

import java.io.File;
import java.io.OutputStream;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.WritableFont;
import jxl.write.WritableWorkbook;

import com.javalego.excel.ExcelWorkbookWriter;
import com.javalego.exception.LocalizedException;

/**
 * Crear fichero de excel usando la librería JExcelApi.
 * 
 * @author ROBERTO RANZ
 * 
 */
public class ExcelWorkbookWriterJxl implements ExcelWorkbookWriter {

	WritableFont cellFont = new WritableFont(WritableFont.COURIER, 16);

	private WorkbookSettings settings = new WorkbookSettings();

	private WritableWorkbook workbook;

	private ExcelWriterJxl writer;

	public ExcelWorkbookWriterJxl() {
		settings.setSuppressWarnings(true);
		// Se define con este encoding para que funcione correctamente la
		// lectura de datos en Android con UTF-8 por defecto.
		settings.setEncoding("Cp1252");
	}

	@Override
	public ExcelWorkbookWriterJxl setFile(String fileName) throws LocalizedException {
		return setFile(new File(fileName));
	}

	@Override
	public ExcelWorkbookWriterJxl setFile(OutputStream stream) throws LocalizedException {
		try {
			workbook = Workbook.createWorkbook(stream, settings);
			writer = new ExcelWriterJxl(workbook);
		}
		catch (Exception e) {
			throw new LocalizedException(e);
		}

		return this;
	}

	@Override
	public ExcelWorkbookWriterJxl setFile(File file) throws LocalizedException {
		try {
			workbook = Workbook.createWorkbook(file, settings);
			writer = new ExcelWriterJxl(workbook);
		}
		catch (Exception e) {
			throw new LocalizedException(e);
		}

		return this;
	}

	/**
	 * Obtiene el workbook de la hoja de cálculo.
	 * 
	 * @return
	 */
	public WritableWorkbook getWorkbook() {
		return workbook;
	}

	@Override
	public void setValue(int row, int column, Object value) throws LocalizedException {
		writer.setValue(row, column, value);
	}

	@Override
	public void setActiveSheet(int index) {
		writer.setActiveSheet(index);
	}

	@Override
	public void write() throws LocalizedException {
		writer.write();
	}

	@Override
	public void setValueBold(int row, int column, Object value) throws LocalizedException {
		writer.setValueBold(row, column, value);
	}

	@Override
	public void setValueBold(int row, String column, Object value) throws LocalizedException {
		writer.setValueBold(row, column, value);
	}

	@Override
	public void sheetAutoFitColumns() {
		writer.sheetAutoFitColumns();
	}

	@Override
	public boolean hasWoorkbook() {
		return workbook != null && writer != null;
	}

}

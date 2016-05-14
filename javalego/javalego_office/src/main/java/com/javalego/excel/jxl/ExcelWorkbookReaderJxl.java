package com.javalego.excel.jxl;

import java.io.File;
import java.io.InputStream;

import jxl.Workbook;
import jxl.WorkbookSettings;

import com.javalego.excel.ExcelWorkbookReader;
import com.javalego.exception.LocalizedException;

/**
 * Acceso a fichero de excel usando la librería JExcelApi.
 * 
 * @author ROBERTO RANZ
 *
 */
public class ExcelWorkbookReaderJxl implements ExcelWorkbookReader {

	private WorkbookSettings settings = new WorkbookSettings();

	private Workbook workbook;

	private ExcelReaderJxl reader;

	public ExcelWorkbookReaderJxl() {
		settings.setSuppressWarnings(true);
		// Se define con este encoding para que funcione correctamente la
		// lectura de datos en Android con UTF-8 por defecto.
		settings.setEncoding("Cp1252");
	}

	@Override
	public ExcelWorkbookReader loadFile(String fileName) throws LocalizedException {
		return loadFile(new File(fileName));
	}

	@Override
	public ExcelWorkbookReader loadFile(InputStream stream) throws LocalizedException {
		try {
			workbook = Workbook.getWorkbook(stream, settings);
		}
		catch (Exception e) {
			throw new LocalizedException(e);
		}
		init();
		return this;
	}

	@Override
	public ExcelWorkbookReader loadFile(File file) throws LocalizedException {
		try {
			workbook = Workbook.getWorkbook(file, settings);
		}
		catch (Exception e) {
			throw new LocalizedException(e);
		}
		init();
		return this;
	}

	private void init() {
		reader = new ExcelReaderJxl(workbook);
	}

	/**
	 * Obtiene el workbook de la hoja de cálculo.
	 * 
	 * @return
	 */
	public Workbook getWorkbook() {
		return workbook;
	}

	@Override
	public Object getValue(int row, int column) {
		return reader.getValue(row, column);
	}

	@Override
	public int getRows() {
		return reader.getRows();
	}

	@Override
	public int getColumns() {
		return reader.getColumns();
	}

	@Override
	public void setActiveSheet(int index) {
		reader.setActiveSheet(index);
	}

	@Override
	public Object getValue(int row, String column) {
		return reader.getValue(row, column);
	}

	@Override
	public int getIndexColumn(int row, String name) {
		return reader.getIndexColumn(row, name);
	}

	@Override
	public boolean hasWoorkbook() {
		return workbook != null;
	}

}

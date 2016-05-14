package com.javalego.excel.jxl;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

import com.javalego.excel.ExcelWorkbook;
import com.javalego.excel.ExcelWorkbookWriter;
import com.javalego.exception.LocalizedException;

/**
 * Librería de acceso a ficheros de excel para JExcelApi (jxl.jar). (lectura y escritura)
 * 
 * @author ROBERTO RANZ
 *
 */
public class ExcelWorkbookJxl implements ExcelWorkbook {

	private ExcelWorkbookWriterJxl writer = new ExcelWorkbookWriterJxl();
	
	private ExcelWorkbookReaderJxl reader = new ExcelWorkbookReaderJxl();
	
	@Override
	public ExcelWorkbook loadFile(String fileName) throws Exception {
		reader.loadFile(fileName);
		return this;
	}

	@Override
	public ExcelWorkbook loadFile(InputStream stream) throws Exception {
		reader.loadFile(stream);
		return this;
	}

	@Override
	public ExcelWorkbook loadFile(File file) throws LocalizedException {
		reader.loadFile(file);
		return this;
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
	public void setValue(int row, int column, Object value) throws LocalizedException {
		writer.setValue(row, column, value);
	}

	@Override
	public void write() throws LocalizedException {
		writer.write();
	}

	@Override
	public Object getValue(int row, String column) {
		return reader.getValue(row, column);
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
	public int getIndexColumn(int row, String name) {
		return reader.getIndexColumn(row, name);
	}

	@Override
	public boolean hasWoorkbook() {
		return reader.hasWoorkbook() || writer.hasWoorkbook();
	}

	@Override
	public ExcelWorkbook setFile(String fileName) throws LocalizedException {
		writer.setFile(fileName);
		return this;
	}

	@Override
	public ExcelWorkbookWriter setFile(File file) throws Exception {
		writer.setFile(file);
		return this;
	}

	@Override
	public ExcelWorkbookWriter setFile(OutputStream stream) throws Exception {
		writer.setFile(stream);
		return this;
	}

}

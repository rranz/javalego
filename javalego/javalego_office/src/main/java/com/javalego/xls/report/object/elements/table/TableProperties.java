package com.javalego.xls.report.object.elements.table;

import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;

import com.javalego.xls.report.ExcelWorkbook;
import com.javalego.xls.report.ReportVisualStyle;
import com.javalego.xls.report.object.ReportObject;
import com.javalego.xls.report.object.elements.GroupProperties;

/**
 * Agrupación de propiedades en un array multidimensional (filas y columnas)
 * @see RowTable
 * @see ColumnTable
 * @author ROBERTO RANZ
 */
public class TableProperties extends GroupProperties {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TableProperties(ReportObject report) {
		super(report);
	}

	/**
	 * Filas
	 */
	protected ArrayList<RowTable> rows = new ArrayList<RowTable>();

	/**
	 * Columnas
	 */
	protected ArrayList<ColumnTable> columns = new ArrayList<ColumnTable>();

	@Override
	public void generate() throws Exception {

		report.addRowIndex();
		//report.addColumnIndex();
		generateHeader();
		//report.setColumnIndex(report.getColumnIndex()-1);

		//report.addColumnIndex();
		report.addRowIndex();

		// Columnas
		int column = report.getColumnIndex();

		generateEmptyCell();

		// Columnas
		for(int i = 0; i < columns.size(); i++) {
			report.addColumnIndex();
			columns.get(i).generateHeader();
		}
		report.setColumnIndex(column);

		// Filas
		for(int i = 0; i < rows.size(); i++) {
			report.addRowIndex();
			rows.get(i).generate();
		}

		report.addColumnIndex();

	}

	/**
	 * Genera una celda en blanco para la intersección entre filas y columnas.
	 */
	private void generateEmptyCell() {
		
		ReportVisualStyle style = new ReportVisualStyle(report.getExcel());

		int columnIndex = report.getColumnIndex();

		ExcelWorkbook excel = report.getExcel();
		HSSFCell cell = excel.getCell(report.getRowIndex(), columnIndex);

		style.setAlignment(HSSFCellStyle.ALIGN_LEFT);

		// Estilo de celda con respecto al tipo de valor.
		style.setBox(true);
		if (style.getForeColor() < 0)
			style.setForeColor(HSSFColor.WHITE.index);
		if (style.getBackgroundColor() < 0)
			style.setBackgroundColor(HSSFColor.BLUE_GREY.index);

		if (style.getFontName() == null) style.setFontName(report.getFontNameDefault());

		style.setApplyStyle(cell);
	}

	/**
	 * Generar el título de la propiedad.
	 * @throws Exception
	 */
	private void generateHeader() throws Exception {

		int columnIndex = report.getColumnIndex();

		ExcelWorkbook excel = report.getExcel();
		HSSFCell cell = excel.getCell(report.getRowIndex(), columnIndex);

		excel.setCellValue(cell, title);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

		// Estilo de celda con respecto al tipo de valor.
		style.setBox(true);
		style.setFontBold(true);
		//style.setFontSize(11);
		if (style.getForeColor() < 0)
			style.setForeColor(HSSFColor.WHITE.index);
		if (style.getBackgroundColor() < 0)
			style.setBackgroundColor(HSSFColor.BLACK.index);

		if (style.getFontName() == null)
			style.setFontName(report.getFontNameDefault());

		cell = excel.getCell(report.getRowIndex(), columnIndex);
		style.setApplyStyle(cell);
		cell = excel.getCell(report.getRowIndex(), columnIndex+1);
		style.setApplyStyle(cell);

		report.getExcel().addMergeCells(report.getRowIndex(), columnIndex, report.getRowIndex(), columnIndex + columns.size());

		style.setApplyStyle(cell);
	}

	public ArrayList<ColumnTable> getColumns() {
		return columns;
	}

	public void setColumns(ArrayList<ColumnTable> columns) {
		this.columns = columns;
	}

	public ArrayList<RowTable> getRows() {
		return rows;
	}

	public void setRows(ArrayList<RowTable> rows) {
		this.rows = rows;
	}

}

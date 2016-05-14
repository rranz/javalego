package com.javalego.xls.report.object.elements.table;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;

import com.javalego.xls.report.ExcelWorkbook;
import com.javalego.xls.report.ReportVisualStyle;
import com.javalego.xls.report.object.ReportObject;
import com.javalego.xls.report.object.elements.BasicElement;

/**
 * Columna de una tabla
 * @see TableProperties
 * @see RowTable
 * @author ROBERTO RANZ
 */
public class ColumnTable extends BasicElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected int columnIndex;

	public ColumnTable(ReportObject report) {
		super(report);
	}

	@Override
	public void generate() throws Exception {

//		report.addRowIndex();
//
//		columnIndex = report.getColumnIndex();
//
//		if (!Functions.isBlank(title)) {
//			generateHeader();
//			report.addRowIndex();
//			report.addColumnIndex();
//		}
//
//		int column= report.getColumnIndex();
//
//		for(int i = 0; i < groupsProperties.size(); i++) {
//
//			report.setColumnIndex(column);
//
//			GroupProperties gp = groupsProperties.get(i);
//
//			gp.generate();
//
//			report.addRowIndex();
//		}

	}

	/**
	 * Generar el título de la propiedad.
	 * @throws Exception
	 */
	public void generateHeader() throws Exception {

		ReportVisualStyle style = new ReportVisualStyle(report.getExcel());
		
		int columnIndex = report.getColumnIndex();
		
		ExcelWorkbook excel = report.getExcel();
		HSSFCell cell = excel.getCell(report.getRowIndex(), columnIndex);

		excel.setCellValue(cell, title);
		style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		
		// Estilo de celda con respecto al tipo de valor.
		style.setBox(true);
		if (style.getForeColor() < 0)
			style.setForeColor(HSSFColor.WHITE.index);
		if (style.getBackgroundColor() < 0)
			style.setBackgroundColor(HSSFColor.BLUE_GREY.index);
		
		if (style.getFontName() == null) style.setFontName(report.getFontNameDefault());
		
		//report.getExcel().addMergeCells(report.getRowIndex(), columnIndex, report.getRowIndex(), columnIndex + 1);

		style.setApplyStyle(cell);
	}

	public int getColumnndex() {
		return columnIndex;
	}

	public void setColumnIndex(int columnIndex) {
		this.columnIndex = columnIndex;
	}

}

package com.javalego.xls.report.object.elements.table;

import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;

import com.javalego.xls.report.ExcelWorkbook;
import com.javalego.xls.report.ReportVisualStyle;
import com.javalego.xls.report.object.ReportObject;
import com.javalego.xls.report.object.elements.BasicElement;
import com.javalego.xls.report.object.elements.Property;

/**
 * Fila de una tabla
 * @see TableProperties
 * @see ColumnTable
 * @author ROBERTO RANZ
 *
 */
public class RowTable extends BasicElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected int columnIndex;

	public RowTable(ReportObject report) {
		super(report);
	}

	protected ArrayList<Property> properties = new ArrayList<Property>();

	@Override
	public void generate() throws Exception {

		generateHeader();
		
		columnIndex = report.getColumnIndex();

		int column= report.getColumnIndex();

		for(int i = 0; i < properties.size(); i++) {

			properties.get(i).setTitle(null);
			properties.get(i).generate();
			report.addColumnIndex();
		}

		report.setColumnIndex(column);
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

		style.setApplyStyle(cell);

	}

	public int getColumnndex() {
		return columnIndex;
	}

	public void setColumnIndex(int columnIndex) {
		this.columnIndex = columnIndex;
	}

	public ArrayList<Property> getProperties() {
		return properties;
	}

	public void setProperties(ArrayList<Property> properties) {
		this.properties = properties;
	}
}

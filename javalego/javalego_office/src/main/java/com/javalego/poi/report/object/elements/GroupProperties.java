package com.javalego.poi.report.object.elements;

import java.util.ArrayList;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;

import com.javalego.poi.report.ExcelWorkbookXSSF;
import com.javalego.poi.report.object.ReportObject;
import com.javalego.util.StringUtils;

/**
 * Agrupación de propiedades.
 * @author ROBERTO RANZ
 */
public class GroupProperties extends BasicElement {

	private static final long serialVersionUID = -6476633986822925570L;

	/**
	 * Alineación de propiedades.
	 */
	private String alignment = "V";

	/**
	 * Visible
	 */
	private boolean visible = true;
	
	/**
	 * Relación de propiedades del objeto incluidas en el grupo.
	 */
	protected ArrayList<Property> properties = new ArrayList<Property>();

	public GroupProperties(ReportObject report) {
		super(report);
	}

	/**
	 * Generar contenido
	 */
	@Override
	public void generate() throws Exception {
		
		if (!StringUtils.isEmpty(title)) {
			report.addRowIndex();
			generateHeader();
		}

		for(int i = 0; i < properties.size(); i++) {
			if (alignment.equals("V"))
				report.addRowIndex();
			else {
				report.addColumnIndex();
			}
			
			properties.get(i).generate();
		}
	}

	/**
	 * Generar el título de la propiedad.
	 * @throws Exception
	 */
	private void generateHeader() throws Exception {

		int columnIndex = report.getColumnIndex();

		ExcelWorkbookXSSF excel = report.getExcel();
		Cell cell = excel.getCell(report.getRowIndex(), columnIndex);

		excel.setCellValue(cell, title == null ? " " : title);
	
		// Altura para el font establecido.
		report.getExcel().setRowHeight(report.getRowIndex(), 400);
		
		style.setAlignment(CellStyle.ALIGN_CENTER);

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

		report.getExcel().addMergeCells(report.getRowIndex(), columnIndex, report.getRowIndex(), columnIndex + 1);

		style.setApplyStyle(cell);
	}

	public ArrayList<Property> getProperties() {
		return properties;
	}

	public void setProperties(ArrayList<Property> properties) {
		this.properties = properties;
	}

	public String getAlignment() {
		return alignment;
	}

	public void setAlignment(String alignment) {
		this.alignment = alignment;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
}

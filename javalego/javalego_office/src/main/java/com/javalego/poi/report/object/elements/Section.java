package com.javalego.poi.report.object.elements;

import java.util.ArrayList;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;

import com.javalego.poi.report.ExcelWorkbookXSSF;
import com.javalego.poi.report.object.ReportObject;
import com.javalego.util.StringUtils;

/**
 * Sección que agrupa una serie de grupos y grupos de propiedades.
 * Cada sección se representa en una serie de columnas necesarias para imprimir
 * el contenido de grupos de propiedades. 
 * @author ROBERTO RANZ
 */
public class Section extends BasicElement {

	private static final long serialVersionUID = -4907137175710964270L;

	// Indice de la columna (progreso de impresión).
	protected int columnIndex;
	
	/**
	 * Grupos de cada sección
	 */
	protected ArrayList<Group> groups = new ArrayList<Group>();

	public Section(ReportObject report) {
		super(report);
	}

	/**
	 * Generar contenido
	 */
	@Override
	public void generate() throws Exception {
		
		report.addColumnIndex();
		
		columnIndex = report.getColumnIndex();
		
		// Guardar la columna para reestablecerla una vez finalizada la impresión de un grupo o grupo de propiedades.
		if (!StringUtils.isEmpty(title)) {
			report.addRowIndex();
			generateHeader();
			report.addColumnIndex();
		}
		
		int column = report.getColumnIndex();

		// Generar grupos
		for(int i = 0; i < groups.size(); i++) {
			
			report.setColumnIndex(column);
			
			Group group = groups.get(i);
			
			group.generate();
			
		}
		
	}

	public ArrayList<Group> getGroups() {
		return groups;
	}

	public void setGroups(ArrayList<Group> groups) {
		this.groups = groups;
	}

	public int getColumnIndex() {
		return columnIndex;
	}

	public void setColumnIndex(int columnIndex) {
		this.columnIndex = columnIndex;
	}

	/**
	 * Generar el título de la propiedad.
	 * @throws Exception
	 */
	private void generateHeader() throws Exception {

		int columnIndex = report.getColumnIndex();
		
		ExcelWorkbookXSSF excel = report.getExcel();
		Cell cell = excel.getCell(report.getRowIndex(), columnIndex);

		excel.setCellValue(cell, title);
		style.setAlignment(CellStyle.ALIGN_LEFT);
		
		// Estilo de celda con respecto al tipo de valor.
		//style.setBox(true);
		style.setFontBold(true);
		style.setFontSize(12);
		
		if (style.getForeColor() < 0)
			style.setForeColor(HSSFColor.BLUE_GREY.index);
		
		if (style.getFontName() == null) 
			style.setFontName(report.getFontNameDefault());
		
		cell = excel.getCell(report.getRowIndex(), columnIndex);
		
		// Altura para el font establecido.
		report.getExcel().setRowHeight(report.getRowIndex(), 600);
		
		style.setApplyStyle(cell);
	}
	
}

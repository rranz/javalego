package com.javalego.xls.report.object.elements;

import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;

import com.javalego.util.StringUtils;
import com.javalego.xls.report.ExcelWorkbook;
import com.javalego.xls.report.object.ReportObject;

/**
 * Grupo que contiene un conjunto de grupos de propiedades.
 * @author ROBERTO RANZ
 *
 */
public class Group extends BasicElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected int columnIndex;
	
	public Group(ReportObject report) {
		super(report);
	}

	/**
	 * Relación de grupos de propiedades
	 */
	protected ArrayList<GroupProperties> groupsProperties = new ArrayList<GroupProperties>();
	
	@Override
	public void generate() throws Exception {

		report.addRowIndex();

		columnIndex = report.getColumnIndex();
		
		//if (!StringUtils.isEmpty(title)) {
			generateHeader();
			if (!StringUtils.isEmpty(title)) 
				report.addRowIndex();
			report.addColumnIndex();
		//}
		
		int column= report.getColumnIndex();
		
		// Grupos de propiedades
		for(int i = 0; i < groupsProperties.size(); i++) {
			
			report.setColumnIndex(column);
			
			GroupProperties gp = groupsProperties.get(i);
			
			gp.generate();
			
			report.addRowIndex();
		}
		
	}

	/**
	 * Generar el título de la propiedad.
	 * @throws Exception
	 */
	private void generateHeader() throws Exception {

		int columnIndex = report.getColumnIndex();
		
		ExcelWorkbook excel = report.getExcel();
		HSSFCell cell = excel.getCell(report.getRowIndex(), columnIndex);

		excel.setCellValue(cell, title == null ? " " : title);
		style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		
		// Altura para el font establecido.
		report.getExcel().setRowHeight(report.getRowIndex(), 400);
		
		// Estilo de celda con respecto al tipo de valor.
		//style.setBox(true);
		style.setFontBold(true);
		style.setFontSize(12);
		if (style.getForeColor() < 0)
			style.setForeColor(HSSFColor.BROWN.index);
		//if (style.getBackgroundColor() < 0)
			//style.setBackgroundColor(HSSFColor.ORANGE.index);
		
		if (style.getFontName() == null) 
			style.setFontName(report.getFontNameDefault());
		
		cell = excel.getCell(report.getRowIndex(), columnIndex);
		//style.setApplyStyle(cell);
		//cell = excel.getCell(report.getRowIndex(), columnIndex+1);
		//style.setApplyStyle(cell);

		//report.getExcel().addMergeCells(report.getRowIndex(), columnIndex, report.getRowIndex(), columnIndex + 1);
			
		style.setApplyStyle(cell);
	}
	
	public ArrayList<GroupProperties> getGroupsProperties() {
		return groupsProperties;
	}

	public void setGroupsProperties(ArrayList<GroupProperties> groupsProperties) {
		this.groupsProperties = groupsProperties;
	}

	public int getColumnndex() {
		return columnIndex;
	}

	public void setColumnIndex(int columnIndex) {
		this.columnIndex = columnIndex;
	}
}

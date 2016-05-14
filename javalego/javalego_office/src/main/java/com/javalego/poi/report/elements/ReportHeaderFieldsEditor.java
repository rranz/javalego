package com.javalego.poi.report.elements;

import java.util.ArrayList;

import com.javalego.poi.report.BasicReport;
import com.javalego.poi.report.elements.ReportHeaderFields;

/**
 * Cabecera de agrupación de campos. Ej.: Neto, Impuestos y Total agrupados por el título Importe Factura.
 * @author ROBERTO
 *
 */
public class ReportHeaderFieldsEditor {
	
	/**
	 * Informe asociado.
	 */
	private BasicReport report;
	
	private int maxRows;
	
	/**
	 * Declara la cabecera de campos como dinámica dependiendo del nivel de anidación que ocupe dentro de la lista de cabeceras de campos.
	 */
	private boolean dinamicStyle;
	
	public ReportHeaderFieldsEditor(BasicReport report){
		this.report = report;
	}
	
	/**
	 * Generar las cabeceras de campo principales controlando las variables rowIndex y columnIndex
	 * ya que de una cabecera a otra varían su posición y se descontrolaría el informe.
	 * El método generateChildren se utiliza recursivamente para los hijos de las cabeceras principales. 
	 */
	public void generate(ArrayList<ReportHeaderFields> headers) throws Exception {
		
		report.addRowIndex();
		
		for(ReportHeaderFields field : headers) {
			
			int rowLevel = report.getRowIndex() + getLevel(field);
		  if (rowLevel > maxRows)
		  	maxRows = rowLevel;
			
		  field.generate(rowLevel, field.getColumnField(true) + report.getInitColumnIndex(), field.getColumnField(false) + report.getInitColumnIndex());
		  
		  if (field.getHeaders() != null) {
		  	generateChildren(field.getHeaders());
		  }
		}
		
		report.setRowIndex(maxRows);
	}

	/**
	 * Nivel de anidamiento
	 * @return
	 */
	private int getLevel(ReportHeaderFields header) {
		
		int level = 0;
		//this;
		ReportHeaderFields h = header;
		while(h != null) {
			if (h != null && h.getHeaderOwner() != null)
				++level;
			h = h.getHeaderOwner();
		}
		
		// Establecer el color de las cabeceras (si no existe)
		if (header.isDinamicStyle()) {
			
			String color = "maroon";
			
			switch (level) {
			case 1:
				color = "navy";
				break;
			case 2:
				color = "blue";
				break;
			case 3:
				color = "silver";
				break;
			case 4:
				color = "gray";
				break;
			default:
				break;
			}
			
			header.getStyle().setBackgroundColor(color);
		}
		
		return level;
	}
	
	/**
	 * Generar los headers hijos.
	 * @throws Exception
	 */
	public void generateChildren(ArrayList<ReportHeaderFields> headers) throws Exception {
		
		for(ReportHeaderFields field : headers) {
			
			int rowLevel = report.getRowIndex() + getLevel(field);
		  if (rowLevel > maxRows)
		  	maxRows = rowLevel;
			
		  field.generate(rowLevel, field.getColumnField(true) + report.getInitColumnIndex(), field.getColumnField(false) + report.getInitColumnIndex());
		  
		  if (field.getHeaders() != null) {
		  	
		  	generateChildren(field.getHeaders());
		  }
		}
	}

	public boolean isDinamicStyle() {
		return dinamicStyle;
	}

	public void setDinamicStyle(boolean dinamicStyle) {
		this.dinamicStyle = dinamicStyle;
	}
	
}

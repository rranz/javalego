package com.javalego.xls.report.elements;

import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.util.HSSFColor;

import com.javalego.util.StringUtils;
import com.javalego.xls.report.BasicReport;
import com.javalego.xls.report.ExcelWorkbook;
import com.javalego.xls.report.ReportVisualStyle;


/**
 * Cabecera de agrupación de campos. Ej.: Neto, Impuestos y Total agrupados por el título Importe Factura.
 * @author ROBERTO
 */
public class ReportHeaderFields extends ArrayList<ReportField> implements IReportItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ReportVisualStyle style;
	
	private String title;
	
	private BasicReport report;
	
	private String description;

	// Variable temporal para obtener la posición de un campo dentro de la lista de campos o cabeceras anidadas.
	private int _columnField = -1;
	
	// Posiciones de fila y columna que ocupa la cabecera de los campos.
	private int rowIndex;
	private int columnInit;
	private int columnEnd;
	
	/**
	 * Declara la cabecera de campos como dinámica dependiendo del nivel de anidación que ocupe dentro de la lista de cabeceras de campos.
	 */
	private boolean dinamicStyle;

	/**
	 * Lista de cabeceras de campos hijos.
	 */
	private ArrayList<ReportHeaderFields> headers = new ArrayList<ReportHeaderFields>();
	
	/**
	 * Header al que está asociado esta lista de cabeceras de campos. Esta relación nos permitirá obtener el nivel de profundidad dentro
	 * de toda la lista de headersFields.
	 */
	private ReportHeaderFields headerOwner;
	
	
	public ReportHeaderFields(BasicReport report, ReportHeaderFields headerOwner, String title){
		this.title = title;
		this.report = report;
		this.headerOwner = headerOwner;

		// Inicializar estilo de la celda.
		style = new ReportVisualStyle(report.getExcel());
		style.setForeColor(HSSFColor.WHITE.index);
		style.setBackgroundColor(HSSFColor.BLACK.index);
		style.setBox(true);
		style.setAlignVerticalCenter(true);
		style.setWrapText(true);
		style.setFontBold(true);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	}
	
	public void addReportField(ReportField reportField){
		add(reportField);
	}
	
	public void addReportField(String fieldName) throws Exception {
		
		for(int i = 0; i < report.getFields().size(); i++){
			
			ReportField field = (ReportField)report.getFields().get(i);
			
			if (field.getFieldName() != null && field.getFieldName().toLowerCase().equals(fieldName.toLowerCase())){
				add(field);
				return;
			}
		}
		throw new Exception("Field " + "'" + fieldName + "'" + " not found"); 
	}

	/**
	 * Generar celda con el título del campo
	 */
	@Override
	public void generate() throws Exception {
	}
	
	/**
	 * Generar celda con el título del campo
	 */
//	public void generate(int columnPos) throws Exception {
//		
//		rowIndex = report.getRowIndex();
//		int columnIndex = report.getInitColumnIndex();
//		
//		columnInit = -1;
//		columnEnd = 0;
//	
//		// Buscar la posición del primer campo (sólo si no tiene cabeceras hijas).
//		if (headers == null || headers.size() == 0) {
//			
//			for(ReportField field : this) {
//				
//				if (field.isVisible()){
//					
//					// Buscar posición del campo en caso de ser visible.
//					int pos = -1;
//					for(ReportField fieldfind : report.getFields()) {
//	
//						if (fieldfind.isVisible()) {
//							++pos;
//							if (fieldfind == field)
//								break;	
//						}
//					}
//					
//					int column = columnIndex + pos;
//			
//					if (columnInit < column && columnEnd == 0 && columnInit < 0)
//						columnInit = column;
//					
//					if (columnEnd < column)
//						columnEnd = column;
//				}
//			}
//		}
//
//		if (columnInit < 0)
//			columnInit = 0;
//		
//		if (columnInit < columnIndex)
//			columnInit = columnIndex;
//		
//		if (columnPos > -1) {
//			columnInit = columnPos;
//			if (columnEnd < columnInit)
//				columnEnd = columnInit;
//		}
//		
//		ExcelWorkbook excel = report.getExcel();
//		HSSFCell cell;
//
//		// Estilo de celda con respecto al tipo de valor.
//		if (style.getFontName() == null) 
//			style.setFontName(report.getFontNameDefault());
//
//		cell = excel.getCell(rowIndex, columnInit);
//		cell.setCellValue(new HSSFRichTextString(title));
//		style.setApplyStyle(cell);
//
//		for(int i = columnInit+1; i <= columnEnd; i++){
//			cell = excel.getCell(rowIndex, i);
//			style.setApplyStyle(cell);
//		}
//
//		// Añadir como comentario la descripción de header.
//		if (!StringUtils.isEmpty(description))
//			report.getExcel().setCellComment(description, rowIndex, columnInit);
//		
//		report.getExcel().addMergeCells(rowIndex, columnInit, rowIndex, columnEnd);		
//	}

	/**
	 * Obtiene la posición de su campo inicial
	 */
	public int getColumnField(boolean init) {
		
		 _columnField = -1;
		
		if (this.size() > 0) {
			
			for(ReportField field : this) {
				
				if (field.isVisible()){
					
					// Buscar posición del campo en caso de ser visible.
					int pos = -1;
					for(ReportField fieldfind : report.getFields()) {
	
						if (fieldfind.isVisible()) {
							++pos;
							if (fieldfind == field) {
								if (init) {
									if (_columnField < 0 || pos < _columnField)
										_columnField = pos;
								}
								else
									_columnField = pos;
							}
						}
					}
				}
			}
		}
		
		if (headers != null && headers.size() > 0) {
			findHeaders(headers, init);
		}
		
		return _columnField;
	}
	
	/**
	 * Buscar campos en la cabecera.
	 * @param headers
	 */
	private void findHeaders(ArrayList<ReportHeaderFields> headers, boolean init) {
		
		for(ReportHeaderFields header : headers) {
			
			if (header.size() > 0) {
				
				for(ReportField field : header) {
					
					if (field.isVisible()){
						
						// Buscar posición del campo en caso de ser visible.
						int pos = -1;
						for(ReportField fieldfind : report.getFields()) {
		
							if (fieldfind.isVisible()) {
								++pos;
								if (fieldfind == field) {
									if (init) {
										if (_columnField < 0 || pos < _columnField)
											_columnField = pos;
									}
									else if (pos > _columnField)
										_columnField = pos;
								}
							}
						}
					}
				}
			}

			if (header.getHeaders() != null && header.getHeaders().size() > 0) {
				findHeaders(header.getHeaders(), init);
			}
		}
		
	}
	
	
	/**
	 * Generar celda con el título del campo
	 */
	public void generate(int rowIndex, int columnInit, int columnEnd) throws Exception {
		
		if (rowIndex < 0 || columnInit < 0 || columnEnd < 0)
			return;
		
		ExcelWorkbook excel = report.getExcel();
		HSSFCell cell;

		// Estilo de celda con respecto al tipo de valor.
		if (style.getFontName() == null) 
			style.setFontName(report.getFontNameDefault());

		cell = excel.getCell(rowIndex, columnInit);
		cell.setCellValue(new HSSFRichTextString(title));
		style.setApplyStyle(cell);

		for(int i = columnInit+1; i <= columnEnd; i++){
			cell = excel.getCell(rowIndex, i);
			style.setApplyStyle(cell);
		}

		// Añadir como comentario la descripción de header.
		if (!StringUtils.isEmpty(description))
			report.getExcel().setCellComment(description, rowIndex, columnInit);
		
		report.getExcel().addMergeCells(rowIndex, columnInit, rowIndex, columnEnd);
		
		this.rowIndex = rowIndex;
		this.columnInit = columnInit;
		this.columnEnd = columnEnd;
	}
	
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ReportVisualStyle getStyle() {
		return style;
	}

	public void setStyle(ReportVisualStyle style) {
		this.style = style;
	}

	public ArrayList<ReportHeaderFields> getHeaders() {
		return headers;
	}

	public int getColumnEnd() {
		return columnEnd;
	}

	public int getRowIndex() {
		return rowIndex;
	}

	public int getColumnInit() {
		return columnInit;
	}

	public void setColumnEnd(int columnEnd) {
		this.columnEnd = columnEnd;
	}
	
	@Override
	public String toString() {
		return title; 
	}

	public ReportHeaderFields getHeaderOwner() {
		return headerOwner;
	}

	public void setHeaderOwner(ReportHeaderFields headerOwner) {
		this.headerOwner = headerOwner;
	}

	public boolean isDinamicStyle() {
		return dinamicStyle;
	}

	public void setDinamicStyle(boolean dinamicStyle) {
		this.dinamicStyle = dinamicStyle;
	}
	
}

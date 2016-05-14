package com.javalego.xls.report.elements;

import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFRow;

import com.javalego.xls.report.BasicReport;
import com.javalego.xls.report.elements.calcfields.BasicReportCalcField;

/**
 * Relación de campos que incluir en el informe.
 */
public class ReportFields extends ArrayList<ReportField> implements IReportItem {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BasicReport report;
	
	public ReportFields(BasicReport report) {
		this.report = report;
	}

	/**
	 * Añade un campo al informe.
	 * @param fieldName
	 * @return
	 */
	public ReportField add(String fieldName) {
		
		ReportField field = new ReportField(report, fieldName, fieldName);
		add(field);
		return field;
	}
	
	/**
	 * Añade un separador al informe.
	 * @return
	 */
	public ReportSeparatorField addSeparator() {
		
		ReportSeparatorField field = new ReportSeparatorField(report, null);
		add(field);
		return field;
	}
	
	/**
	 * Añade un campo al informe.
	 * @param fieldName
	 * @param title
	 * @return
	 */
	public ReportField add(String fieldName, String title) {
		
		ReportField field = new ReportField(report, fieldName, title);
		add(field);
		return field;
	}
	
	/**
	 * Añade un campo calculado al informe.
	 * @param field
	 * @return
	 */
	public ReportField addCalcField(BasicReportCalcField field)	{
		add(field);
		return field;
	}
	
	/**
	 * Buscar el campo por su nombre
	 * @param fieldName
	 * @return
	 */
	public ReportField getField(String fieldName){

		for(ReportField field : this) {
			String name = field.getFieldName();
			if (name != null && name.toLowerCase().equals(fieldName.toLowerCase()))
				return field;
		}
		return null;
	}

	/**
	 * Busca el campo por el nombre, teniendo en cuenta los campos calculados y los separadores.
	 * @param fieldName
	 * @return
	 */
	public int getIndexField(String fieldName) {
		return getIndexField(fieldName, true);
	}
	
	/**
	 * Busca el campo por el nombre, teniendo en cuenta los campos calculados y los separadores.
	 * @param fieldName
	 * @param includeCalcFields
	 * @return
	 */
	public int getIndexField(String fieldName, boolean includeCalcFields){
		
		int pos = -1;
		
		for(int i = 0; i < size(); i++){
			
			if (get(i) instanceof BasicReportCalcField && !includeCalcFields)
				;
			else if (!(get(i) instanceof ReportSeparatorField) && get(i).getFieldName() != null && get(i).getFieldName().toLowerCase().equals(fieldName.toLowerCase())) {
				++pos;
				return pos;
			}
			else if (!(get(i) instanceof ReportSeparatorField))
				++pos;
		}
		return -1;
	}

	/**
	 * Lista de campos.
	 * @return
	 */
	public ReportField[] getFields(){
    return (ReportField[])toArray(new ReportField[size()]);
	}

	/**
	 * Genera la cabecera de los campos.
	 */
	@Override
	public void generate() throws Exception {
		
		report.addRowIndex();
		
		int rowIndex = report.getRowIndex();
		
		int columnIndex = report.getInitColumnIndex();
		int cont = 0;
		
		for(ReportField field : this) {
			
			if (field.isVisible()){
				field.generateHeader(rowIndex, columnIndex + cont);
				++cont;
			}
		}
		
		// Establecer las filas que deben de repetirse.
		report.setRepeatingRowsCount(rowIndex);
		
		if (report.getGroups().size() > 0 && report.isShowDetail()){
			report.addRowIndex();
			// Recortar la altura de la fila que dejamos en blanco entre grupos y totales.
			HSSFRow row = report.getExcel().getActiveSheet().createRow(report.getRowIndex());
			row.setHeight((short)100);
		}

	}
	
}


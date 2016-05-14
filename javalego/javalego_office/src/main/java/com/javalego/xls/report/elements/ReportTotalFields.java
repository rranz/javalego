package com.javalego.xls.report.elements;

import java.util.ArrayList;

import com.javalego.util.StringUtils;
import com.javalego.xls.report.BasicReport;
import com.javalego.xls.report.elements.calcfields.ReportCalcFieldObjects;

/**
 * Descripción breve de ReportTotalFields.
 */
public class ReportTotalFields extends ArrayList<ReportTotalField> implements IReportItem
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	BasicReport report;
	
	public ReportTotalFields(BasicReport report) {
		this.report = report;
	}

	public ReportTotalField add(ReportField field) {
		
		ReportTotalField tf = new ReportTotalField(report, field);
		add(tf);
		return tf;
	}

	public ReportTotalField add(ReportField field, String formula) {
		
		ReportTotalField tf = new ReportTotalField(report, field, formula);
		add(tf);
		return tf;
	}

	/**
	 * Inicializar a 0 los valores totalizadores.
	 */
	public void initialize(){
		
		for(int i = 0; i < size(); i++){
			
			ReportTotalField field = ((ReportTotalField)get(i));
			field.initialize();
		}
	}
	
	/**
	 * Totalizar los atributos de un objeto definido en ReportField.
	 */
	public void totalize(Object dataObject) throws Exception {
		
		for(int i = 0; i < size(); i++){
			
			ReportTotalField field = ((ReportTotalField)get(i));
			
			Object fieldValue = null;
			
			// Campo calculado
			if (field.getField() instanceof ReportCalcFieldObjects) {
				fieldValue = ((ReportCalcFieldObjects)field.getField()).getValue();
			}
			else
				fieldValue = report.getFieldValue(dataObject, field.getField().getFieldName());
			
			Number value = 0;
			
			// tipología de campos
			if (fieldValue instanceof Number) 
				value = (Number)fieldValue;
			else if (fieldValue instanceof Boolean) 
				value = ((Boolean)fieldValue).booleanValue() ? 1 : 0;
			
			field.totalize(value);
			
		}
	}

	public BasicReport getReport() {
		return report;
	}
	
	/**
	 * Buscar un campo a totalizar por su nombre.
	 * @param fieldName
	 * @return
	 */
	public ReportTotalField getReportTotalField(String fieldName) {
		
		if (StringUtils.isEmpty(fieldName))
			return null;
		
		for(int i = 0; i < size(); i++) {
			
			ReportTotalField tf = (ReportTotalField)get(i);
			
			if (tf != null && tf.getField() != null && tf.getField().getFieldName() != null && tf.getField().getFieldName().toLowerCase().equals(fieldName.toLowerCase()))
				return (ReportTotalField)get(i);
			else {
				//GanaException ge = new GanaException("El campo '" + fieldName + "' no se encuentra en la lista de campos a totalizar.", GanaException.ERROR);
				//ge.printStackTrace();
			}
				
		}
		return null;
	}

	/**
	 * Generar los totales de los campos definidos.
	 */
	@Override
	public void generate() throws Exception {
		generate((short)-1);
	}
	
	/**
	 * Generar los totales de los campos definidos.
	 */
	public void generate(short backgroundColor) throws Exception {
		
		for(int i = 0; i < size(); i++) {
			
			ReportTotalField field = ((ReportTotalField)get(i));
			
			if (field != null && field.getField().isVisible())
				field.generate(backgroundColor);
		}
	}

	/**
	 * Añadir un campo buscando por su nombre
	 * @param fieldName
	 */
	public ReportTotalField add(String fieldName) {
		
		for(ReportField field : this.report.getFields()) {
			
			if (field.getFieldName() != null && field.getFieldName().equals(fieldName)) {
				return add(field);
			}
		}
		return null;
	}
}


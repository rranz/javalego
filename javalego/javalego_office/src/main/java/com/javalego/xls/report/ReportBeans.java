package com.javalego.xls.report;

import java.util.List;

import com.javalego.util.ReflectionUtils;
import com.javalego.xls.report.elements.ReportField;
import com.javalego.xls.report.elements.calcfields.ReportCalcFieldObjects;

/**
 * Clase que nos permite generar un informe basado en una colección de beans.
 * @author ROBERTO RANZ
 *
 */
public class ReportBeans extends BasicReport {

	/**
	 * Colección de objetos a imprimir.
	 */
	private List<?> beans;
	
	public ReportBeans(){
	}
	
	public ReportBeans(String title) {
		getHeader().setTitle(title);
	}
	
	public ReportBeans(String title, List<?> beans) {
		getHeader().setTitle(title);
		this.beans = beans;
	}
	
	/**
	 * Generar las filas que contendrán la información de los objetos.
	 */
	@Override
	protected void writeDetail() throws Exception {
		
		ReportField field = null;
		
		Object dataObject = null;
		
		if (beans == null) return;
		
		for(int i = 0; i < beans.size(); i++){
			
			objectsIndex = i;
			dataObject = beans.get(i);
			
			writeGroups(dataObject);
			
			if (isShowDetail()){

				++rowIndex;
				
				for(int k = 0; k < fields.size(); k++){
					
					field = ((ReportField)fields.get(k));
					
					if (field.isVisible())
						field.generate();
				}
			}
			totalFields.totalize(dataObject);
		}
		writeGroups(null);
	}

	
	/**
	 * Obtiene el valor de un atributo o campo de una colección de objetos.
	 * @param fieldName
	 * @return
	 * @throws Exception
	 */
	@Override
	public Object getFieldValue(Object dataObject, String fieldName) throws Exception {
		
	   return ReflectionUtils.getPropertyValue(dataObject, fieldName);
	}
	
	/**
	 * Obtiene el objeto actual dentro del proceso de generación del informe.
	 * @return
	 */
	@Override
	public Object getActualObject() {
		
		if (getObjectsIndex() > -1){
		  return beans.get(getObjectsIndex());
		}
		else
			return null;
	}

	/**
	 * Obtiene un objeto por su índice
	 * @return
	 */
	@Override
	public Object getObject(int index) {
		
		if (index > -1){
		  return beans.get(index);
		}
		else
			return null;
	}

	@Override
	public int getDataSize() {
		return beans != null ? beans.size() : 0;
	}
	
	/**
	 * Añadir un campo calculado.
	 * @param field
	 * @return
	 */
	public void addCalcField(ReportCalcFieldObjects field) {
		fields.addCalcField(field);
	}

	/**
	 * Añadir un campo calculado.
	 * @param field
	 * @return
	 */
	public ReportCalcFieldObjects addCalcField(String fieldName, String title, String expression) {
		
		ReportCalcFieldObjects field = new ReportCalcFieldObjects(this, fieldName, title, expression);
		fields.addCalcField(field);
		return field;
	}

	public List<?> getBeans() {
		return beans;
	}

	public void setBeans(List<?> beans) {
		this.beans = beans;
	}
}

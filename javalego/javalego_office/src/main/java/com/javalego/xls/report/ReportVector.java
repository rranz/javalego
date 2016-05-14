package com.javalego.xls.report;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import com.javalego.xls.report.elements.ReportField;
import com.javalego.xls.report.elements.ReportHeaderFields;
import com.javalego.xls.report.elements.ReportTotalField;
import com.javalego.xls.report.elements.calcfields.ReportCalcFieldVector;


/**
 * Informe sobre un vector de objetos de dos dimensiones.
 * @author ROBERTO
 *
 */
public class ReportVector extends BasicReport {

	/**
	 * Vector de datos. Una alternativa a la colección de objetos.
	 */
	@SuppressWarnings("rawtypes")
	Vector vector;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ReportVector report = new ReportVector();
		report.setFileName("c:/gana/listado.xls");
		report.getHeader().setTitle("Informe de prueba");

		Vector<Object[]> vector = new Vector<Object[]>();
		vector.add(new Object[] { "CLIENTE 1", new Integer(-100), "A2343", new Date() });
		vector.add(new Object[] { "CLIENTE 1", new Integer(200), "A2343", new Date() });
		vector.add(new Object[] { "CLIENTE 2", new Integer(300), "A2343", new Date() });
		vector.add(new Object[] { "CLIENTE 2", new Integer(500), "A2343", new Date() });
		vector.add(new Object[] { "CLIENTE 3", new Integer(600), "A2343", new Date() });
		report.setVector(vector);

		
		//report.addSeparatorField();
		report.addField("name", "Nombre descriptivo");
		ReportField f = report.addField("banco", "Banco");
		report.addField("nif", "Nif");
		report.addSeparatorField();
		report.addCalcField("name", "Campo calculado", "($F{banco} * 2)+89");
		report.addSeparatorField();
		report.addField("fecha", "Fecha");
		// Crear agrupaciones de campos.
		
		ReportHeaderFields headerFields = new ReportHeaderFields(report, null, "titulo");
		try {
			headerFields.addReportField("name");
			headerFields.addReportField("banco");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		report.getHeadersFields().add(headerFields);
		
		report.getTotalFields().add(f, ReportTotalField.FORMULA_AVG);

		//ReportGroup g = report.getGroups().add("name", true, true, true);
		//g.addTotalFields();
		
		try {
			report.setShowDetail(true);
			//report.getExcel().insertImage("d:/mariposa.png");
			report.execute(true);
			report.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@SuppressWarnings("rawtypes")
	public Vector getVector() {
		return vector;
	}

	@SuppressWarnings("rawtypes")
	public void setVector(Vector vector) {
		this.vector = vector;
	}

	/**
	 * Generar las filas que contendrán la información de los objetos.
	 */
	@Override
	protected void writeDetail() throws Exception {
		
		ReportField field = null;
		
		if (vector != null){
			
			for(int i = 0; i < vector.size(); i++){
				
				objectsIndex = i;
				
				Object dataObject = getActualObject();
				
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
		}
		writeGroups(null);
	}

	/**
	 * Valor del campo
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Object getFieldValue(Object dataObject, String fieldName) throws Exception {
		
		int index = fields.getIndexField(fieldName, false);
		
		if (fieldName != null && index > -1)
			if (dataObject instanceof List)
				return ((List)dataObject).get(index);
			else {
				try {
					return ((Object[])dataObject)[index];
				}
				catch(Exception e) {
					throw e;
				}
			}
		else
		  return null;
	}

	/**
	 * Valor del campo buscando por la posición de un registro.
	 */
	@SuppressWarnings("rawtypes")
	public Object getFieldValue(int rowIndex, String fieldName) throws Exception {
		
		int index = fields.getIndexField(fieldName, false);
		
		if (vector != null && fieldName != null && index > -1) {
			
			Object dataObject = vector.get(rowIndex);
			
			if (dataObject != null) {
				
				if (dataObject instanceof List)
					return ((List)dataObject).get(index);
				else {
					try {
						return ((Object[])dataObject)[index];
					}
					catch(Exception e) {
						throw e;
					}
				}
			}
			return null;
		}
		else
		  return null;
	}

	/**
	 * Obtener el objeto que representa el registro actualmente generado en el informe.
	 */
	@Override
	public Object getActualObject() {

		if (getObjectsIndex() > -1) {
		
			// Comprobar que no es un array de dos dimensiones sino de una.
			if (vector.get(getObjectsIndex()) instanceof Object[])
				return (Object[])vector.get(getObjectsIndex());
			else
				return new Object[] { vector.get(getObjectsIndex()) };
			
		}
		else
			return null;
	}
	
	/**
	 * Obtiene un objeto por su índice
	 * @return
	 */
	@Override
	public Object getObject(int index){
		if (index > -1){
		  return vector.get(index);
		}
		else
			return null;
	}

	@Override
	public int getDataSize() {
		return vector != null ? vector.size() : 0;
	}

	/**
	 * Añadir un campo calculado.
	 * @param field
	 * @return
	 */
	public void addCalcField(ReportCalcFieldVector field) {
		fields.addCalcField(field);
	}

	/**
	 * Añadir un campo calculado.
	 * @param field
	 * @return
	 */
	public ReportCalcFieldVector addCalcField(String fieldName, String title, String expression) {
		ReportCalcFieldVector field = new ReportCalcFieldVector(this, fieldName, title, expression);
		fields.addCalcField(field);
		return field;
	}

}

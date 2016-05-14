package com.javalego.xls.report;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.javalego.xls.report.elements.ReportField;
import com.javalego.xls.report.elements.ReportHeaderFields;
import com.javalego.xls.report.elements.ReportTotalField;
import com.javalego.xls.report.elements.calcfields.ReportCalcFieldVector;

/**
 * Informe sobre una lista de array de valores (no objetos) de dos dimensiones.
 * Este tipo de informe es una alternativa al informe ReportVector basado en un Vector y no en un objeto List
 * @author ROBERTO
 *
 */
public class ReportList extends BasicReport {

	/**
	 * Array de valores de dos dimensiones. 
	 */
	@SuppressWarnings("rawtypes")
	List list;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ReportList report = new ReportList();
		report.setFileName("c:/listado.xls");
		report.getHeader().setTitle("Informe de prueba");

		List<Object[]> vector = new ArrayList<Object[]>();
		vector.add(new Object[] { "CLIENTE 1", new Integer(-100), "A2343", new Date() });
		vector.add(new Object[] { "CLIENTE 1", new Integer(200), "A2343", new Date() });
		vector.add(new Object[] { "CLIENTE 2", new Integer(300), "A2343", new Date() });
		vector.add(new Object[] { "CLIENTE 2", new Integer(500), "A2343", new Date() });
		vector.add(new Object[] { "CLIENTE 3", new Integer(600), "A2343", new Date() });
		report.setList(vector);


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
	public List getList() {
		return list;
	}

	@SuppressWarnings("rawtypes")
	public void setList(List list) {
		this.list = list;
	}

	/**
	 * Generar las filas que contendrán la información de los objetos.
	 */
	@Override
	protected void writeDetail() throws Exception {

		ReportField field = null;

		if (list != null){

			for(int i = 0; i < list.size(); i++){

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

	@SuppressWarnings("rawtypes")
	@Override
	public Object getFieldValue(Object dataObject, String fieldName) throws Exception {
		
		int index = fields.getIndexField(fieldName, false);
		
		if (fieldName != null && index > -1) {
			
			// Cambiar el índice de búsqueda del valor cuando el campo del informe tiene definida ya su posición. Este caso aplica a informe de array de valores
			// donde los campos a imprimir no corresponden ni en número ni en posicionamiento con la información dada.
			ReportField field = fields.getField(fieldName);
			if (/*!field.isTmpFieldName() &&*/ field != null && field.getIndex() > -1)
				index = field.getIndex();
			
			if (dataObject instanceof List)
				return ((List)dataObject).get(index);
			else
				return ((Object[])dataObject)[index];
			
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
			if (list.get(getObjectsIndex()) instanceof Object[])
				return (Object[])list.get(getObjectsIndex());
			else
				return list.get(getObjectsIndex());

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
		  return list.get(index);
		}
		else
			return null;
	}

	@Override
	public int getDataSize() {
		return list != null ? list.size() : 0;
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

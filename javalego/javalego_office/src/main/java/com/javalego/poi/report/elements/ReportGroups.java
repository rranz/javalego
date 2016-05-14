package com.javalego.poi.report.elements;

import java.util.ArrayList;

import com.javalego.poi.report.BasicReport;
import com.javalego.poi.report.elements.ReportField;
import com.javalego.poi.report.elements.ReportGroup;


/**
 * Descripción breve de ReportGroups.
 */
public class ReportGroups extends ArrayList<ReportGroup> {
	
	private static final long serialVersionUID = 7698101520729500223L;

	BasicReport report;
	
	public ReportGroups(BasicReport report)	{
		this.report = report;
	}

	/**
	 * Añadir un grupo y sus campos.
	 */
	public ReportGroup add(String fieldNames, boolean showFieldNames, boolean showRecordCount, boolean showTotalValues, boolean hideFields) throws Exception	{
		
		ReportGroup group = new ReportGroup(report);
		
		group.setShowFieldNames(showFieldNames);
		group.setShowRecordCount(showRecordCount);
		group.setShowTotals(showTotalValues);

		// Añadir campos de la agrupación.
		String[] fields = fieldNames.split("\\|");
		
		for(int i = 0; i < fields.length; i++){
			
			// Añadir el campo
			ReportField field = report.getFields().getField(fields[i]);
			
			if (field == null)
				throw new Exception("Field name '" + fields[i] + "' not exist.");
			else
				group.addGroupField(field, hideFields);
		}
		
		if (showTotalValues)
			group.addTotalFields();
		
		add(group);
		
		
		return group;
	}

	/**
	 * Añadir un grupo.
	 */
	public ReportGroup add(boolean showFieldNames, boolean showRecordCount, boolean showTotalValues)	{
		
		ReportGroup group = new ReportGroup(report);
		group.setShowFieldNames(showFieldNames);
		group.setShowRecordCount(showRecordCount);
		group.setShowTotals(showTotalValues);
		add(group);
		return group;
	}

	/**
	 * Imprimir las cabeceras y totales de los grupos definidos.
	 */
	public void generate(Object dataObject) throws Exception {
		
		// Variable temporal para controlar que si cambia un grupo, deberemos generar todas las cabeceras de los grupos restantes. Esto
		// evitará que el método group.isChangeValues() devuelva false cuando el mismo valor del campo del grupo coincida con el anterior registro.
		// Ej.: si tenemos dos grupos 1)Nombre 2)Banco y dos registros Nombre1, banco1 y Nombre2, banco1, la cabecera del grupo banco1 del segundo
		// registro no se mostraría ya que coincide con el valor del primer registro.
		boolean change = false;

		// 1. Pintar los campos totales de cada grupo si cambian los valores de los campos de los grupos.
		ReportGroup group = null;

		// Comprobar si el primer grupo tiene cambios para aplicar al resto y evitar que si el valor de los campos coincide con el anterior registro
		// no se totalize el grupo (ver comentario en variable change).
		if (size() > 0 && ((ReportGroup)get(0)).getRecordCount() > 0 && ((ReportGroup)get(0)).isChangeValues(dataObject))	
			change = true;

		// Generar totales por grupos desdel el último hasta el primero para poder obtener el efecto de filas de totales por agrupación en el orden correcto.
		for(int i = size()-1; i > -1; i--) {
			
			group = (ReportGroup)get(i);

			if (group.isVisible()){
				
				if (group.getRecordCount() > 0 && (group.isChangeValues(dataObject) || change))	{
				
					if (report.isShowDetail()){
						report.addRowIndex();
						group.addGroupRows();
					}
					
					group.generateTotalFields();
					group.initialize();
				}
			}
		}

		// 2. Pintar las cabeceras de los grupos si cambian los valores de las claves de los grupos.
		if (dataObject != null) {

			change = false;
			
			for(int i = 0; i < size(); i++)	{
				
				group = (ReportGroup)get(i);

				if (group.isVisible()){
				
					group.totalize(dataObject);
		
					if (group.isChangeValues(dataObject) || change) {
						
						change = true;
						
						group.setValues(dataObject);
						
						group.generateHeaders(0 + i); //report.getInitColumnIndex() + i);
						//group.generateHeaders(report.getInitColumnIndex() + i);
						
						// Inicializar el resto de grupos
						for(int k = i + 1; k < size(); k++) {
							((ReportGroup)get(k)).initialize();
						}
					}
				}
			}
		}
		
	}

}


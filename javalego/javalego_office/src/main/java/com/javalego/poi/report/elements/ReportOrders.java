package com.javalego.poi.report.elements;

import java.util.ArrayList;

import com.javalego.poi.report.elements.ReportOrderField;

/**
 * Descripción breve de ReportOrders.
 */
public class ReportOrders
{
	ArrayList<ReportOrderField> fields = new ArrayList<ReportOrderField>();

	public ReportOrders()
	{
	}

	public void addField()
	{
		ReportOrderField field = new ReportOrderField();
		/*field.Column = col;
		field.FieldName = col.ColumnName;*/
		fields.add(field);
	}

}


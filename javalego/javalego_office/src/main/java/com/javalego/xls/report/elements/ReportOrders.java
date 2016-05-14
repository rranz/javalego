package com.javalego.xls.report.elements;

import java.util.ArrayList;

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


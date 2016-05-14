package com.javalego.xls.report.elements;

/**
 * Clase abstracta que define un campo asociado a un informe. Clases derivadas: ReportField, ReportTotalField.
 */
public abstract class ReportCustomField
{
	
	protected String description;
	//ExcelWorkbook.RangeFormat format = ExcelWorkbook.RangeFormat.General;

	public ReportCustomField()
	{
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/// <summary>
	/// Establece el formato de la columna
	/// </summary>
	/// <param name="dataType"></param>
	//public void SetFormat() { //DataColumn column)
		// Format
		/*if (column != null)
		{
			if (Utilities.IsColumnCurrency(column.DataType))
				format = ExcelWorkbook.RangeFormat.Currency;
			else if (Utilities.IsColumnInteger(column.DataType))
				format = ExcelWorkbook.RangeFormat.Integer;
			else if (column.DataType == typeof(DateTime))
				format = ExcelWorkbook.RangeFormat.Date;
			else if (Utilities.IsColumnNumeric(column.DataType))
				format = ExcelWorkbook.RangeFormat.Numeric;
			else
				format = ExcelWorkbook.RangeFormat.Text;
		}*/
	//}
}


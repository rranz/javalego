package com.javalego.excel.report;

import com.javalego.exception.LocalizedException;

/**
 * Generación de un informe.
 * 
 * @author ROBERTO RANZ
 *
 */
public interface ReportModel {

	abstract public String getTitle();
	
	abstract public String getSubTitle();
	
	abstract public String getHeader(String name);
	
	abstract public Object getValue(int row, int column) throws LocalizedException;

	public abstract int getColumns();

	public abstract String getHeader(int column);

	public abstract int getRows() throws LocalizedException;
}

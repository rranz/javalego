package com.javalego.poi.report;

import com.javalego.util.StringUtils;

/**
 * Clase que nos permitie exportar una hoja de excel a un archivo csv con
 * una determinada configuración.
 * @author ROBERTO RANZ
 *
 */
public class ExcelToCSV {
	
	/**
	 * Fichero de entrada Excel.
	 */
	private String fileNameExcel;
	/**
	 * Fichero de salida CSV
	 */
	private String fileNameCSV;
	/**
	 * Separador de campos.
	 */
	private String separatorFields = ",";
	/**
	 * Formato aplicado de celdas numéricas
	 */
	private String formatNumber = "######.0#####";
	/**
	 * Formato de fecha
	 */
	private String formatDate = "dd/MM/yyyy";
	/**
	 * Incluir una fila con el nombre de cada sheet.
	 */
	private boolean includeRowSheetName = true;
	
	public void execute() {
		ExcelWorkbookXSSF e = new ExcelWorkbookXSSF();
		try {
			e.loadFile(fileNameExcel);
			e.saveToCSV(fileNameCSV, separatorFields, formatNumber, formatDate, includeRowSheetName);
		} catch (Exception e1) {
			System.out.println("ERROR DE EJECUCIÓN EXCEL A CSV: " + fileNameExcel + " a " + fileNameCSV);
			e1.printStackTrace();
		}
	}

	public String getFileNameCSV() {
		return fileNameCSV;
	}

	public void setFileNameCSV(String fileNameCSV) {
		this.fileNameCSV = fileNameCSV;
	}

	public String getFileNameExcel() {
		return fileNameExcel;
	}

	public void setFileNameExcel(String fileNameExcel) {
		this.fileNameExcel = fileNameExcel;
	}

	public String getFormatDate() {
		return formatDate;
	}

	public void setFormatDate(String formatDate) {
		this.formatDate = formatDate;
	}

	public String getFormatNumber() {
		return formatNumber;
	}

	public void setFormatNumber(String formatNumber) {
		this.formatNumber = formatNumber;
	}

	public boolean isIncludeRowSheetName() {
		return includeRowSheetName;
	}

	public void setIncludeRowSheetName(boolean includeRowSheetName) {
		this.includeRowSheetName = includeRowSheetName;
	}

	public String getSeparatorFields() {
		return separatorFields;
	}

	public void setSeparatorFields(String separatorFields) {
		this.separatorFields = separatorFields;
	}
	
	/**
	 * Ejecución del proceso de conversión de un archivo Excel a un archivo CSV.
	 * Parámetros:
	 * 1. fileNameExcel
	 * 2. fileNameCSV
	 * 3. decimales (opcional)
	 * @param args
	 */
	public static void main(String args[]){
		args = new String[] { "D:\\1_2_15.xls", "D:\\result.csv"};
		if (args.length < 2)
			System.out.println("Debe especificar como parámetros el fichero Excel y el fichero CSV de salida.");
		else{
			ExcelToCSV e = new ExcelToCSV();
			e.setFileNameExcel(args[0]);
			
			// Decimales.
			if (args.length == 3){
				int decimals = new Integer(args[2]).intValue();
				if (decimals > 0){
					String format = StringUtils.replicate("#", decimals-1);
					e.setFormatNumber("######.0" + format);
				}
				else if (decimals == 0){
					e.setFormatNumber("######");
				}
			}
			
			e.setFileNameCSV(args[1]);
			System.out.println("Convirtiendo " + e.getFileNameExcel() + " a " + e.getFileNameCSV() + " ...");
			e.execute();
			System.out.println("PROCESO FINALIZADO CORRECTAMENTE");
		}
	}

}

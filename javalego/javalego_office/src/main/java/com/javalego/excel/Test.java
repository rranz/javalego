package com.javalego.excel;

import com.javalego.excel.jxl.ExcelWorkbookJxl;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		reader();
		//writer();
	}

	public static void reader() {

		try {

			String filename = "C:\\TEMP\\empresas.xls";

			ExcelWorkbook ew = new ExcelWorkbookJxl().loadFile(filename);

			System.out.println(ew.getValue(1, ew.getIndexColumn(0,"id")));
			System.out.println(ew.getValue(1, ew.getIndexColumn(0,"nombre")));
			System.out.println(ew.getValue(1, ew.getIndexColumn(0,"localidad")));
//			System.out.println(ew.getValue(0,"D"));
//			System.out.println(ew.getValue(0,"E"));
			
//			for (int i = 0; i < ew.getRows(); i++) {
//				for (int k = 0; k < ew.getColumns(); k++) {
//					System.out.println(ew.getValue(i, k));
//				}
//			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void writer() {

		try {

			String filename = "C:\\TEMP\\empresas_out.xls";

			ExcelWorkbookJxl ew = new ExcelWorkbookJxl();
			ew.setFile(filename);
			ew.setValue(0, 1, "HOLA CARA COLA");
			ew.setValueBold(0, 2, "HOLA CARA COLA");
			ew.setValueBold(0, 3, "HOLA");
			ew.setValueBold(0, 4, "Hola");
			ew.sheetAutoFitColumns();
			
			ew.write();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}

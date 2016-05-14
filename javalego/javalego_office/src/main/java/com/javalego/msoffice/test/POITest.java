package com.javalego.msoffice.test;

import com.javalego.xls.report.ExcelWorkbook;

public class POITest {

	public static void main(String args[]) {
		
		ExcelWorkbook e = new ExcelWorkbook();
		try {
			e.loadFile("h:\\workbook.xls");
			e.insertImage("h:\\logo_anyhelp.gif", 12, 4);
			e.writeFile();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
}

package com.javalego.test.office.excel;

import java.io.InputStream;

import org.junit.BeforeClass;
import org.junit.Test;

import com.javalego.excel.ExcelWorkbook;
import com.javalego.excel.jxl.ExcelWorkbookJxl;

public class ReaderTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	public void reader() throws Exception {

		InputStream filename = getClass().getClassLoader().getResourceAsStream("in/empresas.xls");

		ExcelWorkbook ew = new ExcelWorkbookJxl().loadFile(filename);

		System.out.println(ew.getValue(3, ew.getIndexColumn(2, "id")));
		System.out.println(ew.getValue(3, ew.getIndexColumn(2, "nombre")));
		System.out.println(ew.getValue(3, ew.getIndexColumn(2, "razon_social")));
		System.out.println(ew.getValue(3, "A"));
		System.out.println(ew.getValue(3, "B"));

		for (int i = 0; i < ew.getRows(); i++) {
			for (int k = 0; k < ew.getColumns(); k++) {
				System.out.println(ew.getValue(i, k));
			}
		}

	}

	@Test
	public void writer() throws Exception {

		String filename = "out/empresas.xls";

		ExcelWorkbookJxl ew = new ExcelWorkbookJxl();
		ew.setFile(filename);
		ew.setValue(0, 1, "HOLA CARA COLA");
		ew.setValueBold(0, 2, "HOLA CARA COLA");
		ew.setValueBold(0, 3, "HOLA");
		ew.setValueBold(0, 4, "Hola");
		ew.sheetAutoFitColumns();

		ew.write();

	}
}

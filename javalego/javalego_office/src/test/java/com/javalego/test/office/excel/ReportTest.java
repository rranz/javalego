package com.javalego.test.office.excel;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.junit.BeforeClass;
import org.junit.Test;

import com.javalego.excel.ExcelWorkbook;
import com.javalego.excel.jxl.ExcelWorkbookJxl;
import com.javalego.excel.report.ReportModelBeans;
import com.javalego.excel.report.ReportWriter;
import com.javalego.excel.report.impl.ReportExcelModelBeans;
import com.javalego.excel.report.impl.ReportExcelWriter;
import com.javalego.ui.field.FieldModel;

public class ReportTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	public void report() throws Exception {

		List<Contacto> beans = new ArrayList<Contacto>();
		Contacto c = new Contacto();
		c.setNombre("ROBERTO PEREZ");
		c.setLocalidad("MADRID");
		c = new Contacto();
		c.setNombre("MAYTE GEEEE");
		c.setLocalidad("SEVILLA");
		beans.add(c);

		List<FieldModel> model = new ArrayList<FieldModel>();
		// model.add(new TextFieldModel("nombre", "Nombre"));
		// model.add(new TextFieldModel("localidad", "Localidad"));

		// Modelo de informes compuesto por una lista de beans y un modelo
		// de configuración de campos a imprimir.
		ReportModelBeans<Contacto> rmodel = new ReportExcelModelBeans<Contacto>(beans, model, "CONTACTOS", Locale.getDefault());

		// Generador de ficheros Excel (API JExcepAPI)
		ExcelWorkbook excel = new ExcelWorkbookJxl();

		// Generador de informes de beans a partir de un modelo de
		// configuración de campos.
		ReportWriter report = new ReportExcelWriter(rmodel, excel);

		// Fichero de salida
		excel.setFile(new FileOutputStream("out/report.xls"));

		// Ejecutar informe
		report.run();

	}

}

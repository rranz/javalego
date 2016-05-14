package com.javalego.excel.report.impl;

import com.javalego.excel.ExcelWorkbook;
import com.javalego.excel.report.ReportModel;
import com.javalego.excel.report.ReportWriter;
import com.javalego.exception.LocalizedException;

/**
 * Generar informe en Excel
 * 
 * @author ROBERTO RANZ
 * 
 */
public class ReportExcelWriter implements ReportWriter {

	private ReportModel model;

	private ExcelWorkbook workbook;

	private int row = 0;

	public ReportExcelWriter(ReportModel model, ExcelWorkbook workbook) {
		this.model = model;
		this.workbook = workbook;
	}

	@Override
	public void writeTitle() throws LocalizedException {

		workbook.setValueBold(row, 0, model.getTitle());

		if (model.getSubTitle() != null) {
			workbook.setValue(++row, 0, model.getSubTitle());
		}
		else {
			++row;
		}
		++row;
	}

	@Override
	public void writeHeaders() throws LocalizedException {

		for (int i = 0; i < model.getColumns(); i++) {
			workbook.setValueBold(row, i, model.getHeader(i));
		}
		++row;

	}

	@Override
	public void writeRecords() throws LocalizedException {

		for (int i = 0; i < model.getRows(); i++) {
			for (int k = 0; k < model.getColumns(); k++) {
				workbook.setValue(row, k, model.getValue(i, k));
			}
			++row;
		}
	}

	@Override
	public void writeFooter() throws LocalizedException {
	}

	@Override
	public void run() throws LocalizedException {

		if (workbook != null && workbook.hasWoorkbook()) {
			writeTitle();
			writeHeaders();
			writeRecords();
			writeFooter();
			workbook.sheetAutoFitColumns();
			workbook.write();
		}
		else {
			throw new LocalizedException("Workbook not exist. Must define an output file");
		}
	}
}

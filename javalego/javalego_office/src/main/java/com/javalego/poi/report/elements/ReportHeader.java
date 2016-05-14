package com.javalego.poi.report.elements;

import java.util.ArrayList;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;

import com.javalego.poi.report.BasicReport;
import com.javalego.poi.report.ReportVisualStyle;
import com.javalego.poi.report.elements.IReportItem;

/**
 * Descripción breve de ReportHeader.
 */
public class ReportHeader implements IReportItem {

	public static final int ROW_HEIGHT = 400;
	
	protected String title;
	
	protected String subTitle;
	
	protected ArrayList<String> headers;
	
	protected BasicReport report;
	
	// Logotipos
	protected byte[] logo1;
	
	protected byte[] logo2;
	
	protected int titleRow;
	protected int subtitleRow;
	
	public ReportHeader(BasicReport report)	{
		this.report = report;
	}

	/**
	 * Generar título y subtítulo junto con sus logos.
	 */
	@Override
	public void generate() throws Exception {
		
		int col = 0; 
		int row = report.getRowIndex();
		
		if (title != null){
			report.addRowIndex();
			
			ReportVisualStyle style = new ReportVisualStyle(report.getExcel());
			
			style.setFontBold(true);
			
			if (hasLogos())
				style.setAlignment(CellStyle.ALIGN_CENTER);
			
			style.setForeColor(HSSFColor.ORANGE.index);
			style.setFontName(report.getFontNameDefault());
			
			if (!hasLogos())
				style.setFontSize(14);
			
			Cell cell = report.getExcel().getCell(report.getRowIndex(), col);
			
			titleRow = report.getRowIndex();
			
			// Altura para el font establecido.
			report.getExcel().setRowHeight(titleRow, ROW_HEIGHT);
			
			cell.setCellValue(report.getExcel().createRichTextString(title));
			
			style.setApplyStyle(cell);
			
			report.addRowIndex();
		}
		
		if (subTitle != null){
			
			if (row < 0)
				report.addRowIndex();
			
			ReportVisualStyle style = new ReportVisualStyle(report.getExcel());
			
			style.setFontBold(true);
			
			if (hasLogos())
				style.setAlignment(CellStyle.ALIGN_CENTER);
			
			style.setFontName(report.getFontNameDefault());
			
			if (!hasLogos())
				style.setFontSize(12);
			
			Cell cell = report.getExcel().getCell(report.getRowIndex(), col);
			
			subtitleRow = report.getRowIndex();
			
			// Altura para el font establecido.
			report.getExcel().setRowHeight(subtitleRow, ROW_HEIGHT);
			
			cell.setCellValue(report.getExcel().createRichTextString(subTitle));
			
			style.setApplyStyle(cell);
			
			report.addRowIndex();
		}
		
		if (hasLogos())
			report.setRowIndex(5);
		
		row = row < 0 ? 0 : row;

	}

	/**
	 * Generar logos
	 */
	public void generateLogos() throws Exception {
		
		// Logotipos
		if (logo1 != null)
			report.getExcel().insertImageFromResource(logo1, titleRow, 0);
		
		if (logo2 != null) {
			report.getExcel().insertImageFromResource(logo2, titleRow, report.getFieldCount()+ (logo1 != null ? 1 : 0));
		}
	}
	
	/**
	 * Comprueba si tiene logotipos definidos
	 * @return
	 */
	public boolean hasLogos() {
		return logo1 != null || logo2 != null;
	}

	/**
	 * Comprueba si tiene el logo derecho
	 * @return
	 */
	public boolean hasRightLogo() {
		return logo2 != null;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;

	}

	public void addHeader(String header){
		headers.add(header);
	}
	
	public ArrayList<String> getHeaders() {
		return headers;
	}

	public void setHeaders(ArrayList<String> headers) {
		this.headers = headers;
	}

	public BasicReport getReport() {
		return report;
	}

	public void setReport(BasicReport report) {
		this.report = report;
	}

	public byte[] getLogo1() {
		return logo1;
	}

	public void setLogo1(byte[] logo1) {
		this.logo1 = logo1;
	}

	public byte[] getLogo2() {
		return logo2;
	}

	public void setLogo2(byte[] logo2) {
		this.logo2 = logo2;
	}

	public int getTitleRow() {
		return titleRow;
	}

	public void setTitleRow(int titleRow) {
		this.titleRow = titleRow;
	}

	public int getSubtitleRow() {
		return subtitleRow;
	}

	public void setSubtitleRow(int subtitleRow) {
		this.subtitleRow = subtitleRow;
	}

}

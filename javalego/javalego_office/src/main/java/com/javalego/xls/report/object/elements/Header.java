package com.javalego.xls.report.object.elements;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.util.HSSFColor;

import com.javalego.xls.report.ReportVisualStyle;
import com.javalego.xls.report.elements.ReportHeader;
import com.javalego.xls.report.object.ReportObject;

/**
 * Cabecera del informe. Título y subtítulo.
 * @author ROBERTO RANZ
 */
public class Header extends BasicElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected String title;
	
	protected String subTitle;
	
	public Header(ReportObject report) {
		super(report);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Generar título y subtítulo
	 */
	@Override
	public void generate() throws Exception {
		
		if (title != null){
			
			report.addRowIndex();
			style.setFontBold(true);
			style.setForeColor(HSSFColor.ORANGE.index);
			style.setFontName(report.getFontNameDefault());
			style.setFontSize(14);
			HSSFCell cell = report.getExcel().getCell(report.getRowIndex(), 0);
			cell.setCellValue(new HSSFRichTextString(title));
			style.setApplyStyle(cell);
			report.getExcel().setRowHeight(report.getRowIndex(), ReportHeader.ROW_HEIGHT);
			report.addRowIndex();
		}
		if (subTitle != null){
			
			ReportVisualStyle style = new ReportVisualStyle(report.getExcel());
			style.setFontBold(true);
			style.setFontName(report.getFontNameDefault());
			style.setFontSize(12);
			HSSFCell cell = report.getExcel().getCell(report.getRowIndex(), 0);
			cell.setCellValue(new HSSFRichTextString(subTitle));
			style.setApplyStyle(cell);
			report.getExcel().setRowHeight(report.getRowIndex(), ReportHeader.ROW_HEIGHT);
			report.addRowIndex();
		}
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public void setTitle(String title) {
		this.title = title;
	}

}

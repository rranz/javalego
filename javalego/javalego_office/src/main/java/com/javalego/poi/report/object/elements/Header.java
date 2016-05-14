package com.javalego.poi.report.object.elements;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;

import com.javalego.poi.report.ReportVisualStyle;
import com.javalego.poi.report.object.ReportObject;
import com.javalego.poi.report.object.elements.BasicElement;

/**
 * Cabecera del informe. Título y subtítulo.
 * @author ROBERTO RANZ
 */
public class Header extends BasicElement {

	private static final long serialVersionUID = 3478367011041895319L;

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
			style.setFontSize(16);
			Cell cell = report.getExcel().getCell(report.getRowIndex(), 0);
			cell.setCellValue(report.getExcel().createRichTextString(title));
			style.setApplyStyle(cell);
			report.addRowIndex();
		}
		if (subTitle != null){
			
			ReportVisualStyle style = new ReportVisualStyle(report.getExcel());
			style.setFontBold(true);
			style.setFontName(report.getFontNameDefault());
			style.setFontSize(14);
			Cell cell = report.getExcel().getCell(report.getRowIndex(), 0);
			cell.setCellValue(report.getExcel().createRichTextString(subTitle));
			style.setApplyStyle(cell);
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

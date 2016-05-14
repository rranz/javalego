package com.javalego.xls.report;

import java.text.NumberFormat;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;

import com.javalego.util.StringUtils;

/**
 * Clase que nos permite definir todas las características visuales que deseamos especificar en
 * cualquier elemento que compone un informe.
 *
 * @author ROBERTO
 */
public class ReportVisualStyle {

	/**
	 * Tipos de formato disponibles
	 */
	static public final String FORMAT_MONEY = "money", FORMAT_PERCENT = "percent", FORMAT_INTEGER = "integer", FORMAT_DECIMAL = "decimal";

	private ExcelWorkbook excel;
	private String format;
	private String fontName;
	private short foreColor = -1;
	private short backgroundColor = -1;
	private boolean fontBold;
	private boolean fontStrikeout;
	private boolean fontUnderline;
	private boolean fontItalic;
	private boolean moneySymbol = true;
	private boolean box;
	// Centrar la alineación de la celda verticalmente.
	private boolean alignVerticalCenter;
	private boolean wrapText;
	private int fontSize = -1;
	private short alignment = -1;
	private HSSFCellStyle style;
	private HSSFFont font;

	public ReportVisualStyle(ExcelWorkbook excel){
		this.excel = excel;
	}
	
	/**
	 * Aplicar estilo visual a la celda.
	 * @param cell
	 */
	public void setApplyStyle(HSSFCell cell) {

		if (style == null) {
			style = excel.createStyle();
		}
		
		// Font
		if (font == null) {
			font = excel.createFontStyle();
		}
		
		setApplyStyle(cell, style, font);
	}

	/**
	 * Aplicar estilo visual a la celda.
	 * @param cell
	 */
	public void setApplyStyle(HSSFCell cell, HSSFCellStyle style, HSSFFont font) {

		// Cell
		if (box) {
			excel.setBoxCellStyle(style, HSSFColor.WHITE.index);
		}

		if (backgroundColor > -1) {
		  style.setFillForegroundColor(backgroundColor);
		}
		else {
			style.setFillForegroundColor(HSSFColor.WHITE.index);
		}

		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);

		if (wrapText) {
			style.setWrapText(true);
		}
		
		if (alignment > -1) {	
			style.setAlignment(alignment);
		}
		
		if (fontName != null) {
			font.setFontName(fontName);
		}
		
		if (foreColor > -1) {
			font.setColor(foreColor);
		}
		
		if (fontBold) {
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		}
		
		font.setStrikeout(fontStrikeout);

		font.setItalic(fontItalic);

		font.setUnderline(fontUnderline ? HSSFFont.U_SINGLE : HSSFFont.U_NONE);

		if (fontSize > -1) 
			font.setFontHeightInPoints((short) fontSize);

		style.setFont(font);

		// Format
		if (format != null) {
			HSSFDataFormat dataFormat = excel.getWorkBook().createDataFormat();
			if (format.toLowerCase().equals("money")) {
				if (moneySymbol)
					style.setDataFormat(dataFormat.getFormat("#,##0.00 " + NumberFormat.getNumberInstance().getCurrency().getSymbol()));
				else
					style.setDataFormat(dataFormat.getFormat("#,##0.00"));
			}
			else if (format.toLowerCase().equals("integer"))
				style.setDataFormat(dataFormat.getFormat("#,##0"));
			else if (format.toLowerCase().equals("decimal"))
				style.setDataFormat(dataFormat.getFormat("#,##0.######"));
			else if (format.toLowerCase().equals("percent"))
				style.setDataFormat(dataFormat.getFormat("#,##0.00 %")); //#.##0,00")); //"#,##0.00 %"));
			else
				style.setDataFormat(dataFormat.getFormat(format));
		  //style.setDataFormat(HSSFDataFormat.getBuiltinFormat(format));
		}
		else
			style.setDataFormat(HSSFDataFormat.getBuiltinFormat("General")); //(short)0);

		if (alignVerticalCenter) {
			style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			//style.setWrapText(true);
		}

		// Aplicar estilo a la celda.
		cell.setCellStyle(style);
	}

	public short getBackgroundColor() {
		return backgroundColor;
	}
	public void setBackgroundColor(short backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	/**
	 * Comprueba si el formato es de tipo porcentaje
	 * @return
	 */
	public boolean isPercentFormat() {
		return format != null && format.toLowerCase().equals("percent");
	}

	/**
	 * Comprueba si el formato es de tipo money
	 * @return
	 */
	public boolean isMoneyFormat() {
		return format != null && format.toLowerCase().equals("money");
	}

	/**
	 * Comprueba si el formato es de tipo integer
	 * @return
	 */
	public boolean isIntegerFormat() {
		return format != null && format.toLowerCase().equals("integer");
	}

	/**
	 * Comprueba si el formato es de tipo decimal
	 * @return
	 */
	public boolean isDecimalFormat() {
		return format != null && format.toLowerCase().equals("decimal");
	}

	public void setBackgroundColor(String backgroundColor) {
		
		if (!StringUtils.isEmpty(backgroundColor)) {
			this.backgroundColor = getColorFromString(backgroundColor);
		}
	}

	public boolean isFontBold() {
		return fontBold;
	}
	
	public void setFontBold(boolean fontBold) {
		this.fontBold = fontBold;
	}
	
	public String getFontName() {
		return fontName;
	}
	
	public void setFontName(String fontName) {
		this.fontName = fontName;
	}
	
	public int getFontSize() {
		return fontSize;
	}
	
	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}
	
	public short getForeColor() {
		return foreColor;
	}
	
	public void setForeColor(short foreColor) {
		this.foreColor = foreColor;
	}

	public void setForeColor(String foreColor) {
		
		if (!StringUtils.isEmpty(foreColor)) {
			this.foreColor = getColorFromString(foreColor);
		}
	}

	/**
	 * Obtener el color de la celda mediante un valor String y su equivalencia en HSSF.
	 * @param color
	 * @return
	 */
	static public short getColorFromString(String color) {
		
		if (color.equals("blue"))
			return HSSFColor.BLUE.index;
		
		else if (color.equals("gray"))
			return HSSFColor.GREY_50_PERCENT.index;
		
		else if (color.equals("silver"))
			return HSSFColor.GREY_25_PERCENT.index;
		
		else if (color.equals("black"))
			return HSSFColor.BLACK.index;
		
		else if (color.equals("green"))
			return HSSFColor.GREEN.index;
		
		else if (color.equals("aqua"))
			return HSSFColor.AQUA.index;
		
		else if (color.equals("white"))
			return HSSFColor.WHITE.index;
		
		else if (color.equals("maroon"))
			return HSSFColor.DARK_RED.index;
		
		else if (color.equals("red"))
			return HSSFColor.RED.index;
		
		else if (color.equals("yellow"))
			return HSSFColor.YELLOW.index;
		
		else if (color.equals("dark_yellow"))
			return HSSFColor.GOLD.index;
		
		else if (color.equals("dark_green"))
			return HSSFColor.DARK_GREEN.index;
		
		else if (color.equals("dark_red"))
			return HSSFColor.DARK_RED.index;
		
		else if (color.equals("coral"))
			return HSSFColor.CORAL.index;
		
		else if (color.equals("navy"))
			return HSSFColor.DARK_BLUE.index;
		
		else if (color.equals("brown"))
			return HSSFColor.BROWN.index;
		
		else if (color.equals("orange"))
			return HSSFColor.ORANGE.index;
		
		else if (color.equals("teal"))
			return HSSFColor.TEAL.index;
		
		else if (color.equals("turquoise"))
			return HSSFColor.TURQUOISE.index;
		
		else if (color.equals("gold"))
			return HSSFColor.GOLD.index;
		else
			return HSSFColor.AUTOMATIC.index;
	}

	public boolean isBox() {
		return box;
	}

	public void setBox(boolean box) {
		this.box = box;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public short getAlignment() {
		return alignment;
	}

	public void setAlignment(short alignment) {
		this.alignment = alignment;
	}

	public boolean isFontStrikeout() {
		return fontStrikeout;
	}

	public void setFontStrikeout(boolean fontStrikeout) {
		this.fontStrikeout = fontStrikeout;
	}

	public boolean isFontUnderline() {
		return fontUnderline;
	}

	public void setFontUnderline(boolean fontUnderline) {
		this.fontUnderline = fontUnderline;
	}

	public boolean isFontItalic() {
		return fontItalic;
	}

	public void setFontItalic(boolean fontItalic) {
		this.fontItalic = fontItalic;
	}

	public boolean isMoneySymbol() {
		return moneySymbol;
	}

	public void setMoneySymbol(boolean moneySymbol) {
		this.moneySymbol = moneySymbol;
	}

	public boolean isWrapText() {
		return wrapText;
	}

	public void setWrapText(boolean wrapText) {
		this.wrapText = wrapText;
	}

	public boolean isAlignVerticalCenter() {
		return alignVerticalCenter;
	}

	public void setAlignVerticalCenter(boolean alignVerticalCenter) {
		this.alignVerticalCenter = alignVerticalCenter;
	}

	public ExcelWorkbook getExcel() {
		return excel;
	}

	public HSSFCellStyle getStyle() {
		return style;
	}

	public void setStyle(HSSFCellStyle style) {
		this.style = style;
	}
}

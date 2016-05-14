package com.javalego.poi.report.object.elements;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;

import com.javalego.poi.report.ExcelWorkbookXSSF;
import com.javalego.poi.report.ReportVisualStyle;
import com.javalego.poi.report.object.ReportObject;
import com.javalego.util.StringUtils;

/**
 * Propiedad del objeto de impresión. Genera dos columnas una para el título y otra para el valor de la propiedad.
 * 
 * @author ROBERTO RANZ
 */
public class Property extends BasicElement {

	private static final long serialVersionUID = 3065628564724727856L;

	/**
	 * Nombre de la propiedad.
	 */
	protected String name;

	/**
	 * No imprimir valor cero.
	 */
	protected boolean notPrintZero;

	/**
	 * Valor de la propiedad que podemos definir para no recuperarla del objeto. Esta propiedad es útil si necesitamos formatear dicha información cuando se trata de propiedades de un objeto especiales.
	 * Ej.: CalendarProperty del framework de Gana donde la clase ObjectDocument establece este valor formateado, evitando mostrar el texto en formato xml.
	 */
	protected Object value;

	/**
	 * Reglas de visualización.
	 */
	private ArrayList<VisualRule> visualRules = new ArrayList<VisualRule>();

	public Property(ReportObject report) {
		super(report);
	}

	/**
	 * Generar campo y valor.
	 */
	@Override
	public void generate() throws Exception {

		// report.addRowIndex();

		if (!StringUtils.isEmpty(title))
			generateHeader();

		int columnIndex = report.getColumnIndex() + 1;

		ExcelWorkbookXSSF excel = report.getExcel();
		Cell cell;

		Object value = null;

		// Asignar el valor definido en esta clase cuando queremos aplicar un valor específico o formateado y no tomarlo del objeto.
		if (this.value != null) {
			value = this.value;
		}
		else {
			if (!StringUtils.isEmpty(name))
				value = report.getPropertyValue(name);
			else
				value = "";
		}

		// Dividir por 100 cuando se desea mostrar el valor con formato de porcentaje.
		if (value != null && value instanceof Number && style.isPercentFormat()) {
			value = ((Number) value).doubleValue() / 100;
		}

		boolean noPrint = false;

		cell = excel.getCell(report.getRowIndex(), columnIndex);

		// Valor boolean poner Sí o No
		if (value != null && value instanceof Boolean) {
			excel.setCellValue(cell, StringUtils.getTextBoolean(((Boolean) value)));
		}
		// Este código nos permite mostrar información de clases complejas donde delegamos que en el método toString() se muestra la información que las represente.
		else if (value == null || value instanceof String || value instanceof Number || value instanceof Date || value instanceof Boolean || value instanceof Time) {

			// No imprimir si el valor numérico == 0 y notPrintZero == true.
			if (value != null && value instanceof Number && notPrintZero && ((Number) value).intValue() == 0) {
				noPrint = true;
				excel.setCellValue(cell, "");
			}
			else
				excel.setCellValue(cell, value);
		}
		else {
			excel.setCellValue(cell, value.toString());
			style.setAlignment(CellStyle.ALIGN_JUSTIFY);
		}

		if (value != null && (value instanceof String || value instanceof Date))
			style.setAlignment(CellStyle.ALIGN_JUSTIFY);

		// Establecer la tipología de la celda. Nota: los boolean se han modificado para imprimir Sí o No y no como celda boolean.
		if (!noPrint) {
			if (value != null && value instanceof Boolean)
				cell.setCellType(Cell.CELL_TYPE_STRING);
			else
				cell.setCellType(excel.getCellTypeFromValue(value));
		}

		// Estilo de celda con respecto al tipo de valor.
		style.setBox(true);
		if (style.getForeColor() < 0)
			style.setForeColor(HSSFColor.BLACK.index);
		if (style.getBackgroundColor() < 0)
			style.setBackgroundColor(HSSFColor.WHITE.index);

		if (style.getFontName() == null)
			style.setFontName(report.getFontNameDefault());

		if (!noPrint && style.getFormat() == null) {
			if (value instanceof Date){
			  String format = "d-mmm-yy"; 
				style.setFormat(format);
			}
			else if (value instanceof Number) {
				if (value instanceof Integer)
					style.setFormat("\\#,\\#\\#0"); 
				else
					style.setFormat("\\#,\\#\\#0.00"); 
			}				
		}

		// Aplicar reglas de visualización definidas.
		if (visualRules.size() > 0) {
			for (int i = 0; i < visualRules.size(); i++) {
				visualRules.get(i).execute(report.getObject(), style, visualRules.get(i).getExpression());
			}
		}

		style.setApplyStyle(cell);
	}

	/**
	 * Generar el título de la propiedad.
	 * 
	 * @throws Exception
	 */
	private void generateHeader() throws Exception {

		ReportVisualStyle style = new ReportVisualStyle(report.getExcel());

		int columnIndex = report.getColumnIndex();

		ExcelWorkbookXSSF excel = report.getExcel();
		Cell cell = excel.getCell(report.getRowIndex(), columnIndex);

		excel.setCellValue(cell, title == null ? " " : title);

		style.setAlignment(CellStyle.ALIGN_LEFT);

		// Estilo de celda con respecto al tipo de valor.
		style.setBox(true);
		if (style.getForeColor() < 0)
			style.setForeColor(HSSFColor.WHITE.index);
		if (style.getBackgroundColor() < 0)
			style.setBackgroundColor(HSSFColor.BLUE_GREY.index);

		if (style.getFontName() == null)
			style.setFontName(report.getFontNameDefault());

		// comentario
		if (!StringUtils.isEmpty(description))
			report.getExcel().setCellComment(description, report.getRowIndex(), columnIndex);

		style.setApplyStyle(cell);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<VisualRule> getVisualRules() {
		return visualRules;
	}

	public void setVisualRules(ArrayList<VisualRule> visualRules) {
		this.visualRules = visualRules;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public boolean isNotPrintZero() {
		return notPrintZero;
	}

	public void setNotPrintZero(boolean notPrintZero) {
		this.notPrintZero = notPrintZero;
	}
}

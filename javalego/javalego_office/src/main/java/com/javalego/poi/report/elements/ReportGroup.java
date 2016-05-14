package com.javalego.poi.report.elements;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.javalego.poi.report.BasicReport;
import com.javalego.poi.report.ExcelWorkbookXSSF;
import com.javalego.poi.report.ReportVisualStyle;

/**
 * Grupo de campos de un informe
 */
public class ReportGroup implements Serializable {

	private static final long serialVersionUID = 8409323960038872611L;

	/**
	 * Report al que está vinculado del grupo.
	 */
	private final BasicReport report;

	/**
	 * Totalizar campos numéricos
	 */
	private ReportTotalFields totalFields;

	/**
	 * Estilo de cabecera de campos
	 */
	private ReportVisualStyle styleHeader;

	/**
	 * Estilo de campos a totalizar
	 */
	private ReportVisualStyle styleTotals;

	/**
	 * Estilo del número de registros.
	 */
	private final ReportVisualStyle styleRecordCount;

	/**
	 * Lista de campos que conforman la agrupación.
	 */
	private ArrayList<ReportGroupField> groupFields = new ArrayList<ReportGroupField>();

	/**
	 * Número de registros del grupo
	 */
	private int recordCount = 0;

	/**
	 * Número de columna donde se se incluyen los campos de cabecera del grupo.
	 */
	private int columnIndex = 0;

	/**
	 * mostrar el número de registros del grupo
	 */
	private boolean showRecordCount;

	/**
	 * Incluir el nombre de los campos junto a su valor en el encabezado de cada agrupación.
	 */
	private boolean showFieldNames;

	/**
	 * Mostrar el total de registros por grupo.
	 */
	private boolean showTotals = true;

	/**
	 * Mostrar el porcentaje de registros con respecto al total
	 */
	private boolean showPercent;

	/**
	 * Activar o desactivar grupo
	 */
	boolean visible = true;

	// Posiciones de los registros asociados al grupo y que servirán para crear grupos de filas en Excel.
	int rowInit = -1;

	private boolean rowGroupCollapsed = false;

	// Longitud de caracteres a visualizar.
	private int displayWidth = -1;

	// longitud máxima impresa que servirá para establecer el ancho de la columna.
	private int maxWidth;

	public ReportGroup(BasicReport report) {
		this.report = report;

		styleTotals = new ReportVisualStyle(report.getExcel());

		styleHeader = new ReportVisualStyle(report.getExcel());
		// Aumentar en dos puntos el tamaño de la fuente
		styleHeader.setFontSize(report.getExcel().getFontStyle().getFontHeightInPoints() + 1);

		styleRecordCount = new ReportVisualStyle(report.getExcel());
		styleRecordCount.setFontSize(report.getExcel().getFontStyle().getFontHeightInPoints() - 3);
		styleRecordCount.setForeColor(ReportVisualStyle.getColorFromString("gray"));

		totalFields = new ReportTotalFields(report);
	}

	/**
	 * Comprobar si hemos cambiado de grupo en la iteración de filas.
	 */
	public boolean isChangeValues(Object dataObject) throws Exception {

		if (dataObject == null)
			return true;

		for (ReportGroupField field : groupFields) {

			Object value = report.getFieldValue(dataObject, field.getField().getFieldName());

			// No distinguir entre mayúsculas y minúsculas.
			// System.out.println(field.getValue() + "   " + value);
			if (field.getValue() == null && value == null)
				return false;
			if (field.getValue() == null || !field.getValue().toString().toLowerCase().equals(value != null ? value.toString().toLowerCase() : null))
				return true;
		}
		return false;
	}

	/**
	 * Definir un campo a totalizar para ir acumulando los importes por grupo.
	 */
	public void addGroupField(ReportField field, boolean hideField) {

		groupFields.add(new ReportGroupField(field, hideField));
	}

	/**
	 * Añadir todos los campos totalizar en el informe en el grupo.
	 */
	public void addTotalFields() {

		for (ReportTotalField field : report.getTotalFields()) {
			totalFields.add(field.getField(), field.getFormula());
		}
	}

	/**
	 * Poner a cero los campos a totalizar
	 */
	public void initialize() {

		totalFields.initialize();
		recordCount = 0;
		rowInit = 0;
	}

	/**
	 * Establecer el valor del grupo
	 * 
	 * @param dataObject
	 * @throws Exception
	 */
	public void setValues(Object dataObject) throws Exception {

		for (ReportGroupField field : groupFields) {

			Object value = report.getFieldValue(dataObject, field.getField().getFieldName());

			field.setValue(value);
		}
	}

	/**
	 * Poner a cero los campos a totalizar
	 */
	public void totalize(Object dataObject) throws Exception {

		++recordCount;
		totalFields.totalize(dataObject);
	}

	public ArrayList<ReportGroupField> getGroupFields() {
		return groupFields;
	}

	public void setGroupFields(ArrayList<ReportGroupField> groupfields) {
		this.groupFields = groupfields;
	}

	public boolean isShowFieldNames() {
		return showFieldNames;
	}

	public void setShowFieldNames(boolean showFieldNames) {
		this.showFieldNames = showFieldNames;
	}

	public boolean isShowRecordCount() {
		return showRecordCount;
	}

	public void setShowRecordCount(boolean showRecordCount) {
		this.showRecordCount = showRecordCount;
	}

	public boolean isShowTotals() {
		return showTotals;
	}

	public void setShowTotals(boolean showTotals) {
		this.showTotals = showTotals;
	}

	public int getRecordCount() {
		return recordCount;
	}

	// public void setRecordCount(int recordCount) {
	// this.recordCount = recordCount;
	// }

	/**
	 * Imprimir la cabecera del grupo con los campos definidos.
	 * 
	 * @throws Exception
	 */
	public void generateHeaders(int columnIndex) throws Exception {

		this.columnIndex = columnIndex;

		ExcelWorkbookXSSF excel = report.getExcel();
		Cell cell = null;
		report.addRowIndex();

		// Estilo de celda con respecto al tipo de valor.

		if (report.isShowDetail()) {

			styleHeader.setBox(false);
			styleHeader.setFontBold(true);

			if (styleHeader.getAlignment() < 0)
				styleHeader.setAlignment(CellStyle.ALIGN_LEFT);

			// Dependiendo del número de agrupaciones, ajustamos color y tamaño de fuente de letra..
			if (styleHeader.getForeColor() < 0) {

				if (columnIndex == 0) {
					styleHeader.setForeColor(HSSFColor.BROWN.index);
				}
				else if (columnIndex == 1) {
					styleHeader.setForeColor(HSSFColor.BLUE_GREY.index); // CORNFLOWER_BLUE
					styleHeader.setFontSize(11 - columnIndex);
				}
				else if (columnIndex == 2) {
					styleHeader.setForeColor(HSSFColor.GREY_50_PERCENT.index);
					styleHeader.setFontSize(11 - columnIndex);
				}
				else if (columnIndex > 2) {
					styleHeader.setForeColor(HSSFColor.GREY_40_PERCENT.index);
					styleHeader.setFontSize(11 - columnIndex);
				}
			}

			if (styleHeader.getBackgroundColor() < 0)
				styleHeader.setBackgroundColor(HSSFColor.BLUE.index);
			if (styleHeader.getFontName() == null)
				styleHeader.setFontName(report.getFontNameDefault());
		}
		else {
			styleHeader.setBox(true);
			if (styleHeader.getForeColor() < 0 || !report.isShowDetail())
				styleHeader.setForeColor(HSSFColor.WHITE.index);
			if (styleHeader.getBackgroundColor() < 0 || !report.isShowDetail())
				styleHeader.setBackgroundColor(HSSFColor.CORNFLOWER_BLUE.index);
		}
		rowInit = report.getRowIndex() + 1;

		// Campos del grupo.
		String value = "";
		for (ReportGroupField field : groupFields) {

			if (!value.equals(""))
				value += field.getSeparator() != null ? field.getSeparator() : " ";

			if (showFieldNames)
				value += field.getField().getTitle() + ": ";

			value += field.getField().getValue();
		}

		cell = excel.getCell(report.getRowIndex(), columnIndex);
		cell.setCellValue(excel.createRichTextString(value));
		styleHeader.setApplyStyle(cell);

		// Establecer la longitud máxima de caracteres del campo
		if (displayWidth < 0 && value != null) {
			if (value.length() > maxWidth)
				maxWidth = value.length();
		}

		// Establecer ancho de fila para que no se oculte la información con ancho por defecto.
		report.getExcel().setRowHeight(report.getRowIndex(), ReportHeader.ROW_HEIGHT);
	}

	/**
	 * Añadir agrupaciones de filas por grupo.
	 */
	public void addGroupRows() {

		if (rowInit > -1) {

			Sheet sheet = report.getExcel().getActiveSheet();

			sheet.groupRow((short) rowInit, (short) report.getRowIndex());

			if (rowGroupCollapsed)
				sheet.setRowGroupCollapsed((short) rowInit, true);
		}
	}

	/**
	 * Imprimir los campos a totalizar defindos en el grupo.
	 * 
	 * @throws Exception
	 */
	public void generateTotalFields() throws Exception {

		if (isShowTotals()) {

			// Modificar el estilo de la celda si no se imprime el detalle.
			if (!report.isShowDetail()) {

				for (ReportTotalField field : totalFields) {
					field.getStyle().setBackgroundColor(HSSFColor.WHITE.index);
					field.getStyle().setForeColor(HSSFColor.BLACK.index);
				}
			}

			// Sólo asociar el color del grupo cuando existen varios.
			if (report.getGroups().size() > 1)
				totalFields.generate(styleHeader.getForeColor());
			else
				totalFields.generate();

			// Añadir una nueva fila de separación y recortar su altura.
			if (isShowRecordCount() && !isShowPercent()) {

				Cell cell = report.getExcel().getCell(report.getRowIndex(), columnIndex);
				cell.setCellValue(report.getExcel().createRichTextString((report.getTextTotalRecords() != null ? report.getTextTotalRecords() : "Records") + ": " + recordCount));
				styleRecordCount.setApplyStyle(cell);
			}

			// Mostrar porcentaje de registros del grupos con respecto al resto de registros.
			if (isShowPercent()) {

				Cell cell = report.getExcel().getCell(report.getRowIndex(), columnIndex);
				// Se incluyen new Double porque de lo contrario sólo obtenemos un integer.
				cell.setCellValue(report.getExcel().createRichTextString((isShowRecordCount() ? "Records" + ": " + recordCount + "     " : "") + new Double(recordCount) * 100 / new Double(report.getDataSize()) + " % "));
				styleHeader.setApplyStyle(cell);
			}

			// Añadir una nueva fila de separación y recortar su altura.
			if (report.isShowDetail()) {

				report.addRowIndex();
				// Recortar la altura de la fila que dejamos en blanco entre grupos y totales.
				Row row = report.getExcel().getActiveSheet().createRow(report.getRowIndex());
				row.setHeight((short) 100);
			}
		}
		// Mostrar porcentaje de registros del grupos con respecto al resto de registros.
		else if (isShowPercent()) {

			Cell cell = report.getExcel().getCell(report.getRowIndex(), columnIndex);
			// Se incluyen new Double porque de lo contrario sólo obtenemos un integer.
			cell.setCellValue(report.getExcel().createRichTextString((isShowRecordCount() ? "Records" + ": " + recordCount + "     " : "") + new Double(recordCount) * 100 / new Double(report.getDataSize()) + " % "));
			styleHeader.setApplyStyle(cell);
		}

	}

	public ReportTotalFields getTotalFields() {
		return totalFields;
	}

	public void setTotalFields(ReportTotalFields totalFields) {
		this.totalFields = totalFields;
	}

	public int getDisplayWidth() {
		return displayWidth;
	}

	public void setDisplayWidth(int displayWidth) {
		this.displayWidth = displayWidth;
	}

	/**
	 * Longitud máxima de celda dentro de la columna que representa el campo del informe.
	 * 
	 * @return
	 */
	public int getMaxWidth() {
		return maxWidth;
	}

	public void setMaxWidth(int maxWidth) {
		this.maxWidth = maxWidth;
	}

	public ReportVisualStyle getStyleHeader() {
		return styleHeader;
	}

	public void setStyleHeader(ReportVisualStyle styleHeader) {
		this.styleHeader = styleHeader;
	}

	public ReportVisualStyle getStyleTotals() {
		return styleTotals;
	}

	public void setStyleTotals(ReportVisualStyle styleTotals) {
		this.styleTotals = styleTotals;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean isRowGroupCollapsed() {
		return rowGroupCollapsed;
	}

	public void setRowGroupCollapsed(boolean rowGroupCollapsed) {
		this.rowGroupCollapsed = rowGroupCollapsed;
	}

	/**
	 * Establecer el color de Background.
	 * 
	 * @param color
	 */
	public void setForeColor(Color color) {
		if (color == Color.BLUE)
			getStyleHeader().setForeColor(HSSFColor.BLUE.index);
		else if (color == Color.MAGENTA)
			getStyleHeader().setForeColor(HSSFColor.MAROON.index);
		else if (color == Color.BLACK)
			getStyleHeader().setForeColor(HSSFColor.BLACK.index);
		else if (color == Color.GRAY)
			getStyleHeader().setForeColor(HSSFColor.GREY_50_PERCENT.index);
	}

	/**
	 * Establecer el tamaño de fuente de los campos de cabecera del grupo.
	 * 
	 * @param color
	 */
	public void setFontSize(int size) {
		getStyleHeader().setFontSize(size);
	}

	public boolean isShowPercent() {
		return showPercent;
	}

	public void setShowPercent(boolean showPercent) {
		this.showPercent = showPercent;
	}
}

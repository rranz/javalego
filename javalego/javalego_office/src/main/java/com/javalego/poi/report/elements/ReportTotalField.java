package com.javalego.poi.report.elements;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;

import com.javalego.poi.report.BasicReport;
import com.javalego.poi.report.ReportVisualStyle;
import com.javalego.util.StringUtils;

/**
 * Totaliza un campo del informe.
 */
public class ReportTotalField implements IReportItem, IReportConstans
{
	/**
	 * Estilo visual aplicado
	 */
	private ReportVisualStyle style;
	
	/**
	 * Campo asociado desde donde obtener los valores a totalizar.
	 */
	private ReportField field;
	
	/**
	 * Informe de referencia.
	 */
	private BasicReport report;
	
	/**
	 * Fórmula aplicada.
	 */
	private String formula = FORMULA_SUM;
	
	/**
	 * Importe totalizado dependiendo del tipo de fórmula.
	 */
	private double total = 0;
	
	/**
	 * Número de registros.
	 */
	private int count = 0;

	public ReportTotalField(BasicReport report, ReportField field) {
		this.field = field;
		this.report = report;
		style = new ReportVisualStyle(report.getExcel());
	}
	
	public ReportTotalField(BasicReport report, ReportField field, String formula) {
		this.field = field;
		this.report = report;
		this.formula = formula;
		style = new ReportVisualStyle(report.getExcel());
	}
	
	/**
	 * Inicializar el valor que acumula los importes del atributo.
	 */
	public void initialize(){
		total = 0;
		count = 0;
	}
	
	public void totalize(Number value){
		
		internalTotalize(value.doubleValue());
	}

	/**
	 * Acumular valores numéricos de un atributo del informe.
	 * @param value
	 */
	public void totalize(double value){
		internalTotalize(value);
	}
	
	public void totalize(int value){
		internalTotalize(value);
	}

	public void totalize(long value){
		internalTotalize(value);
	}
	
	public void totalize(float value){
		internalTotalize(value);
	}

	/**
	 * Totalizar importes dependiendo de la fórmula utilizada.
	 * @param value
	 */
	private void internalTotalize(double value) {
		
		if (formula.equals(FORMULA_MIN)) {
			if (value < total || total == 0) total = value;
		}
		else if (formula.equals(FORMULA_MAX)) {
			if (value > total || total == 0) total = value;
		}
		else if (formula.equals(FORMULA_PERCENT)) {
			total += value;
		}
		else 	
			total += value;
		
		// Acumular registros si es un porcentaje independientemente del su valor ya que es necesario para obtener el porcentaje sobre el número total de registros procesados.
		if (value != 0.0 || formula.equals(FORMULA_PERCENT))
			++count;
	}
	
	public ReportField getField() {
		return field;
	}

	public void setField(ReportField field) {
		this.field = field;
	}

	/**
	 * Valor totalizado
	 * @return
	 */
	public double getValue() {
		
		if (formula.equals(FORMULA_AVG)) 
		
			return count > 0 ? total / count : 0;
			
		else if (formula.equals(FORMULA_PERCENT)) {

			if (count != 0 && total != 0)
				return StringUtils.round(total * 100 / count, 2);
			else
				return 0;
		}
		else
			return total;
	}
	
	/**
	 * Imprimir valor en el informe en la columna del ReportField y fila actual del informe.
	 */
	@Override
	public void generate() throws Exception {
		generate((short)-1);
	}
	
	/**
	 * Imprimir valor en el informe en la columna del ReportField y fila actual del informe.
	 */
	public void generate(short backgroundColor) throws Exception {
		
		Cell cell = null;
		
		int rowIndex = report.getRowIndex();
		
		cell = report.getExcel().getCell(rowIndex, field.getColumnIndex());
				
		double value = getValue();
		
		// Dividir por 100 cuando se desea mostrar el valor con formato de porcentaje.
		if (formula.equals(FORMULA_PERCENT) || style.isPercentFormat() || field.getStyleCell().isPercentFormat()) {
			value = ((Number)value).doubleValue() / 100;
		}
		
		report.getExcel().setCellValue(cell, new Double(value));
				
		cell.setCellType(report.getExcel().getCellTypeFromValue(new Double(total)));
		
		// Estilo de celda con respecto al tipo de valor.
		style.setBox(true);
		
		if (style.getForeColor() < 0) 
			style.setForeColor(HSSFColor.WHITE.index);

		// Color de fondo
		if (backgroundColor > -1)
			style.setBackgroundColor(backgroundColor);
		
		else if (style.getBackgroundColor() < 0) 
			style.setBackgroundColor(HSSFColor.BLUE_GREY.index);
			
		if (style.getFontName() != null) 
			style.setFontName(field.getStyleCell().getFontName() == null ? report.getFontNameDefault() : field.getStyleCell().getFontName());
		else
			style.setFontName(report.getFontNameDefault());

		style.setFormat(field.getFormat());
		
		// Aplicar estilo percent si el fórmula = percent.
		if (formula.equals(FORMULA_PERCENT))
			style.setFormat(FORMULA_PERCENT);
		
		if (style.getFormat() == null) {
			
			// Obtiene el tipo de formato del valor del campo del último registro para dar el mismo formato que el campo al que está vinculado.
			if (field.getValue() != null && field.getValue() instanceof Number) {
				if (field.getValue() instanceof Integer || field.getValue() instanceof Long)
					style.setFormat("\\#,\\#\\#0");
				else
					style.setFormat("\\#,\\#\\#0.00");
			}
			
			// Campos a totalizar boolean
			else if ((field.getValue() != null && field.getValue() instanceof Boolean) || field.isBooleanField()) {

				style.setFormat("\\#,\\#\\#0");
			}
			else
				style.setFormat("\\#,\\#\\#0.00");
		}					
		
		style.setMoneySymbol(field.getStyleCell().isMoneySymbol());
		
		style.setApplyStyle(cell);
	}

	public ReportVisualStyle getStyle() {
		return style;
	}

	public void setStyle(ReportVisualStyle style) {
		this.style = style;
	}

	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}
	
}

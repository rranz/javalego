package com.javalego.poi.report.elements;

import java.sql.Time;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;

import com.javalego.poi.report.BasicReport;
import com.javalego.poi.report.ExcelWorkbookXSSF;
import com.javalego.poi.report.ReportVisualStyle;
import com.javalego.util.StringUtils;

/**
 * Descripción breve de ReportField.
 */
public class ReportField extends ReportCustomField implements IReportItem {
	
	/**
	 * Estilo de la celda de la cabecera del campo.
	 */
	private ReportVisualStyle styleHeader;
	
	/**
	 * Estilo de la celda.
	 */
	private ReportVisualStyle styleCell;
	
	/**
	 * Nombre del campo
	 */
	private String fieldName;
	
	/**
	 * Título de encabezado del campo
	 */
	private String title;
 
	/**
	 * Posición de la columna dentro del informe.
	 */
	private int columnIndex;
	 
	/**
	 * Longitud de caracteres a visualizar.
	 */
	private int displayWidth = -1;
	 
	/**
	 * longitud máxima impresa que servirá para establecer el ancho de la columna.
	 */
	private int maxWidth;
	
	private double maxWidthPixels = -1;
	
	/**
	 * Visibilidad del campo en el informe.
	 */
	private boolean visible = true;
	
	/**
	 * Comprueba si el nombre del campo ha sido generado por el sistema de forma temporal cuando no existiese ninguno.
	 */
	private boolean tmpFieldName;
	
	/**
	 * Define la tipología del valor del campo. De momento esta propiedad sirve para que cuando el valor de un campo boolean sea = null se visualize false. Ver ReportField donde se gestiona esta información.
	 */
	private String type;
	
	/**
	 * No imprimir el campo si su valor numérico es igual a 0.
	 */
	private boolean notPrintZero;
	
	/**
	 * Formateo del campo
	 */
	protected String format;
	
	/**
	 * Alineación
	 */
	protected short align;
	
	/**
	 * Posición dentro del array de valores. Sólo para informes basados en un array de valores donde
	 * por defecto el número de campos corresponde al número de valores del array. De esta forma,
	 * podemos crear informes basados en array de valores cambiando el orden y el número de campos a imprimir.
	 */
	protected int index = -1;
	
	protected BasicReport report;
	
	public ReportField(BasicReport report, String fieldName) {
		
		this.fieldName = fieldName;
		this.report = report;
		styleCell = new ReportVisualStyle(report.getExcel());
		styleHeader = new ReportVisualStyle(report.getExcel());
		styleHeader.setAlignVerticalCenter(true);
	}

	public ReportField(BasicReport report, String fieldName, String title) {
		
		this.fieldName = fieldName;
		this.report = report;
		this.title = title;
		styleCell = new ReportVisualStyle(report.getExcel());
		styleHeader = new ReportVisualStyle(report.getExcel());
		styleHeader.setAlignVerticalCenter(true);
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		
		this.fieldName = fieldName;
		if (title == null) title = fieldName;
	}

	/**
	 * Devuelve el valor del atributo del objeto
	 * @return
	 */
	public Object getValue() throws Exception {
		
		Object value = null;
		
		if (report.getObjectsIndex() > -1){
			value = report.getFieldValue(report.getActualObject(), fieldName);
		}
		return value;
	}
	

	/**
	 * Devuelve el valor del atributo dentro del array de objetos de valores. Se utiliza para obtener el objeto anterior al actual y ver si
	 * hay cambios para eliminar valores duplicados.
	 * @return
	 */
	public Object getValue(int index) throws Exception {
		
		Object value = null;
		
		if (index > -1 && report.getObjectsIndex() > -1){
			value = report.getFieldValue(report.getObject(index), fieldName);
		}
		return value;
	}
	
	/**
	 * Generar el valor de la celda dentro del proceso de impresión del informe y obteniendo su posición del objeto mediante
	 * la propiedad objectIndex.
	 */
	@Override
	public void generate() throws Exception {
			
		int columnIndex = getColumnIndex();
		
		ExcelWorkbookXSSF excel = report.getExcel();
		
		Cell cell = null;
		
		boolean noPrint = false;

		// Valor del campo dependiendo del valor de la propiedad index que establece la posición de la lista de valores o -1 su posición dentro de la lista de campos con respecto al número de valores del objeto.
		Object value = getValue();
		
		// Poner a false el valor de la celda si es null y el campo es boolean.
		if (value == null && type != null && type.equals("boolean"))
			value = false;
		
		// Dividir por 100 cuando se desea mostrar el valor con formato de porcentaje.
		if (value != null && value instanceof Number && (styleCell.isPercentFormat() || (format != null && format.equals("percent")))) {
			value = ((Number)value).doubleValue() / 100;
		}
		
		cell = excel.getCell(report.getRowIndex(), columnIndex);
		
		// Eliminar valores duplicados de celdas.
		if (report.isRemoveEquals() && report.getObjectsIndex() > 0){
			
			Object oldValue = getValue(report.getObjectsIndex()-1);
			
			if (oldValue != null && oldValue.equals(value))
				value = null;
		}
		
		// Valor boolean poner Sí o No
		if (value != null && value instanceof Boolean) {
			
			excel.setCellValue(cell, StringUtils.getTextBoolean((Boolean)value));
		}
		// Este código nos permite mostrar información de clases complejas donde delegamos que en el método toString() se muestra la información que las represente.
		else if (value == null || value instanceof String || value instanceof Number || value instanceof Date || value instanceof Boolean || value instanceof Time) {
			
			// No imprimir si el valor numérico == 0 y notPrintZero == true.
			if (value != null && value instanceof Number && notPrintZero && ((Number)value).doubleValue() == 0.0) {
				noPrint = true;
				excel.setCellValue(cell, "");
			}
			else
				excel.setCellValue(cell, value);
		}
		else {
			excel.setCellValue(cell, value.toString());
			styleCell.setAlignment(CellStyle.ALIGN_JUSTIFY);
		}

		// Alineación
		if (value != null) {
		
			if (value instanceof Date)
				styleCell.setAlignment(CellStyle.ALIGN_CENTER);
			
			else if (value instanceof String)
				styleCell.setAlignment(CellStyle.ALIGN_JUSTIFY);
		}
		
		// Establecer la tipología de la celda. Nota: los boolean se han modificado para imprimir Sí o No y no como celda boolean.
		if (!noPrint) {
			if (value != null && value instanceof Boolean)
				cell.setCellType(Cell.CELL_TYPE_STRING);
			else
				cell.setCellType(excel.getCellTypeFromValue(value));
		}
		
		// Estilo de celda con respecto al tipo de valor.
		styleCell.setBox(true);
		
		if (styleCell.getForeColor() < 0)
			styleCell.setForeColor(HSSFColor.BLACK.index);
		
		if (styleCell.getBackgroundColor() < 0)
			styleCell.setBackgroundColor(HSSFColor.WHITE.index);
		
		if (styleCell.getFontName() == null) styleCell.setFontName(report.getFontNameDefault());
		
		if (!noPrint) {
			if (format != null) {
				styleCell.setFormat(format);
			}
			else if (value instanceof Date){
			  format = "mmm-d-yy"; 
				styleCell.setFormat(format);
			}
			else if (value instanceof Number) {
				if (value instanceof Integer || value instanceof Long)
					styleCell.setFormat("\\#,\\#\\#0"); 
				else
					styleCell.setFormat("\\#,\\#\\#0.00"); 
			}				
		}

		//styleCell.setWrapText(true);
		
		//styleCell.setApplyStyle(cell);
		
		// Establecer la longitud máxima de caracteres del campo
		if (displayWidth < 0 && value != null){
			
			int length = 0;
			
			if (format != null) {
			
				if (value instanceof Date){
				
					SimpleDateFormat f = new SimpleDateFormat(format);
					try {
						length = f.format(cell.getDateCellValue()).length();
					}
					catch(Exception e) {
					}
				}
				else{
					length = 0;
					DecimalFormat f = new DecimalFormat(format);
					try{
					  length = f.format(cell.getNumericCellValue()).length();
					}
					catch(Exception e){
						
					}
				}
			}
			else
				length = value.toString().length();
			
			// Añadir los posiciones si el campo es numérico y tiene un campo totalizador.
			if (value instanceof Number && report.getReportTotalField(fieldName) != null)
				length += 2;
			
			if (length > maxWidth)
				maxWidth = length;
			
			double mw = report.getExcel().getValueWidth(cell);
			
			if (mw > maxWidthPixels)
				maxWidthPixels = mw;
			
			
		}
	}
	
	/**
	 * Imprimir las cabeceras de los campos.
	 * @param rowIndex
	 * @param columnIndex
	 * @throws Exception
	 */
	public void generateHeader(int rowIndex, int columnIndex) throws Exception {
		
		ExcelWorkbookXSSF excel = report.getExcel();
		Cell cell;
		
		// definir la posición de la columna dentro del informe.
		this.columnIndex = columnIndex;

		cell = excel.getCell(rowIndex, columnIndex);

		cell.setCellValue(excel.createRichTextString(title));
		
		// Estilo de celda con respecto al tipo de valor.
		styleHeader.setBox(true);
		styleHeader.setWrapText(true);
		styleHeader.setAlignment(CellStyle.ALIGN_CENTER);
		styleHeader.setForeColor(HSSFColor.WHITE.index);
		styleHeader.setBackgroundColor(HSSFColor.BLUE_GREY.index);
		
		if (styleHeader.getFontName() == null) styleHeader.setFontName(report.getFontNameDefault());
		
		styleHeader.setAlignment(CellStyle.ALIGN_CENTER);

		if (!(this instanceof ReportSeparatorField))
			styleHeader.setApplyStyle(cell);

		if (!StringUtils.isEmpty(description))
			report.getExcel().setCellComment(description, cell);
		
		double mw = report.getExcel().getValueWidth(cell);
		if (mw > maxWidthPixels)
			maxWidthPixels = mw;
	
	}

	public String getTitle() {
		if (StringUtils.isEmpty(title))
			return fieldName;
		else
			return title;
	}

	/**
	 * Valor del Título sin tener en cuenta el si no existe y obtener alternativamente el nombre como título. ver getTitle().
	 * @return
	 */
	public String getOriginalTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Alineación. Opciones: HSSFCellStyle.ALIGN_CENTER etc.
	 * @return
	 */
	public short getAlign() {
		return align;
	}

	public void setAlign(short align) {
		this.align = align;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public void setMoneyFormat() {
		this.format = ReportVisualStyle.FORMAT_MONEY;
	}

	public void setPercentFormat() {
		this.format = ReportVisualStyle.FORMAT_PERCENT;
	}

	/**
	 * Posición de la columna dentro del informe donde se situarán los valores del campo.
	 * @return
	 */
	public int getColumnIndex() {
		return columnIndex;
	}

	public void setColumnIndex(int columnIndex) {
		this.columnIndex = columnIndex;
	}
	
	/**
	 * Longitud máxima de celda dentro de la columna que representa el campo del informe.
	 * @return
	 */
	public int getMaxWidth() {
		if (title != null && title.length() > maxWidth)
			return title.length();
		else
		  return maxWidth;
	}

	public void setMaxWidth(int maxWidth) {
		this.maxWidth = maxWidth;
	}

	/**
	 * Longitud de caracteres que constituirá el ancho de la columna en Excel. De no especificar un valor el sistema calculará este valor
	 * en base a la información de los objetos a imprimir.
	 * @return
	 */
	public int getDisplayWidth() {
		//if (displayWidth > -1)
			return displayWidth;
		//else
			//return getMaxWidth();
	}

	public void setDisplayWidth(int displayWidth) {
		this.displayWidth = displayWidth;
	}

	public ReportVisualStyle getStyleCell() {
		return styleCell;
	}

	public void setStyleCell(ReportVisualStyle styleCell) {
		this.styleCell = styleCell;
	}

	public ReportVisualStyle getStyleHeader() {
		return styleHeader;
	}

	public void setStyleHeader(ReportVisualStyle styleHeader) {
		this.styleHeader = styleHeader;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	/**
	 * Alinear el contenido de campo en el centro de la celda.
	 */
	public void setAlignmentCenter(){
		getStyleCell().setAlignment(CellStyle.ALIGN_CENTER);
	}

	/**
	 * Alinear el contenido de campo a la izquierda de la celda.
	 */
	public void setAlignmentLeft(){
		getStyleCell().setAlignment(CellStyle.ALIGN_LEFT);
	}
	/**
	 * Alinear el contenido de campo a la derecha de la celda.
	 */
	public void setAlignmentRigth(){
		getStyleCell().setAlignment(CellStyle.ALIGN_RIGHT);
	}

	public BasicReport getReport() {
		return report;
	}

	public double getMaxWidthPixels() {
		return maxWidthPixels;
	}

	public void setMaxWidthPixels(double maxWidthPixels) {
		this.maxWidthPixels = maxWidthPixels;
	}

	public boolean isNotPrintZero() {
		return notPrintZero;
	}

	public void setNotPrintZero(boolean notPrintZero) {
		this.notPrintZero = notPrintZero;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Comprueba si el tipo de campo definido es un boolean.
	 * @return
	 */
	public boolean isBooleanField() {
		return type != null && type.equals("boolean");
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public boolean isTmpFieldName() {
		return tmpFieldName;
	}

	public void setTmpFieldName(boolean tmpFieldName) {
		this.tmpFieldName = tmpFieldName;
	}

	@Override
	public String toString() {
		return title;
	}
}

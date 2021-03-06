package com.javalego.poi.report;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Label;
import java.awt.font.FontRenderContext;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.AttributedString;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.FileImageInputStream;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.w3c.dom.Element;

import com.javalego.util.DateUtils;
import com.javalego.util.DesktopUtils;
import com.javalego.util.FileUtils;
import com.javalego.util.StringUtils;
import com.javalego.util.SystemUtils;

/**
 * Clase que nos permite generar archivos de Excel.
 * 
 * @author ROBERTO RANZ
 */
public class ExcelWorkbookXSSF {

	private String fileName = null;

	private Workbook workBook = new XSSFWorkbook();

	private final ArrayList<Sheet> sheets = new ArrayList<Sheet>();

	private final CreationHelper helper = workBook.getCreationHelper();

	private int activeSheetIndex;

	private Drawing patriarch;

	private static final char[] A2Z = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

	public ExcelWorkbookXSSF() {

	}

	/**
	 * Abrir una plantilla de excel existente y definir el fichero de salida al finalizar la generación del mismo.
	 * 
	 * @param fileName
	 * @param useTemplate
	 */
	public ExcelWorkbookXSSF(String resource, String fileName) throws Exception {

		this.fileName = fileName;

		loadResource(resource);
		setNameActiveSheet("Report");

		activeSheetIndex = 0;
	}

	/**
	 * Inicializar la hoja de excel especificando el nombre de la hoja y definiendo si queremos usar la plantilla por defecto que establece la resolución de la hoja a 6000ppp ya que por defecto se queda
	 * en 300ppp y la resolución de impresión es mala.
	 * 
	 * @param fileName
	 * @param useTemplate
	 */
	public ExcelWorkbookXSSF(String fileName) {

		this.fileName = fileName;

		addSheet("Report");

		activeSheetIndex = 0;
	}

	/**
	 * Establecer una fórmula en una celda.
	 * 
	 * @param row
	 * @param column
	 * @param formula
	 */
	public void setFormula(int row, int column, String formula) {

		getCell(row, column).setCellType(Cell.CELL_TYPE_FORMULA);
		getCell(row, column).setCellFormula(formula);

		// "SUM(A1:A2)"
	}

	/**
	 * Bloquear una celda. Para que sea efectivo el bloqueo de la celda debe bloquear la hoja entera, desbloqueando sólo las celdas que requiera.
	 * 
	 * @param row
	 * @param column
	 * @param value
	 */
	public void setLocked(int row, int column, boolean value) {
		getCell(row, column).getCellStyle().setLocked(value);
	}

	/**
	 * Proteger la edición de la hoja completa.
	 * 
	 * @param value
	 */
	public void setProtectActiveSheet(boolean value) {
		getActiveSheet().protectSheet(""); // .setProtect(value);
	}

	/**
	 * Abrir una hoja de excel existente.
	 * 
	 * @param fileName
	 * @throws Exception
	 */
	public void loadFile(String fileName) throws Exception {

		// POIFSFileSystem fs = null;
		try {
			if (this.fileName == null)
				this.fileName = fileName;

			initialize(fileName);

		}
		catch (Exception e) {
			throw new Exception("Puede que el archivo '" + fileName + "' no exista. Error al abrir el archivo.", e);
		}
	}

	/**
	 * Abrir una hoja de excel existente en un recurso
	 * 
	 * @param fileName
	 * @throws Exception
	 */
	public void loadResource(String resourceFileName) throws Exception {

		try {
			initialize(SystemUtils.getResource(resourceFileName));
		}
		catch (Exception e) {
			throw new Exception("Puede que el recurso '" + resourceFileName + "' no exista. Error al abrir el archivo.", e);
		}
	}

	/**
	 * Inicializar workbook
	 */
	private void initialize(InputStream stream) throws Exception {

		workBook = new XSSFWorkbook(stream);

		if (workBook.getNumberOfSheets() > 0) {
			// Llenar los sheets
			sheets.clear();
			for (int i = 0; i < workBook.getNumberOfSheets(); i++) {
				sheets.add(workBook.getSheetAt(i));
			}
			setActiveSheetIndex(0);
		}
	}

	/**
	 * Inicializar workbook
	 */
	@SuppressWarnings("deprecation")
	private void initialize(String filename) throws Exception {

		workBook = new XSSFWorkbook(filename);

		if (workBook.getNumberOfSheets() > 0) {
			// Llenar los sheets
			sheets.clear();
			for (int i = 0; i < workBook.getNumberOfSheets(); i++) {
				sheets.add(workBook.getSheetAt(i));
			}
			setActiveSheetIndex(0);
		}
	}

	/**
	 * Incluir una imagen en la hoja activa.
	 * 
	 * @param fileName
	 */
	public void insertImage(String fileName, int row, int col) throws Exception {

		// HSSFPatriarch patriarch = getActiveSheet().createDrawingPatriarch();
		/*
		 * @param dx1 the x coordinate within the first cell.
		 * 
		 * @param dy1 the y coordinate within the first cell.
		 * 
		 * @param dx2 the x coordinate within the second cell.
		 * 
		 * @param dy2 the y coordinate within the second cell.
		 * 
		 * @param col1 the column (0 based) of the first cell.
		 * 
		 * @param row1 the row (0 based) of the first cell.
		 * 
		 * @param col2 the column (0 based) of the second cell.
		 * 
		 * @param row2 the row (0 based) of the second cell.
		 */
		// HSSFClientAnchor anchor = new
		// HSSFClientAnchor(0,0,0,255,(short)2,2,(short)4,7);

		int idx = workBook.addPicture(FileUtils.getStreamFile(fileName), getTypeImage(fileName));
		Picture picture = getPatriach().createPicture(helper.createClientAnchor(), idx);
		new PictureResizer(getActiveSheet(), picture, row, col, helper).resize();

		// HSSFClientAnchor anchor = new
		// HSSFClientAnchor(0,0,0,255,(short)0,0,(short)2,1);
		// anchor.setAnchorType(2);
		// getPatriach().createPicture(anchor, loadPicture(fileName, workBook ));
	}

	/**
	 * Incluir una imagen en la hoja activa.
	 * 
	 * @param fileName
	 */
	public void insertImageFromResource(String resource) throws Exception {

		int idx = workBook.addPicture(SystemUtils.getBytesFromResource(resource), getTypeImage(fileName));
		Picture picture = getPatriach().createPicture(helper.createClientAnchor(), idx);
		new PictureResizer(getActiveSheet(), picture, 0, 0, helper).resize();

	}

	/**
	 * Incluir una imagen en la hoja activa.
	 * 
	 * @param fileName
	 */
	public void insertImageFromResource(byte[] data, int row, int col) throws Exception {

		int idx = workBook.addPicture(data, getTypeImage(fileName));
		Picture picture = getPatriach().createPicture(helper.createClientAnchor(), idx);
		new PictureResizer(getActiveSheet(), picture, row, col, helper).resize();

	}

	/**
	 * Incluir una imagen en la hoja activa.
	 * 
	 * @param fileName
	 */
	public void insertImageFromResource(String resource, int row, int col) throws Exception {

		int idx = workBook.addPicture(SystemUtils.getBytesFromResource(resource), getTypeImage(fileName));
		Picture picture = getPatriach().createPicture(helper.createClientAnchor(), idx);
		new PictureResizer(getActiveSheet(), picture, row, col, helper).resize();
	}

	/**
	 * Tipo de imagen
	 * 
	 * @param fileName
	 * @return
	 */
	private static int getTypeImage(String fileName) {

		String ext = FileUtils.getFileExtension(fileName).toLowerCase();

		if (ext != null && ext.equals(".png"))
			return Workbook.PICTURE_TYPE_PNG;
		else if (ext != null && ext.equals(".jpg"))
			return Workbook.PICTURE_TYPE_JPEG;
		else
			return Workbook.PICTURE_TYPE_PNG;
	}

	/**
	 * Cargar una imagen desde un recurso.
	 * 
	 * @param path
	 * @param wb
	 * @return
	 * @throws IOException
	 */
	// private static int loadPictureFromResource(String resource, HSSFWorkbook wb) throws Exception {
	// int pictureIndex;
	// ByteArrayOutputStream bos = null;
	// InputStream fis = null;
	// try {
	// bos = new ByteArrayOutputStream();
	// int c;
	// fis = Functions.getResource(resource);
	// while ((c = fis.read()) != -1)
	// bos.write(c);
	// pictureIndex = wb.addPicture(bos.toByteArray(), getTypeImage(resource));
	// } finally {
	// if (fis != null)
	// fis.close();
	// if (bos != null)
	// bos.close();
	// }
	// return pictureIndex;
	// }

	/**
	 * Cargar una imagen desde un recurso.
	 * 
	 * @param path
	 * @param wb
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unused")
	private static int loadPictureFromResource(byte[] data, Workbook wb) throws Exception {
		return wb.addPicture(data, Workbook.PICTURE_TYPE_PNG);
	}

	// public ExcelWorkbook(String fileName, String sheetName) {
	// this.fileName = fileName;
	// addSheet(sheetName);
	// activeSheetIndex = 0;
	// }

	/**
	 * Añadir hoja.
	 * 
	 * @param name
	 * @return
	 */
	public Sheet addSheet(String name) {

		org.apache.poi.ss.usermodel.Sheet s = workBook.createSheet(name);
		sheets.add(s);
		return sheets.get(sheets.size() - 1);
	}

	/**
	 * Añadir hoja.
	 * 
	 * @param name
	 * @return
	 */
	public Sheet addSheet(String name, boolean active) throws Exception {

		sheets.add(workBook.createSheet(name));
		Sheet sheet = sheets.get(sheets.size() - 1);
		if (active)
			setActiveSheetIndex(sheet);
		return sheet;
	}

	/**
	 * Número de hoja activa.
	 * 
	 * @return
	 */
	public int getActiveSheetIndex() {
		return activeSheetIndex;
	}

	/**
	 * Hoja activa.
	 * 
	 * @return
	 */
	public Sheet getActiveSheet() {
		return sheets.get(activeSheetIndex);
	}

	/**
	 * Añadir un comentario a una celda mediante su posición fila y columna.
	 * 
	 * @param text
	 * @param rowIndex
	 * @param columnIndex
	 */
	public void setCellComment(String text, int rowIndex, int columnIndex) {

		Drawing drawing = getActiveSheet().createDrawingPatriarch();

		// When the comment box is visible, have it show in a 1x3 space
		ClientAnchor anchor = helper.createClientAnchor();

		// Create the comment and set the text+author
		Comment comment = drawing.createCellComment(anchor);

		comment.setString(helper.createRichTextString(text));
		// set comment author.
		// you can see it in the status bar when moving mouse over the commented
		// cell
		//comment.setAuthor(Messages.getString("ExcelWorkbook.1"));
		comment.setRow(rowIndex);
		comment.setColumn((short) (columnIndex));
	}

	/**
	 * Obtener el comentario a una celda mediante su posición fila y columna.
	 * 
	 * @param text
	 * @param rowIndex
	 * @param columnIndex
	 */
	public String getCellComment(int rowIndex, int columnIndex) {
		return getCell(rowIndex, columnIndex).getCellComment().getString().toString();
	}

	/**
	 * Añadir un comentario a una celda mediante su posición fila y columna.
	 * 
	 * @param text
	 * @param rowIndex
	 * @param columnIndex
	 */
	public void setCellComment(String text, Cell cell) {

		Drawing drawing = getActiveSheet().createDrawingPatriarch();

		// When the comment box is visible, have it show in a 1x3 space
		ClientAnchor anchor = helper.createClientAnchor();

		// Create the comment and set the text+author
		Comment comment = drawing.createCellComment(anchor);

		// set text in the comment
		comment.setString(helper.createRichTextString(text));
		// set comment author.
		// you can see it in the status bar when moving mouse over the commented
		// cell
		//comment.setAuthor(Messages.getString("ExcelWorkbook.2"));
		cell.setCellComment(comment);
	}

	/**
	 * Obtener el Patriach de la página actual.
	 * 
	 * @return
	 */
	public Drawing getPatriach() {

		if (patriarch == null)
			patriarch = getActiveSheet().createDrawingPatriarch();
		return patriarch;
	}

	/**
	 * Activar un número de hoja.
	 * 
	 * @param activeSheetIndex
	 * @throws Exception
	 */
	public void setActiveSheetIndex(int activeSheetIndex) throws Exception {

		if (activeSheetIndex < sheets.size() - 1 && activeSheetIndex > sheets.size() - 1)
			throw new Exception("Error active sheet " + activeSheetIndex);
		else
			this.activeSheetIndex = activeSheetIndex;
		patriarch = null;
	}

	/**
	 * Activar una hoja existente.
	 * 
	 * @param activeSheetIndex
	 * @throws Exception
	 */
	public void setActiveSheetIndex(Sheet sheet) throws Exception {

		for (int i = 0; i < sheets.size(); i++) {
			if (sheets.get(i) == sheet) {
				activeSheetIndex = i;
				patriarch = null;
				return;
			}
		}
	}

	/**
	 * Establecer el valor de una celda.
	 * 
	 * @param value
	 * @param rowIndex
	 * @param columnIndex
	 */
	public void setCellValue(Object value, int row, String textFind, int rowFind) throws Exception {

		int index = getIndexCell(rowFind, textFind);

		if (index > -1)
			setCellValue(value, row, index);
		else
			throw new Exception("Column '" + textFind + "' not exist");
	}

	/**
	 * Establecer el valor de una celda.
	 * 
	 * @param value
	 * @param rowIndex
	 * @param columnIndex
	 */
	public void setCellValue(Object value, int rowIndex, int columnIndex) {

		Cell cell = getCell(rowIndex, columnIndex);

		if (value instanceof Double)
			cell.setCellValue((Double) value);
		else if (value instanceof Integer)
			cell.setCellValue((Integer) value);
		else if (value instanceof Long)
			cell.setCellValue((Long) value);
		else if (value instanceof Date)
			cell.setCellValue((Date) value);
		else if (value instanceof Boolean)
			cell.setCellValue((Boolean) value);
		else if (value != null)
			cell.setCellValue(helper.createRichTextString((String) value));
	}

	/**
	 * Establecer el valor de una celda.
	 * 
	 * @param value
	 * @param rowIndex
	 * @param columnIndex
	 */
	public void setCellValue(Object value, int rowIndex, int columnIndex, String align) {

		setCellValue(value, rowIndex, columnIndex);
		setCellStyle(rowIndex, columnIndex, false, false, null, null, align, 0);
	}

	/**
	 * Establecer el valor de una celda.
	 * 
	 * @param value
	 * @param rowIndex
	 * @param columnIndex
	 */
	public void setCellValue(Object value, int rowIndex, int columnIndex, String align, String format) {

		// Dividir el valor por 100 cuando es un porcentaje.
		if (format != null && format.equals("percent") && value instanceof Number)
			setCellValue(((Double) value) / 100, rowIndex, columnIndex);
		else
			setCellValue(value, rowIndex, columnIndex);

		setCellStyle(rowIndex, columnIndex, false, false, null, null, align, 0, format);
	}

	/**
	 * Establecer el valor de una celda.
	 * 
	 * @param value
	 * @param rowIndex
	 * @param columnIndex
	 */
	public void setCellValue(Object value, int rowIndex, int columnIndex, String align, int fontSize) {

		setCellValue(value, rowIndex, columnIndex);
		setCellStyle(rowIndex, columnIndex, false, false, null, null, align, fontSize);
	}

	/**
	 * Establecer el valor de una celda.
	 * 
	 * @param value
	 * @param rowIndex
	 * @param columnIndex
	 */
	public void setCellValue(Object value, int row, int column, boolean box, boolean bold, String forecolor, String backgroundColor, String align, int fontSize) {

		setCellValue(value, row, column);
		setCellStyle(row, column, box, bold, forecolor, backgroundColor, align, fontSize);
	}

	/**
	 * Establecer el valor de una celda.
	 * 
	 * @param value
	 * @param rowIndex
	 * @param columnIndex
	 */
	public void setCellValueBold(Object value, int rowIndex, int columnIndex) {

		setCellValue(value, rowIndex, columnIndex);
		setCellStyle(rowIndex, columnIndex, false, true, "black", "white", "left", 0);
	}

	/**
	 * Establecer el valor de una celda.
	 * 
	 * @param value
	 * @param rowIndex
	 * @param columnIndex
	 */
	public void setCellValueBold(Object value, int rowIndex, int columnIndex, String align) {

		setCellValue(value, rowIndex, columnIndex);
		setCellStyle(rowIndex, columnIndex, false, true, "black", "white", align, 0);
	}

	/**
	 * Establecer el valor de una celda.
	 * 
	 * @param value
	 * @param rowIndex
	 * @param columnIndex
	 */
	public void setCellValueBold(Object value, int rowIndex, int columnIndex, String forecolor, String align, int fontSize) {

		setCellValue(value, rowIndex, columnIndex);
		setCellStyle(rowIndex, columnIndex, false, true, forecolor, "white", align, fontSize);
	}

	/**
	 * Aplicar un estilo de celda a una celda.
	 * 
	 * @param rowIndex
	 * @param column
	 * @param bold
	 * @param forecolor
	 * @param backgroundcolor
	 * @param align
	 */
	public void setCellStyle(int row, int column, boolean box, boolean bold, String forecolor, String backgroundColor, String align, int fontSize) {
		setCellStyle(row, column, box, bold, forecolor, backgroundColor, align, fontSize, null);
	}

	/**
	 * Aplicar un estilo de celda a una celda.
	 * 
	 * @param rowIndex
	 * @param column
	 * @param bold
	 * @param forecolor
	 * @param backgroundcolor
	 * @param align
	 * @param format
	 *          percent, money, integer, string, double, date
	 */
	public void setCellStyle(int row, int column, boolean box, boolean bold, String forecolor, String backgroundColor, String align, int fontSize, String format) {

		CellStyle style = createStyle();

		// alineación
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		if (align != null)
			style.setAlignment(align.equals("left") ? CellStyle.ALIGN_LEFT : align.equals("center") ? CellStyle.ALIGN_CENTER : CellStyle.ALIGN_RIGHT);

		// forecolor
		org.apache.poi.ss.usermodel.Font font = getFontStyle();
		if (forecolor != null)
			font.setColor(ReportVisualStyle.getColorFromString(forecolor));

		if (bold)
			font.setBoldweight(org.apache.poi.ss.usermodel.Font.BOLDWEIGHT_BOLD);

		if (fontSize > 0)
			font.setFontHeightInPoints((short) fontSize);

		style.setFont(font);

		// Formato
		if (format != null) {

			DataFormat dataFormat = workBook.createDataFormat();
			if (format.toLowerCase().equals("money"))
				style.setDataFormat(dataFormat.getFormat("#,##0.00 " + NumberFormat.getNumberInstance().getCurrency().getSymbol()));
			else if (format.toLowerCase().equals("integer"))
				style.setDataFormat(dataFormat.getFormat("#,##0"));
			else if (format.toLowerCase().equals("decimal"))
				style.setDataFormat(dataFormat.getFormat("#,##0.######"));
			else if (format.toLowerCase().equals("percent"))
				style.setDataFormat(dataFormat.getFormat("#,##0.00 %"));
			else
				style.setDataFormat(dataFormat.getFormat(format));
		}

		// Backgroundcolor
		if (backgroundColor != null) {
			style.setFillForegroundColor(ReportVisualStyle.getColorFromString(backgroundColor));
			style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		}

		// Box
		if (box) {
			style.setBorderBottom(CellStyle.BORDER_THIN);
			style.setBottomBorderColor(HSSFColor.BLACK.index);
			style.setBorderLeft(CellStyle.BORDER_THIN);
			style.setBorderRight(CellStyle.BORDER_THIN);
			style.setBorderTop(CellStyle.BORDER_THIN);
		}
		getCell(row, column).setCellStyle(style);
	}

	/**
	 * Incluir el valor a una celda y aplicar un box.
	 * 
	 * @param value
	 * @param rowIndex
	 * @param columnIndex
	 * @param align
	 */
	public void setCellValueBox(Object value, int rowIndex, int columnIndex, String align) {

		setCellValue(value, rowIndex, columnIndex);
		setBoxCell(rowIndex, columnIndex, getAlign(align));
	}

	/**
	 * Incluir el valor a una celda y aplicar un box.
	 * 
	 * @param value
	 * @param rowIndex
	 * @param columnIndex
	 * @param align
	 */
	public void setCellValueBox(Object value, int rowIndex, int columnIndex, short align) {

		setCellValue(value, rowIndex, columnIndex);
		setBoxCell(rowIndex, columnIndex, align);
	}

	/**
	 * Incluir el valor a una celda y aplicar un box.
	 * 
	 * @param value
	 * @param rowIndex
	 * @param columnIndex
	 * @param align
	 */
	public void setCellValueBox(Object value, int rowIndex, int columnIndex, String backgroundColor, short align) {

		setCellValue(value, rowIndex, columnIndex);
		setBoxCell(rowIndex, columnIndex, ReportVisualStyle.getColorFromString(backgroundColor), align);
	}

	/**
	 * Incluir el valor a una celda y aplicar un box.
	 * 
	 * @param value
	 * @param rowIndex
	 * @param columnIndex
	 * @param align
	 */
	public void setCellValueBox(Object value, int rowIndex, int columnIndex, String align, int fontSize) {

		setCellValue(value, rowIndex, columnIndex);
		setCellStyle(rowIndex, columnIndex, true, true, null, "white", align, fontSize);
	}

	/**
	 * Incluir el valor a una celda y aplicar un box.
	 * 
	 * @param value
	 * @param rowIndex
	 * @param columnIndex
	 * @param align
	 */
	public void setCellValueBox(Object value, int rowIndex, int columnIndex, String backgroundColor, String align) {

		setCellValue(value, rowIndex, columnIndex);
		setBoxCell(rowIndex, columnIndex, ReportVisualStyle.getColorFromString(backgroundColor), getAlign(align));
	}

	/**
	 * Obtener la alineación desde un string con los valores: left, center y right.
	 * 
	 * @param align
	 * @return
	 */
	public short getAlign(String align) {
		short _align = CellStyle.ALIGN_LEFT;
		if (align != null) {
			_align = align.equals("right") ? CellStyle.ALIGN_RIGHT : align.equals("center") ? CellStyle.ALIGN_CENTER : CellStyle.ALIGN_LEFT;
		}
		return _align;
	}

	public void setResolution() {

		PrintSetup s = getActiveSheet().getPrintSetup();
		s.setHResolution((short) 600);
		// HSSFPrintSetup_setLandscape(thisPrintSetup: *on);

	}

	/**
	 * Obtener una celda en un fila y posición dada.
	 * 
	 * @param rowIndex
	 * @param cellIndex
	 * @return
	 */
	public Cell getCell(int rowIndex, int cellIndex) {

		Row row = getActiveSheet().getRow((short) rowIndex);
		if (row == null)
			row = getActiveSheet().createRow((short) rowIndex);
		Cell cell = row.getCell((short) cellIndex);
		if (cell == null)
			cell = row.createCell((short) cellIndex);
		return cell;
	}

	/**
	 * Unir una región de celdas.
	 * 
	 * @param rowInit
	 * @param columnInit
	 * @param rowEnd
	 * @param columnEnd
	 */
	public void addMergeCells(int rowInit, int columnInit, int rowEnd, int columnEnd) {

		getActiveSheet().addMergedRegion(new CellRangeAddress(rowInit, (short) columnInit, rowInit, (short) columnEnd));
	}

	/**
	 * Adjusts the column width to fit the contents.
	 * 
	 * @param column
	 *          the column index
	 */
	public void autoSizeColumns(int row) {
		for (int i = 0; i < getColumnCount(row); i++)
			autoSizeColumn(i);
	}

	/**
	 * Adjusts the column width to fit the contents.
	 * 
	 * @param column
	 *          the column index
	 */
	public void autoSizeColumns(int columnInit, int columnEnd) {
		for (int i = columnInit; i < columnEnd; i++)
			autoSizeColumn(i);
	}

	/**
	 * Adjusts the column width to fit the contents.
	 * 
	 * @param column
	 *          the column index
	 */
	public void autoSizeColumn(int column) {

		if (DesktopUtils.isWindows()) {

			getActiveSheet().autoSizeColumn(column);

		}
		else {
			double width = -1;

			Row[] rows = getRows();

			for (int k = 0; k < rows.length; k++) {

				Row row = rows[k];
				if (row == null)
					continue;

				Cell cell = row.getCell(column);
				if (cell == null)
					continue;

				double cellwidth = getValueWidth(cell);
				if (cellwidth > width)
					width = cellwidth;
			}
			if (width != -1)
				getActiveSheet().setColumnWidth(column, (short) ((width + 4) * 256));
		}
	}

	/**
	 * Ancho en pixels.
	 * 
	 * @param value
	 * @return
	 */
	public double getValueWidth(Cell cell) {

		boolean linux = DesktopUtils.isLinux();

		AttributedString str;

		/**
		 * Excel measures columns in units of 1/256th of a character width but the docs say nothing about what particular character is used. '0' looks a good choice.
		 */
		char defaultChar = '0';

		FontRenderContext frc = new FontRenderContext(null, true, true);

		Workbook wb = workBook;
		org.apache.poi.ss.usermodel.Font defaultFont = wb.getFontAt((short) 0);

		str = new AttributedString("" + defaultChar);
		str.addAttribute(TextAttribute.FAMILY, defaultFont.getFontName());
		str.addAttribute(TextAttribute.SIZE, new Float(defaultFont.getFontHeightInPoints()));

		int defaultCharWidth = 5;
		if (!linux) {
			TextLayout layout = new TextLayout(str.getIterator(), frc);
			defaultCharWidth = (int) layout.getAdvance();
		}

		CellStyle style = cell.getCellStyle();
		org.apache.poi.ss.usermodel.Font font = workBook.getFontAt(style.getFontIndex());

		double width = -1;

		if (cell.getCellType() == Cell.CELL_TYPE_STRING) {

			RichTextString rt = cell.getRichStringCellValue();

			// Evitar error en linux para el cálculo del tamaño de la columna
			// provocado por layout.getAdvance().
			if (linux) {
				return rt.getString() != null ? rt.getString().length() + 5 : 0;
			}

			String[] lines = rt.getString().split("\\n");

			for (int i = 0; i < lines.length; i++) {

				str = new AttributedString(lines[i] + defaultChar);
				str.addAttribute(TextAttribute.FAMILY, font.getFontName());
				str.addAttribute(TextAttribute.SIZE, new Float(font.getFontHeightInPoints()));
				if (font.getBoldweight() == org.apache.poi.ss.usermodel.Font.BOLDWEIGHT_BOLD)
					str.addAttribute(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD);
				if (rt.numFormattingRuns() > 0) {
					for (int j = 0; j < lines[i].length(); j++) {

						int idx = rt.getIndexOfFormattingRun(j);
						// int idx = rt.getFontAtIndex(j);

						if (idx != 0) {
							org.apache.poi.ss.usermodel.Font fnt = wb.getFontAt((short) idx);
							str.addAttribute(TextAttribute.FAMILY, fnt.getFontName(), j, j + 1);
							str.addAttribute(TextAttribute.SIZE, new Float(fnt.getFontHeightInPoints()), j, j + 1);
						}
					}
				}
				TextLayout layout = new TextLayout(str.getIterator(), frc);
				width = Math.max(width, layout.getAdvance() / defaultCharWidth);
			}
		}
		else {
			String sval = null;

			if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {

				DataFormat dataformat = wb.createDataFormat();
				short idx = style.getDataFormat();
				String format = dataformat.getFormat(idx).replaceAll("\"", "");
				double value = cell.getNumericCellValue();
				try {
					NumberFormat fmt;
					if ("General".equals(format))
						fmt = new DecimalFormat();
					else
						fmt = new DecimalFormat(format);
					sval = fmt.format(value);
				}
				catch (Exception e) {
					sval = "" + value;
				}
			}
			else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
				sval = /* String.valueOf( */cell.getBooleanCellValue() ? "TRUE" : "FALSE"/* ) */;
			}

			str = new AttributedString(sval + defaultChar);
			str.addAttribute(TextAttribute.FAMILY, font.getFontName());
			str.addAttribute(TextAttribute.SIZE, new Float(font.getFontHeightInPoints()));

			if (linux) {
				return sval != null ? sval.length() : 0;
			}
			else {
				TextLayout layout = new TextLayout(str.getIterator(), frc);
				width = Math.max(width, layout.getAdvance() / defaultCharWidth);
			}
		}
		return width;
	}

	/**
	 * @return an iterator of the PHYSICAL rows. Meaning the 3rd element may not be the third row if say for instance the second row is undefined.
	 */
	private Row[] getRows() {

		ArrayList<Row> rows = new ArrayList<Row>();
		for (int i = 0; i < getActiveSheet().getPhysicalNumberOfRows(); i++) {
			rows.add(getActiveSheet().getRow(i));
		}
		return rows.toArray(new Row[rows.size()]);
	}

	/**
	 * Número de filas.
	 * 
	 * @return
	 */
	public int getRowCount() {
		return getActiveSheet().getPhysicalNumberOfRows();
	}

	/**
	 * Número de columnas por fila.
	 * 
	 * @return
	 */
	public int getColumnCount(int rowIndex) {
		return getRows()[rowIndex].getLastCellNum() - getRows()[rowIndex].getFirstCellNum();
	}

	/**
	 * Establecer el ancho por defecto de las columnas.
	 * 
	 * @param width
	 */
	public void setDefaultColumnWidth(int width) {
		getActiveSheet().setDefaultColumnWidth((short) width);
	}

	public void autoBreaks() {
		getActiveSheet().setAutobreaks(true);
	}

	/**
	 * Repetir las filas iniciales de header y headerfields.
	 */
	@SuppressWarnings("deprecation")
	public void setRepeatingRows(int rowInit, int rowEnd) {

		workBook.setRepeatingRowsAndColumns(getActiveSheetIndex(), -1, -1, rowInit, rowEnd);
	}

	/**
	 * Repetir las filas iniciales de header y headerfields.
	 */
	@SuppressWarnings("deprecation")
	public void setRepeatingRows(int sheetIndex, int rowInit, int rowEnd) {

		workBook.setRepeatingRowsAndColumns(sheetIndex, -1, -1, rowInit, rowEnd);
	}

	/**
	 * Establecer el número de caracteres a visualizar por columna.
	 * 
	 * @param columnIndex
	 * @param width
	 */
	public void setColumnWidth(int columnIndex, int width) {

		// getActiveSheet().setColumnWidth((short) (columnIndex + 1), (short) ((50 *
		// 8) / ((double) 1 / 20)));
		getActiveSheet().setColumnWidth((short) columnIndex, (short) (256 * width + 500));
	}

	/**
	 * Establecer el número de caracteres a visualizar por columna.
	 * 
	 * @param columnIndex
	 * @param width
	 */
	public void setColumnWidthCharacters(int columnIndex, double characters) {

		getActiveSheet().setColumnWidth((short) columnIndex, (short) (characters * 256));
	}

	/**
	 * Establecer el número de pixeles a visualizar por columna.
	 * 
	 * @param columnIndex
	 * @param width
	 */
	public void setColumnWidthPixels(int columnIndex, double pixels) {

		getActiveSheet().setColumnWidth((short) columnIndex, (short) (pixels));
	}

	/**
	 * Formatea una celda con bordes
	 */
	public void setBoxCellStyle(CellStyle style, short colorBackground) {

		style.setFillForegroundColor(colorBackground);
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setBorderRight(CellStyle.BORDER_THIN);
		// style.setRightBorderColor(HSSFColor.BLUE.index);
		style.setBorderTop(CellStyle.BORDER_THIN);
	}

	/**
	 * Formatea una celda con bordes
	 */
	public void setBoxCell(int row, int columnInit, int columnEnd, short colorBackground) {
		for (int i = columnInit; i <= columnEnd; i++)
			setBoxCell(row, i, colorBackground, CellStyle.ALIGN_LEFT);
	}

	/**
	 * Formatea una celda con bordes
	 */
	public void setBoxCell(int row, int columnInit, int columnEnd, short colorBackground, short align) {
		for (int i = columnInit; i <= columnEnd; i++)
			setBoxCell(row, i, colorBackground, align);
	}

	/**
	 * Formatea una celda con bordes
	 */
	public void setBoxCell(int row, int columnInit, int columnEnd, short colorBackground, String align) {
		for (int i = columnInit; i <= columnEnd; i++)
			setBoxCell(row, i, colorBackground, getAlign(align));
	}

	/**
	 * Formatea una celda con bordes
	 */
	public void setBoxCell(int row, int columnInit, int columnEnd, String align) {
		for (int i = columnInit; i <= columnEnd; i++)
			setBoxCell(row, i, getAlign(align));
	}

	/**
	 * Formatea una celda con bordes
	 */
	public void setBoxCell(int row, int columnInit, int columnEnd, String colorBackground, String align) {
		for (int i = columnInit; i <= columnEnd; i++)
			setBoxCell(row, i, ReportVisualStyle.getColorFromString(colorBackground), getAlign(align));
	}

	/**
	 * Formatea una celda con bordes
	 */
	public void setBoxCell(int row, int column, short colorBackground, short align) {

		CellStyle style = createStyle();

		Cell c = getCell(row, column);
		if (c != null) {
			if (c.getCellStyle().getDataFormat() > 0) {
				style.setDataFormat(c.getCellStyle().getDataFormat());
			}
		}

		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setAlignment(align);
		style.setFillForegroundColor(colorBackground);
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setBorderTop(CellStyle.BORDER_THIN);
		getCell(row, column).setCellStyle(style);
	}

	/**
	 * Formatea una celda con bordes
	 */
	public void setBoxCell(int row, int column, String align) {
		setBoxCell(row, column, getAlign(align));
	}

	/**
	 * Formatea una celda con bordes
	 */
	public void setBoxCell(int row, int column, String backgroundColor, String align) {
		setBoxCell(row, column, ReportVisualStyle.getColorFromString(backgroundColor), getAlign(align));
	}

	/**
	 * Formatea una celda con bordes
	 */
	public void setBoxCell(int row, int column, short align) {

		CellStyle style = createStyle();
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setAlignment(align);
		style.setFillForegroundColor(HSSFColor.WHITE.index);
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setBorderTop(CellStyle.BORDER_THIN);
		getCell(row, column).setCellStyle(style);
	}

	/**
	 * Obtiene un estilo de celda para aplicar a un celda.
	 * 
	 * @return
	 */
	public CellStyle createStyle() {
		return workBook.createCellStyle();

	}

	/**
	 * Fijar a una página el contenido de la hoja.
	 */
	public void setFitToPage() {
		getActiveSheet().setFitToPage(true);
	}

	/**
	 * Redimensionar a una página
	 */
	public void resizeOnePage() {
		Sheet sheet = getActiveSheet();
		PrintSetup ps = sheet.getPrintSetup();
		// marcar el radiobutton de ajustar a la página
		sheet.setAutobreaks(true);
		ps.setFitWidth((short) 1);
		ps.setFitHeight((short) 500);
	}

	/**
	 * Establecer una celda como una fórmula hiperlink de Excel.
	 * 
	 * @param cell
	 * @param url
	 * @param text
	 */
	public void setHiperLink(Cell cell, String url, String text) {

		cell.setCellType(Cell.CELL_TYPE_FORMULA);
		cell.setCellFormula("HYPERLINK(\"http://" + url + "\", \"" + text + "\")");
	}

	/**
	 * Establece el tipo de celda con respecto al valor y fija el valor de la celda.
	 * 
	 * @param cell
	 * @param value
	 */
	public void setCellValue(Cell cell, Object value) throws Exception {

		cell.setCellType(getCellTypeFromValue(value));

		if (value instanceof String)
			cell.setCellValue(helper.createRichTextString((String) value));
		else if (value instanceof Boolean)
			cell.setCellValue(((Boolean) value).booleanValue());
		else if (value instanceof java.util.Date)
			cell.setCellValue((java.util.Date) value);
		else if (value instanceof Number)
			cell.setCellValue(((Number) value).doubleValue());
		else
			cell.setCellValue(helper.createRichTextString(""));
		// throw new Exception("El tipo de objeto " + value.getClass().getName() +
		// " no puede ser adaptado para incluirlo en una celda.");

	}

	/**
	 * Devuelve el tipo de celda con respecto al tipo de objeto que deseamos incluir como valor.
	 * 
	 * @param value
	 * @return
	 */
	public int getCellTypeFromValue(Object value) {

		if (value instanceof Number || value instanceof java.util.Date)
			return Cell.CELL_TYPE_NUMERIC;
		else if (value instanceof Boolean)
			return Cell.CELL_TYPE_BOOLEAN;
		else
			return Cell.CELL_TYPE_STRING;
	}

	/**
	 * Obtener el valor fecha de una celda.
	 * 
	 * @param row
	 * @param column
	 * @return
	 */
	public Date getDateCellValue(int row, int column) {
		try {
			return getCell(row, column).getDateCellValue();
		}
		catch (Exception e) {
			return null;
		}
	}

	/**
	 * Obtener el valor fecha de una celda.
	 * 
	 * @param row
	 * @param column
	 *          A o AB
	 * @return
	 */
	public Date getDateCellValue(int row, String column) {
		return getDateCellValue(row, getColumnFromRefString(column));
	}

	/**
	 * Obtener el valor de una celda de tipo string
	 * 
	 * @param row
	 * @param column
	 * @return
	 */
	public String getStringCellValue(int row, int column) {
		try {
			return getCell(row, column).getRichStringCellValue().getString();
		}
		catch (Exception e) {
			try {
				Object v = getCellValue(row, column);
				return v == null ? null : (v instanceof Number ? StringUtils.getNumberToString((Number) v) : v.toString());
			}
			catch (Exception e2) {
				return null;
			}
		}
	}

	/**
	 * Obtener el valor de una celda de tipo string
	 * 
	 * @param row
	 * @param column
	 *          A o AB
	 * @return
	 */
	public String getStringCellValue(int row, String column) {
		return getStringCellValue(row, getColumnFromRefString(column));
	}

	/**
	 * Devuelve el valor de una celda buscando por la posición de una fila donde se encuentre un texto.
	 * 
	 * @return
	 */
	public String getStringCellValue(int row, int rowFind, String textFind) throws Exception {

		int index = getIndexCell(rowFind, textFind);

		if (index > -1)
			return getCell(row, index).getRichStringCellValue().getString();
		else
			throw new Exception("Column '" + textFind + "' not exist.");
	}

	/**
	 * Devuelve el valor de una celda dependiendo de su tipología.
	 * 
	 * @return
	 */
	public Object getCellValue(int row, int column) {

		return getCellValue(getCell(row, column));
	}

	/**
	 * Devuelve el valor de una celda dependiendo de su tipología.
	 * 
	 * @param row
	 * @param column
	 *          A o AB
	 * @return
	 */
	public Object getCellValue(int row, String column) {

		return getCellValue(getCell(row, getColumnFromRefString(column)));
	}

	/**
	 * Devuelve el valor de una celda buscando por la posición de una fila donde se encuentre un texto.
	 * 
	 * @return
	 */
	public Object getCellValue(int row, int rowFind, String textFind) throws Exception {

		int index = getIndexCell(rowFind, textFind);

		if (index > -1)
			return getCellValue(getCell(row, index));
		else
			throw new Exception("Column '" + textFind + "' not exist.");
	}

	/**
	 * Devuelve el valor de una celda buscando por la posición de una fila donde se encuentre un texto.
	 * 
	 * @return
	 */
	public Date getDateCellValue(int row, int rowFind, String textFind) {

		int index = getIndexCell(rowFind, textFind);

		if (index > -1)
			return getDateCellValue(row, index);
		else
			return null;
	}

	/**
	 * Devuelve el valor de una celda de tipo double buscando por la posición de una fila donde se encuentre un texto.
	 * 
	 * @return
	 */
	public Double getDoubleCellValue(int row, int rowFind, String textFind) {

		int index = getIndexCell(rowFind, textFind);

		if (index > -1)
			return (Double) getCellValue(row, index);
		else
			return null;
	}

	/**
	 * Devuelve el índice de la celda que contiene un valor.
	 * 
	 * @param row
	 * @param text
	 * @return
	 */
	public int getIndexCell(int row, String text) {

		if (text == null || row < 0)
			return -1;

		int count = getRows()[row].getLastCellNum() - getRows()[row].getFirstCellNum();

		for (int i = 0; i < count; i++) {
			if (getStringCellValue(row, i) != null && getStringCellValue(row, i).trim().toLowerCase().equals(text.trim().toLowerCase()))
				return i;
		}
		return -1;
	}

	/**
	 * Devuelve el valor de una celda dependiendo de su tipología.
	 * 
	 * @return
	 */
	public Object getCellValue(Cell cell) {

		if (cell == null)
			return null;

		/*
		 * try{ if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC && HSSFDateUtil.isCellDateFormatted(cell)) {
		 * 
		 * Date dateCellValue = cell.getDateCellValue(); // && HSSFDateUtil.isValidExcelDate(cell.getNumericCellValue())) {
		 * 
		 * //HSSFDataFormat dataFormat = workBook.createDataFormat(); return new SimpleDateFormat (formatDateToJava(cell.getCellStyle().getDataFormat())).format (cell.getDateCellValue()); } }
		 * catch(Exception e){
		 * 
		 * }
		 */

		if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC && DateUtil.isCellDateFormatted(cell)) {
			return cell.getDateCellValue();
		}
		else
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_NUMERIC:
				return new Double(cell.getNumericCellValue());
			case Cell.CELL_TYPE_BOOLEAN:
				return new Boolean(cell.getBooleanCellValue());
			case Cell.CELL_TYPE_FORMULA:
				return new Double(cell.getNumericCellValue());
			case Cell.CELL_TYPE_STRING:
				return cell.getRichStringCellValue().getString();
			default:
				return null;
			}

	}

	/**
	 * Pasar el formato excel a formato Java compatible.
	 * 
	 * @param format
	 * @return
	 */
	public String formatDateToJava(short format) {

		switch (format) {
		case 174:
			return "MM/dd/yy";
		case 14:
			return "MM/dd/yyyy";
		case 177:
			return "MMM-dd-yyyy";
		case 178:
			return "MM-dd-yyyy";
		default:
			return "MM/dd/yyyy";
		}
	}

	/**
	 * Fusionar varias celdas estableciendo una región.
	 * 
	 * @param rowInit
	 * @param colInit
	 * @param rowEnd
	 * @param colEnd
	 */
	public void mergeCells(int rowInit, int colInit, int rowEnd, int colEnd) {
		getActiveSheet().addMergedRegion(new CellRangeAddress(rowInit, (short) colInit, rowEnd, (short) colEnd));
	}

	/**
	 * Obtiene un estilo de fuente.
	 * 
	 * @return
	 */
	public org.apache.poi.ss.usermodel.Font getFontStyle() {
		return workBook.createFont();
	}

	/***
	 * Formatea una celda con bordes y la fuente Tahoma
	 */
	public void setCellFont(Cell cell, String fontName) {

		org.apache.poi.ss.usermodel.Font font = workBook.createFont();
		// font.setFontHeightInPoints( (short) 12);
		font.setFontName(fontName);
		CellStyle style = workBook.createCellStyle();
		style.setFont(font);
		cell.setCellStyle(style);
	}

	/**
	 * Crea una fuente dado un nombre, tamaño, italica, grosor y subrayado
	 */
	public org.apache.poi.ss.usermodel.Font createFont(String fontName, int point, boolean italic, short boldWeight, byte underline) {

		org.apache.poi.ss.usermodel.Font font = workBook.createFont();
		font.setFontName(fontName);
		font.setFontHeightInPoints((short) point);
		font.setItalic(italic);
		font.setUnderline(underline);
		font.setBoldweight(boldWeight);
		return (font);
	}

	/***
	 * Ejecutar la grabación del archivo.
	 */
	public void writeFile(String fileName) throws Exception {
		this.fileName = fileName;
		writeFile();
	}

	/***
	 * Ejecutar la grabación del archivo.
	 */
	public void writeFile() throws Exception {
		FileOutputStream fileOut = new FileOutputStream(fileName);
		workBook.write(fileOut);
		fileOut.close();
	}

	/***
	 * Mostrar la hoja de excel en MS-Excel
	 */
	public void show() throws Exception {
		DesktopUtils.openFile(fileName);
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Workbook getWorkBook() {
		return workBook;
	}

	public void setWorkBook(Workbook workBook) {
		this.workBook = workBook;
	}

	/**
	 * Establecer el nombre de la hoja actual
	 * 
	 * @param name
	 */
	public void setNameActiveSheet(String name) {

		workBook.setSheetName(getActiveSheetIndex(), name);
	}

	/**
	 * Grabar la hoja de excel actual a formato CSV.
	 */
	public void saveToCSV(String fileName, String separator, String formatNumber, String formatDate, boolean includeRowSheetName) throws Exception {

		FileOutputStream file = new FileOutputStream(fileName);
		try {
			for (int j = 0; j < sheets.size(); j++) {

				Sheet sheet = sheets.get(j);

				if (includeRowSheetName)
					file.write((workBook.getSheetName(j) + "\n").toString().getBytes());

				for (int i = 0; i < sheet.getLastRowNum() + 1; i++) {

					Row record = sheet.getRow(i);

					if (record != null) {

						String recordValue = "";

						for (int k = 0; k < record.getLastCellNum(); k++) {

							Cell cell = record.getCell((short) k);
							Object value = getCellValue(cell);

							Date dateCellValue = null;
							try {
								dateCellValue = cell.getDateCellValue();
							}
							catch (Exception e) {
							}

							if (dateCellValue != null && DateUtil.isCellDateFormatted(cell) && DateUtil.isValidExcelDate(dateCellValue.getTime())) {

								value = new SimpleDateFormat(formatDateToJava(cell.getCellStyle().getDataFormat())).format(dateCellValue);
							}
							else if (value instanceof Number) {
								DecimalFormat f = new DecimalFormat(formatNumber);
								value = f.format(value).replaceAll(",", ".");
							}
							else if (value instanceof Date) {
								value = DateUtils.getDateToString((Date) value, formatDate);
							}

							// Evitar que el valor contenga el carácter , y entrecomillar para
							// evitar este error de división de celda por cada carácter ,.
							if (value != null && value instanceof String && value.toString().indexOf(",") > -1) {
								value = "\"" + value + "\"";
							}

							recordValue += (value != null ? value.toString() : "") + (k < record.getLastCellNum() - 1 ? separator : "");
						}
						recordValue += "\n";
						file.write(recordValue.toString().getBytes());
						// System.out.println(recordValue);
					}
				}
			}
		}
		finally {
			file.close();
		}
	}

	/**
	 * Obtener el formato de la fecha en Java.
	 * 
	 * @param cell
	 * @return
	 */
	public String getFormatDateJava(Cell cell) {

		double d = cell.getNumericCellValue();
		String cellText = null;
		// test if a date!
		if (DateUtil.isCellDateFormatted(cell)) {
			// format in form of M/D/YY
			GregorianCalendar cal = new GregorianCalendar();
			cal.setTime(DateUtil.getJavaDate(d));
			cellText = (String.valueOf(cal.get(Calendar.YEAR))).substring(2);
			cellText = cal.get(Calendar.MONTH) + 1 + "/" + cal.get(Calendar.DAY_OF_MONTH) + "/" + cellText;
		}
		return cellText;
	}

	public static void main(String args[]) throws Exception {

		ExcelWorkbookXSSF e = new ExcelWorkbookXSSF();
		e.loadResource("/datos.xlsx");
		e.setCellValue("OTRO VALOR", 4, 4);
		e.writeFile("c:/temp/a.xlsx");

		// e.saveToCSV("d:\\pruebas.csv", ",", Messages.getString("ExcelWorkbook.37"), Messages.getString("ExcelWorkbook.38"), true);
	}

	/**
	 * Poner o eliminar la visualización de la rejilla de las celdas.
	 * 
	 * @param visible
	 */
	public void setDisplayGridlines(boolean visible) {
		getActiveSheet().setDisplayGridlines(visible);
	}

	/**
	 * Ajusta la sheet actual a una página.
	 */
	public void autoSize() {
		getActiveSheet().setMargin((short) 3, 0.6);
		getActiveSheet().setAutobreaks(true);
		setFitToPage();
	}

	/**
	 * Obtener el DPI de una imagen
	 * 
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public String getDPI(String fileName) throws Exception {

		FileImageInputStream in = new FileImageInputStream(new File(fileName));
		Iterator<ImageReader> iterator = ImageIO.getImageReaders(in);
		ImageReader reader = iterator.next();
		reader.setInput(in);

		IIOMetadata data = reader.getImageMetadata(0);

		// metadata format names are: javax_imageio_jpeg_image_1.0 or
		// javax_imageio_1.0

		Element tree = (Element) data.getAsTree("javax_imageio_jpeg_image_1.0");
		Element jfif = (Element) tree.getElementsByTagName("app0JFIF").item(0);
		String dpiX = jfif.getAttribute("Xdensity");
		// String dpiY = jfif.getAttribute("Ydensity");
		return dpiX;
	}

	/**
	 * Clase que redimensiona la imagen a su tamaño original y la posiciona en una fila y celda de la hoja de Excel.
	 * 
	 * @author ROBERTO RANZ
	 */
	public static class PictureResizer {

		final Picture _picture;
		final Sheet _sheet;
		final float _pw;
		final float _ph;
		int col, row;
		CreationHelper helper;
		int dpi;

		public PictureResizer(Sheet sheet, Picture picture, int row, int col, CreationHelper helper) {
			this(sheet, picture, row, col, null, helper);
		}

		/**
		 * Constructor
		 * 
		 * @param sheet
		 * @param picture
		 * @param row
		 *          fila para posicionar la imagen
		 * @param col
		 *          columan para posicionar la imagen
		 */
		public PictureResizer(Sheet sheet, Picture picture, int row, int col, String _dpi, CreationHelper helper) {

			_picture = picture;
			_sheet = sheet;
			this.dpi = _dpi != null ? StringUtils.toInt(_dpi) : 96;
			this.row = row;
			this.col = col;
			this.helper = helper;

			org.apache.poi.ss.usermodel.Font defaultFont = sheet.getWorkbook().getFontAt((short) 0);
			Font font = new Font(defaultFont.getFontName(), Font.PLAIN, defaultFont.getFontHeightInPoints());

			FontMetrics fontMetrics = new Label().getFontMetrics(font);

			int div = dpi == 96 ? 72 : 40;

			// width of the default character in pixels at 96 dpi
			_pw = (float) fontMetrics.charWidth('0') * dpi / div;
			// height of the default character in pixels at 96 dpi
			// (if the row has a medium or thick top border, or if any
			// the current row has a thick bottom border then the row
			_ph = (float) (fontMetrics.getMaxAscent() + fontMetrics.getMaxDescent() + 0.75) * dpi / div;

		}

		// private float getColumnWidthInPixels(int column) {
		//
		// int cw = _sheet.getColumnWidth(column);
		// return _pw * cw / 256;
		// }

		// @SuppressWarnings("unused")
		// private float getRowHeightInPixels(int i) {
		//
		// Row row = _sheet.getRow(i);
		// float height;
		// if (row != null)
		// height = row.getHeight();
		// else
		// height = _sheet.getDefaultRowHeight();
		//
		// return height / 255 * _ph;
		// }

		/**
		 * Tamaño predefinido.
		 * 
		 * @return
		 */
		public ClientAnchor getPreferredSize() {

			ClientAnchor anchor = helper.createClientAnchor();

			anchor.setCol1(col);
			anchor.setRow1(row);

			// Dimension size = _picture.getImageDimension();
			// double scaledWidth = size.getWidth();
			// double scaledHeight = size.getHeight();

			// float w = 0;

			// space in the leftmost cell
			// w += getColumnWidthInPixels(anchor.getCol1()) * (1 - (float) anchor.getDx1() / 1024);
			short col2 = (short) (anchor.getCol1() + 1);
			int dx2 = 0;

			// while (w < scaledWidth) {
			// w += getColumnWidthInPixels(col2++);
			// }

			// if (w > scaledWidth) {
			// // calculate dx2, offset in the rightmost cell
			// col2--;
			// double cw = getColumnWidthInPixels(col2);
			// double delta = w - scaledWidth;
			// dx2 = (int) ((cw - delta) / cw * 1024);
			// }
			anchor.setCol2(col2);
			anchor.setDx2(dx2);

			// float h = 0;
			// h += (1 - (float) anchor.getDy1() / 256) * getRowHeightInPixels(anchor.getRow1());
			int row2 = anchor.getRow1() + 1;
			int dy2 = 0;

			// while (h < scaledHeight) {
			// h += getRowHeightInPixels(row2++);
			// }
			// if (h > scaledHeight) {
			// row2--;
			// double ch = getRowHeightInPixels(row2);
			// double delta = h - scaledHeight;
			// dy2 = (int) ((ch - delta) / ch * 256);
			// }
			anchor.setRow2(row2);
			anchor.setDy2(dy2);

			return anchor;
		}

		/**
		 * Redimensionar
		 */
		public void resize() {

			ClientAnchor anchor = helper.createClientAnchor();
			anchor.setAnchorType(2);

			ClientAnchor pref = getPreferredSize();

			int row2 = anchor.getRow1() + (pref.getRow2() - pref.getRow1());
			int col2 = anchor.getCol1() + (pref.getCol2() - pref.getCol1());

			anchor.setCol2((short) col2);
			anchor.setDx1(0);
			anchor.setDx2(pref.getDx2());

			anchor.setRow2(row2);
			anchor.setDy1(0);
			anchor.setDy2(pref.getDy2());
		}

	}

	/**
	 * Copiar style cell
	 * 
	 * @param sheet
	 * @param cell
	 * @param dstRow
	 * @param dstColumn
	 */
	public void copyCell(int oriRow, int oriColumn, final int dstRow, final int dstColumn, boolean values) {

		Sheet sheet = getActiveSheet();

		Cell cell = sheet.getRow(oriRow).getCell(oriColumn);

		Row row = sheet.getRow(dstRow);
		if (row == null) {
			row = sheet.createRow((short) dstRow);
		}
		Cell dstCell = row.getCell((short) dstColumn);
		if (dstCell == null) {
			dstCell = row.createCell((short) dstColumn);
		}
		dstCell.setCellStyle(cell.getCellStyle());

		if (values)
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_BOOLEAN:
				dstCell.setCellValue(cell.getBooleanCellValue());
				break;
			case Cell.CELL_TYPE_ERROR:
				dstCell.setCellErrorValue(cell.getErrorCellValue());
				break;
			case Cell.CELL_TYPE_NUMERIC:
				dstCell.setCellValue(cell.getNumericCellValue());
				break;
			case Cell.CELL_TYPE_STRING:
				dstCell.setCellValue(cell.getStringCellValue());
				break;
			}
	}

	/**
	 * Copia e insertar una nueva fila con todos los estilos de las celdas de la fila copiada.
	 * 
	 * @param row
	 * @param i
	 */
	public void copyRow(int copyRow, int newRow) {

		Sheet sheet = getActiveSheet();

		sheet.shiftRows(newRow, sheet.getLastRowNum(), 1);
		for (int i = 0; i < sheet.getRow(copyRow).getLastCellNum(); i++) {
			copyCell(copyRow, i, newRow, i, false);
		}
	}

	/**
	 * Obtener fórmula para sumar celdas de una columna.
	 */
	public void setColumnFormula(String formula, int init_row, int end_row, int row, int column) {

		String value = getCellRefString(init_row, column) + ":" + getCellRefString(end_row, column);
		// for(int i = init_row; i <= end_row; i++) {
		// value += (value.equals("") ? "" : ":") + getCellRefString(i, column);
		// }
		// if (init_row == end_row)
		// value += ":" + value;

		setFormula(row, column, formula + "(" + value + ")");
	}

	/**
	 * Obtener fórmula para sumar celdas de una fila.
	 */
	public void setRowFormula(String formula, int init_column, int end_column, int row, int column) {

		setFormula(row, column, getRowFormula(formula, init_column, end_column, row));
	}

	/**
	 * Obtener fórmula para sumar celdas de una fila.
	 */
	public String getRowFormula(String formula, int init_column, int end_column, int row) {

		String value = getCellRefString(row, init_column) + ":" + getCellRefString(row, end_column);
		// for(int i = init_column; i <= end_column; i++) {
		// value += (value.equals("") ? "" : ":") + getCellRefString(row, i);
		// }
		// if (init_column == end_column)
		// value += ":" + value;

		return formula + "(" + value + ")";
	}

	/**
	 * Obtener el nombre de la celda en formato A1, E4.
	 * 
	 * @param row
	 * @param column
	 * @return
	 */
	public String getCellRefString(int row, int column) {

		StringBuffer retval = new StringBuffer();
		int tempcellnum = column;
		do {
			retval.insert(0, A2Z[tempcellnum % A2Z.length]);
			tempcellnum = (tempcellnum / A2Z.length) - 1;
		}
		while (tempcellnum >= 0);

		retval.append(row + 1);
		return retval.toString();
	}

	/**
	 * Obtener el nombre de la celda en formato A1, E4.
	 * 
	 * @param row
	 * @param column
	 * @return
	 */
	public String getColumnRefString(int column) {

		StringBuffer retval = new StringBuffer();
		int tempcellnum = column;
		do {
			retval.insert(0, A2Z[tempcellnum % A2Z.length]);
			tempcellnum = (tempcellnum / A2Z.length) - 1;
		}
		while (tempcellnum >= 0);

		return retval.toString();
	}

	/**
	 * Obtener el nombre de la celda en formato A1, E4.
	 * 
	 * @param row
	 * @param column
	 * @return
	 */
	public int getColumnFromRefString(String value) {

		for (int i = 0; i < 1000; i++) {

			if (value.equals(getColumnRefString(i)))
				return i;

		}
		return -1;
	}

	/**
	 * Alineación por defecto de una columna
	 * 
	 * @param i
	 * @param alignLeft
	 */
	public void setColumnAlignment(int i, short alignLeft) {

		CellStyle s = createStyle();
		s.setAlignment(CellStyle.ALIGN_LEFT);
		getActiveSheet().setDefaultColumnStyle(0, s);
	}

	/**
	 * Comprueba si la fila pasada como parámetro está oculta.
	 * 
	 * @return
	 */
	public boolean isRowHidden(int row) {
		boolean hidden = false;
		try {
			hidden = getActiveSheet().getRow(row).getRowStyle().getHidden();
			if (hidden)
				System.out.println(row + " " + hidden);
		}
		catch (Exception e) {
		}
		return hidden;
	}

	/**
	 * @param title
	 * @return
	 */
	public RichTextString createRichTextString(String text) {
		return helper.createRichTextString(text);
	}

	/**
	 * Obtener una fila
	 * 
	 * @param row
	 * @return
	 */
	public Row getRow(int row) {

		return getActiveSheet().getRow(row);

	}

	/**
	 * Obtener una fila
	 * 
	 * @param row
	 * @return
	 */
	public void setRowHeight(int row, int pixels) {

		getActiveSheet().getRow(row).setHeight((short) pixels);
	}
}

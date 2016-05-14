package com.javalego.poi.report;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFFooter;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Footer;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Sheet;

import com.javalego.config.EnvironmentVariables;
import com.javalego.poi.report.elements.ReportField;
import com.javalego.poi.report.elements.ReportFields;
import com.javalego.poi.report.elements.ReportGroup;
import com.javalego.poi.report.elements.ReportGroups;
import com.javalego.poi.report.elements.ReportHeader;
import com.javalego.poi.report.elements.ReportHeaderFields;
import com.javalego.poi.report.elements.ReportHeaderFieldsEditor;
import com.javalego.poi.report.elements.ReportSeparatorField;
import com.javalego.poi.report.elements.ReportTotalField;
import com.javalego.poi.report.elements.ReportTotalFields;
import com.javalego.util.DateUtils;
import com.javalego.util.DesktopUtils;
import com.javalego.util.SystemUtils;
import com.javalego.util.StringUtils;

/**
 * Clase básica para la generación de informes.
 * 
 * @author ROBERTO
 */
public abstract class BasicReport {

	/**
	 * Libro Excel
	 */
	protected ExcelWorkbookXSSF excel;

	/**
	 * Nombre del archivo de salida.
	 */
	protected String fileName;

	/**
	 * Usar el template de xls para fijar la resolución de la página. Hay un
	 * caso donde es necesario desactivar esta opción, en la generación de
	 * informes con jawin y combinación de correspondencia porque no se sabe muy
	 * bien pero no funciona correctamente y lo reconoce la hoja con válida,
	 * obteniendo únicamente un campo F1 en lugar de los campos fijados.
	 */
	//protected boolean useTemplate = true;

	/**
	 * Cabecera del informe.
	 */
	protected ReportHeader header = new ReportHeader(this);

	/**
	 * Campos del informe que corresponden a los atributos de los objetos.
	 */
	protected ReportFields fields = new ReportFields(this);

	/**
	 * Lista de Cabeceras de agrupaciones de campos
	 */
	protected ArrayList<ReportHeaderFields> headersFields = new ArrayList<ReportHeaderFields>();

	/**
	 * Campos a totalizar.
	 */
	protected ReportTotalFields totalFields = new ReportTotalFields(this);

	/**
	 * Grupos.
	 */
	protected ReportGroups groups = new ReportGroups(this);

	/**
	 * Apaisado.
	 */
	protected boolean landscape;

	/**
	 * Mostrar los registros de cada objeto.
	 */
	protected boolean showDetail = true;

	/**
	 * Mostrar los campos a totalizar al finalizar el informe.
	 */
	protected boolean showTotals = true;

	/**
	 * Incluir documentación del informe.
	 */
	protected boolean showHelp = false;

	/**
	 * Nombre del font utilizado por defecto.
	 */
	protected String fontNameDefault = "Tahoma";

	/**
	 * Posición de la fila dentro del proceso de generación del informe y que
	 * será utilizado por el resto de los objetos que componen dicho informe.
	 */
	protected int rowIndex = -1;

	/**
	 * Posición de progreso dentro del array de objetos en el proceso de
	 * generación del informe.
	 */
	protected int objectsIndex = -1;

	/**
	 * Eliminar el valor de la celda si la misma celda del anterior registro es
	 * igual.
	 */
	protected boolean removeEquals;

	/**
	 * Mostrar la fecha de ejecución del informe.
	 */
	protected boolean showDate = true;

	/**
	 * Mostrar paginado.
	 */
	protected boolean showNumberPages = true;

	/**
	 * Mostrar el número de registros impresos.
	 */
	protected boolean showRecordCount = true;

	/**
	 * Texto de totalizar registros.
	 */
	protected String textTotalRecords;

	/**
	 * Mensaje al final de la página.
	 */
	protected String textFooter;

	/**
	 * Configurar página (landscape, ancho columnas, quitar grid sólo para
	 * registros, etc.) utilizar esta opción cuando generar ficheros de excel
	 * utilizados para la combinación por correspondencia porque a veces no
	 * reconoce las cabeceras de las columnas.
	 */
	protected boolean configPage = true;

	/**
	 * Repetir las filas de cabecera hasta las cabeceras de los campos en todas
	 * las páginas.
	 */
	protected boolean repeatingRows = true;

	/**
	 * Repetir un número de columnas desde la columna 0.
	 */
	protected boolean repeatingColumns = true;

	/**
	 * Número de filas a repetir en la hoja del informe.
	 */
	protected int repeatingRowsCount;

	/**
	 * Número columnas a repetir.
	 */
	protected int repeatingColumnsCount;

	/**
	 * Nombre del archivo excel a generar.
	 * 
	 * @return
	 */
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {

		this.fileName = fileName;
		getExcel().setFileName(fileName);
	}

	/**
	 * Generar un nombre de archivo a grabar en el directorio temporal del
	 * usuario y asignarlo al fileName del report.
	 * 
	 * @param delete
	 *            Eliminar
	 * @return
	 */
	public String generateFileNameTmp() {

		// Purgar directorio de ficheros temporales generados por gana con 2
		// horas de diferencia
		File dir = new File(EnvironmentVariables.getUserTmp());

		if (dir.list().length > 0) {

			for (String filename : dir.list()) {

				if (filename.indexOf("gana_tmp_") > -1) {

					File file = new File(EnvironmentVariables.getUserTmp() + filename);
					Date d = new Date(file.lastModified());

					if (DateUtils.getDiffHoursDates(d, new Date()) > 1) {
						file.delete();
					}

				}
			}
		}

		fileName = EnvironmentVariables.getUserTmp() + "gana_tmp_report_" + SystemUtils.getIdDocument() + ".xlsx";

		if (excel != null)
			excel.setFileName(fileName);

		return fileName;
	}

	/**
	 * Campos
	 * 
	 * @return
	 */
	public ReportFields getFields() {
		return fields;
	}

	/**
	 * Número de campos visibles
	 * 
	 * @return
	 */
	public int getFieldCount() {
		int count = 0;
		for (ReportField f : getFields()) {
			if (f.isVisible())
				++count;
		}
		return count;
	}

	public void setFields(ReportFields fields) {
		this.fields = fields;
	}

	/**
	 * Libro excel
	 * 
	 * @return
	 */
	public ExcelWorkbookXSSF getExcel() {
		if (excel == null) {
			excel = new ExcelWorkbookXSSF(fileName);
		}
		return excel;
	}

	public void setExcel(ExcelWorkbookXSSF excel) {
		this.excel = excel;
	}

	public ReportHeader getHeader() {
		return header;
	}

	public void setHeader(ReportHeader header) {
		this.header = header;
	}

	/**
	 * Apaisado.
	 * 
	 * @return
	 */
	public boolean isLandscape() {
		return landscape;
	}

	public void setLandscape(boolean landscape) {
		this.landscape = landscape;
	}

	/**
	 * Tipo de letra por defecto que será aplicable a todos los elementos del
	 * informe.
	 * 
	 * @return
	 */
	public String getFontNameDefault() {

		if (EnvironmentVariables.getDefaultFontName() != null)
			return EnvironmentVariables.getDefaultFontName();
		else
			return fontNameDefault;
	}

	public void setFontNameDefault(String fontNameDefault) {
		this.fontNameDefault = fontNameDefault;
	}

	/**
	 * Fila actual en la hoja de excel dentro del proceso de generación del
	 * informe.
	 */
	public int getRowIndex() {

		return rowIndex;
	}

	/**
	 * Fila actual en la hoja de excel dentro del proceso de generación del
	 * informe.
	 */
	public void setRowIndex(int row) {
		rowIndex = row;
	}

	/**
	 * Campos a totalizar al finalizar el informe.
	 * 
	 * @return
	 */
	public ReportTotalFields getTotalFields() {
		return totalFields;
	}

	public void setTotalFields(ReportTotalFields totalFields) {
		this.totalFields = totalFields;
	}

	/**
	 * Posición de progreso dentro del array de objetos en el proceso de
	 * generación del informe.
	 */
	public int getObjectsIndex() {
		return objectsIndex;
	}

	public ReportGroups getGroups() {
		return groups;
	}

	public void setGroups(ReportGroups groups) {
		this.groups = groups;
	}

	public boolean isShowDetail() {
		return showDetail;
	}

	public void setShowDetail(boolean showDetail) {
		this.showDetail = showDetail;
	}

	public boolean isShowTotals() {
		return showTotals;
	}

	public void setShowTotals(boolean showTotals) {
		this.showTotals = showTotals;
	}

	public ArrayList<ReportHeaderFields> getHeadersFields() {
		return headersFields;
	}

	/**
	 * Ejecutar el informe.
	 * 
	 * @throws Exception
	 */
	public void execute(boolean writeFile) throws Exception {

		// Eliminar el fichero a generar para no mostrar el
		rowIndex = -1;

		setVisibleFields();

		totalFields.initialize();

		writeHeader();

		writeHeaderFields();

		writeDetail();

		writeTotalFields();

		writeFooter();

		configColumns();

		if (configPage)
			configPage();

		if (writeFile)
			writeFile();
	}

	public void run() throws Exception {
		execute(true);
		show();
	}

	/**
	 * No visualizar los campos que no tengan totales
	 */
	protected void setVisibleFields() {

		if (!showDetail) {

			for (int i = 0; i < fields.size(); i++) {

				ReportField field = ((ReportField) fields.get(i));
				// buscar el campo en los totales y totales de los grupos
				boolean exist = false;

				for (int k = 0; k < totalFields.size(); k++) {
					if (((ReportTotalField) totalFields.get(k)).getField() == field)
						exist = true;
				}

				for (int k = 0; k < groups.size(); k++) {

					ReportGroup group = (ReportGroup) groups.get(k);

					for (int j = 0; j < group.getTotalFields().size(); j++) {

						if (((ReportTotalField) group.getTotalFields().get(j)).getField() == field)
							exist = true;
					}
				}
				if (!exist)
					field.setVisible(false);
			}
		}
	}

	/**
	 * Incrementar la posición de la fila actual de impresión.
	 */
	public void addRowIndex() {
		++rowIndex;
	}

	/**
	 * Configurar el ancho de las columnas con respecto a la información de las
	 * celdas.
	 */
	protected void configColumns() {

		// configurar el ancho de las columnas de los grupos.
		for (int i = 0; i < groups.size(); i++)
			if (showDetail)
				getExcel().setColumnWidthCharacters(i, (i + 2));
			else
				getExcel().setColumnWidth(i, ((ReportGroup) groups.get(i)).getMaxWidth());

		// configurar el ancho de las columnas de los campos.
		int cont = 0;
		for (int i = 0; i < fields.size(); i++) {

			// excel.autoSizeColumn((short)i);
			ReportField field = ((ReportField) fields.get(i));

			if (field.isVisible()) {

				if (field.getDisplayWidth() > 0) {
					getExcel().setColumnWidthCharacters(getInitColumnIndex() + cont, field.getDisplayWidth());
				}
				else {
					// Formateo de la primera columna para evita tomar el ancho
					// del título y desajustar el informe.
					// if (cont == getInitColumnIndex()) {
					// double pixels = field.getMaxWidthPixels();
					// // Ampliar el ancho de la columna si tiene un campo a
					// totalizar.
					// if
					// (getTotalFields().getReportTotalField(field.getFieldName())
					// != null)
					// pixels += 2;
					// getExcel().setColumnWidthCharacters(getInitColumnIndex()
					// + cont, pixels);
					// }
					// else
					getExcel().autoSizeColumn(getInitColumnIndex() + cont);

				}
				++cont;
			}
		}

		// Ajustar de nuevo la fila con el máximo tamaño calculado con los
		// valores contenidos en sus campos ya que al realizar el autosize del
		// paso anterior
		// la columna 0 se ajusta con el título y queda desproporcionada el
		// campo 1.
		// if (groups.size() == 0) {
		// if (fields.size() > 0) {
		// getExcel().setColumnWidthCharacters(0,
		// fields.get(0).getMaxWidthPixels());
		// }
		// }

		// Merge de celdas para el título hasta completar todos los campos
		// mostrados en el informe.
		if (cont > 1 && header.hasLogos()) {
			if (header.getTitleRow() > -1)
				excel.mergeCells(header.getTitleRow(), getInitColumnIndex(), header.getTitleRow(), getInitColumnIndex() + cont - 1);
			if (header.getSubtitleRow() > -1)
				excel.mergeCells(header.getSubtitleRow(), getInitColumnIndex(), header.getSubtitleRow(), getInitColumnIndex() + cont - 1);
		}

		// Logos
		try {
			header.generateLogos();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Ajustar las propiedades de las hojas.
	 */
	protected void configPage() {

		Sheet sheet = getExcel().getActiveSheet();
		PrintSetup ps = sheet.getPrintSetup();

		// Repetir filas y columnas.
		if (isRepeatingRows() && repeatingRowsCount > -1)
			excel.setRepeatingRows(0, repeatingRowsCount);

		// if (isRepeatingColumns() && repeatingColumnsCount > -1)
		// excel.setRepeatingColumns(0, repeatingColumnsCount);

		sheet.setDisplayGridlines(false);
		// Dejar fijo un conjuto filas o/y columnas
		// sheet.createFreezePane( 0, 3 ); // deja fijas las 3 primeras filas.
		// sheet.createFreezePane( 1, 0, 1, 0 ); // deja fija la primera
		// columna.
		// Create a split with the lower left side being the active quadrant
		// sheet.createSplitPane( 2000, 2000, 0, 0, HSSFSheet.PANE_LOWER_LEFT );

		// margen bottom de la hoja 0.6 = 1.5 en Excel (para que no se solapen
		// las filas con el footer)
		sheet.setMargin((short) 3, 0.6);
		// marcar el radiobutton de ajustar a la página
		sheet.setAutobreaks(true);
		// excel.setColumnWidth(2, 30);
		// ajustar a la página 1 a 500 páginas.
		// excel.setFitToPage();
		// sheet.setVerticallyCenter(true);
		ps.setFitWidth((short) 1);
		ps.setFitHeight((short) 500);
		// horizontal
		ps.setLandscape(landscape);

		if (header.hasRightLogo())
			excel.getActiveSheet().setMargin(Sheet.RightMargin, 0);

		// Crear agrupaciones de filas o columnas.
		// sheet.groupRow((short)3,(short)5);
		// sheet.setRowGroupCollapsed((short)3, true);
	}

	/**
	 * Escribir el píe del informe.
	 */
	protected void writeFooter() {

		Sheet sheet = getExcel().getActiveSheet();
		Footer footer = sheet.getFooter();

		if (showDate)
			footer.setLeft("Date " + /* Messages.getString("BasicReport.5") + */HSSFFooter.date());

		if (!StringUtils.isEmpty(textFooter))
			footer.setCenter(textFooter);

		if (showNumberPages)
			footer.setRight("Pages " /* Messages.getString("BasicReport.6") */+ HSSFFooter.page() + "/" + HSSFFooter.numPages()); // .,.r
	}

	/**
	 * Escribir el fichero Excel.
	 */
	public void writeFile() throws Exception {

		if (StringUtils.isEmpty(fileName)) {
			generateFileNameTmp();
		}
		getExcel().writeFile();
	}

	/**
	 * Mostrar el archivo en pantalla.
	 */
	public void show() throws Exception {
		if (fileName != null && !fileName.equals("")) {
			if (new File(fileName).exists()) {
				DesktopUtils.openFile(fileName);
			}
			else
				throw new Exception("Not exist " + fileName);
		}
	}

	/**
	 * Generar la cabecera del informe.
	 */
	protected void writeHeader() throws Exception {
		header.generate();
	}

	/**
	 * Generar las filas con las cabeceras de los diferentes campos definidos.
	 * 
	 */
	protected void writeHeaderFields() throws Exception {

		// Cabeceras de campos generadas por un editor especializado que realiza
		// la lectura de las cabeceras anidades jerárquicamente.
		if (headersFields.size() > 0) {
			ReportHeaderFieldsEditor e = new ReportHeaderFieldsEditor(this);
			e.generate(headersFields);
		}

		fields.generate();
	}

	/**
	 * Obtiene la posición inicial de las columnas que representan los campos
	 * teniendo en cuenta las agrupaciones existentes.
	 * 
	 * @return
	 */
	public int getInitColumnIndex() {
		return getGroups().size() + (header.hasLogos() ? 1 : 0);
	}

	/**
	 * Imprimir las cabeceras y totales de las agrupaciones definidas, si
	 * procede.
	 * 
	 * @throws Exception
	 */
	protected void writeGroups(Object dataObject) throws Exception {

		groups.generate(dataObject);
	}

	/**
	 * Imprimir los campos totales.
	 * 
	 * @throws Exception
	 */
	protected void writeTotalFields() throws Exception {

		if (isShowTotals()) {

			addRowIndex();

			totalFields.generate(groups != null && groups.size() > 0 ? HSSFColor.ORANGE.index : (short) -1);
		}

		// Total de elementos impresos.
		if (showRecordCount) {

			Cell cell = null;

			int rowIndex = getRowIndex();

			cell = getExcel().getCell(rowIndex + 1, getInitColumnIndex());

			getExcel().setCellValue(cell, (textTotalRecords != null ? textTotalRecords + ": " : "") + new Integer(getDataSize()));
		}

	}

	/**
	 * Añadir un campo al informe.
	 * 
	 * @param fieldName
	 * @return
	 */
	public ReportField addField(String fieldName) {
		return fields.add(fieldName);
	}

	/**
	 * Añadir una separador de campos o columna en medio del resto de campos.
	 * 
	 * @return
	 */
	public ReportSeparatorField addSeparatorField() {
		return fields.addSeparator();
	}

	/**
	 * Añadir un campo al informe.
	 * 
	 * @param fieldName
	 * @param title
	 * @return
	 */
	public ReportField addField(String fieldName, String title) {
		return fields.add(fieldName, title);
	}

	/**
	 * Añadir un campo a totalizar
	 * 
	 * @param fieldName
	 * @return
	 */
	public ReportTotalField addTotalField(ReportField field) {
		return getTotalFields().add(field);
	}

	public ReportTotalField getReportTotalField(String fieldName) {
		return totalFields.getReportTotalField(fieldName);
	}

	/**
	 * Obtiene el valor de un atributo o campo de una colección de objetos.
	 * 
	 * @param fieldName
	 * @return
	 * @throws Exception
	 */
	public abstract Object getFieldValue(Object dataObject, String fieldName) throws Exception;

	/**
	 * Obtiene el objeto actual dentro del proceso de generación del informe.
	 * 
	 * @return
	 */
	public abstract Object getActualObject();

	/**
	 * Obtiene un objeto por su índice
	 * 
	 * @return
	 */
	public abstract Object getObject(int index);

	/**
	 * Imprimir detalle con la información que maneja el informe.
	 * 
	 * @throws Exception
	 */
	protected abstract void writeDetail() throws Exception;

	/**
	 * Número de elementos a imprimir
	 * 
	 */
	public abstract int getDataSize();

	/**
	 * Configurar algunos elementos del informe.
	 */
	public void config(String title) {

		setFileName(EnvironmentVariables.getUserTmp() + "tmp.xlsx");
		getHeader().setTitle(title);
	}

	/**
	 * Eliminar el valor de la celda si la misma celda del anterior registro es
	 * igual.
	 * 
	 * @return
	 */
	public boolean isRemoveEquals() {
		return removeEquals;
	}

	/**
	 * Eliminar el valor de la celda si la misma celda del anterior registro es
	 * igual.
	 * 
	 * @param removeEquals
	 */
	public void setRemoveEquals(boolean removeEquals) {
		this.removeEquals = removeEquals;
	}

	public String getTextFooter() {
		return textFooter;
	}

	public void setTextFooter(String footer) {
		this.textFooter = footer;
	}

	public boolean isShowDate() {
		return showDate;
	}

	public void setShowDate(boolean showDate) {
		this.showDate = showDate;
	}

	public boolean isShowNumberPages() {
		return showNumberPages;
	}

	public void setShowNumberPages(boolean showNumberPages) {
		this.showNumberPages = showNumberPages;
	}

	public boolean isShowRecordCount() {
		return showRecordCount;
	}

	public void setShowRecordCount(boolean showRecordCount) {
		this.showRecordCount = showRecordCount;
	}

	public boolean isShowHelp() {
		return showHelp;
	}

	public void setShowHelp(boolean showHelp) {
		this.showHelp = showHelp;
	}

	public boolean isRepeatingRows() {
		return repeatingRows;
	}

	public void setRepeatingRows(boolean repeatingRows) {
		this.repeatingRows = repeatingRows;
	}

	public int isRepeatingColumnsCount() {
		return repeatingColumnsCount;
	}

	public void setRepeatingColumnsCount(int repeatingColumnsCount) {
		this.repeatingColumnsCount = repeatingColumnsCount;
	}

	public int isRepeatingRowsCount() {
		return repeatingRowsCount;
	}

	public void setRepeatingRowsCount(int repeatingRowsCount) {
		this.repeatingRowsCount = repeatingRowsCount;
	}

	public boolean isRepeatingColumns() {
		return repeatingColumns;
	}

	public void setRepeatingColumns(boolean repeatingColumns) {
		this.repeatingColumns = repeatingColumns;
	}

	public int getRepeatingColumnsCount() {
		return repeatingColumnsCount;
	}

	public int getRepeatingRowsCount() {
		return repeatingRowsCount;
	}

	public boolean isConfigPage() {
		return configPage;
	}

	public void setConfigPage(boolean configPage) {
		this.configPage = configPage;
	}

	public String getTextTotalRecords() {
		return textTotalRecords;
	}

	public void setTextTotalRecords(String textTotalRecords) {
		this.textTotalRecords = textTotalRecords;
	}

}

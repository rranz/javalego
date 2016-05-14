package com.javalego.xls.report.object;

import java.io.File;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFooter;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import com.javalego.config.EnvironmentVariables;
import com.javalego.exception.JavaLegoException;
import com.javalego.util.DesktopUtils;
import com.javalego.util.ReflectionUtils;
import com.javalego.util.StringUtils;
import com.javalego.xls.report.ExcelWorkbook;
import com.javalego.xls.report.object.elements.GroupProperties;
import com.javalego.xls.report.object.elements.Header;
import com.javalego.xls.report.object.elements.Property;
import com.javalego.xls.report.object.elements.Section;

/**
 * Clase básica para la generación de informes.
 * @author ROBERTO
 */
public class ReportObject {

	/**
	 * Objeto de donde se obtienen los valores de sus propiedades para
	 * contruir el informe.
	 */
	protected Object object;

	/**
	 * Secciones de que consta el informe.
	 */
	protected ArrayList<Section> sections = new ArrayList<Section>();

	/**
	 * Cabecera del informe.
	 */
	protected Header header = new Header(this);

	/**
	 * Libro Excel
	 */
	protected ExcelWorkbook excel;

	/**
	 * Nombre del archivo de salida.
	 */
	protected String fileName;

	/**
	 * Apaisado.
	 */
	protected boolean landscape;

	/**
	 * Incluir documentación del informe.
	 */
	protected boolean showHelp = false;

	/**
	 * Nombre del font utilizado por defecto.
	 */
	protected String fontNameDefault = "Tahoma";

	/**
	 * Posición de la fila dentro del proceso de generación del informe y que será utilizado por el resto de los objetos que componen dicho informe. // t.,.2
	 */
	protected int rowIndex = -1;

	/**
	 * Columna inicial
	 */
	protected int columnIndex = 0;

	/**
	 * Mostrar la fecha de ejecución del informe.
	 */
	protected boolean showDate = true;

	/**
	 * Mensaje al final de la página.
	 */
	protected String textFooter;

	/**
	 * Nombre del archivo excel a generar.
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
	 * Generar un nombre de archivo a grabar en el directorio temporal del usuario y
	 * asignarlo al fileName del report.
	 * @return
	 */
	public String generateFileNameTmp(){
		for(int i = 0; i < 100; i++){
			File file = new File(EnvironmentVariables.getUserTmp() + "tmp" + i + ".xls"); 
			// Intentar eliminarlo
			if (file.exists())
				try{
					file.delete();
				}
				catch(Exception e){
				}
			if (!file.exists()){
				setFileName(file.getAbsolutePath());
				break;
			}
		}
		return fileName;
	}

	/**
	 * Libro excel
	 * @return
	 */
	public ExcelWorkbook getExcel() {
		if (excel == null)
			excel = new ExcelWorkbook(fileName);
		return excel;
	}

	public void setExcel(ExcelWorkbook excel) {
		this.excel = excel;
	}

	/**
	 * Apaisado.
	 * @return
	 */
	public boolean isLandscape() {
		return landscape;
	}

	public void setLandscape(boolean landscape) {
		this.landscape = landscape;
	}

	/**
	 * Tipo de letra por defecto que será aplicable a todos los elementos del informe.
	 * @return
	 */
	public String getFontNameDefault() {
		return fontNameDefault;
	}

	public void setFontNameDefault(String fontNameDefault) {
		this.fontNameDefault = fontNameDefault;
	}

	/**
	 * Fila actual en la hoja de excel dentro del proceso de generación del informe.
	 */
	public int getRowIndex() {
		return rowIndex;
	}

	/**
	 * Ejecutar el informe.
	 * @throws Exception
	 */
	public void executeAndShow() throws Exception {
		execute(true);
		DesktopUtils.openFile(fileName);
	}
	
	/**
	 * Ejecutar el informe.
	 * @throws Exception
	 */
	public void execute(boolean writeFile) throws Exception {

		if (object == null)
			throw new JavaLegoException("Not exist object", JavaLegoException.WARNING); 

		// Generar el nombre del fichero xls temporal.
		if (excel.getFileName() == null && StringUtils.isEmpty(fileName)) {
			fileName = generateFileNameTmp();
			excel.setFileName(fileName);
		}

		// Inicializar posición de la fila y columna.
		rowIndex = -1;
		columnIndex = 0;
		
		header.generate();
		
		writeSections();
		
		writeFooter();
		
		configColumns();
		
		configPage();
		
		if (writeFile)
			writeFile();
	}

	/**
	 * Generar contenido de las diferentes secciones definidas.
	 * @throws Exception
	 */
	private void writeSections() throws Exception {

		if (sections.size() == 0)
			throw new JavaLegoException("Not exit sections", JavaLegoException.ERROR); 

		int row = rowIndex;

		for(int i = 0; i < sections.size(); i++) {

			rowIndex = row;

			sections.get(i).generate();

			addColumnIndex();

			if (i < sections.size()-1)
				addSeparator();
		}

	}

	/**
	 * Añadir columna separador de secciones.
	 * @throws Exception
	 */
	private void addSeparator() throws Exception {
		
		HSSFCell cell = excel.getCell(getRowIndex(), getColumnIndex());
		excel.setCellValue(cell, "    "); 
	}

	public void run() throws Exception {
		execute(true);
		show();
	}

	/**
	 * Incrementar la posición de la fila actual de impresión.
	 */
	public void addRowIndex(){
		 ++rowIndex;
	}

	/**
	 * Incrementar la posición de la columna actual de impresión.
	 */
	public void addColumnIndex(){
		 ++columnIndex;
	}

	/**
	 * Configurar el ancho de las columnas con respecto a la información de las celdas.
   */
	protected void configColumns(){

		excel.setColumnWidth(0, 1);

		// Ajustar todas las columnas
		for(int i = 1; i < getColumnIndex()+1; i++){
    	excel.autoSizeColumn((short)i);
    }

		// Ajusta secciones y grupos con títulos.
		for(int i = 0; i < sections.size(); i++) {

			if (!StringUtils.isEmpty(sections.get(i).getTitle())) {
				excel.setColumnWidth(sections.get(i).getColumnIndex(), 1);
			}

			for(int k = 0; k < sections.get(i).getGroups().size(); k++) {

				if (!StringUtils.isEmpty(sections.get(i).getGroups().get(k).getTitle())) {
					excel.setColumnWidth(sections.get(i).getGroups().get(k).getColumnndex(), 1);
				}

			}
		}

	}

	/**
	 * Ajustar las propiedades de las hojas.
	 */
	protected void configPage() {

		HSSFSheet sheet = getExcel().getActiveSheet();

		HSSFPrintSetup ps = sheet.getPrintSetup();

    sheet.setDisplayGridlines(false);
    // Dejar fijo un conjuto filas o/y columnas
    //sheet.createFreezePane( 0, 3 ); // deja fijas las 3 primeras filas.
    //sheet.createFreezePane( 1, 0, 1, 0 ); // deja fija la primera columna.
    // Create a split with the lower left side being the active quadrant
    //sheet.createSplitPane( 2000, 2000, 0, 0, HSSFSheet.PANE_LOWER_LEFT );

    // margen bottom de la hoja 0.6 = 1.5 en Excel (para que no se solapen las filas con el footer)
    sheet.setMargin((short)3, 0.6);
    // marcar el radiobutton de ajustar a la página
    sheet.setAutobreaks(true);
    //excel.setColumnWidth(2, 30);
    // ajustar a la página 1 a 500 páginas.
    //excel.setFitToPage();
    //sheet.setVerticallyCenter(true);
    ps.setFitWidth((short)1);
    ps.setFitHeight((short)500);
    // horizontal
    ps.setLandscape(landscape);

    // Crear agrupaciones de filas o columnas.
    //sheet.groupRow((short)3,(short)5);
    //sheet.setRowGroupCollapsed((short)3, true);
	}

	/**
	 * Escribir el píe del informe.
	 */
	protected void writeFooter(){

		HSSFSheet sheet = getExcel().getActiveSheet();
    HSSFFooter footer = sheet.getFooter();

    if (showDate)
    	footer.setLeft("Date " + HSSFFooter.date()); 

    if (!StringUtils.isEmpty(textFooter))
    	footer.setCenter(textFooter);

	}

	/**
	 * Escribir el fichero Excel.
	 */
	public void writeFile() throws Exception {
		
		if (StringUtils.isEmpty(fileName))
			fileName = generateFileNameTmp();
		
		getExcel().setFileName(fileName);
		getExcel().writeFile();
	}

	/**
	 * Mostrar el archivo en pantalla.
	 */
	public void show() throws Exception {
		if (!StringUtils.isEmpty(fileName)){
			if (new File(fileName).exists()){
		    DesktopUtils.openFile(fileName);
			}
			else
				throw new Exception("File " + fileName + " not found"); 
		}
	}

	/**
	 * Obtiene la posición inicial de las columnas que representan los campos teniendo en cuenta las agrupaciones existentes.
	 * @return
	 */
	public int initColumnIndex(){
		return 0;
	}

	/**
	 * Configurar algunos elementos del informe.
	 */
	public void config(String title) {
		setFileName(EnvironmentVariables.getUserTmp() + "tmp.xls"); 
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

	public boolean isShowHelp() {
		return showHelp;
	}

	public void setShowHelp(boolean showHelp) {
		this.showHelp = showHelp;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public ArrayList<Section> getSections() {
		return sections;
	}

	public void setSections(ArrayList<Section> sections) {
		this.sections = sections;
	}

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public int getColumnIndex() {
		return columnIndex;
	}

	/**
	 * Obtener el valor de una propiedad del objeto.
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public Object getPropertyValue(String propertyName) throws Exception {
		
		if (object != null) {
			
			Object value = ReflectionUtils.getPropertyValue(object, propertyName);
			
			return value;
		}
		else
			return null;
	}

	public void setColumnIndex(int columnIndex) {
		this.columnIndex = columnIndex;
	}

	/**
	 * Buscar una propiedad en la lista de secciones.
	 * @param string
	 * @return
	 */
	public Property getProperty(String propertyName) {

		for(int i = 0; i < sections.size(); i++) {

			for(int k = 0; k < sections.get(i).getGroups().size(); k++) {

				for(int l = 0; l < sections.get(i).getGroups().get(k).getGroupsProperties().size(); l++) {

					GroupProperties gp = sections.get(i).getGroups().get(k).getGroupsProperties().get(l);

					for(int u = 0; u < gp.getProperties().size(); u++) {

						if (gp.getProperties().get(u).getName().equals(propertyName))
							return gp.getProperties().get(u);
					}
				}
			}
		}
		return null;
	}

}

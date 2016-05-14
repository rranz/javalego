package com.javalego.poi.report.word;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.javalego.config.EnvironmentVariables;
import com.javalego.exception.JavaLegoException;
import com.javalego.msoffice.word.Document;
import com.javalego.msoffice.word.MailMerge;
import com.javalego.msoffice.word.WordApplication;
import com.javalego.poi.report.ReportVector;
import com.javalego.poi.report.word.elements.BasicElementWordDocument;
import com.javalego.poi.report.word.elements.Bookmark;
import com.javalego.poi.report.word.elements.BookmarkImage;
import com.javalego.poi.report.word.elements.CheckBox;
import com.javalego.poi.report.word.elements.MergeField;
import com.javalego.util.FileUtils;
import com.javalego.util.ReflectionUtils;
import com.javalego.util.SystemUtils;

/**
 * Clase básica para la generación de informes.
 * 
 * @author ROBERTO
 */
public class WordDocument extends ArrayList<BasicElementWordDocument> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Objeto de donde se obtienen los valores de sus propiedades para contruir
	 * el informe.
	 */
	protected Object object;

	/**
	 * Nombre del archivo que contiene la plantilla de Word.
	 */
	protected String fileName;

	/**
	 * Nombre del fichero de salida utilizado de forma temporal a la hora de
	 * generar el documento.
	 */
	private String fileNameOut;

	public String getFileNameOut() {
		return fileNameOut;
	}

	/**
	 * Permite realizar la utilidad de MS-Word de "Combinar correspondencia"
	 * para generar un documento de word con una lista de registros. // t.,.2
	 */
	private boolean mailMerge;

	/**
	 * Nombre de la vista de Fuente de datos de donde obtiene la información
	 * para construir el informe.
	 */
	private String datasource;

	/**
	 * Preguntar por el directorio de destino del archivo generado.
	 */
	private boolean ask_path;

	/**
	 * Ubicación del archivo.
	 */
	private String path;

	/**
	 * Máscara utilizada para crear el nombre del fichero. Se pueden utilizar
	 * expresiones del tipo $F{campo} con la información contenida en el objeto
	 * asociado al documento.
	 */
	private String output_mask_fileName;

	/**
	 * Aplicación word
	 */
	private WordApplication wordApp;

	/**
	 * Objeto utilizado en la elaboración del document. Cuando se pasa una lista
	 * de objetos, este objeto va cambiando a medida que generamos los
	 * documentos cada uno de sus elementos.
	 */
	private Object objectDocument;

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
	}

	/**
	 * Generar un nombre de archivo a grabar en el directorio temporal del
	 * usuario y asignarlo al fileName del report.
	 * 
	 * @return
	 */
	public String generateFileNameTmp() {

		for (int i = 0; i < 100; i++) {
			File file = new File(EnvironmentVariables.getUserTmp() + "tmp" + i + ".doc");
			// Intentar eliminarlo
			if (file.exists())
				try {
					file.delete();
				}
				catch (Exception e) {
				}
			if (!file.exists()) {
				setFileName(file.getAbsolutePath());
				break;
			}
		}
		return fileName;
	}

	/**
	 * Ejecutar el informe.
	 * 
	 * @throws Exception
	 */
	// public void executeAndShow() throws Exception {
	//
	// execute(true);
	// show();
	// }

	/**
	 * Ejecutar el informe.
	 * 
	 * @throws Exception
	 */
	public void execute(boolean writeFile) throws Exception {

		if (object == null || (object instanceof List && ((List<?>) object).size() == 0))
			throw new JavaLegoException(" IS NULL", JavaLegoException.WARNING);

		// Informe de tipo merge basado en un array de valores.
		if (mailMerge) {
			loadFile(object);
			executeMailMerge();
		}
		// Si es un lista de objetos hay que crear tantos documentos como
		// elementos de la lista.
		else if (object instanceof List) {
			try {
				for (Object _object : (List<?>) object) {
					run(_object, false);
				}
			}
			finally {
				if (wordApp != null)
					wordApp.quit();
			}
		}
		// Si sólo es un objeto
		else
			run(getDocumentObject(), true);
	}

	/**
	 * Ejecutar el informe.
	 * 
	 * @param object
	 * @throws Exception
	 */
	private void run(Object object, boolean quit) throws Exception {

		loadFile(object);

		execute(object, quit);
	}

	/**
	 * Establecer el nombre del fichero de salida y obtener el fichero
	 * plantilla.
	 * 
	 * @throws Exception
	 */
	private void loadFile(Object object) throws Exception {

		objectDocument = object;

		// Nombre del fichero de salida.
		if (output_mask_fileName != null) {

			if (output_mask_fileName.indexOf("$") > -1) {
				// WordDocumentExpression oe = new WordDocumentExpression(this);
				// oe.setExpression(output_mask_fileName);
				// fileNameOut = (String)oe.getValue();
			}
			else
				fileNameOut = output_mask_fileName;

			if (path != null)
				fileNameOut = path + (path.length() == 3 && path.indexOf(":") == 1 ? "" : "/") + fileNameOut + ".doc";
			else
				fileNameOut = FileUtils.getFreeSecuencialFile(EnvironmentVariables.getUserTmp() + fileNameOut, ".doc");
		}
		else {
			if (path != null)
				fileNameOut = FileUtils.getFreeSecuencialFile(path + (path.length() == 3 && path.indexOf(":") == 1 ? "" : "/") + "tmp", ".doc", false); // t.,.2
			else
				fileNameOut = FileUtils.getFreeSecuencialFile(EnvironmentVariables.getUserTmp() + "tmp", ".doc");
		}

		SystemUtils.saveResourceToFile(fileName, fileNameOut);
	}

	/**
	 * Obtener el objeto en el que se base el documento de word para establecer
	 * los valores de los diferentes bookmarks.
	 * 
	 * @return
	 */
	public Object getDocumentObject() {
		return this.object != null && this.object instanceof List && ((List<?>) this.object).size() > 0 ? ((List<?>) this.object).get(0) : this.object;
	}

	/**
	 * Generar información en el documento de Word.
	 * 
	 * @throws Exception
	 */
	private void execute(Object object, boolean quit) throws Exception {

		objectDocument = object;

		if (object == null || (object instanceof List && ((List<?>) object).size() == 0))
			throw new JavaLegoException("No existe información.", JavaLegoException.WARNING);

		// Incluir información en el documento mediante los items o acciones
		// definidas.
		if (wordApp == null) {
			wordApp = new WordApplication();
			wordApp.open(false);
		}

		try {
			Document document = wordApp.getDocuments().openDocument(fileNameOut);

			// Marcadores.
			for (BasicElementWordDocument item : this) {

				// CheckBox
				if (item instanceof CheckBox) {

					CheckBox checkBox = (CheckBox) item;

					Boolean value = null;

					if (object instanceof Object[]) {

						Object bv = ((Object[]) object)[checkBox.getIndex()];

						if (bv instanceof Boolean)
							value = (Boolean) bv;
						else
							throw new JavaLegoException("El valor de la marca '" + checkBox.getName() + "' no es de tipo lógico. Revisar descriptor.", JavaLegoException.ERROR); // t.,.2
					}
					// Evaluar la expresión
					else if (checkBox.getExpression() != null) {

						// Object tmpValue = new ObjectExpression(object,
						// checkBox.getExpression(), checkBox.isNatural(),
						// checkBox.getDisplayFormat()).getValue();
						// if (tmpValue != null) {
						// if (tmpValue instanceof Number)
						// value = ((Number)tmpValue).intValue() == 0 ? false :
						// true;
						// else
						// value =
						// "true".equals(tmpValue.toString().toLowerCase()) ?
						// true : false;
						//
						// document.getCheckBox(item.getName()).setChecked((Boolean)value);
						// }
					}
					else
						value = (Boolean) ReflectionUtils.getPropertyValue(object, ((CheckBox) item).getPropertyName());

					com.javalego.msoffice.word.CheckBox check = document.getCheckBox(checkBox.getName());

					if (check == null)
						throw new JavaLegoException("El componente de formulario del documento de Word denominado '" + checkBox.getName() + "'");

					if (value != null)
						check.setChecked(value);

				}
				// Bookmark
				else if (item instanceof Bookmark) {
					Bookmark bookmark = (Bookmark) item;
					try {
						// Añadir una imagen al documento
						if (bookmark instanceof BookmarkImage)
							;// ((BookmarkImage)bookmark).addImage(getBookmarkImageValue((BookmarkImage)bookmark),
								// bookmark.getName(), wordApp, document);
						else {
							document.getBookmark(bookmark.getName()).setText(getBookmarkValue(bookmark));
							// System.out.println(bookmark + "  =  " +
							// getBookmarkValue(bookmark));
						}
					}
					catch (Exception e) {
						throw new JavaLegoException("El valor definido '" + bookmark.getPropertyName() + "' no existe. Revisad descriptor del informe.", JavaLegoException.ERROR, e);
					}
				}

			}
			document.save();
			document.close();
		}
		finally {
			if (quit)
				wordApp.quit();
		}
	}

	/**
	 * Obtener el valor de un bookmark.
	 * 
	 * @param name
	 * @return
	 */
	public String getBookmarkValue(String name) throws Exception {

		for (BasicElementWordDocument item : this) {

			if (item instanceof Bookmark && item.getName().equals(name)) {
				return getBookmarkValue((Bookmark) item);
			}
		}
		return "";
	}

	/**
	 * Obtener el valor de un bookmark.
	 * 
	 * @param name
	 * @return
	 */
	public String getBookmarkValue(Bookmark bookmark) throws Exception {

		Object value = null;

		if (bookmark.getPropertyName() != null)
			value = bookmark.getStringValue(ReflectionUtils.getPropertyValue(objectDocument, bookmark.getPropertyName()));

		// Expresión de cálculo basada en propiedades del objeto
		// else if (bookmark.getExpression() != null)
		// value = bookmark.getStringValue(new ObjectExpression(objectDocument,
		// bookmark.getExpression(), bookmark.isNatural(),
		// bookmark.getDisplayFormat()).getValue());

		// buscar el valor del índice del array de valores.
		else if (bookmark.getIndex() > -1 && objectDocument instanceof Object[])
			value = bookmark.getStringValue(((Object[]) objectDocument)[bookmark.getIndex()]);

		// Lista de objetos y cada objeto es de tipo Object[]
		else if (bookmark.getIndex() > -1 && objectDocument instanceof List) {
			if (((List<?>) objectDocument).get(0) instanceof Object[])
				value = ((Object[]) ((List<?>) objectDocument).get(0))[bookmark.getIndex()];
		}

		return value == null ? "" : value.toString();
	}

	/**
	 * Obtener el valor de un bookmark.
	 * 
	 * @param name
	 * @return
	 */
	public Object getBookmarkImageValue(BookmarkImage bookmark) throws Exception {

		Object value = null;

		if (bookmark.getPropertyName() != null)
			value = ReflectionUtils.getPropertyValue(objectDocument, bookmark.getPropertyName());

		// Expresión de cálculo basada en propiedades del objeto
		// else if (bookmark.getExpression() != null)
		// value = new ObjectExpression(objectDocument,
		// bookmark.getExpression(), bookmark.isNatural(),
		// bookmark.getDisplayFormat()).getValue();

		// buscar el valor del índice del array de valores.
		else if (bookmark.getIndex() > -1 && objectDocument instanceof Object[])
			value = ((Object[]) objectDocument)[bookmark.getIndex()];

		// Lista de objetos y cada objeto es de tipo Object[]
		else if (bookmark.getIndex() > -1 && objectDocument instanceof List) {
			if (((List<?>) objectDocument).get(0) instanceof Object[])
				value = ((Object[]) ((List<?>) objectDocument).get(0))[bookmark.getIndex()];
		}
		// Recurso de imagen
		else if (bookmark.getResourceName() != null) {
			return SystemUtils.getBytesFromResource(bookmark.getResourceName());
		}

		return value;
	}

	/**
	 * Generar un documento con la funcionalidad de
	 * "Combinación de correspondencia" de MS-Word en base a una lista de
	 * registros.
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private void executeMailMerge() throws Exception {

		// Crear un informe para generar una hoja de excel con la información
		// requerida por el documento MS-Word donde se han definido los campos
		// utilizados en la combinación de correspondencia.
		String path = EnvironmentVariables.getUserTmp();
		String fileName = FileUtils.getFreeSecuencialFile(path + "tmp", ".xls");

		ReportVector report = new ReportVector();
		// Ponemos useTemplate = false para que funcione correctamente la
		// combinación de correspondencia sobre
		// una hoja de excel nueva y no usando el plantilla que tenemos estándar
		// para aumentar la resolución a 600ppp.
		report.setFileName(fileName);
		report.setShowRecordCount(false);
		report.setShowDate(false);
		report.setShowNumberPages(false);

		// Convertir object en un array de Object[]
		List<Object[]> list = (List<Object[]>) object;

		if (list != null && list.size() > 0) {

			Vector<Object[]> vector = new Vector<Object[]>();
			for (Object[] object : list)
				vector.add(object);

			report.setVector(vector);

			// Campos
			for (BasicElementWordDocument item : this)
				if (item instanceof MergeField)
					report.addField(((MergeField) item).getName());

			report.addField("empty");
			report.setShowDetail(true);
			report.execute(true);
		}
		else
			throw new JavaLegoException("No existen alumnos para el grupo actualmente seleccionado.", JavaLegoException.WARNING);

		// Grabar recurso y mostrar documento
		SystemUtils.saveResourceToFile(this.fileName, fileNameOut);

		WordApplication w = new WordApplication();
		try {
			w.open(true);
			// Document document = w.getDocuments().openDocument(fileNameOut);

			// Buscar bookmarks de imágenes
			for (BasicElementWordDocument item : this) {

				if (item instanceof BookmarkImage) {
					// BookmarkImage bookmark = (BookmarkImage)item;
					;// ((BookmarkImage)bookmark).addImage(getBookmarkImageValue(bookmark),
						// bookmark.getName(), w, document);
				}
			}

			MailMerge mail = new MailMerge(w);
			mail.merge(fileName, this.fileNameOut);

			// document.save();
			// document.close();
		}
		finally {
			w.quit();
		}

	}

	/**
	 * Configurar algunos elementos del informe.
	 */
	public void config(String title) {

		setFileName(EnvironmentVariables.getUserTmp() + "tmp.xls");
	}

	/**
	 * Buscar un elemento del informe.
	 * 
	 * @param name
	 * @return
	 */
	public BasicElementWordDocument getItem(String name) {

		for (BasicElementWordDocument item : this)
			if (item.getName() != null && item.getName().equals(name))
				return item;
		return null;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public boolean isMailMerge() {
		return mailMerge;
	}

	public void setMailMerge(boolean mailMerge) {
		this.mailMerge = mailMerge;
	}

	public String getDatasource() {
		return datasource;
	}

	public void setDatasource(String datasource) {
		this.datasource = datasource;
	}

	public boolean isAsk_path() {
		return ask_path;
	}

	public void setAsk_path(boolean ask_path) {
		this.ask_path = ask_path;
	}

	public String getOutput_mask_fileName() {
		return output_mask_fileName;
	}

	public void setOutput_mask_fileName(String output_mask_fileName) {
		this.output_mask_fileName = output_mask_fileName;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Object getObjectDocument() {
		return objectDocument;
	}

}

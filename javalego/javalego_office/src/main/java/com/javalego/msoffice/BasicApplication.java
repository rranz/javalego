package com.javalego.msoffice;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.LibraryLoader;
import com.jacob.com.Variant;
import com.javalego.exception.JavaLegoException;
import com.javalego.msoffice.word.WordApplication;

/**
 * LIBRERÍA UTILIZADA PARA INSTANCIAR OBJETO COM MS OFFICE ES JACOB
 * SourceForge: http://sourceforge.net/projects/jacob-project/files/
 * Autor: http://www.danadler.com/jacob/
 * 
 * Aplicación básica de MS-Office que implementa las funciones de
 * cargar y cerrar los diferentes aplicaciones de que dispone MS-Office.
 * 
 * NOTA: existe un documento en help/notes/MSOfficeOLE.txt desde se describe el procedimiento
 * para visualizar las propiedades enumeradas de Word.
 * @author ROBERTO RANZ
 */
public abstract class BasicApplication {
	
	private boolean displayAlerts;
	
	private static boolean loaded;
	
	/**
	 * Applicación.
	 */
	protected ActiveXComponent pointer = null;

	/**
	 * Nombre de la aplicación.
	 * @return
	 */
	protected abstract String getAppName();
	
	/**
	 * Abrir documento existente.
	 * @param fileName
	 * @return 
	 * @throws Exception
	 */
	public void open(String filename) throws Exception {
		Dispatch documents = pointer.getProperty("Documents").toDispatch();
		Dispatch.call(documents, "Open", filename).toDispatch();		
	}
	
	/**
	 * Abrir aplicación.
	 * @param visible
	 * @throws Exception
	 */
	public void open(boolean visible) throws Exception {
		// Cargar librería si no existe en ficheros temporales JWS (loadLibrary()).
		try {
			loadLibrary();
			pointer = new ActiveXComponent(getAppName());
		}
		// Reintento al cargar librería.
		catch(Exception e) {
			loadLibrary();
			pointer = new ActiveXComponent(getAppName());
		}
		
		if (visible) {
			pointer.setProperty("Visible", new Variant(visible));
		}
		else {
			setDisplayAlerts(false);
		}
	}
	
	public Dispatch getPointer() {
		return pointer;
	}
	
	/**
	 * Terminar ejecución y mostrar documento.
	 * @throws Exception
	 */
	public void close() throws Exception {
		pointer.invoke("Close");
	}
	
	/**
	 * Cerrar aplicación.
	 * @throws Exception
	 */
	public void quit() throws Exception {
		try {
			if (pointer != null)
				pointer.invoke("Quit", 0); 
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new JavaLegoException("No se puede generar el informe " + (this instanceof WordApplication ? "Word" : "Excel") + " porque seguramente no tenga instalado MS Office o un MS Office reducido que no incluye la automaticación OLE necesaria para construir el documento.\n\nConsulte esta incidencia con el departamento técnico.", JavaLegoException.ERROR);
		}
	}
	
	/**
	 * Hacer visible la aplicación
	 * @param visible
	 * @throws Exception
	 */
	public void setVisible(boolean visible) throws Exception {
		pointer.setProperty("Visible", new Variant(visible));
	}
	
	/**
	 * Ocultar la aplicación.
	 * @return
	 * @throws Exception
	 */
	public boolean isVisible() throws Exception {
		return pointer.getProperty("Visible").getBoolean();
	}

	public boolean isDisplayAlerts() {
		return displayAlerts;
	}

	public void setDisplayAlerts(boolean displayAlerts) throws Exception {
		this.displayAlerts = displayAlerts;
		
		if (pointer != null && !displayAlerts) {
			pointer.setProperty("DisplayAlerts", new Variant(0));
			//pointer.put("DisplayAlerts", 0); // wdAlertsNone
		}
		else if (pointer != null && displayAlerts) {
			pointer.setProperty("DisplayAlerts", new Variant(-1));
			//pointer.put("DisplayAlerts", -1); // wdAlertsAll
		}
		// wdAlertsDialogMessages o algo así para deshabilitar mensajes de diálogo.
			
	}
	
	/**
	 * Load the DLL from the classpath rather than from the java path. This code
	 * uses this class's class loader to find the dell in one of the jar files
	 * in this class's class path. It then writes the file as a temp file and
	 * calls Load() on the temp file. The temporary file is marked to be deleted
	 * on exit so the dll is deleted from the system when the application exits.
	 * <p>
	 * Derived from ample code found in Sun's java forums <p.
	 * 
	 * @return true if the native library has loaded, false if there was a
	 *         problem.
	 */
	public boolean loadLibrary() {

		// Cargar la librerías dll,s desde los recursos cuando estamos ejecutando la aplicación desde Java Web Start
		// ya que de lo contrario no se podrían localizar y se generaría un error en tiempo de ejecución.
		// Estas librerías se copiarían localmente en los directorio temporales donde residen las aplicaciones Web de JWS.
		if (loaded) 
			return true;
		
		try {
			
			// Intentar la carga de la libería Dll. En caso de excepción se intentará obtener el recurso Dll y copiar este archivo en un path temporal para realizar la carga de la librería.
			try {
				LibraryLoader.loadJacobLibrary();
				loaded = true;
				return true;
			}
			catch(Exception e) {
			}			
			// this assumes that the dll is in the root dir of the signed
			// jws jar file for this application.
			//
			// Starting in 1.14M6, the dll is named by platform and architecture
			// so the best thing to do is to ask the LibraryLoader what name we
			// expect.
			// this code might be different if you customize the name of
			// the jacob dll to match some custom naming convention
			InputStream inputStream = getClass().getResource(
					"/" + LibraryLoader.getPreferredDLLName() + ".dll")
					.openStream();
			// Put the DLL somewhere we can find it with a name Jacob expects
			File temporaryDll = File.createTempFile(LibraryLoader.getPreferredDLLName(), ".dll");
			FileOutputStream outputStream = new FileOutputStream(temporaryDll);
			byte[] array = new byte[8192];
			for (int i = inputStream.read(array); i != -1; i = inputStream.read(array)) {
				outputStream.write(array, 0, i);
			}
			outputStream.close();
			temporaryDll.deleteOnExit();
			// Ask LibraryLoader to load the dll for us based on the path we
			// set
			System.setProperty(LibraryLoader.JACOB_DLL_PATH, temporaryDll.getPath());
			LibraryLoader.loadJacobLibrary();
			loaded = true;
			return true;
		} catch (Throwable e) {
			e.printStackTrace();
			return false;
		}
	}
}

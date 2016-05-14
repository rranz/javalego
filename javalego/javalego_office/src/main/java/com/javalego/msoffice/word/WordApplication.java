package com.javalego.msoffice.word;

import com.jacob.com.Dispatch;
import com.javalego.msoffice.BasicApplication;

/**
 * MS-Word.
 * Implementa funciones de automatización ole relativas a la aplicación MS-Word.
 * @author ROBERTO RANZ
 */
public class WordApplication extends BasicApplication {

	private Documents documents;
	
	@Override
	protected String getAppName() {
		return "Word.Application";
	}

	/**
	 * Documentos.
	 * @return
	 */
	public Documents getDocuments() {
		
		if (documents == null)
			documents = new Documents(this);
		return documents;
	}
	
	/**
	 * Añadir una imagen en la selección actual.
	 * @param fileName
	 */
	public void addPicture(String fileName) throws Exception {
		
		Dispatch selection = Dispatch.get(pointer, "Selection").toDispatch();
		Dispatch inline = Dispatch.get(selection, "InlineShapes").toDispatch();
		Dispatch.call(inline, "AddPicture", fileName, false, true);

		//System.out.println(inline.get("Count"));
		
		Dispatch cell = Dispatch.call(inline, "Item", new Integer(1)).toDispatch();
		
		//Dispatch.get(cell,"Height");
		Dispatch.put(cell,"Height", 83.9);
		Dispatch.put(cell, "Width", 495.5);
	}
	
	/**
	 * Puntero al documento activo.
	 * @return
	 */
	public Dispatch getActiveDocument() throws Exception {
		return pointer.getProperty("ActiveDocument").toDispatch();
	}
	
	/**
	 * Puntero a las ventanas de la aplicación.
	 * @return
	 */
	public Dispatch getWindows() throws Exception {
		return pointer.getProperty("Windows").toDispatch();
		
		//return (DispatchPtr)getPointer().get("Windows");
	}
	
	/**
	 * Establecer la vista de impresión en el documento actual.
	 * @throws Exception
	 */
	public void setPrintView() throws Exception {

		Dispatch activeWindow = pointer.getProperty("ActiveWindow").toDispatch();
		
		//DispatchPtr activeWindow = (DispatchPtr) pointer.get("ActiveWindow");
		Dispatch view = Dispatch.get(activeWindow, "View").toDispatch(); 
		//Dispatch view = (Dispatch)activeWindow.get("View");
		//view.put("Type", 1); //wdNormalView
		Dispatch.call(view, "Type", 3); //wdPrintView
	}
	
	/**
	 * Establecer la vista de impresión en el documento actual.
	 * @throws Exception
	 */
	public void seekMainDocument() throws Exception {

		//ActiveWindow.ActivePane.View.SeekView = wdSeekMainDocument

		Dispatch activeWindow = pointer.getProperty("ActiveWindow").toDispatch();
		Dispatch activePane = Dispatch.get(activeWindow, "ActivePane").toDispatch();
		//DispatchPtr activePane = (DispatchPtr) activeWindow.get("ActivePane");
		//Dispatch aview = (DispatchPtr) activePane.get("View");
		Dispatch aview = Dispatch.get(activePane, "View").toDispatch();
		Dispatch.call(aview, "SeekView", 0); //wdSeekMainDocument
		
	}

	/**
	 * Grabar un archivo de word a formato txt.
	 * @param fileNameWord
	 * @param fileNameTxt
	 */
	public static void saveWordToTxt(String fileNameWord, String fileNameTxt) throws Exception {
		
		WordApplication w = new WordApplication();
		try {
			w.open(false);
			Document document = w.getDocuments().openDocument(fileNameWord);
			document.saveAs(fileNameTxt);
			document.close();
			w.quit();
		}
		catch(Exception e) {
			try {
				w.quit();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			throw e;
		}
	}

	/**
	 * Grabar un archivo de word a formato txt.
	 * @param fileNameWord
	 * @param fileNameTxt
	 */
	public static void saveWordToHtml(String fileNameWord, String fileNameHtml) throws Exception {
		
		WordApplication w = new WordApplication();
		try {
			w.open(false);
			Document document = w.getDocuments().openDocument(fileNameWord);
			document.saveAsHtml(fileNameHtml);
			document.close();
			w.quit();
		}
		catch(Exception e) {
			try {
				w.quit();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			throw e;
		}
	}
	
	
	/**
	 * Poner al frente la aplicación de Word sobre el resto del desktop de Windows.
	 */
//	public void toFront() throws Exception {
//		getPointer().invoke("GoForward");
//	}
	
}

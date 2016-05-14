package com.javalego.msoffice.excel;

import com.jacob.com.Dispatch;
import com.javalego.msoffice.BasicApplication;

/**
 * MS-Excel.
 * Implementa funciones de automatización ole relativas a la aplicación MS-Excel.
 * @author ROBERTO RANZ
 */
public class ExcelApplication extends BasicApplication {

	@Override
	protected String getAppName() {
		return "Excel.Application";
	}

	/**
	 * Añadir una imagen en la selección actual.
	 * @param fileName
	 */
	public void addPicture(String fileName) throws Exception {
		
//		Dispatch selection = (Dispatch) pointer.get("Selection");
//		Dispatch inline = (Dispatch) selection.get("InlineShapes");
//		inline.invoke("AddPicture", fileName, false, true);

		//System.out.println(inline.get("Count"));
		
//		Dispatch cell = (Dispatch)inline.invoke("Item", new Integer(1));
//		
//		cell.get("Height");
//		cell.put("Height", 83.9);
//		cell.put("Width", 495.5);
	}
	
	/**
	 * Puntero al documento activo.
	 * @return
	 */
	public Dispatch getActiveDocument() throws Exception {
		
		return null; // (Dispatch)getPointer().get("ActiveDocument");
	}
	
	/**
	 * Puntero a las ventanas de la aplicación.
	 * @return
	 */
	public Dispatch getWindows() throws Exception {
		
		return null; // (Dispatch)getPointer().get("Windows");
	}
	
	/**
	 * Establecer la vista de impresión en el documento actual.
	 * @throws Exception
	 */
	public void setPrintView() throws Exception {

		//Dispatch activeWindow = (Dispatch) pointer.get("ActiveWindow");
		//Dispatch view = (Dispatch) activeWindow.get("View");
		//view.put("Type", 1); //wdNormalView
		//view.put("Type", 3); //wdPrintView
	}
	
	/**
	 * Establecer la vista de impresión en el documento actual.
	 * @throws Exception
	 */
	public void seekMainDocument() throws Exception {

		//ActiveWindow.ActivePane.View.SeekView = wdSeekMainDocument

		//Dispatch activeWindow = (Dispatch) pointer.get("ActiveWindow");
		//Dispatch activePane = (Dispatch) activeWindow.get("ActivePane");
		//Dispatch aview = (Dispatch) activePane.get("View");
		//aview.put("SeekView", 0); //wdSeekMainDocument
		
	}

}

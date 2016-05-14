package com.javalego.msoffice.word;

import com.jacob.com.Dispatch;
import com.javalego.msoffice.BasicElementApp;

/**
 * Combinar correspondencia desde una fuente de datos y un documento. 
 * @author ROBERTO RANZ
 */
public class MailMerge extends BasicElementApp {

	/**
	 * Aplicación MS-Word asociada.
	 */
	private WordApplication app;
	
	/**
	 * Constructor donde le indicamos la aplicación asociada a los documentos.
	 * @param app
	 */
	public MailMerge(WordApplication app) {
		this.app = app;
	}
	
	@Override
	protected String getInternalName() {
		return "MailMerge";
	}

	@Override
	protected void setPointer() throws Exception {
		pointer = Dispatch.get(app.getActiveDocument(), getInternalName()).toDispatch();
	}

	/**
	 * Combinar correspondencia con un archivo de fuente de datos.
	 * @param fileNameData
	 */
	public void merge(String fileNameData, String fileNameResult) throws Exception {
		
		Dispatch pointer = getPointer();
		
		Dispatch.put(pointer,"MainDocumentType", 0); // wdFormLetters

		Dispatch.call(pointer, "OpenDataSource", fileNameData, false, false, true);

		Dispatch.put(pointer, "Destination", 0); // wdSendToNewDocument
		Dispatch.put(pointer, "SuppressBlankLines", true); 
		Dispatch data = Dispatch.get(pointer, "DataSource").toDispatch();
		Dispatch.put(data, "FirstRecord", 1); // wdDefaultFirstRecord
		Dispatch.put(data, "LastRecord", -16); // wdDefaultLastRecord
		Dispatch.call(pointer, "Execute", false);
		
		// Cerrar el documento origen
		String name = null;
		try {
			// Obtener el nombre de la ventana principal para cerrarla y grabar la nueva con este mismo nombre.
			if (fileNameResult.indexOf("/") > -1)
				name = fileNameResult.substring(fileNameResult.lastIndexOf("/")+1);
			else
				name = fileNameResult.substring(fileNameResult.lastIndexOf("\\")+1);
			
			Dispatch window = Dispatch.call(app.getWindows(), "Item", name).getDispatch();
			Dispatch.call(window, "Close");
		}
		catch(Exception e) {
		}
		
		Dispatch activeDocument = app.getActiveDocument();
		Dispatch.call(activeDocument, "SaveAs", fileNameResult);

		if (name != null) {
			Dispatch window = Dispatch.call(app.getWindows(), "Item", name).toDispatch();
			Dispatch.call(window, "Close");
		}

	}
	
}

package com.javalego.poi.report.word.elements;

import com.javalego.config.EnvironmentVariables;
import com.javalego.msoffice.word.Document;
import com.javalego.msoffice.word.WordApplication;
import com.javalego.poi.report.word.WordDocument;
import com.javalego.util.FileUtils;

/**
 * Nos permite incluir una imagen en la posición de un bookmark de un documento de MS-Word
 * @author ROBERTO RANZ
 */
public class BookmarkImage extends Bookmark {

	/**
	 * Nombre del recurso desde donde obtenemos la imagen que deseamos incluir en la posición del bookmark del documento Word.
	 */
	private String resourceName;

	/**
	 * Datos de la imagen. Esta propiedad permite establecer el valor de la imagen de forma directa, evitando la referencia a la propiedad de un objeto (generación de
	 * documentos basados en bookmarks) y aplicado principalmente para documentos que utilizan combinación de correspondencia, donde estos bookmarks son adicionales a la creación
	 * del informe. Ej.: necesitamos añadir un logo previamente a la creación de la combinación.
	 */
	private byte[] data;
	
	public BookmarkImage(WordDocument wordDocument) {
		super(wordDocument);
	}

	/**
	 * Añadir la imagen al documento
	 * @param data
	 * @param w
	 * @param document
	 * @throws Exception
	 */
	public void addImage(Object data, String bookmarkName, WordApplication w, Document document) throws Exception {
		addImage(data, bookmarkName, w, document, true);
	}
	
	/**
	 * Añadir la imagen al documento
	 * @param data
	 * @param w
	 * @param document
	 * @param printView
	 * @throws Exception
	 */
	public void addImage(Object data, String bookmarkName, WordApplication w, Document document, boolean printView) throws Exception {
		
		if (data != null && data instanceof byte[]) {
			
			byte[] logo = (byte[])data; 
	
			if (logo != null) {
	
				com.javalego.msoffice.word.Bookmark bookmark = null;
				try {
					bookmark  = document.getBookmark(bookmarkName);
				}
				catch(Exception e) {
				}
				
				if (bookmark != null) {
	
					bookmark.select();
					
					String fileName = FileUtils.getFreeSecuencialFile(EnvironmentVariables.getUserTmp() + "tmp", ".png");
					FileUtils.saveInputStreamToFile(logo, fileName);
					w.addPicture(fileName);
					
					if (printView) {
						w.setPrintView();
						try {
							w.seekMainDocument();
						}
						catch(Exception e) {
						}
						w.setPrintView();
					}
				}
			}
		}
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
}

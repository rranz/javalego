package com.javalego.pdf;

import java.awt.Color;
import java.io.FileOutputStream;

import com.lowagie.text.DocWriter;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfAction;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfOutline;
import com.lowagie.text.pdf.PdfWriter;


/**
 * Clase utiliza para la creación de documentos Pdf.
 * @author Administrador
 *
 */
public class PdfWapperWriter extends BasicWapperWriter {

	protected PdfWriter pdfWriter;

	public PdfWapperWriter() throws Exception {
		super();
	}

	public PdfWapperWriter(String fileName) throws Exception {
		super(fileName);
	}

	/**
	 * Inicializar documento.
	 * @throws Exception
	 */
	@Override
	protected void initialize() throws Exception {
		// Obtener una instancia del fichero donde se escribirá la información.
		pdfWriter = PdfWriter.getInstance(document,	new FileOutputStream(fileName));
	}
	
	@Override
	public void setUseOutlines(){
		// abrir la ventana de marcadores para visualizar los capítulos y secciones.
		pdfWriter.setViewerPreferences(PdfWriter.PageModeUseOutlines); // | PdfWriter.PageModeUseThumbs);
	}
	
	/**
	 * Añadir el outline root del documento.
	 */
	@Override
	public void addRootOutline(String text, Color color, boolean bold){
		
		PdfContentByte cb = pdfWriter.getDirectContent();
    PdfOutline root = cb.getRootOutline();
    PdfOutline links = new PdfOutline(root, new PdfAction(PdfAction.FIRSTPAGE), text);
    links.setColor(color);
    if (bold)
    	links.setStyle(Font.BOLD);
	}

	public PdfWriter getPdfWriter() {
		return pdfWriter;
	}

	@Override
	public String getExtension() {
		return ".pdf";
	}

	@Override
	protected DocWriter getDocWriter() {
		return pdfWriter;
	}
	
}

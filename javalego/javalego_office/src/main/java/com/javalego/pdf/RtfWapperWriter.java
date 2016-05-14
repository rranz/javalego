package com.javalego.pdf;

import java.awt.Color;
import java.io.FileOutputStream;

import com.lowagie.text.DocWriter;
import com.lowagie.text.pdf.PdfWriter;

/**
 * Construir archivos de rt visibles en MS Word u OpenOffice.
 * @author ROBERTO RANZ
 */
public class RtfWapperWriter extends BasicWapperWriter {

	PdfWriter rtfWriter;
	
	public RtfWapperWriter() throws Exception {
		super();
	}
	
	public RtfWapperWriter(String fileName) throws Exception {
		super(fileName);
	}

	@Override
	public void addRootOutline(String text, Color color, boolean bold) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initialize() throws Exception {
		// Obtener una instancia del fichero donde se escribirá la información.
		rtfWriter = PdfWriter.getInstance(document,	new FileOutputStream(fileName));
	}

	@Override
	public void setUseOutlines() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getExtension() {
		return ".doc";
	}

	@Override
	protected DocWriter getDocWriter() {
		return rtfWriter;
	}
	
}

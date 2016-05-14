package com.javalego.pdf;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.lowagie.text.Anchor;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PRAcroForm;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.SimpleBookmark;

/**
 * Ejemplos prácticos
 * 
 * Referencia página web http://www.roseindia.net/java/itext/index.shtml
 * 
 * @author ROBERTO RANZ
 * 
 */
public class iTextExamples {

	public static void main(String args[]) {
		try {
			//iTextExamples.concatRtfFiles();
			iTextExamples.readerRtf();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Combiar dos páginas.
	 * @throws Exception
	 */
	static public void combinesTwoPage() throws Exception {
		
		System.out.println("Combines two page");        
    PdfReader reader = new PdfReader("HelloWorldPdf.pdf");
    int n = reader.getNumberOfPages();
    Rectangle psize = reader.getPageSize(1);
    float width = psize.getHeight();
    float height = psize.getWidth();        
    Document document = new Document(new Rectangle(width, height));
    PdfWriter Pdfwriter = PdfWriter.getInstance(document, new FileOutputStream("combine2Page1.pdf"));
    document.open();
    PdfContentByte cb = Pdfwriter.getDirectContent();
    int i = 0;
    int p = 0;
    while (i < n) {
        document.newPage();
        p++;
        i++;
        PdfImportedPage page1 = Pdfwriter.getImportedPage(reader, i);
        cb.addTemplate(page1, .5f, 0, 0, .5f, 60, 120);
        if (i < n) {
            i++;
            PdfImportedPage page2 = Pdfwriter.getImportedPage(reader, i);
            cb.addTemplate(page2, .5f, 0, 0, .5f, width / 2 + 60, 120);
        }
        BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252,BaseFont.NOT_EMBEDDED);
        cb.beginText();
        cb.setFontAndSize(bf, 19);
        cb.showTextAligned(PdfContentByte.ALIGN_CENTER, "page " + p + " of " + ((n / 2) + (n % 2 > 0? 1 : 0)), width / 2, 40, 0);
        cb.endText();
    }
    document.close();   
	}


	/**
	 * Leear un documento de word.
	 * @throws Exception
	 */
	static public void readerRtf() throws Exception {
		Document document = new Document();
		//int OUTPUT_BYTE_ARRAY_INITIAL_SIZE = 4096;
		//ByteArrayOutputStream out = new ByteArrayOutputStream(OUTPUT_BYTE_ARRAY_INITIAL_SIZE);
		
//		RtfWriter2 rtfWriter2 = RtfWriter2.getInstance(document,new FileOutputStream("e:/Sample.rtf"));
//		//RtfWriter2 rtfWriter2 = RtfWriter2.getInstance(document,out);
//		document.open();
//		InputStream intro = rtfWriter2.getClass().getClassLoader().getResourceAsStream("test/sample.rtf");
//		Reader reader = new InputStreamReader(intro);
//		rtfWriter2.importRtfDocument(reader);
		
		// add the dynamic data to the document

		document.close();
	}
	
	
	/**
	 * Concatenar varios archivos en uno.
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	static public void concatFiles() throws Exception {
		
		String[] args = new String[] { "e:/sample.pdf", "e:/DesktopClient User Guide.pdf", "e:/sampleall.pdf" };
		
		if (args.length < 2) {
		
			System.err.println("arguments: file1 [file2 ...] destfile");
		
		} else {
			
			System.out.println("PdfCopy example");
			
			int pageOffset = 0;
			
			ArrayList master = new ArrayList();
			
			int f = 0;
			
			String outFile = args[args.length - 1];
			
			Document document = null;
			
			PdfCopy writer = null;
			
			while (f < args.length - 1) {
			
				PdfReader reader = new PdfReader(args[f]);
				
				reader.consolidateNamedDestinations();
				
				int n = reader.getNumberOfPages();
				
				List bookmarks = SimpleBookmark.getBookmark(reader);
				
				if (bookmarks != null) {
					if (pageOffset != 0)
						SimpleBookmark.shiftPageNumbers(bookmarks, pageOffset, null);
					master.addAll(bookmarks);
				}
				pageOffset += n;
				if (f == 0) {
					document = new Document(reader.getPageSizeWithRotation(1));
					writer = new PdfCopy(document, new FileOutputStream(outFile));
					document.open();
				}
				PdfImportedPage page;
				for (int i = 0; i < n;) {
					++i;
					page = writer.getImportedPage(reader, i);
					writer.addPage(page);
				}
				PRAcroForm form = reader.getAcroForm();
				if (form != null)
					writer.copyAcroForm(reader);
				f++;
			}
			if (!master.isEmpty())
				writer.setOutlines(master);
			document.close();
		}
	}

	/**
	 * Concatenar varios archivos en uno.
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	static public void concatRtfFiles() throws Exception {
		
		String[] args = new String[] { "e:/sample.doc", "e:/sample2.doc", "e:/sampleall.doc" };
		
		if (args.length < 2) {
		
			System.err.println("arguments: file1 [file2 ...] destfile");
		
		} else {
			
			System.out.println("PdfCopy example");
			
			int pageOffset = 0;
			
			ArrayList master = new ArrayList();
			
			int f = 0;
			
			String outFile = args[args.length - 1];
			
			Document document = null;
			
			PdfCopy writer = null;
			
			while (f < args.length - 1) {
			
				PdfReader reader = new PdfReader(args[f]);
				
				reader.consolidateNamedDestinations();
				
				int n = reader.getNumberOfPages();
				
				List bookmarks = SimpleBookmark.getBookmark(reader);
				
				if (bookmarks != null) {
					if (pageOffset != 0)
						SimpleBookmark.shiftPageNumbers(bookmarks, pageOffset, null);
					master.addAll(bookmarks);
				}
				pageOffset += n;
				if (f == 0) {
					document = new Document(reader.getPageSizeWithRotation(1));
					writer = new PdfCopy(document, new FileOutputStream(outFile));
					document.open();
				}
				PdfImportedPage page;
				for (int i = 0; i < n;) {
					++i;
					page = writer.getImportedPage(reader, i);
					writer.addPage(page);
				}
				PRAcroForm form = reader.getAcroForm();
				if (form != null)
					writer.copyAcroForm(reader);
				f++;
			}
			if (!master.isEmpty())
				writer.setOutlines(master);
			document.close();
		}
	}
	
	
	/**
	 * incluir referencia en páginas Html
	 */
	public void htmlReference() {
		try {

			Document document = new Document();
			//PdfWriter pdf = PdfWriter.getInstance(document, new FileOutputStream("e:/differentWritersdPdf.pdf"));
//			RtfWriter2 rtf = RtfWriter2.getInstance(document, new FileOutputStream("e:/differentWritersRtf.rtf"));
			//HtmlWriter html = HtmlWriter.getInstance(document, new FileOutputStream("e:/differentWritersdHtml.html"));

			document.open();

			document.add(new Paragraph("Hello World"));

			Anchor pdfRef = new Anchor("see Hello World in PDF.");
			pdfRef.setReference("./HelloWorldPdf.pdf");
			Anchor rtfRef = new Anchor("see Hello World in RTF.");
			rtfRef.setReference("./HelloWorldRtf.rtf");

			Image image = Image.getInstance("e:/cupon.png");
			document.add(new Paragraph("Original:"));
			document.add(image);
			image.scalePercent(120.0f);
			image.setRotationDegrees(30.0f);
			document.add(new Paragraph("After Scalable and Rotation"));
			document.add(image);

			// pdf.resume();
			// rtf.resume();

			document.close();

		} catch (DocumentException de) {
			System.err.println(de.getMessage());
		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
		}
	}

	/**
	 * Escalar una imagen
	 */
	public void scaleImage() {
		try {
			Document document = new Document();
			PdfWriter.getInstance(document, new FileOutputStream("e:/imagesRotationPDF.pdf"));
	//		RtfWriter2 rtf = RtfWriter2.getInstance(document, new FileOutputStream("e:/imagesRotationPDF.rtf"));
			document.open();
			Image image = Image.getInstance("e:/cupon.png");
			document.add(new Paragraph("Original:"));
			document.add(image);
			image.scalePercent(20.0f);
			image.setRotationDegrees(30.0f);
			document.add(new Paragraph("After Scalable and Rotation"));
			document.add(image);
		//	rtf.resume();
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

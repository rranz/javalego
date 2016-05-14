package com.javalego.pdf;

import java.io.FileOutputStream;

import com.lowagie.text.Document;
import com.lowagie.text.ExceptionConverter;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;

/**
 * Demonstrates the use of PageEvents.
 */
public class EndPage extends PdfPageEventHelper {
    
    /**
     * Demonstrates the use of PageEvents.
     * @param args no arguments needed
     */
    public static void main(String[] args)
    {
        Document document = new Document(PageSize.A4, 50, 50, 70, 70);
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("d:/endpage.pdf"));
            writer.setPageEvent(new EndPage());
            document.open();
            String text = "Lots of text. ";
            for (int k = 0; k < 10; ++k)
                text += text;
            document.add(new Paragraph(text));
            document.close();
        }
        catch (Exception de) {
            de.printStackTrace();
        }
    }
    
    /**
     * @see com.lowagie.text.pdf.PdfPageEventHelper#onEndPage(com.lowagie.text.pdf.PdfWriter, com.lowagie.text.Document)
     */
    @Override
    public void onStartPage(PdfWriter writer, Document document) {
        try {
            Rectangle page = document.getPageSize();
            PdfPTable head = new PdfPTable(3);
            for (int k = 1; k <= 6; ++k)
                head.addCell("head " + k);
            head.setTotalWidth(page.getWidth() - document.leftMargin() - document.rightMargin());
            head.writeSelectedRows(0, -1, document.leftMargin(), page.getHeight() - document.topMargin() + head.getTotalHeight(),
                writer.getDirectContent());
            PdfPTable foot = new PdfPTable(3);
            for (int k = 1; k <= 6; ++k)
                foot.addCell("foot " + k);
            foot.setTotalWidth(page.getWidth() - document.leftMargin() - document.rightMargin());
            foot.writeSelectedRows(0, -1, document.leftMargin(), document.bottomMargin(),
                writer.getDirectContent());
        }
        catch (Exception e) {
            throw new ExceptionConverter(e);
        }
    }
    
    
    /**
     * Waterman
     */
//    public void onStartPage(PdfWriter writer, Document document) {
//      if (writer.getPageNumber() < 3) {
//          PdfContentByte cb = writer.getDirectContentUnder();
//          cb.saveState();
//          cb.setColorFill(Color.pink);
//          cb.beginText();
//          BaseFont helv = null;
//					try {
//						helv = BaseFont.createFont("Helvetica", BaseFont.WINANSI, false);
//					} catch (DocumentException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//          cb.setFontAndSize(helv, 48);
//          cb.showTextAligned(Element.ALIGN_CENTER, "My Watermark Under " + writer.getPageNumber(), document.getPageSize().width() / 2, document.getPageSize().height() / 2, 45);
//          cb.endText();
//          cb.restoreState();
//      }
//    }
    
    
    /**
     * @see com.lowagie.text.pdf.PdfPageEventHelper#onEndPage(com.lowagie.text.pdf.PdfWriter, com.lowagie.text.Document)
     * 
     * http://es.wikibooks.org/wiki/Programaci%C3%B3n_en_Java_/_Crear_un_PDF_en_Java
     */
//    public void onEndPage(PdfWriter writer, Document document) {
//        PdfContentByte cb = writer.getDirectContent();
//        cb.saveState();
//        // write the headertable
//        Image headerImage;
//        /** The headertable. */
//        PdfPTable table = new PdfPTable(2);
//        /** The Graphic state */
//        PdfGState gstate;
//        /** A template that will hold the total number of pages. */
//        PdfTemplate tpl;
//        /** The font that will be used. */
//        BaseFont helv = null;
//        table.setTotalWidth(document.right() - document.left());
//        table.writeSelectedRows(0, -1, document.left(), document.getPageSize().height() - 50, cb);
//        // compose the footer
//        String text = "Page " + writer.getPageNumber() + " of ";
//        float textSize = helv.getWidthPoint(text, 12);
//        float textBase = document.bottom() - 20;
//        cb.beginText();
//        cb.setFontAndSize(helv, 12);
//        // for odd pagenumbers, show the footer at the left
//        if ((writer.getPageNumber() & 1) == 1) {
//            cb.setTextMatrix(document.left(), textBase);
//            cb.showText(text);
//            cb.endText();
//            cb.addTemplate(tpl, document.left() + textSize, textBase);
//        }
//        // for even numbers, show the footer at the right
//        else {
//            float adjust = helv.getWidthPoint("0", 12);
//            cb.setTextMatrix(document.right() - textSize - adjust, textBase);
//            cb.showText(text);
//            cb.endText();
//            cb.addTemplate(tpl, document.right() - adjust, textBase);
//        }
//        cb.saveState();
//        // draw a Rectangle around the page
//        cb.setColorStroke(Color.orange);
//        cb.setLineWidth(2);
//        cb.rectangle(20, 20, document.getPageSize().width() - 40, document.getPageSize().height() - 40);
//        cb.stroke();
//        cb.restoreState();
//        // starting on page 3, a watermark with an Image that is made transparent
//        if (writer.getPageNumber() >= 3) {
//            cb.setGState(gstate);
//            cb.setColorFill(Color.red);
//            cb.beginText();
//            cb.setFontAndSize(helv, 48);
//            cb.showTextAligned(Element.ALIGN_CENTER, "Watermark Opacity " + writer.getPageNumber(), document.getPageSize().width() / 2, document.getPageSize().height() / 2, 45);
//            cb.endText();
//            try {
//                cb.addImage(headerImage, headerImage.width(), 0, 0, headerImage.height(), 440, 80);
//            }
//            catch(Exception e) {
//                throw new ExceptionConverter(e);
//            }
//            cb.restoreState();
//        }
//    }    
  
}

package com.javalego.msoffice.test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.javalego.util.DesktopUtils;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;

/**
 * The ExtendedHeaderFooter example demonstrates the use of the
 * RtfHeaderFooter object to create more complex headers or footers
 * using more complex elements such as multiple paragraphs or tables.
 * 
 * @version $Revision: 3004 $
 * @author Mark Hall (mhall@edu.uni-klu.ac.at)
 */
public class ExtendHeaderFooter {
    /**
     * Extended headers / footers example
     * 
     * @param args Unused
     */
    public static void main(String[] args) {
        System.out.println("Demonstrates use of the RtfHeaderFooter for extended headers and footers");
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("d:/footer.rtf"));

//            // Create the Paragraphs that will be used in the header.
//            Paragraph date = new Paragraph("01.01.2010");
//            date.setAlignment(Paragraph.ALIGN_RIGHT);
//            Paragraph address = new Paragraph("TheFirm\nTheRoad 24, TheCity\n" +
//                    "+00 99 11 22 33 44");
//
//            // Create the RtfHeaderFooter with an array containing the Paragraphs to add
//            RtfHeaderFooter header = new RtfHeaderFooter(new Element[]{date, address});
//            
//            // Set the header
//            document.setHeader(header);
//
//            // Create the table that will be used as the footer
//            Table footer = new Table(2);
//            footer.setBorder(0);
//            //footer.getDefaultCell().setBorder(0);
//            footer.setWidth(100);
//            footer.addCell(new Cell("(c) Mark Hall"));
//            Paragraph pageNumber = new Paragraph("Page ");
//            
//            // The RtfPageNumber is an RTF specific element that adds a page number field
//            pageNumber.add(new RtfPageNumber());
//            pageNumber.setAlignment(Paragraph.ALIGN_RIGHT);
//            footer.addCell(new Cell(pageNumber));
//            
//            // Create the RtfHeaderFooter and set it as the footer to use
//            document.setFooter(new RtfHeaderFooter(footer));
            
            document.open();
            
            Table table = null;
            table = new Table(4);
            //table.setWidths(new float[] { 35.0f, 25.0f, 15.0f, 25.0f });
            //table.setWidth(100);
          document.add(new Paragraph("This document has headers and footers created" +
          " using the RtfHeaderFooter class."));
            document.add(table);
            document.add(table);
            document.add(table);

            document.close();
            try {
							DesktopUtils.openFile("d:/footer.rtf");
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch (DocumentException de) {
            de.printStackTrace();
        }
    }
}

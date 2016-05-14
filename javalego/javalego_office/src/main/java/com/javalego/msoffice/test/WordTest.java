package com.javalego.msoffice.test;

import com.javalego.msoffice.word.Bookmark;
import com.javalego.msoffice.word.Document;
import com.javalego.msoffice.word.MailMerge;
import com.javalego.msoffice.word.WordApplication;

public class WordTest {

	public static final void main(String args[]) {
		
		WordApplication w = new WordApplication();
		try {
			w.open(true);
			Document document = w.getDocuments().openDocument("c:/tmp_combina_word/diploma.doc");
			
			Bookmark bookmark = document.getBookmark("LOGO");
			bookmark.select();
			w.addPicture("d:\\gana.jpg");
			
			MailMerge mail = new MailMerge(w);
			mail.merge("d:\\diploma.xls", "d:\\diploma.doc");
			
			
//	    If ActiveWindow.View.SplitSpecial = wdPaneNone Then
//      ActiveWindow.ActivePane.View.Type = wdNormalView
//  Else
//      ActiveWindow.View.Type = wdNormalView
//  End If
//  If ActiveWindow.View.SplitSpecial = wdPaneNone Then
//      ActiveWindow.ActivePane.View.Type = wdPrintView
//  Else
//      ActiveWindow.View.Type = wdPrintView
//  End If			
			
			//document.getBookmarks().getBookmark("CAMPO1").getFont().setSize(44);
			//document.getBookmarks().getBookmark("CAMPO1").setText("CAMPO1");
//			document.getBookmarks().getBookmark("CAMPO2").setText("CAMPO2");
//			document.getBookmarks().getBookmark("CAMPO3").setText("CAMPO3");

//			Table table = document.getTables().getTable(1);
//			Row row = table.addRow();
//			row.getCell(1).setText("asdfñlkasdjf");
//			row.getCell(2).setText("asdfñlkasdjf");
//			row.getCell(2).select();
//			w.addPicture("E:\\gana_check2.png");
//			Font f = row.getCell(1).getFont();
//			f.setColor(255);
//			f.setName("Tahoma");
//			f.setSize(22);
//			System.out.println("bm.getText()");
			
//			Bookmark bookmark = document.getBookmark("LOGO");
//			bookmark.select();
//			w.addPicture("E:\\gana_check2.png");
//			w.setPrintView();
//			w.seekMainDocument();
//			w.setPrintView();
			
			
			//document.save();
			
			//document.save();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				w.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	
}

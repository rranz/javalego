package com.javalego.msoffice.test;

/**
 * Creates a simple Word document 
 *
 * @version     $Revision: 1.2 $
 * @author      Stuart Halloway, http://www.relevancellc.com/halloway/weblog/
 */
public class CreateWord {

	public static void main(String[] args) {
//		Dispatch app = null;
//		Dispatch doc = null;
//		try {
//			//Ole32.CoInitialize();
//
//			app = new DispatchPtr("Word.Application");
//			//app.put("DisplayAlerts", 2); // Word.WdAlertLevel.wdAlertsNone
//			app.put("Visible", true);
//			DispatchPtr docs = (DispatchPtr) app.get("Documents");
//			doc = (DispatchPtr) docs.invoke("Open", "c:/tmp_combina_word/diploma.doc");
//
//			DispatchPtr activeDocument = (DispatchPtr) app.get("ActiveDocument");
//			DispatchPtr mail = (DispatchPtr) activeDocument.get("MailMerge");
//
//			mail.put("MainDocumentType", 0); // wdFormLetters
//
//			mail.invoke("OpenDataSource", "d:\\diploma.xls", false, false, true);
//			
////	    ActiveDocument.MailMerge.OpenDataSource Name:="C:\tmp_combina_word\diploma.xls", ConfirmConversions:=False, ReadOnly:= _
////	        False, LinkToSource:=True, AddToRecentFiles:=False, PasswordDocument:="", _
////	         PasswordTemplate:="", WritePasswordDocument:="", WritePasswordTemplate:= _
////	        "", Revert:=False, Format:=wdOpenFormatAuto, Connection:= _
////	        "Provider=Microsoft.Jet.OLEDB.4.0;Password="""";User ID=Admin;Data Source=C:\tmp_combina_word\diploma.xls;Mode=Read;Extended Properties=""HDR=YES;IMEX=1;"";Jet OLEDB:System database="""";Jet OLEDB:Registry Path="""";Jet OLEDB:Database Password="""";Jet OLEDB:Engine " _
////	        , SQLStatement:="SELECT * FROM `Informe$`", SQLStatement1:="", SubType:= _
////	        wdMergeSubTypeAccess
//			
////	    With ActiveDocument.MailMerge
////	        .Destination = wdSendToNewDocument
////	        .SuppressBlankLines = True
////	        With .DataSource
////	            .FirstRecord = wdDefaultFirstRecord
////	            .LastRecord = wdDefaultLastRecord
////	        End With
////	        .Execute Pause:=False
////	    End With
////	    Windows("diploma.doc").Activate			
//			
//			
//			mail.put("Destination", 0); // wdSendToNewDocument
//			mail.put("SuppressBlankLines", true); 
//			DispatchPtr data = (DispatchPtr) mail.get("DataSource");
//			data.put("FirstRecord", 1); // wdDefaultFirstRecord
//			data.put("LastRecord", -16); // wdDefaultLastRecord
//			mail.invoke("Execute", false);
//			
//			DispatchPtr windows = (DispatchPtr) app.get("Windows");
//			DispatchPtr window = (DispatchPtr)windows.invoke("Item", new Integer(2));
//			window.invoke("Close");
//
//			
////			DispatchPtr windows = (DispatchPtr) app.get("Windows");
////			windows.invoke("Close");
//			
//			//Windows("diploma.doc").Close
//			
////	    With ActiveDocument.MailMerge
////      .Destination = wdSendToNewDocument
////      .SuppressBlankLines = True
////      With .DataSource
////          .FirstRecord = wdDefaultFirstRecord
////          .LastRecord = wdDefaultLastRecord
////      End With
////      .Execute Pause:=False
////  End With
//			
////			DispatchPtr selection = (DispatchPtr) app.get("Selection");
////			
////			DispatchPtr tables = (DispatchPtr) selection.get("Tables");
////			DispatchPtr table = (DispatchPtr) tables.invoke("Item", new Integer(1));
////			
////			DispatchPtr rows = (DispatchPtr)table.get("Rows");
////			DispatchPtr row = (DispatchPtr)rows.invoke("Item", new Integer(2));
////			
////			DispatchPtr cells = (DispatchPtr)row.get("Cells");
////			DispatchPtr cell = (DispatchPtr)cells.invoke("Item", new Integer(2));
////			
////			cell.invoke("Select");
////			
////			DispatchPtr inline = (DispatchPtr) selection.get("InlineShapes");
////			DispatchPtr picture = (DispatchPtr)inline.invoke("AddPicture", "E:\\gana_check2.png", false, true);
////			
////			
////						
////			DispatchPtr bookmarks = (DispatchPtr) doc.get("Bookmarks");
//			//DispatchPtr tables = (DispatchPtr) doc.get("Tables");
//
//			//String name = (String) doc.get("Name");
//			//Integer count = (Integer) tables.get("Count");
//
////			DispatchPtr bookmark = (DispatchPtr) bookmarks.invoke("Item", new Integer(3));			
////			DispatchPtr range = (DispatchPtr) bookmark.get("Range");
////			range.put("Text", "Valor..asdklfjasdklñjfklsdajfasdklñjf.");
////			String text = (String) range.get("Text");
////			System.out.println(text);
//
//			
//			//DispatchPtr range = (DispatchPtr)bookmark.get("Range");
//			//bookmark.put("Name", "Use Jawin to call COM objects");			
//			
//			//DispatchPtr range = (DispatchPtr) bookmark.invoke("Range");			
//			
//			//bookmark.put("Text", "valor......");
//			//System.out.println(bm);
//			
//			//System.out.println(item.invoke("Value", "Valor de la marca"));
//			//System.out.println(item.get("Text"));
//			//app.invoke("Run","”EdiciónIrA");
//			
//			Ole32.CoUninitialize();
//		} catch (Exception e) {
//			e.printStackTrace();
//			try {
//				if (doc != null)
//					doc.invoke("Close");
//				//if (app != null)
//					//app.invoke("Exit");
//			} catch (COMException e1) {
//			}
//		}
	}

}

package com.javalego.pdf;

import java.awt.Color;
import java.io.File;
import java.io.IOException;

import com.javalego.util.FileUtils;
import com.javalego.util.SystemUtils;
import com.lowagie.text.Cell;
import com.lowagie.text.Chapter;
import com.lowagie.text.Chunk;
import com.lowagie.text.DocWriter;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfAnnotation;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.draw.LineSeparator;

/**
 * Clase utiliza para la creación de documentos Word (Rtf) o Pdf.
 * @author Administrador
 *
 */
public abstract class BasicWapperWriter extends BasicPdfWapper {

	protected String fileName;
	protected int defaultFontName = Font.HELVETICA; //TIMES_ROMAN;
	protected int defaultSize = 10;

	/**
	 * Fuente base que utilizamos para cargar una fuente por defecto en todos los métodos de esta clase que utilicen un font.
	 * ver solution.xml donde se define la fuente en GANA para cada cliente.
	 */
	private static BaseFont baseFont;
	
	/**
	 * Cargar la fuente desde un recurso.
	 * @param resourceFileName
	 */
	public static void loadDefaultFontResource(String resourceFileName) {
	  try {
			baseFont = BaseFont.createFont(resourceFileName, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	public BasicWapperWriter(String fileName) throws Exception {
		this.fileName = fileName;
		initialize();
	}
	
	public BasicWapperWriter() throws Exception {
		fileName = FileUtils.getFreeSecuencialFile("tmp", getExtension());
		initialize();
	}
	
	/**
	 * Extensión de los archivos
	 * @return
	 */
	public abstract String getExtension();
	
	public void open(){
		document.open();
	}
	
	public void close(){
		document.close();
	}
	
	/**
	 * Inicializar documento.
	 * @throws Exception
	 */
	protected abstract void initialize() throws Exception;
	
	/**
	 * Obtener la clase básica de generación de documentos.
	 * @return
	 */
	protected abstract DocWriter getDocWriter();
	
	/**
	 * Añadir el outline root del documento.
	 */
	public abstract void addRootOutline(String text, Color color, boolean bold);

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public abstract void setUseOutlines();
	
	/**
	 * Propiedades del documento
	 */
	public void setProperties(String title, String subject, String creator, String author){
		document.addTitle(title);
		document.addSubject(subject);
    document.addCreator(creator);
    document.addAuthor(author);
	}

	/**
	 * Añadir número de página en el footer del documento.
	 * @param text
	 */
	public void setFooterPageNumber(){
  	HeaderFooter footer = new HeaderFooter(new Phrase("Page"), true);
  	footer.setAlignment(Paragraph.ALIGN_CENTER);
  	footer.setBorder(Rectangle.NO_BORDER);
  	document.setFooter(footer);
	}

	public void addNewLine() throws Exception {
		document.add(getTextSize(" ", defaultSize)); 
	}
	
	public Paragraph getNewLine() throws Exception {
		return getTextSize(" ", defaultSize); 
	}
	
	public void addHorizontalLine() throws Exception {
		addHorizontalLine(null);
	}
	
	/**
	 * Añadir una anotación a un Paragrah.
	 * @param pdf
	 * @param p
	 * @param note
	 */
	public Phrase getAnnotation(PdfWriter pdf, String text, String note) {
		Phrase p = new Phrase();
		Chunk chunk = new Chunk(" ");
		chunk.setFont(getDefaultFont(22, Font.NORMAL, Color.BLACK));
		PdfAnnotation a = PdfAnnotation.createText(pdf, null,	text, note,	false, "Comment");
		chunk.setAnnotation(a);
		p.add(chunk);
		return p;		
	}
	
	public void addHorizontalLine(Color color) throws Exception {
		LineSeparator l = new LineSeparator();
		l.setLineWidth(0.4f);
		if (color != null)
			l.setLineColor(color);
		document.add(l);
	}
	
	public void addNewLine(int count) throws Exception {
    for(int i = 0; i < count; i++)
    	addNewLine();
	}
	
	/**
	 * Propiedades del documento
	 */
	public void setProperties(String title){
		document.addTitle(title);
	}
	
	/**
	 * Obtener una imagen de un recurso
	 * @param imageName
	 * @return
	 * @throws Exception
	 */
	public Image getImageFromResource(String imageName) throws Exception {
		try{
			return Image.getInstance(SystemUtils.getBytesFromResource(imageName));
		}
		catch(Exception e){
			return null;
		}
	}
	
	/**
	 * Obtener una imagen de un recurso
	 * @param imageName
	 * @param scale escalado de la imagen.
	 * @return
	 * @throws Exception
	 */
	public Image getImageFromResource(String imageName, float scale) throws Exception {
		try{
			Image image = Image.getInstance(SystemUtils.getBytesFromResource(imageName));
			image.scalePercent(scale);
			return image;
		}
		catch(Exception e){
			return null;
		}
	}
	
	/**
	 * Obtener una imagen de un archivo
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public Image getImageFromFileName(String fileName) throws Exception {
		try{
			return Image.getInstance(FileUtils.getBytesFromFile(new File(fileName))); 
		}
		catch(Exception e){
			return null;
		}
	}
	
	public void add(Element element) throws Exception {
		document.add(element);
	}
	
	/**
	 * Añadir imagen al documento.
	 * @param imageName
	 * @throws Exception
	 */
	public void addImageFromResource(String imageName) throws Exception {
		Image image = getImageFromResource(imageName);
		if (image != null)
			document.add(getImageFromResource(imageName));
	}
	
	public void addImageFromResource(String imageName, int align) throws Exception {
    Image jpg = getImageFromResource(imageName);
    if (jpg != null) {
	    jpg.setAlignment(align);
	    document.add(jpg);
    }
	}

	/**
	 * Obtiene un paragraph que incluye una imagen.
	 * @param imageName
	 * @return
	 */
	public Paragraph getParagraphImage(String imageName) throws Exception {
		try{
			Image image = getImageFromResource(imageName);
			if (image != null) {
				Chunk chunk = new Chunk(image, 0,0);
				return new Paragraph(chunk);
			}
			else
				return new Paragraph("ERROR: image " + imageName + " NOT FOUND");
		}
		catch(Exception e){
			return null;
		}
	}
	
	// Obtención de font
	public Font getDefaultFont(int size, int style, Color color) {
		
		if (baseFont == null)
			return new Font(defaultFontName, size, style, color);
		else
			return new Font(baseFont, size, style, color);
	}
	
	// Funciones de texto
	
  // Obtener paragraph
	
	public Paragraph getText(String text) throws Exception {
  	return new Paragraph(text, getDefaultFont(defaultSize, Font.NORMAL, Color.BLACK));
  }
	
  public Paragraph getTextBold(String text) throws Exception {
  	return new Paragraph(text, getDefaultFont(defaultSize, Font.BOLD, Color.BLACK));
  }
	
  public Paragraph getTextColor(String text, Color color) throws Exception {
  	return new Paragraph(text, getDefaultFont(defaultSize, Font.BOLD, color));
  }
  
  public Paragraph getTextStyle(String text, int style) throws Exception {
  	return new Paragraph(text, getDefaultFont(defaultSize, Font.BOLD, Color.BLACK));
  }
	
  public Paragraph getTextBoldColor(String text, Color color) throws Exception {
  	return new Paragraph(text, getDefaultFont(defaultSize, Font.BOLD, color));
  }
	
  public Paragraph getTextStyleColor(String text, int style, Color color) throws Exception {
  	return new Paragraph(text, getDefaultFont(defaultSize, style, color));
  }
	
  // + Size
  public Paragraph getTextSize(String text, int size) throws Exception {
  	return new Paragraph(text, getDefaultFont(size, Font.NORMAL, Color.BLACK));
  }
	
  public Paragraph getTextSizeBold(String text, int size) throws Exception {
  	return new Paragraph(text, getDefaultFont(size, Font.BOLD, Color.BLACK));
  }
	
  public Paragraph getTextSizeColor(String text, int size, Color color) throws Exception {
  	return new Paragraph(text, getDefaultFont(size, Font.BOLD, color));
  }
  
  public Paragraph getTextSizeColor(String text, int size, Color color, int align) throws Exception {
  	Paragraph p = new Paragraph(text, getDefaultFont(size, Font.BOLD, color));
  	p.setAlignment(align);
  	return p;
  }
  
  public Paragraph getTextSizeStyle(String text, int size, int style) throws Exception {
  	return new Paragraph(text, getDefaultFont(size, Font.BOLD, Color.BLACK));
  }
	
  public Paragraph getTextSizeBoldColor(String text, int size, Color color) throws Exception {
  	return new Paragraph(text, getDefaultFont(size, Font.BOLD, color));
  }

  public Paragraph getTextSizeBoldColor(String text, int size, Color color, int align) throws Exception {
  	Paragraph p = new Paragraph(text, getDefaultFont(size, Font.BOLD, color));
    p.setAlignment(align);
    return p;
  }
  
  public Paragraph getTextSizeStyleColor(String text, int size, int style, Color color) throws Exception {
  	return new Paragraph(text, getDefaultFont(size, style, color));
  }
	
  // Añadir texto al documento
  
  public void addText(String text) throws Exception {
  	document.add(getText(text));
  }
	
  public void addTextBold(String text) throws Exception {
  	document.add(getTextBold(text));
  }
	
  public void addTextColor(String text, Color color) throws Exception {
  	document.add(getTextColor(text, color));
  }
	
  public void addTextStyle(String text, int style) throws Exception {
  	document.add(getTextStyle(text, style));
  }
	
  public void addTextBoldColor(String text, Color color) throws Exception {
  	document.add(getTextBoldColor(text, color));
  }
	
  public void addTextStyleColor(String text, int style, Color color) throws Exception {
  	document.add(getTextStyleColor(text, style, color));
  }

  // + size
  public void addTextSize(String text, int size) throws Exception {
  	document.add(getText(text));
  }
	
  public void addTextSizeBold(String text, int size) throws Exception {
  	document.add(getTextSizeBold(text, size));
  }
	
  public void addTextSizeColor(String text, int size, Color color) throws Exception {
  	document.add(getTextSizeColor(text, size, color));
  }
	
  public void addTextSizeColor(String text, int size, Color color, int align) throws Exception {
  	document.add(getTextSizeColor(text, size, color, align));
  }
	
  public void addTextSizeStyle(String text, int size, int style) throws Exception {
  	document.add(getTextSizeStyle(text, size, style));
  }
	
  public void addTextSizeBoldColor(String text, int size, Color color) throws Exception {
  	document.add(getTextSizeBoldColor(text, size, color));
  }
	
  public void addTextSizeStyleColor(String text, int size, int style, Color color) throws Exception {
  	document.add(getTextSizeStyleColor(text, size, style, color));
  }

  
	public int getDefaultFontName() {
		return defaultFontName;
	}

	public void setDefaultFontName(int defaultFontName) {
		this.defaultFontName = defaultFontName;
	}
	
	public void setDefaultFontName(String defaultFontName) {
		this.defaultFontName = Font.getFamilyIndex(defaultFontName);
	}
	
	// Phrase
	public Phrase getPhrase(Paragraph[] paragraph){
    Phrase phrase = new Phrase();
    for(int i = 0; i < paragraph.length; i++)
    	phrase.add(paragraph[i]);
    return phrase;
	}
	
	public void addPhrase(Phrase phrase) throws Exception {
		document.add(phrase);
	}
	
	public Chapter getChapter(String title) throws Exception {
    Chapter chapter = new Chapter(title, 1);
    chapter.setNumberDepth (0);
		return chapter;
	}

	public Chapter getChapter(Paragraph title) throws Exception {
    Chapter chapter = new Chapter(title, 1);
    chapter.setNumberDepth (0);
		return chapter;
	}

	public int getDefaultSize() {
		return defaultSize;
	}

	public void setDefaultSize(int defaultSize) {
		this.defaultSize = defaultSize;
	}

	// Tablas
	public PdfPCell getCell(String text, Color forecolor, Color backgroundcolor) throws Exception {
		PdfPCell cell = new PdfPCell(getTextColor(text, forecolor));
		cell.setBackgroundColor(backgroundcolor);
		return cell;
	}
	
	public PdfPCell getCell(String text, Color backgroundcolor) throws Exception {
		PdfPCell cell = new PdfPCell(getText(text));
		cell.setBackgroundColor(backgroundcolor);
		return cell;
	}
	
	public Cell getBasicCell(String text, Color forecolor, Color backgroundcolor) throws Exception {
		Cell cell = new Cell(getTextColor(text, forecolor));
		cell.setBackgroundColor(backgroundcolor);
		return cell;
	}
	
	public Cell getBasicCell(String text, Color backgroundcolor) throws Exception {
		Cell cell = new Cell(getText(text));
		cell.setBackgroundColor(backgroundcolor);
		return cell;
	}
	
	public PdfPCell getCellForeColor(String text, Color forecolor) throws Exception {
		PdfPCell cell = new PdfPCell(getTextColor(text, forecolor));
		return cell;
	}
	
	public PdfPCell getCell(String text) throws Exception {
		PdfPCell cell = new PdfPCell(getText(text));
		return cell;
	}
	
	public Cell getBasicCell(String text) throws Exception {
		Cell cell = new Cell(getText(text));
		return cell;
	}
	
	public PdfPCell getCellSize(String text, int size) throws Exception {
		PdfPCell cell = new PdfPCell(getTextSize(text, size));
		return cell;
	}
	
	public PdfPCell getCellBold(String text) throws Exception {
		PdfPCell cell = new PdfPCell(getTextBold(text));
		return cell;
	}
	
	public PdfPCell getCellBold(String text, Color forecolor, Color backgroundcolor) throws Exception {
		PdfPCell cell = new PdfPCell(getTextBoldColor(text, forecolor));
		cell.setBackgroundColor(backgroundcolor);
		return cell;
	}
	
	public PdfPCell getCellSizeBold(String text, int size, Color forecolor, Color backgroundcolor) throws Exception {
		PdfPCell cell = new PdfPCell(getTextSizeBoldColor(text, size, forecolor));
		cell.setBackgroundColor(backgroundcolor);
		return cell;
	}
	
	public PdfPCell getCellSize(String text, Color forecolor, Color backgroundcolor) throws Exception {
		PdfPCell cell = new PdfPCell(getTextBoldColor(text, forecolor));
		cell.setBackgroundColor(backgroundcolor);
		return cell;
	}
	
}

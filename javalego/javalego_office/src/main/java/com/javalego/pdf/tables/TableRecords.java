package com.javalego.pdf.tables;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import com.javalego.pdf.RtfWapperWriter;
import com.javalego.util.ReflectionUtils;
import com.lowagie.text.Cell;
import com.lowagie.text.Element;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Table;

/**
 * Construcción de tablas en base a elementos de 
 * @author ROBERTO RANZ
 */
public class TableRecords {

	private int defaultFontSize = 10;
	
	private RtfWapperWriter document;

	private Fields fields = new Fields();
	
	/**
	 * Lista de objetos que representa registros. Estos pueden ser una lista de objetos de donde obtenemos el valores de los campos de la 
	 * tabla mediante reflexión o una lista de objetos de valores (object[]) que accedemos a los campos por su índice.
	 */
	private List<Object> objects;
	
	/**
	 * Ancho de cada celda.
	 */
	public float[] widths;
	
	/**
	 * Ancho de la tabla
	 */
	public float width = 100;
	
	public TableRecords() {}
	
	public TableRecords(RtfWapperWriter document) {
		this.document = document;
		defaultFontSize = document.getDefaultSize();
	}
	
	/**
	 * Obtener la tabla construida en base a los campos definidos y a la lista de objetos que constituyen los registros
	 * @return
	 */
	public Table getTable() throws Exception {
		
		if (objects == null)
			return null;
		
		Table table = new Table(fields.size(), objects.size());
    table.setWidth(width);

    if (widths != null)
    	table.setWidths(widths);
    
    addTableHeaderFields(table);

    addTableRecords(table);
    
		return table;
	}

	/**
	 * Obtener la tabla construida en base a los campos definidos y a la lista de objetos que constituyen los registros
	 * @return
	 */
	public Table getTableRecords() throws Exception {
		return getTableRecords(objects.size());
	}
	
	/**
	 * Obtener la tabla construida en base a los campos definidos y a la lista de objetos que constituyen los registros
	 * @param Número de filas.
	 * @return
	 */
	public Table getTableRecords(int size) throws Exception {
		
		if (objects == null)
			return null;
		
		Table table = new Table(fields.size(), size);
    table.setWidth(width);

    if (widths != null)
    	table.setWidths(widths);
    
    addTableRecords(table);
    
		return table;
	}

	/**
	 * Obtener la tabla construida en base a los campos definidos y a la lista de objetos que constituyen los registros
	 * @return
	 */
	public Table getTableHeaderFields() throws Exception {
		
		Table table = new Table(fields.size(), 1);
    table.setWidth(width);

    if (widths != null)
    	table.setWidths(widths);

    addTableHeaderFields(table);

    return table;
	}

	/**
	 * Obtener la tabla con un número de registros determinados con información vacía.
	 * @return
	 */
	public Table getTableEmptyRecords(int count, boolean header) throws Exception {
		
		Table table = new Table(fields.size(), count);
    table.setWidth(width);

    if (widths != null)
    	table.setWidths(widths);

    if (header)
    	addTableHeaderFields(table);
    
    for(int i = 0; i < count; i++)
    	addTableEmptyRecord(table);

    return table;
	}

	/**
	 * Añadir registros al documento
	 * @param table
	 * @throws Exception
	 */
	private void addTableRecords(Table table) throws Exception {
		
    // Filas de registros
    for(Object object : objects) {
	    
    	int count = -1;
    	for(Field field : fields) {
	    	
    		++count;
	    	
    		// Valor del campo dependiendo de su configuración y los objetos que representan cada registro.
    		String text = "";
    		Object value = null;
    		if (object instanceof Object[])
    		
    			value = ((Object[])object)[count];
    		
    		// Valor de la propiedad del objeto.
    		else if (field.getPropertyName() != null)
    			
    			value = ReflectionUtils.getPropertyValue(object, field.getPropertyName());
    		
    		// Valor por defecto.
    		else if (field.getDefaultValue() != null)
    			value = field.getDefaultValue();
    		
    		if (value != null)
    			text = value.toString();
    		
    		// Generar celda
	      Cell c = new Cell();
	      c.setVerticalAlignment(Element.ALIGN_MIDDLE);
	      
	      Paragraph p = null;
	      if (field.getForeColor() != null)
	      	p = document.getTextSizeColor(text, defaultFontSize, field.getForeColor());
	      else
	      	p = document.getTextSize(text, defaultFontSize);
	  
	      if (field.getBackgroundColor() != null)
	      	c.setBackgroundColor(field.getBackgroundColor());
	      
	      if (value != null && value instanceof Number)
	      	p.setAlignment(Element.ALIGN_RIGHT);
	      else
	      	p.setAlignment(Element.ALIGN_LEFT);
	      
	      c.addElement(p);
	      table.addCell(c);
	    }
    }
	}
	
	/**
	 * Añadir tabla de cabecera de campos.
	 * @throws Exception
	 */
	private void addTableHeaderFields(Table table) throws Exception {
		
    // Fila de títulos de los campos
    for(Field field : fields) {
    	
      Cell c = new Cell();
      
      c.setVerticalAlignment(Element.ALIGN_MIDDLE);
      
      Paragraph text = document.getTextSizeColor(field.getTitle(), defaultFontSize, field.getHeaderForeColor() != null ? field.getHeaderForeColor() : Color.white);
      
      text.setAlignment(Element.ALIGN_CENTER);

      c.setBackgroundColor(field.getHeaderBackgroundColor() != null ? field.getHeaderBackgroundColor() : Color.blue);
      
      c.addElement(text);
      
      table.addCell(c);
    }
	}

	/**
	 * Añadir a la tabla un registro sin valores en sus campos.
	 * @param table
	 * @throws Exception
	 */
	private void addTableEmptyRecord(Table table) throws Exception {
		addTableEmptyRecord(table);
	}
	
	/**
	 * Añadir a la tabla un registro sin valores en sus campos.
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	private void addTableEmptyRecord(Table table, String defaultValue) throws Exception {
		
    // Fila de títulos de los campos
    for(Field field : fields) {
    	
      Cell c = new Cell();
      
      Paragraph text = document.getTextSize(defaultValue == null ? "" : defaultValue, defaultFontSize);
      
      text.setAlignment(Element.ALIGN_CENTER);
      
      if (field.getBackgroundColor() != null)
      	c.setBackgroundColor(field.getBackgroundColor());
      
      c.addElement(text);
      
      table.addCell(c);
    }
	}
	
	public Fields getFields() {
		return fields;
	}

	public void setFields(Fields fields) {
		this.fields = fields;
	}

	public List<Object> getObjects() {
		return objects;
	}

	public void setObjects(List<Object> objects) {
		this.objects = objects;
	}
	
	public static void main(String args[]) {

		TableRecords table = new TableRecords();
		
		table.setObjects(new ArrayList<Object>());
		
		table.getObjects().add(new Object[] { 1, "UNO", 20});
		table.getObjects().add(new Object[] { 2, "DOS", 30});
		table.getObjects().add(new Object[] { 3, "TRES", 50});
		
		Field field = new Field("Número");
		field.setBackgroundColor(Color.red);
		field.setHeaderBackgroundColor(Color.gray);
		table.getFields().add(field);
		table.getFields().add(new Field("Valor"));
		
		table.setWidths(new float[] { 20, 80});
		
		try {
			RtfWapperWriter w = new RtfWapperWriter("d:\\temp.doc");
			table.setDocument(w);
			w.open();
			w.add(table.getTable());
			w.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public RtfWapperWriter getDocument() {
		return document;
	}

	public void setDocument(RtfWapperWriter document) {
		this.document = document;
	}

	public float[] getWidths() {
		return widths;
	}

	public void setWidths(float[] widths) {
		this.widths = widths;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public int getDefaultFontSize() {
		return defaultFontSize;
	}

	public void setDefaultFontSize(int defaultFontSize) {
		this.defaultFontSize = defaultFontSize;
	}
	
}

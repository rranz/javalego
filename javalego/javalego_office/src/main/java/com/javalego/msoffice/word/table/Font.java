package com.javalego.msoffice.word.table;

import com.jacob.com.Dispatch;
import com.javalego.msoffice.word.BasicElementDocument;

/**
 * Font usado en diversos elementos del documento.
 * @author ROBERTO RANZ
 */
public class Font extends BasicElementDocument {

	private boolean bold;
	
	private int size;
	
	private String name;
	
	private int color;
	
	public Font() {
	}

	@Override
	protected String getInternalName() {
		return "Font";
	}

	@Override
	protected void setPointer() throws Exception {
	}

	public void setPointer(Dispatch pointer) {
		this.pointer = pointer;
	}

	public boolean isBold() throws Exception {
		return bold;
	}

	/**
	 * Bold
	 * @param bold
	 * @throws Exception
	 */
	public void setBold(boolean bold) throws Exception {
		Dispatch.put(getPointer(), "Bold", new Boolean(bold));
		this.bold = bold;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) throws Exception {
		Dispatch.put(getPointer(), "Size", size);
		this.size = size;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) throws Exception {
		Dispatch.put(getPointer(), "Name", name);
		this.name = name;
	}

	public void setColor(int color) throws Exception {
		Dispatch.put(getPointer(),"Color", new Integer(color));
		this.color = color;
	}
	
	public int getColor() {
		return color;
	}

}

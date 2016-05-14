package com.javalego.util;

import java.io.File;

/**
 * Filtro de selección de archivos para JFileChooser().
 * @author ROBERTO RANZ
 */
class FileFilters extends javax.swing.filechooser.FileFilter {
	
	private String[] filters;
	
	public FileFilters(String[] filters) {
		this.filters = filters;
	}
	
	@Override
  public boolean accept(File file) {
  	if (file.isDirectory())
  		return true;
  	else {
    	String filename = file.getName();
    	
    	if (filters == null)
    		return true;
    	
    	for(int i = 0; i < filters.length; i++) {
    		if (filename.toLowerCase().endsWith(filters[i]))
    			return true;
    	}
    	return false;
  	}
  }
	@Override
  public String getDescription() {
  	String text = "";
  	if (filters != null)
  		for(int i = 0; i < filters.length; i++) {
  			text += (text.equals("") ? "" : ",") + "*" + filters[i]; 
  	}
  	return text;
  }
}

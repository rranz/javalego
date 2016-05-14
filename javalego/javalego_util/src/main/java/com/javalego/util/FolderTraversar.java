package com.javalego.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Lear recursivamente todos los ficheros de un directorio.
 * Se puede especificar una extensión.
 * @author ROBERTO RANZ
 *
 */
public class FolderTraversar {

	private String indent = "";
	private File originalFileObject;
	private File fileObject;
	private String extension;
	
	private List<File> files = new ArrayList<File>();

	private boolean show;

	public FolderTraversar(File fileObject, boolean show, String extension) {
		this.originalFileObject = fileObject;
		this.fileObject = fileObject;
		this.show = show;
		this.extension = extension;
	}

	/**
	 * Ejecutar lectura
	 * @return
	 */
	public List<File> traverse() {
		recursiveTraversal(fileObject);
		return files;
	}

	private void recursiveTraversal(File fileObject) {
		
		if (fileObject.isDirectory()) {
			indent = getIndent(fileObject);
			if (show)
				System.out.println(indent + fileObject.getName());

			File allFiles[] = fileObject.listFiles();
			for (File aFile : allFiles) {
				recursiveTraversal(aFile);
			}
		} else if (fileObject.isFile()) {
			if (extension == null || FileUtils.getFileExtension(fileObject.getName()).equals(extension)) {
				if (show)
					System.out.println(indent + "  " + fileObject.getName());
				files.add(fileObject);
			}
		}
	}

	private String getIndent(File fileObject) {
		
		String original = originalFileObject.getAbsolutePath();
		String fileStr = fileObject.getAbsolutePath();
		String subString = fileStr.substring(original.length(), fileStr.length());

		String indent = "";
		for (int index = 0; index < subString.length(); index++) {
			char aChar = subString.charAt(index);
			if (aChar == File.separatorChar) {
				indent = indent + "  ";
			}
		}
		return indent;
	}

	public List<File> getFiles() {
		return files;
	}
}

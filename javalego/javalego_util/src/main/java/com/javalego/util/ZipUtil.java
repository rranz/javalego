package com.javalego.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Clase que conmprime y descomprime zip o arrays de bytes
 * 
 * <p>
 * <blockquote>
 * 
 * <pre>
 * Ejemplo práctico:
 *   
 *  ZipUtil zi=new ZipUtil();
 * 	String fuente=&quot;c:\\cosas\\infoval.zip&quot;;
 * 	String destino=&quot;c:\\cosas&quot;;
 * 	try{
 * 		zi.unZip(fuente,destino);	
 * 		}
 * 	catch(Exception e)
 * 		{
 * 		System.out.println(&quot;Error al descomprimir: &quot;+e.getMessage());
 * 		}
 * 	String f1=&quot;C:\\cosas\\bancario.csv&quot;;
 * 	String f2=&quot;c:\\cosas\\gubernamental.csv&quot;;
 * 	String zip=&quot;c:\\cosas\\mizipjava.zip&quot;;
 * 	ArrayList l=new ArrayList(2);
 * 	l.add(f1);
 * 	l.add(f2);	
 * 	try {
 * 		zi.zip(zip,l);
 * 		} 
 * 	catch (Exception e) 
 * 		{
 * 		System.out.println(&quot;Error al comprimir: &quot;+e.getMessage());
 * 		}
 * 	}
 * </pre>
 * 
 * </blockquote>
 * <p>
 * 
 * @author JOSE MARIA BARRIGA
 */
public class ZipUtil {

	private static final int BUFFER_SIZE = 1024;

	/**
	 * Comprimir un array de bytes
	 * 
	 * @param data
	 * @return
	 */
	static public byte[] compress(byte[] data) throws Exception {

		// Create the compressor with highest level of compression
		Deflater compressor = new Deflater();

		compressor.setLevel(Deflater.BEST_COMPRESSION);

		// Give the compressor the data to compress
		compressor.setInput(data);
		compressor.finish();

		// Create an expandable byte array to hold the compressed data.
		// You cannot use an array that's the same size as the orginal because
		// there is no guarantee that the compressed data will be smaller than
		// the uncompressed data.
		ByteArrayOutputStream bos = new ByteArrayOutputStream(data.length);

		// Compress the data
		byte[] buf = new byte[1024];
		while (!compressor.finished()) {
			int count = compressor.deflate(buf);
			bos.write(buf, 0, count);
		}
		bos.close();
		// Get the compressed data
		return bos.toByteArray();
	}

	/**
	 * Descomprimir un array de bytes
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	static public byte[] decompress(byte[] data) throws Exception {

		// Create the decompressor and give it the data to compress
		Inflater decompressor = new Inflater();
		decompressor.setInput(data);

		// Create an expandable byte array to hold the decompressed data
		ByteArrayOutputStream bos = new ByteArrayOutputStream(data.length);

		// Decompress the data
		byte[] buf = new byte[1024];
		while (!decompressor.finished()) {
			int count = decompressor.inflate(buf);
			bos.write(buf, 0, count);
		}
		bos.close();

		// Get the decompressed data
		return bos.toByteArray();
	}

	/**
	 * descomprime un zip en una carpeta especificada.
	 */
	static public void unZip(String zip, String folder) throws Exception {
		
		try {
			BufferedOutputStream dest = null;
			
			FileInputStream fis = new FileInputStream(zip);
			
			ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
			
			int count;
			
			byte data[] = new byte[BUFFER_SIZE];
			
			ZipEntry entry;
			
			while ((entry = zis.getNextEntry()) != null) {
			
				if (!entry.isDirectory()) {
					
					String destFN = folder + System.getProperty("file.separator") + entry.getName();
					
					FileOutputStream fos = new FileOutputStream(destFN);
					
					dest = new BufferedOutputStream(fos, BUFFER_SIZE);
					
					while ((count = zis.read(data, 0, BUFFER_SIZE)) != -1) {
						dest.write(data, 0, count);
					}
					dest.flush();
					dest.close();
				}
			}
			zis.close();
		} catch (Exception e) {
			throw e;
		}
	}

	/*
	 * Comprime en un zip una serie de archivos cuyo nombre esta especificado en
	 * una List.
	 */
	static public void zip(String zip, List<String> files) throws Exception {
		try {
			
			BufferedInputStream origin = null;
			
			FileOutputStream dest = new FileOutputStream(zip);
			
			ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));
			
			byte[] data = new byte[BUFFER_SIZE];
			
			for (String filename : files) {
				
				FileInputStream fi = new FileInputStream(filename);
				
				origin = new BufferedInputStream(fi, BUFFER_SIZE);
				
				ZipEntry entry = new ZipEntry(filename);
				
				out.putNextEntry(entry);
				
				int count;
				while ((count = origin.read(data, 0, BUFFER_SIZE)) != -1) {
					out.write(data, 0, count);
				}
				origin.close();
			}
			out.close();
		} catch (Exception e) {
			throw e;
		}
	}
}

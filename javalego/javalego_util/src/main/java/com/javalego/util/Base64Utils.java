package com.javalego.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * Cifrado de textos
 * @author ROBERTO RANZ
 */
@SuppressWarnings("restriction")
public class Base64Utils {

	//public final static String clave = "3asdf4FBas12";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {		

//		if (args.length != 1 && args.length != 2){
//			System.out.println("Error: Número de argumentos erróneo\n");			
//			System.out.println("1 - Argumento: Debe de poner el texto a cifrar");
//								
//		}
//		else {
//      if (args.length==2) {
        final String resultado64d = decode("Q1NYUTEADVE=", "3234Aaf4");
        System.out.println("Texto Descifrado: " + resultado64d);
        final String resultado64c = encode("ROBERTO", "3asdf4FBas12");
        System.out.println("Texto Cifrado: " + resultado64c);
//      } else {
//  			final String resultado64 = cifrar(args[0]);
//  			System.out.println("Texto Cifrado: " + resultado64);
//      }
//		}
	}
	
	/**
	 * Cifrar texto pasado como parámetro.
	 * @param text
	 * @return
	 */
	public static String encode(String text, String key) {
		
		final byte[] textoBytes = text.getBytes();
		final byte[] claveBytes = key.getBytes();		
		final int longitudTexto = textoBytes.length;
		final int longitudClave = claveBytes.length;
		byte[] resultado;
		resultado = new byte[longitudTexto];
		int j = 0;
		for (int i=0;i<longitudTexto;i++){
			if (j<longitudClave){
				resultado[i]=(byte) (textoBytes[i]^claveBytes[j]);
				j=j+1;
			}
			if (j==longitudClave){
				j=0;
			}
		}		
		final BASE64Encoder enc64 = new BASE64Encoder();		
		return enc64.encode(resultado);
	}
	
	/**
	 * Descifrar texto pasado como parámetro.
	 * @param text
	 * @return
	 */
	public static String decode(String text, String key) {	
		try{
			final BASE64Decoder dec64 = new BASE64Decoder();
			final byte[] textoBytes = dec64.decodeBuffer(text);		
			final byte[] claveBytes = key.getBytes();		
			final int longitudTexto = textoBytes.length;
			final int longitudClave = claveBytes.length;
			byte[] resultado;
			resultado = new byte[longitudTexto];
			int j = 0;
			for (int i=0;i<longitudTexto;i++){
				if (j<longitudClave){
					resultado[i]=(byte) (textoBytes[i]^claveBytes[j]);
					j=j+1;
				}
				if (j==longitudClave){
					j=0;
				}
			}
			return new String(resultado);						
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}		
	}
	
}

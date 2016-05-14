package com.javalego.util;

import java.math.BigDecimal;
import java.text.Collator;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.javalego.config.EnvironmentVariables;

/**
 * Funciones de utilidad tipo String
 * @author ROBERTO RANZ
 */
public final class StringUtils {

	static final String FIELD_SEPARATOR = "\n";

	static public String getDefaultSeparator() {
		return FIELD_SEPARATOR;
	}

	/**
	 * Obtener el texto del número Romano desde un valor entero.
	 * @param i
	 * @return
	 */
	public static String toRoman(int i) {
		if(i>=1000) return "M" + toRoman (i-1000);
		else if ( i >=900 ) return "CM" + toRoman(i-900);
		else if ( i >=100 ) return "C" + toRoman(i-100);
		else if ( i >=90 ) return "XC" + toRoman(i-90);
		else if (i>=50) return "L" + toRoman(i-50);
		else if (i >= 40) return "XL" + toRoman(i-40);
		else if ( i >= 10) return "X" + toRoman(i-10);
		else if (i == 9 ) return "IX" + toRoman ( i - 9);
		else if ( i >= 5 ) return "V" + toRoman ( i - 5);
		else if ( i == 4 ) return "IV" + toRoman (i - 4 );
		else if ( i > 0 ) return "I" + toRoman(i-1);
		else return "";
 		
	}
	
	/**
	 * Comprueba si un valor se encuentra en el array de valores.
	 * 
	 * @param values
	 * @param value
	 * @return
	 */
	static public boolean existInArrayString(String[] values, String value) {

		if (values == null)
			return false;

		for (int i = 0; i < values.length; i++) {
			if (values[i].equals(value))
				return true;
		}
		return false;
	}

	/**
	 * Comprueba si un valor se encuentra en el array de objetos.
	 * 
	 * @param values
	 * @param value
	 * @return
	 */
	static public boolean existInArrayObjects(Object[] values, String value) {

		if (values == null)
			return false;

		for (int i = 0; i < values.length; i++) {
			if (values[i] != null && values[i].equals(value))
				return true;
		}
		return false;
	}

	/**
	 * Comprueba si un valor se encuentra en el array de valores.
	 * 
	 * @param values
	 * @param value
	 * @return
	 */
	static public boolean existInSplitValues(String values, String value) {

		if (values == null)
			return false;

		String[] items = values.split("\\|");

		for (String item : items) {
			if (item.equals(value))
				return true;
		}
		return false;
	}

	/**
	 * Devuelve la posición de un valor dentro de un array de valores.
	 * 
	 * @param values
	 * @param value
	 * @return
	 */
	static public int getIndexInArrayString(String[] values, String value) {

		if (values == null)
			return -1;
		
		for (int i = 0; i < values.length; i++) {
			if (values[i].equals(value))
				return i;
		}
		return -1;
	}

	/**
	 * Obtiene la posición de un valor que se encuentra en el array de valores.
	 * 
	 * @param values
	 * @param value
	 * @return
	 */
	static public int indexOfInArrayString(String[] values, String value) {

		if (values == null || values.length == 0)
			return -1;
		
		for (int i = 0; i < values.length; i++) {
			if (values[i].equals(value))
				return i;
		}
		return -1;
	}

	@SuppressWarnings("rawtypes")
	static public String listToString(List value, String fieldSeparator) {

		String out = "";

		if (value == null || value.size() == 0)
			return null;
		
		if (null == fieldSeparator)
			fieldSeparator = FIELD_SEPARATOR;

		for (int i = 0; i < value.size(); i++)
			out += ((0 == i) ? "" : fieldSeparator) + value.get(i).toString();

		return out;
	}

	static public String arrayToString(Object[] value, String fieldSeparator) {

		String out = "";

		if (value == null || value.length == 0)
			return null;
		
		if (null == fieldSeparator)
			fieldSeparator = FIELD_SEPARATOR;

		for (int i = 0; i < value.length; i++)
			out += ((0 == i) ? "" : fieldSeparator) + value[i].toString();

		return out;
	}

	static public String arrayToString(List<?> values, String fieldSeparator) {

		String out = "";

		if (values == null || values.size() == 0)
			return null;
		
		if (null == fieldSeparator)
			fieldSeparator = FIELD_SEPARATOR;

		for (int i = 0; i < values.size(); i++)
			if (values.get(i) != null)
				out += ((0 == i) ? "" : fieldSeparator) + values.get(i).toString();

		return out;
	}

	@SuppressWarnings("rawtypes")
	static public String castToString(Object value, String fieldSeparator) {

		String out = "";

		if (value == null)
			return null;
		
		if (value instanceof List)
			out = listToString((List) value, fieldSeparator);
		else if (value instanceof Object[])
			out = arrayToString((Object[]) value, fieldSeparator);
		else
			out = value.toString();

		return out;
	}

	@SuppressWarnings("rawtypes")
	static public String castToString(Object value) {

		String out = "";

		if (value == null)
			return null;
		
		if (value instanceof List)
			out = listToString((List) value, FIELD_SEPARATOR);
		else if (value instanceof Object[])
			out = arrayToString((Object[]) value, FIELD_SEPARATOR);
		else
			out = value.toString();

		return out;
	}

	/**
	 * Sustituye el carácter \n incluido en una cadena de caracteres,
	 * reconstruyendo la cadena poniendo explícitamente el carácter \n ya que de
	 * no hacerlo, los componentes visuales no interpretan este carácter y lo
	 * muestran en pantalla.
	 * 
	 * @param value
	 * @return
	 */
	static public String splitStringCarriageReturn(String value) {

		String find = value;
		
		if (value == null)
			return null;
		
		String result = "";
		
		while (find.indexOf("\\n") > -1) {
			result += find.substring(0, find.indexOf("\\n")) + "\n";
			find = find.substring(find.indexOf("\\n") + 2);
		}
		result += find;

		return result;
	}

	/**
	 * Crear una tooltip multilínea mediante html.
	 * 
	 * @param characters
	 * @return
	 */
	static public String getToolTipWithCarriageReturn(int characters, String message) {

		if (message.length() > characters) {
			String newMessage = "<html><font face='" + EnvironmentVariables.getDefaultFontName() + "'>";
			while (message.length() > characters) {
				String m = readWords(message, characters);
				newMessage += m + "<br>";
				message = message.substring(m.length());
			}
			message = newMessage + message;
			message += "</font></html>";
		}
		return message;
	}

	/**
	 * Crear texto html creando varias líneas dependiendo de un número máximo de
	 * caracteres por línea.
	 * 
	 * @param characters
	 * @param message 
	 * @param header incluir tag html al inicio y fin del valor.
	 * @return
	 */
	static public String getHtmlCarriageReturn(int characters, String message, boolean header) {

		if (message == null)
			return "";
		
		if (message.length() > characters) {
			String newMessage = header ? "<html>" : "";
			while (message.length() > characters) {
				String m = readWords(message, characters);
				newMessage += m + "<br>";
				message = message.substring(m.length() < 1 ? characters : m.length());
			}
			message = newMessage + message + (header ? "</html>" : "");
		}
		else if (header)
			message = "<html>" + message + "</html>";
		
		return message;
	}

	/**
	 * Crear texto creando varias líneas dependiendo de un número máximo de
	 * caracteres por línea.
	 * @param characters
	 * @return
	 */
	static public String getCarriageReturn(int characters, String message) {

		if (message.length() > characters) {
			String newMessage = "";
			while (message.length() > characters) {
				String m = readWords(message, characters);
				newMessage += m + "\n";
				message = message.substring(m.length());
			}
			message = newMessage + message;
		}
		return message;
	}

	static private String readWords(String message, int characters) {
		int pos = -1;
		while (message.indexOf(" ", pos + 1) > -1 && pos <= characters) {
			pos = message.indexOf(" ", pos + 1);
		}
		return pos < 1 ? message.length() >= characters ? message.substring(0, characters) : message : message.substring(0, pos);
	}

	/**
	 * Rellenar un string con caracteres por la izquierda hasta completar un
	 * longitud dada.
	 * 
	 * @param value
	 * @param character
	 * @param length
	 * @return
	 */
	static public String fillCharacterLeft(String value, char character, int length) {

		if (value == null)
			return value;
		else if (Character.getNumericValue(character) > -1 && length > 0 && value.length() < length && !value.equals(""))
			return replicate(new String(Character.toString(character)), length - value.length()) + value;
		else
			return value;

	}

	/**
	 * Rellenar un string con caracteres por la derecha hasta completar un
	 * longitud dada.
	 * 
	 * @param value
	 * @param character
	 * @param length
	 * @return
	 */
	static public String fillCharacterRight(String value, char character, int length) {

		if (Character.getNumericValue(character) > -1 && length > 0 && value.length() < length && !value.equals(""))
			return value + replicate(new String(Character.toString(character)), length - value.length());
		else
			return value;

	}

	/**
	 * Validar un Nif pasado como parámetro.
	 * 
	 * @param nif
	 * @return
	 */
	static public boolean validateNIF(String nif) {

		String p = "TRWAGMYFPDXBNJZSQVHLCKE";

		if (!isEmpty(nif)) {

			String value = nif;

			// K,L y M se aplicaríe este valor = 0.
			if (nif.substring(0, 1).equals("X"))
				value = "0" + nif.substring(1);

			else if (nif.substring(0, 1).equals("Y"))
				value = "1" + nif.substring(1);

			else if (nif.substring(0, 1).equals("Z"))
				value = "2" + nif.substring(1);

			// NIE
//			if (value.subSequence(0, 1).equals("X") || value.subSequence(0, 1).equals("Y") || value.subSequence(0, 1).equals("Z")) {
//				value = value.substring(1);
//			}

			// NIF
			if (value.length() == 9) {
				int value2 = 0;
				try {
					value2 = new Integer(value.substring(0, 8)).intValue();
				} catch (Exception e) {
					return false;
				}
				int resto = value2 % 23;
				return (value.substring(8, 9).charAt(0) == p.charAt(resto));
			} else
				return false;
		}
		return false;
	}

	
	/**
	 * Adaptar el valor de nif para que sea válido. El sistema podrá añadir ceros
	 * por la izquierda, la letra final, etc.
	 * 
	 * @param nif
	 * @return
	 */
	static public String nifAdapter(String nif) {

		if (isEmpty(nif))
			return nif;

		if (nif != null)
			nif = nif.trim();

		// Rellenar de ceros por la izquiera a 8 caracteres.
		if (nif.substring(0, 1).equals("X")) {
			if (nif.length() > 9) 
				nif = "X" + nif.substring(nif.length()-8);
			else
				nif = "X" + StringUtils.fillCharacterLeft(nif.substring(1), '0', 7);
		}
		else if (nif.substring(0, 1).equals("Y")) {
			if (nif.length() > 9) 
				nif = "Y" + nif.substring(nif.length()-8);
			else
				nif = "Y" + StringUtils.fillCharacterLeft(nif.substring(1), '0', 7);
		}
		else if (nif.substring(0, 1).equals("Z")) {
			if (nif.length() > 9) 
				nif = "Z" + nif.substring(nif.length()-8);
			else
				nif = "Z" + StringUtils.fillCharacterLeft(nif.substring(1), '0', 7);
		}
		else
			nif = StringUtils.fillCharacterLeft(nif, '0', StringUtils.isOnlyNumbers(nif) ? 8 : 9);

		// Insertar la letra del Nif.
		if (nif.length() == 8)
			nif = insertLetter(nif);
		else if (nif.length() > 9)
			nif = nif.substring(nif.length()-9);

		return nif;
	}

	/**
	 * Insertar letra.
	 */
	static private String insertLetter(String nif) {

		String sValue = nif;

		//String nie = "";
		if (!nif.equals("")) {
			if (nif.substring(0, 1).equals("X") || nif.substring(0, 1).equals("Y") || nif.substring(0, 1).equals("Z"))
				; //nie = nif.substring(0, 1);
			else if (!Character.isDigit(nif.substring(0, 1).charAt(0))) {
				return nif;
			}
		}

		if (isEmpty(sValue))
			return nif;

		boolean endsWithLetter = Character.isUpperCase(sValue.charAt(sValue.length() - 1));
		String root = "";
		if (endsWithLetter) {
			root = sValue.substring(0, sValue.length() - 1);
		} else {
			root = sValue;
		}
		if (root.length() < 8) {
			return nif;
		}
		String letter = "";
//		if (nie.equals("X") || nie.equals("Y") || nie.equals("Z"))
//			letter = JCIF_NIF.obtainNIFLetter( (nie.equals("X") ? "0" : nie.equals("Y") ? "1" : "2") + root.substring(1));
//		else
//			letter = JCIF_NIF.obtainNIFLetter(root);

		if (letter != null) {
			String dni = root + (letter.length() > 1 ? "" : letter);
			nif = dni;
		}

		return nif;
	}

	/**
	 * Código nacional de identificación de seguridad social. Cálculo de
	 * validación: El código NISS se trata de una cifra de 11 dígitos para el caso
	 * de las cuentas de cotización de empresas, y de 12 dígitos en el caso del
	 * número de un trabajador. Los dos últimos dígitos constituyen los dígitos de
	 * control. Estas dos ultimas cifras del número completo, debe ser el resto de
	 * dividir todo el número exceptuando los dos últimos dígitos, que son los de
	 * control, entre 97.
	 * 
	 * @param nass
	 * @return
	 */
	static public boolean validateNASS(String nass) {

		if (isEmpty(nass))
			return true;

		long value = new Long(nass).longValue();

		if (value < 1 || nass.length() < 10)
			return false;

		long control = new Long(nass.substring(nass.length() - 2)).longValue();

		long code = getCodeControlNASS(nass);

		return code % 97 == control;
	}

	/**
	 * Código válido de control del NISS
	 * 
	 * @param nass
	 * @return
	 */
	static public String getCodeNASS(String nass) {

		if (isEmpty(nass))
			return null;

		long value = new Long(nass).longValue();

		if (value < 1 || nass.length() < 10)
			return null;

		long code = getCodeControlNASS(nass);

		return StringUtils.fillCharacterLeft(new Long(code % 97).toString(), '0', 2);
	}

	/**
	 * Obtener el código nass
	 * 
	 * @param nass
	 * @return
	 */
	private static long getCodeControlNASS(String nass) {

		long code = 0;

		long codprov = new Long(nass.substring(0, 2)).longValue();
		long central = new Long(nass.substring(2, nass.length() - 2)).longValue();

		// Sólo para las personas
		if (nass.substring(2, nass.length() - 2).length() == 8 && central < 10000000)
			code = central + codprov * 10000000;
		else if (nass.substring(2, nass.length() - 2).length() == 7 && central < 1000000)
			code = central + codprov * 1000000;
		else
			code = new Long(nass.substring(0, nass.length() - 2)).longValue();

		return code;
	}

	public static void main(String args[]) {
		System.out.println(StringUtils.capitalize("roberto ranz")); //fillCharacterLeft("1234567890", '0', 11));
		//System.out.println(validateNIF("X0011406K"));

		
		//System.out.println(validateCIF("N0171057C"));
	}

	/**
	 * Método que comprueba que el cif exista
	 * 
	 * @param pCif
	 *          cif obtenido en el formulario
	 * @param mensaje
	 *          string con el mensaje que se ha de mostrar en caso de error
	 * @return boolean En función del resultado, retorna true si se cumple o false
	 *         si la comprobación es incorrecta
	 */
	public static boolean validateCIF(String pCif) {
		
		// Si el cif == nif validar el cif.
		if (StringUtils.validateNIF(pCif))
			return true;
		
		if (validateCIF2(pCif))
			return true;
		
		try {
			// Se formatea
			String checkCif = pCif.replaceAll("-", "").toUpperCase().trim();
			//String cif = checkCif.trim();
			//checkCif = pCif.trim(); // tiene guion
			// longitud del cif con guion
			int longCif = checkCif.length();
			// inicializo la longitud del cif limpio
			int longitud_cif = 0;
			if (longCif > 0) {
				// letras validas
				String letras_validas = "[ABCDEFGHJNPQRSUVWabcdefghpqs]";
				// primera letara
				String inicio = checkCif.substring(0, 1);
				Pattern patron = null;
				Matcher coincidencias = null;
				patron = Pattern.compile(letras_validas);// expresion_izq
				coincidencias = patron.matcher(inicio);
				if (coincidencias.find()) {
					// saca ultimo caracter
					String fin = checkCif.substring(longCif - 1);
					coincidencias = patron.matcher(fin);
					// para comprobar si es nacional
					String numeros_validos = "[0-9]";
					Pattern patron_nacional = Pattern.compile(numeros_validos);
					Matcher coinc_nacional = patron_nacional.matcher(fin);
					// variable que controla si es nacional o extranjero
					String control;
					// compruebo si cif es extranjero
					if (coincidencias.find()) {
						control = "extranjero";
					}
					// comprueba si cif es nacional
					else if (coinc_nacional.find()) {
						control = "nacional";
					} else {// si no es nacional ni extranjero{
						return false;
					}// cierra else nac o ext
					if (control.equals("extranjero") || control.equals("nacional")) {
						// comprueba si hay dos letras seguidas al principio X-Y99999999
						String letra_comprobar;
						String letras = "[a-zA-Z]";
						Pattern pat = Pattern.compile(letras);
						letra_comprobar = checkCif.substring(2, 2);
						Matcher coinc = pat.matcher(letra_comprobar);
						if (coinc.find()) {
							return false;
						} else {
							String numeroCif = "";
							checkCif = checkCif.replaceAll("-", "").toUpperCase().trim();
							longitud_cif = checkCif.length();
							// si es extranjero (X-9999999X) X9999999X
							if (control.equals("extranjero")) {
								numeroCif = checkCif.substring(1, longitud_cif - 1);
							}
							// si es nacional
							if (control.equals("nacional")) {
								numeroCif = checkCif.substring(1, longitud_cif - 1);
							}
							longitud_cif = numeroCif.length();
							// ALGORITMO
							int total = 0;
							int indice = 15;
							int resto = 0;
							String aux = numeroCif;
							// desde
							for (int i = longitud_cif; i <= indice - 1; i++) {
								aux = "0" + aux;
							}
							// hacemos un loop mientras el indice sea mayor que 0
							while (indice > 0) {
								// obtengo el valor entero de la posicion 15
								int cadena = new Integer(aux.substring(indice - 1, indice)).intValue();
								int suma = cadena * 2;
								if (suma >= 10) {
									resto = suma % 10;
									int cociente = suma / 10;
									suma = cociente + resto;
								}
								if (indice > 1) {
									int indice2 = indice - 1;
									int caracter = (new Integer(aux.substring(indice2 - 1, indice2))).intValue();
									total = total + suma + caracter;
								}
								indice = indice - 2;
							}// cierra while
							resto = total % 10;
							// digito de control
							int digito_control = 10 - resto;
							// saco el ultimo caracter de digito de control
							String digito = (new Integer(digito_control).toString()).substring((new Integer(digito_control).toString()).length() - 1);
							// guardo en digito_control el digito pasado a entero
							digito_control = new Integer(digito).intValue();
							// si el cif es extranjero se saca del listado de letras la de la
							// posicion del digito de control+1. Si coincide con la ultima letra
							// es correcto
							if (control.equals("extranjero")) {
								String expresion = "JABCDEFGHI";
								String comprueba = expresion.substring(digito_control, digito_control + 1);
								if (comprueba.equals(fin))// "letra_ultima"
									return true;
	
								return false;
							}
							// si el cif es nacional se comprueba que el digito de control
							// coincida con el ultimo digito del cif. Si es asi el cif es
							// correcto
							if (control.equals("nacional")) {
								// paso letra_ultima a entero
								int letra = new Integer(fin).intValue();// letra_ultima
								if (digito_control == letra) {
									return true;
								}
								return false;
							}
						}// cierra else
					}// cierra if control
					return true;
				}// cierra if coincidencias find
				else {
					return false;
				}
			} else {
				return false;
			}
		}
		catch(Exception e) {
			return false;
		}
	}

	/**
	 * Validar CIF
	 * 
	 * @param cif
	 * @return
	 */
	private static boolean validateCIF2(String cif) {
		
		Pattern cifPattern = Pattern.compile("([ABCDEFGHKLMNPQRSUVWSabcdefghklmnpqs])(\\d)(\\d)(\\d)(\\d)(\\d)(\\d)(\\d)([abcdefghijABCDEFGHIJ0123456789])");

		Matcher m = cifPattern.matcher(cif);
		if (m.matches()) {

			// Sumamos las posiciones pares de los números centrales (en realidad
			// posiciones 3,5,7 generales)
			int sumaPar = Integer.parseInt(m.group(3)) + Integer.parseInt(m.group(5)) + Integer.parseInt(m.group(7));

			// Multiplicamos por 2 las posiciones impares de los números centrales (en
			// realidad posiciones 2,4,6,8 generales)
			// Y sumamos ambos digitos: el primer digito sale al dividir por 10 (es un
			// entero y quedará 0 o 1)
			// El segundo dígito sale de modulo 10
			int sumaDigito2 = ((Integer.parseInt(m.group(2)) * 2) % 10) + ((Integer.parseInt(m.group(2)) * 2) / 10);
			int sumaDigito4 = ((Integer.parseInt(m.group(4)) * 2) % 10) + ((Integer.parseInt(m.group(4)) * 2) / 10);
			int sumaDigito6 = ((Integer.parseInt(m.group(6)) * 2) % 10) + ((Integer.parseInt(m.group(6)) * 2) / 10);
			int sumaDigito8 = ((Integer.parseInt(m.group(8)) * 2) % 10) + ((Integer.parseInt(m.group(8)) * 2) / 10);

			int sumaImpar = sumaDigito2 + sumaDigito4 + sumaDigito6 + sumaDigito8;

			int suma = sumaPar + sumaImpar;
			int control = 10 - (suma % 10);
			// La cadena comienza en el caracter 0, J es 0, no 10
			if (control == 10)
				control = 0;

			String letras = "JABCDEFGHI";

			// El dígito de control es una letra
			if (m.group(1).equalsIgnoreCase("K") || m.group(1).equalsIgnoreCase("P") || m.group(1).equalsIgnoreCase("Q") || m.group(1).equalsIgnoreCase("S")) {

				if (m.group(9).equalsIgnoreCase(letras.substring(control, control + 1)))
					return true;
				else
					return false;
			}
			// El dígito de control es un número
			else if (m.group(1).equalsIgnoreCase("A") || m.group(1).equalsIgnoreCase("B") || m.group(1).equalsIgnoreCase("E") || m.group(1).equalsIgnoreCase("H")) {

				if (m.group(9).equalsIgnoreCase("" + control))
					return true;
				else
					return false;
			}
			// El dígito de control puede ser un número o una letra
			else {
				if (m.group(9).equalsIgnoreCase(letras.substring(control, control + 1)) || m.group(9).equalsIgnoreCase("" + control))
					return true;
				else
					return false;
			}
		} else
			return false;
	}

	/**
	 * Encriptar un string en base 64.
	 * 
	 * @param value
	 * @return
	 */
	static public String base64Encoder(String value) {
		if (isEmpty(value))
			return null;
		else
			return Base64Utils.encode(value, "3234Aaf4");
	}

	/**
	 * Desencriptar un string en base 64.
	 * 
	 * @param value
	 * @return
	 */
	static public String base64Decoder(String value) throws Exception {
		if (isEmpty(value))
			return null;
		else {
			return Base64Utils.decode(value, "3234Aaf4");
//			sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
//			return new String(decoder.decodeBuffer(value));
		}
	}

	/**
	 * Obtener una array de String desde un String buscando por un carácter que
	 * separe sus valores.
	 * 
	 * @param value
	 * @param separator
	 * @return
	 */
	static public String[] getArrayFromStringSplit(String value, String separator) {
		
		if (value != null && value.equals(""))
			return null;
		else if (value != null) 
			return value.split(separator);
		else
			return null;
	}

	/**
	 * Obtener una array de String desde un String buscando por un carácter | para
	 * separar sus valores.
	 * 
	 * @param value
	 * @param separator
	 * @return
	 */
	static public String[] getArrayFromStringSplitPipe(String value) {

		if (value != null && value.equals(""))
			return null;
		else if (value != null) 
			return value.split("\\|");
		else
			return null;
	}

	/**
	 * Obtener una array de String desde un String buscando por un carácter | para
	 * separar sus valores.
	 * 
	 * @param value
	 * @param separator
	 * @param count número de elementos que tenemos que generar. Si el valor no contiene todos estos elementos, se generarán en base al valor del primero.
	 * @return
	 */
	static public String[] getArrayFromStringSplitPipe(String value, int count) {

		if (value != null) { 
			String[] items = value.split("\\|");
			if (items.length < count) {
				int total = count - items.length;
				Object item = items.length == 0 ? null : items[0];
				for(int i = 0; i < total; i++) {
					items = (String[])ArrayUtils.add(items, item);
				}
			}
			return items;
		}
		else
			return null;
	}

	/**
	 * Obtener un string con valores separados por el caracter paid desde un array de valores.
	 * separar sus valores.
	 * 
	 * @param value
	 * @param separator
	 * @return
	 */
	static public String getStringFromArraySplitPipe(String[] values) {

		if (values == null || values.length == 0)
			return null;
		else {
			String text = "";
			for(String item : values) {
				if (item != null)
					text += (text.equals("") ? "" : "|") + item.trim();
			} 
			return text;
		}
	}

	/**
	 * Obtener una array de String desde un String buscando por un carácter | para
	 * separar sus valores.
	 * 
	 * @param value
	 * @param separator
	 * @return
	 */
	static public Object[] getArrayFromStringSplitPipe(Object[] values) {

		// Utilizado para asignar el valor de la propiedad mediante Reflection.
		if (values != null && values.length == 1 && values[0] instanceof String)
			return getArrayFromStringSplitPipe(values[0].toString());
		else
			return values;
	}

	/**
	 * Obtener una array de String desde un String buscando por un carácter | para
	 * separar sus valores.
	 * 
	 * @param value
	 * @param separator
	 * @return
	 */
	static public String[] getArrayFromStringSplitPipe(String[] values) {

		// Utilizado para asignar el valor de la propiedad mediante Reflection.
		if (values != null && values.length == 1 && values[0] instanceof String)
			return getArrayFromStringSplitPipe(values[0].toString());
		else
			return values;
	}

	/**
	 * Devuelve una cadena formada de un número de iteraciones de un valor.
	 * 
	 * @param value
	 * @param num
	 * @return String
	 */
	static public String replicate(String value, int num) {
		String result = "";
		for (int i = 0; i < num; i++)
			result += value;
		return result;
	}

	/**
	 * Eliminar acentos de una cadena de caracteres. Sólo en letras mayúsculas
	 * excepto que el parámetro lowercase = true.
	 * 
	 * @param value
	 * @return
	 */
	public static String clearAccents(String value, boolean lowercase) {

		if (value != null) {

			value = value.replace('Á', 'A');
			value = value.replace('É', 'E');
			value = value.replace('Í', 'I');
			value = value.replace('Ó', 'O');
			value = value.replace('Ú', 'U');

			value = value.replace('À', 'A');
			value = value.replace('È', 'E');
			value = value.replace('Ì', 'I');
			value = value.replace('Ò', 'O');
			value = value.replace('Ù', 'U');

			if (lowercase) {
				value = value.replace('á', 'a');
				value = value.replace('é', 'e');
				value = value.replace('í', 'i');
				value = value.replace('ó', 'o');
				value = value.replace('ú', 'u');

				value = value.replace('à', 'A');
				value = value.replace('è', 'E');
				value = value.replace('ì', 'I');
				value = value.replace('ò', 'O');
				value = value.replace('ù', 'U');
			}

			return value;
		} else
			return value;
	}

	/**
	 * Obtener el valor de un boolean en texto (idioma Español).
	 * 
	 * @param value
	 * @return
	 */
	public static String getTextBoolean(boolean value) {
		return value ? "YES" : "NO";
	}

	/**
	 * Comprueba si el texto sólo tiene números
	 * 
	 * @param text
	 * @return
	 */
	public static boolean isOnlyNumbers(String text) {

		if (text != null) {
			for (Character c : text.toCharArray()) {
				if (!Character.isDigit(c.charValue()))
					return false;
			}
			return true;
		} else
			return false;
	}

	/**
	 * Convertir un número a un string
	 * 
	 * @param value
	 * @return
	 */
	public static String getNumberToString(Number value) {

		NumberFormat nf = NumberFormat.getInstance();
		nf.setGroupingUsed(false);
		return nf.format(value);
	}

	public static String bytes2MBytes(long nBytes) {
		long MBytes = nBytes >> 10 >> 10;
		return MBytes > 0 ? Long.toString(MBytes) : "0";
	}

	/**
	 * Transformar una lista de valores separados por el carácter | en una cadena de texto de valores separada por 'valor1','valor2'.
	 * @param values
	 * @return
	 */
	public static String getStringsPipeToQuotes(String values) {
		
		if (values == null)
			return null;
		
		String[] items = values.split("\\|");
		
		String value = "";
		
		for(String item : items) {
			value += (value.equals("") ? "" : ", ") + "'" + item + "'";
		}

		return value;
	}
	
	/**
	 * Ordenar una array de valores.
	 * @param collator
	 * @param strArray
	 */
	public static void sortArray(Collator collator, String[] strArray) {
		String tmp;
		if (strArray.length == 1)
			return;
		for (int i = 0; i < strArray.length; i++) {
			for (int j = i + 1; j < strArray.length; j++) {
				if (collator.compare(strArray[i], strArray[j]) > 0) {
					tmp = strArray[i];
					strArray[i] = strArray[j];
					strArray[j] = tmp;
				}
			}
		}
	}
	
	/**
	 * Obtener una valor String de un objeto adaptándolo de forma correcta en sus diversas tipologías existentes. 
	 * @param value
	 * @return
	 */
	public static String getStringFromValue(Object value) {
		
		if (value instanceof Date)
			return DateUtils.getDateToYYYYMMDD((Date)value);
		else
			return value != null ? value.toString() : null;
			
	}

	/**
	 * Comprueba si un String contiene sólo un único caracter.
	 * @param charecter
	 * @param propertyNames
	 * @return
	 */
	public static boolean hasSameCharacter(char character, String value) {
		
		if (value != null) {
			for(char c : value.toCharArray()) {
				if (c != character) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	/**
	 * Obtener espacios en blanco en Html y XML
	 * @param count
	 * @return
	 */
	public static String getHtmlSpaces(int count) {
		return replicate("&nbsp;", count);
	}

	/**
	 * Capitalizar string.
	 * @param name
	 * @return
	 */
	public static String capitalize(String value) {
		
	  final StringTokenizer st = new StringTokenizer( value, " ", true );
    final StringBuilder sb = new StringBuilder();
     
    while ( st.hasMoreTokens() ) {
        String token = st.nextToken();
        token = String.format( "%s%s",
                                Character.toUpperCase(token.charAt(0)),
                                token.substring(1) );
        sb.append( token );
    }
    return sb.toString();
	}

	/**
	 * Obtener una cadena de caracteres separada por el carácter | de un array de valores.
	 * @param values
	 * @return
	 */
	public static String getSplitValues(Object[] values) {
		
		String text = "";
		if (values != null && values.length > 0) {
			for(Object value : values) {
				text += ("".equals(text) ? "" : "|") + (value != null ? value.toString().trim() : "");
			}
		}
		return text.trim();
	}	

	/**
	 * HTML encodes given string.
	 * 
	 * @param string
	 *          string to encode
	 * @return the encoded string.
	 */
	public static String htmlEncode(final String string) {
	
		final StringBuffer out = new StringBuffer();
		
		for (int i = 0; i < string.length(); i++) {
		
			final char c = string.charAt(i);
			
			if (c > 127 || c == '"' || c == '<' || c == '>') {
				out.append("&#" + (int) c + ";");
			}
			else {
				out.append(c);
			}
		}
		return out.toString();
	}

	/**
	 * Comprueba si un String tiene valor.
	 * 
	 * @param str
	 * @return
	 */
	static public boolean isEmpty(String str) {
		return str == null || str.trim().length() == 0;
	}

	/**
	 * Comprueba si un objeto tiene valor al transformarlo a String.
	 * 
	 * @param str
	 * @return
	 */
	static public boolean isEmpty(Object str) {
		return str == null || str.toString().trim().length() == 0;
	}

	/**
	 * Eliminar un carácter de una cadena.
	 * 
	 * @param oldChar
	 * @return String
	 */
	static public String removeAll(String value, char oldChar) {
		String convert = "";
		for (int i = 0; i < value.length(); i++)
			if (value.substring(i, i + 1).charAt(0) != oldChar)
				convert += value.substring(i, i + 1);
		return convert;
	}
	
	/**
	 * Incluir comillas simples en un String.
	 * 
	 * @param value
	 * @return
	 */
	static public String getSingleQuotedStr(String value) {
		if (value != null)
			return "'" + value + "'";
		else
			return value;
	}

	/**
	 * Pasar a String un valor
	 * 
	 * @param value
	 * @return
	 */
	public static String toString(Object value) {
		if (value == null)
			return null;
		else
			return value.toString();
	}

	/**
	 * Obtiene un String con la lista de valores de un array de objetos
	 * separados por comas.
	 * 
	 * @param values
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	static public String getStringListFromList(List values) {
		String value = "";
		for (int i = 0; i < values.size(); i++)
			value += values.get(i).toString() + (i < values.size() - 1 ? "," : "");
		return value;
	}

	/**
	 * Obtiene el valor double de un String pasado como parámetro.
	 * 
	 * @param value
	 * @return
	 * @throws ParseException
	 */
	static public double getDouble(String value) throws ParseException {

		if (value != null && !value.equals("")) {
			return NumberFormat.getInstance().parse(value).doubleValue();
		}
		else
			return 0;
	}

	/**
	 * Función que devuelve un String de un valor numérico en formato americano.
	 * 
	 * @param value
	 * @return
	 * @throws Exception
	 */
	static public String getDoubleStringEStoUS(Number value) throws Exception {
		DecimalFormatSymbols dfs = new DecimalFormatSymbols(Locale.US);
		DecimalFormat d = new DecimalFormat("################.###", dfs);
		return d.format(value);
	}

	/**
	 * Función que devuelve un String de un valor numérico desde un formato con
	 * un separador de decimales = "," a otro con formato ".".
	 * 
	 * @param value
	 * @param fractionDigits
	 * @param grouping
	 * @return
	 * @throws Exception
	 */
	static public String getDoubleStringEStoUS(String value, int fractionDigits, boolean grouping) throws Exception {

		DecimalFormatSymbols dfs = new DecimalFormatSymbols(Locale.US);
		DecimalFormat d = new DecimalFormat((grouping ? "###,###,###,###,###,###" : "#############") + ".###########", dfs);
		d.setMaximumFractionDigits(fractionDigits);
		return d.format(NumberFormat.getInstance().parse(value));
	}

	/**
	 * Conversión de valores numéricos con formato, americano o español, a su
	 * representación con formato únicamente decimal con separador '.'
	 * 
	 * Se realiza la suposición de que si sólo aparece un caracter este debe ser
	 * el separador decimal. (*)
	 * 
	 * Testeo con los valores :
	 * 
	 * 1) Coma como separador decimal
	 * 
	 * 4 -> 4 4.264 -> 4.264 (*) 4.263.699 -> 4263699 4,2636986301 ->
	 * 4.2636986301 4.263,6986301370 -> 4263.698630137 4.263.698,6301369900 ->
	 * 4263698.63013699 4,26369863E+00 -> 4.26369863E+00 4,26369863E+03 ->
	 * 4.26369863E+03 4,26369863E+06 -> 4.26369863E+06 4,2636986301E+00 ->
	 * 4.2636986301E+00 4,2636986301E-02 -> 4.2636986301E-02 4,2636986301E-04 ->
	 * 4.2636986301E-04
	 * 
	 * 2) Punto como separador decimal
	 * 
	 * 4.2636986301 -> 4.2636986301 4,263.6986301370 -> 4263.698630137
	 * 4,263,698.6301369900 -> 4263698.63013699 4.26369863E+00 -> 4.26369863E+00
	 * 4.26369863E+03 -> 4.26369863E+03 4.26369863E+06 -> 4.26369863E+06
	 * 4.26369863E-02 -> 4.26369863E-02 4.26369863E-04 -> 4.26369863E-04
	 * 
	 * @param numericWithComma
	 * @return Numérico con separador decimal
	 */
	public static String comma2point(String numericWithComma) {
		// patrones
		Pattern p = Pattern.compile("[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?|" + "[-+]?[0-9]*,?[0-9]+([eE][-+]?[0-9]+)?");
		Matcher m = p.matcher(numericWithComma);

		// Captura de los grupos
		ArrayList<Object> partes = new ArrayList<Object>();
		while (m.find()) {
			if (0 == m.group().length())
				continue;
			partes.add(m.group());
		}

		// Sólo la última puede ser el valor decimal
		char point = partes.get(partes.size() - 1).toString().charAt(0);
		boolean isPoint = ('.' == point || ',' == point);

		for (int i = 0; isPoint && (i < partes.size() - 1); i++)
			isPoint &= (-1 == partes.get(i).toString().indexOf(point));

		String numericWithPoint = numericWithComma;
		if (isPoint)
			numericWithPoint = numericWithPoint.replaceAll((('.' == point) ? "," : "\\."), "");
		numericWithPoint = numericWithPoint.replaceAll((('.' == point) ? "\\." : ","), ((isPoint || (1 == partes.size())) ? "\\." : ""));

		return numericWithPoint;
	}

	/**
	 * Redondear un importe double a un número de dígitos.
	 * 
	 * @param value
	 * @param decimalPlace
	 * @return
	 */
	static public double round(double value, int decimalPlace) {
		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
		return bd.doubleValue();
	}

	/**
	 * Incluir comillas en un String.
	 * 
	 * @param value
	 * @return
	 */
	static public String getQuotedStr(String value) {
		if (value != null)
			return "\"" + value + "\"";
		else
			return value;
	}


	/**
	 * Convierte una cadena del tipo "me llamo pepe" a "Me Llamo Pepe"
	 * 
	 * @param in
	 * @return
	 */
	static public String getNomPropio(String in) {
		String out = "";
		String[] a = in.split(" ");

		for (int i = 0; i < a.length; i++)
			out += " " + ((a[i].length() > 1) ? a[i].substring(0, 1).toUpperCase() + a[i].substring(1).toLowerCase() : a[i].toUpperCase());

		return out.trim();
	}

	/**
	 * Elimina los caracteres que no son ni letras ni blancos del parámetro
	 * recibido
	 * 
	 * @param name
	 * @return
	 */
	public static String toLetters(String name) {

		String onlyLetters = "";
		for (int i = 0; i < name.length(); i++) {
			if (Character.isLetter(name.charAt(i)) || Character.isSpaceChar(name.charAt(i))) {
				onlyLetters += name.charAt(i);
			}
		}

		return onlyLetters;
	}

	/**
	 * Compara si dos valores son iguales.
	 * 
	 * @param object1
	 * @param object2
	 * @return
	 */
	@SuppressWarnings("unused")
	static public boolean isEqualValues(Object o1, Object o2) {
		int comparison = 0;
		// Define null less than everything, except null.
		if (o1 == null && o2 == null)
			comparison = 0;
		else if (o1 == null)
			comparison = -1;
		else if (o2 == null)
			comparison = 1;
		else {
			// tipos de campos
			if (o1 instanceof Integer || o1 instanceof Long || o1 instanceof Double || o1 instanceof Float) {
				double d1 = Double.parseDouble(o1.toString());
				double d2 = Double.parseDouble(o2.toString());
				comparison = d1 < d2 ? -1 : (d1 > d2 ? 1 : 0);
			}
			else if (o1 instanceof java.util.Date) {
				long d1 = ((Date) o1).getTime();
				long d2 = ((Date) o2).getTime();
				comparison = d1 < d2 ? -1 : (d1 > d2 ? 1 : 0);
			}
			else {
				if (o1 == null)
					o1 = "";
				if (o2 == null)
					o2 = "";

				if (o1.toString() == null && o2.toString() == null)
					comparison = 1;
				else if (o1.toString() == null && o2.toString() != null)
					comparison = 0;
				else if (o1.toString() != null && o2.toString() == null)
					comparison = 0;
				else
					comparison = o1.toString().compareTo(o2.toString());
			}
		}
		return comparison == 0;
	}

	/**
	 * Ordenar una lista de objetos por una serie de propiedades.
	 * 
	 * @param list
	 * @param propertyNames
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	static public void sortList(List list, String[] propertyNames) {

		BeanAttributeSort[] items = new BeanAttributeSort[propertyNames.length];
		for (int i = 0; i < propertyNames.length; i++) {
			items[i] = new BeanAttributeSort(propertyNames[i]);
		}

		Collections.sort(list, new BeanComparator(items));
	}

	/**
	 * Ordenar una lista de objetos por una propiedad.
	 * 
	 * @param list
	 * @param propertyNames
	 */
	@SuppressWarnings("rawtypes")
	static public void sortList(List list, String propertyName) {
		sortList(list, propertyName, false);
	}

	/**
	 * Ordenar una lista de objetos por una propiedad.
	 * 
	 * @param list
	 * @param propertyNames
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	static public void sortList(List list, String propertyName, boolean descend) {

		BeanAttributeSort[] items = new BeanAttributeSort[1];
		items[0] = new BeanAttributeSort(propertyName, descend ? BeanAttributeSort.DESCENDING : BeanAttributeSort.ASCENDING);

		Collections.sort(list, new BeanComparator(items));
	}

	/**
	 * Fraccionar un mensaje en líneas mediante retornos de carro.
	 * 
	 * @param message
	 * @param length
	 * @return
	 */
	static public String getLinesString(String message, int length) {

		String newMessage = "";
		if (message.length() > length) {
			while (message.length() > length) {
				newMessage += message.substring(0, length) + "\n";
				message = message.substring(length);
			}
		}
		return newMessage + message;
	}

	/**
	 * Comprueba si un valor double tiene una parte decimal.
	 * 
	 * @param value
	 * @return
	 */
	static public boolean hasNumberFraction(double value) {

		String text = new Double(value).toString();
		return text.lastIndexOf(".0") == text.length() - 2;
	}

	/*
	 * Obtiene la parte decimal de un número.
	 */
	static public long getNumberDecimals(double value) {

		String v = new Double(value).toString();
		if (v.indexOf(".") > -1)
			return new Long(v.substring(v.indexOf(".") + 1)).longValue();
		else
			return 0;
	}

	/**
	 * Función que devuelve Double convertido a String para evitar utilizar
	 * toString() cuando los valores son muy grandes.
	 * 
	 * @param value
	 * @throws Exception
	 */
	static public String getNumberToString(Number value, boolean grouping) throws Exception {

		DecimalFormat d = new DecimalFormat();
		d.setGroupingUsed(grouping);
		return d.format(value);
	}

	/**
	 * Obtiene el string de un objeto adaptado a su tipología, evitando utilizar
	 * el método toString() que no es apropiado para fechas y números.
	 * 
	 * @param fieldValue
	 * @return
	 */
	public static String getStringObject(Object value) throws Exception {

		if (value == null)
			return null;
		else if (value instanceof Number)
			return getNumberToString((Number) value);
		else if (value instanceof Date)
			return DateUtils.getDateToString((Date) value);
		else
			return value.toString();
	}


	/**
	 * Obtener el valor en formato monetario.
	 * 
	 * @param value
	 * @return
	 */
	public static String getFormatNumberToMoney(Number value) {
		
		NumberFormat nf = NumberFormat.getCurrencyInstance();
		return nf.format(value);
	}

	/**
	 * Pasar a integer un valor
	 * 
	 * @param value
	 * @return
	 */
	public static int toInt(Object value) {
		if (value == null)
			return 0;
		else if (value instanceof Integer)
			return (Integer) value;
		else if (value instanceof Double)
			return ((Double) value).intValue();
		else {
			try {
				return value.toString().equals("") ? 0 : new Integer(value.toString());
			}
			catch (NumberFormatException e) {
				return 0;
			}
		}
	}

	/**
	 * Pasar a long un valor
	 * 
	 * @param value
	 * @return
	 */
	public static long toLong(Object value) {
		if (value == null)
			return 0;
		else if (value instanceof Long)
			return value.toString().equals("") ? 0 : (Long) value;
		else {
			try {
				return new Long(value.toString());
			}
			catch (NumberFormatException e) {
				return 0;
			}
		}
	}

	/**
	 * Pasar a double un valor
	 * 
	 * @param value
	 * @return
	 */
	public static double toDouble(Object value) {
		if (value == null)
			return 0.0;
		else if (value instanceof Double)
			return (Double) value;
		else {
			try {
				return value.toString().equals("") ? 0.0 : new Double(value.toString());
			}
			catch (NumberFormatException e) {
				return 0;
			}
		}
	}

	/**
	 * Pasar a Boolean un valor
	 * 
	 * @param value
	 * @return
	 */
	public static boolean toBoolean(Object value) {
		if (value == null)
			return false;
		else if (value instanceof Boolean)
			return (Boolean) value;
		else
			return false;
	}

	/**
	 * Comprueba si el valor es infinito o NaN.
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isNaN(Double value) {
		return "NaN".equals(value.toString());
	}

	/**
	 * Comprueba si una cadena es un valor numérico.
	 * 
	 * @param cadena
	 * @return
	 */
	public static boolean isNumeric(String cadena) {
		try {
			Integer.parseInt(cadena);
			return true;
		}
		catch (NumberFormatException nfe) {
			return false;
		}
	}

	/**
	 * Mover un elemento de una lista buscando por el valor de una de sus
	 * propiedades.
	 * 
	 * @param index
	 * @param propertyName
	 * @param value
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void moveElementList(int index, List list, String propertyName, String value) throws Exception {
		if (list != null) {
			for (Object object : list) {
				Object v = ReflectionUtils.getPropertyValue(object, propertyName);
				if (v != null && v.toString().equals(value)) {
					list.remove(object);
					list.add(index, object);
					break;
				}
			}
		}
	}	
}

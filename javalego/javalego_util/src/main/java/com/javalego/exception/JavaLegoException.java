package com.javalego.exception;

import com.javalego.util.StringUtils;

/**
 * Excepción básica usada en la arquitectura JavaLego.
 * 
 * @author ROBERTO RANZ
 */
public class JavaLegoException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Clase y método que se ejecutará cuando se genere un mensaje de log.
	 */
	static public Object objectNotifyEvent;

	/**
	 * Método que se ejecutará cuando se genere un mensaje de log.
	 */
	static public String methodObjectNotifyEvent;

	/**
	 * Imprimir traza de error. Ver functions showMessageError donde se utiliza
	 * esta constante para mostrar o no esta traza.
	 */
	private static boolean printStackTrace = true;

	public static void setPrintStackTrace(boolean printStackTrace) {
		JavaLegoException.printStackTrace = printStackTrace;
	}

	private String type;

	/**
	 * Grado de importancia de la excepción.
	 */
	static public final String INFORMATION = "information", WARNING = "warning", ERROR = "error";

	private boolean printLog = true;

	/**
	 * Excepción Gana con tipología de nivel de ERROR.
	 */
	public JavaLegoException(String message, Exception exception) {
		this(message, ERROR, exception);
	};

	/**
	 * Excepción Gana con tipología de nivel de excepción (error, warning o
	 * information).
	 * 
	 * @param message
	 * @param type
	 */
	public JavaLegoException(String message, String type) {
		this(message, type, true);
	}

	/**
	 * Excepción Gana con tipología de nivel de excepción (error, warning o
	 * information).
	 * 
	 * @param message
	 * @param type
	 */
	public JavaLegoException(String message, String type, String userName) {
		this(message, type, true, userName);
	}

	/**
	 * Excepción Gana con tipología de nivel de excepción (error, warning o
	 * information).
	 * 
	 * @param message
	 * @param type
	 */
	public JavaLegoException(String message, String type, Exception exception) {
		this(message + "\n" + getMessageException(exception), type, true);
	}

	/**
	 * Excepción Gana con tipo = ERROR por defecto.
	 * 
	 * @param message
	 */
	public JavaLegoException(String message) {
		this(message, ERROR, true);
	}

	/**
	 * Excepción Gana con tipología de nivel de excepción (error, warning o
	 * information).
	 * 
	 * @param message
	 * @param type
	 */
	public JavaLegoException(String message, String type, boolean printLog) {
		this(message, type, printLog, null);
	}

	/**
	 * Excepción Gana con tipología de nivel de excepción (error, warning o
	 * information).
	 * 
	 * @param message
	 * @param type
	 */
	public JavaLegoException(String message, String type, boolean printLog, String userName) {

		super(translateMessage(message));
		this.printLog = printLog;
		this.type = type;

		// Grabar en log los mensajes de error.
		if (type.equals(ERROR) && printLog) {
			try {

			}
			catch (Exception e1) {
				e1.printStackTrace();
			}

			if (printLog)
				printStackTrace(getStackTrace());
		}
	};

	/**
	 * Interpretar el mensaje de error para traducir su texto a un lenguaje
	 * compresible por el usuario.
	 * 
	 * @param message
	 */
	public static String translateMessage(String message) {

		return message;
	}

	/**
	 * Imprimir stack
	 * 
	 * @param stack
	 */
	private void printStackTrace(StackTraceElement[] stack) {

		if (stack == null)
			return;

	}

	public String getType() {
		return type;
	}

	public boolean isWarning() {
		return type.equals(WARNING);
	}

	public boolean isError() {
		return type.equals(ERROR);
	}

	public boolean isInformation() {
		return type.equals(INFORMATION);
	}

	public boolean isPrintLog() {
		return printLog;
	}

	public void setPrintLog(boolean printLog) {
		this.printLog = printLog;
	}

	public static boolean isPrintStackTrace() {
		return printStackTrace;
	}

	/**
	 * Obtener un mensaje válido para las excepciones cuyo mensaje sea igual a
	 * null.
	 * 
	 * @param exception
	 * @return
	 */
	static public String getMessageException(Exception exception) {
		String message = "";
		if (exception != null) {
			if (StringUtils.isEmpty(exception.getMessage()))
				message = exception.getClass().getName() + (exception.getStackTrace().length > 0 ? exception.getStackTrace()[0] : "");
			else {
				message = exception.getMessage();
				if (message.indexOf("Connection refused: connect") > -1) {
				}
				// if (message.length() > 80) {
				// String newMessage = "";
				// while (message.length() > 80) {
				// newMessage += message.substring(0,80) + "\n";
				// message = message.substring(80);
				// }
				// message = newMessage + message;
				// }
			}
		}
		return message;
	}

	/**
	 * Obtiene el mensaje de la traza de la excepción pasada como parámetro.
	 * 
	 * @param traceElements
	 * @return
	 */
	static public String getMessageStackTrace(StackTraceElement[] traceElements) {
		String message = "";
		for (int i = 0; i < traceElements.length; i++)
			message += traceElements[i].toString() + "\n";
		return message;
	}


}

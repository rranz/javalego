package com.javalego.exception;

/**
 * Lista de niveles de error.
 * 
 * @author ROBERTO RANZ
 * 
 */
public enum ErrorLevel
{
	/**
	 * Tipologías de niveles de excepción
	 */
	ERROR(1000), DEBUG(2000), INFO(3000), WARN(4000), SUCESS(5000), FATAL(6000);

	/**
	 * Código de nivel
	 */
	private int code;

	/**
	 * Ïndice secuencial dentro del código global de nivel de excepción.
	 */
	private int index;

	/**
	 * Constructor
	 * @param code
	 */
	private ErrorLevel(int code)
	{
		this.code = code;
	}

	int getCode()
	{
		return code;
	}

	double getIndex()
	{
		return index;
	}
	
	void setIndex(int index) 
	{
		this.index = index;
	}

	/**
	 * Obtiene el valor del código de excepción con el código genérico más el índice secuencial.
	 */
	@Override
	public String toString()
	{
		return new Integer(code + index).toString();
	}

}

package com.javalego.errors;

/**
 * Lista de niveles de error.
 * 
 * @author ROBERTO RANZ
 * 
 */
public enum ErrorLevel
{
	/**
	 * Tipologías de niveles de error
	 */
	ERROR(1000), DEBUG(2000), INFO(3000), WARN(4000), SUCESS(5000), FATAL(6000);

	/**
	 * Código de nivel
	 */
	private int code;

	/**
	 * Ïndice secuencial dentro del código global de nivel de error.
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

	public int getCode()
	{
		return code;
	}

	public double getIndex()
	{
		return index;
	}
	
	public void setIndex(int index) 
	{
		this.index = index;
	}

	/**
	 * Obtiene el valor del código de error y el índice secuencial.
	 */
	@Override
	public String toString()
	{
		return new Integer(code + index).toString();
	}

}

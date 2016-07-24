package com.javalego.data;

import java.util.Locale;

import com.javalego.exception.LocalizedException;

/**
 * Excepción asociada a los métodos de persistencia definidos en el proveedor de datos genérico.
 * 
 * @author ROBERTO RANZ
 *
 */
public class DataProviderException extends LocalizedException
{
	private static final long serialVersionUID = 8659332557826212601L;

	public DataProviderException(DataProviderErrors error, Exception exception, String... parameters)
	{
		super(exception, error, parameters);
	}

	public DataProviderException(DataProviderErrors error, Locale locale, Exception exception, String... parameters)
	{
		super(exception, locale, error, parameters);
	}

	public DataProviderException(DataProviderErrors error, String... parameters)
	{
		super(error, parameters);
	}

}

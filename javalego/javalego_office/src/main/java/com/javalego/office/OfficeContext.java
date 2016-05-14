package com.javalego.office;

import com.javalego.model.context.Context;

/**
 * Contexto de ejecución de documentos Office.
 * 
 * TODO Implementar servicios para documentos MS-Office a través de productos como POI Apache, IText, ... 
 * 
 * @author ROBERTO RANZ
 *
 */
public class OfficeContext implements Context {

	private static OfficeContext current = null;
	
	public static OfficeContext getCurrent() {
		return current;
	}

	public static void setCurrent(OfficeContext current) {
		OfficeContext.current = current;
	}
}

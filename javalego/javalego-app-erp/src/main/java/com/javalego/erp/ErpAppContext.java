package com.javalego.erp;

import com.javalego.application.AppContext;
import com.javalego.application.Environment;
import com.javalego.exception.LocalizedException;
import com.javalego.model.keys.Key;

/**
 * Contexto de aplicación para JAVALEGO DEMO ERP
 * 
 * @author ROBERTO RANZ
 *
 */
public class ErpAppContext extends AppContext {


	/**
	 * Contexto de aplicación actual.
	 */
	private static final ErpAppContext current = new ErpAppContext("JAVALEGO ERP Demo", null);

	/**
	 * Constructor
	 * @param name
	 * @param title
	 */
	protected ErpAppContext(String name, Key title) {
		super(name, title);
	}

	@Override
	public void load(Environment environment) throws LocalizedException {
		
		super.load(environment);
	}

	/**
	 * Instancia actual del contexto de aplicación.
	 * 
	 * @return
	 */
	public static ErpAppContext getCurrent() {
		return current;
	}

}

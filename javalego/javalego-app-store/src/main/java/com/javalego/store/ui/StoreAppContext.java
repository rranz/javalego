package com.javalego.store.ui;

import com.javalego.application.AppContext;
import com.javalego.application.Environment;
import com.javalego.exception.LocalizedException;
import com.javalego.model.keys.Key;
import com.javalego.store.environment.BaseEnvironment;
import com.javalego.store.model.ModelService;
import com.javalego.store.model.StoreDataServices;
import com.javalego.store.ui.locale.LocaleStore;

/**
 * Contexto de aplicación para JAVALEGO STORE
 * 
 * @author ROBERTO RANZ
 *
 */
public class StoreAppContext extends AppContext {

	private ModelService modelService;
	
	/**
	 * Contexto de aplicación actual.
	 */
	private static final StoreAppContext current = new StoreAppContext("STORE", LocaleStore.APP);

	/**
	 * Constructor
	 * @param name
	 * @param title
	 */
	protected StoreAppContext(String name, Key title) {
		super(name, title);
	}
	
	@Override
	public void load(Environment environment) throws LocalizedException {
		
		super.load(environment);
		
		// Establecer el servicio de datos definido en el entorno.
		if (environment instanceof BaseEnvironment) {
			modelService = ((BaseEnvironment) environment).getModelService();
		}
	}

	/**
	 * Instancia actual del contexto de aplicación.
	 * 
	 * @return
	 */
	public static StoreAppContext getCurrent() {
		return current;
	}
	
	/**
	 * Modelo de datos
	 * @return
	 */
	public ModelService getModelService() {
		return modelService;
	}

	/**
	 * Servicios de datos o negocio.
	 * @return
	 */
	public StoreDataServices getDataServices() {
		return (StoreDataServices) getData().getBusinessService();
	}

}

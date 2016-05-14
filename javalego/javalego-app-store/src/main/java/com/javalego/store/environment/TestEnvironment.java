package com.javalego.store.environment;

import com.javalego.data.DataProvider;
import com.javalego.data.StoreApplicationContext;
import com.javalego.data.jpa.SpringDataProvider;
import com.javalego.entity.Entity;
import com.javalego.security.services.UserServices;
import com.javalego.store.model.ModelService;
import com.javalego.store.model.StoreDataServices;
import com.javalego.store.model.impl.MockDataServices;
import com.javalego.store.model.impl.ModelServiceImpl;

/**
 * Entorno de pruebas.
 * 
 * @author ROBERTO RANZ
 *
 */
public class TestEnvironment extends BaseEnvironment {

	private ModelService modelService;
	
	private StoreDataServices dataService;

	@Override
	public synchronized StoreDataServices getBusinessService() {

		if (dataService == null) {
			dataService = new MockDataServices();
		}
		return dataService;
	}

	@Override
	public synchronized DataProvider<Entity> getDataProvider() {
		return new SpringDataProvider(StoreApplicationContext.class);
	}
	
	@Override
	public synchronized ModelService getModelService() {

		if (modelService == null) {
			modelService = new ModelServiceImpl();
		}
		return modelService;
	}

	@Override
	public UserServices getUserServices() {
		return null;
	}

}

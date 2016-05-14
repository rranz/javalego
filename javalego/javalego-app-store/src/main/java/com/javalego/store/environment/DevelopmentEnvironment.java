package com.javalego.store.environment;

import org.apache.log4j.Logger;

import com.javalego.data.DataProvider;
import com.javalego.data.StoreApplicationContext;
import com.javalego.data.jpa.SpringDataProvider;
import com.javalego.entity.Entity;
import com.javalego.exception.LocalizedException;
import com.javalego.security.SecurityServices;
import com.javalego.security.services.UserServices;
import com.javalego.security.shiro.DefaultShiroRealm;
import com.javalego.security.shiro.SecurityShiro;
import com.javalego.store.model.ModelService;
import com.javalego.store.model.StoreDataServices;
import com.javalego.store.model.impl.DataServiceImpl;
import com.javalego.store.model.impl.GeneratorService;
import com.javalego.store.model.impl.MockDataServices;
import com.javalego.store.model.impl.ModelServiceImpl;
import com.javalego.store.model.impl.UserServicesImpl;

/**
 * Entorno de desarrollo.
 * 
 * @author ROBERTO RANZ
 *
 */
public class DevelopmentEnvironment extends BaseEnvironment {

	private final static Logger logger = Logger.getLogger(DevelopmentEnvironment.class);

	private SecurityShiro security;

	private StoreDataServices dataService;

	private ModelServiceImpl modelService;

	private UserServices userServices;

	private boolean database = true;

	/**
	 * No requiere autenticación en modo desarrollo
	 */
	@Override
	public synchronized SecurityServices getSecurity() {

		if (security == null) {

			// "classpath:shiro.ini") {
			security = new SecurityShiro(new DefaultShiroRealm(getUserServices())) {
				@Override
				public boolean isAuthenticated() {
					return true;
				}

				@Override
				public Object getPrincipal() {
					return "roberto";
				}

				@Override
				public boolean hasRole(String role) {
					return true;
				}
			};
		}
		return security;
	}

	@Override
	public synchronized StoreDataServices getBusinessService() {

		if (dataService == null) {

			if (database) {
				dataService = new DataServiceImpl();

				// Ejecutar la grabación de datos iniciales si se ha
				// inicializado la base de datos.
				try {
					new GeneratorService().execute();
				}
				catch (LocalizedException e) {
					e.printStackTrace();
					logger.error("Error GeneratorService. Mesage: " + e.getLocalizedMessage());
				}
			}
			else {
				dataService = new MockDataServices();
			}
		}
		return dataService;
	}

	@Override
	public synchronized DataProvider<Entity> getDataProvider() {
		return database ? new SpringDataProvider(StoreApplicationContext.class) : null;
	}

	@Override
	public synchronized ModelService getModelService() {

		if (modelService == null) {
			modelService = new ModelServiceImpl();
		}
		return modelService;
	}

	@Override
	public synchronized UserServices getUserServices() {

		if (userServices == null) {
			userServices = new UserServicesImpl();
		}

		return userServices;
	}
}

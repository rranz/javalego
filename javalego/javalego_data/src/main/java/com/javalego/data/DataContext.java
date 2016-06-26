package com.javalego.data;

import java.util.ArrayList;
import java.util.List;

import com.javalego.data.DataProvider.Type;
import com.javalego.exception.LocalizedException;
import com.javalego.model.AbstractBaseModel;
import com.javalego.model.context.Context;

/**
 * Contexto de acceso a datos.
 * <p>
 * Proporciona información de configuración del datasource utilizado y el proveedor de acceso a los datos (Spring Data,
 * JPA Hibernat, JDBC ...)
 * 
 * @author ROBERTO RANZ
 * 
 */
public class DataContext extends AbstractBaseModel implements Context
{
	private static final long serialVersionUID = 54507919335753297L;

	/**
	 * Proveedor de datos utilizado.
	 */
	private DataProvider dataProvider;

	/**
	 * Lista de proveedores de datos adicionales al actual.
	 */
	private List<DataProvider> dataProviders = new ArrayList<DataProvider>();

	/**
	 * Servicios de datos o negocio
	 */
	private BusinessService businessService;

	/**
	 * Datos de contexto actual.
	 */
	private static DataContext current;

	/**
	 * Constructor
	 * 
	 * @param currentProvider
	 */
	public DataContext(DataProvider currentProvider, List<DataProvider> dataProviders)
	{
		this.dataProvider = currentProvider;
		this.dataProviders = dataProviders;

		if (dataProviders.indexOf(currentProvider) < 0)
		{
			addProvider(currentProvider);
		}
	}

	/**
	 * Proveedor de datos - DAO
	 * 
	 * @return
	 */
	public static DataProvider getProvider()
	{
		return getCurrent().getDataProvider();
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 */
	public DataContext(String name)
	{
		this.setName(name);
	}

	/**
	 * Constructor
	 */
	private DataContext()
	{
	}

	/**
	 * Contexto de datos de aplicación.
	 * 
	 * @return
	 */
	public static synchronized final DataContext getCurrent()
	{
		if (current == null)
		{
			current = new DataContext();
		}
		return current;
	}

	/**
	 * Proveedor de datos - DAO
	 * 
	 * @return
	 */
	private DataProvider getDataProvider()
	{
		return dataProvider;
	}

	/**
	 * Establecer el proveedor de datos activo
	 * 
	 * @param provider
	 */
	public void setProvider(DataProvider provider)
	{
		this.dataProvider = provider;

		if (dataProviders.indexOf(provider) < 0)
		{
			addProvider(provider);
		}
	}

	/**
	 * Añadir proveedor de datos adicional. Estos proveedores de datos adicionales que pueden ser usados dinámicamente
	 * (Ej.: en Android cambiar de local SQLite a remoto con servicios de datos REST).
	 * 
	 * @param dataProvider
	 */
	public void addProvider(DataProvider dataProvider)
	{
		dataProviders.add(dataProvider);
	}

	/**
	 * Cambiar el proveedor de datos de la aplicación de forma dinámica.
	 * 
	 * @param type
	 */
	public void changeProvider(Type type) throws LocalizedException
	{
		for (DataProvider item : dataProviders)
		{
			if (type.equals(item.getType()))
			{
				dataProvider = item;
				return;
			}
		}
		throw new LocalizedException("DataProvider '" + type + "' not found.");
	}

	/**
	 * Cambiar el proveedor de datos de la aplicación al proveedor REST que debe estar definido en la lista de
	 * proveedores
	 * 
	 * @throws LocalizedException
	 */
	public void changeREST() throws LocalizedException
	{
		changeProvider(DataProvider.Type.REST);
	}

	/**
	 * Cambiar el proveedor de datos de la aplicación al proveedor Spring Data que debe estar definido en la lista de
	 * proveedores
	 * 
	 * @throws LocalizedException
	 */
	public void changeSpringData() throws LocalizedException
	{
		changeProvider(DataProvider.Type.Spring_Data);
	}

	/**
	 * Cambiar el proveedor de datos de la aplicación al proveedor JPA que debe estar definido en la lista de
	 * proveedores
	 * 
	 * @throws LocalizedException
	 */
	public void changeJPA() throws LocalizedException
	{
		changeProvider(DataProvider.Type.JPA);
	}

	/**
	 * Cambiar el proveedor de datos de la aplicación al proveedor SQLite que debe estar definido en la lista de
	 * proveedores
	 * 
	 * @throws LocalizedException
	 */
	public void changeSQLite() throws LocalizedException
	{
		changeProvider(DataProvider.Type.SQLite);
	}

	/**
	 * Comprueba si el proveedor de datos actual es de un tipo determinado.
	 * 
	 * @param type
	 */
	public boolean isType(Type type)
	{
		return dataProvider != null && dataProvider.getType().equals(type);
	}

	/**
	 * Proveedor de datos REST.
	 * 
	 * @return
	 */
	public boolean isREST()
	{
		return getCurrent().getDataProvider() != null ? getCurrent().isType(DataProvider.Type.REST) : false;
	}

	/**
	 * Proveedor de datos JPA.
	 * 
	 * @return
	 */
	public boolean isJPA()
	{
		return getCurrent().getDataProvider() != null ? getCurrent().isType(DataProvider.Type.JPA) : false;
	}

	/**
	 * Proveedor de datos Spring Data.
	 * 
	 * @return
	 */
	public boolean isSpringData()
	{
		return getCurrent().getDataProvider() != null ? getCurrent().isType(DataProvider.Type.Spring_Data) : false;
	}

	/**
	 * Proveedor de datos SQLite.
	 * 
	 * @return
	 */
	public boolean isSQLite()
	{
		return getCurrent().getDataProvider() != null ? getCurrent().isType(DataProvider.Type.SQLite) : false;
	}

	/**
	 * Proveedor de datos Mock (datos para realizar pruebas).
	 * 
	 * @return
	 */
	public boolean isMock()
	{
		return getCurrent().getDataProvider() != null ? getCurrent().isType(DataProvider.Type.Mock) : false;
	}

	/**
	 * Servicios de negocio.
	 * 
	 * @return
	 */
	public BusinessService getBusinessService()
	{
		return businessService;
	}

	/**
	 * Servicios negocio.
	 * 
	 * @param businessService
	 */
	public void setBusinessService(BusinessService businessService)
	{
		this.businessService = businessService;
	}

}

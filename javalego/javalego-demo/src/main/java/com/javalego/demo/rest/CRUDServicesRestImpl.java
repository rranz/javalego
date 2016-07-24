package com.javalego.demo.rest;

import java.util.List;

import com.javalego.data.DataProvider;
import com.javalego.data.DataProviderException;
import com.javalego.entity.Entity;

/**
 * Implementación de los métodos básicos de un mantenimiento de entidades (CRUD).
 * 
 * @author ROBERTO RANZ
 *
 */
@SuppressWarnings("unchecked")
public abstract class CRUDServicesRestImpl implements CRUDServicesRest
{
	@Override
	public <T extends Entity<?>> T save(T entity) throws DataProviderException
	{
		return getDataProvider().save(entity);
	}

	/**
	 * Proveedor de datos
	 * 
	 * @return
	 */
	public abstract DataProvider getDataProvider();

	@Override
	public <T extends Entity<?>> List<T> findAll() throws DataProviderException
	{
		return (List<T>) getDataProvider().findAll(getEntityClass());
//		try
//		{
//			return Response.ok(getDataProvider().findAll(getEntityClass())).build();
//		}
//		catch (DataProviderException e)
//		{
//			return Response.status(Response.Status.BAD_REQUEST).entity(e.getLocalizedMessage()).build();
//		}
	}

	@Override
	public <T extends Entity<?>> T find(long id) throws DataProviderException
	{
		return (T) getDataProvider().find(getEntityClass(), id);

//		try
//		{
//			return Response.ok(getDataProvider().find(getEntityClass(), id)).build();
//		}
//		catch (DataProviderException e)
//		{
//			return Response.status(Response.Status.BAD_REQUEST).entity(e.getLocalizedMessage()).build();
//		}	
	}

	@Override
	public <T extends Entity<?>> List<T> findByProperty(String propertyName, Object value) throws DataProviderException
	{
		return (List<T>) getDataProvider().findByProperty(getEntityClass(), propertyName, value);
//		try
//		{
//			return Response.ok(getDataProvider().findByProperty(getEntityClass(),
//				propertyName, value)).build();
//		}
//		catch (DataProviderException e)
//		{
//			return Response.status(Response.Status.BAD_REQUEST).entity(e.getLocalizedMessage()).build();
//
//		}
	}

	@Override
	public <T extends Entity<?>> T delete(long id) throws DataProviderException
	{
		return (T) getDataProvider().find(getEntityClass(), id);
//		try
//		{
//			return Response.ok(getDataProvider().delete(getDataProvider().find(getEntityClass(), id))).build();
//		}
//		catch (DataProviderException e)
//		{
//			return Response.status(Response.Status.BAD_REQUEST).entity(e.getLocalizedMessage()).build();
//		}
	}

}

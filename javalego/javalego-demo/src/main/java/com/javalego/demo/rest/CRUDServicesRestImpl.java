package com.javalego.demo.rest;

import javax.ejb.EJB;
import javax.ws.rs.core.Response;

import com.javalego.data.jpa.JpaProvider;
import com.javalego.entity.Entity;
import com.javalego.exception.LocalizedException;

public abstract class CRUDServicesRestImpl implements CRUDServicesRest
{
	@EJB
	protected JpaProvider dataProvider;
	
	@Override
	public <T extends Entity<?>> Response save(T entity)
	{
		return Response.ok(dataProvider.save(entity)).build();
	}

	@Override
	public Response list()
	{
		return Response.ok(dataProvider.findAll(getClazz())).build();
	}

	@Override
	public Response find(long id)
	{
		return Response.ok(dataProvider.find(getClazz(), id)).build();
	}

	@Override
	public Response delete(long id) throws LocalizedException
	{
		return Response.ok(dataProvider.delete(dataProvider.find(getClazz(), id))).build();
	}

}

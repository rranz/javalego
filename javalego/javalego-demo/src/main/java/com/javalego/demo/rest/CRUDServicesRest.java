package com.javalego.demo.rest;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import com.javalego.entity.Entity;
import com.javalego.exception.LocalizedException;

/**
 * Servicios web para opciones básicas de un mantenimiento estándar (CRUD).
 * 
 * @author ROBERTO RANZ
 *
 */
public interface CRUDServicesRest
{
	@Path("/save")
	@POST
	<T extends Entity<?>> Response save(T entity);

	@Path("/list")
	@GET
	<T extends Entity<?>> Response list();

	@Path("/find/{id}")
	@GET
	<T extends Entity<?>> Response find(@PathParam("id") long id);

	@Path("/delete/{id}")
	@DELETE
	<T extends Entity<?>> Response delete(long id) throws LocalizedException;

	/**
	 * Get class entity
	 * 
	 * @return
	 */
	Class<? extends Entity<?>> getClazz();

}
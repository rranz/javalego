package com.javalego.demo.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.javalego.data.DataProviderException;
import com.javalego.entity.Entity;

/**
 * Servicios web para opciones básicas de un mantenimiento estándar (CRUD).
 * 
 * @author ROBERTO RANZ
 *
 */
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface CRUDServicesRest
{
	@Path("/save")
	@POST
	<T extends Entity<?>> T save(T entity) throws DataProviderException;

	@Path("/findAll")
	@GET
	<T extends Entity<?>> List<T> findAll() throws DataProviderException;

	@Path("/find/{id}")
	@GET
	<T extends Entity<?>> T find(@PathParam("id") long id) throws DataProviderException;

	@Path("/find")
	@GET
	<T extends Entity<?>> List<T> findByProperty(@QueryParam("property") String propertyName, @QueryParam("value") Object value) throws DataProviderException;

	@Path("/delete/{id}")
	@DELETE
	<T extends Entity<?>> T delete(long id) throws DataProviderException;

	/**
	 * Get class entity
	 * 
	 * @return
	 */
	Class<? extends Entity<?>> getEntityClass();

}
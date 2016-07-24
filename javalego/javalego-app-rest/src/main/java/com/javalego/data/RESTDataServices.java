package com.javalego.data;

import java.util.Collection;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.javalego.data.DataContext;
import com.javalego.data.DataProvider;
import com.javalego.data.RESTDataServices;
import com.javalego.entity.Entity;
import com.javalego.entity.impl.EntityId;
import com.javalego.exception.LocalizedException;
import com.javalego.model.context.Services;

/**
 * Servicios REST de acceso a datos. Se usará el DataProvider
 * definido en el servidor para esta aplicación.
 * 
 * Se usará inicialmente para el acceso a datospara el acceso y persistencia de datos para plataformas
 * móviles (Android e IOS).
 * 
 * Nota: podemos obtener el fichero wadl para invocar los servicios REST desde
 * SOAP UI. Es necesario habilitar la obtención de este archivo definiendo un
 * parámetro inicial de Jersey.
 * <p>
 * 
 * <pre>
 * {@code
 * <init-param>
 * 	<param-name>jersey.config.server.wadl.disableWadl</param-name>
 * 	<param-value>false</param-value> 
 * </init-param>
 * }
 * </pre>
 * 
 * http://localhost:8080/javalego_jersey/webapi/application.wadl
 * 
 * @author ROBERTO RANZ
 * 
 */
@Path("/meccanoj")
public class RESTDataServices implements Services {

	private static final String NULL = "null";

	private static final String ORDER = "order";

	private static final String WHERE = "where";
	
	private static final String FIELDNAME = "fieldname";
	
	private static final String FIELDVALUES = "fieldvalues";

	private static final String OBJECT_WHERE = "object_where";

	private static final String OBJECT_ID = "object_id";	
	
	private static final String ID = "id";

	private static final Logger logger = Logger.getLogger(RESTDataServices.class);

	private static final Gson gson = new Gson();

	 // The @Context annotation allows us to have certain contextual objects
    // injected into this class.
    // UriInfo object allows us to get URI information (no kidding).
    @Context
    UriInfo uriInfo;

    // Another "injected" object. This allows us to use the information that's
    // part of any incoming request.
    // We could, for example, get header information, or the requestor's address.
    @Context
    Request request;

	@SuppressWarnings("unchecked")
	@GET
	@Path("/list/{entity}")
	@Produces(MediaType.APPLICATION_JSON)
	// Nota: no se puede repetir el path aunque existan parámetros. Sólo se
	// puede realizar con PathParameter /{where}/{order}
	public String getList(@PathParam("entity") String entityName, @QueryParam(WHERE) String where, @QueryParam(ORDER) String order) throws DataProviderException {
		try {
			List<? extends Entity<?>> list = getDataProvider().findAll((Class<? extends Entity<?>>)Class.forName(entityName), NULL.equals(where) ? null : where, NULL.equals(order) ? null : order);
			return gson.toJson(list);
		}
		catch (ClassNotFoundException e) {
			logger.error("REST ERROR: ENTITY CLASS '" + entityName + " NOT FOUND");
			return null;
		}
		
	}

	@SuppressWarnings("unchecked")
	@GET
	@Path("/" + OBJECT_ID + "/{entity}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getObject(@PathParam("entity") String entityName, @QueryParam(ID) Long id) throws DataProviderException {
		try {
			EntityId entity = getDataProvider().find((Class<? extends EntityId>) Class.forName(entityName), id);
			return gson.toJson(entity);
		}
		catch (ClassNotFoundException e) {
			logger.error("REST ERROR: ENTITY CLASS '" + entityName + " NOT FOUND");
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@GET
	@Path("/" + OBJECT_WHERE + "/{entity}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getObject(@PathParam("entity") String entityName, @QueryParam(WHERE) String where) throws DataProviderException {
		try {
			Entity<?> entity = getDataProvider().find((Class<? extends Entity<?>>) Class.forName(entityName), NULL.equals(where) ? null : where);
			return gson.toJson(entity);
		}
		catch (ClassNotFoundException e) {
			logger.error("REST ERROR: ENTITY CLASS '" + entityName + " NOT FOUND");
			return null;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@POST
	@Path("/save/{entity}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	// Nota: no se puede repetir el path aunque existan parámetros. Sólo se
	// puede realizar con PathParameter /{where}/{order}
	public String save(@PathParam("entity") String entityName, String data) throws DataProviderException {
		try {
			Entity bean = gson.fromJson(data, (Class<? extends Entity>) Class.forName(entityName));
			bean = getDataProvider().save(bean);
			return gson.toJson(bean);
		}
		catch (ClassNotFoundException e) {
			logger.error("REST ERROR: ENTITY CLASS '" + entityName + " NOT FOUND");
			return null;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GET
	@Path("/delete/{entity}")
	@Consumes(MediaType.APPLICATION_JSON)
	public String delete(@PathParam("entity") String entityName, @QueryParam(ID) Long id) {
		try {
			Entity bean = getDataProvider().find((Class<? extends EntityId>) Class.forName(entityName), id);
			if (bean != null) {
				getDataProvider().delete(bean);
			}
		}
		catch (ClassNotFoundException e) {
			logger.error("REST ERROR: ENTITY CLASS '" + entityName + " NOT FOUND");
		}
		catch (LocalizedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GET
	@Path("/" + FIELDVALUES + "/{entity}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getFieldValues(@PathParam("entity") String entityName, @QueryParam(FIELDNAME) String fieldName, @QueryParam(WHERE) String where, @QueryParam(ORDER) String order) throws DataProviderException {
		try {
			Collection<?> list = (Collection<?>) getDataProvider().propertyValues((Class<? extends Entity>) Class.forName(entityName), fieldName, NULL.equals(where) ? null : where, NULL.equals(order) ? null : order);
			return gson.toJson(list);
		}
		catch (ClassNotFoundException e) {
			logger.error("REST ERROR: ENTITY CLASS '" + entityName + " NOT FOUND");
			return null;
		}
	}
	
	/**
	 * Proveedor de datos utilizado por los servicios REST para lectura y
	 * persistencia de datos.
	 * 
	 * @return
	 */
	private DataProvider getDataProvider() {
		return DataContext.getProvider();
	}

}

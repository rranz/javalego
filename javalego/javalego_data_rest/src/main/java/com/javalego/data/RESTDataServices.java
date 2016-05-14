package com.javalego.data;

import java.util.Collection;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.javalego.entity.Entity;
import com.javalego.exception.LocalizedException;
import com.javalego.model.context.Services;

/**
 * Servicios REST de lectura y persistencia de Datos. Se usará el DataProvider definido en el servidor
 * para esta aplicación.
 * 
 * Se usará inicialmente para el acceso y persistencia de datos para plataformas
 * móviles (Android e IOS).
 * 
 * Nota: podemos obtener el fichero wadl para invocar los servicios REST desde
 * SOAP UI. Es necesario habilitar la obtención de este archivo definiendo un
 * parámetro inicial de Jersey
 * <p>
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

	private static final Logger logger = Logger.getLogger(RESTDataServices.class);

	private static final Gson gson = new Gson();
	
	@SuppressWarnings("unchecked")
	@GET
	@Path("/list/{entity}")
	@Produces(MediaType.APPLICATION_JSON)
	// Nota: no se puede repetir el path aunque existan parámetros. Sólo se puede realizar con PathParameter /{where}/{order}
	public String getList(@PathParam("entity") String entityName, @QueryParam("where") String where, @QueryParam("order") String order) throws LocalizedException {
		try {
			Collection<Entity> list = (Collection<Entity>) getDataProvider().getList((Class<? extends Entity>) Class.forName(entityName), where, order);
			return gson.toJson(list);
		} catch (ClassNotFoundException e) {
			logger.error("REST ERROR: ENTITY CLASS '" + entityName + " NOT FOUND");
			return null;
		}
	}

	/**
	 * Proveedor de datos utilizado por los servicios REST para lectura y persistencia de datos.
	 * @return
	 */
	private DataProvider<Entity> getDataProvider() {
		return DataContext.getProvider();
	}
	
}

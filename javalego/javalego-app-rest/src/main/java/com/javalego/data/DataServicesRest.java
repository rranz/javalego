package com.javalego.data;

import java.util.Collection;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import com.google.gson.Gson;
import com.javalego.entity.Entity;
import com.javalego.exception.LocalizedException;
import com.javalego.model.context.Services;
import com.javalego.test.data.entities.Product;

/**
 * Servicios REST de acceso a datos.
 * 
 * @author ROBERTO RANZ
 */
@Path("/javalego")
public class DataServicesRest implements Services {

	private static final Gson gson = new Gson();

	@Context
	UriInfo uriInfo;

	@Context
	Request request;

	@SuppressWarnings("unchecked")
	@GET
	@Path("/products/all")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllProducts() {
		
		Collection<Entity> list;
		try {
			list = (Collection<Entity>) getDataProvider().getList((Class<? extends Entity>) Product.class);
			return gson.toJson(list);
		}
		catch (LocalizedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

//	@GET
//	@Path("/products/save")
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	public String saveProduct(String data) {
//		
//		Product product;
//		try {
//			product = (Product) getDataProvider().save(gson.fromJson(data, Product.class));
//			return gson.toJson(product);
//		}
//		catch (JsonSyntaxException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			//return Response.status(Status.NO_CONTENT).build();
//		}
//		catch (LocalizedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return "";
//	}
//
//	@GET
//	@Path("/products/delete")
//	@Produces(MediaType.APPLICATION_JSON)
//	public void deleteProduct(String data) {
//
//		try {
//			getDataProvider().delete(gson.fromJson(data, Product.class));
//		}
//		catch (JsonSyntaxException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		catch (LocalizedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
	/**
	 * Proveedor de datos
	 * 
	 * @return
	 */
	private DataProvider<Entity> getDataProvider() {
		return DataContext.getProvider();
	}

}

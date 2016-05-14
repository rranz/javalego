package com.javalego.data.rest.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.javalego.data.DataProvider;
import com.javalego.entity.Entity;
import com.javalego.exception.LocalizedException;

/**
 * Acceso a datos con servicios REST.
 * 
 * @author ROBERTO RANZ
 * 
 */
public class DataProviderRESTClient implements DataProvider<Entity> {
	
	private static final Gson gson = new Gson();
	
	private static final String NULL = "null";

	private static final String LIST = "list";

	private static final String DELETE = "delete";

	private static final String SAVE = "save";

	private static final String FIELDVALUES = "fieldvalues";

	private static final String WHERE = "where";

	private static final String ORDER = "order";

	private static final String FIELDNAME = "fieldname";

	private static final String OBJECT_WHERE = "object_where";

	private static final String OBJECT_ID = "object_id";

	private static final String ID = "id";

	private String endpoint;

	private Client client = ClientBuilder.newClient();

	public DataProviderRESTClient(String endpoint) {
		this.endpoint = endpoint;
	}

	@Override
	public Collection<? extends Entity> getList(Class<? extends Entity> entity) throws LocalizedException {

		WebTarget target = client.target(endpoint + LIST).path(entity.getCanonicalName());
		return getList(target, entity);
	}

	@Override
	public Collection<? extends Entity> getList(Class<? extends Entity> entity, String where, String order) throws LocalizedException {

		WebTarget target = client.target(endpoint + LIST).path(entity.getCanonicalName()).queryParam(WHERE, where).queryParam(ORDER, order);
		return getList(target, entity);
	}

	@Override
	public Collection<? extends Entity> getList(Class<? extends Entity> entity, String where) throws LocalizedException {

		WebTarget target = client.target(endpoint + LIST).path(entity.getCanonicalName()).queryParam(WHERE, where);
		return getList(target, entity);
	}

	@Override
	public Collection<? extends Entity> getPagedList(Class<? extends Entity> entity, int startIndex, int count, String where, String order) throws LocalizedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<? extends Entity> getQuery(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void load() {
	}

	@Override
	public Long getLong(String statement) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Entity getObject(String statement) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Entity bean) {

		if (bean != null && bean.getId() != null) {
			WebTarget target = client.target(endpoint + DELETE).path(bean.getClass().getCanonicalName()).queryParam(ID, bean.getId());
			target.request().accept(MediaType.APPLICATION_JSON_TYPE).buildGet().invoke(String.class);
		}
	}

	@Override
	public Entity save(Entity bean) {
		
		WebTarget target = client.target(endpoint + SAVE).path(bean.getClass().getCanonicalName());
		return save(target, bean);
	}

	@Override
	public Entity getObject(Class<? extends Entity> entity, Long id) throws LocalizedException {

		WebTarget target = client.target(endpoint + OBJECT_ID).path(entity.getCanonicalName()).queryParam(ID, id);
		return getObject(target, entity);

	}

	@Override
	public Entity getObject(Class<? extends Entity> entity, String where) throws LocalizedException {
		
		WebTarget target = client.target(endpoint + OBJECT_WHERE).path(entity.getCanonicalName()).queryParam(WHERE, where);
		return getObject(target, entity);
	}

	@Override
	public Collection<?> getFieldValues(Class<? extends Entity> entity, String fieldName, String where, String order) throws LocalizedException {
		
		WebTarget target = client.target(endpoint + FIELDVALUES).path(entity.getCanonicalName()).queryParam(FIELDNAME, fieldName).queryParam(WHERE, where).queryParam(ORDER, order);
		return getFieldValues(target, Object.class);
	}

	/**
	 * Obtener una lista de objetos de tipo IEntity con datos Json obtenido de
	 * la invocación del servicio REST.
	 * 
	 * @param target
	 * @return
	 */
	private Collection<Entity> getList(WebTarget target, Class<? extends Entity> entity) {

		String data = target.request().accept(MediaType.APPLICATION_JSON_TYPE).buildGet().invoke(String.class);

		if (data == null || "".equals(data) || NULL.equals(data)) {
			return null;
		}

		JsonParser jsonParser = new JsonParser();

		JsonArray jsonElement = (JsonArray) jsonParser.parse(data);

		if (jsonElement != null && jsonElement.size() > 0) {

			List<Entity> list = new ArrayList<Entity>();

			for (JsonElement element : jsonElement) {

				Object object = new Gson().fromJson(element, entity);

				if (object instanceof Entity) {
					list.add((Entity) object);
				}
			}
			return list;
		}
		else {
			return null;
		}

	}

	/**
	 * Obtener una lista de valores de un campos de entidad
	 * 
	 * @param target
	 * @return
	 */
	private Collection<Object> getFieldValues(WebTarget target, Class<?> type) {

		String data = target.request().accept(MediaType.APPLICATION_JSON_TYPE).buildGet().invoke(String.class);

		if (data == null || "".equals(data) || NULL.equals(data)) {
			return null;
		}

		JsonParser jsonParser = new JsonParser();

		JsonArray jsonElement = (JsonArray) jsonParser.parse(data);

		if (jsonElement != null && jsonElement.size() > 0) {

			List<Object> list = new ArrayList<Object>();

			for (JsonElement element : jsonElement) {

				Object object = new Gson().fromJson(element, type);

				if (object != null) {
					list.add(object);
				}
			}
			return list;
		}
		else {
			return null;
		}

	}

	/**
	 * Obtener el bean devuelto por el servidor en la consulta getObject().
	 * 
	 * @param target
	 * @return
	 */
	private Entity getObject(WebTarget target, Class<? extends Entity> entity) {

		String data = target.request().accept(MediaType.APPLICATION_JSON_TYPE).buildGet().invoke(String.class);

		if (data == null || "".equals(data) || NULL.equals(data)) {
			return null;
		}

		JsonParser jsonParser = new JsonParser();

		JsonArray jsonElement = (JsonArray) jsonParser.parse(data);

		if (jsonElement != null && jsonElement.size() > 0) {

			for (JsonElement element : jsonElement) {

				Object object = new Gson().fromJson(element, entity);

				if (object instanceof Entity) {
					return (Entity)object;
				}
			}
		}
		return null;
	}
	
	/**
	 * Obtener el bean grabado
	 * 
	 * @param target
	 * @return
	 */
	private Entity save(WebTarget target, Entity bean) {

		Response r = target.request().accept(MediaType.APPLICATION_JSON_TYPE).post(javax.ws.rs.client.Entity.entity(gson.toJson(bean), MediaType.APPLICATION_JSON));
		if (r != null) {
			String jsonLine = r.readEntity(String.class);
			if (jsonLine != null) {
				Object object = gson.fromJson(jsonLine, bean.getClass());
				if (object instanceof Entity) {
					return (Entity)object;
				}				
				return null;
			}
		}
		return null;
	}

	@Override
	public Type getType() {
		return DataProvider.Type.REST;
	}

}

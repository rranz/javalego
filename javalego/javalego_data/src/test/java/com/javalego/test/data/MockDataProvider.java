package com.javalego.test.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.javalego.data.DataProvider;
import com.javalego.entity.Entity;
import com.javalego.exception.LocalizedException;
import com.javalego.test.data.entities.Invoice;
import com.javalego.test.data.entities.Product;

/**
 * Acceso a datos simulados.
 * 
 * NOTA: sólo se implementan los métodos básicos requeridos para las pruebas de
 * concepto.
 * 
 * @author ROBERTO RANZ
 * 
 */
public class MockDataProvider implements DataProvider<Entity> {

	@Override
	public Collection<? extends Entity> getList(Class<? extends Entity> entity) throws LocalizedException {
		
		List<Entity> list = new ArrayList<Entity>();
		if (entity == Invoice.class) {
			list.add(new Invoice("Invoice 1", 1000.0));
			list.add(new Invoice("Invoice 2", 2000.0));
		}
		else if (entity == Product.class) {
			list.add(new Product("Product 1", "Description Product 1"));
			list.add(new Product("Product 2", "Description Product 2"));
		}
		
		return list;
	}

	@Override
	public Collection<? extends Entity> getList(Class<? extends Entity> entity, String where, String order) throws LocalizedException {
		return null;
	}

	@Override
	public Collection<? extends Entity> getList(Class<? extends Entity> entity, String where) throws LocalizedException {
		return null;
	}

	@Override
	public Collection<? extends Entity> getPagedList(Class<? extends Entity> entity, int startIndex, int count, String where, String order) throws LocalizedException {
		return null;
	}

	@Override
	public Collection<? extends Entity> getQuery(String name) throws LocalizedException {
		return null;
	}

	@Override
	public void load() throws LocalizedException {
	}

	@Override
	public Long getLong(String statement) throws LocalizedException {
		return null;
	}

	@Override
	public Entity getObject(String statement) throws LocalizedException {
		return null;
	}

	@Override
	public void delete(Entity bean) throws LocalizedException {
	}

	@Override
	public Entity save(Entity bean) throws LocalizedException {
		return null;
	}

	@Override
	public Entity getObject(Class<? extends Entity> entity, Long id) throws LocalizedException {
		return null;
	}

	@Override
	public Entity getObject(Class<? extends Entity> entity, String where) throws LocalizedException {
		return null;
	}

	@Override
	public Collection<?> getFieldValues(Class<? extends Entity> entity, String fieldName, String where, String order) throws LocalizedException {
		return null;
	}

	@Override
	public com.javalego.data.DataProvider.Type getType() {
		return DataProvider.Type.Mock;
	}

}

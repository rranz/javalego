package com.javalego.test.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Order;

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
public class MockDataProvider implements DataProvider {

	@Override
	public <T extends Entity<?>> T save(T entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends Entity<?>> T merge(T entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends Entity<?>> T delete(T entity) throws LocalizedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends Entity<?>> T find(Class<T> clazz, Serializable id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends Entity<?>> List<T> findByProperty(Class<T> clazz, String propertyName, Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends Entity<?>> List<T> findByProperty(Class<T> clazz, String propertyName, String value, MatchMode matchMode) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Entity<?>> List<T> findAll(Class<T> clazz) {

		List<Entity<?>> list = new ArrayList<Entity<?>>();
		if (clazz == Invoice.class) {
			list.add(new Invoice("Invoice 1", 1000.0));
			list.add(new Invoice("Invoice 2", 2000.0));
		}
		else if (clazz == Product.class) {
			list.add(new Product("Product 1", "Description Product 1"));
			list.add(new Product("Product 2", "Description Product 2"));
		}

		return (List<T>) list;
	}

	@Override
	public <T extends Entity<?>> List<T> findAll(Class<T> clazz, String where) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends Entity<?>> List<T> findAll(Class<T> clazz, String where, String order) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends Entity<?>> List<T> findAll(Class<T> clazz, Order order, String... propertiesOrder) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends Entity<?>> List<T> pagedList(Class<T> clazz, int startIndex, int count, String where, String order) throws LocalizedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> propertyValues(Class<?> clazz, String propertyName, String where, String order) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type getType() {
		return DataProvider.Type.Mock;
	}

	@Override
	public void init() {
	}

	@Override
	public Long count(Class<?> clazz, String where) {
		// TODO Auto-generated method stub
		return null;
	}

}

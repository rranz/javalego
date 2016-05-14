package com.javalego.data.provider.query;

/**
 * Filtro de datos en memoria.
 * 
 * @author ROBERTO RANZ
 *
 */
public class MemoryFilter<T> implements IQueryModel<T> {

	@Override
	public Iterable<T> findAll() {
		return null;
	}

}

package com.javalego.data.provider.query;

/**
 * Filtro basado en la ejecución de un Query con condiciones existente en un Repository.
 * 
 * @author ROBERTO RANZ
 *
 */
public class IRepositoryQueryFilter<T> implements IQueryModel<T> {

	@Override
	public Iterable<T> findAll() {
		return null;
	}

}

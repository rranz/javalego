package com.javalego.data.jpa;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javalego.entity.Entity;
import com.javalego.exception.LocalizedException;

/**
 * Acceso a datos JPA con Spring.
 * 
 * @author ROBERTO RANZ
 *
 */
@Service
@SuppressWarnings("unchecked")
//IMPORTANTE: si serializamos la clase se genera un error al añadir el bean al contexto porque se genera un proxy y obtenemos un error en el cast.
public class SpringJpaDao<T extends Entity> {
	
	/**
	 * EntityManager para consultas JPA.
	 */
	@PersistenceContext
	private EntityManager em;

	/**
	 * Lista de objetos de una clase
	 * @param entity
	 * @return
	 * @throws LocalizedException
	 */
	public Collection<T> getList(Class<? extends T> entity) throws LocalizedException {
		
		return em.createQuery("select p from " + entity.getSimpleName() + " as p").getResultList();
	}

	/**
	 * Lista de objetos de una clase
	 * @param entity
	 * @param where
	 * @param order
	 * @return
	 * @throws LocalizedException
	 */
	public Collection<T> getList(Class<? extends T> entity, String where, String order) throws LocalizedException {

		return em.createQuery("select p from " + entity.getSimpleName() + " as p" + getWhereOrder(where, order)).getResultList();
	}

	/**
	 * Lista de objetos de una clase
	 * @param entity
	 * @param where
	 * @return
	 * @throws LocalizedException
	 */
	public Collection<T> getList(Class<? extends T> entity, String where) throws LocalizedException {
		
		return em.createQuery("select p from " + entity.getSimpleName() + " as p" + getWhereOrder(where, null)).getResultList();
	}

	/**
	 * Lista paginada de objetos de una clase
	 * @param entity
	 * @param startIndex
	 * @param count
	 * @param where
	 * @param order
	 * @return
	 * @throws LocalizedException
	 */
	public Collection<T> getPagedList(Class<? extends T> entity, int startIndex, int count, String where, String order) throws LocalizedException {

		Query query = em.createQuery("select p from " + entity.getSimpleName() + " as p" + getWhereOrder(where, order));
		
		query.setFirstResult(startIndex);
		
		query.setMaxResults(count);
		
		return query.getResultList();
	}

	/**
	 * Incluir where y order en sentencia JQL.
	 * @param where
	 * @param order
	 * @return
	 */
	private String getWhereOrder(String where, String order) {
		
		return (where != null ? " where " + where : "") + (order != null ? " order by " + order : "") ;
	}

	/**
	 * Obtener el valor long de una consulta JPA.
	 * @param statement
	 * @return
	 */
	public Long getLong(String statement) {

		Object value = getObject(statement);
		if (value instanceof Long) {
			return (Long)value;
		}
		else {
			return 0L;
		}
	}

	/**
	 * Obtener un objeto a partir de una sentencia JQL.
	 * @param statement
	 * @return
	 */
	public T getObject(String statement) {
		try {
			return (T) em.createQuery(statement).getSingleResult();
		}
		catch (NoResultException e) {
			return null;
		}
	}

	/**
	 * Buscar una entidad por su id.
	 * @param entity
	 * @param id
	 * @return
	 */
	public T getObject(Class<? extends T> entity, Long id) {
		try {
			return (T) em.createQuery("from " + entity.getCanonicalName() + " e where e." + T.DEFAULT_ID + " = " + id).getSingleResult();
		}
		catch (NoResultException e) {
			return null;
		}
	}	
	
	/**
	 * Buscar una entidad por su id.
	 * @param entity
	 * @param where
	 * @return
	 */
	public T getObject(Class<? extends T> entity, String where) {
		try {
			return (T) em.createQuery("from " + entity.getCanonicalName() + (where != null ? " where " + where : "")).getSingleResult();
		}
		catch (NoResultException e) {
			return null;
		}
	}	
	
	/**
	 * Eliminar bean
	 * @param bean
	 */
	@Transactional(readOnly=false)
	public void delete(T bean) {
		
		bean = (T) em.find(bean.getClass(), bean.getId());
		
		em.remove(bean);
		
		em.flush();
	}

	/**
	 * Persitir bean
	 * @param bean
	 * @return
	 */
	@Transactional(readOnly=false)
	public T save(T bean) {
		
		if (bean.getId() != null) {
			em.merge(bean);
		}
		else {
			em.persist(bean);
		}
		em.flush();
		
		return bean;
	}

	/**
	 * Obtener la lista de valores de los registros de un campo.
	 * @param entity
	 * @param fieldName
	 * @param where
	 * @param order
	 * @return
	 */
	public Collection<?> getFieldValues(Class<? extends T> entity, String fieldName, String where, String order) {
		
		if (where == null) {
			where = fieldName + " is not null";
		}
		else {
			where = fieldName + " is not null" + " and " + where;
		}
		
		Query query = em.createQuery("select " + fieldName + " from " + entity.getSimpleName() + " " + getWhereOrder(where, order));
		
		return (Collection<?>)query.getResultList();
	}
	
}

package com.javalego.data.jpa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

import com.javalego.entity.Entity;
import com.javalego.exception.LocalizedException;

/**
 * JPA (Java Persistence API)
 * <p>
 * Proveedor de datos basado en el estándar de persistencia Java EE JPA.
 * <p>
 * Podemos usar cualquiera de los proveedores de referencia: OpenJPA, Hibernate, EclipseLink, ... incluyendo su
 * dependencia en el proyecto.
 * 
 * @author ROBERTO RANZ
 * 
 */
public abstract class AbstractJpaProvider implements JpaProvider
{
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public <T extends Entity<?>> T save(T entity)
	{
		if (entity.getId() != null)
		{
			return getEntityManager().merge(entity);
		}
		else
		{
			getEntityManager().persist(entity);
			return entity;
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public <T extends Entity<?>> T merge(T entity)
	{
		return getEntityManager().merge(entity);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public <T extends Entity<?>> T delete(T entity)
	{
		T result = merge(entity);
		getEntityManager().remove(result);
		return result;		
	}

	@Override
	public <T extends Entity<?>> T find(Class<T> clazz, Serializable id)
	{
		return getEntityManager().find(clazz, id);
	}

	@Override
	public <T extends Entity<?>> List<T> findByProperty(Class<T> clazz, String propertyName, Object value)
	{
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

		CriteriaQuery<T> cq = cb.createQuery(clazz);

		Root<T> root = cq.from(clazz);

		cq.where(cb.equal(root.get(propertyName), value));

		return getEntityManager().createQuery(cq).getResultList();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public <T extends Entity<?>> List<T> findByProperty(Class<T> clazz, String propertyName, String value,
		MatchMode matchMode)
	{
		// convert the value String to lowercase
		value = value.toLowerCase();

		if (MatchMode.START.equals(matchMode))
		{
			value = value + "%";
		}
		else if (MatchMode.END.equals(matchMode))
		{
			value = "%" + value;
		}
		else if (MatchMode.ANYWHERE.equals(matchMode))
		{
			value = "%" + value + "%";
		}

		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

		CriteriaQuery<T> cq = cb.createQuery(clazz);

		Root root = cq.from(clazz);

		cq.where(cb.like(cb.lower(root.get(propertyName)), value));

		return getEntityManager().createQuery(cq).getResultList();
	}

	@Override
	public <T extends Entity<?>> List<T> findAll(Class<T> clazz)
	{
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

		CriteriaQuery<T> cq = cb.createQuery(clazz);

		cq.from(clazz);

		return getEntityManager().createQuery(cq).getResultList();
	}

	@Override
	public <T extends Entity<?>> List<T> findAll(Class<T> clazz, Order order, String... propertiesOrder)
	{
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

		CriteriaQuery<T> cq = cb.createQuery(clazz);

		Root<T> root = cq.from(clazz);

		List<Order> orders = new ArrayList<>();

		for (String propertyOrder : propertiesOrder)
		{
			if (order.isAscending())
			{
				orders.add(cb.asc(root.get(propertyOrder)));
			}
			else
			{
				orders.add(cb.desc(root.get(propertyOrder)));
			}
		}
		cq.orderBy(orders);

		return getEntityManager().createQuery(cq).getResultList();
	}

	@Override
	public <T extends Entity<?>> List<T> findAll(Class<T> clazz, String where)
	{
		return findAll(clazz, where, null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Entity<?>> List<T> findAll(Class<T> clazz, String where, String order)
	{
		return getEntityManager()
			.createQuery("select e from " + clazz.getSimpleName() + " as e" + getWhereOrder(where, order))
			.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Entity<?>> List<T> pagedList(Class<T> clazz, int startIndex, int count, String where,
		String order) throws LocalizedException
	{
		Query query = getEntityManager()
			.createQuery("select e from " + clazz.getSimpleName() + " as e" + getWhereOrder(where, order));
		query.setFirstResult(startIndex);
		query.setMaxResults(count);

		return query.getResultList();
	}

	@Override
	public List<?> propertyValues(Class<?> clazz, String propertyName, String where, String order)
	{
		if (where == null)
		{
			where = propertyName + " is not null";
		}
		else
		{
			where = propertyName + " is not null" + " and " + where;
		}

		Query query = getEntityManager().createQuery(
			"select " + propertyName + " from " + clazz.getSimpleName() + " " + getWhereOrder(where, order));

		return (List<?>) query.getResultList();
	}

	/**
	 * Incluir where y order en sentencia JQL.
	 * 
	 * @param where
	 * @param order
	 * @return
	 */
	private String getWhereOrder(String where, String order)
	{
		return (where != null ? " where " + where : "") + (order != null ? " order by " + order : "");
	}

	@Override
	public Type getType()
	{
		return Type.JPA;
	}

	@Override
	public void init()
	{
	}

	@Override
	public Long count(Class<?> clazz, String where)
	{
		Object value = getEntityManager()
			.createQuery("select count(*) from " + clazz.getCanonicalName() + (where != null ? " where " + where : ""))
			.getSingleResult();
		return new Long(value != null ? value.toString() : "0");
	}
}

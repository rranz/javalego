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

import com.javalego.data.DataProviderErrors;
import com.javalego.data.DataProviderException;
import com.javalego.entity.Entity;

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
	public <T extends Entity<?>> T save(T entity) throws DataProviderException
	{
		try
		{
			if (entity.getId() != null)
			{
				return getEntityManager().merge(entity);
			}
			else
			{
				getEntityManager().persist(entity);
			}
			return entity;
		}
		catch (Exception e)
		{
			throw new DataProviderException(DataProviderErrors.PERSIST, e);
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public <T extends Entity<?>> T merge(T entity) throws DataProviderException
	{
		try
		{
			return getEntityManager().merge(entity);
		}
		catch (Exception e)
		{
			throw new DataProviderException(DataProviderErrors.MERGE, e);
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public <T extends Entity<?>> T delete(T entity) throws DataProviderException
	{
		T result = null;
		try
		{
			result = merge(entity);
			getEntityManager().remove(result);
		}
		catch (Exception e)
		{
			throw new DataProviderException(DataProviderErrors.DELETE, e);
		}
		return result;
	}

	@Override
	public <T extends Entity<?>> T find(Class<T> clazz, Serializable id) throws DataProviderException
	{
		try
		{
			return getEntityManager().find(clazz, id);
		}
		catch (Exception e)
		{
			throw new DataProviderException(DataProviderErrors.FIND_ID, e);
		}
	}

	@Override
	public <T extends Entity<?>> List<T> findByProperty(Class<T> clazz, String propertyName, Object value)
		throws DataProviderException
	{
		try
		{
			CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

			CriteriaQuery<T> cq = cb.createQuery(clazz);

			Root<T> root = cq.from(clazz);

			cq.where(cb.equal(root.get(propertyName), value));

			return getEntityManager().createQuery(cq).getResultList();
		}
		catch (Exception e)
		{
			throw new DataProviderException(DataProviderErrors.FIND, e);
		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public <T extends Entity<?>> List<T> findByProperty(Class<T> clazz, String propertyName, String value,
		MatchMode matchMode) throws DataProviderException
	{
		try
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
		catch (Exception e)
		{
			throw new DataProviderException(DataProviderErrors.FIND, e);
		}
	}

	@Override
	public <T extends Entity<?>> List<T> findAll(Class<T> clazz) throws DataProviderException
	{
		try
		{
			CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

			CriteriaQuery<T> cq = cb.createQuery(clazz);

			cq.from(clazz);

			return getEntityManager().createQuery(cq).getResultList();
		}
		catch (Exception e)
		{
			throw new DataProviderException(DataProviderErrors.FIND, e);
		}
	}

	@Override
	public <T extends Entity<?>> List<T> findAll(Class<T> clazz, Order order, String... propertiesOrder)
		throws DataProviderException
	{
		try
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
		catch (Exception e)
		{
			throw new DataProviderException(DataProviderErrors.FIND, e);
		}
	}

	@Override
	public <T extends Entity<?>> List<T> findAll(Class<T> clazz, String where) throws DataProviderException
	{
		return findAll(clazz, where, null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Entity<?>> List<T> findAll(Class<T> clazz, String where, String order)
		throws DataProviderException
	{
		try
		{
			return getEntityManager()
				.createQuery("select e from " + clazz.getSimpleName() + " as e" + getWhereOrder(where, order))
				.getResultList();
		}
		catch (Exception e)
		{
			throw new DataProviderException(DataProviderErrors.FIND, e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Entity<?>> List<T> pagedList(Class<T> clazz, int startIndex, int count, String where,
		String order) throws DataProviderException
	{
		try
		{
			Query query = getEntityManager()
				.createQuery("select e from " + clazz.getSimpleName() + " as e" + getWhereOrder(where, order));
			query.setFirstResult(startIndex);
			query.setMaxResults(count);

			return query.getResultList();
		}
		catch (Exception e)
		{
			throw new DataProviderException(DataProviderErrors.FIND, e);
		}
	}

	@Override
	public List<?> propertyValues(Class<?> clazz, String propertyName, String where, String order)
		throws DataProviderException
	{
		try
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
		catch (Exception e)
		{
			throw new DataProviderException(DataProviderErrors.FIND, e);
		}
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
	public Long count(Class<?> clazz, String where) throws DataProviderException
	{
		try
		{
			Object value = getEntityManager()
				.createQuery(
					"select count(*) from " + clazz.getCanonicalName() + (where != null ? " where " + where : ""))
				.getSingleResult();
			return new Long(value != null ? value.toString() : "0");
		}
		catch (NumberFormatException e)
		{
			throw new DataProviderException(DataProviderErrors.FIND, e);
		}
	}
}

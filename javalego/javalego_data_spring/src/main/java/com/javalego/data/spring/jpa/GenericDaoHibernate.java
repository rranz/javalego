package com.javalego.data.spring.jpa;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Order;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javalego.data.DataProvider;
import com.javalego.data.DataProviderErrors;
import com.javalego.data.DataProviderException;
import com.javalego.entity.Entity;
import com.javalego.exception.LocalizedException;

/**
 * A generic DAO implementation using Hibernate's Session directly, not JPA.
 * 
 * <p>
 * The primary noticeable difference is the use of "hibernate criteria" instead of "JPA criteria".
 * 
 */
@Service
public class GenericDaoHibernate implements DataProvider
{
	// take a look at the getSession() method.
	// This is not used "directly".
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional(readOnly = false)
	public <T extends Entity<?>> T save(T entity) throws DataProviderException
	{
		try
		{
			getSession().saveOrUpdate(entity);
			return entity;
		}
		catch (Exception e)
		{
			throw new DataProviderException(DataProviderErrors.PERSIST, e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = false)
	public <T extends Entity<?>> T merge(T entity) throws DataProviderException
	{
		try
		{
			return (T) getSession().merge(entity);
		}
		catch (Exception e)
		{
			throw new DataProviderException(DataProviderErrors.MERGE, e);
		}
	}

	@Override
	@Transactional(readOnly = false)
	public <T extends Entity<?>> T delete(T entity) throws DataProviderException
	{
		try
		{
			Entity<?> result = entityManager.merge(entity);
			entityManager.remove(result);
			return entity;
		}
		catch (Exception e)
		{
			throw new DataProviderException(DataProviderErrors.DELETE, e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Entity<?>> T find(Class<T> clazz, Serializable id) throws DataProviderException
	{
		try
		{
			return (T) getSession().get(clazz, id);
		}
		catch (Exception e)
		{
			throw new DataProviderException(DataProviderErrors.FIND_ID, e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Entity<?>> List<T> findByProperty(Class<T> clazz, String propertyName, Object value)
		throws DataProviderException
	{
		try
		{
			return getSession().createCriteria(clazz).add(Restrictions.eq(propertyName, value)).list();
		}
		catch (HibernateException e)
		{
			throw new DataProviderException(DataProviderErrors.FIND, e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Entity<?>> List<T> findByProperty(Class<T> clazz, String propertyName, String value,
		MatchMode matchMode) throws DataProviderException
	{
		try
		{
			if (matchMode != null)
			{
				return getSession().createCriteria(clazz)
					.add(Restrictions.ilike(propertyName, value, getMatchMode(matchMode))).list();
			}
			else
			{
				return getSession().createCriteria(clazz)
					.add(Restrictions.ilike(propertyName, value, org.hibernate.criterion.MatchMode.EXACT)).list();
			}
		}
		catch (HibernateException e)
		{
			throw new DataProviderException(DataProviderErrors.FIND, e);
		}
	}

	/**
	 * Convert to hibernate MatchMode.
	 * 
	 * @param matchMode
	 * @return criterion
	 */
	private org.hibernate.criterion.MatchMode getMatchMode(MatchMode matchMode)
	{

		if (matchMode == MatchMode.ANYWHERE)
		{
			return org.hibernate.criterion.MatchMode.ANYWHERE;
		}
		else if (matchMode == MatchMode.END)
		{
			return org.hibernate.criterion.MatchMode.END;
		}
		else if (matchMode == MatchMode.EXACT)
		{
			return org.hibernate.criterion.MatchMode.EXACT;
		}
		else if (matchMode == MatchMode.START)
		{
			return org.hibernate.criterion.MatchMode.START;
		}
		else
		{
			return org.hibernate.criterion.MatchMode.ANYWHERE;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Entity<?>> List<T> findAll(Class<T> clazz) throws DataProviderException
	{
		try
		{
			return getSession().createCriteria(clazz).list();
		}
		catch (HibernateException e)
		{
			throw new DataProviderException(DataProviderErrors.FIND, e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Entity<?>> List<T> findAll(Class<T> clazz, Order order, String... propertiesOrder)
		throws DataProviderException
	{
		try
		{
			Criteria criteria = getSession().createCriteria(clazz);

			for (String propertyOrder : propertiesOrder)
			{
				if (order.isAscending())
				{
					criteria.addOrder(org.hibernate.criterion.Order.asc(propertyOrder));
				}
				else
				{
					criteria.addOrder(org.hibernate.criterion.Order.desc(propertyOrder));
				}
			}

			return criteria.list();
		}
		catch (HibernateException e)
		{
			throw new DataProviderException(DataProviderErrors.FIND, e);
		}
	}

	/**
	 * Session
	 * 
	 * @return
	 */
	protected Session getSession()
	{
		return ((Session) entityManager.getDelegate()).getSessionFactory().openSession();
	}

	@Override
	public <T extends Entity<?>> List<T> findAll(Class<T> clazz, String where) throws DataProviderException
	{
		try
		{
			return findAll(clazz, where, null);
		}
		catch (Exception e)
		{
			throw new DataProviderException(DataProviderErrors.FIND, e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Entity<?>> List<T> findAll(Class<T> clazz, String where, String order)
	{
		return getSession()
			.createQuery("select e from " + clazz.getSimpleName() + " as e" + getWhereOrder(where, order)).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Entity<?>> List<T> pagedList(Class<T> clazz, int startIndex, int count, String where,
		String order) throws DataProviderException
	{
		try
		{
			Query query = getSession()
				.createQuery("select e from " + clazz.getSimpleName() + " as e" + getWhereOrder(where, order));
			query.setFirstResult(startIndex);
			query.setMaxResults(count);
			return query.list();
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

			Query query = getSession().createQuery(
				"select " + propertyName + " from " + clazz.getSimpleName() + " " + getWhereOrder(where, order));

			return (List<?>) query.list();
		}
		catch (Exception e)
		{
			throw new DataProviderException(DataProviderErrors.FIND, e);
		}
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
			Object value = getSession()
				.createQuery(
					"select count(*) from " + clazz.getCanonicalName() + (where != null ? " where " + where : ""))
				.uniqueResult();
			return new Long(value != null ? value.toString() : "0");
		}
		catch (NumberFormatException e)
		{
			throw new DataProviderException(DataProviderErrors.FIND, e);
		}
	}
}

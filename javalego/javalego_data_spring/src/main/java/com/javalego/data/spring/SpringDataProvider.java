package com.javalego.data.spring;

import java.io.Serializable;
import java.util.List;

import javax.persistence.criteria.Order;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;

import com.javalego.data.DataProvider;
import com.javalego.data.DataProviderException;
import com.javalego.entity.Entity;
import com.javalego.errors.CommonErrors;
import com.javalego.exception.LocalizedException;

/**
 * Adaptador de acceso a datos para Spring Data.
 * 
 * @author ROBERTO RANZ
 * 
 */
public class SpringDataProvider implements DataProvider
{
	private static final Logger logger = Logger.getLogger(SpringDataProvider.class);

	/**
	 * Clase anotada donde se configura el contexto de aplicación para evitar el uso de archivos xml.
	 */
	private Class<?> application;

	/**
	 * Contexto de aplicación.
	 */
	private GenericApplicationContext context;

	/**
	 * Acceso a datos JPA
	 */
	private DataProvider jpaDao;

	/**
	 * Constructor
	 * 
	 * @param application
	 *            Clase anotada que gestiona la configuración de Spring {@link Configuration}.
	 */
	public SpringDataProvider(Class<?> application)
	{
		this.application = application;
	}

	/**
	 * Constructor
	 * 
	 * @param context
	 */
	public SpringDataProvider(GenericApplicationContext context)
	{
		this.context = context;
	}

	/**
	 * Contexto Spring IoC
	 * 
	 * @return
	 */
	public GenericApplicationContext getContext()
	{
		return context;
	}

	/**
	 * Contexto de aplicación.
	 * 
	 * @param context
	 */
	public void setContext(GenericApplicationContext context)
	{
		this.context = context;
	}

	/**
	 * Cargar contexto de aplicación.
	 */
	@Override
	public void init()
	{
		// Cargar contexto de aplicación.
		if (context == null && application != null)
		{
			try
			{
				context = new AnnotationConfigApplicationContext(application);
			}
			catch (Exception e)
			{
				logger.error(new LocalizedException(e, CommonErrors.DATABASE_ERROR).getLocalizedMessage());
			}
		}

		// Inicializar JPA buscando el bean GenericDaoJpa en el contexto o
		// registrando el bean en el contexto para posteriormente recuperarlo.
		if (jpaDao == null)
		{
			try
			{
				jpaDao = (DataProvider) context.getBean("jpaDao"); //GenericDaoHibernate.class);
			}
			catch (NoSuchBeanDefinitionException e)
			{
				jpaDao = (DataProvider) getBean(DataProvider.class);
			}
		}
	}

	/**
	 * Obtener el dao de acceso a los datos y, en el caso de haberse producido un error en la carga inicial de la
	 * conexión con la fuente de datos, se reintentará la conexión.
	 * 
	 * @return
	 */
	private DataProvider getDao()
	{
		if (jpaDao == null)
		{
			init();
		}
		return jpaDao;
	}

	/**
	 * Obtener un bean en spring realizando una carga dinámica del Bean en Spring si no existe o no se ha declarado en
	 * los archivos de contexto de Spring.
	 * 
	 * @param type
	 * @return
	 */
	public Object getBean(Class<?> type)
	{
		String beanName = type.getSimpleName();

		beanName = beanName.substring(0, 1).toLowerCase() + beanName.substring(1);

		try
		{
			return context.getBean(type);
		}
		catch (Exception e)
		{
			registerBean(beanName, type);
			return context.getBean(type);
		}
	}

	/**
	 * Carga dinámica del bean en el contexto de Spring
	 * 
	 * @param beanName
	 * @param type
	 */
	private void registerBean(String beanName, Class<?> type)
	{
		AutowireCapableBeanFactory factory = context.getAutowireCapableBeanFactory();

		BeanDefinitionRegistry registry = (BeanDefinitionRegistry) factory;

		GenericBeanDefinition bd = new GenericBeanDefinition();

		bd.setBeanClass(type);

		registry.registerBeanDefinition(beanName, bd);
	}

	@Override
	public Type getType()
	{
		return DataProvider.Type.Spring_Data;
	}

	@Override
	public <T extends Entity<?>> T save(T entity) throws DataProviderException
	{
		return getDao().save(entity);
	}

	@Override
	public <T extends Entity<?>> T merge(T entity) throws DataProviderException
	{
		return getDao().merge(entity);
	}

	@Override
	public <T extends Entity<?>> T delete(T entity) throws DataProviderException
	{
		return getDao().delete(entity);
	}

	@Override
	public <T extends Entity<?>> T find(Class<T> clazz, Serializable id) throws DataProviderException
	{
		return getDao().find(clazz, id);
	}

	@Override
	public <T extends Entity<?>> List<T> findByProperty(Class<T> clazz, String propertyName, Object value) throws DataProviderException
	{
		return getDao().findByProperty(clazz, propertyName, value);
	}

	@Override
	public <T extends Entity<?>> List<T> findByProperty(Class<T> clazz, String propertyName, String value,
		MatchMode matchMode) throws DataProviderException
	{
		return getDao().findByProperty(clazz, propertyName, value, matchMode);
	}

	@Override
	public <T extends Entity<?>> List<T> findAll(Class<T> clazz) throws DataProviderException
	{
		return getDao().findAll(clazz);
	}

	@Override
	public <T extends Entity<?>> List<T> findAll(Class<T> clazz, String where) throws DataProviderException
	{
		return getDao().findAll(clazz, where);
	}

	@Override
	public <T extends Entity<?>> List<T> findAll(Class<T> clazz, String where, String order) throws DataProviderException
	{
		return getDao().findAll(clazz, where, order);
	}

	@Override
	public <T extends Entity<?>> List<T> findAll(Class<T> clazz, Order order, String... propertiesOrder) throws DataProviderException
	{
		return getDao().findAll(clazz, order, propertiesOrder);
	}

	@Override
	public <T extends Entity<?>> List<T> pagedList(Class<T> clazz, int startIndex, int count, String where,
		String order) throws DataProviderException
	{
		return getDao().pagedList(clazz, startIndex, count, where, order);
	}

	@Override
	public List<?> propertyValues(Class<?> clazz, String propertyName, String where, String order) throws DataProviderException
	{
		return getDao().propertyValues(clazz, propertyName, where, order);
	}

	@Override
	public Long count(Class<?> clazz, String where) throws DataProviderException
	{
		return getDao().count(clazz, where);
	}
}

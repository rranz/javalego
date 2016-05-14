package com.javalego.data.jpa;

import java.util.Collection;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

import com.javalego.data.DataProvider;
import com.javalego.entity.Entity;
import com.javalego.exception.CommonErrors;
import com.javalego.exception.LocalizedException;

/**
 * Proveedor de Datos Spring Data.
 * 
 * @author ROBERTO RANZ
 * 
 */
public class SpringDataProvider implements DataProvider<Entity> {

	//private static final Logger logger = Logger.getLogger(SpringDataProvider.class);

	/**
	 * Clase anotada donde se configura el contexto de aplicación para evitar el
	 * uso de archivos xml.
	 */
	private Class<?> application;

	/**
	 * Contexto de aplicación.
	 */
	private AnnotationConfigApplicationContext context;

	/**
	 * Acceso a datos JPA
	 */
	private SpringJpaDao<Entity> jpaDao;

	/**
	 * Constructor
	 * 
	 * @param application
	 *            Clase anotada que gestiona la configuración de Spring
	 *            {@link Configuration}.
	 */
	public SpringDataProvider(Class<?> application) {
		this.application = application;
	}

	/**
	 * Constructor
	 * 
	 * @param context
	 */
	public SpringDataProvider(AnnotationConfigApplicationContext context) {
		this.context = context;
	}

	/**
	 * Contexto Spring IoC
	 * 
	 * @return
	 */
	public AnnotationConfigApplicationContext getContext() {
		return context;
	}

	@Override
	public Collection<? extends Entity> getList(Class<? extends Entity> entity) throws LocalizedException {
		return getDao().getList(entity);
	}

	@Override
	public Entity getObject(Class<? extends Entity> entity, Long id) throws LocalizedException {
		return getDao().getObject(entity, id);
	}

	@Override
	public Collection<? extends Entity> getList(Class<? extends Entity> entity, String where, String order) throws LocalizedException {
		return getDao().getList(entity, where, order);
	}

	@Override
	public Collection<? extends Entity> getList(Class<? extends Entity> entity, String where) throws LocalizedException {
		return getDao().getList(entity, where);
	}

	@Override
	public Collection<? extends Entity> getPagedList(Class<? extends Entity> entity, int startIndex, int count, String where, String order) throws LocalizedException {
		return getDao().getPagedList(entity, startIndex, count, where, order);
	}

	@Override
	public Collection<Entity> getQuery(String name)  throws LocalizedException {
		return null;
	}

	@Override
	public Long getLong(String statement) throws LocalizedException {
		return getDao().getLong(statement);
	}

	@Override
	public Entity getObject(String statement) throws LocalizedException  {
		return getDao().getObject(statement);
	}

	@Override
	public void delete(Entity bean)  throws LocalizedException {
		getDao().delete(bean);
	}

	@Override
	public Entity save(Entity bean) throws LocalizedException {
		return getDao().save(bean);
	}

	@Override
	public Entity getObject(Class<? extends Entity> entity, String where) throws LocalizedException {
		return getDao().getObject(entity, where);
	}

	@Override
	public Collection<?> getFieldValues(Class<? extends Entity> entity, String fieldName, String where, String order) throws LocalizedException {
		return getDao().getFieldValues(entity, fieldName, where, order);
	}

	/**
	 * Contexto de aplicación.
	 * 
	 * @param context
	 */
	public void setContext(AnnotationConfigApplicationContext context) {
		this.context = context;
	}

	/**
	 * Cargar contexto de aplicación.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void load() throws LocalizedException {

		// Cargar contexto de aplicación.
		if (context == null && application != null) {

			try {
				context = new AnnotationConfigApplicationContext(application);

				// Inicializar JPA
				if (jpaDao == null) {
					jpaDao = (SpringJpaDao<Entity>) getBean(SpringJpaDao.class);
				}
			}
			catch (Exception e) {
				throw new LocalizedException(e, CommonErrors.DATABASE_ERROR);
			}
		}
	}

	/**
	 * Obtener el dao de acceso a los datos y, en el caso de haberse producido
	 * un error en la carga inicial de la conexión con la fuente de datos, se
	 * reintentará la conexión.
	 * 
	 * @return
	 */
	private SpringJpaDao<Entity> getDao() throws LocalizedException {
		if (jpaDao == null) {
			load();
		}
		return jpaDao;
	}

	/**
	 * Obtener un bean en spring realizando una carga dinámica del Bean en
	 * Spring si no existe o no se ha declarado en los archivos de contexto de
	 * Spring.
	 * 
	 * @param type
	 * @return
	 */
	public Object getBean(Class<?> type) {

		String beanName = type.getSimpleName();

		beanName = beanName.substring(0, 1).toLowerCase() + beanName.substring(1);

		try {
			return context.getBean(type);
		}
		catch (Exception e) {
			reloadBean(beanName, type);
			return context.getBean(type);
		}
	}

	/**
	 * Carga dinámica del bean en Spring
	 * 
	 * @param beanName
	 * @param type
	 */
	private void reloadBean(String beanName, Class<?> type) {

		AutowireCapableBeanFactory factory = context.getAutowireCapableBeanFactory();

		BeanDefinitionRegistry registry = (BeanDefinitionRegistry) factory;

		GenericBeanDefinition bd = new GenericBeanDefinition();

		bd.setBeanClass(type);

		registry.registerBeanDefinition(beanName, bd);
	}

	@Override
	public Type getType() {
		return DataProvider.Type.Spring_Data;
	}

}

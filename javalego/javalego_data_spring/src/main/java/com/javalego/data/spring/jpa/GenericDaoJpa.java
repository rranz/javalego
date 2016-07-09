package com.javalego.data.spring.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javalego.data.jpa.AbstractJpaProvider;
import com.javalego.entity.Entity;

/**
 * Spring JPA (Java Persistence API)
 * <p>
 * Proveedor de datos basado en el estándar de persistencia Java EE JPA.
 * <p>
 * Podemos usar cualquiera de los proveedores de referencia: OpenJPA, Hibernate, EclipseLink, ... incluyendo su
 * dependencia en el proyecto.
 * 
 * @author ROBERTO RANZ
 * 
 */
@Service
public class GenericDaoJpa extends AbstractJpaProvider
{
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional(readOnly=false)
	public <T extends Entity<?>> T save(T entity)
	{
		return super.save(entity);
	}

	@Override
	@Transactional(readOnly=false)
	public <T extends Entity<?>> T merge(T entity)
	{
		return super.merge(entity);
	}

	@Override
	@Transactional(readOnly=false)
	public <T extends Entity<?>> T delete(T entity)
	{
		return super.delete(entity);
	}	
	
	@Override
	public EntityManager getEntityManager()
	{
		return entityManager;
	}

	/**
	 * EntityManager
	 * @param entityManager
	 */
	public void setEntityManager(EntityManager entityManager)
	{
		this.entityManager = entityManager;
	}

}

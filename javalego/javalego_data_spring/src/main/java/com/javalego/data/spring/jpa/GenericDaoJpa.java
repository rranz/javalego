package com.javalego.data.spring.jpa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.javalego.data.DataProvider;
import com.javalego.data.jpa.AbstractJpaProvider;
import com.javalego.entity.Entity;
import com.javalego.exception.LocalizedException;

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

	/**
	 * EntityManager
	 * @return
	 */
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

	@Override
	public String getName()
	{
		return "JPA";
	}

	@Override
	public String getTitle()
	{
		return "Generic JPA";
	}

	@Override
	public String getDescription()
	{
		return null;
	}

}

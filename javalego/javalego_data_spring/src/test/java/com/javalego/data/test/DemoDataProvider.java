package com.javalego.data.test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.javalego.data.jpa.AbstractJpaProvider;

public class DemoDataProvider extends AbstractJpaProvider
{
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public EntityManager getEntityManager()
	{
		return entityManager;
	}

}

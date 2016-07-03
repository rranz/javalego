package com.javalego.demo.ejb;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.javalego.data.jpa.AbstractJpaProvider;

@Stateless
@LocalBean
public class ERPDataProvider extends AbstractJpaProvider
{
	@PersistenceContext(unitName="erp-unit")
	private EntityManager entityManager;

	@Override
	public EntityManager getEntityManager()
	{
		return entityManager;
	}

}

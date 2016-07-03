package com.javalego.data.jpa;

import javax.ejb.Local;
import javax.persistence.EntityManager;

import com.javalego.data.DataProvider;

/**
 * Proveedor de datos basado en JPA.
 * <p>
 * 
 * @see AbstractJpaProvider
 * 
 * @author ROBERTO
 *
 */
@Local
public interface JpaProvider extends DataProvider
{
	/**
	 * Entity manager
	 * 
	 * @return
	 */
	public EntityManager getEntityManager();

}

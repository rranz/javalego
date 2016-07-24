package com.javalego.demo.rest.department;

import javax.ejb.EJB;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ws.rs.Path;

import com.javalego.data.DataProvider;
import com.javalego.data.jpa.JpaProvider;
import com.javalego.demo.entities.Department;
import com.javalego.demo.rest.CRUDServicesRestImpl;
import com.javalego.entity.Entity;

@Singleton
@Lock(LockType.WRITE)
@Path("/department")
public class DepartmentServices extends CRUDServicesRestImpl
{
	@EJB
	protected JpaProvider dataProvider;
	
	@Override
	public Class<? extends Entity<?>> getEntityClass()
	{
		return Department.class;
	}

	@Override
	public DataProvider getDataProvider()
	{
		return dataProvider;
	}


}

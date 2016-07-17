package com.javalego.demo.rest.department;

import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ws.rs.Path;

import com.javalego.demo.entities.Department;
import com.javalego.demo.rest.CRUDServicesRestImpl;
import com.javalego.entity.Entity;

@Singleton
@Lock(LockType.WRITE)
@Path("/department")
public class DepartmentServices extends CRUDServicesRestImpl
{

	@Override
	public Class<? extends Entity<?>> getClazz()
	{
		return Department.class;
	}


}

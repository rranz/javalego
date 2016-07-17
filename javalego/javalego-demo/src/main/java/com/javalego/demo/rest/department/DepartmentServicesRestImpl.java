package com.javalego.demo.rest.department;

import javax.ejb.EJB;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ws.rs.core.Response;

import com.javalego.data.jpa.JpaProvider;
import com.javalego.demo.entities.Department;
import com.javalego.exception.LocalizedException;

/**
 * Outputs are copied because of the enhancement of OpenJPA.
 */
@Singleton
@Lock(LockType.WRITE)
public class DepartmentServicesRestImpl implements DepartmentServicesRest
{
	@EJB
	private JpaProvider dataProvider;

	@Override
	public Response create(String name)
	{
		return Response.ok(dataProvider.save(new Department(name))).build();
	}

	@Override
	public Response list()
	{
		return Response.ok(dataProvider.findAll(Department.class)).build();
	}

	@Override
	public Response find(long id)
	{
		return Response.ok(dataProvider.find(Department.class, id)).build();
	}

	@Override
	public Response delete(long id) throws LocalizedException
	{
		return Response.ok(dataProvider.delete(dataProvider.find(Department.class, id))).build();
	}

	@Override
	public Response update(Department department)
	{
		return Response.ok(dataProvider.save(department)).build();
	}

}

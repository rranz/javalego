package com.javalego.demo.rest.department;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.javalego.demo.entities.Department;
import com.javalego.exception.LocalizedException;

@Path("/department")
public interface DepartmentServicesRest
{
	@Path("/create")
	@POST
	Response create(@QueryParam("name") String name);

	@Path("/update")
	@PUT
	Response update(Department department);

	@Path("/list")
	@GET
	Response list();

	@Path("/find/{id}")
	@GET
	Response find(@PathParam("id") long id);

	@Path("/delete/{id}")
	@DELETE
	Response delete(long id) throws LocalizedException;

}
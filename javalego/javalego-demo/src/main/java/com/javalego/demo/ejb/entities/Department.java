package com.javalego.demo.ejb.entities;

import javax.persistence.Entity;

import com.javalego.entity.impl.EntityId;

@Entity
public class Department extends EntityId
{
	private String name;

	public String getName()
	{
		return name;
	}

	public void setName(String deptName)
	{
		this.name = deptName;
	}
}
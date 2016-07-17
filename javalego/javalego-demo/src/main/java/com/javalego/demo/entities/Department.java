package com.javalego.demo.entities;

import javax.persistence.Entity;

import com.javalego.entity.impl.EntityId;

@Entity
public class Department extends EntityId
{
	private String name;

	public Department() {
		
	}
	
	public Department(String name) {
		this.name = name;
	}
	
	public String getName()
	{
		return name;
	}

	public void setName(String deptName)
	{
		this.name = deptName;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
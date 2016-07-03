package com.javalego.demo.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.javalego.entity.impl.EntityId;

@Entity
public class Employee extends EntityId
{
	private String ename;

	private double salary;

	private String deg;

	@ManyToOne
	private Department department;

	public Employee(String ename, double salary, String deg)
	{
		super();
		this.ename = ename;
		this.salary = salary;
		this.deg = deg;
	}

	public Employee()
	{
		super();
	}

	public String getEname()
	{
		return ename;
	}

	public void setEname(String ename)
	{
		this.ename = ename;
	}

	public double getSalary()
	{
		return salary;
	}

	public void setSalary(double salary)
	{
		this.salary = salary;
	}

	public String getDeg()
	{
		return deg;
	}

	public void setDeg(String deg)
	{
		this.deg = deg;
	}

	public Department getDepartment()
	{
		return department;
	}

	public void setDepartment(Department department)
	{
		this.department = department;
	}
}
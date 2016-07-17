package entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Domain
{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String name = "Domain test";

	public Domain(String name)
	{
		this.name = name;
	}

	public Domain()
	{
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

}

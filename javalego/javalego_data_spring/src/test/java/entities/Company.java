package entities;

import javax.persistence.Entity;

import com.javalego.entity.impl.EntityId;

@Entity(name = "Company")
public class Company extends EntityId {

	private String name;

	public Company() {
	}

	public Company(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

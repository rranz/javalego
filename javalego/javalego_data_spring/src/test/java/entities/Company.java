package entities;

import javax.persistence.Entity;

import com.javalego.entity.impl.IdNumberEntityImpl;

@Entity
public class Company extends IdNumberEntityImpl {

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

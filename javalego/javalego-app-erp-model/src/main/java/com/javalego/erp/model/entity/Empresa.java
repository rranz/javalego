package com.javalego.erp.model.entity;

import javax.persistence.Entity;

@Entity
public class Empresa extends AbstractEmpresa {

	public Empresa() {}
	
	public Empresa(String nombre, String razon_social, String cif) {
		super(nombre, razon_social, cif);
	}

}

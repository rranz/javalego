package com.javalego.erp.model.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class ClienteContacto extends AbstractEmpresaContacto {

	@ManyToOne 
	@JoinColumn(name="empresa_id") 
	@NotNull
	private Cliente empresa;

	public Cliente getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Cliente empresa) {
		this.empresa = empresa;
	}

	@Override
	public String toString() {
		return getNombre() + (empresa != null ? " - " + empresa.getNombre() : "");
	}	

}

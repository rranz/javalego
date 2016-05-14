package com.javalego.erp.model.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class ProveedorContacto extends AbstractEmpresaContacto {

	@ManyToOne 
	@JoinColumn(name="empresa_id") 
	@NotNull
	private Proveedor empresa;

	public Proveedor getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Proveedor empresa) {
		this.empresa = empresa;
	}
	
	@Override
	public String toString() {
		return getNombre() + (empresa != null ? " - " + empresa.getNombre() : "");
	}	
}

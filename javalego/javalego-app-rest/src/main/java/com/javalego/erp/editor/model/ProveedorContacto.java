package com.javalego.erp.editor.model;

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
}

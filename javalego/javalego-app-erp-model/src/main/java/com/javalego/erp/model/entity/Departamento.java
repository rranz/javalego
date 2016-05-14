package com.javalego.erp.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.javalego.entity.impl.IdNumberEntityImpl;

@Entity
public class Departamento extends IdNumberEntityImpl {

	@Column(unique=true, length=100, nullable=false)
	private String nombre;

	private String anotaciones;

//	@ManyToOne 
//	@JoinColumn(name="empresa_id") 
//	@NotNull
//	private Empresa empresa;

//	public Empresa getEmpresa() {
//		return empresa;
//	}
//
//	public void setEmpresa(Empresa empresa) {
//		this.empresa = empresa;
//	}
	
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getAnotaciones() {
		return this.anotaciones;
	}

	public void setAnotaciones(String anotaciones) {
		this.anotaciones = anotaciones;
	}

	@Override
	public String toString() {
		return nombre;
	}

}

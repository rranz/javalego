package com.javalego.erp.editor.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.javalego.entity.impl.EntityId;

@Entity
public class ProductoTarifa extends EntityId {

	private String nombre;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="producto_id")
	private Producto producto;

	private double tarifa;
	
	private double estandar;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public double getTarifa() {
		return tarifa;
	}

	public void setTarifa(double tarifa) {
		this.tarifa = tarifa;
	}

	public double getEstandar() {
		return estandar;
	}

	public void setEstandar(double estandar) {
		this.estandar = estandar;
	}

}

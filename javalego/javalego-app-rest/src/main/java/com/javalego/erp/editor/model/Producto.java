package com.javalego.erp.editor.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.javalego.entity.impl.EntityId;

@Entity
public class Producto extends EntityId {

	@Column(length=100, unique=true, nullable=false)
	private String identificador;

	@Column(length=100, nullable=false)
	private String nombre;

	private String tipo;
	
	private boolean homologado;
	private boolean activo;
	private boolean compra;
	private boolean venta;
	private boolean almacenado;
	
	private String fabricante;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="proveedor_id")
	private Proveedor proveedor;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="categoria_id")
	private Categoria categoria;

	private String caracteristicas;
	
	private byte[] imagen;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public boolean isHomologado() {
		return homologado;
	}

	public void setHomologado(boolean homologado) {
		this.homologado = homologado;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public boolean isCompra() {
		return compra;
	}

	public void setCompra(boolean compra) {
		this.compra = compra;
	}

	public boolean isVenta() {
		return venta;
	}

	public void setVenta(boolean venta) {
		this.venta = venta;
	}

	public boolean isAlmacenado() {
		return almacenado;
	}

	public void setAlmacenado(boolean almacenado) {
		this.almacenado = almacenado;
	}

	public String getFabricante() {
		return fabricante;
	}

	public void setFabricante(String fabricante) {
		this.fabricante = fabricante;
	}

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public String getCaracteristicas() {
		return caracteristicas;
	}

	public void setCaracteristicas(String caracteristicas) {
		this.caracteristicas = caracteristicas;
	}

	public byte[] getImagen() {
		return imagen;
	}

	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}

	@Override
	public String toString() {
		return nombre + (proveedor != null ? " - " + proveedor.getNombre() : "");
	}

	
}

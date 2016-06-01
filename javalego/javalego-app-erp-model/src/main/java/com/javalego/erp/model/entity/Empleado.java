package com.javalego.erp.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.javalego.entity.impl.EntityId;

/**
 * Personas físicas registradas y disponibles para su inclusión en el resto de tablas (usuarios, contactos, etc.)
 */
@Entity
public class Empleado extends EntityId {

	@ManyToOne 
	@JoinColumn(name="departamento_id") 
	@NotNull
	private Departamento departamento;
	
	@ManyToOne 
	@JoinColumn(name="categoria_id") 
	@NotNull
	private CategoriaProfesional categoria;

	@ManyToOne 
	@JoinColumn(name="empresa_id") 
	@NotNull
	private Empresa empresa;

	private String nombre;

	private String nif;

	private Date alta = new Date();

	@Column(length=200)
	private String email;

	@Column(length=20)
	private String telefono;

	@Column(length=20)
	private String movil;
	
	@Lob
	@Column(length=100000)	
	private byte[] foto;
	
	/**
	 * Campo calculado que obtiene toda la información de las siguientes
	 * propiedades: ciudad, poblacion, calle, numero y piso.
	 */
	private String domicilio;

	@Column(length=100)
	private String nacionalidad;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public Date getAlta() {
		return alta;
	}

	public void setAlta(Date alta) {
		this.alta = alta;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getMovil() {
		return movil;
	}

	public void setMovil(String movil) {
		this.movil = movil;
	}

	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	public String getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}

	public String getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public CategoriaProfesional getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaProfesional categoria) {
		this.categoria = categoria;
	}

}

package com.javalego.test.office.excel;

import java.io.Serializable;

public class Contacto implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String nombre;

	private String telefono;
	
	private String email;

	private String localidad;
	
	private byte[] foto;
	
	private Long id;

	public Contacto() {}
	
	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getTelefono() {
		return telefono;
	}


	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getLocalidad() {
		return localidad;
	}


	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}


	public byte[] getFoto() {
		return foto;
	}


	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	@Override
	public String toString() {
		return nombre;
	}

	public Long getId() {
		return id;
	}

	public void setId(Object id) {
		this.id = (Long)id;
	}

}

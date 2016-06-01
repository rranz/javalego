package com.javalego.erp.editor.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.javalego.entity.impl.EntityId;

@MappedSuperclass
public abstract class AbstractEmpresa extends EntityId {
	
	@NotNull
	protected String nombre;

	@NotNull
	private String razon_social;
	
	@Size(min=6, max=20)
	@NotNull
	private String cif;

	private String url;
	
	private Date fecha_creacion;
	
	private String telefono;
	
	private String telefono2;
	
	private String email;
	
	private String email2;
	
	private String CNAE;
	
	private boolean pyme;
	
	private String seguridad_social;
	
	private String representante;
	
	private String representante_nif;
	
	private String representante2;
	
	private String representante2_nif;
	
	@NotNull
	private String pais;
	
	private String provincia;
	
	private String localidad;

	private String domicilio;

	private String codigo_postal;
	
	@Lob
	@Column(length=100000)
	private byte[] logo;
	
	public AbstractEmpresa() {}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getRazon_social() {
		return razon_social;
	}

	public void setRazon_social(String razon_social) {
		this.razon_social = razon_social;
	}

	public String getCif() {
		return cif;
	}

	public void setCif(String cif) {
		this.cif = cif;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getFecha_creacion() {
		return fecha_creacion;
	}

	public void setFecha_creacion(Date fecha_creacion) {
		this.fecha_creacion = fecha_creacion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getTelefono2() {
		return telefono2;
	}

	public void setTelefono2(String telefono2) {
		this.telefono2 = telefono2;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail2() {
		return email2;
	}

	public void setEmail2(String email2) {
		this.email2 = email2;
	}

	public String getCNAE() {
		return CNAE;
	}

	public void setCNAE(String cNAE) {
		CNAE = cNAE;
	}

	public boolean isPyme() {
		return pyme;
	}

	public void setPyme(boolean pyme) {
		this.pyme = pyme;
	}

	public String getSeguridad_social() {
		return seguridad_social;
	}

	public void setSeguridad_social(String seguridad_social) {
		this.seguridad_social = seguridad_social;
	}

	public String getRepresentante() {
		return representante;
	}

	public void setRepresentante(String representante) {
		this.representante = representante;
	}

	public String getRepresentante_nif() {
		return representante_nif;
	}

	public void setRepresentante_nif(String representante_nif) {
		this.representante_nif = representante_nif;
	}

	public String getRepresentante2() {
		return representante2;
	}

	public void setRepresentante2(String representante2) {
		this.representante2 = representante2;
	}

	public String getRepresentante2_nif() {
		return representante2_nif;
	}

	public void setRepresentante2_nif(String representante2_nif) {
		this.representante2_nif = representante2_nif;
	}

	public String getCodigo_postal() {
		return codigo_postal;
	}

	public void setCodigo_postal(String codigo_postal) {
		this.codigo_postal = codigo_postal;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}

	public byte[] getLogo() {
		return logo;
	}

	public void setLogo(byte[] logo) {
		this.logo = logo;
	}

	@Override
	public String toString() {
		return nombre;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

}

package entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;

import com.javalego.entity.impl.IdNumberEntityImpl;

@Entity
public class Empresa extends IdNumberEntityImpl {
	
	private String nombre;

	private String razon_social;
	
	private String cif;

	private String url;
	
	private Date fecha_creacion;
	
	private String telefono;
	
	private String telefono2;
	
	private String email;
	
	private String email2;
	
	private String fax;
	
	private String fax2;

	private String CNAE;
	
	private boolean pyme;
	
	private String seguridad_social;
	
	private String representante;
	
	private String representante_nif;
	
	private String representante2;
	
	private String representante2_nif;
	
	private String codigo_postal;
	
	private String localidad;

	/**
	 * Campo calculado que obtiene toda la información de las siguientes
	 * propiedades: ciudad, poblacion, calle, numero y piso.
	 */
	private String domicilio;

	private String tipo_calle;
	
	private String calle;
	
	private String calle_numero;
	
	private String calle_piso;
	
	@Lob
	@Column(length=100000)
	private byte[] logo;
	
//	@Column(columnDefinition="logo longblob")
//	private byte[] logo;
	
	private Date logo_fecha;
	
	private String logo_nombre;

	public Empresa() {}
	
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

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getFax2() {
		return fax2;
	}

	public void setFax2(String fax2) {
		this.fax2 = fax2;
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

	public String getTipo_calle() {
		return tipo_calle;
	}

	public void setTipo_calle(String tipo_calle) {
		this.tipo_calle = tipo_calle;
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public String getCalle_numero() {
		return calle_numero;
	}

	public void setCalle_numero(String calle_numero) {
		this.calle_numero = calle_numero;
	}

	public String getCalle_piso() {
		return calle_piso;
	}

	public void setCalle_piso(String calle_piso) {
		this.calle_piso = calle_piso;
	}

//	public byte[] getLogo() {
//		return logo;
//	}
//
//	public void setLogo(byte[] logo) {
//		this.logo = logo;
//	}

	public Date getLogo_fecha() {
		return logo_fecha;
	}

	public void setLogo_fecha(Date logo_fecha) {
		this.logo_fecha = logo_fecha;
	}

	public String getLogo_nombre() {
		return logo_nombre;
	}

	public void setLogo_nombre(String logo_nombre) {
		this.logo_nombre = logo_nombre;
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
}

package entities.address;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Direccion {

	@NotNull
	private Provincia provincia;

	@NotNull
	Ciudad ciudad;

	@Size(min = 5, max = 5)
	String codigo_postal;

	@Size(max = 200)
	String direccion;

	Vias via;

	@Size(max = 10)
	String puerta;

	@Size(max = 10)
	String escalera;

	@Size(max = 10)
	String bloque;

	public Provincia getProvincia() {
		return provincia;
	}

	public void setProvincia(Provincia provincia) {
		this.provincia = provincia;
	}

	public Ciudad getCiudad() {
		return ciudad;
	}

	public void setCiudad(Ciudad ciudad) {
		this.ciudad = ciudad;
	}

	public String getCodigo_postal() {
		return codigo_postal;
	}

	public void setCodigo_postal(String codigo_postal) {
		this.codigo_postal = codigo_postal;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Vias getVia() {
		return via;
	}

	public void setVia(Vias via) {
		this.via = via;
	}

	public String getPuerta() {
		return puerta;
	}

	public void setPuerta(String puerta) {
		this.puerta = puerta;
	}

	public String getEscalera() {
		return escalera;
	}

	public void setEscalera(String escalera) {
		this.escalera = escalera;
	}

	public String getBloque() {
		return bloque;
	}

	public void setBloque(String bloque) {
		this.bloque = bloque;
	}
	
	/**
	 * Obtener el formato de la dirección en formato Html
	 * @return
	 */
	public String toHtml() {
		
		StringBuffer text = new StringBuffer();
		text.append("<br>" + (provincia != null && provincia.getNombre() != null ? provincia.getNombre() : ""));
		text.append("<br>");
		text.append(ciudad != null && ciudad.getNombre() != null ? ciudad.getNombre() : "");
		text.append("<br>");
		text.append(via != null ? via : "");
		text.append(" " + (direccion != null ? direccion : ""));
		text.append(" " + (puerta != null ? puerta : ""));
		text.append(" " + (escalera != null ? escalera : ""));
		text.append(" " + (bloque != null ? bloque : ""));
		text.append("<br>" + (codigo_postal != null ? codigo_postal : ""));
		return text.toString();
	}

}

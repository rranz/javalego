package entities.address;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.javalego.entity.impl.IdNumberEntityImpl;
import com.javalego.model.validator.custom.UpperCase;

public class Provincia extends IdNumberEntityImpl {

	@NotNull
	@Size(max=100)
	@UpperCase
	@Column(unique=true)	
	private String nombre;
	
	@NotNull
	@Size(max=3)
	private String prefijo;
	
	@NotNull
	@Size(min=2,max=2)
	@Column(unique=true)
	private String codigo;

	public Provincia(String codigo, String nombre) {
		this.codigo = codigo;
		this.nombre = nombre;
	}

	public Provincia() {
	}

	public String getPrefijo() {
		return prefijo;
	}

	public void setPrefijo(String prefijo) {
		this.prefijo = prefijo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	} 

	@Override
	public String toString() {
		return nombre;
	}
}

package entities.address;

import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.javalego.entity.impl.IdNumberEntityImpl;
import com.javalego.model.validator.custom.UpperCase;

@Table(uniqueConstraints={@UniqueConstraint(columnNames={"provincia, nombre"})})
public class Ciudad extends IdNumberEntityImpl {
	
	@NotNull
	private Provincia provincia;

	@NotNull
	@Size(max=100)
	@UpperCase
	private String nombre;
	
	public Ciudad(Provincia provincia, String nombre) {
		this.provincia = provincia;
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Provincia getProvincia() {
		return provincia;
	}

	public void setProvincia(Provincia provincia) {
		this.provincia = provincia;
	}

	@Override
	public String toString() {
		return nombre;
	}
}

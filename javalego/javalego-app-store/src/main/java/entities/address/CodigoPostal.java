package entities.address;

import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.javalego.entity.impl.IdNumberEntityImpl;

@Table(uniqueConstraints={@UniqueConstraint(columnNames={"provincia, codigo"})})
public class CodigoPostal  extends IdNumberEntityImpl {

	@NotNull
	private Provincia provincia;
	
	@NotNull
	@Size(min=3, max=3)
	private String codigo;

	public Provincia getProvincia() {
		return provincia;
	}

	public void setProvincia(Provincia provincia) {
		this.provincia = provincia;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

}

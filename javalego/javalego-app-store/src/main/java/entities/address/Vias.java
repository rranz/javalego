package entities.address;

public enum Vias {
	AUTOPISTA("Autopista"), 
	AUTOVIA("Autovía"), 
	AVENIDA("Avenida"),
	BULEVAR("Bulevar"), 
	CALLE("Calle"), 
	CALLEJON("Callejón"), 
	CAMINO("Camino"), 
	CARRERA("Carrera"), 
	CARRETERA("Carretera"), 
	GLORIETA("Glorieta"), 
	JARDIN("Jardín"), 
	PARQUE("Parque"), 
	PASAJE("Pasaje"), 
	PASEO("Paseo"), 
	PLAZA("Plaza"), 
	PUENTE("Puente"), 
	RAMBLA("Rambla"), 
	RONDA("Ronda"), 
	SECTOR("Sector"), 
	TRAVESIA("Travesía"), 
	URBANIZACION("Urbanización"), 
	VIA("Vía");
	
	private String name;

	private Vias(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

}

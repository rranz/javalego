package com.javalego.word.report;

public class Test {

	private String nombre;
	private double precio;

	public static final void main(String args[]) {

		Test t = new Test();
		t.setNombre("Plantilla");
		t.setPrecio(10034.34);

		WordDocument r = new WordDocument();
		r.setObject(t);

		try {
			r.execute(true);
			//r.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

}

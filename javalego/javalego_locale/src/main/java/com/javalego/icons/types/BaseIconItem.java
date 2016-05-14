package com.javalego.icons.types;

/**
 * Tipo de Icono base.
 * 
 * <p>
 * Dentro de este paquete se extenderán las diferentes tipologías de iconos
 * posibles desde donde podemos obtener su contenido gráfico.
 * 
 * @author ROBERTO RANZ
 * 
 */
abstract class BaseIconItem implements IconItem {

	protected String name;

	protected byte[] data;

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

}

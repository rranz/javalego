package com.javalego.util;

/**
 * Definición de un atributo de un bean que deseamos ordenar y establecer el tipo de ordenación.
 * @author ROBERTO
 */
public class BeanAttributeSort {

  public static final int DESCENDING = -1;
  public static final int NOT_SORTED = 0;
  public static final int ASCENDING = 1;

  int direction = ASCENDING;
  
  String attributeName;
  
  /**
   * Constructor que establece el nombre del atributo y la dirección de ordenación.
   * @param attributeName
   */
  public BeanAttributeSort(String attributeName, int direction){
  	this.attributeName = attributeName;
  	this.direction = direction;
  }

  /**
   * Constructor que establece el nombre del atributo y la dirección de ordenación ascendente por defecto.
   * @param attributeName
   */
  public BeanAttributeSort(String attributeName){
  	this.attributeName = attributeName;
  }

  /**
   * Nombre del atributo del bean.
   * @return
   */
	public String getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	/**
	 * Dirección de la ordenación. ASCENDING o DESCENDING (Constantes definidas en la clase).
	 * @return
	 */
	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}
	
}

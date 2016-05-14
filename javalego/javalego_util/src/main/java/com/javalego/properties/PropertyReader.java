package com.javalego.properties;

import com.javalego.exception.JavaLegoException;

/**
 * Interfaz para la lectura de propiedades.
 * 
 * @author ROBERTO RANZ
 */
public interface PropertyReader 
{
    /**
     * Devuelve el valor de una propiedad obligatoria de tipo cadena que depende del entorno
     * de ejecución (definida con la propiedad <code>runtime.environment</code>). El nombre
     * completo de la propiedad en la configuración es:
     * <p>
     * <code><i>runtime.environment</i>.<i>propertyName</i></code>
     * 
     * @param propertyName  Nombre de la propiedad.
     * @return  Valor de la propiedad para el entorno de ejecución actual.
     * @throws JavaLegoException  Si la propiedad no existe.
     */    
    public String getRuntimeString (String propertyName) throws JavaLegoException;
    
    /**
     * Devuelve el valor de una propiedad opcional de tipo cadena que depende del entorno
     * de ejecución (definida con la propiedad <code>runtime.environment</code>). El nombre
     * completo de la propiedad en la configuración es:
     * <p>
     * <code><i>runtime.environment</i>.<i>propertyName</i></code>
     * 
     * @param propertyName  Nombre de la propiedad.
     * @return  Valor de la propiedad para el entorno de ejecución actual.
     * @throws JavaLegoException  Si la propiedad no existe.
     */    
    public String getOptRuntimeString (String propertyName) throws JavaLegoException;
    
    /**
     * Devuelve una referencia a un recurso Java en el espacio de nombres <code>java:comp/env</code> 
     * a partir de una propieadad obligatoria, que siempre es una cadena. Las referencias no 
     * varian segón el entorno de ejecución.
     * 
     * @param propertyName  Nombre de la propiedad que almacena el nombre del recurso.
     * @return Cadena con el nombre de la referencia al recurso.
     * @throws JavaLegoException  Si la propiedad no existe.
     */
    public String getResourceRef (String propertyName) throws JavaLegoException;

    /**
     * Devuelve una referencia a un recurso Java en el espacio de nombres <code>java:comp/env</code> 
     * a partir de una propieadad opcional que, de existir, siempre es una cadena. Las referencias no 
     * varian segón el entorno de ejecución.
     * 
     * @param propertyName  Nombre de la propiedad que almacena el nombre del recurso.
     * @return Cadena con el nombre de la referencia al recurso.
     * @throws JavaLegoException  Si la propiedad no existe.
     */
    public String getOptResourceRef (String propertyName) throws JavaLegoException;
    
     /**
     * Devuelve el valor de una propiedad obligatoria de tipo cadena que depende del entorno
     * de ejecución (definida con la propiedad <code>runtime.environment</code>). El nombre
     * completo de la propiedad en la configuración es:
     * <p>
     * <code><i>runtime.environment</i>.<i>propertyName</i></code>
     * 
     * @param propertyName  Nombre de la propiedad.
     * @return  Valor de la propiedad para el entorno de ejecución actual.
     * @throws JavaLegoException  Si la propiedad no existe.
     */    
    public int getRuntimeInt (String propertyName) throws JavaLegoException;
        
     /**
     * Devuelve el valor de una propiedad opcional de tipo cadena que depende del entorno
     * de ejecución (definida con la propiedad <code>runtime.environment</code>). El nombre
     * completo de la propiedad en la configuración es:
     * <p>
     * <code><i>runtime.environment</i>.<i>propertyName</i></code>
     * 
     * @param propertyName  Nombre de la propiedad.
     * @return  Valor de la propiedad para el entorno de ejecución actual.
     * @throws JavaLegoException  Si la propiedad no existe.
     */    
    public int getOptRuntimeInt (String propertyName) throws JavaLegoException;
    
     /**
     * Devuelve el valor de una propiedad obligatoria de tipo cadena de texto.
     * 
     * @param propertyName  Nombre de la propiedad.
     * @return Valor de la propiedad.
     * @throws JavaLegoException  Si la propiedad no existe.
     */
    public String getString (String propertyName) throws JavaLegoException;

    /**
     * Devuelve el valor de una propiedad obligatoria de tipo entero.
     * 
     * @param propertyName  Nombre de la propiedad.
     * @return Valor de la propiedad.
     * @throws JavaLegoException  Si la propiedad no existe o si estó mal formada.
     */
    public int getInt (String propertyName) throws JavaLegoException;

    /**
     * Devuelve el valor de una propiedad obligatoria de tipo entero largo.
     * 
     * @param propertyName  Nombre de la propiedad.
     * @return Valor de la propiedad.
     * @throws JavaLegoException  Si la propiedad no existe o si estó mal formada.
     */
    public long getLong (String propertyName) throws JavaLegoException;

    /**
     * Devuelve el valor de una propiedad optativa de tipo cadena de texto.
     * 
     * @param propertyName  Nombre de la propiedad.
     * @return Valor de la propiedad.
     * @throws JavaLegoException  Si la propiedad no existe.
     */
    public String getOptString (String propertyName) throws JavaLegoException;

    /**
     * Devuelve el valor de una propiedad optativa de tipo entero.
     * 
     * @param propertyName  Nombre de la propiedad.
     * @return Valor de la propiedad.
     * @throws JavaLegoException  Si la propiedad no existe o si estó mal formada.
     */
    public int getOptInt (String propertyName) throws JavaLegoException;

    /**
     * Devuelve el valor de una propiedad optativa de tipo entero largo.
     * 
     * @param propertyName  Nombre de la propiedad.
     * @return Valor de la propiedad.
     * @throws JavaLegoException  Si la propiedad no existe o si estó mal formada.
     */
    public long getOptLong (String propertyName) throws JavaLegoException;

    /**
     * Chequear que las propiedades existan. Si deseamos evitar la excepción y devolver null cuando no exista un valor, poner a false.
     * @param checkProperties
     */
	void setCheckProperties(boolean checkProperties);

	/**
	 * Sustituir las variables ${property} por el valor de las variables de
	 * entorno. Ej.: ${user.dir}
	 */
	void applySystemProperties();
	
}

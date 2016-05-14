/*
 * Created on 30-mar-2005
 */
package com.javalego.xml;

import org.w3c.dom.Element;

/**
 * Interface que define los métodos que deben implementar sus clases derivadas.
 * @author ROBERTO RANZ
 */
public interface IBasicXmlBean {
  
  /**
   * Cargar la descripción del elemento.
   *
   */
  void loadElement(Element element) throws Exception;
  
  /**
   * Grabar en el document la descripción del elemento.
   *
   */
  void saveElement() throws Exception;
}

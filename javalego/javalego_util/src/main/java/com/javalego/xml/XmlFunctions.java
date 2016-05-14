package com.javalego.xml;

import java.util.ArrayList;

import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.sun.org.apache.xpath.internal.XPathAPI;

/**
 * Clase estática para crear funciones de utilidad para obtener
 * información de documentos xml.
 * 
 * @author ROBERTO RANZ
 *
 */
@SuppressWarnings("restriction")
public final class XmlFunctions {

	/**
	 * Obtener la lista de elementos que cuelgan de un nodo.
	 * @param node
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	static public Element[] getChildElements(Node node){
		
		ArrayList elements = new ArrayList();
		
		for(int i = 0; i < node.getChildNodes().getLength(); i++){
		  
			if (node.getChildNodes().item(i).getNodeType() == Node.ELEMENT_NODE){
		  	elements.add(node.getChildNodes().item(i));
		  }
		}
    return (Element[])elements.toArray(new Element[elements.size()]);
	}

	/**
	 * Obtener los hijos de un nodo mediante XPath.
	 * @param node
	 * @param xpath String de búsqueda.
	 * @return
	 */
	static public Element[] getChildElements(Node node, String xpath){
		Node nodeFind;
		try {
			nodeFind = XPathAPI.selectSingleNode(node, xpath);
			if (nodeFind != null){
				return getChildElements(nodeFind);
			}
			else
				return null;
			/*if (nodeFind != null && nodeFind.getNodeType() == Node.ELEMENT_NODE)
				return (Element)nodeFind;
			else
				return null;
			*/
		} catch (TransformerException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Obtener un elemento mediante XPath.
	 * @param node
	 * @param xpath String de búsqueda.
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	static public Element[] getElements(Node node, String xpath){
		NodeList nodesFind;
		try {
			nodesFind = XPathAPI.selectNodeList(node, xpath);
			if (nodesFind != null){
				ArrayList elements = new ArrayList();
				
				for(int i = 0; i < nodesFind.getLength(); i++){
				  
					if (nodesFind.item(i).getNodeType() == Node.ELEMENT_NODE){
				  	elements.add(nodesFind.item(i));
				  }
				}
		    return (Element[])elements.toArray(new Element[elements.size()]);
			}
			else
				return null;
		} catch (TransformerException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Obtener la lista de elementos que cuelgan de un nodo.
	 * @param node
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	static public Element[] getChildElements(Element element){
		
		ArrayList elements = new ArrayList();
		
		if (element != null){
			for(int i = 0; i < element.getChildNodes().getLength(); i++){
			  
				if (element.getChildNodes().item(i).getNodeType() == Node.ELEMENT_NODE){
			  	elements.add(element.getChildNodes().item(i));
			  }
			}
		}
    return (Element[])elements.toArray(new Element[elements.size()]);
	}
	
	/**
	 * Obtener un elemento mediante XPath.
	 * @param node
	 * @param xpath String de búsqueda.
	 * @return
	 */
	static public Element getSingleElement(Node node, String xpath){
		
		Node nodeFind;
		
		try {
			
			nodeFind = XPathAPI.selectSingleNode(node, xpath);
			
			if (nodeFind != null && nodeFind.getNodeType() == Node.ELEMENT_NODE)
				return (Element)nodeFind;
			else
				return null;
			
		} catch (TransformerException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Obtener un elemento mediante XPath.
	 * @param node
	 * @param xpath String de búsqueda.
	 * @return
	 */
	static public NodeList getListElement(Node node, String xpath){
		
		NodeList nodeFind;
		
		try {
			
			nodeFind = XPathAPI.selectNodeList(node, xpath);
			return nodeFind;
			/*if (nodeFind != null && nodeFind.getNodeType() == Node.ELEMENT_NODE)
				return (Element)nodeFind;
			else
				return null;
			*/
		} catch (TransformerException e) {
			e.printStackTrace();
			return null;
		}
	}
	
  /**
   * Obtiene el valor de un nodo.
   * @param node
   * @return
   */
	static public String getNodeValue(Node node){
    if (node.getNodeType() == Node.ELEMENT_NODE && node.getFirstChild() != null) 
      return node.getFirstChild().getNodeValue();
    else
    	return "";
  }

	/**
	 * Obtiene un nodo con un nombre y valor
	 * @param value
	 * @return
	 */
	static public Element getNode(Document document, String name, String value) {
		Element node = document.createElement(name);
		XmlFunctions.setNodeValue(node, value == null ? "" : value);
		return node;
	}
	
//  /**
//   * Obtiene el valor de un nodo.
//   * @param node
//   * @return
//   */
//	static public String getNodeDataValue(Node node){
//    if (node.getNodeType() == Node.ELEMENT_NODE && node.getFirstChild() != null && node.getFirstChild().getNextSibling() != null) { 
//      return node.getFirstChild().getNextSibling().getNodeValue();
//    }
//    else
//    	return "";
//  }
//	
	/**
	 * Fijar el valor de un nodo.
	 * @param node
	 * @param value
	 */
	static public void setNodeValue(Node node, String value){
		if (node.getChildNodes().getLength() > 0)
			node.getChildNodes().item(0).setNodeValue(value == null ? "" : value);
		else
			node.appendChild(node.getOwnerDocument().createTextNode(value));
	}

	/**
	 * Eliminar todos los elementos hijo de un nodo.
	 * @param element
	 */
	public static void removeAllChild(Element element) {
		
		Element[] elements = getChildElements(element);
		for(int i = elements.length - 1; i > -1; i--) {
			element.removeChild(elements[i]);
		}
		
	}
}

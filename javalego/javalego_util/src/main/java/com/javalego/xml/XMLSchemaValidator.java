package com.javalego.xml;

import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

public class XMLSchemaValidator{
	
	@SuppressWarnings("rawtypes")
	private ArrayList errores=new ArrayList();

	public void validateSchema(String XmlDocumentUrl, String SchemaUrl) throws Exception{ 
		
		try{			
			System.setProperty("javax.xml.parsers.DocumentBuilderFactory",
			"org.apache.xerces.jaxp.DocumentBuilderFactoryImpl");
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();     
			factory.setNamespaceAware(true); 
			factory.setValidating(true);     
			factory.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaLanguage", "http://www.w3.org/2001/XMLSchema" );
			factory.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaSource",SchemaUrl);
  
			DocumentBuilder builder = factory.newDocumentBuilder();				
			Validator handler=new Validator(); 		
			builder.setErrorHandler(handler); 	
		
			//El metodo parse puede lanzar una excepcion si hay un error sintactico,
			//por ejemplo no cerrar bien una etiqueta. 
			builder.parse(XmlDocumentUrl);			
			
			//Si no hemos salido del try no ha habido error sintáctico pero puede haberlos
			//léxicos, es decir se pueden estar utilizando etiquetar y atributos no definidos
			//o en un orden incorrecto. Lo comprobamos:			
										
			if(handler.validationError==true) 				
				throw new Exception("no valid");
						
			//Si llegado a este punto no nos ha sacado fuera ninguna excepción el XML es válido.
				 
		}
		
	  catch(java.io.IOException e){    	    
	  	throw new Exception(e.getMessage());
	  }
	  catch (SAXException e){
	  	//Aqui se capturan los errores sintácticos, por ejemplo abrir <a> y cerrar con </b>
	  	throw new Exception(e.getMessage());
		 	         
	  }
	  catch (ParserConfigurationException e) {
	  	throw new Exception(e.getMessage());		  		  
	  }
	}

	private class Validator extends DefaultHandler {	
		public boolean  validationError=false;	     
		@SuppressWarnings("unused")
		public SAXParseException saxParseException=null;
       
  	@SuppressWarnings("unchecked")
		@Override
		public void error(SAXParseException e) throws SAXException {  	 
			String s=e.getMessage();
			errores.add(s);
			validationError = true;	  
			saxParseException=e;     
		} 
  	@SuppressWarnings("unchecked")
		@Override
		public void fatalError(SAXParseException e) throws SAXException {				
			String s=e.getMessage();
			errores.add(s);
			validationError = true;
			saxParseException=e;
		}
	}
	
	@SuppressWarnings("rawtypes")
	public ArrayList getErrors()
	{
		return errores;		
	}	
}

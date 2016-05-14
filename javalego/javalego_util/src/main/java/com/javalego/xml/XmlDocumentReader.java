package com.javalego.xml;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.Serializable;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import com.javalego.exception.JavaLegoException;
import com.javalego.util.DesktopUtils;
import com.javalego.util.url.UrlInputStream;
import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

/**
 * Lector de documentos XML utilizando DocumentBuilderFactory.
 * 
 * Incluir métodos adicionales de exportación y lectura de datos.
 * 
 * @author ROBERTO RANZ
 */
@SuppressWarnings("restriction")
public class XmlDocumentReader implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// variables para la generación del documento XML
	protected DocumentBuilderFactory factory;

	protected DocumentBuilder builder;

	protected Document document;

	/**
	 * Fichero con la información cargada en el documento.
	 */
	protected File file;

	public XmlDocumentReader() {
	}

	/**
	 * Inicializar el documento DOM desde la factoría.
	 */
	public boolean initialize() {
		if (factory == null) {
			factory = DocumentBuilderFactory.newInstance();
			// factory.setValidating(true);
		}
		if (builder == null) {
			try {
				builder = factory.newDocumentBuilder();
			}
			catch (ParserConfigurationException ex) {
				return false;
			}
		}
		document = builder.newDocument();
		// document.setXmlStandalone(true);
		return true;
	}

	/**
	 * Objeto DOM.
	 * 
	 * @return document
	 */
	public Document getDocumentDOM() {
		return document;
	}

	/**
	 * Establecer el documento DOM.
	 */
	public void setDocumentDOM(Document document) {
		this.document = document;
	}

	/**
	 * Genera el archivo XML en base a la definición del módulo.
	 */
	public final void saveToXml(String fileName) throws Exception {
		saveToXml(fileName, false);
	}

	/**
	 * Genera el archivo XML en base a la definición del módulo.
	 */
	public final void saveToXml(String fileName, boolean omitDeclaration) throws Exception {
		saveToXml(fileName, omitDeclaration, "ISO-8859-1");
	}

	/**
	 * Genera el archivo XML en base a la definición del módulo.
	 */
	public final void saveToXml(String fileName, String encoding) throws Exception {
		saveToXml(fileName, false, encoding);
	}

	/**
	 * Genera el archivo XML en base a la definición del módulo.
	 */
	public final void saveToXml(String fileName, boolean omitDeclaration, String encoding) throws Exception {

		FileWriter file = null;
		try {
			OutputFormat format = new OutputFormat(document);
			format.setIndenting(true);
			format.setIndent(2);
			format.setEncoding(encoding);
			format.setOmitXMLDeclaration(omitDeclaration);
			file = new FileWriter(fileName);
			Writer output = new BufferedWriter(file);
			XMLSerializer serializer = new XMLSerializer(output, format);
			serializer.serialize(document);

		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			if (file != null)
				file.close();
		}
	}

	/**
	 * Genera el archivo XML (versión que respecta la estructura del archivo original que deseamos actualizar) en base a la definición del módulo.
	 */
	public final void saveToXml2(String fileName) throws Exception {
		saveToXml2(fileName, false, null);
	}

	/**
	 * Genera el archivo XML (versión que respecta la estructura del archivo original que deseamos actualizar) en base a la definición del módulo.
	 */
	public final void saveToXml2(String fileName, boolean omitDeclaration) throws Exception {
		saveToXml2(fileName, omitDeclaration, null);
	}

	/**
	 * Genera el archivo XML (versión que respecta la estructura del archivo original que deseamos actualizar) en base a la definición del módulo.
	 */
	public final void saveToXml2(String fileName, boolean omitDeclaration, String encoding) throws Exception {

		FileOutputStream fos = new FileOutputStream(fileName);
		try {
			// XERCES 1 or 2 additionnal classes.
			OutputFormat of = new OutputFormat("XML", encoding != null ? encoding : "ISO-8859-1", true);
			of.setIndenting(true);
			of.setOmitXMLDeclaration(omitDeclaration);
			of.setIndent(2);
			of.setLineWidth(2000);
			XMLSerializer serializer = new XMLSerializer(fos, of);
			// As a DOM Serializer
			serializer.asDOMSerializer();
			serializer.serialize(document.getDocumentElement());
		}
		finally {
			fos.close();
		}
	}

	/**
	 * Obtener un array de bytes con el contenido del documento.
	 */
	public final byte[] getBytes() throws Exception {
		return getBytes(false, "ISO-8859-1");
	}

	/**
	 * Obtener un array de bytes con el contenido del documento.
	 */
	public final byte[] getBytes(boolean omitDeclaration, String encoding) throws Exception {

		java.io.ByteArrayOutputStream fos = new java.io.ByteArrayOutputStream();
		try {
			// XERCES 1 or 2 additionnal classes.
			OutputFormat of = new OutputFormat("XML", encoding != null ? encoding : "ISO-8859-1", true);
			of.setIndenting(true);
			of.setOmitXMLDeclaration(omitDeclaration);
			of.setIndent(2);
			of.setLineWidth(2000);
			XMLSerializer serializer = new XMLSerializer(fos, of);
			// As a DOM Serializer
			serializer.asDOMSerializer();
			serializer.serialize(document.getDocumentElement());
		}
		finally {
			fos.close();
		}
		return fos.toByteArray();
	}

	/**
	 * Genera el archivo XML en formato UTF8
	 */
	public final void saveToXmlUft8(String fileName) throws Exception {

		// Prepare the DOM document for writing
		Source source = new DOMSource(document);

		// Prepare the output file
		File file = new File(fileName);
		Result result = new StreamResult(file);

		// Método para indentar el xml. ATENCIÓN: si incluimos un namespace no se
		// incluye.
		StreamSource stylesource = new StreamSource(getClass().getResourceAsStream("/com/gana/xml/Transform.xslt"));

		TransformerFactory tFactory = TransformerFactory.newInstance();
		Transformer transformer = tFactory.newTransformer(stylesource);
		transformer.transform(source, result);

		// Write the DOM document to the file
		// TransformerFactory tf = TransformerFactory.newInstance();
		// indent tab a 2 caracteres
		// tf.setAttribute("indent-number", new Integer(4));
		// Transformer xformer = tf.newTransformer();
		// xformer.setOutputProperty(OutputKeys.INDENT, "yes");
		// xformer.setOutputProperty(OutputKeys.METHOD, "xml");
		// xformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount",
		// "4");
		// xformer.setOutputProperty(OutputKeys.MEDIA_TYPE, "text/xml");
		// xformer.transform(source, result);
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		// transformer.setOutputProperty(OutputKeys.METHOD, "xhtml");
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		transformer.transform(source, result);
	}

	public static void main(String args[]) {

		XmlDocumentReader dom = new XmlDocumentReader();
		dom.initialize();

		Element interfaceNode = dom.getDocumentDOM().createElement("interface");
		interfaceNode.setAttribute("name", "aplicación");
		interfaceNode.setAttribute("client", "peñazo");
		dom.getDocumentDOM().appendChild(interfaceNode);

		try {
			String fileName = "c:\\gana\\prueba.xml";
			dom.saveToXmlUft8(fileName);
			DesktopUtils.openFile(fileName);
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Cargar la información desde un recurso de la aplicación.
	 * 
	 * @param resourceName
	 * @throws Exception
	 */
	public void loadResource(String resourceName) throws Exception {
		try {
			loadStream(getClass().getResourceAsStream(resourceName));
		}
		catch (Exception e) {
			throw new JavaLegoException(e.getMessage(), JavaLegoException.ERROR);
		}
	}

	/**
	 * Cargar la información desde un recurso de la aplicación o, si no existe, buscarlo como fichero.
	 * 
	 * @param resourceName
	 *          Recurso o fichero
	 * @throws Exception
	 */
	public void loadResource(String resourceName, boolean retryFilename) throws Exception {
		try {
			// Cargar archivo de recurso.
			loadStream(getClass().getResourceAsStream(resourceName));
		}
		catch (Exception e) {
			// Reintentar como fichero.
			if (retryFilename) {
				try {
					loadFilename(resourceName);
				}
				catch (Exception e2) {
					throw new JavaLegoException(resourceName + " " + e.getMessage(), JavaLegoException.ERROR);
				}
			}
			else {
				throw new JavaLegoException(resourceName + " " + e.getMessage(), JavaLegoException.ERROR);
			}
		}
	}

	/**
	 * Cargar la información del archivo XML en el objeto DOM.
	 */
	public void loadFilename(String fileName) throws Exception {

		// Create a builder factory
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			factory.setValidating(false);
		}
		catch (Exception e) {
			// evita visualizar errores de validación del documento xml.
		}

		// Create the builder and parse the file
		file = new File(fileName);
		document = factory.newDocumentBuilder().parse(file);
	}

	/**
	 * Cargar archivo desde una dirección web.
	 * 
	 * @param url
	 */
	public void loadUrl(String url) throws Exception {

		UrlInputStream file = new UrlInputStream();
		file.setUrl(url);
		file.execute();
		loadStream(file.getInputStream());
	}

	/**
	 * Cargar un archivo xml desde un String.
	 * 
	 * @param value
	 */
	public void loadString(String xmlString) throws Exception {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		document = builder.parse(new InputSource(new StringReader(xmlString)));
	}

	/**
	 * Cargar la información del archivo XML en el objeto DOM.
	 */
	public void loadStream(InputStream stream) throws Exception {

		// Create a builder factory
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			factory.setValidating(false);
		}
		catch (Exception e) {
			// evita visualizar errores de validación del documento xml.
		}
		// Create the builder and parse the file
		document = factory.newDocumentBuilder().parse(stream);
	}

	/**
	 * Obtener un string con el contenido del documento XML
	 * 
	 * @throws Exception
	 */
	public final String getStringXML() throws Exception {
		return getStringXML(false);
	}

	/**
	 * Obtener un string con el contenido del documento XML
	 * 
	 * @return omitDeclaration Omitir la declaración de archivo xml
	 * @throws Exception
	 */
	public final String getStringXML(boolean omitDeclaration) throws Exception {

		OutputFormat format = new OutputFormat(document);
		format.setIndenting(true);
		format.setIndent(2);
		format.setOmitXMLDeclaration(omitDeclaration);
		StringWriter output = new StringWriter(128);
		XMLSerializer serializer = new XMLSerializer(output, format);
		serializer.serialize(document);
		StringBuffer buffer = output.getBuffer();
		return buffer.toString();
	}

	// /**
	// * Obtener un string con el contenido del documento XML
	// *
	// * @return omitDeclaration Omitir la declaración de archivo xml
	// * @throws Exception
	// */
	// public final String getString2XML(boolean omitDeclaration) throws Exception {
	//
	// TransformerFactory transformerFactory = TransformerFactory.newInstance();
	//
	// Transformer transformer = transformerFactory.newTransformer();
	//
	// Source source = new DOMSource(document);
	//
	// StringWriter writer = new StringWriter();
	// Result result = new StreamResult(writer);
	// transformer.transform(source, result);
	// String s = writer.toString();
	// return s;
	// }

	/**
	 * Devuelve el valor de un atributo contenido en un nodo del documento.
	 * 
	 * @param node
	 *          Node
	 * @param attributeName
	 *          String
	 * @return String
	 */
	public String getAttribute(Node node, String attributeName) {
		return node.getAttributes().getNamedItem(attributeName).getNodeValue();
	}

	/**
	 * Asigna el valor de un atributo contenido en un nodo del documento.
	 * 
	 * @param node
	 *          Node
	 * @param attributeName
	 *          String
	 */
	public void setAttribute(Node node, String attributeName, Object value) {
		node.getAttributes().getNamedItem(attributeName).setNodeValue(value.toString());
	}

	/**
	 * Devuelve el valor de un nodo hijo.
	 * 
	 * @param name
	 * @param node
	 * @return
	 */
	public String getChildNodeValue(String name, Node node) {
		String value = "";
		Node childNode = null;
		for (int k = 0; k < node.getChildNodes().getLength(); k++) {
			childNode = node.getChildNodes().item(k);
			if (childNode.getNodeType() == Node.ELEMENT_NODE) {
				if (childNode.getNodeName().toLowerCase().equals(name.toLowerCase())) {
					if (childNode.getFirstChild() != null) {
						value = childNode.getFirstChild().getNodeValue();
						break;
					}
				}
			}
		}
		return value;
	}

	/**
	 * Devuelve el nodo hijo.
	 * 
	 * @param name
	 * @param node
	 * @return
	 */
	public Element getChildElement(String name, Element element) {
		Node childNode = null;
		for (int k = 0; k < element.getChildNodes().getLength(); k++) {
			childNode = element.getChildNodes().item(k);
			if (childNode.getNodeType() == Node.ELEMENT_NODE) {
				if (childNode.getNodeName().toLowerCase().equals(name.toLowerCase())) {
					return (Element) childNode;
				}
			}
		}
		return null;
	}

	/**
	 * Devuelve el valor de un nodo.
	 * 
	 * @param node
	 * @return
	 */
	public String getNodeValue(Node node) {
		return XmlFunctions.getNodeValue(node);
	}

	/**
	 * Fijar el valor de un nodo.
	 * 
	 * @param node
	 * @param value
	 */
	public void setNodeValue(Node node, String value) {
		if (node.getChildNodes().getLength() > 0)
			node.getChildNodes().item(0).setNodeValue(value);
		else
			node.appendChild(document.createTextNode(value));
	}

	public File getFile() {
		return file;
	}

	/**
	 * Validar el documento con un esquema.
	 * 
	 * @param fileName
	 */
	public void validateFromSchema(String fileName) {
		// load XML Schema
		// XSDBuilder schemaBuilder = new XSDBuilder();
		// XML Schema schema = schemaBuilder.build(new
		// FileInputStream("myschema.xsd"), null);
		//
		// // set the loaded XML Schema to the XSDValidator
		// XSDValidator validator = new XSDValidator();
		// validator.setSchema(schema);
		//
		// // validate the XML-instance against the supplied XML Schema.
		// validator.validate(new FileInputStream("data.xml"));
		//
		// // check for errors
		// XMLError error = validator.getError();
		// if (error.getNumMessages() > 0) {
		// System.out.println("XML-instance is invalid.");
		// error.flushErrors();
		// }
		// else {
		// System.out.println("XML-instance is valid."); }
		//
	}

}

package com.javalego.util;

import java.io.Serializable;
import java.util.Date;
import java.util.Properties;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import com.javalego.exception.JavaLegoException;

/**
 * Envío de correos electrónicos mediante la utilización de las siguientes librerías:
 * 1. activation.jar
 * 2. mail.jar
 * 3. commons-email-1.0.jar
 * @author ROBERTO RANZ
 */
public class Email implements Serializable {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Título del mensaje.
	 */
	private String subject;
	/**
	 * Segunda línea del cuerpo del mensaje
	 */
	//private String msgText;
	/**
	 * Usuario que recibe el mensaje.
	 */
	private String[] to;
	
	/**
	 * Usuario que recibe el mensaje.
	 */
	private String[] cc;
	
	/**
	 * Nombre que queremos incluir como remitente junto con su dirección de correo.
	 */
	//private String to_name;
	
	/**
	 * Cuerpo del mensaje.
	 */
	private String message;
	/**
	 * Host que gestiona el email.
	 */
	private String mailhost;
	/**
	 * Usuario que envía el mensaje.
	 */
	private String from;
	
	/**
	 * Nombre que queremos incluir como destinatario junto con su dirección de correo.
	 */
	private String from_name;
	/**
	 * Password del usuario que envía el mensaje que será autentificado en el host de envío de correos.
	 */
	private String autentication_password;
	
	/**
	 * Usuario de autenticación con el proxy.
	 */
	private String autentication_user;
	
	/**
	 * Puerto de conexión con el host.
	 */
	private int port;
	
	/**
	 * Archivos anexos. Path de ubicación física.
	 */
	private EmailAttachment[] attachments;
	
  @SuppressWarnings("deprecation")
	public static void main(String[] args) {

  	
  	try {
    
			final String username = "EES-CEPSA@norma4.es";
			final String password = "Norma4##";
	 
			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			//props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", "mail.norma4.es");
			//props.put("mail.smtp.port", "587");

			Session session = Session.getInstance(props, new javax.mail.Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			});
			
			
  		HtmlEmail email = new HtmlEmail();

  		email.setMailSession(session);
  		
    	//email.setHostName("mail.norma4.es");
    	//email.setAuthentication("Norma4##", "Norma4##");
//    	email.setSmtpPort(21);
    	email.setFrom("EES-CEPSA@norma4.es");
    	email.addTo("roberto.ranz@gmail.com");
    	email.setSubject("Correo Prueba GANA " + new Date().toGMTString());

    	email.setTextMsg("Mensaje de prueba " + new Date().toGMTString());
			email.setHtmlMsg("<b>Mensaje</b> de prueba <b>html</b>");
	  	//email.setDebug(true);
	  	email.send();
	  	System.out.println("enviado");
	  	
		} catch (EmailException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

  	System.exit(0);

  	
  	
  	
  	
  	
  	
  	//String msgText = "This is a message body.\nHere's the second line.";
  	//String to = "rranz@hedima.es";
  	String to = "pruebahedima@gmail.com";  
  	
    String subject = "Titulo del correo";   
    String message = "<html><b>Hola</b><br><h1>I'm Roberto y estoy realizando una prueba de envío de email en Java.</h1></html>"; 
    String mailhost = "192.168.40.20"; 
    String from = "rranz@hedima.es";
    int port = 21;

    try {
    	
    	Email e = new Email();
    	
//    	EmailAttachment[] attachments = new EmailAttachment[2];
//    	
//    	EmailAttachment attachment = new EmailAttachment();
//	    attachment.setPath("h:/alumnos.xls");
//	    attachment.setDisposition(EmailAttachment.ATTACHMENT);
//	    attachment.setDescription("Archivo de conexiones");
//	    attachment.setName("alumnos.xls");
//	    attachments[0] = attachment;
//	    
//    	attachment = new EmailAttachment();
//	    attachment.setPath("h:/alumnos.xls");
//	    attachment.setDisposition(EmailAttachment.ATTACHMENT);
//	    attachment.setDescription("Imagen");
//	    attachment.setName("alumnos.xls");
//	    attachments[1] = attachment;
//	    
//    	e.setAttachments(attachments);
    	e.to = new String[] {to};
    	e.from = from;
    	e.message = message;
    	e.mailhost = mailhost;
    	e.subject = subject;
    	//e.from_password = from_password;
    	e.from_name = "ROBERTO RANZ";
    	e.port = port;
    	e.send();
    	System.out.println("Enviado");
    	
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    
    
  }
  
  /**
   * Enviar mensaje y anexos (opcional).
   * @throws Exception
   */
  public void send() throws Exception {
  	
  	// Create the attachment
    HtmlEmail email = new HtmlEmail();
    email.setAuthentication("rranz", "Morrosco1");
    email.setHostName(mailhost);
    
    if (to == null || to.length == 0)
    	throw new JavaLegoException("Debe especificar un destinatario.", JavaLegoException.ERROR);
    
    for(String item : to)
    	email.addTo(item, item); //getTo_name());
    
    if (cc != null && cc.length > 0)
	    for(String item : cc)
	    	email.addCc(item, item); //getCc_name());
    
    if (from != null)
    	email.setFrom(from, getFrom_name());
    
    email.setHtmlMsg(message); 
    
    email.setSubject(subject);
    
    email.setMsg(message);
    
    if (port > 0)
    	email.setSmtpPort(port);
    
    if (autentication_user != null);
    	email.setAuthentication(autentication_user, autentication_password);

    if (attachments != null) {
	  	for(int i = 0; i < attachments.length; i++) {
		    // add the attachment
		    email.attach(attachments[i]);
	  	}
    }

    email.send();
    
  }

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getMailhost() {
		return mailhost;
	}

	public void setMailhost(String mailhost) {
		this.mailhost = mailhost;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String[] getTo() {
		return to;
	}

	public void setTo(String[] to) {
		this.to = to;
	}

	public EmailAttachment[] getAttachments() {
		return attachments;
	}

	public void setAttachments(EmailAttachment[] attachments) {
		this.attachments = attachments;
	}

	public String getFrom_name() {
		return from_name == null ? from : from_name;	
	}

	public void setFrom_name(String from_name) {
		this.from_name = from_name;
	}

//	public String getTo_name() {
//		return to_name == null ? to : to_name;
//	}

//	public void setTo_name(String to_name) {
//		this.to_name = to_name;
//	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String[] getCc() {
		return cc;
	}

	public void setCc(String[] cc) {
		this.cc = cc;
	}

	public String getAutentication_user() {
		return autentication_user;
	}

	public void setAutentication_user(String autentication_user) {
		this.autentication_user = autentication_user;
	}

	public String getAutentication_password() {
		return autentication_password;
	}

	public void setAutentication_password(String autentication_password) {
		this.autentication_password = autentication_password;
	}
 }

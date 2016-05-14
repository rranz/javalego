package com.javalego.util;
/********************************************************************
 * Programa minimo de acceso a LDAP
 *********************************************************************/
import java.util.Properties;

import javax.naming.Context;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

class LDAPAccess
{
	InitialDirContext ctx = null;

	public void depura(String cadena)    // codigo para unificar salidas
	{
		System.out.println(cadena);
	}

    public static void main(String[] args)   // punto de entrada a la aplicacion
    {
  		LDAPAccess instancia = new LDAPAccess();
  		instancia.ejecuta();  // evitamos instanciacion estatica de los metodos
	}


	public void ejecuta()
	{
      	String target = "";

       	Properties env = System.getProperties();    // recogemos variables de entorno

		// Entorno desarrollo
		env.put(Context.INITIAL_CONTEXT_FACTORY,    "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, 				"ldap://localhost:389/ou=front,o=desarrollo,o=casa");
		env.put(Context.SECURITY_PRINCIPAL, 		"cn=root,o=casa");
		env.put(Context.SECURITY_CREDENTIALS, 		"+++++++");

		try
		{
	   	 	ctx = new InitialDirContext(env);        // inicializamos contecto
 			depura ("El DN es: " + ctx.getNameInNamespace());

	      	muestraLista(target, ctx.list(target));
	      	ctx.close();
	    }
	 	catch (Exception e)
		{
		    depura("Excepcion EN BUCLE PRINCIPAL");
		    e.printStackTrace();
		}
    }



    // para el DN que se pone en PROVIDER_URL, recorre todos los elementos
    @SuppressWarnings("rawtypes")
		void muestraLista(String msg, NamingEnumeration nl)
    {
		System.out.println("Sacamos lista de elementos para: " + msg);

		if (nl == null)
		{
		    System.out.println("No hay Elementos en la lista");
		}
		else
		{
		    try
		    {
		    	// recorrer la enumeracion
				while (nl.hasMore())
				{
				    Object objeto = nl.next();
				    NameClassPair parNombre = null;

				    depura("Detalle del Objeto" + objeto.getClass().getName());

				    // nos aseguramos que es objeto del tipo adecuado
				    if (objeto instanceof javax.naming.NameClassPair)
				    {
				    	// depura ("Es un javax.naming.NameClassPair");
				    	parNombre = (NameClassPair) objeto;
				    }
				    else
				    {
				    	depura("No es un nombre");
				    	return;
				    }

				    // Cojer el nombre
					String nombre = parNombre.getName();
                    depura("El nombre recogido es " + nombre);

					listaAtributos(ctx,nombre);
		    	}
		    }
		    catch (NamingException e)
		    {
				e.printStackTrace();
		    }
		}
    }


	// listamos los atributos del subcontexto seleccionado
	@SuppressWarnings("rawtypes")
	void listaAtributos (DirContext localContext, String cadena)
	{
	    try
	    {
			// se puede mejorar pasandole un array con el nombre de los atributos a recoger
			Attributes attr = localContext.getAttributes(cadena);

			// recuperamos una enumeracion con todos los atributos
			NamingEnumeration nl = attr.getAll();

			if (nl == null)
			{
				depura("lista de atributos nula");
				return;
			}

			while (nl.hasMore())
			{
			    // recorremos todos los tributos
			    Object objeto = nl.next();

			    if (objeto instanceof Attribute)
			    {
			    	// cojemos un atributo especifico
			    	//Attribute internalAttr = (Attribute)objeto;
			   		depura("\tAtributo = " + objeto.toString());
			     }
	    	}
	    }
	    catch (NamingException e)
	    {
			e.printStackTrace();
	    }
	}

}

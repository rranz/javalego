package com.javalego.demo.ejb;

import javax.ejb.ApplicationException;

/**
 * Excepción requerida para realizar un rollback dentro de una transacción. Estándar J2EE.
 * 
 * @author ROBERTO
 *
 */
@ApplicationException(rollback = true)
public class MyException extends Exception
{

	private static final long serialVersionUID = 5091251945176197307L;
	// ...
}
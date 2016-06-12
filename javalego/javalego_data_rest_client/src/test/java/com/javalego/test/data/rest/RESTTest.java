package com.javalego.test.data.rest;

import java.util.Collection;

import org.junit.BeforeClass;
import org.junit.Test;

import com.javalego.data.rest.client.DataProviderRESTClient;

import entities.Empresa;

public class RESTTest {

	public static final String ENDPOINT = "http://localhost:8080/javalego-app-rest/rest/meccanoj/";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	public void getList() throws Exception {
		DataProviderRESTClient rest = new DataProviderRESTClient(ENDPOINT);
		Collection<Empresa> list = rest.findAll(Empresa.class);
		System.out.println(list);
	}

	@Test
	public void save() throws Exception {
		DataProviderRESTClient rest = new DataProviderRESTClient(ENDPOINT);
		Empresa e = new Empresa();
		e.setNombre("EMPRESA 1");
		e.setCif("CIF 1");
		Empresa bean = rest.save(e);
		System.out.println(bean);
	}

	@Test
	public void delete() throws Exception {
		DataProviderRESTClient rest = new DataProviderRESTClient(ENDPOINT);
		Empresa e = new Empresa();
		e.setId(212L);
		e.setNombre("EMPRESA 1");
		e.setCif("CIF 1");
		rest.delete(e);
	}

	@Test
	public void fieldValues() throws Exception {
		DataProviderRESTClient rest = new DataProviderRESTClient(ENDPOINT);
		Collection<?> values = rest.propertyValues(Empresa.class, "nombre", null, null);
		System.out.println(values);
	}

}

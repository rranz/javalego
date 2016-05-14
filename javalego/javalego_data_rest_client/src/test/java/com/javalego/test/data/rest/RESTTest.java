package com.javalego.test.data.rest;

import java.util.Collection;

import org.junit.BeforeClass;
import org.junit.Test;

import com.javalego.data.rest.client.DataProviderRESTClient;
import com.javalego.entity.Entity;

import entities.Empresa;

public class RESTTest {
	
	public static final String ENDPOINT = "http://localhost:8080/javalego_data_rest/webapi/meccanoj/";
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@SuppressWarnings("unchecked")
	@Test
	public void getList() {
		DataProviderRESTClient rest = new DataProviderRESTClient(ENDPOINT);
		Collection<Empresa> list;
		try {
			list = (Collection<Empresa>) rest.getList(Empresa.class);
			System.out.println(list); 
		} catch (Exception e) {
			assert(false);
		}
	}

	@Test
	public void save() {
		DataProviderRESTClient rest = new DataProviderRESTClient(ENDPOINT);
		try {
			Empresa e = new Empresa();
			e.setNombre("EMPRESA 1");
			e.setCif("CIF 1");
			Entity bean = rest.save(e);
			System.out.println(bean); 
		} catch (Exception e) {
			assert(false);
		}
	}
	
	@Test
	public void delete() {
		DataProviderRESTClient rest = new DataProviderRESTClient(ENDPOINT);
		try {
			Empresa e = new Empresa();
			e.setId(212L);
			e.setNombre("EMPRESA 1");
			e.setCif("CIF 1");
			rest.delete(e);
		} catch (Exception e) {
			assert(false);
		}
	}
	
	@Test
	public void fieldValues() {
		DataProviderRESTClient rest = new DataProviderRESTClient(ENDPOINT);
		try {
			Collection<?> values = rest.getFieldValues(Empresa.class, "nombre", null, null);
			System.out.println(values);
		} catch (Exception e) {
			assert(false);
		}
	}
	
}

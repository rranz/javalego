package com.javalego.test.data.jpa.repository;

import com.javalego.data.provider.IRepository;
import com.javalego.test.data.jpa.repository.DomainSpringData;



public class Test {

	public static void main(String args[]) {
		
		IRepository<Domain> data = new DomainDummy();
		
		data = new DomainSpringData(); 
		
		for(Domain item : data.findAll()) {
			System.out.println(item.getName());
		}
	}
	
}

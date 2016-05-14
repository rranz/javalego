package com.javalego.test.data.jpa.repository;

import java.util.ArrayList;
import java.util.List;

import com.javalego.data.provider.IRepository;


public class DomainDummy implements IRepository<Domain> {

	@Override
	public Iterable<Domain> findAll() {
		List<Domain> list = new ArrayList<Domain>();
		
		list.add(new Domain());
		list.add(new Domain());
		list.add(new Domain());
		
		return list;
	}

}

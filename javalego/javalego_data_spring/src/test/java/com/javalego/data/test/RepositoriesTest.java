package com.javalego.data.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import entities.Domain;
import repositories.DomainRepository;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DemoPersistenceContext.class, loader = AnnotationConfigContextLoader.class)
public class RepositoriesTest {

	@Autowired
	private DomainRepository repo;
	
	@Test
	public void repositories() {
		
		repo.save(new Domain("Company 1"));
		repo.save(new Domain("Company 2"));
		repo.save(new Domain("Company 3"));
		
		for(Domain item : repo.findAll()) {
			System.out.println(item.getName());
		}
	}
	
}

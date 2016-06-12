package com.javalego.test.data.jpa;

import java.util.List;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import entities.Company;
import entities.Customer;
import repositories.CompanyRepository;
import repositories.CustomerRepository;

public class SpringDataTest {

	@Test
	public void test() {

		AbstractApplicationContext context = new AnnotationConfigApplicationContext(TestPersistenceContext.class);

		// Base de datos relacionales (no sirve para mongodb)
		// EntityManagerFactory emf =
		// context.getBean(EntityManagerFactory.class);
		// EntityManager em = emf.createEntityManager();
		// List<Customer> list =
		// em.createQuery("select u from Customer u where lastName like
		// 'B%'").getResultList();

		CompanyRepository erepository = context.getBean(CompanyRepository.class);
		erepository.save(new Company("Company 1"));
		erepository.save(new Company("Company 2"));
		erepository.save(new Company("Company 3"));

		Iterable<Company> list = erepository.findAll();
		System.out.println("Companies found with findAll():");
		System.out.println("-------------------------------");
		for (Company e : list) {
			System.out.println(e.getName());
		}

		CustomerRepository repository = context.getBean(CustomerRepository.class);

		// repository.deleteAll();

		// save a couple of customers
		repository.save(new Customer("Jack", "Bauer"));
		repository.save(new Customer("Chloe", "O'Brian"));
		repository.save(new Customer("Kim", "Bauer"));
		repository.save(new Customer("David", "Palmer"));
		repository.save(new Customer("Michelle", "Dessler"));

		// fetch all customers
		Iterable<Customer> customers = repository.findAll();
		System.out.println("Customers found with findAll():");
		System.out.println("-------------------------------");
		for (Customer customer : customers) {
			System.out.println(customer);
		}
		System.out.println();

		// fetch an individual customer by ID
		Customer customer = repository.findOne(1L);
		System.out.println("Customer found with findOne(1L):");
		System.out.println("--------------------------------");
		System.out.println(customer);
		System.out.println();

		// fetch customers by last name
		List<Customer> bauers = repository.findByLastnameOrFirstname("Bauer", "Kim");
		System.out.println("Customer found with findByLastName('Bauer'):");
		System.out.println("--------------------------------------------");
		for (Customer bauer : bauers) {
			System.out.println(bauer);
		}

		context.close();
	}

}

package com.javalego.data.test;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.javalego.data.jpa.JpaProvider;

import entities.Company;
import entities.Customer;
import repositories.CompanyRepository;
import repositories.CustomerRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DemoPersistenceContext.class, loader = AnnotationConfigContextLoader.class)
public class DemoTest
{
	public static final Logger logger = Logger.getLogger(DemoTest.class);

	@Autowired
	private CompanyRepository erepository;

	@Autowired
	private CustomerRepository repository;
	
	@Autowired
	private JpaProvider dataProvider;

	/**
	 * Ejemplo de carga del entorno en la prueba unitaria.
	 * 
	 * @throws Exception
	 */
	@Test
	public void populates() throws Exception
	{
		dataProvider.save(new Company("Company 0"));
		
		erepository.save(new Company("Company 1"));
		erepository.save(new Company("Company 2"));
		erepository.save(new Company("Company 3"));

		Iterable<Company> list = erepository.findAll();
		logger.info("Companies found with findAll():");
		logger.info("-------------------------------");
		for (Company e : list)
		{
			logger.info(e.getName());
		}

		// repository.deleteAll();

		// save a couple of customers
		repository.save(new Customer("Jack", "Bauer"));
		repository.save(new Customer("Chloe", "O'Brian"));
		repository.save(new Customer("Kim", "Bauer"));
		repository.save(new Customer("David", "Palmer"));
		repository.save(new Customer("Michelle", "Dessler"));

		// fetch all customers
		Iterable<Customer> customers = repository.findAll();
		logger.info("Customers found with findAll():");
		logger.info("-------------------------------");
		for (Customer customer : customers)
		{
			logger.info(customer);
		}

		// fetch an individual customer by ID
		Customer customer = repository.findOne(1L);
		logger.info("Customer found with findOne(1L):");
		logger.info("--------------------------------");
		logger.info(customer);

		// fetch customers by last name
		List<Customer> bauers = repository.findByLastnameOrFirstname("Bauer", "Kim");
		logger.info("Customer found with findByLastName('Bauer'):");
		logger.info("--------------------------------------------");
		for (Customer bauer : bauers)
		{
			logger.info(bauer);
		}

	}

}

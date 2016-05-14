package com.javalego.test.data.jpa.repository;

import java.util.ArrayList;

import com.javalego.data.provider.IRepository;


/**
 * Proveedor de datos basado en un repository de Spring Data.
 * 
 * @author ROBERTO RANZ
 *
 */
public class DomainSpringData implements IRepository<Domain> {

	//@Autowired 
	private DomainRepository repository;

	@Override
	public Iterable<Domain> findAll() {
		return repository != null ? repository.findAll() : new ArrayList<Domain>();
	}

}

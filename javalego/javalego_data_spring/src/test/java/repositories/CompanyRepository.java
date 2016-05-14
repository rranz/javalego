package repositories;

import org.springframework.data.repository.CrudRepository;

import entities.Company;

public interface CompanyRepository extends CrudRepository<Company, Long> {

}

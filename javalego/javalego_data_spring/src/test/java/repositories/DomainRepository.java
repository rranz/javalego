package repositories;

import org.springframework.data.repository.CrudRepository;

import entities.Domain;

public interface DomainRepository extends CrudRepository<Domain, Long> {
}

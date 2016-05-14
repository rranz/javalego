package repositories;

import org.springframework.data.repository.CrudRepository;

import entities.Empresa;

public interface EmpresaRepository extends CrudRepository<Empresa, Long> {

}

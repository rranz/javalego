package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import entities.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    List<Customer> findByLastName(String lastName);

    List<Customer> findByFirstName(String name);
    
    @Query("select u from Customer u where u.firstName = :firstName and u.lastName = :lastName")
    List<Customer> findByLastnameOrFirstname(@Param("lastName") String lastname, @Param("firstName") String firstname);
    
}

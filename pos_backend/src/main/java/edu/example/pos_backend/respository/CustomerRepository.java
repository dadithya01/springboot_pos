package edu.example.pos_backend.respository;

import edu.example.pos_backend.entity.Customer;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {


}

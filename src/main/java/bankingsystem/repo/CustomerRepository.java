package bankingsystem.repo;

import java.util.Optional;

import bankingsystem.entities.Customer;

public interface CustomerRepository {

	 Optional<Customer> findById(Long id);
}

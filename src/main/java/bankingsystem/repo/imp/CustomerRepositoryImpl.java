package bankingsystem.repo.imp;

import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import bankingsystem.entities.Customer;
import bankingsystem.repo.CustomerRepository;

public class CustomerRepositoryImpl implements CustomerRepository {

	private final SessionFactory sessionFactory;

    public CustomerRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<Customer> findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            Customer customer = session.get(Customer.class, id);
            return Optional.ofNullable(customer);
        }
    }
}

package bankingsystem.service.impl;

import java.awt.print.Pageable;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.util.ObjectUtils;

import bankingsystem.dtos.CustomerDTO;
import bankingsystem.entities.Customer;
import bankingsystem.exception.ResourceNoteFoundException;
import bankingsystem.helper.ApiResponseMessage;
import bankingsystem.helper.Transfer;
import bankingsystem.service.CustomerService;

public class CustomerServiceImpl implements CustomerService {

	private final HibernateTemplate hibernateTemplate;

	public CustomerServiceImpl(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	@Override
	@Transactional
	public CustomerDTO addCustomer(CustomerDTO customerDto) {

		Customer customer = Transfer.convertToEntity(customerDto);
		Serializable id  = hibernateTemplate.save(customer);
		customer =  hibernateTemplate.get(Customer.class, id);
		System.out.print(customer.getId());
		return Transfer.convertToDTO(customer);
	}

	@Override
	public List<CustomerDTO> getAllCustomers() {
		List<Customer> customers = hibernateTemplate.loadAll(Customer.class);
		return customers.stream().map(Transfer::convertToDTO).collect(Collectors.toList());
	}

	@Override
	public CustomerDTO getCustomerById(String id) throws ResourceNoteFoundException {
		Customer customer = hibernateTemplate.get(Customer.class, Long.parseLong(id));
		if (customer == null) {
			throw new ResourceNoteFoundException("Customer not exsist in DB");
		}
		
		return customer != null ? Transfer.convertToDTO(customer) : null;
	}

	@Override
	@Transactional
	public CustomerDTO updateCustomer(CustomerDTO customerDto) {
    	if(customerDto.getId()==null || customerDto.getId()== "") {
    	
    		throw new ResourceNoteFoundException("CustomerDto is not empty");
    	}
		

		Customer customer = hibernateTemplate.get(Customer.class, Long.parseLong(customerDto.getId()));
		if (customer == null) {
		
			throw new ResourceNoteFoundException("Customer not exsist in DB");
		}
		
		Customer customerUpdated = Transfer.toUpadte(customer, customerDto);
		hibernateTemplate.update(customerUpdated);
		return Transfer.convertToDTO(customerUpdated);
	}

	@Override
	@Transactional
	public void deleteCustomer(String id) {
		Customer customer = hibernateTemplate.get(Customer.class, Long.parseLong(id));
		if (customer != null) {
			hibernateTemplate.delete(customer);
			System.out.println("Customer deleted successfully!");
		} else {
			throw new ResourceNoteFoundException("Customer with ID " + id + " not found.");
			// System.out.println("Customer with ID " + id + " not found.");
		}
	}

	@Override
	public List<CustomerDTO> getAllPaginatedCustomer(int pageNumber, int pageSize) {
		int firstResult = pageNumber * pageSize;
		List<Customer> customers = hibernateTemplate
				.execute(session -> session.createQuery("from Customer", Customer.class).setFirstResult(firstResult)
						.setMaxResults(pageSize).list());
		return customers.stream().map(Transfer::convertToDTO).collect(Collectors.toList());
	}

}

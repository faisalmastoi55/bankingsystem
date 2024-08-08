package bankingsystem.service;

import java.io.IOException;
import java.util.List;

import bankingsystem.dtos.CustomerDTO;
import bankingsystem.entities.Customer;
import bankingsystem.helper.ApiResponseMessage;

public interface CustomerService {

	CustomerDTO addCustomer(CustomerDTO customeDto) throws IOException;

    List<CustomerDTO> getAllCustomers();

    CustomerDTO getCustomerById(String  id);

    CustomerDTO updateCustomer(CustomerDTO customerDto);

    void deleteCustomer(String id);
    
    List<CustomerDTO> getAllPaginatedCustomer(int pageNumber, int pageSize);
}

package bankingsystem.controller;

import bankingsystem.dtos.CustomerDTO;
import bankingsystem.entities.Customer;
import bankingsystem.exception.ResourceNoteFoundException;
import bankingsystem.helper.ApiResponseMessage;
import bankingsystem.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

	private final CustomerService customerService;

	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<ApiResponseMessage> addCustomer(@RequestBody CustomerDTO customerDto) throws IOException {

		ApiResponseMessage message = new ApiResponseMessage();
		customerDto = customerService.addCustomer(customerDto);
		message.setDto(customerDto);
		message.setMessage("Customer added successfully!");
		message.setSuccess(true);
		message.setStatus(HttpStatus.CREATED);
		return new ResponseEntity<>(message, HttpStatus.CREATED);

	}
	
	
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
		List<CustomerDTO> customers = customerService.getAllCustomers();
		return new ResponseEntity<>(customers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable("id") String id) {
		
		CustomerDTO customer = customerService.getCustomerById(id);
		if (customer != null) {
			return new ResponseEntity<>(customer, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public ResponseEntity<ApiResponseMessage> updateCustomer(@RequestBody CustomerDTO customerDto) {

		ApiResponseMessage response =  new ApiResponseMessage();
		customerDto = customerService.updateCustomer(customerDto);
		response.setMessage("Customer Updated successfully!");
		response.setSuccess(true);
		response.setStatus(HttpStatus.ACCEPTED);
		response.setDto(customerDto);
		return new ResponseEntity<ApiResponseMessage>(response, HttpStatus.ACCEPTED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<ApiResponseMessage> deleteCustomer(@PathVariable("id") String id) {
		customerService.deleteCustomer(id);
		ApiResponseMessage message = new ApiResponseMessage();
		message.setMessage("Customer is deleted successfully !!");
		message.setSuccess(true);
		message.setStatus(HttpStatus.OK);

		return new ResponseEntity(message, HttpStatus.OK);
	}

	@RequestMapping(value = "/paginated", method = RequestMethod.GET)
	public List<CustomerDTO> getAllPaginatedCustomer(
			@RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
		return customerService.getAllPaginatedCustomer(pageNumber, pageSize);
	}
}

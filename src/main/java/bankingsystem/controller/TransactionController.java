package bankingsystem.controller;

import bankingsystem.dtos.AccountDTO;
import bankingsystem.dtos.TransactionDTO;
import bankingsystem.helper.ApiResponseMessage;
import bankingsystem.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

	private final TransactionService transactionService;

	public TransactionController(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<ApiResponseMessage> addTransaction(@RequestBody TransactionDTO transactionDTO) {

		transactionDTO = transactionService.addTransaction(transactionDTO);
		ApiResponseMessage message = new ApiResponseMessage();
		message.setDto(transactionDTO);
		message.setMessage("Transaction added successfully!");
		message.setSuccess(true);
		message.setStatus(HttpStatus.CREATED);
		return new ResponseEntity<>(message, HttpStatus.CREATED);

	}

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity<List<TransactionDTO>> getAllTransactions() {
		List<TransactionDTO> transactions = transactionService.getAllTransactions();
		return new ResponseEntity<>(transactions, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<TransactionDTO> getTransactionById(@PathVariable String id) {
		TransactionDTO transactionDTO = transactionService.getTransactionById(id);
		if (transactionDTO != null) {
			return new ResponseEntity<>(transactionDTO, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public ResponseEntity<ApiResponseMessage> updateTransaction(@RequestBody TransactionDTO transactionDTO) {
		ApiResponseMessage message = new ApiResponseMessage();
		transactionDTO = transactionService.updateTransaction(transactionDTO);

		message.setDto(transactionDTO);
		message.setMessage("Transaction updated successfully!");
		message.setSuccess(true);
		message.setStatus(HttpStatus.OK);
		message.setDto(transactionDTO);
		return new ResponseEntity<>(message, HttpStatus.CREATED);

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteTransaction(@PathVariable String id) {

		transactionService.deleteTransaction(id);
		ApiResponseMessage message = new ApiResponseMessage();
		message.setMessage("Transaction deleted successfully!");
		message.setSuccess(true);
		message.setStatus(HttpStatus.OK);

		return new ResponseEntity(message, HttpStatus.OK);

	}

	@RequestMapping(value = "/paginated", method = RequestMethod.GET)
	public List<TransactionDTO> getAllPaginatedTransaction(
			@RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
		return transactionService.getAllPaginatedTransaction(pageNumber, pageSize);
	}
}

package bankingsystem.controller;

import bankingsystem.dtos.AccountDTO;
import bankingsystem.dtos.CustomerDTO;
import bankingsystem.helper.ApiResponseMessage;
import bankingsystem.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

	private final AccountService accountService;

	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<ApiResponseMessage> addAccount(@RequestBody AccountDTO accountDTO) {
		accountDTO = accountService.addAccount(accountDTO);
		ApiResponseMessage message = new ApiResponseMessage();
		message.setMessage("Account added successfully!");
		message.setSuccess(true);
		message.setStatus(HttpStatus.CREATED);
		message.setDto(accountDTO);
		return new ResponseEntity<>(message, HttpStatus.CREATED);

	}

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity<List<AccountDTO>> getAllAccounts() {
		List<AccountDTO> accounts = accountService.getAllAccounts();
		return new ResponseEntity<>(accounts, HttpStatus.OK);
	}

	@RequestMapping(value = "/{accountNumber}", method = RequestMethod.GET)
	public ResponseEntity<AccountDTO> getAccountByNumber(@PathVariable String accountNumber) {
		AccountDTO accountDTO = accountService.getAccountByNumber(accountNumber);
		if (accountDTO != null) {
			return new ResponseEntity<>(accountDTO, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public ResponseEntity<ApiResponseMessage> updateAccount(@RequestBody AccountDTO accountDTO) {
		accountDTO = accountService.updateAccount(accountDTO);
		ApiResponseMessage response = new ApiResponseMessage();
		response.setMessage("Account Updated successfully!");
		response.setSuccess(true);
		response.setStatus(HttpStatus.ACCEPTED);
		response.setDto(accountDTO);
		return new ResponseEntity<ApiResponseMessage>(response, HttpStatus.ACCEPTED);

	}

	@RequestMapping(value = "/{accountNumber}", method = RequestMethod.DELETE)
	public ResponseEntity<ApiResponseMessage> deleteAccount(@PathVariable String accountNumber) {
		accountService.deleteAccount(accountNumber);
		ApiResponseMessage message = new ApiResponseMessage();
		message.setMessage("Account deleted successfully!");
		message.setSuccess(true);
		message.setStatus(HttpStatus.OK);
		return new ResponseEntity<>(message, HttpStatus.OK);

	}

	@RequestMapping(value = "/paginated", method = RequestMethod.GET)
	public List<AccountDTO> getAllPaginatedAccount(
			@RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
		return accountService.getAllPaginatedAccount(pageNumber, pageSize);
	}
}

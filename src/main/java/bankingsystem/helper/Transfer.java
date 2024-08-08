package bankingsystem.helper;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import bankingsystem.dtos.AccountDTO;
import bankingsystem.dtos.CustomerDTO;
import bankingsystem.dtos.TransactionDTO;
import bankingsystem.entities.Account;
import bankingsystem.entities.Customer;
import bankingsystem.entities.Transaction;
import bankingsystem.exception.ResourceNoteFoundException;
import bankingsystem.repo.AccountRepository;

public class Transfer {

	private static AccountRepository accountRepository;

	public Transfer(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	public static CustomerDTO convertToDTO(Customer customer) {
		CustomerDTO dto = new CustomerDTO();
		dto.setId(String.valueOf(customer.getId()));
		dto.setName(customer.getName());
		dto.setEmail(customer.getEmail());
		dto.setPhone(customer.getPhone());
		dto.setAddress(customer.getAddress());
		return dto;
	}

	public static Customer convertToEntity(CustomerDTO dto) {
		Customer customer = new Customer();
		if (dto.getId() != null) {
			customer.setId(Long.parseLong(dto.getId()));
		}
		customer.setName(dto.getName());
		customer.setEmail(dto.getEmail());
		customer.setPhone(dto.getPhone());
		customer.setAddress(dto.getAddress());
		return customer;
	}

	public static Customer toUpadte(Customer customer, CustomerDTO dto) {

		if (dto.getId() != null) {
			customer.setId(Long.parseLong(dto.getId()));
		}
		if (dto.getName() != null) {
			customer.setName(dto.getName());
		}
		if (dto.getEmail() != null) {
			customer.setEmail(dto.getEmail());
		}
		if (dto.getPhone() != null) {
			customer.setPhone(dto.getPhone());
		}
		if (dto.getAddress() != null) {
			customer.setAddress(dto.getAddress());
		}
		return customer;
	}

	public static AccountDTO convertToDTO(Account account) {
		AccountDTO dto = new AccountDTO();
		dto.setAccountNumber(account.getAccountNumber());
		dto.setBalance(String.valueOf(account.getBalance()));
		if (account.getCustomer() != null) {
			dto.setCustomerDTO(Transfer.convertToDTO(account.getCustomer())); 
		}

		return dto;
	}

	public static Account convertToEntity(AccountDTO dto) {
		Account account = new Account();
		if (dto.getAccountNumber() != null && !dto.getAccountNumber().isEmpty()) {
			account.setAccountNumber(dto.getAccountNumber());
		}
		account.setAccountNumber(dto.getAccountNumber());
		account.setBalance(Double.parseDouble(dto.getBalance()));
		return account;
	}
	
	public static Account toUpdate(Account account, AccountDTO dto) {

		if (dto.getAccountNumber() != null) {
			account.setAccountNumber(dto.getAccountNumber());
		}
		if (dto.getBalance() != null) {
			account.setBalance(Double.parseDouble(dto.getBalance()));
		}
		if (dto.getCustomerDTO() != null) {
			account.setCustomer(Transfer.convertToEntity(dto.getCustomerDTO()));
		}

		return account;
	}

	public TransactionDTO convertToDTO(Transaction transaction) {
		TransactionDTO dto = new TransactionDTO();
		dto.setId(transaction.getId());
		dto.setAmount(String.valueOf(transaction.getAmount()));
		dto.setType(transaction.getType());
		//dto.setSenderAccountNumber(transaction.getSenderAccount().getAccountNumber());
		//dto.setReceiverAccountNumber(transaction.getReceiverAccount().getAccountNumber());
		
		if (transaction.getSenderAccount() != null) {
			dto.setSenderAccountNumber(Transfer.convertToDTO(transaction.getSenderAccount()));
		}
		
		if(transaction.getReceiverAccount() != null) {
			dto.setReceiverAccountNumber(Transfer.convertToDTO(transaction.getReceiverAccount()));
		}
		return dto;
	}

	public List<TransactionDTO> convertToDTO(List<Transaction> transactions) {
		return transactions.stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	public static Transaction convertToEntity(TransactionDTO dto) {
		Transaction transaction = new Transaction();
		transaction.setId(dto.getId());
		transaction.setAmount(Double.parseDouble(dto.getAmount()));
		transaction.setType(dto.getType());

		//Account senderAccount = accountRepository.findByAccount(dto.getSenderAccountNumber());
		//Account receiverAccount = accountRepository.findByAccount(dto.getReceiverAccountNumber());
		Account senderAccount = accountRepository.findByAccountNumber(dto.getSenderAccountNumber().getAccountNumber());
		Account receiverAccount = accountRepository.findByAccountNumber(dto.getReceiverAccountNumber().getAccountNumber());

		try {
			if (senderAccount == null) {
				throw new ResourceNoteFoundException("Sender account not found!");

			} else {
				transaction.setSenderAccount(senderAccount);
				if (receiverAccount == null) {
					throw new ResourceNoteFoundException("Receiver account not found");

				} else {

					transaction.setReceiverAccount(receiverAccount);
				}
			}
		} catch (NullPointerException e) {
			System.out.println("Please try again.");
		}

		return transaction;
	}

	public Transaction toUpdate(Transaction transaction, TransactionDTO dto) {
		//Account senderAccount = accountRepository.findByAccountNumber(dto.getSenderAccountNumber().toString());
		//System.out.println("Sender Account : " +senderAccount.getAccountNumber());
		//Account receiverAccount = accountRepository.findByAccountNumber(dto.getReceiverAccountNumber().toString());
		//System.out.println("Receiver Account : " +receiverAccount.getAccountNumber());
		
		if(dto.getAmount() != null) {
			transaction.setAmount(Double.parseDouble(dto.getAmount()));
		}
		if(dto.getType() != null) {
			transaction.setType(dto.getType());
		}
		if(dto.getSenderAccountNumber() != null) {
			transaction.setSenderAccount(Transfer.convertToEntity(dto.getSenderAccountNumber()));
		}
		if(dto.getReceiverAccountNumber() != null) {
			transaction.setReceiverAccount(Transfer.convertToEntity(dto.getReceiverAccountNumber()));
		}
		return transaction;
	}

}
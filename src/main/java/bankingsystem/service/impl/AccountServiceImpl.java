package bankingsystem.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.orm.hibernate5.HibernateTemplate;

import bankingsystem.dtos.AccountDTO;
import bankingsystem.entities.Account;
import bankingsystem.entities.Customer;
import bankingsystem.exception.ResourceNoteFoundException;
import bankingsystem.helper.ApiResponseMessage;
import bankingsystem.helper.Transfer;
import bankingsystem.repo.AccountRepository;
import bankingsystem.repo.CustomerRepository;
import bankingsystem.service.AccountService;

public class AccountServiceImpl implements AccountService {

	private final HibernateTemplate hibernateTemplate;
	private final AccountRepository accountRepository;
	private final CustomerRepository customerRepository;

	public AccountServiceImpl(HibernateTemplate hibernateTemplate, AccountRepository accountRepository,
			CustomerRepository customerRepository) {
		this.hibernateTemplate = hibernateTemplate;
		this.accountRepository = accountRepository;
		this.customerRepository = customerRepository;
	}

	@Override
	@Transactional
	public AccountDTO addAccount(AccountDTO accountDTO) {
		Account account = Transfer.convertToEntity(accountDTO);

		try {
			// Assuming accountDTO has a method getCustomerId() to fetch the customer ID
			Long customerId = Long.parseLong(accountDTO.getCustomerDTO().getId());

			// Check if customerId is valid
			if (customerId == null) {
				throw new IllegalArgumentException("Customer ID is required for loading");
			}

			Customer customer = hibernateTemplate.get(Customer.class, customerId);
			Account existingAccount = accountRepository.findByAccountNumber(accountDTO.getAccountNumber());

			if (existingAccount == null) {
				if (customer != null) {
					account.setCustomer(customer);
					Serializable id = hibernateTemplate.save(account);
					account = hibernateTemplate.get(Account.class, id);
					System.out.println("Account added successfully!");
				} else {
					throw new ResourceNoteFoundException("Account with Customer ID not found.");
				}
			} else {
				throw new ResourceNoteFoundException("Account already exists.");
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}

		return Transfer.convertToDTO(account);
	}

	@Override
	public List<AccountDTO> getAllAccounts() {
		List<Account> accounts = hibernateTemplate.loadAll(Account.class);
		return accounts.stream().map(Transfer::convertToDTO).collect(Collectors.toList());
	}

	@Override
	public AccountDTO getAccountByNumber(String accountNumber) {
		Account account = hibernateTemplate.get(Account.class, accountNumber);
		if (account == null) {

			throw new ResourceNoteFoundException("Account not exsist in DB");
		}
		return account != null ? Transfer.convertToDTO(account) : null;
	}

	@Override
	@Transactional
	public AccountDTO updateAccount(AccountDTO accountDTO) {

		if (accountDTO.getAccountNumber() == null || accountDTO.getAccountNumber() == "") {

			throw new ResourceNoteFoundException("AccountDTO is not empty");
		}

		Account account = hibernateTemplate.get(Account.class, accountDTO.getAccountNumber());
		if (account == null) {
			throw new ResourceNoteFoundException("Account not exsist in DB");
		}

		Account update = Transfer.toUpdate(account, accountDTO);
		hibernateTemplate.update(update);

		return Transfer.convertToDTO(update);
	}

	@Override
	@Transactional
	public void deleteAccount(String accountNumber) {
		Account account = hibernateTemplate.get(Account.class, accountNumber);
		if (account != null) {
			hibernateTemplate.delete(account);
			System.out.println("Account deleted successfully!");
		} else {
			throw new ResourceNoteFoundException("Account Number " + accountNumber + " not found.");
		}
	}

	@Override
	public List<AccountDTO> getAllPaginatedAccount(int pageNumber, int pageSize) {
		int firstResult = pageNumber * pageSize;
		List<Account> accounts = hibernateTemplate.execute(session -> session.createQuery("from Account", Account.class)
				.setFirstResult(firstResult).setMaxResults(pageSize).list());
		return accounts.stream().map(Transfer::convertToDTO).collect(Collectors.toList());
	}

}

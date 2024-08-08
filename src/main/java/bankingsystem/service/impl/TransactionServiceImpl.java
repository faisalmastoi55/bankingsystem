package bankingsystem.service.impl;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.orm.hibernate5.HibernateTemplate;

import bankingsystem.dtos.AccountDTO;
import bankingsystem.dtos.TransactionDTO;
import bankingsystem.entities.Account;
import bankingsystem.entities.Transaction;
import bankingsystem.exception.BadApiRequestException;
import bankingsystem.exception.ResourceNoteFoundException;
import bankingsystem.helper.ApiResponseMessage;
import bankingsystem.helper.Transfer;
import bankingsystem.repo.AccountRepository;
import bankingsystem.repo.TransactionRepository;
import bankingsystem.service.TransactionService;

public class TransactionServiceImpl implements TransactionService {

	private final HibernateTemplate hibernateTemplate;
	private final AccountRepository accountRepository;
	private final TransactionRepository transactionRepository;
	private final Transfer transfer;

	public TransactionServiceImpl(HibernateTemplate hibernateTemplate, AccountRepository accountRepository,
			TransactionRepository transactionRepository, Transfer transfer) {
		this.hibernateTemplate = hibernateTemplate;
		this.accountRepository = accountRepository;
		this.transactionRepository = transactionRepository;
		this.transfer = transfer;
	}

	@Override
	@Transactional
	public TransactionDTO addTransaction(TransactionDTO transactionDTO) {
		Transaction transaction = Transfer.convertToEntity(transactionDTO);
		Account senderAccount = accountRepository.findByAccountNumber(transactionDTO.getSenderAccountNumber().getAccountNumber());
		Account receiverAccount = accountRepository.findByAccountNumber(transactionDTO.getReceiverAccountNumber().getAccountNumber());

		try {
			if (transaction.getAmount() > senderAccount.getBalance()) {
				throw new ResourceNoteFoundException("Not required balance");
			} else {

				if (senderAccount.equals(receiverAccount)) {
					throw new ResourceNoteFoundException("NO transaction itself Account..");
				} else {
					if (transaction.getAmount() < 1) {
						throw new ResourceNoteFoundException("Not Amount");
					} else {

						if (senderAccount.getBalance() == 0 || senderAccount.getBalance() == -1) {
							throw new ResourceNoteFoundException("Balance insufficient");
						} else {
							if (senderAccount == null) {
								throw new ResourceNoteFoundException("Sender account not found");
							} else {
								if (receiverAccount == null) {
									throw new ResourceNoteFoundException("Receiver account not found");
								} else {
									SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
									int count = 1;
									String id = "TRX-" + count + "-" + dateFormat.format(new Date());
									boolean isTrue = true;
									while (isTrue) {
										Transaction byId = transactionRepository.findById(id);
										if (byId == null) {
											transaction.setId(id);
											isTrue = false;
										} else {
											count++;
											id = "TRX-" + count + "-" + dateFormat.format(new Date());
										}
									}

									double amount = Double.parseDouble(transactionDTO.getAmount());
									senderAccount.setBalance(senderAccount.getBalance() - amount);
									receiverAccount.setBalance(receiverAccount.getBalance() + amount);

									accountRepository.save(senderAccount);
									accountRepository.save(receiverAccount);
									Serializable id1 = hibernateTemplate.save(transaction);
									transaction = hibernateTemplate.get(Transaction.class, id1);
									
									System.out.println("Transaction send successfully!");
								}

							}

						}
					}
				}
			}
		} catch (NullPointerException e) {
			throw new ResourceNoteFoundException("Plz try again.");
		} catch (BadApiRequestException e) {
			e.printStackTrace();
		}
		return transfer.convertToDTO(transaction);

	}

	@Override
	public List<TransactionDTO> getAllTransactions() {
		List<Transaction> transactions = hibernateTemplate.loadAll(Transaction.class);
		return transactions.stream().map(transfer::convertToDTO).collect(Collectors.toList());
	}

	@Override
	public TransactionDTO getTransactionById(String id) {
		Transaction transaction = hibernateTemplate.get(Transaction.class, id);
		if (transaction == null) {

			throw new ResourceNoteFoundException("Transaction not exsist in DB");
		}
		return transaction != null ? transfer.convertToDTO(transaction) : null;

	}

	@Override
	@Transactional
	public TransactionDTO updateTransaction(TransactionDTO transactionDTO) {
		
		Transaction transaction = hibernateTemplate.get(Transaction.class, transactionDTO.getId());
		if (transaction == null) {
			throw new ResourceNoteFoundException("Transaction not exsist in DB");
		}
		
		Transaction update = transfer.toUpdate(transaction, transactionDTO);
		System.out.println("Updated Transactions : " +update);
		transactionRepository.save(update);
		return transfer.convertToDTO(update);
	}

	@Override
	@Transactional
	public void deleteTransaction(String id) {
		Transaction transaction = hibernateTemplate.get(Transaction.class, id);
		if (transaction != null) {
			hibernateTemplate.delete(transaction);
			System.out.println("Transaction deleted successfully!");
		} else {
			throw new ResourceNoteFoundException("Transaction with ID " + id + " not found.");
		}
	}

	@Override
	public List<TransactionDTO> getAllPaginatedTransaction(int pageNumber, int pageSize) {
		int firstResult = pageNumber * pageSize;
		List<Transaction> transactions = hibernateTemplate
				.execute(session -> session.createQuery("from Transaction", Transaction.class)
						.setFirstResult(firstResult).setMaxResults(pageSize).list());
		return transactions.stream().map(transfer::convertToDTO).collect(Collectors.toList());
	}

}

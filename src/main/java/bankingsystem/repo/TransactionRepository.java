package bankingsystem.repo;

import bankingsystem.entities.Transaction;

public interface TransactionRepository {

	void save(Transaction transaction);
    int countByTransactionIdPrefix(String prefix);
    Transaction findById(String id);
}

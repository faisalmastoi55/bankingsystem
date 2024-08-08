package bankingsystem.repo;

import bankingsystem.dtos.AccountDTO;
import bankingsystem.entities.Account;

public interface AccountRepository {

	Account findByAccountNumber(String accountNumber);
	Account findByAccount(AccountDTO accountNumber);
    void save(Account account);
    void delete(Account account);
}

package bankingsystem.service;

import java.util.List;

import bankingsystem.dtos.AccountDTO;
import bankingsystem.dtos.CustomerDTO;
import bankingsystem.helper.ApiResponseMessage;

public interface AccountService {

	AccountDTO addAccount(AccountDTO accountDTO);
    List<AccountDTO> getAllAccounts();
    AccountDTO getAccountByNumber(String accountNumber);
    AccountDTO updateAccount(AccountDTO accountDTO);
    void deleteAccount(String accountNumber);
    List<AccountDTO> getAllPaginatedAccount(int pageNumber, int pageSize);
}

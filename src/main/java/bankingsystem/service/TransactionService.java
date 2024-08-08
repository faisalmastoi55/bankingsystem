package bankingsystem.service;

import java.util.List;

import bankingsystem.dtos.AccountDTO;
import bankingsystem.dtos.TransactionDTO;
import bankingsystem.helper.ApiResponseMessage;

public interface TransactionService {

	TransactionDTO addTransaction(TransactionDTO transactionDTO);
    List<TransactionDTO> getAllTransactions();
    TransactionDTO getTransactionById(String id);
    TransactionDTO updateTransaction(TransactionDTO transactionDTO);
    void deleteTransaction(String id);
    
    List<TransactionDTO> getAllPaginatedTransaction(int pageNumber, int pageSize);
}

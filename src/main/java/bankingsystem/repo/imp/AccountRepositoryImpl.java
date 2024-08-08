package bankingsystem.repo.imp;

import org.springframework.orm.hibernate5.HibernateTemplate;

import bankingsystem.dtos.AccountDTO;
import bankingsystem.entities.Account;
import bankingsystem.repo.AccountRepository;

public class AccountRepositoryImpl implements AccountRepository {

    private final HibernateTemplate hibernateTemplate;

    public AccountRepositoryImpl(HibernateTemplate hibernateTemplate) {
        this.hibernateTemplate = hibernateTemplate;
    }

    @Override
    public Account findByAccountNumber(String accountNumber) {
        return hibernateTemplate.get(Account.class, accountNumber);
    }

    @Override
    public void save(Account account) {
        hibernateTemplate.saveOrUpdate(account);
    }

    @Override
    public void delete(Account account) {
        hibernateTemplate.delete(account);
    }

	@Override
	public Account findByAccount(AccountDTO accountNumber) {
		String accountNumber2 = accountNumber.getAccountNumber();
		System.out.println("Account Number : " +accountNumber2);
        return hibernateTemplate.get(Account.class, accountNumber2);
        
        
	}

}

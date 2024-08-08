package bankingsystem.dtos;

public class AccountDTO {
	
	private String accountNumber;
    private String balance;
    private CustomerDTO customerDTO;
	public AccountDTO(String accountNumber, String balance, CustomerDTO customerDTO) {
		super();
		this.accountNumber = accountNumber;
		this.balance = balance;
		this.customerDTO = customerDTO;
	}
	public AccountDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public CustomerDTO getCustomerDTO() {
		return customerDTO;
	}
	public void setCustomerDTO(CustomerDTO customerDTO) {
		this.customerDTO = customerDTO;
	}
	

    
}

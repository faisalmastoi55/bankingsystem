package bankingsystem.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    private String accountNumber;

    private double balance;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "senderAccount")
    private Set<Transaction> sentTransactions;

    @OneToMany(mappedBy = "receiverAccount")
    private Set<Transaction> receivedTransactions;

	public Account(String accountNumber, double balance, Customer customer, Set<Transaction> sentTransactions,
			Set<Transaction> receivedTransactions) {
		super();
		this.accountNumber = accountNumber;
		this.balance = balance;
		this.customer = customer;
		this.sentTransactions = sentTransactions;
		this.receivedTransactions = receivedTransactions;
	}

	public Account() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Set<Transaction> getSentTransactions() {
		return sentTransactions;
	}

	public void setSentTransactions(Set<Transaction> sentTransactions) {
		this.sentTransactions = sentTransactions;
	}

	public Set<Transaction> getReceivedTransactions() {
		return receivedTransactions;
	}

	public void setReceivedTransactions(Set<Transaction> receivedTransactions) {
		this.receivedTransactions = receivedTransactions;
	}

    
    
}
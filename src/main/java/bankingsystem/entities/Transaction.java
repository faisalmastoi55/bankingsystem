package bankingsystem.entities;

import javax.persistence.*;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    private String id;

    private double amount;
    private String type;

    @ManyToOne
    @JoinColumn(name = "sender_account_id")
    private Account senderAccount;

    @ManyToOne
    @JoinColumn(name = "receiver_account_id")
    private Account receiverAccount;

	public Transaction(String id, double amount, String type, Account senderAccount, Account receiverAccount) {
		super();
		this.id = id;
		this.amount = amount;
		this.type = type;
		this.senderAccount = senderAccount;
		this.receiverAccount = receiverAccount;
	}

	public Transaction() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Account getSenderAccount() {
		return senderAccount;
	}

	public void setSenderAccount(Account senderAccount) {
		this.senderAccount = senderAccount;
	}

	public Account getReceiverAccount() {
		return receiverAccount;
	}

	public void setReceiverAccount(Account receiverAccount) {
		this.receiverAccount = receiverAccount;
	}
    
    

}

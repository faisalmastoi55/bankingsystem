//package bankingsystem.dtos;
//
//public class TransactionDTO {
//
//	private String id;
//    private String amount;
//    private String type;
//    private String senderAccountNumber;
//    private String receiverAccountNumber;
//	public TransactionDTO(String id, String amount, String type, String senderAccountNumber,
//			String receiverAccountNumber) {
//		super();
//		this.id = id;
//		this.amount = amount;
//		this.type = type;
//		this.senderAccountNumber = senderAccountNumber;
//		this.receiverAccountNumber = receiverAccountNumber;
//	}
//	public TransactionDTO() {
//		super();
//		// TODO Auto-generated constructor stub
//	}
//	public String getId() {
//		return id;
//	}
//	public void setId(String id) {
//		this.id = id;
//	}
//	public String getAmount() {
//		return amount;
//	}
//	public void setAmount(String amount) {
//		this.amount = amount;
//	}
//	public String getType() {
//		return type;
//	}
//	public void setType(String type) {
//		this.type = type;
//	}
//	public String getSenderAccountNumber() {
//		return senderAccountNumber;
//	}
//	public void setSenderAccountNumber(String senderAccountNumber) {
//		this.senderAccountNumber = senderAccountNumber;
//	}
//	public String getReceiverAccountNumber() {
//		return receiverAccountNumber;
//	}
//	public void setReceiverAccountNumber(String receiverAccountNumber) {
//		this.receiverAccountNumber = receiverAccountNumber;
//	}
//    
//    
//}

package bankingsystem.dtos;

public class TransactionDTO {

	private String id;
    private String amount;
    private String type;
    private AccountDTO senderAccountNumber;
    private AccountDTO receiverAccountNumber;
	public TransactionDTO(String id, String amount, String type, AccountDTO senderAccountNumber,
			AccountDTO receiverAccountNumber) {
		super();
		this.id = id;
		this.amount = amount;
		this.type = type;
		this.senderAccountNumber = senderAccountNumber;
		this.receiverAccountNumber = receiverAccountNumber;
	}
	public TransactionDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public AccountDTO getSenderAccountNumber() {
		return senderAccountNumber;
	}
	public void setSenderAccountNumber(AccountDTO senderAccountNumber) {
		this.senderAccountNumber = senderAccountNumber;
	}
	public AccountDTO getReceiverAccountNumber() {
		return receiverAccountNumber;
	}
	public void setReceiverAccountNumber(AccountDTO receiverAccountNumber) {
		this.receiverAccountNumber = receiverAccountNumber;
	}
	
    
    
    
}

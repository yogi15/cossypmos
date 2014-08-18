package beans;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;

import util.commonUTIL;

public class Transfer extends BOObject implements Serializable,Comparable<Transfer>,Cloneable {
	transient   Hashtable<String, String> attributesData = new Hashtable<String, String>();
	private static String payment ="PAYMENT";
	private static String receipt = "RECEIPT";
	boolean isCanceled = false;
	boolean isSettled = false;
	int transferVersionID = 0;
	double quantity = 0.0;
	int tradeId;
	int productId;
	double amount;
	int linkid =0;
	int id;
	int tradeVersionID;
	String rec_pay;
	String eventType;
	String transferType;
	String transferStatus;
	String settlecurrency;
	String payerCode;
	String payerRole;
	String receiverCode;
	String receiverRole;
	String paymentStatus;
	String deliveryDate;
	String valueDate;
	String method;
	String receiverInst;
	String payerInst;
	String attributes;
	String status;
	
	int nettedTransferID;
	/**
	 * Delivery Type
	 *
	 */
	static final public String DELIVERYAFTERPAYMENT = "DAP";
	static final public String DELIVERYBEFPREPAYMENT = "DFP";
		
	public double getQuantity() {
		return quantity;
	}
	
	public boolean isTransferPayment(String transferEventType) {
		boolean flag = false;
		if(commonUTIL.isEmpty(transferEventType))
			return false;
		if(transferEventType.equalsIgnoreCase(payment))
			 flag = true;
		 
		return flag;
	}
	
	public boolean isTransferPayment() {
		return isTransferPayment(getEventType());
	}
	public boolean isTransferReceipt() {
		return isTransferReceipt(getEventType());
	}
	public boolean isTransferReceipt(String transferEventType) {
		boolean flag = false;
		if(commonUTIL.isEmpty(transferEventType))
			return false;
		if(transferEventType.equalsIgnoreCase(receipt))
			 flag = true;
		 
		return flag;
	}
	public boolean isAvaliableForNetting() {
		boolean flag = false;
		if(commonUTIL.isEmpty(getEventType()))
				return false;
		flag = isTransferPayment(getEventType());
		if(!flag)
			flag = isTransferReceipt(getEventType());
		return flag;
	}
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public int getTransferVersionID() {
		return transferVersionID;
	}

	public void setTransferVersionID(int transferVersionID) {
		this.transferVersionID = transferVersionID;
	}

	public boolean isSettled() {
		return isSettled;
	}

	public boolean isApproved() {
		return isApproved;
	}

   int bookId;
    public int getBookId() {
    	return bookId;
    }
    public void setBookId(int bookId) {
    	this.bookId = bookId;
    }

	boolean isApproved = false;
	public boolean isCanceled() {
		return isCanceled;
	}
	
	public int getLeid() {
		return leid;
	}
	public void setLeid(int leid) {
		this.leid = leid;
	}
	public Hashtable<String, String> getAttributesData() {
		if(attributesData == null) 
			attributesData = new Hashtable<String, String>();
		if(attributesData.size() == 0)
			setAttributesData(getAttributes());
		return attributesData;
	}
	


	
	public int getTradeVersionID() {
		return tradeVersionID;
	}
	public void setTradeVersionID(int tradeVersionID) {
		this.tradeVersionID = tradeVersionID;
	}





	
	public int getLinkid() {
		return linkid;
	}
	public void setLinkid(int linkid) {
		this.linkid = linkid;
	}





	
	public int getNettedTransferID() {
		return nettedTransferID;
	}
	public void setNettedTransferID(int nettedTransferID) {
		this.nettedTransferID = nettedTransferID;
	}
	public int getNettedConfigID() {
		return nettedConfigID;
	}
	public void setNettedConfigID(int nettedConfigID) {
		this.nettedConfigID = nettedConfigID;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public double getSettleAmount() {
		return settleAmount;
	}
	public void setSettleAmount(double settleAmount) {
		this.settleAmount = settleAmount;
	}





	int nettedConfigID;
	int version;
	double settleAmount;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		
		this.status = status;
		if(status.equalsIgnoreCase("CANCELLED")) 
			isCanceled = true;
		if(status.equalsIgnoreCase("SETTLED")) 
			isSettled = true;
		if(status.equalsIgnoreCase("APPROVED")) 
			isApproved = true;
		
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}





	String action;
	
	public String getAttributes() {
		return attributes;
	}
	public void setAttributes(String attributes) {
		if(attributes == null)
			return;
		this.attributes = attributes;
		setAttributesData(attributes);
		//attributes = getAttributes();
	}
	
	public void setTranserType(String transerType) {
		this.transferType = transerType;
	}
	public String getTransferStatus() {
		return transferStatus;
	}
	public void setTransferStatus(String transferStatus) {
		this.transferStatus = transferStatus;
	}
	public String getSettlecurrency() {
		return settlecurrency;
	}
	public void setSettlecurrency(String settlecurrency) {
		this.settlecurrency = settlecurrency;
	}
	public String getPayerCode() {
		return payerCode;
	}
	public void setPayerCode(String payerCode) {
		this.payerCode = payerCode;
	}
	public String getPayerRole() {
		return payerRole;
	}
	public void setPayerRole(String payerRole) {
		this.payerRole = payerRole;
	}
	public String getReceiverCode() {
		return receiverCode;
	}
	public void setReceiverCode(String receiverCode) {
		this.receiverCode = receiverCode;
	}
	public String getReceiverRole() {
		return receiverRole;
	}
	public void setReceiverRole(String receiverRole) {
		this.receiverRole = receiverRole;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public String getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	public String getValueDate() {
		return valueDate;
	}
	public void setValueDate(String valueDate) {
		this.valueDate = valueDate;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getReceiverInst() {
		return receiverInst;
	}
	public void setReceiverInst(String receiverInst) {
		this.receiverInst = receiverInst;
	}
	public String getPayerInst() {
		return payerInst;
	}
	public void setPayerInst(String payerInst) {
		this.payerInst = payerInst;
	}

	
	
	
	
    public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	
    
	public int getTradeId() {
		return tradeId;
	}
	public void setTradeId(int tradeId) {
		this.tradeId = tradeId;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getRec_pay() {
		return rec_pay;
	}
	public void setRec_pay(String rec_pay) {
		this.rec_pay = rec_pay;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setTransferType(String transerType) {
		// TODO Auto-generated method stub
		this.transferType = transerType;
	}
	
	public String getTransferType() {
		// TODO Auto-generated method stub
		return transferType;
	}
	public void addAttribues(String key,String value) {
		System.out.println(" adding attributes for transfer id " + getId());
		if(((key != null) && (!key.isEmpty())) && ((value != null) && (!value.isEmpty()))) {
			attributesData.put(key, value);
		}
		this.attributes =getALLAttributesData();
	}
	
	
	public void removeAttribues(String key) {
		if(((key != null) && (!key.isEmpty())) ) {
			attributesData.remove(key);
		}
		this.attributes =getALLAttributesData();
	}
	
	
	public String getAttributeValue(String key) {
		String value = null;
		if(attributesData.size() == 0)
			setAttributesData(attributes);
		
		return attributesData.get(key);
	}
	
	public void setAttributesData(String attributesFromTransfer) {
		if(attributesFromTransfer.equalsIgnoreCase("null") ) {
			attributesFromTransfer = null;
			return;
		}
		if(((attributesFromTransfer != null) && (!attributesFromTransfer.isEmpty()))) {
			String [] attr = attributesFromTransfer.split(";");
			for(int i=0;i<attr.length;i++) {
				String value = attr[i].substring(attr[i].indexOf('=')+1, attr[i].length());
    			String key = attr[i].substring(0, attr[i].indexOf('='));
    			attributesData.put(key,value);
    			
			}
				 
			
		}
	}
	
	public String getValueForAttribute(String attributeName) {
		return (String) attributesData.get(attributeName);
	}
	
	public boolean containTransferKey(String key) {
		if(attributesData != null)
		return attributesData.containsKey(key);
		else 
			return getAttributesData().containsKey(key);
	}
	
	public String getALLAttributesData() {
		String allAttributes = "";
		if(((attributesData != null) && (!attributesData.isEmpty()))) {
			
			Enumeration<String > keys = attributesData.keys();
			while(keys.hasMoreElements()) {
				String key = (String) keys.nextElement();
				String value = (String) attributesData.get(key);
				allAttributes = allAttributes.trim() + key +"="+value+";";
		    }
			
	        
       }
		this.attributes = allAttributes;
       return allAttributes;

	}
	@Override
	public int compareTo(Transfer transfer) {
				
		//String oldTransfer = this.getDeliveryDate()+this.getEventType()+this.getTransferType()+this.getAmount()+this.getSettlecurrency()+this.getValueDate()+this.getMethod()+this.getRec_pay()+this.getReceiverCode()+this.getReceiverInst()+this.getReceiverRole()+this.payerCode+this.getPayerInst()+this.getPayerRole()+this.getProductId();
		//String newTransfer = transfer.getDeliveryDate()+transfer.getEventType()+transfer.getTransferType()+transfer.getAmount()+transfer.getSettlecurrency()+transfer.getValueDate()+transfer.getMethod()+transfer.getRec_pay()+transfer.getReceiverCode()+transfer.getReceiverInst()+transfer.getReceiverRole()+transfer.payerCode+transfer.getPayerInst()+transfer.getPayerRole()+transfer.getProductId();
		
		String oldTransfer = new StringBuffer(this.getDeliveryDate())
								 .append(this.getEventType())
								 .append(this.getTransferType())
								 .append(this.getAmount())
								 .append(this.getSettlecurrency())
								 .append(this.getValueDate())
								 .append(this.getMethod())
								 .append(this.getRec_pay())
								 .append(this.getReceiverCode())
								 .append(this.getReceiverRole())
								 .append(this.payerCode)
								 .append(this.getPayerRole())
								 .append(this.getProductId())
								 .toString();
		
		String newTransfer = new StringBuffer(transfer.getDeliveryDate())
								 .append(transfer.getEventType())
								 .append(transfer.getTransferType())
								 .append(transfer.getAmount())
								 .append(transfer.getSettlecurrency())
								 .append(transfer.getValueDate())
								 .append(transfer.getMethod())
								 .append(transfer.getRec_pay())
								 .append(transfer.getReceiverCode())
								 .append(transfer.getReceiverRole())
								 .append(transfer.payerCode)
								 .append(transfer.getPayerRole())
								 .append(transfer.getProductId())
								 .toString();
		
		
		System.out.println(oldTransfer);
		System.out.println(newTransfer);
		int i = oldTransfer.compareTo(newTransfer);
		return i;
	}
	
	int leid = 0;
	public int getCPid() {
		return leid;
	}
	String productType = null;
		
	
	public void setCPid(int leid) {
		// TODO Auto-generated method stub
		this.leid = leid;
		
	}
	public void setProductType(String productType) {
		// TODO Auto-generated method stub
		this.productType = productType;
	}
	public String getProductType() {
		return productType;
	}
   int userID = 0;
	public int getUserid() {
		// TODO Auto-generated method stub
	return userID;
	}
	public void setUserid(int userID) {
		// TODO Auto-generated method stub
	this. userID = userID;
	}
	@Override

	public Transfer clone() throws CloneNotSupportedException {

	return (Transfer) super.clone();

	}

	public Object getDeliveryType() {
		// TODO Auto-generated method stub
		return null;
	}
	
}

package beans;

import java.io.Serializable;

import util.commonUTIL;

public class Message implements Serializable, Cloneable {
	 
	public static final String FORMAT_ISSUE_MSG_ATTR = "FORMAT ISSUE";
	 /**
     * Msg Attribute for Message references.
     * In certain instances, messages must have awareness of
     * each other (For SWIFT tags, typically)
     */
    static final public String MESSAGE_REF = "MessageRef";
    /**
	 * @return the userID
	 */
	public int getUserID() {
		return userID;
	}
	/**
	 * @param userID the userID to set
	 */
	public void setUserID(int userID) {
		this.userID = userID;
	}
	int userID = 0;
	int id = 0;
	 int tradeId = 0;
	 int transferId = 0;
	 String messageType = "";
	 String productType = "";
	 String senderName = "";
	 String senderRole = "";
	 String receiverRole = "";
	 String receiverName = "";
	 int tradeVersion = 0;
	 int transferVersion = 0;
	 String action = "";
	 String status = "";
	 String addressType = "";
	 String templateName ="";
	 int linkId = 0;
	 String messageDate = "";
	 String tradeDate = "";
	 String messageGateway = "";
	 String productSubType = "";
	 String eventType="";
	 String triggerON = "";
	 int productID =0;
	 String attributes;
	 String format = "";
	 private String _senderAddressCode;
	 protected String _receiverAddressCode;
	 int receiverID;
	 String poContactType="";	 
	 String subAction = "NEW";
	 String isUpdatedBeforeSend = "";
	 String receiverContactType="";
	 int senderID = 0;
	 int messageConfigID = 0;
	 
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTradeId() {
		return tradeId;
	}
	public void setTradeId(int tradeId) {
		this.tradeId = tradeId;
	}
	public int getTransferId() {
		return transferId;
	}
	public void setTransferId(int transferId) {
		this.transferId = transferId;
	}
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getSenderName() {
		return senderName;
	}
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	public String getSenderRole() {
		return senderRole;
	}
	public void setSenderRole(String senderRole) {
		this.senderRole = senderRole;
	}
	public String getReceiverRole() {
		return receiverRole;
	}
	public void setReceiverRole(String receiverRole) {
		this.receiverRole = receiverRole;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public int getTradeVersion() {
		return tradeVersion;
	}
	public void setTradeVersion(int tradeVersion) {
		this.tradeVersion = tradeVersion;
	}
	public int getTransferVersion() {
		return transferVersion;
	}
	public void setTransferVersion(int transferVersion) {
		this.transferVersion = transferVersion;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAddressType() {
		return addressType;
	}
	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public int getLinkId() {
		return linkId;
	}
	public void setLinkId(int linkId) {
		this.linkId = linkId;
	}
	public String getMessageDate() {
		return messageDate;
	}
	public void setMessageDate(String messageDate) {
		this.messageDate = messageDate;
	}
	public String getTradeDate() {
		return tradeDate;
	}
	public void setTradeDate(String tradeDate) {
		this.tradeDate = tradeDate;
	}
	public String getMessageGateway() {
		return messageGateway;
	}
	public void setMessageGateway(String messageGateway) {
		this.messageGateway = messageGateway;
	}
	public String getProductSubType() {
		return productSubType;
	}
	public void setProductSubType(String productSubType) {
		this.productSubType = productSubType;
	}
	
	public String getEventType() {		
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	
	public String getTriggerON() {		
		return triggerON;
	}
	public void setTriggerON(String triggerON) {		
		this.triggerON = triggerON;
	}
	
	public int getproductID() {		
		return productID;
	}
	public void setproductID(int productID) {		
		this.productID = productID;
	}	
	public String getAttributes() {
		return attributes;
	}
	public void setAttributes(String attributes) {
		this.attributes = attributes;
	}
	
	public void setFormat(String formatType) {		
		format = formatType;
	}
	public String getFormat() {		
		return format;
	}
	 /**
     * Returns the sender person's address string, which is an
     * actual address value like a fax number. See details at
     * {@link #setSenderAddressCode(String) setSenderAddressCode}.
     *
     * @return   the sender's address String, which is an actual
     * address value like a fax number
     */
	 
	 final public void setSenderAddressCode(String s) {  _senderAddressCode=s;}

    final public String getSenderAddressCode() { return _senderAddressCode;}
    final public String getReceiverAddressCode() { return _receiverAddressCode;}
    final public void setReceiverAddressCode(String s) {  _receiverAddressCode=s;}
    
	public int getReceiverId() {		
		return receiverID;
	}
	public void setReceiverId(int receiverID) {		
		this.receiverID =  receiverID;
	}
	public String getSenderContactType() {		
		return poContactType;
	}
	public String getReceiverContactType() {		
		return receiverContactType;
	}
	public String getSettleDate() {		
		return null;
	}	
	public void setSenderContactType(String poContactType) {		
		this.poContactType = poContactType;
	}	
	public void setReceiverContactType(String receiverContactType) {		
		this.receiverContactType = receiverContactType;
	}	 
	public void setSenderId(int poid) {		
		this.senderID = poid;		
	}
	public int getSenderId() {
		return senderID ;		
	}
	public boolean getExternalB() {		
		return false;
	}
	public String  getAttribute(String string) {		
		return null;
	}
	public int getVersion() {		
		return 0;
	}
	public String getLanguage() {		
		return "English";
	}	
	public void setMessageConfigID(int messageConfig) {		
		messageConfigID = messageConfig;		
	}
	public int getMessageConfigID() {
		return messageConfigID;
	}	
	public void setSubAction(String subAction) {
		this.subAction = subAction;
	}
	public String getSubAction() {
		return subAction;
	}
	
	public String getUpdateBeforeSend() {
		return isUpdatedBeforeSend;
	}
	public void setUpdateBeforeSend(String flagforUpdate) {		
		isUpdatedBeforeSend =  flagforUpdate;
	}
	
	public String getValues() {
		String values ="";

		try {
		 values = "Id="+getId()+
		                ";tradeId="+getTradeId()+
		                ";transferId="+getTransferId()+
		                ";version="+getVersion()+
		                ";tradeVersion="+getTradeVersion()+
		                ";transferVersion="+getTransferVersion()+
		                ";senderId="+ getSenderId()+
	                    ";senderName="+ getSenderName() +
	                    ";senderRole="+ getSenderRole() +	                    
	                    ";_senderAddressCode="+ getSenderAddressCode() +	                    
		                ";receiverName="+ getReceiverName() +
	                    ";receiverRole="+getReceiverRole()+
	                    ";_receiverAddressCode="+ getReceiverAddressCode() +
	                    ";receiverContactType="+ getReceiverContactType() +
	                    ";messageType="+getMessageType()+
	                     ";tradeDate="+ getTradeDate() +
	                     ";templateName="+ getTemplateName() +
	                     ";messageDate="+ getMessageDate() +
	                     ";messageGateway="+ getMessageGateway() +
	                     ";messageConfigID="+ getMessageConfigID() +
	                     ";eventType="+ getEventType().trim() +	                     	                    
	                     ";triggerON="+ getTriggerON() +
	                     ";format= "+ getFormat() +
	                      ";poContactType="+ getSenderContactType()+
	                      ";subAction="+getSubAction()+
	                      ";isUpdatedBeforeSend="+getUpdateBeforeSend()+
		                  ";attributes="+getAttributes()+		                  		      
		                  ";userID="+getUserID()+
		                  ";action="+getAction()+
		                  ";linkid="+getLinkId()+
		                  ";status="+getStatus()+
		                  ";productType="+getProductType()+
		                  ";productSubType="+getProductSubType()+
		                  ";productId="+getproductID();
		 
		}catch(Exception e) {
			commonUTIL.displayError("Trade Object", "getValues  == " + values, e);
			return values;
		}
		
		return values;
	}
}

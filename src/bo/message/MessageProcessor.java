package bo.message;

import java.rmi.RemoteException;
import java.util.Hashtable;
import java.util.Vector;

import util.commonUTIL;

import beans.Book;
import beans.LegalEntity;
import beans.Message;
import beans.MessageConfig;
import beans.Trade;
import beans.Transfer;
import bo.message.bomessagehandler.BOMessageHandler;
import dsEventProcessor.EventProcessor;
import dsEventProcessor.TradeEventProcessor;
import dsEventProcessor.TransferEventProcessor;
import dsServices.RemoteBOProcess;
import dsServices.RemoteReferenceData;
import dsServices.RemoteTrade;

public class MessageProcessor {

	RemoteBOProcess remoteBO;
	RemoteTrade remoteTrade;
	RemoteReferenceData refData;
	Hashtable<Integer,Book> books = new Hashtable<Integer,Book>();
	Hashtable<String,Vector<MessageConfig>> messageConfigs = new Hashtable<String,Vector<MessageConfig>>();
	Hashtable<Integer,LegalEntity>  legalEntitys = new  Hashtable<Integer,LegalEntity>();
	Vector<Message> publishMessages = new Vector<Message>();
	public Vector<Message> getPublishMessages() {
		return publishMessages;
	}

	public void setPublishMessages(Vector<Message> publishMessages) {
		this.publishMessages = publishMessages;
	}

	public void setRemoteBOProcess(RemoteBOProcess remoteBO) {
		// TODO Auto-generated method stub
		this.remoteBO = remoteBO;
		
	}

	public void setRefData(RemoteReferenceData refData) {
		// TODO Auto-generated method stub
		this.refData = refData;
		
	}

	public void setRemoteTrade(RemoteTrade remoteTrade) {
		// TODO Auto-generated method stub
		this.remoteTrade = remoteTrade;
		
	}

	public synchronized void processMessage(EventProcessor event) {
		// TODO Auto-generated method stub
		Trade trade = null;
		Transfer transfer = null; 
		if(event instanceof TradeEventProcessor) {
			TradeEventProcessor tradeEvent = (TradeEventProcessor) event;
			trade = tradeEvent.getTrade();
		}
		if(event instanceof TransferEventProcessor) {
			TransferEventProcessor transferEvent = (TransferEventProcessor) event;
			trade = transferEvent.getTrade();
			transfer = transferEvent.getTransfer();
		}
		Vector<MessageConfig> messConfigs = getMessageConfig(event,trade);
		Hashtable<String,Vector<Message>> filterMessages = new Hashtable<String,Vector<Message>>();
		Vector<Message> messages = new Vector<Message>();
		if(messConfigs == null || messConfigs.isEmpty() ) {
			commonUTIL.display("MessageProcessor" , "Message Configuration not Found on " + event.getEventType() + " for Proudct " + trade.getProductType() 
					+ " subType " + trade.getTradedesc1() + "tradeEvent " + event.getEventType() + " and selected PO");
		    return;
		}
		for(int i=0;i<messConfigs.size();i++) {
			MessageConfig messConfig = messConfigs.get(i);
			BOMessageHandler boHandler = BOMessageHandler.getBOHandler(messConfig.getProductType(), messConfig.getProductSubType());
			if(boHandler == null) {
				commonUTIL.display("MessageProcessor" , "Message Handler not Found  for Product " + trade.getProductType() + " subType " + trade.getTradedesc1());
			    return;
			}
			LegalEntity receiver = getLegalEntity(messConfig.getReceiverID());
			LegalEntity sender = getLegalEntity(messConfig.getPoid());
			sender.setRole("PO");// this is might be issue 
			Message message = null;
			if(event instanceof TradeEventProcessor) {
			    message = boHandler.fillMessage(trade, null, messConfig,"TRADE",null,(TradeEventProcessor) event,receiver,sender);
			   // messages.add(message); 
			    filterOldMessages(message.getMessageConfigID(),trade.getId(),message,"TRADE",filterMessages);
				saveFilterMessages(filterMessages,trade,transfer);
			}
			if(event instanceof TransferEventProcessor) {
				 message = boHandler.fillMessage(trade, transfer, messConfig,"TRANSFER",(TransferEventProcessor) event,null,receiver,sender);
			//	 messages.add(message); 
				 filterOldMessages(message.getMessageConfigID(),transfer.getId(),message,"TRANSFER",filterMessages);
					saveFilterMessages(filterMessages,trade,transfer);
			}
			  
		}
		
		
	
	}
	

	private LegalEntity getLegalEntity(int id) {
		// TODO Auto-generated method stub
		LegalEntity le = null;
		synchronized (legalEntitys) {
			le = legalEntitys.get(id);
				}
		if(le == null) {
			try {
				le = refData.selectLE(id);
				if(le != null) 
					legalEntitys.put(le.getId(), le);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return le;
	}

	/*public void processMessage(TransferEventProcessor transferEvent, Transfer transfer) {
		// TODO Auto-generated method stub
		Trade trade = transferEvent.getTrade();
		Vector<MessageConfig> messConfigs = getMessageConfig(transferEvent,transferEvent.getTrade());
		Hashtable<String,Vector<Message>> filterMessages = new Hashtable<String,Vector<Message>>();
		Vector<Message> messages = new Vector<Message>();
		if(messConfigs == null || messConfigs.isEmpty() ) {
			commonUTIL.display("MessageProcessor" , "Message Configuration not Found on " + transferEvent.getEventType() + " for Proudct " + trade.getProductType() + " subType " + trade.getTradedesc1());
		    return;
		}
		for(int i=0;i<messConfigs.size();i++) {
			MessageConfig messConfig = messConfigs.get(i);
			BOMessageHandler boHandler = BOMessageHandler.getBOHandler(messConfig.getProductType(), messConfig.getProductSubType());
			LegalEntity receiver = getLegalEntity(messConfig.getReceiverID());
			LegalEntity sender = getLegalEntity(messConfig.getPoid());
			Message message = boHandler.fillMessage(trade, transfer, messConfig,"TRANSFER",transferEvent,null,receiver,sender);
			messages.add(message);
		}
		filterOldMessages(trade.getId(),messages,"TRANSFER",filterMessages);
		
	} */
	
	private void filterOldMessages(int messageConfigid,int objectID,Message message,String eventTriggerON,Hashtable<String,Vector<Message>> filterMessages) {
		// TODO Auto-generated method stub
		Vector<Message> insertMessages = new Vector<Message>();
		Vector<Message> updatetMessages = new Vector<Message>();
		if(message == null )
			return;
		//for(int i=0;i<messages.size();i++) {
		//	Message message = messages.get(i);
			Vector<Message> oldmess = getOLDMessage(messageConfigid,objectID,message.getEventType(),eventTriggerON);
			if(commonUTIL.isEmpty(oldmess)) {
				
				
				if(message.getEventType().contains("CANCELLED")) {
					message.setSubAction("CANCEL");  // this logic needs to be changed as first it must check if confirmation has been send or not if yes then send cancel messages.
				} else {
					message.setSubAction("NEW");
				}
				insertMessages.add(message);
			} else {
				// checkout code need to added.
			    if(!commonUTIL.isEmpty(oldmess) && oldmess !=null) {
			    	
			    		Message oldMessage = oldmess.get(0);  // always going to first record bz of we have used order id by in where clause.
			    		
			    			
			    		if(oldMessage.getSubAction().equalsIgnoreCase("NEW") && isMessageWasSend(oldMessage,oldmess)) {
			    			message.setSubAction("AMEND");
			    			message.setLinkId(oldMessage.getId());
			    			insertMessages.add(message);
			    		} else {
			    			message.setSubAction("NEW");
			    			message.setId(oldMessage.getId());
			    			message.setUpdateBeforeSend("TRUE");
			    			updatetMessages.add(message);
			    		//	message.setLinkId(oldMessage.getLinkId());
			    			//message.
			    		}
			    		if(message.getEventType().contains("CANCELLED") && isMessageWasSend(oldMessage,oldmess)) {
			    			message.setSubAction("CANCEL");
			    			message.setLinkId(oldMessage.getId());
			    			if(isMessageWasSend(oldMessage,oldmess)) {
			    			 insertMessages.add(message);
			    		} else {
			    			message.setId(oldMessage.getId());
			    			message.setSubAction("NEW");
			    			message.setUpdateBeforeSend("TRUE");
			    			updatetMessages.add(message);
			    		}
			    				
			    		}
			    		
			    		
			    		
			    	
			    }
		//	}
		}
		filterMessages.put("insert",insertMessages);
		filterMessages.put("update",updatetMessages);
	}

	
	private boolean isMessageWasSend(Message oldMessage,Vector<Message> Oldmessages) {
		// TODO Auto-generated method stub
		boolean flag = false;
		if(oldMessage.getStatus().equalsIgnoreCase("SEND"))
			flag =  true;
		for(int i=0;i<Oldmessages.size();i++) {
			Message message  = Oldmessages.get(i);
			if(message.getStatus().equalsIgnoreCase("SEND")) {
				flag =  true;
				break;
			}
			
		}
		return flag;
		
		
	}

	private Vector<Message> getOLDMessage(int messageConfig,int tradeID,String eventType,String messageOn) {
		Vector<Message> message = null;
		try {
			message = remoteBO.getMessages(messageConfig,tradeID, eventType, messageOn);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return message;
		
	}
	
	
	private Vector<MessageConfig> getMessageConfig(EventProcessor tradeEvent, Trade trade) {
		Vector<MessageConfig> messConfigs = null;
		
		try {
			String messConfig = trade.getProductType()+"_"+trade.getTradedesc1()+"_"+tradeEvent.getEventType()+"_"+getBook(trade.getBookId()).getLe_id();	
			synchronized (messageConfigs) {
				messConfigs = messageConfigs.get(messConfig);				
			}
			if(messConfigs == null || messConfig.isEmpty()) {
				messConfigs = (Vector<MessageConfig>) refData.getMessageConfig(trade.getProductType(), trade.getTradedesc1(), tradeEvent.getEventType(), getBook(trade.getBookId()).getLe_id());
			   if(messConfigs != null)
				messageConfigs.put(messConfig, messConfigs);
			} 
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return messConfigs;
	}
	
	private Book getBook(int bookID) {
				Book book = null;
				synchronized (books) {
						book = books.get(bookID);
				} 
				if(book == null) {
						book = new Book();
						book.setBookno(bookID);
						try {
								book = refData.selectBook(book);
								books.put(bookID, book);
						} catch (RemoteException e) {
							// TODO Auto-generated catch block
							commonUTIL.displayError("MessaggeProcessor", "getBook  getting While reterive Book on id == "+bookID , e);
						}
				}
		return book;
		
	}
	
	private void saveFilterMessages(
			Hashtable<String, Vector<Message>> filterMessages,Trade trade,Transfer transfer) {
		// TODO Auto-generated method stub
		Vector<Message> newpublishMessages = new Vector<Message>();
		if(filterMessages == null || filterMessages.isEmpty()) {
			return;
		}
		Vector<Message> insertMessage = filterMessages.get("insert");
		Vector<Message> updateMessage = filterMessages.get("update");
			saveMessages(insertMessage,newpublishMessages,"insert",trade,transfer);
			saveMessages(updateMessage,newpublishMessages,"update",trade,transfer);
			publishMessages.isEmpty();
			setPublishMessages(newpublishMessages);
			
			
		//remoteBO.save
	}
	
	
    private void saveMessages(Vector<Message> messages,Vector<Message> publishMessage,String sqlType,Trade trade,Transfer transfer) {
    	try {
			Vector<Message> messagesData =	remoteBO.saveMesage(messages, sqlType,trade,transfer);
			if(messagesData != null ) {
				for(int i=0;i<messagesData.size();i++) {
					publishMessage.add(messagesData.get(i));
				}
			}
			
	} catch (RemoteException e) {
	// TODO Auto-generated catch block
		e.printStackTrace();
	}
    }
		
		
	
}

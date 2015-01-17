package bo.message;

import java.rmi.RemoteException;
import java.util.Hashtable;
import java.util.Vector;

import logAppender.MessageServiceAppender;
import logAppender.TransferServiceAppender;

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
import dsManager.MessageManager;
import dsServices.RemoteBOProcess;
import dsServices.RemoteReferenceData;
import dsServices.RemoteTrade;

public class MessageProcessor extends Thread {

	RemoteBOProcess remoteBO;
	RemoteTrade remoteTrade;
	RemoteReferenceData refData;
	Hashtable<Integer,Book> books = new Hashtable<Integer,Book>();
	Hashtable<String,Vector<MessageConfig>> messageConfigs = new Hashtable<String,Vector<MessageConfig>>();
	Hashtable<Integer,LegalEntity>  legalEntitys = new  Hashtable<Integer,LegalEntity>();
	Hashtable<String,Integer> duplicateEventCheck = new Hashtable<String,Integer>();
	Vector<Message> publishMessages = new Vector<Message>();
	MessageManager manager = null;
	/**
	 * @return the manager
	 */
	public MessageManager getManager() {
		return manager;
	}

	/**
	 * @param manager the manager to set
	 */
	public void setMessageManager(MessageManager manager) {
		this.manager = manager;
	}


	int counter = 0;
	boolean eventUnderProcess = true;
	public Vector<Message> getPublishMessages() { // this method is access by MesssageManager
		return publishMessages; 
	}

	public void setPublishMessages(Vector<Message> publishMessages1) { // this method is access within Processor 
		if((publishMessages1 != null) || (!commonUTIL.isEmpty(publishMessages1))) {
			for(int i=0;i<publishMessages1.size();i++) 
				publishMessages.add(publishMessages1.get(i)); 
		}
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
			MessageServiceAppender.printLog("DEBUG", "MessageProcessor Entered in  processing of   ****** " +   trade.getId() + " on "+ trade.getStatus() + " status ");
				
			trade = tradeEvent.getTrade();
			if(trade == null) {
				try {
				trade = 	remoteTrade.getTradeOnVersion(tradeEvent.getObjectID(), tradeEvent.getObjectVersionID());
				tradeEvent.setTrade(trade);
					
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			duplicateEventCheck.put(trade.getId()+"_"+trade.getStatus()+"_"+trade.getVersion(),tradeEvent.getEventid());
		}
		if(event instanceof TransferEventProcessor) {
			
			TransferEventProcessor transferEvent = (TransferEventProcessor) event;
			MessageServiceAppender.printLog("DEBUG", "MessageProcessor Entered in  processing of   ****** " +   transfer.getId() + " on "+ trade.getStatus() + " status ");
			
			trade = transferEvent.getTrade();
			transfer = transferEvent.getTransfer();
			duplicateEventCheck.put(transfer.getId()+"_"+transfer.getStatus()+"_"+transfer.getVersion(),transferEvent.getEventid());
		}
		Vector<MessageConfig> messConfigs = getMessageConfig(event,trade);
		MessageServiceAppender.printLog("DEBUG", "MessageProcessor found   ****** " +   messConfigs.size() + " MessageConfig  on "+ trade.getStatus() + " status ");
		
		Hashtable<String,Vector<Message>> filterMessages = new Hashtable<String,Vector<Message>>();
		Vector<Message> messages = new Vector<Message>();
		if(messConfigs == null || messConfigs.isEmpty() ) {
			MessageServiceAppender.printLog("DEBUG",  "Message Configuration not Found on " + event.getEventType() + " for Proudct " + trade.getProductType() + " subType " + trade.getTradedesc1());
			
			commonUTIL.display("MessageProcessor" , "Message Configuration not Found on " + event.getEventType() + " for Proudct " + trade.getProductType() + " subType " + trade.getTradedesc1());
			manager.updateEventProcess(event);
			return;
		}
		for(int i=0;i<messConfigs.size();i++) {
			MessageConfig messConfig = messConfigs.get(i);
			MessageServiceAppender.printLog("DEBUG", "MessageProcessor geneating MessageBOHandler for  productTYpe " + messConfig.getProductType() + " and subTYpe " +   trade.getTradedesc1());
			
			BOMessageHandler boHandler = BOMessageHandler.getBOHandler(messConfig.getProductType(), messConfig.getProductSubType());
			MessageServiceAppender.printLog("DEBUG", "MessageProcessor Found MessageBOHandler for  productTYpe " + messConfig.getProductType() + " and subTYpe " +   trade.getTradedesc1());
			
			if(boHandler == null) {
				commonUTIL.display("MessageProcessor" , "Message Handler not Found  for Product " + trade.getProductType() + " subType " + trade.getTradedesc1());
				MessageServiceAppender.printLog("DEBUG", "Message Handler not Found  for Product " + trade.getProductType() + " subType " + trade.getTradedesc1());
				
				manager.updateEventProcess(event);
				return;
			}
			LegalEntity receiver = getLegalEntity(messConfig.getReceiverID());
			MessageServiceAppender.printLog("DEBUG", "MessageProcessor get Receiver " + receiver.getName() + " for messConfig " +   messConfig.getId());
			
			LegalEntity sender = getLegalEntity(messConfig.getPoid());
			MessageServiceAppender.printLog("DEBUG", "MessageProcessor get Sender " + sender.getName() + " for messConfig " +   messConfig.getId());
			
			//sender.setRole("PO");// this is might be issue 
			Message message = null;
			if(event instanceof TradeEventProcessor) {
				MessageServiceAppender.printLog("DEBUG", "MessageProcessor Starting process to Fill Message Object " + event.getEventType() + " for messConfig " +   messConfig.getId() + " "+trade.getId());
				
			    message = boHandler.fillMessage(trade, null, messConfig,"TRADE",null,(TradeEventProcessor) event,receiver,sender);
			   // messages.add(message); 
			    MessageServiceAppender.printLog("DEBUG", "MessageProcessor Starting process filtering of  Message Object " + event.getEventType() + " for messConfig " +   messConfig.getId() + " "+trade.getId());
				   
			    filterOldMessages(message.getMessageConfigID(),trade.getId(),message,"TRADE",filterMessages);
			    MessageServiceAppender.printLog("DEBUG", "MessageProcessor Complete   filtering of  Message Object t" + event.getEventType() + " for messConfig " +   messConfig.getId() + " "+trade.getId());
			    MessageServiceAppender.printLog("DEBUG", "MessageProcessor Starting saving and update  of  Message Object t" + event.getEventType() + " for messConfig " +   messConfig.getId() + " "+trade.getId());
				
				saveFilterMessages(filterMessages,trade,transfer,event);
				MessageServiceAppender.printLog("DEBUG", "MessageProcessor Ending of  saving and update  of  Message Object t" + event.getEventType() + " for messConfig " +   messConfig.getId() + " "+trade.getId());
				
			}
			if(event instanceof TransferEventProcessor) {
				 message = boHandler.fillMessage(trade, transfer, messConfig,"TRANSFER",(TransferEventProcessor) event,null,receiver,sender);
			//	 messages.add(message); 
				 filterOldMessages(message.getMessageConfigID(),transfer.getId(),message,"TRANSFER",filterMessages);
					saveFilterMessages(filterMessages,trade,transfer,event);
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
		   MessageServiceAppender.printLog("DEBUG", "MessageProcessor in  filterOldMessages of  Message Object for messageCOnfig " + " "+messageConfigid + " for message eventType " + message.getEventType() + " " + message.getTradeId());
			
		Vector<Message> insertMessages = new Vector<Message>();
		Vector<Message> updatetMessages = new Vector<Message>();
		if(message == null )
			return;
		//for(int i=0;i<messages.size();i++) {
		//	Message message = messages.get(i);
			Vector<Message> oldmess = getOLDMessage(messageConfigid,objectID,message.getEventType(),eventTriggerON);
			MessageServiceAppender.printLog("DEBUG", "MessageProcessor in  filterOldMessages found "+oldmess.size() +"  oldmessages against messconfig " + message.getMessageConfigID() + " for trade "+ message.getTradeId() + " against event "+ message.getEventType() + " with subAction as " + message.getSubAction()); 
			if(commonUTIL.isEmpty(oldmess)) {
				MessageServiceAppender.printLog("DEBUG", "MessageProcessor in  filterOldMessages found zero oldMessages " + message.getTradeId() + " eventTYpe "+message.getEventType());
				
				if(message.getEventType().contains("CANCELLED")) {
					
					message.setSubAction("CANCEL");  // this logic needs to be changed as first it must check if confirmation has been send or not if yes then send cancel messages.
				} else {
					message.setSubAction("NEW");
				}

				MessageServiceAppender.printLog("DEBUG", "MessageProcessor in  filterOldMessages setting subAction " + message.getSubAction() + " aganist message Action "+message.getAction()); 
				insertMessages.add(message);
				MessageServiceAppender.printLog("DEBUG", "MessageProcessor in filterOldMessage adding message in insert Vector as old message are zero "); 
				
			} else {
				// checkout code need to added.
			    if(!commonUTIL.isEmpty(oldmess) && oldmess !=null) {
			    	MessageServiceAppender.printLog("DEBUG", "MessageProcessor in  filterOldMessages found "+oldmess.size() + "  oldMessages on " + message.getTradeId() + " on eventTYpe "+message.getEventType());
					
			    		Message oldMessage = oldmess.get(0);  // always going to first(latest) record bz of we have used order id by in where clause.
			    		
			    			
			    		if(oldMessage.getSubAction().equalsIgnoreCase("NEW") && isMessageWasSend(oldMessage,oldmess)) {
			    			message.setSubAction("AMEND");
			    			message.setLinkId(oldMessage.getId());
			    			insertMessages.add(message);
			    			MessageServiceAppender.printLog("DEBUG", "MessageProcessor in  filterOldMessages setting subAction " + message.getSubAction() + " aganist message Action "+message.getAction() + " oldMessage found with subaction of oldMessage as "+oldMessage.getSubAction()); 
			    			
			    		} else {
			    			message.setSubAction("NEW");
			    			message.setId(oldMessage.getId());
			    			message.setUpdateBeforeSend("TRUE");
			    			MessageServiceAppender.printLog("DEBUG", "MessageProcessor in  filterOldMessages setting subAction " + message.getSubAction() + " aganist message Action "+message.getAction() + " oldMessage found with subaction of oldMessage as "+oldMessage.getSubAction()); 
			    			
			    			updatetMessages.add(message);
			    		//	message.setLinkId(oldMessage.getLinkId());
			    			//message.
			    		}
			    		if(message.getEventType().contains("CANCELLED") && isMessageWasSend(oldMessage,oldmess)) {
			    			message.setSubAction("CANCEL");
			    			message.setLinkId(oldMessage.getId());
			    			if(isMessageWasSend(oldMessage,oldmess)) {
			    			 insertMessages.add(message);
			    				MessageServiceAppender.printLog("DEBUG", "MessageProcessor in  filterOldMessages setting subAction " + message.getSubAction() + " aganist message Action "+message.getAction() + " oldMessage found with subaction of oldMessage as "+oldMessage.getSubAction()); 
					    		
			    		} else {
			    			message.setId(oldMessage.getId());
			    			message.setSubAction("NEW");
			    			message.setUpdateBeforeSend("TRUE");
			    			MessageServiceAppender.printLog("DEBUG", "MessageProcessor in  filterOldMessages setting subAction " + message.getSubAction() + " aganist message Action "+message.getAction() + " oldMessage found with subaction of oldMessage as "+oldMessage.getSubAction()); 
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
			if(messConfigs == null) {
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
			Hashtable<String, Vector<Message>> filterMessages,Trade trade,Transfer transfer,EventProcessor event) {
		// TODO Auto-generated method stub
		Vector<Message> newpublishMessages = new Vector<Message>();
		if(filterMessages == null || filterMessages.isEmpty()) {
			return;
		}
		Vector<Message> insertMessage = filterMessages.get("insert");
		Vector<Message> updateMessage = filterMessages.get("update");
			saveMessages(insertMessage,newpublishMessages,"insert",trade,transfer);
			saveMessages(updateMessage,newpublishMessages,"update",trade,transfer);
			//publishMessages.isEmpty();
			setPublishMessages(newpublishMessages);
			manager.updateEventProcess(event);
			
			
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

    public void run(){
		  for( ; ; ) {
			  try {
				Thread.sleep(300);
				
					 
					
				
				
				if(manager.balance.size() > counter) {
					 System.out.println(":pppp:"+  manager.balance.size() + " counter " + counter);
					
						EventProcessor event = null;
						
							 synchronized (manager.transferEvents) {
					        	 
								 event   = manager.balance.get(counter);
							}
							if(event == null)
								return;
							eventUnderProcess = false;
							if(event instanceof TradeEventProcessor) {
								TradeEventProcessor tradeEvent = (TradeEventProcessor) event;
							      
							      if(!isDuplicateEvent(tradeEvent.getTrade())) {
							    	  manager.publishMessage(tradeEvent.getTrade(), null);
							      }
							}
							if(event instanceof TransferEventProcessor) {
								TransferEventProcessor transferEvent = (TransferEventProcessor) event;
							      
							      if(!isDuplicateEvent(transferEvent.getTransfer())) {
							    	  manager.publishMessage(transferEvent.getTrade(), transferEvent.getTransfer());
							      }
							}
							 
							 counter = counter + 1;
							// eventUnderProcess = true;
							  
						 
					  
				 }
				
				 
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  
		  }
	  }
    private boolean isDuplicateEvent(Trade trade) {
		boolean flag = false;
		String key = trade.getId()+"_"+trade.getStatus()+"_"+trade.getVersion();
		System.out.println(" for key = " + key); 
		if(duplicateEventCheck.containsKey(key))
			flag = true;
		return flag;
	}
	
	
    private boolean isDuplicateEvent(Transfer transfer) {
		boolean flag = false;
		String key = transfer.getId()+"_"+transfer.getStatus()+"_"+transfer.getVersion();
		System.out.println(" for key = " + key); 
		if(duplicateEventCheck.containsKey(key))
			flag = true;
		return flag;
	}
	
	
	
	
}

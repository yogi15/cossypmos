package dsServices;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.util.Collection;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import mqServices.messageProducer.CreateNewMessage;

import util.commonUTIL;
import util.common.DateU;
import wfManager.WFHandler;

import dbSQL.MessageSQL;
import dbSQL.PostingSQL;
import dbSQL.ProductSQL;
import dbSQL.TaskSQL;
import dbSQL.TransferSQL;
import dbSQL.WFConfigSQL;
import dbSQL.dsSQL;
import dsEventProcessor.TaskEventProcessor;
import dsEventProcessor.TransferEventProcessor;

import beans.BOObject;
import beans.DocumentInfo;
import beans.Message;
import beans.NettingConfig;
import beans.Posting;
import beans.Product;
import beans.Task;
import beans.Trade;
import beans.Transfer;
import beans.WFConfig;
import bo.swift.bic.BICSwiftData;

public class BOProcessImp implements RemoteBOProcess {
	CreateNewMessage newMessage = null;
	Hashtable<String,Transfer> nettedTransfers = new Hashtable<String,Transfer>();
	WFHandler wfhandler = null;
	 public static  ServerConnectionUtil de = null;
	 RemoteTrade remoteTrade = null;
		public BOProcessImp() {
			
			
		}
	@Override
	public void removePosting(Posting posting) throws RemoteException {
		// TODO Auto-generated method stub
		PostingSQL.delete(posting, dsSQL.getConn());
		
	}

	@Override
	public void removeTransfer(Transfer transfer) throws RemoteException {
		// TODO Auto-generated method stub
		TransferSQL.delete(transfer, dsSQL.getConn());
		
	}

	@Override
	public int savePosting(Posting posting) throws RemoteException {
		// TODO Auto-generated method stub
		return PostingSQL.save(posting, dsSQL.getConn());
	}

	@Override
	public int saveTransfer(Transfer transfer) throws RemoteException {
		return TransferSQL.save(transfer, dsSQL.getConn());
	}

	@Override
	public Posting selectPosting(Posting Posting) throws RemoteException {
		// TODO Auto-generated method stub
		Vector v1 = (Vector) PostingSQL.selectPosting( Posting.getId(), dsSQL.getConn());
		return (Posting) v1.elementAt(0);
	}

	@Override
	public Transfer selectTransfer(Transfer transfer) throws RemoteException {
		// TODO Auto-generated method stub
		Vector v1 = (Vector) TransferSQL.selectTransfer(transfer.getId(), dsSQL.getConn());
		if(commonUTIL.isEmpty(v1))
			return null;
		return (Transfer) v1.elementAt(0);
	}

	@Override
	public void updatePosting(Posting Posting) throws RemoteException {
		// TODO Auto-generated method stub
		PostingSQL.update(Posting, dsSQL.getConn());
	}

	@Override
	public void updateTransfer(Transfer transfer) throws RemoteException {
		// TODO Auto-generated method stub
		TransferSQL.update(transfer, dsSQL.getConn());
		
	}

	@Override
	public Collection queryWhere(String boObject, String where)
			throws RemoteException {
		// TODO Auto-generated method stub
		
		if(boObject.equalsIgnoreCase("Transfer")) {
			return TransferSQL.selectWhere(where,dsSQL.getConn());
		} else if(boObject.equalsIgnoreCase("Posting"))  {
			return PostingSQL.selectWhere(where,dsSQL.getConn());
		}if(boObject.equalsIgnoreCase("Sdi")) {
			//SdiSQL.selectWhere(where,dsSQL.getConn());
		}
		return null;
		
		
		
		
		
		
		
		
		
	}
	

public void setNewMessage(CreateNewMessage newMessage) {
	this.newMessage = newMessage;
}


public CreateNewMessage getNewMessage() {
	return newMessage;
}
public void startProducingMessage() {
	   newMessage	 = new CreateNewMessage(commonUTIL.getLocalHostName()+":61616");
	  
		Thread sendMessage =  new Thread(newMessage);
		 setNewMessage(newMessage);
		 sendMessage.start();
		 
}
	@Override
	public void publishnewTransfer(String messageIndicator, String messageType,
			Object object) throws RemoteException {
		try{   
			if(getNewMessage() == null) {
					startProducingMessage();
					newMessage.produceNewMessage(messageIndicator,"TRANSFER",messageType,(Serializable) object,"true"); 
			} else {
			
			newMessage.produceNewMessage(messageIndicator,"TRANSFER",messageType,(Serializable) object,null); 
			//newMessage.run();
			}
			}catch(Exception e){
					commonUTIL.displayError("BOProcessImp", "publishnewTransfer", e);
					}   
	//de.publishEvent(messageIndicator,"TRANSFER",messageType,(Serializable) object);
		
		}

	@Override
	public  Collection saveTransfers(Vector<Transfer> transfers, String type,String tradeStatus,Trade trade,int userID)
			throws RemoteException {
		Vector<Transfer> newTransfer = new Vector();
		// TODO Auto-generated method stub
		if(!transfers.isEmpty()) {
			if(type.equalsIgnoreCase("insert")) {
				Iterator<Transfer > trans = transfers.iterator();
				while(trans.hasNext()) {
			     Transfer trs = trans.next();
			     trs.setStatus("NONE");
			     trs.setAction("NEW");
			    
			   //  System.out.println("id == "+ trs.getId() + " action == " + trs.getAction() + " status " + trs.getStatus());
			     Vector<String> statusMessages = new Vector<String>();
			    WFConfig wfs = getStatusOnTransferAction(trs,trs.getStatus(),statusMessages,trade);
			     
			     trs.setStatus(wfs.getOrgStatus());
			     trs.setUserid(userID);
				 int id = 	saveTransfer(trs);
				 trs.setId(id);
				 trs.setVersion(1);  // not sure how this will work but need to tested properly temperary soultion.
				 Task task =  processTask(trs,trade,userID,wfs);
				 publishTask(task,trade,trs);
				 newTransfer.addElement(trs);
				 
				}
			} else if(type.equalsIgnoreCase("update")) {
				Iterator<Transfer > trans = transfers.iterator();
				while(trans.hasNext()) {
			     Transfer trs = trans.next();
			     System.out.println("*************************vv   "+trs.getId() + "  " + trade.getId() );
			     Transfer oldTransfer = trs;
		//	     System.out.println("id == "+ trs.getId() + " action == " + trs.getAction() + " status " + trs.getStatus());
			     Vector<String> statusMessages = new Vector<String>();
			     WFConfig wfs = getStatusOnTransferAction(trs,trs.getStatus(),statusMessages,trade);
			     
			     trs.setStatus(wfs.getOrgStatus());
			     trs.setUserid(userID);
					updateTransfer(trs);
					 publishTaskOnOldTransfer(oldTransfer,trade,userID);
				//	Task task =  processTask(trs,trade,userID,wfs);
					// publishTask(task,trade,trs);
				 newTransfer.addElement(trs);
				 
				}
			}else if(type.equalsIgnoreCase("delete")) {
				Iterator<Transfer > trans = transfers.iterator();
				while(trans.hasNext()) {
			     Transfer trs = trans.next();
			     trs.setAction("CANCEL");
			     trs.setUserid(userID);
			     Vector<String> statusMessages = new Vector<String>();
			     WFConfig wfs = getStatusOnTransferAction(trs,trs.getStatus(),statusMessages,trade);
			     
			     trs.setStatus(wfs.getOrgStatus());
			     updateTransfer(trs);
			     Task task =  processTask(trs,trade,userID,wfs);
				 publishTask(task,trade,trs);
				 
				}
			}
		}
		
		
		
		return newTransfer;
	}

	
	
	


	private Task updateTaskwithUpdatedTrasnfer(Task task,Transfer transfer,Trade trade,int userID) {
		task.setTradeID(transfer.getTradeId());
		task.setProductID(transfer.getProductId());
		task.setTransferID(transfer.getId());
		//task.setType(trade.getStatus());
		task.setAction(transfer.getAction());
		task.setProductType(task.getProductType());
		
		task.setStatus(transfer.getStatus());
		task.setTaskDate(commonUTIL.dateToString(commonUTIL.getCurrentDate()));
		task.setTradeDate(trade.getTradeDate());
		task.setTransferDate(transfer.getValueDate());
		task.setTaskstatus(task.getTaskstatus());
		task.setCurrency(transfer.getSettlecurrency());
		task.setBookid(transfer.getBookId());
		task.setEvent_type(transfer.getStatus()+"_"+transfer.getEventType());
		task.setType(task.getType());
		task.setProductType(transfer.getProductType());
		task.setUserid(userID);
		task.setTransferVersionID(transfer.getVersion());
		task.setTradeVersionID(trade.getVersion());
		return task;
	}
	
	
	private void publishTaskOnOldTransfer(Transfer oldTransfer,Trade trade,int userid) {
	if(oldTransfer.getTradeId() == 0)
		return;
	String sql = "";
	if(oldTransfer.getId() != 0 ) {
		String sqlwhere = "  transferid = "+oldTransfer.getId() + " and tradeid = "+ oldTransfer.getTradeId() + " and  transferversion = " + oldTransfer.getVersion() + " and task_status not in ('2') ";
		Task task = TaskSQL.getTaskOnTransferIDandTransferVersion(oldTransfer, dsSQL.getConn());
		updateTaskwithUpdatedTrasnfer(task,oldTransfer,trade,userid);
		boolean updateFlag = TaskSQL.update(task, dsSQL.getConn());//need to pass system user TOBE done later
		if(updateFlag) {
		
	
					 if(remoteTrade == null)
						 initRemoteInterface();
					try {
						remoteTrade.publishnewTrade("POS_NEWTRADE","Object",getTaskEvent(task,trade,oldTransfer));
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
			}
			
			
		}
	}


	
private Task processTask(Transfer transfer,Trade trade,int userID,WFConfig wfc) {
	if(transfer.getTradeId() == 0)
		return null;
	// TODO Auto-generated method stub
	Task task = null;
	if(wfc == null) {
		task = new Task();
		task.setTradeID(transfer.getTradeId());
		task.setProductID(transfer.getProductId());
		task.setTransferID(transfer.getId());
		//task.setType(trade.getStatus());
		task.setAction(transfer.getAction());
		task.setStatus(transfer.getStatus());
		task.setTaskDate(commonUTIL.dateToString(commonUTIL.getCurrentDate()));
		task.setTradeDate(trade.getTradeDate());
		task.setTransferDate(transfer.getValueDate());
		task.setTaskstatus("0");
		task.setCurrency(transfer.getSettlecurrency());
		task.setBookid(transfer.getBookId());
		task.setEvent_type(transfer.getStatus()+"_"+transfer.getEventType());
		task.setType("TRANSFER");
		task.setProductType(transfer.getProductType());
		task.setUserid(userID);
		task.setTransferVersionID(transfer.getVersion());
		task.setTradeVersionID(trade.getVersion());
		task.setId(0);
		return task;
	} 
	if(wfc.isTask()) {

	 task = new Task();
	task.setTradeID(transfer.getTradeId());
	task.setProductID(transfer.getProductId());
	task.setTransferID(transfer.getId());
	//task.setType(trade.getStatus());
	task.setAction(transfer.getAction());
	task.setStatus(transfer.getStatus());
	task.setTaskDate(commonUTIL.dateToString(commonUTIL.getCurrentDate()));
	task.setTradeDate(trade.getTradeDate());
	task.setTransferDate(transfer.getValueDate());
	task.setTaskstatus("0");
	task.setCurrency(transfer.getSettlecurrency());
	task.setBookid(transfer.getBookId());
	task.setEvent_type(transfer.getStatus()+"_"+transfer.getEventType());
	task.setType("TRANSFER");
	task.setProductType(transfer.getProductType());
	task.setUserid(userID);
	task.setTransferVersionID(transfer.getVersion());
	task.setTradeVersionID(trade.getVersion());
	task.setId(0);
	//task.se
	}
	return task;
	
	
}
	
	
	
	public WFConfig getStatusOnTransferAction(Transfer transfer,String status,Vector<String> statusMessages,Trade trade) {
		Product product = ProductSQL.selectProduct(transfer.getProductId(), dsSQL.getConn());
		String whereClause = "productType ='" + product.getProductType() + "' and productSubType = '"+ product.getProdcutShortName() + "' and currentstatus = '" + transfer.getStatus() + "' and action = '" + transfer.getAction() + "' and type ='TRANSFER'";
		Vector  wfs = (Vector) WFConfigSQL.selectWhere(whereClause, dsSQL.getConn());// this must alway retunr one transition which is unique .
		WFConfig wf = (WFConfig) wfs.elementAt(0);
		if(!commonUTIL.isEmpty(wf.getRule())) {
			if(wfhandler != null) {				
				wfhandler.generateTransferRule(trade,transfer, wf, statusMessages,dsSQL.getConn());				
			} else {
				wfhandler = new WFHandler();				
				wfhandler.generateTransferRule(trade,transfer, wf, statusMessages,dsSQL.getConn());	
				
				
			}
			
		} 
		if(wfhandler == null) {
			wfhandler = new WFHandler();
		}
		wf = checkSTPExistsonTransition(product,wf,transfer,statusMessages,trade) ; //check if stp exists on this transition
		return wf;
	}
	
	
	public WFConfig getStatusOnMessageAction(Message message,String status,Vector<String> statusMessages,Trade trade,Transfer transfer) {
		Product product = ProductSQL.selectProduct(message.getproductID(), dsSQL.getConn());
		String whereClause = "productType ='" + product.getProductType() + "' and productSubType = '"+ product.getProdcutShortName() + "' and currentstatus = '" + message.getStatus() + "' and action = '" + message.getAction() + "' and type ='MESSAGE'";
		Vector  wfs = (Vector) WFConfigSQL.selectWhere(whereClause, dsSQL.getConn());// this must alway retunr one transition which is unique .
		WFConfig wf = (WFConfig) wfs.elementAt(0);
		if(!commonUTIL.isEmpty(wf.getRule())) {
			if(wfhandler != null) {				
				wfhandler.generateMessageRule(trade,transfer,message, wf, statusMessages,dsSQL.getConn());				
			} else {
				wfhandler = new WFHandler();				
				wfhandler.generateMessageRule(trade,transfer,message, wf, statusMessages,dsSQL.getConn());	
				
				
			}
			
		} 
		if(wfhandler == null) {
			wfhandler = new WFHandler();
		}
		wf = checkSTPExistsonTransition(product,wf,message,statusMessages,trade,transfer) ; //check if stp exists on this transition
		return wf;
	}
	private WFConfig checkSTPExistsonTransition(Product product,WFConfig wf,Message message,Vector<String> statusMessages,Trade trade,Transfer transfer) {
		// TODO Auto-generated method stub
		WFConfig cf = wf;
		if(wf != null) {
			if(wf.getAuto() == 1 ) {
				String whereClause = "productType ='" + product.getProductType() + "' and productSubType = '"+ product.getProdcutShortName() + "' and currentstatus = '" + wf.getOrgStatus()  + "' and type ='MESSAGE'";
				//System.out.println(whereClause);
				Vector<WFConfig>  wfs = (Vector) WFConfigSQL.selectWhere(whereClause, dsSQL.getConn());
				if(wfs.size() ==1) {
					cf = wfs.elementAt(0); 
					if(cf.getAuto() == 0) {
					  cf = wf; 
					} else  {
						wfhandler.generateMessageRule(trade,transfer,message, cf, statusMessages,dsSQL.getConn());
						
					}
				} else {
					
						WFConfig cff = containsSTPTransitions(trade,transfer,message,wfs,statusMessages,dsSQL.getConn());
						if(cff == null) { 
							return cf;
						} else {
							cf = checkSTPExistsonTransition(product,cff,message,statusMessages,trade,transfer);
						}
					
					
				}
			} else {
				cf = wf;
			}
			
			
		}
		return cf;
	}
	
	private WFConfig checkSTPExistsonTransition(Product product,WFConfig wf,Transfer transfer,Vector<String> statusMessages,Trade trade) {
		// TODO Auto-generated method stub
		WFConfig cf = wf;
		if(wf != null) {
			if(wf.getAuto() == 1 ) {
				String whereClause = "productType ='" + product.getProductType() + "' and productSubType = '"+ product.getProdcutShortName() + "' and currentstatus = '" + wf.getOrgStatus()  + "' and type ='TRANSFER'";
				//System.out.println(whereClause);
				Vector<WFConfig>  wfs = (Vector) WFConfigSQL.selectWhere(whereClause, dsSQL.getConn());
				if(wfs.size() ==1) {
					cf = wfs.elementAt(0); 
					if(cf.getAuto() == 0) {
					  cf = wf; 
					} else  {
						wfhandler.generateTransferRule(trade,transfer, cf, statusMessages,dsSQL.getConn());
						
					}
				} else {
					
						WFConfig cff = containsSTPTransitions(trade,transfer,wfs,statusMessages,dsSQL.getConn());
						if(cff == null) { 
							return cf;
						} else {
							cf = checkSTPExistsonTransition(product,cff,transfer,statusMessages,trade);
						}
					
					
				}
			} else {
				cf = wf;
			}
			
			
		}
		return cf;
	}
// old one
  
	private WFConfig containsSTPTransitions(Trade trade,Transfer transfer,Vector<WFConfig> transitions,Vector tradeStats,Connection con) {
		WFConfig stpWFConfig = null;
		if(transitions != null || !transitions.isEmpty()) {
			for(int i=0;i<transitions.size();i++) {
				WFConfig cfs = (WFConfig) transitions.get(i);
				if(cfs.getAuto() == 1)  {
					stpWFConfig = cfs; 
					wfhandler.generateTransferRule(trade, transfer,stpWFConfig, tradeStats, con);
					break;
				}
			}
		}
		
		return stpWFConfig;
	}
	private WFConfig containsSTPTransitions(Trade trade,Transfer transfer,Message message,Vector<WFConfig> transitions,Vector tradeStats,Connection con) {
		WFConfig stpWFConfig = null;
		if(transitions != null || !transitions.isEmpty()) {
			for(int i=0;i<transitions.size();i++) {
				WFConfig cfs = (WFConfig) transitions.get(i);
				if(cfs.getAuto() == 1)  {
					stpWFConfig = cfs; 
					wfhandler.generateMessageRule(trade, transfer,message,stpWFConfig, tradeStats, con);
					break;
				}
			}
		}
		
		return stpWFConfig;
	}

	private void getAction(Transfer trs, String tradeAction) {
		// TODO Auto-generated method stub
		if(tradeAction.equalsIgnoreCase("CANCELLED")) {
			trs.setAction("CANCEL");
		}
		
		
	}
	@Override
	public Collection getTransferOnTrade(int tradeID) throws RemoteException {
		// TODO Auto-generated method stub
		return TransferSQL.getTransferOnTrade(tradeID,dsSQL.getConn());
	}
	
	
	@Override
	   public Collection getOnlyAction(int transferID) throws RemoteException {
		Vector v2 = (Vector) TransferSQL.selectTransfer(transferID, dsSQL.getConn());
		if(v2 == null)
			return null;
		 Transfer transfer = (Transfer) v2.elementAt(0);
		   Product product = ProductSQL.selectProduct(transfer.getProductId(), dsSQL.getConn());
			String whereClause = "productType ='" + product.getProductType() + "' and productSubType = '"+ product.getProdcutShortName() + "' and currentstatus = '" + transfer.getStatus() + "' and type ='TRANSFER'";
			Vector v1 = (Vector)  WFConfigSQL.selectWhere(whereClause, dsSQL.getConn());
			return v1;
	   }
	
	@Override
   public Collection getOnlyAction(Transfer transfer) throws RemoteException {
	   Product product = ProductSQL.selectProduct(transfer.getProductId(), dsSQL.getConn());
		String whereClause = "productType ='" + product.getProductType() + "' and productSubType = '"+ product.getProdcutShortName() + "' and currentstatus = '" + transfer.getStatus() + "' and type ='TRANSFER'";
		Vector v1 = (Vector)  WFConfigSQL.selectWhere(whereClause, dsSQL.getConn());
		return v1;
   }
	
	@Override
	   public Collection getOnlyAction(Message message) throws RemoteException {
		   Product product = ProductSQL.selectProduct(message.getproductID(), dsSQL.getConn());
			String whereClause = "productType ='" + product.getProductType() + "' and productSubType = '"+ product.getProdcutShortName() + "' and currentstatus = '" + message.getStatus() + "' and type ='MESSAGE'";
			Vector v1 = (Vector)  WFConfigSQL.selectWhere(whereClause, dsSQL.getConn());
			return v1;
	   }
	
	@Override
	public Collection getAction(Transfer transfer) throws RemoteException {
		// TODO Auto-generated method stub
		Product product = ProductSQL.selectProduct(transfer.getProductId(), dsSQL.getConn());
		String whereClause = "productType ='" + product.getProductType() + "' and productSubType = '"+ product.getProdcutShortName() + "' and currentstatus = '" + transfer.getStatus() + "' and action = '" + transfer.getAction() + "' and type ='TRANSFER'";
		Vector v1 = (Vector)  WFConfigSQL.selectWhere(whereClause, dsSQL.getConn());
		WFConfig wfconfig = null;
		
		if(v1 != null || (!v1.isEmpty())) {
			if(v1.size() == 0) {
			wfconfig = new WFConfig();
			wfconfig.setOrgStatus(transfer.getStatus());
			v1.addElement(wfconfig);
			return v1;
			}
			wfconfig = (WFConfig) v1.elementAt(0); 
			wfconfig = getStponNewStatus(wfconfig);
			v1.removeAllElements();
			v1.addElement(wfconfig);
			
		} 
		return v1;
	} 
	
	
	private WFConfig getStponNewStatus(WFConfig wfconfig) throws RemoteException {
		WFConfig autowf = null;
		if(wfconfig != null) {
			 
			String sql = "productType ='" + wfconfig.getProductType() + "' and productSubType = '"+ wfconfig.getProductSubType() + "' and type ='"+wfconfig.getType()+"' and currentstatus = '" + wfconfig.getOrgStatus() + "' and auto = 1 ";
			autowf = getStponNewStatus1(sql);
			if(autowf != null) {
				autowf = getStponNewStatus(autowf);
			} else {
				autowf = wfconfig;
			}
		} 
		
		return autowf;
	}
	
	
	private WFConfig getStponNewStatus1(String whereClause) throws RemoteException {
		// TODO Auto-generated method stub
		WFConfig autowf = null;
		if(whereClause != null) {
			 
			Vector v1 = (Vector)  WFConfigSQL.selectWhere(whereClause, dsSQL.getConn());
			if(v1 == null || v1.isEmpty()) 
				return null;
			if((v1 != null) || (!v1.isEmpty())) {
				autowf = (WFConfig) v1.elementAt(0);
			}
			
		}
	
		return autowf;
	} 
  // event is fired from transfer Panel
	@Override
	public Collection updateTransferAndPublish(Transfer transfer, int userID) {
		Vector updateTransfer = new Vector();
		try{
			transfer.setUserid(userID);
		updateTransfer.add(transfer);
		  if(remoteTrade == null)
		    	initRemoteInterface();
		Trade trade = (Trade) remoteTrade.selectTrade(transfer.getTradeId());
		updateTransfer = (Vector) saveTransfers(updateTransfer, "update", transfer.getStatus(),trade,userID);
		Transfer updateedtransfer = (Transfer)updateTransfer.elementAt(0);
		TransferEventProcessor event = getTransferEvent(updateedtransfer,null);
		if(event != null) 
			if(remoteTrade != null) {
				
				event.setTrade(trade);
				event.setProcessName("BOOfficeProcess");
			  remoteTrade.publishnewTrade("TRANS_NEWTRANSFER","TRADE",event);
			  
			} else {
				initRemoteInterface();
				
				event.setTrade(trade);
				event.setProcessName("BOOfficeProcess");
				remoteTrade.publishnewTrade("TRANS_NEWTRANSFER","TRADE",event);
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			commonUTIL.displayError("BOProcessImp ", "updateTransferAndPublish ", e);
		}
		return updateTransfer;
		
	}
	
	private  TransferEventProcessor getTransferEvent(Transfer transfer,Trade trade ) {
		TransferEventProcessor transferEvent = new TransferEventProcessor();
		transferEvent.setTransferID(transfer.getId());
		transferEvent.setTradeID(transfer.getTradeId());
		transferEvent.setTrade(trade);
		transferEvent.setTransfer(transfer);

		transferEvent.setEventType(transfer.getStatus()+"_"+transfer.getEventType());
		commonUTIL.display("TransferManager",transferEvent.getEventType());
		commonUTIL.display("","");
		return transferEvent;
	}
	
	
	private void initRemoteInterface() {
		de =ServerConnectionUtil.connect("localhost", 1099,commonUTIL.getServerIP());
		try {
			remoteTrade = (RemoteTrade) de.getRMIService("Trade");
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
		commonUTIL.displayError("BOProcessImp", "initRemoteInterface", e);
		}
	}
	private String getProcessTradeStatus(Collection actions)  {
		String status = "";
		if(actions == null)
			return null;
		Vector vector = (Vector) actions;
		Iterator< WFConfig> its = vector.iterator();
while(its.hasNext()) {
    		
	WFConfig wf = its.next();
	status = wf.getOrgStatus();
	//System.out.println(" new Status " + status);
		
		
	}
return status;
	
	}
	@Override
	public Collection getTransfers(Trade trade, boolean cancelTrue)
			throws RemoteException {
		// TODO Auto-generated method stub
		
		return null;
	}
	
	private void getTransferAttributes(Transfer transfer,Trade trade,WFConfig wfs) {
		Hashtable<String,String> attributes = transfer.getAttributesData();
		if(attributes.containsKey("FARLEG")) {
			String value = attributes.get("FARLEG");
			if(value.equalsIgnoreCase("NEW")) {
				transfer.setAction("NEW");
				transfer.setStatus("NONE");
				 Vector<String> statusMessages = new  Vector<String>();
				 wfs = getStatusOnTransferAction(transfer,transfer.getStatus(),statusMessages,trade);
			}
			if(value.equalsIgnoreCase("CANCELLED")) {
				if(trade.getRollOverFrom() >0 )
				   transfer.setAction("ROLLOVER");
				if(trade.getRollBackFrom() >0 )
					 transfer.setAction("ROLLBACK");
				transfer.setStatus("CANCELLED");
			}
			
		} 
		
		if(attributes.containsKey("NEARLEG")) {
			String value = attributes.get("NEARLEG");
			if(value.equalsIgnoreCase("NEW")) {
				transfer.setAction("NEW");
				transfer.setStatus("NONE");
				 Vector<String> statusMessages = new  Vector<String>();
				 wfs = getStatusOnTransferAction(transfer,transfer.getStatus(),statusMessages,trade);	
			}
			if(value.equalsIgnoreCase("CANCELLED")) {
				if(trade.getRollOverFrom() >0 )
					   transfer.setAction("ROLLOVER");
					if(trade.getRollBackFrom() >0 )
						 transfer.setAction("ROLLBACK");
				transfer.setStatus("CANCELLED");
			}
			
		} 
	//	String transferAtt = transfer.getAttributesData()
	}
	
	private boolean isRollOverRollBackTransfer(Transfer transfer) {
		Hashtable<String,String> attributes = transfer.getAttributesData();
		if(attributes.containsKey("NEARLEG") || attributes.containsKey("FARLEG"))
		return true;
		return false;
	}
	
	@Override
	public Collection saveTransfers(Vector<Transfer> transfers, String type,
			String tradeAction, NettingConfig netConfig,Trade trade) throws RemoteException {
		Vector<Transfer> newTransfer = new Vector();
		if(!transfers.isEmpty()) {
			if(type.equalsIgnoreCase("insert")) {
				Iterator<Transfer > trans = transfers.iterator();
				  WFConfig wfs = null; 
				while(trans.hasNext()) {
			     Transfer trs = trans.next();
			     trs.setStatus("NONE");
			     trs.setAction("NEW");
			   
			     if(!isRollOverRollBackTransfer(trs)) {
			    	 trs.setUserid(trade.getUserID());
			    	 Vector<String> statusMessages = new  Vector<String>();
					 wfs = getStatusOnTransferAction(trs,trs.getStatus(),statusMessages,trade);
			    	     
			     	 trs.setStatus(wfs.getOrgStatus());
			     } else {
			    	 getTransferAttributes(trs,trade,wfs);
			     }
			     	 Transfer nettedTransfer = getNettedTransfer(trs,netConfig);
			     	 if(nettedTransfer != null) {
			     	     trs.setNettedTransferID(nettedTransfer.getId());
			     	 }
			             int id = 	saveTransfer(trs);
				         trs.setId(id);
				         trs.setVersion(1);  // not sure how this will work but need to tested properly temperary soultion.
				    	 Task task =  processTask(trs,trade,trade.getUserID(),wfs);
				 	     publishTask(task,trade,trs);
				 	     newTransfer.addElement(trs);
			     	 
				 
				}
			} else if(type.equalsIgnoreCase("update")) {
				Iterator<Transfer > trans = transfers.iterator();
				while(trans.hasNext()) {
			     Transfer trs = trans.next();
			     Transfer oldTransfer = trs;
			     if(isRollOverRollBackTransfer(oldTransfer)) {
			    	 if(oldTransfer.getAction().equalsIgnoreCase("CANCEL"))   { // if trade is rollover or rollback and in cancel status. 
			    		 trs.setStatus("CANCELLED");
			    		 updateTransfer(trs);
			    	 } else {
			    		 Vector<String> statusMessages = new  Vector<String>();
					     WFConfig wfs = getStatusOnTransferAction(trs,trs.getStatus(),statusMessages,trade);
			    		 trs.setUserid(trade.getUserID());
			    		 trs.setStatus(wfs.getOrgStatus());
			    		 Transfer nettedTransfer = getNettedTransfer(trs,netConfig);
			    		 if(trs.getNettedTransferID() > 0 && (trs.isCanceled() || trs.isSettled())) {
			    		 		trs.setNettedConfigID(0);
			    		 		trs.setNettedTransferID(0);
			     // nettedTransfer.setSettleAmount(nettedTransfer.getSettleAmount() - trs.getAmount());
			    		 }
			    		 updateTransfer(trs);
			    	 }
			     } else {
			    	 Vector<String> statusMessages = new  Vector<String>();
				     WFConfig wfs = getStatusOnTransferAction(trs,trs.getStatus(),statusMessages,trade);
			    	 trs.setUserid(trade.getUserID());
			    	 trs.setStatus(wfs.getOrgStatus());
			    	 Transfer nettedTransfer = getNettedTransfer(trs,netConfig); // not required in case of update and delete
			    	 if(trs.getNettedTransferID() > 0 && (trs.isCanceled() || trs.isSettled())) {
			    		 	trs.setNettedConfigID(0);
			    		 	trs.setNettedTransferID(0);
			     // nettedTransfer.setSettleAmount(nettedTransfer.getSettleAmount() - trs.getAmount());
			    	 }
			    	 updateTransfer(trs);
			     }
					
					 publishTaskOnOldTransfer(oldTransfer,trade,trade.getUserID()); // userid need to be checked. 
				//	 Task task =  processTask(trs,trade,trade.getUserID(),wfs);
					// publishTask(task,trade,trs);
				    newTransfer.addElement(trs);
				 
				}
			}else if(type.equalsIgnoreCase("delete")) {
				Iterator<Transfer > trans = transfers.iterator();
				while(trans.hasNext()) {
			     Transfer trs = trans.next();
			     Transfer oldTransfer = trs;
			     trs.setAction("CANCEL");
			     
			     Vector<String> statusMessages = new  Vector<String>();
			     WFConfig wfs = getStatusOnTransferAction(trs,trs.getStatus(),statusMessages,trade);
			     trs.setUserid(trade.getUserID());
			     trs.setStatus(wfs.getOrgStatus());		
			     Transfer nettedTransfer = getNettedTransfer(trs,netConfig); //not required in case of update and delete
			     if(trs.getNettedTransferID() > 0 && trs.isCanceled()|| trs.isSettled()) {
			    	 trs.setNettedConfigID(0);
			    	 trs.setNettedTransferID(0);
			    //  nettedTransfer.setSettleAmount(nettedTransfer.getSettleAmount() - trs.getAmount());
			     }
			     publishTaskOnOldTransfer(oldTransfer,trade,trade.getUserID()); // userid need to be checked. 
			     updateTransfer(trs);
				 
				}
			}
		}
		
		
		
		return newTransfer;
	}
	
	private void publishTask(Task task,Trade trade,Transfer transfer) {
		if(task != null) {
			 int taskID = 0;
			if(task  != null) {
				if(task.getId() == 0)
			    taskID = TaskSQL.save(task,dsSQL.getConn());
				else 
					taskID = task.getId();
			    if(taskID >0 ) {
			    	try {
			    	task.setId(taskID);
			    	TaskEventProcessor taskEvent =  getTaskEvent(task,trade,transfer);
			    //System.out.println(remoteTrade);
			    if(remoteTrade == null)
			    	initRemoteInterface();
						remoteTrade.publishnewTrade("TRANS_NEWTRANSFER","TRADE",taskEvent);
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						commonUTIL.displayError("BOProcessImpl", "publishTask", e);
					}
			    }
			}
		}
	}
	private TaskEventProcessor getTaskEvent(Task task,Trade trade,Transfer transfer) {
		TaskEventProcessor taskEvent = new TaskEventProcessor();
		taskEvent.setTask(task);
		taskEvent.setTrade(trade);
	   taskEvent.setTransfer(transfer);
	   taskEvent.setTaskID(task.getId());
		taskEvent.setProcessName("TaskManagerProcesser");
		
		taskEvent.setTaskID(task.getId());
				return taskEvent;
	}
	// check if nettingTransfer exits on nettconfig  
	// if exists than add transfer passed to  nettingtransfer or else create new nettedtransfer not exist on passed transfer deliverydate.
	private Transfer getNettedTransfer(Transfer trs, NettingConfig netConfig) {
		// TODO Auto-generated method stub
		Transfer nettedTransfer = null;
	//	if(trs.getStatus().equalsIgnoreCase("CANCELLED"))
		//	return nettedTransfer;
		if(netConfig == null) 
			return nettedTransfer;
		if(isTransferEligableForNetting(trs,netConfig)) {
			nettedTransfer = getNettingTransfer(netConfig.getId(),trs.getDeliveryDate());
			if(nettedTransfer == null) {
				//System.out.println("StartGenerating New Netted Traansfer  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>  ");
				nettedTransfer = generateNettingTransfer(trs,netConfig);
				
				Task task = getNettedTransferTask(nettedTransfer);
				task.setTransfer(nettedTransfer);
				   publishTask(task,null,null);
				nettedTransfers.put(nettedTransfer.getDeliveryDate()+"_"+netConfig.getId(),nettedTransfer);
			} else {
			
			if(trs.isCanceled() || trs.isSettled()) {
				System.out.println(" In Cancelled before NettedTranfer id= " + nettedTransfer.getId() +  "  transfer id " + trs.getId() +  "     "+ nettedTransfer.getSettleAmount() + "  " + nettedTransfer.getDeliveryDate());
				System.out.println(trs.getAmount());
					 nettedTransfer.setSettleAmount(nettedTransfer.getSettleAmount() - trs.getAmount());
						System.out.println(" In Normal After NettedTranfer id= " + nettedTransfer.getId() +  "  transfer id " + trs.getId() +  "     " + nettedTransfer.getSettleAmount()+ "  " + nettedTransfer.getDeliveryDate());
			} else {
				System.out.println(" In Normal before NettedTranfer id= " + nettedTransfer.getId() +  "  transfer id " + trs.getId() +  "     " + nettedTransfer.getSettleAmount() + "  " + nettedTransfer.getDeliveryDate());
				nettedTransfer.setSettleAmount(nettedTransfer.getSettleAmount() + getTransferSettlementAmount(trs));
				System.out.println(" In Normal After NettedTranfer id= " + nettedTransfer.getId() +  "  transfer id " + trs.getId() +  "     " + nettedTransfer.getSettleAmount() + "  " + nettedTransfer.getDeliveryDate());
			}
			
			   TransferSQL.update(nettedTransfer, dsSQL.getConn());
			   nettedTransfers.put(nettedTransfer.getDeliveryDate()+"_"+netConfig.getId(),nettedTransfer);
			   Task task = getNettedTransferTask(nettedTransfer);
			   task.setTransfer(nettedTransfer);
			   publishTask(task,null,null);
		   }
		}
	    
		return nettedTransfer;
	}
	// if payment return negative value or else if receipt return positive value 

	private Task getNettedTransferTask(Transfer nettedTransfer) {
		Task task = new Task();
		task.setTradeID(0);
		  String sql = " nettedConfigid = "+ nettedTransfer.getId() +" and  productType = '"+ nettedTransfer.getProductType()+ "' and  currency = '"+nettedTransfer.getSettlecurrency()+"' and bookno =  "+ nettedTransfer.getBookId() + " and cpid = " + nettedTransfer.getCPid();
		 Vector<Task> tasks =  (Vector<Task>) TaskSQL.selectTaskWhere(sql, dsSQL.getConn());
		 if(!commonUTIL.isEmpty(tasks)) {
			 task = tasks.elementAt(0);
		 } else {
			
		
				task.setProductID(0);
				task.setTransferID(nettedTransfer.getId());
				//task.setType(trade.getStatus());
				//task.setAction(transfer.getAction());
			//	task.setStatus(transfer.getStatus());
				task.setTaskDate(commonUTIL.dateToString(commonUTIL.getCurrentDate()));
				//task.setTradeDate(trade.getTradeDate());
				//task.setTransferDate(transfer.getValueDate());
				task.setTaskstatus("0");
				task.setCurrency(nettedTransfer.getSettlecurrency());
				task.setBookid(nettedTransfer.getBookId());
				task.setCpid(nettedTransfer.getCPid());
				//task.setEvent_type(transfer.getStatus()+"_"+transfer.getEventType());
				task.setType("NETTINGTRANSFERS");
				task.setProductType(nettedTransfer.getProductType());
				task.setUserid(nettedTransfer.getUserid());
				task.setTransferVersionID(nettedTransfer.getVersion());
				task.setNettedConfigID(nettedTransfer.getId());
				//task.setTradeVersionID(trade.getVersion());
				task.setId(0);
		 }
		return task;
		// TODO Auto-generated method stub
		
	}
	private double getTransferSettlementAmount(Transfer transfer) {
		double amount = 0.0;
		if(transfer.isTransferPayment()) 
			amount =  amount * -1;
		if(transfer.isTransferReceipt())
			amount =  transfer.getAmount();
		return amount;
		
	}
	
	// create new netting transfer on basis of netconfig configuration and deliverydate. 
	private Transfer generateNettingTransfer(Transfer trs, NettingConfig netConfig) {
		// TODO Auto-generated method stub
		Transfer newNettedTransfer = new Transfer();
		newNettedTransfer.setId(0);
		newNettedTransfer.setTradeId(0);
		newNettedTransfer.setBookId(trs.getBookId());
		newNettedTransfer.setVersion(0);
		newNettedTransfer.setAction(trs.getAction());
		newNettedTransfer.setStatus(trs.getStatus());
		newNettedTransfer.setNettedConfigID(netConfig.getId());
		newNettedTransfer.setDeliveryDate(trs.getDeliveryDate());
		newNettedTransfer.setSettleAmount(trs.getAmount());
		newNettedTransfer.setCPid(netConfig.getLeid());
		newNettedTransfer.setProductType(netConfig.getProductType());
		newNettedTransfer.setSettlecurrency(netConfig.getCurrency());
		newNettedTransfer.setProductId(0);
		newNettedTransfer.setAmount(0);
		//newNettedTransfer.set
		int id = TransferSQL.save(newNettedTransfer, dsSQL.getConn());
		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$     From generateNettingTransfer "+ id);
		newNettedTransfer.setId(id);
		return newNettedTransfer;
	}
	
	
	// get existing netting transfer from hashtable or from db. 
	@Override
	public Transfer getNettingTransfer(int nettingConfigid, String deliveryDate) {
		// TODO Auto-generated method stub 
		Transfer nettingTransfer = null;
		synchronized (nettedTransfers) {
			   nettingTransfer = nettedTransfers.get(deliveryDate+"_"+nettingConfigid);
		}
		if(nettingTransfer == null) {
				String sql = " nettedTransferID =  " +nettingConfigid + " and deliveryDate = '" +deliveryDate+"'";
				Vector nettingTransferV = (Vector) TransferSQL.getNettedTransfer(sql, dsSQL.getConn());
				if(nettingTransferV == null || nettingTransferV.isEmpty()) 
					return nettingTransfer;
				nettingTransfer = (Transfer) nettingTransferV.elementAt(0);
				synchronized (nettedTransfers) {
					    nettedTransfers.put(deliveryDate+"_"+nettingConfigid,nettingTransfer);
				}
		}
		return nettingTransfer;
	}
	@Override
	public Collection getNettedTransfers(int nettingTransferID) {
		String sql = " nettedtransferid = " + nettingTransferID;
		return TransferSQL.getNettedTransfer(sql, dsSQL.getConn());
	}
	// check if transfer is payment or reciept and then  deliverdate exists between netConfig effective and enddate or after effective date
	
	public boolean isTransferEligableForNetting(Transfer transfer,NettingConfig netConfig) {
		boolean flag = false;
		
		Date transferSettleDate = commonUTIL.stringToDate(transfer.getDeliveryDate(),true);
		Date nettingStartDate = commonUTIL.stringToDate(netConfig.getEffectiveDate(),true);
		if(netConfig.getEndEdate() == null) {
			if(transferSettleDate.after(nettingStartDate)) 
				flag = true;
		} else {
			Date nettingEndDate =  commonUTIL.stringToDate(netConfig.getEndEdate(),true);
			if(commonUTIL.between2dates(nettingStartDate, nettingEndDate,transferSettleDate)) 
					flag = true;
			}
		if(!flag)
			return flag;
		flag = transfer.isAvaliableForNetting();
		if(!flag)
			return flag;
		return flag;
	}
	@Override
	public Collection getNettedTransfers(NettingConfig netConfig) {
		if(netConfig == null)
			return null;
		String sql = " cpid = " + netConfig.getLeid() + " and nettedconfigid = " + netConfig.getId() + " and producttype = '" + netConfig.getProductType() + "' and settleamount != 0";
		return TransferSQL.getNettedTransfer(sql, dsSQL.getConn());
	}
	@Override
	public void removeMessage(Message message) throws RemoteException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void updateMessage(Message message) throws RemoteException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Message selectMessage(Message message) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Collection getMessageOnTrade(int tradeID) throws RemoteException {
		// TODO Auto-generated method stub
		String where = " tradeid = " + tradeID;
		return MessageSQL.getMessageOnWhere(where, dsSQL.getConn());
	}
	@Override
	public Collection getMessageOnTransfer(int transferID)
			throws RemoteException {
		// TODO Auto-generated method stub
		String where = " transferid = " + transferID;
		return MessageSQL.getMessageOnWhere(where, dsSQL.getConn());
	}
	
	@Override
	public void saveMessage(Message message) throws RemoteException {
		// TODO Auto-generated method stub
		MessageSQL.save(message,dsSQL.getConn());
		
	}
	
	@Override
	public Collection getMessage(int id,String eventType,String triggerON) throws RemoteException {
		// TODO Auto-generated method stub
		String sql = "";
		if(triggerON.equalsIgnoreCase("TRADE")) 
			sql = " tradeid = "+id+" and eventtype = '"+eventType +"' and triggerON ='"+triggerON+"'";
		if(triggerON.equalsIgnoreCase("TRANSFER")) 
			sql = " transferid = "+id+" and eventtype = '"+eventType +"' and triggerON ='"+triggerON+"'";
		
		return  MessageSQL.getMessageOnWhere(sql, dsSQL.getConn());
	
	}
	
	
	@Override
	public Vector<Message> saveMesage(Vector<Message> mess, String sqlType,Trade trade,Transfer transfer)
			throws RemoteException {
		// TODO Auto-generated method stub
		Vector<Message> messages = null;
		if(mess == null || mess.isEmpty()) 
			return null;
		if(sqlType.equalsIgnoreCase("insert")) {
			messages = processMessageAction(mess,"insert",trade,transfer);
			messages =  MessageSQL.insert(messages, dsSQL.getConn());
		}
		//if(sqlType.equalsIgnoreCase("update"))
			//messages =  MessageSQL.update(mess, dsSQL.getConn());
		return messages;
	}
	
	private Vector<Message> processMessageAction(Vector<Message> messages,String sqlType,Trade trade,Transfer transfer) {
		Vector<Message> messsageWithStatus = new Vector<Message>();
	   for(int i=0;i<messages.size();i++) {
		   if(sqlType.equalsIgnoreCase("insert")) {
			   Message message = messages.get(i);
			   message.setAction("NEW");
			   message.setStatus("NONE");
			 //  message.setSubAction("NEW");
			   
			   Vector<String> statusMessages = new Vector<String>();
			   WFConfig wf =  getStatusOnMessageAction(message, message.getStatus(), statusMessages, trade, transfer);
			   message.setStatus(wf.getOrgStatus());
			   if(message.getStatus().equalsIgnoreCase("CANCELLED")) {
				   message.setSubAction("CANCEL");
			   }
			  
			   messsageWithStatus.add(message);
		   }
		   
	   }
	   return messsageWithStatus;
	}
	@Override
	public Vector<Message> getMessagesOnWhere(String where)
			throws RemoteException {
		// TODO Auto-generated method stub
		return (Vector<Message>) MessageSQL.getMessageOnWhere(where,dsSQL.getConn());
	}
	@Override
	public Vector<Message> getMessages(int tradeID, String eventType,
			String triggerON) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Vector<Message> saveMesage(Vector<Message> mess, String sqlType)
			throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Collection getSwiftBICData(String sql) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Collection getSwiftBICData(BICSwiftData query)
			throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Transfer getTransfer(int transferID) throws RemoteException {
		// TODO Auto-generated method stub
		return (Transfer) TransferSQL.getTransfer(transferID, dsSQL.getConn());
	}
	@Override
	public DocumentInfo getLatestAdviceDocument(int id, Object object)
			throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Message selectMessageOnLinkid(int linkId) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Vector<Message> getMessages(int messageConfigid, int tradeID,
			String eventType, String triggerON) throws RemoteException {
		// TODO Auto-generated method stub
		String sql = " messConfigID="+messageConfigid+" and tradeID ="+tradeID+" and eventType='"+eventType+"' and triggerON ='"+triggerON+"' order by id desc";
		 return  getMessagesOnWhere(sql);
		
	}

}

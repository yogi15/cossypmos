package dsManager;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import util.commonUTIL;

import apps.window.staticwindow.StartDataUPWindow;
import beans.StartUPData;
import beans.Task;
import beans.Trade;
import beans.Transfer;
import bo.transfer.TransferProcessor;
import dsEventProcessor.EventProcessor;
import dsEventProcessor.TaskEventProcessor;
import dsEventProcessor.TradeEventProcessor;
import dsEventProcessor.TransferEventProcessor;
import dsServices.RemoteBOProcess;
import dsServices.RemoteReferenceData;
import dsServices.RemoteTrade;

public class TransferManager extends ControllerManager {
	
	
	
	String managerName = "TranserManager";
	RemoteBOProcess remoteBO;
	RemoteTrade remoteTrade;
	RemoteReferenceData refData;
	TransferProcessor processor = null;
	Thread transferManagerThread = null;
	Transfer transfer = null;
	String queueName = "TRANSFER";
	String transferTriggerEvt = "TRANSFERTriggerEvent";
	String canceltransferTriggerEvt = "TRANSFERCancelTriggerEvent";
	Vector<String> transferTriggerEvts = new Vector<String>();
	boolean newEvent = false;
	Hashtable<String,Integer> duplicateEventCheck = new Hashtable<String,Integer>();
	public boolean isNewEvent() {
		//System.out.println("PPPPPPPPPPPPPPPPPPPPPPP  isNewEvent " + newEvent);
		return newEvent;
	}


	public void setNewEvent(boolean newEvent) {
		this.newEvent = newEvent;
		//System.out.println("PPPPPPPPPPPPPPPPPPPPPPP " + newEvent);
	}

	int counter = 0;
	
	public Hashtable<Integer,TradeEventProcessor> transferEvents = new Hashtable<Integer,TradeEventProcessor>();
	public Map<Integer,TradeEventProcessor> balance = new ConcurrentHashMap<Integer,TradeEventProcessor> ();
	public String getQueueName() {
		return queueName;
	}
	private boolean isDuplicateEvent(Trade trade) {
		boolean flag = false;
		String key = trade.getId()+"_"+trade.getStatus()+"_"+trade.getVersion();
		 
		if(duplicateEventCheck.containsKey(key)) {
			System.out.println(" getting duplicat key ***** for key = " + key);
			flag = true;
		}
		return flag;
	}
	

	public TransferManager(String host,String hostName,String managerName) {
		super(host,hostName,managerName);
		try {
			processor = new TransferProcessor();		
			remoteBO = (RemoteBOProcess) getDe().getRMIService("BOProcess");
			remoteTrade = (RemoteTrade) getDe().getRMIService("Trade");
	   		refData = (RemoteReferenceData) getDe().getRMIService("ReferenceData");
	   	    setTransferTriggerEvt(refData.getStartUPData(transferTriggerEvt));
	   	    processor.setCancelTransferTriggerEvent(refData.getStartUPData(canceltransferTriggerEvt));
	   	 processor.setRemoteBOProcess(remoteBO);
			processor.setRefData(refData);
			processor.setRemoteTrade(remoteTrade);
	   	    processor.setTransferManager(this);
	   	   processor.start();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			commonUTIL.displayError("TransferManager", "In Constructor", e);
		}
   		
   		
		
		
   		
	}
	
	
	
	private void setTransferTriggerEvt(Collection startUPData) {
		// TODO Auto-generated method stub
		Vector<StartUPData> startupData = (Vector)startUPData;
		if((startupData != null) || (!startupData.isEmpty())) {
			for(int i=0;i<startupData.size();i++) {
				transferTriggerEvts.addElement(((StartUPData) startupData.get(i)).getName());
			}
		}
		
	}


	public  synchronized void handleEvent(EventProcessor event)	 {
		System.out.println("Starting of  ****** " +   event.getTradeID());
	//	setNewEvent(true);
		TradeEventProcessor tradeEvent = null;
		if(event instanceof TradeEventProcessor) {
			tradeEvent = (TradeEventProcessor) event;
			
		//	System.out.println("Starting of  ****** " +   tradeEvent.getTradeID() + " " + transferEvents.size());
			//System.out.println(tradeEvent.getTrade().getId());
			if(transferTriggerEvts.contains(event.getEventType())) {
				 
					    			     				   
					      
					        synchronized (transferEvents) {
					        	if(!isDuplicateEvent(tradeEvent.getTrade())) {
					        		  System.out.println(" adding at  counter *****  = " + counter + tradeEvent.getTrade().getId() + " " + tradeEvent.getTrade().getStatus() + " " + tradeEvent.getTrade().getVersion());
					        	       
					        		  balance.put(counter, tradeEvent);
					        		 
					        		  duplicateEventCheck.put(tradeEvent.getTrade().getId()+"_"+tradeEvent.getTrade().getStatus()+"_"+tradeEvent.getTrade().getVersion(),tradeEvent.getTrade().getId());
					        		  counter = counter + 1;
					        	} else {
					        		  System.out.println(" Notting addding value = " + counter + tradeEvent.getTrade().getId() + " " + tradeEvent.getTrade().getStatus() + " " + tradeEvent.getTrade().getVersion());
					        	}
					        	 
							}
						 
						  
					   
					   
				   
				
		    	
			}
			
			
			//System.out.println("End  of ****** " +   tradeEvent.getTradeID());
		}
	//	setNewEvent(false);
		//tradeEvent = null;
	}
	
	
public  void processPosition(TradeEventProcessor tradeEvent) {
		
		synchronized (processor) {
			//System.out.println("        Start Process of generating position for trade " + trade.getId() + " version == " + trade.getVersion());
			  processor.processTransfer(tradeEvent,tradeEvent.getTrade());
		}
}
	
	
	
public void publishTransfer(Trade trade) {
		
		
			 Vector<Transfer> transfers = 	processor.getPublishtransfers();
			
			 if(transfers != null || (!transfers.isEmpty())) {
			//	 System.out.println();
				 for(int i=0;i<transfers.size();i++)
					try {
						commonUTIL.display("TransferManager","publishing transfer id "+ transfers.get(i).getId() + " for trade   "+ trade.getId() + "  on event " );
						TransferEventProcessor event = getTransferEvent(transfers.get(i),trade);
						if(event != null) {
						  remoteTrade.publishnewTrade("TRANS_NEWTRANSFER","TRADE",event);
						} else {
							commonUTIL.display("TransferManager","Event not publish Trade is Cancel or Terminated  <<<<<<<<<<<<< ");						}
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						commonUTIL.displayError("TransferManager", "publishTransfer", e);
					}
				 
				// transferexist = false;
			  }
			
		
	}
public Thread init(TransferManager amanager,Trade trade) {
	
	transferManagerThread = new Thread(amanager);
	return transferManagerThread;
}



public TransferEventProcessor getTransferEvent(Transfer transfer,Trade trade ) {
	TransferEventProcessor transferEvent = new TransferEventProcessor();
	transferEvent.setTransferID(transfer.getId());
	transferEvent.setTradeID(transfer.getTradeId());
	transferEvent.setTrade(trade);
	transferEvent.setTransfer(transfer);
	transferEvent.setProcessName("TransferEngineProcess");

	transferEvent.setEventType(transfer.getStatus()+"_"+transfer.getEventType());
	commonUTIL.display("TransferManager",transferEvent.getEventType());
	commonUTIL.display("","");
	return transferEvent;
}

//public void stop() {
//	commonUTIL.display(manager.getManagerName(), "Stop <<<<<<<<<  " +manager.getManagerName());
	//this.stop();
	//System.exit(0);
	
//}
	public static void main(String args[]) {
		TransferManager tmanager = new TransferManager("localhost",commonUTIL.getLocalHostName(),"TransferManager");
		commonUTIL.display("TransferManager", "Started Transfer Manager >>>>>>>> ");
		tmanager.start(tmanager);
	}


	
	
 
}

  class InnerTransferProcessor implements Runnable {
    

	boolean processEvent = false;
     
	@Override
	public void run() {
		// TODO Auto-generated method stub
		for( ; ; ) {
				
		}
	}
	
	 public boolean isProcessEvent() {
			return processEvent;
		}

		public void setProcessEvent(boolean processEvent) {
			this.processEvent = processEvent;
		}
	  
  }

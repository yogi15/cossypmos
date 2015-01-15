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
import dsEventProcessor.AdminEventProcessor;
import dsEventProcessor.EventProcessor;
import dsEventProcessor.TaskEventProcessor;
import dsEventProcessor.TradeEventProcessor;
import dsEventProcessor.TransferEventProcessor;
import dsServices.EngineMonitorUtil;
import dsServices.RemoteAdminManager;
import dsServices.RemoteBOProcess;
import dsServices.RemoteEvent;
import dsServices.RemoteReferenceData;
import dsServices.RemoteTrade;

public class TransferManager extends ControllerManager {
	
	
	
	String managerName = "TransferManager";
	TransferManagerStartup startUpManager;
	RemoteBOProcess remoteBO;
	RemoteTrade remoteTrade;
	RemoteReferenceData refData;
	RemoteAdminManager remoteAdmin;
	RemoteEvent remoteEvent;
	TransferProcessor processor = null;
	Thread transferManagerThread = null;
	Transfer transfer = null;
	String queueName = "TRANSFER";
	String transferTriggerEvt = "TRANSFERTriggerEvent";
	String canceltransferTriggerEvt = "TRANSFERCancelTriggerEvent";
	Vector<String> transferTriggerEvts = new Vector<String>();
	Vector<EventProcessor> eventNotProcess = new Vector<EventProcessor>();
	boolean newEvent = false;
	boolean serviceStarted = false;
	
	/**
	 * @return the serviceStarted
	 */
	public boolean isServiceStarted() {
		return serviceStarted;
	}


	/**
	 * @param serviceStarted the serviceStarted to set
	 */
	public void setServiceStarted(boolean serviceStarted) {
		this.serviceStarted = serviceStarted;
	}

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
	

	public TransferManager(String host,String hostName,String managerName,TransferManagerStartup startUpManager ) {
		
		super(host,hostName,managerName);
		this.startUpManager = startUpManager;
		
		try {
			processor = new TransferProcessor();		
			remoteBO = (RemoteBOProcess) getDe().getRMIService("BOProcess");
			remoteTrade = (RemoteTrade) getDe().getRMIService("Trade");
	   		refData = (RemoteReferenceData) getDe().getRMIService("ReferenceData");
	   		remoteAdmin = (RemoteAdminManager) getDe().getRMIService("ServerController");
	   		remoteEvent = (RemoteEvent) getDe().getRMIService("Event");
	   	    setTransferTriggerEvt(refData.getStartUPData(transferTriggerEvt));
	   	    eventNotProcess = (Vector<EventProcessor>) remoteEvent.getEventNotProcessed("Transfer");
	   	    processor.setCancelTransferTriggerEvent(refData.getStartUPData(canceltransferTriggerEvt));
	   	    processor.setRemoteBOProcess(remoteBO);
			processor.setRefData(refData);
			processor.setRemoteTrade(remoteTrade);
	   	    processor.setTransferManager(this);
	   	    if(eventNotProcess.size() > 0) {
	   	    	for(int i=0;i<eventNotProcess.size();i++) {  // first process all event are not consumed. 
	   	    		EventProcessor event = eventNotProcess.get(i); 
	   	    		handleEvent(event);
	   	    	}
	   	    }
	   	    startUpManager.setClientID(getDe().getClientID());
	   	 startUpManager.startProducingMessage(managerName, hostName, ":61616", startUpManager);
	   	   processor.start();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			commonUTIL.displayError("TransferManager", "In Constructor", e);
		}
   		
   		
		
   		
	}
	
	
		public void publishStartEvent(String engineName,String siginal) {
			
			try {
				remoteAdmin.addEngines(engineName+"_"+siginal,startUpManager.getClientID(),startUpManager.getUser());
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
		
	}

public void updateEventProcess(EventProcessor event) {
	    try {
	    	event.setSubscribableList("Transfer");
			remoteTrade.isEventExceuted(event);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	//	System.out.println("Starting of  ****** " +   event.getTradeID());
	//	setNewEvent(true);
		try {
		TradeEventProcessor tradeEvent = null;
	//	System.out.println(event.getEventType() + " " + event.getClassName());
		if(event instanceof AdminEventProcessor ) {
			  AdminEventProcessor  adminEvent = (AdminEventProcessor) event;
			  if(adminEvent.getEngineStartedSignal().contains(managerName))  {
				  if(adminEvent.getEngineStartedSignal().contains(managerName)) {
				  if(adminEvent.getClientID() == startUpManager.getClientID())
				     startUpManager.stop();
				  }
			  }
		  }
		if(event instanceof TradeEventProcessor) {
			tradeEvent = (TradeEventProcessor) event;
			
		//	System.out.println("Starting of  ****** " +   tradeEvent.getTradeID() + " " + transferEvents.size());
			//System.out.println(tradeEvent.getTrade().getId());
			if(transferTriggerEvts.contains(event.getEventType())) {
				 
					    			     				   
					      
					        synchronized (transferEvents) {
					        	if( tradeEvent.getTrade() == null) {
					        		Trade trade;
									try {
										trade = remoteTrade.getTradeOnVersion(tradeEvent.getObjectID(),tradeEvent.getObjectVersionID());
										tradeEvent.setTrade(trade);
									} catch (RemoteException e) {
										// TODO Auto-generated catch block
										commonUTIL.displayError("TransferManager", "handleEvent", e);
									}
					        		
					        	}
					        	if(!isDuplicateEvent(tradeEvent.getTrade())) {
					        		  System.out.println(" adding at  counter *****  = " + counter + tradeEvent.getTrade().getId() + " " + tradeEvent.getTrade().getStatus() + " " + tradeEvent.getTrade().getVersion());
					        	       
					        		  balance.put(counter, tradeEvent);
					        		 
					        		  duplicateEventCheck.put(tradeEvent.getTrade().getId()+"_"+tradeEvent.getTrade().getStatus()+"_"+tradeEvent.getTrade().getVersion(),tradeEvent.getTrade().getId());
					        		  counter = counter + 1;
					        	} else {
					        		  System.out.println(" Notting addding value = " + counter + tradeEvent.getTrade().getId() + " " + tradeEvent.getTrade().getStatus() + " " + tradeEvent.getTrade().getVersion());
					        	}
					        	 
							}
						 
						  
					   
					   
				   
				
		    	
			} else {
			updateEventProcess(tradeEvent);
			}
			
			//System.out.println("End  of ****** " +   tradeEvent.getTradeID());
		}
		}
		catch(NullPointerException e) {
			commonUTIL.displayError("TransferManager", "HandleEvent", e);
		}
	//	setNewEvent(false);
		//tradeEvent = null;
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
	transferEvent.setPublish(true);
	transferEvent.setSavetoDB(true);
	transferEvent.setEventType(transfer.getStatus()+"_"+transfer.getEventType());
	transferEvent.setType("Transfer");
	transferEvent.setObjectID(transfer.getId());
	transferEvent.setComments(" Transfer status  " + transfer.getStatus() + " for Action "+transfer.getAction() + " on Trade_"+trade.getId()+"_Version_"+trade.getVersion() );
	
	commonUTIL.display("TransferManager",transferEvent.getEventType());
	commonUTIL.display("","");
	return transferEvent;
}

 
}
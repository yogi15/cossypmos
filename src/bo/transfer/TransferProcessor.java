package bo.transfer;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;


import util.ClassInstantiateUtil;
import util.commonUTIL;
import dsEventProcessor.EventProcessor;
import dsEventProcessor.TradeEventProcessor;
import dsManager.TransferManager;
import dsServices.RemoteBOProcess;
import dsServices.RemoteReferenceData;
import dsServices.RemoteTrade;
import beans.NettingConfig;
import beans.StartUPData;
import beans.Trade;
import beans.Transfer;

public class TransferProcessor extends Thread {
		RemoteTrade remoteTrade = null;
		RemoteBOProcess remoteBOProcess = null;
		Transfer transfer = null;
		
		TransferManager manager = null;
		
	RemoteReferenceData refData = null;
	Vector<Transfer> publishtransfers = new Vector<Transfer>();
	Vector<String> FeeType = new Vector<String>();
	Vector<String> cancelTransferTriggerEvents = new Vector<String>();
	Hashtable<String,Integer> duplicateEventCheck = new Hashtable<String,Integer>();
	int counter = 0;
	public Vector<String> getFeeType() {
		return FeeType;
	}

	public TransferManager getTransferManager() {
		return manager;
	}

	public void setTransferManager(TransferManager transferManager) {
		this.manager = transferManager;
	}
	
	
	
    public void setCancelTransferTriggerEvent(Collection startUPData) {
		// TODO Auto-generated method stub
		Vector<StartUPData> startupData = (Vector)startUPData;
		if((startupData != null) || (!startupData.isEmpty())) {
			for(int i=0;i<startupData.size();i++) {
				cancelTransferTriggerEvents.addElement(((StartUPData) startupData.get(i)).getName());
			}
		}
    	
    }



	public   Vector<Transfer> getPublishtransfers() {
		return publishtransfers;
	}

    
	
	
	
	public RemoteReferenceData getRefData() {
		return refData;
	}


	public void setRefData(RemoteReferenceData refData) {
		this.refData = refData;
	}


	public Transfer getTransfer() {
		return transfer;
	}


	


	public RemoteBOProcess getRemoteBOProcess() {
		return remoteBOProcess;
	}


	public void setRemoteBOProcess(RemoteBOProcess remoteBOProcess) {
		this.remoteBOProcess = remoteBOProcess;
	}
	Hashtable<String, BOTransfer> handlers = new Hashtable();
	public RemoteTrade getRemoteTrade() {
		return remoteTrade;
	}


	public void setRemoteTrade(RemoteTrade remoteTrade) {
		this.remoteTrade = remoteTrade;
	}


	public  void  processTransfer(EventProcessor event, Trade trade) {
		// TODO Auto-generated method stub
	//	System.out.println("  From TransferProcessor " + trade.getId());
		System.out.println("Entered in  processing of   ****** " +   trade.getId() + " on "+ trade.getStatus() + " status ");
		duplicateEventCheck.put(trade.getId()+"_"+trade.getStatus()+"_"+trade.getVersion(),trade.getId());
		TradeEventProcessor tradeEvent = (TradeEventProcessor) event;
		String productType = trade.getProductType();
		
	//	getFeesType();
		BOTransfer transferHandler = getTransferHandler(productType);
		NettingConfig netConfig = getNettingConfig(trade.getCpID(), trade.getProductType());
		Vector<Transfer> transfers =  transferHandler.generateTransfer(trade,FeeType,netConfig);
		Vector<Transfer> oldTransfers = getTransfersOnTrade(trade.getId());
		Hashtable transfersData = new Hashtable();
		if(oldTransfers!= null && oldTransfers.size() > 0) {
			       if(cancelTransferTriggerEvents.contains(trade.getStatus())) {
		            transferHandler.filterTransfer(oldTransfers, transfers, trade,transfersData,true);
			       } else  {
		            	transferHandler.filterTransfer(oldTransfers, transfers, trade,transfersData,false);
			       }
		} else {
			transfersData.put("insert", transfers);
		}

		if(trade.getId() >0 ) {
			publishtransfers.removeAllElements();   // this is a problem  can caused data corruption
			Enumeration<String > keys = transfersData.keys();
		     while(keys.hasMoreElements()) {
		    	 String sqlType = keys.nextElement();
		    	 Vector<Transfer> transfersD = (Vector) transfersData.get(sqlType);
		    	 if(transfersD != null && (!transfersD.isEmpty())) {
		    		 Vector<Transfer> transfs = saveTransfer(transfersD, sqlType,trade.getStatus(),netConfig,trade);
		    		 setTransfertoPublish(transfs);
		    	 }
		     }
		     
		}
		System.out.println("End  of ****** processing " +   trade.getId() + " on "+ trade.getStatus() + " status ");
		//setTransfer(transfer);
		
	}
	
	
	
	private NettingConfig getNettingConfig(int counterParyID,String productType) {
		String sql = "leid = "+counterParyID+" and productType ='"+productType+"'";
		NettingConfig netConfig = null;
		try {
			Vector<NettingConfig> netConfigV = (Vector) refData.getNettingConfigOnWhere(sql);
			if(netConfigV ==null || netConfigV.isEmpty())
				return netConfig;
			else 
				netConfig = netConfigV.elementAt(0);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return netConfig;
		
	}
	private void setTransfertoPublish(Vector<Transfer> transfers) {
		if((transfers != null) || (!transfers.isEmpty())) {
			for(int i=0;i<transfers.size();i++) 
				publishtransfers.add(transfers.get(i));
		}
	}
	
	boolean eventUnderProcess = true;
	public void run(){
		  for( ; ; ) {
			  try {
				Thread.sleep(300);
				
					 
					
				
				
				if(manager.balance.size() > counter) {
					 System.out.println(":pppp:"+  manager.balance.size() + " counter " + counter);
					
						 TradeEventProcessor tradeEvent = null;
						
							// synchronized (manager.transferEvents) {
					        	 
								 tradeEvent   = manager.balance.get(counter);
							//}
							if(tradeEvent == null)
								return;
							 eventUnderProcess = false;
							 if(!isDuplicateEvent(tradeEvent.getTrade())) {
									
								
							 processTransfer(tradeEvent,tradeEvent.getTrade());
							 manager.publishTransfer(tradeEvent.getTrade());
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
	
	
	
	
	
	private void getFeesType() {
		try {
		Vector feeType	 = (Vector) refData.getStartUPData("FEETYPE");
		for(int i=0;i<feeType.size();i++) {
			StartUPData sdata = (StartUPData)feeType.get(i);
			FeeType.addElement(sdata.getName());
		}
			
		} catch (Exception e) {
        	commonUTIL.displayError("TransferProcessor", "getFeesType <<<<< not able get Fees Type ", e);
        }
	}


	private BOTransfer getTransferHandler(String name) {
        String productTransfer = "bo.transfer.Generate"  + name.toUpperCase() + "Transfer";
        BOTransfer transferHandler = null;
        
        try {
        	transferHandler = handlers.get(name);
        	if(transferHandler == null) {
        Class class1 =    ClassInstantiateUtil.getClass(productTransfer,true);
        transferHandler =  (BOTransfer) class1.newInstance();
        transferHandler.setRemoteTrade(remoteTrade);
        transferHandler.setBoProcess(remoteBOProcess);
        transferHandler.setRemoteRef(refData);
        
        handlers.put(name, transferHandler);
        }
           //  productWindow = (BondPanel) 
        } catch (Exception e) {
        	commonUTIL.displayError("TransferProcessor", "getTransferHandler <<<<< not able to create Handler ", e);
        }

        return transferHandler;
    }
	
	private  Vector<Transfer>  saveTransfer(Vector<Transfer> transfers,String sqlType,String tradeStatus,NettingConfig netConfig,Trade trade) {
		
		Vector<Transfer> transfs = null;
		try {
			 transfs = (Vector) remoteBOProcess.saveTransfers(transfers,sqlType,tradeStatus,netConfig,trade);
			
			//remoteBOProcess.publishnewTransfer(messageIndicator, messageType, transfs);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return transfs;
	}


	private Vector getTransfersOnTrade(int id) {
		Vector<Transfer> transfers = null;
		    try {
		    	transfers = (Vector)	remoteBOProcess.getTransferOnTrade(id);
		    	return transfers;
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
			commonUTIL.displayError("TransferProcessor " ,"getTransfersOnTrade " , e);
			return null;
			}
	}
}

package dsManager;

import java.rmi.RemoteException;

import util.commonUTIL;
import beans.Message;
import beans.StartUPData;
import beans.Task;
import beans.Trade;
import beans.Transfer;
import bo.message.MessageProcessor;
import dsEventProcessor.EventProcessor;
import dsEventProcessor.TaskEventProcessor;
import dsEventProcessor.TradeEventProcessor;
import dsEventProcessor.TransferEventProcessor;
import dsEventProcessor.MessageEventProcessor;
import dsServices.RemoteBOProcess;
import dsServices.RemoteReferenceData;
import dsServices.RemoteTrade;

public class MessageManager extends ControllerManager {
	String managerName = "MessageManager";
	RemoteBOProcess remoteBO;
	RemoteTrade remoteTrade;
	RemoteReferenceData refData;
	MessageProcessor processor = null;
	Thread messageManagerThread = null;
	Message message = null;
	String queueName = "MESSAGE";
	public String getQueueName() {
		return queueName;
	}
	public MessageManager(String host,String hostName,String managerName) {
		super(host,hostName,managerName);
		try {
			processor = new MessageProcessor();
			remoteBO = (RemoteBOProcess) getDe().getRMIService("BOProcess");
			remoteTrade = (RemoteTrade) getDe().getRMIService("Trade");
	   		refData = (RemoteReferenceData) getDe().getRMIService("ReferenceData");
	   	        // setTransferTriggerEvt(refData.getStartUPData(transferTriggerEvt));
	   	    //  processor.setCancelTransferTriggerEvent(refData.getStartUPData(canceltransferTriggerEvt));
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			commonUTIL.displayError("MessageManager", "In Constructor", e);
		}
   		
   		
		
		processor.setRemoteBOProcess(remoteBO);
		processor.setRefData(refData);
		processor.setRemoteTrade(remoteTrade);
   		
	}
	
  public void handleEvent(EventProcessor event)	 {
		if(event instanceof TradeEventProcessor) {
			TradeEventProcessor tradeEvent = (TradeEventProcessor) event;
			System.out.println(tradeEvent.getTrade().getId());
			
		    	processor.processMessage(tradeEvent);
			     publishMessage(tradeEvent.getTrade());
			
		}if(event instanceof TransferEventProcessor) {
			TransferEventProcessor transferEvent = (TransferEventProcessor) event;
			System.out.println(transferEvent.getTrade().getId());
			
		    	processor.processMessage(transferEvent);
			     publishMessage(transferEvent.getTrade());
			
		}
		
	}

	
private void publishMessage(Trade trade) {
	// TODO Auto-generated method stub
	
}
public Thread init(MessageManager amanager,Trade trade) {
	
	messageManagerThread = new Thread(amanager);
	return messageManagerThread;
}


public static void main(String args[]) {
	MessageManager mmanager = new MessageManager("localhost",commonUTIL.getLocalHostName(),"MessageManager");
	commonUTIL.display("MessageManager", "Started Message Manager >>>>>>>> ");
	mmanager.start(mmanager);
}

public MessageEventProcessor getMessageEvent(Message message,Trade trade,Transfer transfer,String eventType ) {
	MessageEventProcessor messageEvent = new MessageEventProcessor();
	messageEvent.setTransferID(transfer.getId());
	messageEvent.setTradeID(transfer.getTradeId());
	messageEvent.setTrade(trade);
	messageEvent.setTransfer(transfer);
	messageEvent.setProcessName("MessageEngineProcess");

	messageEvent.setEventType(eventType);
	commonUTIL.display("MessageManager",messageEvent.getEventType());
	
	return messageEvent;
}


}
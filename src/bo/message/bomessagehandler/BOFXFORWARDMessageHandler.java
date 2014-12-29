package bo.message.bomessagehandler;

import java.util.Vector;

import util.commonUTIL;
import dsEventProcessor.TradeEventProcessor;
import dsEventProcessor.TransferEventProcessor;
import beans.LegalEntity;
import beans.Message;
import beans.MessageConfig;
import beans.Sdi;
import beans.Trade;
import beans.Transfer;
import bo.util.SDISelectorUtil;

public class BOFXFORWARDMessageHandler extends BOMessageHandler  {
	
	

	public Message fillMessage(Trade trade,Transfer transfer,MessageConfig mConfig,String trrigerON,TransferEventProcessor transferEvent,TradeEventProcessor tradeEvent,LegalEntity receiver,LegalEntity po) {
		Message message = null;
		try {
			message =  new Message();
			message.setMessageConfigID(mConfig.getId());
		message.setTradeId(trade.getId());
		message.setProductType(mConfig.getProductType());
		if(transfer != null ) {
			message.setTransferId(transfer.getId());
			message.setEventType(transfer.getEventType());
			message.setTriggerON("TRANSFER");
		} else {
			message.setEventType(tradeEvent.getEventType());
			message.setTriggerON("TRADE");
		}
		message.setTradeDate(trade.getTradeDate());
		message.setAddressType(mConfig.getAddressType());
		message.setMessageDate(commonUTIL.getCurrentDateTime());
		message.setProductSubType(mConfig.getProductSubType());
		message.setMessageGateway(mConfig.getGateWay());
		message.setMessageType(mConfig.getMessageType());
		message.setproductID(trade.getProductId());
		message.setTradeVersion(trade.getVersion());
		if(receiver == null)
			message.setReceiverName("");
		else 
			message.setReceiverName(receiver.getName());
		
		message.setReceiverRole(mConfig.getReceiverRole());
		message.setSenderName(po.getName());
		message.setSenderRole("PO"); // i have doubt on the same. 
		message.setTemplateName(mConfig.getTemplateName());
		message.setFormat(mConfig.getFormatType());
		message.setAddressType(mConfig.getAddressType());
		message.setSenderContactType(mConfig.getPoContactType());
		message.setReceiverContactType(mConfig.getReceiverContactType());
		if(mConfig.getReceiverID() == 0) {
			if(mConfig.getReceiverRole().equalsIgnoreCase("CounterParty")) {
				message.setReceiverId(trade.getCpID());
			} else {
					
				if(message.getTriggerON().equalsIgnoreCase("TRADE")) {
					String key = "PO|"+trade.getCurrency()+"|"+trade.getProductType()+"|"+String.valueOf(mConfig.getPoid());
					 Vector<Sdi> sdiperferred = SDISelectorUtil.getPreferredSdisOnKey(key);
					 if(sdiperferred != null) {
						 Sdi sdi = sdiperferred.elementAt(0);
						 message.setReceiverId(sdi.getAgentId());
					 } else {
						 message.setReceiverId(0); // no sdi found. 
					 }
					 
				}
			}
		} else {
		message.setReceiverId(mConfig.getReceiverID());
		}
		message.setSenderId(mConfig.getPoid());
	//	message.setSenderAddressCodet)
		
		//message.set
		}catch (NullPointerException e) {
			commonUTIL.displayError("BOFXForwardHandler", "fillMessage", e);
			return message;
		}
		return message;
		
	}
	

}

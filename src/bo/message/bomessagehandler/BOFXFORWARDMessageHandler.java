package bo.message.bomessagehandler;

import util.commonUTIL;
import dsEventProcessor.TradeEventProcessor;
import dsEventProcessor.TransferEventProcessor;
import beans.LegalEntity;
import beans.Message;
import beans.MessageConfig;
import beans.Trade;
import beans.Transfer;

public class BOFXFORWARDMessageHandler extends BOMessageHandler  {
	
	

	public Message fillMessage(Trade trade,Transfer transfer,MessageConfig mConfig,String trrigerON,TransferEventProcessor transferEvent,TradeEventProcessor tradeEvent,LegalEntity receiver,LegalEntity po) {
		
		Message message = new Message();
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
		message.setReceiverName(receiver.getName());
		message.setReceiverRole(receiver.getRole());
		message.setSenderName(po.getName());
		message.setSenderRole("PO");
		message.setTemplateName(mConfig.getTemplateName());
		message.setFormat(mConfig.getFormatType());
		message.setAddressType(mConfig.getAddressType());
		message.setSenderContactType(mConfig.getPoContactType());
		message.setReceiverContactType(mConfig.getReceiverContactType());
		message.setReceiverId(mConfig.getReceiverID());
		message.setSenderId(mConfig.getPoid());
	//	message.setSenderAddressCodet)
		
		//message.set
			
		return message;
		
	}
	

}

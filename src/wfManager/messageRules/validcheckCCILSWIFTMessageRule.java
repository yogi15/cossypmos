package wfManager.messageRules;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.util.Vector;

import productPricing.Pricer;
import beans.LegalEntity;
import beans.Message;
import beans.Trade;
import beans.Transfer;
import dsServices.RemoteBOProcess;
import dsServices.RemoteReferenceData;
import dsServices.RemoteTrade;
import wfManager.MessageRule;

public class validcheckCCILSWIFTMessageRule implements MessageRule {

	@Override
	public boolean checkValid(Trade newTrade, Transfer transfer,
			Message message, Vector messageData, RemoteTrade remoteTrade,
			RemoteBOProcess remoteBo, RemoteReferenceData remoteRef,
			Pricer pricer, Connection con) {
		// TODO Auto-generated method stub
		try {
			LegalEntity le = remoteRef.selectLE(newTrade.getCpID());
			// le.getAttributes(); from here get attributes ;
			// message.getFormat(); // formate of message.
			// trade.getCurrency(); or 
			// 
			if(message.getFormat().equalsIgnoreCase("CCIL")) {
				messageData.addElement(new String("validCCILSWIFTMessageRule failed As Message will not generated for CCIL"));
			}
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean update(Trade newTrade, Transfer transfer, Message message,
			Vector messageData, RemoteTrade remoteTrade,
			RemoteBOProcess remoteBo, RemoteReferenceData remoteRef,
			Pricer pricer, Connection con) {
		// TODO Auto-generated method stub
		return true;
	}

}

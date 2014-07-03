package wfManager;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.util.Hashtable;
import java.util.Vector;

import productPricing.Pricer;
import dsServices.RemoteBOProcess;
import dsServices.RemoteTrade;
import dsServices.ServerConnectionUtil;
import util.ClassInstantiateUtil;
import util.commonUTIL;
import beans.Message;
import beans.Trade;
import beans.Transfer;
import beans.WFConfig;

public class WFHandler {
	
	
	Hashtable<String,TradeRule> _tradeRules = new Hashtable<String,TradeRule>();
	Hashtable<String,TransferRule> _transferRules = new Hashtable<String,TransferRule>();
	static protected Hashtable _pricers = new Hashtable();
	RemoteTrade remoteTrade = null;
	RemoteBOProcess remoteBO = null;
	 ServerConnectionUtil de = null;
	
	public WFHandler() {
		 	de =ServerConnectionUtil.connect("localhost", 1099,commonUTIL.getServerIP() );
		  try { 
		  remoteTrade = (RemoteTrade) de.getRMIService("Trade");
		  remoteBO = (RemoteBOProcess) de.getRMIService("BOProcess");
			//	System.out.println(deals.toString());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			 
	}





	public  void generateTradeRule(Trade trade,WFConfig wfconfig,Vector messageData,Connection   con) {
		String rule = wfconfig.getRule();
		Pricer pricer = getProductPricer(trade.getProductType());
		if(!commonUTIL.isEmpty(rule)) {
		if(rule.contains(",")) {
			String rules [] = rule.split(",");
			for(int i=0;i<rules.length;i++) {
				TradeRule traderule =(TradeRule) getTradeRule(rule);
				traderule.checkValid(trade, trade, messageData,remoteTrade,pricer,con);
			}
			if(messageData.size() > 0 )
				return;
			for(int i=0;i<rules.length;i++) {
				TradeRule traderule =(TradeRule) getTradeRule(rule); // db transcation handler needs to be handle. 
				
			//	start Transcation here
				traderule.update(trade, trade, messageData,remoteTrade,pricer,con);
				//end Transcation here.
			}
		} else {
	TradeRule traderule =(TradeRule) getTradeRule(rule);
		
		traderule.checkValid(trade, trade, messageData,remoteTrade,pricer,con);
		if(messageData.size() > 0 )
			return;
//		start Transcation here
		traderule.update(trade, trade, messageData,remoteTrade,pricer,con);
		//end Transcation here.
		}
		}
	}
	
	
	public  void generateTransferRule(Trade trade,Transfer newTransfer,WFConfig wfconfig,Vector messageData,Connection   con) {
		String rule = wfconfig.getRule();
	
		Pricer pricer = getProductPricer(trade.getProductType());
		if(!commonUTIL.isEmpty(rule)) {
		if(rule.contains(",")) {
			String rules [] = rule.split(",");
			for(int i=0;i<rules.length;i++) {
				TransferRule transferrule =(TransferRule) getTransferRule(rule);
				transferrule.checkValid(trade, newTransfer,newTransfer,remoteTrade,remoteBO, messageData,pricer,con);
			}
			if(messageData.size() > 0 )
				return;
			for(int i=0;i<rules.length;i++) {
				TransferRule transferRule =(TransferRule) getTradeRule(rule); // db transcation handler needs to be handle. 
				System.out.println("Rule " + newTransfer.getId()  +  " get Rule " + transferRule);
			//	start Transcation here
				transferRule.update(trade, newTransfer,newTransfer,remoteTrade,remoteBO, messageData,pricer,con);
				//end Transcation here.
			}
		} else {
			TransferRule transferrule =(TransferRule) getTransferRule(rule);
			System.out.println("Rule " + newTransfer.getId()  +  " get Rule " + transferrule);
			transferrule.checkValid(trade, newTransfer,newTransfer,remoteTrade,remoteBO, messageData,pricer,con);
		if(messageData.size() > 0 )
			return;
//		start Transcation here
		transferrule.update(trade, newTransfer,newTransfer,remoteTrade,remoteBO, messageData,pricer,con);
		//end Transcation here.
		}
		}
	}
	
	
	
	
	private Pricer getProductPricer(String name) {
        String pricerName = "productPricing."  + name.toUpperCase() + "Pricing";
        Pricer pricer = null;
        
        try {
        	  synchronized(_pricers) {
        		  pricer = (Pricer) _pricers.get(name);
        	  }
        	if(pricer == null) {
        Class class1 =    ClassInstantiateUtil.getClass(pricerName,true);
        pricer =  (Pricer) class1.newInstance();
        if ( pricer == null) 
        	commonUTIL.display("AccountProcessor", "getAccountingHandler Pricer for Product "+ name + " not found <<<<<<< " );
        else 
        	_pricers.put(pricerName, pricer);
        commonUTIL.display("AccountProcessor", "getAccountingHandler Pricer  Found for Product " + name);  
        }
           //  productWindow = (BondPanel) 
        } catch (Exception e) {
        	commonUTIL.displayError("AccountProcessor", "getAccountingHandler <<<<< not able to create Pricer  ", e);
        }

        return pricer;
    }
	
	
	
	private  TradeRule getTradeRule(String name) {
        String tradeRule = "wfManager.tradeRules.valid"  + name.trim() + "TradeRule";
        TradeRule _tradeRule = null;
        
        try {
        	  synchronized(_tradeRules) {
        		  _tradeRule = (TradeRule) _tradeRules.get(name);
        	  }
        	if(_tradeRule == null) {
        Class class1 =    ClassInstantiateUtil.getClass(tradeRule,true);
        _tradeRule =  (TradeRule) class1.newInstance();
        if ( _tradeRule == null) 
        	commonUTIL.display("WFHandler", "getTradeRule for Trade  "+ name + "is null  <<<<<<< " );
        else 
        	_tradeRules.put(name, _tradeRule);
        commonUTIL.display("WFHandler", "getTradeRule for Trade  "+ name + "Found >>>>  " );
        }
           //  productWindow = (BondPanel) 
        } catch (Exception e) {
        	commonUTIL.displayError("WFHandler", "getTradeRule for Trade  "+ name + "is null  <<<<<<< " , e);
        }

        return _tradeRule;
    }
	
	private  TransferRule getTransferRule(String name) {
        String tradeRule = "wfManager.transferRules.valid"  + name.trim() + "TransferRule";
        TransferRule _transferRule = null;
        
        try {
        	  synchronized(_transferRules) {
        		  _transferRule = (TransferRule) _transferRules.get(name);
        	  }
        	if(_transferRule == null) {
        Class class1 =    ClassInstantiateUtil.getClass(tradeRule,true);
        _transferRule =  (TransferRule) class1.newInstance();
        if ( _transferRule == null) 
        	commonUTIL.display("WFHandler", "getTransferRule for Trade  "+ name + "is null  <<<<<<< " );
        else 
        	_transferRules.put(name, _transferRule);
        commonUTIL.display("WFHandler", "getTradeRule for Trade  "+ name + "Found >>>>  " );
        }
           //  productWindow = (BondPanel) 
        } catch (Exception e) {
        	commonUTIL.displayError("WFHandler", "getTransferRule for Transfer  "+ name + "is null  <<<<<<< " , e);
        }

        return _transferRule;
    }
	public RemoteTrade getRemoteTrade() {
		return remoteTrade;
	}
	public void setRemoteTrade(RemoteTrade remoteTrade) {
		this.remoteTrade = remoteTrade;
	}





	public void generateMessageRule(Trade trade, Transfer transfer,
			Message message, WFConfig stpWFConfig, Vector tradeStats,
			Connection con) {
		// TODO Auto-generated method stub
		
	}

}

package apps.window.operationwindow.trialBalance;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import beans.Account;
import beans.Trade;

import util.commonUTIL;
import dsServices.RemoteAccount;
import dsServices.RemoteBOProcess;
import dsServices.RemoteTrade;
import dsServices.ServerConnectionUtil;

public class TradeTreeView {

	Hashtable<Integer,TradeTV> traddes = new Hashtable<Integer,TradeTV> ();
	 public RemoteTrade getRemoteTrade() {
		return remoteTrade;
	}
	public void setRemoteTrade(RemoteTrade remoteTrade) {
		this.remoteTrade = remoteTrade;
	}
	RemoteTrade remoteTrade = null;
	public void showTrade() {
		JFrame frame = new JFrame("TreeTable");
		
			JTreeTable treeTable = new JTreeTable(new TradeModel(traddes.get(26)));

			frame.addWindowListener(new WindowAdapter() {
			    public void windowClosing(WindowEvent we) {
				System.exit(0);
			    }
			});

			frame.getContentPane().add(new JScrollPane(treeTable));
			frame.pack();
			frame.show();
	}
	public Hashtable<Integer, TradeTV> getTraddes() {
		return traddes;
	}
	public void setTraddes(Hashtable<Integer, TradeTV> traddes) {
		this.traddes = traddes;
	}
	public JTreeTable getjTreeTrade(int id) {
		return  new JTreeTable(new TradeModel(traddes.get(id)));
	}
	 public static void main(String args[]) {
			ServerConnectionUtil de = null; 
			de =ServerConnectionUtil.connect("localhost", 1099,commonUTIL.getLocalHostName() );
			RemoteBOProcess remoteBO = null;
			Vector accounts = null;
			try {
				TradeTreeView tb = new TradeTreeView();
				RemoteTrade remoteTrade = (RemoteTrade) de.getRMIService("Trade");
				Vector rolloverH = (Vector) remoteTrade
						.getTradeRollOverHierarchies(26);
				tb.getFillTree(rolloverH);
				tb.showTrade();
			}catch(RemoteException e) {
				
			}
	 }
	 public Trade getFillTree(Vector trades) {
		 Trade  parentTrade = null;
		  Trade a =  (Trade) trades.get(0);
		//
		  Vector<Trade> remomainingacc1 = new Vector<Trade>();
		  for(int i=0;i<trades.size();i++) {
			  parentTrade = (Trade) trades.get(i);
			  if(parentTrade.getRollOverFrom() == 0) {
				  TradeTV actB  = fillTV(parentTrade);
				//  System.out.println(actB.getAccountID() +  "  " + actB.getAccountName());
				  traddes.put(actB.getTradeID(), actB);
			  }  	 else {
				  remomainingacc1.add(parentTrade);
				  
			  }
		  }
		  addChilds(remomainingacc1);
		 
		  return parentTrade;
		  
	  }
	 
	 
	 
	 public void addChilds(Vector<Trade> trades) {
		  System.out.println(" StartA   >>>>>>>>>>>>>>> " + trades.size());
		  Vector<Trade> remaningAcc = new Vector<Trade>();
		  for(int i=0;i<trades.size();i++) {
			 Trade parentAcc = (Trade) trades.get(i);
		//	 System.out.println(" StartA looking Parent for   >>>>>>>>>>>>>>> " + parentAcc.getId());
			   TradeTV accT =  getParent(parentAcc);
			   
			   if(accT != null) {
				  // System.out.println();
				   TradeTV actB  = fillTV(parentAcc);
				//   System.out.println(actB.getAccountID() + "  " + accT.getAccountID());
				   
			   		accT.addChild(actB);
			   		traddes.put(accT.getTradeID(), accT);
			   		
			   } else {
				   System.out.println("Not add" + parentAcc.getId() + "  " );
				   remaningAcc.add(parentAcc);
			   }
		  }
		  if(remaningAcc.size() > 0)  {
			  System.out.println("PPP "+remaningAcc.size());
			  addChilds(remaningAcc);
		  }
	  }
	 
	 public TradeTV getParent(Trade parentAcc) {
		  TradeTV acc  = null;
		  Enumeration enu = traddes.elements();
		  while(enu.hasMoreElements()) {
			  TradeTV aTB = (TradeTV)enu.nextElement();
		//	  System.out.print("  Parent search " + aTB.getAccountID());
			  if(aTB.isParentOFChild(parentAcc)) {
				  acc = aTB;
			  break;
			  }
		  }
		//  System.out.println();
		  return acc;
		  
	  }
	 public  TradeTV fillTV(Trade trade) {
		 TradeTV tradeTV = new TradeTV();
		 tradeTV.setTradeID(trade.getId());
		 tradeTV.setProductType(trade.getTradedesc1());
		 tradeTV.setNominal(trade.getNominal());
		 tradeTV.setQuantity(trade.getQuantity());
		 tradeTV.setRollOVerfromid(trade.getRollOverFrom());
		 return tradeTV;
		 
	 }
}

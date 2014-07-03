package apps.window.tradewindow;

import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JPanel;

import dsEventProcessor.TaskEventProcessor;
import dsServices.RemoteTrade;

import productPricing.Pricer;
import util.NumericTextField;
import util.commonUTIL;
import beans.Trade;
import beans.Users;
import beans.WFConfig;

public abstract class TradePanel extends CommonPanel  {
	
	   /**
	 * 
	 */
	

//	public abstract void processActionData( javax.swing.DefaultComboBoxModel combodata,String productSubType);
	public abstract void setTaskPanel(BackOfficePanel panel);
	public abstract void setSDIPanel(BackOfficePanel panel);
	public abstract void setFEESPanel(BackOfficePanel panel);
	public abstract void setLimitPanel(BackOfficePanel panel);
	public abstract void setTradeTransfers(BackOfficePanel panel);
	public abstract void setTradePostings(BackOfficePanel panel);
	
	public abstract void setTrade(Trade trade);
	public abstract Trade getTrade();
	public abstract void saveASNew(Trade trade);
	public abstract void setUser(Users user);
	public abstract String getAction();
	
	public abstract void setTradeApplication(TradeApplication app);
	
	public void setRealTimeFlag(boolean flag) {
		
	}
	
	public void processActionData( javax.swing.DefaultComboBoxModel combodata,String productType,String productSubType,String tradestatus,RemoteTrade remoteTrade) {
		Vector vector;
		try {
			combodata.removeAllElements();
			String sql = "productType = '"+productType.toUpperCase().trim() +  "'  and productSubType = '"+productSubType.toUpperCase().trim()+   "' and currentstatus = '" +tradestatus +"' and type ='TRADE'";
			//System.out.println(sql);
			vector = (Vector) remoteTrade.getAllActionsOnStatus(sql);
			if(vector == null)
				return;
			Iterator it = vector.iterator();
			int i =0;
			while(it.hasNext()) {
	    		
				WFConfig wf = (WFConfig)	 it.next();
				combodata.insertElementAt(wf.getAction(), i);
				i++;
			}
			
    		
		}catch (RemoteException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}catch(Exception e) {
    				commonUTIL.displayError( productType + " TradePanel "," proccssActionData", e);
    			}
    	
    	
    }
	public abstract void processTask(TaskEventProcessor taskEvent,Users WindowUser);
		// TODO Auto-generated method stub
	public abstract  void setLimitBreachMarkOnAction(int i);
		
		// TODO Auto-generated method stub
		
	
		
	
	
}

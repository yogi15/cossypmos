package apps.window.operationwindow.trialBalance;

import java.util.Enumeration;
import java.util.Hashtable;

import beans.Account;
import beans.Trade;


public class TradeTV {
	
	
	
	int tradeID;
	
	
	
	
	
	String productType;
	double quantity;
	double nominal;
	int parentID;
	int rollOVerfromid =0;
	
	public int getRollOVerfromid() {
		return rollOVerfromid;
	}
	public void setRollOVerfromid(int rollOVerfromid) {
		this.rollOVerfromid = rollOVerfromid;
	}
	Hashtable<Integer,TradeTV> childs = new Hashtable<Integer,TradeTV>();
	
	
	public String toString() { 
		return getProductType();
	    }
	public boolean isParent() {
		if(childs.size() > 0) 
			return true;
		else 
			return false;
	}
	public boolean isChild() {
		if(childs.size() == 0) 
			return true;
		else 
			return false;
	}
	
	public int getTradeID() {
		return tradeID;
	}
	public void setTradeID(int tradeID) {
		this.tradeID = tradeID;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public double getQuantity() {
		return quantity;
	}
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
	public double getNominal() {
		return nominal;
	}
	public void setNominal(double nominal) {
		this.nominal = nominal;
	}
	public int getParentID() {
		return parentID;
	}
	public void setParentID(int parentID)  {
		this.parentID = parentID;
	}
	public Hashtable<Integer, TradeTV> getChilds() {
		if(childs.size() > 0) 
			return childs;
		else 
			return null;
	}
	public void setChilds(Hashtable<Integer, TradeTV> childs) {
		this.childs = childs;
	}
	public boolean addChild(TradeTV tbData) {
		boolean addC = false;
		// TODO Auto-generated method stub
		
			  Enumeration enu = childs.elements();
			  while(enu.hasMoreElements()) {
				  TradeTV aTB = (TradeTV)enu.nextElement();
			if(aTB.getTradeID() == tbData.getRollOVerfromid()) {
				aTB.addChild(tbData);
				System.out.println("  added " +tbData.getRollOVerfromid() + " to " +  aTB.getTradeID() + " which is child of " + aTB.getRollOVerfromid());
				addC = true;
				break;
			}
			if(aTB.containsChild()) {
				addC = aTB.addChild(tbData);
				if(addC == true)
					break;
					  
				
			}
			
		}
		if(getTradeID() == tbData.getRollOVerfromid())  {
			childs.put(tbData.getTradeID(), tbData);
			addC = true;
		}
		return addC;
	}
	private boolean containsChild() {
		if(childs.size() > 0) 
			return true;
		else 
			return false;
	}
	public boolean isParentOFChild(Trade tbData) {
		boolean addC = false;
		// TODO Auto-generated method stub
		 Enumeration enu = childs.elements();
		  while(enu.hasMoreElements()) {
			  TradeTV aTB = (TradeTV)enu.nextElement();
			//  System.out.println("  Parent search " + aTB.getAccountID() + " child of " + aTB.getParentID());
			if(aTB.getTradeID() == tbData.getRollOverFrom()) {
				
				addC = true;
				break;
			} else {
				if(aTB.containsChild()) {
					addC = aTB.isParentOFChild(tbData);
					if(addC == true)
						break;
				}
			
					
			}
			
		}
		if(getTradeID() == tbData.getRollOverFrom())
			addC = true;
		return addC;
	}
	
	public Object [] getChildrens() {
		TradeTV [] ac = new TradeTV[childs.size()];
		Enumeration enu = childs.elements();
		int i=0;
		  while(enu.hasMoreElements()) {
			  TradeTV aTB = (TradeTV)enu.nextElement();
			  ac[i] = aTB;
			  i++;
			  
			  
		  }
		  return ac;
	}
	

}


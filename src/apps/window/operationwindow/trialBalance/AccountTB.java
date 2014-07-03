package apps.window.operationwindow.trialBalance;

import java.util.Enumeration;
import java.util.Hashtable;

import beans.Account;


public class AccountTB {
	
	
	
	int accountID;
	
	
	
	
	
	String accountName;
	double drAmt;
	double crAmt;
	int parentID;
	Hashtable<Integer,AccountTB> childs = new Hashtable<Integer,AccountTB>();
	
	
	public String toString() { 
		return getAccountName();
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
	
	public int getAccountID() {
		return accountID;
	}
	public void setAccountID(int accountID) {
		this.accountID = accountID;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public double getDrAmt() {
		return drAmt;
	}
	public void setDrAmt(double drAmt) {
		this.drAmt = drAmt;
	}
	public double getCrAmt() {
		return crAmt;
	}
	public void setCrAmt(double crAmt) {
		this.crAmt = crAmt;
	}
	public int getParentID() {
		return parentID;
	}
	public void setParentID(int parentID)  {
		this.parentID = parentID;
	}
	public Hashtable<Integer, AccountTB> getChilds() {
		if(childs.size() > 0) 
			return childs;
		else 
			return null;
	}
	public void setChilds(Hashtable<Integer, AccountTB> childs) {
		this.childs = childs;
	}
	public boolean addChild(AccountTB tbData) {
		boolean addC = false;
		// TODO Auto-generated method stub
		
			  Enumeration enu = childs.elements();
			  while(enu.hasMoreElements()) {
				  AccountTB aTB = (AccountTB)enu.nextElement();
			if(aTB.getAccountID() == tbData.getParentID()) {
				aTB.addChild(tbData);
				System.out.println("  added " +tbData.getAccountID() + " to " +  aTB.getAccountID() + " which is child of " + aTB.getParentID());
				addC = true;
				break;
			}
			if(aTB.containsChild()) {
				addC = aTB.addChild(tbData);
				if(addC == true)
					break;
					  
				
			}
			
		}
		if(getAccountID() == tbData.getParentID())  {
			childs.put(tbData.getAccountID(), tbData);
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
	public boolean isParentOFChild(Account tbData) {
		boolean addC = false;
		// TODO Auto-generated method stub
		 Enumeration enu = childs.elements();
		  while(enu.hasMoreElements()) {
			  AccountTB aTB = (AccountTB)enu.nextElement();
			//  System.out.println("  Parent search " + aTB.getAccountID() + " child of " + aTB.getParentID());
			if(aTB.getAccountID() == tbData.getParentID()) {
				
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
		if(getAccountID() == tbData.getParentID())
			addC = true;
		return addC;
	}
	
	public Object [] getChildrens() {
		AccountTB [] ac = new AccountTB[childs.size()];
		Enumeration enu = childs.elements();
		int i=0;
		  while(enu.hasMoreElements()) {
			  AccountTB aTB = (AccountTB)enu.nextElement();
			  ac[i] = aTB;
			  i++;
			  
			  
		  }
		  return ac;
	}
	

}

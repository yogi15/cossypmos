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
import util.commonUTIL;
import dsServices.RemoteAccount;
import dsServices.RemoteBOProcess;
import dsServices.RemoteReferenceData;
import dsServices.ServerConnectionUtil;


public class TBRemote {

	Hashtable<Integer,AccountTB> accou = new Hashtable<Integer,AccountTB> ();
	public void readH() {
		 System.out.println(accou);
		 AccountTB accT = new AccountTB();
		 accT.setAccountID(0);
		 accT.setAccountName("Total");
		 accT.setCrAmt(0.0);
		 accT.setDrAmt(0.0);
		
		 Enumeration enu = accou.elements();
		  while(enu.hasMoreElements()) {
			  AccountTB aTB = (AccountTB)enu.nextElement();
			  accT.addChild(aTB);
		  }
		  
		  JFrame frame = new JFrame("TreeTable");
			JTreeTable treeTable = new JTreeTable(new AccountModel(accT));

			frame.addWindowListener(new WindowAdapter() {
			    public void windowClosing(WindowEvent we) {
				System.exit(0);
			    }
			});

			frame.getContentPane().add(new JScrollPane(treeTable));
			frame.pack();
			frame.show();
		    }

	
	
	  public static void main(String args[]) {
			ServerConnectionUtil de = null; 
			de =ServerConnectionUtil.connect("localhost", 1099,commonUTIL.getLocalHostName() );
			RemoteBOProcess remoteBO = null;
			Vector accounts = null;
			try {
				TBRemote tb = new TBRemote();
				RemoteAccount remoteReference = (RemoteAccount) de.getRMIService("Account");
				 accounts = (Vector) remoteReference.getTBData();
				 tb.getParentAccount(accounts);
				 tb.readH();
			}catch(RemoteException e) {
				
			}
	  }
	  
	  
	  public AccountTB getParent(Account parentAcc) {
		  AccountTB acc  = null;
		  Enumeration enu = accou.elements();
		  while(enu.hasMoreElements()) {
			  AccountTB aTB = (AccountTB)enu.nextElement();
		//	  System.out.print("  Parent search " + aTB.getAccountID());
			  if(aTB.isParentOFChild(parentAcc)) {
				  acc = aTB;
			  break;
			  }
		  }
		//  System.out.println();
		  return acc;
		  
	  }
	  public Account getParentAccount(Vector accounts) {
		  Account parentAcc = null;
		  Account a =  (Account) accounts.get(0);
		//
		  Vector<Account> remomainingacc1 = new Vector<Account>();
		  for(int i=0;i<accounts.size();i++) {
			  parentAcc = (Account) accounts.get(i);
			  if(parentAcc.getParentID() == 0) {
				  AccountTB actB  = fillTB(parentAcc);
				//  System.out.println(actB.getAccountID() +  "  " + actB.getAccountName());
				  accou.put(actB.getAccountID(), actB);
			  }  	 else {
				  remomainingacc1.add(parentAcc);
				  
			  }
		  }
		  addChilds(remomainingacc1);
		 
		  return parentAcc;
		  
	  }
	  
	  
	  public void addChilds(Vector<Account> accts) {
		  System.out.println(" StartA   >>>>>>>>>>>>>>> " + accts.size());
		  Vector<Account> remaningAcc = new Vector<Account>();
		  for(int i=0;i<accts.size();i++) {
			 Account parentAcc = (Account) accts.get(i);
		//	 System.out.println(" StartA looking Parent for   >>>>>>>>>>>>>>> " + parentAcc.getId());
			   AccountTB accT =  getParent(parentAcc);
			   
			   if(accT != null) {
				  // System.out.println();
				   AccountTB actB  = fillTB(parentAcc);
				//   System.out.println(actB.getAccountID() + "  " + accT.getAccountID());
				   if(actB.getAccountID() == 8229) {
					   System.out.println("gg");
				   }
			   		accT.addChild(actB);
			   		accou.put(accT.getAccountID(), accT);
			   		
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
	  
	  public AccountTB  fillTB(Account acc) {
		  AccountTB tbData = new AccountTB();
		  tbData.setAccountID(acc.getId());
		  tbData.setAccountName(acc.getAccountName());
		  tbData.setParentID(acc.getParentID());
		
		  return tbData;
		  
	  }
	  
	 
	  
}

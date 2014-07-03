package apps.window.operationwindow.trialBalance;

import java.util.Enumeration;


public class AccountModel extends AbstractTreeTableModel 
implements TreeTableModel {
	   static protected String[]  cNames = {"Name",   "Debit", "Credit"};

	    // Types of the columns.
	    static protected Class[]  cTypes = {TreeTableModel.class,Double.class, Double.class};


	public AccountModel(Object root) {
		super((AccountTB) root);
		// TODO Auto-generated constructor stub
	}
	public Class getColumnClass(int column) {
		return cTypes[column];
	    }
	 
	 public String getColumnName(int column) {
			return cNames[column];
		    }

	public Object getChild(Object accTB, int arg1) {
		// TODO Auto-generated method stub
		AccountTB obj = null;
		AccountTB tb = (AccountTB) accTB;
		 Enumeration enu = tb.childs.elements();
		 int i=0;
		  while(enu.hasMoreElements()) {
			  AccountTB aTB = (AccountTB)enu.nextElement();
			  if(i == arg1) {
				  obj = aTB;
				 // System.out.println(tb.getAccountName() + "  " + aTB.getAccountName() + " " + i);
				  break;
			  }
			  i++;
			  
		  }
		return obj;
	}

	
	protected Object[] getChildren(Object node) {
		AccountTB fileNode = ((AccountTB)node); 
		
		return fileNode.getChildrens();
	    }
	
	

	public int getChildCount(Object accTB) {
		// TODO Auto-generated method stub
		AccountTB tb = (AccountTB) accTB;
		
		return tb.childs.size();
	}


	


	 public int getColumnCount() {
			return cNames.length;
		    }


	public Object getValueAt(Object node, int column) {
		// TODO Auto-generated method stub
		AccountTB tb = (AccountTB) node;
		Object value = null;
		switch(column) {
		  
	    case 0:
		return tb.getAccountName();
	    
	    case 1:
		return tb.getDrAmt();
	    case 2:
		return tb.getCrAmt();
	    }
		    return value;
		
	}

}

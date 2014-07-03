package apps.window.operationwindow.trialBalance;

import java.util.Enumeration;

public class TradeModel extends AbstractTreeTableModel 
implements TreeTableModel {
	
	static protected String[]  cNames = {"TradeID",  "TradeID", "ProductType" ,"Quantity","Nominal"};

    // Types of the columns.
    static protected Class[]  cTypes = {TreeTableModel.class,Integer.class, String.class,Double.class,Double.class};


	public TradeModel(Object root) {
		super((TradeTV) root);
		// TODO Auto-generated constructor stub
	}

	public Class getColumnClass(int column) {
		return cTypes[column];
	    }
	 
	public Object getChild(Object accTB, int arg1) {
		// TODO Auto-generated method stub
		TradeTV obj = null;
		TradeTV tb = (TradeTV) accTB;
		 Enumeration enu = tb.childs.elements();
		 int i=0;
		  while(enu.hasMoreElements()) {
			  TradeTV aTB = (TradeTV)enu.nextElement();
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
		TradeTV tb = (TradeTV) accTB;
		
		return tb.childs.size();
	}


	


	 public int getColumnCount() {
			return cNames.length;
		    }


	public Object getValueAt(Object node, int column) {
		// TODO Auto-generated method stub
		TradeTV tb = (TradeTV) node;
		Object value = null;
		switch(column) {
		  
	    case 0:
		return tb.getTradeID();
	    
	    case 1:
		return tb.getTradeID();
	    case 2:
		return tb.getProductType();
	    case 3:
			return tb.getQuantity();
	    case 4:
			return tb.getNominal();
	    }
		    return value;
		
	}

	 public String getColumnName(int column) {
			return cNames[column];
		    }
}

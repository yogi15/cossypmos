package apps.window.positionwindow;

import java.rmi.RemoteException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JComboBox;

import beans.Book;
import beans.FilterBean;
import beans.LegalEntity;
import beans.Liquidation;
import beans.Openpos;
import beans.StartUPData;
import beans.Task;
import beans.Trade;
import beans.Transfer;
import beans.UserJobsDetails;
import util.commonUTIL;
import util.common.NumberFormatUtil;
import dsServices.RemoteBOProcess;
import dsServices.RemoteMO;
import dsServices.RemoteReferenceData;
import dsServices.RemoteTask;
import dsServices.RemoteTrade;

public class PositionFilterValues {
	
	Hashtable<String,Vector> dataValues = null; 
	 String datesSearch [] = {"between",">=",">","<=","<"};
	 String starupData [] = {"SearchCriteria","TaskColumn","Status","ProductType","Currency","EventType","WFType","BUY/SELL","TransferType","FEEType","accEvent"};
	 String referenceData [] = {"Book" };
	 Hashtable<Integer,Book> bookValues = new Hashtable<Integer,Book>(); 
	 Hashtable<Integer,LegalEntity> counterPary = new Hashtable<Integer,LegalEntity>(); 
	 static Hashtable<String,String> matachingColumns = new   Hashtable<String,String>(); 
	 static Hashtable<String,String> columnNames = new Hashtable<String,String>(); 
	 static Hashtable<String,String> numberDataTypes = new Hashtable<String,String>(); 
	 RemoteReferenceData remote = null;
	 RemoteTrade remoteTrade = null;
	 RemoteBOProcess remoteBO = null;
	 RemoteTask remoteTask = null;
	
	RemoteMO remoteMO = null;
	 static {

		 matachingColumns.put("PrimaryCurr", "Currency");
		 matachingColumns.put("QuotingCurr", "Currency");
		 
		 
		 columnNames.put("Book", "Bookno");
		 columnNames.put("LegalEntity", "id");
		 columnNames.put("Currency", "Currency");
		 columnNames.put("SettleCurrency", "Currency");
		 columnNames.put("Action", "action");
		 columnNames.put("Status", "Status");
		 columnNames.put("ProductType", "ProductType");
		 columnNames.put("EventType", "EventType");
		 columnNames.put("WFConfig", "EventType");
		 columnNames.put("WFType", "Type");
		 columnNames.put("BUY/SELL", "Type");
		 columnNames.put("TradeID", "tradeid");
		 columnNames.put("Amount", "amount");
		 
		 numberDataTypes.put("Book", "Bookno");
		 numberDataTypes.put("LegalEntity", "id");
		 numberDataTypes.put("Task", "id");
		 numberDataTypes.put("Trade", "id");
		 numberDataTypes.put("Transfer", "id");
		 numberDataTypes.put("Posting", "id");
		 numberDataTypes.put("TradeID", "tradeid");
		 numberDataTypes.put("Amount", "amount");
		
		 
	 }
	
	
	public PositionFilterValues(RemoteReferenceData remote,RemoteTrade remoteTrade,RemoteTask remoteTask,RemoteBOProcess remoteBO,RemoteMO remoteMO) {
		this.remoteTask = remoteTask;
		this.remoteTrade = remoteTrade;
		this.remoteBO = remoteBO;
		this.remote = remote;
		this.remoteMO = remoteMO;
		dataValues = new Hashtable<String,Vector>();
		for(int i=0;i<starupData.length;i++) {
			getValuesonColumn(starupData[i],remote);
		}
		dataValues.put("Book", getBooks(remote));
		dataValues.put("CounterParty", getCounterParty(remote));
		
	}
	
	
	
	public  Vector getValuesonColumn(String name,RemoteReferenceData remote1) {
		Vector data = null;
		boolean duplicateColumn = false;
		String duplicateColumnName = "";
		if(remote == null)  {
			
			return data;
		}
		
	
			if(dataValues.containsKey(name)) {
					data = dataValues.get(name);
			} else {
				duplicateColumnName = 	matachingColumns.get(name);
				if(!commonUTIL.isEmpty(duplicateColumnName))
				duplicateColumn = true;
			}
		
		if(data == null)  {
			try{ 
				if(duplicateColumn) {
					 data = (Vector)  dataValues.get(duplicateColumnName);
					 if(data == null)
				   data = (Vector) remote.getStartUPData(duplicateColumnName);
				} else {
					  data = (Vector) remote.getStartUPData(name);
				}
				synchronized (dataValues) {
					dataValues.put(name,data);
				}
				} catch (Exception e) {
					commonUTIL.displayError("JobsPanel ","FilterValues", e);
	            }
    	
			}
			
		convertVectortoSringArray(data,name);
		
		
		return data;
		
		
	}

	public String [] getDateSearchCriteria() {
		return datesSearch;
	}
	public String []  convertVectortoSringArrayForBook(Vector v,String objectType) {
		 
    	String name [] = null;
    	int i=0;
    	name = stringarray.get("Book");
    	if(name != null) 
    		return name;
    	else 
    	if(v != null ) {
    		name = new String[v.size()+1];
    		Iterator its = v.iterator();
    		name[i]  = "Seleced Item";
    		i = 1;
    		while(its.hasNext()) {
    			
    			name [i] = ( (Book) its.next()).getBook_name();
    			i++;
    		}
    	}
    	stringarray.put("Book", name);
		return name;                                           
        // TODO add your handling code here:
    } 
	
	public String []  convertVectortoSringArrayForCounterParty(Vector v,String objectType) {
		 
    	String name [] = null;
    	int i=0;
    	name = stringarray.get("CounterParty");
    	if(name != null) 
    		return name;
    	else 
    	if(v != null ) {
    		name = new String[v.size()+1];
    		Iterator its = v.iterator();
    		name[i]  = "Seleced Item";
    		i = 1;
    		while(its.hasNext()) {
    			
    			name [i] = ( (LegalEntity) its.next()).getName();
    			i++;
    		}
    	}
    	stringarray.put("CounterParty", name);
		return name;                                           
        // TODO add your handling code here:
    } 
	 public String []  convertVectortoSringArray(Vector v,String sname,int firstItem) {
		 
	    	String name [] = null;
	    	int i=0;
	    	name = stringarray.get(sname);
	    	if(name != null) 
	    		return name;
	    	else 
	    	if(v != null ) {
	    		name = new String[v.size()+1];
	    		Iterator its = v.iterator();
	    		name[i]  = "Seleced Item";
	    		i = 1;
	    		while(its.hasNext()) {
	    			
	    			name [i] = ( (StartUPData) its.next()).getName();
	    			i++;
	    		}
	    	}
	    	stringarray.put(sname, name);
			return name;                                           
	        // TODO add your handling code here:
	    } 
	 public String []  convertVectortoSringArray(Vector v,String sname) {
		 
	    	String name [] = null;
	    	int i=0;
	    	name = stringarray.get(sname);
	    	if(name != null) 
	    		return name;
	    	else 
	    	if(v != null ) {
	    		name = new String[v.size()+1]; 
	    		name[i] = "Selected Values";
	    		i = 1;
	    		Iterator its = v.iterator();
	    		while(its.hasNext()) {
	    			
	    			name [i] = ( (StartUPData) its.next()).getName();
	    			i++;
	    		}
	    	}
	    	stringarray.put(sname, name);
			return name;                                           
	        // TODO add your handling code here:
	    } 
	
	public void fillStartUPData(Vector data,JComboBox comb) {
		Iterator<StartUPData> its = data.iterator(); 
		while(its.hasNext()) {
			comb.addItem(((StartUPData) its.next()).getName());
			
		}
		
	}
	
	public Book getBook(int bookId) {
	Vector books = 	dataValues.get("Book");
	Book book = null;
for(int i=0;i<books.size();i++) {
	Book b1 = (Book) books.get(i);
	if(bookId == b1.getBookno()) {
		book = b1;
		break;
	}
}
		return book;
	}
	
	public LegalEntity geCounterParty(int cpID) {
		Vector counterParty = 	dataValues.get("CounterParty");
		LegalEntity le = null;
		
		for(int i=0;i<counterParty.size();i++) {
			LegalEntity b1 = (LegalEntity) counterParty.get(i);
			if(cpID ==b1.getId()) {
				le = b1;
				break;
			}
		}
			return le;
		}
		
	
	public Vector getActionOnTransfer(int transferID) {
		Vector actions = null;
	 try {
		 actions =  (Vector) remoteBO.getOnlyAction(transferID);
		 return actions;
	} catch (RemoteException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return null;
	}
	}
	public Transfer getTransfer(int transferID) {
		Transfer transfer = new Transfer();
		transfer.setId(transferID);
		try {
			return remoteBO.selectTransfer(transfer);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	public Trade getTrade(int tradeId) {
		
		try {
			return remoteTrade.selectTrade(tradeId);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	 public Vector getActions(Trade trade) {
			Vector actions = null;
		 try {
			 actions =  (Vector) remoteTrade.getOnlyAction(trade);
			 return actions;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	public void fillBookData(Vector data,JComboBox comb) {
		Iterator<Book> its = data.iterator(); 
		while(its.hasNext()) {
			comb.addItem(((Book) its.next()).getBook_name());
			
		}
		
	}
	public void fillCounterParty(Vector data,JComboBox comb) {
		Iterator<Book> its = data.iterator(); 
		while(its.hasNext()) {
			comb.addItem(((Book) its.next()).getBook_name());
			
		}
		
	}
	private Vector getCounterParty(RemoteReferenceData remote) {
		Vector counterParty = null;
		try {
			if(dataValues.containsKey("counterPary")) {
				counterParty = dataValues.get("counterPary");
		}
			if(counterParty == null ) {
				counterParty =  (Vector) remote.selectAllLs();
			   dataValues.put("CounterParty",counterParty);
			   convertVectortoSringArrayForCounterParty(counterParty,"CounterParty");
			}
			
			return counterParty;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			commonUTIL.displayError("FilterValues " , " getCounterParty", e);
			return null;
		}
		
	}
	private Vector getBooks(RemoteReferenceData remote) {
		Vector books = null;
		try {
			if(dataValues.containsKey("Book")) {
				books = dataValues.get("Book");
		}
			if(books == null ) {
			   books =  (Vector) remote.selectALLBooks();
			   dataValues.put("Book",books);
			   convertVectortoSringArrayForBook(books,"Book");
			}
			
			return books;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			commonUTIL.displayError("FilterValues " , " getBooks", e);
			return null;
		}
		
	}
	Hashtable<String,String[]> stringarray = new Hashtable<String,String[]>();


	public String createWhere(Vector<FilterBean> jobdetails) {
		// TODO Auto-generated method stub
		String where  ="";
		if(jobdetails == null || jobdetails.size() == 0 || jobdetails.isEmpty()) 
			return null;
		for(int i=0;i<jobdetails.size();i++) {
			FilterBean bean = (FilterBean) jobdetails.get(i);
			   if(bean.getColumnName().equalsIgnoreCase("Book")) {
				   where = where + createCriteriaOnBook(dataValues.get("Book"),bean);
				   
			   }if(bean.getColumnName().equalsIgnoreCase("CounterParty")) {
				   where = where + createCriteriaOnCounterParty(dataValues.get("CounterParty"),bean);
				   
			   } else {
				   where = where + createCriteria(bean);
			   }
			   if(i != jobdetails.size()-1) {
				   if(bean.getAnd_or() == null || bean.getAnd_or().isEmpty())
					   where = where + " and ";
				   else 
					   where = where + " " + bean.getAnd_or() +  "  ";
			   } 
			
		}
		where = where + " and task_status not in ('2')";
		return where;
	}

	public String createWhere(Vector<FilterBean> jobdetails,String tableName) {
		// TODO Auto-generated method stub
		String where  ="";
		if(jobdetails == null || jobdetails.size() == 0 || jobdetails.isEmpty()) 
			return null;
		for(int i=0;i<jobdetails.size();i++) {
			FilterBean bean = (FilterBean) jobdetails.get(i);
			   if(bean.getColumnName().equalsIgnoreCase("Book")) {
				   where = where + " " + tableName+"."+ createCriteriaOnBook(dataValues.get("Book"),bean);
				   
			   } else {
				   where = where +  " " + tableName+"."+ createCriteria(bean);
			   }
			   if(i != jobdetails.size()-1) {
				   if(bean.getAnd_or() == null || bean.getAnd_or().isEmpty())
					   where = where + " and ";
				   else 
					   where = where + " " + bean.getAnd_or() +  "  ";
			   } 
			
		}
		if(tableName.equalsIgnoreCase("trade"))
		    where = where + " and trade.version >= 0 ";
		
		return where;
	}
	
	
	private String createCriteriaOnBook(Vector<Book> books,FilterBean bean) {
		String bookCriteria  ="";
		String ids =bean.getIdSelected();
		if(books == null || books.size() == 0 || books.isEmpty()) 
			return null;
		if(ids == null) 
			return null;
		if(ids.contains(","))  {
			String values [] = ids.split(",");
			 for(int i=0;i<values.length;i++) {
				 String id = values[i].trim();
				 Book book = books.elementAt(new Integer(id).intValue()-1); 
		       bookCriteria = bookCriteria + book.getBookno() + ",";
			 }
			 bookCriteria = bookCriteria.substring(0,bookCriteria.length()-1);
		} else {
			bookCriteria = ids;
		}
		ids = columnNames.get(bean.getColumnName()) + " " + " " +attachsqlTypeCrietria(bean.getSearchCriteria().trim(),bookCriteria,bean.getColumnName());
		return ids;
		
	} 
	
	private String createCriteriaOnCounterParty(Vector<LegalEntity> counterPartys,FilterBean bean) {
		String leCriteria  ="";
		String ids =bean.getIdSelected();
		if(counterPartys == null || counterPartys.size() == 0 || counterPartys.isEmpty()) 
			return null;
		if(ids == null) 
			return null;
		if(ids.contains(","))  {
			String values [] = ids.split(",");
			 for(int i=0;i<values.length;i++) {
				 String id = values[i].trim();
				 LegalEntity le = counterPartys.elementAt(new Integer(id).intValue()-1); 
				 leCriteria = leCriteria + le.getId() + ",";
			 }
			 leCriteria = leCriteria.substring(0,leCriteria.length()-1);
		} else {
			leCriteria = ids;
		}
		ids = columnNames.get(bean.getColumnName()) + " " + " " +attachsqlTypeCrietria(bean.getSearchCriteria().trim(),leCriteria,bean.getColumnName());
		return ids;
		
	} 
	
	private String attachsqlTypeCrietria(String criteriaType,String values,String columnName ) {
		String typeC ="";
		if(criteriaType.equalsIgnoreCase("in")  || criteriaType.equalsIgnoreCase("not in") ) {
			typeC = criteriaType + " ('";
			if(values.contains(","))  {
				//values =getFilterValues(values,"Books");
				String value [] = values.split(",");
				 for(int i=0;i<value.length;i++) {
					 String id = value[i].trim();
			          typeC = typeC + id +"','";
				 }
				 typeC =typeC.substring(0,typeC.length()-2)   + ")";
			} else {
				typeC = typeC + values +"')";
			}
		}
		if(criteriaType.equalsIgnoreCase("=")  || criteriaType.equalsIgnoreCase("!=") ) {
			typeC = criteriaType + " '" + values +"'";
			}
		if(criteriaType.equalsIgnoreCase(">")  || criteriaType.equalsIgnoreCase("<") || criteriaType.equalsIgnoreCase(">=") || criteriaType.equalsIgnoreCase("<=") ) {
			
			if (numberDataTypes.containsKey(columnName.trim()) ) {
				typeC = criteriaType + values;
			} else 
				typeC = criteriaType + " '" + values +"'";
			}
		return typeC;
		
	}
	private String createCriteria(FilterBean bean) {
		String criteria = columnNames.get(bean.getColumnName()) + " " + " " +attachsqlTypeCrietria(bean.getSearchCriteria().trim(),bean.getColumnValues(),bean.getColumnName());
		return criteria;
		
	}



	public String getids(String columnValues,String values) {
		// TODO Auto-generated method stub
		return null;
	}



	public String getFilterValues(String ids, String columnName) {
		// TODO Auto-generated method stub
		String bookIds = "";
		if(ids == null || ids.isEmpty())
			return null;
		int start = ids.indexOf("[")+1;
		int end = ids.indexOf("]");
		String id = ids.substring(start,end);
		
			return id;
		
		
	}



	public void updateTransferAndPublissh(Transfer transfer, int userID) {
		// TODO Auto-generated method stub
		try {
			transfer.setUserid(userID);
			remoteBO.updateTransferAndPublish(transfer,userID);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	public int updateTraderAndPublish(Trade trade, int userID) {
		// TODO Auto-generated method stub
		try {
			trade.setUserID(userID);
			return remoteTrade.saveTrade(trade);
			 
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
		
	}

	 public RemoteMO getRemoteMO() {
			return remoteMO;
		}



		public void setRemoteMO(RemoteMO remoteMO) {
			this.remoteMO = remoteMO;
		}
		
		
		public Vector<Openpos> getOpenPosition(int positionID) {
			 Vector<Openpos> openPos = null;
			try {
				openPos = (Vector) remoteMO.getOpenPosition(positionID);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return openPos;
		}
		public String getPNLRealisedOnPosition(int positionID) {
			double  pnlRealised= 0;
			try {
				pnlRealised =  remoteMO.getPNLRealisedOnPosition(positionID);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
			
			return commonUTIL.doubleFormat((pnlRealised));
		}

		public Vector<Liquidation> getLiqOpenPosition(int positionID) {
			 Vector<Liquidation> liqPos = null;
			try {
				liqPos = (Vector) remoteMO.getLiquidationsOnPosition(positionID);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return liqPos;
		}
	public void savePublishTask(Task task,String status) {
		// TODO Auto-generated method stub
		try {
			remoteTask.updateTaskStatus(task,status );
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			commonUTIL.displayError("FilterValues", "savePublishTask", e);
		}
		
	}
	
}

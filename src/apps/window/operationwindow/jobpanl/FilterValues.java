package apps.window.operationwindow.jobpanl;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JComboBox;

import beans.Book;
import beans.FilterBean;
import beans.LegalEntity;
import beans.StartUPData;
import beans.Task;
import beans.Trade;
import beans.Transfer;
import beans.UserJobsDetails;

import util.commonUTIL;

import dsServices.RemoteBOProcess;
import dsServices.RemoteReferenceData;
import dsServices.RemoteTask;
import dsServices.RemoteTrade;

public class FilterValues {
	
	 Hashtable<String,Vector> dataValues = null; 
	 String starupData [] = {"SearchCriteria","TaskColumn","Status","ProductType","Currency","EventType","WFType","BUY/SELL","TransferType","FEEType","accEvent","TaskType","TradeAttribute","QuotingCurr","PrimaryCurr","LEAttributes"};
	 String referenceData [] = {"Book" };
	 String datesSearch [] = {"between",">=",">","<=","<"};
	 Hashtable<Integer,Book> bookValues = new Hashtable<Integer,Book>(); 
	 Hashtable<Integer,LegalEntity> counterPary = new Hashtable<Integer,LegalEntity>(); 
	 static Hashtable<String,String> columnNames = new Hashtable<String,String>(); 
	 static Hashtable<String,String> numberDataTypes = new Hashtable<String,String>(); 
	 static Hashtable<String,String> matachingColumns = new   Hashtable<String,String>(); 
	 static Hashtable<String,String> tableNames = new   Hashtable<String,String>();
	 String attributesObject [] = {"legalEntity.Attributes","book.Attributes","transfer.attributes"};
	 RemoteTrade remoteTrade = null;
	 RemoteBOProcess remoteBO = null;
	 RemoteTask remoteTask = null;
	 RemoteReferenceData remote = null;
	 static {
		 columnNames.put("Book", "Bookid");
		 columnNames.put("LegalEntity", "id");
		 columnNames.put("Currency", "Currency");
		 columnNames.put("SettleCurrency", "Currency");
		 columnNames.put("Action", "action");
		 columnNames.put("Status", "Status");
		 columnNames.put("ProductType", "ProductType");
		 columnNames.put("EventType", "EventType");
		 columnNames.put("WFConfig", "EventType");
		 columnNames.put("WFType", "WFType");
		 columnNames.put("BUY/SELL", "Type");
		 columnNames.put("TradeID", "tradeid");
		 columnNames.put("Amount", "amount");
		 columnNames.put("TaskType", "taskType");
		 columnNames.put("TaskType", "taskType");
		 columnNames.put("TradeDate", "tradeDate");
		 columnNames.put("EffectiveDate", "effectiveDate");
		 columnNames.put("DeliveryDate", "deliveryDate");
		 columnNames.put("CounterParty", "cpid");
		 columnNames.put("SettleDate", "SettleDate");
		 columnNames.put("PrimaryCurr", "PrimaryCurr");
		 columnNames.put("QuotingCurr", "QuotingCurr");
		 columnNames.put("Quantity", "Quantity");
		 columnNames.put("TaskDate", "TaskDate");
		 
		 numberDataTypes.put("Book", "Bookid");
		 numberDataTypes.put("LegalEntity", "id");
		 numberDataTypes.put("Task", "id");
		 numberDataTypes.put("Trade", "id");
		 numberDataTypes.put("Transfer", "id");
		 numberDataTypes.put("Posting", "id");
		 numberDataTypes.put("TradeID", "tradeid");
		 numberDataTypes.put("Amount", "amount");
		 numberDataTypes.put("Quantity", "Quantity");
		
		 matachingColumns.put("PrimaryCurr", "Currency");
		 matachingColumns.put("QuotingCurr", "Currency");
		 
		 tableNames.put("Trade", "Trade");
		 tableNames.put("Transfer", "Transfer");
		 tableNames.put("CashPosition", "Cashposition");
		 tableNames.put("OpenPos", "OpenPos");
		 tableNames.put("Posting", "Posting");
		 tableNames.put("Product", "Product");
		 tableNames.put("Coupon", "Coupon");
		 tableNames.put("Book", "Book");
		 tableNames.put("Le", "Le");
		 tableNames.put("Trader", "Le");
		 tableNames.put("PNL", "Liquidpos");
		// tableNames.put("Cashposition", "Cashposition");
		 
		 
		 
		 
		 
	 }
	
	 public String getTableName(String tableName) {
		return tableNames.get(tableName);
	 }
	
	public FilterValues(RemoteReferenceData remote,RemoteTrade remoteTrade,RemoteTask remoteTask,RemoteBOProcess remoteBO) {
		this.remote = remote;
		this.remoteTask = remoteTask;
		this.remoteTrade = remoteTrade;
		this.remoteBO = remoteBO;
		dataValues = new Hashtable<String,Vector>();
		for(int i=0;i<starupData.length;i++) {
			getValuesonColumn(starupData[i],remote);
		}
		dataValues.put("Book", getBooks(remote));
		dataValues.put("CounterParty", getCounterParty(remote));
		dataValues.put("QuotingCurr", dataValues.get("Currency"));
		dataValues.put("PrimaryCurr", dataValues.get("Currency"));
		dataValues.put("legalEntity.Attributes", dataValues.get("LEAttributes"));
		
		
	}
	
	public String [] getDateSearchCriteria() {
		return datesSearch;
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
	Book book = (Book) books.get(bookId);
		return book;
	}
	
	public LegalEntity geCounterParty(int cpID) {
		Vector counterParty = 	dataValues.get("CounterParty");
		LegalEntity le = (LegalEntity) counterParty.get(cpID);
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
			boolean isStaticRefData = true;
			   if(bean.getColumnName().equalsIgnoreCase("Book")) {
				   isStaticRefData = false;
				   where = where + createCriteriaOnBook(dataValues.get("Book"),bean);
				   
			   }if(bean.getColumnName().equalsIgnoreCase("CounterParty")) {
				   isStaticRefData = false;
				   where = where + createCriteriaOnCounterParty(dataValues.get("CounterParty"),bean);
				   
			   } else if((isStaticRefData) ) {
				   where = where + createCriteria(bean,"");
			   }
			   if(i != jobdetails.size()-1) {
				   if(commonUTIL.isEmpty(bean.getAnd_or()))
					   where = where + " and ";
				   else 
					   where = where + " " + bean.getAnd_or() +  "  ";
			   } 
			
		}
		where = where + " and task_status not in ('2')";
		return where;
	}

	public String createWhereForAttribute(FilterBean bean,int count) {
		String where = "";
		if(count ==0) {
			 where = where + " attribute"+count+".attributename = '"+ bean.getColumnValues() + "' and attribute"+count+".attributeValue "+bean.getSearchCriteria() + " '" + bean.getAnd_or() + "'";
		       if(bean.getColumnName().contains("Trade")) {
		    	   where = where + " and attribute"+count+".type = 'Trade' and attribute"+count+".id = Trade.id";
		       }
		} else {
			where = where + " attribute"+count+".attributename = '"+ bean.getColumnValues() + "' and attribute"+count+".attributeValue "+bean.getSearchCriteria() + " '" + bean.getAnd_or() + "'";
		       if(bean.getColumnName().contains("Trade")) {
		    	   where = where + " and attribute"+count+".type = 'Trade'  and attribute"+count+".id = Trade.id";
		       }
		}
		return where.trim();
	}
	
	public boolean isObjectAttribute(String objectAndAttributeName) {
		boolean flag =false;
		for(int i=0;i<attributesObject.length;i++) {
			String objAttributeNme = (String) attributesObject[i];
			if(objAttributeNme.equalsIgnoreCase(objectAndAttributeName)){
				flag = true;
				break;
			}
		}
		return flag;
	}
	
	public String createWhere(Vector<FilterBean> jobdetails,String tableName1) {
		// TODO Auto-generated method stub
		String where  ="";
	    int att = 0;
		if(jobdetails == null || jobdetails.size() == 0 || jobdetails.isEmpty()) 
			return where;
		for(int i=0;i<jobdetails.size();i++) {
			FilterBean bean = (FilterBean) jobdetails.get(i);
			boolean isStaticRefData = true;
			if(isObjectAttribute(bean.getColumnName())) {
				 where = where + " " + bean.getColumnName() + " "  + bean.getSearchCriteria() + " '%"  +bean.getColumnValues() + "="+bean.getAnd_or()+"%'";
			} else 	   if(bean.getColumnName().equalsIgnoreCase("Book")) {
				   where = where + " " + tableNames.get(tableName1).toLowerCase() +"."+ createCriteriaOnBook(dataValues.get("Book"),bean);
				   
			   } else    if(bean.getColumnName().equalsIgnoreCase("CounterParty")) {
				   isStaticRefData = false;
				   where = where + " " + tableNames.get(tableName1).toLowerCase()+"."+ createCriteriaOnCounterParty(dataValues.get("CounterParty"),bean);
				   
			   } else  if(bean.getColumnName().equalsIgnoreCase("TradeAttribute")) { 
				   where = where + " " + createWhereForAttribute(bean,att);
				   att++;
			       if(i != jobdetails.size()-1) {
			    	   where = where + " and ";
			       }
			   } else {
				   where = where +  " " + tableNames.get(tableName1).toLowerCase()+"."+ createCriteria(bean,tableName1);
			   }
			   
				  
			   
			   if(i != jobdetails.size()-1) {
				   if(bean.getAnd_or() == null || bean.getAnd_or().isEmpty())
					   where = where + " and ";
				   else 
					   if(bean.getColumnName().endsWith("Date")) {
						   where = where + " and ";
					   } else {
					       where = where + " " + bean.getAnd_or() +  "  ";
					   }
			   } 
			
		}
		if(tableName1.equalsIgnoreCase("trade"))
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
			Book book = books.elementAt(new Integer(ids).intValue()-1); 
			bookCriteria = new Integer(book.getBookno()).toString();
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
			 LegalEntity le = counterPartys.elementAt(new Integer(ids).intValue()-1);
			leCriteria = new Integer(le.getId()).toString();
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
	private String createCriteria(FilterBean bean,String tableName1) {
		String criteria = "";
		if(bean.getColumnName().endsWith("Date")) {
			 criteria = columnNames.get(bean.getColumnName()) + " " + " "  + getDatesWhereClause(bean.getColumnValues(),bean.getAnd_or(),bean.getSearchCriteria(),tableNames.get(tableName1)+"."+columnNames.get(bean.getColumnName()));
		} else {
		 criteria = columnNames.get(bean.getColumnName()) + " " + " " +attachsqlTypeCrietria(bean.getSearchCriteria().trim(),bean.getColumnValues().toUpperCase(),bean.getColumnName());
		}
		 return criteria;
		
	}

	 public String getDatesWhereClause(String startDate,String endDate,String searchCriteria,String columnName)  {
	    	String dateWhereClause = "";
	    	if(searchCriteria.equalsIgnoreCase("between")) {
	    		dateWhereClause = " between  to_date('"+ startDate.trim()+"', 'dd/MM/YYYY')" ;
	    		dateWhereClause = dateWhereClause + " and to_date('"+endDate.trim()+"', 'dd/MM/YYYY')";
	    	} else {
	    		dateWhereClause =  searchCriteria + "  to_date('"+ startDate.trim()+"', 'dd/MM/YYYY')" ;
	    		if(commonUTIL.isEmpty(endDate))
	    			return dateWhereClause;
	    		if(searchCriteria.equalsIgnoreCase(">")) {
	    			dateWhereClause =dateWhereClause + " and " + columnName +"   <" + "  to_date('"+ endDate.trim()+"', 'dd/MM/YYYY')" ;
	    					
	    		} else {
	    			dateWhereClause = dateWhereClause + " and " + columnName +"  >" + "  to_date('"+ endDate.trim()+"', 'dd/MM/YYYY')" ;
	    		}
	    	}
	    	
	    	return dateWhereClause;
	    	
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

    

	public int updateTraderAndPublissh(Trade trade, int userID) {
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
	public Collection getNettedTransfers(int transferID) {
		Vector nettedTransfer =null;
		try {
			nettedTransfer =  (Vector) remoteBO.getNettedTransfers(transferID);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nettedTransfer;
	}


	public void savePublishTask(Task task,String status) {
		// TODO Auto-generated method stub
		try {
			remoteTask.updateTaskStatus(task,task.getUserid(),task.getUser_name(),status );
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			commonUTIL.displayError("FilterValues", "savePublishTask", e);
		}
		
	}
//	tradeAttribute.ISIN
	public String createWhereOnAttributes(String columnSQL,String where) {
		if(!columnSQL.contains("tradeAttribute")) {
			  if(columnSQL.contains("where"))
				return columnSQL ;
			  else 
				  return columnSQL + " where " +  where; 
		}
		int counter = 0;
		String tablename = "";
		String whereClause =where;
		String sql = "Select ";
		String type = "Trade";
		int selectIndex = columnSQL.indexOf("select");
    	int fromIndex = columnSQL.indexOf("from");
    	int whereIndex = columnSQL.indexOf("where");
    	String sqlColumn = columnSQL.substring(selectIndex+6, fromIndex);
    	if(whereIndex > 0){
    	tablename = columnSQL.substring(fromIndex,whereIndex);
    	 whereClause =" where " + whereClause + " and " + columnSQL.substring(whereIndex+5, columnSQL.length()) + " and " ;
    	} else {
    		tablename = columnSQL.substring(fromIndex,columnSQL.length());
    		if(commonUTIL.isEmpty(where)) {
       	        whereClause = " where "; // whereClause + columnSQL.substring(whereIndex, columnSQL.length());
    		}   else {
       	        	whereClause = " where " + where + " and ";
    		}
    	}
    	String columns [] = sqlColumn.split(",");
    	for(int i=0;i < columns.length;i++) {
    		 if(columns[i].contains("tradeAttribute"))  {
    			 String attributeCol = columns[i].trim();
    			 String attributeName = attributeCol.replace("tradeAttribute.", "");
    			  
    				 
    				  attributeCol = attributeCol.replace("tradeAttribute.", "attribute"+counter+ ".attributevalue " );
    				  tablename = tablename + ", Attribute attribute"+counter  + " ";
    				  if(!whereClause.contains(attributeName)) {
    					  String whereC = getAttributeWhereAlias(counter,whereClause);
    					  tablename = tablename + addNestedTableName(counter,tablename);
    					  whereClause =whereClause +" "+ whereC + ".id = "+type+".id and  "+ whereC + ".type='"+type+"' and  "+ whereC + ".attributename ='"+attributeName+"'  and ";
    				      
    				  }
    				  counter = counter+1;
    			 
    			 sql = sql + attributeCol + ",";
    		 } else {
    			 sql = sql + columns[i] + ",";
    		 }
    		 
    	}
    	sql = sql.trim().substring(0, sql.length()-1); 
		// TODO Auto-generated method stub
    	whereClause = whereClause.substring(0, whereClause.lastIndexOf("and"));
    	sql = sql  + " " + tablename + " " + whereClause;
		return sql;
	}
	
	public String addNestedTableName(int counter,String tablenames) {
		String tablealias  =  "attribute"+counter;
		if(tablenames.contains(tablealias)) {
			tablealias = "";
		} else {
			tablealias = ", Attribute " + "attribute"+counter;
		}
		return tablealias;
	}
	
	public String getAttributeWhereAlias(int counter,String where) {
		int count = counter;
		String attributeAlias =  "attribute"+count;
		  if(checkAttribute(attributeAlias,where)) {
			  count = count + 1;
			  attributeAlias =  getAttributeWhereAlias(count,where);
		  }
		  return attributeAlias;
	}
	public boolean checkAttribute(String alias,String where) {
		boolean flag = false;
		String attributeAlias = alias;
		if(where.contains(attributeAlias)) {
			return true;
		}
		return flag;
		
		
	}
	
}

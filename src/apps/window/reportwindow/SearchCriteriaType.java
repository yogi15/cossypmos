package apps.window.reportwindow;

import java.rmi.RemoteException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JPanel;

import util.commonUTIL;

import dsServices.RemoteBOProcess;
import dsServices.RemoteReferenceData;
import dsServices.RemoteTask;
import dsServices.RemoteTrade;
import dsServices.ServerConnectionUtil;

import apps.window.operationwindow.jobpanl.FilterValues;
import beans.Book;
import beans.FilterBean;
import beans.LegalEntity;
import beans.StartUPData;
import beans.UserJobsDetails;

public abstract class SearchCriteriaType extends JPanel
{
	public static ServerConnectionUtil de = null;
	RemoteReferenceData remoteBORef;
	RemoteTask remoteTask;
	RemoteTrade remoteTrade;
	RemoteBOProcess remoteBo;
	FilterValues filterValues = null;
	Hashtable<Integer,beans.Book> books = new Hashtable<Integer,beans.Book> ();
	Hashtable<Integer,beans.LegalEntity> counterPartyID = new Hashtable();
	Hashtable<Integer,beans.LegalEntity> poID = new Hashtable();
	Hashtable<Integer,beans.LegalEntity> agentID = new Hashtable();
     /**
	 * @return the filterValues
	 */
	public FilterValues getFilterValues() {
		return filterValues;
	}
	/**
	 * @param filterValues the filterValues to set
	 */
	
	public abstract Vector<FilterBean>  searchCriteria();
     public abstract  void clearllCriterial();
     public abstract void loadFilters(Vector<UserJobsDetails> jobdetails);
     
     public SearchCriteriaType() {
    	 de = ServerConnectionUtil.connect("localhost", 1099,
 				commonUTIL.getServerIP());

 		try {
 			remoteBORef = (RemoteReferenceData) de
 					.getRMIService("ReferenceData");
 			remoteTask = (RemoteTask) de.getRMIService("Task");
 			remoteTrade = (RemoteTrade) de.getRMIService("Trade");
 			remoteBo = (RemoteBOProcess) de.getRMIService("BOProcess");
 			filterValues = new FilterValues(remoteBORef, remoteTrade,remoteTask, remoteBo);
 		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			commonUTIL.displayError("JFrameNewReport ", " Constructor ", e);
		}
    	 
     }
     
     public FilterBean getTradeID(String tradeID)  {
    	 FilterBean bean = null;
    	 if(!commonUTIL.isEmpty(tradeID)) {
 			bean = new FilterBean();
 			bean.setColumnName("TradeID");
 			bean.setColumnValues(tradeID);
 			bean.setAnd_or("And");
 			bean.setSearchCriteria("in");
 			
 		}
    	 return bean;
     }
     
     public FilterBean getOtherId(String tradeID)  {
    	 FilterBean bean = null;
    	 if(!commonUTIL.isEmpty(tradeID)) {
 			bean = new FilterBean();
 			bean.setColumnName("otherTradeID");
 			bean.setColumnValues(tradeID);
 			bean.setAnd_or("And");
 			bean.setSearchCriteria("in");
 			
 		}
    	 return bean;
     }
     
     public FilterBean getTransferId(String transferId)  {
    	 FilterBean bean = null;
    	
		bean = new FilterBean();
		bean.setColumnName("TransferId");
		bean.setColumnValues(transferId);
		bean.setAnd_or("And");
		bean.setSearchCriteria("in");
 			
    	 return bean;
     }
     public FilterBean getCriteriaDate(String TradeDateFrom, String DateTo, String colName)  {
    	 FilterBean bean = null;
    	 if(!commonUTIL.isEmpty(TradeDateFrom)) {
    		 bean = new FilterBean();
 			bean.setColumnName(colName);
 			bean.setColumnValues(TradeDateFrom);
 			
 			if (!DateTo.equals(TradeDateFrom)) {
 				
 				bean.setAnd_or(DateTo);
 				bean.setSearchCriteria("between");
 				
 			} else {
 				
 				bean.setAnd_or(TradeDateFrom);
 				bean.setSearchCriteria("equal");
 			}
 			
 			
 		}
    	 return bean;
     }
     public FilterBean getMaturityDateFrom(String MaturityDateFrom)  {
    	 FilterBean bean = null;
    	 if(!commonUTIL.isEmpty(MaturityDateFrom)) {
    		 bean = new FilterBean();
 			bean.setColumnName("DeliveryDate");
 			bean.setColumnValues(MaturityDateFrom);
 			bean.setAnd_or(MaturityDateFrom);
 			bean.setSearchCriteria("between");
 			
 		}
    	 return bean;
     }
     public FilterBean getSettleDateFrom(String SettleDateFrom)  {
    	 FilterBean bean = null;
    	 if(!commonUTIL.isEmpty(SettleDateFrom)) {
    		 bean = new FilterBean();
 			bean.setColumnName("SettleDate");
 			bean.setColumnValues(SettleDateFrom);
 			bean.setAnd_or(SettleDateFrom);
 			bean.setSearchCriteria("between");
 			
 		}
    	 return bean;
     }
     
     
     public FilterBean getBUYSELL(String tradeType)  {
    	 FilterBean bean = null;
    	 if(!commonUTIL.isEmpty(tradeType)) {
    	 bean = new FilterBean();
			bean.setColumnName("Type");
			bean.setColumnValues(tradeType);
			bean.setSearchCriteria("in");
			bean.setAnd_or("And");
		
 			
 		}
    	 return bean;
     }
     public FilterBean getLadder(String ladder,String Ladder)  {
    	 FilterBean bean = null;
    	 if(!commonUTIL.isEmpty(ladder)) {
    	 bean = new FilterBean();
			bean.setColumnName(Ladder);
			bean.setColumnValues(ladder);
			bean.setSearchCriteria("in");
			bean.setAnd_or("And");
		
 			
 		}
    	 return bean;
     }
     public FilterBean getStatus(String Status)  {
    	 FilterBean bean = null;
    	 if(!commonUTIL.isEmpty(Status)) {
    		 bean = new FilterBean();
 			bean.setColumnName("Status");
 			bean.setColumnValues(Status);
 			bean.setAnd_or("And");
 			bean.setSearchCriteria("in");
 			
 		}
    	 return bean;
     }
     public FilterBean getAction(String Action)  {
    	 FilterBean bean = null;
    	 if(!commonUTIL.isEmpty(Action)) {
    		 bean = new FilterBean();
 			bean.setColumnName("Action");
 			bean.setColumnValues(Action);
 			bean.setSearchCriteria("in");
 			bean.setAnd_or("And");
 			
 		}
    	 return bean;
     }
     public FilterBean getCurrency(String Currency, String colName)  {
    	 FilterBean bean = null;
    	 if(!commonUTIL.isEmpty(Currency)) {
    		 bean = new FilterBean();
    			bean.setColumnName(colName);
    			
    			bean.setColumnValues(Currency);
    			bean.setAnd_or("And");
    			bean.setSearchCriteria("in");
 			
 		}
    	 return bean;
     }
     public FilterBean getProductType(String ProductType)  {
    	 FilterBean bean = null;
    	 if(!commonUTIL.isEmpty(ProductType)) {
    		 bean = new FilterBean();
 			bean.setColumnName("ProductType");
 			bean.setColumnValues(ProductType);
 			bean.setAnd_or("And");
 			bean.setSearchCriteria("in");
 			
 		}
    	 return bean;
     }
     public FilterBean getProductSubType(String ProductSubType)  {
    	 FilterBean bean = null;
    	 if(!commonUTIL.isEmpty(ProductSubType)) {
    		 bean = new FilterBean();
 			bean.setColumnName("ProductSubType");
 			bean.setColumnValues(ProductSubType);
 			bean.setSearchCriteria("in");
 			bean.setAnd_or("And");
 			
 		}
    	 return bean;
     }
     
     public FilterBean getTransferType(String transferType)  {
    	 FilterBean bean = null;
    	 if(!commonUTIL.isEmpty(transferType)) {
    		 bean = new FilterBean();
 			bean.setColumnName("TransferType");
 			bean.setColumnValues(transferType);
 			bean.setSearchCriteria("in");
 			bean.setAnd_or("And");
 			
 		}
    	 return bean;
     }
    
     public FilterBean getTransferEventType(String transferEventType)  {
    	 FilterBean bean = null;
    	 if(!commonUTIL.isEmpty(transferEventType)) {
    		 bean = new FilterBean();
 			bean.setColumnName("TransferEventType");
 			bean.setColumnValues(transferEventType);
 			bean.setSearchCriteria("in");
 			bean.setAnd_or("And");
 			
 		}
    	 return bean;
     }
     
     
     public FilterBean getBookName(int bookID)  {
    	 FilterBean bean = null;
    	 if(bookID > 0) {
    		 bean = new FilterBean();
 			bean.setColumnName("Book");
 			bean.setSearchCriteria("in");
 			
 			bean.setColumnValues(new Integer(getBookID(bookID)).toString());
 			bean.setAnd_or("And");
 		
 			
 			
 		}
    	 return bean;
     }
     public FilterBean getLegalEntity(int leId, String role)  {
    	 FilterBean bean = null;
    	
    	 if(leId > -1) {
    		 
    		 bean = new FilterBean();
 			 bean.setColumnName(role);
 			 
 			 if (role.equals("cpid")) {
 				 
 				bean.setColumnValues(new Integer(getCPid(leId)).toString());
 				
 			 } else if (role.equals("poId")) {
 				 
 				bean.setColumnValues(new Integer(getPOid(leId)).toString());
 			 
 			 } else if (role.equals("agentId")) {
 				 
  				bean.setColumnValues(new Integer(getAgentid(leId)).toString());
  			 
 			 }
 			 
 			 bean.setAnd_or("And");
 			 bean.setSearchCriteria("in");
 			
 		}
    	 return bean;
     }
     
     public int getBookID(int idSelected) {
 		Book book = (Book) books.get(idSelected);
 		return book.getBookno();
 		
 	}
     public int getCPid(int idSelected) {
 		LegalEntity le = (LegalEntity)  counterPartyID.get(idSelected);
 		return le.getId();
 		
 	}
 	
     public int getPOid(int idSelected) {
  		LegalEntity le = (LegalEntity)  poID.get(idSelected);
  		return le.getId();
  		
  	}
     
     public int getAgentid(int idSelected) {
  		LegalEntity le = (LegalEntity)  agentID.get(idSelected);
  		return le.getId();
  		
  	}
     public int getBooktoSelected(int idSelected) {
 		int selectID = 0;
 		for(int i=0;i<books.size();i++) {
 			selectID = i;
 			Book book = (Book) books.get(i);
 			if(book != null)
 			if(book.getBookno() == idSelected) 
 				break;
 		}
 		return selectID;
 		
 	}
     public int getCPtoSelected(int idSelected) {
 		int selectID = 0;
 		for(int i=0;i<counterPartyID.size();i++) {
 			selectID = i;
 			LegalEntity le = (LegalEntity) counterPartyID.get(i);
 			if(le != null)
 			if(le.getId() == idSelected) 
 				break;
 		}
 		return selectID;
 		
 	}
     
     public int getPOtoSelected(int idSelected) {
  		int selectID = 0;
  		for(int i=0;i<poID.size();i++) {
  			selectID = i;
  			LegalEntity le = (LegalEntity) poID.get(i);
  			if(le != null)
  			if(le.getId() == idSelected) 
  				break;
  		}
  		return selectID;
  		
  	}
     
     public void processLEDataCombo1(javax.swing.DefaultComboBoxModel combodata, Hashtable ids, String role) {
 		
 		Vector ledata;
 		
 				//String roleType = " role like 'PO' ";
 			ledata = (Vector<String>) getFilterValues().getLegalEntitys();

 			Iterator it = ledata.iterator();
 			int p = 0;
 			combodata.addElement("");
 			
 			while (it.hasNext()) {

 				LegalEntity le = (LegalEntity) it.next();
 				
 				if (le.getRole().equalsIgnoreCase(role)) {
 					
 					combodata.insertElementAt(le.getName(), p);
 	 				ids.put(p, le);
 	 				p++;
 	 				
 				}
 			
 			}

 		

 	}
 	

     protected void processBookDataCombo1(javax.swing.DefaultComboBoxModel combodata, Hashtable ids) {
    		
    		Vector ledata;
    		
    				//String roleType = " role like 'PO' ";
    			ledata = (Vector) getFilterValues().getBooks();

    			Iterator it = ledata.iterator();
    			int p = 1;
    			combodata.addElement("");
    			while (it.hasNext()) {

    				Book book = (Book) it.next();

    				combodata.insertElementAt(book.getBook_name(), p);
    				ids.put(p, book);
    				
    				p++;
    			
    			}

    		

    	}
     

 	public void processLEDataCombo1(DefaultComboBoxModel legalEntityData2,
 			Hashtable<Integer, LegalEntity> counterPartyID2) {
 		// TODO Auto-generated method stub
 		Vector ledata;
 		
			//String roleType = " role like 'PO' ";
		ledata = (Vector<String>) getFilterValues().getLegalEntitys();

		Iterator it = ledata.iterator();
		int p = 0;
		legalEntityData2.addElement("");
		
		while (it.hasNext()) {

			LegalEntity le = (LegalEntity) it.next();
			
		
				
				legalEntityData2.insertElementAt(le.getName(), p);
				counterPartyID2.put(p, le);
				p++;
				
		
		
		}
 	}
     
     protected void processDomainData(javax.swing.DefaultComboBoxModel combodata, Vector<String> domainData) {
    		
    		Vector ledata;
    		
    				//String roleType = " role like 'PO' ";
    			ledata =domainData;

    			Iterator it = ledata.iterator();
    			int p = 0;
    			combodata.addElement("");
    			while (it.hasNext()) {

    				StartUPData stData = (StartUPData) it.next();

    				combodata.insertElementAt(stData.getName(), p);
    				
    			
    			}

    		

    	}
     
     
}

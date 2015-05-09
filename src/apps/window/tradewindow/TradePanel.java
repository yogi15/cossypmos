package apps.window.tradewindow;

import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.ActionMap;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.jidesoft.combobox.TableExComboBox;
import com.jidesoft.combobox.TableExComboBoxSearchable;
import com.jidesoft.grid.SortableTable;

import constants.TradeConstants;

import dsEventProcessor.TaskEventProcessor;
import dsServices.RemoteTrade;

import productPricing.Pricer;
import productPricing.pricingUtil.CashFlow;
import util.ClassInstantiateUtil;
import util.NumericTextField;
import util.RemoteServiceUtil;
import util.commonUTIL;
import apps.window.operationwindow.jobpanl.FilterValues;
import apps.window.tradewindow.FXPanels.TradeAttributesD;
import apps.window.tradewindow.cashflowpanel.CashFlowPanel;
import apps.window.tradewindow.util.StaticDataCacheUtil;
import apps.window.utilwindow.TableBookModelUtil;
import apps.window.utilwindow.TableLegalEntityModelUtil;
import beans.Attribute;
import beans.Book;
import beans.Flows;
import beans.LegalEntity;
import beans.StartUPData;
import beans.Trade;
import beans.Users;
import beans.WFConfig;

public abstract class TradePanel extends CommonPanel  {
	
	   /**
	 * 
	 */
	Hashtable<String,CashFlowPanel> cashFlowPanel = new Hashtable<String,CashFlowPanel>();
	FilterValues filterValue = null;
	

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
	public abstract ActionMap getHotKeysActionMapper();
	public abstract JPanel getHotKeysPanel();
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
	public void processActionData( javax.swing.DefaultComboBoxModel combodata,String productType,String productSubType,String tradestatus) {
		Vector vector;
		try {
			combodata.removeAllElements();
			String sql = "productType = '"+productType.toUpperCase().trim() +  "'  and productSubType = '"+productSubType.toUpperCase().trim()+   "' and currentstatus = '" +tradestatus +"' and type ='TRADE'";
			//System.out.println(sql);
			vector = (Vector) RemoteServiceUtil.getRemoteTradeService().getAllActionsOnStatus(sql);
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
	public void setSDIPanelInstruction() {
		// TODO Auto-generated method stub
		
	}
	public  void setFilterValue(FilterValues filterValues) {
		this.filterValue = filterValues;
		
	}
		
	protected void processTableData(Hashtable<String,String> hattributes,DefaultTableModel model,TradeAttributesD attributes) {			
    	Vector vector;
		
			attributes.clearllCriteriaModel();
			vector = (Vector) StaticDataCacheUtil.getDomainValues("TradeAttribute");
			Iterator it = vector.iterator();
	    	int i =0;
	    	while(it.hasNext()) {
	    		
	    		StartUPData tradeAttributes = (StartUPData) it.next();
	    		Attribute attru = new Attribute();
	    		int rowCount = attributes.getTableRowCount();
	    		attru.setName(tradeAttributes.getName().toString());
	    	//	attru.setName(tradeAttributes.getName().toString());
	    		if(tradeAttributes.getName().equalsIgnoreCase("Trade Date") 
	    				|| tradeAttributes.getName().equalsIgnoreCase("TradeModifiedDateTime")) {
	    	    	
	    			attru.setValue(commonUTIL.getCurrentDateTime());
	    			
	    			//@yogesh 08/02/2015
                    // we are using DateCellEditor to show Jide calender in TradeDate and TradeModifiedDateTime	               
	    			attributes.addRowEditor(rowCount, "Values");
	    			
	    	    } else {
	    	    	Vector attributeValues = (Vector) StaticDataCacheUtil.getDomainValues(attru.getName());
		    		if(!commonUTIL.isEmpty(attributeValues)) {
			    		 String values [] = attributes.convertVectortoSringArray(attributeValues,tradeAttributes.getName().toString());
			    		attributes.addRowEditor(rowCount, 1, attributes.getJComboxBox(values),"Values");
		    		 	 values = null;
		    		} else {
		    			attributes.addRowEditor(rowCount, 1,attributes.getJTextFieldBox(), "Values");
		    		}
	    	    }
	    		
	    		attributes.addNewRow(attru);
	    		
	    			
	    	}
	    	
	    	attributes.tradeAction = "New";
	    	   /* if(tradeAttributes.getName().equalsIgnoreCase("TradeDate")) {
	    	    	model.insertRow(i, new Object[]{tradeAttributes.getName(),commonUTIL.getCurrentDateTime()});
	    	    	attributes.put(tradeAttributes.getName(),(String) model.getValueAt(i, 1));
	    	    } else {
	    		   model.insertRow(i, new Object[]{tradeAttributes.getName(),""});
	    		   attributes.put(tradeAttributes.getName(),"");
	    	    }
	    		i++;
	    		} */
	    		
		
    }
	
	
	protected TableBookModelUtil getBookModelUtil(Vector<Book> bookData) {
		String cols[] = { "Bookid", "Name "  };
		return new TableBookModelUtil(bookData,cols);
	}
	protected TableLegalEntityModelUtil getTraderModelUtil(Vector<LegalEntity> traderData) {
		String traderCol[] = { "ID", "TraderName "  };
		return new TableLegalEntityModelUtil(traderData,traderCol);
	}
	protected TableLegalEntityModelUtil getCPModelUtil(Vector<LegalEntity> traderData) {
		String col[] = { "Id", "Name " };
		return new TableLegalEntityModelUtil(traderData,col);
	}
	
	protected TableExComboBox getCounterPartyComboBox(Vector<LegalEntity> leData) {
		TableExComboBox counterParty =  new TableExComboBox(
					getCPModelUtil(leData)) {
				@Override
				protected JTable createTable(TableModel model) {
					return new SortableTable(model);
				}
				
				
			};
			counterParty.setEditable(false);
			counterParty.setBorder(null);
			counterParty.setValueColumnIndex(1);
			new TableExComboBoxSearchable(counterParty);
			return counterParty;
	}
	protected TableExComboBox getTraderComboBox(Vector<LegalEntity> leData) {
		TableExComboBox trader =  new TableExComboBox(
					getCPModelUtil(leData)) {
				@Override
				protected JTable createTable(TableModel model) {
					return new SortableTable(model);
				}
				
				
			};
			trader.setEditable(false);
			trader.setBorder(null);
			trader.setValueColumnIndex(1);
			new TableExComboBoxSearchable(trader);
			return trader;
	}
	protected TableExComboBox getBookComboBox(Vector<Book> bookData) {
		TableExComboBox book =  new TableExComboBox(
				getBookModelUtil(bookData)) {
				@Override
				protected JTable createTable(TableModel model) {
					return new SortableTable(model);
				}
				
				
			};
			book.setEditable(false);
			book.setBorder(null);
			book.setValueColumnIndex(1);
			new TableExComboBoxSearchable(book);
			return book;
	}
	
	
	protected  String getAttributeValue( TradeAttributesD attributes) {
		String attributesV  = "";
		Vector<Attribute> attributesData = attributes.getData();
		for(int i=0;i<attributesData.size();i++) {
			Attribute att = attributesData.get(i);
			String value = att.getValue();
			if(value != null ) {
				
				if(value.trim().length() > 0)
			    attributesV = attributesV + att.getName()+ "=" + att.getValue() + ";";
			}
			
		}
		if(attributesV.trim().length() > 0)
			return attributesV.substring(0, attributesV.length()-1);
			return attributesV;
	}
	
	protected Trade saveTrade(Vector<String> messages,Trade trade) {
		Vector mess = null;
		try {
			mess = RemoteServiceUtil.getRemoteTradeService().saveTrade(trade, messages);
			 String statusT = (String) mess.elementAt(0);
           	 
           	 int i = ((Integer) mess.elementAt(1)).intValue();
           	 if(i <= -10) {
           		commonUTIL.showAlertMessage((statusT));
    			return null;
           	 }
           	 
           	 if(i == -4) {
     			commonUTIL.showAlertMessage((statusT));
        			return null;
        		 }
           	 if(i == -3) {
        			commonUTIL.showAlertMessage((statusT));
        			return null;
        		 }
           	 if(i > 0 ) {
           		trade = RemoteServiceUtil.getRemoteTradeService().selectTrade(i);
           		
           	 }
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return trade;
	}
	
	protected void setDataFromTradeObject(int id,String name,TableExComboBox objecttoSet) { 
		Object obj = null;
		try {
		if(name.equalsIgnoreCase(TradeConstants.BOOK)) {
			Book book = new Book();
			book.setBookno(id);
			book = (Book) RemoteServiceUtil.getRemoteReferenceDataService().selectBook(book);
			if(book != null) {
			objecttoSet.setSelectedItem(book.getBook_name());
			objecttoSet.setName(new Integer(id).toString());
			}
			
		} else if(name.equalsIgnoreCase(TradeConstants.COUNTERPARTY) || name.equalsIgnoreCase(TradeConstants.TRADER)) {
			
			
			LegalEntity le = (LegalEntity) RemoteServiceUtil.getRemoteReferenceDataService().selectLE(id);
			if(le != null) {
			objecttoSet.setSelectedItem(le.getName());
			objecttoSet.setName(String.valueOf(le.getId()));
			}
		}
		}catch(RemoteException  e) {
			commonUTIL.displayError("Super Class Trade Panel " , " getDataFromTrade ", e);
			
		}
		
		
	}
	
	protected void setAttribute(String attributesV,TradeAttributesD attributes,Hashtable<String,String> attributeDataValue ) {
		String attributeColumnName [] =    {"Attribute Name ", "Attribute  Value "};
        
	    //    DefaultTableModel attributeModel = new DefaultTableModel(attributeColumnName,0);
	    	
	    	if(attributesV != null && attributesV.length() > 0) {
	    	String atttoken [] = attributesV.trim().split(";"); 
	
    		for(int i =0;i<atttoken.length;i++) {
    			String att = (String) atttoken[i];
    			
    			if(att.contains("=")) {
					String attvalue = att.substring(att.indexOf('=')+1, att.length());
					String attnameName = att.substring(0, att.indexOf('='));
					int atRows = attributes.jTable1.getRowCount();
					for(int t=0;t <atRows; t++) {
						String atName = (String) attributes.jTable1.getModel().getValueAt(t,0);
						if(atName.trim().equalsIgnoreCase(attnameName)) {
							attributes.jTable1.setValueAt(attvalue, t, 1);
							attributeDataValue.put(attnameName,attvalue);
							
							if(atName.trim().equalsIgnoreCase("Trade Date")){								
								attributes.tradeAction = "open";
							}
						} 
					}
    				}
    		}
	    	//	attributes.jTable1.removeAll();
	    	//	attributes.jTable1.setModel(attributeModel);
	    		}
	}
		// TODO Auto-generated method stub
		
	
		protected void setCashFlow(JTable table,Collection cashFlows,String productType) {
			table.removeAll();
			CashFlowPanel cashFlowPanel = makeCashFlowPanel(productType);
			table.setModel(cashFlowPanel.getCashFlows((Vector) cashFlows));
			
			
			
		}
	
		protected CashFlowPanel makeCashFlowPanel(String productType) {
			String productWindowName = "apps.window.tradewindow.cashflowpanel."
					+ productType.toUpperCase() + "CashFlowPanel";
			CashFlowPanel panel = null;
			

			try {
				panel = cashFlowPanel.get(productType);
				if(panel == null) {
				Class class1 = ClassInstantiateUtil.getClass(productWindowName,
						true);
				panel =  (CashFlowPanel) class1.newInstance();
				cashFlowPanel.put(productType, panel);
				
				}
				
			} catch (Exception e) {
				System.out.println(e);
			}

			return panel;
		}
}

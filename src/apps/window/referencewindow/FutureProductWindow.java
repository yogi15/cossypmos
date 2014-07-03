package apps.window.referencewindow;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import beans.Book;
import beans.DateRule;
import beans.Folder;
import beans.FutureContract;
import beans.LegalEntity;
import beans.Product;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;

import util.ReferenceDataCache;
import util.RemoteServiceUtil;
import util.commonUTIL;
import util.common.DateU;

import apps.window.reportwindow.DatePanel;
import apps.window.utilwindow.propertypane.FutureContractPropertyTable;
import apps.window.utilwindow.propertypane.Combox.ContractSelectorComboBox;
import beans.FutureProduct;

import com.jidesoft.dialog.ButtonPanel;
import com.jidesoft.editor.selection.SelectionEvent;
import com.jidesoft.grid.PropertyTable;
import com.jidesoft.swing.JideBoxLayout;
import com.standbysoft.component.date.DateSelectionModel.SelectionMode;

import constants.ProductConstants;

import dsServices.RemoteProduct;
import dsServices.RemoteReferenceData;



public class FutureProductWindow extends JFrame {
	 private JPanel contractStatusBar = new JPanel();
	 Product futureContract = null;
	
	 Vector<FutureContract> data = new Vector<FutureContract>();
	 String col[] = {"ExpirationDate","LastTradeDate","FirstDeliveryDate","LastDeliveryDate","FirstNotificationDate","LastNotificationDate","QuoteName","CUSIP","ISIN"};
	 private final String DISPLAYABLEOBJECT = ProductConstants.FUTURECONTRACT;
	    private JPanel ContractStatusBar = new JPanel();
	 private final int WINDOW_WIDTH = 1200;//900
	    private final int WINDOW_DEPTH = 600;
	    private final int CONTRACT_PANEL_SPLIT_LOCATION = 105;
	    private final int CONTRACT_FUTURE_PANEL_SPLIT_LOCATION = 370;
	    private JPanel splitLeftPanel = new JPanel(new BorderLayout());
	    private JPanel splitRightPanel = new JPanel(new BorderLayout());
	    private JLabel contractLabel = new JLabel("FutureContract");
	    private JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, splitLeftPanel, splitRightPanel);
	    private ContractSelectorComboBox contractSelectorComboBox = null;
	    ContractSelectorComboBoxListener contractSelectorComboBoxActionListener = null;
	    private JPanel oldPropertyTablePanel = null;
		private JPanel _futureContractDetailPropertyTable = null;
		private JPanel _futureUnderlyingPanel = null;
		private JPanel futureContractTablePanel = new JPanel(new BorderLayout());
		private final String CONTRACTPROPERTIESPANELNAME = "Contract Details";
		private JPanel contractPropertiesPanel = new JPanel(new BorderLayout());
		TableModelFutureContractUtil model = null;
		protected JButton loadButton = new JButton("Load...");
		protected JButton newButton = new JButton("New");
		protected JButton deleteButton = new JButton("Delete");
		protected JButton saveButton = new JButton("Save");
		protected JButton saveAsNewButton = new JButton("Save As New");
		protected JButton closeButton = new JButton("Close");
		RemoteProduct remoteProduct = null;
		RemoteReferenceData remoteRef = null;
		FutureProduct futureProduct = null;
		protected JLabel dateLabel = new JLabel("Date");
		protected JTextField dateText = new JTextField("12/06/2014  ");
		protected JButton loadFuture = new JButton("Load Future");
		protected JButton saveFuture = new JButton("Save Futures");
		protected JButton deleteFuture = new JButton("Delete Futures");
		
		private JPanel buttonsPanel = new JPanel();
		private JPanel rightbuttonsPanel = new JPanel(new BorderLayout());
		private JScrollPane scrollPane = new JScrollPane();
		JTable futureContracts = new JTable();
		
		private PropertyTable propertyTable = null;
		
	 private void createLayout() {
		 Container contentPane = getContentPane();
	       setSize(WINDOW_WIDTH, WINDOW_DEPTH);
	       
	       contentPane.setLayout(new BorderLayout());
	       splitLeftPanel.setLayout(new BorderLayout());
	       contentPane.add(splitPane, BorderLayout.CENTER);
	      
	       splitPane.setDividerLocation(CONTRACT_FUTURE_PANEL_SPLIT_LOCATION);
	       splitLeftPanel.add(contractStatusBar, BorderLayout.NORTH);
	       splitLeftPanel.add(contractPropertiesPanel, BorderLayout.CENTER);
	       splitLeftPanel.add(buttonsPanel, BorderLayout.SOUTH);
	     //  rightbuttonsPanel.add(getControlPanel() ,BorderLayout.NORTH );
	      
	       splitRightPanel.add(rightbuttonsPanel, BorderLayout.NORTH);
	       setupFutureContractStatusBar();
	       model = new TableModelFutureContractUtil(data,col);
	       futureContracts.setModel(model);
	      // futureContracts.setSelectionMode(SelectionMode.MULTIPLE_INTERVAL);
	       scrollPane.getViewport().add(futureContracts);
	       splitRightPanel.add(scrollPane, BorderLayout.CENTER);
	     //  splitPane.add(splitLeftPanel);
	      
	      createButtonsPanel();
	      createPropertyTablesForFutureContract();
	 }
	 
	 private void setupFutureContractStatusBar() {
	    	FlowLayout layout = new FlowLayout();
	    	layout.setAlignment(FlowLayout.LEFT);
	    	rightbuttonsPanel.setLayout(layout);
	    	
	    	dateText.setText(commonUTIL.getCurrentDateInString().substring(0, 10));
	    	
	        rightbuttonsPanel.add(dateLabel);
	        rightbuttonsPanel.add(dateText);
	        rightbuttonsPanel.add(loadFuture);
	        rightbuttonsPanel.add(saveFuture);
	        rightbuttonsPanel.add(deleteFuture);
	        saveFuture.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent evt) {
					int row [] = futureContracts.getSelectedRows();
					for(int r=0;r<row.length;r++) {
						 FutureContract fc =    data.get(row[r]);
						 try {
							 if(futureProduct.getID() > 0) {
								 fc.setProductID(futureProduct.getID());
								 fc.setProductType(futureContract.getProductType());
								 fc.setProductshortname(futureContract.getUnderlyingProductType());
								 fc.setParentProductID(futureProduct.getUnderlying_productID());
								int i =  remoteProduct.saveFutureContract(fc);
								if(i == -1) {
									 commonUTIL.display("Contract already exists with name ", fc.getQuoteName());
									// return;
								}
								fc.setID(i);
								commonUTIL.display("Contract save with name " , fc.getQuoteName());
							 } else {
								 commonUTIL.display("Contract save with name " , fc.getQuoteName());
							 }
							
						} catch (RemoteException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
				}
	        });
	        loadFuture.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent evt) {
					data.clear();
					futureContracts.removeAll();
					for(int i=0;i<model.getRowCount();i++) 
						model.delRow(i);
					String exipryRule = futureProduct.getExpiry_Date_Rule();
					String lastTradingRule = futureProduct.getLast_Trading_rule();
					String firstDelRule = futureProduct.getFirst_delivery_Trading_rule();
					String lastDelRule = futureProduct.getLast_delivery_Trading_rule();
					String firstNotiRule = futureProduct.getFirst__notification_rule();
					String lastNotiRule = futureProduct.getLast_notification_rule();
					try {
						DateRule eRule =  remoteRef.getDateRule(exipryRule);
						DateRule firstDRule =  remoteRef.getDateRule(firstDelRule);
						DateRule lastDRule =  remoteRef.getDateRule(lastDelRule);
						DateRule firstNRule =  remoteRef.getDateRule(firstNotiRule);
						DateRule lastNRule =  remoteRef.getDateRule(lastNotiRule);
						DateRule ltTradingRule =  remoteRef.getDateRule(lastTradingRule);
						setFutureContractTables(exipryRule,eRule.generateDates(dateText.getText(), getEndDate(dateText.getText()),futureProduct.getTradecontract_no()));
						setFutureContractTables(firstDelRule,firstDRule.generateDates(dateText.getText(), getEndDate(dateText.getText()),futureProduct.getTradecontract_no()));
						setFutureContractTables(lastDelRule,lastDRule.generateDates(dateText.getText(), getEndDate(dateText.getText()),futureProduct.getTradecontract_no()));
						setFutureContractTables(firstNotiRule,firstNRule.generateDates(dateText.getText(), getEndDate(dateText.getText()),futureProduct.getTradecontract_no()));
						setFutureContractTables(lastNotiRule,lastNRule.generateDates(dateText.getText(), getEndDate(dateText.getText()),futureProduct.getTradecontract_no()));
						setFutureContractTables(lastTradingRule,ltTradingRule.generateDates(dateText.getText(), getEndDate(dateText.getText()),futureProduct.getTradecontract_no()));
						
						for(int i=0;i<futureProduct.getTradecontract_no();i++) {
							FutureContract  fc = new FutureContract();
							fc.setExpriationDate((futureContractDates.get(exipryRule)).get(i));
							fc.setFirstDeliveryDate((futureContractDates.get(exipryRule)).get(i));
							fc.setLastDeliveryDate((futureContractDates.get(exipryRule)).get(i));
							fc.setFirstNotificationDate((futureContractDates.get(exipryRule)).get(i));
							fc.setLastNotificationDate((futureContractDates.get(exipryRule)).get(i));
							fc.setLastTradeDate((futureContractDates.get(lastTradingRule)).get(i));
						
							fc.setQuoteName(getQuoteName(fc.getExpriationDate(),futureContract.getProductname().replace(".", ":")));
							model.addRow(fc);
							
							
						}
								
						
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					 
				}
	        });
	      
	    }
	 
	 private String getEndDate(String startDate) {
		 String enddate = startDate.substring(0,startDate.lastIndexOf("/"));
		 return enddate+"/2100";
	 }
	 private String getQuoteName(String date,String contractName) {
		 String quoteName = "";
		 
		 String fname [] = contractName.split(":");
		 String exch = fname[1];
		 String name = fname[0];
		 String curr = fname[2];
		 DateU date1 = DateU.valueOf(commonUTIL.stringToDate(date, true));
		String mon= date1.getMonthName(date1.getMonth()-1);
		quoteName = "Future."+curr+"."+exch+"."+name+"."+mon+"."+date1.getYear();
		 return quoteName;
		 
	 }
	Hashtable<String,Vector<String>> futureContractDates = new Hashtable<String,Vector<String>>();
	 private void setFutureContractTables(String name,Vector<String> exipryRules) {
		 System.out.println(exipryRules.size());
		 futureContractDates.put(name, exipryRules);
		// System.out.println("00000000000000");
	 }
	 
	 protected ActionListener getContractSelectorComboBoxActionListener(){
	    	if(contractSelectorComboBoxActionListener == null){
	    		contractSelectorComboBoxActionListener =new  ContractSelectorComboBoxListener();
	    	}
	    	return contractSelectorComboBoxActionListener;
	    }
	 class ContractSelectorComboBoxListener implements ActionListener{
	    	public void actionPerformed(java.awt.event.ActionEvent event) {
	    		Object object=event.getSource();
	    		if (!(object instanceof ContractSelectorComboBox)) return;
	    		ContractSelectorComboBox obj = (ContractSelectorComboBox)object;
	    		Product futcon = (Product)obj.getSelectedItem();

	    		if(futcon != null && futcon.getId() > 0){
					
				}
	    		setAndShowFutureContract(futcon);
	    	}

			
	    }
	 private void setAndShowFutureContract(Product futcon) {
			// TODO Auto-generated method stub
		 futureContract = futcon;
		 String productname = futureContract.getProductname();
		 productname = productname.replace(".", ":");
		 String name [] = productname.split(":");
		 propertyTable.setValueAt(name[0], 2, 1);
		 propertyTable.setValueAt(name[1], 1, 1);
		 propertyTable.setValueAt(name[2], 3, 1);
		 propertyTable.setValueAt(name[3], 5, 1);
		
		// RemoteProduct remoteProduct = RemoteServiceUtil.getRemoteProductService();
		 try {
			Vector fproduct = (Vector) remoteProduct.selectUnderlyingFutureProduct(futureContract.getId());
			 futureProduct =(FutureProduct) fproduct.get(0);
			 propertyTable.setValueAt(futureProduct.getQuote_type(), 8, 1);
			 propertyTable.setValueAt(futureProduct.getQuote_decimals(), 9, 1);
			 propertyTable.setValueAt(futureProduct.getContract_size(), 10, 1);
			 propertyTable.setValueAt(futureProduct.getLots(), 11, 1);
			 propertyTable.setValueAt(futureProduct.getTicksize(), 12, 1);
			 propertyTable.setValueAt(futureProduct.getTradecontract_no(), 13, 1);
			 propertyTable.setValueAt(futureProduct.getSettlement_method(), 14, 1);
			 propertyTable.setValueAt(futureProduct.getExpiry_Date_Rule(), 15, 1);
			 propertyTable.setValueAt(futureProduct.getTime_zone(), 16, 1);
			 propertyTable.setValueAt(futureProduct.getTime_minute(), 17, 1);
			 propertyTable.setValueAt(futureProduct.getLast_Trading_rule(), 18, 1);
			 propertyTable.setValueAt(futureProduct.getFirst_delivery_Trading_rule(), 19, 1);
			 propertyTable.setValueAt(futureProduct.getLast_delivery_Trading_rule(), 20, 1);
			 propertyTable.setValueAt(futureProduct.getFirst__notification_rule(), 21, 1);
			 propertyTable.setValueAt(futureProduct.getLast_notification_rule(), 22, 1);
			 
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			
		}
	 private void setupMainComponents() {
	        String TITLE = "Future Contract Definition";
			setTitle(TITLE);
	        setupContractStatusBar();
	     //   setupFutureContractStatusBar();
	      
	    }
	 public FutureProductWindow() {
	        try {
	            jbInit();
	        } catch (Exception e) {
	        //    Log.error(this, e);
	        }
	      //  initDomains();
	    }
	 private void createPropertyTablesForFutureContract() {

			
			
			JPanel propertyTablePanel = getFutureContractPropertyTablePanel();
			if (oldPropertyTablePanel != null) {
				futureContractTablePanel.remove(oldPropertyTablePanel);
			}
			oldPropertyTablePanel = propertyTablePanel;
			contractPropertiesPanel.add(oldPropertyTablePanel, BorderLayout.CENTER);
		}
	 public JPanel getFutureContractPropertyTablePanel() {

			// FutureContractPropertyTable contractPropertyTable = new
			// FutureContractPropertyTable("FUTURES_FX");

			propertyTable = new FutureContractPropertyTable("FUTURES_FX")
					.getFutureProductPropertyTable();
         for(int i=0;i< propertyTable.getRowCount();i++) {
        	 propertyTable.setValueAt("", i, 1);
         }
			JPanel basePanel = new JPanel();
			JScrollPane tableScrollPane = new JScrollPane();
			tableScrollPane.getViewport().add(propertyTable);
			basePanel.setLayout(new BorderLayout());
			basePanel.add(tableScrollPane, BorderLayout.CENTER);

			return basePanel;

		}
	 
	 private void createButtonsPanel() {

			//setButtonDetails();
		 JPanel buttonPanel = new ButtonPanel(SwingConstants.RIGHT);

			buttonPanel.add(buttons2Column(loadButton, newButton));
			buttonPanel.add(buttons2Column(saveAsNewButton, saveButton));
			buttonPanel.add(buttons2Column(deleteButton, closeButton));

			initBottomButtonsActionListeners();

			getRootPane().setDefaultButton(loadButton);
			buttonsPanel.add(buttonPanel, JideBoxLayout.FLEXIBLE);

		}
	 
	 
	 
	 private ButtonPanel buttons2Column(JButton topButton, JButton botButton) {

			ButtonPanel buttonPanel = new ButtonPanel(SwingConstants.TOP);
			buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
			buttonPanel.add(topButton, ButtonPanel.AFFIRMATIVE_BUTTON);
			buttonPanel.add(botButton, ButtonPanel.AFFIRMATIVE_BUTTON);
			return buttonPanel;

		}
	
	 
	 private void initBottomButtonsActionListeners() {

			saveButton.setToolTipText(saveButton.getName());
			saveButton.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent evt) {
					System.out.println("Save");

				}

			});

			closeButton.setToolTipText(closeButton.getName());
			closeButton.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent evt) {
					System.out.println("Close");
				}

			});

			saveAsNewButton.setToolTipText(saveAsNewButton.getName());
			saveAsNewButton.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent evt) {
					//System.out.println("Save As New");
					System.out.println(propertyTable.getValueAt(1, 1) + "++++++");
					System.out.println(propertyTable.getValueAt(2, 1) +"----");
					Product product = new Product();
					product.setId(0);
					product.setName(propertyTable.getValueAt(2, 1).toString());
					LegalEntity exchangeID = ReferenceDataCache.getExchangeLE(propertyTable.getValueAt(1, 1).toString());
					product.setIssuerId(exchangeID.getId());
					product.setIssueCurrency(propertyTable.getValueAt(3, 1).toString());
				//	System.out.println(propertyTable.getValueAt(5, 1).toString());
					product.setProdcutShortName(propertyTable.getValueAt(5, 1).toString());
					
					product.setProductType(ProductConstants.FUTURECONTRACT);
					product.setPositionBased(true);
					product.setProductname(propertyTable.getValueAt(2, 1).toString()+"."+exchangeID.getAlias()+"."+product.getIssueCurrency()+"."+product.getProdcutShortName());
					if(product.getProdcutShortName().equalsIgnoreCase("FX")) {
					    product.setUnderlyingProductType(product.getProdcutShortName()+"/"+propertyTable.getValueAt(7, 1).toString());
				}
					FutureProduct futuresProduct = new FutureProduct();
					
					futuresProduct.setID(0);
					//futuresProduct.setID(Integer.parseInt(propertyTable.getValueAt(1, 1).toString()));
					futuresProduct.setSettlement_currency(propertyTable.getValueAt(2, 1).toString());
					futuresProduct.setUnderlying_productID(0);
					//futuresProduct.setUnderlying_productID(Integer.parseInt(propertyTable.getValueAt(4, 1).toString()));
					//futuresProduct.setCcyPair(propertyTable.getValueAt(6, 1).toString());
					futuresProduct.setQuote_type(propertyTable.getValueAt(8, 1).toString());
					futuresProduct.setQuote_decimals(Double.parseDouble(propertyTable.getValueAt(9, 1).toString()));
					futuresProduct.setContract_size(Integer.parseInt(propertyTable.getValueAt(10, 1).toString()));
					futuresProduct.setLots(Integer.parseInt(propertyTable.getValueAt(11, 1).toString()));
					futuresProduct.setTicksize(Integer.parseInt(propertyTable.getValueAt(12, 1).toString()));
					futuresProduct.setTradecontract_no(Integer.parseInt((propertyTable.getValueAt(13, 1).toString())));
					futuresProduct.setSettlement_method(propertyTable.getValueAt(14, 1).toString());
					futuresProduct.setExpiry_Date_Rule(propertyTable.getValueAt(15, 1).toString());
					
					futuresProduct.setTime_zone(propertyTable.getValueAt(16, 1).toString());
					futuresProduct.setTime_minute(propertyTable.getValueAt(17, 1).toString());
					futuresProduct.setLast_Trading_rule(propertyTable.getValueAt(18, 1).toString());
					futuresProduct.setFirst_delivery_Trading_rule(propertyTable.getValueAt(19, 1).toString());
					futuresProduct.setLast_delivery_Trading_rule(propertyTable.getValueAt(20, 1).toString());
					futuresProduct.setFirst__notification_rule(propertyTable.getValueAt(21, 1).toString());
					futuresProduct.setLast_notification_rule(propertyTable.getValueAt(22, 1).toString());
					futuresProduct.setUnderlyingProduct(product);
					/*futuresProduct.setLastTradingDay(Integer.parseInt((propertyTable.getValueAt(17, 1).toString())));
					futuresProduct.setLastTradingTime(propertyTable.getValueAt(18, 1).toString());
					futuresProduct.setDailySettlementType(propertyTable.getValueAt(19, 1).toString());
					futuresProduct.setFinalSettlementType(propertyTable.getValueAt(20, 1).toString());
					futuresProduct.setProductAttributes(propertyTable.getValueAt(22, 1).toString());*/
					
					// remoteProduct = RemoteServiceUtil.getRemoteProductService();
					
					int id = 0;
					try {
						id = remoteProduct.saveFutureProduct(futuresProduct);
					//	futuresProduct.setID(id);
						contractSelectorComboBox.reloadData(futuresProduct.getUnderlying_productID());
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					if (futuresProduct.getID() >0) {
						
						commonUTIL.showAlertMessage("Future Product Saved");
						
					} else {
						
						commonUTIL.showAlertMessage("There was an error while saving Future Product");
					
					}
					
					propertyTable.getValueAt(1, 1);
				}

			});

			deleteButton.setToolTipText(deleteButton.getName());
			deleteButton.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent evt) {
					System.out.println("delete");
				}

			});

			newButton.setToolTipText(newButton.getName());
			newButton.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent evt) {
					System.out.println("New");
				}

			});

			loadButton.setToolTipText(loadButton.getName());
			loadButton.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent evt) {
					System.out.println("Load");
				}

			});

		}
	 public static void main(String args[]) {
		 FutureProductWindow c1 = new FutureProductWindow();
		 c1.setSize(900, 900);
		 c1.setVisible(true);
	 }
	 protected void jbInit() throws Exception {
	     //   super();
		 remoteProduct = RemoteServiceUtil.getRemoteProductService();
		 remoteRef = RemoteServiceUtil.getRemoteReferenceDataService();
	        createLayout();
	        setupMainComponents();
	    }
	 private void setupContractStatusBar(){
	        contractStatusBar.add(contractLabel);
	        if (contractSelectorComboBox == null){
	        	contractSelectorComboBox = new ContractSelectorComboBox(DISPLAYABLEOBJECT);
	        }
	    	contractSelectorComboBox.addActionListener(getContractSelectorComboBoxActionListener());
	    	contractSelectorComboBox.setPreferredSize(new Dimension(200, contractSelectorComboBox.getPreferredSize().height));
	        contractStatusBar.add(contractSelectorComboBox);
	      //  contractStatusBar.add(futureButtons.get(FutureButtonType.NEW));
	    }

	
	 
	 class TableModelFutureContractUtil extends AbstractTableModel {   
		    
		 final String[] columnNames;  
		    
		 final Vector<FutureContract> data;   
		
		
		        
		 public TableModelFutureContractUtil( Vector<FutureContract> myData,String col [] ) {   
		 	this.columnNames = col;
		this.data = myData;   
		
		}   

		    
		 public int getColumnCount() {   
		     return columnNames.length;   
		 }   
		    
		 public int getRowCount() {   
		     return data.size();   
		 }   
		 public String getColumnName(int col) {   
		     return columnNames[col];   
		 }  
		
		 public Object getValueAt(int row, int col) {   
		     Object value = null;  	 
		 
		     FutureContract fc = (FutureContract) data.get(row);
			 switch (col) {
		     case 0:
		         value = fc.getExpriationDate();
		         break;
		     case 1:
		         value = fc.getLastTradeDate();
		         break;
		     case 2:
		    	
		         value = fc.getFirstDeliveryDate();
		         break;
		     case 3:
		    	
		         value = fc.getLastDeliveryDate();
		         break;
		     case 4:
			    	
		         value = fc.getFirstNotificationDate();
		         break;
		     case 5:
			    	
		         value = fc.getLastNotificationDate();
		         break;
		     case 6:
			    	
		         value = fc.getQuoteName();
		         break;
		     case 7:
			    	
		         value = fc.getCtd();
		         break;
		    
			 }
		     return value;
		 }   
		 
		 
		   
		 
		 
		 public boolean isCellEditable(int row, int col) {   
		 return false;   
		 }   
		 public void setValueAt(Object value, int row, int col) {   
		         
		         if(value instanceof Book) {
		     data.set(row,(FutureContract) value) ;
		     this.fireTableDataChanged();   
		         }
		         
		        
		 }   
		    
		 public void addRow(Object value) {   
		    
			 data.add((FutureContract) value) ;
		 this.fireTableDataChanged();   
		   
		 }   
		    
		 public void delRow(int row) {   
		    
		 data.remove(row);          
		 this.fireTableDataChanged();   
		    
		 }   
		 
		 public void udpateValueAt(Object value, int row, int col) {   
		    
		  
		     data.set(row,(FutureContract) value) ;
		 fireTableCellUpdated(row, col);   
		     System.out.println("New value of data:");   
		    
		}   
	}
}

package apps.window.tradewindow;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;

import productPricing.Pricer;
import util.RemoteServiceUtil;
import util.commonUTIL;
import util.common.DateU;
import apps.window.tradewindow.panelWindow.FeesPanel;
import apps.window.tradewindow.panelWindow.PostingPanel;
import apps.window.tradewindow.panelWindow.SDIPanel;
import apps.window.tradewindow.panelWindow.TaskPanel;
import apps.window.tradewindow.panelWindow.TransferPanel;
import apps.window.utilwindow.propertypane.FutureContractPropertyTable;
import beans.Book;
import beans.FutureContract;
import beans.LegalEntity;
import beans.Trade;
import beans.Users;

import com.jidesoft.combobox.DateSpinnerComboBox;
import com.jidesoft.grid.PropertyTable;
import com.jidesoft.swing.AutoCompletionComboBox;

import constants.CommonConstants;
import constants.FuturesConstants;
import constants.ProductConstants;
import dsEventProcessor.TaskEventProcessor;
import dsServices.RemoteAccount;
import dsServices.RemoteBOProcess;
import dsServices.RemoteProduct;
import dsServices.RemoteReferenceData;
import dsServices.RemoteTask;
import dsServices.RemoteTrade;


//VS4E -- DO NOT REMOVE THIS LINE!
public class FUTURETradePanel extends  TradePanel  {

	private static final long serialVersionUID = 1L;
	private JLabel jLabel0;
	private JLabel jLabel2;
	private JLabel jLabel3;
	private JLabel jLabel1;
	private JLabel jLabel4;
	private JLabel jLabel5;
	private JPanel jPanel0;
	private JPanel jPanel1;
	private AutoCompletionComboBox jcp;
	private AutoCompletionComboBox book;
	private AutoCompletionComboBox ttradername;
	private JTextField tradestatus;
	private AutoCompletionComboBox caction;
	private JTextField tradeid;
	private PropertyTable propertyTable = null;
	private PropertyTable attributePropertyTable = null;
	FutureContract futureContract = null;
	private JTextField tuse;
	 Users user = null;
	 private JLabel jLabel6;
 	 String productType = ProductConstants.FUTURECONTRACT;
	javax.swing.DefaultComboBoxModel bookCombo = new javax.swing.DefaultComboBoxModel();
	 Hashtable bookData = new Hashtable();
	 javax.swing.DefaultComboBoxModel cpCombo = new javax.swing.DefaultComboBoxModel();
	 Hashtable cpData = new Hashtable();
	 javax.swing.DefaultComboBoxModel traderCombo = new javax.swing.DefaultComboBoxModel();
	 Hashtable trader = new Hashtable();
	 javax.swing.DefaultComboBoxModel actionstatus = new javax.swing.DefaultComboBoxModel();
	// DateSpinnerComboBox datec= new com.jidesoft.combobox.DateSpinnerComboBox();
	 TaskPanel taskPanel = null; 
	 PostingPanel postingPanel = null;
	 TransferPanel transferPanel = null;
	 SDIPanel sdiPanel = null;
	 FeesPanel feesPanel = null;
	 Trade trade = null;
	 RemoteReferenceData referenceData; 	
 	 RemoteTrade rtradeservices;
 	 RemoteBOProcess remoteBO;
	 RemoteTask remoteTask;
	 RemoteProduct remoteProduct;
	 RemoteAccount remoteAccount;
	 public DateSpinnerComboBox tradeDate;
	 private JPanel jPanel3;
	 JLabel jLabel7;
	public FUTURETradePanel() {
		init();
		initComponents();
		createPropertyTablesForFutureContractTrade();
		createPropertyTablesForFutureContractTradeAttributes();
	}

	private void initComponents() {
		setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
		setLayout(new BorderLayout());
		add(getJPanel0(), BorderLayout.NORTH);
		add(getJPanel1(), BorderLayout.WEST);
		add(getJPanel3(), BorderLayout.CENTER);
		setSize(952, 501);
	}
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jPanel3 = new JPanel();
			jPanel3.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			jPanel3.setLayout(new BorderLayout());
		}
		return jPanel3;
	}

	private JTextField getJTextField0() {
		if (tradeid == null) {
			tradeid = new JTextField();
			tradeid.setText("0");
			//jTextField0.setText();
		}tradeid.addKeyListener(new KeyAdapter() {

            
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                 try {	
                   String number = tradeid.getText();
                   int tradeId = Integer.parseInt(number); 
                   trade = (Trade) rtradeservices.selectTrade(tradeId);
                   
                   if(trade != null) {
                	   if(! trade.getProductType().equalsIgnoreCase(productType)) {
                     	  commonUTIL.showAlertMessage("This Trade Will not open in current Window");
                       }
                	   setTrade(trade);
					openTrade(trade);
				//	productWindowpanel.openTrade(trade);
					//if(e.getClickCount() == 2) 
					 getTradeTask(taskPanel);
					 getTradeSDI(sdiPanel);
                   } else {
                	   commonUTIL.showAlertMessage("Trade does not Exists with id " );
                   }
                   
                 } catch(NumberFormatException n) {
                	 tradeid.setText("");
                	 commonUTIL.showAlertMessage("Enter Number only ");
                	 
                 } catch(RemoteException r) {
                	 commonUTIL.displayError("FutureTradePanel", "getJTextField0", r);
                 }
                }
            }
        });
		return tradeid;
	}

	private AutoCompletionComboBox getJComboBox4() {
		if (caction == null) {
			caction = new AutoCompletionComboBox();
			actionstatus.insertElementAt("NEW", 0);
			caction.setModel(actionstatus);
		}
		return caction;
	}

	private JTextField getJComboBox3() {
		if (tradestatus == null) {
			tradestatus = new JTextField();
			tradestatus.setText("NONE");
		//	jComboBox3.setModel(new DefaultComboBoxModel(new Object[] { "item0", "item1", "item2", "item3" }));
		}
		return tradestatus;
	}
	private JTextField getJTextField1() {
		if (tuse == null) {
		
			tuse = new JTextField();
			tuse.setBackground(Color.white);
			tuse.setEditable(false);
			//jTextField1.setText("jTextField1");
		}
		return tuse;
	}
	private AutoCompletionComboBox getJComboBox2() {
		if (ttradername == null) {
			ttradername = new AutoCompletionComboBox();
			ttradername.setModel(traderCombo);
		}
		return ttradername;
	}

	private AutoCompletionComboBox getJComboBox1() {
		if (book == null) {
			book = new AutoCompletionComboBox();
			book.setModel(bookCombo);
		}
		return book;
	}

	private AutoCompletionComboBox getJComboBox0() {
		if (jcp == null) {
			jcp = new AutoCompletionComboBox(cpCombo);
			jcp.setStrict(false);
			//jcp.setModel(cpCombo);
		}
		return jcp;
	}
	private JPanel oldPropertyTablePanel = null;
	private JPanel oldPropertyAttributesTablePanel = null;
	/**
	 * @return the propertyTable
	 */
	public PropertyTable getPropertyTable() {
		return propertyTable;
	}

	/**
	 * @param propertyTable the propertyTable to set
	 */
	public void setPropertyTable(PropertyTable propertyTable) {
		this.propertyTable = propertyTable;
	}

	public JPanel getOldPropertyTablePanel() {
		if (oldPropertyTablePanel == null) {
			oldPropertyTablePanel = new JPanel();
			oldPropertyTablePanel.setLayout(new BorderLayout());
			oldPropertyTablePanel.add(getJPanel0(), BorderLayout.NORTH);
		}
		return oldPropertyTablePanel;
	}
	public JPanel getOldPropertyAttributesTablePanel() {
		if (oldPropertyAttributesTablePanel == null) {
			oldPropertyAttributesTablePanel = new JPanel();
			oldPropertyAttributesTablePanel.setLayout(new BorderLayout());
			oldPropertyAttributesTablePanel.add(getJPanel0(), BorderLayout.NORTH);
		}
		return oldPropertyAttributesTablePanel;
	}

	/**
	 * @param oldPropertyTablePanel the oldPropertyTablePanel to set
	 */
	public void setOldPropertyTablePanel(JPanel oldPropertyTablePanel) {
		this.oldPropertyTablePanel = oldPropertyTablePanel;
	}

	/**
	 * @return the futureContractTablePanel
	 */
	public JPanel getFutureContractTablePanel() {
		return futureContractTablePanel;
	}

	/**
	 * @param futureContractTablePanel the futureContractTablePanel to set
	 */
	public void setFutureContractTablePanel(JPanel futureContractTablePanel) {
		this.futureContractTablePanel = futureContractTablePanel;
	}

	/**
	 * @return the contractPropertiesPanel
	 */
	public JPanel getContractPropertiesPanel() {
		return contractPropertiesPanel;
	}

	/**
	 * @param contractPropertiesPanel the contractPropertiesPanel to set
	 */
	public void setContractPropertiesPanel(JPanel contractPropertiesPanel) {
		this.contractPropertiesPanel = contractPropertiesPanel;
	}
	private JPanel futureContractTablePanel = new JPanel(new BorderLayout());
	private JPanel futureContractAttributesTablePanel = new JPanel(new BorderLayout());
	private JPanel contractPropertiesPanel = new JPanel(new BorderLayout());
	private JPanel contractPropertiesAttributesPanel = new JPanel(new BorderLayout());
	private static final String PREFERRED_LOOK_AND_FEEL = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(new BorderLayout());
			jPanel1.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			jPanel1.setPreferredSize(new Dimension(500, 500));
			//jPanel2.setLayout(new GroupLayout());
		}
		return jPanel1;
	}

	private void createPropertyTablesForFutureContractTrade() {

		JPanel propertyTablePanel = getFutureContractTradePropertyTablePanel();
		if (oldPropertyTablePanel != null) {
			futureContractTablePanel.remove(oldPropertyTablePanel);
			jPanel1.remove(propertyTablePanel);
		}
		//oldPropertyTablePanel = propertyTablePanel;
	//	contractPropertiesPanel.add(oldPropertyTablePanel, BorderLayout.CENTER);
		jPanel1.add(propertyTablePanel,BorderLayout.CENTER);
		
	}
	private void createPropertyTablesForFutureContractTradeAttributes() {

		JPanel propertyTablePanel = getFutureContractTradeAttributePropertyTablePanel();
		if (oldPropertyAttributesTablePanel != null) {
			futureContractAttributesTablePanel.remove(oldPropertyAttributesTablePanel);
		}
	//	oldPropertyAttributesTablePanel = propertyTablePanel;
		//contractPropertiesAttributesPanel.add(oldPropertyAttributesTablePanel, BorderLayout.CENTER);
		jPanel3.add(propertyTablePanel,BorderLayout.CENTER);
	}
	public JPanel getFutureContractTradePropertyTablePanel() {

		// FutureContractPropertyTable contractPropertyTable = new
		// FutureContractPropertyTable("FUTURES_FX");

		propertyTable = new FutureContractPropertyTable(FuturesConstants.FUTURES_FX_TRADE).getFutureProductPropertyTable();
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
	public JPanel getFutureContractTradeAttributePropertyTablePanel() {

		// FutureContractPropertyTable contractPropertyTable = new
		// FutureContractPropertyTable("FUTURES_FX");

		attributePropertyTable = new FutureContractPropertyTable(FuturesConstants.FUTURES_FX_TRADE_ATTRIBUTE).getFutureProductPropertyTable();
		  for(int i=0;i< attributePropertyTable.getRowCount();i++) {
			  attributePropertyTable.setValueAt("", i, 1);
	         }
		JPanel basePanel = new JPanel();
		JScrollPane tableScrollPane = new JScrollPane();
		tableScrollPane.getViewport().add(attributePropertyTable);
		basePanel.setLayout(new BorderLayout());
		basePanel.add(tableScrollPane, BorderLayout.CENTER);

		return basePanel;

	}
	private JPanel getJPanel0() {
		if (jPanel0 == null) {
			jPanel0 = new JPanel();
			jPanel0.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			jPanel0.setPreferredSize(new Dimension(100, 100));
			jPanel0.setLayout(new GroupLayout());
			jPanel0.add(getJLabel2(), new Constraints(new Leading(5, 59, 10, 10), new Leading(59, 12, 12)));
			jPanel0.add(getJLabel0(), new Constraints(new Leading(5, 73, 10, 10), new Leading(22, 12, 12)));
			jPanel0.add(getJComboBox0(), new Constraints(new Leading(80, 157, 10, 10), new Leading(18, 26, 12, 12)));
			jPanel0.add(getJComboBox1(), new Constraints(new Leading(80, 157, 12, 12), new Leading(56, 26, 12, 12)));
			jPanel0.add(getJLabel1(), new Constraints(new Leading(252, 72, 10, 10), new Leading(62, 12, 12)));
			jPanel0.add(getJLabel3(), new Constraints(new Leading(252, 60, 10, 10), new Leading(24, 12, 12)));
			jPanel0.add(getJTextField0(), new Constraints(new Leading(313, 155, 10, 10), new Leading(56, 23, 12, 12)));
			jPanel0.add(getJComboBox2(), new Constraints(new Leading(312, 157, 12, 12), new Leading(18, 26, 12, 12)));
			jPanel0.add(getJLabel4(), new Constraints(new Leading(481, 63, 12, 12), new Leading(24, 12, 12)));
			jPanel0.add(getJLabel5(), new Constraints(new Leading(480, 31, 12, 12), new Leading(59, 12, 12)));
			jPanel0.add(getJComboBox3(), new Constraints(new Leading(524, 157, 10, 10), new Leading(18, 26, 12, 12)));
			jPanel0.add(getJComboBox4(), new Constraints(new Leading(523, 157, 12, 12), new Leading(53, 26, 12, 12)));
			jPanel0.add(getJLabel6(), new Constraints(new Leading(693, 50, 12, 12), new Leading(22, 12, 12)));
			jPanel0.add(getJLabel7(), new Constraints(new Leading(692, 36, 12, 12), new Leading(59, 12, 12)));
			jPanel0.add(getJTextField1(), new Constraints(new Leading(737, 91, 10, 10), new Leading(18, 23, 12, 12)));
			jPanel0.add(getJTextField2(), new Constraints(new Leading(737, 142, 10, 10), new Leading(54, 12, 12)));
		}
		return jPanel0;
	}

	private DateSpinnerComboBox getJTextField2() {
		if (tradeDate == null) {
			tradeDate = new  com.jidesoft.combobox.DateSpinnerComboBox();
			tradeDate.setTimeDisplayed(true);
			SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
			tradeDate.setFormat(dateFormat);
			tradeDate.setDate(commonUTIL.getCurrentTimeDate());
		}
		return tradeDate;
	}

	private JLabel getJLabel7() {
		
		if (jLabel7 == null) {
			jLabel7 = new JLabel();
			jLabel7.setText("TradeDate");
		}
		return jLabel7;
	}
	private JLabel getJLabel6() {
		if (jLabel6 == null) {
			jLabel6 = new JLabel();
			jLabel6.setText("User");
		}
		return jLabel6;
	}
	private JLabel getJLabel5() {
		if (jLabel5 == null) {
			jLabel5 = new JLabel();
			jLabel5.setText("Action");
		}
		return jLabel5;
	}

	private JLabel getJLabel4() {
		if (jLabel4 == null) {
			jLabel4 = new JLabel();
			jLabel4.setText("Status");
		}
		return jLabel4;
	}

	private JLabel getJLabel1() {
		if (jLabel1 == null) {
			jLabel1 = new JLabel();
			jLabel1.setText("Trade ID");
		}
		return jLabel1;
	}

	private JLabel getJLabel3() {
		if (jLabel3 == null) {
			jLabel3 = new JLabel();
			jLabel3.setText("Trader");
		}
		return jLabel3;
	}

	private JLabel getJLabel2() {
		if (jLabel2 == null) {
			jLabel2 = new JLabel();
			jLabel2.setText("Book");
		}
		return jLabel2;
	}
	
	private JLabel getJLabel0() {
		if (jLabel0 == null) {
			jLabel0 = new JLabel();
			jLabel0.setText("CounterParty");
		}
		return jLabel0;
	}

	@Override
	public void setTaskPanel(BackOfficePanel panel) {
		// TODO Auto-generated method stub
		taskPanel = (TaskPanel) panel;
	}

	@Override
	public void setSDIPanel(BackOfficePanel panel) {
		// TODO Auto-generated method stub
		sdiPanel = (SDIPanel) panel;
	}

	@Override
	public void setFEESPanel(BackOfficePanel panel) {
		// TODO Auto-generated method stub
		feesPanel = (FeesPanel) panel;
	}

	@Override
	public void setLimitPanel(BackOfficePanel panel) {
		// TODO Auto-generated method stub
		
	}

	 @Override
     public void setTradeTransfers(BackOfficePanel panel) {
            // TODO Auto-generated method stub
            transferPanel = (TransferPanel) panel;
     }
	 
	@Override
	public void setTradePostings(BackOfficePanel panel) {
		// TODO Auto-generated method stub
		postingPanel = (PostingPanel)panel;
	}

	@Override
	public void setTrade(Trade trade) {
		// TODO Auto-generated method stub
		 this.trade = trade;
	}

	@Override
	public Trade getTrade() {
		// TODO Auto-generated method stub
		return trade;
	}
	public void getTradeTransfers(BackOfficePanel panel) {
		try {
			if(transferPanel != null) {
			transferPanel.setTrade(trade);
			panel.fillJTabel((Vector)remoteBO.queryWhere("Transfer", "tradeId = " + trade.getId()));
		}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void getTradePostings(PostingPanel postingPanel) {
		// TODO Auto-generated method stub
		try {
			String sql = "tradeid = " + trade.getId()
					+ " and linkTo = 0 order by type,acceventtype  desc ";
			postingPanel.fillJTabel((Vector) remoteAccount
					.getPostingonWhereClause(sql));
			postingPanel.setTrade(trade);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	@Override
	public void saveASNew(Trade trade) {
		// TODO Auto-generated method stub
		tradeid.setText(new Integer(trade.getId()).toString());
		tradestatus.setText(trade.getStatus());
		actionstatus.removeAllElements();
		 processActionData(actionstatus,productType.toUpperCase(),trade.getTradedesc1(),tradestatus.getText(),rtradeservices);
			this.trade = trade;
			setTrade(trade);
			getTradeTransfers(transferPanel);
			getTradePostings(postingPanel);
			getTradeTask(taskPanel);
			getTradeSDI(sdiPanel);
		
	}
	private Users getUser(int i) {
		 try {	    	 
			Users user = (Users) referenceData.selectUser(i);			
			if (user.getUser_name() == null || user.getUser_name().equals(CommonConstants.BLANKSTRING) ) {				
				commonUTIL.showAlertMessage("User not found");			
			}			
			setUser(user);			
			return getUser();			
		 } catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
			
	}
	 public void getTradeSDI(BackOfficePanel panel) {
			try {
				
				sdiPanel.setTrade(trade);
				panel.fillJTabel((Vector) rtradeservices.getSDisOnTrade(trade));
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	public void getTradeTask(BackOfficePanel panel) {
		try {
			System.out.println(panel);
		
			panel.fillJTabel((Vector)remoteTask.selectTaskWhere("tradeId = " + trade.getId()));
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public Users getUser() {
		return user;
	}
	@Override
	public void setUser(Users user) {
		// TODO Auto-generated method stub
		this.user = user;
    	tuse.setText(user.getUser_name());
	}

	@Override
	public String getAction() {
		// TODO Auto-generated method stub
		String action = null;
		if(caction.getSelectedIndex() == -1) {
			return null;
	}
	if(caction.getSelectedIndex() >= 0) {
			action = caction.getSelectedItem().toString();
	}
		return action;
	}

	@Override
	public void setTradeApplication(TradeApplication app) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void processTask(TaskEventProcessor taskEvent, Users WindowUser) {
		// TODO Auto-generated method stub
		System.out.println(productType);
		System.out.println("Task" + taskEvent.getUserID());
		System.out.println(" WindowUser " + WindowUser.getId());
	}

	@Override
	public void setLimitBreachMarkOnAction(int i) {
		// TODO Auto-generated method stub
		if( i <= 0) {
			int red = 240;
			int green = 31;
			int blue = 122;
			Color colr = new Color(red,green,blue);
			tradestatus.setBackground(colr);
			//tradeid.setBackground(colr);
	} else { 
		tradestatus.setBackground(new Color(128, 255, 255));
		tradestatus.setEditable(false);
	}
		
	}

	
	private void clearPropertyPane() {
		for(int i=0;i<propertyTable.getRowCount();i++) {
			propertyTable.setValueAt("", i, 1);
		}for(int i=0;i<attributePropertyTable.getRowCount();i++) {
			attributePropertyTable.setValueAt("", i, 1);
		}
		
	}
	@Override
	public void buildTrade(Trade trade, String actionType) {
		// TODO Auto-generated method stub
		if(actionType.equalsIgnoreCase("NEW")) {
			tradeid.setText("0");
			tradestatus.setEditable(true);
			tradestatus.setText("NONE");
			tradestatus.setEditable(false);
			actionstatus.removeAllElements();
			caction.removeAll();
			actionstatus.insertElementAt("NEW", 0);
			
			
			caction.setModel(actionstatus);
			caction.setSelectedIndex(0);
			 trade.setProductType(productType);
			 book.setSelectedIndex(-1);
				jcp.setSelectedIndex(-1);
				ttradername.setSelectedIndex(-1);
				tradeDate.setDate(commonUTIL.getCurrentTimeDate());
				clearPropertyPane();
				
				
		}else {
			
			
			validateTradeDetails(trade,actionType);
			
			trade.setId(new Integer(tradeid.getText()).intValue());
		}
		
	}
private void validateTradeDetails(Trade trade,String actionType) {
		
		
		
		if (jcp.getSelectedIndex() == -1) {
			
			commonUTIL.showAlertMessage("Please select Legal Entity");
			return;
		
		}
		LegalEntity cp = (LegalEntity) cpData.get(jcp.getSelectedIndex());
		int cpId = cp.getId();
		trade.setCpID(cpId);
		
		if (ttradername.getSelectedIndex() == -1) {
			
			commonUTIL.showAlertMessage("Please select Trader");
			return;
		
		}		
        trade.setTraderID((((LegalEntity) trader.get(ttradername.getSelectedIndex())).getId()));
        
       
		if (book.getSelectedIndex() == -1) {
			
			commonUTIL.showAlertMessage("Please select Book");
			return;
		}
        trade.setBookId(((Book) bookData.get(book.getSelectedIndex())).getBookno());
        
        String status = tradestatus.getText();
        if (status.equals(CommonConstants.BLANKSTRING)) {
        	
        	commonUTIL.showAlertMessage("Please set workflow");
			return;
        	
        }
        trade.setStatus(status);
       
        String tradeDate1 = commonUTIL.convertDateTimeTOString(tradeDate.getDate());
        if (tradeDate1.equals(CommonConstants.BLANKSTRING)) {
        	
        	commonUTIL.showAlertMessage("Please enter Trade Date");
			return;
        	
        }
        trade.setTradeDate(tradeDate1);
       
        if(!actionType.equalsIgnoreCase("SAVEASNEW")) {
        			if(!(caction.getSelectedIndex() == -1)) {
        				trade.setAction(caction.getSelectedItem().toString());
        			}
        			 else {
        				commonUTIL.showAlertMessage("Action Not Selected ");
			return;
        			 }
        } else {
        trade.setAction("NEW");
        }
        trade.setProductType(productType);
        String productName = (String) propertyTable.getValueAt(6, 1);
        if(commonUTIL.isEmpty(productName)) {
        	commonUTIL.showAlertMessage("Select FutureContract ");
        	return;
        }
        trade.setProductId(getProductId(productName));
        String buysell = (String)propertyTable.getValueAt(10, 1);
     
        if(commonUTIL.isEmpty(buysell)) {
        	commonUTIL.showAlertMessage("Select BUY/SELL ");
        	return;
        }
        trade.setCurrency((String) propertyTable.getValueAt(2, 1)) ;
        trade.setUserID(getUser().getId());
        trade.setType(buysell);
        double price = Double.parseDouble(propertyTable.getValueAt(11, 1).toString());
        trade.setPrice(price);
        double quanity = Double.parseDouble(propertyTable.getValueAt(12, 1).toString()); // lots 
        trade.setQuantity(quanity);
        double nominal = Double.parseDouble(propertyTable.getValueAt(13, 1).toString()); // contract size 
        trade.setNominal(nominal);
        trade.setTradeAmount(Double.parseDouble(propertyTable.getValueAt(14, 1).toString())) ;// price * lots * size 
        trade.setTradedesc(futureContract.getQuoteName()); //
        trade.setTradedesc1("FX"); //
        DateU date = DateU.valueOf(commonUTIL.stringToDate("12/12/2012", true));
       
        trade.setDelivertyDate(futureContract.getLastTradeDate());
     //   trade.setProductType("FutureContract");
        
        
        
}

    private String getAttributes() {
    	int rowcount = attributePropertyTable.getRowCount();
    	int rr = 15;
    	String attribute = "";
    	for(int r=0;r<rowcount;r++) {
    		String columnName = (String) attributePropertyTable.getValueAt(r, 0);
    		String value = (String) attributePropertyTable.getValueAt(r, 1);
    		if(!commonUTIL.isEmpty(value))
    		    attribute= attribute + columnName +"="+value+";";
    		
    	}
    	return attribute;
    }
    
     private int getProductId(String name) {
    	 if(!commonUTIL.isEmpty(name)) {
    		 try {
				futureContract =  remoteProduct.getFutureContractQuoteName(name);
				if(futureContract == null)
					return 0;
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	 }
    	return  futureContract.getParentProductID();
     }
	 public int getBookKey(Hashtable t , int id) {
			int i = 0;
			Set set = t.entrySet();
		    Iterator it = set.iterator();
		    while (it.hasNext()) {
		      Map.Entry entry = (Map.Entry) it.next();
		     Book book =  ((Book) entry.getValue());
		     if(id == book.getBookno())
		    	 i = ((Integer) entry.getKey()).intValue();
		    }
	       return i;	    

		
			
		}
		public int getLEKey(Hashtable t , int id) {
			int i = 0;
			Set set = t.entrySet();
		    Iterator it = set.iterator();
		    while (it.hasNext()) {
		      Map.Entry entry = (Map.Entry) it.next();
		      LegalEntity le =  ((LegalEntity) entry.getValue());
		     if(id == le.getId())
		    	 i = ((Integer) entry.getKey()).intValue();
		    }
	       return i;	    

		
			
		}
	@Override
	public void openTrade(Trade trade) {
		this.trade = trade;
		tradeid.setText(new Integer(trade.getId()).toString());
		  tradestatus.setText(trade.getStatus());
		//  tradeDate.setDate(commonUTIL.);
		  actionstatus.removeAllElements();
		  caction.removeAllItems();
		  processActionData(actionstatus,trade.getProductType().toUpperCase(),trade.getTradedesc1(),tradestatus.getText(),rtradeservices);
		  caction.setModel(actionstatus);
	        book.setSelectedIndex(getBookKey(bookData,trade.getBookId()));
	        jcp.setSelectedIndex(getLEKey(cpData,trade.getCpID()));
	        ttradername.setSelectedIndex(getLEKey(trader,trade.getTraderID()));	        
	        caction.setSelectedItem(trade.getAction());
	        tuse.setText(getUser(trade.getUserID()).getUser_name());
	        clearPropertyPane();
	        String productquoteName = trade.getTradedesc();
	        productquoteName  = productquoteName.replace(".", ":");
	        String name [] = productquoteName.split(":");
	        productquoteName  = productquoteName.replace(":", ".");
	        propertyTable.setValueAt(name[2], 1, 1);
	        propertyTable.setValueAt(name[1], 2, 1);
	        propertyTable.setValueAt(name[3], 4, 1);
	        String contractDate = productquoteName.substring(productquoteName.lastIndexOf(".")-3, productquoteName.length());
	        propertyTable.setValueAt(contractDate, 5, 1);
	        propertyTable.setValueAt(productquoteName, 6, 1);
	        propertyTable.setValueAt(trade.getType(), 10, 1);
	        propertyTable.setValueAt(trade.getPrice(), 11, 1);
	        propertyTable.setValueAt(trade.getQuantity(), 12, 1); // lots 
	        propertyTable.setValueAt(trade.getNominal(), 13, 1); // size 
	        propertyTable.setValueAt(trade.getTradeAmount(), 14, 1);  // lots * price * size 
	        tradeDate.setDate(commonUTIL.getCurrentTimeDate(trade.getTradeDate()));
	        if(!commonUTIL.isEmpty(trade.getAttributes()))
                if(trade.getAttributes().contains(";")) {
               setAttributes(trade.getAttributes().split(";"));
                } else {
               
                       int rowcount = attributePropertyTable.getRowCount();
                       for(int i=0;i<rowcount;i++) {
                             String columnName = (String) attributePropertyTable.getValueAt(i, 0);
                             String attribute = trade.getAttributes().substring(0, trade.getAttributes().indexOf("="));
                             String value =trade.getAttributes().substring(trade.getAttributes().indexOf("=")+1, trade.getAttributes().length());
                             if(attribute.equalsIgnoreCase(columnName)) {
                                    attributePropertyTable.setValueAt(value,i, 1);
                             }
                       }
                }
	        
	        setTrade(trade);
	        
	        
		
	}
	
	private void setAttributes(String att []) {
			
			int rowcount = attributePropertyTable.getRowCount();
	    	int rr = 15;
	    	
	    	for(int r=0;r<rowcount;r++) {
	    		String columnName = (String) attributePropertyTable.getValueAt(r, 0);
	    		for(int i=0;i<att.length;i++) {
	    			String attribute = att[i].substring(0, att[i].indexOf("="));
	    			String value = att[i].substring(att[i].indexOf("=")+1, att[i].length());
	    			if(attribute.equalsIgnoreCase(columnName)) {
	    				attributePropertyTable.setValueAt(value,r, 1);
	    			}
	    			
	    		}
	    	}
	}

	@Override
	public void setPanelValue(CommonPanel tradeValue) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setTradePanelValue(CommonPanel tradeValue) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection getCashFlows() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pricer getPricer() {
		// TODO Auto-generated method stub
		return null;
	}
	public void processDataCombo1( javax.swing.DefaultComboBoxModel combodata,Hashtable ids,String name,String leRole) {
		Vector vector;
		try {
			if(name.equalsIgnoreCase("Book"))
			vector = (Vector) referenceData.selectALLBooks();
			else 
				vector = (Vector) referenceData.selectLEonWhereClause(" role like '%"+leRole + "%'");
			Iterator it = vector.iterator();
	    	int i =0;
	    	while(it.hasNext()) {
	    		
	    	Object obj =	 it.next();
	    	
    		
	    	if(name.equalsIgnoreCase("Book"))	
    		combodata.insertElementAt(((Book)obj).getBook_name(), i);
	    	else 
	    		combodata.insertElementAt(((LegalEntity)obj).getName(), i);
    		ids.put(i, obj);
    		i++;
    	}	
		}catch (RemoteException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    	
    	
    }
	private void init() {
		this.productType = ProductConstants.FUTURECONTRACT;
		  
		
		   		 referenceData = RemoteServiceUtil.getRemoteReferenceDataService();
		   	rtradeservices = (RemoteTrade)	RemoteServiceUtil.getRemoteTradeService();
		   	remoteTask = (RemoteTask) 	RemoteServiceUtil.getRemoteTaskService();
		   	remoteBO = (RemoteBOProcess) RemoteServiceUtil.getRemoteBOProcessService();
		   	remoteProduct = (RemoteProduct) RemoteServiceUtil.getRemoteProductService();
		   	remoteAccount = (RemoteAccount) RemoteServiceUtil.getRemoteAccountService();
		   	Collection<Book> booksVec = null;
	    	Collection<LegalEntity> cpVec = null;
	    	Collection<LegalEntity> traderVec = null;
	    	Collection<Book> statusVec = null;
	    	try {
	    		cpVec  = referenceData.selectLEonWhereClause(" role like 'CounterParty'");
	    		traderVec  = referenceData.selectLEonWhereClause(" role like 'Trader'");
	    		booksVec = referenceData.selectALLBooks();
	    		  processDataCombo1(bookCombo,bookData,"Book",null); 
	  			
	  			processDataCombo1(cpCombo,cpData,"cp","CounterParty"); 
	  			processDataCombo1(traderCombo, trader, "cp", "Trader");
	 			
	          //  AutoCompletionComboBox traderComBoBox = new AutoCompletionComboBox(traderCombo);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
	}


	

}

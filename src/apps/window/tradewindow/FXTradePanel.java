	package apps.window.tradewindow;
	
	import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;

import productPricing.Pricer;
import util.ReferenceDataCache;
import util.commonUTIL;
import util.common.DateU;
import apps.window.tradewindow.FXPanels.BasicData;
import apps.window.tradewindow.FXPanels.FWDOptionPanel;
import apps.window.tradewindow.FXPanels.FunctionalityD;
import apps.window.tradewindow.FXPanels.JTableButtonRenderer;
import apps.window.tradewindow.FXPanels.Swap;
import apps.window.tradewindow.FXPanels.TakeUPWindow;
import apps.window.tradewindow.FXPanels.TradeAttributesD;
import apps.window.tradewindow.FXPanels.outRight;
import apps.window.tradewindow.FXPanels.rollPanel;
import apps.window.tradewindow.panelWindow.FeesPanel;
import apps.window.tradewindow.panelWindow.SDIPanel;
import apps.window.tradewindow.panelWindow.TaskPanel;
import apps.window.tradewindow.panelWindow.TransferPanel;
import apps.window.tradewindow.util.FXSplitUtil;
import apps.window.utilwindow.JDialogTable;
import beans.Attribute;
import beans.B2BConfig;
import beans.Book;
import beans.CurrencySplitConfig;
import beans.Favorities;
import beans.LegalEntity;
import beans.Product;
import beans.StartUPData;
import beans.Trade;
import beans.Users;

import com.jidesoft.combobox.TableExComboBox;
import com.jidesoft.converter.BooleanConverter;
import com.jidesoft.converter.DateConverter;
import com.jidesoft.converter.DoubleConverter;
import com.jidesoft.converter.IntegerConverter;
import com.jidesoft.converter.MonthNameConverter;
import com.jidesoft.converter.ObjectConverterManager;
import com.jidesoft.converter.PercentConverter;
import com.jidesoft.converter.QuarterNameConverter;
import com.jidesoft.converter.YearNameConverter;
import com.jidesoft.grid.CellEditorManager;
import com.jidesoft.grid.CellRendererManager;
import com.jidesoft.grouper.ObjectGrouperManager;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.standbysoft.component.date.swing.JDatePicker;

import dsEventProcessor.TaskEventProcessor;
import dsServices.RemoteBOProcess;
import dsServices.RemoteProduct;
import dsServices.RemoteReferenceData;
import dsServices.RemoteTask;
import dsServices.RemoteTrade;
import dsServices.ServerConnectionUtil;
	
	
	//VS4E -- DO NOT REMOVE THIS LINE!
	public class FXTradePanel extends  TradePanel {
	
		  boolean isTradeProper = false;
		  Collection<StartUPData> instrumenTypeStartupData = new Vector<StartUPData>();
		  Collection<String> instrumenTypeVal = new Vector<String>();
		  BasicData basicData = null;
		  int instrumentType = 0;
		  static String FXSWAP = "FXSWAP";
		  static String FXFORWARDOPTION = "FXFORWARDOPTION";
		  static String FXFORWARD = "FXFORWARD";
		  static String FXTAKEUP = "FXTAKEUP";
		  static String FX = "FX";
		  static String FXFWDOPTION = "FXFWDOPTION";
		 int bookId =0;
		 int counterPartyID =0;
		 boolean actionController = false;
		 int traderID =0;
		 SDIPanel sdiPanel = null;
		 FeesPanel feesPanel = null;
		 TaskPanel taskPanel = null; 
		 String primaryCurrency = "";
		 String quotingCurrency = "";
		 String productType = "FX";
		 String productSubType = "FXFORWARD";
		 TransferPanel transferPanel;
		 String currencyPair = "";
		 Trade trade = null;
		 Product product = null;
		 RemoteProduct remoteProduct = null;
		 Swap swap = null;
		 outRight out = null;
		 FWDOptionPanel fwdOp = null;
		 TradeAttributesD attributes = null;
		 RemoteBOProcess remoteBO;
		 FunctionalityD functionality = null;
		 TableCellRenderer defaultRenderer;
		 CurrencySplitConfig sconfig = null;
		 String s [] = {"id","BookName"};
		 Book mirrorBook =new Book();
			DefaultTableModel booktablemodel = new DefaultTableModel(s,0);
			Hashtable<Integer,Book> books = new Hashtable<Integer,Book>();
			
		 javax.swing.DefaultComboBoxModel actionstatus = new javax.swing.DefaultComboBoxModel();
		 
		 Hashtable<String,String>  attributeDataValue = new Hashtable<String,String>();
		 B2BConfig b2bconfig = null;
		 DefaultTableModel attributeModel = null;
		 private JTable __table;
		 TakeUPWindow takeupW = new TakeUPWindow();
		 Collection<Vector<Trade>> childTrades = null;
		 Product takeUPproduct = null;
		 Users user = null;
		 public static  ServerConnectionUtil de = null;
		 DefaultCellEditor  startupDataInstrumentTypeDC = null;
		 RemoteTrade remoteTrade ;
			RemoteBOProcess boremote;
			RemoteTask remoteTask;
			RemoteReferenceData remoteReference;
			boolean favEnableFlag = false;
			 Object[][] __rows = {
				      {  new JButton("Button 1One"), new JButton("Button One") },
				      {  new JButton("Button 2One"), new JButton("Button Two") },
				      {  new JButton("Button 3One"), new JButton("Button Three") },
				      { new JButton("Button 4One"), new JButton("Button Four") }
				    };
		 public BasicData getBasicData() {
			if (basicData == null) {
				basicData = new BasicData(remoteReference);
				//basicData.setBorder(new LineBorder(Color.black, 1, false));
			}
			return basicData;
		}
		 
		public void setBasicData(BasicData basicData) {
			this.basicData = basicData;
		}
	
		public int getBookId() {
			return bookId;
		}
	
		public void setBookId(int bookId) {
			this.bookId = bookId;
		}
	
		public int getCounterPartyID() {
			return counterPartyID;
		}
	
		public void setCounterPartyID(int counterPartyID) {
			this.counterPartyID = counterPartyID;
		}
	
		public int getTraderID() {
			return traderID;
		}
	
		public void setTraderID(int traderID) {
			this.traderID = traderID;
		}
	
		public SDIPanel getSdiPanel() {
			return sdiPanel;
		}
	
		public void setSdiPanel(SDIPanel sdiPanel) {
			this.sdiPanel = sdiPanel;
		}
	
		public FeesPanel getFeesPanel() {
			return feesPanel;
		}
	
		public void setFeesPanel(FeesPanel feesPanel) {
			this.feesPanel = feesPanel;
		}
	
		public TaskPanel getTaskPanel() {
			return taskPanel;
		}
	
		public void setTaskPanel(TaskPanel taskPanel) {
			this.taskPanel = taskPanel;
		}
	
		public String getPrimaryCurrency() {
			return primaryCurrency;
		}
	
		public void setPrimaryCurrency(String primaryCurrency) {
			this.primaryCurrency = primaryCurrency;
		}
	
		public String getQuotingCurrency() {
			return quotingCurrency;
		}
	
		public void setQuotingCurrency(String quotingCurrency) {
			this.quotingCurrency = quotingCurrency;
		}
	
		public String getProductType() {
			return productType;
		}
	
		public void setProductType(String productType) {
			this.productType = productType;
		}
	
		public String getProductSubType() {
			return productSubType;
		}
	
		public void setProductSubType(String productSubType) {
			this.productSubType = productSubType;
		}
	
		public TransferPanel getTransferPanel() {
			return transferPanel;
		}
	
		public void setTransferPanel(TransferPanel transferPanel) {
			this.transferPanel = transferPanel;
		}
	
		public String getCurrencyPair() {
			return currencyPair;
		}
	
		public void setCurrencyPair(String currencyPair) {
			this.currencyPair = currencyPair;
		}
	
	
		public Product getProduct() {
			return product;
		}
	
		public void setProduct(Product product) {
			this.product = product;
		}
	
		public RemoteProduct getRemoteProduct() {
			return remoteProduct;
		}
	
		public void setRemoteProduct(RemoteProduct remoteProduct) {
			this.remoteProduct = remoteProduct;
		}
	
		public JPanel getSwap() {
			
			if (swap == null) {
				swap = new Swap();
				//swap.setBorder(new LineBorder(Color.black, 1, false));
				swap.setVisible(false);
			}
			return swap;
		}
	public JPanel getFwdOp() {
			
			if (fwdOp == null) {
				fwdOp = new FWDOptionPanel();
				fwdOp.setVisible(false);
				fwdOp.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			}
			return fwdOp;
		}
	public JPanel getFW() {
			
			if (swap == null) {
				swap = new Swap();
				//swap.setBorder(new LineBorder(Color.black, 1, false));
			}
			return swap;
		}
		public void setSwap(Swap swap) {
			this.swap = swap;
		}
	
		public outRight getOut() {
			if (out == null) {
				out = new outRight();
				//out.setBorder(new LineBorder(Color.black, 1, false));
			}
			return out;
		}
	
		public void setOut(outRight out) {
			this.out = out;
		}
	
		public TradeAttributesD getAttributes() {
			if (attributes == null) {
				attributes = new TradeAttributesD();
				attributes.setFXPanelObjs(fwdOp);
				//attributes.setBorder(new LineBorder(Color.black, 1, false));
			}
			return attributes;
		}
	
		public void setAttributes(TradeAttributesD attributes) {
			this.attributes = attributes;
		}
	
		public RemoteBOProcess getRemoteBO() {
			return remoteBO;
		}
	
		public void setRemoteBO(RemoteBOProcess remoteBO) {
			this.remoteBO = remoteBO;
		}
	
		public FunctionalityD getFunctionality() {
			if (functionality == null) {
				functionality = new FunctionalityD();
				functionality.setRemoteRef(remoteReference);
				//functionality.setBorder(new LineBorder(Color.black, 1, false));
			}
			return functionality;
		}
	
		public void setFunctionality(FunctionalityD functionality) {
			this.functionality = functionality;
		}
	
		public TableCellRenderer getDefaultRenderer() {
			return defaultRenderer;
		}
	
		public void setDefaultRenderer(TableCellRenderer defaultRenderer) {
			this.defaultRenderer = defaultRenderer;
		}
	
		public RemoteTrade getRemoteTrade() {
			return remoteTrade;
		}
	
		public void setRemoteTrade(RemoteTrade remoteTrade) {
			this.remoteTrade = remoteTrade;
		}
	
		public RemoteBOProcess getBoremote() {
			return boremote;
		}
	
		public void setBoremote(RemoteBOProcess boremote) {
			this.boremote = boremote;
		}
	
		public RemoteTask getRemoteTask() {
			return remoteTask;
		}
	
		public void setRemoteTask(RemoteTask remoteTask) {
			this.remoteTask = remoteTask;
		}
	
		public RemoteReferenceData getRemoteReference() {
			return remoteReference;
		}
	
		public void setRemoteReference(RemoteReferenceData remoteReference) {
			this.remoteReference = remoteReference;
		}
	
		public boolean isFavEnableFlag() {
			return favEnableFlag;
		}
	
		public void setFavEnableFlag(boolean favEnableFlag) {
			this.favEnableFlag = favEnableFlag;
		}

		private static final long serialVersionUID = 1L;
		private JPanel jPanel0;
		private JPanel jPanel1;
		private JPanel jPanel2;
		private JPanel jPanel3;
		private JPanel jPanel4;
		private rollPanel rollpanel;
		 void initJide() {
	        // Initialize JIDE settings.
	        // JIDE Licenses Verification
	
	        LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
	
	        //---------------------------------------------------------------------
	        // JIDE ObjectConverterManager.initDefaultConverter
	        CellEditorManager.initDefaultEditor();
	        CellRendererManager.initDefaultRenderer();
	        ObjectConverterManager.initDefaultConverter();
	        ObjectGrouperManager.initDefaultGrouper();
	
	        // Note: Apply special converters to display class in something useful to the user.
	        ObjectConverterManager.registerConverter(Date.class, new DateConverter(), DateConverter.DATE_CONTEXT);
	        ObjectConverterManager.registerConverter(Date.class, new DateConverter(), DateConverter.DATETIME_CONTEXT);
	        ObjectConverterManager.registerConverter(boolean.class, new BooleanConverter());
	        ObjectConverterManager.registerConverter(Boolean.class, new BooleanConverter());
	
	        // Special formatting of integers
	        DecimalFormat myFormatter = new DecimalFormat("#########"); // removes the thousand separator
	        IntegerConverter intConverter = new IntegerConverter();
	        intConverter.setNumberFormat(myFormatter);
	        ObjectConverterManager.registerConverter(int.class, intConverter);
	        ObjectConverterManager.registerConverter(Integer.class, intConverter);
	
	        // Choose formatting of doubles
	        NumberFormat format = NumberFormat.getNumberInstance();
	        format.setMinimumFractionDigits(9);
	        format.setMaximumFractionDigits(10);
	        DoubleConverter converter = new DoubleConverter(format);
	        ObjectConverterManager.registerConverter(Double.class, converter);
	        ObjectConverterManager.registerConverter(double.class, converter);
	
	        ObjectConverterManager.registerConverter(double.class,
	            new PercentConverter(), PercentConverter.CONTEXT);
	        ObjectConverterManager.registerConverter(Double.class,
	            new PercentConverter(), PercentConverter.CONTEXT);
	
	        // Register convertors for date class.
	        ObjectConverterManager.registerConverter(Date.class, new YearNameConverter(), YearNameConverter.CONTEXT);
	        ObjectConverterManager.registerConverter(Date.class, new QuarterNameConverter(), QuarterNameConverter.CONTEXT);
	        ObjectConverterManager.registerConverter(Date.class, new MonthNameConverter(), MonthNameConverter.CONTEXT);
		  }

		public FXTradePanel() {
			initComponents();
		}
	
		private void initComponents() {
			//initJide();
			//@ yogesh 01/02/2015
			// takeup type default is set to utilize 
			initRemote();
			takeupW.typeComboBox.setSelectedItem("Utilize");
			
			setBorder(new LineBorder(Color.black, 1, false));
			setLayout(new GroupLayout());
			add(getSwap(), new Constraints(new Leading(7, 12, 12), new Leading(193, 89, 12, 12)));
			add(getFwdOp(), new Constraints(new Leading(7, 512, 358, 358), new Leading(193, 89, 10, 10)));
			add(getFunctionality(),  new Constraints(new Leading(7, 843, 10, 10), new Leading(294, 10, 10)));
		
			add(getOut(), new Constraints(new Leading(5, 838, 12, 12), new Leading(101, 87, 12, 12)));
			add(getAttributes(),  new Constraints(new Leading(860, 329, 12, 12), new Leading(4, 536, 10, 10)));
			//add(getOut(), new Constraints(new Leading(5, 838, 12, 12), new Leading(101, 87, 12, 12)));
			add(getBasicData(), new Constraints(new Leading(7, 835, 10, 10), new Leading(8, 90, 10, 10)));
			add(getRollPanel(),  new Constraints(new Leading(483, 360, 10, 10), new Leading(193, 89, 10, 10)));
			
			
			setSize(1234, 698);
			rollpanel.setVisible(false);
			init();
		}
		private String []  convertVectortoSringArray(Vector v) {
	    	String name [] = null;
	    	int i=0;
	    	if(v != null ) {
	    		name = new String[v.size()];
	    		Iterator its = v.iterator();
	    		while(its.hasNext()) {
	    			name [i] = ( (StartUPData) its.next()).getName();
	    			i++;
	    		}
	    	}
			return name;                                           
	        // TODO add your handling code here:
	    } 
	
		private JPanel getRollPanel() {
			if (rollpanel == null) {
				rollpanel = new rollPanel();
			//	rollpanel.setBorder(new LineBorder(Color.black, 1, false));
				rollpanel.setLayout(new GroupLayout());
				rollpanel.setVisible(false);
			
			}
			return 	rollpanel.getJPanel0();
			
		}
		@Override
		public void openTrade(Trade trade) {
			this.trade = trade;
			if(trade == null)
				return;
			this.trade = trade; 
			 Vector<Trade> routingTrades = new Vector<Trade>();
			attributeDataValue.clear();
			openTradeInWindow(trade);
			functionality.jButton5.setText("SAVEASNEW");
			functionality.jButton6.setEnabled(true);
			
			functionality.refreshTables(trade.getTradedesc(),basicData.book.getText(),new Integer(basicData.book.getName()).intValue());
			try {
				String autoType = trade.getAutoType();
				if(!commonUTIL.isEmpty(autoType)) {
			        if(trade.getTradedesc1().equalsIgnoreCase(FX) || trade.getTradedesc1().equalsIgnoreCase(FXSWAP) || trade.getTradedesc1().equalsIgnoreCase(FXFORWARD) ) {
			        	
			        	routingTrades  = remoteTrade.getSplitTrades(trade);  // i know only 5 records are going to come. 
			        	if(routingTrades.size() == 5) {
				        	out.jCheckBox2.setSelected(true);
				        	functionality.jLabel2.setText(routingTrades.get(1).getTradedesc());
				        	functionality.jLabel3.setText(routingTrades.get(3).getTradedesc()); 
				        	functionality.jTextField2.setText(String.valueOf(routingTrades.get(1).getPrice())); // bad programming.
				        	functionality.jTextField3.setText(String.valueOf(routingTrades.get(3).getPrice())); // bad programming.
				        	if(trade.getTradedesc1().equalsIgnoreCase(FXSWAP)) {
				        		functionality.FarRate1.setText(String.valueOf(routingTrades.get(1).getSecondPrice())); // bad programming.
				        		functionality.FarRate2.setText(String.valueOf(routingTrades.get(3).getSecondPrice())); // bad programming.
				        	}
			        	}
			        	functionality.setRoutingData(routingTrades);
			        }   		
			      			
				}
				
			} catch (RemoteException e) {				
				e.printStackTrace();
			}		
			
			rollpanel.setVisible(false);			
		}
		private JPanel getJPanel4() {
			if (jPanel4 == null) {
				jPanel4 = new JPanel();
			//	jPanel4.setBorder(new LineBorder(Color.black, 1, false));
				jPanel4.setLayout(new GroupLayout());
			}
			return functionality;
		}
	
		private JPanel getJPanel3() {
			if (jPanel3 == null) {
				jPanel3 = new JPanel();
			//	jPanel3.setBorder( LineBorder(Color.black, 1, false));
				jPanel3.setLayout(new GroupLayout());
			}
			return swap;
		}
	
		private JPanel getJPanel2() {
			if (jPanel2 == null) {
				jPanel2 = new JPanel();
			//	jPanel2.setBorder(new LineBorder(Color.black, 1, false));
				jPanel2.setLayout(new GroupLayout());
			}
			return out;
		}
	
		private JPanel getJPanel1() {
			if (jPanel1 == null) {
				jPanel1 = new JPanel();
			//	jPanel1.setBorder(new LineBorder(Color.black, 1, false));
				jPanel1.setLayout(new GroupLayout());
			}
			return basicData;
		}
	
		private JPanel getJPanel0() {
			if (jPanel0 == null) {
				jPanel0 = new JPanel();
			//	jPanel0.setBorder(new LineBorder(Color.black, 1, false));
				jPanel0.setLayout(new GroupLayout());
			}
			return attributes;
		}
		public void getDataForcurrencySplitOnBook() {
			Favorities fav = new Favorities();
			fav.setType("CurrencyPair");
				 
			fav.setUserId(user.getId());
			Vector favData;
			try {
				favData = (Vector) remoteReference.selectFavourites(fav);
				functionality.fillCurrencyPair(favData);
				fav.setType("Book");
				favData = 	(Vector) remoteReference.selectFavourites(fav);
				functionality.fillBooks(favData);
			} catch (RemoteException e) {				
				e.printStackTrace();
			}			   
		}
		
		public void getDataForB2bCps() {
			Favorities fav = new Favorities();
			Vector favData;
			try {
				fav.setUserId(getUser().getId());
				fav.setType("CounterParty");
				favData = 	(Vector) remoteReference.selectFavourites(fav);
				functionality.fillB2bCPs(favData);
			} catch (RemoteException e) {				
				e.printStackTrace();
			}			   
		}
		
		public void getDataForcurrencySplitOnCp() {
			Favorities fav = new Favorities();
			fav.setType("CurrencyPair");
			fav.setUserId(user.getId());
			Vector favData;
			try {
				favData = (Vector) remoteReference.selectFavourites(fav);
				functionality.fillCurrencyPair(favData);
				fav.setType("CounterParty");
				favData = 	(Vector) remoteReference.selectFavourites(fav);
				functionality.fillCPs(favData);
			} catch (RemoteException e) {				
				e.printStackTrace();
			}
			   
		}
		public void initRemote() {
			de =ServerConnectionUtil.connect("localhost", 1099,commonUTIL.getServerIP());
		   	 try {
		   		remoteTrade = (RemoteTrade) de.getRMIService("Trade");
		   		boremote = (RemoteBOProcess) de.getRMIService("BOProcess");
		   		remoteTask = (RemoteTask) de.getRMIService("Task");
		   		remoteReference = (RemoteReferenceData) de.getRMIService("ReferenceData");
		   		remoteProduct = (RemoteProduct) de.getRMIService("Product");
		   		product = (Product) remoteProduct.selectProductOnType(productType, productSubType);     
		   		remoteBO = (RemoteBOProcess) de.getRMIService("BOProcess");
		   	 }catch(RemoteException r) {
		   		 commonUTIL.displayError("FXTradePanel", "initRemote", r);
		   	 }
		   		
		}
		public void init() {
			//de =ServerConnectionUtil.connect("localhost", 1099,commonUTIL.getServerIP());
		   	 try {
		   	/*	remoteTrade = (RemoteTrade) de.getRMIService("Trade");
		   		boremote = (RemoteBOProcess) de.getRMIService("BOProcess");
		   		remoteTask = (RemoteTask) de.getRMIService("Task");
		   		remoteReference = (RemoteReferenceData) de.getRMIService("ReferenceData");
		   		remoteProduct = (RemoteProduct) de.getRMIService("Product");
		   		product = (Product) remoteProduct.selectProductOnType(productType, productSubType);     
		   		remoteBO = (RemoteBOProcess) de.getRMIService("BOProcess"); */
		   		Vector startupDataInstrumentType = (Vector) remoteReference.getStartUPData("InstrumentType");
		   		JComboBox startupDataInstrumentTypecomboBox = new JComboBox( convertVectortoSringArray(startupDataInstrumentType) );
		   		startupDataInstrumentTypeDC = new DefaultCellEditor(startupDataInstrumentTypecomboBox);
		   	//	trade = new Trade();
				//	System.out.println(remoteTrade);
		   		 functionality.setRemoteRef(remoteReference);
		   		 functionality.jButton6.setEnabled(false);
		   		 functionality.jButton8.setEnabled(false);
		   		 actionstatus.insertElementAt("NEW", 0);
		   		 actionstatus.setSelectedItem("NEW");
		   		 basicData.buysell.setEditable(false);
		   		// basicData.counterPary.setEditable(false);
		   		out.jCheckBox2.setEnabled(false);
		   		out.jCheckBox2.setSelected(false);
		   		 basicData.book.setEditable(false);
		   		 out.jComboBox1.removeAll();
		   		 out.jComboBox1.setModel(actionstatus);
		   		 basicData.jTextField7.setEditable(false);
				 out.jTextField6.setEditable(false);
				 out.jLabel2.setText("");
				 out.jTextField5.setText("0"); // id
				 out.jTextField1.setText("0"); // amt1
				 out.jTextField2.setText("0"); // amt2
					
				 out.outRightDate.setDateFormat(commonUTIL.getDateFormat()); // date
				 out.outRightDate.setSelectedDate(commonUTIL.getCurrentDate());
				 swap.swapDate.setDateFormat(commonUTIL.getDateFormat());
				 swap.swapDate.setSelectedDate(commonUTIL.getCurrentDate());
				 rollpanel.jTextField0.setDateFormat(commonUTIL.getDateFormat());
				 out.jTextField4.setText("0"); // spot 
				 basicData.jTextField7.setText("0");  
				//String attributeColumnName [] =    {"Attribute Name ", "Attribute  Value "};
	             
				 //   attributeModel = new DefaultTableModel(attributeColumnName,0);
				 processTableData(attributeDataValue,attributeModel);
			        //attributes.jTable1.setModel(attributeModel);
			       
			     swap.jTextField1.setText("0.0");
			     swap.jTextField2.setText("0.0");
			     swap.jTextField4.setText("0.0");
			     swap.setVisible(false);
			     basicData.jRadioButton0.setSelected(false);
			     basicData.jRadioButton1.setSelected(true);
			     basicData.jRadioButton2.setSelected(false);
			     basicData.jRadioButton5.setSelected(false);
			     // mpankaj 02/02
				 functionality.jPanel2.setVisible(false);
			     getBookDataCombo1(booktablemodel,books);
			     basicData.book.addKeyListener(new KeyAdapter() {
			     public void  keyTyped(KeyEvent e) {
					int count =  functionality.jTabbedPane1.getTabCount();
					for(int i = 0;i < count ;i++) {
						String tabName = functionality.jTabbedPane1.getTitleAt(i);
						if(tabName.equalsIgnoreCase("Book")) {
							functionality.jTabbedPane1.setSelectedIndex(i);
							
						}
					}
			     }
			 
		});
			     
			    
	    
	    // takeup button        
        functionality.jButton0.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if( !functionality.jButton0.isEnabled()) 
					return;
				if(!productSubType.equalsIgnoreCase(FXFORWARDOPTION))
					return;
			//	takeupW  = new TakeUPWindow();
				takeupW.setVisible(true);
				takeupW.setLocationRelativeTo(basicData);
				takeupW.setResizable(false);
				URL imgURL = this.getClass().getResource("/resources/icon/sql.jpg");
				 
				takeupW.setIconImage(Toolkit.getDefaultToolkit()
		      		   .getImage(imgURL));
				takeupW.setTitle("Cosmos TakeUP Window : " + user.getUser_name()    );
				
				//@yogesh 10/02/2015
				// when new is pressed trade is null and if its merchant trade and takeUp Button is pressed it 
				//throws null pointer. To avoid that we check if trade is null below
				if (trade != null) {
					try {
						takeupW.jTextField0.setValue(fwdOp.primaryC.getDoubleValue());
						takeupW.jTextField2.setValue(fwdOp.quotingC.getDoubleValue());
						takeupW.jLabel10.setText(trade.getCurrency());
						takeupW.jLabel11.setText(trade.getCurrency());
						takeupW.jLabel5.setText(basicData.currencyPair.getText().substring(0, 3));
						takeupW.jLabel4.setText(basicData.currencyPair.getText().substring(0, 3));
					//	takeupW.jTextField4.setDateFormat(commonUTIL.getDateTimeFormat());
	                  //   takeupW.jTextField7.setDateFormat(commonUTIL.getDateTimeFormat());
	                     //takeupW.jTextField5.setText(commonUTIL.getCurrentDateInString());
	                     //takeupW.jTextField5.setEditable(false);
						//takeupW.jTextField5.setDate(commonUTIL.getDateTimeFormat());
	                     //takeupW.jTextField6.setFormat(commonUTIL.getDateTimeFormat());
	                     
	                     /*if (!commonUTIL.stringToDate(trade.getDelivertyDate(), true).equals(
	                    		 commonUTIL.stringToDate(trade.getEffectiveDate(), true))) {                    	 
	                    	 takeupW.jTextField7.setText(trade.getEffectiveDate());                    	 
	                     } else {                    	 
	                    	 takeupW.jTextField7.setText(trade.getTradeDate());                    	 
	                     }*/
	                     
	                     takeupW.jTextField7.setText(trade.getEffectiveDate());
	                     takeupW.jTextField4.setText(trade.getTradeDate());                  
	                     takeupW.jTextField1.setValue(0);
	                     takeupW.jTextField3.setValue(0);
	                     takeupW.model.setData((Vector) childTrades);

					} catch (ParseException e1) {
						e1.printStackTrace();
					}
				}
				//takeupW.setUser(user);				
			}
        });
    	functionality.FarRate2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
								setRoutingDataCal();
								populateRountingData();
			}
    	});
	  functionality.FarRate2.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				setRoutingDataCal();
				populateRountingData();
			}
	  });
  
	  functionality.FarRate1.addKeyListener(new KeyAdapter() { 
	  		@Override		        
		  	public void keyTyped(KeyEvent e) {
		  		if (e.getKeyChar() == KeyEvent.VK_ENTER) {
		  			populateRountingData();
		  		}
		  	}
	  });
			        
	  // this is used only we press enter on NearRate of  Quoting Leg			        
    functionality.jTextField3.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			
			setRoutingDataCal();
			populateRountingData();
			
		/*	try {
				double rate1 = 0.0 ;
				double rate2 = 0.0 ;	
				double farRate1 = 0.0;
				double farRate2 = 0.0;
				if(!commonUTIL.isEmpty( functionality.jTextField2.getText()))
					rate1 =  functionality.jTextField2.getDoubleValue();
				if(!commonUTIL.isEmpty( functionality.jTextField3.getText()))
					rate2 =  functionality.jTextField3.getDoubleValue();
				if(!commonUTIL.isEmpty( functionality.FarRate1.getText()))
					farRate1 =  functionality.FarRate1.getDoubleValue();
				if(!commonUTIL.isEmpty( functionality.FarRate2.getText()))
					farRate2 =  functionality.FarRate2.getDoubleValue();
				
				  
				if(commonUTIL.isEmpty( functionality.getRoutingData()))
					return;
				if( functionality.getRoutingData().size() > 4) {
					Trade orignalTrade = functionality.getRoutingData().get(0);
					if( orignalTrade.getId() == 0) {
							Trade xsplit1  =  functionality.getRoutingData().get(1);									
							Trade xsplit2  =  functionality.getRoutingData().get(3);
						Vector<Trade>	rounting =     FXSplitUtil.splitTrade(xsplit1, xsplit2, functionality.getRoutingData().get(0), rate1, rate2,farRate1,farRate2);
						functionality.setRoutingData(rounting);
					}  else {
						Vector<Trade> rounting =  FXSplitUtil.splitTrade(functionality.getRoutingData(), rate1, rate2,farRate1,farRate2);
						if(commonUTIL.isEmpty(rounting)) 
							return;
						functionality.setRoutingData(rounting);
						functionality. jTextField3.setText(String.valueOf(rate2));
						functionality. jTextField2.setText(String.valueOf(rate1));
						functionality. FarRate1.setText(String.valueOf(farRate1));
						functionality. FarRate2.setText(String.valueOf(farRate2));
						attributes.changeXccySplitRate("splitBaseNearRate",String.valueOf(rate1));
						attributes.changeXccySplitRate("splitQuoteNearRate",String.valueOf(rate2));
						attributes.changeXccySplitRate("splitBaseFarRate",String.valueOf(farRate1));
						attributes.changeXccySplitRate("splitQuoteFarRate",String.valueOf(farRate2));
					}
                 //  getJTable1().repaint();
                  
				}
                   
			} catch (ParseException e) {
				
				e.printStackTrace();
			} */
		}
	
		
		
	});
    // this is used when we press enter on Near rate of base currency
	functionality.jTextField2.addActionListener(new ActionListener() {							
		@Override
		public void actionPerformed(ActionEvent arg0) {
			
		setRoutingDataCal();
		/*try {
			double rate1 = 0.0 ;
			double rate2 = 0.0 ;	
			double farRate1 = 0.0;
			double farRate2 = 0.0;
			if(!commonUTIL.isEmpty( functionality.jTextField2.getText()))
				rate1 =  functionality.jTextField2.getDoubleValue();
			if(!commonUTIL.isEmpty( functionality.jTextField3.getText()))
				rate2 =  functionality.jTextField3.getDoubleValue();
			if(!commonUTIL.isEmpty( functionality.FarRate1.getText()))
				farRate1 =  functionality.FarRate1.getDoubleValue();
			if(!commonUTIL.isEmpty( functionality.FarRate2.getText()))
				farRate2 =  functionality.FarRate2.getDoubleValue();
			
			  
			if(commonUTIL.isEmpty( functionality.getRoutingData()))
				return;
			if( functionality.getRoutingData().size() > 4) {
				Trade orignalTrade = functionality.getRoutingData().get(0);
				if( orignalTrade.getId() == 0) {
						Trade xsplit1  =  functionality.getRoutingData().get(1);									
						Trade xsplit2  =  functionality.getRoutingData().get(3);
					Vector<Trade>	rounting =     FXSplitUtil.splitTrade(xsplit1, xsplit2, functionality.getRoutingData().get(0), rate1, rate2,farRate1,farRate2);
					functionality.setRoutingData(rounting);
				}  else {
					Vector<Trade> rounting =  FXSplitUtil.splitTrade(functionality.getRoutingData(), rate1, rate2,farRate1,farRate2);
					if(commonUTIL.isEmpty(rounting)) 
						return;
					functionality.setRoutingData(rounting);
					functionality. jTextField3.setText(String.valueOf(rate2));
					functionality. jTextField2.setText(String.valueOf(rate1));
					functionality. FarRate1.setText(String.valueOf(farRate1));
					functionality. FarRate2.setText(String.valueOf(farRate2));
					attributes.changeXccySplitRate("splitBaseNearRate",String.valueOf(rate1));
					attributes.changeXccySplitRate("splitQuoteNearRate",String.valueOf(rate2));
					attributes.changeXccySplitRate("splitBaseFarRate",String.valueOf(farRate1));
					attributes.changeXccySplitRate("splitQuoteFarRate",String.valueOf(farRate2));
				}
	         //  getJTable1().repaint();
	          
			}
	           
		} catch (ParseException e) {
			
			e.printStackTrace();
		}*/
		}
	
	});
			        
    takeupW.jTextField1.addKeyListener(new KeyAdapter() { 
    	@Override		        
    	public void keyTyped(KeyEvent e) {
    		if (e.getKeyChar() == KeyEvent.VK_ENTER) {
    			try {
					double amt1 = takeupW.jTextField1.getDoubleValue();
					double quant = fwdOp.primaryC.getDoubleValue();
					if(quant >  0.0) {
						if(amt1 < 0.0)  {
							commonUTIL.showAlertMessage("TakeUp  Direction must same as FX Time Option");
							return;
						}
						if(amt1 > quant)  {
							commonUTIL.showAlertMessage("TakeUp PrimaryCurr greater then FX Time Option PrimaryCurr");
							return;
						}
						takeupW.jTextField3.setValue((amt1 * trade.getPrice()) * -1);
					}
					if(quant <  0.0) {
						if(amt1 > 0.0)  {
							commonUTIL.showAlertMessage("TakeUp  Direction must same as FX Time Option");
							return;
						}
						if(amt1 < quant)  {
							commonUTIL.showAlertMessage("TakeUp PrimaryCurr greater then FX Time Option PrimaryCurr");
							return;
						}
						takeupW.jTextField3.setValue((amt1 * trade.getPrice()) * -1);
					}
					
				} catch (ParseException e1) {
					
					e1.printStackTrace();
				}
    		}
    	}
    	
    });
    takeupW.jTextField3.addKeyListener(new KeyAdapter() { 
    	@Override		        
    	public void keyTyped(KeyEvent e) {
    		if (e.getKeyChar() == KeyEvent.VK_ENTER) {
    			try {
					double amt1 = takeupW.jTextField3.getDoubleValue();
					double noma = fwdOp.quotingC.getDoubleValue();
					if(noma >  0.0) {
						if(amt1 < 0.0)  {
							commonUTIL.showAlertMessage("TakeUp  Direction must same as FX Time Option");
							return;
						}
						if(amt1 >noma)  {
							commonUTIL.showAlertMessage("TakeUp Quoting greater then FX Time Option Quoting Curr");
							return;
						}
						takeupW.jTextField1.setValue((amt1 / trade.getPrice()) * -1);
					}
					if(noma <  0.0) {
						if(amt1 > 0.0)  {
							commonUTIL.showAlertMessage("TakeUp  Direction must same as FX Time Option");
							return;
						}
						if(amt1 < noma)  {
							commonUTIL.showAlertMessage("TakeUp  Quoting greater then FX Time Option Quoting Curr");
							return;
						}
						takeupW.jTextField1.setValue((amt1 /  trade.getPrice()) * -1);
					}
					
				} catch (ParseException e1) {
					
					e1.printStackTrace();
				}
    		}
    	}
    	
    });
			     
    takeupW.jTable0.addMouseListener(new java.awt.event.MouseAdapter() {

		@Override
		public void mouseClicked(MouseEvent e) {
			int selectRow = takeupW.jTable0.getSelectedRow();
			Trade trade =takeupW.tableData.get(selectRow);
			try {
				trade = remoteTrade.selectTrade(trade.getId());
			} catch (RemoteException e1) {
				
				e1.printStackTrace();
			}
			TradeApplication tradeWindow = new TradeApplication("FX", getUser());
            //JFrameTradeWindowApplication tradeWindow = new JFrameTradeWindowApplication(trade.getProductType(),user);
        	
          //  tradeWindow.setUserName(user);
            tradeWindow.setOpenTrade(trade);
            tradeWindow.setVisible(true);
		}
	});
    //  if(takeupW != null) {
    // takeUp window save button	
	 takeupW.jButton0.addMouseListener(new java.awt.event.MouseAdapter() {
	
			@Override
			public void mouseClicked(MouseEvent e) {
				Trade tradeTakeUp = new Trade();
				double noma;
				try {
					noma = takeupW.jTextField3.getDoubleValue();
				double	prim = takeupW.jTextField1.getDoubleValue();
					if(noma == 0 && prim ==0 ) {
						commonUTIL.showAlertMessage("Takeup Quoting/Primary Currency is Zero");
						return;
				}
			} catch (ParseException e2) {				
				e2.printStackTrace();
			}
			
			 try {
				 
				 if(takeUPproduct == null)
				 takeUPproduct	 = (Product) remoteProduct.selectProductOnType(productType, FXTAKEUP);
				   
				 DateU tradedate = DateU.valueOf(commonUTIL.stringToDate(trade.getTradeDate(),true));
				 DateU tradeEffectiveDate= DateU.valueOf(commonUTIL.stringToDate(trade.getEffectiveDate(),true));
				 DateU tradeDeliverydate = DateU.valueOf(commonUTIL.stringToDate(trade.getDelivertyDate(),true));
				 DateU takeUPTradeDate = DateU.valueOf(takeupW.jTextField5.getDate());
				 DateU takeUPSettleDate = DateU.valueOf(takeupW.jTextField6.getDate());
				
				 if(!commonUTIL.between2dates(tradedate.getDate(), tradeEffectiveDate.getDate(), takeUPTradeDate.getDate())) {
					commonUTIL.showAlertMessage("Take-up Trade date(s) has to be within the Start and End dates of the FX Time Option");
					return;
				 }
				 
				 if(!commonUTIL.between2dates(tradedate.getDate(), tradeEffectiveDate.getDate(), takeUPSettleDate.getDate())) {
					commonUTIL.showAlertMessage("Take-up Settle date(s) has to be within the FwdOption Start date and FwdOption End date");
					return;
				 }
				 
				 tradeTakeUp.setId(0);
				 tradeTakeUp.setTradedesc1(FXTAKEUP);
				 tradeTakeUp.setAction("NEW");
				 tradeTakeUp.setStatus("NONE");
				 tradeTakeUp.setParentID(trade.getId());
				 tradeTakeUp.setBookId(trade.getBookId());
				 tradeTakeUp.setCpID(trade.getCpID());
				 tradeTakeUp.setType(trade.getType());
				 tradeTakeUp.setProductType(trade.getProductType());
				 tradeTakeUp.setCurrency(trade.getCurrency());
				 tradeTakeUp.setTradedesc(trade.getTradedesc());
				 tradeTakeUp.setPrice(trade.getPrice());
				 tradeTakeUp.setTraderID(trade.getTraderID());
				 tradeTakeUp.setAttribute("ParentID",Integer.toString(trade.getId()));
				
				 if ( takeupW.typeComboBox.getSelectedItem() == null) {
					commonUTIL.showAlertMessage("Select Takeup Type");
					return;
				 } else{
					tradeTakeUp.setAttribute("TakeUpType", takeupW.typeComboBox.getSelectedItem().toString());	
				 }
						
				 tradeTakeUp.setUserID(user.getId());
				 double amt1 = takeupW.jTextField1.getDoubleValue();
				 double amt2 = takeupW.jTextField3.getDoubleValue();
				 if(amt1 > 0.) {
				    if(  amt1 > takeupW.jTextField0.getDoubleValue()) {
				    	commonUTIL.showAlertMessage("Take-up amount greater then Outstanding FX Time Option");
				    	return;
				    }
				 }
				 
				 if(amt1 < 0.) {
				    if( amt1 < takeupW.jTextField0.getDoubleValue() ) {
				    	commonUTIL.showAlertMessage("Take-up amount greater then Outstanding FX Time Option");
				    	return;
				    }
				 }
				 
				 tradeTakeUp.setQuantity(amt1);
				 tradeTakeUp.setNominal(amt2);
				 tradeTakeUp.setTradeDate(commonUTIL.convertDateTimeTOString(takeUPTradeDate.getDate()));
				 tradeTakeUp.setDelivertyDate(commonUTIL.convertDateTOString(takeUPSettleDate.getDate()));
				 tradeTakeUp.setEffectiveDate(commonUTIL.convertDateTOString(takeUPSettleDate.getDate()));
				 tradeTakeUp.setAttribute("Trade Date",commonUTIL.convertDateTimeTOString(takeUPTradeDate.getDate()));
				 tradeTakeUp.setAttribute("InstrumentType",trade.getAttributeValue("InstrumentType"));
				 tradeTakeUp.setProductId(takeUPproduct.getId());
				 
				 Vector tradestatus = new Vector();
				 tradestatus = remoteTrade.saveTrade(tradeTakeUp,tradestatus);
				
               	 String statusT = (String) tradestatus.elementAt(0);
               	 
               	 int i = ((Integer) tradestatus.elementAt(1)).intValue();
               	 if(i <= -10) {
               		commonUTIL.showAlertMessage((statusT));
        			return;
               	 }
               	 
               	 if(i == -4) {
         			commonUTIL.showAlertMessage((statusT));
            			return;
            		 }
               	 if(i == -3) {
            			commonUTIL.showAlertMessage((statusT));
            			return;
            		 }
               	if(i > 0)  {
               		commonUTIL.showAlertMessage((statusT));
               		attributes.isfwOpTrade = true;
               	 }
               	 
               	 tradeTakeUp.setId(i);
               	 takeupW.model.addRow(tradeTakeUp);
               	 
               	 if(tradeTakeUp.getQuantity() > 0.) {
               		fwdOp.primaryC.setValue(fwdOp.primaryC.getDoubleValue() - tradeTakeUp.getQuantity() );
               		fwdOp.quotingC.setValue(fwdOp.quotingC.getDoubleValue() + (tradeTakeUp.getNominal() * -1));
               		takeupW.jTextField0.setValue(takeupW.jTextField0.getDoubleValue() -tradeTakeUp.getQuantity() );
               		takeupW.jTextField2.setValue(takeupW.jTextField2.getDoubleValue()+ (tradeTakeUp.getNominal() * -1));
               		
               	 }
               	 
               	 if(tradeTakeUp.getQuantity() < 0.) {
               		fwdOp.primaryC.setValue(fwdOp.primaryC.getDoubleValue() + (tradeTakeUp.getQuantity() * -1) );
               		fwdOp.quotingC.setValue(fwdOp.quotingC.getDoubleValue() -tradeTakeUp.getNominal());
               		takeupW.jTextField0.setValue(takeupW.jTextField0.getDoubleValue() +(tradeTakeUp.getQuantity() *-1));
               		takeupW.jTextField2.setValue(takeupW.jTextField2.getDoubleValue() - tradeTakeUp.getNominal() );
               		
               	 }					
				              
			} catch (ParseException e1) {				
				e1.printStackTrace();
			} catch (RemoteException e1) {					
					e1.printStackTrace();
			}				
		}
	 });
	 
	 //@yogesh 05/02/2014
	 // below action listener commented as edit button is not required	 
	 //takeUp edit button
	/* takeupW.jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
		 
		 @Override
		public void mouseClicked(MouseEvent e) {
			 
			 double amt1;
			 double amt2;
			 
			try {
				
				amt1 = takeupW.jTextField1.getDoubleValue();
				amt2 = takeupW.jTextField3.getDoubleValue();
								
				if(amt1 == 0 || amt2 ==0 ) {
					commonUTIL.showAlertMessage("Takeup Quoting/Primary Amount is Zero");
					return;
				}
				
				if (null == takeupW.jTextField5.getDate()) {
					commonUTIL.showAlertMessage("Please select TakeUp Trade Date");
					return;
				}
				
				if (null == takeupW.jTextField6.getDate()) {
					commonUTIL.showAlertMessage("Please select TakeUp Settle Date");
					return;
				}
				
				int selectRow = takeupW.jTable0.getSelectedRow();
				
				Trade tradeTakeUpSelected =takeupW.tableData.get(selectRow);
				tradeTakeUpSelected.setQuantity(amt1);
				tradeTakeUpSelected.setNominal(amt2);
				tradeTakeUpSelected.setTradeDate(commonUTIL.convertDateTOString(takeupW.jTextField5.getDate()));
				tradeTakeUpSelected.setDelivertyDate(commonUTIL.convertDateTOString(takeupW.jTextField6.getDate()));
				
				Vector<String> message = new Vector<String>();
                
				Vector tradestatus = null;
	                
				tradestatus	 = 	remoteTrade.saveTrade(tradeTakeUpSelected, message);
				
				try {
			       	
					if(commonUTIL.isEmpty(tradestatus)) {
			       		commonUTIL.showAlertMessage("Error in ServerSide in saving Trade");
			       		return;
			       	 }
			       	 String statusT = (String) tradestatus.elementAt(0);
			       	 int i = ((Integer) tradestatus.elementAt(1)).intValue();
			       	 if(i == -10) {
			       		commonUTIL.showAlertMessage((statusT));
						return;
					 
			       	 }
			       	 if(i == -4) {
			 			commonUTIL.showAlertMessage((statusT));
			    			return;
			    		 }
			       	 if(i == -3) {
			    			commonUTIL.showAlertMessage((statusT));
			    			return;
			    		 }
			       	 if(i > 0) 
			       		commonUTIL.showAlertMessage((statusT));
							
				} catch (Exception e2) {				
					e2.printStackTrace();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		 }
		 
	 });*/
       
	 // }
	// functionality.j
	 
	 // B2B config button
	  functionality.jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if (!functionality.jButton2.isEnabled()) {
					return;
				}
				
				String BookID =basicData.book.getName();
				if(BookID == null || commonUTIL.isEmpty(BookID.trim())) {
					commonUTIL.showAlertMessage("Select Book & Currency Pair");
					return;
				}
				int bookID = Integer.parseInt(basicData.book.getName());
				String currencyPair = basicData.currencyPair.getText();
				
				/*	try {
						Vector b2b = (Vector) remoteReference.getB2BConfig(bookID, currencyPair);
						if(b2b == null || b2b.isEmpty()) {
							commonUTIL.showAlertMessage("B2b Configuration Missing");
						return;
					} */
						 b2bconfig = new B2BConfig();
						 functionality.jPanel2.setVisible(false);
						functionality.jPanel6.setVisible(true);
					   functionality.b2bCurrencyPair.setText(basicData.currencyPair.getText());
					   getDataForB2bCps();
					   // functionality.b2bBook.setText(getBook(b2bconfig.getBookid()).getBook_name());
					    functionality.b2bTransferTo.setText("0");
					   
					    
				/*	} catch (RemoteException e1) {
						
						e1.printStackTrace();
					} */
				
				
			}
	  });
	  
	  // saveasnew or DEAL is new
	  functionality.jButton5.addMouseListener(new java.awt.event.MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (!functionality.jButton5.isEnabled()) {
				return;
			}
			if(trade != null) {
				String autoType = trade.getAutoType();
				if(!commonUTIL.isEmpty(autoType)) {
					if(autoType.equalsIgnoreCase("INTERNAL")) { 
						// internal trade can't be save as new trade
						commonUTIL.showAlertMessage("Please click on New as this Trade is Internal Trade can't save as New");
						return;
					}
				}
			}
			
			if(basicData.jRadioButton5.isSelected() && fwdOp.startDate.isEnabled()) {				
		    	if (commonUTIL.addSubtractDate(commonUTIL.stringToDate(out.outRightDate.getSelectedDateAsText(), true), 31).before(
						 fwdOp.startDate.getDate())) {		    		
		    		commonUTIL.showAlertMessage("Option End date falls beyond 31 days after Trade end date");
		    		return;		    		
		    	}
		    	
		    	if (fwdOp.startDate.getDate().before(commonUTIL.stringToDate(out.outRightDate.getSelectedDateAsText(), true))){
		    		commonUTIL.showAlertMessage("Option End date cannot be before Trade end date");
		    		return;	
		    	}
			}
			
			 //@yogesh 04/02/2014
			  // check if split rates are filled if split rate checkbox is checked
			if(!checkRates()){
				return;
			}
			
			//@mpankaj 06/02/2014
			getDataBeforeSave();			
			
		    if(validdateALLFields("NEW")) {
		    	trade = new Trade();
		    	mirrorBook.setBookno(0);
		    	
		    	removeAttributeFromTrade(trade);
	            fillTrade(trade,"NEW");
	            
	            //@yogesh 04/02/2014
	            // check if instrumentType is selected for every Trade
	  		  	// checks if tradeDate is less then delivery date
	            if (!checktradeDetails(trade)){
	            	return;
	            }
	            
		    	int isHoliday = 0;
				try {					
					isHoliday = remoteReference.checkHolidayOrWeekend((String)trade.getTradedesc().substring(0, 3), 
					trade.getDelivertyDate());
					
					if (isHoliday == 1) {				
						commonUTIL.showAlertMessage("Near Date selected is a Holiday or a weekend. " +
								"Please select another date");
						return;
						//out.outRightDate.setBackground(Color.red);
					}
					
					if (isHoliday != 1) {						
						isHoliday = remoteReference.checkHolidayOrWeekend((String)trade.getTradedesc().substring(4, 7), 
								trade.getEffectiveDate());
						
						if (isHoliday == 1) {							
							commonUTIL.showAlertMessage("Far Date selected is a Holiday or a weekend. " +
									"Please select another date");
							return;
							//out.outRightDate.setBackground(Color.red);
						}
						
					} else if (isHoliday == -1) {
						commonUTIL.showAlertMessage("Server Problem");
						return;
					}
					
				} catch (RemoteException e2) {					
					e2.printStackTrace();
				}	
		    	
		    	/**/
		    	
	             if(trade.getTradedesc1() == null || trade.getTradedesc1().trim().length() == 0) {
	            	 commonUTIL.showAlertMessage("Product not Registered");
	            	 return;
	             }
	             
	             if ( (commonUTIL.stringToDate(trade.getEffectiveDate(), true)).before( 
	            		 commonUTIL.stringToDate(trade.getDelivertyDate(), true)) ) {	            	 	
	            	 commonUTIL.showAlertMessage("Far Date cannot be less than Near Date");
	            	 return;	            	 
	             }
	             
	             if(trade.getTradedesc1() != null && trade.getTradedesc1().trim().length() > 0)  {            		
            		 trade.setUserID( getUser().getId());
            		 try {
               	         
		               	 trade.setId(0);
		               	 trade.setStatus("NONE");
		               	 trade.setAction("NEW");
		               	 Vector<String> message = new Vector<String>();
		               	
		               	 //setAttribute is already done in fillTrade so commented 
		               //trade.setAttributes(getAttributeValue());
		               	trade.setMirrorBookid(0);
		        		trade.setMirrorID(0);
		        		trade.setAutoType("");
		        		// No need to add attributes without any value. Below attributes can be set whenever it has value like been 
		        		//done in tradeImp for swap split trade. so commented below
		        	//	trade.setAttribute("MirrorID","");	
		        	//	trade.setAttribute("B2BID","");	
		        	//	trade.setAttribute("B2BFlag","");		
		        	//	trade.setAttribute("XCurrSOriginalTradeID","");		
		        	//	trade.setAttribute("ParitialTo","");		
		        	//	trade.setAttribute("SXccySplitID","");		
		        	//	trade.setAttribute("ParitialFrom","");		
		        	//	trade.setAttribute("XccySplitFrom","");		
		        	//	trade.setAttribute("FXccySplitID","");		
		        	//	trade.setAttribute("OffsetID","");		
		        	//	trade.setAttribute("OriginalTradeID","");		
					//	trade.setAttribute("MirrorFromTradeID","");	
						trade.setOffsetid(0);
					//trade.seto
		        		trade.setParentID(0);
		        		trade.setB2Bflag(false);
		        		trade.setB2bid(0);
		        		trade.setXccySPlitid(0);
		      
		        		trade.setMirrorBookid(0);
		        		trade.setFees(feesPanel.getFeesDataV());
		        		Vector tradestatus = null;
		        		if(!commonUTIL.isEmpty(functionality.getRoutingData())  && functionality.getRoutingData().size() > 1) {
		        		  // trade.clearAttributes();
		        		   Vector<Trade> rountingTrades;
		        		   try {
								rountingTrades = FXSplitUtil.getRountingData(trade,remoteReference,functionality.FarRate1.getDoubleValue(),functionality.FarRate2.getDoubleValue());
							
								if(!commonUTIL.isEmpty(rountingTrades) && rountingTrades.size() >1 ) {								
										rountingTrades =     FXSplitUtil.splitTrade(rountingTrades.get(1), rountingTrades.get(3),
												trade,functionality.jTextField2.getDoubleValue(), functionality.jTextField3.getDoubleValue(),functionality.FarRate1.getDoubleValue(),functionality.FarRate2.getDoubleValue());
										trade.setRountingTrades(rountingTrades);
					        		    tradestatus  = 	remoteTrade.saveBatchSplitTrades(trade.getRountingTrades(),trade,message);
								} 
		        		   } catch (ParseException e2) {									
		        			   e2.printStackTrace();								
							}					 
		        		    
		        		} else {	        			
		        			 trade.setAllocatedID(0);
		        			 trade.setAutoType("Original");
		        			 tradestatus  = 	remoteTrade.saveTrade(trade,message);
		        		}
		               	 if(commonUTIL.isEmpty(tradestatus)) {
		               		commonUTIL.showAlertMessage("Error in ServerSide in saving Trade");
		               		 return;
		               	 }
		               	 String statusT = (String) tradestatus.elementAt(0);
		               	 int i = ((Integer) tradestatus.elementAt(1)).intValue();
		               	 if(i == -4) {
		               		 commonUTIL.showAlertMessage((statusT));
		               		 return;
		               	 }
		               	 if(i == -3) {
		            		commonUTIL.showAlertMessage((statusT));
		            		return;
		               	 }
		               	 if(i > 0) 
		               		commonUTIL.showAlertMessage((statusT));
		               	 	//System.out.println("*************** " +i);
	               	 	trade = (Trade) remoteTrade.selectTrade(i);
		               	 
	               	 	openTrade(trade);	        	
		               	
            		 } catch (RemoteException e1) {        			 
            			 e1.printStackTrace();
            		 } 	             
	             } 
	             //@yogesh 07/02/2015
	             // jbuttuon% should always be DEAL
	             //functionality.jButton5.setText("SAVEASNEW");
	             functionality.jButton6.setEnabled(true);            	
		    }
		}	
	  });

		// for currency split functionality. 
	    out.jCheckBox2.addMouseListener(new java.awt.event.MouseAdapter() {	
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if( out.jCheckBox2.isSelected()) {
				//	if(trade == null) {
					//	commonUTIL.showAlertMessage("Select Trade ");
					//	out.jCheckBox2.setSelected(false);
					//	return;
					//}
					
					 try {
					//	 basicData.book.getName() 
						 if(commonUTIL.isEmpty(basicData.book.getName()) && commonUTIL.isEmpty(basicData.currencyPair.getText())) {
							 commonUTIL.showAlertMessage("Select Book & CurrencyPair");
							 out.jCheckBox2.setSelected(false);
							 return;
						 }
						 // mpankaj 02/02
						 functionality.jPanel6.setVisible(false);
							// functionality.jButton8.setEnabled(true);
						 // mpankaj 02/02
							 functionality.jPanel2.setVisible(true);
						 if(productSubType.equalsIgnoreCase(FXSWAP)) {
							 functionality.FarRate1.setVisible(true);
							 functionality.FarRate2.setVisible(true);
							 functionality.jLabel4.setVisible(true);
							 functionality.jLabel5.setVisible(true);
						 } else {
							 functionality.FarRate1.setVisible(false);
							 functionality.FarRate2.setVisible(false);
							 functionality.jLabel4.setVisible(false);
							 functionality.jLabel5.setVisible(false);
						 }
						//System.out.println();
						Vector vector = (Vector) remoteReference.getCurrencySplitConfig(Integer.parseInt(basicData.book.getName()), basicData.currencyPair.getText());
						 
						if(!commonUTIL.isEmpty(vector)) {
							
							if(trade == null || trade.getId() == 0)
							     functionality.clearRounting();
							sconfig =  (CurrencySplitConfig)vector.elementAt(0);
							functionality.jLabel2.setText(sconfig.getFirstCurrencySplit());
							populateRountingData();
						
						functionality.jLabel3.setText(sconfig.getSecondCurrencySPlit());
						 functionality. jTabbedPane2.setVisible(true);
						
						 } else {
							 
							 commonUTIL.
							 showAlertMessage("Currency split config for selected Currency Pair and Book not found.");
							 out.jCheckBox2.setSelected(false);
							 functionality.jPanel2.setVisible(false);
							
						 }
					} catch (RemoteException e1) {
						
						e1.printStackTrace();
					}
					//	if(vector == null || vector.isEmpty())
						//	return;
						 sconfig = null;// (CurrencySplitConfig)vector.elementAt(0);
					 
						//Book firstbook = new Book();
						//firstbook.setBookno(sconfig.getFirstSpotBook());
					//	firstbook = (Book) remoteReference.selectBook(firstbook);
						//functionality.jTextField0.setText(firstbook.getBook_name());
					//	Book secbook = new Book();
					//	secbook.setBookno(sconfig.getSecondSpotBook());
					//	secbook = (Book) remoteReference.selectBook(secbook);
					/*	if(functionality.jCheckBox0.isSelected()) {
						      getDataForcurrencySplitOnBook();
						      functionality.jLabel4.setText("First Spot Book");
								functionality.jLabel5.setText("Second Spot Book");
						} else {
							getDataForcurrencySplitOnCp();
							functionality.jLabel4.setText("First Legal Entity");
							functionality.jLabel5.setText("Second Legal Entity");
						}*/
						//functionality.jTextField1.setText(secbook.getBook_name());
				} else {
					 functionality.jButton8.setEnabled(false);
					 functionality.jPanel2.setVisible(false);
					// functionality.jLabel2.setText("    ");
					 //functionality.jLabel3.setText("    ");
					 functionality.jTextField2.setText("");
					 functionality.jTextField3.setText("");
					
				}
				
			}
	    });
			        
			     /*   functionality.jCheckBox0.addMouseListener(new java.awt.event.MouseAdapter() {
	
						@Override
						public void mouseClicked(MouseEvent e) {
							
							if( out.jCheckBox2.isSelected()) {
								if(trade == null) {
									commonUTIL.showAlertMessage("Select Trade ");
									out.jCheckBox2.setSelected(false);
									return;
								}
								
							//	 Vector vector = (Vector) remoteReference.getCurrencySplitConfig(trade.getBookId(), basicData.currencyPair.getText());
								//	if(vector == null || vector.isEmpty())
									//	return;
									 sconfig = null;// (CurrencySplitConfig)vector.elementAt(0);
								  //  functionality.jLabel2.setText(sconfig.getFirstCurrencySplit());
									//functionality.jLabel3.setText(sconfig.getSecondCurrencySPlit());
									//Book firstbook = new Book();
									//firstbook.setBookno(sconfig.getFirstSpotBook());
								//	firstbook = (Book) remoteReference.selectBook(firstbook);
									//functionality.jTextField0.setText(firstbook.getBook_name());
								//	Book secbook = new Book();
								//	secbook.setBookno(sconfig.getSecondSpotBook());
								//	secbook = (Book) remoteReference.selectBook(secbook);
									if(functionality.jCheckBox0.isSelected()) {
									      getDataForcurrencySplitOnBook();
									      functionality.jLabel4.setText("First Spot Book");
											functionality.jLabel5.setText("Second Spot Book");
									} else {
										getDataForcurrencySplitOnCp();
										functionality.jLabel4.setText("First Legal Entity");
										functionality.jLabel5.setText("Second Legal Entity");
									}
									//functionality.jTextField1.setText(secbook.getBook_name());
							} else {
								 functionality.jButton8.setEnabled(false);
								 functionality.jPanel2.setVisible(false);
								// functionality.jLabel2.setText("    ");
								 //functionality.jLabel3.setText("    ");
								 functionality.jTextField2.setText("");
								 functionality.jTextField3.setText("");
								
							}
							
						}
			        }); */
			        
    functionality.jTableR2.addMouseListener(new java.awt.event.MouseAdapter() {
    	@Override
		public void mouseClicked(MouseEvent e) {
    		int rowselected = functionality.jTableR2.getSelectedRow();
    		if(rowselected == -1)
    			return;
    		int tradeID = (Integer) functionality.jTableR2.getValueAt(rowselected,0);
    		if(tradeID == 0)
    			return;
    		app.trade = null;
    		Trade trade  = null;
			try {
				trade = remoteTrade.selectTrade(tradeID);
			} catch (RemoteException e1) {
				
				e1.printStackTrace();
			}
    		openTrade(trade);
    		
    	}
    });        
			        
	 // currencySPlit button
    functionality.jButton8.addMouseListener(new java.awt.event.MouseAdapter() {

		@Override
		public void mouseClicked(MouseEvent e) {
		/*	boolean usedCP = true;
			if(functionality.jCheckBox0.isSelected())
				usedCP = false;
			if(!functionality.jButton8.isEnabled())
				return;
			if(trade == null) {
				commonUTIL.showAlertMessage("Select Trade ");
				return;
			}
			if(out.jComboBox1.getSelectedIndex() == -1) {
				commonUTIL.showAlertMessage("Select Action");
				return;
			} else {
				trade.setAction(out.jComboBox1.getSelectedItem().toString());
			}
			try {
				sconfig = new CurrencySplitConfig();
				sconfig.setBookid(trade.getBookId());
			//	sconfig.setCurrencyToSplit(trade.get)
				sconfig.setFirstCurrencySplit(functionality.jLabel2.getSelectedItem().toString());
				sconfig.setSecondCurrencySPlit(functionality.jLabel3.getSelectedItem().toString());
			    if(usedCP) {
			    	sconfig.setFirstSpotBook(	(functionality.getCurrSPlitCP(functionality.jTextField0.getSelectedIndex())).getId());
			    	sconfig.setSecondSpotBook(	(functionality.getCurrSPlitCP(functionality.jTextField1.getSelectedIndex())).getId());
			    } else {
			    	sconfig.setFirstSpotBook(	(functionality.getCurrSPlitBook(functionality.jTextField0.getSelectedIndex())).getBookno());
			    	sconfig.setSecondSpotBook(	(functionality.getCurrSPlitBook(functionality.jTextField1.getSelectedIndex())).getBookno());
			    }
				//if(!isValidCurrencyPairSplit(sconfig.getFirstCurrencySplit(),sconfig.getSecondCurrencySPlit())) {
				//	commonUTIL.showAlertMessage("Select Valid CurrencyPair Selection");
					//return;
				//}
				if(sconfig == null)
					return;
			//	functionality.jLabel2.setText(sconfig.getFirstCurrencySplit());
			//	functionality.jLabel3.setText(sconfig.getSecondCurrencySPlit());
				Book orignalBook = new Book();
				orignalBook.setBookno(sconfig.getBookid());
				 orignalBook = (Book) remoteReference.selectBook(orignalBook);
			
				
					double frate = functionality.jTextField2.getDoubleValue();
					
					double srate = functionality.jTextField3.getDoubleValue();
				if(frate == 0. || srate == 0.) {
					commonUTIL.showAlertMessage("Currency Split rate is Zero");
					return ;
				}
			   // Trade offSetTrade = buildOffSetTrade(trade,orignalBook);
			    Trade xccY1 = (Trade) trade.clone();
			   	Trade xccY2 = (Trade) trade.clone();
			   	xccY1.setId(0);
				xccY2.setId(0);
			 	xccY1.setAction("NEW");
			 	xccY2.setAction("NEW");
			 	xccY1.setStatus("NONE");
			 	xccY2.setStatus("NONE");
			 	if(usedCP) {
			 	   xccY1.setBookId(orignalBook.getBookno()); // if cp used book will be trade book id 
			 	   xccY1.setCpID(sconfig.getFirstSpotBook());  // cp used from selected curr split config
			 	   xccY1.setTradedesc(sconfig.getFirstCurrencySplit());
			 	  xccY2.setBookId(orignalBook.getBookno());
			 	   xccY2.setCpID(sconfig.getSecondSpotBook());
			 	   xccY2.setTradedesc(sconfig.getSecondCurrencySPlit());
			 	   xccY2.setCurrency(xccY2.getTradedesc().substring(4, 7));
			 	  xccY1.setCurrency(xccY1.getTradedesc().substring(4, 7));
			 	} else {
			 		xccY1.setBookId(sconfig.getFirstSpotBook()); 
				 	xccY1.setCpID(trade.getCpID());
				 	xccY1.setTradedesc(sconfig.getSecondCurrencySPlit());
			 		xccY2.setBookId(sconfig.getSecondSpotBook());
				 	xccY2.setCpID(trade.getCpID());
				 	xccY2.setTradedesc(sconfig.getSecondCurrencySPlit());
				 	 xccY2.setCurrency(xccY2.getTradedesc().substring(4, 7));
				 	  xccY1.setCurrency(xccY1.getTradedesc().substring(4, 7));
			 	}
			 	
			   
			   Vector<Trade> splitTrades = buildXccySplit(xccY1,xccY2,trade,sconfig);
			   if(splitTrades == null)
				   return;
			 //  splitTrades.add(offSetTrade);
			   if(splitTrades.size() > 0) {
			  trade = remoteTrade.saveBatchSplitTrades(splitTrades, trade);
			  openTrade(trade);
			//  Vector splictrde = remoteTrade.getSplitTrades(trade.getId());
			 // splictrde.add(trade);
			//  functionality.setRountingData(splictrde);
			   }
			    
			} catch (RemoteException e1) {
				
				e1.printStackTrace();
			} catch (NumberFormatException e1) {
				
				commonUTIL.showAlertMessage("Enter Number in Rate Field");
				return;
			}
			catch (CloneNotSupportedException e1) {
				
				e1.printStackTrace();
			}	catch (ParseException oe) {
					
					oe.printStackTrace();
				} */
			
		}
		
		private Vector<Trade> buildXccySplit(Trade xccY1,Trade xccY2,Trade trade,CurrencySplitConfig sconfig) {
			Vector<Trade> xccySplit = new Vector<Trade>();
			
				double frate;
				Vector<Trade> trades = null;
				try {
				
			
			double srate = functionality.jTextField3.getDoubleValue();
			frate = functionality.jTextField2.getDoubleValue();
			if(frate == 0. || srate == 0.) {
				commonUTIL.showAlertMessage("Currency Split rate is Zero");
				return null;
			}
			
	//		trades  = FXSplitUtil.splitTrade(xccY1,xccY2,trade,frate,srate);
				} catch (ParseException e) {
					
					e.printStackTrace();
				}
		
			return trades;
			/*if(trade.getType().equalsIgnoreCase("BUY")) {
				xccY1.setType("BUY");
				xccY2.setType("SELL");
				xccY1.setCurrency(xccY1.getTradedesc().substring(5, 7));
				xccY2.setCurrency(xccY2.getTradedesc().substring(5, 7));
				xccY1.setNominal(trade.getQuantity() * -1); //
				xccY1.setQuantity(trade.getQuantity() / frate);
				xccY1.setPrice(frate);
				
				
				xccY2.setQuantity(xccY1.getQuantity() *-1);
				xccY2.setNominal(xccY2.getQuantity() * srate * -1);
				xccY2.setPrice(frate);
				
			
				
				
				xccY1.setAttribute("XccySplitFrom",  Integer.valueOf(trade.getId()).toString());
				xccY2.setAttribute("XccySplitFrom",  Integer.valueOf(trade.getId()).toString());
				xccY2.setPrice(srate);
				xccY1.setPrice(frate);
				xccY2.setAutoType("XccySplit");
				xccY1.setAutoType("XccySplit");
				xccY1.setXccySPlitid(trade.getId());
				xccY2.setXccySPlitid(trade.getId());
			} else {
				xccY1.setType("SELL");
				xccY2.setType("BUY");
				xccY2.setPrice(srate);
				xccY1.setPrice(frate);
				
				xccY1.setCurrency(xccY1.getTradedesc().substring(5, 7));
				xccY2.setCurrency(xccY2.getTradedesc().substring(5, 7));
				xccY1.setNominal(trade.getQuantity() * -1); //
				xccY1.setQuantity(trade.getQuantity() / frate);
				xccY1.setPrice(frate);
				
				
				xccY2.setQuantity(xccY1.getQuantity() *-1);
				xccY2.setNominal(xccY2.getQuantity() * srate * -1);
				xccY2.setPrice(frate);
				
			
				
				xccY1.setAttribute("XccySplitFrom",  Integer.valueOf(trade.getId()).toString());
				xccY2.setAttribute("XccySplitFrom",  Integer.valueOf(trade.getId()).toString());
				xccY2.setAutoType("XccySplit");
				xccY1.setAutoType("XccySplit");
				xccY1.setXccySPlitid(trade.getId());
				xccY2.setXccySPlitid(trade.getId());
				
			}
			xccySplit.add(xccY1);
			xccySplit.add(xccY2);
			} catch (ParseException e) {
				
				e.printStackTrace();
			}
			return xccySplit; */
		}

		// this logic is wrong needs to be correct
		private boolean isValidCurrencyPairSplit(String firstCP,String secondCP) {
			String fcp = firstCP.substring(0, 3);
			String scp = secondCP.substring(4, 7);
			if(trade.getTradedesc().equalsIgnoreCase(fcp+"/"+scp))
				return true;
			if(trade.getTradedesc().equalsIgnoreCase(scp+"/"+fcp))
				return true;
			fcp = firstCP.substring(4, 7);
			 scp = secondCP.substring(0, 3);
			 if(trade.getTradedesc().equalsIgnoreCase(fcp+"/"+scp))
					return true;
			 if(trade.getTradedesc().equalsIgnoreCase(fcp+"/"+scp))
					return true;
			 fcp = secondCP.substring(0, 3);
			 scp = firstCP.substring(4, 7);
			 if(trade.getTradedesc().equalsIgnoreCase(fcp+"/"+scp))
					return true;
			 fcp = secondCP.substring(4, 7);
			 scp = firstCP.substring(0, 3);
			 if(trade.getTradedesc().equalsIgnoreCase(fcp+"/"+scp))
					return true;
			 
			 return false;
		}
		private Trade buildOffSetTrade(Trade trade,Book poBook) {
			
			Trade offSetTrade = null;
			try {
				offSetTrade = (Trade) trade.clone();
				offSetTrade.setId(0);
				offSetTrade.setAction("NEW");
				offSetTrade.setStatus("NONE");
				if(offSetTrade.getType().equalsIgnoreCase("BUY")) {
				     offSetTrade.setType("SELL");
				     offSetTrade.setQuantity(offSetTrade.getQuantity() * -1);
				     offSetTrade.setNominal(offSetTrade.getNominal() * -1);
				} else  {
					 offSetTrade.setType("BUY");
					 offSetTrade.setQuantity(offSetTrade.getQuantity() * -1);
				     offSetTrade.setNominal(offSetTrade.getNominal() * -1);
				}
				offSetTrade.setAutoType("Offset");
				offSetTrade.setOffsetid(trade.getId());
				offSetTrade.setCpID(poBook.getLe_id());
				
				offSetTrade.setAttribute("XCurrSOriginalTradeID", Integer.valueOf(trade.getId()).toString());
				
				
			} catch (CloneNotSupportedException e) {
				
				e.printStackTrace();
			}
			return offSetTrade;
		}
    });
	        
    out.jTextField4.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent e) {
	    	 if(basicData.buysell.getText().equalsIgnoreCase("BUY/SELL") || basicData.buysell.getText().equalsIgnoreCase("BUY")) {
					
					double amt1 =  (new Double(out.jTextField1.getText()).doubleValue());
					amt1 = Math.abs(amt1);
					double spot = (new Double(out.jTextField4.getText()).doubleValue());
					out.jTextField1.setText(commonUTIL.getStringFromDoubleExp(amt1));
					double amt2 = amt1* spot;								
					out.jTextField2.setText(commonUTIL.getStringFromDoubleExp(amt2));
					
					 double farAmt1 = amt1 * -1;
			    	 swap.jTextField1.setText(commonUTIL.getStringFromDoubleExp(farAmt1));
			    	// System.out.println("farAmt1 " + farAmt1);
			    	// System.out.println("out.jTextField2"+ out.jTextField2.getText());
			    	 //System.out.println("out.jTextField1"+ out.jTextField1.getText());
			    	 //System.out.println("swap.jTextField2"+ swap.jTextField1.getText());
			    	 if (! commonUTIL.isEmpty(swap.jTextField4.getText()) && (!swap.jTextField4.getText().equalsIgnoreCase("0"))) {
			    		 
			    		 double farRate = (new Double(swap.jTextField4.getText()).doubleValue());
			    		 double farAmt2 = Math.abs(farAmt1);								
			    		 if(farRate !=0)
			    		 swap.jTextField2.setText(commonUTIL.getStringFromDoubleExp(farAmt2*farRate));
			    	 }
					
	    	 } else {
	    		 
	    		 	double amt1 =  (new Double(out.jTextField1.getText()).doubleValue());
					double spot = (new Double(out.jTextField4.getText()).doubleValue());
					out.jTextField1.setText(commonUTIL.getStringFromDoubleExp(amt1));
					double amt2 = Math.abs(amt1) * -1;		
				//	System.out.println("------------- "+commonUTIL.getStringFromDoubleExp(amt2*spot));
					out.jTextField2.setText(commonUTIL.getStringFromDoubleExp(amt2*spot));
					
					 double farAmt1 = amt1 * -1;
			    	 swap.jTextField1.setText(commonUTIL.getStringFromDoubleExp(farAmt1));
			    	 
			    	 if (! commonUTIL.isEmpty(swap.jTextField4.getText())) {
			    		 
			    		 double farRate = (new Double(swap.jTextField4.getText()).doubleValue());
			    		 double farAmt2 = Math.abs(farAmt1) * -1;	
			    		 if(farRate != 0)
			    		 swap.jTextField2.setText(commonUTIL.getStringFromDoubleExp(farAmt2*farRate).toString());
			    	 }	    		 																
	    	 }	    	
	          populateRountingData();		
	    }
    });
			        
    
    
    basicData.jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
		 public void mouseClicked(MouseEvent e) {
			 
			 if( basicData.buysell.getText().equalsIgnoreCase("BUY")) {
				 basicData. buysell.setText("SELL");
				 
			} else {
				if(!productSubType.equalsIgnoreCase("FXSWAP")) {
				      basicData.buysell.setText("BUY");
				}
			}
			 
			 if( basicData.buysell.getText().equalsIgnoreCase("BUY/SELL") ) {
				 basicData. buysell.setText("SELL/BUY");
				 basicData. buysell.setBackground(Color.red);				 
			} else {
				if(productSubType.equalsIgnoreCase("FXSWAP")) {
					basicData.buysell.setText("BUY/SELL");
					basicData. buysell.setBackground(Color.green);
				}
			}
			 
		 if(basicData.buysell.getText().equalsIgnoreCase("SELL")) {
			
			double amt1 =  (new Double(out.jTextField1.getText()).doubleValue());
			amt1 = Math.abs(amt1) * -1;
			out.jTextField1.setText(commonUTIL.getStringFromDoubleExp(amt1).toString());
			 double amt2 = Math.abs( (new Double(out.jTextField2.getText()).doubleValue()));
			 out.jTextField2.setText(commonUTIL.getStringFromDoubleExp(amt2).toString());
			
		 }  else if(basicData.buysell.getText().equalsIgnoreCase("BUY")) {
				 double amt2 =  (new Double(out.jTextField2.getText()).doubleValue());
				 amt2 = Math.abs(amt2) * -1;
				 out.jTextField2.setText(commonUTIL.getStringFromDoubleExp(amt2).toString());
				 double amt1= Math.abs( (new Double(out.jTextField1.getText()).doubleValue()));
				 out.jTextField1.setText(commonUTIL.getStringFromDoubleExp(amt1).toString());
			 }
			 
			 if(basicData.buysell.getText().equalsIgnoreCase("SELL/BUY")) {
					
				 double amt1 =  (new Double(out.jTextField1.getText()).doubleValue());
					amt1 = Math.abs(amt1) * -1;
					out.jTextField1.setText(commonUTIL.getStringFromDoubleExp(amt1).toString());
					 double amt2 = Math.abs( (new Double(out.jTextField2.getText()).doubleValue()));
					 out.jTextField2.setText(commonUTIL.getStringFromDoubleExp(amt2).toString());
					 
					 double samt2 =  (new Double(swap.jTextField1.getText()).doubleValue());
					 samt2 = Math.abs(samt2) * -1;
					 double samt1= Math.abs( (new Double(swap.jTextField2.getText()).doubleValue()));
					 swap.jTextField2.setText(commonUTIL.getStringFromDoubleExp(amt2));
					
					 swap.jTextField1.setText(commonUTIL.getStringFromDoubleExp(samt1).toString());
					
				 }  else if(basicData.buysell.getText().equalsIgnoreCase("BUY/SELL")) {
					 double amt2 =  (new Double(out.jTextField2.getText()).doubleValue());
					 amt2 = Math.abs(amt2) * -1;
					 out.jTextField2.setText(commonUTIL.getStringFromDoubleExp(amt2).toString());
					 double amt1= Math.abs( (new Double(out.jTextField1.getText()).doubleValue()));
					 out.jTextField1.setText(commonUTIL.getStringFromDoubleExp(amt1).toString());
					 
					 double samt1 =  (new Double(swap.jTextField1.getText()).doubleValue());
						samt1 = Math.abs(samt1) * -1;
						swap.jTextField1.setText(commonUTIL.getStringFromDoubleExp(samt1).toString());
						 double samt2 = Math.abs( (new Double(swap.jTextField2.getText()).doubleValue()));
						 swap.jTextField2.setText(commonUTIL.getStringFromDoubleExp(samt2).toString());
					
				 }
			 
			 populateRountingData();
			 
		 }
	 });
			        
			    /*    out.jCheckBox1.addMouseListener(new java.awt.event.MouseAdapter() {
						 public void mouseClicked(MouseEvent e) {
							 if(!basicData.jRadioButton2.isSelected()) {
								 
							 if(out.jCheckBox1.isSelected()) {
								 SimpleDateFormat formator = new SimpleDateFormat("dd/MM/yyyy");
							     out.outRightDate.setDateFormat(formator);
							     out.outRightDate.setSelectedDate(commonUTIL.getCurrentDate());
							    
								 productSubType = "FX";
						
								  
							 } else {
								 productSubType = "FXForward";
							 } 
						 } else {
							 productSubType = "FXSWAP";
						 }
				 
						
					}
			
			 
			 
						 
					
						 
		          });  */
			        
			        basicData.currencyPair.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							
							//out.jLabel2.setText(basicData.currencyPair.getText());
							
						}
					
						
						
					}); 
					 
					 out.outRightDate.addKeyListener(new KeyAdapter() {
						 public void  keyTyped(KeyEvent e) {
				int count =  functionality.jTabbedPane1.getTabCount();
				for(int i = 0;i < count ;i++) {
					String tabName = functionality.jTabbedPane1.getTitleAt(i);
					if(tabName.equalsIgnoreCase("Tenor")) {
						functionality.jTabbedPane1.setSelectedIndex(i);
						
					}
				}
			 }
	 
		          });  
					 
					 
					 basicData.book.addKeyListener(new KeyAdapter() {
						 public void  keyTyped(KeyEvent e) {
				int count =  functionality.jTabbedPane1.getTabCount();
				for(int i = 0;i < count ;i++) {
					String tabName = functionality.jTabbedPane1.getTitleAt(i);
					if(tabName.equalsIgnoreCase("Book")) {
						functionality.jTabbedPane1.setSelectedIndex(i);
						
					}
				}
			 }
			 
		});
		    
					 basicData.book.addKeyListener(new KeyAdapter() {
						 public void  keyTyped(KeyEvent e) {
				int count =  functionality.jTabbedPane1.getTabCount();
				for(int i = 0;i < count ;i++) {
					String tabName = functionality.jTabbedPane1.getTitleAt(i);
					if(tabName.equalsIgnoreCase("Book")) {
						functionality.jTabbedPane1.setSelectedIndex(i);
						
					}
				}
			 }
			 
		});
		    
		 out.jTextField5.addKeyListener(new KeyAdapter() {
	
	            @Override
	            public void keyTyped(KeyEvent e) {
	                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
	                 try {	
	                   String number = out.jTextField5.getText();
	                   int tradeId = Integer.parseInt(number); 
	                   trade = (Trade) remoteTrade.selectTrade(tradeId);
	                   if(trade != null) {
	                	   if(! trade.getProductType().equalsIgnoreCase(productType)) {
	                     	  commonUTIL.showAlertMessage("This Trade will not open in current Window");
	                     	  return;
	                       }
	                	   setTrade(trade);
	                	   app.trade = trade;
						openTrade(trade);
						rollpanel.setVisible(false);
						//if(e.getClickCount() == 2) 
						 getTradeTask(taskPanel);
						 getTradeSDI(sdiPanel);
	                   } else {
	                	   commonUTIL.showAlertMessage("Trade does not Exists with id " + tradeId);
	                	   return;
	                   }
	                   
	                 } catch(NumberFormatException n) {
	                	 out.jTextField5.setText("");
	                	 commonUTIL.showAlertMessage("Enter Number only " );
	                	 
	                 } catch(RemoteException r) {
	                	commonUTIL.displayError("FXTradePanel", "init" , r);
	                 }
	                }
	            }
	        });
		 
		 
					 basicData.counterPary.addKeyListener(new KeyAdapter() {
						 public void  keyTyped(KeyEvent e) {
				int count =  functionality.jTabbedPane1.getTabCount();
				for(int i = 0;i < count ;i++) {
					String tabName = functionality.jTabbedPane1.getTitleAt(i);
					if(tabName.equalsIgnoreCase("CounterParty")) {
						functionality.jTabbedPane1.setSelectedIndex(i);
						
					}
				}
			 }
			 
		          });   
					 basicData.currencyPair.addKeyListener(new KeyAdapter() {
						 public void  keyTyped(KeyEvent e) {
				int count =  functionality.jTabbedPane1.getTabCount();
				for(int i = 0;i < count ;i++) {
					String tabName = functionality.jTabbedPane1.getTitleAt(i);
					if(tabName.equalsIgnoreCase("CurrencyPair")) {
						functionality.jTabbedPane1.setSelectedIndex(i);
						
					}
				}
			 }
			 
		          });  
					 
			        
			        swap.jTextField1.addActionListener(new java.awt.event.ActionListener() {
					    public void actionPerformed(java.awt.event.ActionEvent e) {
					    	try{
					    	 if(basicData.buysell.getText().equalsIgnoreCase("BUY/SELL")) {
								  		 double amt1 =  (new Double(swap.jTextField1.getText()).doubleValue());
									amt1 = Math.abs(amt1) * -1;
									double Fspot = (new Double(swap.jTextField4.getText()).doubleValue());
									
									double amt2 = Math.abs(amt1 *-1);								
									swap.jTextField2.setText(commonUTIL.getStringFromDoubleExp(amt2 * Fspot).toString());
									
					    	 } else {
					    		 
					    		 double amt1 =  (new Double(swap.jTextField1.getText()).doubleValue());
									amt1 = Math.abs(amt1) ;
									double Fspot = (new Double(swap.jTextField4.getText()).doubleValue());
									
									double amt2 = Math.abs(amt1) * -1;								
									swap.jTextField2.setText(commonUTIL.getStringFromDoubleExp(amt2 * Fspot).toString());
									
									
					    		 
					    	 }
					    	 populateRountingData();
					    }catch(NumberFormatException e1) {
	                		commonUTIL.showAlertMessage("Enter Number ");
	                	}
						
					 }
				 });
			        
			        // basic amt1 field
			        out.jTextField1.addKeyListener(new KeyAdapter() {
	
		                @Override
		                public void keyTyped(KeyEvent e) {
		                    if (e.getKeyChar() == KeyEvent.VK_ENTER) {
		                    	try{
		                    	String type = basicData.buysell.getText();
		                    	if(type.equalsIgnoreCase("SELL/BUY")) {
		                    		type = "SELL";
		                    	}
		                    	if(type.equalsIgnoreCase("BUY/SELL")) {
		                    		type = "BUY";
		                    	}
		                    	if(type.equalsIgnoreCase("SELL")) {
									
									double amt1 =  (new Double(out.jTextField1.getText()).doubleValue());
									amt1 = Math.abs(amt1) * -1;
									double spot = (new Double(out.jTextField4.getText()).doubleValue());
									out.jTextField1.setText(commonUTIL.getStringFromDoubleExp(amt1).toString());
									double amt2 = Math.abs(amt1);								
									out.jTextField2.setText(commonUTIL.getStringFromDoubleExp((amt1 * -1) * spot).toString());
									
					    	 } else {
					    		 
					    		 double amt1 =  (new Double(out.jTextField1.getText()).doubleValue());
									amt1 = Math.abs(amt1) ;
									double spot = (new Double(out.jTextField4.getText()).doubleValue());
									out.jTextField1.setText(commonUTIL.getStringFromDoubleExp(amt1).toString());
									double amt2 = Math.abs(amt1) * -1;								
									out.jTextField2.setText(commonUTIL.getStringFromDoubleExp((amt1 * -1) * spot).toString());
					    		 
					    	 }
		                    	populateRountingData();
		                    }catch(NumberFormatException e1) {
	                    		commonUTIL.showAlertMessage("Enter Number ");
	                    	}
		                    	 
		                    }
		                  
		                }
		              
		            });
				 
			        
			        // amt2 field
			        out.jTextField2.addKeyListener(new KeyAdapter() {
	
		                @Override
		                public void keyTyped(KeyEvent e) {
		                    if (e.getKeyChar() == KeyEvent.VK_ENTER) {
		                    	try{
		                    	String type = basicData.buysell.getText();
		                    	if(type.equalsIgnoreCase("SELL/BUY")) {
		                    		type = "SELL";
		                    	}
		                    	if(type.equalsIgnoreCase("BUY/SELL")) {
		                    		type = "BUY";
		                    	}
		                    	if(type.equalsIgnoreCase("SELL")) {
									
									double amt2 =  (new Double(out.jTextField2.getText()).doubleValue());
									amt2 = Math.abs(amt2);
									double spot = (new Double(out.jTextField4.getText()).doubleValue());
									out.jTextField2.setText(commonUTIL.getStringFromDoubleExp(amt2).toString());
									double amt1 = Math.abs(amt2) * -1;								
									out.jTextField1.setText(commonUTIL.getStringFromDoubleExp(amt1 / spot).toString());
									
					    	 } else {
					    		 
					    		 double amt2 =  (new Double(out.jTextField2.getText()).doubleValue());
									amt2 = Math.abs(amt2) * -1 ;
									double spot = (new Double(out.jTextField4.getText()).doubleValue());
									out.jTextField2.setText(commonUTIL.getStringFromDoubleExp(amt2).toString());
									double amt1 = Math.abs(amt2);								
									out.jTextField1.setText(commonUTIL.getStringFromDoubleExp((amt2 *-1)/ spot).toString());
					    		 
					    	 }
		                    	populateRountingData();
		                    	}catch(NumberFormatException e1) {
		                    		commonUTIL.showAlertMessage("Enter Number ");
		                    	}
		                    }
		                }
		            });
			        // spot field
			        out.jTextField4.addKeyListener(new KeyAdapter() {
	
		                @Override
		                public void keyTyped(KeyEvent e) {
		                    if (e.getKeyChar() == KeyEvent.VK_ENTER) {
		                    	try{
		                    	if(basicData.buysell.getText().equalsIgnoreCase("SELL")) {
									
									double amt1 =  (new Double(out.jTextField1.getText()).doubleValue());
									amt1 = Math.abs(amt1) * -1;
									double spot = (new Double(out.jTextField4.getText()).doubleValue());
									out.jTextField1.setText(commonUTIL.getStringFromDoubleExp(amt1).toString());
									double amt2 = Math.abs(amt1);								
									out.jTextField2.setText(commonUTIL.getStringFromDoubleExp(amt2 * spot).toString());
									
					    	 } else {
					    		 
					    		 double amt1 =  (new Double(out.jTextField1.getText()).doubleValue());
									amt1 = Math.abs(amt1) ;
									double spot = (new Double(out.jTextField4.getText()).doubleValue());
									out.jTextField1.setText(commonUTIL.getStringFromDoubleExp(amt1).toString());
									double amt2 = Math.abs(amt1) * -1;								
									out.jTextField2.setText(commonUTIL.getStringFromDoubleExp(amt1 * spot).toString());
					    		 
					    	 }
		                    	populateRountingData();
		                    	}catch(NumberFormatException e1) {
		                    		commonUTIL.showAlertMessage("Enter Number ");
		                    	}
		                    }
		                }
		                
		               
		            });
			        swap.jTextField4.addKeyListener(new KeyAdapter() {
	
		                @Override
		                public void keyTyped(KeyEvent e) {
		                    if (e.getKeyChar() == KeyEvent.VK_ENTER) {
		                    	try{
		                    	if(basicData.buysell.getText().equalsIgnoreCase("SELL/BUY")) {
									
									
		                    		 double amt1 =  (new Double(swap.jTextField1.getText()).doubleValue());
										amt1 = Math.abs(amt1) ;
										double spot = (new Double(swap.jTextField4.getText()).doubleValue());
										swap.jTextField1.setText(commonUTIL.getStringFromDoubleExp(amt1).toString());
										double amt2 = Math.abs(amt1) * -1;								
										swap.jTextField2.setText(commonUTIL.getStringFromDoubleExp(amt2* spot).toString());
		                    		
									
					    	 } else {
					    		 
					    		 double amt1 =  (new Double(swap.jTextField1.getText()).doubleValue());
									amt1 = Math.abs(amt1) * -1;
									double spot = (new Double(swap.jTextField4.getText()).doubleValue());
									swap.jTextField1.setText(commonUTIL.getStringFromDoubleExp(amt1).toString());
									double amt2 = Math.abs(amt1);								
									swap.jTextField2.setText(commonUTIL.getStringFromDoubleExp(amt2*spot).toString());
					    		 
					    	 }
		                    	String type = basicData.buysell.getText();
		                    	if(type.equalsIgnoreCase("SELL/BUY")) {
		                    		type = "SELL";
		                    	}
		                    	if(type.equalsIgnoreCase("BUY/SELL")) {
		                    		type = "BUY";
		                    	}
		                    	if(type.equalsIgnoreCase("SELL")) {
									
									double amt1 =  (new Double(out.jTextField1.getText()).doubleValue());
									amt1 = Math.abs(amt1) * -1;
									double spot = (new Double(out.jTextField4.getText()).doubleValue());
									out.jTextField1.setText(commonUTIL.getStringFromDoubleExp(amt1).toString());
									double amt2 = Math.abs(amt1);								
									out.jTextField2.setText(commonUTIL.getStringFromDoubleExp(amt2 * spot).toString());
									
					    	 } else {
					    		 
					    		 double amt1 =  (new Double(out.jTextField1.getText()).doubleValue());
									amt1 = Math.abs(amt1) ;
									double spot = (new Double(out.jTextField4.getText()).doubleValue());
									out.jTextField1.setText(commonUTIL.getStringFromDoubleExp(amt1));
									double amt2 = Math.abs(amt1) * -1;								
									out.jTextField2.setText(commonUTIL.getStringFromDoubleExp(amt2 * spot));
					    		 
					    	 }
		                    	populateRountingData();
		                    	 }catch(NumberFormatException e1) {
			                    		commonUTIL.showAlertMessage("Enter Number ");
			                    	}
		                    }
		                   
		                }
		            });
			     // swap forward rate field
			        out.jTextField4.addKeyListener(new KeyAdapter() {
	
		                @Override
		                public void keyTyped(KeyEvent e) {
		                    if (e.getKeyChar() == KeyEvent.VK_ENTER) {
		                    	try{
		                    	String type = basicData.buysell.getText();
		                    	if(type.equalsIgnoreCase("SELL/BUY")) {
		                    		type = "SELL";
		                    	}
		                    	if(type.equalsIgnoreCase("BUY/SELL")) {
		                    		type = "BUY";
		                    	}
		                    	if(type.equalsIgnoreCase("SELL")) {
									
									double amt1 =  (new Double(out.jTextField1.getText()).doubleValue());
									amt1 = Math.abs(amt1) * -1;
									double spot = (new Double(out.jTextField4.getText()).doubleValue());
									out.jTextField1.setText(commonUTIL.getStringFromDoubleExp(amt1).toString());
									double amt2 = Math.abs(amt1);								
									out.jTextField2.setText(commonUTIL.getStringFromDoubleExp(amt2 * spot).toString());
									
					    	 } else {
					    		 
					    		 double amt1 =  (new Double(out.jTextField1.getText()).doubleValue());
									amt1 = Math.abs(amt1) ;
									double spot = (new Double(out.jTextField4.getText()).doubleValue());
									out.jTextField1.setText(commonUTIL.getStringFromDoubleExp(amt1));
									double amt2 = Math.abs(amt1) * -1;								
									out.jTextField2.setText(commonUTIL.getStringFromDoubleExp(amt2 * spot));
					    		 
					    	 }
		                    	populateRountingData();
		                    }catch(NumberFormatException e1) {
	                    		commonUTIL.showAlertMessage("Enter Number ");
	                    	}
		                    }
		                }
		              
		            });
			        
			        // caching can be done in this process. 
			        
			        functionality.jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
	
						@Override
						public void mouseClicked(MouseEvent e) {
							
							if (!functionality.jButton1.isEnabled()) {
								return;
							}
							
							if(!favEnableFlag) {
							int i = functionality.jTabbedPane1.getSelectedIndex();
							//functionality.jTabbedPane1.addt
							String name = functionality.jTabbedPane1.getTitleAt(i);
							
								__rows = getRows("CurrencyPair");
								basicData.currencyPair.setText(((JButton) __rows[0][0]).getName());
								__table = fillFavourites(__rows,basicData.currencyPair,null,out.jLabel2,false,"CurrencyPair");
								functionality.jTabbedPane1.removeAll();
							functionality.jTabbedPane1.add("CurrencyPair",__table);
							__rows = getRows("Tenor");
						//	if(productSubType.equalsIgnoreCase(FXSWAP)) {
							__table = fillFavourites(__rows,out.outRightDate,swap.swapDate,null,true,"Date");
						//	} else {
							//__table = fillFavourites(__rows,out.outRightDate,null,true,"Date");
							//}
							functionality.jTabbedPane1.add("Tenor",__table);
							__rows = getRows("CounterParty");
							
							__table = fillFavourites(__rows,basicData.counterPary,null,null,false,mirrorBook,"CounterParty");
							functionality.jTabbedPane1.add("CounterParty",__table);
							__rows = getRows("Book");
							__table = fillFavourites(__rows,basicData.book,null,null,false,"Book");
							functionality.jTabbedPane1.add("Book",__table);
							__rows = getRows("Trader");
							__table = fillFavourites(__rows,basicData.jTextField7,null,null,false,"Trader");
							functionality.jTabbedPane1.add("Trader",__table);
							favEnableFlag = true;
							if(productSubType.equalsIgnoreCase(FXFORWARDOPTION)) {
							String currP = basicData.currencyPair.getText();
							fwdOp.jLabel2.setText(currP.substring(0, 3));
							 fwdOp.jLabel3.setText(currP.substring(4, 7));
							}
							functionality.jTabbedPane1.setVisible(true);
							} 
							
						}
						
						
		});
				    
			       /// NEW BUTTON functionality
			        functionality.jButton4.addMouseListener(new java.awt.event.MouseAdapter() {
	
						@Override
						public void mouseClicked(MouseEvent e) {
							
							if (!functionality.jButton4.isEnabled()) {
								return;
							}
							
							newTradeView();
						
							functionality.jButton5.setText("DEAL");
							functionality.jButton6.setEnabled(false);
						}
			    });
			        // ROLLOVER & ROLLBACK Functionality 
			        functionality.jButton7.addMouseListener(new java.awt.event.MouseAdapter() {
	
						@Override
						public void mouseClicked(MouseEvent e) {
							if(! functionality.jButton7.isEnabled())
								return;
						double rollRate;
						double rollAmt = 0.0;
						boolean parital = false;
						try {
							if(out.jComboBox1.getSelectedIndex() == -1)  {
								commonUTIL.showAlertMessage("Select Action");
								return;
							}
							if(out.jComboBox1.getSelectedItem().toString().equalsIgnoreCase("ROLLBACK") || out.jComboBox1.getSelectedItem().toString().equalsIgnoreCase("ROLLOVER")) { 
									if(rollpanel.jCheckBox0.isSelected()) 
										parital = true;
										rollAmt =  rollpanel.jTextField2.getDoubleValue();
										rollRate = rollpanel.jTextField1.getDoubleValue();
										if(trade == null)
											return;
										if(rollpanel.jCheckBox0.isSelected()) {
													if(trade.getQuantity() > 0.) {
															if(rollAmt > trade.getQuantity()  ) {
																	commonUTIL.showAlertMessage("ROll Amt greater then Primary Curr");
																	return;
															}
															if( rollAmt < 0 ) {
																commonUTIL.showAlertMessage("ROll Amt is negative as Primary Curr is postive");
																return;
																}
													}
													if(trade.getQuantity() < 0.) {
															if(rollAmt < trade.getQuantity() ) {
																commonUTIL.showAlertMessage("ROll Amt greater then Primary Curr");
																return;
															}
															if( rollAmt > 0 ) {
																commonUTIL.showAlertMessage("ROll Amt is Postive as Primary Curr is Negative");
																return;
															}
													}
											
										}
										String roll = functionality.jButton7.getText();
										DateU dateRolldate = DateU.valueOf(rollpanel.jTextField0.getSelectedDate());
										if(roll.equalsIgnoreCase("ROLLOVER")) {
												DateU dateFWDDate = DateU.valueOf(out.outRightDate.getSelectedDate());
												if(dateRolldate.lte(dateFWDDate)) {
													commonUTIL.showAlertMessage("ROLLOVER Date before OutRight Date");
													return;
												}
										}
										if(roll.equalsIgnoreCase("ROLLBACK")) {
											DateU dateFWDDate = DateU.valueOf(out.outRightDate.getSelectedDate());
											if(dateFWDDate.lte(dateRolldate)) {
												commonUTIL.showAlertMessage("OutRight  Date before ROLLBack Date");
												return;
											}
										}
										if(rollRate > 0.) {
								//trade.setAction("ROLL);
								
												trade.setAction(roll);
												if(!remoteTrade.isValidAction(trade)) {
													commonUTIL.showAlertMessage("Action "+roll + " not valid on" + trade.getStatus());
													return;
												}
												int r= 0;
												if(trade.getTradedesc1().equalsIgnoreCase(FXSWAP)) {
														r = fillRollSwapTrade(trade,roll,trade.getId(),parital,rollAmt);
												} else {
													r = fillRollOutRightTrade(trade,roll,trade.getId(),parital,rollAmt);
												}
																Vector mess = new Vector();
												if(roll.equalsIgnoreCase("ROLLOVER")) {
													attributeDataValue = trade.getAttributeH();
													attributeDataValue.put("rollOverTO", Integer.toString(r));
													trade.setRollOverTo(r);
												}  else {
													attributeDataValue = trade.getAttributeH();
													attributeDataValue.put("rollBackTO", Integer.toString(r));
													trade.setRollBackTo(r);
												}
												trade.setAttributes(getAttributeValue());
												Vector tradestatus = remoteTrade.saveTrade(trade,mess);
												if(commonUTIL.isEmpty(tradestatus)) {
								               		commonUTIL.showAlertMessage("Error in ServerSide in saving Trade");
								               		 return;
								               	 }
												String statusT = (String) tradestatus.elementAt(0);
												int i = ((Integer) tradestatus.elementAt(1)).intValue();
								 
												if(i == -4) {
														commonUTIL.showAlertMessage((statusT));
														return;
												}
												if(i == -3) {
													commonUTIL.showAlertMessage((statusT));
													return;
												}
												if(i > 0) 
													commonUTIL.showAlertMessage((statusT));
				               	 //System.out.println("*************** " +i);
												trade = (Trade) remoteTrade.selectTrade(i);
												openTrade(trade);
												basicData.jRadioButton6.setEnabled(false);
												basicData.jRadioButton7.setEnabled(false);
												functionality.jButton7.setEnabled(false);
							    
										} else {
											commonUTIL.showAlertMessage("Enter Rate " );
										}
							} else {
								commonUTIL.showAlertMessage("Select Rollover or RollBack Action");
								return;
							
							}
								
							
						} catch (ParseException e1) {
							
							e1.printStackTrace();
						} catch (RemoteException e1) {
							
							e1.printStackTrace();
						
						
							}catch (NumberFormatException e1) {
								
								commonUTIL.showAlertMessage("Enter Number ");
							
							
								}
								
							
						}
						
			    
			    	
			    });
			        // internal functional. 
			        
			       
			        functionality.jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
			        	  
			        	@Override
						public void mouseClicked(MouseEvent e) {
			        		
			        		if (!functionality.jButton3.isEnabled()) {
								return;
							}
			        		
			        		if(trade == null) {
			        			commonUTIL.showAlertMessage("Select Trade");
			        			return;
			        		}
			        	//	Hashtable<Integer,Book> poBooks = new Hashtable<Integer,Book>();
			        		DefaultTableModel booktablemodel12 = new DefaultTableModel(s,0);
			        		getBooksOnPoDataCombo1(booktablemodel12, getBook(trade.getBookId()).getLe_id());
			        	final	JDialogTable showbook = new JDialogTable(booktablemodel12);
				        	 
			        	//  if(mirrorBook.getBookno() ==0)	 {
			        		 showbook.setLocationRelativeTo(showbook);
			        		showbook.setVisible(true);
			        	  //} else {
			        		//  mirrorBook.setBookno(0);
			        		 // basicData.counterPary.setText("");
			        		 // counterPartyID = 0;
			        	  //}
			        	  
			        	  showbook.jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
	
								@Override
								public void mouseClicked(MouseEvent e) {
									int id  = ((Integer)	showbook.jTable1.getValueAt(showbook.jTable1.getSelectedRow(),0)).intValue();
								
									 mirrorBook = getBook(id);
									 if(mirrorBook.getBookno() > 0 )
									basicData.counterPary.setSelectedItem(mirrorBook.getBook_name());
									 counterPartyID = trade.getCpID();
									 showbook.dispose();
								}
								
					    
					    	
					          });   
			        		
			        	}
			        	
			        	
			        });
			        // to check mirrorbook is null or not.
			       functionality.jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
			    	   @Override
						public void mouseClicked(MouseEvent e) {
			    		   commonUTIL.showAlertMessage((String) functionality.jTable2.getValueAt(functionality.jTable2.getSelectedRow(), functionality.jTable2.getSelectedColumn()));
			    		   mirrorBook.setBookno(0);
			    	   }
			    	
			       });
			        
			        // save button functional  jButton6 
			        functionality.jButton6.addMouseListener(new java.awt.event.MouseAdapter() {
	
						@Override
						public void mouseClicked(MouseEvent e) {
							if(!functionality.jButton6.isEnabled())
								return;
							// buildTrade(trade,"SAVE");
							if(!validdateALLFields("SAVE")) {
								return;
							}	else {
								
								if(basicData.jRadioButton5.isSelected() && fwdOp.startDate.isEnabled()) {				
							    	if (commonUTIL.addSubtractDate(commonUTIL.stringToDate(out.outRightDate.getSelectedDateAsText(), true), 31).before(
											 fwdOp.startDate.getDate())) {		    		
							    		commonUTIL.showAlertMessage("Option End date falls beyond 31 days after Trade end date");
							    		return;		    		
							    	}
							    	
							    	if (fwdOp.startDate.getDate().before(commonUTIL.stringToDate(out.outRightDate.getSelectedDateAsText(), true))){
							    		commonUTIL.showAlertMessage("Option End date cannot be before Trade end date");
							    		return;	
							    	}
								}
								
								 //@yogesh 04/02/2014
								  // check if split rates are filled if split rate checkbox is checked
								if(!checkRates()){
									return;
								}
								
								//@yogesh 07/02/2015
								//pankaj forgot to add in save
								getDataBeforeSave();
								
								fillTrade(trade,"NEW");												
								
								int isHoliday = 0;
								try {
									
									//@yogesh 04/02/2014
						            // check if instrumentType is selected for every Trade
						  		  	// checks if tradeDate is less then delivery date
						            if (!checktradeDetails(trade)){
						            	return;
						            }
						            
									isHoliday = remoteReference.checkHolidayOrWeekend((String)trade.getTradedesc().substring(0, 3), 
											trade.getDelivertyDate());
									
									if (isHoliday == 1) {
										
										commonUTIL.showAlertMessage("Near Date selected is a Holiday or a weekend. " +
												"Please select another date");
										return;
										//out.outRightDate.setBackground(Color.red);
									}
									
									if (isHoliday != 1) {
										
										isHoliday = remoteReference.checkHolidayOrWeekend((String)trade.getTradedesc().substring(4, 7), 
												trade.getEffectiveDate());
										
										if (isHoliday == 1) {
											
											commonUTIL.showAlertMessage("Far Date selected is a Holiday or a weekend. " +
													"Please select another date");
											return;
											//out.outRightDate.setBackground(Color.red);
										}
										
									} else if (isHoliday == -1) {
										commonUTIL.showAlertMessage("Server Problem");
										return;
									}
									
								} catch (RemoteException e2) {
									
									e2.printStackTrace();
								}
								
							}
							
							
				             
				             if ( (commonUTIL.stringToDate(trade.getEffectiveDate(), true)).before( 
				            		 commonUTIL.stringToDate(trade.getDelivertyDate(), true)) ) {
				            	 	
				            	 commonUTIL.showAlertMessage("Far Date cannot be less than Near Date");
				            	 return;
				            	 
				             }
			                 trade.setUserID(getUser().getId());
			                 try {
			                	 Vector<String> message = new Vector<String>();
					               	
					               	trade.setAttributes(getAttributeValue());
					               	trade.setFees(feesPanel.getFeesDataV());
					                Vector tradestatus = null;
					               	if(!commonUTIL.isEmpty(functionality.getRoutingData()) && functionality.getRoutingData().size() > 1) {
						        		   
						        		  Vector<Trade> rountingTrades = functionality.getRoutingData();
											if(!commonUTIL.isEmpty(rountingTrades) && rountingTrades.size() >1 ) {
												
													try {
														rountingTrades =     FXSplitUtil.splitTrade(rountingTrades, functionality.jTextField2.getDoubleValue(),
																functionality.jTextField3.getDoubleValue(), trade,functionality.FarRate1.getDoubleValue(),functionality.FarRate2.getDoubleValue(),true);
													} catch (ParseException e1) {
														
														e1.printStackTrace();
													}
													trade.setRountingTrades(rountingTrades);
								        		    tradestatus  = 	remoteTrade.saveBatchSplitTrades(trade.getRountingTrades(),message);
												
											}
											 
						        		    
						        		} else {
						        			//trade = functionality.getRoutingData().get(0);
						        		
						        			tradestatus	 = 	remoteTrade.saveTrade(trade,message);
						        		}
					               	if(commonUTIL.isEmpty(tradestatus)) {
					               		commonUTIL.showAlertMessage("Error in ServerSide in saving Trade");
					               		 return;
					               	 }
					               	 String statusT = (String) tradestatus.elementAt(0);
					               	 int i = ((Integer) tradestatus.elementAt(1)).intValue();
					               	 if(i == -10) {
					               		commonUTIL.showAlertMessage((statusT));
				            			return;
				            		 
					               	 }
					               	 if(i == -4) {
					         			commonUTIL.showAlertMessage((statusT));
					            			return;
					            		 }
					               	 if(i == -3) {
					            			commonUTIL.showAlertMessage((statusT));
					            			return;
					            		 }
					               	 if(i > 0) 
					               		commonUTIL.showAlertMessage((statusT));
					               	 //System.out.println("*************** " +i);
					               	 trade = (Trade) remoteTrade.selectTrade(i);
					               	openTrade(trade);
			       			 
			       			//System.out.println("*************** " +i);
			                   		 		trade = (Trade) remoteTrade.selectTrade(i);
			                   		 		setTrade(trade);
			                   		 		//saveASNew(trade);
			                   		 	openTrade(trade);
						        		functionality.refreshTables(trade.getTradedesc(),basicData.book.getText(),new Integer(basicData.book.getName()).intValue());
						        	
			       		} catch (RemoteException e1) {
			       			
			       			e1.printStackTrace();
			       		} 
			                  
			                  
			               
							}
							
						
						
			    
			    	
			    });
			        //outright radio button
			        basicData.jRadioButton1.addMouseListener(new java.awt.event.MouseAdapter() {
	
						@Override
						public void mouseClicked(MouseEvent e) {
							if(!basicData.jRadioButton1.isEnabled())
								return ;
							 functionality.FarRate1.setVisible(false);
							 functionality.FarRate2.setVisible(false);
							 functionality.jLabel4.setVisible(false);
							 functionality.jLabel5.setVisible(false);
							basicData.jRadioButton2.setSelected(false);
							basicData.jRadioButton1.setSelected(true);
							basicData.jRadioButton5.setSelected(false);
							basicData.jRadioButton0.setSelected(false);
							functionality.jButton2.setEnabled(true);
							functionality.jButton3.setEnabled(true);
							//out.jCheckBox2.setEnabled(true);
							//out.jCheckBox0.setEnabled(false);
							functionality.jButton2.setEnabled(true);
							fwdOp.setVisible(false);
							swap.jTextField1.setText("0.0");
							swap.jTextField2.setText("0.0");
							swap.jTextField4.setText("0.0");
							swap.setVisible(false);
							
							functionality.jButton0.setEnabled(false);
							productSubType = "FXFORWARD";
							// mpankaj 02/02 
							//out.jCheckBox1.setSelected(false);
						/*	if(basicData.buysell.getText().equalsIgnoreCase("BUY/SELL")) {
								basicData.buysell.setText("BUY");
								basicData.buysell.setBackground(Color.green);
							}
						if(basicData.buysell.getText().equalsIgnoreCase("SELL/BUY")) {
							basicData.buysell.setText("SELL"); 
						if(basicData.buysell.getText().equalsIgnoreCase("BUY"))
						        basicData.buysell.setBackground(Color.green);
							
						}  else { 
							basicData.buysell.setBackground(Color.red);
						} */
							basicData.buysell.setText("BUY");
							basicData.buysell.setBackground(Color.green);
							
						}
						
						
			    
			    	
			    });
			        
			       // swap  radio button 
			        
			        basicData.jRadioButton2.addMouseListener(new java.awt.event.MouseAdapter() {
	
						@Override
						public void mouseClicked(MouseEvent e) {
							if(!basicData.jRadioButton2.isEnabled())
								return ;
							basicData.jRadioButton2.setSelected(true);
							 functionality.FarRate1.setVisible(true);
							 functionality.FarRate2.setVisible(true);
							 functionality.jLabel4.setVisible(true);
							 functionality.jLabel5.setVisible(true);
							basicData.jRadioButton1.setSelected(false);
						//	out.jCheckBox2.setEnabled(true);
							//out.jCheckBox0.setEnabled(false);
							functionality.jButton2.setEnabled(true);
							functionality.jButton3.setEnabled(true);
							basicData.jRadioButton5.setSelected(false);
							functionality.jButton0.setEnabled(false);
							basicData.jRadioButton0.setSelected(false);
							fwdOp.setVisible(false);
							productSubType = "FXSWAP";
							swap.setVisible(true);
							swap.jTextField1.setText("0.0");
							swap.jTextField2.setText("0.0");
							swap.jTextField4.setText("0.0");
							out.outRightDate.setSelectedDate(commonUTIL.addSubtractDate(commonUTIL.getCurrentDate(), 2));
									
							//if(basicData.buysell.getText().equalsIgnoreCase("BUY")) {
							//	basicData.buysell.setText("SELL/BUY");
							//	basicData.buysell.setBackground(Color.red);
								/*double amt1 = Math.abs(new Double(out.jTextField1.getText()).doubleValue() );
								
								double amt2 = Math.abs(new Double(out.jTextField2.getText()).doubleValue()  * -1);
								swap.jTextField1.setText(out.jTextField1.getText());
								swap.jTextField2.setText(out.jTextField2.getText());
								out.jTextField1.setText(commonUTIL.getStringFromDoubleExp(amt1 * -1).toString());
								out.jTextField2.setText(commonUTIL.getStringFromDoubleExp(amt2 ).toString()); */
								
								
							//}
					//	if(basicData.buysell.getText().equalsIgnoreCase("SELL")) {
							basicData.buysell.setText("BUY/SELL");
							basicData.buysell.setBackground(Color.green
									);
							
							
							
						/*	double amt1 = Math.abs(new Double(out.jTextField1.getText()).doubleValue() * -1);
							//	out.jTextField1.setText(commonUTIL.getStringFromDoubleExp(amt1).toString());
								
								double amt2 = Math.abs(new Double(out.jTextField2.getText()).doubleValue());
							//	out.jTextField1.setText(commonUTIL.getStringFromDoubleExp(amt2).toString());
								swap.jTextField1.setText(out.jTextField1.getText());
								swap.jTextField2.setText(out.jTextField2.getText());
								out.jTextField1.setText(commonUTIL.getStringFromDoubleExp(amt1).toString());
								out.jTextField2.setText(commonUTIL.getStringFromDoubleExp(amt2 * -1).toString());*/
							
						//}
							}
							
						
						
			    
			    	
			    });
			        out.jComboBox1.addItemListener(new ItemListener() {
	
						@Override
						public void itemStateChanged(ItemEvent arg0) {
							
							if(actionController) 
								return;
							 if(out.jComboBox1.getSelectedIndex() == -1)
								 return;
							String selectAction =  out.jComboBox1.getSelectedItem().toString();
							if(selectAction.equalsIgnoreCase("ROLLBACK") || selectAction.equalsIgnoreCase("ROLLOVER")) {
								 functionality.jButton6.setEnabled(false);
							} else {
								 functionality.jButton6.setEnabled(true);
							}
						}
			        	
			        });
			        // Fwd Option Radio button. 
			        
			        basicData.jRadioButton5.addMouseListener(new java.awt.event.MouseAdapter() {
	
						@Override
						public void mouseClicked(MouseEvent e) {
							if(!basicData.jRadioButton5.isEnabled())
								return ;
							 functionality.FarRate1.setVisible(false);
							 functionality.FarRate2.setVisible(false);
							 functionality.jLabel4.setVisible(false);
							 functionality.jLabel5.setVisible(false);
							functionality.jButton2.setEnabled(false);
							functionality.jButton3.setEnabled(false);
							basicData.jRadioButton2.setSelected(false);
							basicData.jRadioButton1.setSelected(false);
							basicData.jRadioButton5.setSelected(true);
							
							String currP = basicData.currencyPair.getText();
							
							//@ yogesh 01/02/2015
							//@ no action id currency pair is not selected
							/*if(currP.isEmpty()) {
								basicData.jRadioButton1.setSelected(true);
								basicData.jRadioButton2.setSelected(false);
								
								swap.jTextField1.setText("0.0");
								swap.jTextField2.setText("0.0");
								swap.jTextField4.setText("0.0");
								swap.setVisible(false);
								basicData.jRadioButton5.setSelected(false);
								
						    	basicData.jRadioButton0.setSelected(false);
								commonUTIL.showAlertMessage("Select Currency Pair");
								return;
							}*/
						//	out.jCheckBox2.setEnabled(false);
							functionality.jButton2.setEnabled(false);
							functionality.jButton3.setEnabled(false);
							//functionality.jButton4.setEnabled(false);
							productSubType = FXFORWARDOPTION;
							 functionality.jButton0.setEnabled(true);
							 //out.jCheckBox0.setEnabled(true);
							
							swap.jTextField1.setText("0.0");
							swap.jTextField2.setText("0.0");
							swap.jTextField4.setText("0.0");
							swap.setVisible(false);
							fwdOp.setVisible(true);
							fwdOp.primaryC.setValue(0);
							fwdOp.quotingC.setValue(0);	
							// mpankaj 02/02
							basicData.buysell.setText("BUY");
							basicData.buysell.setBackground(Color.green);
							//@ yogesh 01/02/2015
							//@ date should be displayes even though currency pair is not selected
							fwdOp.startDate.setDate(commonUTIL.getCurrentDate());
							
							if(!currP.isEmpty()) {
								fwdOp.jLabel2.setText(currP.substring(0, 3));
							 	fwdOp.jLabel3.setText(currP.substring(4, 7));
							}								
							 	//fwdOp.startDate.setText(commonUTIL.convertDateTOString(commonUTIL.getCurrentDate()));
							} 
			        });
			        basicData.jRadioButton6.addMouseListener(new java.awt.event.MouseAdapter() {
	
						@Override
						public void mouseClicked(MouseEvent e) {
							if(!basicData.jRadioButton6.isEnabled())
								return ;
							 functionality.FarRate1.setVisible(false);
							 functionality.FarRate2.setVisible(false);
							 functionality.jLabel4.setVisible(false);
							 functionality.jLabel5.setVisible(false);
							if(basicData.jRadioButton6.isSelected()) {
							String status = out.jTextField6.getText();
							if(status.equalsIgnoreCase("ROLLOVER") || status.equalsIgnoreCase("ROLLBACK"))
								return;
								basicData.jRadioButton7.setSelected(false);
								rollpanel.getJPanel0().setVisible(true);
								rollpanel.jLabel0.setText("ROLL DATE");
								functionality.jButton7.setEnabled(true);
								functionality.jButton7.setText("ROLLBACK");
							} else {
								basicData.jRadioButton7.setSelected(true);
							}
							
							
						}
			        });
			        basicData.jRadioButton7.addMouseListener(new java.awt.event.MouseAdapter() {
	
						@Override
						public void mouseClicked(MouseEvent e) {
							if(!basicData.jRadioButton7.isEnabled())
								return ;
							 functionality.FarRate1.setVisible(false);
							 functionality.FarRate2.setVisible(false);
							 functionality.jLabel4.setVisible(false);
							 functionality.jLabel5.setVisible(false);
							if(basicData.jRadioButton7.isSelected()) {
								basicData.jRadioButton6.setSelected(false);
								String status = out.jTextField6.getText();
								if(status.equalsIgnoreCase("ROLLOVER") || status.equalsIgnoreCase("ROLLBACK"))
									return;
								rollpanel.getJPanel0().setVisible(true);
								rollpanel.jLabel0.setText("ROLL DATE");
								functionality.jButton7.setEnabled(true);
								functionality.jButton7.setText("ROLLOVER");
								
							} else {
								basicData.jRadioButton6.setSelected(true);
							}
							
							
						}
			        });
				} catch (RemoteException e) {
					
					e.printStackTrace();
				}
			
	        	try {
	        		instrumenTypeStartupData = remoteReference.getStartUPData("InstrumentTypeVal");
	        		getInstrumentVal();
				} catch (RemoteException e) {
					e.printStackTrace();
				}
		}
	
		private Book getBook(int bookID) {
			 int b=0;
			 Book bo = null;
			 Hashtable cloneBooks = (Hashtable<Integer,Book >) books.clone();
			 Enumeration<Integer> keys = cloneBooks.keys();
		    	while(keys.hasMoreElements()) {
		    		
		    		Integer key =	(Integer) keys.nextElement();
		    		Book book =   (Book) cloneBooks.get(key);
		    		if(book.getBookno() == bookID) {
		    			bo = book;
		    			break;
		    		}
		    		  //booktablemodel.insertRow(i, new Object[]{book.getBookno(),book.getBook_name()});
		    	
		    		
		    		b++;
			}	cloneBooks.clear();
		    	cloneBooks = null;
		    	if(bo == null) {
		    		bo = ReferenceDataCache.getBook(bookID);
		    		books.put(bo.getBookno(), bo);
		    	}
		    	return bo;
		}
		private void getBookDataCombo1(DefaultTableModel booktablemodel2,
				Hashtable<Integer, Book> books2) {
			Vector vector;
			try {
				
					vector = (Vector) remoteReference.selectALLBooks();
				   Iterator it = vector.iterator();
		    	   int i =0;
		    	 
		    	while(it.hasNext()) {
		    		
		    		Book le =	(Book) it.next();
		    		booktablemodel2.insertRow(i, new Object[]{le.getBookno(),le.getBook_name()});
		    		books2.put(i,le);
		    		
		    		i++;
			}	
			}catch (RemoteException e) {
						
				commonUTIL.displayError("FXTradePanel","getBookDataCombo1", e);
					}
			
			
		}
	
		public void getDataBeforeSave() {
    		if(basicData.buysell.getText().equalsIgnoreCase("BUY/SELL") || basicData.buysell.getText().equalsIgnoreCase("BUY")) {
					
					double amt1 =  (new Double(out.jTextField1.getText()).doubleValue());
					amt1 = Math.abs(amt1);
					double spot = (new Double(out.jTextField4.getText()).doubleValue());
					out.jTextField1.setText(commonUTIL.getStringFromDoubleExp(amt1));
					double amt2 = (amt1 * spot * -1);								
					out.jTextField2.setText(commonUTIL.getStringFromDoubleExp(amt2));
					
					 double farAmt1 = amt1 * -1;
			    	 swap.jTextField1.setText(commonUTIL.getStringFromDoubleExp(farAmt1));
			    	// System.out.println("farAmt1 " + farAmt1);
			    	// System.out.println("out.jTextField2"+ out.jTextField2.getText());
			    	 //System.out.println("out.jTextField1"+ out.jTextField1.getText());
			    	 //System.out.println("swap.jTextField2"+ swap.jTextField1.getText());
			    	 if (! commonUTIL.isEmpty(swap.jTextField4.getText()) && (!swap.jTextField4.getText().equalsIgnoreCase("0"))) {
			    		 
			    		 double farRate = (new Double(swap.jTextField4.getText()).doubleValue());
			    		 double farAmt2 = Math.abs(farAmt1);								
			    		 if(farRate !=0)
			    		 swap.jTextField2.setText(commonUTIL.getStringFromDoubleExp(farAmt2*farRate));
			    	 }
					
	    	 } else {
	    		 
	    		 	double amt1 =  (new Double(out.jTextField1.getText()).doubleValue());
					double spot = (new Double(out.jTextField4.getText()).doubleValue());
					out.jTextField1.setText(commonUTIL.getStringFromDoubleExp(amt1));
					double amt2 = Math.abs(amt1) * 1;		
				//	System.out.println("------------- "+commonUTIL.getStringFromDoubleExp(amt2*spot));
					out.jTextField2.setText(commonUTIL.getStringFromDoubleExp(amt2*spot));
					
					 double farAmt1 = amt1 * -1;
			    	 swap.jTextField1.setText(commonUTIL.getStringFromDoubleExp(farAmt1));
			    	 
			    	 if (! commonUTIL.isEmpty(swap.jTextField4.getText())) {			    		 
			    		 double farRate = (new Double(swap.jTextField4.getText()).doubleValue());
			    		 double farAmt2 = Math.abs(farAmt1) * -1;	
			    		 if(farRate != 0)
			    		 swap.jTextField2.setText(commonUTIL.getStringFromDoubleExp(farAmt2*farRate).toString());
			    	 }	    		 																
	    	 }	    	
	          populateRountingData();		
	    
    }
		
		
		@Override
		public void setTaskPanel(BackOfficePanel panel) {
			
			taskPanel = (TaskPanel) panel;
		}
	
		@Override
		public void setTrade(Trade trade) {
			
			this.trade = trade;
		}
	
		@Override
		public Trade getTrade() {
			
			return trade;
		}
	
		@Override
		public void saveASNew(Trade trade) {
			
		 if(trade != null) {
			out.jTextField5.setText(new Integer(trade.getId()).toString());
			out.jTextField6.setText(trade.getStatus());
			actionstatus.removeAllElements();
			trade.setMirrorBookid(0);
			trade.setMirrorID(0);
			trade.setAutoType("");
			trade.setAttribute("MirrorID","");	
			trade.setAttribute("B2BID","");	
			trade.setAttribute("B2BFlag","");		
			trade.setAttribute("XCurrSOriginalTradeID","");		
			trade.setAttribute("ParitialTo","");		
			trade.setAttribute("SXccySplitID","");		
			trade.setAttribute("ParitialFrom","");		
			trade.setAttribute("XccySplitFrom","");		
			trade.setAttribute("FXccySplitID","");		
			trade.setAttribute("OffsetID","");		
			trade.setAttribute("OriginalTradeID","");		
			trade.setAttribute("MirrorFromTradeID","");	
			trade.setOffsetid(0);
																						//trade.seto
			trade.setParentID(0);
			trade.setB2Bflag(false);
			trade.setB2bid(0);
			trade.setXccySPlitid(0);
			trade.setMirrorID(0);
			trade.setMirrorBookid(0);
		//trade.set
			//processActionData(actionstatus,trade.getTradedesc1()); 
			actionController = true;
			  processActionData(actionstatus,productType,trade.getTradedesc1(),out.jTextField6.getText(),remoteTrade);
			  actionController = false;
			this.trade = trade;
			getTradeTransfers(transferPanel);
			getTradeTask(taskPanel);
			getTradeSDI(sdiPanel);
			functionality.refreshTables(trade.getTradedesc(),basicData.book.getText(),new Integer(basicData.book.getName()).intValue());
			functionality.jButton7.setEnabled(false);
			//basicData.jRadioButton6.setEnabled(false);
	      //  basicData.jRadioButton7.setEnabled(false);
		}
			
		}
		
		
		
	 
		@Override
		public void setUser(Users user1) {
			
			this.user = user1;
			
		}
	    public Users getUser() {
	    	return user;
	    }
		@Override
		public String getAction() {
			
			String action = null;
			if(out.jComboBox1.getSelectedIndex() == -1) {
				return null;
		}
		if(out.jComboBox1.getSelectedIndex() >= 0) {
				action = out.jComboBox1.getSelectedItem().toString();
		}
			return action;
		
		}
	
		@Override
		public void buildTrade(Trade trade, String actionType) {
			
			if(actionType.equalsIgnoreCase("NEW")) {
				newTradeView();
			} else {
				if(validdateALLFields("NEW")) {
					if(basicData.jRadioButton5.isSelected() && fwdOp.startDate.isEnabled()) {
						
				    	if (commonUTIL.addSubtractDate(commonUTIL.stringToDate(trade.getDelivertyDate(), true), 31).before(
								 commonUTIL.stringToDate(trade.getEffectiveDate(), true))) {
				    		
				    		commonUTIL.showAlertMessage("Option End date falls beyond 31 days after Trade end date");
				    		 return;
				    		
				    	}
					}
					
			     fillTrade(trade,"NEW");
			        
				} else {
					trade.setTradedesc1("");
					//trade = null;
				}
			}
			
		}
		
		/**/
		
		public boolean validdateALLFields(String type) {
			boolean flag = false;
			if(!type.equalsIgnoreCase("NEW")) {
				if(out.jComboBox1.getSelectedIndex() ==  -1) {
					commonUTIL.showAlertMessage("Select Action");
					return flag;
				}
			}
			if(commonUTIL.isEmpty(basicData.currencyPair.getText()))  {
				    commonUTIL.showAlertMessage("Select CurrencyPair");
					return flag;
			}
			if(!validateALLIDField())
			   return flag;
			if(!validateTradeAmts()) {
				 commonUTIL.showAlertMessage("Enter Rate and Amt1, Amt2");
				return flag;
			}
		
			flag = true;
			return flag;
		}
		public boolean validateTradeAmts() {
			boolean flag = true;
			try {
				double rate = out.jTextField4.getDoubleValue();
				double amt1 =  out.jTextField1.getDoubleValue();
				double amt2 = out.jTextField2.getDoubleValue();
				if(rate == 0.0 && amt1 == 0.0)
					flag = false;
				if(rate == 0.0d && amt2 == 0.0)
					flag = false;
				if(amt1 == 0 && amt2 == 0)
					flag = false;
				if(rate == 0)
					flag = false;
				
				if(productType.equalsIgnoreCase(FXSWAP)) {
					double fWrate = swap.jTextField4.getDoubleValue();
					double fWamt1 = swap.jTextField1.getDoubleValue();
					double fWamt2 = swap.jTextField2.getDoubleValue();
					if(fWrate == 0.0 && fWamt1 == 0.0)
						flag = false;
					if(fWrate == 0.0d && fWamt2 == 0.0)
						flag = false;
					if(fWamt1 == 0 && fWamt2 == 0)
						flag = false;
					if(fWrate == 0)
						flag = false;
				}
				
			} catch (ParseException e) {
				
				e.printStackTrace();
			}
			return flag;
		}
		public boolean validateALLIDField() {
			boolean flag = false;
			
			if(!validateField(basicData.book,"BOOK"))
				return flag;
			if(!validateField(basicData.counterPary,"CounterParty"))
				return flag;
			if(!validateField(basicData.jTextField7,"Trader"))
				return flag;
			else 
				flag = true;
			
			return flag;
		}		
		
		public boolean validateField(JTextField jtext,String name) {			
			if(!validateFieldValue(jtext.getName())) {
				commonUTIL.showAlertMessage(" Select  "+ name);
				return false;
			}
			return true;
		}
		public boolean validateField(TableExComboBox jtext,String name) {			
			if(!validateFieldValue(jtext.getName())) {
				commonUTIL.showAlertMessage(" Select  "+ name);
				return false;
			}
			return true;
		}
		// this is method is used to RountingData Calculation		
		public void setRoutingDataCal() {
			try {
				double rate1 = 0.0 ;
				double rate2 = 0.0 ;	
				double farRate1 = 0.0;
				double farRate2 = 0.0;
				if(!commonUTIL.isEmpty( functionality.jTextField2.getText()))
					rate1 =  functionality.jTextField2.getDoubleValue();
				if(!commonUTIL.isEmpty( functionality.jTextField3.getText()))
					rate2 =  functionality.jTextField3.getDoubleValue();
				if(!commonUTIL.isEmpty( functionality.FarRate1.getText()))
					farRate1 =  functionality.FarRate1.getDoubleValue();
				if(!commonUTIL.isEmpty( functionality.FarRate2.getText()))
					farRate2 =  functionality.FarRate2.getDoubleValue();
				
				  
				if(commonUTIL.isEmpty( functionality.getRoutingData()))
					return;
				if( functionality.getRoutingData().size() > 4) {
					Trade orignalTrade = functionality.getRoutingData().get(0);
					if( orignalTrade.getId() == 0) {
							Trade xsplit1  =  functionality.getRoutingData().get(1);									
							Trade xsplit2  =  functionality.getRoutingData().get(3);
						Vector<Trade>	rounting =     FXSplitUtil.splitTrade(xsplit1, xsplit2, functionality.getRoutingData().get(0), rate1, rate2,farRate1,farRate2);
						functionality.setRoutingData(rounting);
					}  else {
						Vector<Trade> rounting =  FXSplitUtil.splitTrade(functionality.getRoutingData(), rate1, rate2,farRate1,farRate2);
						if(commonUTIL.isEmpty(rounting)) 
							return;
						functionality.setRoutingData(rounting);
						functionality. jTextField3.setText(String.valueOf(rate2));
						functionality. jTextField2.setText(String.valueOf(rate1));
						functionality. FarRate1.setText(String.valueOf(farRate1));
						functionality. FarRate2.setText(String.valueOf(farRate2));
						attributes.changeXccySplitRate("splitBaseNearRate",String.valueOf(rate1));
						attributes.changeXccySplitRate("splitQuoteNearRate",String.valueOf(rate2));
						attributes.changeXccySplitRate("splitBaseFarRate",String.valueOf(farRate1));
						attributes.changeXccySplitRate("splitQuoteFarRate",String.valueOf(farRate2));
					}
                 //  getJTable1().repaint();
                  
				}
                   
			} catch (ParseException e) {
				
				e.printStackTrace();
			}
		}
		public boolean validateFieldValue(String value) {
			boolean flag = false;
		
			try {
			   int id = new Integer(value).intValue();
			   if(id > 0)
				   flag = true;
			}catch(NumberFormatException n) {
				return flag;
			}catch(NullPointerException n) {
				return flag;
			}
			return flag;				
		}
			
		@Override
		public void setPanelValue(CommonPanel tradeValue) {					
		}
	
		@Override
		public void setTradePanelValue(CommonPanel tradeValue) {			
		}
	
		@Override
		public Collection getCashFlows() {			
			return null;
		}
	
		@Override
		public Pricer getPricer() {			
			return null;
		}		

		private void processTableData(Hashtable<String,String> attributes,DefaultTableModel model) {			
	    	Vector vector;
			try {
				this.attributes.clearllCriteriaModel();
				vector = (Vector) remoteReference.getStartUPData("TradeAttribute");
				Iterator it = vector.iterator();
		    	int i =0;
		    	while(it.hasNext()) {
		    		
		    		StartUPData tradeAttributes = (StartUPData) it.next();
		    		Attribute attru = new Attribute();
		    		int rowCount = this.attributes.getTableRowCount();
		    		attru.setName(tradeAttributes.getName().toString());
		    	//	attru.setName(tradeAttributes.getName().toString());
		    		if(tradeAttributes.getName().equalsIgnoreCase("Trade Date") 
		    				|| tradeAttributes.getName().equalsIgnoreCase("TradeModifiedDateTime")) {
		    	    	
		    			attru.setValue(commonUTIL.getCurrentDateTime());
		    			
		    			//@yogesh 08/02/2015
	                    // we are using DateCellEditor to show Jide calender in TradeDate and TradeModifiedDateTime	               
		    			this.attributes.addRowEditor(rowCount, "Values");
		    			
		    	    } else {
		    	    	Vector attributeValues = (Vector) remoteReference.getStartUPData(attru.getName());
			    		if(!commonUTIL.isEmpty(attributeValues)) {
				    		 String values [] = this.attributes.convertVectortoSringArray(attributeValues,tradeAttributes.getName().toString());
				    		 this.attributes.addRowEditor(rowCount, 1, this.attributes.getJComboxBox(values),"Values");
			    		 	 values = null;
			    		} else {
			    			this.attributes.addRowEditor(rowCount, 1,this.attributes.getJTextFieldBox(), "Values");
			    		}
		    	    }
		    		
		    		this.attributes.addNewRow(attru);
		    		
		    			
		    	}
		    	
		    	this.attributes.tradeAction = "New";
		    	   /* if(tradeAttributes.getName().equalsIgnoreCase("TradeDate")) {
		    	    	model.insertRow(i, new Object[]{tradeAttributes.getName(),commonUTIL.getCurrentDateTime()});
		    	    	attributes.put(tradeAttributes.getName(),(String) model.getValueAt(i, 1));
		    	    } else {
		    		   model.insertRow(i, new Object[]{tradeAttributes.getName(),""});
		    		   attributes.put(tradeAttributes.getName(),"");
		    	    }
		    		i++;
		    		} */
		    		
			} catch (RemoteException e) {				
				e.printStackTrace();
			}
	    }
	
		@Override
		public void setSDIPanel(BackOfficePanel panel) {
			sdiPanel = (SDIPanel) panel;
		}
		
		// mpank jthis method is called when you clicked on SDIPanel
		public void setSDIPanelInstruction() {
			if(trade != null) {
				sdiPanel.clearALL();
				sdiPanel.setTrade(trade);				 
			} else {
				Trade dummy = new Trade();
				dummy.setId(0);
				fillTrade(dummy,"NEW");
				if(isTradeProper)  {
					sdiPanel.clearALL();
					//@yogesh 07/02/2015
					// below is commented as dummy trade cause null pointer in setTrade which calls generate transfer rule.
					//sdiPanel.setTrade(dummy);
				}
				isTradeProper = false;				
			}
			
		}
		 public void getTradeSDI(BackOfficePanel panel) {				
			//System.out.println(panel);
			//sdiPanel.setTrade(trade);
			//panel.fillJTabel((Vector) SDISelectorUtil.selectSdiOntrade(trade, remoteReference));
			 setSDIPanelInstruction();				
		}
	
		@Override
		public void setFEESPanel(BackOfficePanel panel) {			
			feesPanel = (FeesPanel) panel;			
		}
	
		
	
		 public void getTradeTask(BackOfficePanel panel) {
			try {
				//sdiPanel.setTrade(trade);
				panel.fillJTabel((Vector)remoteTask.selectTaskWhere("tradeId = " + trade.getId()));
			} catch (RemoteException e) {
				
				e.printStackTrace();
			}
		}
		 
	
		@Override
		public void setTradePostings(BackOfficePanel panel) {					
		}
	
		@Override
		public void processTask(TaskEventProcessor taskEvent, Users WindowUser) {				
		}
		
		@Override
		public void setTradeTransfers(BackOfficePanel panel) {			
			transferPanel = (TransferPanel) panel;
		} 
		
		public void getTradeTransfers(BackOfficePanel panel) {
			try {
				transferPanel.setTrade(trade);
				panel.fillJTabel((Vector)remoteBO.queryWhere("Transfer", "tradeId = " + trade.getId()));
			} catch (RemoteException e) {
				
				e.printStackTrace();
			}
		}
		// new button
		public void newTradeView() {
			
			//@yogesh 07/02/2015
			// if called after takeUpWindow is opened then funtionality buttons disable while opening 
			//takeup trade should be enabled
			enableTakeUpDisabledFunctionalityButton();
			
			//@yogesh 10/02/2015
			//enable the instrument type and 
			attributes.tradeAction = "New";
			attributes.isfwOpTrade = false;
			
			//@yogesh 10/02/2015
			// make a new takeUpwindow and set its type to utilize
			if (trade != null && trade.getTradedesc1().equals(FXFORWARDOPTION)) {
				takeupW = new TakeUPWindow();
				takeupW.typeComboBox.setSelectedItem("Utilize");
			}			
			
			app.trade = null;
			//out.jCheckBox2.setEnabled(true);
			//out.jCheckBox0.setEnabled(false);
			functionality.jButton2.setEnabled(true);
			basicData.currencyPair.setText("");
			basicData.book.setText("");
			basicData.book.setName("0");
			basicData.counterPary.setSelectedItem("");
			basicData.counterPary.setName("0");
			basicData.buysell.setText("BUY");
		//	basicData.jRadioButton3.setSelected(true);
		//	basicData.jRadioButton4.setSelected(false);
			out.jLabel2.setText("");
			out.jTextField5.setText("0"); // id
			out.jTextField1.setText("0"); // amt1
			out.jTextField2.setText("0"); // amt2
		//	out.outRightDate.setDateFormat(commonUTIL.getDateFormat()); // date
			out.outRightDate.setSelectedDate(commonUTIL.getCurrentDate());// date
			out.jTextField4.setText("0"); // spot 
			basicData.jTextField7.setText("0"); // trader
			basicData.jTextField7.setName("0");
			out.jTextField6.setText("NONE"); // status
			
			out.jComboBox1.removeAll();
			actionstatus.removeAllElements();
			actionstatus.insertElementAt("NEW", 0); 
			out.jComboBox1.setModel(actionstatus);
			
			//@yogesh 07/02/2015
		    // swap.setVisible(false); set its field to zero
			/*swap.jTextField1.setText("0");
			swap.jTextField2.setText("0");
			swap.swapDate.setText("0");
			swap.jTextField4.setText("0");*/
	
			String attributeColumnName [] =    {"Attribute Name ", "Attribute  Value "};
	        
	      //  attributeModel = new DefaultTableModel(attributeColumnName,0);
	        processTableData(attributeDataValue,attributeModel);
	        attributes.jTable1.removeAll();
	     //   attributes.jTable1.setModel(attributeModel);
	        swap.setVisible(false);
			fwdOp.setVisible(false);
			fwdOp.setEnabled(false);
	        rollpanel.getJPanel0().setVisible(false);
	        functionality.jPanel2.setVisible(false);
	        functionality.jPanel6.setVisible(false);
	        functionality.jButton7.setEnabled(false);
	        trade = null;
	      
	     //   productSubType = "";
	        basicData.jRadioButton0.setSelected(false);
	        basicData.jRadioButton0.setEnabled(false);
			basicData.jRadioButton1.setSelected(true);
	        basicData.jRadioButton1.setEnabled(true);
			basicData.jRadioButton2.setSelected(false);
			basicData.jRadioButton2.setEnabled(true);
			basicData.jRadioButton5.setSelected(false);
			basicData.jRadioButton5.setEnabled(true);			
			basicData.jRadioButton6.setSelected(false);
	        basicData.jRadioButton6.setEnabled(false);
	        basicData.jRadioButton7.setSelected(false);
	        basicData.jRadioButton7.setEnabled(false);
	        
	        /*if(productSubType.equalsIgnoreCase(FXFORWARD)) {
				//basicData.jRadioButton1.setSelected(true);
				basicData.jRadioButton2.setSelected(false);
				basicData.jRadioButton5.setSelected(false);
				basicData.jRadioButton0.setSelected(false);
				functionality.jButton2.setEnabled(true);
				functionality.jButton3.setEnabled(true);
				basicData.buysell.setText("BUY");
			}
			if(productSubType.equalsIgnoreCase(FXSWAP)) {
				basicData.jRadioButton2.setSelected(false);
				basicData.jRadioButton5.setSelected(false);
				//basicData.jRadioButton1.setSelected(false);
				basicData.jRadioButton0.setSelected(false);
				functionality.jButton2.setEnabled(true);
				functionality.jButton3.setEnabled(true);
				basicData.buysell.setText("BUY/SELL");
				
			}
			if(productSubType.equalsIgnoreCase(FXFORWARDOPTION)) {
				basicData.jRadioButton5.setSelected(false);
				basicData.jRadioButton2.setSelected(false);
				//basicData.jRadioButton1.setSelected(false);
				basicData.jRadioButton0.setSelected(false);
				functionality.jButton2.setEnabled(false);
				functionality.jButton3.setEnabled(false);
				basicData.buysell.setText("BUY");
				fwdOp.primaryC.setText("0");
				fwdOp.quotingC.setText("0");
			}
			if(productSubType.equalsIgnoreCase(FXTAKEUP)) {
				//basicData.jRadioButton0.setSelected(true);
				basicData.jRadioButton5.setSelected(false);
				basicData.jRadioButton2.setSelected(false);
				//basicData.jRadioButton1.setSelected(false);
				functionality.jButton2.setEnabled(false);
				functionality.jButton3.setEnabled(false);
				//basicData.buysell.setText("BUY");
			}*/
	        functionality.setRoutingData(null);
	        b2bconfig = null;
	     //   functionality.jPanel2.setVisible(true);
			//functionality.jPanel6.setVisible(true);
		   functionality.b2bCurrencyPair.setText("");
		  //  functionality.b2bBook.setText("");
		    functionality.b2bTransferTo.setText("");
		    functionality.clearRounting();

		  //@yogesh 07/02/2015
		    // functionality.jPanel6.setVisible(false); set its field to zero	
		    
/*		    functionality.jLabel2.setText("");
		    functionality.jLabel3.setText("");
		    functionality.jTextField2.setText("0.0");
		    functionality.jTextField3.setText("0.0");
		    functionality.FarRate1.setText("0.0");
		    functionality.FarRate2.setText("0.0");*/
		   
		  //  functionality.jTabbedPane2.setVisible(false);
		  //  functionality.jTabbedPane1.setVisible(true);
		    		    		 
		 // mpankaj 02/02functionality.jPanel2.jTextField2
			 functionality.jPanel2.setVisible(false);
			 out.jCheckBox2.setSelected(false);
			 out.jCheckBox2.setEnabled(false);
			// getTradeTransfers(transferPanel);
				//getTradeTask(taskPanel);
				getTradeSDI(sdiPanel);
		}
		public int fillRollParitialOutRightTrade(Trade rolltrade,String type,int rollFROMID,boolean isParital,double rollAmt)  {
			Trade newrolltrade = new Trade();
			Trade rollparitialtrade = new Trade();
			Product rollProduct = null;
			String rollProductSubType = "";
			int rollID = 0;
			  if(productSubType.equalsIgnoreCase(FXFORWARD)) {
				  rollProductSubType = FXSWAP;
				  
				  newrolltrade.setBookId(new Integer(basicData.book.getName()).intValue());
				  newrolltrade.setCpID(new Integer(basicData.counterPary.getName()).intValue());
				  newrolltrade.setTraderID(new Integer(basicData.jTextField7.getName()).intValue());
				  newrolltrade.setTradeDate(commonUTIL.getCurrentDateTime());
				  newrolltrade.setDelivertyDate(out.outRightDate.getSelectedDateAsText());
				  newrolltrade.setStatus("NONE");
				  newrolltrade.setProductType(productType);
		   
		  
				  newrolltrade.setAction("NEW");
				  newrolltrade.setCurrency((String)basicData.currencyPair.getText().substring(4, 7));  // negotiable curr ie. quote currency ie. settlementCurrency
		    if(basicData.buysell.getText().equalsIgnoreCase("BUY")) {
		    	newrolltrade.setType("SELL/BUY");
		    	newrolltrade.setQuantity(rollAmt * -1); 
		    	newrolltrade.setNominal(( newrolltrade.getQuantity() * -1) * trade.getPrice());
		    } else {
		    	newrolltrade.setType("BUY/SELL");
		    	newrolltrade.setQuantity(rollAmt * -1); 
		    	newrolltrade.setNominal(( newrolltrade.getQuantity() * -1) * trade.getPrice());
		    }
		    newrolltrade.setTradedesc(basicData.currencyPair.getText()); 
		    newrolltrade.setUserID(user.getId());
		    newrolltrade.setAttributes(rolltrade.getAttributes());
		    if(type.equalsIgnoreCase("ROLLOVER"))  {
		    	newrolltrade.setAttribute("rollOverFrom",Integer.toString(rollFROMID));
		    	newrolltrade.setRollOverFrom(rollFROMID);
		       
		    } else  {
		    	newrolltrade.setAttribute("rollBackFrom",Integer.toString(rollFROMID));
		    	newrolltrade.setRollBackFrom(rollFROMID);
		    }
		    newrolltrade.setPrice(new Double(out.jTextField4.getText()).doubleValue());
		    newrolltrade.setEffectiveDate(rollpanel.jTextField1.getText());  // use as FORWARD DATE for FXSWAP in FX. 
		    // amt1 (negotiated amt for negotiable curr) 
		  	  // amt2  (negotiated amt for non-negotiable curr) nominal =  quantity * price
		    
		    newrolltrade.setTradedesc1(rollProductSubType);
		   
				     
				    try {
				    	if(newrolltrade.getType().equalsIgnoreCase("BUY/SELL")) {
				    		newrolltrade.setTradeAmount(newrolltrade.getQuantity() *-1);  // used as quantity for FXSWAP in FX 
				    		newrolltrade.setYield((newrolltrade.getTradeAmount() *-1) * rollpanel.jTextField1.getDoubleValue());
				    } else  {
				    	newrolltrade.setTradeAmount(newrolltrade.getQuantity() *-1);
				    	newrolltrade.setYield((newrolltrade.getTradeAmount() *-1) * rollpanel.jTextField1.getDoubleValue());
				    }
					} catch (ParseException e1) {
						
						e1.printStackTrace();
					}         // used as fwd amt for FXSWAP in FX
				    newrolltrade.setEffectiveDate(rollpanel.jTextField0.getSelectedDateAsText());
				    try {
				    	newrolltrade.setSecondPrice(rollpanel.jTextField1.getDoubleValue());  // used as forward rate 
					} catch (ParseException e) {
						
						e.printStackTrace();
					}
		    } 
			  try {
				  rollProduct = (Product) remoteProduct.selectProductOnType(productType, rollProductSubType);
				  newrolltrade.setProductId(rollProduct.getId());
				  
				  try {
					rollparitialtrade = (Trade) trade.clone();
				} catch (CloneNotSupportedException e) {
					
					e.printStackTrace();
				}
				  rollparitialtrade.setId(0);
				  rollparitialtrade.setProductId(rollProduct.getId());
				  rollparitialtrade.setTradedesc1(rollProductSubType);
				  rollparitialtrade.setSecondPrice(trade.getSecondPrice());
				 // rollparitialtrade.setAction("NEW");
				//  rollparitialtrade.setStatus("NONE");
				  if(rollparitialtrade.getType().equalsIgnoreCase("BUY")) {
					  rollparitialtrade.setType("BUY/SELL");
					  rollparitialtrade.setQuantity(rollparitialtrade.getQuantity() - rollAmt);
					  rollparitialtrade.setNominal(( rollparitialtrade.getQuantity() * trade.getPrice())*-1);
					  rollparitialtrade.setTradeAmount(rollparitialtrade.getQuantity() * -1);
					  rollparitialtrade.setYield(rollparitialtrade.getNominal() * -1);
				  } else {
					  rollparitialtrade.setType("SELL/BUY");
					  rollparitialtrade.setQuantity( rollparitialtrade.getQuantity() + rollAmt);
					  rollparitialtrade.setNominal((rollparitialtrade.getQuantity() * trade.getPrice())*-1);
					  rollparitialtrade.setTradeAmount(rollparitialtrade.getQuantity() * -1);
					  rollparitialtrade.setYield(rollparitialtrade.getNominal() * -1);
				  }
			 	  
				  rollparitialtrade.setParitial(true);
				  Hashtable atth = rollparitialtrade.getAttributeH();
				  atth.clear();
				  atth.put("ParitialFrom",Integer.valueOf(trade.getId()).toString());
				  atth.put("Trade Date",commonUTIL.getCurrentDateInString());
				  rollparitialtrade.setAttributes(atth);
				  rollID=  remoteTrade.saveTrade(rollparitialtrade);
				  atth = null;
				  atth = trade.getAttributeH();
				  atth.put("ParitialTo",Integer.valueOf(rollID).toString());
				  trade.setAttributes(atth);
				  
				  rollID=  remoteTrade.saveTrade(newrolltrade);
				  
				} catch (RemoteException e) {
					
					e.printStackTrace();
					return -1;
				} 
			
			return rollID;
		}
		
		public int fillRollOutRightTrade(Trade rolltrade,String type,int rollFROMID,boolean isParital,double rollAmt) {
			//
				//trade.setId(0);
			if(isParital) {
				return  fillRollParitialOutRightTrade(trade,type,trade.getId(),isParital,rollAmt);
			}
				
			int rollID = 0;
			Trade newrolltrade = new Trade();
			Product rollProduct = null;
			newrolltrade.setId(0);
			String rollProductSubType = "";
				  if(productSubType.equalsIgnoreCase(FXFORWARD)) {
					  rollProductSubType = FXSWAP;
					  
					  newrolltrade.setBookId(new Integer(basicData.book.getName()).intValue());
					  newrolltrade.setCpID(new Integer(basicData.counterPary.getName()).intValue());
					  newrolltrade.setTraderID(new Integer(basicData.jTextField7.getName()).intValue());
					  newrolltrade.setTradeDate(commonUTIL.getCurrentDateTime());
					  newrolltrade.setDelivertyDate(out.outRightDate.getSelectedDateAsText());
					  newrolltrade.setStatus("NONE");
					  newrolltrade.setProductType(productType);
			   
			  
					  newrolltrade.setAction("NEW");
					  newrolltrade.setCurrency((String)basicData.currencyPair.getText().substring(4, 7));  // negotiable curr ie. quote currency ie. settlementCurrency
			    if(basicData.buysell.getText().equalsIgnoreCase("BUY")) {
			    	newrolltrade.setType("SELL/BUY");
			    	newrolltrade.setQuantity(new Double(out.jTextField1.getText()).doubleValue() * -1); 
			    	newrolltrade.setNominal(new Double(out.jTextField2.getText()).doubleValue() * -1);
			    } else {
			    	newrolltrade.setType("BUY/SELL");
			    	newrolltrade.setQuantity(new Double(out.jTextField1.getText()).doubleValue() * -1); 
			    	newrolltrade.setNominal(new Double(out.jTextField2.getText()).doubleValue() * -1);
			    }
			    newrolltrade.setTradedesc(basicData.currencyPair.getText()); 
			    newrolltrade.setUserID(user.getId());
			    newrolltrade.setAttributes(rolltrade.getAttributes());
			    if(type.equalsIgnoreCase("ROLLOVER"))  {
			    	newrolltrade.setAttribute("rollOverFrom",Integer.toString(rollFROMID));
			    	newrolltrade.setRollOverFrom(rollFROMID);
			       
			    } else  {
			    	newrolltrade.setAttribute("rollBackFrom",Integer.toString(rollFROMID));
			    	newrolltrade.setRollBackFrom(rollFROMID);
			    }
			    newrolltrade.setPrice(new Double(out.jTextField4.getText()).doubleValue());
			    newrolltrade.setEffectiveDate(rollpanel.jTextField1.getText());  // use as FORWARD DATE for FXSWAP in FX. 
			    // amt1 (negotiated amt for negotiable curr) 
			  	  // amt2  (negotiated amt for non-negotiable curr) nominal =  quantity * price
			    
			    newrolltrade.setTradedesc1(rollProductSubType);
			   
					     
					    try {
					    	if(trade.getType().equalsIgnoreCase("BUY/SELL")) {
					    		newrolltrade.setTradeAmount(trade.getQuantity() *-1 * rollpanel.jTextField1.getDoubleValue()) ;  // used as quantity for FXSWAP in FX 
					    		newrolltrade.setYield((newrolltrade.getTradeAmount() *-1) );
					    } else  {
					    	newrolltrade.setTradeAmount((trade.getQuantity() ) );
					    	newrolltrade.setYield((trade.getQuantity() *-1 *  rollpanel.jTextField1.getDoubleValue()) );
					    }
						} catch (ParseException e1) {
							
							e1.printStackTrace();
						}         // used as fwd amt for FXSWAP in FX
					    newrolltrade.setEffectiveDate(rollpanel.jTextField0.getSelectedDateAsText());
					    try {
					    	newrolltrade.setSecondPrice(rollpanel.jTextField1.getDoubleValue());  // used as forward rate 
						} catch (ParseException e) {
							
							e.printStackTrace();
						}
			    } 
				  try {
					  rollProduct = (Product) remoteProduct.selectProductOnType(productType, rollProductSubType);
					  newrolltrade.setProductId(rollProduct.getId());
					 
					  rollID=  remoteTrade.saveTrade(newrolltrade);
					} catch (RemoteException e) {
						
						e.printStackTrace();
						return -1;
					}  
			  
			 return rollID;
				  
			    
			}
		
		
		
		
		public int fillRollSwapTrade(Trade rolltrade,String type,int rollFROMID,boolean isParital,double rollAmt) {
			//
				//trade.setId(0);
		
			int rollID = 0;
			Trade tradeSwap = new Trade();
			Product rollProduct = null;
			tradeSwap.setId(0);
			String rollProductSubType =  FXSWAP;
			try{
					 
					  
				tradeSwap.setBookId(new Integer(basicData.book.getName()).intValue());
				tradeSwap.setCpID(new Integer(basicData.counterPary.getName()).intValue());
				tradeSwap.setTraderID(new Integer(basicData.jTextField7.getName()).intValue());
				tradeSwap.setTradeDate(commonUTIL.getCurrentDateTime());
				tradeSwap.setDelivertyDate(rolltrade.getEffectiveDate());
				tradeSwap.setStatus("NONE");
				tradeSwap.setProductType(productType);
			   
			  
				tradeSwap.setAction("NEW");
				tradeSwap.setCurrency(rolltrade.getCurrency());  // negotiable curr ie. quote currency ie. settlementCurrency
			    if(rolltrade.getType().equalsIgnoreCase("BUY/SELL")) {
			    	tradeSwap.setType("BUY/SELL");
			    	tradeSwap.setQuantity(rolltrade.getTradeAmount()  ); 
			    	tradeSwap.setNominal(rolltrade.getYield());
			    	tradeSwap.setTradeDate(rolltrade.getEffectiveDate());
			    	tradeSwap.setTradeAmount(tradeSwap.getQuantity() *-1);
			    	tradeSwap.setYield(tradeSwap.getQuantity() * rollpanel.jTextField1.getDoubleValue());
			    	tradeSwap.setEffectiveDate(rollpanel.jTextField0.getSelectedDateAsText());
			    } else {
			    	tradeSwap.setType("SELL/BUY");
			    	
			    	tradeSwap.setQuantity(rolltrade.getTradeAmount() *-1 ); 
			    	tradeSwap.setNominal(trade.getYield() *-1);
			    	tradeSwap.setTradeDate(trade.getEffectiveDate());
			    	tradeSwap.setTradeAmount(trade.getQuantity() *-1);
			    	tradeSwap.setYield(trade.getQuantity() * rollpanel.jTextField1.getDoubleValue());
			    	tradeSwap.setEffectiveDate(rollpanel.jTextField0.getSelectedDateAsText());
			    }
			    tradeSwap.setTradedesc(rolltrade.getTradedesc()); 
			    tradeSwap.setUserID(user.getId());
			    //trade.setAttributes(rolltrade.getAttributes());
			    tradeSwap.setAttribute("Trade Date", commonUTIL.getCurrentDateInString());
			    if(type.equalsIgnoreCase("ROLLOVER"))  {
			    	tradeSwap.setAttribute("rollOverFrom",Integer.toString(rollFROMID));
			    	tradeSwap.setRollOverFrom(rollFROMID);
			    } else  {
			    	tradeSwap.setAttribute("rollBackFrom",Integer.toString(rollFROMID));
			    	tradeSwap.setRollBackFrom(rollFROMID);
			    }
			    tradeSwap.setPrice(new Double(out.jTextField4.getText()).doubleValue());
			    tradeSwap.setSecondPrice(rollpanel.jTextField1.getDoubleValue());
			 //   tradeSwap.setEffectiveDate(rollpanel.jTextField1.getText());  // use as FORWARD DATE for FXSWAP in FX. 
			    // amt1 (negotiated amt for negotiable curr) 
			  	  // amt2  (negotiated amt for non-negotiable curr) nominal =  quantity * price
			    
			    tradeSwap.setTradedesc1(rollProductSubType);
			   
					   
			    
				
					rollProduct = (Product) remoteProduct.selectProductOnType(productType, rollProductSubType);
					tradeSwap.setProductId(rollProduct.getId());
				 
					rollID=  remoteTrade.saveTrade(tradeSwap);
				} catch (RemoteException e) {
					
					e.printStackTrace();
					return -1;
				}  catch (ParseException e) {
					e.printStackTrace();
					return -1;
				}
			  
					return rollID;				  
			    
			} 
		
		/// to remove the attribute we this methods. 		
		public void removeAttributeFromTrade(Trade trade) {
			attributeDataValue.remove("rollOverTO");
			attributeDataValue.remove("rollBackTo");
			attributeDataValue.remove("FXccySplitID");
			attributeDataValue.remove("SXccySplitID");			
		}		
		
		public void fillTrade(Trade trade,String type) {
		//
			isTradeProper = false;
			//trade.setId(0);
			if(basicData.jRadioButton0.isSelected()) {
				productSubType = FXTAKEUP;
			} else if(basicData.jRadioButton1.isSelected()) {
				productSubType = FXFORWARD;
			} else if(basicData.jRadioButton2.isSelected()) {
				productSubType = FXSWAP;
			} else if(basicData.jRadioButton5.isSelected()) {
				productSubType = FXFORWARDOPTION;
			} else if(productSubType.isEmpty()) {
				commonUTIL.showAlertMessage("Select ProductType");
				return;
			}
			
			try {
				product = (Product) remoteProduct.selectProductOnType(productType, productSubType);
			} catch (RemoteException e) {				
				e.printStackTrace();
			}    
			if(commonUTIL.isEmpty(basicData.book.getName()))
			   return;
			trade.setBookId(new Integer(basicData.book.getName()).intValue());
			if(mirrorBook.getBookno()> 0) {
			//	trade.setCpID(counterPartyID);
				trade.setMirrorBookid(mirrorBook.getBookno());				
			} else {
				trade.setMirrorID(0);
				if(commonUTIL.isEmpty(basicData.counterPary.getName())) {
					commonUTIL.showAlertMessage("Select CounterParty");
					return;
				}
				if(commonUTIL.isEmpty(basicData.counterPary.getName()))
					return;
			trade.setCpID(new Integer(basicData.counterPary.getName()).intValue());
			}
			if(commonUTIL.isEmpty(basicData.jTextField7.getName()))
				return;
			trade.setTraderID(new Integer(basicData.jTextField7.getName()).intValue());
			trade.setTradeDate(commonUTIL.getCurrentDateTime());
		    trade.setDelivertyDate(out.outRightDate.getSelectedDateAsText());
			trade.setStatus(out.jTextField6.getText());
		    trade.setProductType(productType);
		   
		    if(!(out.jComboBox1.getSelectedIndex() == -1))
		        trade.setAction(out.jComboBox1.getSelectedItem().toString());
		    if(!commonUTIL.isEmpty(basicData.currencyPair.getText()))
		    trade.setCurrency((String)basicData.currencyPair.getText().substring(4, 7));  // negotiable curr ie. quote currency ie. settlementCurrency
		    trade.setType(basicData.buysell.getText());
		    trade.setTradedesc(basicData.currencyPair.getText()); 
		    trade.setUserID(user.getId());
		    trade.setAttributes(getAttributeValue());
		    trade.setPrice(new Double(out.jTextField4.getText()).doubleValue());

		    // amt1 (negotiated amt for negotiable curr) 
		    trade.setQuantity(new Double(out.jTextField1.getText()).doubleValue()); 
		    // amt2  (negotiated amt for non-negotiable curr) nominal =  quantity * price
		    trade.setNominal(new Double(out.jTextField2.getText()).doubleValue());  
		    
		    trade.setTradedesc1(product.getProductname());
		    if(productSubType.equalsIgnoreCase("FXSWAP")) {	    	
		    	double fwdAmt = 0.0;
				try {
					if(trade.getType().equalsIgnoreCase("BUY/SELL")) {
						 trade.setTradeAmount(new Double(swap.jTextField1.getText()).doubleValue());   // used as quantity for FXSWAP in FX 
					         fwdAmt = ((trade.getTradeAmount() * -1) * swap.jTextField4.getDoubleValue());
					} else  {
						 trade.setTradeAmount(new Double(swap.jTextField1.getText()).doubleValue());   // used as quantity for FXSWAP in FX 
						fwdAmt = ((swap.jTextField1.getDoubleValue()) * swap.jTextField4.getDoubleValue());
					}
					  trade.setYield(swap.jTextField2.getDoubleValue());
				} catch (ParseException e1) {					
					e1.printStackTrace();
				}
			    
				// used as fwd amt for FXSWAP in FX
			    trade.setEffectiveDate(swap.swapDate.getSelectedDateAsText());
			    try {
					trade.setSecondPrice(swap.jTextField4.getDoubleValue());  // used as forward rate 
				} catch (ParseException e) {						
					e.printStackTrace();
				}
				
		    } else {
		    	trade.setTradeAmount(0);   // used as quantity 0 if not fxswap
			    trade.setYield(0);         // used as fwd amt 0 if not fxswap
			    trade.setEffectiveDate(trade.getDelivertyDate());
		    }		    
		    
		    //@yogesh 01/02/2015
		    // for merchant float trade effective date is fwdOp.start da
		    String instrumentType = trade.getAttributeValue("InstrumentType");	
		    
			if (instrumenTypeVal.contains(instrumentType) && trade.getTradedesc1().equalsIgnoreCase(FXFORWARDOPTION)) {				 
				 trade.setEffectiveDate(commonUTIL.convertDateTOString(fwdOp.startDate.getDate()));				 
			 } 
		    
		    trade.setProductId(product.getId());
		    if(b2bconfig != null) {
				trade.setAttribute("B2BFlag", "True"); 
				trade.setB2Bflag(true);
				b2bconfig.setCounterPartyID(functionality.getCurrSPlitCP(functionality.b2bBook.getSelectedIndex()).getId());
				
				try {
					int i = Integer.valueOf(functionality.b2bTransferTo.getText()).intValue();
					if(i==0) {
				
					commonUTIL.showAlertMessage("Enter Rate for B2b");
					return;
					}
					b2bconfig.setRate(i);
					trade.setB2bConfig(b2bconfig );
				} catch(NumberFormatException e) {
					commonUTIL.showAlertMessage("Enter Rate for B2b");
					return;
				}
				
		    }
		    isTradeProper = true;
		   // trade.setVersionID(0);		    
		}
		
		// this method is used to populate currencysplit data
		public void populateRountingData() {
			
			//FilterValues.isavaliableForSplit(trade.getTradedesc(),trade.getBookId(),)
			if(productSubType.equalsIgnoreCase(FXFORWARDOPTION))
				return;
			if(productSubType.equalsIgnoreCase(FXTAKEUP))
				return;
			Trade  originaltrade  = null;
			if(trade == null) {
				originaltrade = new Trade();
				if(commonUTIL.isEmpty(basicData.book.getName()) || commonUTIL.isEmpty(basicData.currencyPair.getText()))
				   return;		
				
			    fillTrade(originaltrade,"NEW");
			}	else {
				originaltrade = trade;
				fillTrade(originaltrade,"NEW");
				if(!basicData.buysell.getText().equalsIgnoreCase(originaltrade.getType())) {
					originaltrade.setPrice(new Double(out.jTextField4.getText()).doubleValue());
					originaltrade.setQuantity(new Double(out.jTextField1.getText()).doubleValue()); 
					originaltrade.setNominal(new Double(out.jTextField2.getText()).doubleValue()); 
				//	originaltrade.setType(basicData.buysell.getText());
				}
			}
			if(productSubType.equalsIgnoreCase(FXFORWARDOPTION))
				return;
			if(productSubType.equalsIgnoreCase(FXTAKEUP))
				return;
			if(commonUTIL.isEmpty(originaltrade.getTradedesc()) || originaltrade.getBookId() == 0)
				 return;
			double rate1 = 0;
			double rate2 = 0;
			double farrate1 =0;
			double farrate2 = 0;			
			
				try {
					if(!commonUTIL.isEmpty(functionality.jTextField2.getText()) && commonUTIL.isNumeric(functionality.jTextField2.getText()))
					rate1 = functionality.jTextField2.getDoubleValue();
					if(!commonUTIL.isEmpty(functionality.jTextField3.getText()) && commonUTIL.isNumeric(functionality.jTextField3.getText()))
						rate2 = functionality.jTextField3.getDoubleValue();
					if(!commonUTIL.isEmpty(functionality.FarRate1.getText()) && commonUTIL.isNumeric(functionality.FarRate1.getText()))
						farrate1 = functionality.FarRate1.getDoubleValue();
					if(!commonUTIL.isEmpty(functionality.FarRate2.getText()) && commonUTIL.isNumeric(functionality.FarRate2.getText()))
						farrate2 = functionality.FarRate2.getDoubleValue();
		
				 // Vector<Trade> rountingTrades =	FXSplitUtil.getRountingData(trade,remoteReference);
					Vector<Trade> rountingTrades  =             functionality.getRoutingData();
					if(commonUTIL.isEmpty(rountingTrades)) {
						rountingTrades = FXSplitUtil.getRountingData(originaltrade,remoteReference,farrate1,farrate2);
						if(!commonUTIL.isEmpty(rountingTrades) && rountingTrades.size() >0 ) {
							if(trade == null || trade.getId() == 0) {
						         if(rountingTrades.size() == 1) {
						        	 FXSplitUtil.splitTrade(null, null, originaltrade, 0, 0,0,0);
						         } else {
				                    rountingTrades =     FXSplitUtil.splitTrade(rountingTrades.get(1), rountingTrades.get(3), originaltrade, rate1, rate2,farrate1,farrate2);
						         }
							} else  {
								rountingTrades = FXSplitUtil.splitTrade(rountingTrades, rate1, rate2,farrate1,farrate2);
							}
						}
					} else {
						if(!commonUTIL.isEmpty(rountingTrades) && rountingTrades.size() >0 )
						//rountingTrades =     FXSplitUtil.splitTrade(rountingTrades.get(1), rountingTrades.get(3), originaltrade, rate1, rate2);
							if(trade == null || trade.getId() == 0) {
								if(rountingTrades.size() == 1) {
						        	 FXSplitUtil.splitTrade(null, null, originaltrade, 0, 0,0,0);
						         } else {
				                    rountingTrades =     FXSplitUtil.splitTrade(rountingTrades.get(1), rountingTrades.get(3), originaltrade, rate1, rate2,farrate1,farrate2);
						         }
						}	else  {
							if(!basicData.buysell.getText().equalsIgnoreCase(trade.getType())) {
								originaltrade.setType(basicData.buysell.getText());
								rountingTrades = FXSplitUtil.splitTrade(rountingTrades, rate1, rate2,originaltrade,farrate1,farrate2,false); // add change done on the screen.
							} else {
								rountingTrades = FXSplitUtil.splitTrade(rountingTrades, rate1, rate2,originaltrade,farrate1,farrate2,false);
							}
						}
					}
               //  functionality.setRoutingData(rounting);
				//  fillTrade(trade,"NEW");
				 
				  if(!commonUTIL.isEmpty(rountingTrades)) {
						functionality.setRoutingData(rountingTrades);
					}
				} catch (ParseException e) {
					isTradeProper = false;
					e.printStackTrace();
				}
			
		}
		
		public void getDataFromTrade(int id,String name) { 
			try {
			if(name.equalsIgnoreCase("Book")) {
				Book book = new Book();
				book.setBookno(id);
				book = (Book) remoteReference.selectBook(book);
				basicData.book.setText(book.getBook_name());
				basicData.book.setName(new Integer(id).toString());
			} else if(name.equalsIgnoreCase("CounterParty") || name.equalsIgnoreCase("Trader")) {
				LegalEntity le = new LegalEntity();
				
				le = (LegalEntity) remoteReference.selectLE(id);
				if(name.equalsIgnoreCase("Trader")) {
					basicData.jTextField7.setText(le.getName());
					basicData.jTextField7.setName(new Integer(id).toString());
				} else {
				   basicData.counterPary.setSelectedItem(le.getName());
				   basicData.counterPary.setName(new Integer(id).toString());
				}
			}
			}catch(RemoteException  e) {
				commonUTIL.displayError("Fx Trade Panel " , " getDataFromTrade ", e);
			}
			
		}
		
		public void openTradeInWindow(Trade trade) {
			//trade = new Trade();
			if(trade == null)
				return;
			
			int atRows = attributes.jTable1.getRowCount();
			for(int t=0;t <atRows; t++) {
				attributes.jTable1.setValueAt("", t, 1);
			}
			
			//@yogesh 01/02/2015
			// this has to be before setAttribute(trade.getAttributes());
			/*if(trade.getTradedesc1().equalsIgnoreCase(FXFORWARDOPTION) && trade.getVersion() > 1) {
				
				//@yogesh 04/02/2015
				// checks if trade has take ups. if yes then disable InstrumentType attribute
				Collection<Attribute> list = new Vector<Attribute>();
				try {
					list = remoteReference.selectWhereAttribute("attributename like 'ParentID' and attributevalue =" + trade.getId());
				} catch (RemoteException e) {				
					e.printStackTrace();
				}
				//disable InstrumentType attribute
				if (list.size() > 0) {
					attributes.isfwOpTrade = true;
				}
				
			}*/
			getDataFromTrade(trade.getBookId(),"Book");

			//@yogesh
			//always first setAttribute because in this method a hashtable is set 
			//with all attributes and ite values. This hastable is used to get 
			//values ahead.
			setAttribute(trade.getAttributes());
			  
			if(trade.isMirrorTrade()) {
				mirrorBook = getBook(trade.getMirrorBookid());
				Book book = getBook(trade.getCpID());
				basicData.counterPary.setSelectedItem(book.getBook_name());
				basicData.counterPary.setName(new Integer(book.getBookno()).toString());
			} else {
				getDataFromTrade(trade.getCpID(),"CounterParty");
			}
			getDataFromTrade(trade.getTraderID(),"Trader");
			basicData.currencyPair.setText(trade.getTradedesc());
			out.jLabel2.setText(trade.getTradedesc().substring(0, 3));
			
			basicData.buysell.setText(trade.getType());
			out.outRightDate.setSelectedDate(commonUTIL.stringToDate(trade.getDelivertyDate(),true));
			out.jTextField6.setText(trade.getStatus());
			productType = trade.getProductType();
			productSubType = trade.getTradedesc1();
		    trade.setStatus(out.jTextField6.getText());
		    trade.setProductType(productType);

		    out.jTextField4.setText(Double.toString(trade.getPrice()).toString());
		    if(trade.getType().equalsIgnoreCase("BUY") || trade.getType().equalsIgnoreCase("BUY/SELL")) {
		       out.jTextField1.setText(commonUTIL.getStringFromDoubleExp(trade.getQuantity()).toString());
		       out.jTextField2.setText(commonUTIL.getStringFromDoubleExp(trade.getNominal()).toString());
		       basicData.buysell.setBackground(Color.green);
		    } else  {
		    	out.jTextField1.setText(commonUTIL.getStringFromDoubleExp(trade.getQuantity()).toString());
		    	 out.jTextField2.setText(commonUTIL.getStringFromDoubleExp(trade.getNominal()).toString());
		    	 basicData.buysell.setBackground(Color.red);
		    }
		    app.openTrade(trade,false);
		   
		    out.jTextField5.setText(new Integer(trade.getId()).toString());
		    
		//    processActionData(actionstatus, productSubType);
		    actionController = true;
		    processActionData(actionstatus,productType,trade.getTradedesc1(),out.jTextField6.getText(),remoteTrade);
		    actionController = false;
		    if(trade.getTradedesc1().equalsIgnoreCase(FXSWAP)) {		    	

				//@yogesh 07/02/2015
				// if called after takeUpWindow is opened then funtionality buttons disable while opening 
				//takeup trade should be enabled
		    	enableTakeUpDisabledFunctionalityButton();
		    	
		    	swap.jTextField1.setText(commonUTIL.getStringFromDoubleExp(trade.getTradeAmount()).toString());
		    	swap.jTextField2.setText(commonUTIL.getStringFromDoubleExp(trade.getYield()).toString());
		    	swap.swapDate.setSelectedDate(commonUTIL.stringToDate(trade.getEffectiveDate().toString(),true));
		    	swap.jTextField4.setValue(trade.getSecondPrice());
		    	
		    	
		    	swap.setVisible(true);
		    	fwdOp.setVisible(false);
		    	basicData.jRadioButton2.setSelected(true);
		    	
		    	basicData.jRadioButton2.setEnabled(true);
		    	basicData.jRadioButton1.setSelected(false);
		    	basicData.jRadioButton5.setEnabled(false);
		    	basicData.jRadioButton1.setEnabled(false);
		    	basicData.jRadioButton0.setEnabled(false);
		    	basicData.jRadioButton0.setSelected(false);
		    	basicData.jRadioButton6.setSelected(false);
		    	basicData.jRadioButton7.setSelected(false);
		    	
		    	rollpanel.setVisible(false);
		    	productSubType = "FXSWAP";
		    }
		    
		    if(trade.getTradedesc1().equalsIgnoreCase(FXFORWARDOPTION)) {
		    	
		    	//@yogesh 07/02/2015
				// if called after takeUpWindow is opened then funtionality buttons disable while opening 
				//takeup trade should be enabled
		    	enableTakeUpDisabledFunctionalityButton();
		    	
		    	swap.setVisible(false);
		    	rollpanel.setVisible(false);
		    	fwdOp.setVisible(true);
		    	
		    	swap.jTextField1.setText("0.0");
				swap.jTextField2.setText("0.0");
				swap.jTextField4.setText("0.0");
						    	
		    	basicData.jRadioButton0.setEnabled(false);
		    	basicData.jRadioButton0.setSelected(false);
		    	basicData.jRadioButton1.setEnabled(false);
		    	basicData.jRadioButton1.setSelected(false);
		    	basicData.jRadioButton2.setEnabled(false);
		    	basicData.jRadioButton2.setSelected(false);
		    	basicData.jRadioButton5.setSelected(true);
		    	basicData.jRadioButton5.setEnabled(true);    	
		    	
		    	basicData.jRadioButton6.setSelected(false);
		    	basicData.jRadioButton7.setSelected(false);
		    	
		    	functionality.jButton2.setSelected(false);
		    	functionality.jButton3.setSelected(false);
		    	String currP = trade.getTradedesc();
		    	fwdOp.jLabel2.setText(currP.substring(0, 3));
				fwdOp.jLabel3.setText(currP.substring(4, 7));
				fwdOp.startDate.setEnabled(false);	
				 // attributeDataValue is set in setAttributes method
				 String instrumentType = trade.getAttributeValue("InstrumentType");
				 
				 //@ yogesh 01/02/2015
				 // fwdoptionEnd date is shown
				 if (instrumenTypeVal.contains(instrumentType)) {
					 fwdOp.startDate.setEnabled(true);
					 fwdOp.startDate.setEditable(true);
					 fwdOp.startDate.setDate(commonUTIL.convertStringtoSQLDate(trade.getEffectiveDate()));									 				 
				 } else {					 
					 fwdOp.startDate.setDate(commonUTIL.convertStringtoSQLDate(attributeDataValue.get("Trade Date")));					 
				 }
				 
				//@ yogesh 01/02/2015
				 // fwdoption date shown
				 fwdOp.startDate.setDate(commonUTIL.convertStringtoSQLDate(trade.getEffectiveDate()));	
				 
				 functionality.jButton0.setEnabled(true);
				 fwdOp.primaryC.setValue(trade.getQuantity());
				 fwdOp.quotingC.setValue(trade.getNominal());
				 rollpanel.setVisible(false);
				 productSubType = FXFORWARDOPTION;
		    	 try {		    		
		    		childTrades 	= remoteTrade.getChildTrades(trade.getId());
		    		Vector childes = (Vector) childTrades;
		    		if(!childes.isEmpty())  {	
		    			//@yogesh 13/02/2015
		    			// if fwdopt trade has take up then disable instrumentType
		    			attributes.isfwOpTrade = true;
		    			setOutStandingBalONFwdOption(childes,trade);		    		
		    		} else {
		    			attributes.isfwOpTrade = false;
		    		}		    		
				} catch (RemoteException e) {					
					e.printStackTrace();
				}
				
		    } 
		    
		    if(trade.getTradedesc1().equalsIgnoreCase(FXFORWARD))  {
		    	
		    	//@yogesh 07/02/2015
				// if called after takeUpWindow is opened then funtionality buttons disable while opening 
				//takeup trade should be enabled
		    	enableTakeUpDisabledFunctionalityButton();
		    	
		    	swap.setVisible(false);
		    	rollpanel.setVisible(false);
		    	fwdOp.setVisible(false);
		    	
		    	swap.jTextField1.setText("0.0");
				swap.jTextField2.setText("0.0");
				swap.jTextField4.setText("0.0");
										    	
				basicData.jRadioButton0.setEnabled(false);
		    	basicData.jRadioButton0.setSelected(false);
		    	basicData.jRadioButton1.setSelected(true);
		    	basicData.jRadioButton1.setEnabled(true);
		    	basicData.jRadioButton2.setEnabled(false);
		    	basicData.jRadioButton2.setSelected(false);
		    	basicData.jRadioButton5.setEnabled(false);
		    	basicData.jRadioButton5.setSelected(false);		    	
		    	basicData.jRadioButton6.setSelected(false);
		    	basicData.jRadioButton7.setSelected(false);
		    	fwdOp.setVisible(false);
		    	  rollpanel.setVisible(false);
		    	//productSubType = "FXSWAP";
		    }
		    
		 //   basicData.jRadioButton6.setEnabled(true);
		//    basicData.jRadioButton7.setEnabled(true);
		    
		    
		    if(trade.getTradedesc1().equalsIgnoreCase(FXTAKEUP))  {
		    	
		    	productSubType = FXTAKEUP;
		    	
		    	// @yogesh 12/02/2014
		    	//Instrument type is not editable for takeUp trade
		    	attributes.isfwOpTrade = true;
		    	
		    	out.jTextField4.setEditable(false);
		    	
		    	swap.setVisible(false);
		    	rollpanel.setVisible(false);
		    	fwdOp.setVisible(false);
		
		    	swap.jTextField1.setText("0.0");
				swap.jTextField2.setText("0.0");
				swap.jTextField4.setText("0.0");
				
				basicData.currencyPair.setEditable(false);
				basicData.jRadioButton0.setEnabled(true);
		    	basicData.jRadioButton0.setSelected(true);
				basicData.jRadioButton1.setSelected(false);
		    	basicData.jRadioButton1.setEnabled(false);
		    	basicData.jRadioButton2.setEnabled(false);
		    	basicData.jRadioButton2.setSelected(false);		    	
		    	basicData.jRadioButton5.setEnabled(false);    	
		    	basicData.jRadioButton5.setSelected(false);
		    	basicData.jRadioButton6.setEnabled(false);    	
		    	basicData.jRadioButton6.setSelected(false);
		    	basicData.jRadioButton7.setEnabled(false);    	
		    	basicData.jRadioButton7.setSelected(false);
		    	
		    	functionality.jButton1.setEnabled(false);
		    	functionality.jButton2.setEnabled(false);
		    	functionality.jButton3.setEnabled(false);
		    	//functionality.jButton4.setEnabled(false);
		    	functionality.jButton5.setEnabled(false);
		    	
		    	functionality.jButton1.setSelected(false);
		    	functionality.jButton2.setSelected(false);
		    	functionality.jButton3.setSelected(false);
		    	functionality.jButton4.setSelected(false);
		    	functionality.jButton5.setSelected(false);

		    }
		    
		    rollpanel.setVisible(false);
		    setTrade(trade);
		}
		
		private void getBooksOnPoDataCombo1(DefaultTableModel booktablemodel ,int poID) {
			if(books.isEmpty()) {
				getBookDataCombo1(booktablemodel, books);
			} 
			Hashtable<Integer,Book> cloneBooks = (Hashtable<Integer,Book>) books.clone();
			Vector<Book> books = ReferenceDataCache.getALLPOBooks(poID);
			if(!commonUTIL.isEmpty(books)) {
				for(int i=0;i<books.size();i++) {
					Book book = books.get(i);
					 booktablemodel.insertRow(i, new Object[]{book.getBookno(),book.getBook_name()});
				}
			}
		/*	ReferenceDataCache.
		//	booktablemodel =  new DefaultTableModel(s,0);
				Enumeration<Integer> keys =  cloneBooks.keys();
				   
		    	 int i=0;
		    	while(keys.hasMoreElements()) {
		    		
		    		Integer key =	(Integer) keys.nextElement();
		    		Book book =   cloneBooks.get(key);
		    		if(book.getLe_id() == poID) {
		    		  booktablemodel.insertRow(i, new Object[]{book.getBookno(),book.getBook_name()});
		    		  i++;
		    		}	    		
		    		
			}	*/
		    cloneBooks.clear();
		    cloneBooks = null;			
		}
		
		private void setOutStandingBalONFwdOption(Vector<Trade> childTrade,Trade trade) {
			if(childTrade.isEmpty() || childTrade == null)
				return;
			double primaryCurrAmt = trade.getQuantity();
			double qoutCurrAmt =trade.getNominal();
			if(trade.getQuantity() > 0.) {
			for(int i=0;i <childTrade.size();i++) {
				Trade ctrade = (Trade) childTrade.elementAt(i);
				primaryCurrAmt = primaryCurrAmt - ctrade.getQuantity();
				qoutCurrAmt = qoutCurrAmt +( ctrade.getNominal() * -1);
				
			  }
			fwdOp.primaryC.setValue(primaryCurrAmt);
			fwdOp.quotingC.setValue(qoutCurrAmt);
			}
			if(trade.getQuantity() < 0.) {
				for(int i=0;i <childTrade.size();i++) {
					Trade ctrade = (Trade) childTrade.elementAt(i);
					primaryCurrAmt = primaryCurrAmt + (ctrade.getQuantity() * -1);
					qoutCurrAmt = qoutCurrAmt - ctrade.getNominal();
					
				  }
				fwdOp.primaryC.setValue(primaryCurrAmt);
				fwdOp.quotingC.setValue(qoutCurrAmt);
			}
		}
		private void setAttribute(String attributesV) {
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
		private JTable fillFavourites(Object __rows12 [][],JTextField textField,JTextField textField2,JLabel label,boolean dateField,String type) {
			Color colr = new Color(239,239,242);
			
			 __table = new JTable(new JTableButtonModel(__rows12));
			 __table.setRowHeight(30);
			 __table.setColumnSelectionAllowed(false);
			 __table.setRowSelectionAllowed(true);
			 __table.setIntercellSpacing(new Dimension(0, 0));
			 __table.setShowGrid(false);
			 __table.setBackground(colr);
			
			    defaultRenderer = __table.getDefaultRenderer(JButton.class);
			    __table.setDefaultRenderer(JButton.class,
						       new JTableButtonRenderer(defaultRenderer));
			    __table.setPreferredScrollableViewportSize(new Dimension(400, 200));
			    __table.addMouseListener(new JTableButtonMouseListener(__table,textField,textField2,label,dateField,type));
			    if(label  != null) {
			    //	currencyPair = textField.getText();
			    	//functionality.refreshPositionTable(currencyPair,new Integer(basicData.book.getName()).intValue());
			    }
			    
			    return __table;
			
		}
		private JTable fillFavourites(Object __rows12 [][],JTextField textField,JTextField textField2,JLabel label,boolean dateField,Book mirrorBook,String type) {
			Color colr = new Color(239,239,242);
			
			 __table = new JTable(new JTableButtonModel(__rows12));
			 __table.setRowHeight(30);
			 __table.setColumnSelectionAllowed(false);
			 __table.setRowSelectionAllowed(true);
			 __table.setIntercellSpacing(new Dimension(0, 0));
			 __table.setShowGrid(false);
		//	 __table.setBackground(colr);
			
			    defaultRenderer = __table.getDefaultRenderer(JButton.class);
			    __table.setDefaultRenderer(JButton.class,
						       new JTableButtonRenderer(defaultRenderer));
			    __table.setPreferredScrollableViewportSize(new Dimension(400, 200));
			    __table.addMouseListener(new JTableButtonMouseListener(__table,textField,textField2,label,dateField,mirrorBook,type));
			    if(label  != null) {
			    //	currencyPair = textField.getText();
			    	//functionality.refreshPositionTable(currencyPair,new Integer(basicData.book.getName()).intValue());
			    }
			    
			    return __table;
			
		}
		private JTable fillFavourites(Object __rows12 [][],TableExComboBox textField,JTextField textField2,JLabel label,boolean dateField,Book mirrorBook,String type) {
			Color colr = new Color(239,239,242);
			
			 __table = new JTable(new JTableButtonModel(__rows12));
			 __table.setRowHeight(30);
			 __table.setColumnSelectionAllowed(false);
			 __table.setRowSelectionAllowed(true);
			 __table.setIntercellSpacing(new Dimension(0, 0));
			 __table.setShowGrid(false);
		//	 __table.setBackground(colr);
			
			    defaultRenderer = __table.getDefaultRenderer(JButton.class);
			    __table.setDefaultRenderer(JButton.class,
						       new JTableButtonRenderer(defaultRenderer));
			    __table.setPreferredScrollableViewportSize(new Dimension(400, 200));
			    __table.addMouseListener(new JTableButtonMouseListener(__table,textField,textField2,label,dateField,mirrorBook,type));
			    if(label  != null) {
			    //	currencyPair = textField.getText();
			    	//functionality.refreshPositionTable(currencyPair,new Integer(basicData.book.getName()).intValue());
			    }
			    
			    return __table;
			
		}
		private JTable fillFavourites(Object __rows12 [][],JDatePicker textField2,JLabel label,boolean dateField,String type) {
			
			Color colr = new Color(239,239,242);
			 __table = new JTable(new JTableButtonModel(__rows12));
			 __table.setRowHeight(30);
			 __table.setColumnSelectionAllowed(false);
			 __table.setRowSelectionAllowed(true);
			 __table.setIntercellSpacing(new Dimension(0, 0));
			 __table.setShowGrid(false);
			// __table.setBackground(colr);
			    defaultRenderer = __table.getDefaultRenderer(JButton.class);
			    __table.setDefaultRenderer(JButton.class,
						       new JTableButtonRenderer(defaultRenderer));
			    __table.setPreferredScrollableViewportSize(new Dimension(400, 200));
			   if( productSubType.equalsIgnoreCase(FXSWAP) )
					   __table.addMouseListener(new JTableButtonMouseListener(__table,textField2,label,dateField,type));
			    __table.addMouseListener(new JTableButtonMouseListener(__table,textField2,label,dateField,type));
			    if(label  != null) {
			    //	currencyPair = textField.getText();
			    	//functionality.refreshPositionTable(currencyPair,new Integer(basicData.book.getName()).intValue());
			    }
			    
			    return __table;
			
		}
private JTable fillFavourites(Object __rows12 [][],JDatePicker textField2,JDatePicker textField3,JLabel label,boolean dateField,String type) {
			
			Color colr = new Color(239,239,242);
			 __table = new JTable(new JTableButtonModel(__rows12));
			 __table.setRowHeight(30);
			 __table.setColumnSelectionAllowed(false);
			 __table.setRowSelectionAllowed(true);
			 __table.setIntercellSpacing(new Dimension(0, 0));
			 __table.setShowGrid(false);
			// __table.setBackground(colr);
			    defaultRenderer = __table.getDefaultRenderer(JButton.class);
			    __table.setDefaultRenderer(JButton.class,
						       new JTableButtonRenderer(defaultRenderer));
			    __table.setPreferredScrollableViewportSize(new Dimension(400, 200));
		//	   if( productSubType.equalsIgnoreCase(FXSWAP) )
					   __table.addMouseListener(new JTableButtonMouseListener(__table,textField2,textField3,label,dateField,type));
			 //   __table.addMouseListener(new JTableButtonMouseListener(__table,textField2,label,dateField,type));
			    if(label  != null) {
			    //	currencyPair = textField.getText();
			    	//functionality.refreshPositionTable(currencyPair,new Integer(basicData.book.getName()).intValue());
			    }
			    
			    return __table;
			
		}
		private Object [][] getRows(String name) {
			Object rows [][] = null;
		
			try {
			//	if(name.equalsIgnoreCase("Book")) {
				Favorities fav = new Favorities();
				fav.setType(name);
				fav.setUserId(user.getId());
				Vector favData = 	(Vector) remoteReference.selectFavourites(fav);
				rows =  fillRows(favData);
				//}
			} catch (RemoteException e) {				
				e.printStackTrace();		
			}
			return rows;
		
		}
		
		public Object [][] fillRows(Vector rows) {
			int size = rows.size()%4;
			if(size > 1)
				 size = 1;
			JButton cell = null;
			int i =0;
			int rowsize = 0;
			if(rows.size() > 4) {
				rowsize = (rows.size()/4)+size;
			} else {
				rowsize = rows.size();
			}
			Object [][] buttons = new JButton [rowsize] [4];
			
			Color colr = new Color(246,143,8);
			for(int row =0 ; row < buttons.length; ++row) {
				 for(int column =0; column<buttons[row].length;++column) {
					 if(i == rows.size())
						 break;
					 Favorities fav = (Favorities) rows.elementAt(i);
					 cell = new JButton();
					 
					 cell.setBackground(Color.orange);
					 cell.setText(fav.getTypeName());
					 cell.setName(fav.getTypeValue());
					 buttons[row][column] = cell;
					
					 i++;
					 
				 }
			}
	
			return buttons;
		}
		
		 public double getSignOnAmount(double amount,int i) {
				
				return amount * i;
			 }
			
			private String getAttributeValue() {
				
				String attributesV  = "";
				
					/*Enumeration<String> keys =  attributeDataValue.keys();
					while(keys.hasMoreElements()) {
						String key = (String) keys.nextElement();
						String value = attributeDataValue.get(key);
						if(value != null && value.length() > 0) 
							attributesV = attributesV + key+ "=" + value + ";";
								
					} */
						//int tradeDateRowno = -1;
						//String strTradeDate = "TradeModifiedDateTime";
						//String tradeDateVal="";
						//boolean tradeDateFilled = false;
				// this need to be cache
						Vector<Attribute> attributesData = attributes.getData();
						for(int i=0;i<attributesData.size();i++) {
							Attribute att = attributesData.get(i);
							String value = att.getValue();
						//	System.out.println(att.getName());
							/*if (att.getName().equals(strTradeDate)) {
								
								tradeDateRowno = i;
							}*/
							if(value != null ) {
								
								/*if (tradeDateRowno >= 0) {
									
									tradeDateFilled = true;
									tradeDateVal = value;
								}*/
								
								if(value.trim().length() > 0)
							    attributesV = attributesV + att.getName()+ "=" + att.getValue() + ";";
							}
							
						}
						
						/*if (!tradeDateFilled) {
							
							tradeDateVal = commonUTIL.getCurrentDateTime();
							attributesV = attributesV + strTradeDate+ "=" + tradeDateVal + ";";
							attributes.jTable1.setValueAt(tradeDateVal, tradeDateRowno, 1);
							
						}*/
					
				
				if(attributesV.trim().length() > 0)
				return attributesV.substring(0, attributesV.length()-1);
				return attributesV;
			}
			
	
	class JTableButtonMouseListener implements MouseListener {		
		
		  private JTable __table;
		  JTextField comp = null;
		  TableExComboBox comp1 = null;
		  JDatePicker swapField = null;
		  JLabel label = null;
		  JDatePicker datefield = null;
		  boolean dateF = false;
		  String type = "";
		 // Book mirrorBook = null;
		  private void __forwardEventToButton(MouseEvent e) {
		    TableColumnModel columnModel = __table.getColumnModel();
		    int column = columnModel.getColumnIndexAtX(e.getX());
		    int row    = e.getY() / __table.getRowHeight();
		    Object value;
		    JButton button;
		    
		    MouseEvent buttonEvent;
	
		    if(row >= __table.getRowCount() || row < 0 ||
		       column >= __table.getColumnCount() || column < 0)
		      return;
	
		    value = __table.getValueAt(row, column);
	
		    if(!(value instanceof JButton))
		      return;
	
		    button = (JButton)value;
	
		    buttonEvent =
		      (MouseEvent)SwingUtilities.convertMouseEvent(__table, e, button);
		    button.dispatchEvent(buttonEvent);
		
		    mirrorBook.setBookno(0);
		    //  buttonEvent.
		    //  System.out.println( button.getText() + " " + button.getName());
		   if(dateF) {
			  //As we dont need to calculate near date based on tenor we have commented below method
			   // generateDeliveryDate(datefield,button.getName());
			   String swapTenor = button.getName();
			   
			   //commented below  far date is currentdate + tenor
			   /*int t = (new Integer(swapTenor.substring(0, swapTenor.length()-1)).intValue() * 2);
			   swapTenor = t + swapTenor.substring(swapTenor.length()-1, swapTenor.length());*/
			   generateDeliveryDate(swapField,swapTenor,datefield);		   
			   
		   } else {
			   if(comp != null) {
			  comp.setText(button.getText());
			  comp.setName(button.getName());
			   } 
			   if(comp1 != null) {
				   comp1.setSelectedItem(button.getText());
					  comp1.setName(button.getName());
			   }
			  if(type.equalsIgnoreCase("Book")) {
				  String currencyPair = basicData.currencyPair.getText();
				  if(!commonUTIL.isEmpty(currencyPair)) {
					  Vector vector;
					try {
						vector = (Vector) remoteReference.getCurrencySplitConfig(Integer.parseInt(button.getName()), currencyPair);
						if(!commonUTIL.isEmpty(vector)) {
							// @yogesh 07/02/2015
							//split checkbox should also be selected but disable when combination of
							// book and currencypair is found in currncySplitConfig
							out.jCheckBox2.setSelected(true);
							out.jCheckBox2.setEnabled(false);
																				
							if(trade == null || trade.getId() == 0) 
							    functionality.clearRounting();
							sconfig =  (CurrencySplitConfig)vector.elementAt(0);
							functionality.jLabel2.setText(sconfig.getFirstCurrencySplit());
							populateRountingData();
							functionality.jLabel3.setText(sconfig.getSecondCurrencySPlit());
							// functionality.jButton8.setEnabled(true);
							 // mpankaj 02/02
							
							// @ yogesh 04/02/2105
							// split currency panel is made visible if split currency chechbox is set selected above
							// and if a split currency config is found for book and currency 
							functionality.jPanel2.setVisible(true);
							if (basicData.jRadioButton1.isSelected() || basicData.jRadioButton5.isSelected()) {
								functionality.FarRate1.setVisible(false);
								functionality.FarRate2.setVisible(false);
								functionality.jLabel4.setVisible(false);
								functionality.jLabel5.setVisible(false);
							}
						 } else {
							out.jCheckBox2.setSelected(false);
							out.jCheckBox2.setEnabled(false);
							functionality.clearRounting();
							functionality.jPanel2.setVisible(false);
						}
						  
					} catch (NumberFormatException | RemoteException e1) {							
						e1.printStackTrace();
					}
				  }					
			  }
			  if(type.equalsIgnoreCase("CurrencyPair")) {
				  String book = basicData.book.getName();
				  if(!commonUTIL.isEmpty(book)) {
					  Vector vector;
					try {
						vector = (Vector) remoteReference.getCurrencySplitConfig(Integer.parseInt(book), button.getName());
						if(!commonUTIL.isEmpty(vector)) {
							out.jCheckBox2.setEnabled(true);
							if(trade == null || trade.getId() == 0)
							     functionality.clearRounting();
								sconfig =  (CurrencySplitConfig)vector.elementAt(0);
								functionality.jLabel2.setText(sconfig.getFirstCurrencySplit());
								populateRountingData();
								functionality.jLabel3.setText(sconfig.getSecondCurrencySPlit());
							} else {
								out.jCheckBox2.setSelected(false);
								out.jCheckBox2.setEnabled(false);
								 functionality.clearRounting();
								 functionality.jPanel2.setVisible(false);
							}
						
						} catch (NumberFormatException | RemoteException e1) {						
							e1.printStackTrace();
						}
				  	}				  
			  }
		   }
		   
		  if(label != null){
			  label.setText(comp.getText().substring(0, 3)); // negiotated currency			  
		  }
		  
		 //  comp.
		   
		    // This is necessary so that when a button is pressed and released
		    // it gets rendered properly.  Otherwise, the button may still appear
		    // pressed down when it has been released.
		    __table.repaint();
		  }
	
		  public JTableButtonMouseListener(JTable table,JTextField textField,JTextField textField2,JLabel labeln,boolean dateField,String type) {
		    __table = table;
		    comp = textField;
		    label = labeln;
		    dateF = dateField;
		    this.type = type;
		    
		  
		   // swapField = textField2;
		  }
		  public JTableButtonMouseListener(JTable table,JTextField textField,JTextField textField2,JLabel labeln,boolean dateField,Book mirrorBook,String type) {
			    __table = table;
			    comp = textField;
			    label = labeln;
			    dateF = dateField;
			    this.type = type;
			   // swapField = textField2;
			   // mirrorBook = mirrorBook;  
		  }
		  public JTableButtonMouseListener(JTable table,TableExComboBox textField,JTextField textField2,JLabel labeln,boolean dateField,Book mirrorBook,String type) {
			    __table = table;
			    comp1 = textField;
			    label = labeln;
			    dateF = dateField;
			    this.type = type;
			   // swapField = textField2;
			   // mirrorBook = mirrorBook;  
		  }
		  public JTableButtonMouseListener(JTable table,JDatePicker  textField2,JLabel labeln,boolean dateField,String type) {
			    __table = table;
			    //datefield = textField;
			    label = labeln;
			    dateF = dateField;
			    swapField = textField2;
			    this.type = type;
		  }
		  
		  public JTableButtonMouseListener(JTable table, JDatePicker textField,JDatePicker  textField2,JLabel labeln,boolean dateField,String type) {
			    __table = table;
			    datefield = textField;
			    label = labeln;
			    dateF = dateField;
			    swapField = textField2;
			    this.type = type;
		  }
	   
		  public void mouseClicked(MouseEvent e) {
		    __forwardEventToButton(e);
		  }
	
		  public void mouseEntered(MouseEvent e) {
		 //   __forwardEventToButton(e);
		  }
	
		  public void mouseExited(MouseEvent e) {
		//   __forwardEventToButton(e);
		  }
	
		  public void mousePressed(MouseEvent e) {
		//    __forwardEventToButton(e);
		  }
	
		  public void mouseReleased(MouseEvent e) {
		//    __forwardEventToButton(e);
		  }
		  public void generateDeliveryDate(JDatePicker dateField,String tenor,JDatePicker outRight1) {

			  	DateU dateissueDate = DateU.valueOf(out.outRightDate.getSelectedDate());
			  	dateissueDate.convertToCode(tenor);
				if(basicData.jRadioButton2.isSelected()) {					
					swap.swapDate.setSelectedDate(dateissueDate.getDate());
				} else {
					out.outRightDate.setSelectedDate(dateissueDate.getDate());
				}
			}
		 
		}
	
	class JTableButtonModel extends AbstractTableModel {
		  private Object[][] __rows = {
		      { "One", new JButton("Button One")},
		      { "Two", new JButton("Button Two") },
		      { "Three", new JButton("Button Three") },
		      { "Four", new JButton("Button Four") }
		    };
		  
		  JTableButtonModel(Object  o [][]) {
			  __rows = o;
		  }
	
		  private String[] __columns = { " 1  ", " 2    "," 3  ", " 4    " };
	
		  public String getColumnName(int column) { 
		    return __columns[column];
		  }
	
		  public int getRowCount() {
		    return __rows.length;
		  }
	
		  public int getColumnCount() {
		    return __columns.length;
		  }
	
		  public Object getValueAt(int row, int column) { 
		      return __rows[row][column];
		  }
	
		  public boolean isCellEditable(int row, int column) {
		    return false;
		  }
	
		  
		  
		  public Class getColumnClass(int column) {
			  if(getValueAt(0, column) == null) {
				  JButton nullbutton = new JButton();
				  return nullbutton.getClass();
			  }
		    return getValueAt(0, column).getClass();
		  }
		}
	
	@Override
	public void setTradeApplication(TradeApplication app) {
		
		this.app = app;
	}
		TradeApplication app = null;
		@Override
		public void setLimitPanel(BackOfficePanel panel) {
			
			
		}
	
		@Override
		public void setLimitBreachMarkOnAction(int i) {
			
			if(i < 0) {
					int red = 240;
					int green = 31;
					int blue = 122;
					Color colr = new Color(red,green,blue);
									out.jTextField6.setBackground(colr);
		} else {
			out.jTextField6.setBackground(new Color(128, 255, 255));
			out.jTextField6.setEditable(false);
		}
		
			
		}
	 boolean realTimeFlag = false;
		
		public void setRealTimeFlag(boolean flag) {
			if(realTimeFlag == false) 
				realTimeFlag = true;
			if(realTimeFlag == true) 
				realTimeFlag = false;
		}
	//@yogesh 01/02/2015
	// instrumentTypeVal
	 private void getInstrumentVal() {
        	Iterator it = instrumenTypeStartupData.iterator();
        	
        	while (it.hasNext()) {
        		StartUPData data = (StartUPData)it.next();
        		instrumenTypeVal.add(data.getName());
        	}
        }
	 
	 	   //@yogesh 04/02/2014
		  // check if split rates are filled if split rate checkbox is checked
		  private boolean checkRates() {		  
				boolean ratesOk = true;								
				//split checkbox is selected
				if (out.jCheckBox2.isSelected()) {
					String splitBaseNearRate = functionality.jTextField2.getText().toString();
					String splitQuoteNearRate = functionality.jTextField3.getText().toString();
					
					if (splitBaseNearRate.equals("") || Double.parseDouble(splitBaseNearRate) == 0)  {
						commonUTIL.showAlertMessage("Please enter Split Base Near Rate");
						return false;	
					} else if (splitQuoteNearRate.equals("") || Double.parseDouble(splitQuoteNearRate) == 0) {
						commonUTIL.showAlertMessage("Please enter Split Quote Near Rate");
						return false;	
					} 
					
					// swap radio button
					if (basicData.jRadioButton2.isSelected()) {					
						String swapFarAmt1 = swap.jTextField1.getText().toString();
						String swapFarAmt2 = swap.jTextField2.getText().toString();	
						String swapRate = swap.jTextField4.getText().toString();
						
						if (swapFarAmt1.equals("") ||  Double.parseDouble(swapFarAmt1) == 0 ) {
							commonUTIL.showAlertMessage("Please enter FarAmt1");
							return false;	
						} else if (swapFarAmt2.equals("") ||  Double.parseDouble(swapFarAmt2) == 0 ) {
							commonUTIL.showAlertMessage("Please enter FarAmt2");
							return false;	
						} else if (swapRate.equals("") ||  Double.parseDouble(swapRate) == 0 ) {
							commonUTIL.showAlertMessage("Please SwapRate");
							return false;	
						} 
						
						String farRate1 = functionality.FarRate1.getText().toString();
						String farRate2 = functionality.FarRate2.getText().toString();
						
						if (farRate1.equals("") || Double.parseDouble(farRate1) == 0)  {
							commonUTIL.showAlertMessage("Please enter Split FarRate1");
							return false;	
						} else if (farRate2.equals("") || Double.parseDouble(farRate2) == 0) {
							commonUTIL.showAlertMessage("Please enter Split FarRate2");
							return false;	
						}
					}
				} else {
					String amt1 = out.jTextField1.getText().toString();
					//String amt2 = out.jTextField2.getText().toString();
					String rate = out.jTextField4.getText().toString();
					
					if (amt1.equals("") ||  Double.parseDouble(amt1) == 0 ) {
						commonUTIL.showAlertMessage("Please enter Amt1");
						return false;	
					} else if (rate.equals("") ||  Double.parseDouble(rate) == 0 ) {
						commonUTIL.showAlertMessage("Please enter Rate");
						return false;	
					}
					
					// swap radio button
					if (basicData.jRadioButton2.isSelected()) {					
						String swapFarAmt1 = swap.jTextField1.getText().toString();
						String swapFarAmt2 = swap.jTextField2.getText().toString();	
						String swapRate = swap.jTextField4.getText().toString();
						
						if (swapFarAmt1.equals("") ||  Double.parseDouble(swapFarAmt1) == 0 ) {
							commonUTIL.showAlertMessage("Please enter FarAmt1");
							return false;	
						} else if (swapFarAmt2.equals("") ||  Double.parseDouble(swapFarAmt2) == 0 ) {
							commonUTIL.showAlertMessage("Please enter FarAmt2");
							return false;	
						} else if (swapRate.equals("") ||  Double.parseDouble(swapRate) == 0 ) {
							commonUTIL.showAlertMessage("Please SwapRate");
							return false;	
						} 
					}
				}
	            
				return ratesOk;
		  }
		  
		//@yogesh 04/02/2014
          // check if instrumentType is selected for every Trade
		  // checks if tradeDate is less then delivery date
		  private boolean checktradeDetails(Trade trade) {
			  boolean isTradeOk = true;
			  
			  if (trade.getAttributeValue("InstrumentType").equals("")) {
	            	commonUTIL.showAlertMessage("PleaseSelect Instrument Type");
	            	return false;	
	          }	            
	            
			  if ( (commonUTIL.stringToDate(trade.getAttributeValue("Trade Date"), true)).after( 
	            		 commonUTIL.stringToDate(trade.getDelivertyDate(), true)) ) {	            	 	
	            	 commonUTIL.showAlertMessage("Trade Date cannot be greater than Delivery Date");
	            	 return false;		            	 
	          }
	            
	          return isTradeOk;
		  }
		  
		//@yogesh 07/02/2015
			// enables all functionality buttons disable while opening 
			//takeup trade
		  private void enableTakeUpDisabledFunctionalityButton() {
			  out.jTextField4.setEditable(true);
			  functionality.jButton1.setEnabled(true);
			  functionality.jButton2.setEnabled(true);
			  functionality.jButton3.setEnabled(true);
			  functionality.jButton4.setEnabled(true);
			  functionality.jButton5.setEnabled(true);
		  }
	}
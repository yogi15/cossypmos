	package apps.window.tradewindow;
	
	import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;
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
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;
	
import apps.window.operationwindow.jobpanl.FilterValues;
	import apps.window.tradewindow.FXPanels.Swap;
import apps.window.tradewindow.FXPanels.TradeAttributes;
import apps.window.tradewindow.FXPanels.outRight;
import apps.window.tradewindow.panelWindow.FeesPanel;
import apps.window.tradewindow.panelWindow.PostingPanel;
import apps.window.tradewindow.panelWindow.SDIPanel;
import apps.window.tradewindow.panelWindow.TaskPanel;
import apps.window.tradewindow.panelWindow.TransferPanel;
	
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
	
	import org.dyno.visual.swing.layouts.Bilateral;
import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;
	
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

import constants.CommonConstants;
	
	import productPricing.Pricer;
import util.NumericTextField;
import util.ReferenceDataCache;
import util.commonUTIL;
import util.common.DateU;
import apps.window.tradewindow.FXPanels.BasicData;
import apps.window.tradewindow.FXPanels.FWDOptionPanel;
import apps.window.tradewindow.FXPanels.FunctionalityD;
import apps.window.tradewindow.FXPanels.Funtionality;
import apps.window.tradewindow.FXPanels.JTableButtonRenderer;
import apps.window.tradewindow.FXPanels.Swap;
import apps.window.tradewindow.FXPanels.TakeUPWindow;
import apps.window.tradewindow.FXPanels.TradeAttributesD;
import apps.window.tradewindow.FXPanels.outRight;
import apps.window.tradewindow.FXPanels.rollPanel;
import apps.window.tradewindow.panelWindow.FeesPanel;
import apps.window.tradewindow.panelWindow.SDIPanel;
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
import dsEventProcessor.TaskEventProcessor;
import dsServices.RemoteBOProcess;
import dsServices.RemoteProduct;
import dsServices.RemoteReferenceData;
import dsServices.RemoteTask;
import dsServices.RemoteTrade;
import dsServices.ServerConnectionUtil;
	
	
	//VS4E -- DO NOT REMOVE THIS LINE!
	public class FXTradePanel extends  TradePanel {
	
		
		  BasicData basicData = null;
		  int instrumentType = 0;
		  static String FXSWAP = "FXSWAP";
		  static String FXFORWARDOPTION = "FXFORWARDOPTION";
		  static String FXFORWARD = "FXFORWARD";
		  static String FXTAKEUP = "FXTAKEUP";
		  static String FX = "FX";
		 
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
				basicData = new BasicData();
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
			initJide();
			setBorder(new LineBorder(Color.black, 1, false));
			setLayout(new GroupLayout());
			add(getSwap(), new Constraints(new Leading(7, 12, 12), new Leading(193, 89, 12, 12)));
			add(getFwdOp(), new Constraints(new Leading(7, 512, 358, 358), new Leading(193, 89, 10, 10)));
			add(getFunctionality(),  new Constraints(new Leading(7, 843, 10, 10), new Leading(294, 10, 10)));
			add(getAttributes(),  new Constraints(new Leading(860, 329, 12, 12), new Leading(4, 536, 10, 10)));
			add(getOut(), new Constraints(new Leading(5, 838, 12, 12), new Leading(101, 87, 12, 12)));
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
			// TODO Auto-generated method stubthis.trade = trade;
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
				
				    		routingTrades  = remoteTrade.getSplitTrades(trade);
			      			functionality.setRoutingData(routingTrades);
				}
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
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
				// TODO Auto-generated catch block
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
				// TODO Auto-generated catch block
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			   
		}
		public void init() {
			de =ServerConnectionUtil.connect("localhost", 1099,commonUTIL.getServerIP());
		   	 try {
		   		remoteTrade = (RemoteTrade) de.getRMIService("Trade");
		   		boremote = (RemoteBOProcess) de.getRMIService("BOProcess");
		   		remoteTask = (RemoteTask) de.getRMIService("Task");
		   		remoteReference = (RemoteReferenceData) de.getRMIService("ReferenceData");
		   		remoteProduct = (RemoteProduct) de.getRMIService("Product");
		   		       product = (Product) remoteProduct.selectProductOnType(productType, productSubType);     
		   		    remoteBO = (RemoteBOProcess) de.getRMIService("BOProcess");
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
					basicData.counterPary.setEditable(false);
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
			        swap.setVisible(false);
			        basicData.jRadioButton0.setSelected(false);
			        basicData.jRadioButton1.setSelected(false);
			        basicData.jRadioButton2.setSelected(false);
			        basicData.jRadioButton5.setSelected(false);
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
			        // takeup jbutton
			        
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
							try {
								takeupW.jTextField0.setValue(fwdOp.primaryC.getDoubleValue());
								takeupW.jTextField2.setValue(fwdOp.quotingC.getDoubleValue());
								takeupW.jLabel10.setText(trade.getCurrency());
								takeupW.jLabel11.setText(trade.getCurrency());
								takeupW.jLabel5.setText(basicData.currencyPair.getText().substring(0, 3));
								takeupW.jLabel4.setText(basicData.currencyPair.getText().substring(0, 3));
							//	takeupW.jTextField4.setDateFormat(commonUTIL.getDateTimeFormat());
			                  //   takeupW.jTextField7.setDateFormat(commonUTIL.getDateTimeFormat());
			                     takeupW.jTextField5.setText(commonUTIL.getCurrentDateInString());
			                     takeupW.jTextField5.setEditable(false);
			                     takeupW.jTextField6.setDateFormat(commonUTIL.getDateTimeFormat());
			                     takeupW.jTextField4.setText(trade.getTradeDate());
			                     takeupW.jTextField7.setText(trade.getDelivertyDate());
			                   
			                     takeupW.jTextField1.setValue(0);
									takeupW.jTextField3.setValue(0);
			                     takeupW.model.setData((Vector) childTrades);
							} catch (ParseException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							//takeupW.setUser(user);
							
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
									// TODO Auto-generated catch block
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
									// TODO Auto-generated catch block
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
								// TODO Auto-generated catch block
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
										// TODO Auto-generated catch block
										e2.printStackTrace();
									}
									
									 try {
										 if(takeUPproduct == null)
										 takeUPproduct	 = (Product) remoteProduct.selectProductOnType(productType, FXTAKEUP);
										   
									DateU tradedate = DateU.valueOf(commonUTIL.stringToDate(trade.getTradeDate(),true));
									DateU tradeDeliverydate = DateU.valueOf(commonUTIL.stringToDate(trade.getDelivertyDate(),true));
									DateU takeUPTradeDate = DateU.valueOf(commonUTIL.stringToDate(takeupW.jTextField5.getText(), true));
									DateU takeUPSettleDate = DateU.valueOf(takeupW.jTextField6.getSelectedDate());
									if(!commonUTIL.between2dates(tradedate.getDate(), tradeDeliverydate.getDate(), takeUPTradeDate.getDate())) {
										commonUTIL.showAlertMessage("Take-up Trade date(s) has to be within the Start and End dates of the FX Time Option");
										return;
									}
									if(!commonUTIL.between2dates(tradedate.getDate(), tradeDeliverydate.getDate(), takeUPSettleDate.getDate())) {
										commonUTIL.showAlertMessage("Take-up Settle date(s) has to be within the Start and End dates of the FX Time Option");
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
										tradeTakeUp.setTradeDate(takeupW.jTextField5.getText());
										tradeTakeUp.setDelivertyDate(commonUTIL.getOnlyDateFromStringDate(takeupW.jTextField6.getSelectedDateAsText()));
										tradeTakeUp.setEffectiveDate(commonUTIL.getOnlyDateFromStringDate(takeupW.jTextField5.getText()));
										tradeTakeUp.setAttribute("Trade Date",tradeTakeUp.getTradeDate());
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
						               	 if(i > 0) 
						               		commonUTIL.showAlertMessage((statusT));
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
										// TODO Auto-generated catch block
										e1.printStackTrace();
									} catch (RemoteException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
									
									
									
								}
			        	 });
			       // }
			        	// functionality.j
			        	 
			        	 // B2B config button
			        	  functionality.jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
			        			@Override
								public void mouseClicked(MouseEvent e) {
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
											// TODO Auto-generated catch block
											e1.printStackTrace();
										} */
			        				
			        				
			        			}
			        	  });
			        // saveasnew or DEAL is new
			        functionality.jButton5.addMouseListener(new java.awt.event.MouseAdapter() {
	
						@Override
						public void mouseClicked(MouseEvent e) {
							if(trade != null) {
							String autoType = trade.getAutoType();
							if(!commonUTIL.isEmpty(autoType)) {
								if(autoType.equalsIgnoreCase("INTERNAL")) { // internal trade can't be save as new trade
									commonUTIL.showAlertMessage("Please click on New as this Trade is Internal Trade can't save as New");
									return;
								}
							}
							}
						    if(validdateALLFields("NEW")) {
						    	trade = new Trade();
						    	mirrorBook.setBookno(0);
						    	
						    	removeAttributeFromTrade(trade);
					            fillTrade(trade,"NEW");
					             
						    	int isHoliday = 0;
								try {
									
									isHoliday = remoteReference.checkHolidayOrWeekend((String)trade.getTradedesc().substring(0, 3), 
											trade.getDelivertyDate());
									
									if (isHoliday == 1) {
										
										commonUTIL.showAlertMessage("Near Date selected is a Holiday or a weekend. " +
												"Please select another date");
										out.outRightDate.setBackground(Color.red);
									}
									
									if (isHoliday != 1) {
										
										isHoliday = remoteReference.checkHolidayOrWeekend((String)trade.getTradedesc().substring(4, 7), 
												trade.getEffectiveDate());
										
										if (isHoliday == 1) {
											
											commonUTIL.showAlertMessage("Far Date selected is a Holiday or a weekend. " +
													"Please select another date");
											out.outRightDate.setBackground(Color.red);
										}
										
									} else if (isHoliday == -1) {
										commonUTIL.showAlertMessage("Server Problem");
										return;
									}
									
								} catch (RemoteException e2) {
									// TODO Auto-generated catch block
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
						               	
						               	trade.setAttributes(getAttributeValue());
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
						        		trade.setFees(feesPanel.getFeesDataV());
						        		Vector tradestatus = null;
						        		if(!commonUTIL.isEmpty(functionality.getRoutingData())  && functionality.getRoutingData().size() > 1) {
						        		   trade.clearAttributes();
						        		   Vector<Trade> rountingTrades = FXSplitUtil.getRountingData(trade,remoteReference);
											if(!commonUTIL.isEmpty(rountingTrades) && rountingTrades.size() >1 ) {
												try {
													rountingTrades =     FXSplitUtil.splitTrade(rountingTrades.get(1), rountingTrades.get(3), trade,functionality.jTextField2.getDoubleValue(), functionality.jTextField3.getDoubleValue());
													trade.setRountingTrades(rountingTrades);
								        		    tradestatus  = 	remoteTrade.saveBatchSplitTrades(trade.getRountingTrades(),trade,message);
												} catch (ParseException e1) {
													// TODO Auto-generated catch block
													e1.printStackTrace();
												}
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
			            			 // TODO Auto-generated catch block
			            			 e1.printStackTrace();
			            		 } 
					             } 
					             functionality.jButton5.setText("SAVEASNEW");
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
								functionality.jPanel6.setVisible(false);
								// functionality.jButton8.setEnabled(true);
								 functionality.jPanel2.setVisible(true);
								 try {
								//	 basicData.book.getName() 
									Vector vector = (Vector) remoteReference.getCurrencySplitConfig(Integer.parseInt(basicData.book.getName()), basicData.currencyPair.getText());
									 if(!commonUTIL.isEmpty(vector)) {
									  sconfig =  (CurrencySplitConfig)vector.elementAt(0);
									  functionality.jLabel2.addItem(sconfig.getFirstCurrencySplit());
									
									functionality.jLabel3.addItem(sconfig.getSecondCurrencySPlit());
									 } 
								} catch (RemoteException e1) {
									// TODO Auto-generated catch block
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
			        
			        functionality.jCheckBox0.addMouseListener(new java.awt.event.MouseAdapter() {
	
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
			        });
			        
			        functionality.jTableR2.addMouseListener(new java.awt.event.MouseAdapter() {
			        	@Override
						public void mouseClicked(MouseEvent e) {
			        		int rowselected = functionality.jTableR2.getSelectedRow();
			        		if(rowselected == -1)
			        			return;
			        		int tradeID = (Integer) functionality.jTableR2.getValueAt(rowselected,0);
			        		app.trade = null;
			        		Trade trade  = null;
							try {
								trade = remoteTrade.selectTrade(tradeID);
							} catch (RemoteException e1) {
								// TODO Auto-generated catch block
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
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (NumberFormatException e1) {
								// TODO Auto-generated catch block
								commonUTIL.showAlertMessage("Enter Number in Rate Field");
								return;
							}
							catch (CloneNotSupportedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}	catch (ParseException oe) {
									// TODO Auto-generated catch block
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
							
							trades  = FXSplitUtil.splitTrade(xccY1,xccY2,trade,frate,srate);
								} catch (ParseException e) {
									// TODO Auto-generated catch block
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
								// TODO Auto-generated catch block
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
							// TODO Auto-generated method stub
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
								// TODO Auto-generated catch block
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
								 
							} else {
								if(productSubType.equalsIgnoreCase("FXSWAP")) {
								basicData.buysell.setText("BUY/SELL");
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
							// TODO Auto-generated method stub
							out.jLabel2.setText(basicData.currencyPair.getText());
							
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
						if(!favEnableFlag) {
							int i = functionality.jTabbedPane1.getSelectedIndex();
							//functionality.jTabbedPane1.addt
							String name = functionality.jTabbedPane1.getTitleAt(i);
							
								__rows = getRows("CurrencyPair");
								basicData.currencyPair.setText(((JButton) __rows[0][0]).getName());
								__table = fillFavourites(__rows,basicData.currencyPair,null,out.jLabel2,false);
								functionality.jTabbedPane1.removeAll();
							functionality.jTabbedPane1.add("CurrencyPair",__table);
							__rows = getRows("Tenor");
							__table = fillFavourites(__rows,swap.swapDate,null,true);
							__table = fillFavourites(__rows,out.outRightDate,null,true);
							functionality.jTabbedPane1.add("Tenor",__table);
							__rows = getRows("CounterParty");
							
							__table = fillFavourites(__rows,basicData.counterPary,null,null,false,mirrorBook);
							functionality.jTabbedPane1.add("CounterParty",__table);
							__rows = getRows("Book");
							__table = fillFavourites(__rows,basicData.book,null,null,false);
							functionality.jTabbedPane1.add("Book",__table);
							__rows = getRows("Trader");
							__table = fillFavourites(__rows,basicData.jTextField7,null,null,false);
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
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						
						
							}catch (NumberFormatException e1) {
								// TODO Auto-generated catch block
								commonUTIL.showAlertMessage("Enter Number ");
							
							
								}
								
							
						}
						
			    
			    	
			    });
			        // internal functional. 
			        
			       
			        functionality.jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
			        	  
			        	@Override
						public void mouseClicked(MouseEvent e) {
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
									basicData.counterPary.setText(mirrorBook.getBook_name());
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
								fillTrade(trade,"NEW");
								
								int isHoliday = 0;
								try {
									
									isHoliday = remoteReference.checkHolidayOrWeekend((String)trade.getTradedesc().substring(0, 3), 
											trade.getDelivertyDate());
									
									if (isHoliday == 1) {
										
										commonUTIL.showAlertMessage("Near Date selected is a Holiday or a weekend. " +
												"Please select another date");
										out.outRightDate.setBackground(Color.red);
									}
									
									if (isHoliday != 1) {
										
										isHoliday = remoteReference.checkHolidayOrWeekend((String)trade.getTradedesc().substring(4, 7), 
												trade.getEffectiveDate());
										
										if (isHoliday == 1) {
											
											commonUTIL.showAlertMessage("Far Date selected is a Holiday or a weekend. " +
													"Please select another date");
											out.outRightDate.setBackground(Color.red);
										}
										
									} else if (isHoliday == -1) {
										commonUTIL.showAlertMessage("Server Problem");
										return;
									}
									
								} catch (RemoteException e2) {
									// TODO Auto-generated catch block
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
														rountingTrades =     FXSplitUtil.splitTrade(rountingTrades, functionality.jTextField2.getDoubleValue(),functionality.jTextField3.getDoubleValue(), trade,true);
													} catch (ParseException e1) {
														// TODO Auto-generated catch block
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
			       			// TODO Auto-generated catch block
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
							basicData.jRadioButton2.setSelected(false);
							basicData.jRadioButton1.setSelected(true);
							basicData.jRadioButton5.setSelected(false);
							basicData.jRadioButton0.setSelected(false);
							functionality.jButton2.setEnabled(true);
							functionality.jButton3.setEnabled(true);
							out.jCheckBox2.setEnabled(true);
							functionality.jButton2.setEnabled(true);
							fwdOp.setVisible(false);
							swap.setVisible(false);
							functionality.jButton0.setEnabled(false);
							productSubType = "FXFORWARD";
							//out.jCheckBox1.setSelected(false);
							if(basicData.buysell.getText().equalsIgnoreCase("BUY/SELL")) {
								basicData.buysell.setText("BUY");
							}
						if(basicData.buysell.getText().equalsIgnoreCase("SELL/BUY"))
							basicData.buysell.setText("SELL"); 
							
						} 
							
						
						
			    
			    	
			    });
			       // swap  radio button 
			        basicData.jRadioButton2.addMouseListener(new java.awt.event.MouseAdapter() {
	
						@Override
						public void mouseClicked(MouseEvent e) {
							if(!basicData.jRadioButton2.isEnabled())
								return ;
							basicData.jRadioButton2.setSelected(true);
							
							basicData.jRadioButton1.setSelected(false);
							out.jCheckBox2.setEnabled(true);
							functionality.jButton2.setEnabled(true);
							functionality.jButton3.setEnabled(true);
							basicData.jRadioButton5.setSelected(false);
							functionality.jButton0.setEnabled(false);
							basicData.jRadioButton0.setSelected(false);
							fwdOp.setVisible(false);
							productSubType = "FXSWAP";
							swap.setVisible(true);
							
							out.outRightDate.setSelectedDate(commonUTIL.addSubtractDate(commonUTIL.getCurrentDate(), 2));
									
							if(basicData.buysell.getText().equalsIgnoreCase("BUY")) {
								basicData.buysell.setText("SELL/BUY");
								
								double amt1 = Math.abs(new Double(out.jTextField1.getText()).doubleValue() );
								
								double amt2 = Math.abs(new Double(out.jTextField2.getText()).doubleValue()  * -1);
								swap.jTextField1.setText(out.jTextField1.getText());
								swap.jTextField2.setText(out.jTextField2.getText());
								out.jTextField1.setText(commonUTIL.getStringFromDoubleExp(amt1 * -1).toString());
								out.jTextField2.setText(commonUTIL.getStringFromDoubleExp(amt2 ).toString());
								
								
							}
						if(basicData.buysell.getText().equalsIgnoreCase("SELL")) {
							basicData.buysell.setText("BUY/SELL");
							
							
							
							
							double amt1 = Math.abs(new Double(out.jTextField1.getText()).doubleValue() * -1);
							//	out.jTextField1.setText(commonUTIL.getStringFromDoubleExp(amt1).toString());
								
								double amt2 = Math.abs(new Double(out.jTextField2.getText()).doubleValue());
							//	out.jTextField1.setText(commonUTIL.getStringFromDoubleExp(amt2).toString());
								swap.jTextField1.setText(out.jTextField1.getText());
								swap.jTextField2.setText(out.jTextField2.getText());
								out.jTextField1.setText(commonUTIL.getStringFromDoubleExp(amt1).toString());
								out.jTextField2.setText(commonUTIL.getStringFromDoubleExp(amt2 * -1).toString());
							
						}
							}
							
						
						
			    
			    	
			    });
			        out.jComboBox1.addItemListener(new ItemListener() {
	
						@Override
						public void itemStateChanged(ItemEvent arg0) {
							// TODO Auto-generated method stub
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
							functionality.jButton2.setEnabled(false);
							functionality.jButton3.setEnabled(false);
							basicData.jRadioButton2.setSelected(false);
							basicData.jRadioButton1.setSelected(false);
							basicData.jRadioButton5.setSelected(true);
							
							String currP = basicData.currencyPair.getText();
							if(currP.isEmpty()) {
								basicData.jRadioButton1.setSelected(true);
								basicData.jRadioButton2.setSelected(false);
								swap.setVisible(false);
								basicData.jRadioButton5.setSelected(false);
								
						    	basicData.jRadioButton0.setSelected(false);
								//commonUTIL.showAlertMessage("Select Currency Pair");
								return;
							}
							out.jCheckBox2.setEnabled(false);
							functionality.jButton2.setEnabled(false);
							functionality.jButton3.setEnabled(false);
							//functionality.jButton4.setEnabled(false);
							productSubType = FXFORWARDOPTION;
							 functionality.jButton0.setEnabled(true);
							swap.setVisible(false);
							fwdOp.setVisible(true);
							fwdOp.primaryC.setValue(0);
							fwdOp.quotingC.setValue(0);
							
							
								
							if(!currP.isEmpty())
							     fwdOp.jLabel2.setText(currP.substring(0, 3));
							 fwdOp.jLabel3.setText(currP.substring(4, 7));
							 fwdOp.startDate.setText(commonUTIL.getCurrentDateTime());
							
						} 
							
						
						
			    
			    	
			    });
			        basicData.jRadioButton6.addMouseListener(new java.awt.event.MouseAdapter() {
	
						@Override
						public void mouseClicked(MouseEvent e) {
							if(!basicData.jRadioButton6.isEnabled())
								return ;
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
					// TODO Auto-generated catch block
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
						// TODO Auto-generated catch block
				commonUTIL.displayError("FXTradePanel","getBookDataCombo1", e);
					}
			
			
		}
	
		@Override
		public void setTaskPanel(BackOfficePanel panel) {
			// TODO Auto-generated method stub
			taskPanel = (TaskPanel) panel;
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
	
		@Override
		public void saveASNew(Trade trade) {
			// TODO Auto-generated method stub
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
			basicData.jRadioButton6.setEnabled(true);
	        basicData.jRadioButton7.setEnabled(true);
		}
			
		}
		
		
		
	 
		@Override
		public void setUser(Users user1) {
			// TODO Auto-generated method stub
			this.user = user1;
			
		}
	    public Users getUser() {
	    	return user;
	    }
		@Override
		public String getAction() {
			// TODO Auto-generated method stub
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
			// TODO Auto-generated method stub
			if(actionType.equalsIgnoreCase("NEW")) {
				newTradeView();
			} else {
				if(validdateALLFields("NEW")) {
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
			flag = true;
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
		
		
		
		
		private void processTableData(Hashtable<String,String> attributes,DefaultTableModel model) {
			// TODO Auto-generated method stub
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
		    		if(tradeAttributes.getName().equalsIgnoreCase("Trade Date") || tradeAttributes.getName().equalsIgnoreCase("TradeModifiedDateTime")) {
		    	    	
		    			attru.setValue(commonUTIL.getCurrentDateTime());
		    	    }
		    		this.attributes.addNewRow(attru);
		    		Vector attributeValues = (Vector) remoteReference.getStartUPData(attru.getName());
		    		 if(!commonUTIL.isEmpty(attributeValues)) {
		    		 String values [] = this.attributes.convertVectortoSringArray(attributeValues,tradeAttributes.getName().toString());
		    		 this.attributes.addRowEditor(rowCount, 1, this.attributes.getJComboxBox(values),"Values");
		    		 		     values = null;
		    	} else {
		    		
		    		this.attributes.addRowEditor(rowCount, 1,this.attributes.getJTextFieldBox(), "Values");
		    	}
		    			
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	
		@Override
		public void setSDIPanel(BackOfficePanel panel) {
	sdiPanel = (SDIPanel) panel;
			
		}
		 public void getTradeSDI(BackOfficePanel panel) {
				try {
					//System.out.println(panel);
					sdiPanel.setTrade(trade);
					panel.fillJTabel((Vector) remoteTrade.getSDisOnTrade(trade));
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	
		@Override
		public void setFEESPanel(BackOfficePanel panel) {
			// TODO Auto-generated method stub
			feesPanel = (FeesPanel) panel;
			
		}
	
		
	
		 public void getTradeTask(BackOfficePanel panel) {
				try {
					//sdiPanel.setTrade(trade);
				
					panel.fillJTabel((Vector)remoteTask.selectTaskWhere("tradeId = " + trade.getId()));
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		 
	
		@Override
		public void setTradePostings(BackOfficePanel panel) {
			// TODO Auto-generated method stub
			
		}
	
		@Override
		public void processTask(TaskEventProcessor taskEvent, Users WindowUser) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void setTradeTransfers(BackOfficePanel panel) {
			// TODO Auto-generated method stub
			transferPanel = (TransferPanel) panel;
		} 
		public void getTradeTransfers(BackOfficePanel panel) {
			try {
				transferPanel.setTrade(trade);
				panel.fillJTabel((Vector)remoteBO.queryWhere("Transfer", "tradeId = " + trade.getId()));
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public void newTradeView() {
			app.trade = null;
			out.jCheckBox2.setEnabled(true);
			functionality.jButton2.setEnabled(true);
			basicData.currencyPair.setText("");
			basicData.book.setText("");
			basicData.book.setName("0");
			basicData.counterPary.setText("");
			basicData.counterPary.setName("0");
			basicData.buysell.setText("BUY");
			basicData.jRadioButton1.setSelected(true);
			basicData.jRadioButton2.setSelected(false);
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
			swap.jTextField1.setText("0");
			swap.jTextField2.setText("0");
			swap.swapDate.setText("0");
			swap.jTextField4.setText("0");
			String attributeColumnName [] =    {"Attribute Name ", "Attribute  Value "};
	        
	      //  attributeModel = new DefaultTableModel(attributeColumnName,0);
	        processTableData(attributeDataValue,attributeModel);
	        attributes.jTable1.removeAll();
	     //   attributes.jTable1.setModel(attributeModel);
	        basicData.jRadioButton6.setEnabled(false);
	        basicData.jRadioButton7.setEnabled(false);
	        basicData.jRadioButton6.setSelected(false);
	        basicData.jRadioButton7.setSelected(false);
	        rollpanel.getJPanel0().setVisible(false);
	        functionality.jButton7.setEnabled(false);
	        trade = null;
	        
	     //   productSubType = "";
	        basicData.jRadioButton5.setEnabled(true);
	        basicData.jRadioButton1.setEnabled(true);
	        basicData.jRadioButton2.setEnabled(true);
	        basicData.jRadioButton0.setSelected(false);
	        basicData.jRadioButton0.setEnabled(false);
	        basicData.jRadioButton1.setSelected(true);
	        if(productSubType.equalsIgnoreCase(FXFORWARD)) {
				basicData.jRadioButton1.setSelected(true);
				basicData.jRadioButton2.setSelected(false);
				basicData.jRadioButton5.setSelected(false);
				basicData.jRadioButton0.setSelected(false);
				functionality.jButton2.setEnabled(true);
				functionality.jButton3.setEnabled(true);
				basicData.buysell.setText("BUY");
			}
			if(productSubType.equalsIgnoreCase(FXSWAP)) {
				basicData.jRadioButton2.setSelected(true);
				basicData.jRadioButton5.setSelected(false);
				basicData.jRadioButton1.setSelected(false);
				basicData.jRadioButton0.setSelected(false);
				functionality.jButton2.setEnabled(true);
				functionality.jButton3.setEnabled(true);
				basicData.buysell.setText("BUY/SELL");
				
			}
			if(productSubType.equalsIgnoreCase(FXFORWARDOPTION)) {
				basicData.jRadioButton5.setSelected(true);
				basicData.jRadioButton2.setSelected(false);
				basicData.jRadioButton1.setSelected(false);
				basicData.jRadioButton0.setSelected(false);
				functionality.jButton2.setEnabled(false);
				functionality.jButton3.setEnabled(false);
				basicData.buysell.setText("BUY");
				fwdOp.primaryC.setText("0");
				fwdOp.quotingC.setText("0");
			}
			if(productSubType.equalsIgnoreCase(FXTAKEUP)) {
				basicData.jRadioButton0.setSelected(true);
				basicData.jRadioButton5.setSelected(false);
				basicData.jRadioButton2.setSelected(false);
				basicData.jRadioButton1.setSelected(false);
				functionality.jButton2.setEnabled(false);
				functionality.jButton3.setEnabled(false);
				//basicData.buysell.setText("BUY");
			}
	        functionality.setRoutingData(null);
	        b2bconfig = null;
	     //   functionality.jPanel2.setVisible(true);
			functionality.jPanel6.setVisible(true);
		   functionality.b2bCurrencyPair.setText("");
		  //  functionality.b2bBook.setText("");
		    functionality.b2bTransferTo.setText("");
		    functionality.clearRounting();
		    functionality.jLabel2.setSelectedIndex(-1);
		    functionality.jLabel3.setSelectedIndex(-1);
		    functionality.jTextField2.setText("0");
		    functionality.jTextField3.setText("0");
		  
		    functionality.jPanel6.setVisible(false);
	        
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
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}         // used as fwd amt for FXSWAP in FX
				    newrolltrade.setEffectiveDate(rollpanel.jTextField0.getSelectedDateAsText());
				    try {
				    	newrolltrade.setSecondPrice(rollpanel.jTextField1.getDoubleValue());  // used as forward rate 
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		    } 
			  try {
				  rollProduct = (Product) remoteProduct.selectProductOnType(productType, rollProductSubType);
				  newrolltrade.setProductId(rollProduct.getId());
				  
				  try {
					rollparitialtrade = (Trade) trade.clone();
				} catch (CloneNotSupportedException e) {
					// TODO Auto-generated catch block
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
					// TODO Auto-generated catch block
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
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}         // used as fwd amt for FXSWAP in FX
					    newrolltrade.setEffectiveDate(rollpanel.jTextField0.getSelectedDateAsText());
					    try {
					    	newrolltrade.setSecondPrice(rollpanel.jTextField1.getDoubleValue());  // used as forward rate 
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			    } 
				  try {
					  rollProduct = (Product) remoteProduct.selectProductOnType(productType, rollProductSubType);
					  newrolltrade.setProductId(rollProduct.getId());
					 
					  rollID=  remoteTrade.saveTrade(newrolltrade);
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
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
						// TODO Auto-generated catch block
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}    
			trade.setBookId(new Integer(basicData.book.getName()).intValue());
			if(mirrorBook.getBookno()> 0) {
			//	trade.setCpID(counterPartyID);
				trade.setMirrorBookid(mirrorBook.getBookno());
				
			} else {
				trade.setMirrorID(0);
				if(commonUTIL.isEmpty(basicData.counterPary.getName().trim())) {
					commonUTIL.showAlertMessage("Select CounterParty");
					return;
				}
			trade.setCpID(new Integer(basicData.counterPary.getName()).intValue());
			}
			trade.setTraderID(new Integer(basicData.jTextField7.getName()).intValue());
			trade.setTradeDate(commonUTIL.getCurrentDateTime());
		    
			trade.setDelivertyDate(out.outRightDate.getSelectedDateAsText());
		    trade.setStatus(out.jTextField6.getText());
		    trade.setProductType(productType);
		   
		    if(!(out.jComboBox1.getSelectedIndex() == -1))
		        trade.setAction(out.jComboBox1.getSelectedItem().toString());
		    trade.setCurrency((String)basicData.currencyPair.getText().substring(4, 7));  // negotiable curr ie. quote currency ie. settlementCurrency
		    trade.setType(basicData.buysell.getText());
		    trade.setTradedesc(basicData.currencyPair.getText()); 
		    trade.setUserID(user.getId());
		    trade.setAttributes(getAttributeValue());
		    trade.setPrice(new Double(out.jTextField4.getText()).doubleValue());
		    trade.setEffectiveDate(trade.getTradeDate());  // use as FORWARD DATE for FXSWAP in FX. 
		   trade.setQuantity(new Double(out.jTextField1.getText()).doubleValue());  // amt1 (negotiated amt for negotiable curr) 
		  	trade.setNominal(new Double(out.jTextField2.getText()).doubleValue());  // amt2  (negotiated amt for non-negotiable curr) nominal =  quantity * price
		    
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
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				           // used as fwd amt for FXSWAP in FX
				    trade.setEffectiveDate(swap.swapDate.getSelectedDateAsText());
				    try {
						trade.setSecondPrice(swap.jTextField4.getDoubleValue());  // used as forward rate 
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		    } else {
		    	trade.setTradeAmount(0);   // used as quantity 0 if not fxswap
			    trade.setYield(0);         // used as fwd amt 0 if not fxswap
			    trade.setEffectiveDate(trade.getDelivertyDate());
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
		   // trade.setVersionID(0);
		    
		    
		}
		
		// this method is used to populate currencysplit data
		public void populateRountingData() {
			
			//FilterValues.isavaliableForSplit(trade.getTradedesc(),trade.getBookId(),)
			Trade  originaltrade  = null;
			if(trade == null) {
				originaltrade = new Trade();
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
			double rate1 = 0;
			double rate2 = 0;
			
			
				try {
					if(!commonUTIL.isEmpty(functionality.jTextField2.getText()) && commonUTIL.isNumeric(functionality.jTextField2.getText()))
					rate1 = functionality.jTextField2.getDoubleValue();
					if(!commonUTIL.isEmpty(functionality.jTextField3.getText()) && commonUTIL.isNumeric(functionality.jTextField3.getText()))
						rate2 = functionality.jTextField3.getDoubleValue();
				
					
					
				 // Vector<Trade> rountingTrades =	FXSplitUtil.getRountingData(trade,remoteReference);
					Vector<Trade> rountingTrades  =             functionality.getRoutingData();
					if(commonUTIL.isEmpty(rountingTrades)) {
						rountingTrades = FXSplitUtil.getRountingData(originaltrade,remoteReference);
						if(!commonUTIL.isEmpty(rountingTrades) && rountingTrades.size() >0 ) {
							if(trade == null || trade.getId() == 0) {
								         if(rountingTrades.size() == 1) {
								        	 FXSplitUtil.splitTrade(null, null, originaltrade, 0, 0);
								         } else {
						                    rountingTrades =     FXSplitUtil.splitTrade(rountingTrades.get(1), rountingTrades.get(3), originaltrade, rate1, rate2);
								         }
							} else  {
								rountingTrades = FXSplitUtil.splitTrade(rountingTrades, rate1, rate2);
							}
						}
					} else {
						if(!commonUTIL.isEmpty(rountingTrades) && rountingTrades.size() >0 )
						//rountingTrades =     FXSplitUtil.splitTrade(rountingTrades.get(1), rountingTrades.get(3), originaltrade, rate1, rate2);
							if(trade == null || trade.getId() == 0) {
								if(rountingTrades.size() == 1) {
						        	 FXSplitUtil.splitTrade(null, null, originaltrade, 0, 0);
						         } else {
				                    rountingTrades =     FXSplitUtil.splitTrade(rountingTrades.get(1), rountingTrades.get(3), originaltrade, rate1, rate2);
						         }
						}	else  {
							if(!basicData.buysell.getText().equalsIgnoreCase(trade.getType())) {
								originaltrade.setType(basicData.buysell.getText());
								rountingTrades = FXSplitUtil.splitTrade(rountingTrades, rate1, rate2,originaltrade,false); // add change done on the screen.
							} else {
								rountingTrades = FXSplitUtil.splitTrade(rountingTrades, rate1, rate2,originaltrade,false);
							}
						}
					}
               //  functionality.setRoutingData(rounting);
				//  fillTrade(trade,"NEW");
				
				 
				  if(!commonUTIL.isEmpty(rountingTrades)) {
						functionality.setRoutingData(rountingTrades);
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
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
				   basicData.counterPary.setText(le.getName());
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
			getDataFromTrade(trade.getBookId(),"Book");
			
			//always first setAttribute because in this method a hashtable is set with all attributes and ite values. This hastable is used to get 
			//values ahead.
			setAttribute(trade.getAttributes());
			  
			if(trade.isMirrorTrade()) {
				mirrorBook = getBook(trade.getMirrorBookid());
				Book book = getBook(trade.getCpID());
				 basicData.counterPary.setText(book.getBook_name());
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
		   
		  
		   
		    out.jTextField4.setText(commonUTIL.getStringFromDoubleExp(trade.getPrice()).toString());
		    if(trade.getType().equalsIgnoreCase("BUY")) {
		       out.jTextField1.setText(commonUTIL.getStringFromDoubleExp(trade.getQuantity()).toString());
		       out.jTextField2.setText(commonUTIL.getStringFromDoubleExp(trade.getNominal()).toString());
		    } else  {
		    	out.jTextField1.setText(commonUTIL.getStringFromDoubleExp(trade.getQuantity()).toString());
		    	 out.jTextField2.setText(commonUTIL.getStringFromDoubleExp(trade.getNominal()).toString());
		    }
		    app.openTrade(trade,false);
		   
		    out.jTextField5.setText(new Integer(trade.getId()).toString());
		    
		//    processActionData(actionstatus, productSubType);
		    actionController = true;
		    processActionData(actionstatus,productType,trade.getTradedesc1(),out.jTextField6.getText(),remoteTrade);
		    actionController = false;
		    if(trade.getTradedesc1().equalsIgnoreCase(FXSWAP)) {
		    	swap.jTextField1.setText(commonUTIL.getStringFromDoubleExp(trade.getTradeAmount()).toString());
		    	swap.jTextField2.setText(commonUTIL.getStringFromDoubleExp(trade.getYield()).toString());
		    	swap.swapDate.setSelectedDate(commonUTIL.stringToDate(trade.getEffectiveDate().toString(),true));
		    	swap.jTextField4.setValue(trade.getSecondPrice());
		    	try {
					System.out.println(swap.jTextField4.getDoubleValue());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
		    }if(trade.getTradedesc1().equalsIgnoreCase(FXFORWARDOPTION)) {
		    	swap.setVisible(false);
		    	rollpanel.setVisible(false);
		    	fwdOp.setVisible(true);
		    	basicData.jRadioButton2.setEnabled(false);
		    	basicData.jRadioButton5.setSelected(true);
		    	basicData.jRadioButton1.setEnabled(false);
		    	basicData.jRadioButton0.setEnabled(false);
		    	basicData.jRadioButton0.setSelected(false);
		    	basicData.jRadioButton6.setSelected(false);
		    	basicData.jRadioButton7.setSelected(false);
		    	functionality.jButton2.setSelected(false);
		    	functionality.jButton3.setSelected(false);
		    	String currP = trade.getTradedesc();
		    	 fwdOp.jLabel2.setText(currP.substring(0, 3));
				 fwdOp.jLabel3.setText(currP.substring(4, 7));
				 
				 // attributeDataValue is set in setAttributes method
				 fwdOp.startDate.setText(
						commonUTIL.separteDateTime(attributeDataValue.get("Trade Date")));
				 
				 functionality.jButton0.setEnabled(true);
				 fwdOp.primaryC.setValue(trade.getQuantity());
				 fwdOp.quotingC.setValue(trade.getNominal());
				 rollpanel.setVisible(false);
				 productSubType = FXFORWARDOPTION;
		    	 try {
		    		
		    		childTrades 	= remoteTrade.getChildTrades(trade.getId());
		    		Vector childes = (Vector) childTrades;
		    		if(!childes.isEmpty())  {
		    			 
		    		setOutStandingBalONFwdOption(childes,trade);
		    		
		    		}
		    		
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    } if(trade.getTradedesc1().equalsIgnoreCase(FXFORWARD))  {
		    	
		    	basicData.jRadioButton1.setSelected(true);
		    	basicData.jRadioButton1.setEnabled(true);
		    	swap.setVisible(false);
		    	basicData.jRadioButton5.setEnabled(false);
		    	basicData.jRadioButton2.setEnabled(false);
		    	basicData.jRadioButton2.setSelected(false);
		    	basicData.jRadioButton5.setEnabled(false);
		    	basicData.jRadioButton0.setEnabled(false);
		    	basicData.jRadioButton0.setSelected(false);
		    	basicData.jRadioButton6.setSelected(false);
		    	basicData.jRadioButton7.setSelected(false);
		    	fwdOp.setVisible(false);
		    	  rollpanel.setVisible(false);
		    	//productSubType = "FXSWAP";
		    }if(trade.getTradedesc1().equalsIgnoreCase(FXTAKEUP))  {
		    	productSubType = FXTAKEUP;
		    	basicData.jRadioButton1.setSelected(false);
		    	basicData.jRadioButton1.setEnabled(false);
		    	swap.setVisible(false);
		    	basicData.jRadioButton5.setEnabled(false);
		    	basicData.jRadioButton2.setEnabled(false);
		    	basicData.jRadioButton2.setSelected(false);
		    	basicData.jRadioButton5.setSelected(false);
		    	basicData.jRadioButton5.setEnabled(false);
		    	basicData.jRadioButton0.setEnabled(true);
		    	basicData.jRadioButton0.setSelected(true);
		    	functionality.jButton2.setSelected(false);
		    	functionality.jButton3.setSelected(false);
		    	fwdOp.setVisible(false);
		    	  rollpanel.setVisible(false);
		    }
		    basicData.jRadioButton6.setEnabled(true);
		    basicData.jRadioButton7.setEnabled(true);
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
		private JTable fillFavourites(Object __rows12 [][],JTextField textField,JTextField textField2,JLabel label,boolean dateField) {
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
			    __table.addMouseListener(new JTableButtonMouseListener(__table,textField,textField2,label,dateField));
			    if(label  != null) {
			    //	currencyPair = textField.getText();
			    	//functionality.refreshPositionTable(currencyPair,new Integer(basicData.book.getName()).intValue());
			    }
			    
			    return __table;
			
		}
		private JTable fillFavourites(Object __rows12 [][],JTextField textField,JTextField textField2,JLabel label,boolean dateField,Book mirrorBook) {
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
			    __table.addMouseListener(new JTableButtonMouseListener(__table,textField,textField2,label,dateField,mirrorBook));
			    if(label  != null) {
			    //	currencyPair = textField.getText();
			    	//functionality.refreshPositionTable(currencyPair,new Integer(basicData.book.getName()).intValue());
			    }
			    
			    return __table;
			
		}
		private JTable fillFavourites(Object __rows12 [][],JDatePicker textField2,JLabel label,boolean dateField) {
			
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
			    __table.addMouseListener(new JTableButtonMouseListener(__table,textField2,label,dateField));
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
				// TODO Auto-generated catch block
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
			for(int row =0 ; row < buttons.length; ++row) {
				 for(int column =0; column<buttons[row].length;++column) {
					 if(i == rows.size())
						 break;
					 Favorities fav = (Favorities) rows.elementAt(i);
					 cell = new JButton();
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
				// TODO Auto-generated method stub
				String attributesV  = "";
				
					/*Enumeration<String> keys =  attributeDataValue.keys();
					while(keys.hasMoreElements()) {
						String key = (String) keys.nextElement();
						String value = attributeDataValue.get(key);
						if(value != null && value.length() > 0) 
							attributesV = attributesV + key+ "=" + value + ";";
								
					} */
						int tradeDateRowno = -1;
						String strTradeDate = "TradeModifiedDateTime";
						String tradeDateVal="";
						boolean tradeDateFilled = false;
						Vector<Attribute> attributesData = attributes.getData();
						for(int i=0;i<attributesData.size();i++) {
							Attribute att = attributesData.get(i);
							String value = att.getValue();
							
							if (att.equals(strTradeDate)) {
								
								tradeDateRowno = i;
							}
							if(value != null ) {
								
								if (tradeDateRowno >= 0) {
									
									tradeDateFilled = true;
									tradeDateVal = value;
								}
								
								if(value.trim().length() > 0)
							    attributesV = attributesV + att.getName()+ "=" + att.getValue() + ";";
							}
							
						}
						
						if (!tradeDateFilled) {
							
							tradeDateVal = commonUTIL.getCurrentDateTime();
							attributesV = attributesV + strTradeDate+ "=" + tradeDateVal + ";";
							attributes.jTable1.setValueAt(tradeDateVal, tradeDateRowno, 1);
							
						}
					
				
				if(attributesV.trim().length() > 0)
				return attributesV.substring(0, attributesV.length()-1);
				return attributesV;
			}
			
	
	class JTableButtonMouseListener implements MouseListener {
		
		
		  private JTable __table;
		  JTextField comp = null;
		  JDatePicker swapField = null;
		  JLabel label = null;
		  JDatePicker datefield = null;
		  boolean dateF = false;
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
			   generateDeliveryDate(swapField,swapTenor);
			   
			   
		   } else {
		  comp.setText(button.getText());
		  comp.setName(button.getName());
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
	
		  public JTableButtonMouseListener(JTable table,JTextField textField,JTextField textField2,JLabel labeln,boolean dateField) {
		    __table = table;
		    comp = textField;
		    label = labeln;
		    dateF = dateField;
		   // swapField = textField2;
		  }
		  public JTableButtonMouseListener(JTable table,JTextField textField,JTextField textField2,JLabel labeln,boolean dateField,Book mirrorBook) {
			    __table = table;
			    comp = textField;
			    label = labeln;
			    dateF = dateField;
			   // swapField = textField2;
			   // mirrorBook = mirrorBook;
			  }
		  public JTableButtonMouseListener(JTable table,JDatePicker  textField2,JLabel labeln,boolean dateField) {
			    __table = table;
			    //datefield = textField;
			    label = labeln;
			    dateF = dateField;
			    swapField = textField2;
			  }
		  public JTableButtonMouseListener(JTable table, JDatePicker textField,JDatePicker  textField2,JLabel labeln,boolean dateField) {
			    __table = table;
			    datefield = textField;
			    label = labeln;
			    dateF = dateField;
			    swapField = textField2;
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
		  public void generateDeliveryDate(JDatePicker dateField,String tenor) {
			//  System.out.println(commonUTIL.getCurrentDate());
				DateU dateissueDate = DateU.valueOf(commonUTIL.getCurrentDate());
				dateissueDate.convertToCode(tenor);
				
				dateField.setSelectedDate(dateissueDate.getDate());
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
		// TODO Auto-generated method stub
		this.app = app;
	}
		TradeApplication app = null;
		@Override
		public void setLimitPanel(BackOfficePanel panel) {
			// TODO Auto-generated method stub
			
		}
	
		@Override
		public void setLimitBreachMarkOnAction(int i) {
			// TODO Auto-generated method stub
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
	}

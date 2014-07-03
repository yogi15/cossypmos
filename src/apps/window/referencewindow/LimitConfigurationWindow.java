package apps.window.referencewindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

import org.dyno.visual.swing.layouts.Bilateral;
import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;
import org.dyno.visual.swing.layouts.Trailing;

import util.commonUTIL;
import util.common.DateU;
import apps.window.utilwindow.JDialogTable;
import beans.Book;
import beans.Folder;
import beans.LegalEntity;
import beans.Limit;
import beans.LimitConfig;
import beans.StartUPData;
import dsServices.RemoteLimit;
import dsServices.RemoteReferenceData;
import dsServices.ServerConnectionUtil;


//VS4E -- DO NOT REMOVE THIS LINE!
public class LimitConfigurationWindow extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField jTextField0;
	private JLabel jLabel0;
	private JComboBox jComboBox0;
	private JComboBox jComboBox1;
	private JRadioButton jRadioButton0;
	private JPanel jPanel0;
	private JRadioButton jRadioButton1;
	private JRadioButton jRadioButton2;
	private JRadioButton jRadioButton3;
	private JPanel jPanel1;
	private JLabel jLabel1;
	private JLabel jLabel3;
	private JComboBox jComboBox2;
	private JComboBox jComboBox3;
	private JComboBox jComboBox4;
	private JLabel jLabel2;
	private JPanel jPanel2;
	private JButton jButton0;
	private JButton jButton1;
	private JButton jButton2;
	private JButton jButton3;
	private JTable jTable0;
	private JButton jButton4;
	private JButton jButton5;
	private JTable jTable1;
	private JComboBox jComboBox6;
	private JScrollPane jScrollPane1;
	RemoteReferenceData referenceData;
	RemoteLimit remoteLimit;
	String limitType = "CounterPartyLimit";
	String currencyFilterType = "Currency";
	String currencyFilterValue = "";
	String productFilterType = "ProductType";
	String productFilterValue = "";
	String bookFilterType = "Book";
	String bookFilterValue = "";
	String FilterType = "CounterParty";
	String FilterValue = "";
	
	
	 public static  ServerConnectionUtil de = null;
	 javax.swing.DefaultComboBoxModel bookModel = new javax.swing.DefaultComboBoxModel();
	 Hashtable<Integer,Book> books = new Hashtable<Integer,Book>();
	 javax.swing.DefaultComboBoxModel productModel = new javax.swing.DefaultComboBoxModel();
	 javax.swing.DefaultComboBoxModel currencytModel = new javax.swing.DefaultComboBoxModel();
	 javax.swing.DefaultComboBoxModel cpCombo = new javax.swing.DefaultComboBoxModel();
 	 Hashtable cpData = new Hashtable();
 	 javax.swing.DefaultComboBoxModel issuerCombo = new javax.swing.DefaultComboBoxModel();
 	 Hashtable issuerData = new Hashtable();
 	 javax.swing.DefaultComboBoxModel traderCombo = new javax.swing.DefaultComboBoxModel();
 	 Hashtable trader = new Hashtable();
 	 Hashtable<String,LimitConfig> limitConfigs = new  Hashtable<String,LimitConfig>();
	private JScrollPane jScrollPane0;
	DefaultCellEditor tenorObject = null;
	DefaultCellEditor limitWarningObject = null;
	DefaultCellEditor limitThreObject = null;
	 String limitArrange []= {"Min Amount","Max Amount","Warning %","Threshold %","Start Date","Tenor","Expiry Date"};
	  Object[][] data1 = null;
	  LimitConfig limitConfigration = null;
	  TableModelUtil limitModel = null;
	  Vector<Limit> limitdata = new Vector<Limit>();
	  DefaultTableModel model = new DefaultTableModel(data1, limitArrange);
	  String limits []= {"Limit ID","Limit Type","Limit Name","Threshold","Max Amount","Min Amount","Start Date","Expiry Date"};
	  private JButton jButton6;
	private JButton jButton7;
	private JButton jButton8;
	Vector uniqueLimitConfig = null;
	private static final String PREFERRED_LOOK_AND_FEEL = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
	public LimitConfigurationWindow() {
		init();
		initComponents();
	}

	private void init() {
		// TODO Auto-generated method stub
		de =ServerConnectionUtil.connect("localhost", 1099,commonUTIL.getServerIP());
	   	 try {
	   	       referenceData = (RemoteReferenceData) de.getRMIService("ReferenceData");
	   		remoteLimit = (RemoteLimit) de.getRMIService("Limit");
	   		Vector books = (Vector) referenceData.selectALLBooks();
	   		Vector prdoucts = (Vector) referenceData.getStartUPData("ProductType");
	   		Vector currency = (Vector) referenceData.getStartUPData("Currency");
	   		 uniqueLimitConfig = (Vector) remoteLimit.getAllUnquineLimitConfig();
	   		Vector tenor=(Vector)	  referenceData.getStartUPData("Tenor");
	   		Vector limitWarning=(Vector)	  referenceData.getStartUPData("LimitWarning");
	   		Vector limitThershold=(Vector)	  referenceData.getStartUPData("LimitThershold");
	   		processBookData(books,bookModel,true);
	   		processBookData(prdoucts,productModel,false);
	   		processBookData(currency,currencytModel,false);
	   		processDataCombo1(cpCombo,cpData,"cp","CounterParty"); 
	   		processDataCombo1(issuerCombo,issuerData,"cp","Issuer"); 
			processDataCombo1(traderCombo, trader, "cp", "Trader");
			processDataLimitConfig(uniqueLimitConfig,limitConfigs);
			
			final JComboBox tenorCombox = new JComboBox( convertVectortoSringArray(tenor) );
			final JComboBox limitWarningCombox = new JComboBox( convertVectortoSringArray(limitWarning) );
			final JComboBox limitThersholdCombox = new JComboBox( convertVectortoSringArray(limitThershold) );
			tenorObject = new DefaultCellEditor( tenorCombox );
			limitThreObject =  new DefaultCellEditor( limitThersholdCombox );
			limitWarningObject  =  new DefaultCellEditor( limitWarningCombox );
			tenorCombox.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent e) {
					String date = "";
					String tenor = (String) tenorCombox.getSelectedItem();
					if(commonUTIL.isEmpty(tenor))
						return;
					int row = jTable0.getSelectedRow();
					date = (String) jTable0.getValueAt(row, 4);
					if (date == null || (date.trim().isEmpty())
							|| date.length() <= 0) {
						commonUTIL.showAlertMessage("Enter Start Date ");
						return;
					}
					DateU dateissueDate = DateU.valueOf(commonUTIL.stringToDate(
							date, false));
					dateissueDate.convertToCode(tenor);
					jTable0.setValueAt(
							commonUTIL.getDateFormat(dateissueDate.getDate()), row, 6);
				}
			});
			showAllLimitConfig.jTable1
			.addMouseListener(new java.awt.event.MouseAdapter() {

				
				public void mouseClicked(MouseEvent e) {
					String configName = showAllLimitConfig.jTable1.getValueAt(
							showAllLimitConfig.jTable1.getSelectedRow(), 0)
							.toString();
					jTextField0.setText(configName);
					jComboBox2.setSelectedIndex(0);
					jComboBox3.setSelectedIndex(0);
					jComboBox4.setSelectedIndex(0);
					LoadLimitConfig(configName);
					showAllLimitConfig.dispose();
					
				}
			});
			
	   	} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   		
		
	}

	protected void LoadLimitConfig(String configName) {
		// TODO Auto-generated method stub
		try {
		Vector v1 = (Vector)	remoteLimit.getLimitConfig(configName);
		if(commonUTIL.isEmpty(v1))
			return;
		for(int i=0;i<v1.size();i++) {
			LimitConfig limitConfig = (LimitConfig) v1.elementAt(i);
			limitConfigration = limitConfig; 
			if(limitConfig.getLimitType().equalsIgnoreCase("CounterPartyLimit")) {
				if(limitConfig.getParentID() != 0) {
					jRadioButton0.setSelected(true);
					int id = getHashTableData(Integer.parseInt(limitConfig.getFilterValue()),cpData,"CounterParty");
					jComboBox0.setSelectedIndex(id);
				}
			}
			if(limitConfig.getLimitType().equalsIgnoreCase("TraderLimit")) {
				if(limitConfig.getParentID() != 0) {
					jRadioButton1.setSelected(true);
					int id = getHashTableData(Integer.parseInt(limitConfig.getFilterValue()),cpData,"Trader");
					jComboBox1.setSelectedIndex(id);
				}
			}
			if(limitConfig.getFilterType().equalsIgnoreCase("Book")) {
			//	jRadioButton1.setSelected(true);
				int id = getHashTableData(Integer.parseInt(limitConfig.getFilterValue()),books,"Book");
				jComboBox4.setSelectedIndex(id);
			}
			if(limitConfig.getFilterType().equalsIgnoreCase("ProductType")) {
				//	jRadioButton1.setSelected(true);
				//	int id = getHashTableData(Integer.parseInt(limitConfig.getFilterValue()),books,"Book");
					jComboBox3.setSelectedItem(new String(limitConfig.getFilterValue()));
				}
			if(limitConfig.getFilterType().equalsIgnoreCase("Currency")) {
				
				jComboBox2.setSelectedItem(limitConfig.getFilterValue());
			}
		}
	 limitdata = remoteLimit.getLimitOnLimitConfig(limitConfigration.getId());
		limitModel = new TableModelUtil(limitdata, limits);
		jTable1.setModel(limitModel);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private int getHashTableData(int id,Hashtable hashtable,String type) {
		int ids = 0;
		for(int i=0;i<hashtable.size();i++) {
			if(type.equalsIgnoreCase("CounterParty")) {
			    LegalEntity le = (LegalEntity) hashtable.get(i); 
			    if(le.getId() == id) {
			    	ids = i;
			         break;
			    }
			}
			if(type.equalsIgnoreCase("Trader")) {
			    LegalEntity le = (LegalEntity) hashtable.get(i); 
			    if(le.getId() == id) {
			    	ids = i;
			         break;
			    }
			}
			if(type.equalsIgnoreCase("Book")) {
			    Book le = (Book) hashtable.get(i); 
			    if(le.getBookno() == id) {
			    	ids = i;
			         break;
			    }
			}
		}
		return ids;
	}
	private int getHashTableData(String name,Hashtable hashtable,String type) {
		int ids = 0;
		for(int i=0;i<hashtable.size();i++) {
			if(type.equalsIgnoreCase("CounterParty")) {
			    LegalEntity le = (LegalEntity) hashtable.get(i); 
			    if(le.getAlias().equalsIgnoreCase(name)) {
			    	ids = i;
			         break;
			    }
			}
			if(type.equalsIgnoreCase("Trader")) {
			    LegalEntity le = (LegalEntity) hashtable.get(i); 
			    if(le.getAlias().equalsIgnoreCase(name)) {
			    	ids = i;
			         break;
			    }
			}
			if(type.equalsIgnoreCase("Book")) {
			    Book le = (Book) hashtable.get(i); 
			    if(le.getBook_name().equalsIgnoreCase(name)) {
			    	ids = i;
			         break;
			    }
			}
		}
		return ids;
	}
	
	
	private void processDataLimitConfig(Vector uniqueLimitConfig,
			Hashtable<String, LimitConfig> limitConfigs2) {
		// TODO Auto-generated method stub
		if(commonUTIL.isEmpty(uniqueLimitConfig))
			return;
		for(int i=0;i<uniqueLimitConfig.size();i++) {
			LimitConfig limitConfig = (LimitConfig) uniqueLimitConfig.get(i);
			limitConfigs2.put(limitConfig.getConfig_name(), limitConfig);
			tablemodel.insertRow(i, new Object[] {new String(limitConfig.getConfig_name())});
		}
		
	}
	private DefaultTableModel processDataLimitConfig1(Vector uniqueLimitConfig,
			DefaultTableModel tablemodel,boolean firstTime) {
		// TODO Auto-generated method stub
		if(firstTime) {
				if(commonUTIL.isEmpty(uniqueLimitConfig))
					return null;
				for(int i=0;i<uniqueLimitConfig.size();i++) {
					LimitConfig limitConfig = (LimitConfig) uniqueLimitConfig.get(i);
					
					tablemodel.addRow( new Object[] {new String(limitConfig.getConfig_name())});
				
				}
		} else {
			Enumeration<String>  limitCon = limitConfigs.keys();
			while(limitCon.hasMoreElements()) {
				String keys = (String) limitCon.nextElement();
				LimitConfig limitConfig = limitConfigs.get(keys);
				tablemodel.addRow( new Object[] {new String(limitConfig.getConfig_name())});
				
			
			}
		}
		return tablemodel;
		
	}
	private void initComponents() {
		setLayout(new GroupLayout());
		add(getJPanel0(), new Constraints(new Leading(6, 262, 12, 12), new Bilateral(8, 9, 10, 437)));
		add(getJPanel2(), new Constraints(new Bilateral(278, 13, 625), new Bilateral(60, 12, 0)));
		add(getJPanel1(), new Constraints(new Bilateral(280, 10, 626), new Leading(8, 44, 10, 10)));
		setSize(1190, 535);
	}

	private JButton getJButton8() {
		if (jButton8 == null) {
			jButton8 = new JButton();
			jButton8.setText("Save");
		}jButton8.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				Limit limit = new Limit();
				try {
					fillLimitData(limit);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		return jButton8;
	}
	String s[] = { "Limit Config Name " };
	
	 
	
    private void fillLimitData(Limit limit) throws RemoteException {
    	if(jTable0.getSelectedRow() == -1) {
    		commonUTIL.showAlertMessage("Select Limit Arrange");
    		return;
    	}
    	int i = jTable0.getSelectedRow();
    	String maxAmount = (String) jTable0.getValueAt(i, 1);
    	String minAmount = (String) jTable0.getValueAt(i, 0);
    	String warning = (String) jTable0.getValueAt(i, 2);
    	String threshold = (String) jTable0.getValueAt(i, 3);
    	if(checkNotNumberFormat(maxAmount,"Max Amount"))
    		return;
    	if(checkNotNumberFormat(minAmount,"Min Amount"))
    		return;
    	if(checkNotNumberFormat(warning,"Warning"))
    		return;
    	if(checkNotNumberFormat(threshold,"Threshold"))
    		return;
    	limit.setLimitmax(Double.parseDouble(maxAmount));
    	limit.setLimitmin(Double.parseDouble(minAmount));
    	if(limit.getLimitmin() > limit.getLimitmax()) {
    		commonUTIL.showAlertMessage("Min Amount must not be greater then Max Amount");
    		return;
    	}
    	limit.setLimitWarning(Double.parseDouble(warning));
    	limit.setLimitTolarance(Double.parseDouble(threshold));
    	String startDate = (String) jTable0.getValueAt(i, 4);
    	String ExpriyDate = (String) jTable0.getValueAt(i, 6);
    	if(commonUTIL.isEmpty(startDate)) {
    		commonUTIL.showAlertMessage("Enter Start Date ");
		return;
    	}
    	if(commonUTIL.isEmpty(ExpriyDate)) {
    		commonUTIL.showAlertMessage("Enter End Date ");
		return;
    	}
    	
    	limit.setLimitDate(startDate);
    	limit.setLimitAvaliableDate(startDate);
    	limit.setLimitExpiryDate(ExpriyDate);
    	if(limitConfigration != null) {
    		limit.setLimitConfigId(limitConfigration.getId());
    		limit.setLimitType(limitConfigration.getLimitType());
    		limit.setLimitName(limitConfigration.getConfig_name());
    		int limitid = remoteLimit.saveLimit(limit);
    		limit.setId(limitid);
    		
    		limitModel.addRow(limit);
    	}  else {
    		commonUTIL.showAlertMessage("Select LimitConfig");
    		return;
    	}
    	
    }
	
	private boolean checkNotNumberFormat(String amount,String name) {
		boolean flag = false;
		if(commonUTIL.isEmpty(amount) || (!commonUTIL.isNumeric(amount))) {
			commonUTIL.showAlertMessage("Enter " + name + " in Numbers");
			flag = true;
		}
		return flag;
	}
	
	DefaultTableModel tablemodel = new DefaultTableModel(s, 0) {
		@Override
		public boolean isCellEditable(int row, int column) {
			// all cells false
			return false;
		}

	};
	 JDialogTable showAllLimitConfig = new JDialogTable(tablemodel);
	 
	
	private JButton getJButton6() {
		if (jButton6 == null) {
			jButton6 = new JButton();
			jButton6.setText("LOAD");
			showAllLimitConfig.setLocationRelativeTo(this);
		}jButton6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
				showAllLimitConfig.jTable1.removeAll();
				
			DefaultTableModel tablemodel2 = new DefaultTableModel(s, 0);
				
			
			tablemodel2 = processDataLimitConfig1(uniqueLimitConfig,tablemodel2,false);
			if(tablemodel2 == null) {
				commonUTIL.showAlertMessage("No Record");				
				return;
			}
				showAllLimitConfig.jTable1.setModel(tablemodel2);
				showAllLimitConfig.setVisible(true);

			}
		});
		
		
		return jButton6;
	}

	
	
	private JScrollPane getJScrollPane0() {
		if (jScrollPane0 == null) {
			jScrollPane0 = new JScrollPane();
			jScrollPane0.setViewportView(getJTable0());
		}
		return jScrollPane0;
	}

	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getJTable1());
		}
		return jScrollPane1;
	}

	private JTable getJTable1() {
		if (jTable1 == null) {
			jTable1 = new JTable();
			limitModel = new TableModelUtil(limitdata, limits);
			jTable1.setModel(limitModel);
		}
		return jTable1;
	}
	
	private JTable getJTable0() {
		if (jTable0 == null) {
			jTable0 = new JTable(model){
	        	 
	        	public TableCellEditor getCellEditor(int row, int column)
	    		{
	    			int modelColumn = convertColumnIndexToModel( column );
	    			int modelRow = convertRowIndexToModel(row);
	    			if (modelColumn == 4 && row ==modelRow) {
	    				 TableCellEditor t1 = ((TableCellEditor) new DateCellEditor12());
	    			        
	    			       
	    				return (TableCellEditor)t1;
	    				
	    			}
	    			if (modelColumn == 2 && row ==modelRow) {
	    				 TableCellEditor t1 = ((TableCellEditor) limitWarningObject);
	    			        
	    			       
	    				return (TableCellEditor)t1;
	    				
	    			}
	    			if (modelColumn == 3 && row ==modelRow) {
	    				 TableCellEditor t1 = ((TableCellEditor) limitThreObject);
	    			        
	    			       
	    				return (TableCellEditor)t1;
	    				
	    			}
	    			if (modelColumn == 5 && row ==modelRow) {
	    				 TableCellEditor t1 = ((TableCellEditor)tenorObject);
	    			        
	    			       
	    				return (TableCellEditor)t1;
	    				
	    			}else 
	      				 
	    				return super.getCellEditor(1, 0);
	    		
	    		}
			};
			
		}
		return jTable0;
	}

	private JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new JButton();
			jButton3.setText("EDIT");
		}
		return jButton3;
	}

	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setText("DEL");
		}
		return jButton2;
	}

	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("SAVE");
		}jButton1.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				LimitConfig limitConfig = new LimitConfig();
				jTable0.removeAll();
				limitModel.removeALL();
				jTable1.removeAll();
				if(!fillLimitConfig(limitConfig)) {
					return;
				}
				if(limitConfigs.get(limitConfig.getConfig_name()) == null) {
					try {
						int id = remoteLimit.saveLimitConfig(limitConfig,true);
						limitConfig.setId(id);
						limitConfigs.put(limitConfig.getConfig_name(), limitConfig);
						System.out.println(limitConfigs.size());
						String filterValue = jComboBox2.getSelectedItem().toString();
						if(!filterValue.equalsIgnoreCase("NONE")) {
							fillLimitsConfig1(limitConfig,id,"Currency",filterValue);
						}
						filterValue = jComboBox3.getSelectedItem().toString();
						
						if(!filterValue.equalsIgnoreCase("NONE")) {
							fillLimitsConfig1(limitConfig,id,"ProductType",filterValue);
						}
						int bookid = jComboBox4.getSelectedIndex();
						Book book = books.get(bookid);
						if(!filterValue.equalsIgnoreCase("NONE")) {
							fillLimitsConfig1(limitConfig,id,"Book",Integer.valueOf(book.getBookno()).toString());
						}
						limitdata.clear();
			    		limitModel = new TableModelUtil(limitdata, limits);
			    		jTable1.setModel(limitModel);
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					limitConfigration = limitConfig;
				} else {
					commonUTIL.showAlertMessage("Limit Name Already");
					return;
				}
				
			}
		});
		return jButton1;
	}

	private JButton getJButton0() {
		if (jButton0 == null) {
			jButton0 = new JButton();
			jButton0.setText("NEW");
		}jButton0.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				jComboBox0.setSelectedIndex(0);
				jComboBox1.setSelectedIndex(0);
				jComboBox2.setSelectedIndex(0);
				jComboBox3.setSelectedIndex(0);
				jComboBox4.setSelectedIndex(0);
				jTextField0.setText("");
				jTable0.removeAll();
				model = new DefaultTableModel(data1, limitArrange);
				jTable0.setModel(model);
				limitModel.removeALL();
				jTable1.removeAll();
				jRadioButton0.setSelected(true);
				limitConfigration = null;
				limitType = "";
				 limitType = "CounterPartyLimit";
				 currencyFilterType = "Currency";
				 currencyFilterValue = "";
				 productFilterType = "ProductType";
				 productFilterValue = "";
				 bookFilterType = "Book";
				 bookFilterValue = "";
				 FilterType = "CounterParty";
				 FilterValue = "";
			}
		});
		return jButton0;
	}

	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			jPanel2.setLayout(new GroupLayout());
			jPanel2.add(getJScrollPane1(), new Constraints(new Bilateral(5, 12, 23), new Leading(221, 234, 10, 10)));
			jPanel2.add(getJScrollPane0(), new Constraints(new Bilateral(6, 14, 23), new Leading(6, 209, 10, 10)));
		}
		return jPanel2;
	}

	private JLabel getJLabel2() {
		if (jLabel2 == null) {
			jLabel2 = new JLabel();
			jLabel2.setText("Product Type");
		}
		return jLabel2;
	}

	private JComboBox getJComboBox4() {
		if (jComboBox4 == null) {
			jComboBox4 = new JComboBox();
			jComboBox4.setModel(bookModel);
		}
		return jComboBox4;
	}

	private JComboBox getJComboBox3() {
		if (jComboBox3 == null) {
			jComboBox3 = new JComboBox();
			jComboBox3.setModel(productModel);
		}
		return jComboBox3;
	}

	private JComboBox getJComboBox2() {
		if (jComboBox2 == null) {
			jComboBox2 = new JComboBox();
			jComboBox2.setModel(currencytModel);
		}
		return jComboBox2;
	}

	private JLabel getJLabel3() {
		if (jLabel3 == null) {
			jLabel3 = new JLabel();
			jLabel3.setText("Book");
		}
		return jLabel3;
	}

	private JLabel getJLabel1() {
		if (jLabel1 == null) {
			jLabel1 = new JLabel();
			jLabel1.setText("Currency");
		}
		return jLabel1;
	}

	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			jPanel1.setLayout(new GroupLayout());
			jPanel1.add(getJLabel1(), new Constraints(new Leading(23, 48, 10, 10), new Leading(15, 12, 12)));
			jPanel1.add(getJButton4(), new Constraints(new Leading(665, 54, 12, 12), new Leading(11, 12, 12)));
			jPanel1.add(getJComboBox2(), new Constraints(new Leading(73, 81, 10, 10), new Leading(8, 28, 12, 12)));
			jPanel1.add(getJLabel2(), new Constraints(new Leading(170, 78, 10, 10), new Leading(15, 19, 12, 12)));
			jPanel1.add(getJComboBox3(), new Constraints(new Leading(260, 135, 10, 10), new Leading(8, 28, 12, 12)));
			jPanel1.add(getJLabel3(), new Constraints(new Leading(424, 10, 10), new Leading(15, 12, 12)));
			jPanel1.add(getJComboBox4(), new Constraints(new Leading(459, 194, 12, 12), new Leading(8, 28, 12, 12)));
			jPanel1.add(getJButton5(), new Constraints(new Leading(727, 54, 10, 10), new Leading(12, 12, 12)));
			jPanel1.add(getJButton8(), new Constraints(new Leading(795, 56, 10, 10), new Leading(11, 12, 12)));
		}
		return jPanel1;
	}

	private JButton getJButton5() {
		if (jButton5 == null) {
			jButton5 = new JButton();
			jButton5.setText("DEL");
		}
		return jButton5;
	}

	private JButton getJButton4() {
		if (jButton4 == null) {
			jButton4 = new JButton();
			jButton4.setText("ADD");
		}jButton4.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				String [] data1 =
				    	
	        		 {"", ""};
				model.addRow(data1);
				
			}
		});
		return jButton4;
	}
	private JRadioButton getJRadioButton3() {
		if (jRadioButton3 == null) {
			jRadioButton3 = new JRadioButton();
			//jRadioButton3.setSelected(true);
			jRadioButton3.setText("SettlementLimit");
		}jRadioButton3.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				jRadioButton3.setSelected(true);
				jRadioButton0.setSelected(false);
				jRadioButton1.setSelected(false);
				jRadioButton2.setSelected(false);
				limitType = jRadioButton3.getText();
				FilterType = "Settlement";
				
			}
			
		});
		return jRadioButton3;
	}
	private JComboBox getJComobox6() {
		if (jComboBox6 == null) {
			jComboBox6 = new JComboBox();
			//jTextField1.setText("jTextField1");
		//	processDataCombo1(cpCombo,cpData,"cp","CounterParty"); 
			jComboBox6.setModel(issuerCombo);
		}
		return jComboBox6;
	}
	private JRadioButton getJRadioButton2() {
		if (jRadioButton2 == null) {
			jRadioButton2 = new JRadioButton();
		//	jRadioButton2.setSelected(true);
			jRadioButton2.setText("IssuerLimit");
		}
		jRadioButton2.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				jRadioButton3.setSelected(false);
				jRadioButton0.setSelected(false);
				jRadioButton1.setSelected(false);
				jRadioButton2.setSelected(true);
				limitType = jRadioButton2.getText();
				FilterType = "Issuer";
				
			}
			
		});
		return jRadioButton2;
	}

	private JRadioButton getJRadioButton1() {
		if (jRadioButton1 == null) {
			jRadioButton1 = new JRadioButton();
		//	jRadioButton1.setSelected(true);
			jRadioButton1.setText("TraderLimit");
		}jRadioButton1.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				jRadioButton3.setSelected(false);
				jRadioButton0.setSelected(false);
				jRadioButton1.setSelected(true);
				jRadioButton2.setSelected(false);
				limitType = jRadioButton1.getText();
				FilterType = "Trader";
			}
			
		});
		return jRadioButton1;
	}

	private JPanel getJPanel0() {
		if (jPanel0 == null) {
			jPanel0 = new JPanel();
			jPanel0.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			jPanel0.setLayout(new GroupLayout());
			jPanel0.add(getJLabel0(), new Constraints(new Leading(7, 47, 10, 10), new Leading(19, 21, 12, 12)));
			jPanel0.add(getJRadioButton0(), new Constraints(new Leading(9, 172, 12, 12), new Leading(62, 10, 10)));
			jPanel0.add(getJRadioButton1(), new Constraints(new Leading(7, 172, 12, 12), new Leading(119, 10, 10)));
			jPanel0.add(getJTextField0(), new Constraints(new Leading(50, 172, 10, 10), new Leading(18, 26, 12, 12)));
			jPanel0.add(getJComboBox0(), new Constraints(new Leading(7, 223, 10, 10), new Leading(89, 27, 10, 10)));
			jPanel0.add(getJButton1(), new Constraints(new Leading(63, 60, 12, 12), new Leading(481, 10, 10)));
			jPanel0.add(getJButton2(), new Constraints(new Leading(126, 60, 10, 10), new Leading(481, 10, 10)));
			jPanel0.add(getJButton3(), new Constraints(new Trailing(12, 60, 12, 12), new Leading(481, 10, 10)));
			jPanel0.add(getJButton0(), new Constraints(new Leading(3, 12, 12), new Leading(481, 10, 10)));
			jPanel0.add(getJComboBox1(), new Constraints(new Leading(9, 219, 12, 12), new Leading(148, 27, 12, 12)));
			jPanel0.add(getJRadioButton3(), new Constraints(new Leading(9, 185, 12, 12), new Leading(253, 10, 10)));
			jPanel0.add(getJButton6(), new Constraints(new Leading(5, 57, 10, 10), new Leading(452, 12, 12)));
			jPanel0.add(getJRadioButton2(), new Constraints(new Leading(9, 172, 12, 12), new Leading(189, 10, 10)));
			jPanel0.add(getJComobox6(), new Constraints(new Leading(12, 219, 12, 12), new Leading(218, 27, 12, 12)));
			jPanel0.add(getJTextField1(), new Constraints(new Leading(12, 218, 12, 12), new Leading(284, 26, 10, 10)));
		}
		return jPanel0;
	}
	private JTextField jTextField1;
	private JTextField getJTextField1() {
		if (jTextField1 == null) {
			jTextField1 = new JTextField();
			//jTextField1.setText("jTextField1");
		}
		return jTextField1;
	}
	private JRadioButton getJRadioButton0() {
		if (jRadioButton0 == null) {
			jRadioButton0 = new JRadioButton();
			jRadioButton0.setSelected(true);
			jRadioButton0.setText("CounterPartyLimit");
		} 
		jRadioButton0.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				jRadioButton0.setSelected(true);
				jRadioButton1.setSelected(false);
				jRadioButton2.setSelected(false);
				jRadioButton3.setSelected(false);
				limitType = jRadioButton0.getText();
				FilterType = "CounterParty";
				
			}
			
		});
		return jRadioButton0;
	}

	private JComboBox getJComboBox1() {
		if (jComboBox1 == null) {
			jComboBox1 = new JComboBox();
			jComboBox1.setModel(traderCombo);
		}
		return jComboBox1;
	}

	private JComboBox getJComboBox0() {
		if (jComboBox0 == null) {
			jComboBox0 = new JComboBox();
			jComboBox0.setModel(cpCombo);
		}
		return jComboBox0;
	}

	private JLabel getJLabel0() {
		if (jLabel0 == null) {
			jLabel0 = new JLabel();
			jLabel0.setText("Name");
		}
		return jLabel0;
	}

	private JTextField getJTextField0() {
		if (jTextField0 == null) {
			jTextField0 = new JTextField();
		//	jTextField0.setText("jTextField0");
		}
		return jTextField0;
	}
	
	private boolean fillLimitConfig(LimitConfig limitConfig) {
		boolean flag = true;
		if(commonUTIL.isEmpty(jTextField0.getText())) {
			flag = false;
			commonUTIL.showAlertMessage("Enter Config Name");
			return flag;
		}
		
		limitConfig.setConfig_name(jTextField0.getText());
		limitConfig.setLimitType(limitType);
		limitConfig.setFilterType(FilterType);
		if(limitConfig.getFilterType().equalsIgnoreCase("CounterParty")) {
			if(jComboBox0.getSelectedIndex() == -1) {
				flag = false;
				commonUTIL.showAlertMessage("Select CounterParty");
				return flag;
			}
			int i = jComboBox0.getSelectedIndex();
			LegalEntity le = (LegalEntity) cpData.get(i);
			limitConfig.setFilterValue(Integer.toString(le.getId()));
		}
		if(limitConfig.getFilterType().equalsIgnoreCase("Trader")) {
			if(jComboBox1.getSelectedIndex() == -1) {
				flag = false;
				commonUTIL.showAlertMessage("Select Trader");
				return flag;
			}
			int i = jComboBox1.getSelectedIndex();
			LegalEntity le = (LegalEntity) trader.get(i);
			limitConfig.setFilterValue(Integer.toString(le.getId()));
		}
		if(limitConfig.getFilterType().equalsIgnoreCase("Issuer")) {
			if(jComboBox6.getSelectedIndex() == -1) {
				flag = false;
				commonUTIL.showAlertMessage("Select Issuer");
				return flag;
			}
			int i = jComboBox6.getSelectedIndex();
			LegalEntity le = (LegalEntity) issuerData.get(i);
			limitConfig.setFilterValue(Integer.toString(le.getId()));
		}
		return flag;
	}
	private void fillLimitsConfig1(LimitConfig limitConfig,int configID,String filterType,String filterValue) {
		LimitConfig limitConfig1 = new LimitConfig();
		
		limitConfig1.setId(configID);
		limitConfig1.setConfig_name(limitConfig.getConfig_name());
		limitConfig1.setLimitType(limitConfig.getLimitType());
		limitConfig1.setFilterType(filterType);
		limitConfig1.setFilterValue(filterValue);
		limitConfig1.setParentID(0);
		try {
			remoteLimit.saveLimitConfig(limitConfig1, false);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private void processBookData(Vector book,DefaultComboBoxModel cmodel,boolean flag) {
		// TODO Auto-generated method stub
		
	    	if(flag) {
	    		Vector vector;
				Iterator it = book.iterator();
				books.put(0,new Book());
				cmodel.addElement("NONE");
				cmodel.setSelectedItem("NONE");
		    	int i =1;
	    			while(it.hasNext()) {			
	    					Book boo = (Book) it.next();
	    					cmodel.addElement(boo.getBook_name());
	    					books.put(i, boo);
	    					i++;
			
	    			}
	    	} else {
	    		Iterator it = book.iterator();
	    		cmodel.addElement("NONE");
	    		cmodel.setSelectedItem("NONE");
	    		int i =0;
    			while(it.hasNext()) {			
    				StartUPData boo = (StartUPData) it.next();
    					cmodel.addElement(boo.getName());
    					
    				
		
    			}
	    		
	    	}
	}
	public Book getBook( int bookID) {
		Book le = null;
    	
    	le = books.get(bookID);
    	
    	return le;
		
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


class TableModelUtil extends AbstractTableModel {   
    
	 final String[] columnNames;  
	    
	 Vector<Limit> myData;   
	 RemoteReferenceData remoteRef ;
	        
	 public TableModelUtil(  Vector<Limit> myData,String col []) {   
	 	this.columnNames = col;
	this.myData = myData;   
	
	}   

	    
	 public int getColumnCount() {   
	     return columnNames.length;   
	 }   
	    
	 public int getRowCount() {   
		 if(myData != null)
	     return myData.size();   
		 return 0;
	 }   
	 public String getColumnName(int col) {   
	     return columnNames[col];   
	 }   
	 public Object getValueAt(int row, int col) {   
	     Object value = null;  	 
	 
	     Limit limitEvnt = (Limit) myData.get(row);
	    
		 switch (col) {
	     case 0:
	         value = limitEvnt.getId();
	         break;
	        
	     case 1:
	         value =limitEvnt.getLimitType();
	         break;
	     case 2:
	    	
	         value =  limitEvnt.getLimitName();
	         break;
	     case 3:
	         value =limitEvnt.getLimitTolarance();
	         break;
	     case 4:
	         value =limitEvnt.getLimitmax();
	         break;
	     case 5:
	         value =limitEvnt.getLimitmin();
	         break;
	     case 6:
	         value = limitEvnt.getLimitDate();
	         break;
	     case 7:
	         value =limitEvnt.getLimitExpiryDate();
	         break;
	     
		 }
	     return value;
	 }   
	   
	 public boolean isCellEditable(int row, int col) {   
	 return false;   
	 }   
	 public void setValueAt(Object value, int row, int col) {   
	         System.out.println("Setting value at " + row + "," + col   
	                            + " to " + value   
	                            + " (an instance of "    
	                            + value.getClass() + ")");  
	         if(value instanceof Folder) {
	        	 myData.set(row,(Limit) value) ;
	     this.fireTableDataChanged();   
	         System.out.println("New value of data:");   
	         }
	        
	 }   
	    
	 public void addRow(Object value) {   
	    if(myData != null) {
	    	
	    	myData.add((Limit) value) ;
	 this.fireTableDataChanged();   
	    }
	   
	 }   
	    
	 public void delRow(int row) {   
	    if(row != -1) {
	    	myData.remove(row);          
	 this.fireTableDataChanged();   
	    }
	    
	 }   
	 
	 public void udpateValueAt(Object value, int row, int col) {   
	     
	  
		 myData.set(row,(Limit) value) ;
	     for(int i=0;i<columnNames.length;i++)
	    	 	fireTableCellUpdated(row, i);   
	    
	}   
	    
	   
	    public void removeALL() {
	    	if(myData != null) {
	    		myData.removeAllElements();
	    	} 
	    	myData = null;
	  	 this.fireTableDataChanged();  
	    }
}
}

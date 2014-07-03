package apps.window.referencewindow;

import java.awt.Color;
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
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

import org.dyno.visual.swing.layouts.Bilateral;
import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;
import org.dyno.visual.swing.layouts.Trailing;

import util.commonUTIL;
import beans.B2BConfig;
import beans.Book;
import beans.CurrencyPair;
import beans.CurrencySplitConfig;
import beans.StartUPData;
import beans.Users;
import dsServices.RemoteReferenceData;
import dsServices.ServerConnectionUtil;

//VS4E -- DO NOT REMOVE THIS LINE!
public class CurrencySplitWindow extends JPanel {

	private static final long serialVersionUID = 1L;
	RemoteReferenceData referenceData;
	 public static  ServerConnectionUtil de = null;
	 Users user = null;
	private JTable jTable0;
	private JScrollPane jScrollPane0;
	private JLabel jLabel0;
	private JLabel jLabel1;
	private JComboBox jComboBox20;
	private JComboBox jComboBox0;
	private JComboBox jComboBox2;
	private JLabel jLabel3;
	private JComboBox jComboBox1;
	private JLabel jLabel2;
	private JComboBox jComboBox3;
	private JLabel jLabel4;
	private JPanel jPanel0;
	private JButton jButton0;
	private JButton jButton1;
	private JButton jButton2;
	private JButton jButton3;
	 String cols [] = {"id","CurrencyPair","CurrencyToSplitON","Book","FirstCurrencySplit","SecondCurrencySplit","FirstSpotBook","SecondSpotBook"};
	 String b2bcols [] = {"id","CurrencyPair","Holding Currency","BookFrom","BookTo"};
	Vector<CurrencySplitConfig> splitConfigs = new Vector<CurrencySplitConfig>();
	Vector<B2BConfig> b2bConfig = new Vector<B2BConfig>();
	 javax.swing.DefaultComboBoxModel currencyDataModel = new javax.swing.DefaultComboBoxModel();
	 javax.swing.DefaultComboBoxModel currencyPairDataModel = new javax.swing.DefaultComboBoxModel();
	 javax.swing.DefaultComboBoxModel b2bcurrencyDataModel = new javax.swing.DefaultComboBoxModel();
	 javax.swing.DefaultComboBoxModel b2bcurrencyPairDataModel = new javax.swing.DefaultComboBoxModel();
	 javax.swing.DefaultComboBoxModel bookModel = new javax.swing.DefaultComboBoxModel();
	 javax.swing.DefaultComboBoxModel b2bbookModel = new javax.swing.DefaultComboBoxModel();
	 javax.swing.DefaultComboBoxModel b2bTransferTObookModel = new javax.swing.DefaultComboBoxModel();
	 javax.swing.DefaultComboBoxModel bookFirstSpotModel = new javax.swing.DefaultComboBoxModel();
	 javax.swing.DefaultComboBoxModel bookSecondSpotDataModel = new javax.swing.DefaultComboBoxModel();
	 TableModelUtil model = null;
	 TableModelUtilBb2 b2bModel = null;
	 Hashtable<Integer,Book> books = new Hashtable<Integer,Book>();
	private JLabel jLabel6;
	private JLabel jLabel5;
	
		private JTabbedPane jTabbedPane0;
		private JPanel jPanel1;
		private JPanel jPanel2;
		private JLabel jLabel7;
		private JComboBox b2bBook1;
		private JComboBox b2bCurrencyPair;
		private JLabel jLabel8;
		private JComboBox b2bBookTransferTo;
		private JLabel jLabel9;
		private JLabel jLabel10;
		private JComboBox b2bCurrency;
		private JTable jTable1;
		private JScrollPane jScrollPane1;
		private JButton b2bNew;
		private JButton b2bAdd;
		private JButton b2bEdit;
		private JButton b2bDel;
		private static final String PREFERRED_LOOK_AND_FEEL = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
		public CurrencySplitWindow() {
		initComponents();
	}

	
	private void initComponents() {
		init();
		setLayout(new GroupLayout());
		add(getJPanel1(), new Constraints(new Bilateral(4, 12, 1067), new Leading(6, 459, 10, 10)));
		setSize(1076, 475);
	}


	private JButton getB2bDel() {
		if (b2bDel == null) {
			b2bDel = new JButton();
			b2bDel.setText("Delete");
		}b2bDel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				B2BConfig splitConfig = new B2BConfig();
				int selectID = jTable1.getSelectedRow();
				B2BConfig  b2bConf = (B2BConfig)	b2bConfig.get(jTable1.getSelectedRow());
				if(b2bConf != null) {
					try {
						if(referenceData.deleteB2BConfig(b2bConf)) {
							b2bModel.delRow(jTable1.getSelectedRow());
						}
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
				
			}
		});
		return b2bDel;
	}


	private JButton getB2bEdit() {
		if (b2bEdit == null) {
			b2bEdit = new JButton();
			b2bEdit.setText("Edit");
		}
		return b2bEdit;
	}


	private JButton getB2bAdd() {
		if (b2bAdd == null) {
			b2bAdd = new JButton();
			b2bAdd.setText("ADD");
		}b2bAdd.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				B2BConfig b2bConfig = new B2BConfig();
				b2bConfig.setId(0);
				fillB2bConfig(b2bConfig);
					
				try {
					b2bConfig = (B2BConfig) referenceData.saveB2BConfig(b2bConfig);
					b2bModel.addRow(b2bConfig);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
					
					
				
				
			}

			
		});
		return b2bAdd;
	}


	private JButton getB2bNew() {
		if (b2bNew == null) {
			b2bNew = new JButton();
			b2bNew.setText("NEW");
			
		}
		return b2bNew;
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
			b2bModel = new TableModelUtilBb2(b2bConfig, b2bcols, books);
			jTable1.setModel(b2bModel);
		}
		return jTable1;
	}


	private JComboBox getB2bCurrency() {
		if (b2bCurrency == null) {
			b2bCurrency = new JComboBox();
			b2bCurrency.setModel(b2bcurrencyDataModel);
		}
		return b2bCurrency;
	}


	private JLabel getJLabel10() {
		if (jLabel10 == null) {
			jLabel10 = new JLabel();
			jLabel10.setText("Holding Curr");
		}
		return jLabel10;
	}


	private JLabel getJLabel9() {
		if (jLabel9 == null) {
			jLabel9 = new JLabel();
			jLabel9.setText("Transfer Book To");
		}
		return jLabel9;
	}


	private JComboBox getB2bBookTransferTo() {
		if (b2bBookTransferTo == null) {
			b2bBookTransferTo = new JComboBox();
			b2bBookTransferTo.setModel(b2bTransferTObookModel);
		}
		return b2bBookTransferTo;
	}


	private JLabel getJLabel8() {
		if (jLabel8 == null) {
			jLabel8 = new JLabel();
			jLabel8.setText("Transfer Book From");
		}
		return jLabel8;
	}


	private JComboBox getB2bCurrencyPair() {
		if (b2bCurrencyPair == null) {
			b2bCurrencyPair = new JComboBox();
			b2bCurrencyPair.setModel(b2bcurrencyPairDataModel);
		}
		return b2bCurrencyPair;
	}


	private JComboBox getB2bBook1() {
		if (b2bBook1 == null) {
			b2bBook1 = new JComboBox();
			b2bBook1.setModel(b2bbookModel);
		}
		return b2bBook1;
	}


	private JLabel getJLabel7() {
		if (jLabel7 == null) {
			jLabel7 = new JLabel();
			jLabel7.setText("CurrencyPair");
		}
		return jLabel7;
	}


	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setLayout(new GroupLayout());
			jPanel2.add(getJLabel7(), new Constraints(new Leading(24, 79, 10, 10), new Leading(31, 22, 12, 12)));
			jPanel2.add(getB2bBook1(), new Constraints(new Leading(186, 139, 10, 10), new Leading(70, 27, 12, 12)));
			jPanel2.add(getB2bCurrencyPair(), new Constraints(new Leading(188, 139, 10, 10), new Leading(29, 27, 12, 12)));
			jPanel2.add(getJLabel8(), new Constraints(new Leading(24, 121, 10, 10), new Leading(75, 22, 10, 10)));
			jPanel2.add(getB2bBookTransferTo(), new Constraints(new Leading(480, 139, 10, 10), new Leading(70, 27, 12, 12)));
			jPanel2.add(getJLabel9(), new Constraints(new Leading(367, 95, 12, 12), new Leading(75, 22, 12, 12)));
			jPanel2.add(getJLabel10(), new Constraints(new Leading(367, 95, 12, 12), new Leading(34, 22, 12, 12)));
			jPanel2.add(getB2bCurrency(), new Constraints(new Leading(480, 89, 10, 10), new Leading(29, 27, 12, 12)));
			jPanel2.add(getJScrollPane1(), new Constraints(new Leading(11, 1022, 10, 10), new Trailing(12, 194, 10, 10)));
			jPanel2.add(getB2bNew(), new Constraints(new Leading(204, 72, 10, 10), new Leading(135, 25, 10, 10)));
			jPanel2.add(getB2bAdd(), new Constraints(new Leading(311, 72, 10, 10), new Leading(135, 25, 51, 218)));
			jPanel2.add(getB2bEdit(), new Constraints(new Leading(414, 72, 10, 10), new Leading(135, 25, 51, 218)));
			jPanel2.add(getB2bDel(), new Constraints(new Leading(522, 72, 10, 10), new Leading(135, 25, 51, 218)));
		}
		return jPanel2;
	}


	private JTabbedPane getJTabbedPane0() {
		if (jTabbedPane0 == null) {
			jTabbedPane0 = new JTabbedPane();
			jTabbedPane0.addTab("CurrencySplit", getJPanel0());
			jTabbedPane0.addTab("B2BConfig", getJPanel2());
		}
		return jTabbedPane0;
	}


	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setBorder(new LineBorder(Color.black, 1, false));
			jPanel1.setLayout(new GroupLayout());
			jPanel1.add(getJTabbedPane0(), new Constraints(new Leading(5, 1052, 10, 10), new Leading(6, 435, 10, 10)));
		}
		return jPanel1;
	}


	private JLabel getJLabel5() {
		if (jLabel5 == null) {
			jLabel5 = new JLabel();
			jLabel5.setText("     ");
			jLabel5.setBorder(new LineBorder(Color.black, 1, false));
		}
		return jLabel5;
	}

	private JLabel getJLabel6() {
		if (jLabel6 == null) {
			jLabel6 = new JLabel();
			jLabel6.setText("     ");
			jLabel6.setBorder(new LineBorder(Color.black, 1, false));
		}
		return jLabel6;
	}

	private JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new JButton();
			jButton3.setText("Delete");
		}jButton3.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				CurrencySplitConfig splitConfig = new CurrencySplitConfig();
				int selectID = jTable0.getSelectedRow();
				CurrencySplitConfig splitC = (CurrencySplitConfig)	splitConfigs.get(jTable0.getSelectedRow());
				if(splitC != null) {
					try {
						if(referenceData.deleteCurrencySplitConfig(splitC)) {
							model.delRow(jTable0.getSelectedRow());
						}
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
				
			}
		});
		return jButton3;
	}

	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setText("Edit");
		}
		return jButton2;
	}

	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("Add");
		}jButton1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				CurrencySplitConfig splitConfig = new CurrencySplitConfig();
				splitConfig.setId(0);
					fillCurrencySplitConfig(splitConfig);
					
				try {
					splitConfig = (CurrencySplitConfig) referenceData.saveCurrencySplitConfig(splitConfig);
					model.addRow(splitConfig);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
					
					
				
				
			}

			
		});
		return jButton1;
	}

	private JButton getJButton0() {
		if (jButton0 == null) {
			jButton0 = new JButton();
			jButton0.setText("NEW");
		}
		return jButton0;
	}

	public JPanel getJPanel0() {
		if (jPanel0 == null) {
			jPanel0 = new JPanel();
			jPanel0.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			jPanel0.setLayout(new GroupLayout());
			jPanel0.add(getJScrollPane0(), new Constraints(new Leading(9, 921, 10, 10), new Trailing(12, 178, 10, 10)));
			jPanel0.add(getJLabel0(), new Constraints(new Leading(14, 91, 10, 10), new Leading(15, 25, 10, 10)));
			jPanel0.add(getJLabel1(), new Constraints(new Leading(248, 57, 10, 10), new Leading(21, 19, 50, 202)));
			jPanel0.add(getJButton0(), new Constraints(new Leading(288, 10, 10), new Trailing(208, 131, 131)));
			jPanel0.add(getJButton1(), new Constraints(new Leading(394, 61, 10, 10), new Trailing(208, 131, 131)));
			jPanel0.add(getJButton2(), new Constraints(new Leading(501, 61, 10, 10), new Trailing(208, 131, 131)));
			jPanel0.add(getJButton3(), new Constraints(new Leading(608, 77, 10, 10), new Trailing(208, 10, 29)));
			jPanel0.add(getJComboBox0(), new Constraints(new Leading(114, 78, 10, 10), new Leading(15, 25, 51, 202)));
			jPanel0.add(getJComboBox20(), new Constraints(new Leading(323, 52, 10, 10), new Leading(14, 25, 243, 243)));
			jPanel0.add(getJLabel2(), new Constraints(new Leading(21, 39, 10, 10), new Leading(60, 10, 10)));
			jPanel0.add(getJComboBox1(), new Constraints(new Leading(21, 151, 10, 10), new Leading(86, 25, 51, 202)));
			jPanel0.add(getJComboBox2(), new Constraints(new Leading(248, 146, 10, 10), new Leading(90, 25, 243, 243)));
			jPanel0.add(getJComboBox3(), new Constraints(new Leading(467, 162, 10, 10), new Leading(90, 25, 243, 243)));
			jPanel0.add(getJLabel3(), new Constraints(new Leading(251, 106, 10, 10), new Leading(60, 50, 202)));
			jPanel0.add(getJLabel4(), new Constraints(new Leading(467, 120, 10, 10), new Leading(60, 243, 243)));
			jPanel0.add(getJLabel6(), new Constraints(new Leading(605, 82, 12, 12), new Leading(60, 243, 243)));
			jPanel0.add(getJLabel5(), new Constraints(new Leading(361, 72, 10, 10), new Leading(60, 243, 243)));
			jPanel0.add(getJPanel2(), new Constraints(new Leading(794, 100, 10, 10), new Leading(35, 100, 10, 10)));
		}
		return jPanel0;
	}


	private JLabel getJLabel4() {
		if (jLabel4 == null) {
			jLabel4 = new JLabel();
			jLabel4.setText("Spot Split Second Book");
		}
		return jLabel4;
	}

	private JComboBox getJComboBox3() {
		if (jComboBox3 == null) {
			jComboBox3 = new JComboBox();
			jComboBox3.setModel(bookSecondSpotDataModel);
			jComboBox3.setDoubleBuffered(false);
			jComboBox3.setBorder(null);
		}
		return jComboBox3;
	}

	private JLabel getJLabel2() {
		if (jLabel2 == null) {
			jLabel2 = new JLabel();
			jLabel2.setText("Book");
		}
		return jLabel2;
	}

	private JComboBox getJComboBox1() {
		if (jComboBox1 == null) {
			jComboBox1 = new JComboBox();
			jComboBox1.setModel(bookModel);
			jComboBox1.setDoubleBuffered(false);
			jComboBox1.setBorder(null);
		}
		return jComboBox1;
	}

	private JLabel getJLabel3() {
		if (jLabel3 == null) {
			jLabel3 = new JLabel();
			jLabel3.setText("Spot Split First Book");
		}
		return jLabel3;
	}

	private JComboBox getJComboBox2() {
		if (jComboBox2 == null) {
			jComboBox2 = new JComboBox();
			jComboBox2.setModel(bookFirstSpotModel);
			jComboBox2.setDoubleBuffered(false);
			jComboBox2.setBorder(null);
		}
		return jComboBox2;
	}

	private JComboBox getJComboBox0() {
		if (jComboBox0 == null) {
			jComboBox0 = new JComboBox();
			jComboBox0.setModel(currencyPairDataModel);
			jComboBox0.setDoubleBuffered(false);
			jComboBox0.setBorder(null);
		}jComboBox0.addItemListener( new ItemListener() {

        	public void itemStateChanged(ItemEvent e) {
        		// TODO Auto-generated method stub
        		
        	String firstSplit = 	jLabel3.getText();
        	
        	String SecondSplit = 	jLabel4.getText();
        	String currecyPair = jComboBox0.getSelectedItem().toString();
        	String currencyToSplit = jComboBox20.getSelectedItem().toString();
        	
        	firstSplit =  currencyToSplit + "/"+ currecyPair.substring(0, 3) ;
        	SecondSplit =  currecyPair.substring(4, 7) + "/"+currencyToSplit;
        	
        	jLabel5.setText(firstSplit);
        	jLabel6.setText(SecondSplit);
        		
        		
        	}
        	   
           });
		return jComboBox0;
	}

	private JComboBox getJComboBox20() {
		if (jComboBox20 == null) {
			jComboBox20 = new JComboBox();
			jComboBox20.setModel(currencyDataModel);
		}jComboBox20.addItemListener( new ItemListener() {

        	public void itemStateChanged(ItemEvent e) {
        		// TODO Auto-generated method stub
        		
        	String firstSplit = 	"";
        	String SecondSplit = 	"";
        	String currecyPair = jComboBox0.getSelectedItem().toString();
        	String currencyToSplit = jComboBox20.getSelectedItem().toString();
        	
        	firstSplit =  currencyToSplit + "/"+ currecyPair.substring(0, 3) ;
        	SecondSplit = currecyPair.substring(4, 7) + "/"+currencyToSplit;
        	
        	jLabel5.setText(firstSplit);
        	jLabel6.setText(SecondSplit);
        		
        		
        	}
        	   
           });
		return jComboBox20;
	}

	private JLabel getJLabel1() {
		if (jLabel1 == null) {
			jLabel1 = new JLabel();
			jLabel1.setText("Currency");
		}
		return jLabel1;
	}

	private JLabel getJLabel0() {
		if (jLabel0 == null) {
			jLabel0 = new JLabel();
			jLabel0.setText("Currency Pair");
		}
		return jLabel0;
	}

	private JScrollPane getJScrollPane0() {
		if (jScrollPane0 == null) {
			jScrollPane0 = new JScrollPane();
			jScrollPane0.setViewportView(getJTable0());
		}
		return jScrollPane0;
	}

	
	public void init() {
		de =ServerConnectionUtil.connect("localhost", 1099,commonUTIL.getServerIP());
	   	 try {
	   	       referenceData = (RemoteReferenceData) de.getRMIService("ReferenceData");
	   	    Vector currency = (Vector) referenceData.getStartUPData("Currency");
	   	 Vector currencyPair = (Vector) referenceData.selectALLCurrencyPair();
	 	Vector books = (Vector) referenceData.selectALLBooks();
	 	splitConfigs = (Vector) referenceData.selectALCurrencySplitConfig();
		b2bConfig = (Vector) referenceData.selectALB2BConfig();
	 	processBookData(books,bookModel,true);
	 	processBookData(books,bookFirstSpotModel,false);
	 	processBookData(books,bookSecondSpotDataModel,false);
	 	processCurrency(currency,currencyDataModel);
	 	processCurrencyPairs(currencyPair,currencyPairDataModel);
	 	
	   	} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void processCurrency(Vector currency, DefaultComboBoxModel currencyDataModel2) {
		
		
		
		Iterator it = currency.iterator();
    	int i =0;
	while(it.hasNext()) {
		
		StartUPData boo = (StartUPData) it.next();
	
		currencyDataModel2.addElement(boo.getName());
		b2bcurrencyDataModel.addElement(boo.getName());
	
	}
	}
public void processCurrencyPairs(Vector currencypair, DefaultComboBoxModel currencyDataModel2) {
		
		
		
		Iterator it = currencypair.iterator();
    	int i =0;
	while(it.hasNext()) {
		
		CurrencyPair boo = (CurrencyPair) it.next();
	
		currencyDataModel2.addElement(boo.getPrimary_currency()+"/"+boo.getQuoting_currency());
	    b2bcurrencyPairDataModel.addElement(boo.getPrimary_currency()+"/"+boo.getQuoting_currency());
	}
	}
	
	private void processBookData(Vector book,DefaultComboBoxModel cmodel,boolean flag) {
		// TODO Auto-generated method stub
		Vector vector;
		
			
			
			
			Iterator it = book.iterator();
	    	int i =0;
		while(it.hasNext()) {
			
			Book boo = (Book) it.next();
		
			cmodel.addElement(boo.getBook_name());
			b2bbookModel.addElement(boo.getBook_name());
			b2bTransferTObookModel.addElement(boo.getBook_name());
		if(flag)
			books.put(i, boo);
			i++;
		}
		
		
		
		
		
	}
	
	public void fillCurrencySplitConfig(CurrencySplitConfig splitConfig) {
		if(jComboBox0.getSelectedIndex() == -1 )  {
			commonUTIL.showAlertMessage("Select Currency Pair");
			return;
		}
		if(jComboBox20.getSelectedIndex() == -1 )  {
			commonUTIL.showAlertMessage("Select Currency to split ");
			return;
		}
		if(jComboBox1.getSelectedIndex() == -1 )  {
			commonUTIL.showAlertMessage("Select Book ");
			return;
		}
		if(jComboBox2.getSelectedIndex() == -1 )  {
			commonUTIL.showAlertMessage("Select Book for first Currency Split");
			return;
		}
		if(jComboBox3.getSelectedIndex() == -1 )  {
			commonUTIL.showAlertMessage("Select Book for Second Currency Split ");
			return;
		}
		splitConfig.setCurrencyToSplit(jComboBox20.getSelectedItem().toString());
		splitConfig.setBookid(getBook(jComboBox1.getSelectedIndex()).getBookno());
		splitConfig.setCurrencyPair(jComboBox0.getSelectedItem().toString());
		splitConfig.setFirstSpotBook(getBook(jComboBox2.getSelectedIndex()).getBookno());
		splitConfig.setSecondSpotBook(getBook(jComboBox3.getSelectedIndex()).getBookno());
		splitConfig.setFirstCurrencySplit(splitConfig.getCurrencyToSplit() +"/"+splitConfig.getCurrencyPair().substring(0, 3));
		splitConfig.setSecondCurrencySPlit(splitConfig.getCurrencyPair().substring(4, 7) +"/"+ splitConfig.getCurrencyToSplit());
		
	}
	public void fillB2bConfig(B2BConfig splitConfig) {
		if(b2bCurrencyPair.getSelectedIndex() == -1 )  {
			commonUTIL.showAlertMessage("Select Currency Pair");
			return;
		}
		if(b2bCurrency.getSelectedIndex() == -1 )  {
			commonUTIL.showAlertMessage("Select Currency to split ");
			return;
		}
		if(b2bBook1.getSelectedIndex() == -1 )  {
			commonUTIL.showAlertMessage("Select Book ");
			return;
		}
		if(b2bBookTransferTo.getSelectedIndex() == -1 )  {
			commonUTIL.showAlertMessage("Select Book To Transfer");
			return;
		}
		
	
		splitConfig.setBookid(getBook(b2bBook1.getSelectedIndex()).getBookno());
		splitConfig.setCurrencyPair(b2bCurrencyPair.getSelectedItem().toString());
		splitConfig.setTransferBookTo(getBook(b2bBookTransferTo.getSelectedIndex()).getBookno());
		
		splitConfig.setHoldingCurrency(b2bCurrency.getSelectedItem().toString());
		
	}
	public Book getBook( int bookID) {
		Book le = null;
    	
    	le = books.get(bookID);
    	
    	return le;
		
	}
	private JTable getJTable0() {
		if (jTable0 == null) {
			jTable0 = new JTable();
			model = new TableModelUtil(splitConfigs, cols, books);
			jTable0.setModel(model);
		}jTable0.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				int selectID = jTable0.getSelectedRow();
				CurrencySplitConfig splitC = (CurrencySplitConfig)	splitConfigs.get(jTable0.getSelectedRow());
				
				jComboBox20.setSelectedItem(splitC.getCurrencyToSplit());
				jComboBox1.setSelectedItem(getBook(splitC.getBookid()).getBook_name());
				jComboBox0.setSelectedItem(splitC.getCurrencyPair());
				jComboBox2.setSelectedItem(getBook(splitC.getFirstSpotBook()).getBook_name());
				jComboBox3.setSelectedItem(getBook(splitC.getSecondSpotBook()).getBook_name());
				jLabel5.setText(splitC.getFirstCurrencySplit());
	        	jLabel6.setText(splitC.getSecondCurrencySPlit());
			
				
			}
			  });
		return jTable0;
	}


class TableModelUtil extends AbstractTableModel {   
    
	 final String[] columnNames;  
	    
	 Vector<CurrencySplitConfig> data;   
	 RemoteReferenceData remoteRef ;
	 Hashtable<Integer,Book> books;
	 public TableModelUtil( Vector<CurrencySplitConfig> myData,String col [],Hashtable<Integer,Book> books) {   
	 	this.columnNames = col;
	this.data = myData;   
	this.books = books;
	}   

	    
	 public int getColumnCount() {   
	     return columnNames.length;   
	 }   
	    
	 public int getRowCount() {   
		 if(data != null)
	     return data.size();   
		 return 0;
	 }   
	 public String getColumnName(int col) {   
	     return columnNames[col];   
	 }   
	 public Object getValueAt(int row, int col) {   
	     Object value = null;  	 
	
	     CurrencySplitConfig currSplit = (CurrencySplitConfig) data.get(row);
	    
		 switch (col) {
	     case 0:
	         value = currSplit.getId();
	         break;
	     case 1:
	         value =currSplit.getCurrencyPair();
	         break;
	     case 2:
	         value =currSplit.getCurrencyToSplit();
	         break;
	     case 3:
	    	Book le = (Book) getBook(currSplit.getBookid());
	         value =  le.getBook_name();
	         break;
	     case 4:
	         value =currSplit.getFirstCurrencySplit();
	         break;
	     case 5:
	         value = currSplit.getSecondCurrencySPlit();
	         break;
	     case 6:
	    		Book se = (Book) getBook(currSplit.getFirstSpotBook());
	         value =se.getBook_name();
	         break;
	     case 7:
	    	 Book sfe = (Book) getBook(currSplit.getSecondSpotBook());
	         value =sfe.getBook_name();
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
	         if(value instanceof CurrencySplitConfig) {
	     data.set(row,(CurrencySplitConfig) value) ;
	     this.fireTableDataChanged();   
	         System.out.println("New value of data:");   
	         }
	        
	 }   
	    
	 public void addRow(Object value) {   
	    
		 data.add((CurrencySplitConfig) value) ;
	 this.fireTableDataChanged();   
	   
	 }   
	    
	 public void delRow(int row) {   
	    if(row != -1) {
	 data.remove(row);          
	 this.fireTableDataChanged();   
	    }
	    
	 }   
	 
	 public void udpateValueAt(Object value, int row, int col) {   
	     
	  
	     data.set(row,(CurrencySplitConfig) value) ;
	     for(int i=0;i<columnNames.length;i++)
	    	 	fireTableCellUpdated(row, i);   
	    
	}   
	    
	    private Book getBook(int leID) {
	    	Book le = null;
	    	Enumeration boos = books.keys();
	    	while(boos.hasMoreElements()) {
	    		int id = ((Integer) boos.nextElement()).intValue();
	    		Book book = books.get(id);
	    		if(book.getBookno() == leID) {
	    			le = book;
	    			break;
	    		}
	    	}
	    	return le;
	    }
	    
	    public void removeALL() {
	    	if(data != null) {
	  	  data.removeAllElements();
	    	} 
	    data = null;
	  	 this.fireTableDataChanged();  
	    }
}

class TableModelUtilBb2 extends AbstractTableModel {   
    
	 final String[] columnNames;  
	    
	 Vector<B2BConfig> data;   
	 RemoteReferenceData remoteRef ;
	 Hashtable<Integer,Book> books;
	 public TableModelUtilBb2( Vector<B2BConfig> myData,String col [],Hashtable<Integer,Book> books) {   
	 	this.columnNames = col;
	this.data = myData;   
	this.books = books;
	}   

	    
	 public int getColumnCount() {   
	     return columnNames.length;   
	 }   
	    
	 public int getRowCount() {   
		 if(data != null)
	     return data.size();   
		 return 0;
	 }   
	 public String getColumnName(int col) {   
	     return columnNames[col];   
	 }   
	 String b2bcols [] = {"id","CurrencyPair","Holding Currency","BookFrom","BookTo"};
	 public Object getValueAt(int row, int col) {   
	     Object value = null;  	 
	
	     B2BConfig b2bConfig = (B2BConfig) data.get(row);
	    
		 switch (col) {
	     case 0:
	         value = b2bConfig.getId();
	         break;
	     case 1:
	         value =b2bConfig.getCurrencyPair();
	         break;
	     case 2:
	         value =b2bConfig.getHoldingCurrency();
	         break;
	     case 3:
	    	Book le = (Book) getBook(b2bConfig.getBookid());
	         value =  le.getBook_name();
	         break;
	     
	     case 4:
	    		Book se = (Book) getBook(b2bConfig.getTransferBookTo());
	         value =se.getBook_name();
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
	         if(value instanceof B2BConfig) {
	     data.set(row,(B2BConfig) value) ;
	     this.fireTableDataChanged();   
	         System.out.println("New value of data:");   
	         }
	        
	 }   
	    
	 public void addRow(Object value) {   
	    
		 data.add((B2BConfig) value) ;
	 this.fireTableDataChanged();   
	   
	 }   
	    
	 public void delRow(int row) {   
	    if(row != -1) {
	 data.remove(row);          
	 this.fireTableDataChanged();   
	    }
	    
	 }   
	 
	 public void udpateValueAt(Object value, int row, int col) {   
	     
	  
	     data.set(row,(B2BConfig) value) ;
	     for(int i=0;i<columnNames.length;i++)
	    	 	fireTableCellUpdated(row, i);   
	    
	}   
	    
	    private Book getBook(int leID) {
	    	Book le = null;
	    	Enumeration boos = books.keys();
	    	while(boos.hasMoreElements()) {
	    		int id = ((Integer) boos.nextElement()).intValue();
	    		Book book = books.get(id);
	    		if(book.getBookno() == leID) {
	    			le = book;
	    			break;
	    		}
	    	}
	    	return le;
	    }
	    
	    public void removeALL() {
	    	if(data != null) {
	  	  data.removeAllElements();
	    	} 
	    data = null;
	  	 this.fireTableDataChanged();  
	    }
}

  

	
}

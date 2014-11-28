package apps.window.tradewindow.FXPanels;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;
import org.dyno.visual.swing.layouts.Trailing;

import util.NumericTextField;
import util.commonUTIL;
import apps.window.tradewindow.util.FXSplitUtil;
import beans.Book;
import beans.CurrencySplitConfig;
import beans.Favorities;
import beans.LegalEntity;
import beans.Openpos;
import beans.Position;
import beans.Trade;
import dsServices.RemoteMO;
import dsServices.RemoteReferenceData;
import dsServices.ServerConnectionUtil;


//VS4E -- DO NOT REMOVE THIS LINE!
public class FunctionalityD extends JPanel  implements Runnable , ExceptionListener {

	private static final long serialVersionUID = 1L;
	RemoteMO remotemo = null;
	DecimalFormat format = new DecimalFormat("##,###,#######");
	 static private String hostName = "";
	 ServerConnectionUtil de = null;
	String positiongcol [] = {"DATE     ","primaryC","quotingC"};
	 String routingCol [] =  {"TradeID","Book",  "CurrencyPair","AutoType","Type","AMT1","AMT2"};
	 Hashtable<Integer,Book> books = new  Hashtable<Integer,Book>();
	 Hashtable<Integer,LegalEntity> legalentitys = new  Hashtable<Integer,LegalEntity>();
	 DefaultComboBoxModel jtext0 = new DefaultComboBoxModel();
	 DefaultComboBoxModel jtext1 = new DefaultComboBoxModel();
	 DefaultComboBoxModel b2bCpIDs = new DefaultComboBoxModel();
	// DefaultTableModel  routingModel = null;
	 TableModelUtil routingModel = null;
	 public JTable jTable0;
	 public JScrollPane jScrollPane0;
	 public JTable jTable1;
	 public JScrollPane jScrollPane1;
	 public JTable jTable2;
	 public JScrollPane jScrollPane2;
	 public JTable jTable3;
	 public JScrollPane jScrollPane3;
	public JTabbedPane jTabbedPane1;
	public JPanel jPanel1;
	public JPanel jPanel0;
	public JButton jButton1;
	public JButton jButton2;
	public JButton jButton3;
	public JButton jButton4;
	public JButton jButton5;
	public JButton jButton6;
	public JButton jButton0;
	public JPanel jPanel3;
	public JPanel jPanel5;
	JTabbedPane jTabbedPane2;
	JScrollPane jScrollPaneR2;
	JScrollPane jScrollPaneP1;
	public JTable jTableP1;
	public JTable jTableR2;
	
	 public String primaryCurr = "";
	    public String quotingCurr = "";
	    DefaultTableModel positionModel = null;
	    int bookid = 0;
		public JButton jButton7;
		public JPanel jPanel2;
		public JButton jButton8;
		public JLabel jLabel0;
		public JComboBox<String> jLabel2;
		public JLabel jLabel1;
		public JComboBox<String> jLabel3;
		public JLabel jLabel5;
		public JLabel jLabel4;
		public NumericTextField jTextField2;
		public JComboBox jTextField0;
		public JComboBox jTextField1;
		public JLabel jLabel6;
		public JLabel jLabel7;
		public JPanel jPanel6;
		public NumericTextField jTextField3;
		Vector<Trade> rounting = new Vector<Trade>();
		RemoteReferenceData remoteRef;
		


		private JLabel jLabel8;
		public JComboBox b2bBook;
		public JTextField b2bCurrencyPair;
		private JLabel jLabel9;
		private JLabel jLabel10;
		public JTextField b2bTransferTo;
		public FunctionalityD() {
	        initComponents();
	        hostName = commonUTIL.getLocalHostName();
	        init();
	    }
		
		public void clearRounting() {
			routingModel.data.clear();
			
			 
		repaint();
	//	 routingModel = new TableModelUtil(rounting, routingCol,getRemoteRef());
		//	getJTableR2().setModel(routingModel);
		//	repaint();
			//routingModel.fireTableDataChanged();
		//	routingModel.fireTableStructureChanged();
			
	//	
			
			
			
		}
	private void initComponents() {
			setLayout(new GroupLayout());
			add(getJPanel0(), new Constraints(new Leading(5, 833, 10, 10), new Leading(7, 240, 10, 10)));
			setSize(848, 257);
		}
	private JCheckBox getJCheckBox0() {
		if (jCheckBox0 == null) {
			jCheckBox0 = new JCheckBox();
			jCheckBox0.setText("jCheckBox0");
		}
		return jCheckBox0;
	}
	private JTextField getB2bTransferTo() {
		if (b2bTransferTo == null) {
			b2bTransferTo = new JTextField();
			//jTextField6.setText("jTextField6");
			b2bTransferTo.setEnabled(true);
		}
		return b2bTransferTo;
	}
	private JLabel getJLabel10() {
		if (jLabel10 == null) {
			jLabel10 = new JLabel();
			jLabel10.setText("Rate");
		}
		return jLabel10;
	}
	private JLabel getJLabel9() {
		if (jLabel9 == null) {
			jLabel9 = new JLabel();
			jLabel9.setText("LegalEntity");
		}
		return jLabel9;
	}
	private JTextField getB2bcurrencypair() {
		if (b2bCurrencyPair == null) {
			b2bCurrencyPair = new JTextField();
		//	b2bCurrencyPair.setText("jTextField4");
			b2bCurrencyPair.setEditable(false);
			b2bCurrencyPair.setEnabled(false);
		}
		return b2bCurrencyPair;
	}
	private JComboBox getB2bbook() {
		if (b2bBook == null) {
			b2bBook = new JComboBox();
			//jTextField5.setText("jTextField5");
			b2bBook.setEnabled(true);
		}
		return b2bBook;
	}
	private JLabel getJLabel8() {
		if (jLabel8 == null) {
			jLabel8 = new JLabel();
			jLabel8.setText("Currency Pair");
		}
		return jLabel8;
	}
	private NumericTextField getJTextField3() {
		if (jTextField3 == null) {
			jTextField3 = new NumericTextField(10,format);
			jTextField3.setText("0");
		}
		return jTextField3;
	}
	private JLabel getJLabel7() {
		if (jLabel7 == null) {
			jLabel7 = new JLabel();
			jLabel7.setText("Rate");
		}
		return jLabel7;
	}
	private JLabel getJLabel6() {
		if (jLabel6 == null) {
			jLabel6 = new JLabel();
			jLabel6.setText("Rate");
		}
		return jLabel6;
	}
	private JComboBox getJTextField1() {
		if (jTextField1 == null) {
			jTextField1 = new JComboBox();
			jTextField1.setEditable(false);
			//jTextField1.setText("jTextField1");
		}
		return jTextField1;
	}
	private JComboBox getJTextField0() {
		if (jTextField0 == null) {
			jTextField0 = new JComboBox();
			jTextField0.setEditable(false);
			//jTextField0.setText("jTextField0");
		}
		return jTextField0;
	}
	private NumericTextField getJTextField2() {
		if (jTextField2 == null) {
			jTextField2 =  new NumericTextField(10,format);
			jTextField2.setText("0");
			jTextField2.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					try {
						double rate1 = 0.0 ;
						double rate2 = 0.0 ;	
						if(!commonUTIL.isEmpty(jTextField2.getText()))
							rate1 = jTextField2.getDoubleValue();
						if(!commonUTIL.isEmpty(jTextField3.getText()))
							rate2 = jTextField3.getDoubleValue();
						
						  
						if(commonUTIL.isEmpty(rounting))
							return;
						if(rounting.size() > 4) {
							Trade orignalTrade = rounting.get(0);
							if( orignalTrade.getId() == 0) {
									Trade xsplit1  =  rounting.get(1);									
									Trade xsplit2  =  rounting.get(3);
									rounting =     FXSplitUtil.splitTrade(xsplit1, xsplit2, rounting.get(0), rate1, rate2);
		                             setRoutingData(rounting);
							}  else {
								rounting =  FXSplitUtil.splitTrade(rounting, rate1, rate2);
								if(commonUTIL.isEmpty(rounting)) 
									return;
								 setRoutingData(rounting);
								 jTextField3.setText(String.valueOf(rate2));
								 jTextField2.setText(String.valueOf(rate1));
							}
		                 //  getJTable1().repaint();
		                  
						}
		                   
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			
				
				
			});
		}
		
		return jTextField2;
	}
	private JLabel getJLabel4() {
		if (jLabel4 == null) {
			jLabel4 = new JLabel();
			jLabel4.setText("First Spot Book");
		}
		return jLabel4;
	}
	private JLabel getJLabel5() {
		if (jLabel5 == null) {
			jLabel5 = new JLabel();
			jLabel5.setText("Second Spot Book");
		}
		return jLabel5;
	}
	private JComboBox getJLabel3() {
		if (jLabel3 == null) {
			jLabel3 = new JComboBox<String>();
			jLabel3.setEditable(false);
			//jLabel3.setText("INR/EUR");
		}
		return jLabel3;
	}
	private JLabel getJLabel1() {
		if (jLabel1 == null) {
			jLabel1 = new JLabel();
			jLabel1.setText("Second Curr Split");
		}
		return jLabel1;
	}
	private JComboBox getJLabel2() {
		if (jLabel2 == null) {
			jLabel2 = new JComboBox<String>();
	//		jLabel2.setEditable(false);
			//jLabel2.setText("USD/INR");
		}
		return jLabel2;
	}
	private JLabel getJLabel0() {
		if (jLabel0 == null) {
			jLabel0 = new JLabel();
			jLabel0.setText("First Curr Split");
		}
		return jLabel0;
	}
	private JButton getJButton8() {
		if (jButton8 == null) {
			jButton8 = new JButton();
			jButton8.setText("CURR SPLIT");
			jButton8.setEnabled(true);
		}
		return jButton8;
	}
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			jPanel2.setLayout(new GroupLayout());
			jPanel2.add(getJLabel0(), new Constraints(new Leading(3, 81, 10, 10), new Leading(8, 23, 10, 10)));
			jPanel2.add(getJLabel2(), new Constraints(new Leading(5, 10, 10), new Leading(37, 12, 12)));
		//	jPanel2.add(getJLabel4(), new Constraints(new Leading(5, 81, 12, 12), new Leading(61, 23, 12, 12)));
		//	jPanel2.add(getJTextField0(), new Constraints(new Leading(3, 108, 30, 132), new Leading(86, 26, 12, 12)));
			jPanel2.add(getJLabel6(), new Constraints(new Leading(8, 36, 12, 12), new Leading(120, 13, 12, 12)));
			jPanel2.add(getJLabel1(), new Constraints(new Leading(123, 87, 10, 10), new Leading(10, 23, 10, 10)));
			jPanel2.add(getJLabel3(), new Constraints(new Leading(123, 10, 10), new Leading(39, 10, 10)));
	//		jPanel2.add(getJLabel5(), new Constraints(new Leading(123, 99, 10, 10), new Leading(63, 15, 12, 12)));
			//jPanel2.add(getJTextField1(), new Constraints(new Leading(123, 108, 12, 12), new Leading(88, 26, 12, 12)));
			jPanel2.add(getJLabel7(), new Constraints(new Leading(123, 36, 12, 12), new Leading(122, 13, 10, 10)));
			jPanel2.add(getJTextField2(), new Constraints(new Leading(3, 108, 12, 12), new Leading(141, 26, 12, 12)));
			jPanel2.add(getJTextField3(), new Constraints(new Leading(117, 108, 12, 12), new Leading(141, 26, 10, 10)));
			jPanel2.add(getJCheckBox0(), new Constraints(new Leading(222, 31, 12, 12), new Leading(12, 12, 12)));
			
			jPanel2.setVisible(true);
		}
		//jPanel2.setVisible(false);
		return jPanel2;
	}
	private JButton getJButton7() {
		if (jButton7 == null) {
			jButton7 = new JButton();
			jButton7.setText("ROLLOVER");
			jButton7.setEnabled(false);
		}
		return jButton7;
	}
	private JButton getJButton0() {
		if (jButton0 == null) {
			jButton0 = new JButton();
			jButton0.setText("TakeUp");
			jButton0.setEnabled(false);
		}
		return jButton0;
	}

	private JButton getJButton6() {
		if (jButton6 == null) {
			jButton6 = new JButton();
			jButton6.setText("SAVE");
		}
		return jButton6;
	}

	private JButton getJButton5() {
		if (jButton5 == null) {
			jButton5 = new JButton();
			jButton5.setText("DEAL");
		}
		return jButton5;
	}

	private JButton getJButton4() {
		if (jButton4 == null) {
			jButton4 = new JButton();
			jButton4.setText("NEW");
		}
		return jButton4;
	}

	private JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new JButton();
			jButton3.setText("INTERNAL");
		}
		return jButton3;
	}

	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setText("B2B");
		}jButton2.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				
				jTabbedPane1.setVisible(false);
				jTabbedPane2.setVisible(true);
			   
			   
				
			}
		});
		return jButton2;
	}

	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("FAV");
		}jButton1.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if(jTabbedPane2.isVisible()) {
					jTabbedPane1.setVisible(true);
					jTabbedPane2.setVisible(false);
				}	else {
					jTabbedPane1.setVisible(false);
					jTabbedPane2.setVisible(true);
					}
			   
				
			}

			
        
        	
        });

		return jButton1;
	}

	
	
	
	private JPanel getJPanel0() {
		if (jPanel0 == null) {
			jPanel0 = new JPanel();
			jPanel0.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			jPanel0.setLayout(new GroupLayout());
			jPanel0.add(getJTabbedPane0(), new Constraints(new Leading(6, 538, 10, 10), new Leading(48, 183, 10, 10)));
			jPanel0.add(getJTabbedPane2(), new Constraints(new Leading(6, 538, 10, 10), new Leading(48, 183, 10, 10)));
			jPanel0.add(getJPanel1(), new Constraints(new Leading(0, 829, 12, 12), new Leading(5, 39, 10, 10)));
			jPanel0.add(getJPanel2(), new Constraints(new Leading(550, 273, 12, 12), new Leading(51, 176, 12, 12)));
			jPanel0.add(getJPanel6(), new Constraints(new Leading(550, 273, 12, 12), new Leading(51, 176, 12, 12)));
		}
		
		return jPanel0;
	}
	
	private JPanel getJPanel6() {
		if (jPanel6 == null) {
			jPanel6 = new JPanel();
			jPanel6.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			jPanel6.setLayout(new GroupLayout());
			jPanel6.add(getJLabel8(), new Constraints(new Leading(14, 80, 10, 10), new Leading(16, 10, 10)));
			jPanel6.add(getB2bbook(), new Constraints(new Trailing(12, 166, 10, 10), new Leading(59, 26, 12, 12)));
			jPanel6.add(getB2bcurrencypair(), new Constraints(new Trailing(106, 72, 91, 106), new Leading(12, 26, 12, 12)));
			jPanel6.add(getJLabel9(), new Constraints(new Leading(12, 55, 10, 10), new Leading(65, 10, 10)));
			jPanel6.add(getJLabel10(), new Constraints(new Leading(10, 65, 10, 10), new Leading(108, 10, 10)));
			jPanel6.add(getB2bTransferTo(), new Constraints(new Leading(93, 166, 12, 12), new Leading(103, 26, 12, 12)));
			jPanel6.setVisible(false);
		}
		//jPanel6.setVisible(false);
		return jPanel6;
	}
	private JPanel getJPanel3() {
		
		// TODO Auto-generated method stub
		if (jPanel3 == null) {
			jPanel3 = new JPanel();
		//	jPanel0.setBorder(new LineBorder(Color.black, 1, false));
			jPanel3.setLayout(new GroupLayout());
			jPanel3.add(getJTabbedPane2(), new Constraints(new Leading(6, 804, 12, 12), new Leading(48, 255, 10, 10)));
		}
		return jPanel3;
	}
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(new GroupLayout());
			jPanel1.add(getJButton1(), new Constraints(new Leading(12, 75, 10, 10), new Leading(7, 25, 10, 10)));
			jPanel1.add(getJButton2(), new Constraints(new Leading(99, 75, 12, 12), new Leading(7, 25, 12, 12)));
			jPanel1.add(getJButton3(), new Constraints(new Leading(190, 75, 10, 10), new Leading(7, 25, 12, 12)));
			jPanel1.add(getJButton4(), new Constraints(new Leading(273, 75, 10, 10), new Leading(7, 25, 12, 12)));
			jPanel1.add(getJButton6(), new Constraints(new Leading(444, 75, 12, 12), new Leading(7, 25, 12, 12)));
			jPanel1.add(getJButton5(), new Constraints(new Leading(354, 75, 12, 12), new Leading(7, 25, 12, 12)));
			jPanel1.add(getJButton0(), new Constraints(new Leading(529, 75, 10, 10), new Leading(7, 25, 12, 12)));
			jPanel1.add(getJButton7(), new Constraints(new Leading(610, 91, 12, 12), new Leading(7, 25, 12, 12)));
			jPanel1.add(getJButton8(), new Constraints(new Leading(707, 91, 12, 12), new Leading(7, 25, 12, 12)));
		}
		return jPanel1;
	}
	private JTabbedPane getJTabbedPane0() {
		if (jTabbedPane1 == null) {
			jTabbedPane1 = new JTabbedPane();
			jTabbedPane1.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			jTabbedPane1.setVisible(false);
			jTabbedPane1.addTab("jTable0", getJScrollPane0());
			jTabbedPane1.addTab("jTable1", getJScrollPane1());
			jTabbedPane1.addTab("jTable2", getJScrollPane2());
			jTabbedPane1.addTab("jTable3", getJScrollPane3());
		}
		return jTabbedPane1;
	}
	private JTabbedPane getJTabbedPane2() {
		if (jTabbedPane2 == null) {
			jTabbedPane2 = new JTabbedPane();
			jTabbedPane2.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			jTabbedPane2.setVisible(true);
			jTabbedPane2.addTab("Routing", getJScrollPaneR2());
			jTabbedPane2.addTab("Position", getJScrollPaneP1());
		}
		return jTabbedPane2;
	}
	private JScrollPane getJScrollPaneP1() {
		
		if (jScrollPaneP1 == null) {
			jScrollPaneP1 = new JScrollPane();
			jScrollPaneP1.setViewportView(getJTableP1());
		}
		return jScrollPaneP1;
	}
	private Component  getJScrollPaneR2() {
	
		// TODO Auto-generated method stub
		if (jScrollPaneR2 == null) {
			jScrollPaneR2 = new JScrollPane();
			jScrollPaneR2.setViewportView(getJTableR2());
		}
		return jScrollPaneR2;
	}
	private JScrollPane getJScrollPane3() {
		if (jScrollPane3 == null) {
			jScrollPane3 = new JScrollPane();
			jScrollPane3.setViewportView(getJTable3());
		}
		return jScrollPane3;
	}

	private JTable getJTable3() {
		if (jTable3 == null) {
			jTable3 = new JTable();
			jTable3.setModel(new DefaultTableModel(new Object[][] { { "0x0", "0x1", }, { "1x0", "1x1", }, }, new String[] { "Title 0", "Title 1", }) {
				private static final long serialVersionUID = 1L;
				Class<?>[] types = new Class<?>[] { Object.class, Object.class, };
	
				public Class<?> getColumnClass(int columnIndex) {
					return types[columnIndex];
				}
			});
		}
		return jTable3;
	}

	private JScrollPane getJScrollPane2() {
		if (jScrollPane2 == null) {
			jScrollPane2 = new JScrollPane();
			jScrollPane2.setVisible(false);
			jScrollPane2.setViewportView(getJTable2());
		}
		return jScrollPane2;
	}

	private JTable getJTable2() {
		if (jTable2 == null) {
			jTable2 = new JTable();
			jTable2.setModel(new DefaultTableModel(new Object[][] { { "0x0", "0x1", }, { "1x0", "1x1", }, }, new String[] { "Title 0", "Title 1", }) {
				private static final long serialVersionUID = 1L;
				Class<?>[] types = new Class<?>[] { Object.class, Object.class, };
	
				public Class<?> getColumnClass(int columnIndex) {
					return types[columnIndex];
				}
			});
		}
		return jTable2;
	}

	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setVisible(false);
			jScrollPane1.setViewportView(getJTable1());
		}
		return jScrollPane1;
	}

	private JTable getJTable1() {
		if (jTable1 == null) {
			jTable1 = new JTable();
			/*jTable1.setModel(new DefaultTableModel(new Object[][] { { "0x0", "0x1", }, { "1x0", "1x1", }, }, new String[] { "Title 0", "Title 1", }) {
				private static final long serialVersionUID = 1L;
				Class<?>[] types = new Class<?>[] { Object.class, Object.class, };
	
				public Class<?> getColumnClass(int columnIndex) {
					return types[columnIndex];
				}
			});*/
		}
		return jTable1;
	}
	
	 
     private JTable getJTableP1() {
 		if (jTableP1 == null) {
 			
 		    jTableP1 = new JTable();
 		   positionModel = new DefaultTableModel (positiongcol,0);
 		  jTableP1.setModel(positionModel);
 		   
 		}
 		return jTableP1;
 	}
     private JTable getJTableR2() {
  		  final int firstXccy = 0;
  		 final  int secondXccy = 0;
  			
  			//jTableR2 = new JTable();
  		  routingModel = new TableModelUtil(rounting, routingCol,getRemoteRef());
  		 jTableR2 = new JTable(routingModel) {
			public Component prepareRenderer(TableCellRenderer renderer,
					int row, int column) {
				Component c = super.prepareRenderer(renderer, row, column);

				// Color row based on a cell value

				if (!isRowSelected(row)) {
					c.setBackground(getBackground());
					int modelRow = convertRowIndexToModel(row);
					String autotype = (String) getModel().getValueAt(modelRow,
							3);
					
					if ("Offset".equals(autotype)) {
						c.setBackground(Color.RED);
					} else if ("XccySplit".equals(autotype)) {
						
						c.setBackground(Color.yellow); 
					} if ("Original".equals(autotype)) {
						c.setBackground(Color.pink);
						Trade trade = routingModel.data.get(row);
						
						 
					}

				}

					return c;
				}
			};
  		 jTableR2.setModel(routingModel);
  		   
  		
  		return jTableR2;
  	}
     
     public void setRoutingData(Vector<Trade> splitTrades) {
    	 if(commonUTIL.isEmpty(splitTrades) || splitTrades == null) 
    		 return;
    		 rounting = splitTrades;
    	
    	Trade originalTrade =  FXSplitUtil.getOriginalTradeFromRountingTrades(rounting);
    	 rounting.set(0,originalTrade);
    	if(originalTrade.getId() >0 && rounting.size() > 4) {
    		int xxy1 = Integer.parseInt(originalTrade.getAttributeValue("FXccySplitID"));
	    Trade xccy1 = FXSplitUtil.getSplitOrMirrorTradeFromRountingTrades(xxy1,rounting);
	    rounting.set(1, xccy1);
	    Trade mirror1 = FXSplitUtil. getSplitOrMirrorTradeFromRountingTrades( Integer.parseInt(xccy1.getAttributeValue("MirrorID")),rounting);
	    rounting.set(2, mirror1);
	    int xxy2 = Integer.parseInt(originalTrade.getAttributeValue("SXccySplitID"));
	    Trade xccy2 = FXSplitUtil.getSplitOrMirrorTradeFromRountingTrades(xxy2,splitTrades);
	    Trade mirror2 = FXSplitUtil. getSplitOrMirrorTradeFromRountingTrades( Integer.parseInt(xccy2.getAttributeValue("MirrorID")),rounting);
	    rounting.set(3, xccy2);
	    rounting.set(4, mirror2);
	    
	    if(xccy1 != null)
	    jTextField2.setValue(xccy1.getPrice());
	    if(xccy2 != null)
	    jTextField3.setValue(xccy2.getPrice());
    	}
    	routingModel = new TableModelUtil(rounting, routingCol,getRemoteRef());
   	 jTableR2.setModel(routingModel);
    
     }
     public Vector<Trade> getRoutingData() {
    	return rounting;
     }
	private JScrollPane getJScrollPane0() {
		if (jScrollPane0 == null) {
			jScrollPane0 = new JScrollPane();
			jScrollPane0.setVisible(false);
			jScrollPane0.setViewportView(getJTable0());
		}
		return jScrollPane0;
	}
	public void refreshTables(String currencyPair,String bookName,int bookID) {
    	jTabbedPane1.setVisible(false);
    	jTabbedPane2.setVisible(true);
	//	jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(bookName));
		refreshPositionTable(currencyPair,bookID);
    }
	
	public void refreshPositionTable(String currencyPair,int bookID) {
    	primaryCurr = currencyPair.substring(0,3);
    	quotingCurr = currencyPair.substring(4,currencyPair.length());
    	positiongcol[0] = "DATE     ";
    	positiongcol[1] =  quotingCurr;
    	positiongcol[2] =primaryCurr;
    	bookid = bookID;
    	positionModel = new DefaultTableModel(positiongcol,0);
    	refreshPositionTable();
    	
    }
    public void refreshPositionTable() {
    	try{
    		Vector openpos = (Vector) remotemo.getOpenPositionOnProductSubType(primaryCurr+"/"+quotingCurr);
    		 Iterator it = openpos.iterator();
    		 while(it.hasNext()) {
    			 Openpos openp = (Openpos) it.next();
    		     String date = openp.getProductSubType().substring(openp.getProductSubType().indexOf('_'), openp.getProductSubType().indexOf('_') +10);
    			 positionModel.insertRow(positionModel.getRowCount(), new Object[]{date,openp.getQuotingAmt(),openp.getOpenNominal()});
    		 }
    		 
    		 jTableP1.removeAll();
    		 jTableP1.setModel(positionModel);
    		 
    	} catch(RemoteException e) {
    }
    	
    	
    	
    	
    	
    }
    
    public void fillCurrencyPair(Vector currencyPairs) {
    	if(!commonUTIL.isEmpty(currencyPairs)) {
    		for(int i=0;i<currencyPairs.size();i++) {
    			 Favorities fav = (Favorities) currencyPairs.elementAt(i);
    		 jLabel2.addItem(fav.getTypeName());
    		 jLabel3.addItem(fav.getTypeName());
    		}
    	}
    }
    Hashtable<Integer,Book> bookCs = new Hashtable<Integer,Book>();
	public JCheckBox jCheckBox0;
	private static final String PREFERRED_LOOK_AND_FEEL = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
    public void fillBooks(Vector  books) {
    	jTextField0.removeAll();
    	jTextField1.removeAll();
    	jtext0 = null;
    	jtext0 = new DefaultComboBoxModel();
    	jtext1= null;
    	jtext1 = new DefaultComboBoxModel();
    	
    	bookCs.clear();
    	if(!commonUTIL.isEmpty(books)) {
    		for(int i=0;i<books.size();i++) {
    			 Favorities fav = (Favorities) books.elementAt(i);
    			 Book book = new Book();
    			 book.setBook_name(fav.getTypeName());
    			 book.setBookno(Integer.valueOf(fav.getTypeValue()).intValue());
    		jtext0.insertElementAt(fav.getTypeName(), i);
    		jtext1.insertElementAt(fav.getTypeName(), i);
    		 bookCs.put(i, book);
    		}
    		jTextField0.setModel(jtext0);
    		jTextField1.setModel(jtext1);
    	}
    }
    Hashtable<Integer,LegalEntity> cpData = new Hashtable<Integer,LegalEntity>();
    
    public void fillB2bCPs(Vector  b2bCPData) {
    	b2bBook.removeAll();
    	jTextField1.removeAll();
    	cpData.clear();
    	
    	b2bCpIDs = new DefaultComboBoxModel();
    	jtext1= null;
    	jtext1 = new DefaultComboBoxModel();
    	if(!commonUTIL.isEmpty(b2bCPData)) {
    		for(int i=0;i<b2bCPData.size();i++) {
    			 Favorities fav = (Favorities) b2bCPData.elementAt(i);
    			 LegalEntity le = new LegalEntity();
    			 le.setName(fav.getTypeName());
    			 le.setId(Integer.valueOf(fav.getTypeValue()).intValue());
    			 b2bCpIDs.insertElementAt(fav.getTypeName(), i);
    			
    		cpData.put(i, le);
    		}
    		b2bBook.setModel(b2bCpIDs);
    		
    	}
    }
    public void fillCPs(Vector  books) {
    	jTextField0.removeAll();
    	jTextField1.removeAll();
    	cpData.clear();
    	jTextField0.removeAll();
    	jTextField1.removeAll();
    	jtext0 = null;
    	jtext0 = new DefaultComboBoxModel();
    	jtext1= null;
    	jtext1 = new DefaultComboBoxModel();
    	if(!commonUTIL.isEmpty(books)) {
    		for(int i=0;i<books.size();i++) {
    			 Favorities fav = (Favorities) books.elementAt(i);
    			 LegalEntity le = new LegalEntity();
    			 le.setName(fav.getTypeName());
    			 le.setId(Integer.valueOf(fav.getTypeValue()).intValue());
    			 jtext0.insertElementAt(fav.getTypeName(), i);
    			 jtext1.insertElementAt(fav.getTypeName(), i);
    		cpData.put(i, le);
    		}
    		jTextField0.setModel(jtext0);
    		jTextField1.setModel(jtext1);
    	}
    }
    public Book getCurrSPlitBook(int id) {
    	Enumeration keys = bookCs.keys();
    	Book book = null;
    	while(keys.hasMoreElements()) {
    		Integer key = (Integer) keys.nextElement();
    		if(key.intValue() == id) {
    		book = bookCs.get(key);
    		
    			
    			break;
    		}
    	}
    	return book;
    		
    }
    
    public LegalEntity getCurrSPlitCP(int id) {
    	Enumeration keys = cpData.keys();
    	LegalEntity le = null;
    	while(keys.hasMoreElements()) {
    		Integer key = (Integer) keys.nextElement();
    		if(key.intValue() == id) {
    			 le = cpData.get(key);
    		
    			
    			break;
    		}
    	}
    	return le;
    		
    }
    public RemoteReferenceData getRemoteRef() {
		return remoteRef;
	}
	public void setRemoteRef(RemoteReferenceData remoteRef) {
		this.remoteRef = remoteRef;
	}
    
    public void init() {
    	de =ServerConnectionUtil.connect("localhost", 1099,commonUTIL.getServerIP());
	   	 try {
	   		remotemo = (RemoteMO) de.getRMIService("MO");
	   	jTabbedPane1.setVisible(false);
	   	 } catch (RemoteException e) {
				// TODO Auto-generated catch block
				commonUTIL.displayError("FxTradePanel --> Functionlity Panel", "init() ", e);
			}
    }
    
	private JTable getJTable0() {
		if (jTable0 == null) {
			jTable0 = new JTable();
			jTable0.setModel(new DefaultTableModel(new Object[][] { { "0x0", "0x1", }, { "1x0", "1x1", }, }, new String[] { "Title 0", "Title 1", }) {
				private static final long serialVersionUID = 1L;
				Class<?>[] types = new Class<?>[] { Object.class, Object.class, };
	
				public Class<?> getColumnClass(int columnIndex) {
					return types[columnIndex];
				}
			});
		}
		return jTable0;
	}
	 @Override
		public synchronized void run() {
			for( ; ; ) {
				try {
					
					  ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://"+hostName+":61616");

			            // Create a Connection
			            Connection connection = connectionFactory.createConnection();
			            connection.start();

			            connection.setExceptionListener(this);

			            // Create a Session
			            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			            // Create the destination (Topic or Queue)
			            Destination destination = session.createQueue("POSITION");

			            // Create a MessageConsumer from the Session to the Topic or Queue
			            MessageConsumer consumer = session.createConsumer(destination);
			            Message message = consumer.receive(12000);

			            if (message instanceof ObjectMessage) {
			            	ObjectMessage oMessage = (ObjectMessage) message;

			            	Position pos = (Position) oMessage.getObject();
			                System.out.println("From Funcationality start Process Position for Pos "+ pos.getPositionId());
			               
			              
			                System.out.println("FromFuncationality  End Processing Position for Pos "+ pos.getPositionId());
			                refreshPositionTable();
			    			//Thread.sleep(8000);
			            } 

			            consumer.close();
			            session.close();
			            connection.close();
			        
					
				} catch (JMSException j) {
					// TODO Auto-generated catch block
					j.printStackTrace();
				} catch(Exception e) {
					 commonUTIL.displayError("PositionManager", "run()", e);
				 }
			}
			
		}
		@Override
		public void onException(JMSException e) {
			commonUTIL.displayError("PositionManager","Error in listening" , e);
			
		}
		

class TableModelUtil extends AbstractTableModel {   
    
	 final String[] columnNames;  
	    
	 Vector<Trade> data;   
	 RemoteReferenceData remoteRef ;
	
	 public TableModelUtil( Vector<Trade> myData,String col [],RemoteReferenceData remoteRef) {   
	 	this.columnNames = col;
	this.data = myData;   
	this.remoteRef = remoteRef;
	
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
	     
	     Trade trade = (Trade) data.get(row);
	     if(trade == null) 
	    	 return null;
	   //  System.out.println(" row " + row + " "+ trade.getId());
	    
		 switch (col) {
		 case 0:
	         value =trade.getId();
	         break;
	    
	     case 1:
	        try {
				value = getBook(trade.getBookId()).getBook_name();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	         break;
	    
	     case 2:
	         value =trade.getTradedesc();
	         break;
	     case 3:
		    	
		        value =  trade.getAutoType();
		         break;
	     case 4:
		    	
	        value =  trade.getType();
	         break;
	     case 5:
	    	
	         value =  trade.getQuantity();
	         break;
	     case 6:
	         value =trade.getNominal();
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
	         if(value instanceof Trade) {
	     data.set(row,(Trade) value) ;
	     this.fireTableDataChanged();   
	         System.out.println("New value of data:");   
	         }
	        
	 }   
	    
	 public void addRow(Object value) {   
	    
		 data.add((Trade) value) ;
	 this.fireTableDataChanged();   
	   
	 }   
	 
	 public void setData(Vector<Trade> trades) {
		 this.data.clear();
		 
		 this.data = trades;
		 
		 this.fireTableDataChanged();  
	 }
	    
	 public void delRow(int row) {   
	    if(row != -1) {
	    	System.out.println(row);
	 data.remove(row);          
	 this.fireTableDataChanged();  
	// this.fir
	    }
	    
	 }   
	 
	 public void udpateValueAt(Object value, int row, int col) {   
	     if(value == null)
	    	 return;
	  
	     data.set(row,(Trade) value) ;
	     for(int i=0;i<columnNames.length;i++)
	    	 	fireTableCellUpdated(row, i);   
	    
	}   
	    
	    private Book getBook(int bookid) throws RemoteException {
	    	Book le = new Book();
	    	le.setBookno(bookid);
	    	if(books.containsKey(bookid)) 
	    	{
	    		le = books.get(bookid);
	    	} else {
	    		le = (Book) getRemoteRef().selectBook(le);
	    		if(le != null)
	    		books.put(bookid, le);
	    	}
	    	return le;
	    }
	    private LegalEntity getLE(int legalID) throws RemoteException {
	    	
	    LegalEntity le = new LegalEntity();
	    	if(legalentitys.containsKey(legalID)) {
	    		le = legalentitys.get(legalID);
	    	} else {
	    		le = (LegalEntity) remoteRef.selectLE(legalID);
	    		if(le != null)
	    		legalentitys.put(legalID, le);
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

package apps.window.tradewindow;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.ComponentInputMap;
import javax.swing.DefaultComboBoxModel;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.border.EtchedBorder;
import javax.swing.plaf.ActionMapUIResource;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.dyno.visual.swing.layouts.Bilateral;
import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;
import org.dyno.visual.swing.layouts.Trailing;

import com.jidesoft.combobox.DateComboBox;
import com.jidesoft.combobox.TableExComboBox;
import com.jidesoft.combobox.TableExComboBoxSearchable;
import com.jidesoft.grid.SortableTable;

import constants.MMConstants;
import constants.TradeConstants;

import apps.window.tradewindow.FXPanels.TradeAttributesD;
import apps.window.tradewindow.panelWindow.FeesPanel;
import apps.window.tradewindow.panelWindow.SDIPanel;
import apps.window.tradewindow.panelWindow.TaskPanel;
import apps.window.tradewindow.panelWindow.TransferPanel;
import apps.window.tradewindow.util.StaticDataCacheUtil;
import beans.Attribute;
import beans.Book;
import beans.Coupon;
import beans.Favorities;
import beans.Flows;
import beans.LegalEntity;
import beans.Product;
import beans.StartUPData;
import beans.Trade;
import beans.Users;

import productPricing.MMPricing;
import productPricing.Pricer;
import productPricing.pricingUtil.MMCashFlow;
import util.NumericTextField;
import util.RemoteServiceUtil;
import util.commonUTIL;
import beans.Trade;
import beans.Users;
import dsEventProcessor.TaskEventProcessor;
import dsServices.RemoteReferenceData;


//VS4E -- DO NOT REMOVE THIS LINE!
public class MMTradePanel extends TradePanel {

	private static final long serialVersionUID = 1L;
	private JPanel jPanel0;
	private JPanel jPanel1;
	private JPanel jPanel2;
	private JPanel jPanel3;
	 TradeAttributesD attributes = null;
	private JPanel jPanel5;
	private JLabel jLabel0;
	private JLabel jLabel1;
	private JLabel jLabel2;
	private JLabel jLabel3;
	private JRadioButton Rollover;
	private JPanel jPanel6;
	private JLabel buysell;
	private JLabel jLabel5;
	private JButton New;
	private JButton Save;
	private JButton SaveAsNew;
	private JButton Delete;
	private JLabel jLabel6;
	private JLabel jLabel7;
	private JLabel jLabel8;
	private JLabel jLabel9;
	private JLabel jLabel10;
	private JLabel jLabel11;
	private JComboBox Currency;
	private JRadioButton Rollback;
	private JTextField TradeID;
	private DateComboBox startDate;
	private DateComboBox endDate;
	private NumericTextField Amount1;
	private JTextField status;
	private JTable CashFlowTable;
	private JScrollPane jScrollPane1;
	private JPanel jPanel7;
	private JLabel jLabel12;
	private JLabel jLabel14;
	private NumericTextField splitRate;
	private NumericTextField inrEqu;
	private JPanel flotingPanel;
	private JPanel compDPanel;
	private JButton jButton4;
	private JButton jButton5;
	private JComboBox dayCount;
	private JLabel jLabel15;
	private JComboBox rateC;
	private JComboBox actionC;
	String rateType = "Fixed";
    private JPanel fixedPanel;
	private JLabel jLabel16;
	private NumericTextField FixedRate;
	private JLabel jLabel17;
	private JComboBox rateIndexC;
	private JPanel RateIndex;
	private JPanel compPanelSub;
	private JLabel jLabel18;
	private JComboBox tenor;
	private JLabel jLabel19;
	private NumericTextField spread;
	private JLabel jLabel21;
	private JComboBox payFreq;
	private JLabel jLabel22;
	private JComboBox compMethod;
	private JLabel jLabel23;
	private JComboBox compFreq;
	Vector<String> currencyData = null;
	Vector<LegalEntity> legalEntityData  = null;
	Vector<LegalEntity> traderData  = null;
	Vector<beans.Book> bookData = null;
	TradeApplication app = null;
	 SDIPanel sdiPanel = null;
	 FeesPanel feesPanel = null;
	 TaskPanel taskPanel = null; 
	 TransferPanel transferPanel;
	 MMPricing pricing = null;
	public TableExComboBox counterParty = null;
	public TableExComboBox book = null;
	public TableExComboBox trader = null;
	Trade trade = null;
	Product product = null;
	Coupon coupon = null;
	DecimalFormat format1 = new DecimalFormat("##,###,#######.######");
	
	javax.swing.DefaultComboBoxModel<String> actionstatus = null;
	String productType = "MM";
	String productSubType = "";
	 Hashtable<String,String>  attributeDataValue = new Hashtable<String,String>();
	 DefaultTableModel attributeModel = null;
	 ActionMap actionMap =null;
Users usr = null;
	private static final String PREFERRED_LOOK_AND_FEEL = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
	/**
	 * @return the rateType
	 */
	public String getRateType() {
		return rateType;
	}

	/**
	 * @param rateType the rateType to set
	 */
	public void setRateType(String rateType) {
		this.rateType = rateType;
	}
	public MMTradePanel(Users user) {
		setUser(user);
		initComponents();
		fixedPanel.setVisible(true);
		flotingPanel.setVisible(false);
		compDPanel.setVisible(false);
	}
	//public MMTradePanel() {
	//	initComponents();
//	}

	private void initComponents() {
		
		
		currencyData = StaticDataCacheUtil.getDomainValues("Currency");
		actionstatus = new javax.swing.DefaultComboBoxModel<String>();
		bookData = StaticDataCacheUtil.getUserFavBooks(usr.getId(),Favorities.book);
		traderData = StaticDataCacheUtil.getUserFavTrader(usr.getId(), Favorities.trader);
		legalEntityData = StaticDataCacheUtil.getUserFavLegalEntity(usr.getId(), Favorities.CounterParty);
		
	
	
		setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
		setFocusable(true);
		setEnabled(true);
		setVisible(true);
		setVerifyInputWhenFocusTarget(true);
		setDoubleBuffered(true);
		setRequestFocusEnabled(true);
		setOpaque(true);
		setLayout(new GroupLayout());
		add(getJPanel1(), new Constraints(new Leading(1, 866, 8, 8), new Leading(99, 122, 16, 16)));
		add(getJPanel3(), new Constraints(new Leading(0, 866, 12, 12), new Leading(314, 151, 12, 12)));
		add(getJPanel0(), new Constraints(new Leading(1, 866, 12, 12), new Leading(3, 95, 10, 10)));
		add(getHotKeysPanels(), new Constraints(new Leading(5, 866,10, 10), new Leading(471, 46,10, 10)));
		add(getJPanel4(), new Constraints(new Leading(882, 334, 10, 10), new Leading(5, 517, 10, 10)));
		add(getJPanel2(), new Constraints(new Leading(3, 866, 10, 10), new Leading(224, 94, 10, 10)));
		setSize(1225, 544);
		  actionMap = new ActionMapUIResource();
		    actionMap.put("action_save", new AbstractAction() {
		        @Override
		        public void actionPerformed(ActionEvent e) {
		        	buildTrade(trade,"save");
		        }
		    });
		    actionMap.put("action_new", new AbstractAction() {
		        @Override
		        public void actionPerformed(ActionEvent e) {
		        	//commonUTIL.showAlertMessage("New action performed.");
		        	buildTrade(trade,"NEW");
		        }
		    });
		    actionMap.put("action_del", new AbstractAction() {
		        @Override
		        public void actionPerformed(ActionEvent e) {
		        	commonUTIL.showAlertMessage("Delete action performed.");
		        }
		    });
		    actionMap.put("action_saveasnew", new AbstractAction() {
		        @Override
		        public void actionPerformed(ActionEvent e) {
		        	//commonUTIL.showAlertMessage("Save As New action performed.");
		        	buildTrade(trade,"saveAsNew");
		        
		        //	commonUTIL.showAlertMessage("Save As new  action performed.");
		        }
		    });
		    actionMap.put("action_cashflow", new AbstractAction() {
		        @Override
		        public void actionPerformed(ActionEvent e) {
		        	//commonUTIL.showAlertMessage("Save As New action performed.");
		        	buildCashFlow();
		        
		        //	commonUTIL.showAlertMessage("Save As new  action performed.");
		        }

				
		    });
		   
		    processTableData(attributeDataValue,attributeModel,attributes); 
		    setCashFlow(getCashFlowTable(), null, "MM"); // for empty Cash Flow

	}

	private JComboBox getCompFreq() {
		if (compFreq == null) {
			Vector<String> compoundingData = StaticDataCacheUtil.getDomainValues("Compounding");
			compFreq = new JComboBox(commonUTIL.convertVectortoSringArray(compoundingData));
		
			
		}
		return compFreq;
	}
	

	private JLabel getJLabel23() {
		if (jLabel23 == null) {
			jLabel23 = new JLabel();
			jLabel23.setText("Comp Freq");
		}
		return jLabel23;
	}
	
	 
	private JComboBox getJComboBox8() {
		if (compMethod == null) {
			compMethod = new JComboBox();
			compMethod.setModel(new DefaultComboBoxModel(new Object[] { "item0", "item1", "item2", "item3" }));
		}
		return compMethod;
	}

	private JLabel getJLabel22() {
		if (jLabel22 == null) {
			jLabel22 = new JLabel();
			jLabel22.setText("Comp Method");
		}
		return jLabel22;
	}

	private JComboBox getPayFreq() {
		if (payFreq == null) {
		
			Vector<String> paymentFRQData = StaticDataCacheUtil.getDomainValues("PaymentFRQ");
			payFreq = new JComboBox(commonUTIL.convertVectortoSringArray(paymentFRQData));
		}
		return payFreq;
	}

	private JLabel getJLabel21() {
		if (jLabel21 == null) {
			jLabel21 = new JLabel();
			jLabel21.setText("Payment Freq");
		}
		return jLabel21;
	}

	private NumericTextField getSpread() {
		if (spread == null) {
			spread = new NumericTextField();
		}
		return spread;
	}

	private JLabel getJLabel19() {
		if (jLabel19 == null) {
			jLabel19 = new JLabel();
			jLabel19.setText("Spread");
		}
		return jLabel19;
	}


	private JComboBox getTenor() {
		if (tenor == null) {
			Vector<String> indexData = StaticDataCacheUtil.getDomainValues("Tenor");
			tenor = new JComboBox(commonUTIL.convertVectortoSringArray(indexData));
			
		}
		return tenor;
	}

	private JLabel getJLabel18() {
		if (jLabel18 == null) {
			jLabel18 = new JLabel();
			jLabel18.setText("Tenor");
		}
		return jLabel18;
	}

	private JPanel getJPanel13() {
		if (RateIndex == null) {
			RateIndex = new JPanel();
			RateIndex.setLayout(new GroupLayout());
			RateIndex.add(getJLabel17(), new Constraints(new Leading(12, 12, 12), new Leading(10, 10, 10)));
			RateIndex.add(getRateIndexC(), new Constraints(new Leading(13, 121, 10, 10), new Bilateral(36, 12, 22)));
			RateIndex.add(getJLabel18(), new Constraints(new Leading(179, 10, 10), new Leading(10, 12, 12)));
			RateIndex.add(getTenor(), new Constraints(new Leading(179, 136, 10, 10), new Bilateral(36, 12, 22)));
			RateIndex.add(getJLabel19(), new Constraints(new Leading(378, 10, 10), new Leading(16, 10, 10)));
			RateIndex.add(getSpread(), new Constraints(new Leading(378, 94, 10, 10), new Leading(38, 10, 10)));
		
		}
		return RateIndex;
	}
	
	private JComboBox getRateIndexC() {
		if (rateIndexC == null) {
			Vector<String> indexData = StaticDataCacheUtil.getDomainValues("INDEX");
			rateIndexC = new JComboBox(commonUTIL.convertVectortoSringArray(indexData));
			
		}
		return rateIndexC;
	}

	private JLabel getJLabel17() {
		if (jLabel17 == null) {
			jLabel17 = new JLabel();
			jLabel17.setText("Rate Index");
		}
		return jLabel17;
	}

	

	private NumericTextField getFixedRate() {
		if (FixedRate == null) {
			FixedRate = new NumericTextField();
		}
		return FixedRate;
	}

	private JLabel getJLabel16() {
		if (jLabel16 == null) {
			jLabel16 = new JLabel();
			jLabel16.setText("Fixed Rate");
		}
		return jLabel16;
	}

	private JPanel getCompDPanel() {
		if (compPanelSub == null) {
			compPanelSub = new JPanel();
			compPanelSub.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			compPanelSub.setLayout(new GroupLayout());
			compPanelSub.add(getPayFreq(), new Constraints(new Leading(5, 118, 10, 10), new Leading(23, 26, 10, 10)));
			compPanelSub.add(getJLabel21(), new Constraints(new Leading(9, 10, 10), new Leading(1, 12, 12)));
			compPanelSub.add(getJLabel22(), new Constraints(new Leading(202, 10, 10), new Leading(3, 10, 10)));
			compPanelSub.add(getJComboBox8(), new Constraints(new Leading(204, 124, 10, 10), new Leading(23, 26, 12, 12)));
			compPanelSub.add(getJLabel23(), new Constraints(new Leading(381, 10, 10), new Leading(5, 10, 10)));
			compPanelSub.add(getCompFreq(), new Constraints(new Leading(383, 152, 10, 10), new Leading(23, 26, 12, 12)));
		}
		return compPanelSub;
	}

	private JPanel getJPanel12() {
		if (fixedPanel == null) {
			fixedPanel = new JPanel();
		//	fixedPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			fixedPanel.setLayout(new GroupLayout());
			fixedPanel.add(getJLabel16(), new Constraints(new Leading(6, 10, 10), new Leading(15, 10, 10)));
			
			fixedPanel.add(getFixedRate(), new Constraints(new Leading(88, 83, 10, 10), new Leading(15, 24, 10, 10)));
		    fixedPanel.setVisible(true);
		}
		return fixedPanel;
	}
	
	private JComboBox getActionC() {
		if (actionC == null) {
			actionC = new JComboBox();
			 actionstatus.insertElementAt("NEW", 0);
	   		 actionstatus.setSelectedItem("NEW");
	   		actionC.setModel(actionstatus);
		}
		return actionC;
	}

	private JComboBox getRateSelection() {
		if (rateC == null) {
			rateC = new JComboBox();
			DefaultComboBoxModel rates = new DefaultComboBoxModel();
			rates.addElement(new String("Fixed"));
			rates.addElement(new String("Float"));
			rates.addElement(new String("CompD"));
			rateC.setModel(rates);
			rateC.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					if(rateC.getSelectedItem().toString().equalsIgnoreCase("Fixed")) {
						rateType = "Fixed";
						fixedPanel.setVisible(true);
						flotingPanel.setVisible(false);
						compDPanel.setVisible(false);
						
					} 
					if(rateC.getSelectedItem().toString().equalsIgnoreCase("Float")) {
						rateType = "Float";
						fixedPanel.setVisible(false);
						flotingPanel.setVisible(true);
						compDPanel.setVisible(false);
						
						
					} 
					if(rateC.getSelectedItem().toString().equalsIgnoreCase("CompD")) {
						rateType = "CompD";
						compDPanel.setVisible(true);
						fixedPanel.setVisible(false);
						flotingPanel.setVisible(false);
						
						
					} 
					
				}
			});
			
			
		}
		return rateC;
	}

	private JLabel getJLabel15() {
		if (jLabel15 == null) {
			jLabel15 = new JLabel();
			jLabel15.setText("DayCount");
		}
		return jLabel15;
	}

	private JComboBox getDayCount() {
		if (dayCount == null) {
		
			Vector<String> dayCountData = StaticDataCacheUtil.getDomainValues("DayCount");
			dayCount = new JComboBox(commonUTIL.convertVectortoSringArray(dayCountData));
		}
		return dayCount;
	}

	


	private JButton getJButton5() {
		if (jButton5 == null) {
			jButton5 = new JButton();
			jButton5.setText("RollBack");
		}
		return jButton5;
	}

	private JButton getJButton4() {
		if (jButton4 == null) {
			jButton4 = new JButton();
			jButton4.setText("RollOver");
		}
		return jButton4;
	}

	private JPanel getJPanel9() {
		if (compDPanel == null) {
			compDPanel =new JPanel();
			//compDPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			compDPanel.setLayout(new GroupLayout());
			compDPanel.add(getCompDPanel(), new Constraints(new Bilateral(2, 0, 0), new Leading(2, 74, 10, 10)));
			compDPanel.setVisible(true);
		}
		return compDPanel;
	}

	private JPanel getJPanel8() {
		if (flotingPanel == null) {
			flotingPanel = new JPanel();
			//flotingPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			flotingPanel.setLayout(new GroupLayout());
			flotingPanel.add(getJPanel13(), new Constraints(new Bilateral(2, 0, 0), new Leading(2, 74, 10, 10)));
			flotingPanel.setVisible(false);
		}
		return flotingPanel;
	}

	private NumericTextField getJTextField8() {
		if (inrEqu == null) {
			inrEqu = new  NumericTextField(10,format1);
		}
		return inrEqu;
	}

	private NumericTextField getJTextField7() {
		if (splitRate == null) {
			splitRate = new  NumericTextField(10,format1);
		}
		return splitRate;
	}

	private JLabel getJLabel14() {
		if (jLabel14 == null) {
			jLabel14 = new JLabel();
			jLabel14.setText("INR Equi");
		}
		return jLabel14;
	}

	private JLabel getJLabel12() {
		if (jLabel12 == null) {
			jLabel12 = new JLabel();
			jLabel12.setText("Split Rate");
		}
		return jLabel12;
	}

	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			jPanel2.setLayout(new GroupLayout());
			jPanel2.add(getJPanel7(), new Constraints(new Leading(665, 190, 10, 10), new Leading(12, 72, 12, 12)));
			jPanel2.add(getJPanel8(), new Constraints(new Leading(4, 657, 10, 10), new Leading(4, 84, 10, 10)));
			jPanel2.add(getJPanel9(), new Constraints(new Leading(4, 646, 10, 10), new Leading(8, 64, 10, 10)));
			jPanel2.add(getJPanel12(), new Constraints(new Leading(4, 646, 10, 10), new Leading(8, 64, 10, 10)));
			
		}
		return jPanel2;
	}

	private JPanel getJPanel7() {
		if (jPanel7 == null) {
			jPanel7 = new JPanel();
			jPanel7.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			jPanel7.setLayout(new GroupLayout());
			jPanel7.add(getJLabel12(), new Constraints(new Leading(3, 10, 10), new Leading(3, 10, 10)));
			jPanel7.add(getJLabel14(), new Constraints(new Leading(6, 12, 12), new Leading(37, 12, 12)));
			jPanel7.add(getJTextField7(), new Constraints(new Leading(72, 97, 10, 10), new Leading(3, 12, 12)));
			jPanel7.add(getJTextField8(), new Constraints(new Leading(72, 96, 12, 12), new Leading(37, 12, 12)));
		}
		return jPanel7;
	}



	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getCashFlowTable());
		}
		return jScrollPane1;
	}

	
	
	public JTable getCashFlowTable() {
		if (CashFlowTable == null) {
			CashFlowTable = new JTable();
			CashFlowTable.setModel(new DefaultTableModel(new Object[][] { { "0x0", "0x1", }, { "1x0", "1x1", }, }, new String[] { "Title 0", "Title 1", }) {
				private static final long serialVersionUID = 1L;
				Class<?>[] types = new Class<?>[] { Object.class, Object.class, };
	
				public Class<?> getColumnClass(int columnIndex) {
					return types[columnIndex];
				}
			});
		}
		return CashFlowTable;
	}

	private JTextField getStatus() {
		if (status == null) {
			status = new JTextField();
			status.setBackground(new Color(128, 255, 255));
			status.setEditable(false);
			status.setText("NONE");
		}
		return status;
	}


	private NumericTextField getNominal() {
		if (Amount1 == null) {
			Amount1 = new NumericTextField(10,format1);
		}
		return Amount1;
	}

	private com.jidesoft.combobox.DateComboBox getEndDate() {
		if (endDate == null) {
			endDate = new com.jidesoft.combobox.DateComboBox();
			endDate.setFormat(new SimpleDateFormat("dd/MM/yyyy"));
			Calendar currentDate = Calendar.getInstance();
			endDate.setDate(currentDate.getTime());
			endDate.setName("endDate");
		}
		return endDate;
	}

	private com.jidesoft.combobox.DateComboBox getStartDate() {
		if (startDate == null) {
			startDate = new com.jidesoft.combobox.DateComboBox();
			startDate.setFormat(new SimpleDateFormat("dd/MM/yyyy"));
			Calendar currentDate = Calendar.getInstance();
			startDate.setDate(currentDate.getTime());
			startDate.setName("startDate");
		}
		return startDate;
	}

	private JTextField getTradeID() {
		if (TradeID == null) {
			TradeID = new JTextField();
			TradeID.setText("0");
			TradeID.addKeyListener(new KeyAdapter() {
			@Override
            public void keyTyped(KeyEvent e) {
            	 try {
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                
                	 String number = TradeID.getText();
	                   int tradeId = Integer.parseInt(number); 
	                  
						trade = (Trade) RemoteServiceUtil.getRemoteTradeService().selectTrade(tradeId);
					
                 
                }
            	 } catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}catch(NumberFormatException n) {
						TradeID.setText("");
	                	 commonUTIL.showAlertMessage("Enter Number only " );
	                	 
	                 }
            }
            });
		}
		
		return TradeID;
	}

	private TableExComboBox getTrader() {
		if (trader == null) {
			trader  =  getTraderComboBox(traderData);
			trader.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					// TODO Auto-generated method stub
					int leid = trader.getSelectedIndex();
					// @ yogesh 24/02/2015
					// returns if -1
					if(leid == 0 || leid == -1)
						return;
					LegalEntity le = traderData.get(leid);
					trader.setName(String.valueOf(((le)).getId()));
					//System.out.println(counterPary.getName());
					
				}
			});
			
		}
			return trader;
			
			
		
	}

	private TableExComboBox getCounterParty() {
		if (counterParty == null) {
			counterParty =  getCounterPartyComboBox(legalEntityData);
			counterParty.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					int leid = counterParty.getSelectedIndex();
					// @ yogesh 24/02/2015
					// returns if -1
					if(leid == 0 || leid == -1)
						return;
					LegalEntity le = legalEntityData.get(leid);
					counterParty.setName(String.valueOf(((le)).getId()));
					//System.out.println(counterPary.getName());
					
				}
			});
			
		}
			return counterParty;
	}

	private TableExComboBox getBook() {
		if (book == null) {
			book =  getBookComboBox(bookData);
			book.setEditable(false);
			book.setBorder(null);
			book.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					int leid = book.getSelectedIndex();
					// @ yogesh 24/02/2015
					// returns if -1
					if(leid == 0 || leid == -1)
						return;
					Book boo = bookData.get(leid);
					book.setName(String.valueOf(((boo)).getBookno()));
					//System.out.println(counterPary.getName());
					
				}
			});
			
		}
			return book;
	}
	private JRadioButton getJRadioButton3() {
		if (Rollback == null) {
			Rollback = new JRadioButton();
			Rollback.setSelected(true);
			Rollback.setText("RollBack");
		}
		return Rollback;
	}

	private JComboBox getCurrency() {
		if (Currency == null) {
			Currency = new JComboBox();
			Currency = new JComboBox(commonUTIL.convertVectortoSringArray(currencyData));
		}
		return Currency;
	}

	private JLabel getJLabel11() {
		if (jLabel11 == null) {
			jLabel11 = new JLabel();
			jLabel11.setText("EndDate");
		}
		return jLabel11;
	}

	private JLabel getJLabel10() {
		if (jLabel10 == null) {
			jLabel10 = new JLabel();
			jLabel10.setText("StartDate");
		}
		return jLabel10;
	}

	private JLabel getJLabel9() {
		if (jLabel9 == null) {
			jLabel9 = new JLabel();
			jLabel9.setText("Action");
		}
		return jLabel9;
	}

	private JLabel getJLabel8() {
		if (jLabel8 == null) {
			jLabel8 = new JLabel();
			jLabel8.setText("Status");
		}
		return jLabel8;
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
			jLabel6.setText("Nominal");
		}
		return jLabel6;
	}

	private JButton getJButton3() {
		if (Delete == null) {
			Delete = new JButton();
			Delete.setText("Delete");
		}
		return Delete;
	}

	private JButton getJButton2() {
		if (SaveAsNew == null) {
			SaveAsNew = new JButton();
			SaveAsNew.setText("saveAsnew");
		
			SaveAsNew.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					buildTrade(trade,"saveAsNew");
					
				}
			});
		}
		return SaveAsNew;
	}

	private JButton getJButton1() {
		if (Save == null) {
			Save = new JButton();
			Save.setText("Save");
			Save.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					buildTrade(trade,"save");
					
				}
			});
		}
		return Save;
	}

	private JButton getJButton0() {
		if (New == null) {
			New = new JButton();
			New.setText("New");
			New.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					buildTrade(trade,"NEW");
					
				}
			});
		}
		return New;
	}

	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			jPanel1.setLayout(new GroupLayout());
			//jPanel1.add(getJLabel5(),new Constraints(new Leading(372, 10, 10), new Leading(17, 10, 10)));
			jPanel1.add(getJLabel10(),  new Constraints(new Leading(69, 10, 10), new Leading(9, 12, 12)));
			jPanel1.add(getJLabel11(),new Constraints(new Leading(213, 10, 10), new Leading(9, 12, 12)));
			jPanel1.add(getStartDate(),  new Constraints(new Leading(66, 136, 10, 10), new Leading(28, 25, 12, 12)));
			jPanel1.add(getEndDate(), new Constraints(new Leading(210, 138, 10, 10), new Leading(28, 25, 12, 12)));
			jPanel1.add(getBuysell(), new Constraints(new Leading(6, 12, 12), new Leading(31, 12, 12)));
			//jPanel1.add(getProductName(), new Constraints(new Leading(6, 432, 10, 10), new Leading(93, 25, 12, 12)));
			jPanel1.add(getNominal(),  new Constraints(new Leading(69, 221, 12, 12), new Leading(68, 25, 12, 12)));
			jPanel1.add(getJLabel6(),  new Constraints(new Leading(6, 12, 12), new Leading(68, 12, 12)));
			jPanel1.add(getJLabel7(),new Constraints(new Leading(296, 12, 12), new Leading(68, 12, 12)));
			jPanel1.add(getRateSelection(), new Constraints(new Leading(366, 108, 12, 12), new Leading(68, 25, 12, 12)));
			//jPanel1.add(getStatus(), new Constraints(new Trailing(12, 143, 385, 419), new Leading(90, 22, 12, 12)));
			jPanel1.add(getJLabel15(),new Constraints(new Leading(447, 10, 10), new Leading(9, 12, 12)));
			jPanel1.add(getDayCount(),new Constraints(new Leading(447, 93, 12, 12), new Leading(28, 25, 12, 12)));
		//	jPanel1.add(getJLabel8(),  new Constraints(new Trailing(119, 383, 383), new Leading(76, 12, 12))); 
			jPanel1.add(getCurrency(), new Constraints(new Leading(366, 54, 12, 12), new Leading(28, 25, 12, 12)));
			//jPanel1.add(getActionC(),new Constraints(new Leading(562, 138, 10, 10), new Leading(36, 22, 12, 12)));
			//jPanel1.add(getJLabel9(), new Constraints(new Leading(567, 10, 10), new Leading(12, 12, 12))); 

			jPanel1.add(getJLabel0(), new Constraints(new Leading(362, 12, 12), new Leading(9, 12, 12)));
			
		}
		return jPanel1;
	}

	

	private JLabel getJLabel5() {
		if (jLabel5 == null) {
			jLabel5 = new JLabel();
			jLabel5.setText("Trade ID");
		}
		return jLabel5;
	}

	private JLabel getBuysell() {
		if (buysell == null) {
			buysell = new JLabel();
			buysell.setText("DEPOSIT");
		}
			buysell.addMouseListener(new MouseListener() {

				@Override
				public void mouseClicked(MouseEvent e) {
					if (buysell.getText().equalsIgnoreCase("LOAN")) {
						buysell.setText("DEPOSIT");
					} else {
						buysell.setText("LOAN");
					}
				}

				@Override
				public void mouseEntered(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseExited(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mousePressed(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseReleased(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}
			});	
			return buysell;
		}
		
	

	

	private JRadioButton getJRadioButton0() {
		if (Rollover == null) {
			Rollover = new JRadioButton();
			Rollover.setSelected(true);
			Rollover.setText("RollOver");
		}
		return Rollover;
	}

	private JLabel getJLabel3() {
		if (jLabel3 == null) {
			jLabel3 = new JLabel();
			jLabel3.setText("Trader");
		}
		return jLabel3;
	}

	private JPanel getJPanel0() {
		if (jPanel0 == null) {
			jPanel0 = new JPanel();
			jPanel0.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			jPanel0.setLayout(new GroupLayout());
			jPanel0.add(getTrader(), new Constraints(new Leading(532, 177, 10, 10), new Leading(44, 26, 10, 12)));
			jPanel0.add(getJLabel1(), new Constraints(new Leading(9, 10, 10), new Leading(49, 12, 12)));
			jPanel0.add(getCounterParty(), new Constraints(new Leading(304, 175, 12, 12), new Leading(44, 26, 10, 12)));
			jPanel0.add(getJLabel2(),  new Constraints(new Leading(211, 12, 12), new Leading(49, 12, 12)));
			jPanel0.add(getBook(), new Constraints(new Leading(67, 138, 10, 10), new Leading(44, 26, 10, 12)));
			jPanel0.add(getJLabel5(),  new Constraints(new Leading(7, 10, 10), new Leading(12, 12, 12)));
			jPanel0.add(getTradeID(), new Constraints(new Leading(67, 138, 12, 12), new Leading(7, 26, 40, 40)));
			//jPanel0.add(getJLabel1(), new Constraints(new Leading(155, 10, 10), new Leading(15, 12, 12)));
			jPanel0.add(getJLabel9(), new Constraints(new Leading(233, 10, 10), new Leading(10, 12, 12)));
			jPanel0.add(getActionC(), new Constraints(new Leading(304, 174, 12, 12), new Leading(5, 26, 10, 51)));
			jPanel0.add(getJLabel8(), new Constraints(new Leading(483, 10, 10), new Leading(12, 12, 12)));
			jPanel0.add(getJLabel3(), new Constraints(new Leading(485, 41, 12, 12), new Leading(49, 25, 12, 12)));
			jPanel0.add(getStatus(), new Constraints(new Leading(535, 170, 12, 12), new Leading(5, 26, 10, 51)));
		}
		return jPanel0;
	}

	private JLabel getJLabel2() {
		if (jLabel2 == null) {
			jLabel2 = new JLabel();
			jLabel2.setText("CounterParty");
		}
		return jLabel2;
	}

	private JLabel getJLabel1() {
		if (jLabel1 == null) {
			jLabel1 = new JLabel();
			jLabel1.setText("Book");
		}
		return jLabel1;
	}

	private JLabel getJLabel0() {
		if (jLabel0 == null) {
			jLabel0 = new JLabel();
			jLabel0.setText("Currency");
		}
		return jLabel0;
	}

	private JPanel getHotKeysPanels() {
		if (jPanel5 == null) {
			jPanel5 = new JPanel();
			jPanel5.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			jPanel5.setLayout(new GroupLayout());
			jPanel5.add(getJButton0(), new Constraints(new Leading(19, 10, 10), new Leading(8, 10, 10)));
			jPanel5.add(getJButton1(), new Constraints(new Leading(98, 10, 10), new Leading(8, 12, 12)));
			jPanel5.add(getJButton2(), new Constraints(new Leading(179, 10, 10), new Leading(8, 12, 12)));
			jPanel5.add(getJButton4(), new Constraints(new Leading(301, 10, 10), new Leading(8, 12, 12)));
			jPanel5.add(getJButton5(), new Constraints(new Leading(388, 12, 12), new Leading(8, 12, 12)));
		//	jPanel5.add(getJButton5(), new Constraints(new Leading(489, 10, 10), new Leading(8, 12, 12)));
		}
		return jPanel5;
	}
// for Attributes. 
	private JPanel getJPanel4() {
		if (attributes == null) {
			attributes = new TradeAttributesD();
			attributes.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			//attributes.setLayout(new GroupLayout());
			
		}
		return attributes;
	}

	
	
	
// cash flow panel
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jPanel3 = new JPanel();
		//	jPanel3.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			jPanel3.setLayout(new GroupLayout());
			jPanel3.add(getJScrollPane1(), new Constraints(new Leading(3, 853, 10, 10), new Leading(4, 139, 10, 10)));
		}
		return jPanel3;
	}

	@Override
	public void setTaskPanel(BackOfficePanel panel) {
		// TODO Auto-generated method stub
		this.taskPanel = taskPanel;
		
	}
	public TaskPanel getTaskPanel() {
		return taskPanel;
	}
	public FeesPanel getFeesPanel() {
		return feesPanel;
	}
	public SDIPanel getSdiPanel() {
		return sdiPanel;
	}

	public void setSdiPanel(SDIPanel sdiPanel) {
		this.sdiPanel = sdiPanel;
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
	public void getTradeTransfers(BackOfficePanel panel) {
		try {
			transferPanel.setTrade(trade);
			panel.fillJTabel((Vector)RemoteServiceUtil.getRemoteBOProcessService().queryWhere("Transfer", "tradeId = " + trade.getId()));
		} catch (RemoteException e) {
			
			e.printStackTrace();
		}
	}
	@Override
	public void setTradePostings(BackOfficePanel panel) {
		// TODO Auto-generated method stub
		
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
		
	}

	@Override
	public void setUser(Users user) {
		// TODO Auto-generated method stub
		this.usr =user;
		
	}

	@Override
	public String getAction() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTradeApplication(TradeApplication app) {
		// TODO Auto-generated method stub
		this.app = app;
	}

	@Override
	public void processTask(TaskEventProcessor taskEvent, Users WindowUser) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLimitBreachMarkOnAction(int i) {
		// TODO Auto-generated method stub
		
	}
	private boolean validateProductData() {
		boolean flag = false;
		
		String pstartDate = commonUTIL.convertDateTimeTOString(startDate.getDate());
		String pendDate = commonUTIL.convertDateTimeTOString(endDate.getDate());
		String cFreq = payFreq.getSelectedItem().toString();
		String cType = rateC.getSelectedItem().toString();
		String curr = Currency.getSelectedItem().toString();
		String cDayCount = dayCount.getSelectedItem().toString();
		double cfixedRate = commonUTIL.converStringToDouble(FixedRate.getText());
		double cspread = commonUTIL.converStringToDouble(spread.getText());
		String cTenor = tenor.getSelectedItem().toString();
		String cRateIndex = rateIndexC.getSelectedItem().toString();
		String cCompFreq = compFreq.getSelectedItem().toString();
		if(commonUTIL.isEmpty(pstartDate) || (!commonUTIL.isStringDate(pstartDate)))  {
			commonUTIL.showAlertMessage("Select Start Date  " );
	        return flag;
	   }
		
		if(commonUTIL.isEmpty(pendDate) || (!commonUTIL.isStringDate(pendDate)))  {
			commonUTIL.showAlertMessage("Select End Date  " );
			 return flag;
	   }
		if(commonUTIL.isEmpty(curr))  {
			commonUTIL.showAlertMessage("Select Currency " );
			 return flag;
	   }
		if(commonUTIL.isEmpty(cDayCount))  {
			commonUTIL.showAlertMessage("Select DayCount " );
			 return flag;
	   }
		if(commonUTIL.isEmpty(cDayCount))  {
			commonUTIL.showAlertMessage("Select DayCount " );
			 return flag;
	   }
		//product = new Product();
		product.setIssueCurrency(curr);
		product.setIssueDate(pstartDate);
		product.setMarturityDate(pendDate);
		product.setProductType("MM");
		product.setProdcutShortName("CASH."+buysell.getText());
		try {
			product.setQuantity(Amount1.getDoubleValue().doubleValue());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			commonUTIL.showAlertMessage("Please enter number in Nominal " );
			Amount1.setFocusable(true);
			return flag;
		}
		//coupon = new Coupon();
		coupon.setCCY(curr);
		coupon.setCouponType(cType);
		coupon.setDayCount(cDayCount);
			coupon.setFixedRate(cfixedRate);
		if(cType.equalsIgnoreCase(MMConstants.FLOAT)) {
			coupon.setCouponFrequency(cFreq);
			product.setTenor(cTenor);
			coupon.setYieldDecimals(cspread); // yieldDecimals treated as spread as don't want to create unneccessary columns. 
			coupon.setYieldMethod(cRateIndex); 
		}
		flag = true;
		
		
		
		
		
		
		//String floatingRate = Floating
		
		
		return flag;
		
	}
	@Override
	public void buildTrade(Trade trade, String actionType) {
		// TODO Auto-generated method stub
		if(actionType.equalsIgnoreCase("NEW")) {
			setNewAction(trade);
		}
		if(actionType.equalsIgnoreCase("saveAsNew")) {
			product = new Product();
			coupon = new Coupon();
			actionC.setSelectedItem("NEW");
			actionC.getModel().setSelectedItem("NEW");
			if(validateProductData() && validateTradeData()) {
				setSaveAsNew(trade);
			}
		}
		if(actionType.equalsIgnoreCase("save")) {
			if(validateProductData() && validateTradeData()) {
				setSave(trade);
			}
		}
		
	}
	
	private boolean validateTradeData() {
		boolean flag = false;
		String act = actionC.getSelectedItem().toString();
		if(!actionC.getSelectedItem().toString().equalsIgnoreCase("NEW")) {
			if(actionC.getSelectedIndex() ==  -1) {
				commonUTIL.showAlertMessage("Select Action");
				return flag;
			}
		}
		if(commonUTIL.isEmpty(((String)Currency.getSelectedItem())))  {
		    commonUTIL.showAlertMessage("Select Currency");
			return flag;
	}
		if (counterParty.getSelectedIndex() == -1) {
			commonUTIL.showAlertMessage("Select CounterParty");
			return flag;
		}
		
		if (book.getSelectedIndex() == -1) {
			commonUTIL.showAlertMessage("Select Book");
			return flag;
		}
		if (startDate.getDate().after(endDate.getDate())){
    		commonUTIL.showAlertMessage("End date cannot be before Trade end date");
    		return flag;	
    	}
			flag = true;
		return flag;
	}
	private void setSave(Trade trade2) {
		// TODO Auto-generated method stub
		//trade = new Trade();
		trade.setQuantity(product.getQuantity());
		trade.setNominal(0);
		if(buysell.getText().equalsIgnoreCase("LOAN")) {
			 trade.setType("SELL");
		}else  {
				trade.setType("BUY");
		}
		 // product.setId(0);
			try {
			 RemoteServiceUtil.getRemoteProductService().updateProduct(product, coupon);
				 
			
			if(product.getId() >0) {
				 Vector<String> message = new Vector<String>();
						//trade.setProductId(product.getId());
						trade.setTradedesc(product.getProdcutShortName());
						trade.setTradedesc1(product.getProductType());
						trade.setCurrency(Currency.getSelectedItem().toString());
						trade.setCpID(commonUTIL.converStringToInteger(counterParty.getName()));
						trade.setBookId(commonUTIL.converStringToInteger(book.getName()));
						trade.setTraderID(commonUTIL.converStringToInteger(trader.getName()));
						trade.setAction(actionC.getSelectedItem().toString());
						trade.setStatus(status.getText());
						trade.setPrice(coupon.getFixedRate());
						trade.setUserID(usr.getId());
						trade.setProductType("MM");
						trade.setAttributes(getAttributeValue(attributes));
		               	trade.setFees(feesPanel.getFeesDataV());
		               	trade.setEffectiveDate(product.getIssueDate());
		               	trade.setDelivertyDate(product.getMarturityDate());
		               	trade = saveTrade(message,trade);
		               	if(trade  != null) {
		               		commonUTIL.showAlertMessage("Trade Saved with ID " + trade.getId());
		               		openTrade(trade);
		               	}
						
			} else {
				commonUTIL.showAlertMessage("Trade Product not created ");
			}
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			} 
		
	private void setSaveAsNew(Trade trade2) {
		// TODO Auto-generated method stub
		trade = new Trade();
		trade.setQuantity(product.getQuantity());
		trade.setNominal(0);
		if(buysell.getText().equalsIgnoreCase("LOAN")) {
			 trade.setType("SELL");
		}else  {
				trade.setType("BUY");
		}
		  product.setId(0);
			try {
				int productId  = RemoteServiceUtil.getRemoteProductService().saveProduct(product, coupon);
				  product.setId(productId);
			
			if(product.getId() >0) {
				 Vector<String> message = new Vector<String>();
						trade.setProductId(product.getId());
						trade.setTradedesc(product.getProdcutShortName());
						trade.setTradedesc1(product.getProductType());
						trade.setCurrency(Currency.getSelectedItem().toString());
						trade.setCpID(commonUTIL.converStringToInteger(counterParty.getName()));
						trade.setBookId(commonUTIL.converStringToInteger(book.getName()));
						trade.setTraderID(commonUTIL.converStringToInteger(trader.getName()));
						trade.setAction("NEW");
						trade.setStatus("NONE");
						trade.setPrice(coupon.getFixedRate());
						trade.setUserID(usr.getId());
						trade.setProductType("MM");
						trade.setAttributes(getAttributeValue(attributes));
		               	trade.setFees(feesPanel.getFeesDataV());
		               	trade.setEffectiveDate(product.getIssueDate());
		               	trade.setDelivertyDate(product.getMarturityDate());
		               	trade = saveTrade(message,trade);
		               	if(trade  != null) {
		               		commonUTIL.showAlertMessage("Trade Saved with ID " + trade.getId());
		               		openTrade(trade);
		               	}
						
			} else {
				commonUTIL.showAlertMessage("Trade Product not created ");
			}
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			} 
		
		
	//	trade.sett
		
	

	private void setNewAction(Trade tradeNew) {
		// TODO Auto-generated method stub
		trade = new Trade();
		product = new Product();
		coupon = new Coupon();
		TradeID.setText("0");
		book.setSelectedIndex(-1);
		counterParty.setSelectedIndex(-1);
		trader.setSelectedIndex(-1);
		startDate.setDate(commonUTIL.getCurrentDate());
		endDate.setDate(commonUTIL.getCurrentDate());
		actionC.setSelectedIndex(0);
		status.setText("NONE");
		Amount1.setValue(0.0);
		rateC.setSelectedIndex(0);
		splitRate.setValue(0.0);
		inrEqu.setValue(0.0);
		FixedRate.setValue(0.0);
		rateIndexC.setSelectedIndex(0);
		tenor.setSelectedIndex(0);
		spread.setValue(0.0);
		payFreq.setSelectedIndex(0);
		compFreq.setSelectedIndex(0);
		compMethod.setSelectedIndex(0);
		setCashFlow(getCashFlowTable(), null, "MM");
		
		
		
		
	}
	
	

	@Override
	public void openTrade(Trade trade) {
		// TODO Auto-generated method stub
		if(trade != null) {
			
			 this.trade = trade; 
			 this.product = trade.getProduct();
			 this.coupon = trade.getProduct().getCoupon();
			 setTrade(trade);
			 CashFlowTable.removeAll();
			 attributeDataValue.clear();
			 setAttribute(trade.getAttributes(),attributes,attributeDataValue);
			 setDataFromTradeObject(trade.getCpID(), TradeConstants.COUNTERPARTY,counterParty);
			
			 setDataFromTradeObject(trade.getTraderID(), TradeConstants.TRADER,trader);
			 setDataFromTradeObject(trade.getBookId(), TradeConstants.BOOK,book);
			Currency.setSelectedItem(trade.getCurrency());
			dayCount.setSelectedItem(product.getCoupon().getDayCount());
			 startDate.setDate(commonUTIL.convertStringtoSQLDate(trade.getEffectiveDate()));
			 endDate.setDate(commonUTIL.convertStringtoSQLDate(trade.getDelivertyDate()));
			 status.setText(trade.getStatus());
			 Amount1.setValue(trade.getQuantity());
			 app.openTrade(trade,false);
			 TradeID.setText(String.valueOf(trade.getId()));
			 processActionData(actionstatus,productType,trade.getTradedesc(),trade.getStatus());
			 rateC.setSelectedItem(coupon.getCouponType());
			 FixedRate.setText(String.valueOf(trade.getPrice()));
			 if(rateC.getSelectedItem().toString().equalsIgnoreCase(MMConstants.FLOAT)) {
				    
				    
				    compFreq.setSelectedItem(trade.getProduct().getCoupon().getCouponFrequency());
				    tenor.setSelectedItem(trade.getProduct().getTenor());
					
					spread.setValue(trade.getProduct().getCoupon().getYieldDecimals()); // yieldDecimals treated as spread as don't want to create unneccessary columns. 
					compMethod.setSelectedItem(trade.getProduct().getCoupon().getYieldMethod()); 
					rateType = "Float";
					fixedPanel.setVisible(false);
					flotingPanel.setVisible(true);
					compDPanel.setVisible(false);
					
					
				} 
				if(rateC.getSelectedItem().toString().equalsIgnoreCase("CompD")) {
					rateType = "CompD";
					compDPanel.setVisible(true);
					fixedPanel.setVisible(false);
					flotingPanel.setVisible(false);
					
					
				} 
				if(rateC.getSelectedItem().toString().equalsIgnoreCase("Fixed")) {
					rateType = "Fixed";
					compDPanel.setVisible(false);
					fixedPanel.setVisible(true);
					flotingPanel.setVisible(false);
					
				}
			
		    
		}
		
	}
	private void buildCashFlow() {
		// TODO Auto-generated method stub
		pricing = (MMPricing) getPricer();
		setCashFlow(getCashFlowTable(),(Vector<Flows>) getCashFlows(),"MM");
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
		pricing.price(getTrade(),getTrade().getProduct(), getTrade().getProduct().getCoupon());
		 MMCashFlow cashFlow =   pricing.generateCashFlow();
		return cashFlow.getFlows();
	}

	@Override
	public Pricer getPricer() {
		// TODO Auto-generated method stub
		pricing = new MMPricing();
		
		return pricing;
	}
	
	public void setPricing(MMPricing pricing) {
		pricing = (MMPricing) getPricer();
		
	}
 
public void calculatePrice(MMPricing price,Trade trade,Product product,Coupon coupon) {
		
	price.price(trade, product, coupon);
         MMCashFlow cashFlow = price.generateCashFlow();
         
        // setCashFlows(cashFlow.getFlows());
         setPricing(pricing);
         
       
	}
 
@Override
public ActionMap getHotKeysActionMapper() {
	// TODO Auto-generated method stub
	return actionMap;
}

@Override
public JPanel getHotKeysPanel() {
	// TODO Auto-generated method stub
	return jPanel5;
}
}

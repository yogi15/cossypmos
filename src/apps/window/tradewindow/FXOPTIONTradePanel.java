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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.ActionMap;
import javax.swing.JPanel;

import productPricing.FXPricing;
import productPricing.Pricer;
import util.NumericTextField;
import util.RemoteServiceUtil;
import util.commonUTIL;
import apps.window.tradewindow.FXPanels.TradeAttributesD;
import apps.window.tradewindow.panelWindow.FeesPanel;
import apps.window.tradewindow.panelWindow.SDIPanel;
import apps.window.tradewindow.panelWindow.TaskPanel;
import apps.window.tradewindow.panelWindow.TransferPanel;
import apps.window.tradewindow.util.FXOptionUtil;
import apps.window.tradewindow.util.StaticDataCacheUtil;
import beans.Book;
import beans.Coupon;
import beans.Favorities;
import beans.LegalEntity;
import beans.Product;
import beans.ProductFXOption;
import beans.Trade;
import beans.Users;
import dsEventProcessor.TaskEventProcessor;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
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
import javax.swing.plaf.ActionMapUIResource;
import javax.swing.table.DefaultTableModel;

import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;

import com.jidesoft.combobox.DateComboBox;
import com.jidesoft.combobox.TableExComboBox;

import constants.ProductConstants;


//VS4E -- DO NOT REMOVE THIS LINE!
public class FXOPTIONTradePanel extends TradePanel {

	private static final long serialVersionUID = 1L;
	private JPanel AttributePanel;
	private JPanel jPanel0;
	private JPanel jPanel1;
	private JPanel jPanel2;
	private JPanel jPanel3;
	private JPanel jPanel4;
	private JPanel jPanel5;
	private JLabel jLabel1;
	private JLabel jLabel2;
	private JLabel jLabel3;
	private JPanel jPanel6;
	private JLabel jLabel5;
	private JButton New;
	private JButton Save;
	private JButton SaveAsNew;
	private JButton Delete;
	private JLabel jLabel8;
	private JLabel jLabel9;
	private JRadioButton Rollback;
	private JComboBox Book;
	private JComboBox cp;
	private JComboBox Trader;
	private JTextField TradeID;
	private JComboBox actionC;
	private JTextField status;
	private JTable jTable0;
	private JScrollPane AttributeTable;
	private JLabel jLabel13;
	private JButton jButton4;
	private JButton jButton5;
	private JComboBox optionType;
	private JLabel jLabel0;
	private JLabel jLabel4;
	private JComboBox exeriseType;
	private JLabel jLabel6;
	private JComboBox settlementType;
	private JLabel jLabel10;
	private JLabel jLabel11;
	private JLabel jLabel15;
	private JLabel jLabel17;
	private JLabel jLabel16;
	private com.jidesoft.combobox.DateComboBox exipryDate;
	private JLabel jLabel7;
	private JTextField expiryTime;
	private JComboBox currencyPair;
	private JComboBox NotionalCurrency;
	private JTextField callPut;
	private JLabel jLabel18;
	private JTextField buysell;
	private com.jidesoft.combobox.DateComboBox deliveryDate;
	private JLabel jLabel12;
	private JTextField curr2putcall;
	private JLabel jLabel14;
	private JTextField settlementCCY;
	private JLabel jLabel19;
	private NumericTextField strikePrice;
	private NumericTextField amt1;
	private NumericTextField amt2;
	private JLabel jLabel20;
	private JLabel jLabel21;
	private JPanel jPanel7;
	private JTextField fxRate;
	private JLabel jLabel22;
	private Users usr =null;
	 TradeAttributesD attributes = null;
	private static final String PREFERRED_LOOK_AND_FEEL = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
	String productType = "FX";
	String productSubType = "FXOption";
	 Hashtable<String,String>  attributeDataValue = new Hashtable<String,String>();
	 DefaultTableModel attributeModel = null;
	 ActionMap actionMap =null;
	 Trade trade = null;
	 Vector<String> currencyData = null;
		Vector<LegalEntity> legalEntityData  = null;
		Vector<LegalEntity> traderData  = null;
		Vector<beans.Book> bookData = null;
		TradeApplication app = null;
		 SDIPanel sdiPanel = null;
		 FeesPanel feesPanel = null;
		 TaskPanel taskPanel = null; 
		 TransferPanel transferPanel;
		 public TableExComboBox counterParty = null;
			public TableExComboBox book = null;
			public TableExComboBox trader = null;
			ProductFXOption productFXOption = null;
			DecimalFormat format1 = new DecimalFormat("##,###,#######.######");
			
			javax.swing.DefaultComboBoxModel<String> actionstatus = null;
			Product product = null;
			ProductFXOption fxOptionProduct = null;
			
	public FXOPTIONTradePanel() {
		initComponents();
	}
	public FXOPTIONTradePanel(Users user) {
		setUser(user);
		initComponents();
	}
	private void initComponents() {
		currencyData = StaticDataCacheUtil.getDomainValues("Currency");
		actionstatus = new javax.swing.DefaultComboBoxModel<String>();
		bookData = StaticDataCacheUtil.getUserFavBooks(usr.getId(),Favorities.book);
		traderData = StaticDataCacheUtil.getUserFavTrader(usr.getId(), Favorities.trader);
		legalEntityData = StaticDataCacheUtil.getUserFavLegalEntity(usr.getId(), Favorities.CounterParty);
		
		setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
		setLayout(new GroupLayout());
		add(getJPanel0(), new Constraints(new Leading(1, 866, 12, 12), new Leading(3, 95, 10, 10)));
		add(getJPanel4(),  new Constraints(new Leading(882, 334, 10, 10), new Leading(5, 517, 10, 10)));
		add(getJPanel5(), new Constraints(new Leading(5, 866,10, 10), new Leading(471, 46,10, 10)));
		add(getJPanel3(), new Constraints(new Leading(0, 12, 12), new Leading(338, 121, 12, 12)));
		add(getJPanel1(),  new Constraints(new Leading(1, 866, 8, 8), new Leading(99, 122, 16, 16)));
		add(getJPanel2(), new Constraints(new Leading(3, 866, 12, 12), new Leading(224, 112, 12, 12)));
		add(getJPanel7(), new Constraints(new Leading(5, 712, 12, 12), new Leading(338, 121, 12, 12)));
		//setSize(990, 527);
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
		        //	buildCashFlow();
		        
		        //	commonUTIL.showAlertMessage("Save As new  action performed.");
		        }

				
		    });
		   
		    processTableData(attributeDataValue,attributeModel,attributes); 
		    setBUYSELL(jLabel17,buysell);
	}

	private JLabel getJLabel22() {
		if (jLabel22 == null) {
			jLabel22 = new JLabel();
			jLabel22.setText("FX Rate");
		}
		return jLabel22;
	}

	private JTextField getFxRate() {
		if (fxRate == null) {
			fxRate = new JTextField();
		}
		return fxRate;
	}

	private JPanel getJPanel7() {
		if (jPanel7 == null) {
			jPanel7 = new JPanel();
			jPanel7.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			jPanel7.setLayout(new GroupLayout());
		}
		return jPanel7;
	}

	private JLabel getJLabel21() {
		if (jLabel21 == null) {
			jLabel21 = new JLabel();
			jLabel21.setText("Amt2");
		}
		return jLabel21;
	}

	private JLabel getJLabel20() {
		if (jLabel20 == null) {
			jLabel20 = new JLabel();
			jLabel20.setText("Amt1");
		}
		return jLabel20;
	}

	private JTextField getAmt2() {
		if (amt2 == null) {
			amt2 = new NumericTextField(10,format1);
		}
		return amt2;
	}

	private JTextField getAmt1() {
		if (amt1 == null) {
			amt1 = new NumericTextField(10,format1);
		}
		return amt1;
	}

	private JTextField getStrikePrice() {
		if (strikePrice == null) {
			strikePrice = new NumericTextField(10,format1);
		}
		return strikePrice;
	}

	private JLabel getJLabel19() {
		if (jLabel19 == null) {
			jLabel19 = new JLabel();
			jLabel19.setText("Strike Price");
		}
		return jLabel19;
	}

	private JTextField getSettlementCCY() {
		if (settlementCCY == null) {
			settlementCCY = new JTextField();
			settlementCCY.setEditable(false);
			settlementCCY.setEnabled(false);
		}
		return settlementCCY;
	}

	private JLabel getJLabel14() {
		if (jLabel14 == null) {
			jLabel14 = new JLabel();
			jLabel14.setText("Settlement CCY");
		}
		return jLabel14;
	}

	private JTextField getCurr2putcall() {
		if (curr2putcall == null) {
			curr2putcall = new JTextField();
			curr2putcall.setEditable(false);
			curr2putcall.setEnabled(false);
		}
		return curr2putcall;
	}

	private JLabel getJLabel12() {
		if (jLabel12 == null) {
			jLabel12 = new JLabel();
			jLabel12.setText("CCY2 Put/Call");
		}
		return jLabel12;
	}

	private com.jidesoft.combobox.DateComboBox getDeliveryDate() {
		if (deliveryDate == null) {
			deliveryDate =  new com.jidesoft.combobox.DateComboBox();
			deliveryDate.setFormat(new SimpleDateFormat("dd/MM/yyyy"));
				Calendar currentDate = Calendar.getInstance();
				deliveryDate.setDate(currentDate.getTime());
				deliveryDate.setName("deliveryDate");
		}
		return deliveryDate;
	}

	private JTextField getBuySell() {
		if (buysell == null) {
			buysell = new JTextField();
			buysell.setText("BUY");
			buysell.setBackground(Color.green);
		}
		return buysell;
	}

	private JLabel getJLabel18() {
		if (jLabel18 == null) {
			jLabel18 = new JLabel();
			jLabel18.setText("Call/Put");
		
			jLabel18.addMouseListener(new MouseListener() {

				@Override
				public void mouseClicked(MouseEvent e) {
					String callput = jLabel18.getText();
					String currPair = currencyPair.getSelectedItem().toString();
					FXOptionUtil.setCallPutOnCurrPairChanged(currPair,jLabel18,callPut,jLabel12,curr2putcall);
					
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
		}
		return jLabel18;
	}

	private JTextField getCallPut() {
		if (callPut == null) {
			callPut = new JTextField();
			callPut.setEditable(false);
			callPut.setEnabled(false);
		//	callPut.setText("Call");
			//callPut.setBackground(Color.pink);
		}
		
		return callPut;
	}

	private JComboBox getNotionalCurrency() {
		if (NotionalCurrency == null) {
			NotionalCurrency = new JComboBox();
			NotionalCurrency.setModel(new DefaultComboBoxModel(new Object[] { "item0", "item1", "item2", "item3" }));
		}
		return NotionalCurrency;
	}
	private JComboBox getCurrencyPair() {
		if (currencyPair == null) {
			currencyPair = new JComboBox(getAllCurrencyPair());
			currencyPair.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {

					String currPair = currencyPair.getSelectedItem().toString();
					FXOptionUtil.setCurrPairChanged(currPair,jLabel18,callPut,jLabel12,curr2putcall,settlementCCY);
				
					
				}
			});
		}
		return currencyPair;
	}
	private JTextField getExpiryTime() {
		if (expiryTime == null) {
			expiryTime = new JTextField();
		}
		return expiryTime;
	}

	private JLabel getJLabel7() {
		if (jLabel7 == null) {
			jLabel7 = new JLabel();
			jLabel7.setText("Expiry Date");
		}
		return jLabel7;
	}

	private DateComboBox getExipryDate() {
		if (exipryDate == null) {
			exipryDate =  new com.jidesoft.combobox.DateComboBox();
			exipryDate.setFormat(new SimpleDateFormat("dd/MM/yyyy"));
			Calendar currentDate = Calendar.getInstance();
			exipryDate.setDate(currentDate.getTime());
			exipryDate.setName("exipryDate");
		}
		return exipryDate;
	}

	private JLabel getJLabel16() {
		if (jLabel16 == null) {
			jLabel16 = new JLabel();
			jLabel16.setText("Expiry Time");
		}
		return jLabel16;
	}

	private JLabel getJLabel17() {
		if (jLabel17 == null) {
			jLabel17 = new JLabel();
			jLabel17.setText("BUY/SELL");
		}
		return jLabel17;
	}

	private JLabel getJLabel15() {
		if (jLabel15 == null) {
			jLabel15 = new JLabel();
			jLabel15.setText("Notional Ccy");
		}
		return jLabel15;
	}

	private JLabel getJLabel11() {
		if (jLabel11 == null) {
			jLabel11 = new JLabel();
			jLabel11.setText("CurrPair/Underlying");
		}
		return jLabel11;
	}

	private JLabel getJLabel10() {
		if (jLabel10 == null) {
			jLabel10 = new JLabel();
			jLabel10.setText("DeliveryDate");
		}
		return jLabel10;
	}

	private JComboBox getSettlementType() {
		if (settlementType == null) {

			Vector<String> settlementTypeData = StaticDataCacheUtil.getDomainValues("SettlementType");
			settlementType = new JComboBox(commonUTIL.convertVectortoSringArray(settlementTypeData));
		
		}
		return settlementType;
	}

	private JLabel getJLabel6() {
		if (jLabel6 == null) {
			jLabel6 = new JLabel();
			jLabel6.setText("Settlement Type");
		}
		return jLabel6;
	}

	private JComboBox getExeriseType() {
		if (exeriseType == null) {
			Vector<String> exerciseTypeData = StaticDataCacheUtil.getDomainValues("ExerciseType");
			exeriseType = new JComboBox(commonUTIL.convertVectortoSringArray(exerciseTypeData));
		}
		return exeriseType;
	}

	private JLabel getJLabel4() {
		if (jLabel4 == null) {
			jLabel4 = new JLabel();
			jLabel4.setText("Exercise Type");
		}
		return jLabel4;
	}

	private JLabel getJLabel0() {
		if (jLabel0 == null) {
			jLabel0 = new JLabel();
			jLabel0.setText("StrategyName");
		}
		return jLabel0;
	}

	private JComboBox getOptionType() {
		if (optionType == null) {
			Vector<String> fXOptionStrategy = StaticDataCacheUtil.getDomainValues("FXOptionStrategy");
			optionType = new JComboBox(commonUTIL.convertVectortoSringArray(fXOptionStrategy));
			
		}
		return optionType;
	}

	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jPanel3 = new JPanel();
			jPanel3.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			jPanel3.setLayout(new GroupLayout());
		}
		return jPanel3;
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

	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			jPanel2.setLayout(new GroupLayout());
			jPanel2.add(getJLabel17(), new Constraints(new Leading(4, 10, 10), new Leading(14, 10, 10)));
			jPanel2.add(getJLabel18(), new Constraints(new Leading(147, 10, 10), new Leading(14, 10, 10)));
			jPanel2.add(getCallPut(), new Constraints(new Leading(235, 55, 10, 10), new Leading(12, 12, 12)));
			jPanel2.add(getJLabel12(), new Constraints(new Leading(317, 10, 10), new Leading(12, 12, 12)));
			jPanel2.add(getCurr2putcall(), new Constraints(new Leading(408, 55, 12, 12), new Leading(12, 12, 12)));
			jPanel2.add(getJLabel19(), new Constraints(new Leading(5, 12, 12), new Leading(54, 12, 12)));
			jPanel2.add(getBuySell(), new Constraints(new Leading(76, 55, 12, 12), new Leading(12, 12, 12)));
			jPanel2.add(getFxRate(), new Constraints(new Leading(76, 115, 10, 10), new Leading(82, 12, 12)));
			jPanel2.add(getJLabel22(), new Constraints(new Leading(7, 10, 10), new Leading(85, 12, 12)));
			jPanel2.add(getStrikePrice(), new Constraints(new Leading(74, 122, 10, 10), new Leading(46, 25, 12, 12)));
			jPanel2.add(getJLabel20(), new Constraints(new Leading(208, 12, 12), new Leading(52, 12, 12)));
			jPanel2.add(getAmt1(), new Constraints(new Leading(247, 152, 10, 10), new Leading(48, 25, 12, 12)));
			jPanel2.add(getJLabel21(), new Constraints(new Leading(420, 12, 12), new Leading(54, 12, 12)));
			jPanel2.add(getAmt2(), new Constraints(new Leading(466, 152, 10, 10), new Leading(51, 25, 12, 12)));
			jPanel2.add(getJLabel14(), new Constraints(new Leading(488, 10, 10), new Leading(15, 19, 12, 12)));
			jPanel2.add(getSettlementCCY(), new Constraints(new Leading(593, 77, 10, 10), new Leading(14, 12, 12)));
		}
		return jPanel2;
	}

	private JLabel getJLabel13() {
		if (jLabel13 == null) {
			jLabel13 = new JLabel();
			jLabel13.setText("INR Equi");
		}
		return jLabel13;
	}

	private JScrollPane getJScrollPane0() {
		if (AttributeTable == null) {
			AttributeTable = new JScrollPane();
			AttributeTable.setViewportView(getJTable0());
		}
		return AttributeTable;
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

	private JTextField getStatus() {
		if (status == null) {
			status = new JTextField();
			status.setBackground(new Color(128, 255, 255));
			status.setEditable(false);
			status.setText("NONE");
		}
		return status;
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


	private TableExComboBox getCP() {
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
			SaveAsNew.setText("SaveAsNew");
		}
		return SaveAsNew;
	}

	private JButton getJButton1() {
		if (Save == null) {
			Save = new JButton();
			Save.setText("Save");
		}
		return Save;
	}

	private JButton getJButton0() {
		if (New == null) {
			New = new JButton();
			New.setText("NEW");
		}
		return New;
	}

	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			jPanel1.setLayout(new GroupLayout());
			jPanel1.add(getJLabel7(), new Constraints(new Leading(6, 12, 12), new Leading(43, 12, 12)));
			jPanel1.add(getJLabel11(), new Constraints(new Leading(6, 12, 12), new Leading(83, 12, 12)));
			jPanel1.add(getCurrencyPair(), new Constraints(new Leading(126, 89, 10, 10), new Leading(77, 26, 12, 12)));
			jPanel1.add(getOptionType(), new Constraints(new Leading(99, 119, 10, 10), new Leading(5, 25, 12, 12)));
			jPanel1.add(getJLabel4(), new Constraints(new Leading(224, 10, 10), new Leading(9, 12, 12)));
			jPanel1.add(getExeriseType(), new Constraints(new Leading(309, 151, 10, 10), new Leading(5, 25, 12, 12)));
			jPanel1.add(getJLabel6(), new Constraints(new Leading(466, 10, 10), new Leading(12, 12, 12)));
			jPanel1.add(getSettlementType(), new Constraints(new Leading(568, 132, 10, 10), new Leading(5, 25, 12, 12)));
			jPanel1.add(getJLabel0(), new Constraints(new Leading(4, 92, 10, 10), new Leading(9, 12, 12)));
			jPanel1.add(getExpiryTime(), new Constraints(new Leading(571, 126, 12, 12), new Leading(43, 12, 12)));
			jPanel1.add(getExipryDate(), new Constraints(new Leading(100, 117, 12, 12), new Leading(43, 12, 12)));
			jPanel1.add(getDeliveryDate(), new Constraints(new Leading(312, 140, 12, 12), new Leading(43, 12, 12)));
			jPanel1.add(getNotionalCurrency(), new Constraints(new Leading(312, 68, 12, 12), new Leading(79, 26, 12, 12)));
			jPanel1.add(getJLabel15(), new Constraints(new Leading(224, 12, 12), new Leading(80, 23, 12, 12)));
			jPanel1.add(getJLabel10(), new Constraints(new Leading(224, 12, 12), new Leading(43, 12, 12)));
			jPanel1.add(getJLabel16(), new Constraints(new Leading(470, 12, 12), new Leading(43, 12, 12)));
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
			jPanel0.add(getBook(), new Constraints(new Leading(67, 138, 10, 10), new Leading(44, 26, 10, 12)));
			jPanel0.add(getJLabel5(), new Constraints(new Leading(7, 10, 10), new Leading(12, 12, 12)));
			jPanel0.add(getJLabel8(), new Constraints(new Leading(483, 10, 10), new Leading(12, 12, 12)));
			jPanel0.add(getStatus(), new Constraints(new Leading(535, 170, 12, 12), new Leading(5, 26, 10, 51)));
			jPanel0.add(getCP(), new Constraints(new Leading(304, 175, 12, 12), new Leading(44, 26, 10, 12)));
			jPanel0.add(getJLabel2(), new Constraints(new Leading(211, 12, 12), new Leading(49, 12, 12)));
			jPanel0.add(getActionC(), new Constraints(new Leading(304, 174, 12, 12), new Leading(5, 26, 10, 51)));
			jPanel0.add(getJLabel3(), new Constraints(new Leading(485, 41, 12, 12), new Leading(49, 25, 12, 12)));
			jPanel0.add(getTradeID(), new Constraints(new Leading(67, 138, 12, 12), new Leading(7, 26, 40, 40)));
			jPanel0.add(getJLabel9(), new Constraints(new Leading(214, 10, 10), new Leading(10, 12, 12)));
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

	private JPanel getJPanel5() {
		if (jPanel5 == null) {
			jPanel5 = new JPanel();
			jPanel5.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			jPanel5.setLayout(new GroupLayout());
			jPanel5.add(getJButton0(), new Constraints(new Leading(19, 10, 10), new Leading(8, 10, 10)));
			jPanel5.add(getJButton1(), new Constraints(new Leading(98, 10, 10), new Leading(8, 12, 12)));
			jPanel5.add(getJButton2(), new Constraints(new Leading(179, 10, 10), new Leading(8, 12, 12)));
			jPanel5.add(getJButton3(), new Constraints(new Leading(301, 10, 10), new Leading(8, 12, 12)));
			jPanel5.add(getJButton4(), new Constraints(new Leading(388, 12, 12), new Leading(8, 12, 12)));
			jPanel5.add(getJButton5(), new Constraints(new Leading(489, 10, 10), new Leading(8, 12, 12)));
		}
		return jPanel5;
	}

	private JPanel getJPanel4() {
		if (attributes == null) {
			attributes = new TradeAttributesD();
			attributes.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			//attributes.setLayout(new GroupLayout());
			
		}
		return attributes;
	}

	private Component getAttributePanel() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setTaskPanel(BackOfficePanel panel) {
		// TODO Auto-generated method stub
		this.taskPanel = taskPanel;
	}
	public SDIPanel getSdiPanel() {
		return sdiPanel;
	}
	public TaskPanel getTaskPanel() {
		return taskPanel;
	}
	public FeesPanel getFeesPanel() {
		return feesPanel;
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
	public void setTradeApplication(TradeApplication app) {
		// TODO Auto-generated method stub
		this.app = app;
	}

	@Override
	public void setTradeTransfers(BackOfficePanel panel) {
		// TODO Auto-generated method stub
		transferPanel = (TransferPanel) panel;
		
	}

	@Override
	public void setTradePostings(BackOfficePanel panel) {
		// TODO Auto-generated method stub
		
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
		this.usr  =user;
		
	}

	@Override
	public String getAction() {
		// TODO Auto-generated method stub
		return null;
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

	private boolean validateProductData() {
		boolean flag = false;
		
		if(currencyPair.getSelectedIndex() == -1) {
			commonUTIL.showAlertMessage("Select CurrencyPair");
			return false;
		}
		String currPair = currencyPair.getSelectedItem().toString();
		String exDate = commonUTIL.convertDateTimeTOString(exipryDate.getDate());
		if(commonUTIL.isEmpty(exDate)) {
			commonUTIL.showAlertMessage("Select Expiry Date");
			return false;
		}
		String delDate = commonUTIL.convertDateTimeTOString(deliveryDate.getDate());
		if(commonUTIL.isEmpty(delDate)) {
			commonUTIL.showAlertMessage("Select Delivery Date");
			return false;
		}
		String settlementCurr = settlementCCY.getText();
		String baseCurr =currPair.substring(0, 3);
		String quoteCurr = currPair.substring(4, 7);
		String optionStrategy = optionType.getSelectedItem().toString();
		if(commonUTIL.isEmpty(optionStrategy)) {
			commonUTIL.showAlertMessage("Select Option Strategy");
			return false;
		}
		String execriseTy = exeriseType.getSelectedItem().toString();
		if(commonUTIL.isEmpty(execriseTy)) {
			commonUTIL.showAlertMessage("Select Execerise Type");
			return false;
		}
		String settleType = settlementType.getSelectedItem().toString();
		if(commonUTIL.isEmpty(settleType)) {
			commonUTIL.showAlertMessage("Select Settlement Type");
			return false;
		}
		
		if(commonUTIL.isEmpty(strikePrice.getText())) {
			commonUTIL.showAlertMessage("Enter Strike Price");
			return false;
		}
		double strickePrice = commonUTIL.converStringToDouble(strikePrice.getText());
		double amt1d = commonUTIL.converStringToDouble(amt1.getText());
		double amt2d = commonUTIL.converStringToDouble(amt2.getText());
		String buySell = buysell.getText();
		String callput = callPut.getText();
		double marketRate =  commonUTIL.converStringToDouble(fxRate.getText());
		double quantity = 0.0;
		
		  product = new Product();
		product.setUnderlyingProductType(currPair);
		product.setProductType("FX");
		product.setPositionBased(true);
		product.setMarturityDate(delDate);
		product.setQuantity(quantity);
		product.setCurrency(settlementCurr);
		product.setProductSubType("FXOption");
		product.setProdcutShortName("FXOption");
		
		fxOptionProduct = new ProductFXOption();
		fxOptionProduct.setCurrencyBase(baseCurr);
		fxOptionProduct.setCurrencyQuote(quoteCurr);
		fxOptionProduct.setExericseType(execriseTy);
		fxOptionProduct.setOptionStrike(strickePrice);
		fxOptionProduct.setCurrency(settlementCurr);
		fxOptionProduct.setSettlementType(settleType);
		fxOptionProduct.setMarturityDate(delDate);
	//	fxOptionBean.setExercised_DATE(exercised_DATE); // how to assign it. 
		fxOptionProduct.setExpiryDate(exDate);
		fxOptionProduct.setOptionStyle(optionStrategy);
		fxOptionProduct.setOptionType(callput);
		fxOptionProduct.setPrimaryAmount(amt1d);
		fxOptionProduct.setQuotingAmount(amt2d);
		fxOptionProduct.setSettleDate(delDate);
		fxOptionProduct.setSpotDate(commonUTIL.getCurrentDateInString());
		
		
		 product.setFXOptionProduct(fxOptionProduct);
	    
		
		
				
		
		return flag;
	}

	@Override
	public void processTask(TaskEventProcessor taskEvent, Users WindowUser) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLimitBreachMarkOnAction(int i) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void buildTrade(Trade trade, String actionType) {
		// TODO Auto-generated method stub
		if(actionType.equalsIgnoreCase("NEW")) {
			setNewAction(trade);
		}
		if(actionType.equalsIgnoreCase("saveAsNew")) {
			product = new Product();
			
			actionC.setSelectedItem("NEW");
			actionC.getModel().setSelectedItem("NEW");
			if(validateProductData() && validateTradeData()) {
				setSaveAsNew(trade);
			}
		}
		if(actionType.equalsIgnoreCase("save")) {
			if(validateProductData() && validateTradeData()) {
				//setSave(trade);
			}
		} 
		
	}

	@Override
	public void openTrade(Trade trade) {
		// TODO Auto-generated method stub
		
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
	
	private boolean validateTradeData() {
		boolean flag = false;
		String act = actionC.getSelectedItem().toString();
		if(!actionC.getSelectedItem().toString().equalsIgnoreCase("NEW")) {
			if(actionC.getSelectedIndex() ==  -1) {
				commonUTIL.showAlertMessage("Select Action");
				return flag;
			}
		}
		if(commonUTIL.isEmpty(((String)currencyPair.getSelectedItem())))  {
		    commonUTIL.showAlertMessage("Select Currency Pair");
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
		if (deliveryDate.getDate().after(exipryDate.getDate())){
    		commonUTIL.showAlertMessage("Maturity date cannot be before Expiry end date");
    		return flag;	
    	}
			flag = true;
		return flag;
	}
	private void setSaveAsNew(Trade trade2) {
		// TODO Auto-generated method stub
		trade = new Trade();
		trade.setQuantity(product.getQuantity());
		trade.setNominal(0);
		trade.setType(buysell.getText());
		 product.setId(0);
			try {
				int productId  = RemoteServiceUtil.getRemoteProductService().saveProduct(product);
				  product.setId(productId);
			
				  if(product.getId() >0) {
					  Vector<String> message = new Vector<String>();
						trade.setProductId(product.getId());
						trade.setTradedesc(product.getUnderlyingProductType());
						trade.setTradedesc1(product.getProductSubType());
						trade.setCurrency(settlementCCY.getText());
						trade.setCpID(commonUTIL.converStringToInteger(counterParty.getName()));
						trade.setBookId(commonUTIL.converStringToInteger(book.getName()));
						trade.setTraderID(commonUTIL.converStringToInteger(trader.getName()));
						trade.setAction("NEW");
						trade.setStatus("NONE");
						trade.setPrice(Double.valueOf(fxRate.getText()));
						trade.setUserID(usr.getId());
						trade.setProductType(product.getProductType());
						trade.setAttributes(getAttributeValue(attributes));
		               	trade.setFees(feesPanel.getFeesDataV());
		               	//trade.setEffectiveDate(product.getIssueDate());
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
	private void setNewAction(Trade tradeNew) {
		// TODO Auto-generated method stub
		trade = new Trade();
		product = new Product();
		fxOptionProduct = new ProductFXOption();
		TradeID.setText("0");
		book.setSelectedIndex(-1);
		counterParty.setSelectedIndex(-1);
		trader.setSelectedIndex(-1);
		exipryDate.setDate(commonUTIL.getCurrentDate());
		deliveryDate.setDate(commonUTIL.getCurrentDate());
		actionC.setSelectedIndex(0);
		status.setText("NONE");
		amt1.setValue(0.0);
		amt2.setValue(0.0);
		optionType.setSelectedIndex(0);
		exeriseType.setSelectedIndex(0);
		settlementType.setSelectedIndex(0);
		currencyPair.setSelectedIndex(-1);
		settlementCCY.setText("");
		buysell.setText("BUY");
		callPut.setText("");
		jLabel18.setText("Call/Put");
		jLabel12.setText("CCY2 Put/Call");
		curr2putcall.setText("");
		
		
		
	}
	@Override
	public ArrayList<Component> getFocusOrderList() {
		// TODO Auto-generated method stub
		return null;
	}

}

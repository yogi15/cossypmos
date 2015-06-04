package apps.window.tradewindow;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
import java.util.Iterator;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.plaf.ActionMapUIResource;
import javax.swing.table.DefaultTableModel;

import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;

import productPricing.BONDPricing;
import productPricing.BONDZCPricing;
import productPricing.Pricer;
import productPricing.pricingUtil.BondCashFlow;
import util.NumericTextField;
import util.RemoteServiceUtil;
import util.commonUTIL;
import util.common.DateU;
import apps.window.tradewindow.FXPanels.TradeAttributesD;
import apps.window.tradewindow.panelWindow.FeesPanel;
import apps.window.tradewindow.panelWindow.SDIPanel;
import apps.window.tradewindow.panelWindow.TaskPanel;
import apps.window.tradewindow.panelWindow.TransferPanel;
import apps.window.tradewindow.util.StaticDataCacheUtil;
import beans.Book; 
import beans.Coupon;
import beans.Favorities;
import beans.Flows;
import beans.LegalEntity;
import beans.Product;
import beans.Trade;
import beans.Users;

import com.jidesoft.combobox.TableExComboBox;

import constants.CommonConstants;
import constants.ProductConstants;
import constants.TradeConstants;
import dsEventProcessor.TaskEventProcessor;

//VS4E -- DO NOT REMOVE THIS LINE!
public class BONDTradePanel  extends TradePanel {

	private static final long serialVersionUID = 1L;
	private JPanel jPanel0;
	private JPanel jPanel1;
	private JPanel jPanel2;
	private JPanel jPanel3;
	 TradeAttributesD attributes = null;
	private JPanel jPanel5;
	private JLabel jLabel1;
	private JLabel jLabel2;
	private JLabel jLabel3;
	private JLabel jLabel5;
	private JButton New;
	private JButton Save;
	private JButton SaveAsNew;
	private JButton Delete;
	private JLabel jLabel8;
	private JLabel jLabel9;
	private JTextField TradeID;
	private NumericTextField Amount1;
	private JTextField status;
	private JTable CashFlowTable;
	private JScrollPane jScrollPane1;
	private JButton jButton4;
	private JButton jButton5;
	private JComboBox actionC;
	private JLabel jLabel18;
	private JLabel RateIndexjLabel21;
	
	private JLabel tradeDatejLabel7;
	private com.jidesoft.combobox.DateComboBox tradeDate;
	private com.jidesoft.combobox.DateComboBox settleDate;
	private JLabel settleDatejLabel0;
	private JLabel pricejLabel4;
	private NumericTextField priceText;
	private JLabel BUYSELLJLabel6;
	private JTextField buySelltext;
	private JTextField  rateType;
	private JTextField   rateIndex;
	private JLabel tradeCurrJLabel10jLabel10;
	

	private JComboBox currency;
	private JLabel amountjLabel11;
	private NumericTextField amount;
	private JComboBox underlying;
	private JLabel underlyingjLabel;
	private JButton searchProduct;
	
	private JLabel PrevCPNjLabel14;
	private JTextField previousCoupon;
	private JLabel NextCPNjLabel15;
	private JTextField nextCoupon;
	private JLabel MaturityDatejLabel16;
	private JTextField maturityDate;
	private JLabel AccruedDaysjLabel17;
	private JTextField accruedDays;
	private JLabel AccuredIntjLabel18;
	private NumericTextField accuredInt;
	private JLabel SettlementAMTjLabel19;
	private NumericTextField settlementAMT;
	private JLabel QTYjLabel20;
	private NumericTextField quantity;
	private JLabel DayCountjLabel21;
	private JTextField daycount;
	private JLabel BDCjLabel22;
	private JTextField bdcText;
	private JLabel RatejLabel23;
	private JLabel  RateTypejLabel22;
	private NumericTextField rate;
	
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
	Trade trade = null;
	Product product = null;
	Coupon coupon = null;
	DecimalFormat format1 = new DecimalFormat("##,###,#######.######");
	JTextField freq = null;
	javax.swing.DefaultComboBoxModel<String> actionstatus = null;
	String productType = "BOND";
	String productSubType = "";
	 Hashtable<String,String>  attributeDataValue = new Hashtable<String,String>();
	 DefaultTableModel attributeModel = null;
	 BONDPricing pricing =  new BONDPricing();
		JLabel FREQjLabel23 = null;
	 ActionMap actionMap =null;
Users usr = null;

javax.swing.DefaultComboBoxModel productData = null; 
Hashtable productIDs = new Hashtable();
	private static final String PREFERRED_LOOK_AND_FEEL = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
	
	public BONDTradePanel(Users user) {
		setUser(user);
		init();
		initComponents();
	
	}
	//public MMTradePanel() {
	//	initComponents();
//	}

	private void init() {
		// TODO Auto-generated method stub
		productData = new DefaultComboBoxModel<>();
	     processProductData(productData,productIDs);
	}

	private void processProductData(DefaultComboBoxModel underlyingData2,
			Hashtable productIDs2) {
		// TODO Auto-generated method stub
	
			Vector vector;
			 
				
				vector = StaticDataCacheUtil.getProductDefinationData();
				 
				Iterator it = vector.iterator();
		    	int i =0;
		    	while(it.hasNext()) {
	    		Product product = (Product) it.next();
	    		underlyingData2.insertElementAt(product.getProductname(), i);
	    		productIDs2.put(i, product);
	    		i++;
	    	}	
			 
	    	
	    	
	    }
		
	

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
		add(getJPanel1(), new Constraints(new Leading(1, 866, 8, 8), new Leading(99, 112, 16, 16))); // business 
		add(getJPanel3(), new Constraints(new Leading(0, 866, 12, 12), new Leading(324, 151, 12, 12))); // cashflow
		add(getJPanel0(), new Constraints(new Leading(1, 866, 12, 12), new Leading(3, 95, 10, 10)));  // trade main common fields
		add(getHotKeysPanels(), new Constraints(new Leading(5, 866,10, 10), new Leading(481, 46,10, 10))); // buttons
		add(getJPanel4(), new Constraints(new Leading(882, 334, 10, 10), new Leading(5, 517, 10, 10))); // attributes
		add(getJPanel2(), new Constraints(new Leading(3, 866, 10, 10), new Leading(214, 110, 10, 10))); // bussiness
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
		    setCashFlow(getCashFlowTable(), null, "BOND"); // for empty Cash Flow

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
			jPanel2.add(getPrevCPNjLabel14(), new Constraints(new Leading(5, 10, 10), new Leading(7, 10, 10)));
			jPanel2.add(AccruedDaysjLabel17(), new Constraints(new Leading(2, 12, 12), new Leading(46, 12, 12)));
			jPanel2.add(getPreviousCoupon(), new Constraints(new Leading(70, 80, 8, 8), new Leading(2, 25, 8,8)));
			jPanel2.add(getAccruedDays(), new Constraints(new Leading(78, 50, 10, 10), new Leading(37, 25, 12, 12)));
			jPanel2.add(getNextCPNjLabel15JLabel15(), new Constraints(new Leading(155, 12, 12), new Leading(7, 12, 12)));
			jPanel2.add(getNextCoupon(), new Constraints(new Leading(212, 80, 10, 10), new Leading(2, 25, 12, 12)));
			jPanel2.add(getMaturityDatejLabel16(), new Constraints(new Leading(300, 10, 10), new Leading(4, 12, 12)));
			jPanel2.add(getFREQjLabel23(), new Constraints(new Leading(608, 10, 10), new Leading(4, 12, 12)));
			jPanel2.add(getDayCountjLabel21(), new Constraints(new Leading(463, 10, 10), new Leading(4, 12, 12)));
			jPanel2.add(getDaycount(), new Constraints(new Leading(526, 80, 10, 10), new Leading(2, 25, 12, 12)));
			jPanel2.add(getMaturityDate(), new Constraints(new Leading(380, 80, 10, 10), new Leading(2, 25, 12, 12)));
			jPanel2.add(getFREQ(), new Constraints(new Leading(645, 80, 10, 10), new Leading(2, 25, 12, 12)));
			jPanel2.add(getBDCjLabel22(), new Constraints(new Leading(726, 80, 10, 10), new Leading(2, 25, 12, 12)));
			jPanel2.add(getBdcText(), new Constraints(new Leading(760, 100, 10, 10), new Leading(2, 25, 12, 12)));
			jPanel2.add(getAccuredIntjLabel18(), new Constraints(new Leading(220, 12, 12), new Leading(41, 12, 12)));
			jPanel2.add(getAccuredInt(), new Constraints(new Leading(288, 130, 10, 10), new Leading(36, 25, 12, 12)));
			jPanel2.add(getSettlementAMTJLabel19(), new Constraints(new Leading(424, 90, 10, 10), new Leading(41, 12, 12)));
			jPanel2.add(getAccredAmtjLabel22(), new Constraints(new Leading(653, 90, 10, 10), new Leading(41, 12, 12)));
			jPanel2.add(getSettlementAMT(), new Constraints(new Leading(516, 130, 12, 12), new Leading(38, 25, 12, 12)));
			jPanel2.add(getAccrAmtText(), new Constraints(new Leading(726, 120, 12, 12), new Leading(38, 25, 12, 12)));
			jPanel2.add(getQTYJLabel20(), new Constraints(new Leading(2, 73, 12, 12), new Leading(80, 12, 12)));
			jPanel2.add(getQuantity(), new Constraints(new Leading(38, 130, 10, 10), new Leading(74, 25, 12, 12)));
			jPanel2.add(getRateIndexjLabel21(), new Constraints(new Leading(170, 73, 12, 12), new Leading(80, 12, 12)));
			jPanel2.add(getRateTypejLabel22(), new Constraints(new Leading(301, 12, 12), new Leading(83, 12, 12)));
			jPanel2.add(getRateTypeext(), new Constraints(new Leading(364, 60, 10, 10), new Leading(75, 25, 12, 12)));
			jPanel2.add(getRatejLabel23(), new Constraints(new Leading(428, 33, 10, 10), new Leading(83, 12, 12)));
			jPanel2.add(getRateIndexext(), new Constraints(new Leading(230, 70, 10, 10), new Leading(75, 25, 12, 12)));
			jPanel2.add(getRate(), new Constraints(new Leading(455, 74, 10, 10), new Leading(77, 25, 12, 12)));	 
			jPanel2.add(getCleanPricejLabel23(), new Constraints(new Leading(529, 130, 10, 10), new Leading(75, 25, 12, 12)));
			jPanel2.add(getCleanPrice(), new Constraints(new Leading(590, 100, 10, 10), new Leading(77, 25, 12, 12)));	 
			jPanel2.add(getDirtyPricejLabel23(), new Constraints(new Leading(690, 130, 10, 10), new Leading(75, 25, 12, 12)));
			jPanel2.add(getDirtyPrice(), new Constraints(new Leading(753, 100, 10, 10), new Leading(77, 25, 12, 12)));	 
			
		}
		return jPanel2;
	}
	
	private JTextField getFREQ() {
		// TODO Auto-generated method stub
		if (freq == null) {
			freq = new JTextField();
			freq.setEditable(false);
			freq.setEnabled(false);
		}
		return freq;
	}
	JTextField CleanPrice = null;
	private JTextField getCleanPrice() {
		// TODO Auto-generated method stub
		if (CleanPrice == null) {
			CleanPrice = new JTextField();
			CleanPrice.setEditable(false);
			CleanPrice.setEnabled(false);
		}
		return CleanPrice;
	}
	JTextField dirtyPrice = null;
	private JTextField getDirtyPrice() {
		// TODO Auto-generated method stub
		if (dirtyPrice == null) {
			dirtyPrice = new JTextField();
			dirtyPrice.setEditable(false);
			dirtyPrice.setEnabled(false);
		}
		return dirtyPrice;
	}
	private JTextField getRateTypeext() {
		// TODO Auto-generated method stub
		if (rateType == null) {
			rateType = new JTextField();
			rateType.setEditable(false);
			rateType.setEnabled(false);
		}
		return rateType;
	}
	private JTextField getRateIndexext() {
		// TODO Auto-generated method stub
		if (rateIndex == null) {
			rateIndex = new JTextField();
			rateIndex.setEditable(false);
			rateIndex.setEnabled(false);
			
		}
		return rateIndex;
	}

	private JLabel getRateTypejLabel22() {
		// TODO Auto-generated method stub
		if (RateTypejLabel22 == null) {
			RateTypejLabel22 = new JLabel();
			RateTypejLabel22.setText("RateType");
		}
		return RateTypejLabel22;
	}

	private JTextField getRate() {
		if (rate == null) {
			rate = new NumericTextField();
			rate.setEditable(false);
			rate.setEnabled(false);
		}
		return rate;
	}

	private JLabel getRatejLabel23() {
		if (RatejLabel23 == null) {
			RatejLabel23 = new JLabel();
			RatejLabel23.setText("Rate");
		}
		return RatejLabel23;
	}
	private JLabel getFREQjLabel23() {
		if (FREQjLabel23 == null) {
			FREQjLabel23 = new JLabel();
			FREQjLabel23.setText("FREQ");
		}
		return FREQjLabel23;
	}
	JLabel CleanPricejLabel23 = null;
	private JLabel getCleanPricejLabel23() {
		if (CleanPricejLabel23 == null) {
			CleanPricejLabel23 = new JLabel();
			CleanPricejLabel23.setText("CleanPrice");
		}
		return CleanPricejLabel23;
	}
	JLabel DirtyPricejLabel23 = null;
	private JLabel getDirtyPricejLabel23() {
		if (DirtyPricejLabel23 == null) {
			DirtyPricejLabel23 = new JLabel();
			DirtyPricejLabel23.setText("DirtyPrice");
		}
		return DirtyPricejLabel23;
	}
	private JTextField getBdcText() {
		if (bdcText == null) {
			bdcText = new JTextField();
			bdcText.setEditable(false);
			bdcText.setEnabled(false);
		}
		return bdcText;
	}
	NumericTextField AccrAmtText = null;
	private NumericTextField getAccrAmtText() {
		if (AccrAmtText == null) {
			AccrAmtText = new NumericTextField();
			AccrAmtText.setEditable(false);
			AccrAmtText.setEnabled(false);
		}
		return AccrAmtText;
	}

	private JLabel getBDCjLabel22() {
		if (BDCjLabel22 == null) {
			BDCjLabel22 = new JLabel();
			BDCjLabel22.setText("BDC");
			
		}
		return BDCjLabel22;
	}
	JLabel AccredAmtjLabel22 = null;
	private JLabel getAccredAmtjLabel22() {
		if (AccredAmtjLabel22 == null) {
			AccredAmtjLabel22 = new JLabel();
			AccredAmtjLabel22.setText("Accr. Amt");
			
		}
		return AccredAmtjLabel22;
	}
	private JTextField getDaycount() {
		if (daycount == null) {
			daycount = new JTextField();

			daycount.setEditable(false);
			daycount.setEnabled(false);
		}
		return daycount;
	}

	private JLabel getDayCountjLabel21() {
		if (DayCountjLabel21 == null) {
			DayCountjLabel21 = new JLabel();
			DayCountjLabel21.setText("DayCount");
		}
		return DayCountjLabel21;
	}
	private JLabel getRateIndexjLabel21() {
		if (RateIndexjLabel21 == null) {
			RateIndexjLabel21 = new JLabel();
			RateIndexjLabel21.setText("RateIndex");
		}
		return RateIndexjLabel21;
	}
	private JTextField getQuantity() {
		if (quantity == null) {
			quantity = new NumericTextField();
			quantity.setEditable(false);
			quantity.setEnabled(false);
		}
		return quantity;
	}

	private JLabel getQTYJLabel20() {
		if (QTYjLabel20 == null) {
			QTYjLabel20 = new JLabel();
			QTYjLabel20.setText("QTY");
		}
		return QTYjLabel20;
	}

	private NumericTextField getSettlementAMT() {
		if (settlementAMT == null) {
			settlementAMT = new NumericTextField();
			settlementAMT.setEditable(false);
			settlementAMT.setEnabled(false);
		}
		return settlementAMT;
	}

	private JLabel getSettlementAMTJLabel19() {
		if (SettlementAMTjLabel19 == null) {
			SettlementAMTjLabel19 = new JLabel();
			SettlementAMTjLabel19.setText("SettlementAMT");
		}
		return SettlementAMTjLabel19;
	}

	private NumericTextField getAccuredInt() {
		if (accuredInt == null) {
			accuredInt = new NumericTextField();
			accuredInt.setEditable(false);
			accuredInt.setEnabled(false);
		}
		return accuredInt;
	}

	private JLabel getAccuredIntjLabel18() {
		if (AccuredIntjLabel18 == null) {
			AccuredIntjLabel18 = new JLabel();
			AccuredIntjLabel18.setText("Accured Int");
		}
		return AccuredIntjLabel18;
	}

	private JTextField getAccruedDays() {
		if (accruedDays == null) {
			accruedDays = new JTextField();
			accruedDays.setEditable(false);
			accruedDays.setEnabled(false);
		}
		return accruedDays;
	}

	private JLabel AccruedDaysjLabel17() {
		if (AccruedDaysjLabel17 == null) {
			AccruedDaysjLabel17 = new JLabel();
			AccruedDaysjLabel17.setText("AccruedDays");
		}
		return AccruedDaysjLabel17;
	}

	private JTextField getMaturityDate() {
		if (maturityDate == null) {
			 
			maturityDate = new JTextField();
			maturityDate.setEditable(false);
			maturityDate.setEnabled(false);
			 
		}
		return maturityDate;
	}

	private JLabel getMaturityDatejLabel16() {
		if (MaturityDatejLabel16 == null) {
			MaturityDatejLabel16 = new JLabel();
			MaturityDatejLabel16.setText("MaturityDate");
		}
		return MaturityDatejLabel16;
	}

	private JTextField getNextCoupon() {
		if (nextCoupon == null) {
			nextCoupon = new JTextField();
			nextCoupon.setEditable(false);
			nextCoupon.setEnabled(false);
			 
		}
		return nextCoupon;
	}

	private JLabel getNextCPNjLabel15JLabel15() {
		if (NextCPNjLabel15 == null) {
			NextCPNjLabel15 = new JLabel();
			NextCPNjLabel15.setText("Next CPN");
		}
		return NextCPNjLabel15;
	}

	private JTextField getPreviousCoupon() {
		if (previousCoupon == null) {
			previousCoupon = new JTextField();
			previousCoupon.setEditable(false);
			previousCoupon.setEnabled(false);
		}
		return previousCoupon;
	}

	private JLabel getPrevCPNjLabel14() {
		if (PrevCPNjLabel14 == null) {
			PrevCPNjLabel14 = new JLabel();
			PrevCPNjLabel14.setText("Prev. CPN");
		}
		return PrevCPNjLabel14;
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
			Amount1.addKeyListener(new KeyAdapter() {

	            
	            public void keyTyped(KeyEvent e) {
	                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
	                //	trade.getTradeAmount() / product.getFaceValue();
	                  try {
						double amt  =Amount1.getDoubleValue();
						if(product == null) {
							commonUTIL.showAlertMessage("Select Product");
							return;
						}
						//String value = new Double(amt/   productWindowpanel.product.getFaceValue()).toString();
						quantity.setValue(amt/    product.getFaceValue());
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
					   commonUTIL.displayError("BONDTradePanel", "getNominal", e1);
					}
	                  
	                
	                }
	            }
	            });
			Amount1.addFocusListener(new FocusListener() {
				
				@Override
				public void focusLost(FocusEvent arg0) {
					try {
						double amt  =Amount1.getDoubleValue();
						if(product == null) {
							commonUTIL.showAlertMessage("Select Product");
							return;
						}
						//String value = new Double(amt/   productWindowpanel.product.getFaceValue()).toString();
						quantity.setValue(amt/    product.getFaceValue());
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
					   commonUTIL.displayError("BONDTradePanel", "getNominal", e1);
					}
				}

				@Override
				public void focusGained(FocusEvent arg0) {
					// TODO Auto-generated method stub
					
				}
			});
		}
		return Amount1;
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
					if( leid == -1)
						return;
					LegalEntity le = traderData.get(leid);
					trader.setName(String.valueOf(((le)).getId()));
					//trader.getModel().setSelectedItem(leid);
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
					if( leid == -1)
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
					if( leid == -1)
						return;
					Book boo = bookData.get(leid);
					book.setName(String.valueOf(((boo)).getBookno()));
					//System.out.println(counterPary.getName());
					
				}
			});
			
		}
			return book;
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
			jPanel1.add(getTradeDateJLabel7(), new Constraints(new Leading(4, 10, 10), new Leading(20, 10, 10)));
			jPanel1.add(getSettleDateJLabel0(), new Constraints(new Leading(222, 12, 12), new Leading(20, 10, 10)));
			jPanel1.add(getSettleDate(), new Constraints(new Leading(303, 167, 12, 12), new Leading(9, 25, 12, 12)));
			jPanel1.add(getPriceJLabel4(), new Constraints(new Leading(482, 12, 12), new Leading(14, 12, 12)));
			jPanel1.add(getPriceText(), new Constraints(new Leading(528, 150, 10, 10), new Leading(9, 25, 12, 12)));
			jPanel1.add(getTradeCurrJLabel10(), new Constraints(new Leading(224, 12, 12), new Leading(50, 12, 12)));
			jPanel1.add(getCurrency(), new Constraints(new Leading(303, 90, 12, 12), new Leading(42, 25, 12, 12)));
			jPanel1.add(getTradeDate(), new Constraints(new Leading(70, 145, 12, 12), new Leading(11, 25, 12, 12)));
			jPanel1.add(getAmountJLabel11(), new Constraints(new Leading(469, 10, 10), new Leading(50, 12, 12)));
			jPanel1.add(getBUYSELLJLabel6(), new Constraints(new Leading(7, 12, 12), new Leading(51, 12, 12)));
			jPanel1.add(getUnderlying(), new Constraints(new Leading(70, 323, 10, 10), new Leading(78, 25, 12, 12)));
			jPanel1.add(getUnderlyingJLabel12(), new Constraints(new Leading(7, 12, 12), new Leading(81, 10, 10)));
			jPanel1.add(getSearchProduct(), new Constraints(new Leading(407, 12, 12), new Leading(78, 12, 12)));
			jPanel1.add(getBuySellText(), new Constraints(new Leading(70, 72, 12, 12), new Leading(46, 25, 12, 12)));
			jPanel1.add(getAmount(), new Constraints(new Leading(528, 150, 12, 12), new Leading(42, 25, 10, 10)));
			
		}
		return jPanel1;
	}


	private JButton getSearchProduct() {
		if (searchProduct == null) {
			searchProduct = new JButton();
			searchProduct.setText("...");
		}
		return searchProduct;
	}

	private JLabel getUnderlyingJLabel12() {
		if (underlyingjLabel == null) {
			underlyingjLabel = new JLabel();
			underlyingjLabel.setText("Underlying");
		}
		return underlyingjLabel;
	}

	private JComboBox getUnderlying() {
		if (underlying == null) {
			underlying =new JComboBox();
			underlying.setModel(productData);
		}underlying.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				int index = underlying.getSelectedIndex();
				if(index >= 0) {
				 product = returnProducID(index,productIDs);
				 coupon = product.getCoupon();
				 setTableValues(product); 
				} else {
					product = null;
				//	setTableValuesBlank();
					//nominal.setText("0");
				}
				 
				
			}

			

			
			
		});
		return underlying;
	}
	private void setTableValues(Product product) {
		// TODO Auto-generated method stub
		maturityDate.setText(product.getMarturityDate());
		daycount.setText(product.getCoupon().getDayCount());
		rateType.setText(product.getCoupon().getCouponType());
		currency.setSelectedItem(product.getCoupon().getCCY());
		
        
		if(product.getCoupon().getCouponType().equalsIgnoreCase(ProductConstants.RATETYPEFLOAT)) {
			rateIndex.setText(product.getCoupon().getRateIndex());
		} else {
			rateIndex.setText(TradeConstants.BLANK);
		}
		bdcText.setText(product.getCoupon().getBusinessDayConvention());
		rate.setValue(product.getCoupon().getFixedRate());
		freq.setText(product.getCoupon().getCouponFrequency());
		try {
			if(amount.getDoubleValue() != 0.0) {
			 quantity.setValue(amount.getDoubleValue()/product.getFaceValue());
			}
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			commonUTIL.displayError("BONDTradePanel", "setTableValues", e1);
		}
		if(coupon.getCouponFrequency().equalsIgnoreCase("ZC")) {
        	pricing = new BONDZCPricing();
        	if(trade == null) 
        		trade = new Trade();
        		try {
					trade.setPrice(priceText.getDoubleValue());
					trade.setTradeAmount(amount.getDoubleValue());
					trade.setNominal(amount.getDoubleValue());
					trade.setTradeDate(commonUTIL.convertDateTOString(tradeDate.getDate()));
					trade.setDelivertyDate(commonUTIL.convertDateTOString(settleDate.getDate()));
					trade.setQuantity(quantity.getDoubleValue());
					DateU mdate = DateU.valueOf(commonUTIL.stringToDate(product.getMarturityDate(), false));
			        DateU settdate = DateU.valueOf(commonUTIL.stringToDate(trade.getDelivertyDate(), false));
			        DateU tdate =  DateU.valueOf(commonUTIL.stringToDate(trade.getTradeDate(), false));
			        if(settdate.gte(mdate)) {
			        	commonUTIL.showAlertMessage(" Settlement Date greater then Product Maturity Date ");
			        	return;
			        }
			        if(tdate.gte(settdate)) {
			        	commonUTIL.showAlertMessage(" Trade Date greater then Settlement Date ");
			        	return;
			        }
					 
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					commonUTIL.displayError("BONDTradePanel", "setTableValues", e);
				}
        		
        		
        	
        	calculatePrice(pricing,trade,product,coupon);
        	
        } else {
        	if(trade == null) 
        		trade = new Trade();
        		try {
					trade.setPrice(priceText.getDoubleValue());
					trade.setTradeAmount(amount.getDoubleValue());
					trade.setTradeDate(commonUTIL.convertDateTOString(tradeDate.getDate()));
					trade.setDelivertyDate(commonUTIL.convertDateTOString(settleDate.getDate()));
					trade.setQuantity(quantity.getDoubleValue());
					trade.setNominal(amount.getDoubleValue());
					DateU mdate = DateU.valueOf(commonUTIL.stringToDate(product.getMarturityDate(), false));
			        DateU settdate = DateU.valueOf(commonUTIL.stringToDate(trade.getDelivertyDate(), false));
			        DateU tdate =  DateU.valueOf(commonUTIL.stringToDate(trade.getTradeDate(), false));
			        if(settdate.gte(mdate)) {
			        	commonUTIL.showAlertMessage(" Settlement Date greater then Product Maturity Date ");
			        	return;
			        }
			        if(tdate.gte(settdate)) {
			        	commonUTIL.showAlertMessage(" Trade Date greater then Settlement Date ");
			        	return;
			        }
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					commonUTIL.displayError("BONDTradePanel", "setTableValues", e);
				}
        		
        		
        	
        	calculatePrice(pricing,trade,product,coupon);
        	previousCoupon.setText(commonUTIL.dateToString(pricing.getPreviousCouponDate()));

        	nextCoupon.setText(commonUTIL.dateToString(pricing.getNextCouponDate()));
        	accruedDays.setText(String.valueOf(pricing.getAccrualDays()));
        	accuredInt.setText(String.valueOf(commonUTIL.convertToFinanceFormate(pricing.getAccrualInterest())));
        	AccrAmtText.setText(String.valueOf(commonUTIL.convertToFinanceFormate(pricing.getAccural())));
        	CleanPrice.setText(String.valueOf(pricing.getCleanPrice()));
        	dirtyPrice.setText(String.valueOf(commonUTIL.convertToFinanceFormate(pricing.getDirtyPrice())));
        	settlementAMT.setText(String.valueOf(commonUTIL.convertToFinanceFormate(pricing.getTotalAmount())));
        	
         
        }
		
	}
	
	private void setTradeValues() {
		
	}
	private JTextField getAmount() {
		if (amount == null) {
			amount = new NumericTextField();
			//amount.setText("jTextField3");
			amount.addKeyListener(new KeyAdapter() {
				@Override
	            public void keyTyped(KeyEvent e) {
	            	  
	                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
	                	setTableValues(product);
	                }
	            	 }
			 
        });
		}
		return  amount;
	}

	private JLabel getAmountJLabel11() {
		if (amountjLabel11 == null) {
			amountjLabel11 = new JLabel();
			amountjLabel11.setText("Amount");
		}
		return amountjLabel11;
	}

	private JComboBox getCurrency() {
		if (currency == null) {
			currency = new JComboBox(commonUTIL.convertVectortoSringArray(currencyData));
			//currency.setModel(new DefaultComboBoxModel(new Object[] { "item0", "item1", "item2", "item3" }));
		}
		return currency;
	}

	private JLabel getTradeCurrJLabel10() {
		if (tradeCurrJLabel10jLabel10 == null) {
			tradeCurrJLabel10jLabel10 = new JLabel();
			tradeCurrJLabel10jLabel10.setText("TradeCurr");
		}
		return tradeCurrJLabel10jLabel10;
	}

	private JTextField getBuySellText() {
		if (buySelltext == null) {
			buySelltext = new JTextField();
			buySelltext.setText("BUY");
			buySelltext.setBackground(Color.green);
			buySelltext.setEditable(false);
			buySelltext.setEnabled(false);
			
		}
		return buySelltext;
	}

	private JLabel getBUYSELLJLabel6() {
		if (BUYSELLJLabel6 == null) {
			BUYSELLJLabel6 = new JLabel();
			BUYSELLJLabel6.setText("BUY/SELL");
			BUYSELLJLabel6.addMouseListener(new MouseListener() {

				@Override
				public void mouseClicked(MouseEvent e) {
					String buySell = buySelltext.getText();
			    	
			    	if (buySell.equalsIgnoreCase("BUY")) {
			    		buySelltext.setText("SELL");
			    		buySelltext.setBackground(Color.red);
			    		BUYSELLJLabel6.setText("SELL/BUY");
			    	} 
			    	if (buySell.equalsIgnoreCase("SELL")) {
			    		buySelltext.setText("BUY");
			    		buySelltext.setBackground(Color.green);
			    		BUYSELLJLabel6.setText("BUY/SELL");
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
		}
		return BUYSELLJLabel6;
	}

	private JTextField getPriceText() {
		if (priceText == null) {
			priceText = new NumericTextField();
			priceText.addKeyListener(new KeyAdapter() {
				@Override
	            public void keyTyped(KeyEvent e) {
	            	  
	                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
	                	 setTableValues(product); 
	                }
	            	 }
			 
        });
		}
		return priceText;
	}

	private JLabel getPriceJLabel4() {
		if (pricejLabel4 == null) {
			pricejLabel4 = new JLabel();
			pricejLabel4.setText("Price");
		}
		return pricejLabel4;
	}

	private JLabel getSettleDateJLabel0() {
		if (settleDatejLabel0 == null) {
			settleDatejLabel0 = new JLabel();
			settleDatejLabel0.setText("SettleDate");
		}
		return settleDatejLabel0;
	}

	private com.jidesoft.combobox.DateComboBox getSettleDate() {
		if (settleDate == null) {
			
			settleDate = new com.jidesoft.combobox.DateComboBox();
			settleDate.setFormat(new SimpleDateFormat("dd/MM/yyyy"));
			Calendar currentDate = Calendar.getInstance();
			settleDate.setDate(currentDate.getTime());
			settleDate.setName("settleDate");
			settleDate.addKeyListener(new KeyAdapter() {
				@Override
	            public void keyTyped(KeyEvent e) {
	            	  
	                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
	                	setTableValues(product);
	                }
	            	 }
			 
        });
			settleDate.addFocusListener(new FocusListener() {
				
				@Override
				public void focusLost(FocusEvent e) {
					// TODO Auto-generated method stub
					//commonUTIL.showAlertMessage("COming");
					settleDate.hidePopup();
				}
				
				@Override
				public void focusGained(FocusEvent e) {
					// TODO Auto-generated method stub
					settleDate.showPopup();
					
				}
			});
				
			
		}
		return settleDate;
	}

	private com.jidesoft.combobox.DateComboBox getTradeDate() {
		if (tradeDate == null) {
			tradeDate = new com.jidesoft.combobox.DateComboBox();
			tradeDate = new com.jidesoft.combobox.DateComboBox();
			tradeDate.setFormat(new SimpleDateFormat("dd/MM/yyyy"));
			Calendar currentDate = Calendar.getInstance();
			tradeDate.setDate(currentDate.getTime());
			tradeDate.setName("tradeDate");
		}
		return tradeDate;
	}

	private JLabel getTradeDateJLabel7() {
		if (tradeDatejLabel7 == null) {
			tradeDatejLabel7 = new JLabel();
			tradeDatejLabel7.setText("TradeDate");
		}
		return tradeDatejLabel7;
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
		
		
		return flag;
		
	}
	@Override
	public void buildTrade(Trade trade, String actionType) {
		// TODO Auto-generated method stub
		if(actionType.equalsIgnoreCase("NEW")) {
			setNewAction(trade);
		}
		if(actionType.equalsIgnoreCase("saveAsNew")) {
			 
			actionC.setSelectedItem("NEW");
			actionC.getModel().setSelectedItem("NEW");
			if( validateTradeData()) {
				setSaveAsNew(trade);
			}
		}
		if(actionType.equalsIgnoreCase("save")) {
			if(  validateTradeData()) {
				setSave(trade);
			}
		}
		
	}
	
	private boolean validateTradeData() {
		boolean flag = false;
		if(product == null) {
			commonUTIL.showAlertMessage("Select Underlying Product");
			return flag;
		}
		//String act = actionC.getSelectedItem().toString();
		if(!actionC.getSelectedItem().toString().equalsIgnoreCase("NEW")) {
			if(actionC.getSelectedIndex() ==  -1) {
				commonUTIL.showAlertMessage("Select Action");
				return flag;
			}
		}
if (tradeDate.equals(CommonConstants.BLANKSTRING)) {
        	
        	commonUTIL.showAlertMessage("Please Enter Trade Date");
			return flag;
			
        }
if (settleDate.equals(CommonConstants.BLANKSTRING)) {
	
	commonUTIL.showAlertMessage("Please Enter Settle Date");
	return flag;
	
}
		if(commonUTIL.isEmpty(((String)currency.getSelectedItem())))  {
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
		if (tradeDate.getDate().after(settleDate.getDate())){
    		commonUTIL.showAlertMessage("SettleDate date cannot be before Trade end date");
    		return flag;	
    	}
		if (tradeDate.getDate().after(settleDate.getDate())){
    		commonUTIL.showAlertMessage("SettleDate date cannot be before Trade end date");
    		return flag;	
    	}
		flag = true;
		return flag;
	}
	private void setSave(Trade trade2) {
		// TODO Auto-generated method stub
		//trade = new Trade();
		trade2.setProductId(product.getId());
		trade2.setTradedesc(product.getProdcutShortName());
		trade2.setTradedesc1(product.getProductType());
		trade2.setProductType(product.getProductType());
		trade2.setType(buySelltext.getText());
		trade2.setCurrency(currency.getSelectedItem().toString());
		trade2.setCpID(commonUTIL.converStringToInteger(counterParty.getName()));
		trade2.setBookId(commonUTIL.converStringToInteger(book.getName()));
		trade2.setTraderID(commonUTIL.converStringToInteger(trader.getName()));
		trade.setAction(actionC.getSelectedItem().toString());
		trade.setStatus(status.getText());
		trade2.setAttributes(getAttributeValue(attributes));
       	trade2.setFees(feesPanel.getFeesDataV());
       	trade2.setUserID(usr.getId());
		trade2.setDelivertyDate(commonUTIL.convertDateTOString(tradeDate.getDate()));
        trade2.setEffectiveDate(commonUTIL.convertDateTOString(settleDate.getDate()));
        try {
			trade2.setTradeAmount(amount.getDoubleValue());
			 trade2.setPrice(priceText.getDoubleValue());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			commonUTIL.displayError("BONDTradePanel", "setSave", e);
		}
       
        trade2.setQuantity(trade2.getTradeAmount()/product.getFaceValue());
        trade2.setNominal(trade2.getTradeAmount());
		
		
			} 
		
	private void setSaveAsNew(Trade trade2) {
		// TODO Auto-generated method stub
		trade2.setProductId(product.getId());
		trade2.setTradedesc(product.getProdcutShortName());
		trade2.setTradedesc1(product.getProductType());
		trade2.setProductType(product.getProductType());
		trade2.setType(buySelltext.getText());
		trade2.setCurrency(currency.getSelectedItem().toString());
		trade2.setCpID(commonUTIL.converStringToInteger(counterParty.getName()));
		trade2.setBookId(commonUTIL.converStringToInteger(book.getName()));
		trade2.setTraderID(commonUTIL.converStringToInteger(trader.getName()));
		trade2.setAction(TradeConstants.TRADEACTIONNEW);
		trade2.setStatus(TradeConstants.TRADESTATUSNONE);
		trade2.setAttributes(getAttributeValue(attributes));
       	trade2.setFees(feesPanel.getFeesDataV());
       	trade2.setUserID(usr.getId());
		trade2.setDelivertyDate(commonUTIL.convertDateTOString(tradeDate.getDate()));
        trade2.setEffectiveDate(commonUTIL.convertDateTOString(settleDate.getDate()));
        try {
			trade2.setTradeAmount(amount.getDoubleValue());
			 trade2.setPrice(priceText.getDoubleValue());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			commonUTIL.displayError("BONDTradePanel", "setSaveAsNew", e);
		}
       
        trade2.setQuantity(trade2.getTradeAmount()/product.getFaceValue());
        trade2.setNominal(trade2.getTradeAmount());
        Vector<String> message = new Vector<String>();
    	trade = saveTrade(message,trade);
       	if(trade  != null) {
       		commonUTIL.showAlertMessage("Trade Saved with ID " + trade.getId());
       		openTrade(trade);
       	}
			} 
		
		
	//	trade.sett
		
	

	private void setNewAction(Trade tradeNew) {
		// TODO Auto-generated method stub
		trade = new Trade();
		product = new Product();
		coupon = new Coupon();
		TradeID.setText(TradeConstants.ZERO);
		book.setSelectedIndex(-1);
		counterParty.setSelectedIndex(-1);
		trader.setSelectedIndex(-1);
		tradeDate.setDate(commonUTIL.getCurrentDate());
		settleDate.setDate(commonUTIL.getCurrentDate());
		actionC.setSelectedIndex(0);
		status.setText(TradeConstants.TRADESTATUSNONE);
		Amount1.setValue(0.0);
		priceText.setValue(0.0);
		underlying.setSelectedIndex(-1);
		previousCoupon.setText(TradeConstants.BLANK);
		nextCoupon.setText(TradeConstants.BLANK);

		maturityDate.setText(TradeConstants.BLANK);

		daycount.setText(TradeConstants.BLANK);

		freq.setText(TradeConstants.BLANK);

		accruedDays.setText(TradeConstants.BLANK);

		AccrAmtText.setText(TradeConstants.BLANK);

		accuredInt.setText(TradeConstants.BLANK);

		settlementAMT.setText(TradeConstants.BLANK);

		quantity.setText(TradeConstants.BLANK);

		rateType.setText(TradeConstants.BLANK);

		rate.setText(TradeConstants.BLANK);

		rateIndex.setText(TradeConstants.BLANK);

		CleanPrice.setText(TradeConstants.BLANK);
		dirtyPrice.setText(TradeConstants.BLANK);
		daycount.setText(TradeConstants.BLANK);
		bdcText.setText(TradeConstants.BLANK);
		buySelltext.setText("BUY");
		BUYSELLJLabel6.setText("BUY/SELL");
		buySelltext.setBackground(Color.green);
		setCashFlow(getCashFlowTable(), null, ProductConstants.PRODUCTTYPEBOND);
		
		
		
		
		
	}
	
	

	@Override
	public void openTrade(Trade trade) {
		// TODO Auto-generated method stub
		if(trade != null) {
			
			 this.trade = trade; 
			 this.product = trade.getProduct();
			 this.coupon = trade.getProduct().getCoupon();
			 if(!trade.getProductType().equalsIgnoreCase(ProductConstants.PRODUCTTYPEBOND)) {
				 commonUTIL.showAlertMessage("Trade Type is not BOND");
				 return;
			 }
			 underlying.setSelectedItem(product.getProdcutShortName());
			 setTrade(trade);
			 CashFlowTable.removeAll();
			 attributeDataValue.clear();
			 TradeID.setText(String.valueOf(trade.getId()));
			 setAttribute(trade.getAttributes(),attributes,attributeDataValue);
			 setDataFromTradeObject(trade.getCpID(), TradeConstants.COUNTERPARTY,counterParty);
			
			 if (trade.getType().equalsIgnoreCase("BUY")) {
				 buySelltext.setText("BUY");
		    		buySelltext.setBackground(Color.green);
		    		BUYSELLJLabel6.setText("BUY/SELL");
		    		
		    	} 
		    	if (trade.getType().equalsIgnoreCase("SELL")) {
		    		buySelltext.setText("SELL");
		    		buySelltext.setBackground(Color.red);
		    		BUYSELLJLabel6.setText("SELL/BUY");
		    	} 
			 setDataFromTradeObject(trade.getTraderID(), TradeConstants.TRADER,trader);
			 setDataFromTradeObject(trade.getBookId(), TradeConstants.BOOK,book);
			currency.setSelectedItem(trade.getCurrency());
			tradeDate.setDate(commonUTIL.stringToDate(trade.getTradeDate(), false));
			settleDate.setDate(commonUTIL.stringToDate(trade.getTradeDate(), false));
		    priceText.setValue(trade.getPrice());
		    amount.setValue(trade.getNominal());
		    
			setTableValues(product);
		}
		    
		
		
	}
	private void buildCashFlow() {
		// TODO Auto-generated method stub
		pricing = (BONDPricing) getPricer();
		setCashFlow(getCashFlowTable(),(Vector<Flows>) getCashFlows(),"BOND");
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
		pricing.price(getTrade(),product, product.getCoupon());
		BondCashFlow cashFlow =   pricing.generateCashFlow();
		
		return cashFlow.getFlows();
	}
	
	@Override
	public Pricer getPricer() {
		// TODO Auto-generated method stub
		pricing = new BONDPricing();
		
		return pricing;
	}
	
	public void setPricing(BONDPricing pricing) {
		pricing = (BONDPricing) getPricer();
		
	}
 
public void calculatePrice(BONDPricing price,Trade trade,Product product,Coupon coupon) {
		
	price.price(trade, product, coupon);
         BondCashFlow cashFlow = price.generateCashFlow();
         pricing.setTradeData(cashFlow);
        // setCashFlows(cashFlow.getFlows());
        // setPricing(pricing);
         
       
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
private Product returnProducID(int indexid,Hashtable h) {
	return ((Product) h.get(indexid));
	

}

@Override
public ArrayList<Component> getFocusOrderList() {
	// TODO Auto-generated method stub
	ArrayList<Component> list = new ArrayList<Component>();
	//list
	list.add(TradeID);
	list.add(actionC);
	list.add(book);
	list.add(counterParty);
	list.add(trader);
	list.add(tradeDate);
	list.add(settleDate);
	list.add(priceText);
	list.add(buySelltext);
	list.add(currency);
	list.add(amount);
	list.add(underlying);
	list.add(searchProduct);
	
	
	//list.add(e)
	return list;
}

}


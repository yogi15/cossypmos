package apps.window.tradewindow;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

import org.dyno.visual.swing.layouts.Bilateral;
import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;
import org.dyno.visual.swing.layouts.Trailing;

import constants.DateConstants;

import productPricing.BONDZCPricing;
import productPricing.DefaultCashFlow;
import productPricing.Pricer;
import productPricing.PricerFactory;
import productPricing.REPOPricing;
import productPricing.pricingUtil.BondCashFlow;
import productPricing.pricingUtil.CashFlowFactory;
import productPricing.pricingUtil.RepoCashFlow;
import util.NumericTextField;
import util.commonUTIL;
import util.common.DateU;
import apps.window.referencewindow.DateCellEditor12;
import beans.Coupon;
import beans.Product;
import beans.StartUPData;
import beans.Trade;
import dsServices.RemoteProduct;
import dsServices.RemoteReferenceData;
import dsServices.ServerConnectionUtil;
import apps.window.utilwindow.JDialogTable;

//VS4E -- DO NOT REMOVE THIS LINE!
public class REPOPanel extends CommonPanel {

	private static final long serialVersionUID = 1L;
	private JTable JTable1;
	private JScrollPane jScrollPane0;
	private JPanel jPanel0;
	DecimalFormat format = new DecimalFormat("##,###,#######.##");

	private JTextField jTextField7;
	private JButton bondProduct;
	private JLabel buysell;
	private JLabel Nominal;
	public NumericTextField nominal;
	private JPanel jPanel1;
	private JPanel jPanel2;
	private JPanel jPanel3;
	private JTabbedPane jTabbedPane0;
	private JTable jTable1;
	private JScrollPane jScrollPane1;
	private JPanel jPanel4;
	private JTabbedPane jTabbedPane1;
	private JTable jTable2;
	private JScrollPane jScrollPane2;

	Product collateralProduct = null;
	RemoteReferenceData remoteBORef;
	RemoteReferenceData referenceData;
	RemoteProduct remoteProduct;
	public static ServerConnectionUtil de = null;

	Vector cashFlows = null;
	Vector collateralCashFlows = null;

	REPOPricing pricing = new REPOPricing();

	REPOTradePanel tradevalue = null;
	Hashtable productID = new Hashtable();

	Product product = null;
	Coupon coupon = null;

	DefaultTableModel t1model;
	DefaultTableModel t2model;
	DefaultTableModel couponModel;
	DefaultTableModel amorModel;
	DefaultTableModel tradePrice = null;
	javax.swing.DefaultComboBoxModel productData = null;

	DateCellEditor12 startDateC = null;
	DateCellEditor12 endDateC = null;
	DefaultCellEditor currencyData = null;
	DefaultCellEditor dce13 = null;
	DefaultCellEditor perioddce13 = null;
	DefaultCellEditor indexdce13 = null;
	DefaultCellEditor repoType = null;
	DefaultCellEditor currencydce13 = null;
	DefaultCellEditor dayCount = null;
	DefaultCellEditor tenorce13 = null;
	DefaultCellEditor callType = null;
	DefaultCellEditor frequency13 = null;
	DefaultCellEditor bdc13 = null;
	DefaultCellEditor termAsDatece13 = null;
	DefaultCellEditor termAsLoandce13 = null;
	DefaultCellEditor interestType = null;
	DefaultCellEditor openTermD = null;
	DefaultCellEditor compoundingFrequency13 = null;
	Vector curr = null;
	Vector ve1 = null;
	Vector dayc = null;
	Vector repostype = null;
	Vector tenors = null;
	Vector indexs = null;
	Vector ve6 = null;
	Vector tenor = null;
	Vector ve7 = null;
	Vector frequency = null;
	Vector bdc = null;
	// private static final String PREFERRED_LOOK_AND_FEEL =
	// "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";

	JComboBox openTerm = null;

	public JComboBox getOpenTerm() {
		return openTerm;
	}

	public void setOpenTerm(JComboBox openTerm) {
		this.openTerm = openTerm;
	}

	public REPOPanel() {
		init();
		startDateC = new DateCellEditor12();
		endDateC = new DateCellEditor12();

		try {

			curr = (Vector) referenceData.getStartUPData("Currency");
			dayc = (Vector) referenceData.getStartUPData("DayCount");
			repostype = (Vector) referenceData.getStartUPData("RepoType");
			tenors = (Vector) referenceData.getStartUPData("Tenor");
			indexs = (Vector) referenceData.getStartUPData("INDEX");
			frequency = (Vector) referenceData.getStartUPData("PaymentFRQ");
			bdc = (Vector) referenceData.getStartUPData("DateRoll");
			JComboBox currencycomboBox11 = new JComboBox(
					convertVectortoSringArray(curr));
			JComboBox dayCountcomboBox11 = new JComboBox(
					convertVectortoSringArray(dayc));
			dayCountcomboBox11.setSelectedIndex(1);
			JComboBox tenorcomboBox11 = new JComboBox(
					convertVectortoSringArray(tenors));
			JComboBox indexcomboBox11 = new JComboBox(
					convertVectortoSringArray(indexs));
			JComboBox frequencyComboBox11 = new JComboBox(
					convertVectortoSringArray(frequency));
			JComboBox bdcComboBox11 = new JComboBox(
					convertVectortoSringArray(bdc));
			JComboBox interestTypec = new JComboBox();
			interestTypec.addItem("Fixed");
			interestTypec.addItem("Float");
			openTerm = new JComboBox();
			openTerm.addItem("True");
			openTerm.addItem("False");
			JComboBox repoTypecomboBox11 = new JComboBox(
					convertVectortoSringArray(repostype));
			repoTypecomboBox11.setSelectedIndex(0);
			JComboBox callableType = new JComboBox();
			callableType.addItem(new String("Lender"));
			callableType.addItem(new String("BOTH"));
			callableType.addItem(new String("None"));
			callableType.setSelectedIndex(1);
			JComboBox compoundingFrequencyComboBox11 = new JComboBox(
					convertVectortoSringArray(frequency));
			currencyData = new DefaultCellEditor(currencycomboBox11);
			dayCount = new DefaultCellEditor(dayCountcomboBox11);
			repoType = new DefaultCellEditor(repoTypecomboBox11);
			interestType = new DefaultCellEditor(interestTypec);
			tenorce13 = new DefaultCellEditor(tenorcomboBox11);
			indexdce13 = new DefaultCellEditor(indexcomboBox11);
			frequency13 = new DefaultCellEditor(frequencyComboBox11);
			bdc13 = new DefaultCellEditor(bdcComboBox11);
			callType = new DefaultCellEditor(callableType);
			openTermD = new DefaultCellEditor(openTerm);
			compoundingFrequency13 = new DefaultCellEditor(
					compoundingFrequencyComboBox11);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		initComponents();

		setTableValuesBlank();
	}

	private void initComponents() {
		setLayout(new GroupLayout());
		add(getJPanel1(), new Constraints(new Bilateral(5, 2, 1218),
				new Bilateral(8, 17, 10)));
		setSize(1225, 362);
		showAllTrades.jTable1
				.addMouseListener(new java.awt.event.MouseAdapter() {

					public void mouseClicked(MouseEvent e) {
						String productName = showAllTrades.jTable1.getValueAt(
								showAllTrades.jTable1.getSelectedRow(), 1)
								.toString();
						String productid = showAllTrades.jTable1.getValueAt(
								showAllTrades.jTable1.getSelectedRow(), 0)
								.toString();
						int collateralID = new Integer(productid).intValue();

						jTextField0.setText(productName);
						try {
							collateralProduct = (Product) remoteProduct
									.selectProduct(collateralID);
						} catch (RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						showAllTrades.dispose();

					}

				});

		openTerm.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent arg0) {
				// TODO Auto-generated method stub
				String value = openTerm.getSelectedItem().toString();
				if (value.equalsIgnoreCase("True")) {
					jTable1.setValueAt(0, 5, 1);
					jTable1.setValueAt("", 1, 1);
				}
			}

		});

	}

	private JButton getPriceButton() {
		if (priceButton == null) {
			priceButton = new JButton();
			priceButton.setText("Price");
		}

		priceButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (product != null) {
					Trade trade = new Trade();
					// System.out.println(tradevalue.tradeyield.getText());
					// trade.setYield(new
					// Double(tradevalue.tradeyield.getText()).doubleValue());

					trade.setTradeAmount(new Double(tradevalue.tradeamount
							.getText()));
					// trade.setNominal(new
					// Double(nominal.getText()).doubleValue());
					trade.setPrice(new Double(tradevalue.tprice.getText())
							.doubleValue());
					trade.setTradeDate(tradevalue.tradeDate.getText());
					trade.setDelivertyDate(jTable1.getValueAt(0, 1).toString());
					trade.setAmoritizationData("compoundingFrequency="
							+ jTable1.getValueAt(8, 1).toString());
					// trade.setQuantity(new
					// Double(tradevalue.tquantity.getText()).doubleValue());

					if (jTabbedPane0.getTitleAt(0).equalsIgnoreCase("REPO")) {

						trade.setType("BUY");
						trade.setQuantity(product.getQuantity());

					} else {

						trade.setType("SELL");
						trade.setQuantity(product.getQuantity() * -1);
					}

					DateU settdate = DateU.valueOf(commonUTIL.stringToDate(
							trade.getDelivertyDate(), false));

					DateU mdate = DateU.valueOf(commonUTIL.stringToDate(jTable1
							.getValueAt(1, 1).toString(), false));

					if (settdate.gte(mdate)) {
						commonUTIL
								.showAlertMessage(" Settlement Date greater then Product Maturity Date ");
						return;
					}

					calculatePrice(pricing, trade, product, coupon);

				}

			}

		});

		return priceButton;
	}

	String s[] = { "Product id", "ProductName" };
	DefaultTableModel tablemodel = new DefaultTableModel(s, 0) {
		@Override
		public boolean isCellEditable(int row, int column) {
			// all cells false
			return false;
		}

	};
	final JDialogTable showAllTrades = new JDialogTable(tablemodel);

	private JLabel getJLabel0() {
		if (jLabel0 == null) {
			jLabel0 = new JLabel();
			jLabel0.setText("Nominal");
			jLabel0.setFont(new Font("Arial", Font.BOLD, 13));
		}
		return jLabel0;
	}

	private JTextField getJTextField0() {
		if (jTextField0 == null) {
			jTextField0 = new JTextField();
			// jTextField0.setText("jTextField0");
		}
		return jTextField0;
	}

	private void init() {
		coupon = new Coupon();
		product = new Product();
		de = ServerConnectionUtil.connect("localhost", 1099,
				commonUTIL.getServerIP());
		try {
			remoteProduct = (RemoteProduct) de.getRMIService("Product");
			referenceData = (RemoteReferenceData) de
					.getRMIService("ReferenceData");
			productData = new javax.swing.DefaultComboBoxModel();
			processDataCombo1(tablemodel, productID);

			// System.out.println(remoteProduct);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private JScrollPane getJScrollPane2() {
		if (jScrollPane2 == null) {
			jScrollPane2 = new JScrollPane();
			jScrollPane2.setViewportView(getJTable2());
		}
		return jScrollPane2;
	}

	private JTable getJTable2() {
		if (jTable2 == null) {
			jTable2 = new JTable();
			String priceName[] = { "TradeLeg ", " Value " };
			tradePrice = new DefaultTableModel(priceName, 0);
			jTable2 = new javax.swing.JTable(tradePrice);
			processTableData(tradePrice);

		}
		return jTable2;
	}

	private JTabbedPane getJTabbedPane1() {
		if (jTabbedPane1 == null) {
			jTabbedPane1 = new JTabbedPane();

			jTabbedPane1.addTab("Coupon", getJPanel9());
			jTabbedPane1.addTab("TradePrice", getJPanel4());
		}
		return jTabbedPane1;
	}

	private JPanel getJPanel4() {
		if (jPanel4 == null) {
			jPanel4 = new JPanel();
			jPanel4.setLayout(new GroupLayout());
			jPanel4.add(getJScrollPane2(), new Constraints(new Bilateral(7, 5,
					415), new Bilateral(6, 4, 10, 280)));
		}
		return jPanel4;
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
			String col1[] = { "Repo Details       ", "Value      " };
			t1model = new DefaultTableModel(col1, 0);
			jTable1 = jTable1 = new javax.swing.JTable(t1model) {
				public TableCellEditor getCellEditor(int row, int column) {
					int modelColumn = convertColumnIndexToModel(column);
					if (modelColumn == 1 && row == 0) {
						TableCellEditor t1 = ((TableCellEditor) startDateC);
						return (TableCellEditor) t1;
					} else if (modelColumn == 1 && (row == 1)) {
						TableCellEditor t1 = ((TableCellEditor) endDateC);
						return (TableCellEditor) t1;
					} else if (modelColumn == 1 && (row == 3)) {
						TableCellEditor t1 = ((TableCellEditor) currencyData);
						return (TableCellEditor) t1;
					} else if (modelColumn == 1 && (row == 4)) {
						TableCellEditor t1 = ((TableCellEditor) openTermD);
						return (TableCellEditor) t1;
					} else if (modelColumn == 1 && (row == 6)) {
						TableCellEditor t1 = ((TableCellEditor) callType);
						return (TableCellEditor) t1;
					} else if (modelColumn == 1 && (row == 7)) {
						TableCellEditor t1 = ((TableCellEditor) repoType);
						return (TableCellEditor) t1;
					} else if (modelColumn == 1 && (row == 8)) {
						TableCellEditor t1 = ((TableCellEditor) compoundingFrequency13);
						return (TableCellEditor) t1;
					} else {
						return super.getCellEditor(1, 0);
					}
				}

			};

		}
		// jTable1.setValueAt(commonUTIL.getDateFormat(commonUTIL.getCurrentDate()),
		// 1, 1);

		return jTable1;
	}

	private JTabbedPane getJTabbedPane0() {
		if (jTabbedPane0 == null) {
			jTabbedPane0 = new JTabbedPane();
			jTabbedPane0.addTab("Repo", getJPanel3());

		}
		jTabbedPane0.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				String type = jTabbedPane0.getTitleAt(0);
				if (type.equalsIgnoreCase("REPO")) {
					jTabbedPane0.setTitleAt(0, "REVERSEREPO");
				}
				if (type.equalsIgnoreCase("REVERSEREPO")) {
					jTabbedPane0.setTitleAt(0, "REPO");
				}

			}

		});
		return jTabbedPane0;
	}

	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jPanel3 = new JPanel();
			jPanel3.setLayout(new GroupLayout());
			jPanel3.add(getJScrollPane1(), new Constraints(new Leading(7, 744,
					10, 10), new Trailing(12, 235, 12, 12)));
		}
		return jPanel3;
	}

	JPanel jPanel9 = null;

	private JPanel getJPanel9() {
		if (jPanel9 == null) {
			jPanel9 = new JPanel();
			jPanel9.setLayout(new GroupLayout());
			jPanel9.add(getJScrollPane3(), new Constraints(new Bilateral(7, 5,
					415), new Bilateral(6, 4, 10, 280)));
		}
		return jPanel9;
	}

	JScrollPane jScrollPane3 = null;

	private JScrollPane getJScrollPane3() {
		if (jScrollPane3 == null) {
			jScrollPane3 = new JScrollPane();
			jScrollPane3.setViewportView(getJTable3());
		}
		return jScrollPane3;
	}

	JTable jTable3;

	private JTable getJTable3() {
		if (jTable3 == null) {
			String col3[] = { " Floating Data   ", "Value      " };
			couponModel = new DefaultTableModel(col3, 0);
			jTable3 = new javax.swing.JTable(couponModel) {
				public TableCellEditor getCellEditor(int row, int column) {
					int modelColumn = convertColumnIndexToModel(column);
					if (modelColumn == 1 && row == 0) {
						TableCellEditor t1 = ((TableCellEditor) dayCount);
						return (TableCellEditor) t1;
					} else if (modelColumn == 1 && row == 1) {
						TableCellEditor t1 = ((TableCellEditor) interestType);
						return (TableCellEditor) t1;
					} else if (modelColumn == 1 && row == 4) {
						TableCellEditor t1 = ((TableCellEditor) indexdce13);
						return (TableCellEditor) t1;
					} else if (modelColumn == 1 && row == 5) {
						TableCellEditor t1 = ((TableCellEditor) tenorce13);
						return (TableCellEditor) t1;
					} else if (modelColumn == 1 && row == 6) {
						TableCellEditor t1 = ((TableCellEditor) frequency13);
						return (TableCellEditor) t1;

					} else if (modelColumn == 1 && row == 7) {
						TableCellEditor t1 = ((TableCellEditor) bdc13);
						return (TableCellEditor) t1;

					} else {
						return super.getCellEditor(1, 0);
					}
				}

			};
		}

		return jTable3;
	}

	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setLayout(new GroupLayout());
		}
		return jPanel2;
	}

	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(new GroupLayout());
			jPanel1.add(getJTabbedPane0(), new Constraints(new Leading(6, 761,
					12, 12), new Bilateral(51, 8, 10)));
			jPanel1.add(getJTabbedPane1(), new Constraints(new Bilateral(776,
					10, 418), new Bilateral(9, 10, 10, 316)));
			jPanel1.add(getBuysell(), new Constraints(new Leading(6, 68, 448,
					485), new Leading(20, 21, 301, 301)));
			jPanel1.add(getNominal(), new Constraints(new Leading(625, 135,
					448, 485), new Leading(14, 27, 301, 301)));
			jPanel1.add(getJLabel0(), new Constraints(new Leading(567, 54, 10,
					10), new Leading(14, 27, 301, 301)));
			jPanel1.add(getPriceButton(), new Constraints(new Leading(494, 67,
					10, 10), new Leading(11, 30, 301, 301)));
			jPanel1.add(getJTextField0(), new Constraints(new Leading(74, 370,
					448, 485), new Leading(14, 27, 301, 301)));
			jPanel1.add(getBondproduct(), new Constraints(new Leading(452, 30,
					448, 485), new Leading(11, 30, 301, 301)));
		}
		return jPanel1;
	}

	private JLabel getJLabel14() {
		if (Nominal == null) {
			Nominal = new JLabel();
			// Nominal.setFont(new Font("Dialog", Font.PLAIN, 13));
			Nominal.setText("QTY");
		}
		return Nominal;
	}

	private JLabel getBuysell() {
		if (buysell == null) {
			buysell = new JLabel();
			buysell.setFont(new Font("Arial", Font.BOLD, 13));
			buysell.setText("Collateral");
		}
		return buysell;
	}

	private JButton getBondproduct() {
		if (bondProduct == null) {
			bondProduct = new JButton();
			bondProduct.setFont(new Font("SansSerif", Font.PLAIN, 13));
			bondProduct.setText("...");
		}
		bondProduct.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				showAllTrades.jTable1.setModel(tablemodel);
				showAllTrades.setVisible(true);
				// showAllTrades.setLocation(this);

			}

		});

		return bondProduct;
	}

	private void setTableValuesBlank() {
		jTable1.removeAll();
		int r = t1model.getRowCount();
		t1model.insertRow(t1model.getRowCount(), new Object[] { "Start Date",
				commonUTIL.getDateFormat(commonUTIL.getCurrentDate()) });

		t1model.insertRow(t1model.getRowCount(),
				new Object[] { "End Date", "" });

		t1model.insertRow(t1model.getRowCount(), new Object[] { "Principal",
				"0" });
		t1model.insertRow(t1model.getRowCount(), new Object[] { "Currency",
				"INR" });
		t1model.insertRow(t1model.getRowCount(), new Object[] { "Open Term",
				"True" });
		t1model.insertRow(t1model.getRowCount(), new Object[] {
				"Notice Period", "0" });
		t1model.insertRow(t1model.getRowCount(), new Object[] { "Callable By",
				"Both" });
		t1model.insertRow(t1model.getRowCount(), new Object[] { "Type", "" });
		t1model.insertRow(t1model.getRowCount(), new Object[] {
				"Compounding Frequency", "" });

		/*
		 * tradePrice.insertRow(tradePrice.getRowCount(), new Object[]{ "Type",
		 * ""}); tradePrice.insertRow(tradePrice.getRowCount(), new Object[]{
		 * "Ccy", ""}); tradePrice.insertRow(tradePrice.getRowCount(), new
		 * Object[]{ "Rate", ""});
		 * tradePrice.insertRow(tradePrice.getRowCount(), new Object[]{
		 * "DayCount", ""}); tradePrice.insertRow(tradePrice.getRowCount(), new
		 * Object[]{ "SalesMargin", ""});
		 * tradePrice.insertRow(tradePrice.getRowCount(), new Object[]{
		 * "Cmp Frq", ""}); tradePrice.insertRow(tradePrice.getRowCount(), new
		 * Object[]{ "Tenor", ""});
		 */

		couponModel.insertRow(couponModel.getRowCount(), new Object[] {
				"DayCount", "" });
		couponModel.insertRow(couponModel.getRowCount(), new Object[] {
				"Interest Type", "Fixed" });

		couponModel.insertRow(couponModel.getRowCount(), new Object[] { "Rate",
				"0" });
		couponModel.insertRow(couponModel.getRowCount(), new Object[] {
				"Spread", "0" });
		couponModel.insertRow(couponModel.getRowCount(), new Object[] {
				"Index", " " });
		couponModel.insertRow(couponModel.getRowCount(), new Object[] {
				"Tenor", "1D" });
		couponModel.insertRow(couponModel.getRowCount(), new Object[] {
				"Frequency", "MTH" });
		couponModel.insertRow(couponModel.getRowCount(), new Object[] {
				"Date Roll", "NO_ADJUST" });

		jTable1.setValueAt(
				commonUTIL.getDateFormat(commonUTIL.getCurrentDate()), 0, 1);

	}

	private JTextField getJTextField7() {
		if (jTextField7 == null) {
			jTextField7 = new JTextField();
			jTextField7.setBackground(Color.white);
			// jTextField7.setFont(new Font("Dialog", Font.PLAIN, 13));
		}
		return jTextField7;
	}

	private NumericTextField getNominal() {
		if (nominal == null) {
			nominal = new NumericTextField(15, format);
			nominal.setBackground(Color.white);
			nominal.setFont(new Font("Dialog", Font.PLAIN, 13));
		}
		return nominal;
	}

	private String[] convertVectortoSringArray(Vector v) {
		String name[] = null;
		int i = 0;
		if (v != null) {
			name = new String[v.size()];
			Iterator its = v.iterator();
			while (its.hasNext()) {
				name[i] = ((StartUPData) its.next()).getName();
				i++;
			}
		}
		return name;
		// TODO add your handling code here:
	}

	private JScrollPane getJScrollPane0() {
		if (jScrollPane0 == null) {
			jScrollPane0 = new JScrollPane();
			jScrollPane0.setBorder(BorderFactory.createBevelBorder(
					BevelBorder.LOWERED, null, null, null, null));
			jScrollPane0.setViewportView(getJTable0());
		}
		return jScrollPane0;
	}

	private JTable getJTable0() {
		if (JTable1 == null) {
			JTable1 = new JTable();
			JTable1.setModel(new DefaultTableModel(new Object[][] {
					{ "0x0", "0x1", }, { "1x0", "1x1", }, }, new String[] {
					"Title 0", "Title 1", }) {
				private static final long serialVersionUID = 1L;
				Class<?>[] types = new Class<?>[] { Object.class, Object.class, };

				public Class<?> getColumnClass(int columnIndex) {
					return types[columnIndex];
				}
			});
		}
		return JTable1;
	}

	private void processDataCombo1(DefaultTableModel model, Hashtable ids) {
		Vector vector;
		try {
			String sql = " producttype ='BOND' and   productname like 'BOND%'";
			vector = (Vector) remoteProduct.selectProductWhereClaus(sql);
			Iterator it = vector.iterator();
			int i = 0;
			while (it.hasNext()) {
				Product product = (Product) it.next();

				model.insertRow(
						i,
						new Object[] { product.getId(),
								product.getProductname() });
				ids.put(i, product);
				i++;
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public int getProductKey(Hashtable t, int id) {
		int i = 0;
		Set set = t.entrySet();
		Iterator it = set.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			Product le = ((Product) entry.getValue());
			if (id == le.getId())
				i = ((Integer) entry.getKey()).intValue();
		}
		return i;

	}

	public void buildTrade1(Trade trade, String actionType) {

	}

	private Product returnProducID(int indexid, Hashtable h) {
		return ((Product) h.get(indexid));

	}

	private Product getProdcutToOpenTrade(int prouductID, Hashtable products) {
		Enumeration keys;
		keys = products.elements();
		product = null;
		while (keys.hasMoreElements()) {
			Product prod = (Product) keys.nextElement();
			if (prod.getId() == prouductID) {
				product = prod;
				break;
			}

		}
		if (product == null) {
			try {
				product = (Product) remoteProduct.selectProduct(prouductID);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			productID.put(productID.size() + 1, product);

		}
		return product;

	}

	@Override
	public void buildTrade(Trade trade, String actionType) {
		if (actionType.equalsIgnoreCase("NEW")) {
			// bondProduct.setSelectedIndex(-1);
			trade.setProductId(0);
			trade.setQuantity(0);
			product = new Product();
			coupon = new Coupon();
			if (jTabbedPane0.getTitleAt(0).equalsIgnoreCase("REPO"))
				trade.setType("BUY");
			else
				trade.setType("SELL");
			trade.setDelivertyDate("");
			trade.setEffectiveDate("");
			trade.setTradedesc("REPO");
			trade.setProductType("REPO");
			trade.setTradedesc1("");
			nominal.setText("0");
			jTable1.setValueAt(
					commonUTIL.getDateFormat(commonUTIL.getCurrentDate()), 0, 1);
			jTable1.setValueAt(new String(""), 1, 1);
			jTable1.setValueAt(new String("0"), 2, 1);
			jTable1.setValueAt(new String("INR"), 3, 1);
			jTable1.setValueAt(new String("True"), 4, 1);
			jTable1.setValueAt(new String("0"), 5, 1);
			jTable1.setValueAt(new String("Both"), 6, 1);
			jTable2.setValueAt(new String(""), 0, 1);
			jTable2.setValueAt(new String("Fixed"), 1, 1);
			jTable2.setValueAt(new String(""), 2, 1);
			jTable2.setValueAt(new String(""), 3, 1);
			jTable2.setValueAt(new String(""), 4, 1);
			jTable2.setValueAt(new String("1D"), 5, 1);
			jTabbedPane0.setTitleAt(0, "Repo");

		} else {
			if (!validateProductData()) {
				trade.setProductId(0);
				return;
			}
			try {
				int productId = 0;
				if (actionType.equalsIgnoreCase("SAVE")) {

					productId = product.getId();

					remoteProduct.updateProduct(product, coupon);

				} else {
					product.setId(0);
					productId = remoteProduct.saveProduct(product, coupon);
				}
				if (productId > 0) {
					product.setId(productId);
					// bondProduct.removeAll();

					trade.setProductId(product.getId());
					trade.setNominal(product.getQuantity());

					if (jTabbedPane0.getTitleAt(0).equalsIgnoreCase("REPO")) {

						trade.setType("BUY");
						trade.setQuantity(product.getQuantity());

					} else {

						trade.setType("SELL");
						trade.setQuantity(product.getQuantity() * -1);
					}

					/*
					 * if (trade.getType().equalsIgnoreCase("SELL"))
					 * trade.setQuantity(product.getQuantity() * -1); else
					 * trade.setQuantity(product.getQuantity());
					 */
					trade.setDelivertyDate(jTable1.getValueAt(0, 1).toString());
					trade.setEffectiveDate(trade.getTradeDate());
					trade.setTradedesc(product.getProductname());
					trade.setTradedesc1(product.getProdcutShortName());
					trade.setAmoritizationData("compoundingFrequency="
							+ jTable1.getValueAt(8, 1).toString());
				}

			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	@Override
	public void openTrade(Trade trade) {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		// bondProduct.setSelectedIndex(getProductKey(productID,trade.getProductId()));
		if (trade.getType().equalsIgnoreCase("SELL")) {
			jTabbedPane0.setTitleAt(0, "REVERSEREPO");
		} else {
			jTabbedPane0.setTitleAt(0, "REPO");
		}
		buysell.setText("Collateral");

		// System.out.println(trade.getQuantity());
		// startDate.setText(trade); //ppppppppppppppppppppppp
		nominal.setText(new Double(trade.getQuantity()).toString());
		setTableValues(getProdcutToOpenTrade(trade.getProductId(), productID),
				trade);

	}

	public void setTableValues(Product product, Trade trade) {
		Vector coupons = null;

		try {
			coupons = (Vector) remoteProduct.getCoupon(product.getId());
			coupon = (Coupon) coupons.elementAt(0);
			collateralProduct = remoteProduct.selectProduct(product
					.getCollateralID());
			jTextField0.setText(collateralProduct.getProductname());
			// System.out.println(remoteProduct);
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		int r = t1model.getRowCount();
		// System.out.println(r);
		for (int rows = r; rows > 0; rows--) {
			t1model.removeRow(rows - 1);
		}
		jTable1.repaint();

		t1model.insertRow(t1model.getRowCount(), new Object[] { "Start Date",
				product.getIssueDate() });

		t1model.insertRow(t1model.getRowCount(), new Object[] { "End Date",
				product.getMarturityDate() });

		t1model.insertRow(t1model.getRowCount(), new Object[] { "Principal",
				product.getFaceValue() });
		t1model.insertRow(t1model.getRowCount(), new Object[] { "Currency",
				product.getIssueCurrency() });

		if (!commonUTIL.isEmpty(product.getMarturityDate())) {
			Date startDate = commonUTIL.stringToDate(product.getIssueDate(),
					false);
			Date endDate = commonUTIL.stringToDate(product.getMarturityDate(),
					false);
			int days = (int) commonUTIL.diffInDays(startDate, endDate);

			t1model.insertRow(t1model.getRowCount(), new Object[] {
					"Open Term", "False" });
			t1model.insertRow(t1model.getRowCount(), new Object[] {
					"Notice Period", days });
		} else {

			t1model.insertRow(t1model.getRowCount(), new Object[] {
					"Open Term", "True" });
			t1model.insertRow(t1model.getRowCount(), new Object[] {
					"Notice Period", 0 });
		}
		t1model.insertRow(t1model.getRowCount(), new Object[] { "Callable By",
				product.getCallType() });
		t1model.insertRow(t1model.getRowCount(),
				new Object[] { "Type", product.getRepoType() });

		String amortizationData = trade.getAmoritizationData();
		String compoundingFrequency = amortizationData.substring(
				amortizationData.indexOf('=') + 1, amortizationData.length()) != null ? amortizationData
				.substring(amortizationData.indexOf('=') + 1,
						amortizationData.length()) : "";

		t1model.insertRow(t1model.getRowCount(), new Object[] {
				"Compounding Frequency", compoundingFrequency });
		r = couponModel.getRowCount();
		// System.out.println(r);
		for (int rows = r; rows > 0; rows--) {
			couponModel.removeRow(rows - 1);
		}
		jTable3.repaint();
		couponModel.insertRow(couponModel.getRowCount(), new Object[] {
				"DayCount", coupon.getDayCount() });
		couponModel.insertRow(couponModel.getRowCount(), new Object[] {
				"Interest Type", coupon.getCouponType() });
		couponModel.insertRow(couponModel.getRowCount(), new Object[] { "Rate",
				coupon.getFixedRate() });
		couponModel.insertRow(couponModel.getRowCount(), new Object[] {
				"Spread", coupon.getYieldDecimals() });
		couponModel.insertRow(couponModel.getRowCount(), new Object[] {
				"Index", coupon.getYieldMethod() });
		couponModel.insertRow(couponModel.getRowCount(), new Object[] {
				"Tenor", coupon.getTenor() });
		couponModel.insertRow(couponModel.getRowCount(), new Object[] {
				"Frequency", coupon.getCouponFrequency() });
		couponModel.insertRow(couponModel.getRowCount(), new Object[] {
				"Date Roll", coupon.getBusinessDayConvention() });
		/*-			couponModel.insertRow(couponModel.getRowCount(),  new Object[]{ "Coupon Type",coupon.getCouponType()});
		 couponModel.insertRow(couponModel.getRowCount(),  new Object[]{ "Frequency",coupon.getCouponFrequency()});
		 couponModel.insertRow(couponModel.getRowCount(),  new Object[]{ "DayCount",coupon.getDayCount()});
		
		
		 couponModel.insertRow(couponModel.getRowCount(),  new Object[]{ "Coupon Currency ",coupon.getCCY()});
		 couponModel.insertRow(couponModel.getRowCount(),  new Object[]{ "Coupon Rate ",coupon.getFixedRate()});
		 */

		// - couponModel.insertRow(couponModel.getRowCount(), new Object[]{
		// "Coupon Days", ""});

		// nominal.setText(Double.toString(product.getFaceValue()));

		// tradevalue.tcurrency.setSelectedItem(coupon.getCCY());
		// tradevalue.tsettlement.setText(coupon.get)

	}

	@Override
	public void setPanelValue(CommonPanel tradeValue) {
		// TODO Auto-generated method stub
		tradevalue = (REPOTradePanel) tradeValue;
	}

	public REPOPricing getPricing() {
		return pricing;
	}

	public void setPricing(REPOPricing pricing) {
		this.pricing = pricing;
	}

	@Override
	public Pricer getPricer() {
		// TODO Auto-generated method stub
		return getPricing();
	}

	@Override
	public void setTradePanelValue(CommonPanel tradeValue) {
		// TODO Auto-generated method stub
		tradeValue = (MMTradePanel) tradeValue;
	}

	private void processCouponTable(String name, String value) {
		// TODO Auto-generated method stub
		int rowCount = couponModel.getRowCount();
		for (int i = 0; i < rowCount; i++) {
			// System.out.println((String) couponModel.getValueAt(i, 0));
			if (((String) couponModel.getValueAt(i, 0)).equalsIgnoreCase(name)) {
				couponModel.setValueAt(value, i, 1);
				break;
			}

		}

		jTable3.setModel(couponModel);
		jTable3.repaint();
	}

	@Override
	public Collection getCashFlows() {
		// TODO Auto-generated method stub
		return cashFlows;
	}

	public void setCashFlows2(Vector collateralCashFlows) {
		this.collateralCashFlows = collateralCashFlows;
	}

	public Collection getCashFlows2() {
		// TODO Auto-generated method stub
		return collateralCashFlows;
	}

	public void setCashFlows(Vector cashFlows) {
		this.cashFlows = cashFlows;
	}

	private void processPriceTable(String name, String value) {
		// TODO Auto-generated method stub
		int rowCount = tradePrice.getRowCount();
		for (int i = 0; i < rowCount; i++) {
			// System.out.println((String) tradePrice.getValueAt(i, 0));
			if (((String) tradePrice.getValueAt(i, 0)).equalsIgnoreCase(name)) {
				tradePrice.setValueAt(value, i, 1);
				break;
			}

		}

		jTable2.setModel(tradePrice);
		jTable2.repaint();
	}

	public boolean validateProductData() {
		boolean flag = false;
		String issueDate = (String) jTable1.getValueAt(0, 1);
		if (commonUTIL.isEmpty(issueDate)
				|| (!commonUTIL.isStringDate(issueDate))) {
			commonUTIL.showAlertMessage("Select Issue Date  ");
			return flag;
		}
		String currency = (String) jTable1.getValueAt(3, 1).toString();
		String openTerm = (String) jTable1.getValueAt(4, 1).toString();

		if ((commonUTIL.isEmpty(openTerm))) {
			commonUTIL.showAlertMessage("openTerm is Empty ");
			return flag;
		}
		String endDate = "";
		if (openTerm.equalsIgnoreCase("False")) {
			endDate = jTable1.getValueAt(1, 1).toString();
			if (commonUTIL.isEmpty(endDate)
					|| (!commonUTIL.isStringDate(endDate))) {
				commonUTIL.showAlertMessage("Enter EndDate");
				return flag;
			}
			endDate = jTable1.getValueAt(1, 1).toString();
			if (commonUTIL.isEmpty(endDate)
					|| (!commonUTIL.isStringDate(endDate))) {
				commonUTIL.showAlertMessage("Select End Date  ");
				return flag;
			}
			DateU startD = DateU.valueOf(commonUTIL.stringToDate(issueDate,
					true));
			DateU endD = DateU.valueOf(commonUTIL.stringToDate(endDate, true));
			if (endD.lte(startD)) {
				commonUTIL
						.showAlertMessage("End Date  must be greater then Start End");
				return flag;
			}
		}

		String principal = (String) jTable1.getValueAt(2, 1).toString();

		if ((commonUTIL.isEmpty(principal))
				|| (!commonUTIL.isNumeric(principal))) {
			commonUTIL.showAlertMessage("Principal must be  numberic ");
			return flag;
		}

		if ((commonUTIL.isEmpty(currency))) {
			commonUTIL.showAlertMessage("Currency is Empty ");
			return flag;
		}

		String noticePeriod = (String) jTable1.getValueAt(5, 1).toString();

		if ((commonUTIL.isEmpty(noticePeriod))
				|| (!commonUTIL.isNumeric(noticePeriod))) {
			commonUTIL.showAlertMessage("Notice Period must be  numberic ");
			return flag;
		}
		String callabeBy = (String) jTable1.getValueAt(6, 1).toString();

		if ((commonUTIL.isEmpty(callabeBy))) {
			commonUTIL.showAlertMessage("CallabeBy is Empty ");
			return flag;
		}

		String repoType = (String) jTable1.getValueAt(7, 1).toString();

		if ((commonUTIL.isEmpty(repoType))) {
			commonUTIL.showAlertMessage("repoType is Empty ");
			return flag;
		}

		String compoundingFrequency = (String) jTable1.getValueAt(8, 1)
				.toString();

		// coupon.setCouponFrequency(freq);
		String dayCount = jTable3.getValueAt(0, 1).toString();
		if (commonUTIL.isEmpty(dayCount)) {
			commonUTIL.showAlertMessage("Select Day Count ");
			return flag;
		}
		coupon.setDayCount(dayCount);

		String interestType = jTable3.getValueAt(1, 1).toString();
		if (commonUTIL.isEmpty(interestType)) {
			commonUTIL.showAlertMessage("Select Interest Type ");
			return flag;
		}
		coupon.setCouponType(interestType);

		String rate = jTable3.getValueAt(2, 1).toString();
		if ((commonUTIL.isEmpty(rate)) || (!commonUTIL.isNumeric(rate))) {
			commonUTIL.showAlertMessage("Rate must be a numeric value");
			return flag;
		}
		coupon.setFixedRate(Double.valueOf(rate).doubleValue());

		coupon.setTenor(jTable3.getValueAt(5, 1).toString());

		String couponFrequency = jTable3.getValueAt(6, 1).toString();

		coupon.setCouponFrequency(couponFrequency);

		coupon.setBusinessDayConvention(jTable3.getValueAt(7, 1).toString());

		if (DateConstants.getCompareFrequencyCode(compoundingFrequency) > DateConstants
				.getCompareFrequencyCode(couponFrequency)) {
			commonUTIL
					.showAlertMessage("Compounding frequency should be less then Coupon Frequency");
			return flag;
		}
		String ccy = currency;
		if (commonUTIL.isEmpty(ccy)) {
			commonUTIL.showAlertMessage("Select Currency ");
			return flag;
		}
		coupon.setCCY(ccy);
		product.setIssueCurrency(ccy);
		if (collateralProduct == null) {
			commonUTIL.showAlertMessage("Please select Collateral Product");
			return flag;
		}
		// product.set
		if (openTerm.equalsIgnoreCase("True")) {
			product.setMarturityDate(""); // if openTerm is true the
											// marturitydate is not consider
			product.setTenor("2D"); // if openTerm is true then notice period is
									// consider as 2 days.
		} else {
			product.setMarturityDate(endDate);
			product.setTenor("0"); // notice period.
		}

		product.setFaceValue(new Double(principal).doubleValue()); // principal
																	// is
																	// consider
																	// as Face
																	// Value

		if (!coupon.getCouponType().equalsIgnoreCase("Fixed")) {
			String spread = jTable3.getValueAt(3, 1).toString();
			if (commonUTIL.isEmpty(spread) || (!commonUTIL.isNumeric(spread))) {
				commonUTIL.showAlertMessage("Select spread in numbers");
				return flag;
			}
			coupon.setYieldDecimals(new Double(spread).doubleValue()); // yieldDecimals
																		// treated
																		// as
																		// spread
																		// as
																		// don't
																		// want
																		// to
																		// create
																		// unneccessary
																		// columns.
			String index = jTable3.getValueAt(4, 1).toString();
			if (commonUTIL.isEmpty(index)) {
				commonUTIL.showAlertMessage("Select Index");
				return flag;
			}
			coupon.setYieldMethod(index);
			String tenor = jTable3.getValueAt(5, 1).toString();
			if (commonUTIL.isEmpty(tenor)) {
				commonUTIL.showAlertMessage("Select tenor");
				return flag;
			}
			coupon.setTenor(tenor);

		}

		if (collateralProduct == null) {
			commonUTIL.showAlertMessage("Select Collateral");
			return flag;
		}
		product.setCollateralID(collateralProduct.getId());
		product.setCallType(callabeBy);
		product.setRepoType(repoType);
		// coupon.setQuoteType(quoteType)
		// product.setTenor(tenor);

		product.setIssueDate(issueDate);
		product.setMarturityDate(endDate);
		product.setProductType("REPO");
		product.setProdcutShortName("REPO." + buysell.getText());
		product.setProductname(product.getProdcutShortName() + "."
				+ product.getCallType() + "." + product.getIssueDate() + "."
				+ product.getMarturityDate() + "." + product.getIssueCurrency()
				+ "." + (coupon.getFixedRate() / 100) + "."
				+ coupon.getCouponType().substring(0, 2));
		// product.setId(0);
		productName = product.getProductname();
		flag = true;
		return flag;
		// product.set

	}

	private void processTableData(DefaultTableModel model) {
		// TODO Auto-generated method stub
		Vector vector;
		try {
			vector = (Vector) referenceData.getStartUPData("TradePrice");
			Iterator it = vector.iterator();
			int i = 0;
			while (it.hasNext()) {

				StartUPData tradeAttributes = (StartUPData) it.next();

				model.insertRow(i, new Object[] { tradeAttributes.getName(),
						" " });
				i++;
			}

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void calculatePrice(REPOPricing price, Trade trade, Product product,
			Coupon coupon) {

		pricing.price(trade, product, coupon);

		RepoCashFlow cashFlow = (RepoCashFlow) pricing.genearteCashFlow();

		setCashFlows(cashFlow.getFlows());

		pricing.setTradeData(cashFlow);

		setPricing(pricing);

		Pricer collateralPricing = PricerFactory
				.getProductPricer(collateralProduct.getProductType());

		Vector<Coupon> collateralCoupon = null;
		try {

			collateralCoupon = (Vector<Coupon>) remoteProduct.getCoupon(product
					.getCollateralID());

		} catch (RemoteException e) {

			e.printStackTrace();

		}

		// System.out.println("------ " + collateralCoupon.get(0).getId() );
		collateralPricing.price(trade, collateralProduct,
				collateralCoupon.get(0));

		DefaultCashFlow collateralCashFlow = CashFlowFactory
				.getProductCashflow(collateralProduct.getProductType());

		collateralCashFlow = (DefaultCashFlow) collateralPricing
				.genearteCashFlow();

		setCashFlows2(collateralCashFlow.getFlows());

		processPriceTable("DirtyPrice",
				commonUTIL.doubleFormat(pricing.getDirtyPrice()));
		processPriceTable("CleanPrice",
				commonUTIL.doubleFormat(pricing.getCleanPrice()));
		processPriceTable("TotalAmount",
				commonUTIL.doubleFormat(pricing.getTotalAmount()));
		processPriceTable("Quantity",
				commonUTIL.doubleFormat(pricing.getQuantity()));

		tradevalue.tradeyield.setText(commonUTIL.doubleFormat(pricing
				.getYield()));
		nominal.setText(commonUTIL.doubleFormat(pricing.getQuantity()));

	}

	private String productName = "";
	private JTextField jTextField0;
	private JLabel jLabel0;
	private JButton priceButton;
	private static final String PREFERRED_LOOK_AND_FEEL = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
}

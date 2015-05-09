package apps.window.tradewindow.cashflowpanel;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

import util.commonUTIL;
import beans.Flows;

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

import constants.MMConstants;

public class MMCashFlowPanel extends CashFlowPanel {
	static DefaultTableModel cashFlowsModel = new DefaultTableModel();

	public MMCashFlowPanel() {
		initComponents();
	}

	void initJide() {
		// Initialize JIDE settings.
		// JIDE Licenses Verification

		LookAndFeelFactory.installDefaultLookAndFeelAndExtension();

		// ---------------------------------------------------------------------
		// JIDE ObjectConverterManager.initDefaultConverter
		CellEditorManager.initDefaultEditor();
		CellRendererManager.initDefaultRenderer();
		ObjectConverterManager.initDefaultConverter();
		ObjectGrouperManager.initDefaultGrouper();

		// Note: Apply special converters to display class in something useful
		// to the user.
		ObjectConverterManager.registerConverter(Date.class,
				new DateConverter(), DateConverter.DATE_CONTEXT);
		ObjectConverterManager.registerConverter(Date.class,
				new DateConverter(), DateConverter.DATETIME_CONTEXT);
		ObjectConverterManager.registerConverter(boolean.class,
				new BooleanConverter());
		ObjectConverterManager.registerConverter(Boolean.class,
				new BooleanConverter());

		// Special formatting of integers
		DecimalFormat myFormatter = new DecimalFormat("############.##"); // removes
																			// the
																			// thousand
																			// separator
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
		ObjectConverterManager.registerConverter(Date.class,
				new YearNameConverter(), YearNameConverter.CONTEXT);
		ObjectConverterManager.registerConverter(Date.class,
				new QuarterNameConverter(), QuarterNameConverter.CONTEXT);
		ObjectConverterManager.registerConverter(Date.class,
				new MonthNameConverter(), MonthNameConverter.CONTEXT);
	}

	private void initComponents() {
		initJide();
		jPanel1 = new javax.swing.JPanel();
		jScrollPane1 = new javax.swing.JScrollPane();
		jTable1 = new javax.swing.JTable();

		jTable1.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] { { null, null, null, null },
						{ null, null, null, null }, { null, null, null, null },
						{ null, null, null, null } }, new String[] { "#",
						"Amount", "interest", "Fixed Payment Amount",
						"Payment Date", "Type", }));
		jScrollPane1.setViewportView(jTable1);

		javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(
				this);
		this.setLayout(jPanel2Layout);
		jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				jPanel2Layout
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(jScrollPane1,
								javax.swing.GroupLayout.DEFAULT_SIZE, 870,
								Short.MAX_VALUE).addContainerGap()));
		jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				jPanel2Layout
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(jScrollPane1,
								javax.swing.GroupLayout.DEFAULT_SIZE, 207,
								Short.MAX_VALUE).addContainerGap()));

	}// </editor-fold>

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String args[]) {
		/* Set the Nimbus look and feel */
		// <editor-fold defaultstate="collapsed"
		// desc=" Look and feel setting code (optional) ">
		/*
		 * If Nimbus (introduced in Java SE 6) is not available, stay with the
		 * default look and feel. For details see
		 * http://download.oracle.com/javase
		 * /tutorial/uiswing/lookandfeel/plaf.html
		 */
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager
					.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(CashFlowPanel.class.getName())
					.log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(CashFlowPanel.class.getName())
					.log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(CashFlowPanel.class.getName())
					.log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(CashFlowPanel.class.getName())
					.log(java.util.logging.Level.SEVERE, null, ex);
		}
		// </editor-fold>

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {

			public void run() {
				new MMCashFlowPanel().setVisible(true);
			}
		});
	}

	// Variables declaration - do not modify
	private javax.swing.JPanel jPanel1;
	private javax.swing.JScrollPane jScrollPane1;
	private static javax.swing.JTable jTable1;

	// End of variables declaration
public static DefaultTableModel getCashFlow(Vector cashFlows) {
	String cols[];
    if(cashFlows == null) {
    	cols = new String[] { "#", "TradeAmount", "CpnStartDate",
				"CpnEndDate", "CmpdStartDate",
				"CmpdEndDate", "PaymentDate", "CpnDays", "Rate", "Interest",  "Type",
				"PaymentAmt", };
    	cashFlowsModel = new DefaultTableModel(null, cols);
    	return cashFlowsModel;
    	
    }
	Flows flow0 = (Flows) cashFlows.elementAt(0);
	if (flow0.getAmortizationType().equalsIgnoreCase(
			MMConstants.MORTGAGEAMORTYPE)
			|| flow0.getAmortizationType().equalsIgnoreCase(
					MMConstants.SCHEDULEAMORTYPE)) {

		 cols = new String[] { "#", "Coupon Start Date", "Coupon End Date",
				"o/s Amount", "Interest", "Fixed Payment Amount",
				"Balance Amount", "Payment Date", "Type", };

		cashFlowsModel = new DefaultTableModel(null, cols);
		if (cashFlows != null && (!cashFlows.isEmpty())) {
			for (int i = 0; i < cashFlows.size(); i++) {
				Flows flow = (Flows) cashFlows.elementAt(i);

				cashFlowsModel
						.insertRow(
								i,
								new Object[] {
										i + 1,
										commonUTIL.getDateFormat(flow
												.getStartDate()),
										commonUTIL.getDateFormat(flow
												.getEndDate()),
										flow.getOutstandingAmount(),
										flow.getInterest(),
										flow.getFixedAmountPayment(),
										flow.getBalanceAmount(),
										commonUTIL.getDateFormat(flow
												.getPaymentDate()),
										flow.getType(), });
			}

		}

	} else if (flow0.getAmortizationType().equalsIgnoreCase(MMConstants.SIMPLECOMPOUNDINGAMORTYPE)) {
		
		cols = new String[] { "#", "TradeAmount", "CpnStartDate",
				"CpnEndDate", "CmpdStartDate",
				"CmpdEndDate", "PaymentDate", "CpnDays", "Rate", "Interest",  "Type",
				"PaymentAmt", };
		cashFlowsModel = new DefaultTableModel(null, cols);

		if (cashFlows != null && (!cashFlows.isEmpty())) {
			for (int i = 0; i < cashFlows.size(); i++) {
				Flows flow = (Flows) cashFlows.elementAt(i);

				cashFlowsModel
						.insertRow(
								i,
								new Object[] {
										i + 1,
										commonUTIL.doubleFormat(flow.getNominal()),
										commonUTIL.getDateFormat(flow
												.getStartDate()),
										commonUTIL.getDateFormat(flow
												.getEndDate()),
										commonUTIL.getDateFormat(flow
												.getCmpdStartDate()),
										commonUTIL.getDateFormat(flow
												.getCmpdEnddate()),
												commonUTIL.getDateFormat(flow
														.getPaymentDate()),
										flow.getAccuralDays(),
										flow.getRate(),
										commonUTIL.doubleFormat(flow
												.getInterest()),
										flow.getType(),
										commonUTIL.doubleFormat(flow
												.getCouponAmount()), });
			}

		}
	} else {

		cols = new String[] { "#", "TradeAmount", "CpnStartDate",
				"CpnEndDate", "PaymentDate", "CpnDays", "Rate", "Type",
				"PaymentAmt", };

		cashFlowsModel = new DefaultTableModel(null, cols);

		if (cashFlows != null && (!cashFlows.isEmpty())) {
			for (int i = 0; i < cashFlows.size(); i++) {
				Flows flow = (Flows) cashFlows.elementAt(i);

				cashFlowsModel
						.insertRow(
								i,
								new Object[] {
										i + 1,
										commonUTIL.doubleFormat(flow.getNominal()),
										commonUTIL.getDateFormat(flow
												.getStartDate()),
										commonUTIL.getDateFormat(flow
												.getEndDate()),
										commonUTIL.getDateFormat(flow
												.getPaymentDate()),
										commonUTIL.doubleFormat(flow
												.getFlowsdays()),
										flow.getRate(),
										flow.getType(),
										commonUTIL.doubleFormat(flow
												.getCouponAmount()), });
			}

		}

	}

	//jTable1.removeAll();
	//jTable1.setModel(cashFlowsModel);
	return cashFlowsModel;

}


	public  void setCashFlows(Vector cashFlows) {
		String cols[];

		Flows flow0 = (Flows) cashFlows.elementAt(0);
		

		if (flow0.getAmortizationType().equalsIgnoreCase(
				MMConstants.MORTGAGEAMORTYPE)
				|| flow0.getAmortizationType().equalsIgnoreCase(
						MMConstants.SCHEDULEAMORTYPE)) {

			cols = new String[] { "#", "Coupon Start Date", "Coupon End Date",
					"o/s Amount", "Interest", "Fixed Payment Amount",
					"Balance Amount", "Payment Date", "Type", };

			cashFlowsModel = new DefaultTableModel(null, cols);

			if (cashFlows != null && (!cashFlows.isEmpty())) {
				for (int i = 0; i < cashFlows.size(); i++) {
					Flows flow = (Flows) cashFlows.elementAt(i);

					cashFlowsModel
							.insertRow(
									i,
									new Object[] {
											i + 1,
											commonUTIL.getDateFormat(flow
													.getStartDate()),
											commonUTIL.getDateFormat(flow
													.getEndDate()),
											flow.getOutstandingAmount(),
											flow.getInterest(),
											flow.getFixedAmountPayment(),
											flow.getBalanceAmount(),
											commonUTIL.getDateFormat(flow
													.getPaymentDate()),
											flow.getType(), });
				}

			}

		} else if (flow0.getAmortizationType().equalsIgnoreCase(MMConstants.SIMPLECOMPOUNDINGAMORTYPE)) {
			
			cols = new String[] { "#", "TradeAmount", "CpnStartDate",
					"CpnEndDate", "CmpdStartDate",
					"CmpdEndDate", "PaymentDate", "CpnDays", "Rate", "Interest",  "Type",
					"PaymentAmt", };
			cashFlowsModel = new DefaultTableModel(null, cols);

			if (cashFlows != null && (!cashFlows.isEmpty())) {
				for (int i = 0; i < cashFlows.size(); i++) {
					Flows flow = (Flows) cashFlows.elementAt(i);

					cashFlowsModel
							.insertRow(
									i,
									new Object[] {
											i + 1,
											commonUTIL.doubleFormat(flow.getNominal()),
											commonUTIL.getDateFormat(flow
													.getStartDate()),
											commonUTIL.getDateFormat(flow
													.getEndDate()),
											commonUTIL.getDateFormat(flow
													.getCmpdStartDate()),
											commonUTIL.getDateFormat(flow
													.getCmpdEnddate()),
													commonUTIL.getDateFormat(flow
															.getPaymentDate()),
											flow.getAccuralDays(),
											flow.getRate(),
											commonUTIL.doubleFormat(flow
													.getInterest()),
											flow.getType(),
											commonUTIL.doubleFormat(flow
													.getCouponAmount()), });
				}

			}
		} else {

			cols = new String[] { "#", "TradeAmount", "CpnStartDate",
					"CpnEndDate", "PaymentDate", "CpnDays", "Rate", "Type",
					"PaymentAmt", };

			cashFlowsModel = new DefaultTableModel(null, cols);

			if (cashFlows != null && (!cashFlows.isEmpty())) {
				for (int i = 0; i < cashFlows.size(); i++) {
					Flows flow = (Flows) cashFlows.elementAt(i);

					cashFlowsModel
							.insertRow(
									i,
									new Object[] {
											i + 1,
											commonUTIL.doubleFormat(flow.getNominal()),
											commonUTIL.getDateFormat(flow
													.getStartDate()),
											commonUTIL.getDateFormat(flow
													.getEndDate()),
											commonUTIL.getDateFormat(flow
													.getPaymentDate()),
											commonUTIL.doubleFormat(flow
													.getFlowsdays()),
											flow.getRate(),
											flow.getType(),
											commonUTIL.doubleFormat(flow
													.getCouponAmount()), });
				}

			}

		}

		jTable1.removeAll();
		jTable1.setModel(cashFlowsModel);

	}

	@Override
	public DefaultTableModel getCashFlows(Vector cashFlows) {
		// TODO Auto-generated method stub
		return getCashFlow(cashFlows);
	}
}

package apps.window.tradewindow.cashflowpanel;

import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;

import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;

import util.commonUTIL;
import beans.Flows;

//VS4E -- DO NOT REMOVE THIS LINE!
public class REPOCashFlowPanel  extends  CashFlowPanel  {

DefaultTableModel cashFlowsModel = new DefaultTableModel();
DefaultTableModel cashFlowsModel2 = new DefaultTableModel();
	private static final long serialVersionUID = 1L;
	private JPanel jPanel0;
	private JTable jTable0;
	private JScrollPane jScrollPane0;
	private JPanel jPanel1;
	private JTable jTable1;
	private JScrollPane jScrollPane1;
	private JPanel jPanel2;
	private JTabbedPane jTabbedPane0;

	public REPOCashFlowPanel() {
		initComponents();
	}

	private void initComponents() {
		setLayout(new GroupLayout());
		add(getJPanel0(), new Constraints(new Leading(8, 919, 10, 10), new Leading(8, 279, 10, 10)));
		setSize(935, 293);
	}

	private JPanel getJPanel0() {
		if (jPanel0 == null) {
			jPanel0 = new JPanel();
			jPanel0.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			jPanel0.setLayout(new GroupLayout());
			jPanel0.add(getJTabbedPane0(), new Constraints(new Leading(3, 907, 10, 10), new Leading(2, 268, 10, 10)));
		}
		return jPanel0;
	}

	private JTabbedPane getJTabbedPane0() {
		if (jTabbedPane0 == null) {
			jTabbedPane0 = new JTabbedPane();
			jTabbedPane0.addTab("CashFlow", getJPanel1());
			jTabbedPane0.addTab("CollateralCashFlow", getJPanel2());
		}
		return jTabbedPane0;
	}

	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setLayout(new GroupLayout());
			jPanel2.add(getJScrollPane1(), new Constraints(new Leading(5, 889, 10, 10), new Leading(5, 229, 10, 10)));
		}
		return jPanel2;
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
			jTable1.setModel(new javax.swing.table.DefaultTableModel(
			          new Object [][] {
			                  {null, null, null, null},
			                  {null, null, null, null},
			                  {null, null, null, null},
			                  {null, null, null, null}
			              },
			              new String [] {
			                  "CStartDate", "CEndDate", " Accural Amt", "Payment","Price","YTM",
			              }
			          ));
		}
		return jTable1;
	}

	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(new GroupLayout());
			jPanel1.add(getJScrollPane0(), new Constraints(new Leading(6, 889, 10, 10), new Leading(5, 232, 10, 10)));
		}
		return jPanel1;
	}

	private JScrollPane getJScrollPane0() {
		if (jScrollPane0 == null) {
			jScrollPane0 = new JScrollPane();
			jScrollPane0.setViewportView(getJTable0());
		}
		return jScrollPane0;
	}

	private JTable getJTable0() {
		if (jTable0 == null) {
			jTable0 = new JTable();
			jTable0.setModel(new javax.swing.table.DefaultTableModel(
			          new Object [][] {
			                  {null, null, null, null},
			                  {null, null, null, null},
			                  {null, null, null, null},
			                  {null, null, null, null}
			              },
			              new String [] {
			                  "CStartDate", "CEndDate", " Accural Amt", "Payment","Price","YTM",
			              }
			          ));
		}
		return jTable0;
	}
	public void setCashFlows2(Vector cashFlows) {
		String cols[] = { "TradeAmount", "CpnStartDate", "CpnEndDate",
				"PaymentDate", "CpnDays", "Rate", "Type", "PaymentAmt",
				};
		cashFlowsModel = new DefaultTableModel(null, cols);
		if (cashFlows != null && (!cashFlows.isEmpty())) {
			for (int i = 0; i < cashFlows.size(); i++) {
				Flows flow = (Flows) cashFlows.elementAt(i);
				cashFlowsModel
						.insertRow(
								i,
								new Object[] {
										flow.getNominal(),
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
												.getCouponAmount())
										 });
			}

		}
		jTable1.removeAll();
		jTable1.setModel(cashFlowsModel);
		
		
  }
	public void setCashFlows(Vector cashFlows) {
			
		String cols[] = { "TradeAmount", "CpnStartDate", "CpnEndDate",
				"CmpdStartDate",
				"CmpdEndDate",
				"PaymentDate", "CpnDays", "Rate", 
				"Interest",
				"Type", "PaymentAmt",
				};
		cashFlowsModel = new DefaultTableModel(null, cols);
		if (cashFlows != null && (!cashFlows.isEmpty())) {
			for (int i = 0; i < cashFlows.size(); i++) {
				Flows flow = (Flows) cashFlows.elementAt(i);
				cashFlowsModel
						.insertRow(
								i,
								new Object[] {
										flow.getNominal(),
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
										commonUTIL.doubleFormat(flow
												.getAccuralDays()),
										flow.getRate(),
										commonUTIL.doubleFormat(flow
												.getInterest()),
										flow.getType(),
										commonUTIL.doubleFormat(flow
												.getCouponAmount())
										 });
			}

		}
		jTable0.removeAll();
		jTable0.setModel(cashFlowsModel);
		
		
  }
}

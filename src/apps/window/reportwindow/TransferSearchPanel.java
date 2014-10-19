package apps.window.reportwindow;

import java.awt.Font;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;

import beans.FilterBean;
import beans.UserJobsDetails;

//VS4E -- DO NOT REMOVE THIS LINE!
public class TransferSearchPanel extends SearchCriteriaType {

	private static final long serialVersionUID = 1L;
	private JLabel jLabel2;
	private JLabel jLabel1;
	private JLabel jLabel0;
	private JLabel jLabel3;
	private JLabel jLabel4;
	private JLabel jLabel5;
	private JTextField TradeID;
	private JTextField EndDate;
	private JLabel jLabel6;
	private JLabel jLabel7;
	private JLabel jLabel8;
	private JLabel jLabel9;
	private JLabel jLabel10;
	private JLabel jLabel11;
	private JLabel jLabel12;
	private JLabel jLabel13;
	private JLabel jLabel14;
	private JLabel jLabel15;
	private JLabel jLabel16;
	private JTextField POLegalEntity;
	private JTextField counterParty;
	private JTextField StartDate;
	private JTextField action;
	private JTextField LegalEntity;
	private JTextField productType;
	private JTextField status;
	private JTextField currency;
	private JTextField transferType;
	private JTextField nettingType;
	private JTextField eventType;
	private JTextField nettingID;
	private JTextField settlementType;
	private static final String PREFERRED_LOOK_AND_FEEL = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
	public TransferSearchPanel() {
		initComponents();
	}

	private void initComponents() {
		setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
		setFocusable(true);
		setEnabled(true);
		setVisible(true);
		setVerifyInputWhenFocusTarget(true);
		setDoubleBuffered(true);
		setRequestFocusEnabled(true);
		setOpaque(true);
		setLayout(new GroupLayout());
		add(getJLabel2(), new Constraints(new Leading(-181, 60, 10, 10), new Leading(181, 10, 10)));
		add(getJLabel1(), new Constraints(new Leading(10, 52, 10, 10), new Leading(9, 12, 12)));
		add(getJLabel3(), new Constraints(new Leading(10, 52, 10, 10), new Leading(41, 12, 12)));
		add(getJLabel5(), new Constraints(new Leading(10, 58, 12, 12), new Leading(73, 12, 12)));
		add(getTradeID(), new Constraints(new Leading(264, 96, 10, 10), new Leading(12, 12, 12)));
		add(getEndDate(), new Constraints(new Leading(264, 96, 12, 12), new Leading(41, 12, 12)));
		add(getPOLegalEntity(), new Constraints(new Leading(264, 96, 12, 12), new Leading(73, 12, 12)));
		add(getJLabel7(), new Constraints(new Leading(10, 70, 10, 10), new Leading(105, 12, 12)));
		add(getJLabel9(), new Constraints(new Leading(8, 70, 10, 10), new Leading(145, 12, 12)));
		add(getJLabel10(), new Constraints(new Leading(8, 70, 12, 12), new Leading(177, 12, 12)));
		add(getJLabel11(), new Constraints(new Leading(8, 70, 12, 12), new Leading(211, 12, 12)));
		add(getJLabel12(), new Constraints(new Leading(10, 70, 12, 12), new Leading(243, 12, 12)));
		add(getJLabel13(), new Constraints(new Leading(10, 70, 12, 12), new Leading(272, 17, 10, 10)));
		add(getJLabel14(), new Constraints(new Leading(8, 84, 12, 12), new Leading(307, 12, 12)));
		add(getJLabel16(), new Constraints(new Leading(10, 84, 12, 12), new Leading(341, 10, 10)));
		add(getStartDate(), new Constraints(new Leading(95, 98, 12, 12), new Leading(41, 12, 12)));
		add(getJLabel0(), new Constraints(new Leading(201, 12, 12), new Leading(12, 12, 12)));
		add(getJLabel8(), new Constraints(new Leading(201, 52, 12, 12), new Leading(111, 12, 12)));
		add(getJLabel6(), new Constraints(new Leading(201, 52, 12, 12), new Leading(76, 12, 12)));
		add(getJLabel4(), new Constraints(new Leading(201, 52, 12, 12), new Leading(44, 12, 12)));
		add(getJLabel15(), new Constraints(new Leading(201, 52, 12, 12), new Leading(145, 12, 12)));
		add(getAction(), new Constraints(new Leading(95, 98, 12, 12), new Leading(12, 12, 12)));
		add(getLegalEntity(), new Constraints(new Leading(95, 98, 12, 12), new Leading(73, 12, 12)));
		add(getProductType(), new Constraints(new Leading(95, 98, 10, 10), new Leading(105, 12, 12)));
		add(getStatus(), new Constraints(new Leading(95, 98, 12, 12), new Leading(142, 12, 12)));
		add(getCurrency(), new Constraints(new Leading(264, 98, 12, 12), new Leading(107, 12, 12)));
		add(getTransferType(), new Constraints(new Leading(95, 98, 12, 12), new Leading(176, 12, 12)));
		add(getNettingType(), new Constraints(new Leading(95, 98, 12, 12), new Leading(208, 12, 12)));
		add(getEventType(), new Constraints(new Leading(95, 98, 12, 12), new Leading(240, 12, 12)));
		add(getNettingID(), new Constraints(new Leading(95, 98, 12, 12), new Leading(269, 12, 12)));
		add(getSettlementType(), new Constraints(new Leading(95, 98, 12, 12), new Leading(307, 12, 12)));
		setSize(420, 537);
	}

	private JTextField getJTextField0() {
		if (settlementType == null) {
			settlementType = new JTextField();
		}
		return settlementType;
	}

	private JTextField getSettlementType() {
		if (settlementType == null) {
			settlementType = new JTextField();
			settlementType.setText("        ");
		}
		return settlementType;
	}
	private JTextField getNettingID() {
		if (nettingID == null) {
			nettingID = new JTextField();
			nettingID.setText("        ");
		}
		return nettingID;
	}

	private JTextField getNettingType() {
		if (nettingType == null) {
			nettingType = new JTextField();
		}
		return nettingType;
	}
	private JTextField getEventType() {
		if (eventType == null) {
			eventType = new JTextField();
		}
		return eventType;
	}

	private JTextField getCurrency() {
		if (currency == null) {
			currency = new JTextField();
		}
		return currency;
	}


	private JTextField getTransferType() {
		if (transferType == null) {
			transferType = new JTextField();
		}
		return transferType;
	}

	private JTextField getStatus() {
		if (status == null) {
			status = new JTextField();
		}
		return status;
	}

	private JTextField getProductType() {
		if (productType == null) {
			productType = new JTextField();
		}
		return productType;
	}

	private JTextField getLegalEntity() {
		if (LegalEntity == null) {
			LegalEntity = new JTextField();
		}
		return LegalEntity;
	}

	private JTextField getAction() {
		if (action == null) {
			action = new JTextField();
			
		}
		return action;
	}
	private JTextField getEndDate() {
		if (EndDate == null) {
			EndDate = new JTextField();
			
		}
		return EndDate;
	}
	private JTextField getPOLegalEntity() {
		if (POLegalEntity == null) {
			POLegalEntity = new JTextField();
			
		}
		return POLegalEntity;
	}
	
	private JLabel getJLabel16() {
		if (jLabel16 == null) {
			jLabel16 = new JLabel();
			jLabel16.setText("Agent");
		}
		return jLabel16;
	}

	private JLabel getJLabel15() {
		if (jLabel15 == null) {
			jLabel15 = new JLabel();
			jLabel15.setText("Action");
		}
		return jLabel15;
	}

	private JLabel getJLabel14() {
		if (jLabel14 == null) {
			jLabel14 = new JLabel();
			jLabel14.setText("Settlement Type");
		}
		return jLabel14;
	}

	private JLabel getJLabel13() {
		if (jLabel13 == null) {
			jLabel13 = new JLabel();
			jLabel13.setText("Netting ID");
		}
		return jLabel13;
	}

	private JLabel getJLabel12() {
		if (jLabel12 == null) {
			jLabel12 = new JLabel();
			jLabel12.setText("Event Type");
		}
		return jLabel12;
	}

	private JLabel getJLabel11() {
		if (jLabel11 == null) {
			jLabel11 = new JLabel();
			jLabel11.setText("Netting Type");
		}
		return jLabel11;
	}

	private JLabel getJLabel10() {
		if (jLabel10 == null) {
			jLabel10 = new JLabel();
			jLabel10.setText("Transfer Type");
		}
		return jLabel10;
	}

	private JLabel getJLabel9() {
		if (jLabel9 == null) {
			jLabel9 = new JLabel();
			jLabel9.setText("Status");
		}
		return jLabel9;
	}

	private JLabel getJLabel8() {
		if (jLabel8 == null) {
			jLabel8 = new JLabel();
			jLabel8.setText("Currency");
		}
		return jLabel8;
	}

	private JLabel getJLabel7() {
		if (jLabel7 == null) {
			jLabel7 = new JLabel();
			jLabel7.setText("ProductType");
		}
		return jLabel7;
	}

	private JLabel getJLabel6() {
		if (jLabel6 == null) {
			jLabel6 = new JLabel();
			jLabel6.setText("PO");
		}
		return jLabel6;
	}

	private JTextField getStartDate() {
		if (StartDate == null) {
			StartDate = new JTextField();
		}
		return StartDate;
	}

	private JTextField getTradeID() {
		if (TradeID == null) {
			TradeID = new JTextField();
		}
		return TradeID;
	}

	private JLabel getJLabel5() {
		if (jLabel5 == null) {
			jLabel5 = new JLabel();
			jLabel5.setText("LegalEntity");
		}
		return jLabel5;
	}

	private JLabel getJLabel4() {
		if (jLabel4 == null) {
			jLabel4 = new JLabel();
			jLabel4.setText("EndDate");
		}
		return jLabel4;
	}

	private JLabel getJLabel3() {
		if (jLabel3 == null) {
			jLabel3 = new JLabel();
			jLabel3.setText("StartDate");
		}
		return jLabel3;
	}

	private JLabel getJLabel0() {
		if (jLabel0 == null) {
			jLabel0 = new JLabel();
			jLabel0.setText("TransferID");
		}
		return jLabel0;
	}

	private JLabel getJLabel1() {
		if (jLabel1 == null) {
			jLabel1 = new JLabel();
			jLabel1.setText("Trade ID");
		}
		return jLabel1;
	}

	private JLabel getJLabel2() {
		if (jLabel2 == null) {
			jLabel2 = new JLabel();
			jLabel2.setFont(new Font("Arial", Font.PLAIN, 9));
			jLabel2.setText("StartDate");
		}
		return jLabel2;
	}

	@Override
	public Vector<FilterBean> searchCriteria() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clearllCriterial() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadFilters(Vector<UserJobsDetails> jobdetails) {
		// TODO Auto-generated method stub
		
	}

}

package apps.window.reportwindow;

import java.awt.Font;
import java.util.Date;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;

import util.commonUTIL;
import beans.FilterBean;
import beans.UserJobsDetails;

import com.jidesoft.combobox.DateComboBox;

//VS4E -- DO NOT REMOVE THIS LINE!
public class TransferSearchPanel extends SearchCriteriaType {

	private static final long serialVersionUID = 1L;
	private JLabel jLabel2;
	private JLabel jLabel1;
	private JLabel jLabel0;
	private JLabel jLabel3;
	private JLabel jLabel4;
	private JLabel jLabel5;
	private JTextField TransferId;
	private DateComboBox EndDate;
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
	private JTextField counterParty;
	private DateComboBox StartDate;
	private JTextField tradeId;
	private JTextField nettingID;
	private JLabel jLabel17;
	private JComboBox leComboBox;
	private JComboBox poComboBOx;
	private JComboBox currencyComboBox;
	private JComboBox productTypeComboBox;
	private JComboBox statusComboBox;
	private JComboBox actionComboBox;
	private JComboBox transferTypeComboBox;
	private JComboBox nettingTypeComboBox;
	private JComboBox eventTypeComboBox;

	private JComboBox settlementTypeComboBox;
	private JComboBox agentComboBox;

	
	javax.swing.DefaultComboBoxModel<String> legalEntityData = new javax.swing.DefaultComboBoxModel<String>();
	javax.swing.DefaultComboBoxModel<String> poData = new javax.swing.DefaultComboBoxModel<String>();
	javax.swing.DefaultComboBoxModel<String> agentData = new javax.swing.DefaultComboBoxModel<String>();
	javax.swing.DefaultComboBoxModel<String> bookAttributesData = new javax.swing.DefaultComboBoxModel<String>();
	javax.swing.DefaultComboBoxModel<String> currencyAttributeData = new javax.swing.DefaultComboBoxModel<String>();
	javax.swing.DefaultComboBoxModel<String> actionAttributeData = new javax.swing.DefaultComboBoxModel<String>();
	javax.swing.DefaultComboBoxModel<String> statusAttributeData = new javax.swing.DefaultComboBoxModel<String>();
	javax.swing.DefaultComboBoxModel<String> productTypeAttributeData = new javax.swing.DefaultComboBoxModel<String>();
	javax.swing.DefaultComboBoxModel<String> transferTypeAttributeData = new javax.swing.DefaultComboBoxModel<String>();
	javax.swing.DefaultComboBoxModel<String> eventTypeAttributeData = new javax.swing.DefaultComboBoxModel<String>();
	
	private JLabel jLabel18;
	private JLabel jLabel19;
	private JLabel jLabel20;
	private JComboBox bookComboBox;
	private static final String PREFERRED_LOOK_AND_FEEL = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
	
	public TransferSearchPanel() {
		init();
		initComponents();
	}
	
	private void init() {
		
		processLEDataCombo1(legalEntityData,  counterPartyID, "CounterParty");
		processLEDataCombo1(poData,  poID, "PO");
		processLEDataCombo1(agentData,  agentID, "Agent");
		
		processDomainData(bookAttributesData,  getFilterValues().getDomainValues("BookAttributes"));
		processDomainData(currencyAttributeData,  getFilterValues().getDomainValues("Currency"));
		processDomainData(statusAttributeData,  getFilterValues().getDomainValues("Status"));
		processDomainData(actionAttributeData,  getFilterValues().getDomainValues("Action"));
		processDomainData(productTypeAttributeData,  getFilterValues().getDomainValues("ProductType"));
		processDomainData(transferTypeAttributeData,  getFilterValues().getDomainValues("TransferType"));
		processDomainData(eventTypeAttributeData,  getFilterValues().getDomainValues("EventType"));
		
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
		add(getTransferId(), new Constraints(new Leading(264, 96, 10, 10), new Leading(12, 12, 12)));
		add(getJLabel8(), new Constraints(new Leading(201, 52, 12, 12), new Leading(111, 12, 12)));
		add(getTradeId(), new Constraints(new Leading(95, 98, 12, 12), new Leading(12, 12, 12)));
		add(getNettingID(), new Constraints(new Leading(95, 98, 12, 12), new Leading(269, 12, 12)));
		add(getJLabel1(), new Constraints(new Leading(8, 52, 12, 12), new Leading(18, 12, 12)));
		add(getJLabel0(), new Constraints(new Leading(201, 12, 12), new Leading(15, 12, 12)));
		add(getJLabel9(), new Constraints(new Leading(8, 70, 12, 12), new Leading(149, 12, 12)));
		add(getJLabel3(), new Constraints(new Leading(8, 52, 12, 12), new Leading(47, 12, 12)));
		add(getJLabel15(), new Constraints(new Leading(201, 52, 12, 12), new Leading(149, 12, 12)));
		add(getJLabel7(), new Constraints(new Leading(10, 70, 12, 12), new Leading(108, 19, 12, 12)));
		add(getJLabel17(), new Constraints(new Leading(7, 59, 10, 10), new Leading(79, 12, 12)));
		add(getLeComboBox(), new Constraints(new Leading(95, 98, 12, 12), new Leading(73, 12, 12)));
		add(getPOComboBOx(), new Constraints(new Leading(264, 96, 12, 12), new Leading(73, 12, 12)));
		add(getJLabel6(), new Constraints(new Leading(201, 52, 12, 12), new Leading(79, 12, 12)));
		add(getCurrencyComboBox(), new Constraints(new Leading(264, 96, 12, 12), new Leading(105, 12, 12)));
		add(getProductTypeComboBox(), new Constraints(new Leading(95, 98, 12, 12), new Leading(104, 12, 12)));
		add(getStatusComboBox(), new Constraints(new Leading(96, 98, 12, 12), new Leading(142, 12, 12)));
		add(getActionComboBox(), new Constraints(new Leading(264, 96, 12, 12), new Leading(142, 12, 12)));
		add(getTransferTypeComboBox(), new Constraints(new Leading(96, 98, 12, 12), new Leading(174, 12, 12)));
		add(getJLabel10(), new Constraints(new Leading(7, 70, 12, 12), new Leading(179, 12, 12)));
		add(getNettingTypeComboBox(), new Constraints(new Leading(95, 98, 12, 12), new Leading(206, 12, 12)));
		add(getJLabel11(), new Constraints(new Leading(7, 70, 12, 12), new Leading(212, 12, 12)));
		add(getEventTypeComboBox(), new Constraints(new Leading(96, 96, 12, 12), new Leading(238, 12, 12)));
		add(getJLabel12(), new Constraints(new Leading(7, 70, 12, 12), new Leading(244, 12, 12)));
		add(getJLabel13(), new Constraints(new Leading(7, 70, 12, 12), new Leading(272, 17, 12, 12)));
		add(getSettlementTypeComboBox(), new Constraints(new Leading(93, 98, 12, 12), new Leading(299, 10, 10)));
		add(getAgentComboBox(), new Constraints(new Leading(93, 98, 12, 12), new Leading(327, 12, 12)));
		add(getStartDate(), new Constraints(new Leading(95, 98, 12, 12), new Leading(41, 20, 12, 12)));
		add(getEndDate(), new Constraints(new Leading(264, 96, 12, 12), new Leading(41, 20, 12, 12)));
		add(getJLabel4(), new Constraints(new Leading(203, 52, 10, 10), new Leading(47, 12, 12)));
		add(getJLabel14(), new Constraints(new Leading(7, 84, 12, 12), new Leading(301, 20, 12, 12)));
		add(getJLabel16(), new Constraints(new Leading(8, 84, 12, 12), new Leading(330, 12, 12)));
		add(getJLabel18(), new Constraints(new Leading(8, 12, 12), new Leading(424, 12, 12)));
		add(getJLabel19(), new Constraints(new Leading(4, 12, 12), new Leading(395, 10, 10)));
		add(getJLabel20(), new Constraints(new Leading(8, 12, 12), new Leading(365, 12, 12)));
		add(getBookComboBox(), new Constraints(new Leading(93, 98, 12, 12), new Leading(359, 12, 12)));
		setSize(420, 537);
	}

	private JComboBox<String> getBookComboBox() {
		if (bookComboBox == null) {
			bookComboBox = new JComboBox<String>();
			bookComboBox.setModel(bookAttributesData);
		}
		return bookComboBox;
	}

	private JLabel getJLabel20() {
		if (jLabel20 == null) {
			jLabel20 = new JLabel();
			jLabel20.setText("Book");
		}
		return jLabel20;
	}

	private JLabel getJLabel19() {
		if (jLabel19 == null) {
			jLabel19 = new JLabel();
			jLabel19.setText("Product SubType");
		}
		return jLabel19;
	}

	private JLabel getJLabel18() {
		if (jLabel18 == null) {
			jLabel18 = new JLabel();
			jLabel18.setText("Method");
		}
		return jLabel18;
	}

	private JComboBox getAgentComboBox() {
		if (agentComboBox == null) {
			agentComboBox = new JComboBox();
			agentComboBox.setModel(agentData);
		}
		return agentComboBox;
	}

	private JComboBox getSettlementTypeComboBox() {
		if (settlementTypeComboBox == null) {
			settlementTypeComboBox = new JComboBox();
			settlementTypeComboBox.setModel(new DefaultComboBoxModel(new Object[] {}));
		}
		return settlementTypeComboBox;
	}

	private JComboBox getEventTypeComboBox() {
		if (eventTypeComboBox == null) {
			eventTypeComboBox = new JComboBox();
			eventTypeComboBox.setModel(eventTypeAttributeData);
		}
		return eventTypeComboBox;
	}

	private JComboBox getNettingTypeComboBox() {
		if (nettingTypeComboBox == null) {
			nettingTypeComboBox = new JComboBox();
			nettingTypeComboBox.setModel(new DefaultComboBoxModel(new Object[] {}));
		}
		return nettingTypeComboBox;
	}

	private JComboBox getTransferTypeComboBox() {
		if (transferTypeComboBox == null) {
			transferTypeComboBox = new JComboBox();
			transferTypeComboBox.setModel(transferTypeAttributeData);
		}
		return transferTypeComboBox;
	}

	private JComboBox getActionComboBox() {
		if (actionComboBox == null) {
			actionComboBox = new JComboBox();
			actionComboBox.setModel(actionAttributeData);
		}
		return actionComboBox;
	}

	private JComboBox getStatusComboBox() {
		if (statusComboBox == null) {
			statusComboBox = new JComboBox();
			statusComboBox.setModel(statusAttributeData);
		}
		return statusComboBox;
	}

	private JComboBox getProductTypeComboBox() {
		if (productTypeComboBox == null) {
			productTypeComboBox = new JComboBox();
			productTypeComboBox.setModel(productTypeAttributeData);
		}
		return productTypeComboBox;
	}

	private JComboBox getCurrencyComboBox() {
		if (currencyComboBox == null) {
			currencyComboBox = new JComboBox();
			currencyComboBox.setModel(currencyAttributeData);
		}
		return currencyComboBox;
	}

	private JComboBox getPOComboBOx() {
		if (poComboBOx == null) {
			poComboBOx = new JComboBox();
			poComboBOx.setModel(poData);
		}
		return poComboBOx;
	}

	private JComboBox getLeComboBox() {
		if (leComboBox == null) {
			leComboBox = new JComboBox();
			leComboBox.setModel(legalEntityData);
		}
		return leComboBox;
	}

	private JLabel getJLabel17() {
		if (jLabel17 == null) {
			jLabel17 = new JLabel();
			jLabel17.setText("Counter Party");
		}
		return jLabel17;
	}

	/*private JComboBox getEventTypeComboBox() {
		if (eventTypeComboBox == null) {
			eventTypeComboBox = new JComboBox();
		}
		return eventTypeComboBox;
	}*/

	private JTextField getNettingID() {
		if (nettingID == null) {
			nettingID = new JTextField();
			nettingID.setText("");
		}
		return nettingID;
	}

	private JTextField getTradeId() {
		if (tradeId == null) {
			tradeId = new JTextField();
			
		}
		return tradeId;
	}
	private DateComboBox getEndDate() {
		if (EndDate == null) {
			EndDate = new DateComboBox();
			EndDate.setFormat(commonUTIL.getDateFormat());
			EndDate.setDate(null);
		}
		return EndDate;
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
			jLabel10.setText("Ledger Type");
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
			jLabel8.setText("Settlement Currency");
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

	private DateComboBox getStartDate() {
		if (StartDate == null) {
			StartDate = new DateComboBox();
			StartDate.setFormat(commonUTIL.getDateFormat());
			StartDate.setDate(null);
		}
		return StartDate;
	}

	private JTextField getTransferId() {
		if (TransferId == null) {
			TransferId = new JTextField();
		}
		return TransferId;
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
			jLabel0.setText("Ledger ID");
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
		
		Vector<FilterBean> filterBeans = new Vector<FilterBean>();
		FilterBean bean = null;
		
		if(!commonUTIL.isEmpty(tradeId.getText())) {
			
			filterBeans.add(getOtherId(tradeId.getText()));
		} 
		
		if(!commonUTIL.isEmpty(TransferId.getText())) {
			
			filterBeans.add(getTransferId(TransferId.getText()));
		} 
	
		
		if( (StartDate.getDate() != null 
				&& !commonUTIL.isEmpty(commonUTIL.convertDateTOString(StartDate.getDate())))) {				
			
			Date fromTrade = StartDate.getDate();
			
			if (EndDate.getDate() != null) {
				
				Date toTradeDate = EndDate.getDate();
				
				if (toTradeDate.after(fromTrade)) {
					
					filterBeans.add(getCriteriaDate(commonUTIL.convertDateTOString(fromTrade), 
							commonUTIL.convertDateTOString(toTradeDate), "TradeDate"));
				
					
				}  else {
					
					commonUTIL.showAlertMessage("Start Date should be less then End Date");
					
				}				
			} 
			
			
			if (fromTrade == null  && EndDate.getDate() != null) {
				
				commonUTIL.showAlertMessage("Please select Start Date first");
			
			}
			
			filterBeans.add(getCriteriaDate(commonUTIL.convertDateTOString(fromTrade), 
					commonUTIL.convertDateTOString(fromTrade), "TradeDate"));
		}
		
		if((!commonUTIL.isEmpty(leComboBox.getSelectedItem().toString()))) {
			
			filterBeans.add(getLegalEntity(leComboBox.getSelectedIndex(), "cpid"));
			
		} 
		
		if((!commonUTIL.isEmpty(poComboBOx.getSelectedItem().toString()))) {
			
			filterBeans.add(getLegalEntity(poComboBOx.getSelectedIndex(), "poId"));
			
		}

		if((!commonUTIL.isEmpty(productTypeComboBox.getSelectedItem().toString()))) {
			
			filterBeans.add(getProductType(productTypeComboBox.getSelectedItem().toString()));
		} 	
		
		if( (!commonUTIL.isEmpty(currencyComboBox.getSelectedItem().toString()))) {
			
			filterBeans.add(getCurrency(currencyComboBox.getSelectedItem().toString(), "TrasnferCurrency"));
	
		}
		
		if((!commonUTIL.isEmpty(statusComboBox.getSelectedItem().toString()))) {
			
			filterBeans.add(getStatus(statusComboBox.getSelectedItem().toString()));
		} 
		
		if( (!commonUTIL.isEmpty(actionComboBox.getSelectedItem().toString()))) {
			
			filterBeans.add(getAction(actionComboBox.getSelectedItem().toString()));
		} 
		
		if( (!commonUTIL.isEmpty(transferTypeComboBox.getSelectedItem().toString()))) {
			
			filterBeans.add(getTransferType(transferTypeComboBox.getSelectedItem().toString()));
		}
		
		if( (!commonUTIL.isEmpty(eventTypeComboBox.getSelectedItem().toString()))) {
			
			filterBeans.add(getTransferEventType(eventTypeComboBox.getSelectedItem().toString()));
		}

		if((!commonUTIL.isEmpty(agentComboBox.getSelectedItem().toString()))) {
			
			filterBeans.add(getLegalEntity(agentComboBox.getSelectedIndex(), "agentId"));
			
		} 

		return filterBeans;
	
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

package apps.window.reportwindow;

import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;

import beans.FilterBean;
import beans.UserJobsDetails;

//VS4E -- DO NOT REMOVE THIS LINE!
public class PNLSearchPanel extends SearchCriteriaType {

	private static final long serialVersionUID = 1L;
	private JLabel jLabel0;
	private JLabel jLabel1;
	private JLabel jLabel2;
	private JLabel jLabel3;
	private JLabel jLabel4;
	private JLabel jLabel5;
	private JLabel jLabel6;
	private JLabel jLabel7;
	private JLabel jLabel8;
	private JLabel jLabel9;
	private JTextField TradeDate;
	private JComboBox currency;
	private JComboBox ProductType;
	private JComboBox productSubType;
	private JComboBox status;
	private JComboBox action;
	private JComboBox book;
	private JComboBox counterParty;
	private JComboBox bookAttributes;
	private JComboBox leAttributes;
	private JTextField jTextField1;
	private JTextField jTextField2;
	private static final String PREFERRED_LOOK_AND_FEEL = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
	javax.swing.DefaultComboBoxModel bookData = new javax.swing.DefaultComboBoxModel();
	javax.swing.DefaultComboBoxModel legalEntityData = new javax.swing.DefaultComboBoxModel();
	javax.swing.DefaultComboBoxModel bookAttributesData = new javax.swing.DefaultComboBoxModel();
	javax.swing.DefaultComboBoxModel legalEntityAttributeData = new javax.swing.DefaultComboBoxModel();
	javax.swing.DefaultComboBoxModel currencyAttributeData = new javax.swing.DefaultComboBoxModel();
	javax.swing.DefaultComboBoxModel actionAttributeData = new javax.swing.DefaultComboBoxModel();
	javax.swing.DefaultComboBoxModel statusAttributeData = new javax.swing.DefaultComboBoxModel();

	javax.swing.DefaultComboBoxModel productTypeAttributeData = new javax.swing.DefaultComboBoxModel();
	javax.swing.DefaultComboBoxModel productSubTypeAttributeData = new javax.swing.DefaultComboBoxModel();
	public PNLSearchPanel() {
		processBookDataCombo1(bookData, books);
		processLEDataCombo1(legalEntityData,  counterPartyID, "CounterParty");
		processDomainData(bookAttributesData,  getFilterValues().getDomainValues("BookAttributes"));
		processDomainData(legalEntityAttributeData,  getFilterValues().getDomainValues("LEAttributes"));
		processDomainData(currencyAttributeData,  getFilterValues().getDomainValues("Currency"));
		processDomainData(statusAttributeData,  getFilterValues().getDomainValues("Status"));
		processDomainData(actionAttributeData,  getFilterValues().getDomainValues("Action"));
		processDomainData(productTypeAttributeData,  getFilterValues().getDomainValues("ProductType"));
		initComponents();
	}

	private void initComponents() {
		setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
		setLayout(new GroupLayout());
		add(getJLabel0(), new Constraints(new Leading(12, 12, 12), new Leading(18, 10, 10)));
		add(getJLabel1(), new Constraints(new Leading(12, 12, 12), new Leading(50, 12, 12)));
		add(getJLabel2(), new Constraints(new Leading(12, 12, 12), new Leading(80, 10, 10)));
		add(getJLabel3(), new Constraints(new Leading(12, 12, 12), new Leading(112, 12, 12)));
		add(getJLabel4(), new Constraints(new Leading(14, 10, 10), new Leading(147, 12, 12)));
		add(getJLabel5(), new Constraints(new Leading(12, 12, 12), new Leading(179, 12, 12)));
		add(getJLabel6(), new Constraints(new Leading(12, 12, 12), new Leading(205, 12, 12)));
		add(getJLabel7(), new Constraints(new Leading(12, 12, 12), new Leading(234, 10, 10)));
		add(getJLabel9(), new Constraints(new Leading(12, 12, 12), new Leading(299, 10, 10)));
		add(getCounterParty(), new Constraints(new Leading(136, 82, 10, 10), new Leading(238, 12, 12)));
		add(getCurrency(), new Constraints(new Leading(136, 82, 12, 12), new Leading(38, 12, 12)));
		add(getProductType(), new Constraints(new Leading(136, 82, 12, 12), new Leading(70, 12, 12)));
		add(getStatus(), new Constraints(new Leading(136, 82, 12, 12), new Leading(138, 12, 12)));
		add(getProductSubType(), new Constraints(new Leading(136, 82, 12, 12), new Leading(106, 12, 12)));
		add(getAction(), new Constraints(new Leading(136, 82, 12, 12), new Leading(170, 12, 12)));
		add(getBook(), new Constraints(new Leading(136, 82, 12, 12), new Leading(202, 12, 12)));
		add(getTradeDate(), new Constraints(new Leading(136, 121, 12, 12), new Leading(9, 10, 10)));
		add(getBookAttributes(), new Constraints(new Leading(138, 78, 12, 12), new Leading(267, 12, 12)));
		add(getJLabel8(), new Constraints(new Leading(12, 12, 12), new Leading(270, 12, 12)));
		add(getLeAttributes(), new Constraints(new Leading(136, 78, 12, 12), new Leading(299, 12, 12)));
		add(getJTextField1(), new Constraints(new Leading(232, 76, 10, 10), new Leading(299, 12, 12)));
		add(getJTextField2(), new Constraints(new Leading(230, 76, 10, 10), new Leading(266, 12, 12)));
		setSize(346, 361);
	}

	private JTextField getJTextField2() {
		if (jTextField2 == null) {
			jTextField2 = new JTextField();
		}
		return jTextField2;
	}

	private JTextField getJTextField1() {
		if (jTextField1 == null) {
			jTextField1 = new JTextField();
		}
		return jTextField1;
	}

	private JComboBox getLeAttributes() {
		if (leAttributes == null) {
			leAttributes = new JComboBox();
			leAttributes.setModel(legalEntityAttributeData);
		}
		return leAttributes;
	}

	private JComboBox getBookAttributes() {
		if (bookAttributes == null) {
			bookAttributes = new JComboBox();
			bookAttributes.setModel(bookAttributesData);
		}
		return bookAttributes;
	}

	private JComboBox getCounterParty() {
		if (counterParty == null) {
			counterParty = new JComboBox();
			counterParty.setModel(legalEntityData);
		}
		return counterParty;
	}

	private JComboBox getBook() {
		if (book == null) {
			book = new JComboBox();
			book.setModel(bookData);
		}
		return book;
	}

	private JComboBox getAction() {
		if (action == null) {
			action = new JComboBox();
			action.setModel(actionAttributeData);
		}
		return action;
	}

	private JComboBox getStatus() {
		if (status == null) {
			status = new JComboBox();
			status.setModel(statusAttributeData);
		}
		return status;
	}
	
	private JTextField getTradeDate() {
		if (TradeDate == null) {
			TradeDate = new JTextField();
		}
		return TradeDate;
	}

	private JComboBox getProductType() {
		if (ProductType == null) {
			ProductType = new JComboBox();
			ProductType.setModel(productTypeAttributeData);
		}
		return ProductType;
	}

	private JComboBox getProductSubType() {
		if (productSubType == null) {
			productSubType = new JComboBox();
		 productSubType.setModel(productSubTypeAttributeData);
		}
		return productSubType;
	}
	private JComboBox getCurrency() {
		if (currency == null) {
			currency = new JComboBox();
			currency.setModel(currencyAttributeData);
		}
		return currency;
	}

	private JTextField getJTextField0() {
		if (TradeDate == null) {
			TradeDate = new JTextField();
			TradeDate.setText("jTextField0");
		}
		return TradeDate;
	}

	private JLabel getJLabel9() {
		if (jLabel9 == null) {
			jLabel9 = new JLabel();
			jLabel9.setText("LE Attributes");
		}
		return jLabel9;
	}

	private JLabel getJLabel8() {
		if (jLabel8 == null) {
			jLabel8 = new JLabel();
			jLabel8.setText("Book Attributes");
		}
		return jLabel8;
	}

	private JLabel getJLabel7() {
		if (jLabel7 == null) {
			jLabel7 = new JLabel();
			jLabel7.setText("CounterPary");
		}
		return jLabel7;
	}

	private JLabel getJLabel6() {
		if (jLabel6 == null) {
			jLabel6 = new JLabel();
			jLabel6.setText("Book");
		}
		return jLabel6;
	}

	private JLabel getJLabel5() {
		if (jLabel5 == null) {
			jLabel5 = new JLabel();
			jLabel5.setText("Action");
		}
		return jLabel5;
	}

	private JLabel getJLabel4() {
		if (jLabel4 == null) {
			jLabel4 = new JLabel();
			jLabel4.setText("Status");
		}
		return jLabel4;
	}

	private JLabel getJLabel3() {
		if (jLabel3 == null) {
			jLabel3 = new JLabel();
			jLabel3.setText("Product Sub Type");
		}
		return jLabel3;
	}

	private JLabel getJLabel2() {
		if (jLabel2 == null) {
			jLabel2 = new JLabel();
			jLabel2.setText("Product Type");
		}
		return jLabel2;
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
			jLabel0.setText("Trade Date");
		}
		return jLabel0;
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

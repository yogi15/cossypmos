package apps.window.reportwindow;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;

import util.commonUTIL;
import beans.Book;
import beans.FilterBean;
import beans.LegalEntity;
import beans.StartUPData;
import beans.UserJobsDetails;

//VS4E -- DO NOT REMOVE THIS LINE!
public class TradeSearchPanel extends SearchCriteriaType {

	private static final long serialVersionUID = 1L;
	private JLabel jLabel0;
	private JTextField TradeID;
	private JTextField TradeDateFrom;
	private JLabel jLabel3;
	private JLabel jLabel1;
	private JComboBox<String> TradeKeyWordName;
	private JLabel jLabel2;
	private JTextField jTextField4;
	private JLabel jLabel5;
	private JLabel jLabel6;
	private JLabel jLabel4;
	private JTextField TradeDateTo;
	private JTextField MaturityDateFrom;
	private JTextField TradeKeyWordValue;
	private JLabel jLabel7;
	private JTextField SettlementDateFrom;
	private JTextField SettlementDateTo;
	private JLabel jLabel8;
	private JLabel jLabel9;
	private JComboBox BookName;
	private JLabel jLabel10;
	private JComboBox BookAttributeName;
	private JTextField BookAttributeValue;
	private JLabel jLabel11;
	private JComboBox LegalEntityName;
	private JLabel jLabel12;
	private JComboBox LeAttributes;
	private JTextField MaturityDateTo;
	private JTextField LegalEntity;
	Hashtable<Integer,beans.Book> books = new Hashtable<Integer,beans.Book> ();
	Hashtable<Integer,beans.LegalEntity> counterPartyID = new Hashtable();
	javax.swing.DefaultComboBoxModel bookData = new javax.swing.DefaultComboBoxModel();
	javax.swing.DefaultComboBoxModel legalEntityData = new javax.swing.DefaultComboBoxModel();
	javax.swing.DefaultComboBoxModel bookAttributesData = new javax.swing.DefaultComboBoxModel();
	javax.swing.DefaultComboBoxModel legalEntityAttributeData = new javax.swing.DefaultComboBoxModel();
	javax.swing.DefaultComboBoxModel tradeAttributeData = new javax.swing.DefaultComboBoxModel();
	javax.swing.DefaultComboBoxModel currencyAttributeData = new javax.swing.DefaultComboBoxModel();
	javax.swing.DefaultComboBoxModel actionAttributeData = new javax.swing.DefaultComboBoxModel();
	javax.swing.DefaultComboBoxModel statusAttributeData = new javax.swing.DefaultComboBoxModel();
	javax.swing.DefaultComboBoxModel productTypeAttributeData = new javax.swing.DefaultComboBoxModel();
	javax.swing.DefaultComboBoxModel productSubTypeAttributeData = new javax.swing.DefaultComboBoxModel();
	
	private JTextField LeAttributesValues;
	private JLabel jLabel13;
	private JComboBox Currency;
	private JLabel jLabel14;
	private JComboBox Status;
	private JLabel jLabel15;
	private JComboBox Action;
	private JLabel jLabel16;
	private JComboBox ProductType;
	private JLabel jLabel17;
	private JComboBox ProductSubType;
	private JLabel jLabel18;
	private JComboBox CurrencyPair;
	private JLabel jLabel19;
	private JComboBox BUYSELL;
	private JLabel jLabel20;
	private JTextField ProductId;
	private static final String PREFERRED_LOOK_AND_FEEL = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
	public TradeSearchPanel() {
		init();
		initComponents();
		
	}

	private void init() {
		// TODO Auto-generated method stub
		processBookDataCombo1(bookData, books);
		processLEDataCombo1(legalEntityData,  counterPartyID);
		processDomainData(bookAttributesData,  getFilterValues().getDomainValues("BookAttributes"));
		processDomainData(legalEntityAttributeData,  getFilterValues().getDomainValues("LEAttributes"));
		processDomainData(tradeAttributeData,  getFilterValues().getDomainValues("TradeAttribute"));
		processDomainData(currencyAttributeData,  getFilterValues().getDomainValues("Currency"));
		processDomainData(statusAttributeData,  getFilterValues().getDomainValues("Status"));
		processDomainData(actionAttributeData,  getFilterValues().getDomainValues("Action"));
		processDomainData(productTypeAttributeData,  getFilterValues().getDomainValues("ProductType"));
	
	}

	private void initComponents() {
		setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		setFocusable(true);
		setEnabled(true);
		setVisible(true);
		setVerifyInputWhenFocusTarget(true);
		setDoubleBuffered(true);
		setRequestFocusEnabled(true);
		setOpaque(true);
		setLayout(new GroupLayout());
		add(getJLabel0(), new Constraints(new Leading(10, 59, 12, 12), new Leading(14, 10, 10)));
		add(getTradeID(), new Constraints(new Leading(100, 95, 10, 10), new Leading(12, 12, 12)));
		add(getTradeDateFrom(), new Constraints(new Leading(100, 95, 12, 12), new Leading(43, 12, 12)));
		add(getJLabel3(), new Constraints(new Leading(10, 10, 10), new Leading(43, 12, 12)));
		add(getJLabel1(), new Constraints(new Leading(45, 24, 10, 10), new Leading(83, -33, 10, 10)));
		add(getMaturityDateFrom(), new Constraints(new Leading(100, 95, 12, 12), new Leading(78, 12, 12)));
		add(getJLabel5(), new Constraints(new Leading(10, 81, 12, 12), new Leading(78, 12, 12)));
		add(getJLabel6(), new Constraints(new Leading(213, 12, 12), new Leading(78, 12, 12)));
		add(getJLabel4(), new Constraints(new Leading(213, 12, 12), new Leading(43, 12, 12)));
		add(getTradeDateTo(), new Constraints(new Leading(243, 93, 12, 12), new Leading(43, 12, 12)));
		add(getMaturityDateTo(), new Constraints(new Leading(243, 93, 10, 10), new Leading(78, 12, 12)));
		add(getTradeKeyWordName(), new Constraints(new Leading(100, 95, 12, 12), new Leading(146, 10, 10)));
		add(getJLabel2(), new Constraints(new Leading(10, 84, 12, 12), new Leading(145, 12, 12)));
		add(getTradeKeyWordValue(), new Constraints(new Leading(243, 93, 12, 12), new Leading(145, 12, 12)));
		add(getJLabel7(), new Constraints(new Leading(10, 81, 12, 12), new Leading(113, 12, 12)));
		add(getSettlementDateFrom(), new Constraints(new Leading(100, 95, 12, 12), new Leading(113, 12, 12)));
		add(getSettlementDateTo(), new Constraints(new Leading(243, 93, 12, 12), new Leading(110, 12, 12)));
		add(getJLabel8(), new Constraints(new Leading(213, 28, 12, 12), new Leading(114, 16, 12, 12)));
		add(getJLabel9(), new Constraints(new Leading(10, 84, 12, 12), new Leading(183, 12, 12)));
		add(getJLabel10(), new Constraints(new Leading(10, 84, 12, 12), new Leading(215, 12, 12)));
		add(getBookAttributeName(), new Constraints(new Leading(100, 95, 10, 10), new Leading(215, 12, 12)));
		add(getJLabel11(), new Constraints(new Leading(10, 77, 12, 12), new Leading(253, 16, 12, 12)));
		add(getJLabel12(), new Constraints(new Leading(10, 76, 12, 12), new Leading(289, 16, 10, 10)));
		add(getLeAttributes(), new Constraints(new Leading(100, 95, 10, 10), new Leading(289, 12, 12)));
		add(getBookName(), new Constraints(new Leading(100, 93, 12, 12), new Leading(180, 12, 12)));
		add(getBookAttributeValue(), new Constraints(new Leading(242, 95, 12, 12), new Leading(215, 12, 12)));
		add(getLegalEntityName(), new Constraints(new Leading(100, 95, 12, 12), new Leading(253, 12, 12)));
		add(getLeAttributesValues(), new Constraints(new Leading(242, 95, 12, 12), new Leading(289, 12, 12)));
		add(getJLabel13(), new Constraints(new Leading(10, 76, 12, 12), new Leading(327, 16, 12, 12)));
		add(getCurrency(), new Constraints(new Leading(100, 58, 10, 10), new Leading(327, 12, 12)));
		add(getJLabel14(), new Constraints(new Leading(10, 76, 12, 12), new Leading(365, 16, 12, 12)));
		add(getStatus(), new Constraints(new Leading(100, 94, 12, 12), new Leading(361, 12, 12)));
		add(getJLabel15(), new Constraints(new Leading(213, 39, 10, 10), new Leading(365, 16, 12, 12)));
		add(getJLabel16(), new Constraints(new Leading(10, 76, 12, 12), new Leading(399, 16, 12, 12)));
		add(getProductType(), new Constraints(new Leading(101, 94, 12, 12), new Leading(395, 12, 12)));
		add(getJLabel17(), new Constraints(new Leading(211, 51, 10, 10), new Leading(399, 16, 12, 12)));
		add(getProductSubType(), new Constraints(new Leading(260, 108, 10, 10), new Leading(395, 12, 12)));
		add(getAction(), new Constraints(new Leading(262, 104, 12, 12), new Leading(361, 12, 12)));
		add(getJLabel18(), new Constraints(new Leading(209, 46, 12, 12), new Leading(329, 16, 12, 12)));
		add(getCurrencyPair(), new Constraints(new Leading(260, 104, 12, 12), new Leading(325, 12, 12)));
		add(getJLabel19(), new Constraints(new Leading(206, 35, 12, 12), new Leading(14, 16, 12, 12)));
		add(getBUYSELL(), new Constraints(new Leading(245, 65, 10, 10), new Leading(12, 12, 12)));
		add(getJLabel20(), new Constraints(new Leading(10, 76, 12, 12), new Leading(433, 16, 12, 12)));
		add(getProductId(), new Constraints(new Leading(100, 95, 12, 12), new Leading(429, 12, 12)));
		setSize(407, 519);
	}

	private JTextField getProductId() {
		if (ProductId == null) {
			ProductId = new JTextField();
		}
		return ProductId;
	}

	private JLabel getJLabel20() {
		if (jLabel20 == null) {
			jLabel20 = new JLabel();
			jLabel20.setText("ProductID");
		}
		return jLabel20;
	}

	private JComboBox getBUYSELL() {
		if (BUYSELL == null) {
			BUYSELL = new JComboBox();
			BUYSELL.addItem("");
			BUYSELL.setSelectedIndex(0);
			BUYSELL.addItem("BUY");
			BUYSELL.addItem("SELL");
		}
		return BUYSELL;
	}

	private JLabel getJLabel19() {
		if (jLabel19 == null) {
			jLabel19 = new JLabel();
			jLabel19.setText("Type");
		}
		return jLabel19;
	}

	private JComboBox getCurrencyPair() {
		if (CurrencyPair == null) {
			CurrencyPair = new JComboBox();
			
		}
		return CurrencyPair;
	}

	private JLabel getJLabel18() {
		if (jLabel18 == null) {
			jLabel18 = new JLabel();
			jLabel18.setText("CurrPair");
		}
		return jLabel18;
	}

	private JComboBox getProductSubType() {
		if (ProductSubType == null) {
			ProductSubType = new JComboBox();
			ProductSubType.addItem("");
			ProductSubType.setSelectedIndex(0);
			ProductSubType.setModel(productTypeAttributeData);
		}
		return ProductSubType;
	}

	private JLabel getJLabel17() {
		if (jLabel17 == null) {
			jLabel17 = new JLabel();
			jLabel17.setText("SubType");
		}
		return jLabel17;
	}

	private JComboBox getProductType() {
		if (ProductType == null) {
			ProductType = new JComboBox();
			ProductType.addItem("");
			ProductType.setSelectedIndex(0);
			ProductType.setModel(productTypeAttributeData);
			ProductType.addItemListener( new ItemListener() {

	        	@Override
	        	public void itemStateChanged(ItemEvent e) {
	        		if(ProductType.getSelectedIndex() == -1)
	        			return;
	        		String productType = ProductType.getSelectedItem().toString();
	        		productSubTypeAttributeData.removeAllElements();
	        		productSubTypeAttributeData = null;
	        		productSubTypeAttributeData = new  javax.swing.DefaultComboBoxModel();
	        		ProductSubType.removeAll();
	        		processDomainData(productSubTypeAttributeData,  getFilterValues().getDomainValues(productType+".subType"));
	        		ProductSubType.setModel(productSubTypeAttributeData);
	        		
	        	}
			});
		}
		return ProductType;
	}

	private JLabel getJLabel16() {
		if (jLabel16 == null) {
			jLabel16 = new JLabel();
			jLabel16.setText("ProductType");
		}
		return jLabel16;
	}

	private JComboBox getAction() {
		if (Action == null) {
			Action = new JComboBox();
			Action.setModel(actionAttributeData);
		}
		return Action;
	}

	private JLabel getJLabel15() {
		if (jLabel15 == null) {
			jLabel15 = new JLabel();
			jLabel15.setText("Action");
		}
		return jLabel15;
	}

	private JComboBox getStatus() {
		if (Status == null) {
			Status = new JComboBox();
			Status.setModel(statusAttributeData);
		}
		return Status;
	}

	private JLabel getJLabel14() {
		if (jLabel14 == null) {
			jLabel14 = new JLabel();
			jLabel14.setText("Status");
		}
		return jLabel14;
	}

	private JComboBox getCurrency() {
		if (Currency == null) {
			Currency = new JComboBox();
			Currency.setModel(currencyAttributeData);
		}
		return Currency;
	}

	private JLabel getJLabel13() {
		if (jLabel13 == null) {
			jLabel13 = new JLabel();
			jLabel13.setText("Currency");
		}
		return jLabel13;
	}

	private JTextField getLeAttributesValues() {
		if (LeAttributesValues == null) {
			LeAttributesValues = new JTextField();
			LeAttributesValues.setText("       ");
		}
		return LeAttributesValues;
	}

	private JTextField getLegalEntity() {
		if (LegalEntity == null) {
			LegalEntity = new JTextField();
		}
		return LegalEntity;
	}

	private JTextField getJTextField0() {
		if (LegalEntity == null) {
			LegalEntity = new JTextField();
			LegalEntity.setText("jTextField0");
		}
		return LegalEntity;
	}

	private JTextField getMaturityDateTo() {
		if (MaturityDateTo == null) {
			MaturityDateTo = new JTextField();
			
		}
		return MaturityDateTo;
	}

	private JComboBox getLeAttributes() {
		if (LeAttributes == null) {
			LeAttributes = new JComboBox();
			LeAttributes.setModel(legalEntityAttributeData);
		}
		return LeAttributes;
	}

	private JLabel getJLabel12() {
		if (jLabel12 == null) {
			jLabel12 = new JLabel();
			jLabel12.setText("LE Attributes");
		}
		return jLabel12;
	}

	private JComboBox getLegalEntityName() {
		if (LegalEntityName == null) {
			LegalEntityName = new JComboBox();
			LegalEntityName.setModel(legalEntityData);
		}
		return LegalEntityName;
	}

	private JLabel getJLabel11() {
		if (jLabel11 == null) {
			jLabel11 = new JLabel();
			jLabel11.setText("Legal Entity");
		}
		return jLabel11;
	}

	private JTextField getBookAttributeValue() {
		if (BookAttributeValue == null) {
			BookAttributeValue = new JTextField();
		}
		return BookAttributeValue;
	}

	private JComboBox getBookAttributeName() {
		if (BookAttributeName == null) {
			BookAttributeName = new JComboBox();
			BookAttributeName.setModel(bookAttributesData);
		}
		return BookAttributeName;
	}

	private JLabel getJLabel10() {
		if (jLabel10 == null) {
			jLabel10 = new JLabel();
			jLabel10.setText("Book Attributes");
		}
		return jLabel10;
	}

	private JComboBox getBookName() {
		if (BookName == null) {
			BookName = new JComboBox();
			BookName.setModel(bookData);
		}
		return BookName;
	}

	private JLabel getJLabel9() {
		if (jLabel9 == null) {
			jLabel9 = new JLabel();
			jLabel9.setText("Book");
		}
		return jLabel9;
	}

	private JLabel getJLabel8() {
		if (jLabel8 == null) {
			jLabel8 = new JLabel();
			jLabel8.setText("TO");
		}
		return jLabel8;
	}

	private JTextField getSettlementDateTo() {
		if (SettlementDateTo == null) {
			SettlementDateTo = new JTextField();
		}
		return SettlementDateTo;
	}

	private JTextField getSettlementDateFrom() {
		if (SettlementDateFrom == null) {
			SettlementDateFrom = new JTextField();
		}
		return SettlementDateFrom;
	}

	private JLabel getJLabel7() {
		if (jLabel7 == null) {
			jLabel7 = new JLabel();
			jLabel7.setText("Settlement Date");
		}
		return jLabel7;
	}

	private JTextField getTradeKeyWordValue() {
		if (TradeKeyWordValue == null) {
			TradeKeyWordValue = new JTextField();
		}
		return TradeKeyWordValue;
	}

	private JTextField getMaturityDateFrom() {
		if (MaturityDateFrom == null) {
			MaturityDateFrom = new JTextField();
			
		}
		return MaturityDateFrom;
	}

	private JTextField getTradeDateTo() {
		if (TradeDateTo == null) {
			TradeDateTo = new JTextField();
		}
		return TradeDateTo;
	}

	private JLabel getJLabel4() {
		if (jLabel4 == null) {
			jLabel4 = new JLabel();
			jLabel4.setText("TO");
		}
		return jLabel4;
	}

	private JLabel getJLabel6() {
		if (jLabel6 == null) {
			jLabel6 = new JLabel();
			jLabel6.setText("TO");
		}
		return jLabel6;
	}

	private JLabel getJLabel5() {
		if (jLabel5 == null) {
			jLabel5 = new JLabel();
			jLabel5.setText("Maturity Date");
		}
		return jLabel5;
	}

	private JTextField getJTextField4() {
		if (jTextField4 == null) {
			jTextField4 = new JTextField();
		}
		return jTextField4;
	}

	private JLabel getJLabel2() {
		if (jLabel2 == null) {
			jLabel2 = new JLabel();
			jLabel2.setText("Trade keyword");
		}
		return jLabel2;
	}

	private JComboBox getTradeKeyWordName() {
		if (TradeKeyWordName == null) {
			TradeKeyWordName = new JComboBox<String>();
			TradeKeyWordName.setModel(tradeAttributeData);
		}
		return TradeKeyWordName;
	}

	private JLabel getJLabel1() {
		if (jLabel1 == null) {
			jLabel1 = new JLabel();
			jLabel1.setText("Trade Date");
		}
		return jLabel1;
	}

	private JLabel getJLabel3() {
		if (jLabel3 == null) {
			jLabel3 = new JLabel();
			jLabel3.setText("Trade Date");
		}
		return jLabel3;
	}

	private JTextField getTradeDateFrom() {
		if (TradeDateFrom == null) {
			TradeDateFrom = new JTextField();
		}
		return TradeDateFrom;
	}

	private JTextField getTradeID() {
		if (TradeID == null) {
			TradeID = new JTextField();
		}
		return TradeID;
	}

	private JLabel getJLabel0() {
		if (jLabel0 == null) {
			jLabel0 = new JLabel();
			jLabel0.setText("Trade ID");
		}
		return jLabel0;
	}

	
	public Vector<FilterBean>  searchCriteria() {
		Vector<FilterBean> filterBeans = new Vector<FilterBean>();
		FilterBean bean = null;
		if(!commonUTIL.isEmpty(TradeID.getText())) {
			bean = new FilterBean();
			bean.setColumnName("TradeID");
			bean.setColumnValues(TradeID.getText());
			bean.setAnd_or("And");
			bean.setSearchCriteria("in");
			filterBeans.add(bean);
		}
		if(!commonUTIL.isEmpty(TradeKeyWordValue.getText())) {
			bean = new FilterBean();
			if(TradeKeyWordName.getSelectedIndex() != -1 && (!commonUTIL.isEmpty(TradeKeyWordName.getSelectedItem().toString()))) {
			bean.setColumnName("TradeKeyword");
			bean.setColumnValues("tradeattribute.attributename = '"+TradeKeyWordName.getSelectedItem().toString()+"' and tradeattribute.attributevalue = '"+TradeKeyWordValue.getText() +"'");
			bean.setAnd_or("And");
			bean.setSearchCriteria("in");
			filterBeans.add(bean);
			}
		}
		
		if(!commonUTIL.isEmpty(LeAttributesValues.getText())) {
			bean = new FilterBean();
			if(LeAttributes.getSelectedIndex() != -1 && (!commonUTIL.isEmpty(LeAttributes.getSelectedItem().toString()))) {
			bean.setColumnName("LeKeyword");
			bean.setColumnValues("legalentityattribute.attributename = '"+LeAttributes.getSelectedItem().toString()+"' and legalentityattribute.attributevalue = '"+LeAttributesValues.getText() +"'");
			bean.setAnd_or("And");
			bean.setSearchCriteria("in");
			filterBeans.add(bean);
			}
		}
		if(!commonUTIL.isEmpty(BookAttributeValue.getText())) {
			bean = new FilterBean();
			if(BookAttributeName.getSelectedIndex() != -1 && (!commonUTIL.isEmpty(BookAttributeName.getSelectedItem().toString()))) {
			bean.setColumnName("BookKeyword");
			bean.setColumnValues("bookattribute.attributename = '"+BookAttributeName.getSelectedItem().toString()+"' and bookattribute.attributevalue = '"+BookAttributeValue.getText() +"'");
			bean.setAnd_or("And");
			bean.setSearchCriteria("in");
			filterBeans.add(bean);
			}
		}
		if(!commonUTIL.isEmpty(TradeDateFrom.getText())) {
			bean = new FilterBean();
			bean.setColumnName("TradeDate");
			bean.setColumnValues(TradeDateFrom.getText());
			bean.setAnd_or(TradeDateFrom.getText());
			bean.setSearchCriteria("between");
			filterBeans.add(bean);
		}
		if(!commonUTIL.isEmpty(MaturityDateFrom.getText())) {
			bean = new FilterBean();
			bean.setColumnName("DeliveryDate");
			bean.setColumnValues(MaturityDateFrom.getText());
			bean.setAnd_or(MaturityDateFrom.getText());
			bean.setSearchCriteria("between");
			filterBeans.add(bean);
		}
		if(BUYSELL.getSelectedIndex() != -1 && (!commonUTIL.isEmpty(BUYSELL.getSelectedItem().toString()))) {
			
			bean = new FilterBean();
			bean.setColumnName("Type");
			bean.setColumnValues(BUYSELL.getSelectedItem().toString());
			bean.setSearchCriteria("in");
			bean.setAnd_or("And");
		
			filterBeans.add(bean);
		}
		if(Status.getSelectedIndex() != -1 && (!commonUTIL.isEmpty(Status.getSelectedItem().toString()))) {
			
			bean = new FilterBean();
			bean.setColumnName("Status");
			bean.setColumnValues(Status.getSelectedItem().toString());
			bean.setAnd_or("And");
			bean.setSearchCriteria("in");
			filterBeans.add(bean);
		}
		if(Action.getSelectedIndex() != -1 && (!commonUTIL.isEmpty(Action.getSelectedItem().toString()))) {
			
			bean = new FilterBean();
			bean.setColumnName("Action");
			bean.setColumnValues(Action.getSelectedItem().toString());
			bean.setSearchCriteria("in");
			bean.setAnd_or("And");
		
			filterBeans.add(bean);
		}
		if(ProductType.getSelectedIndex() != -1 && (!commonUTIL.isEmpty(ProductType.getSelectedItem().toString()))) {
			
			bean = new FilterBean();
			bean.setColumnName("ProductType");
			bean.setColumnValues(ProductType.getSelectedItem().toString());
			bean.setAnd_or("And");
			bean.setSearchCriteria("in");
			filterBeans.add(bean);
		}
		if(ProductSubType.getSelectedIndex() != -1 && (!commonUTIL.isEmpty(ProductSubType.getSelectedItem().toString()))) {
			
			bean = new FilterBean();
			bean.setColumnName("ProductSubType");
			bean.setColumnValues(ProductSubType.getSelectedItem().toString());
			bean.setSearchCriteria("in");
			bean.setAnd_or("And");
		
			filterBeans.add(bean);
		}
		if(BookName.getSelectedIndex() != -1 && (!commonUTIL.isEmpty(BookName.getSelectedItem().toString()))) {
			
			bean = new FilterBean();
			bean.setColumnName("Book");
			bean.setSearchCriteria("in");
			
			bean.setColumnValues(new Integer(getBookID(BookName.getSelectedIndex())).toString());
			bean.setAnd_or("And");
		
			filterBeans.add(bean);
		}
if(LegalEntityName.getSelectedIndex() != -1 && (!commonUTIL.isEmpty(LegalEntityName.getSelectedItem().toString()))) {
			
			bean = new FilterBean();
			bean.setColumnName("cpid");
			
			bean.setColumnValues(new Integer(getCPid(LegalEntityName.getSelectedIndex())).toString());
			bean.setAnd_or("And");
			bean.setSearchCriteria("in");
			filterBeans.add(bean);
		}
if(Currency.getSelectedIndex() != -1 && (!commonUTIL.isEmpty(Currency.getSelectedItem().toString()))) {
	
	bean = new FilterBean();
	bean.setColumnName("Currency");
	
	bean.setColumnValues(Currency.getSelectedItem().toString());
	bean.setAnd_or("And");
	bean.setSearchCriteria("in");
	filterBeans.add(bean);
}
		return filterBeans;
		
	}

	
	private int getBookID(int idSelected) {
		Book book = (Book) books.get(BookName.getSelectedIndex());
		return book.getBookno();
		
	}
	private int getCPid(int idSelected) {
		LegalEntity le = (LegalEntity)  counterPartyID.get(LegalEntityName.getSelectedIndex());
		return le.getId();
		
	}
	
	private int getBooktoSelected(int idSelected) {
		int selectID = 0;
		for(int i=0;i<books.size();i++) {
			selectID = i;
			Book book = (Book) books.get(i);
			if(book != null)
			if(book.getBookno() == idSelected) 
				break;
		}
		return selectID;
		
	}
	private int getCPtoSelected(int idSelected) {
		int selectID = 0;
		for(int i=0;i<counterPartyID.size();i++) {
			selectID = i;
			LegalEntity le = (LegalEntity) counterPartyID.get(i);
			if(le != null)
			if(le.getId() == idSelected) 
				break;
		}
		return selectID;
		
	}
	@Override
	public void clearllCriterial() {
		// TODO Auto-generated method stub
		TradeID.setText("");
		TradeDateFrom.setText("");
		MaturityDateFrom.setText("");
		BUYSELL.setSelectedIndex(-1);
		Status.setSelectedIndex(-1);
		Action.setSelectedIndex(-1);
		Currency.setSelectedIndex(-1);
		BUYSELL.setSelectedIndex(-1);
		ProductType.setSelectedIndex(-1);
		ProductSubType.setSelectedIndex(-1);
		BookAttributeName.setSelectedIndex(-1);
		BookName.setSelectedIndex(-1);
		LegalEntityName.setSelectedIndex(-1);
		TradeKeyWordName.setSelectedIndex(-1);
		TradeKeyWordValue.setText("");
		LeAttributes.setSelectedIndex(-1);
		LeAttributesValues.setText("");
		BookAttributeValue.setText("");
		//Legal
		
	}

	@Override
	public void loadFilters(Vector<UserJobsDetails> jobdetails  ) {
		// TODO Auto-generated method stub
		for(int i=0;i<jobdetails.size();i++) {
			UserJobsDetails bean = jobdetails.get(i);
			
			if(bean.getColumnName().equalsIgnoreCase("TradeID")) {
				TradeID.setText(bean.getValues());
			}
			if(bean.getColumnName().equalsIgnoreCase("TradeDate")) {
				TradeDateFrom.setText(bean.getValues());
			}
			if(bean.getColumnName().equalsIgnoreCase("DeliveryDate")) {
				MaturityDateFrom.setText(bean.getValues());
			}
			if(bean.getColumnName().equalsIgnoreCase("Type")) {
				BUYSELL.setSelectedItem(bean.getValues());
			}
			if(bean.getColumnName().equalsIgnoreCase("Action")) {
				Action.setSelectedItem(bean.getValues());
			}
			if(bean.getColumnName().equalsIgnoreCase("Status")) {
				Status.setSelectedItem(bean.getValues());
			}
			if(bean.getColumnName().equalsIgnoreCase("Currency")) {
				Currency.setSelectedItem(bean.getValues());
			}
			if(bean.getColumnName().equalsIgnoreCase("ProductType")) {
				ProductType.setSelectedItem(bean.getValues());
			}
			if(bean.getColumnName().equalsIgnoreCase("ProductSubType")) {
				ProductSubType.setSelectedItem(bean.getValues());
			}
			if(bean.getColumnName().equalsIgnoreCase("BookID")) {
				BookName.setSelectedIndex(getBooktoSelected(Integer.parseInt(bean.getValues())));
			}
			if(bean.getColumnName().equalsIgnoreCase("cpid")) {
				LegalEntityName.setSelectedIndex(getCPtoSelected(Integer.parseInt(bean.getValues())));
			}
			if(bean.getColumnName().equalsIgnoreCase("TradeKeyword")) {
				
				String tradeAttName = "tradeattribute.attributename = '";
				String tradeAttValue = "' and tradeattribute.attributevalue = '";
				String tradeAttributeNames = bean.getValues().substring(bean.getValues().indexOf("tradeattribute.attributename = '")+tradeAttName.length(),bean.getValues().indexOf("' and tradeattribute.attributevalue = '"));
				String tradeAttributeValue = bean.getValues().substring(bean.getValues().indexOf("' and tradeattribute.attributevalue = '")+tradeAttValue.length(),bean.getValues().length()-1);
				TradeKeyWordName.setSelectedItem(tradeAttributeNames);
				TradeKeyWordValue.setText(tradeAttributeValue);
			}
			if(bean.getColumnName().equalsIgnoreCase("LeKeyword")) {
				String leAttName = "legalentityattribute.attributename = '";
				String leAttValue = "' and legalentityattribute.attributevalue = '";
				String leAttributeNames = bean.getValues().substring(bean.getValues().indexOf(leAttName)+leAttName.length(),bean.getValues().indexOf(leAttValue));
				String leAttributeValue = bean.getValues().substring(bean.getValues().indexOf(leAttValue)+leAttValue.length(),bean.getValues().length()-1);
				LeAttributes.setSelectedItem(leAttributeNames);
				LeAttributesValues.setText(leAttributeValue);
			}
			if(bean.getColumnName().equalsIgnoreCase("BookKeyword")) {
				String leAttName = "bookattribute.attributename = '";
				String leAttValue = "' and bookattribute.attributevalue = '";
				String leAttributeNames = bean.getValues().substring(bean.getValues().indexOf(leAttName)+leAttName.length(),bean.getValues().indexOf(leAttValue));
				String leAttributeValue = bean.getValues().substring(bean.getValues().indexOf(leAttValue)+leAttValue.length(),bean.getValues().length()-1);
				BookAttributeName.setSelectedItem(leAttributeNames);
				BookAttributeValue.setText(leAttributeValue);
			}
		}
		
	}
private void processLEDataCombo1(javax.swing.DefaultComboBoxModel combodata, Hashtable ids) {
		
		Vector ledata;
		
				//String roleType = " role like 'PO' ";
			ledata = (Vector) getFilterValues().getLegalEntitys();

			Iterator it = ledata.iterator();
			int p = 0;
			combodata.addElement("");
			while (it.hasNext()) {

				LegalEntity le = (LegalEntity) it.next();

				combodata.insertElementAt(le.getName(), p);
				ids.put(p, le);
				
				p++;
			
			}

		

	}
	
private void processBookDataCombo1(javax.swing.DefaultComboBoxModel combodata, Hashtable ids) {
	
	Vector ledata;
	
			//String roleType = " role like 'PO' ";
		ledata = (Vector) getFilterValues().getBooks();

		Iterator it = ledata.iterator();
		int p = 1;
		combodata.addElement("");
		while (it.hasNext()) {

			Book book = (Book) it.next();

			combodata.insertElementAt(book.getBook_name(), p);
			ids.put(p, book);
			
			p++;
		
		}

	

}
private void processDomainData(javax.swing.DefaultComboBoxModel combodata, Vector<String> domainData) {
	
	Vector ledata;
	
			//String roleType = " role like 'PO' ";
		ledata =domainData;

		Iterator it = ledata.iterator();
		int p = 0;
		combodata.addElement("");
		while (it.hasNext()) {

			StartUPData stData = (StartUPData) it.next();

			combodata.insertElementAt(stData.getName(), p);
			
		
		}

	

}
     
	
	
}

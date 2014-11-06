package apps.window.reportwindow;

import java.util.Hashtable;
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

import util.commonUTIL;

import beans.FilterBean;
import beans.LegalEntity;
import beans.UserJobsDetails;

//VS4E -- DO NOT REMOVE THIS LINE!
public class CashPositionSearchPanel extends SearchCriteriaType {

	private static final long serialVersionUID = 1L;
	private JLabel jLabel0;
	private JLabel jLabel1;
	private JLabel jLabel2;
	private JLabel jLabel3;
	private JLabel jLabel4;
	private JLabel jLabel5;
	private JComboBox currency;
	private JComboBox book;
	private JComboBox counterParty;
	private JComboBox primaryCurr;
	private JComboBox quotingCurr;
	private JComboBox BUYSELL;
	private static final String PREFERRED_LOOK_AND_FEEL = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
	javax.swing.DefaultComboBoxModel bookData = new javax.swing.DefaultComboBoxModel();
	javax.swing.DefaultComboBoxModel legalEntityData = new javax.swing.DefaultComboBoxModel();

	javax.swing.DefaultComboBoxModel currencyAttributeData = new javax.swing.DefaultComboBoxModel();

	javax.swing.DefaultComboBoxModel primarryCurrAttributeData = new javax.swing.DefaultComboBoxModel();

	javax.swing.DefaultComboBoxModel quotingCurrAttributeData = new javax.swing.DefaultComboBoxModel();
	
	Hashtable<Integer,beans.Book> books = new Hashtable<Integer,beans.Book> ();
	Hashtable<Integer,beans.LegalEntity> counterPartyID = new Hashtable();
	public CashPositionSearchPanel() {
		processBookDataCombo1(bookData, books);
		processLEDataCombo1(legalEntityData,  counterPartyID);
		processDomainData(currencyAttributeData,  getFilterValues().getDomainValues("Currency"));
		processDomainData(primarryCurrAttributeData,  getFilterValues().getDomainValues("Currency"));
		processDomainData(quotingCurrAttributeData,  getFilterValues().getDomainValues("Currency"));
		initComponents();
	}


	private void initComponents() {
		setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
		setLayout(new GroupLayout());
		add(getJLabel0(), new Constraints(new Leading(18, 10, 10), new Leading(22, 10, 10)));
		add(getJLabel1(), new Constraints(new Leading(18, 12, 12), new Leading(54, 12, 12)));
		add(getJLabel2(), new Constraints(new Leading(18, 12, 12), new Leading(82, 10, 10)));
		add(getJLabel3(), new Constraints(new Leading(18, 103, 10, 10), new Leading(114, 12, 12)));
		add(getJLabel4(), new Constraints(new Leading(18, 12, 12), new Leading(146, 12, 12)));
		add(getJLabel5(), new Constraints(new Leading(20, 10, 10), new Leading(176, 10, 10)));
		add(getCurrency(), new Constraints(new Leading(145, 73, 10, 10), new Leading(22, 12, 12)));
		add(getBook(), new Constraints(new Leading(147, 124, 10, 10), new Leading(84, 10, 10)));
		add(getCounterPary(), new Constraints(new Leading(145, 126, 12, 12), new Leading(54, 12, 12)));
		add(getPrimaryCurr(), new Constraints(new Leading(147, 79, 10, 10), new Leading(116, 12, 12)));
		add(getQuotingCurr(), new Constraints(new Leading(147, 82, 10, 10), new Leading(148, 12, 12)));
		add(getBUYSELL(), new Constraints(new Leading(147, 78, 12, 12), new Leading(182, 10, 10)));
		setSize(320, 397);
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


	private JComboBox getQuotingCurr() {
		if (quotingCurr == null) {
			quotingCurr = new JComboBox();
			quotingCurr.setModel(quotingCurrAttributeData);
		}
		return quotingCurr;
	}

	private JComboBox getPrimaryCurr() {
		if (primaryCurr == null) {
			primaryCurr = new JComboBox();
			primaryCurr.setModel(primarryCurrAttributeData);
		}
		return primaryCurr;
	}

	private JComboBox getBook() {
		if (book == null) {
			book = new JComboBox();
			book.setModel(bookData);
		}
		return book;
	}

	private JComboBox getCounterPary() {
		if (counterParty == null) {
			counterParty = new JComboBox();
			counterParty.setModel(legalEntityData);
		}
		return counterParty;
	}

	private JComboBox getCurrency() {
		if (currency == null) {
			currency = new JComboBox();
			currency.setModel(currencyAttributeData);
		}
		return currency;
	}

	private JLabel getJLabel5() {
		if (jLabel5 == null) {
			jLabel5 = new JLabel();
			jLabel5.setText("Type");
		}
		return jLabel5;
	}

	private JLabel getJLabel4() {
		if (jLabel4 == null) {
			jLabel4 = new JLabel();
			jLabel4.setText("Quoting Currency");
		}
		return jLabel4;
	}

	private JLabel getJLabel3() {
		if (jLabel3 == null) {
			jLabel3 = new JLabel();
			jLabel3.setText("Primary Currency");
		}
		return jLabel3;
	}

	private JLabel getJLabel2() {
		if (jLabel2 == null) {
			jLabel2 = new JLabel();
			jLabel2.setText("LegalEntity");
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

	@Override
	public Vector<FilterBean> searchCriteria() {
		// TODO Auto-generated method stub
		Vector<FilterBean> filterBeans = new Vector<FilterBean>();
		FilterBean bean = null;
		if(currency.getSelectedIndex() != -1 && (!commonUTIL.isEmpty(currency.getSelectedItem().toString()))) {
			
			filterBeans.add(getCurrency(currency.getSelectedItem().toString(), "Currency"));
	
		}
		if(book.getSelectedIndex() != -1 && (!commonUTIL.isEmpty(book.getSelectedItem().toString()))) {
			filterBeans.add(getBookName(book.getSelectedIndex()));
			
		}
if(primaryCurr.getSelectedIndex() != -1 && (!commonUTIL.isEmpty(primaryCurr.getSelectedItem().toString()))) {
			
			filterBeans.add(getCurrency(primaryCurr.getSelectedItem().toString(), "primaryCurr"));
	
		}
if(quotingCurr.getSelectedIndex() != -1 && (!commonUTIL.isEmpty(quotingCurr.getSelectedItem().toString()))) {
	
	filterBeans.add(getCurrency(quotingCurr.getSelectedItem().toString(), "quotingCurr"));

}
if(counterParty.getSelectedIndex() != -1 && (!commonUTIL.isEmpty(counterParty.getSelectedItem().toString()))) {
	
	filterBeans.add(getLegalEntity(counterParty.getSelectedIndex(), "cpid"));
	
} 
if(BUYSELL.getSelectedIndex() != -1 && (!commonUTIL.isEmpty(BUYSELL.getSelectedItem().toString()))) {
	filterBeans.add(getBUYSELL(BUYSELL.getSelectedItem().toString()));

	
} 
		return filterBeans;
	}

	@Override
	public void clearllCriterial() {
		// TODO Auto-generated method stub
		currency.setSelectedIndex(-1);
		BUYSELL.setSelectedIndex(-1);
		primaryCurr.setSelectedIndex(-1);
		quotingCurr.setSelectedIndex(-1);
		book.setSelectedIndex(-1);
		counterParty.setSelectedIndex(-1);
	}

	@Override
	public void loadFilters(Vector<UserJobsDetails> jobdetails) {
		// TODO Auto-generated method stub
		for(int i=0;i<jobdetails.size();i++) {
			UserJobsDetails bean = jobdetails.get(i);
			if(bean.getColumnName().equalsIgnoreCase("Type")) {
				BUYSELL.setSelectedItem(bean.getValues());
			}
			if(bean.getColumnName().equalsIgnoreCase("Currency")) {
				currency.setSelectedItem(bean.getValues());
			}
			if(bean.getColumnName().equalsIgnoreCase("BookID")) {
				book.setSelectedIndex(getBooktoSelected(Integer.parseInt(bean.getValues())));
			}
			if(bean.getColumnName().equalsIgnoreCase("cpid")) {
				counterParty.setSelectedIndex(getCPtoSelected(Integer.parseInt(bean.getValues())));
			}
			if(bean.getColumnName().equalsIgnoreCase("primaryCurr")) {
				primaryCurr.setSelectedItem(bean.getValues());
			}
			if(bean.getColumnName().equalsIgnoreCase("quotingCurr")) {
				quotingCurr.setSelectedItem(bean.getValues());
			}
		}
		
	}

}

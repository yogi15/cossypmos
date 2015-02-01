package apps.window.tradewindow.FXPanels;

import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;

import com.jidesoft.combobox.DateComboBox;

import util.NumericTextField;
import util.commonUTIL;


//VS4E -- DO NOT REMOVE THIS LINE!
public class FWDOptionPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	public JLabel jLabel0;
	public JLabel jLabel1;
	public DateComboBox startDate;
	public JLabel jLabel2;
	public NumericTextField primaryC;
	public NumericTextField quotingC;
	public JLabel jLabel3;
	public JButton jButton0;
	public JRadioButton jRadioButton0;
	public JRadioButton jRadioButton1;
	private JLabel jLabel4;
	DecimalFormat format = new DecimalFormat("##,###,#######");
	public FWDOptionPanel() {
		initComponents();
	}

	private void initComponents() {
		setLayout(new GroupLayout());
		add(getJButton0(), new Constraints(new Leading(596, 41, 10, 10), new Leading(32, 12, 12)));
		add(getJRadioButton0(), new Constraints(new Leading(656, 10, 10), new Leading(12, 12, 12)));
		add(getJRadioButton1(), new Constraints(new Leading(656, 10, 10), new Leading(48, 12, 12)));
		add(getJLabel3(), new Constraints(new Leading(431, 48, 10, 10), new Leading(16, 24, 10, 10)));
		add(getJLabel2(), new Constraints(new Leading(246, 48, 10, 10), new Leading(16, 24, 12, 12)));
		add(getQuotingC(), new Constraints(new Leading(320, 163, 12, 12), new Leading(48, 27, 12, 12)));
		add(getPrimaryC(), new Constraints(new Leading(134, 163, 12, 12), new Leading(48, 27, 12, 12)));
		add(getJLabel4(), new Constraints(new Leading(320, 81, 12, 12), new Leading(16, 24, 12, 12)));
		add(getJLabel1(), new Constraints(new Leading(134, 81, 12, 12), new Leading(16, 24, 12, 12)));
		add(getJLabel0(), new Constraints(new Leading(12, 81, 12, 12), new Leading(16, 24, 12, 12)));
		add(getStartdate(), new Constraints(new Leading(9, 117, 10, 10), new Leading(48, 27, 12, 12)));
		setSize(320, 100);
	}

	private JLabel getJLabel4() {
		if (jLabel4 == null) {
			jLabel4 = new JLabel();
			jLabel4.setText("Oustanding");
		}
		return jLabel4;
	}

	private JRadioButton getJRadioButton1() {
		if (jRadioButton1 == null) {
			jRadioButton1 = new JRadioButton();
			jRadioButton1.setSelected(true);
			jRadioButton1.setText("jRadioButton1");
			jRadioButton1.setVisible(false);
		}
		return jRadioButton1;
	}

	private JRadioButton getJRadioButton0() {
		if (jRadioButton0 == null) {
			jRadioButton0 = new JRadioButton();
			jRadioButton0.setSelected(true);
			jRadioButton0.setText("jRadioButton0");
			jRadioButton0.setVisible(false);
		}
		return jRadioButton0;
	}

	private JButton getJButton0() {
		if (jButton0 == null) {
			jButton0 = new JButton();
			jButton0.setText("jButton0");
			jButton0.setVisible(false);
		}
		return jButton0;
	}

	private JLabel getJLabel3() {
		if (jLabel3 == null) {
			jLabel3 = new JLabel();
		//	jLabel3.setText("jLabel3");
		}
		return jLabel3;
	}

	private NumericTextField getQuotingC() {
		if (quotingC == null) {
			quotingC = new  NumericTextField(10,format);
		//	quotingC.setText("jTextField2");
			quotingC.setEnabled(false);
		}
		return quotingC;
	}

	private NumericTextField getPrimaryC() {
		if (primaryC == null) {
			primaryC =new  NumericTextField(10,format);
		//	jTextField1.setText("jTextField1");
			primaryC.setEnabled(false);
		}
		return primaryC;
	}

	private JLabel getJLabel2() {
		if (jLabel2 == null) {
			jLabel2 = new JLabel();
			//jLabel2.setText("jLabel2");
		}
		return jLabel2;
	}

	private DateComboBox getStartdate() {
		if (startDate == null) {
			startDate = new DateComboBox();
		//	startDate.setText("jTextField0");
			startDate.setFormat(commonUTIL.getDateFormat());
			startDate.setEditable(false);
			startDate.setEnabled(false);
		}
		return startDate;
	}

	private JLabel getJLabel1() {
		if (jLabel1 == null) {
			jLabel1 = new JLabel();
			jLabel1.setText("OutStanding");
		}
		return jLabel1;
	}

	private JLabel getJLabel0() {
		if (jLabel0 == null) {
			jLabel0 = new JLabel();
			jLabel0.setText("OptionEndDate");
		}
		return jLabel0;
	}

}

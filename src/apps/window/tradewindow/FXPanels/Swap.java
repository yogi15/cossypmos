package apps.window.tradewindow.FXPanels;

import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;
import org.dyno.visual.swing.layouts.Trailing;

import util.NumericTextField;

import com.standbysoft.component.date.swing.JDatePicker;


//VS4E -- DO NOT REMOVE THIS LINE!
public class Swap extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel jLabel0;
	private JPanel jPanel0;
	private JLabel jLabel1;
	public NumericTextField jTextField1;
	private JLabel jLabel2;
	public NumericTextField jTextField2;
	private JLabel jLabel3;
	public JDatePicker swapDate;
	public NumericTextField jTextField4;
	DecimalFormat format = new DecimalFormat("##,###,#######.####");
	private static final String PREFERRED_LOOK_AND_FEEL = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
	public Swap() {
		initComponents();
	}

	private void initComponents() {
		setLayout(new GroupLayout());
		add(getJPanel0(), new Constraints(new Leading(5, 455, 10, 10), new Leading(5, 81, 10, 10)));
		setSize(474, 96);
	}

	private JTextField getJTextField4() {
		if (jTextField4 == null) {
			jTextField4 = new NumericTextField(10,format);
			jTextField4.setText("0");
		}
		return jTextField4;
	}

	private JDatePicker getJTextField3() {
		if (swapDate == null) {
			swapDate = new JDatePicker();
			//jTextField3.setText("jTextField2");
		}
		return swapDate;
	}

	private JLabel getJLabel3() {
		if (jLabel3 == null) {
			jLabel3 = new JLabel();
			jLabel3.setText("FAR RATE");
		}
		return jLabel3;
	}

	private JTextField getJTextField2() {
		if (jTextField2 == null) {
			jTextField2 = new  NumericTextField(10,format);
			jTextField2.setText("0");
		}
		return jTextField2;
	}

	private JLabel getJLabel2() {
		if (jLabel2 == null) {
			jLabel2 = new JLabel();
			jLabel2.setText("FAR DATE");
		}
		return jLabel2;
	}

	private JTextField getJTextField1() {
		if (jTextField1 == null) {
			jTextField1 = new  NumericTextField(10,format);
			jTextField1.setText("0");
		}
		return jTextField1;
	}

	private JLabel getJLabel1() {
		if (jLabel1 == null) {
			jLabel1 = new JLabel();
			jLabel1.setText("FAR AMT2");
		}
		return jLabel1;
	}

	private JPanel getJPanel0() {
		if (jPanel0 == null) {
			jPanel0 = new JPanel();
			jPanel0.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			jPanel0.setLayout(new GroupLayout());
			jPanel0.add(getJLabel0(), new Constraints(new Leading(9, 12, 12), new Leading(12, 12, 12)));
			jPanel0.add(getJTextField1(), new Constraints(new Leading(69, 166, 12, 12), new Leading(5, 28, 12, 12)));
			jPanel0.add(getJTextField3(), new Constraints(new Leading(302, 136, 12, 12), new Leading(6, 28, 12, 12)));
			jPanel0.add(getJLabel2(), new Constraints(new Leading(244, 52, 12, 12), new Leading(12, 12, 12)));
			jPanel0.add(getJLabel1(), new Constraints(new Leading(9, 12, 12), new Leading(49, 10, 10)));
			jPanel0.add(getJTextField4(), new Constraints(new Trailing(12, 136, 305, 313), new Leading(44, 28, 12, 12)));
			jPanel0.add(getJLabel3(), new Constraints(new Leading(244, 57, 30, 160), new Leading(51, 12, 12)));
			jPanel0.add(getJTextField2(), new Constraints(new Leading(69, 166, 30, 160), new Leading(42, 28, 12, 12)));
		}
		return jPanel0;
	}

	private JLabel getJLabel0() {
		if (jLabel0 == null) {
			jLabel0 = new JLabel();
			jLabel0.setText("FAR AMT1");
		}
		return jLabel0;
	}

}

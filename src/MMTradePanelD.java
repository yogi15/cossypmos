import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;

import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;


//VS4E -- DO NOT REMOVE THIS LINE!
public class MMTradePanelD extends JPanel {

	private static final long serialVersionUID = 1L;
	private JPanel AttributePanel;
	private JPanel jPanel0;
	private JPanel jPanel1;
	private JPanel jPanel2;
	private JPanel jPanel3;
	private JPanel jPanel4;
	private JPanel jPanel5;
	private JLabel jLabel0;
	private JLabel jLabel1;
	private JLabel jLabel2;
	private JLabel jLabel3;
	private JRadioButton Rollover;
	private JPanel jPanel6;
	private JLabel jLabel4;
	private JLabel jLabel5;
	private JButton New;
	private JButton Save;
	private JButton SaveAsNew;
	private JButton Delete;
	private JLabel jLabel6;
	private JLabel jLabel7;
	private JLabel jLabel8;
	private JLabel jLabel9;
	private JLabel jLabel10;
	private JLabel jLabel11;
	private JComboBox Currency;
	private JRadioButton Rollback;
	private JComboBox Book;
	private JComboBox cp;
	private JComboBox Trader;
	private JTextField TradeID;
	private JTextField startDate;
	private JTextField productName;
	private JTextField EndDate;
	private JTextField Rate;
	private JTextField Amount1;
	private JComboBox Action;
	private JTextField status;
	private JTable CashFlowTable;
	private JScrollPane jScrollPane1;
	private JTable jTable0;
	private JScrollPane AttributeTable;
	private JLabel jLabel13;
	private JPanel jPanel7;
	private JLabel jLabel12;
	private JLabel jLabel14;
	private JTextField jTextField7;
	private JTextField jTextField8;
	private JPanel jPanel8;
	private JCheckBox jCheckBox0;
	private static final String PREFERRED_LOOK_AND_FEEL = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
	public MMTradePanelD() {
		initComponents();
	}

	private void initComponents() {
		setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
		setLayout(new GroupLayout());
		add(getJPanel0(), new Constraints(new Leading(-1, 719, 12, 12), new Leading(3, 80, 10, 10)));
		add(getJPanel1(), new Constraints(new Leading(-1, 719, 12, 12), new Leading(89, 132, 12, 12)));
		add(getJPanel4(), new Constraints(new Leading(724, 259, 10, 10), new Leading(6, 511, 10, 10)));
		add(getJPanel3(), new Constraints(new Leading(2, 714, 12, 12), new Leading(364, 153, 12, 12)));
		add(getJPanel5(), new Constraints(new Leading(3, 710, 12, 12), new Leading(312, 46, 12, 12)));
		add(getJPanel2(), new Constraints(new Leading(3, 12, 12), new Leading(224, 84, 10, 10)));
		setSize(990, 527);
	}

	private JCheckBox getJCheckBox0() {
		if (jCheckBox0 == null) {
			jCheckBox0 = new JCheckBox();
			jCheckBox0.setSelected(true);
			jCheckBox0.setText("Float");
		}
		return jCheckBox0;
	}

	private JPanel getJPanel8() {
		if (jPanel8 == null) {
			jPanel8 = new JPanel();
			jPanel8.setLayout(new GroupLayout());
		}
		return jPanel8;
	}

	private JTextField getJTextField8() {
		if (jTextField8 == null) {
			jTextField8 = new JTextField();
		}
		return jTextField8;
	}

	private JTextField getJTextField7() {
		if (jTextField7 == null) {
			jTextField7 = new JTextField();
		}
		return jTextField7;
	}

	private JLabel getJLabel14() {
		if (jLabel14 == null) {
			jLabel14 = new JLabel();
			jLabel14.setText("INR Equi");
		}
		return jLabel14;
	}

	private JLabel getJLabel12() {
		if (jLabel12 == null) {
			jLabel12 = new JLabel();
			jLabel12.setText("Split Rate");
		}
		return jLabel12;
	}

	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			jPanel2.setLayout(new GroupLayout());
			jPanel2.add(getJPanel8(), new Constraints(new Leading(4, 495, 12, 12), new Leading(4, 72, 12, 12)));
			jPanel2.add(getJPanel7(), new Constraints(new Leading(511, 190, 10, 10), new Leading(4, 72, 10, 10)));
		}
		return jPanel2;
	}

	private JPanel getJPanel7() {
		if (jPanel7 == null) {
			jPanel7 = new JPanel();
			jPanel7.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			jPanel7.setLayout(new GroupLayout());
			jPanel7.add(getJLabel12(), new Constraints(new Leading(3, 10, 10), new Leading(3, 10, 10)));
			jPanel7.add(getJLabel14(), new Constraints(new Leading(6, 12, 12), new Leading(37, 12, 12)));
			jPanel7.add(getJTextField7(), new Constraints(new Leading(72, 97, 10, 10), new Leading(3, 12, 12)));
			jPanel7.add(getJTextField8(), new Constraints(new Leading(72, 96, 12, 12), new Leading(37, 12, 12)));
		}
		return jPanel7;
	}

	private JLabel getJLabel13() {
		if (jLabel13 == null) {
			jLabel13 = new JLabel();
			jLabel13.setText("INR Equi");
		}
		return jLabel13;
	}

	private JScrollPane getJScrollPane0() {
		if (AttributeTable == null) {
			AttributeTable = new JScrollPane();
			AttributeTable.setViewportView(getJTable0());
		}
		return AttributeTable;
	}

	private JTable getJTable0() {
		if (jTable0 == null) {
			jTable0 = new JTable();
			jTable0.setModel(new DefaultTableModel(new Object[][] { { "0x0", "0x1", }, { "1x0", "1x1", }, }, new String[] { "Title 0", "Title 1", }) {
				private static final long serialVersionUID = 1L;
				Class<?>[] types = new Class<?>[] { Object.class, Object.class, };
	
				public Class<?> getColumnClass(int columnIndex) {
					return types[columnIndex];
				}
			});
		}
		return jTable0;
	}

	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getJTable1());
		}
		return jScrollPane1;
	}

	private JTable getJTable1() {
		if (CashFlowTable == null) {
			CashFlowTable = new JTable();
			CashFlowTable.setModel(new DefaultTableModel(new Object[][] { { "0x0", "0x1", }, { "1x0", "1x1", }, }, new String[] { "Title 0", "Title 1", }) {
				private static final long serialVersionUID = 1L;
				Class<?>[] types = new Class<?>[] { Object.class, Object.class, };
	
				public Class<?> getColumnClass(int columnIndex) {
					return types[columnIndex];
				}
			});
		}
		return CashFlowTable;
	}

	private JTextField getJTextField6() {
		if (status == null) {
			status = new JTextField();
		}
		return status;
	}

	private JComboBox getJComboBox4() {
		if (Action == null) {
			Action = new JComboBox();
			Action.setModel(new DefaultComboBoxModel(new Object[] { "item0", "item1", "item2", "item3" }));
		}
		return Action;
	}

	private JTextField getJTextField2() {
		if (Amount1 == null) {
			Amount1 = new JTextField();
		}
		return Amount1;
	}

	private JTextField getJTextField5() {
		if (Rate == null) {
			Rate = new JTextField();
		}
		return Rate;
	}

	private JTextField getJTextField4() {
		if (EndDate == null) {
			EndDate = new JTextField();
		}
		return EndDate;
	}

	private JTextField getJTextField1() {
		if (productName == null) {
			productName = new JTextField();
		}
		return productName;
	}

	private JTextField getJTextField3() {
		if (startDate == null) {
			startDate = new JTextField();
		}
		return startDate;
	}

	private JTextField getJTextField0() {
		if (TradeID == null) {
			TradeID = new JTextField();
		}
		return TradeID;
	}

	private JComboBox getJComboBox3() {
		if (Trader == null) {
			Trader = new JComboBox();
			Trader.setModel(new DefaultComboBoxModel(new Object[] { "item0", "item1", "item2", "item3" }));
		}
		return Trader;
	}

	private JComboBox getJComboBox2() {
		if (cp == null) {
			cp = new JComboBox();
			cp.setModel(new DefaultComboBoxModel(new Object[] { "item0", "item1", "item2", "item3" }));
		}
		return cp;
	}

	private JComboBox getJComboBox1() {
		if (Book == null) {
			Book = new JComboBox();
			Book.setModel(new DefaultComboBoxModel(new Object[] { "item0", "item1", "item2", "item3" }));
		}
		return Book;
	}

	private JRadioButton getJRadioButton3() {
		if (Rollback == null) {
			Rollback = new JRadioButton();
			Rollback.setSelected(true);
			Rollback.setText("RollBack");
		}
		return Rollback;
	}

	private JComboBox getJComboBox0() {
		if (Currency == null) {
			Currency = new JComboBox();
			Currency.setModel(new DefaultComboBoxModel(new Object[] { "item0", "item1", "item2", "item3" }));
		}
		return Currency;
	}

	private JLabel getJLabel11() {
		if (jLabel11 == null) {
			jLabel11 = new JLabel();
			jLabel11.setText("EndDate");
		}
		return jLabel11;
	}

	private JLabel getJLabel10() {
		if (jLabel10 == null) {
			jLabel10 = new JLabel();
			jLabel10.setText("StartDate");
		}
		return jLabel10;
	}

	private JLabel getJLabel9() {
		if (jLabel9 == null) {
			jLabel9 = new JLabel();
			jLabel9.setText("Action");
		}
		return jLabel9;
	}

	private JLabel getJLabel8() {
		if (jLabel8 == null) {
			jLabel8 = new JLabel();
			jLabel8.setText("Status");
		}
		return jLabel8;
	}

	private JLabel getJLabel7() {
		if (jLabel7 == null) {
			jLabel7 = new JLabel();
			jLabel7.setText("Rate");
		}
		return jLabel7;
	}

	private JLabel getJLabel6() {
		if (jLabel6 == null) {
			jLabel6 = new JLabel();
			jLabel6.setText("Amount");
		}
		return jLabel6;
	}

	private JButton getJButton3() {
		if (Delete == null) {
			Delete = new JButton();
			Delete.setText("Delete");
		}
		return Delete;
	}

	private JButton getJButton2() {
		if (SaveAsNew == null) {
			SaveAsNew = new JButton();
			SaveAsNew.setText("SaveAsNew");
		}
		return SaveAsNew;
	}

	private JButton getJButton1() {
		if (Save == null) {
			Save = new JButton();
			Save.setText("Save");
		}
		return Save;
	}

	private JButton getJButton0() {
		if (New == null) {
			New = new JButton();
			New.setText("NEW");
		}
		return New;
	}

	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			jPanel1.setLayout(new GroupLayout());
			jPanel1.add(getJLabel5(), new Constraints(new Leading(8, 10, 10), new Leading(12, 12, 12)));
			jPanel1.add(getJLabel7(), new Constraints(new Leading(532, 10, 10), new Leading(12, 12, 12)));
			jPanel1.add(getJTextField0(), new Constraints(new Leading(8, 80, 10, 10), new Leading(34, 12, 12)));
			jPanel1.add(getJLabel10(), new Constraints(new Leading(109, 10, 10), new Leading(14, 12, 12)));
			jPanel1.add(getJTextField3(), new Constraints(new Leading(106, 116, 10, 10), new Leading(34, 12, 12)));
			jPanel1.add(getJLabel11(), new Constraints(new Leading(239, 10, 10), new Leading(12, 12, 12)));
			jPanel1.add(getJLabel6(), new Constraints(new Leading(383, 10, 10), new Leading(14, 12, 12)));
			jPanel1.add(getJLabel4(), new Constraints(new Leading(12, 12, 12), new Leading(82, 12, 12)));
			jPanel1.add(getJTextField4(), new Constraints(new Leading(239, 120, 10, 10), new Leading(34, 12, 12)));
			jPanel1.add(getJLabel9(), new Constraints(new Leading(596, 12, 12), new Leading(14, 12, 12)));
			jPanel1.add(getJTextField5(), new Constraints(new Leading(518, 60, 12, 12), new Leading(34, 12, 12)));
			jPanel1.add(getJTextField2(), new Constraints(new Leading(368, 138, 10, 10), new Leading(34, 12, 12)));
			jPanel1.add(getJComboBox4(), new Constraints(new Leading(586, 119, 10, 10), new Leading(37, 49, 49)));
			jPanel1.add(getJTextField1(), new Constraints(new Leading(60, 390, 10, 10), new Leading(82, 12, 12)));
			jPanel1.add(getJLabel8(), new Constraints(new Leading(588, 12, 12), new Leading(74, 12, 12)));
			jPanel1.add(getJTextField6(), new Constraints(new Leading(586, 118, 12, 12), new Leading(93, 12, 12)));
			jPanel1.add(getJCheckBox0(), new Constraints(new Leading(475, 12, 12), new Leading(81, 12, 12)));
		}
		return jPanel1;
	}

	private JLabel getJLabel5() {
		if (jLabel5 == null) {
			jLabel5 = new JLabel();
			jLabel5.setText("Trade ID");
		}
		return jLabel5;
	}

	private JLabel getJLabel4() {
		if (jLabel4 == null) {
			jLabel4 = new JLabel();
			jLabel4.setText("Deposit");
		}
		return jLabel4;
	}

	private JPanel getJPanel6() {
		if (jPanel6 == null) {
			jPanel6 = new JPanel();
			jPanel6.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			jPanel6.setLayout(new GroupLayout());
			jPanel6.add(getJRadioButton0(), new Constraints(new Leading(3, 10, 10), new Leading(9, 10, 10)));
			jPanel6.add(getJRadioButton3(), new Constraints(new Leading(0, 12, 12), new Leading(34, 10, 10)));
		}
		return jPanel6;
	}

	private JRadioButton getJRadioButton0() {
		if (Rollover == null) {
			Rollover = new JRadioButton();
			Rollover.setSelected(true);
			Rollover.setText("RollOver");
		}
		return Rollover;
	}

	private JLabel getJLabel3() {
		if (jLabel3 == null) {
			jLabel3 = new JLabel();
			jLabel3.setText("Trader");
		}
		return jLabel3;
	}

	private JPanel getJPanel0() {
		if (jPanel0 == null) {
			jPanel0 = new JPanel();
			jPanel0.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			jPanel0.setLayout(new GroupLayout());
			jPanel0.add(getJLabel0(), new Constraints(new Leading(7, 10, 10), new Leading(10, 10, 10)));
			jPanel0.add(getJLabel1(), new Constraints(new Leading(95, 10, 10), new Leading(10, 10, 10)));
			jPanel0.add(getJPanel6(), new Constraints(new Leading(622, 85, 10, 10), new Leading(2, 71, 10, 10)));
			jPanel0.add(getJComboBox1(), new Constraints(new Leading(91, 131, 10, 10), new Leading(36, 26, 12, 12)));
			jPanel0.add(getJLabel2(), new Constraints(new Leading(230, 10, 10), new Leading(12, 12, 12)));
			jPanel0.add(getJComboBox2(), new Constraints(new Leading(226, 219, 12, 12), new Leading(36, 26, 12, 12)));
			jPanel0.add(getJLabel3(), new Constraints(new Leading(451, 12, 12), new Leading(12, 12, 12)));
			jPanel0.add(getJComboBox3(), new Constraints(new Leading(451, 163, 10, 10), new Leading(36, 26, 12, 12)));
			jPanel0.add(getJComboBox0(), new Constraints(new Leading(5, 54, 12, 12), new Leading(36, 26, 12, 12)));
		}
		return jPanel0;
	}

	private JLabel getJLabel2() {
		if (jLabel2 == null) {
			jLabel2 = new JLabel();
			jLabel2.setText("CounterParty");
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

	private JPanel getJPanel5() {
		if (jPanel5 == null) {
			jPanel5 = new JPanel();
			jPanel5.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			jPanel5.setLayout(new GroupLayout());
			jPanel5.add(getJButton0(), new Constraints(new Leading(19, 10, 10), new Leading(8, 10, 10)));
			jPanel5.add(getJButton1(), new Constraints(new Leading(98, 10, 10), new Leading(8, 12, 12)));
			jPanel5.add(getJButton2(), new Constraints(new Leading(179, 10, 10), new Leading(8, 12, 12)));
			jPanel5.add(getJButton3(), new Constraints(new Leading(301, 10, 10), new Leading(8, 12, 12)));
		}
		return jPanel5;
	}

	private JPanel getJPanel4() {
		if (jPanel4 == null) {
			jPanel4 = new JPanel();
			jPanel4.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			jPanel4.setLayout(new GroupLayout());
			jPanel4.add(getJScrollPane0(), new Constraints(new Leading(4, 245, 10, 10), new Leading(8, 477, 10, 10)));
		}
		return jPanel4;
	}

	private Component getAttributePanel() {
		// TODO Auto-generated method stub
		return null;
	}

	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jPanel3 = new JPanel();
			jPanel3.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			jPanel3.setLayout(new GroupLayout());
			jPanel3.add(getJScrollPane1(), new Constraints(new Leading(3, 698, 10, 10), new Leading(4, 116, 10, 10)));
		}
		return jPanel3;
	}

}

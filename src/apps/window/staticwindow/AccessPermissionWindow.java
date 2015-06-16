package apps.window.staticwindow;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;

import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;
import org.dyno.visual.swing.layouts.Trailing;

import apps.window.referencewindow.staticReferencePanel;

//VS4E -- DO NOT REMOVE THIS LINE!
public class AccessPermissionWindow extends staticReferencePanel {

	private static final long serialVersionUID = 1L;
	private JLabel jLabel0;
	private JPanel jPanel0;
	private JList jList0;
	private JScrollPane jScrollPane0;
	private JLabel jLabel1;
	private JList jList1;
	private JScrollPane jScrollPane1;
	private JPanel jPanel1;
	private JLabel jLabel2;
	private JComboBox jComboBox0;
	private JCheckBox jCheckBox0;
	private JCheckBox jCheckBox1;
	private JCheckBox jCheckBox2;
	private JButton jButton0;
	private JButton jButton1;
	private JPanel jPanel2;
	private JPanel jPanel3;
	private JComboBox jComboBox1;
	private JComboBox jComboBox2;
	private JButton jButton2;
	private JCheckBox jCheckBox3;
	private JCheckBox jCheckBox4;
	private JTable jTable0;
	private JScrollPane jScrollPane2;
	private JLabel jLabel3;
	private JLabel jLabel4;
	ActionMap actionMap =null;
	private static final String PREFERRED_LOOK_AND_FEEL = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
	public AccessPermissionWindow() {
		initComponents();
	}

	private void initComponents() {
		setLayout(new GroupLayout());
		add(getJPanel0(), new Constraints(new Leading(6, 1200, 10, 10), new Leading(6, 433, 10, 10)));
		setSize(1215, 449);
	}

	private JLabel getJLabel4() {
		if (jLabel4 == null) {
			jLabel4 = new JLabel();
			jLabel4.setText("WindowName");
		}
		return jLabel4;
	}

	private JLabel getJLabel3() {
		if (jLabel3 == null) {
			jLabel3 = new JLabel();
			jLabel3.setText("GroupName");
		}
		return jLabel3;
	}

	private JScrollPane getJScrollPane2() {
		if (jScrollPane2 == null) {
			jScrollPane2 = new JScrollPane();
			jScrollPane2.setViewportView(getJTable0());
		}
		return jScrollPane2;
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

	private JCheckBox getJCheckBox4() {
		if (jCheckBox4 == null) {
			jCheckBox4 = new JCheckBox();
			jCheckBox4.setSelected(true);
			jCheckBox4.setText("Read/Write");
		}
		return jCheckBox4;
	}

	private JCheckBox getJCheckBox3() {
		if (jCheckBox3 == null) {
			jCheckBox3 = new JCheckBox();
			jCheckBox3.setText("Read");
		}
		return jCheckBox3;
	}

	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setText("Load");
		}
		return jButton2;
	}

	private JComboBox getJComboBox2() {
		if (jComboBox2 == null) {
			jComboBox2 = new JComboBox();
			jComboBox2.setModel(new DefaultComboBoxModel(new Object[] { "item0", "item1", "item2", "item3" }));
		}
		return jComboBox2;
	}

	private JComboBox getJComboBox1() {
		if (jComboBox1 == null) {
			jComboBox1 = new JComboBox();
			jComboBox1.setModel(new DefaultComboBoxModel(new Object[] { "item0", "item1", "item2", "item3" }));
		}
		return jComboBox1;
	}

	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jPanel3 = new JPanel();
			jPanel3.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			jPanel3.setLayout(new GroupLayout());
			jPanel3.add(getJButton2(), new Constraints(new Leading(7, 10, 10), new Leading(368, 10, 10)));
			jPanel3.add(getJLabel3(), new Constraints(new Leading(4, 12, 12), new Leading(12, 12, 12)));
			jPanel3.add(getJLabel4(), new Constraints(new Leading(4, 12, 12), new Leading(64, 12, 12)));
			jPanel3.add(getJComboBox2(), new Constraints(new Leading(91, 227, 10, 10), new Leading(58, 28, 12, 12)));
			jPanel3.add(getJComboBox1(), new Constraints(new Leading(91, 226, 12, 12), new Leading(6, 28, 12, 12)));
			jPanel3.add(getJCheckBox3(), new Constraints(new Leading(323, 12, 12), new Leading(12, 12, 12)));
			jPanel3.add(getJCheckBox4(), new Constraints(new Leading(323, 12, 12), new Leading(52, 12, 12)));
			jPanel3.add(getJScrollPane2(), new Constraints(new Leading(4, 426, 10, 10), new Leading(98, 262, 10, 10)));
		}
		return jPanel3;
	}

	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			jPanel2.setLayout(new GroupLayout());
			jPanel2.add(getJButton0(), new Constraints(new Leading(12, 12, 12), new Leading(12, 12, 12)));
			jPanel2.add(getJButton1(), new Constraints(new Leading(94, 10, 10), new Leading(12, 12, 12)));
		}
		return jPanel2;
	}

	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("Clear");
		}
		return jButton1;
	}

	private JButton getJButton0() {
		if (jButton0 == null) {
			jButton0 = new JButton();
			jButton0.setText("Save");
		}
		return jButton0;
	}

	private JCheckBox getJCheckBox2() {
		if (jCheckBox2 == null) {
			jCheckBox2 = new JCheckBox();
			jCheckBox2.setSelected(true);
			jCheckBox2.setText("Write");
		}
		return jCheckBox2;
	}

	private JCheckBox getJCheckBox1() {
		if (jCheckBox1 == null) {
			jCheckBox1 = new JCheckBox();
			jCheckBox1.setSelected(true);
			jCheckBox1.setText("Read / Write");
		}
		return jCheckBox1;
	}

	private JCheckBox getJCheckBox0() {
		if (jCheckBox0 == null) {
			jCheckBox0 = new JCheckBox();
			jCheckBox0.setSelected(true);
			jCheckBox0.setText("Read");
		}
		return jCheckBox0;
	}

	private JComboBox getJComboBox0() {
		if (jComboBox0 == null) {
			jComboBox0 = new JComboBox();
			jComboBox0.setModel(new DefaultComboBoxModel(new Object[] { "item0", "item1", "item2", "item3" }));
		}
		return jComboBox0;
	}

	private JLabel getJLabel2() {
		if (jLabel2 == null) {
			jLabel2 = new JLabel();
			jLabel2.setText("Group Name");
		}
		return jLabel2;
	}

	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			jPanel1.setLayout(new GroupLayout());
			jPanel1.add(getJLabel1(), new Constraints(new Leading(299, 10, 10), new Leading(9, 12, 12)));
			jPanel1.add(getJCheckBox0(), new Constraints(new Leading(552, 12, 12), new Leading(90, 10, 10)));
			jPanel1.add(getJCheckBox1(), new Constraints(new Leading(552, 12, 12), new Leading(123, 10, 10)));
			//jPanel1.add(getJCheckBox2(), new Constraints(new Leading(552, 12, 12), new Leading(157, 10, 10)));
			jPanel1.add(getJLabel0(), new Constraints(new Leading(32, 10, 10), new Leading(6, 12, 12)));
			jPanel1.add(getJScrollPane0(), new Constraints(new Leading(5, 276, 10, 10), new Leading(28, 330, 10, 10)));
			jPanel1.add(getJScrollPane1(), new Constraints(new Leading(291, 236, 10, 10), new Leading(31, 326, 10, 10)));
			jPanel1.add(getJComboBox0(), new Constraints(new Trailing(0, 195, 330, 539), new Leading(34, 29, 12, 12)));
			jPanel1.add(getJLabel2(), new Constraints(new Trailing(124, 396, 396), new Leading(12, 12, 12)));
		}
		return jPanel1;
	}

	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getJList1());
		}
		return jScrollPane1;
	}

	private JList getJList1() {
		if (jList1 == null) {
			jList1 = new JList();
			DefaultListModel listModel = new DefaultListModel();
			listModel.addElement("item0");
			listModel.addElement("item1");
			listModel.addElement("item2");
			listModel.addElement("item3");
			jList1.setModel(listModel);
		}
		return jList1;
	}

	private JLabel getJLabel1() {
		if (jLabel1 == null) {
			jLabel1 = new JLabel();
			jLabel1.setText("Function Name");
		}
		return jLabel1;
	}

	private JScrollPane getJScrollPane0() {
		if (jScrollPane0 == null) {
			jScrollPane0 = new JScrollPane();
			jScrollPane0.setViewportView(getJList0());
		}
		return jScrollPane0;
	}

	private JList getJList0() {
		if (jList0 == null) {
			jList0 = new JList();
			DefaultListModel listModel = new DefaultListModel();
			listModel.addElement("item0");
			listModel.addElement("item1");
			listModel.addElement("item2");
			listModel.addElement("item3");
			jList0.setModel(listModel);
		}
		return jList0;
	}

	private JPanel getJPanel0() {
		if (jPanel0 == null) {
			jPanel0 = new JPanel();
			jPanel0.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			jPanel0.setLayout(new GroupLayout());
			jPanel0.add(getJPanel1(), new Constraints(new Leading(456, 735, 10, 10), new Leading(3, 368, 10, 10)));
			jPanel0.add(getJPanel2(), new Constraints(new Leading(456, 734, 12, 12), new Leading(375, 50, 10, 10)));
			jPanel0.add(getJPanel3(), new Constraints(new Leading(5, 439, 12, 12), new Leading(7, 415, 10, 10)));
		}
		return jPanel0;
	}

	private JLabel getJLabel0() {
		if (jLabel0 == null) {
			jLabel0 = new JLabel();
			jLabel0.setText("Window Name");
		}
		return jLabel0;
	}

	@Override
	public ActionMap getHotKeysActionMapper() {
		// TODO Auto-generated method stub
		return actionMap;
	}

	@Override
	public JPanel getHotKeysPanel() {
		// TODO Auto-generated method stub
		return jPanel2;
	}

	@Override
	public ArrayList<Component> getFocusOrderList() {
		// TODO Auto-generated method stub
		ArrayList<Component> list = new ArrayList<Component>();
		return list;
	}

}

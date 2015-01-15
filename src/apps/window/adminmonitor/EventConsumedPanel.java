package apps.window.adminmonitor;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.dyno.visual.swing.layouts.Bilateral;
import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;

import dsServices.RemoteEvent;

public class EventConsumedPanel extends JPanel { 
	private JPanel jPanel3;
	private JTable jTable0;
	private JButton jButton3;
	private JButton jButton2;
	private JScrollPane  jScrollPane4;
	
	private JComboBox jComboBox0;
	private JLabel  jLabel0;
	RemoteEvent remoteEvent = null;
	
	
	
	/**
	 * @return the remoteEvent
	 */
	public RemoteEvent getRemoteEvent() {
		return remoteEvent;
	}
	/**
	 * @param remoteEvent the remoteEvent to set
	 */
	public void setRemoteEvent(RemoteEvent remoteEvent) {
		this.remoteEvent = remoteEvent;
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
	private JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new JButton();
			jButton3.setText("Consumed All Event");
		}
		return jButton3;
	}
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setText("Load");
		}
		return jButton2;
	}
	private JScrollPane getJScrollPane4() {
		if (jScrollPane4 == null) {
			jScrollPane4 = new JScrollPane();
			jScrollPane4.setViewportView(getJTable0());
		}
		return jScrollPane4;
	}

	
	private JComboBox getJComboBox0() {
		if (jComboBox0 == null) {
			jComboBox0 = new JComboBox();
			jComboBox0.setModel(new DefaultComboBoxModel(new Object[] { "item0", "item1", "item2", "item3" }));
		}
		return jComboBox0;
	}
	private JLabel getJLabel0() {
		if (jLabel0 == null) {
			jLabel0 = new JLabel();
			jLabel0.setText("EventType Name");
		}
		return jLabel0;
	}

	
	public JPanel getJPanel3() {
		if (jPanel3 == null) {
			jPanel3 = new JPanel();
			jPanel3.setLayout(new GroupLayout());
			jPanel3.add(getJLabel0(), new Constraints(new Leading(54, 131, 10, 10), new Leading(26, 10, 10)));
			jPanel3.add(getJComboBox0(), new Constraints(new Leading(194, 205, 10, 10), new Leading(23, 28, 10, 10)));
			jPanel3.add(getJScrollPane4(), new Constraints(new Leading(11, 1303, 10, 10), new Bilateral(75, 12, 31)));
			jPanel3.add(getJButton2(), new Constraints(new Leading(420, 95, 10, 10), new Leading(26, 28, 49, 449)));
			jPanel3.add(getJButton3(), new Constraints(new Leading(542, 151, 10, 10), new Leading(26, 28, 49, 449)));
		}
		return jPanel3;
	}
	
	
	
	
	

}

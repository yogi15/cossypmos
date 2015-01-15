package apps.window.adminmonitor;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;

import org.dyno.visual.swing.layouts.Bilateral;
import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;

import dsEventProcessor.DebugEventProcessor;
import dsEventProcessor.EventProcessor;

//VS4E -- DO NOT REMOVE THIS LINE!
public class ErrorLogsPanel1 extends JPanel {

	private static final long serialVersionUID = 1L;
	private JButton jButton0;
	private JList jList0;
	private JScrollPane jScrollPane0;
	private JScrollPane jScrollPane1;
	private JTextArea jTextArea0;
	private JScrollPane jScrollPane2;
	private JPanel jPanel1;
	private JTabbedPane jTabbedPane0;
	private JPanel jPanel0;
	private JButton jButton1;
	private JButton jButton2;
	private JList jList1;
	DefaultListModel<String> listModel1 = null;
	DefaultListModel<String> listModel2 = null;
	private static final String PREFERRED_LOOK_AND_FEEL = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
	public ErrorLogsPanel1() {
		initComponents();
	}

	private void initComponents() {
		setLayout(new GroupLayout());
		add(getJPanel0(), new Constraints(new Leading(4, 1334, 10, 10), new Leading(6, 533, 10, 10)));
		setSize(1341, 549);
	}

	private JList getJList1() {
		if (jList1 == null) {
			jList1 = new JList();
			jList1.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			listModel1= new DefaultListModel();
			
			jList1.setModel(listModel1);
		}
		return jList1;
	}

	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setText("Save to File");
		}
		return jButton2;
	}

	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("Clear");
		}
		return jButton1;
	}

	private JPanel getJPanel0() {
		if (jPanel0 == null) {
			jPanel0 = new JPanel();
			jPanel0.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			jPanel0.setLayout(new GroupLayout());
			jPanel0.add(getJTabbedPane0(), new Constraints(new Leading(4, 1312, 10, 10), new Leading(6, 519, 10, 10)));
		}
		return jPanel0;
	}

	private JTabbedPane getJTabbedPane0() {
		if (jTabbedPane0 == null) {
			jTabbedPane0 = new JTabbedPane();
			jTabbedPane0.addTab("DefaultLogs", getJPanel1());
		}
		return jTabbedPane0;
	}

	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(new GroupLayout());
			jPanel1.add(getJButton0(), new Constraints(new Leading(14, 63, 10, 10), new Leading(9, 10, 10)));
			jPanel1.add(getJScrollPane0(), new Constraints(new Leading(11, 204, 10, 10), new Bilateral(35, 12, 27)));
			jPanel1.add(getJButton1(), new Constraints(new Leading(81, 64, 12, 12), new Leading(9, 45, 168)));
			jPanel1.add(getJButton2(), new Constraints(new Leading(231, 10, 10), new Leading(7, 10, 10)));
			jPanel1.add(getJScrollPane1(), new Constraints(new Bilateral(221, 12, 29), new Leading(44, 320, 12, 12)));
			jPanel1.add(getJScrollPane2(), new Constraints(new Leading(220, 1073, 10, 10), new Leading(372, 106, 10, 10)));
		}
		return jPanel1;
	}

	private JScrollPane getJScrollPane2() {
		if (jScrollPane2 == null) {
			jScrollPane2 = new JScrollPane();
			jScrollPane2.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			jScrollPane2.setViewportView(getJTextArea0());
		}
		return jScrollPane2;
	}

	private JTextArea getJTextArea0() {
		if (jTextArea0 == null) {
			jTextArea0 = new JTextArea();
		}
		return jTextArea0;
	}

	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			jScrollPane1.setViewportView(getJList1());
		}
		return jScrollPane1;
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
			jList0.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			listModel2 = new DefaultListModel();
			listModel2.addElement("item0");
			
			jList0.setModel(listModel2);
		}
		return jList0;
	}

	private JButton getJButton0() {
		if (jButton0 == null) {
			jButton0 = new JButton();
			jButton0.setText("Add to New Window");
		}
		return jButton0;
	}
	public void addtaskData(EventProcessor taskEvent) {
		if(taskEvent != null) {
			
				listModel1.addElement(taskEvent.getComments());
			
		}
		}

}

package apps.window.adminmonitor;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.dyno.visual.swing.layouts.GroupLayout;

public class ErrorLogsPanel  extends JPanel {
	
	JTabbedPane jTabbedPane1 = null;
	JPanel SQLErrorPanel  = null;
	JPanel  RemoteServiceErrorPanel = null;
	public JTabbedPane getJTabbedPane1() {
		if (jTabbedPane1 == null) {
			jTabbedPane1 = new JTabbedPane();
			jTabbedPane1.addTab("SQLErrorPanel", getJPanel5());
			jTabbedPane1.addTab("RemoteServiceErrorPanel", getJPanel6());
		}
		return jTabbedPane1;
	}

	private JPanel getJPanel6() {
		if (RemoteServiceErrorPanel == null) {
			RemoteServiceErrorPanel = new JPanel();
			RemoteServiceErrorPanel.setVisible(false);
			RemoteServiceErrorPanel.setLayout(new GroupLayout());
		}
		return RemoteServiceErrorPanel;
	}

	private JPanel getJPanel5() {
		if (SQLErrorPanel == null) {
			SQLErrorPanel = new JPanel();
			SQLErrorPanel.setLayout(new GroupLayout());
		}
		return SQLErrorPanel;
	}

}

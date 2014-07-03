package apps.window.operationwindow;
import java.awt.Color;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.LineBorder;

import org.dyno.visual.swing.layouts.Bilateral;
import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;


//VS4E -- DO NOT REMOVE THIS LINE!
public class TaskFramePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JPanel jPanel0;
	private JSplitPane jSplitPane0;
	private JTabbedPane jTabbedPane0;
	private JToolBar jToolBar0;
	private JInternalFrame jInternalFrame0;
	private JButton jButton0;
	private JButton jButton1;
	private JButton jButton2;
	private JPanel jPanel1;
	private static final String PREFERRED_LOOK_AND_FEEL = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
	public TaskFramePanel() {
		initComponents();
	}

	private void initComponents() {
		setLayout(new GroupLayout());
		add(getJPanel0(), new Constraints(new Bilateral(17, 0, 539), new Bilateral(17, 0, 539)));
		setSize(556, 556);
	}

	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(new GroupLayout());
		}
		return jPanel1;
	}

	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setFont(new Font("Arial", Font.PLAIN, 10));
			jButton2.setText("2b");
		}
		return jButton2;
	}

	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setFont(new Font("Arial", Font.PLAIN, 10));
			jButton1.setText("1B");
		}
		return jButton1;
	}

	private JButton getJButton0() {
		if (jButton0 == null) {
			jButton0 = new JButton();
			jButton0.setFont(new Font("Arial", Font.PLAIN, 10));
			jButton0.setText("j0");
		}
		return jButton0;
	}

	private JInternalFrame getJInternalFrame0() {
		if (jInternalFrame0 == null) {
			jInternalFrame0 = new JInternalFrame();
			
			jInternalFrame0.setBorder(new LineBorder(Color.lightGray, 1, false));
				jInternalFrame0.setVisible(true);
			jInternalFrame0.setLayout(new GroupLayout());

			javax.swing.plaf.InternalFrameUI ifu= jInternalFrame0.getUI();
			((javax.swing.plaf.basic.BasicInternalFrameUI)ifu).setNorthPane(null);
			 
			jInternalFrame0.add(getJToolBar0(), new Constraints(new Bilateral(7, 0, 9), new Leading(4, 10, 10)));
		}
		return jInternalFrame0;
	}

	private JToolBar getJToolBar0() {
		if (jToolBar0 == null) {
			jToolBar0 = new JToolBar();
			jToolBar0.add(Box.createHorizontalGlue());
			jToolBar0.setBorderPainted(false);
			
			 
			jToolBar0.add(getJButton0(), new Constraints(new Bilateral(7, 0, 9), new Leading(9, 13, 10, 10)));
			jToolBar0.add(getJButton1(), new Constraints(new Bilateral(7, 0, 9), new Leading(9, 13, 10, 10)));
			jToolBar0.add(getJButton2(), new Constraints(new Bilateral(7, 0, 9), new Bilateral(7, 0, 9)));
			jToolBar0.setAlignmentX(90);
			

			jToolBar0.setFloatable(false);
		}
		return jToolBar0;
	}

	private JSplitPane getJSplitPane0() {
		if (jSplitPane0 == null) {
			jSplitPane0 = new JSplitPane();
			jSplitPane0.setBorder(new LineBorder(Color.black, 1, false));
			jSplitPane0.setDividerLocation(134);
			jSplitPane0.setLeftComponent(getJTabbedPane0());
		}
		return jSplitPane0;
	}

	private JTabbedPane getJTabbedPane0() {
		if (jTabbedPane0 == null) {
			jTabbedPane0 = new JTabbedPane();
			jTabbedPane0.add(getJInternalFrame0(),new Constraints(new Bilateral(7, 0, 39), new  Bilateral(7, 0, 39)));
			jTabbedPane0.setTabPlacement(JTabbedPane.LEFT);
			jTabbedPane0.setFont(new Font("Arial", Font.PLAIN, 10));
			jTabbedPane0.addTab("TaskReport", getJInternalFrame0());
			jTabbedPane0.setTitleAt(0, "<HTML> T<BR>a<BR>s<BR>k<BR>R<BR>e<BR>p<BR>o<BR>r<BR>r<BR>t<BR>");
		}
		return jTabbedPane0;
	}

	private JPanel getJPanel0() {
		if (jPanel0 == null) {
			jPanel0 = new JPanel();
			jPanel0.setLayout(new GroupLayout());
			jPanel0.add(getJSplitPane0(), new Constraints(new Bilateral(7, 0, 39), new  Bilateral(7, 0, 39)));
		}
		return jPanel0;
	}
	



}

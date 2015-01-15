package apps.window.adminmonitor;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;

import org.dyno.visual.swing.layouts.GroupLayout;

import beans.Users;

import com.jidesoft.docking.DefaultDockableHolder;
import com.jidesoft.docking.DefaultDockingManager;
import com.jidesoft.docking.DockContext;
import com.jidesoft.docking.DockableFrame;
import com.jidesoft.icons.JideIconsFactory;

import util.ClassInstantiateUtil;
import util.commonUTIL;

//VS4E -- DO NOT REMOVE THIS LINE!
public class JFrameAdminMonitorWindow  extends DefaultDockableHolder  {

	private static final long serialVersionUID = 1L;
	private static final String PREFERRED_LOOK_AND_FEEL = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
	 private static final String PROFILE_NAME = "Cosmos AdminMonitor Data";
	 JPanel adminDataPanel = null;
	public JFrameAdminMonitorWindow(String name,Users user) {
		setTitle("Cosmos " + name + " Window");
		this.user = user;
		adminDataPanel	= makeProductPanel(name);
		createInternalFrame(name);
	}
	protected DockableFrame createSampleTaskListFrame() {

		DockableFrame frameT = new DockableFrame("AdminMonitor",JideIconsFactory.getImageIcon(JideIconsFactory.DockableFrame.FRAME1));

		//_sortableTable = new SortableTable(null);
		//JideScrollPane scrollPane = new JideScrollPane(_sortableTable);
		
		frameT.getContentPane().add(adminDataPanel, BorderLayout.NORTH);
		frameT.setInitSide(DockContext.DOCK_SIDE_CENTER);
		frameT.setInitMode(DockContext.STATE_FRAMEDOCKED);
		frameT.setTitle(frameT.getName());
		frameT.setTabTitle(frameT.getName());
		//frameT.setPreferredSize(new Dimension(500, 500));
	//	frameT.setMinimumSize(new Dimension(100, 100));
		
		return frameT;
	}
   Users user= null;
	String referenceWindowName = "";
	public void createInternalFrame(String referenceWindowName) {
		URL url = getImageURL("/resources/icon/sql.jpg");
		this.user = user;
		this.referenceWindowName = referenceWindowName;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getDockingManager().setUndoLimit(10);
		getDockingManager().addUndoableEditListener(new UndoableEditListener() {
			public void undoableEditHappened(UndoableEditEvent e) {
				// refreshUndoRedoMenuItems();
			}
		});
		
		getDockingManager().beginLoadLayoutData();

		// _frame.getDockingManager().setFloatable(false);

		getDockingManager().setInitSplitPriority(DefaultDockingManager.SPLIT_SOUTH_NORTH_EAST_WEST);
		getDockingManager().setProfileKey(PROFILE_NAME);
		setIconImage(Toolkit.getDefaultToolkit().getImage(url));
		getDockingManager().addFrame(createSampleTaskListFrame());
	//	getDockingManager().addFrame(createSampleTaskListFrame());
		getDockingManager().loadLayoutData();
	//	setMenuBar();
		toFront();
		setVisible(true);
		//SDIWindowNewWindow
		//SDIWindowNewWindow
	}
	public URL getImageURL(String s) {

		return this.getClass().getResource(s);
		
	}
	
	protected JPanel makeProductPanel(String name) {
        String productWindowName = "apps.window.adminmonitor." + name + "Window";

        JPanel productWindow = null;
        try {
        Class class1 =    ClassInstantiateUtil.getClass(productWindowName,true);
        return (JPanel) class1.newInstance();
           //  productWindow = (BondPanel) 
        } catch (Exception e) {
           commonUTIL.displayError("JFrameAdminMonitor", "makeProductPanel", e);
        }

        return productWindow;
    }

}

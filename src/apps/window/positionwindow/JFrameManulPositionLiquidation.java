package apps.window.positionwindow;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;

import org.dyno.visual.swing.layouts.GroupLayout;

import util.ClassInstantiateUtil;
import util.commonUTIL;

import apps.window.reportwindow.ReportPanel;
import apps.window.utilwindow.ButtonsIconsFactory;
import beans.StartUPData;
import beans.UserJob;
import beans.Users;

import com.jidesoft.docking.DefaultDockableHolder;
import com.jidesoft.docking.DefaultDockingManager;
import com.jidesoft.docking.DockContext;
import com.jidesoft.docking.DockableFrame;
import com.jidesoft.grid.SortableTable;
import com.jidesoft.icons.JideIconsFactory;
import com.jidesoft.swing.JideScrollPane;

import dsServices.RemoteBOProcess;
import dsServices.RemoteMO;
import dsServices.RemoteReferenceData;
import dsServices.RemoteTask;
import dsServices.RemoteTrade;
import dsServices.ServerConnectionUtil;

//VS4E -- DO NOT REMOVE THIS LINE!
public class JFrameManulPositionLiquidation extends DefaultDockableHolder {

	private static final long serialVersionUID = 1L;
	private static final String PROFILE_NAME = "ManualLiquidation";
	private static final String PREFERRED_LOOK_AND_FEEL = null;
	String SQL = " select t.id,t.bookid,t.cpid,t.type,t.producttype,t.tradedesc,t.tradedesc1,t.price,t.quantity,t.nominal,Openpos.openquantity,Openpos.opennominal,Openpos.id,Openpos.positionid,t.deliverydate from trade t,openpos Openpos  	  where  t.id = Openpos.tradeid and Openpos.openquantity <> 0  and t.producttype = 'FX' ";
	private static JFrameManulPositionLiquidation frame;
	DockableFrame manualframe;
	Users user = null;
	public static ServerConnectionUtil de = null;
	RemoteReferenceData remoteBORef;
	RemoteTask remoteTask;
	RemoteTrade remoteTrade;
	RemoteBOProcess remoteBo;
	RemoteMO remoteMO;
	PositionFilterValues filterValues = null;
	Vector<StartUPData> searchCriteria;
	Vector<StartUPData> searchColumn;
	Vector<UserJob> jobs = null;
	String type = "";
	ReportPanel reportPanel;
    
	JInternalReportFrame internalFrame;

	
  public static void main(String args[]) {
	  Users user = new Users();
	  user.setId(1);
	  JFrameManulPositionLiquidation jj = new JFrameManulPositionLiquidation("pp",user);
	  jj.setVisible(true);
  }
	public JFrameManulPositionLiquidation(String name, Users user) {
		setName(name);
		setUser(user);
		setTitle("ManualPosition Liquidation " + user.getUser_name());
	//	initComponents();
		createInternalFrame(name);
	}

	private void initComponents() {
		setLayout(new GroupLayout());
		setSize(320, 240);
		
	}
	public URL getImageURL(String s) {

		return this.getClass().getResource(s);
	}

	public void createInternalFrame(String type) {
		init();
		URL url = getImageURL("/resources/icon/sql.jpg");
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
		getDockingManager().addFrame(createSampleServerFrame(filterValues, searchCriteria,searchColumn,get2Type()));
		getDockingManager().addFrame(createSampleTaskListFrame());
		getDockingManager().loadLayoutData();
		toFront();
		setVisible(true);
		setSize(600, 800);
	}
	private SortableTable _sortableTable;

	protected DockableFrame createSampleTaskListFrame() {

		DockableFrame frameT = new DockableFrame("Report ",JideIconsFactory.getImageIcon(JideIconsFactory.DockableFrame.FRAME1));

		//_sortableTable = new SortableTable(null);
		//JideScrollPane scrollPane = new JideScrollPane(_sortableTable);
		
		frameT.getContentPane().add(reportPanel.loadreport(), BorderLayout.CENTER);
		frameT.setInitSide(DockContext.DOCK_SIDE_CENTER);
		frameT.setInitMode(DockContext.STATE_FRAMEDOCKED);
		frameT.setTitle(frameT.getName());
		frameT.setTabTitle(frameT.getName());
		frameT.setPreferredSize(new Dimension(200, 200));
		frameT.setMinimumSize(new Dimension(100, 100));
		
		return frameT;
	}
	protected DockableFrame createDockableFrame(String key, Icon icon) {
		DockableFrame frame = new DockableFrame(key, icon);
		frame.setPreferredSize(new Dimension(200, 200));
		frame.add(new JideScrollPane(new JTextArea()));

		return frame;
	}

	protected DockableFrame createSampleServerFrame(PositionFilterValues filterValues,
			Vector<StartUPData> searchCriteria,
			Vector<StartUPData> searchColumn, String type) {

		manualframe = createDockableFrame("ManulLiquidation",
				ButtonsIconsFactory
						.getImageIcon(ButtonsIconsFactory.Buttons.FILTER));

		manualframe.getContext().setInitMode(DockContext.STATE_AUTOHIDE);
		manualframe.getContext().setInitSide(DockContext.DOCK_SIDE_WEST);
		manualframe.getContext().setInitIndex(0);
		internalFrame = new JInternalReportFrame(filterValues, searchCriteria,
				searchColumn, type);
		internalFrame.setTemplateFrame(manualframe);
	    internalFrame.setReportPanel(reportPanel);
		internalFrame.setUser(getUser());
		manualframe.add(createScrollPane(internalFrame));
		manualframe.setToolTipText("Template ");
		return manualframe;
	}

	private JScrollPane createScrollPane(Component component) {
		JScrollPane pane = new JideScrollPane(component);
		pane.setVerticalScrollBarPolicy(JideScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		return pane;
	}protected ReportPanel getReportPanel(String name) {
		String reportP = "apps.window.reportwindow." + name + "Panel";
		ReportPanel panel = null;

		try {
			Class class1 = ClassInstantiateUtil.getClass(reportP, true);
			panel = (ReportPanel) class1.newInstance();
			// productWindow = (BondPanel)
		} catch (Exception e) {
			System.out.println(e);
		}

		return panel;
	}


	private void init() {
		de = ServerConnectionUtil.connect("localhost", 1099,
				commonUTIL.getServerIP());

		try {
			remoteBORef = (RemoteReferenceData) de
					.getRMIService("ReferenceData");
			remoteMO = (RemoteMO)  de.getRMIService("MO");
			remoteTask = (RemoteTask) de.getRMIService("Task");
			
			remoteTrade = (RemoteTrade) de.getRMIService("Trade");
			remoteBo = (RemoteBOProcess) de.getRMIService("BOProcess");
			searchCriteria = (Vector) remoteBORef.getStartUPData("SearchCriteria");
			searchColumn = (Vector) remoteBORef.getStartUPData("Trade" + "Column");// understand which column to display on 	//
			filterValues = new PositionFilterValues(remoteBORef, remoteTrade,remoteTask, remoteBo,remoteMO);
			set2Type("Openpos");
			reportPanel = new ManualPositionPanel();
			reportPanel.setColumnSQL(SQL);
			reportPanel.setRemoteMO(remoteMO);
			reportPanel.setReferenceData(remoteBORef);
			reportPanel.setRemoteTask(remoteTask);
			reportPanel.setRemoteTrade(remoteTrade);
			reportPanel.setUser(user);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			commonUTIL.displayError("JFrameNewReport ", " Constructor ", e);
		}
	}
	private static void installLnF() {
		try {
			String lnfClassname = PREFERRED_LOOK_AND_FEEL;
			if (lnfClassname == null)
				lnfClassname = UIManager.getCrossPlatformLookAndFeelClassName();
			UIManager.setLookAndFeel(lnfClassname);
		} catch (Exception e) {
			System.err.println("Cannot install " + PREFERRED_LOOK_AND_FEEL
					+ " on this platform:" + e.getMessage());
		}
	}

	/**
	 * @return the user
	 */
	private Users getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	private void setUser(Users user) {
		this.user = user;
	}

	String name = "";

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the type
	 */
	private String get2Type() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	private void set2Type(String type) {
		this.type = type;
	}
}

package apps.window.adminmonitor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;

import org.dyno.visual.swing.layouts.Bilateral;
import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;

import util.RemoteServiceUtil;
import util.commonUTIL;
import beans.StartUPData;
import dsEventListener.AdminEvtListener;
import dsEventProcessor.AdminEventProcessor;
import dsEventProcessor.DebugEventProcessor;
import dsEventProcessor.EngineEventMonitorProcessor;
import dsEventProcessor.EventProcessor;
import dsServices.RemoteAdminManager;
import dsServices.RemoteEvent;
import dsServices.RemoteReferenceData;
import dsServices.RemoteTrade;

//VS4E -- DO NOT REMOVE THIS LINE!
public class AdminMonitorWindow extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JPanel jPanel0;
	

	private JTabbedPane jTabbedPane0;
	private JPanel jPanel1;
	private JSplitPane jSplitPane0;
	private JButton jButton0;
	private JButton jButton1;
	private JList jList0;
	private JScrollPane jScrollPane1;
	private JList jList1;
	private JScrollPane jScrollPane3;
	private JPanel jPanel2;
	DefaultListModel<String> listModel1 = null;
	DefaultListModel<String> listModel2 = null;
	RemoteEvent remoteEvt = null;
	RemoteReferenceData remoteRef = null;
	RemoteAdminManager remoteAdmin;
	RemoteTrade  remoteTrade;
	AdminEvtListener adminListener = null;
	EventConsumedPanel eventConsumed = null;
	ErrorLogsPanel1 errorLogsPanel = null;
	EventCleanUpPanel eventCleanPanel = null;
	MonitorExceptionPanel monitorException = null;
	RemoteEvent remoteEvent;
	Vector<StartUPData> serviceManagers =new Vector<StartUPData>();
	/**
	 * @return the errorLogsPanel
	 */
	public ErrorLogsPanel1 getErrorLogsPanel() {
		return errorLogsPanel;
	}

	/**
	 * @param errorLogsPanel the errorLogsPanel to set
	 */
	public void setErrorLogsPanel(ErrorLogsPanel1 errorLogsPanel) {
		this.errorLogsPanel = errorLogsPanel;
	}

	Vector<StartUPData> eventControllers = new Vector<StartUPData>();
	
	

	private JPanel jPanel4;

	
    private EngineViewerPanel engineViewerPanel=null;
    
	/**
	 * @return the engineViewerPanel
	 */
	public EngineViewerPanel getEngineViewerPanel() {
		return engineViewerPanel;
	}

	/**
	 * @param engineViewerPanel the engineViewerPanel to set
	 */
	public void setEngineViewerPanel(EngineViewerPanel engineViewerPanel) {
		this.engineViewerPanel = engineViewerPanel;
	}

	private static final String PREFERRED_LOOK_AND_FEEL = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
	public AdminMonitorWindow()  {
		listModel2 = new DefaultListModel();
		
		
		adminListener = new AdminEvtListener("localhost",commonUTIL.getLocalHostName(),"","ADMIN");
		adminListener.start(adminListener);
		adminListener.setAdminEvtListener(this);
		try {
			remoteRef = (RemoteReferenceData) (RemoteReferenceData) RemoteServiceUtil
					.getRemoteReferenceDataService();
			remoteAdmin = (RemoteAdminManager)  RemoteServiceUtil
					.getRemoteAdmin();
			remoteTrade =  (RemoteTrade) RemoteServiceUtil
					.getRemoteTradeService();
			eventControllers = (Vector) remoteRef.getStartUPData("EventController");
			serviceManagers = (Vector) remoteRef.getStartUPData("ServiceManager");
			eventConsumed = new EventConsumedPanel();
			engineViewerPanel = new EngineViewerPanel(remoteAdmin,remoteTrade);
			errorLogsPanel = new ErrorLogsPanel1();
			eventCleanPanel = new EventCleanUpPanel(serviceManagers);
			remoteEvent = (RemoteEvent) RemoteServiceUtil.getRemoteEventService();
			eventCleanPanel.setRemoteEvent(remoteEvent);
			monitorException = new MonitorExceptionPanel();
			initComponents();
			
		
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @return the eventConsumed
	 */
   public EventConsumedPanel getEventConsumed() {
	return eventConsumed;
	}

	/**
	 * @param eventConsumed the eventConsumed to set
	 */
	public void setEventConsumed(EventConsumedPanel eventConsumed) {
		this.eventConsumed = eventConsumed;
	}


	private void fillEvents(Vector<StartUPData> eventControllers2,
			DefaultListModel<String> listModel22) {
		// TODO Auto-generated method stub
		if(commonUTIL.isEmpty(eventControllers2))
			return;
		for(int i=0; i<eventControllers2.size();i++) {
			StartUPData data = (StartUPData) eventControllers2.get(i);
			listModel22.addElement(data.getName());
            }
		
	}

	private void initComponents() {
		setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
		setLayout(new GroupLayout());
		add(getJTabbedPane0(), new Constraints(new Leading(0, 1333, 10, 10), new Bilateral(6, 12, 462)));
		setSize(1346, 573);
	}

	

	

	private JPanel getJPanel4() {
		if (jPanel4 == null) {
			jPanel4 = new JPanel();
			jPanel4.setLayout(new GroupLayout());
			jPanel4.add(errorLogsPanel, new Constraints(new Bilateral(6, 12, 5), new Bilateral(8, 12, 5)));
		}
		return jPanel4;
	}

	

	

	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setLayout(new GroupLayout());
			jPanel2.add(getJScrollPane3(), new Constraints(new Leading(6, 966, 10, 10), new Leading(7, 485, 10, 10)));
		}
		return jPanel2;
	}

	private JScrollPane getJScrollPane3() {
		if (jScrollPane3 == null) {
			jScrollPane3 = new JScrollPane();
			jScrollPane3.setViewportView(getJList1());
		}
		return jScrollPane3;
	}

	private JList getJList1() {
		if (jList1 == null) {
			jList1 = new JList();
			 listModel1 = new DefaultListModel();
		
			jList1.setModel(listModel1);
		}
		return jList1;
	}

	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getJList0());
		}
		return jScrollPane1;
	}

	private JList getJList0() {
		if (jList0 == null) {
			jList0 = new JList();

			jList0.setModel(listModel2);
			 fillEvents(eventControllers,listModel2);
			
		}
		return jList0;
	}

	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("Show Publish Events");
		}
		return jButton1;
	}

	private JButton getJButton0() {
		if (jButton0 == null) {
			jButton0 = new JButton();
			jButton0.setText("Clear");
			jButton0.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					listModel1.clear();
					listModel1.removeAllElements();
					
				}
			});
		}
		return jButton0;
	}

	private JPanel getJPanel0() {
		if (jPanel0 == null) {
			jPanel0 = new JPanel();
			jPanel0.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			jPanel0.setLayout(new GroupLayout());
			jPanel0.add(getJSplitPane0(), new Constraints(new Bilateral(0, 12, 1217), new Leading(7, 504, 10, 10)));
		}
		return jPanel0;
	}

	private JSplitPane getJSplitPane0() {
		if (jSplitPane0 == null) {
			jSplitPane0 = new JSplitPane();
			jSplitPane0.setDividerLocation(332);
			jSplitPane0.setLeftComponent(getJPanel1());
			jSplitPane0.setRightComponent(getJPanel2());
		}
		return jSplitPane0;
	}

	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(new GroupLayout());
			jPanel1.add(getJButton0(), new Constraints(new Leading(9, 10, 10), new Leading(26, 10, 10)));
			jPanel1.add(getJButton1(), new Constraints(new Leading(87, 10, 10), new Leading(26, 10, 10)));
			jPanel1.add(getJScrollPane1(), new Constraints(new Bilateral(8, 12, 27), new Bilateral(58, 12, 27)));
		}
		return jPanel1;
	}

	private JTabbedPane getJTabbedPane0() {
		if (jTabbedPane0 == null) {
			jTabbedPane0 = new JTabbedPane();
			
			jTabbedPane0.addTab("EngineViewer",engineViewerPanel.getJPanel0());
			jTabbedPane0.addTab("EventSubscription", getJPanel0());
			jTabbedPane0.addTab("DefaultLogs", getJPanel4());
			jTabbedPane0.addTab("EventArchiveCleanUp", eventCleanPanel);
			jTabbedPane0.addTab("ErrorMonitor", monitorException);
			
		}
		return jTabbedPane0;
	}

	public void addtaskData(EventProcessor taskEvent) {
		if(taskEvent != null) {
			
			if(taskEvent.isAdminEvent()) {
				AdminEventProcessor  adminEvent = (AdminEventProcessor) taskEvent;
				engineViewerPanel.addtaskData(adminEvent);
				
			} else {
			String value = taskEvent.getProcessName() + " publishing Event " + taskEvent.getEventType() +"_"+taskEvent.getEventid()   + "  on "+ taskEvent.getComments() + "  " + taskEvent.getOccurrenceTime();
			if(taskEvent instanceof EngineEventMonitorProcessor) {
		     
			} else {
				 listModel1.addElement(value);
			}
			if(taskEvent instanceof DebugEventProcessor) {
			errorLogsPanel.addtaskData(taskEvent);
			}
		}
		// TODO Auto-generated method stub
		
	}

	}
}

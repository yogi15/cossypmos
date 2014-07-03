package apps.window.positionwindow;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.tree.DefaultMutableTreeNode;

import util.commonUTIL;

import beans.Position;
import beans.StartUPData;
import beans.UserJob;
import beans.Users;

import com.jidesoft.docking.DefaultDockableHolder;
import com.jidesoft.docking.DefaultDockingManager;
import com.jidesoft.docking.DockContext;
import com.jidesoft.docking.DockableFrame;
import com.jidesoft.icons.JideIconsFactory;
import com.jidesoft.swing.JideScrollPane;

import dsServices.RemoteMO;
import dsServices.RemoteReferenceData;
import dsServices.RemoteTask;
import dsServices.RemoteTrade;
import dsServices.ServerConnectionUtil;

import apps.window.operationwindow.jobpanl.FilterValues;
import apps.window.utilwindow.JDialogBoxJListSingleSelection;
import apps.window.utilwindow.JTreeChoice;
public class JFramePosition extends DefaultDockableHolder {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Users user = null;
	apps.window.reportwindow.ReportPanel positionPanel;
	public static ServerConnectionUtil de = null;
	private static final String PROFILE_NAME = "PositionWindow";
	RemoteTrade remoteTrade;
	RemoteMO remoteMO;
	PositionFilterValues filterValues = null;
	Vector<StartUPData> searchCriteria;
	Vector<StartUPData> searchColumn;
	Vector<UserJob> jobs = null;
	String reportType = "Position";
	String columnSQL;
	RemoteTask remoteTask;
	RemoteReferenceData remoteBORef;
	private static JFramePosition frame;
	DockableFrame tempalteframe;
	private UserJob job=null;
	DefaultListModel<String> listModel = new DefaultListModel<String> ();
	Hashtable<Integer,UserJob> teamplates = new Hashtable<Integer,UserJob>();
	int templateIdSelected = 0;
	JInternalPositionFrame internalFrame = null;
	DefaultMutableTreeNode columnsTreeNode = null; // used to show columns
	String name = "";
	
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}


	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	public static void main(String args[]) {
		Users user = new Users();
		user.setId(1);
		JFramePosition dd = new JFramePosition("Testing",user);
	}
	public  JFramePosition(String name,Users user) {
	
		setName(name);
		setUser(user);
		init();
		
		//  positionPanel = new PositionPanel("Position", searchCriteria, searchColumn, filterValues, job, detailsJob, remoteTask, remoteTrade,remoteMO, getUser());
		  
		createInternalFrame(name);
		setTitle(name + " : " + user.getUser_name()  + " : " +  " Cosmos Reporting Framework ");
	}
	public URL getImageURL(String s) {
		return this.getClass().getResource(s);
	}
	public void createInternalFrame(String reportType) {
		URL url = getImageURL("/resources/icon/sql.jpg");
		setName(name);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getDockingManager().setUndoLimit(10);
		getDockingManager().addUndoableEditListener(new UndoableEditListener() {
			public void undoableEditHappened(UndoableEditEvent e) {
				// refreshUndoRedoMenuItems();
			}
		});
		
		getDockingManager().beginLoadLayoutData();
		getDockingManager().setInitSplitPriority(DefaultDockingManager.SPLIT_SOUTH_NORTH_EAST_WEST);
		getDockingManager().setProfileKey(PROFILE_NAME);
		setIconImage(Toolkit.getDefaultToolkit().getImage(url));
		getDockingManager().addFrame(createSampleServerFrame(filterValues, searchCriteria,searchColumn,getReportType()));
		getDockingManager().addFrame(createSampleTaskListFrame());
		getDockingManager().loadLayoutData();
	//	setMenuBar();
		toFront();
		setVisible(true);
		setSize(600, 800);
	}
	protected DockableFrame createDockableFrame(String key, Icon icon) {
		DockableFrame frame = new DockableFrame(key, icon);
		frame.setPreferredSize(new Dimension(200, 200));
		frame.add(new JideScrollPane(new JTextArea()));

		return frame;
	}
	
	protected DockableFrame createSampleServerFrame(PositionFilterValues filterValues,
			Vector<StartUPData> searchCriteria, Vector<StartUPData> searchColumn,String reportType) {
		String iconPath = "/resources/icon/report_filter.png";
		Icon icon = getIcon(iconPath);

		tempalteframe = createDockableFrame("Template ", icon);
    
		tempalteframe.getContext().setInitMode(DockContext.STATE_AUTOHIDE);
		tempalteframe.getContext().setInitSide(DockContext.DOCK_SIDE_WEST);
		tempalteframe.getContext().setInitIndex(0);
		 internalFrame =  new JInternalPositionFrame(filterValues, searchCriteria, searchColumn,"Position");
		internalFrame.setTemplateFrame(tempalteframe);
    	internalFrame.setReportPanel(positionPanel);
    	internalFrame.setUserJob(job);
		internalFrame.setUser(getUser());
		tempalteframe.add(createScrollPane(internalFrame));
		tempalteframe.setToolTipText("Template ");
		return tempalteframe;
	}
	private Icon getIcon(String path) {
		java.net.URL imgURL = getClass().getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL, "");
		}
		return null;

	}

	public void init() {
		de =ServerConnectionUtil.connect("localhost", 1099,commonUTIL.getServerIP() );
		
		try {
			remoteBORef = (RemoteReferenceData) de.getRMIService("ReferenceData");
			remoteTrade = (RemoteTrade)  de.getRMIService("Trade");
			remoteMO = (RemoteMO) de.getRMIService("MO");
			remoteTask = (RemoteTask) de.getRMIService("Task");
			searchCriteria = (Vector) remoteBORef.getStartUPData("SearchCriteria");
			 searchColumn = (Vector)  remoteBORef.getStartUPData("Position" + "Column");// understand which column to display on selected report
			 filterValues = new PositionFilterValues(remoteBORef,remoteTrade,remoteTask,null,remoteMO);
			 positionPanel = new JPositionPanel();

				getUserTempleates(user.getId());
				if(jobs !=  null)  {
				 job = jobs.elementAt(0);
				  Vector detailsJob = job.getDetailsJobs();
				  positionPanel.setUser(user);
					 positionPanel.setUserJob(job);
					 positionPanel.setUserJobsDetails(job.getDetailsJobs());
		}
			
			 positionPanel.setReferenceData(remoteBORef);
			 positionPanel.setRemoteTrade(remoteTrade);
			 positionPanel.setRemoteTask(remoteTask);
			 positionPanel.setRemoteMO(remoteMO);
			 positionPanel.setPositionFilterValues(filterValues);
			
			 
			 //initComponents();
		}catch (RemoteException e) {
			// TODO Auto-generated catch block
			commonUTIL.displayError("JFramePosition ", " Constructor " , e);
		}
	}
	protected DockableFrame createSampleTaskListFrame() {

		DockableFrame frameT = new DockableFrame("Report ",JideIconsFactory.getImageIcon(JideIconsFactory.DockableFrame.FRAME1));

		//_sortableTable = new SortableTable(null);
		//JideScrollPane scrollPane = new JideScrollPane(_sortableTable);
	Vector<Position>	 data = null;
		String where = positionPanel.getPositionFilterValues().createWhere(internalFrame.getFilters());
		try {
			data = (Vector<Position>)	positionPanel.getRemoteMO().getPositionOnWhere(where, null);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		frameT.getContentPane().add(positionPanel.loadreport(data), BorderLayout.CENTER);  //positionPanel
		frameT.setInitSide(DockContext.DOCK_SIDE_CENTER);
		frameT.setInitMode(DockContext.STATE_FRAMEDOCKED);
		frameT.setTitle(frameT.getName());
		frameT.setTabTitle(frameT.getName());
		frameT.setPreferredSize(new Dimension(200, 200));
		frameT.setMinimumSize(new Dimension(100, 100));
		
		return frameT;
	}
	
	private JScrollPane createScrollPane(Component component) {
		JScrollPane pane = new JideScrollPane(component);
		pane.setVerticalScrollBarPolicy(JideScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		return pane;
	}
	
	/**
	 * @return the reportType
	 */
	private String getReportType() {
		return reportType;
	}

	/**
	 * @param reportType the reportType to set used to populate the template of TradeReport
	 */
	private void setReportType(String reportType) {
		this.reportType = reportType.replace("Report", "");
		//this.reportType = reportType;
		
		//reportPanel = getReportPanel(reportType);
	}

	/**
	 * @return the columnSQL
	 */
	private String getColumnSQL() {
		return columnSQL;
	}

	/**
	 * @param columnSQL the columnSQL to set
	 */
	private void setColumnSQL(String columnSQL) {
		this.columnSQL = columnSQL;
	}
	/**
	 * @return the user
	 */
	private Users getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	private void setUser(Users user) {
		this.user = user;
	}
	/**
	 * @return the jobs
	 */
	private Vector<UserJob> getJobs() {
		return jobs;
	}

	/**
	 * @param jobs the jobs to set
	 */
	private void setJobs(Vector<UserJob> jobs) {
		this.jobs = jobs;
	}
	private void processTeamplates(DefaultListModel<String> listModel,Vector<UserJob> userJobs) {
		if(!commonUTIL.isEmpty(userJobs)) {
			for(int i=0;i<userJobs.size();i++) {
				listModel.add(i, userJobs.get(i).getTreeNodeName());
				teamplates.put(i, userJobs.get(i));
			}
		}
	}
	
	private void setMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		JMenu fileMenu = new JMenu("Report");
		JMenu setupd = new JMenu("Report Column Setup");
		JMenu formatMenu = new JMenu("Format");
		JMenuItem columnconfigure = new JMenuItem("Columns ");
		JMenuItem subcolms = new JMenuItem("SubColumns ");
		final JMenuItem loadTemplate = new JMenuItem("Load Template ");
		final JMenuItem newTemplate = new JMenuItem("New Template ");
		final JMenuItem saveTemplate = new JMenuItem("Save Template ");

		menuBar.add(fileMenu);
		setupd.add(columnconfigure);
		setupd.add(subcolms);

		fileMenu.add(newTemplate);
		fileMenu.add(loadTemplate);
		fileMenu.add(saveTemplate);
		menuBar.add(setupd);
		menuBar.add(formatMenu);
		// Create and add simple menu item to one of the drop down menu
		JMenuItem newAction = new JMenuItem("New");
		JMenuItem save = new JMenuItem("Save");
		JMenuItem saveAsNewAction = new JMenuItem("Save as New");

		JMenuItem exitAction = new JMenuItem("Exit");
		JMenuItem opemAction = new JMenuItem("Open ");
		JMenuItem Excel = new JMenuItem("Excel");
		JMenuItem cvs = new JMenuItem("CVS ");
		JMenuItem html = new JMenuItem("HTML ");
		
		
		
		
		final JTreeChoice choiceColumns = new JTreeChoice(this,columnsTreeNode);
		
		 
		  loadTemplate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				final  JDialogBoxJListSingleSelection  templatesLists = new JDialogBoxJListSingleSelection(listModel);
				 templatesLists.setLocationRelativeTo(loadTemplate);
				templatesLists.setSize(230, 220);
				templatesLists.setVisible(true);
				templatesLists.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent e) {
						String templateName = templatesLists.jTextField0.getText();
						tempalteframe.setTitle(templateName);
						templateIdSelected = templatesLists.getTemplateId();
						job = (UserJob) teamplates.get(templatesLists.getTemplateId());
						
						internalFrame.setUserJob(job);
						if(!commonUTIL.isEmpty(job.getSql())) {
							positionPanel.setColumnSQL(job.getSql());
						}
						
						templatesLists.dispose();
					}
				});
				
			}
		});
		  newTemplate.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					final  JDialogBoxJListSingleSelection  templatesLists = new JDialogBoxJListSingleSelection(listModel);
					 templatesLists.setLocationRelativeTo(loadTemplate);
					 templatesLists.setSize(230, 220);
					templatesLists.setVisible(true);
					templatesLists.addWindowListener(new WindowAdapter() {
						public void windowClosing(WindowEvent e) {
							String templateName = templatesLists.jTextField0.getText();
							tempalteframe.setTitle(templateName);
							listModel.insertElementAt(templateName, listModel.size());
							
							UserJob job = new UserJob();
							job.setId(0);
							job.setTabid(0);
							job.setUserID(user.getId());
							job.setType("Report"+getReportType().toUpperCase());
							job.setTreeNodeName(templateName);
							
							try {
								 job = (UserJob) remoteTask.saveUserJob(job);
								teamplates.put(listModel.size()-1, job);
							} catch (RemoteException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							internalFrame.setUserJob(job);
							
							templatesLists.dispose();
						}
					});
					
				}
			});
		  saveTemplate.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					internalFrame.saveJobDetails();
					
				}
			});
		  
		  
		
		choiceColumns.setLocationRelativeTo(this);
		columnconfigure.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				choiceColumns.setSize(380, 410);
				choiceColumns.setVisible(true);

			}
		});
		
		choiceColumns.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {

				String tableName = "";
				String joinsTable = "";
				String tablerefer = "";

				Object obj[] = choiceColumns.cmodList2.toArray();
			/*	if (obj.length > 0) {
					String SQLcolumnsName = sqlGen.getSQLColumns(obj);
					for (int i = 0; i < obj.length; i++) { // this to get
															// columns from
															// hashtable to
															// build table name
						tablerefer = tablerefer + (String) obj[i] + ",";
					}
					tableName = sqlGen.getSQLTables(tablerefer);
					if (!tableName.contains(filterValues.getTableName(reportType))) {
						tableName = filterValues.getTableName(reportType) + " " + filterValues.getTableName(reportType).toLowerCase()
								+ ", " + tableName;
					}
					columnSQL = SQLcolumnsName + "  from  " + tableName;
					String sql = " select " + columnSQL 	+ sqlGen.getjoinSQL(tableName, reportType);
					// System.out.println(" from sql " + sql);
					columnSQL = sql;
					reportPanel.setColumnSQL(sql);
					
					UserJob jb = (UserJob) teamplates.get(templateIdSelected);
					jb.setSql(sql);
					teamplates.put(templateIdSelected, jb);
					// if(columnSQL.length() > 0)
					// reportPanel.populateReportData(columnSQL,false);
				} */
				//choiceColumns.cmodList2.removeAllElements();
			}
		});
	}
	private void saveJobs(Vector<UserJob> jobs2) {
		// TODO Auto-generated method stub
		if(jobs2 != null || jobs2.size() > 0) {
			for(int i=0;i<jobs2.size();i++) {
				UserJob job = jobs2.elementAt(i);
				
				try {
					job = (UserJob) remoteTask.saveUserJob(job);
					if(jobs == null) 
						this.jobs = new Vector<UserJob>();
					jobs.add(job);
					
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else {
			setJobs(jobs);
		}
		
	}
	private Vector<UserJob> getUserTempleates(int userid) {
		Vector<UserJob> jobs = null;
		try {
			jobs =	remoteTask.getUserJob(userid,"POSITION"); 
			if((jobs == null) || jobs.isEmpty()) {
				UserJob job = new UserJob();
				job.setId(0);
				job.setTabid(0);
				job.setUserID(user.getId());
				job.setType(reportType.toUpperCase());
				job.setTreeNodeName("DefaultTemplate");
				jobs = new Vector<UserJob>();
				jobs.add(job);
				saveJobs(jobs);
				
			}else {
				setJobs(jobs);
				}
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				commonUTIL.displayError("JFrameNewReport ", " getUserTempleates " , e);
				
			}
			return jobs;
		}
	

	//ReportColumns columns = new ReportColumns();
	//ReportSQLGenerator sqlGen = new ReportSQLGenerator();

}

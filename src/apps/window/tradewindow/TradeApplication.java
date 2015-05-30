package apps.window.tradewindow;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ComponentInputMap;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.table.DefaultTableModel;

import org.dyno.visual.swing.layouts.Bilateral;
import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;

import util.ClassInstantiateUtil;
import util.commonUTIL;
import apps.window.operationwindow.jobpanl.FilterValues;
import apps.window.operationwindow.trialBalance.JTreeTable;
import apps.window.operationwindow.trialBalance.TradeTreeView;
import apps.window.tradewindow.cashflowpanel.CashFlowPanel;
import apps.window.tradewindow.panelWindow.FeesPanel;
import apps.window.tradewindow.panelWindow.LimitPanel;
import apps.window.tradewindow.panelWindow.MessagePanel;
import apps.window.tradewindow.panelWindow.PostingPanel;
import apps.window.tradewindow.panelWindow.TaskPanel;
import apps.window.tradewindow.panelWindow.TransferPanel;
import apps.window.utilwindow.AuditTradeWindow;
import apps.window.utilwindow.ButtonsIconsFactory;
import apps.window.utilwindow.openTradeDialog;
import beans.Audit;
import beans.Trade;
import beans.Users;
import bo.transfer.rule.ProductTransferRule;

import com.jidesoft.docking.DefaultDockableHolder;
import com.jidesoft.docking.DockContext;
import com.jidesoft.docking.DockableFrame;
import com.jidesoft.icons.JideIconsFactory;

import dsEventProcessor.TaskEventProcessor;
import dsManager.TaskManager;
import dsServices.RemoteAccount;
import dsServices.RemoteBOProcess;
import dsServices.RemoteLimit;
import dsServices.RemoteProduct;
import dsServices.RemoteReferenceData;
import dsServices.RemoteTask;
import dsServices.RemoteTrade;
import dsServices.ServerConnectionUtil;

//VS4E -- DO NOT REMOVE THIS LINE!
public class TradeApplication extends DefaultDockableHolder {

	private static final long serialVersionUID = 1L;
	private JToolBar jToolBar1;
	private JPanel jPanel1;
	private JTabbedPane jTabbedPane0;
	private JToolBar jToolBar0;
	private JPanel jPanel0;
	private JButton jButton1;
	private JButton jButton2;
	private JButton jButton0;
	private JButton jButton4;
	private JButton jButton5;
	private JButton jButton6;
	private JButton jButton7;
	private JButton  jButton9;
	private JPanel jPanel2;
	private JSplitPane jSplitPane0;
	private JPanel jPanel3;
	private JTabbedPane jTabbedPane1;
	// private testingTradePanel jPanel4;
	private JButton jButton3;
	private JButton jButton11;
	FilterValues filterValues = null;
	// private JButton jButton4; // for rollOver
	private static final String PREFERRED_LOOK_AND_FEEL = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";

	public static ServerConnectionUtil de = null;
	RemoteTrade remoteTrade;
	RemoteBOProcess boremote;
	RemoteTask remoteTask;
	RemoteReferenceData remoteReference;
	RemoteAccount remoteAccount;
	RemoteProduct remoteProduct;
    RemoteLimit remoteLimit;
	TradePanel tradeP = null;
	CommonPanel productWindowpanel = null;

	Hashtable<String, ProductTransferRule> rulehandlers = new Hashtable<String, ProductTransferRule>();
	ProductTransferRule transferRule = null;

	Trade trade = new Trade();

	String panelName = "";
	TransferPanel transferPanel;
	LimitPanel limitPanel = null;
	PostingPanel postingPanel;
	TaskPanel taskPanel;
	MessagePanel messagePanel;
	apps.window.tradewindow.panelWindow.SDIPanel sdiPanel;
	FeesPanel feesPanel;

	Users userName = null;
	Users forTaskUser = null;

	JMenu cashFlow = null;

	String productTypeName = "";
	TaskManager taskManager = null;

	int tradeId = 0;
	// Trade tradeW = null;

	String s[] = { "Tradeid", "ProductName" };
	DefaultTableModel tablemodel = new DefaultTableModel(s, 0) {
		@Override
		public boolean isCellEditable(int row, int column) {
			// all cells false
			return false;
		}

	};

//	final JDialogTable showAllTrades = new JDialogTable(tablemodel);
      openTradeDialog  showAllTrades = null; // new javax.swing.JPanel();

	public TradeApplication(String name, int tradeId, Users user) {
		TradeApplication task = new TradeApplication(name, user);

		this.tradeId = tradeId;
	}

	public TradeApplication(String name, Users user) {
		taskManager = new TaskManager("localhost",
				commonUTIL.getLocalHostName(), "TaskManager   "
						+ user.getUser_name());
		taskManager.start(taskManager);

		initComponents(name, user);

		taskManager.setTradWIndow(this);
	}

	public static void main(String args[]) {
		Users user = new Users();
		user.setUser_name("PPPP");
		TradeApplication tradeAPp = new TradeApplication("MM", user);
		// tradeAPp.set
		tradeAPp.setVisible(true);

	}
	private Icon getIcon(String path) {
		java.net.URL imgURL = getClass().getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL, "");
		}
		return null;

	}
	public JPanel JInternalReportFrame() {
		JPanel pp = new JPanel();
		pp.setLayout(new GroupLayout());
		pp.add(getJPanel0(), new Constraints(new Bilateral(6, 9, 978),
				new Bilateral(8, 7, 10, 499)));
		return pp;
	}
	class ClockLabel extends JLabel implements ActionListener {

		  public ClockLabel() {
		    super("" + new Date());
		    Timer t = new Timer(1000, this);
		    t.start();
		  }

		  public void actionPerformed(ActionEvent ae) {
		    setText((new Date()).toString());
		  }
		}
	 ClockLabel clock = new ClockLabel();
	public DockableFrame getTempalteframe(String name1) {
		String iconPath = "/resources/icon/report_filter.png";
		Icon icon = getIcon(iconPath);
		
		
		DockableFrame frameT = new DockableFrame(name1 + " " + clock.getText(),JideIconsFactory.getImageIcon(JideIconsFactory.DockableFrame.FRAME1));
		frameT.add(clock);
		frameT.setName(name1 + " " + clock.getText());
		frameT.setAvailableButtons(DO_NOTHING_ON_CLOSE);
		frameT.getContentPane().add(JInternalReportFrame(), BorderLayout.CENTER);
		frameT.setInitSide(DockContext.DOCK_SIDE_CENTER);
		frameT.setInitMode(DockContext.STATE_FRAMEDOCKED);
		frameT.setTitle(frameT.getName());
		frameT.setTabTitle(frameT.getName());
		frameT.setPreferredSize(new Dimension(200, 200));
		frameT.setMinimumSize(new Dimension(100, 100));
		//frameT.s
	///	tempalteframe.getContext().setInitMode(DockContext.DOCK_SIDE_ALL_AND_CENTER);
	//	tempalteframe.getContext().setInitSide(DockContext.DOCK_SIDE_WEST);
	//	tempalteframe.getContext().setInitIndex(0);
	
		return frameT;
		
	}
	private void initComponents(String name, Users user) {
	//	installLnF();
		setUserName(user);
	//	setTitle(name + " Trade Window " + user.getUser_name()+ " :"+user.getId());
	//	setTitle(title)
		//setLayout(new GroupLayout());
	//	this.getContentPane().setBackground(commonUTIL.getColors());
		// setFont(new Font("SansSerif-12", Font.PLAIN, 12));
		init(name, user);
		initproductTradePanel(name, user);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getDockingManager().setUndoLimit(10);
		getDockingManager().addUndoableEditListener(new UndoableEditListener() {
			public void undoableEditHappened(UndoableEditEvent e) {
				// refreshUndoRedoMenuItems();
			}
		});
		
		getDockingManager().beginLoadLayoutData();
		getDockingManager().addFrame(getTempalteframe(name));
		getDockingManager().loadLayoutData();
	//	setLayout(new GroupLayout());
		toFront();
		setVisible(true);
		showAllTrades = new openTradeDialog(this,"SelectTrade",remoteTrade,remoteReference,boremote,remoteTask);
		
		showAllTrades.setLocationRelativeTo(this);
	//	add(getJPanel0(), new Constraints(new Bilateral(6, 9, 978),
			//	new Bilateral(8, 7, 10, 499)));

		if (productWindowpanel != null) {
			productWindowpanel.setBackground(commonUTIL.getColors());
			tradeP.setBackground(commonUTIL.getColors());
			
			productWindowpanel.setPanelValue(tradeP);
			jTabbedPane1.setVisible(true);
			jTabbedPane1.setBackground(commonUTIL.getColors());
			jTabbedPane1.add(productWindowpanel);
			final CashFlowPanel cashFlowp = makeCashFlowPanel(name);
			cashFlowp.setBackground(commonUTIL.getColors());
			//jTabbedPane1.add("CashFlow", cashFlowp);
			jTabbedPane1.setTitleAt(1,"<HTML> C<BR>A<BR>S<BR>H<BR> <BR> <BR>F<BR>L<BR>O<BR>W<BR>");
			jTabbedPane1.setTabPlacement(JTabbedPane.LEFT);
			tradeP.setPanelValue(productWindowpanel);
			jTabbedPane1.addMouseListener(new java.awt.event.MouseAdapter() {

				@Override
				public void mouseClicked(MouseEvent arg0) {
					// TODO Auto-generated method stub
					if(productTypeName.equalsIgnoreCase("REPO")) {
						Vector cashFlows = (Vector) productWindowpanel
								.getCashFlows();
						if (cashFlows != null) {
							cashFlowp.setCashFlows(cashFlows);
						}
						Vector cashFlows2= (Vector) productWindowpanel.getCashFlows2();
						if (cashFlows != null) {
							cashFlowp.setCashFlows2(cashFlows2);
						}
					} else {
					Vector cashFlows = (Vector) productWindowpanel
							.getCashFlows();
					if (cashFlows != null) {
						cashFlowp.setCashFlows(cashFlows);
					}
					}
				}

			});

		} else {
			// jTabbedPane1.setVisible(false);
			// bottomPanel.setVisible(false);
			// add(jTabbedPane1);
			// setSize(900,600);
		}
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		processTableDataOpen(tablemodel, getProductTypeName());
		JMenu fileMenu = new JMenu("File");
		//cashFlow = new JMenu("CashFlow");
	///	cashFlow.setEnabled(false);
		
		//@yogesh 07/02/2015
		// hide file menu to avoid save, saveasnew code from there
		//menuBar.add(fileMenu);
	//	menuBar.add(cashFlow);
		commonUTIL.setBackGroundColor(menuBar);
		// Create and add simple menu item to one of the drop down menu
		JMenuItem newAction = new JMenuItem("New");
		JMenuItem saveAction = new JMenuItem("Save");
		JMenuItem saveAsNewAction = new JMenuItem("Save as New");
		JMenuItem deleteAction = new JMenuItem("Delete");
		JMenuItem auditAsNewAction = new JMenuItem("Monitor");

		JMenuItem cutAction = new JMenuItem(".....");
		JMenuItem exitAction = new JMenuItem("Exit");
		JMenuItem opemAction = new JMenuItem("Open ");

		fileMenu.add(newAction);
		fileMenu.add(saveAction);
		fileMenu.addSeparator();
		fileMenu.add(opemAction);
		fileMenu.add(saveAsNewAction);
		fileMenu.add(auditAsNewAction);
		fileMenu.add(deleteAction);
		fileMenu.add(exitAction);
	//	cashFlow.add(cutAction);
		auditAsNewAction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (tradeP.getTrade() == null) {

					commonUTIL.showAlertMessage("Select Trade");
					return;
				}

				setTradeId(tradeP.getTrade().getId());
				String s[] = { "id", "Chanage Date", "Change Field", "Type",
						"Trade Version", "Column Values", "Trade Attributes",
						"User", "Group" };
				DefaultTableModel tablemodel = new DefaultTableModel(s, 0);
				processTableData(tablemodel);
				AuditTradeWindow showAudit = new AuditTradeWindow(tablemodel);
				showAudit.setTitle("Trade Audit Window");

				showAudit.setSize(700, 400);
				showAudit.setFocusable(false);
				showAudit.setVisible(true);

			}
		});
	
		saveAsNewAction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Call to menu saveASNEW " + trade);
				trade = new Trade();

				tradeP.buildTrade(trade, "SAVEASNEW");
				if (productWindowpanel != null) {
					productWindowpanel.buildTrade(trade, "SAVEASNEW");
				}
				if (trade.getTradedesc1() != null
						&& trade.getTradedesc1().trim().length() > 0) {

					trade.setUserID(userName.getId());

					try {

						trade.setId(0);
						trade.setStatus("NONE");
						trade.setAction("NEW");
						feesPanel.refreshFees();

						// trade.setFees(feesPanel.getFeesDataV());

						Vector tradestatus = new Vector();
						tradestatus = remoteTrade.saveTrade(trade, tradestatus);

						String statusT = (String) tradestatus.elementAt(0);

						int i = ((Integer) tradestatus.elementAt(1)).intValue();
						if (i <= -10) {
							commonUTIL.showAlertMessage((statusT));
							openTrade(trade, true);
							if(statusT.contains("Limit")) {
							Vector data = (Vector) remoteLimit.getBreachDetailsOnLimit(trade);
							tradeP.setLimitBreachMarkOnAction(i);
							limitPanel.fillJTabel(data);
							}
							
							return;
						}
						if (i == -4) {
							commonUTIL.showAlertMessage((statusT));
							return;
						}
						if (i == -3) {
							commonUTIL.showAlertMessage((statusT));
							return;
						}
						if (i > 0) {
							commonUTIL.showAlertMessage((statusT));
						tradeId = i;
						}
						if (i == -4) {
							commonUTIL
									.showAlertMessage("Trade is Lock by another User ");
							return;
						}
						tradeP.setLimitBreachMarkOnAction(i);
						//if (i > 0)
						//	commonUTIL.showAlertMessage("Trade Saved with  " 									+ i);

						// System.out.println("*************** " +i);
						trade = (Trade) remoteTrade.selectTrade(i);
						// tradeW = trade;
						tradeP.saveASNew(trade);
						//feesPanel.setTrade(trade);
						openTrade(trade,true);
						// taskManager.setTradWIndow(tradeP);
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
		});
		newAction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(trade == null) {
					trade = new Trade();
				}
				trade.setId(0);
				tradeId = 0;
				tradeP.buildTrade(trade, arg0.getActionCommand());
				if (productWindowpanel != null) {
					productWindowpanel.buildTrade(trade, "NEW");
				}

				// editMenu.setEnabled(false);
				getTradeTransfers(transferPanel);
				getTradePostings(postingPanel);
				getLimits(limitPanel);
				getTradeTask(taskPanel);
				getTradeMessages(messagePanel);
				feesPanel.refreshFees();

			}
		});
		deleteAction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int tradeID = 0;
				if (tradeP.getTrade() != null && tradeP.getTrade().getId() > 0) {
					tradeID = tradeP.getTrade().getId();
					Trade trade = new Trade();
					tradeP.buildTrade(trade, "SAVE");
					trade.setUserID(userName.getId());
					if (productWindowpanel != null) {
						productWindowpanel.buildTrade(trade, "SAVE");
					}
					trade.setVersionID(-1);
					trade.setId(tradeID);
					try {
						remoteTrade.removeTrade(trade);

					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				trade.setId(0);
				tradeP.buildTrade(trade, "NEW");
				if (productWindowpanel != null) {
					productWindowpanel.buildTrade(trade, "NEW");
				}

				// editMenu.setEnabled(false);
				getTradeTransfers(transferPanel);
				getTradeTask(taskPanel);
				getTradePostings(postingPanel);
				getLimits(limitPanel);
				getTradeMessages(messagePanel);
				commonUTIL.showAlertMessage("Trade " + tradeID + " Deleted");

			}
		});

		saveAction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (tradeP.getAction() == null) {
					commonUTIL.showAlertMessage("Select Action");
					return;
				}
				tradeP.buildTrade(trade, "SAVE");
				trade.setUserID(userName.getId());
				if (productWindowpanel != null) {
					productWindowpanel.buildTrade(trade, "SAVE");
				}

				try {
					trade.setFees(feesPanel.getFeesDataV());
					int i = remoteTrade.saveTrade(trade);
					tradeId = i;
					if (i == -4) {
						commonUTIL
								.showAlertMessage("Trade is Lock by another User ");
						return;
					}
					if (i == -3) {
						commonUTIL
								.showAlertMessage("Amend not allowed on Cancel Trade");
						return;
					}
					if (i > 0)
						commonUTIL.showAlertMessage("Trade Updated");

					// System.out.println("*************** " +i);
					trade = (Trade) remoteTrade.selectTrade(i);
					// tradeW = trade;
					tradeP.saveASNew(trade);
					openTrade(trade,true);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		
		opemAction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			//	String s[] = { "Tradeid", "ProductName" };
			//	showAllTrades.jTable1.removeAll();
			//	DefaultTableModel tablemodel = new DefaultTableModel(s, 0);
			//	processTableDataOpen(tablemodel, getProductTypeName());
			//	showAllTrades.jTable1.setModel(tablemodel);
				showAllTrades.setVisible(true);

			}
		});

		showAllTrades.jTable1
				.addMouseListener(new java.awt.event.MouseAdapter() {

					@Override
					public void mouseClicked(MouseEvent e) {
						String tradeid = showAllTrades.jTable1.getValueAt(
								showAllTrades.jTable1.getSelectedRow(), 0)
								.toString();
						int id = new Integer(tradeid).intValue();
						try {

							trade = (Trade) remoteTrade.selectTrade(id);
							System.out.println(trade.getAttributes());
							tradeId = trade.getId();
							// tradeW = trade; // used when task for same trade
							// is publish
							openTrade(trade,true);
							showAllTrades.dispose();
						} catch (RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}

				});
		setSize(1311, 725);
		 
	}

	private JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new JButton("Logs");
		//	jButton3.setText("jButton3");
			jButton3.setToolTipText("Logs");
			jButton3.setIcon(ButtonsIconsFactory.getImageIcon(ButtonsIconsFactory.Buttons.LOGS));
		}//
		return jButton3;
	}
	
	private JButton getJButton11() {
		if (jButton11 == null) {
			jButton11 = new JButton("Audit");
		//	jButton3.setText("jButton3");
			jButton11.setToolTipText("Audit");
			jButton11.setIcon(ButtonsIconsFactory.getImageIcon(ButtonsIconsFactory.Buttons.AUDIT));
		}//
		return jButton11;
	}

	/*
	 * private testingTradePanel getJPanel4() { if (jPanel4 == null) { jPanel4 =
	 * new testingTradePanel(); //jPanel4.setBorder(new LineBorder(Color.black,
	 * 1, false)); } return (testingTradePanel) jPanel4; }
	 */

	private JTabbedPane getJTabbedPane1() {
		if (jTabbedPane1 == null) {
			jTabbedPane1 = new JTabbedPane();
			// jTabbedPane1.addTab("jPanel3",new backOfficePanel());
			commonUTIL.setBackGroundColor(jTabbedPane1);
		}
		return jTabbedPane1;
	}

	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jPanel3 = new JPanel();
			jPanel3.setLayout(new GroupLayout());
		}
		commonUTIL.setBackGroundColor(jPanel3);
		return jPanel3;
	}

	private JSplitPane getJSplitPane0() {
		if (jSplitPane0 == null) {
			jSplitPane0 = new JSplitPane();
			jSplitPane0.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
			jSplitPane0.setDividerLocation(343);
			jSplitPane0.setOrientation(JSplitPane.VERTICAL_SPLIT);
			jSplitPane0.setTopComponent(getJTabbedPane1());
			commonUTIL.setBackGroundColor(jSplitPane0);
		}
		return jSplitPane0;
	}

	/*
	 * private JPanel getJPanel2() { if (jPanel2 == null) { jPanel2 =new
	 * testingTradePanel(); //jPanel2.setBorder(new LineBorder(Color.black, 1,
	 * false)); jPanel2.setLayout(new GroupLayout()); } return jPanel2; }
	 */

	private JButton getJButton7() {
		if (jButton7 == null) {
			jButton7 = new JButton();
			//jButton7.setBackground(Color.white);
			jButton7.setBorderPainted(false);
			jButton7.setMargin(new Insets(0,0,0,0));
			jButton7.setVerticalAlignment(SwingConstants.TOP);
			jButton7.setAlignmentX(TOP_ALIGNMENT);
			jButton7.setFocusPainted(false);
			jButton7.setContentAreaFilled(false);
			//jButton7.setIcon(new ImageIcon(getClass().getResource(
				//	"/resources/icon/New.jpg")));
			jButton7.setToolTipText("Delete");
		}
		return jButton7;
	}

	private JButton getJButton6() {
		if (jButton6 == null) {
			jButton6 = new JButton("Save As New ");
		//	jButton6.setIcon(new ImageIcon(getClass().getResource(
			//		"/resources/icon/open.png")));
			/*jButton6.setBorder(null);
			jButton6.setBorderPainted(false);
			jButton6.setMargin(new Insets(0,0,0,0));
			jButton6.setVerticalAlignment(SwingConstants.TOP);
			jButton6.setAlignmentX(TOP_ALIGNMENT);
			jButton6.setFocusPainted(false);
			jButton6.setContentAreaFilled(false); */
			jButton6.setIcon(ButtonsIconsFactory.getImageIcon(ButtonsIconsFactory.Buttons.SAVEASNEW));
			jButton6.setToolTipText("Save As New");
		}
		jButton6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tradeP.buildTrade(trade, "SAVEASNEW");
				trade.setUserID(userName.getId());
				if (productWindowpanel != null) {

					productWindowpanel.buildTrade(trade, "SAVEASNEW");
					if (trade.getProductId() == 0) {

						return;
					}
				}

				try {
					trade.setId(0);
					tradeId = 0;
					// tradeW = null;
					trade.setStatus("NONE");
					trade.setAction("NEW");
					feesPanel.refreshFees();
					int i = remoteTrade.saveTrade(trade);
					if (i == -4) {
						commonUTIL
								.showAlertMessage("Trade is Lock by another User ");
						return;
					}
					tradeId = i;
					if (i > 0)
						JOptionPane.showMessageDialog(null,
								"Trade Saved with  " + i, null,
								JOptionPane.INFORMATION_MESSAGE);
					// System.out.println("*************** " +i);
					trade = (Trade) remoteTrade.selectTrade(i);
					// tradeW = trade;
					tradeP.saveASNew(trade);
					tradeP.setTrade(trade);
					// taskManager.setTradWIndow(tradeP);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		return jButton6;
	}

	private JButton getJButton5() {
		if (jButton5 == null) {
			jButton5 = new JButton("Open ");
		//	jButton5.setIcon(new ImageIcon(getClass().getResource(
			//		"/resources/icon/open.png")));
		/*	jButton5.setBorderPainted(false);
			jButton5.setMargin(new Insets(0,0,0,0));
			jButton5.setVerticalAlignment(SwingConstants.TOP);
			jButton5.setAlignmentX(TOP_ALIGNMENT);
			jButton5.setFocusPainted(false);
			jButton5.setContentAreaFilled(false);
			//jButton5.setSize(20, 5); */
			jButton5.setIcon(ButtonsIconsFactory.getImageIcon(ButtonsIconsFactory.Buttons.SMALLOPEN));
			jButton5.setToolTipText("Open");
		}
		jButton5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String s[] = { "Tradeid", "ProductName" };
				/*showAllTrades.jTable1.removeAll();
				DefaultTableModel tablemodel = new DefaultTableModel(s, 0);
				processTableDataOpen(tablemodel, getProductTypeName());
				showAllTrades.jTable1.setModel(tablemodel);*/
				showAllTrades.setVisible(true);

			}
		});
		return jButton5;
	}

	private JButton getJButton9() {
		if (jButton9 == null) {
			jButton9 = new JButton();
			jButton9.setText("RealTime");
			jButton9.setToolTipText("RealTimeQuote");

		}
		jButton9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			if(productTypeName.equalsIgnoreCase("FX")) {
				tradeP.setRealTimeFlag(true);
			  }
			}
		});
		return jButton9;
	}
	private JButton getJButton4() {
		if (jButton4 == null) {
			jButton4 = new JButton("RollOverView");
		//	jButton4.setText("RollOverView");
			jButton4.setToolTipText("RollOverView");
			jButton4.setIcon(ButtonsIconsFactory.getImageIcon(ButtonsIconsFactory.Buttons.ROLLOVER));

		}
		jButton4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					System.out.println(trade.getId());
					Vector rolloverH = (Vector) remoteTrade
							.getTradeRollOverHierarchies(trade.getId());
					
					
					
					
					///RollTradePanel panelr = new RollTradePanel();
					//panelr.setData(rolloverH);
					TradeTreeView tb = new TradeTreeView();
					tb.getFillTree(rolloverH);
					if(trade != null) {
					JTreeTable treeTable  = tb.getjTreeTrade(trade.getId());
					JFrame rollView = new JFrame();
					//rollView.add(panelr);

					rollView.getContentPane().add(new JScrollPane(treeTable));
					rollView.setVisible(true);
					rollView.setSize(1128, 240);

					}
					
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		return jButton4;
	}

	private JButton getJButton0() {
		if (jButton0 == null) {
			jButton0 = new JButton("CashFlow ");
			//jButton0.setText("SWT");
		/*	jButton0.setBorder(null);
			jButton0.setBorderPainted(false);
			jButton0.setMargin(new Insets(0,0,0,0));
			jButton0.setVerticalAlignment(SwingConstants.TOP);
			jButton0.setAlignmentX(TOP_ALIGNMENT);
			jButton0.setFocusPainted(false);
			jButton0.setContentAreaFilled(false); */
			jButton0.setToolTipText("CashFlow"); 
			jButton0.setIcon(ButtonsIconsFactory.getImageIcon(ButtonsIconsFactory.Buttons.CASHFLOW));
		}
		return jButton0;
	}

	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton("Save ");
		//	jButton2.setIcon(new ImageIcon(getClass().getResource(
			//		"/resources/icon/New.jpg")));
			jButton2.setToolTipText("Save");
		/*	jButton2.setBorder(null);
			jButton2.setBorderPainted(false);
			jButton2.setMargin(new Insets(0,0,0,0));
			jButton2.setVerticalAlignment(SwingConstants.TOP);
			jButton2.setAlignmentX(TOP_ALIGNMENT);
			jButton2.setFocusPainted(false);
			jButton2.setContentAreaFilled(false); */
			jButton2.setIcon(ButtonsIconsFactory.getImageIcon(ButtonsIconsFactory.Buttons.MEDIUMSAVE));
		}
		jButton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tradeP.buildTrade(trade, "SAVE");
				if (tradeP.getAction() == null) {
					commonUTIL.showAlertMessage("Select Action");
					return;
				}
				trade.setUserID(userName.getId());
				if (productWindowpanel != null) {
					productWindowpanel.buildTrade(trade, "SAVE");
				}

				try {
					trade.setFees(feesPanel.getFeesDataV());
					int i = remoteTrade.saveTrade(trade);
					if (i == -4) {
						commonUTIL
								.showAlertMessage("Trade is Lock by another User ");
						return;
					}
					if (i == -3) {
						commonUTIL
								.showAlertMessage("Amend not allowed on Cancel Trade");
						return;
					}
					if (i > 0)
						commonUTIL.showAlertMessage("Trade Updated");

					// System.out.println("*************** " +i);
					trade = (Trade) remoteTrade.selectTrade(i);
					// tradeW = trade;
					tradeId = i;
					tradeP.saveASNew(trade);
					openTrade(trade,true);
					// taskManager.setTradWIndow(tradeP);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

		return jButton2;
	}

	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton("New ");
		//	jButton1.setBorder(null);
		/*	jButton1.setBorderPainted(false);
			jButton1.setMargin(new Insets(0,0,0,0));
			jButton1.setVerticalAlignment(SwingConstants.TOP);
			jButton1.setAlignmentX(TOP_ALIGNMENT);
			jButton1.setFocusPainted(false);
			jButton1.setContentAreaFilled(false); */
			jButton1.setIcon(ButtonsIconsFactory.getImageIcon(ButtonsIconsFactory.Buttons.NEW));
		//	jButton1.setIcon(new ImageIcon(getClass().getResource(
		//			"/resources/icon/open.png")));
			jButton1.setToolTipText("New");
		}
		jButton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(trade != null) 
				trade.setId(0);
				tradeId = 0;
				tradeP.buildTrade(trade, "New");
				if (productWindowpanel != null) {
					productWindowpanel.buildTrade(trade, "NEW");
				}

				// editMenu.setEnabled(false);
				getTradeTransfers(transferPanel);
				getTradePostings(postingPanel);
				getTradeTask(taskPanel);
				getTradeMessages(messagePanel);
				getLimits(limitPanel);
				feesPanel.refreshFees();
				
			}

		});
		return jButton1;
	}

	private JPanel getJPanel0() {
		if (jPanel0 == null) {
			jPanel0 = new JPanel();
			// jPanel0.setBorder(new LineBorder(Color.black, 1, false));
			jPanel0.setLayout(new GroupLayout());
			jPanel0.add(getJToolBar0(), new Constraints(new Bilateral(6, 12, 500), new Leading(5, 31, 10, 10)));
			jPanel0.add(getJTabbedPane0(), new Constraints(new Leading(8, 1250, 12, 12), new Leading(42, 582, 10, 10)));
		//	jPanel0.add(getJToolBar1(), new Constraints(new Leading(6, 23, 109,
			//		109), new Leading(34, 100, 20, 10)));
			jPanel0.setBackground(commonUTIL.getColors());
		}
		return jPanel0;
	}

	private JToolBar getJToolBarB() {
		
			JToolBar jToolBarB = new JToolBar();
			// jToolBar0.setBorder(new LineBorder(Color.black, 1, false));
			//jToolBarB.setLayout(new BorderLayout());
			//jToolBar0.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			//jToolBarB.add(getJButton1());
			jToolBarB.add(getJButton5());
			//jToolBarB.add(getJButton2());
			//jToolBarB.add(getJButton6());
			//jToolBar0.add(getJButton7());
			jToolBarB.add(getJButton0());
			jToolBarB.add(getJButton3());
			jToolBarB.add(getJButton4());
			jToolBarB.add(getJButton11());
		//	jToolBar0.add(clock,BorderLayout.EAST);
			//jToolBar0.add(getJButton11(),);
			//commonUTIL.setBackGroundColor(jToolBar0);
		
		return jToolBarB;
	}
	private JToolBar getJToolBar0() {
		if (jToolBar0 == null) {
			jToolBar0 = new JToolBar();
			// jToolBar0.setBorder(new LineBorder(Color.black, 1, false));
			jToolBar0.setLayout(new BorderLayout());
			jToolBar0.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			jToolBar0.add(getJToolBarB(), BorderLayout.WEST);
			/*jToolBar0.add(getJButton1());
			jToolBar0.add(getJButton5());
			jToolBar0.add(getJButton2());
			jToolBar0.add(getJButton6());
			//jToolBar0.add(getJButton7());
			jToolBar0.add(getJButton0());
			jToolBar0.add(getJButton3());
			jToolBar0.add(getJButton4());
			jToolBar0.add(getJButton11()); */
			jToolBar0.add(clock,BorderLayout.EAST);
			//jToolBar0.add(getJButton11(),);
			//commonUTIL.setBackGroundColor(jToolBar0);
		}
		return jToolBar0;
	}

	private JTabbedPane getJTabbedPane0() {
		if (jTabbedPane0 == null) {
			jTabbedPane0 = new JTabbedPane();
			// jTabbedPane0.setBorder(new LineBorder(Color.black, 1, false));

			jTabbedPane0.addTab("DealEntry", getJPanel1());
			jTabbedPane0.addTab("Instruction", sdiPanel);
			jTabbedPane0.addTab("Jobs", taskPanel);
			jTabbedPane0.addTab("Ledgers", transferPanel);
			jTabbedPane0.addTab("Journals", postingPanel);
			
			jTabbedPane0.addTab("Message ", messagePanel);
			jTabbedPane0.addTab("Fees ", feesPanel);
			jTabbedPane0.addTab("Limit ", limitPanel);
			
			commonUTIL.setLabelFont(jTabbedPane0);
			jTabbedPane0.addMouseListener(new java.awt.event.MouseAdapter() {

				@Override
				public void mouseClicked(MouseEvent arg0) {
					String title = jTabbedPane0.getTitleAt(jTabbedPane0.getSelectedIndex());
					if(title.equalsIgnoreCase("Instruction")) {
						tradeP.setSDIPanelInstruction();
					}
				}

			});

		}
		return jTabbedPane0;
	}

	private JPanel getSPanel(JPanel panel) {
		JPanel sdPanel = null;
		if (sdPanel == null) {
			sdPanel = new JPanel();
			sdPanel.setLayout(new GroupLayout());
			commonUTIL.setLabelFont(panel);
			sdPanel.add(panel, new Constraints(new Bilateral(41, 9, 94),
					new Bilateral(34, 12, 5)));
			commonUTIL.setLabelFont(sdPanel);
		}

		return sdPanel;
	}

	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
		//	jPanel1.setLayout(new GroupLayout());
			if (productWindowpanel != null) {
				jPanel1.add(tradeP);
				jPanel1.add(getJSplitPane0());
				//jPanel1.add(tradeP, new Constraints(new Bilateral(4, 5, 0),
						//new Leading(5, 226, 10, 10)));
				//jPanel1.add(getJSplitPane0(), new Constraints(new Bilateral(4,
				//		5, 7), new Leading(237, 371, 10, 10)));
			} else {
			//	jPanel1.add(tradeP, new Constraints(new Bilateral(41, 9, 94),
					//	new Bilateral(34, 12, 5)));
				jPanel1.add(tradeP);
			}
		}
		commonUTIL.setBackGroundColor(jPanel1);
		return jPanel1;
	}

	private JToolBar getJToolBar1() {
		if (jToolBar1 == null) {
			jToolBar1 = new JToolBar();
			jToolBar1.setOrientation(SwingConstants.VERTICAL);
			jToolBar1.setAlignmentX(0.0f);
			jToolBar1.add(getJButton0());
			jToolBar1.add(getJButton3());
			jToolBar1.add(getJButton4());
			jToolBar1.add(getJButton9());
			jToolBar1.addComponentListener(new ComponentAdapter() {

				public void componentResized(ComponentEvent event) {
					jToolBar1ComponentComponentResized(event);
				}
			});
		}
		commonUTIL.setLabelFont(jToolBar1);
		return jToolBar1;
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
	 * Main entry of the class. Note: This class is only created so that you can
	 * easily preview the result at runtime. It is not expected to be managed by
	 * the designer. You can modify it as you like.
	 */

	private void init(String name, Users user) {

		if(name.equalsIgnoreCase("Future") || name.equalsIgnoreCase("FutureContract"))
	    	setProductTypeName("FutureContract");
	    	else 
	    		setProductTypeName(name);
		this.userName = user;
		// setUserName(user);
		forTaskUser = user;
		de = ServerConnectionUtil.connect("localhost", 1099,
				commonUTIL.getLocalHostName());
		try {
			remoteTrade = (RemoteTrade) de.getRMIService("Trade");
			boremote = (RemoteBOProcess) de.getRMIService("BOProcess");
			remoteTask = (RemoteTask) de.getRMIService("Task");
			remoteReference = (RemoteReferenceData) de
					.getRMIService("ReferenceData");
			remoteAccount = (RemoteAccount) de.getRMIService("Account");
			remoteProduct = (RemoteProduct) de.getRMIService("Product");
			remoteLimit = (RemoteLimit) de.getRMIService("Limit");
			
			// System.out.println(remoteTrade);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			commonUTIL.displayError("TradeApplication ", "init" + name, e);
		}
		setTitle(name);
		panelName = name;
		setTitle("Trade Window " + getUserName());
	}

	private void initproductTradePanel(String name, Users user) {
		tradeP = makeTradePanel(name,user);
		if(tradeP == null) {
		tradeP = makeTradePanel(name); 
	}
		tradeP.setUser(user);
		filterValues = new FilterValues(remoteReference, remoteTrade, remoteTask, boremote);
		productWindowpanel = makeProductPanel(name);
		tradeP.setTradeApplication(this);
		tradeP.setFilterValue(filterValues);
		initTransferPanel(name, user);
		initPostingPanel(name, user);
		initSdiPanel(name, user);
		initFeesPanel(name, user);
		initTaskPanel(name, user);
		initMessagePanel(name,user);
		initLimitPanel(name,user);
		tradeP.setSDIPanel(sdiPanel);
		tradeP.setTaskPanel(taskPanel);
		tradeP.setTradeTransfers(transferPanel);
	
		tradeP.setTradePostings(postingPanel);
		tradeP.setFEESPanel(feesPanel);
		tradeP.setLimitPanel(limitPanel);
		// hot keys 
		if(tradeP.getHotKeysPanel() != null) {
		 InputMap keyMap = new ComponentInputMap(tradeP.getHotKeysPanel());
		    keyMap.put(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S,
		            java.awt.Event.CTRL_MASK), "action_save");
		    keyMap.put(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N,
		            java.awt.Event.CTRL_MASK), "action_new");
		    keyMap.put(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D,
		            java.awt.Event.CTRL_MASK), "action_del");
		    keyMap.put(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A,
		            java.awt.Event.CTRL_MASK), "action_saveasnew");
		    keyMap.put(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O,
		            java.awt.Event.CTRL_MASK), "action_opentrade");
		    keyMap.put(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C,
		            java.awt.Event.CTRL_MASK), "action_cashflow");
		    SwingUtilities.replaceUIActionMap(tradeP.getHotKeysPanel(), tradeP.getHotKeysActionMapper());
		    SwingUtilities.replaceUIInputMap(tradeP.getHotKeysPanel(), JComponent.WHEN_IN_FOCUSED_WINDOW,
		            keyMap);
		    tradeP.getHotKeysActionMapper().put("action_opentrade", new AbstractAction() {
		        @Override
		        public void actionPerformed(ActionEvent e) {
		        	showAllTrades.setVisible(true);
		        }
		    });
		}
		// taskManager.setTradWIndow(tradePanel);
	}

	private void initLimitPanel(String name, Users user) {
		// TODO Auto-generated method stub
		limitPanel = (apps.window.tradewindow.panelWindow.LimitPanel) makeBOOperationPanel("Limit");
	}

	private void initTransferPanel(String name, Users user) {
		transferRule = getTransferRuleHandler(name);
		transferRule.setRefDate(remoteReference);
		transferRule.setRemoteBOProcess(boremote);
		transferRule.setRemoteTrade(remoteTrade);
		transferRule.setRemoteProduct(remoteProduct);

		transferPanel = (apps.window.tradewindow.panelWindow.TransferPanel) makeBOOperationPanel("Transfer");
		transferPanel.setRemoteProduct(remoteProduct);
		transferPanel.setRemoteTrade(remoteTrade);
		transferPanel.setRemoteBO(boremote);
		transferPanel.setRefData(remoteReference);
		transferPanel.setUser(userName);

	}

	private void initPostingPanel(String name, Users user) {
		postingPanel = (apps.window.tradewindow.panelWindow.PostingPanel) makeBOOperationPanel("Posting");
		postingPanel.setRemoteAccount(remoteAccount);
		postingPanel.setRemoteRef(remoteReference);
	}

	private void initSdiPanel(String name, Users user) {
		taskPanel = (TaskPanel) makeBOOperationPanel("Task");
	}

	private void initMessagePanel(String name, Users user) {
		messagePanel = (MessagePanel) makeBOOperationPanel("Message");
		messagePanel.setRemoteRef(remoteReference);
		messagePanel.setRemoteBO(boremote);
		messagePanel.setRemoteTrade(remoteTrade);
	}
	
	private void initFeesPanel(String name, Users user) {
		feesPanel = (FeesPanel) makeBOOperationPanel("Fees");
	}

	private void initTaskPanel(String name, Users user) {
		sdiPanel = (apps.window.tradewindow.panelWindow.SDIPanel) makeBOOperationPanel("SDI");
		sdiPanel.setRule(transferRule);
	}

	private void jToolBar1ComponentComponentResized(ComponentEvent event) {
	}

	public Users getUserName() {
		return userName;
	}

	public void setUserName(Users userName) {
		this.userName = userName;
		if(tradeP != null)
		tradeP.setUser(userName);
	}

	public String getProductTypeName() {
		return productTypeName;
	}

	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
	}

	public int getTradeId() {
		return tradeId;
	}

	public void setTradeId(int tradeId) {
		this.tradeId = tradeId;
	}

	public void setOpenTrade(Trade newTrade) {

		tradeP.openTrade(newTrade);
		tradeP.setTrade(newTrade);
		if (productWindowpanel != null) {
			productWindowpanel.openTrade(newTrade);
		}
		trade = newTrade;
		getTradeTask(taskPanel);
		getTradeMessages(messagePanel);
		transferPanel.setTrade(trade);
		getTradeTransfers(transferPanel);
		getTradePostings(postingPanel);
		getLimits(limitPanel);
		// TODO Auto-generated method stub

	}

	public void openTrade(Trade trade,boolean flag) {
		if (productWindowpanel != null) {
			productWindowpanel.openTrade(trade);
			tradeP.openTrade(trade);
		  } else {
			  if(flag) {
				  tradeP.openTrade(trade);
			  }
			  tradeP.setTrade(trade);
			  this.trade = trade;
		}
		// if(e.getClickCount() == 2)
		getTradeSDI(sdiPanel);
		getTradeTask(taskPanel);
		getTradeTransfers(transferPanel);
		getTradePostings(postingPanel);
		getTradeMessages(messagePanel);
		getLimits(limitPanel);
		feesPanel.setTrade(trade);
	}

	public void getTradeTransfers(BackOfficePanel panel) {
		try {
			if(trade != null) {
			panel.setTrade(trade);
			panel.fillJTabel((Vector) boremote.queryWhere("Transfer",
					"tradeId = " + trade.getId()));
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void getTradePostings(PostingPanel postingPanel) {
		// TODO Auto-generated method stub
		try {
			if(trade != null ) {
			String sql = "tradeid = " + trade.getId()
					+ " and linkTo = 0 order by type,acceventtype  desc ";
			postingPanel.fillJTabel((Vector) remoteAccount
					.getPostingonWhereClause(sql));
			postingPanel.setTrade(trade);
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	private void getTradeMessages(MessagePanel messagePanel) {
		// TODO Auto-generated method stub
		try {
			if(trade != null) {
			messagePanel.fillJTabel((Vector) boremote.getMessageOnTrade(trade.getId()));
					
			messagePanel.setTrade(trade);
			messagePanel.setUsers(getUserName());
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	private void getLimits(LimitPanel limitPanel) {
		// TODO Auto-generated method stub
		try {
			if(trade != null) {
				Vector data = (Vector) remoteLimit.getBreachDetailsOnLimit(trade);
				limitPanel.fillJTabel(data);
					
			//messagePanel.setTrade(trade);
			//messagePanel.setUsers(getUserName());
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public void getTradeTask(BackOfficePanel panel) {
		try {
			// System.out.println(trade);
			if(trade != null) {
			Vector data = (Vector) remoteTask.selectTaskWhere("tradeId = "
					+ trade.getId());
			panel.fillJTabel((data));
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void getTradeSDI(BackOfficePanel panel) {
		//try {
			// System.out.println(trade);
			if(trade != null) {
			panel.setTrade(trade);
			//Vector data = (Vector) remoteTrade.getSDisOnTrade(trade);
	//.setSDIPanelInstruction();
			}
			//if(!commonUTIL.isEmpty(data)) 
			//  panel.fillJTabel(data);
			//  else 
			///	 commonUTIL.showAlertMessage("SDI Missing for " + trade.getId());
			//return;
		//	}
		//} catch (RemoteException e) {
			// TODO Auto-generated catch block
		//	e.printStackTrace();
		//}
	}

	public void getTradeFees(BackOfficePanel panel) {

		panel.setTrade(trade);

	}

	protected CommonPanel makeProductPanel(String name) {
		String productWindowName = "apps.window.tradewindow."
				+ name.toUpperCase() + "Panel";
		CommonPanel panel = null;

		try {
			Class class1 = ClassInstantiateUtil.getClass(productWindowName,
					true);
			panel = (CommonPanel) class1.newInstance();
			// productWindow = (BondPanel)
		} catch (Exception e) {
			System.out.println(e);
		}

		return panel;
	}

	protected TradePanel makeTradePanel(String name,Users user) {
		String productWindowName = "apps.window.tradewindow."
				+ name.toUpperCase() + "TradePanel";
		TradePanel panel = null;

		try {
			Class class1 = ClassInstantiateUtil.getClass(productWindowName,
					true);
			if(user != null)
			panel = (TradePanel) class1.getConstructor(Users.class).newInstance(user);
			else 
				panel = (TradePanel) class1.getConstructor().newInstance();
			// productWindow = (BondPanel)
		} catch (Exception e) {
			System.out.println(e);
		}

		return panel;
	}
	protected TradePanel makeTradePanel(String name) {
		String productWindowName = "apps.window.tradewindow."
				+ name.toUpperCase() + "TradePanel";
		TradePanel panel = null;

		try {
			Class class1 = ClassInstantiateUtil.getClass(productWindowName,
					true);
			
				panel = (TradePanel) class1.getConstructor().newInstance();
			// productWindow = (BondPanel)
		} catch (Exception e) {
			System.out.println(e);
		}

		return panel;
	}

	protected BackOfficePanel makeBOOperationPanel(String name) {
		String productWindowName = "apps.window.tradewindow.panelWindow."
				+ name + "Panel";
		BackOfficePanel panel = null;

		try {
			Class class1 = ClassInstantiateUtil.getClass(productWindowName,
					true);
			panel = (BackOfficePanel) class1.newInstance();
			// productWindow = (BondPanel)
		} catch (Exception e) {
			System.out.println(e);
		}

		return panel;
	}

	protected CashFlowPanel makeCashFlowPanel(String name) {
		String productWindowName = "apps.window.tradewindow.cashflowpanel."
				+ name.toUpperCase() + "CashFlowPanel";
		CashFlowPanel panel = null;

		try {
			Class class1 = ClassInstantiateUtil.getClass(productWindowName,
					true);
			panel = (CashFlowPanel) class1.newInstance();
			// productWindow = (BondPanel)
		} catch (Exception e) {
			System.out.println(e);
		}

		return panel;
	}

	private ProductTransferRule getTransferRuleHandler(String name) {
		String productTransfer = "bo.transfer.rule.Generate"
				+ name.toUpperCase() + "TransferRule";
		ProductTransferRule transferRuleHandler = null;

		try {
			transferRuleHandler = (ProductTransferRule) rulehandlers.get(name);
			if (transferRuleHandler == null) {
				Class class1 = ClassInstantiateUtil.getClass(productTransfer,
						true);
				transferRuleHandler = (ProductTransferRule) class1
						.newInstance();
				rulehandlers.put(name, transferRuleHandler);
			}
			// productWindow = (BondPanel)
		} catch (Exception e) {
			commonUTIL.displayError("JFrameTradeApplication  ",
					"getTransferRuleHandler <<<<< not able to create Handler ",
					e);
		}

		return transferRuleHandler;
	}

	public synchronized void processTasks(TaskEventProcessor taskEvent) {
		///System.out.println(getTitle() + "  " + tradeId + " version   "
			//	+ tradeP.getTrade().getId() + " version   "
			//	+ tradeP.getTrade().getVersion());
		String userid = getTitle().substring(getTitle().indexOf(":") + 1,
				getTitle().length());
		int uid = new Integer(userid).intValue();
		if ((taskEvent.getUserID() != uid)
				&& (taskEvent.getTradeID() == tradeP.getTrade().getId())) {
			commonUTIL.showAlertMessage("Trade amended by another user ");
			openTrade(taskEvent.getTrade(),true);
		}
		tradeP.processTask(taskEvent, forTaskUser);
	}

	private void processTableData(DefaultTableModel model) {
		Vector vector;
		try {
			vector = (Vector) remoteTrade.getAuditedTrade(getTradeId());
			Iterator it = vector.iterator();
			int i = 0;
			while (it.hasNext()) {

				Audit audit = (Audit) it.next();
				Users user = (Users) remoteReference.selectUser(audit
						.getUserid());
				model.insertRow(
						i,
						new Object[] { audit.getTradeid(),
								audit.getChangeDate(), audit.getFieldname(),
								audit.getType(), audit.getVersion(),
								audit.getTattribue(), user.getUser_name(),
								user.getUser_groups() });
				i++;
			}

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void processTableDataOpen(DefaultTableModel model, String name) {

		Vector vector;
		try {
			vector = (Vector) remoteTrade.selectforOpen(name.trim());
			Iterator it = vector.iterator();
			int i = 0;
			while (it.hasNext()) {
				Trade trade = (Trade) it.next();

				model.insertRow(i,
						new Object[] { trade.getId(), trade.getTradedesc() });
				i++;
			}

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

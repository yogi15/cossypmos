package apps.window.tradewindow;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.Hashtable;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import dsManager.TaskManager;
import dsServices.ServerConnectionUtil;
import beans.Users;
import apps.window.adminmonitor.JFrameAdminMonitorWindow;
import apps.window.limitdashboardpanel.JFrameLimitDashBoard;
import apps.window.operationwindow.JFrameJobApplication;
import apps.window.operationwindow.JFrameTradeBrowser;
import apps.window.operationwindow.SearchTrade;
import apps.window.operationwindow.jobpanl.JFrameNewJobPanel;
import apps.window.positionwindow.JFrameManulPositionLiquidation;
import apps.window.positionwindow.JFramePosition;
import apps.window.referencewindow.FutureProductWindow;
import apps.window.referencewindow.JFrameAccountingWindow;
import apps.window.referencewindow.JFrameReferenceWindow;
import apps.window.reportwindow.JFrameNewReport;
import apps.window.reportwindow.JFrameReportsApplication;
import apps.window.staticwindow.FavoritiesWindow;
//import apps.window.reportwindow.NewJFrame2;
import apps.window.staticwindow.JFrameStaticWindow;
import apps.window.utilwindow.Login;
import util.ClassInstantiateUtil;
import util.commonUTIL;

public class JFrameMainApplication extends JFrame {

	private static JMenu[] menu = { new JMenu("Static Configuration"),new JMenu("Reference"), new JMenu("Trade"), new JMenu("Report"),	new JMenu("Jobs"), new JMenu("Help") };
	private static JMenuItem[] menuitemTrade = { new JMenu("FixedIncome"),new JMenuItem("MoneyMarket"), new JMenuItem("FX") ,new JMenuItem("Future")};
	private static JMenuItem[] menuReferencedata = { new JMenuItem("CounterParty"), new JMenuItem("Product"),	new JMenuItem("Book"), new JMenuItem("User"),
			new JMenu("Accounting"), new JMenuItem("LiquidationConfig"),
			new JMenuItem("LimitConfiguration"), new JMenu("Settlements"),
			new JMenuItem("MarketQuote"), new JMenu("FXAutoConfig"),new JMenuItem("DateRule"),new JMenu("Listed Derivatives") };
	private static JMenuItem[] menuStaticdata = { new JMenuItem("StartDataUP"),
			new JMenuItem("WorkFlowSetup"), new JMenuItem("CurrencyDefault"),
			new JMenuItem("Holiday"), new JMenuItem("Folder"),
			new JMenu("Favorities"), new JMenuItem("MessageConfig") };
	private static JMenuItem[] reports = { new JMenuItem("TradeReport"),
			new JMenuItem("TransferReport"), new JMenuItem("PostingReport"),new JMenuItem("MessageReport"),new JMenuItem("CashLedgerPositionReport"),new JMenuItem("CashPositionReport"),new JMenuItem("ForwardLadderReport"),
			new JMenuItem("PNLReport"), new JMenu("Other") };
	private static JMenuItem[] favItems = { new JMenuItem("CounterParty"),
			new JMenuItem("Trader"), new JMenuItem("Tenor"),
			new JMenuItem("Book"), new JMenuItem("CurrencyPair") };
	private static JMenuItem[] accountItems = { new JMenuItem("Account"),
			new JMenuItem("AccountFlowConfig"), new JMenuItem("AccEventConfig") };
	private static JMenuItem[] derivativeItems = { new JMenuItem("FutureProduct"),
		new JMenuItem("FutureOptionContract") };
	
	private static JMenuItem[] fxConfigItems = {
			new JMenuItem("CurrencySplit"), new JMenuItem("B2BConfig"),
			new JMenuItem("SpotRollOver") };
	private static JMenuItem[] settlements = { new JMenuItem("SDI"),
			new JMenuItem("NettingConfiguration") };
	private static JMenuItem[] jobs = { new JMenuItem("Jobs"),
			new JMenuItem("Deal Viewer"), new JMenuItem("Quick Look Up"),
			new JMenu("PositionManagement"), new JMenuItem("LimitDashBoard") };
	private static JMenuItem[] fixedIncome = { new JMenuItem("Bond"),
			new JMenuItem("Repo") };
	private static JMenuItem[] RBIReport = { new JMenuItem("FTDReport"),new JMenuItem("VARReport"), new JMenuItem("OtherReport"), 
		 };
	private static JMenuItem[] positionmenu = {
			new JMenuItem("PositionManager"),
			new JMenuItem("ManualLiquidation") };
	private static JMenuItem[] adminUtilmenu = {
		new JMenuItem("AdminMonitorUtil")
		 };
	
	Hashtable<String, JMenuItem[]> subMenu = new Hashtable<String, JMenuItem[]>();
	URL imgURL = null;
	Users name = null;
	JFrameMainApplication j = null;
	public static ServerConnectionUtil de = null;
	Hashtable<String, JFrame> singleInstance = new Hashtable<String, JFrame>();
	private static final String PREFERRED_LOOK_AND_FEEL = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";

	public JFrameMainApplication(Users user) {
		installLnF();
		de = ServerConnectionUtil.connect("localhost", 1099,
				commonUTIL.getServerIP());
		subMenu.put("FixedIncome", fixedIncome);
		subMenu.put("PositionManagement", positionmenu);
		subMenu.put("Favorities", favItems);
		subMenu.put("Accounting", accountItems);
		subMenu.put("Settlements", settlements);
		subMenu.put("FXAutoConfig", fxConfigItems);
		subMenu.put("Listed Derivatives", derivativeItems);
		subMenu.put("RBI Report", RBIReport);
		

		setTitle(" Main Apps " + user.getUser_name());
		this.name = user;
		j = this;
		imgURL = this.getClass().getResource("/resources/icon/sql.jpg");

		setIconImage(Toolkit.getDefaultToolkit().getImage(imgURL));
		setSize(1000, 100);
		JMenuBar menubar = new JMenuBar();

		for (int i = 0; i < menuitemTrade.length; i++) {
			// getMenu("Trade").add(menuitemTrade[i]);
			
			JMenuItem submenu1[] = getSubMenuItems(menuitemTrade[i].getText());
			if (submenu1 == null) {
				menu[2].add(menuitemTrade[i]);
				menu[2].setFont(Font.decode("SansSerif-12"));
				commonUTIL.setBackGroundColor(menu[2]);
				menuitemTrade[i].setFont(Font.decode("SansSerif-12"));
				commonUTIL.setBackGroundColor(menuitemTrade[i]);
				menuitemTrade[i].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						String productName = arg0.getActionCommand().toString();
						if (productName.equalsIgnoreCase("MoneyMarket"))
							productName = "MM";
						TradeApplication tradeWindow = new TradeApplication(
								productName, name);
						// JFrameTradeWindowApplication tradeWindow = new
						// JFrameTradeWindowApplication(productName,name);
						tradeWindow.setUserName(name);
						// tradeWindow.setResizable(false);
						tradeWindow.setTitle(arg0.getActionCommand()
								+ " Trade Window " + name.getUser_name() + " :"
								+ name.getId());
						tradeWindow.setIconImage(Toolkit.getDefaultToolkit()
								.getImage(imgURL));
						tradeWindow.setVisible(true);
						if (productName.toString().equalsIgnoreCase("FX")) {
							tradeWindow.setTitle("CosMos FX Trade Window  "
									+ name.getUser_name() + " :" + name.getId());
							tradeWindow.setSize(1340, 740);
							tradeWindow.setLocation(5, 5);
							// tradeWindow.setResizable(false);
						} else {
							// tradeWindow.setSize(1020,600);
							tradeWindow.setLocation(5, 0);
						}

					}
				});
			} else {
				for (int s = 0; s < submenu1.length; s++) {
					menuitemTrade[i].add(submenu1[s]);
					commonUTIL.setBackGroundColor(menuitemTrade[i]);
				}
				menu[2].add(menuitemTrade[i]);

				menu[2].setFont(Font.decode("SansSerif-12"));
				menuitemTrade[i].setFont(Font.decode("SansSerif-12"));
				commonUTIL.setBackGroundColor(menuitemTrade[i]);
				commonUTIL.setBackGroundColor(menu[2]);
			}

		}

		for (int i = 0; i < jobs.length; i++) {
			// getMenu("Trade").add(menuitemTrade[i]);
			JMenuItem submenu1[] = getSubMenuItems(jobs[i].getText());
			if (submenu1 == null) {
			menu[4].add(jobs[i]);
			commonUTIL.setBackGroundColor(jobs[i]);
			commonUTIL.setBackGroundColor(menu[4]);
			menu[4].setFont(Font.decode("SansSerif-12"));
			jobs[i].setFont(Font.decode("SansSerif-12"));
			jobs[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if (arg0.getActionCommand().equalsIgnoreCase("Jobs")) {
						JFrameNewJobPanel jobs = (JFrameNewJobPanel) singleInstance
								.get("Jobs");

						if (jobs == null) {
							jobs = new JFrameNewJobPanel("Jobs", name);

							jobs.setIconImage(Toolkit.getDefaultToolkit()
									.getImage(imgURL));
							jobs.setSize(900, 750);
							jobs.setVisible(true);
							jobs.setIconImage(Toolkit.getDefaultToolkit()
									.getImage(imgURL));
							jobs.setTitle("Jobs Station " + name.getUser_name());
							jobs.setUser(name);
							jobs.addWindowListener(new WindowAdapter() {
								public void WindowClosed(final ActionEvent act) {
									singleInstance.remove("Jobs");

								}
							});
							singleInstance.put("Jobs", jobs);
						} else {
							jobs.setIconImage(Toolkit.getDefaultToolkit()
									.getImage(imgURL));
							jobs.setSize(900, 750);
							jobs.setVisible(true);
							jobs.setIconImage(Toolkit.getDefaultToolkit()
									.getImage(imgURL));
							jobs.setTitle("Jobs Station " + name.getUser_name());
							jobs.setUser(name);
							// jobs.setAlwaysOnTop(true);
							// jobs.setFocusCycleRoot(true);
							jobs.setFocusableWindowState(true);

						}

					} else if (arg0.getActionCommand().equalsIgnoreCase(
							"Quick Look Up")) {

						SearchTrade searchTrade = new SearchTrade();
						searchTrade.setIconImage(Toolkit.getDefaultToolkit()
								.getImage(imgURL));
						searchTrade.setVisible(true);
						searchTrade.setUser(name);
						searchTrade.setSize(260, 140);
						searchTrade.setTitle("TradeSearch");
						searchTrade.setResizable(false);
						// login.setFont(new java.awt.Font("Verdana", 0, 11));
						searchTrade.setLocationRelativeTo(new SearchTrade());
					} else if (arg0.getActionCommand().equalsIgnoreCase(
							"Deal Viewer")) {

						JFrameTradeBrowser dealViewer = new JFrameTradeBrowser();
						dealViewer.setVisible(true);
						dealViewer.setIconImage(Toolkit.getDefaultToolkit()
								.getImage(imgURL));
						// dealViewer.setUser(name);
						dealViewer.setSize(1250, 750);
						dealViewer.setTitle("Deal Viewer : "
								+ name.getUser_name());
						dealViewer.setResizable(false);

					}/* else if (arg0.getActionCommand().equalsIgnoreCase(
							"PositionManager")) {
						JFramePosition pm = new JFramePosition("Position", name);
						pm.setSize(900, 750);
						pm.setVisible(true);
						pm.setIconImage(Toolkit.getDefaultToolkit().getImage(
								imgURL));
						pm.setTitle("Position" + name.getUser_name());
						// pm.setUser(name);
						// pm.setAlwaysOnTop(true);
						// jobs.setFocusCycleRoot(true);
						pm.setFocusableWindowState(true);
						// Thread startPm = new Thread(pm);
						// startPm.start();
					}*/ else if (arg0.getActionCommand().equalsIgnoreCase(
							"LimitDashBoard")) {
						JFrameLimitDashBoard limitDB = new JFrameLimitDashBoard(
								"LimitDashBoard", name);
						limitDB.setSize(900, 750);
						limitDB.setVisible(true);
						limitDB.setIconImage(Toolkit.getDefaultToolkit()
								.getImage(imgURL));
						limitDB.setTitle("LimitDashBoard" + name.getUser_name());
						// pm.setUser(name);
						// pm.setAlwaysOnTop(true);
						// jobs.setFocusCycleRoot(true);
						limitDB.setFocusableWindowState(true);
						// Thread startPm = new Thread(pm);
						// startPm.start();
					}
				}

			});
			} else {
				for (int s = 0; s < submenu1.length; s++) {
					jobs[i].add(submenu1[s]);
					commonUTIL.setBackGroundColor(jobs[i]);
				}
				menu[4].add(jobs[i]);

				menu[4].setFont(Font.decode("SansSerif-12"));
				jobs[i].setFont(Font.decode("SansSerif-12"));
				commonUTIL.setBackGroundColor(menu[4]);
				commonUTIL.setBackGroundColor(menu[4]);
			}

		}

		for (int i = 0; i < reports.length; i++) {
			JMenuItem submenu1[] = getSubMenuItems(reports[i].getText());
			if (submenu1 == null) {
			// getMenu("Trade").add(menuitemTrade[i]);
			menu[3].add(reports[i]);
			menu[3].setFont(Font.decode("SansSerif-12"));
			reports[i].setFont(Font.decode("SansSerif-12"));
			commonUTIL.setBackGroundColor(reports[i]);
			commonUTIL.setBackGroundColor(menu[3]);
			reports[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					// NewJFrame2 reports = new NewJFrame2();
					// JFrameReportsApplication reports = new
					// JFrameReportsApplication("Report",name);
					JFrameNewReport reports = new JFrameNewReport(arg0
							.getActionCommand(), name);
					reports.setIconImage(Toolkit.getDefaultToolkit().getImage(
							imgURL));
					reports.setSize(1250, 750);
					reports.setVisible(true);
					reports.setTitle(arg0.getActionCommand() + " : "
							+ name.getUser_name());
					// reports.setVisible(true);
					// reports.setUser(name);

				}

			});
		} else {
			for (int s = 0; s < submenu1.length; s++) {
				reports[i].add(submenu1[s]);
				commonUTIL.setBackGroundColor(reports[i]);
			}
			menu[3].add(reports[i]);

			menu[3].setFont(Font.decode("SansSerif-12"));
			reports[i].setFont(Font.decode("SansSerif-12"));
			commonUTIL.setBackGroundColor(menu[3]);
			commonUTIL.setBackGroundColor(menu[3]);
		}


		}
		for (int i = 0; i < favItems.length; i++) {
			final Users us1 = user;
			favItems[i].setFont(Font.decode("SansSerif-12"));
			// commonUTIL.setBackGroundColor( reports[i]);
			commonUTIL.setBackGroundColor(favItems[i]);
			favItems[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {

					FavoritiesWindow favoritiesWindow = new FavoritiesWindow(
							arg0.getActionCommand(), us1);
					favoritiesWindow.setTitle(arg0.getActionCommand()
							+ "  FavoritiesWindow " + us1.getUser_name());
					favoritiesWindow.setIconImage(Toolkit.getDefaultToolkit()
							.getImage(imgURL));
					// favoritiesWindow.setSize(1200,650);
					favoritiesWindow.setVisible(true);
					favoritiesWindow.setLocation(200, 150);

				}

			});

		}
		for (int i = 0; i < adminUtilmenu.length; i++) {
			final Users us1 = user;
			menu[5].add(adminUtilmenu[i]);
			adminUtilmenu[i].setFont(Font.decode("SansSerif-12"));
			adminUtilmenu[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					JFrameAdminMonitorWindow pm = new JFrameAdminMonitorWindow("AdminMonitor", name);
					pm.setSize(900, 750);
					pm.setVisible(true);
					pm.setIconImage(Toolkit.getDefaultToolkit().getImage(
							imgURL));
					pm.setTitle("Admin Monitor " + name.getUser_name());
				}

			});
		}
		for (int i = 0; i < positionmenu.length; i++) {
			final Users us1 = user;
			positionmenu[i].setFont(Font.decode("SansSerif-12"));
			// commonUTIL.setBackGroundColor( reports[i]);
			
			positionmenu[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {

					 if (arg0.getActionCommand().equalsIgnoreCase(
								"PositionManager")) {
							JFramePosition pm = new JFramePosition("Position", name);
							pm.setSize(900, 750);
							pm.setVisible(true);
							pm.setIconImage(Toolkit.getDefaultToolkit().getImage(
									imgURL));
							pm.setTitle("Position" + name.getUser_name());
							// pm.setUser(name);
							// pm.setAlwaysOnTop(true);
							// jobs.setFocusCycleRoot(true);
							pm.setFocusableWindowState(true);
							// Thread startPm = new Thread(pm);
							// startPm.start();
						} else {
							JFrameManulPositionLiquidation manualPostion = new JFrameManulPositionLiquidation("ManualLiquidation", name);
							manualPostion.setSize(900, 750);
							manualPostion.setVisible(true);
							manualPostion.setIconImage(Toolkit.getDefaultToolkit().getImage(
									imgURL));
							manualPostion.setTitle("ManualLiquidationWindow" + name.getUser_name());
							// pm.setUser(name);
							// pm.setAlwaysOnTop(true);
							// jobs.setFocusCycleRoot(true);
							manualPostion.setFocusableWindowState(true);
						}
					 

				}

			});

		}
		for (int i = 0; i < accountItems.length; i++) {
			final Users us1 = user;
			accountItems[i].setFont(Font.decode("SansSerif-12"));
			commonUTIL.setBackGroundColor(accountItems[i]);
			commonUTIL.setBackGroundColor(favItems[i]);
			accountItems[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {

					JFrameAccountingWindow accountingWindow = new JFrameAccountingWindow(
							arg0.getActionCommand(), us1);
					accountingWindow.setTitle(arg0.getActionCommand()
							+ "  AccountingWindow " + us1.getUser_name());
					accountingWindow.setIconImage(Toolkit.getDefaultToolkit()
							.getImage(imgURL));
					if (arg0.getActionCommand().equalsIgnoreCase(
							"AccEventConfig"))
						accountingWindow.setSize(750, 470);
					else
						accountingWindow.setSize(750, 600);
					accountingWindow.setVisible(true);
					accountingWindow.setLocation(200, 150);
					accountingWindow.setResizable(false);
				}
			});
		}
		for (int s = 0; s < settlements.length; s++) {
			settlements[s].setFont(Font.decode("SansSerif-12"));
			commonUTIL.setBackGroundColor(settlements[s]);
			settlements[s].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					JFrameReferenceWindow refWindow = new JFrameReferenceWindow(
							arg0.getActionCommand());
					refWindow.setIconImage(Toolkit.getDefaultToolkit()
							.getImage(imgURL));
					if (arg0.getActionCommand().toString()
							.equalsIgnoreCase("SDI")) {
					//	refWindow.setSize(1050, 540);
					}
					if (arg0.getActionCommand().toString()
							.equalsIgnoreCase("NettingConfiguration")) {
						refWindow.setSize(1050, 540);
					}
					refWindow.setLocationRelativeTo(j);
					refWindow.setVisible(true);
					refWindow.setLocation(150, 150);

				}
			});
		}
		for (int s = 0; s < fxConfigItems.length; s++) {
			fxConfigItems[s].setFont(Font.decode("SansSerif-12"));
			commonUTIL.setBackGroundColor(fxConfigItems[s]);
			fxConfigItems[s].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					JFrameReferenceWindow refWindow = new JFrameReferenceWindow(
							arg0.getActionCommand());
					refWindow.setIconImage(Toolkit.getDefaultToolkit()
							.getImage(imgURL));

					refWindow.setSize(970, 450);

					refWindow.setLocationRelativeTo(j);
					refWindow.setVisible(true);
					refWindow.setLocation(150, 150);

				}
			});
		}
		for (int s = 0; s < derivativeItems.length; s++) {
			//derivativeItems[s].setFont(Font.decode("SansSerif-12"));
			commonUTIL.setBackGroundColor(derivativeItems[s]);
			derivativeItems[s].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					FutureProductWindow refWindow = new FutureProductWindow();
					refWindow.setIconImage(Toolkit.getDefaultToolkit()
							.getImage(imgURL));

					refWindow.setSize(1270, 580);

					refWindow.setLocationRelativeTo(j);
					refWindow.setVisible(true);
					refWindow.setLocation(100, 100);

				}
			});
		}
		for (int r = 0; r < RBIReport.length; r++) {
			//derivativeItems[s].setFont(Font.decode("SansSerif-12"));
			commonUTIL.setBackGroundColor(RBIReport[r]);
			RBIReport[r].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					JFrameNewReport reports = new JFrameNewReport(arg0
							.getActionCommand(), name);
					reports.setIconImage(Toolkit.getDefaultToolkit().getImage(
							imgURL));
					reports.setSize(1250, 750);
					reports.setVisible(true);
					reports.setTitle(arg0.getActionCommand() + " : "
							+ name.getUser_name());

				}
			});
		}
		for (int i = 0; i < fixedIncome.length; i++) {
			final Users us1 = user;
			fixedIncome[i].setFont(Font.decode("SansSerif-12"));
			commonUTIL.setBackGroundColor(fixedIncome[i]);
			fixedIncome[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					String productName = arg0.getActionCommand().toString();
					TradeApplication tradeWindow = new TradeApplication(
							productName, name);
					// JFrameTradeWindowApplication tradeWindow = new
					// JFrameTradeWindowApplication(productName,name);
					tradeWindow.setUserName(name);
					// tradeWindow.setResizable(false);
					tradeWindow.setTitle(arg0.getActionCommand()
							+ " Trade Window " + name.getUser_name() + " :"
							+ name.getId());
					tradeWindow.setIconImage(Toolkit.getDefaultToolkit()
							.getImage(imgURL));
					// tradeWindow.setBackground(getColors());
					tradeWindow.setVisible(true);
					tradeWindow.setLocation(5, 0);

				}

			});

		}

		for (int i = 0; i < menuStaticdata.length; i++) {
			// getMenu("Trade").add(menuitemTrade[i]);
			final String addfav = "false";
			commonUTIL.setBackGroundColor(menuStaticdata[i]);
			JMenuItem submenu1[] = getSubMenuItems(menuStaticdata[i].getText());
			if (submenu1 == null) {
				menu[0].add(menuStaticdata[i]);
				menu[0].setFont(Font.decode("SansSerif-12"));
				menuStaticdata[i].setFont(Font.decode("SansSerif-12"));
				menuStaticdata[i].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						JFrameStaticWindow staticwindow = new JFrameStaticWindow(
								arg0.getActionCommand());
						staticwindow.setIconImage(Toolkit.getDefaultToolkit()
								.getImage(imgURL));
						if (arg0.getActionCommand().toString()
								.equalsIgnoreCase("Folder")) {
							staticwindow.setSize(505, 550);
							staticwindow.setLocation(200, 150);
						}
						if (arg0.getActionCommand().toString()
								.equalsIgnoreCase("StartDataUP")) {
							staticwindow.setLocation(100, 100);
							staticwindow.setSize(875, 575);
						}
						if (arg0.getActionCommand().toString()
								.equalsIgnoreCase("WorkFlowSetup")) {
							staticwindow.setLocation(50, 100);
							staticwindow.setSize(1150, 575);
						}
						if (arg0.getActionCommand().toString()
								.equalsIgnoreCase("CurrencyDefault")) {
							staticwindow.setLocation(50, 100);
							staticwindow.setSize(765, 585);
						}
						if (arg0.getActionCommand().toString()
								.equalsIgnoreCase("Holiday")) {
							staticwindow.setLocation(150, 100);
							staticwindow.setSize(565, 450);
						} else if (arg0.getActionCommand().toString()
								.equalsIgnoreCase("MessageConfig")) {
							staticwindow.setLocation(100, 100);
							staticwindow.setSize(1350, 675);

						}
						staticwindow.setResizable(false);
						staticwindow.setVisible(true);

					}

				});

			} else {
				for (int s = 0; s < submenu1.length; s++) {
					menuStaticdata[i].add(submenu1[s]);

				}
				menu[0].add(menuStaticdata[i]);

				menu[0].setFont(Font.decode("SansSerif-12"));
				menuStaticdata[i].setFont(Font.decode("SansSerif-12"));

			}

		}

		/*
		 * try {
		 * UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		 * } catch(Exception e) {
		 * System.out.println("Error setting native LAF: " + e); }
		 */

		for (int i = 0; i < menuReferencedata.length; i++) {
			// System.out.println(menuReferencedata[i].getLabel());
			commonUTIL.setBackGroundColor(menuReferencedata[i]);
			JMenuItem submenu1[] = getSubMenuItems(menuReferencedata[i]
					.getText());
			if (submenu1 != null)
				for (int s = 0; s < submenu1.length; s++) {
					menuReferencedata[i].add(submenu1[s]);
					commonUTIL.setBackGroundColor(menuReferencedata[i]);
				}
			menu[1].add(menuReferencedata[i]);
			commonUTIL.setBackGroundColor(menu[1]);
			menuReferencedata[i].setFont(Font.decode("SansSerif-12"));
			menu[1].setFont(Font.decode("SansSerif-12"));
			menuReferencedata[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					JFrameReferenceWindow refWindow = new JFrameReferenceWindow(
							arg0.getActionCommand());
					refWindow.setIconImage(Toolkit.getDefaultToolkit()
							.getImage(imgURL));

					if (arg0.getActionCommand().toString()
							.equalsIgnoreCase("MarketQuote")) {
						refWindow.setSize(1150, 600);

					} else if (arg0.getActionCommand().toString()
							.equalsIgnoreCase("SDI1")) {
						refWindow.setSize(900, 500);
					} else if (arg0.getActionCommand().toString()
							.equalsIgnoreCase("Book")) {
						refWindow.setSize(750, 560);
					} else if (arg0.getActionCommand().toString()
							.equalsIgnoreCase("LimitConfiguration")) {
						refWindow.setSize(1250, 580);
					} else {
						refWindow.setSize(1050, 500);
					}
					refWindow.setLocationRelativeTo(j);
					refWindow.setVisible(true);
					refWindow.setLocation(200, 150);
				}

			});

		}

		for (JMenu menus : menu) {

			menubar.add(menus);
			commonUTIL.setBackGroundColor(menubar);
			setJMenuBar(menubar);
		}
		// setLayout(new FlowLayout());
		// getContentPane().setBackground(getColors());
		// menubar.setBackground(getColors());
	}

	private Color getColors() {
		// TODO Auto-generated method stub
		return new Color(232, 230, 215);
	}

	public static void setFrame(final JFrame frame, final int width,
			final int height) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				frame.setTitle(frame.getClass().getSimpleName());
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setSize(width, height);
				frame.setVisible(true);
			}
		});
	}

	public JMenuItem[] getSubMenuItems(String itemname) {

		JMenuItem items[] = null;
		if (subMenu.containsKey(itemname)) {
		
			items = subMenu.get(itemname);

		}
		return items;

	}

	public static void installLnF() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {

				try {
					String lnfClassname = PREFERRED_LOOK_AND_FEEL;

					UIManager.setLookAndFeel(UIManager
							.getSystemLookAndFeelClassName());

					UIManager.put("MenuBar.background", commonUTIL.getColors());
					UIManager.put("Menu.background", commonUTIL.getColors());
					UIManager.put("MenuItem.background", commonUTIL.getColors());
					UIManager.put("Panel.background", commonUTIL.getColors());
					UIManager.put("ComboBox.background", commonUTIL.getColors());
					UIManager.put("Button.background", commonUTIL.getColors());
					UIManager.put("TabbedPane.background",
							commonUTIL.getColors());
					UIManager.put("ScrollPane.background",
							commonUTIL.getColors());
				//	UIManager.put("Table.background", commonUTIL.getColors());
					UIManager.put("ToolBar.background", Color.white);
					UIManager.put("RadioButton.background",
							commonUTIL.getColors());
					UIManager.put("CheckBox.background", commonUTIL.getColors());
					UIManager.put("ToolBar.background", commonUTIL.getColors());

				} catch (Exception e) {
					System.err.println("Cannot install "
							+ PREFERRED_LOOK_AND_FEEL + " on this platform:"
							+ e.getMessage());
				}
			}
		});

	}

	public static void main(String[] args) {
		// installLnF();
		java.awt.EventQueue.invokeLater(new Runnable() {

			public void run() {
				Login login = new Login();
				login.setVisible(true);
				String icon = "E:\\eclipse\\workspace\\sbiCCIL\\resources\\icon\\sql.jpg";
				login.setIconImage(Toolkit.getDefaultToolkit().getImage(icon));
				ImageIcon img = new ImageIcon(this.getClass().getResource(
						"/resources/icon/sql.jpg"));
				login.setIconImage(img.getImage());

				login.setSize(220, 200);
				login.setTitle("Login");
				login.setResizable(false);
				// login.setFont(new java.awt.Font("Verdana", 0, 11));
				login.setLocationRelativeTo(new Login());
			}
		});

		// login.setFocusable(true)
		// setFrame(new JFrameMainApplication(), 500, 100); }
	}

}

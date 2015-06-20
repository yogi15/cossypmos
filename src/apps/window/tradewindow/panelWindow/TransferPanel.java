package apps.window.tradewindow.panelWindow;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

import org.dyno.visual.swing.layouts.Bilateral;
import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;
import org.dyno.visual.swing.layouts.Trailing;


import util.commonUTIL;
import apps.window.tradewindow.BackOfficePanel;
import apps.window.tradewindow.panelWindow.FeesPanel.TableModelUtil;
import beans.NettingConfig;
import beans.Product;
import beans.Trade;
import beans.Transfer;
import beans.Users;
import beans.WFConfig;
import dsEventListener.TransferEvtListener;
import dsEventProcessor.TaskEventProcessor;
import dsServices.RemoteBOProcess;
import dsServices.RemoteProduct;
import dsServices.RemoteReferenceData;
import dsServices.RemoteTrade;

//VS4E -- DO NOT REMOVE THIS LINE!
public class TransferPanel extends BackOfficePanel {

	private static final long serialVersionUID = 1L;
	JPopupMenu popupMenu = new JPopupMenu();
	public JPopupMenu getPopupMenu() {
		return popupMenu;
	}
	   Hashtable<String,JTable> dynTables = new  Hashtable<String,JTable>();
	//   java.awt.Font f = new  java.awt.Font("ARIAL",Font.BOLD,11);
	private JTable jTable0;
	private JScrollPane jScrollPane0;
	private JLabel jLabel0;
	private JTextField amountTextField;
	private JTextField jTextField1;
	private JTextField jTextField2;
	private JLabel jLabel3;
	private JTextField jTextField3;
	private JLabel amountLabel;
	private JLabel jLabel2;
	private JPanel jPanel0;
	private JLabel jLabel4;
	private JLabel jLabel5;
	private JLabel jLabel6;
	private JLabel jLabel7;
	private JTextField jTextField4;
	private JTextField jTextField7;
	private JTextField jTextField6;
	private JTextField jTextField5;
	private JLabel jLabel8;
	private JLabel jLabel9;
	private JLabel jLabel10;
	JLabel settlementAmountLabel;
	JButton RefreshButton;
	JTextField settlementAmountTextField;
	private JTextField jTextField8;
	private JTextField jTextField9;
	private JTextField jTextField10;
	private JLabel jLabel18;
	private JTable jTable1;
	private JScrollPane jScrollPane1;
	private JButton jButton0;
	private Vector data = new Vector();
	private Vector settlementdata = new Vector();
	TableModelUtil model = null;
	TableModelUtil settlementmodel = null;
	Users users = null;
	JMenu actions = new JMenu("Action");

	public JMenu getActions() {
		return actions;
	}

	RemoteProduct remoteProduct;
	String col[] = { "Transfer ID", "Trade id", "EventType", "TranferType",
			"AMOUNT", "Settlement", "Quantity", "Product", "Status",
			"SettlementDate", "Currency", "ValueDate", "Payer", "Receiver","Method","PayerInst","RecInst" };

	TransferEvtListener transferEvts = null;
	public TransferPanel() {
		initComponents();
		transferEvts = new TransferEvtListener("localhost",commonUTIL.getLocalHostName(),"");
		transferEvts.start(transferEvts);
		transferEvts.setTransferPanelEvtListener(this);
		// popupMenu.add(actions);
	}

	private void initComponents() {
		setFocusable(true);
		setEnabled(true);
		setVisible(true);
		setDoubleBuffered(true);
		setVerifyInputWhenFocusTarget(true);
		setRequestFocusEnabled(true);
		setOpaque(true);
		setLayout(new GroupLayout());
		add(getJPanel4(), new Constraints(new Bilateral(8, 8, 1129),
				new Bilateral(7, 323, 10, 282)));
		add(getJTabbedPane0(), new Constraints(new Bilateral(8, 9, 0),
				new Trailing(8, 309, 329, 329)));
		setSize(1062, 462);
		popupMenu.add(actions);
	}

	private JPanel getJPanel4() {
		if (jPanel4 == null) {
			jPanel4 = new JPanel();
			jPanel4.setLayout(new GroupLayout());
			jPanel4.add(getJScrollPane0(), new Constraints(new Bilateral(7, 10,
					1089), new Bilateral(7, 7, 10, 254)));
		}
		return jPanel4;
	}

	private JScrollPane getJScrollPane3() {
		if (jScrollPane3 == null) {
			jScrollPane3 = new JScrollPane();
			jScrollPane3.setViewportView(getJTable3());
		}
		return jScrollPane3;
	}

	private JTable getJTable3() {
		if (jTable3 == null) {
			jTable3 = new JTable();
			jTable3.setModel(new DefaultTableModel(new Object[][] {
					{ "0x0", "0x1", }, { "1x0", "1x1", }, }, new String[] {
					"Title 0", "Title 1", }) {
				private static final long serialVersionUID = 1L;
				Class<?>[] types = new Class<?>[] { Object.class, Object.class, };

				public Class<?> getColumnClass(int columnIndex) {
					return types[columnIndex];
				}
			});
		}
		return jTable3;
	}

	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jPanel3 = new JPanel();
			jPanel3.setVisible(false);
			jPanel3.setLayout(new GroupLayout());
		}
		return jPanel3;
	}

	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setLayout(new GroupLayout());
		}
		return jPanel2;
	}

	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(new GroupLayout());
			jPanel1.add(getJScrollPane2(), new Constraints(new Leading(9, 1010,
					10, 10), new Leading(6, 187, 10, 10)));
		}
		return jPanel1;
	}

	private JScrollPane getJScrollPane2() {
		if (jScrollPane2 == null) {
			jScrollPane2 = new JScrollPane();
			jScrollPane2.setViewportView(getJTable2());
		}
		return jScrollPane2;
	}

	private JTable getJTable2() {
		if (jTable2 == null) {
			jTable2 = new JTable();
			Vector nettedTransfers = new Vector();
			TableModelUtil mutil = new TableModelUtil(nettedTransfers, col,
					remoteBO, remoteProduct, false);
			jTable2.setModel(mutil);
		}
		jTable2.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				int selectRow = jTable2.getSelectedRow();
				if (selectRow >= 0) {
					Transfer transfer = (Transfer) settlementdata
							.get(selectRow);
					if(transfer == null)
						return;
					Vector nettedTransfers = null;
					try {
						nettedTransfers = (Vector) remoteBO
								.getNettedTransfers(transfer.getId());
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					boolean flag = true;

					for (int i = 0; i < jTabbedPane0.getTabCount(); i++) {
						String name = jTabbedPane0.getTitleAt(i);
						if (name.equalsIgnoreCase((transfer.getDeliveryDate())))
							flag = false;
					}

					if (flag)
						jTabbedPane0.addTab(transfer.getDeliveryDate(),
								getDynJPanel(nettedTransfers, users.getId(),transfer.getDeliveryDate()));
				}

			}
		});

		return jTable2;
	}

	protected JPanel getDynJPanel(Vector nettedTransfers, int userID,String deliveryDate) {
		// TODO Auto-generated method stub
		JPanel panel = new JPanel();
		panel.setLayout(new GroupLayout());
		JScrollPane jScrollPane = new JScrollPane();
		if(nettedTransfers == null) {
			commonUTIL.showAlertMessage("NO netting Found");
			panel.add(jScrollPane, new Constraints(new Leading(9, 1240, 10, 10),
					new Leading(6, 187, 10, 10)));
			return panel;
		}
		jScrollPane.setViewportView(getTableDyn(nettedTransfers, userID,deliveryDate));
		panel.add(jScrollPane, new Constraints(new Leading(9, 1240, 10, 10),
				new Leading(6, 187, 10, 10)));

		return panel;
	}

	protected JTable getTableDyn(final Vector nettedTransfers, int userID,String deliveryDate) {
		
		TableModelUtil mutil = new TableModelUtil(nettedTransfers, col,
				remoteBO, remoteProduct, false);
		final JTable jTable = new JTable(mutil) {
			public Component prepareRenderer(TableCellRenderer renderer,
					int row, int column) {
				Component c = super.prepareRenderer(renderer, row, column);

				// Color row based on a cell value

				if (!isRowSelected(row)) {
					c.setBackground(getBackground());
					int modelRow = convertRowIndexToModel(row);
					String type = (String) getModel().getValueAt(modelRow,
							8);
					
					if (!commonUTIL.isEmpty(type) && "SETTLED".equals(type))
						c.setBackground(Color.pink);

				}

					return c;
				}
			};
		jTable.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (SwingUtilities.isRightMouseButton(e) == true) {
					int row = jTable.rowAtPoint(e.getPoint());
					int selectrow[] = jTable.getSelectedRows();
					Transfer transfer = (Transfer) nettedTransfers.get(row);
					try {
						fillActioninPopupMenu(
								(Vector) remoteBO.getOnlyAction(transfer),
								transfer, users.getId(), row);
						popupMenu.show(e.getComponent(), e.getX(), e.getY());
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}
			}
		});
		dynTables.put(deliveryDate,jTable);
		return jTable;
	}

	private JTabbedPane getJTabbedPane0() {
		if (jTabbedPane0 == null) {
			jTabbedPane0 = new JTabbedPane();
			jTabbedPane0.addTab("Transfer Details", getJPanel0());
			jTabbedPane0.addTab("SettlementPanel", getJPanel1());
			// jTabbedPane0.addTab("SettlementPanel", getJPanel4());
		}
		return jTabbedPane0;
	}

	private JTextField getJTextField11() {
		if (jTextField11 == null) {
			jTextField11 = new JTextField();
			jTextField11.setText("CANCELLED");
		}
		return jTextField11;
	}

	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("jButton1");
		}
		return jButton1;
	}

	private JTable getJTable0() {
		if (jTable0 == null) {
			jTable0 = new JTable();
			//jTable0.setAutoCreateRowSorter(true);
			jTable0.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			model = new TableModelUtil(data, col, remoteBO, remoteProduct,
					false);
			jTable0 = new JTable(model) {
				public Component prepareRenderer(TableCellRenderer renderer,
						int row, int column) {
					Component c = super.prepareRenderer(renderer, row, column);

					// Color row based on a cell value

					if (!isRowSelected(row)) {
						c.setBackground(getBackground());
						int modelRow = convertRowIndexToModel(row);
						String type = (String) getModel().getValueAt(modelRow,
								8);
						if (!commonUTIL.isEmpty(type) && "CANCELLED".equals(type))
							c.setBackground(Color.orange);
						if (!commonUTIL.isEmpty(type) && "SETTLED".equals(type))
							c.setBackground(Color.pink);

					}

					return c;
				}
			};
			jTable0.getColumnModel().getColumn(0).setPreferredWidth(80);
			jTable0.getColumnModel().getColumn(1).setPreferredWidth(70);
			jTable0.getColumnModel().getColumn(2).setPreferredWidth(100);
			jTable0.getColumnModel().getColumn(3).setPreferredWidth(80);
			jTable0.getColumnModel().getColumn(4).setPreferredWidth(100);
			jTable0.getColumnModel().getColumn(5).setPreferredWidth(80);
			jTable0.getColumnModel().getColumn(6).setPreferredWidth(80);
		//	jTable0.getTableHeader().setReorderingAllowed(false);
			//jTable0.getTableHeader().setResizingAllowed(true);

			jTable0.setModel(model);
		}
		jTable0.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				int selectRow = jTable0.getSelectedRow();
				Transfer transfer = (Transfer) data.get(selectRow);

				jTextField1.setText(((Integer) jTable0.getValueAt(selectRow, 1))
						.toString());
				jTextField4.setText((String) jTable0.getValueAt(selectRow, 2));
				jTextField8.setText((String) jTable0.getValueAt(selectRow, 3));
				amountTextField.setText((new Double((Double) jTable0.getValueAt(
						selectRow, 4))).toString());
				settlementAmountTextField.setText((new Double((Double) jTable0
						.getValueAt(selectRow, 5))).toString());
				jTextField5.setText((String) jTable0.getValueAt(selectRow, 7));
				jTextField9.setText((String) jTable0.getValueAt(selectRow, 8));
				jTextField2.setText((String) jTable0.getValueAt(selectRow, 9));
				jTextField7.setText((String) jTable0.getValueAt(selectRow, 10));
				jTextField10.setText((String) jTable0.getValueAt(selectRow, 11));
				jTextField3.setText((String) jTable0.getValueAt(selectRow, 12));
				jTextField6.setText((String) jTable0.getValueAt(selectRow, 13));

				jTextField1.setEditable(false);
				jTextField4.setEditable(false);
				jTextField8.setEditable(false);
				amountTextField.setEditable(false);
				jTextField5.setEditable(false);
				jTextField9.setEditable(false);

				jTextField2.setEditable(false);
				jTextField7.setEditable(false);
				jTextField10.setEditable(false);
				jTextField3.setEditable(false);
				jTextField6.setEditable(false);

				try {
					int rowindex = jTable0.getSelectedRow();
					TableModel model = jTable0.getModel();
					Integer ss = (Integer) model.getValueAt(rowindex, 0);
					if (SwingUtilities.isRightMouseButton(e) == true) {
						int row = jTable0.rowAtPoint(e.getPoint());
						transfer = (Transfer) data.get(selectRow);
						fillActioninPopupMenu(
								(Vector) remoteBO.getOnlyAction(transfer),
								transfer, users.getId(), rowindex);
						popupMenu.show(e.getComponent(), e.getX(), e.getY());
					}
				} catch (Exception e1) {
					commonUTIL.displayError("TransferPanel", " mouseClicked()",
							e1);
				}
			}
		});
		jTable0.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				// commonUTIL.showAlertMessage("keyCode " + e.getKeyCode());
				if (e.getKeyCode() == 40 || e.getKeyCode() == 38) {
					int selectRow = jTable0.getSelectedRow();
					Transfer transfer = (Transfer) data.get(selectRow);

					jTextField1.setText(((Integer) jTable0.getValueAt(
							selectRow, 1)).toString());
					jTextField4.setText((String) jTable0.getValueAt(selectRow,
							2));
					jTextField8.setText((String) jTable0.getValueAt(selectRow,
							3));
					amountTextField.setText((new Double((Double) jTable0
							.getValueAt(selectRow, 4))).toString());
					settlementAmountTextField.setText((new Double((Double) jTable0
							.getValueAt(selectRow, 5))).toString());
					jTextField5.setText((String) jTable0.getValueAt(selectRow,
							7));
					jTextField9.setText((String) jTable0.getValueAt(selectRow,
							8));
					jTextField2.setText((String) jTable0.getValueAt(selectRow,
							9));
					jTextField7.setText((String) jTable0.getValueAt(selectRow,
							10));
					jTextField10.setText((String) jTable0.getValueAt(selectRow,
							11));
					jTextField3.setText((String) jTable0.getValueAt(selectRow,
							12));
					jTextField6.setText((String) jTable0.getValueAt(selectRow,
							13));

					jTextField1.setEditable(false);
					jTextField4.setEditable(false);
					jTextField8.setEditable(false);
					amountTextField.setEditable(false);
					jTextField5.setEditable(false);
					jTextField9.setEditable(false);

					jTextField2.setEditable(false);
					jTextField7.setEditable(false);
					jTextField10.setEditable(false);
					jTextField3.setEditable(false);
					jTextField6.setEditable(false);
				}

			}
		});

		return jTable0;
	}

	protected void fillActioninPopupMenu(Vector onlyAction,
			final Transfer transfer, final int userID, final int row) {
		// TODO Auto-generated method stub

		actions.removeAll();
		if (onlyAction == null || onlyAction.isEmpty())
			return;
		JMenuItem actionItem[] = new JMenuItem[onlyAction.size()];
		for (int a = 0; a < onlyAction.size(); a++) {
			WFConfig wf = (WFConfig) onlyAction.get(a);
			actionItem[a] = new JMenuItem(wf.getAction());
			actions.add(actionItem[a]);
			actionItem[a].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					String action = arg0.getActionCommand().toString();
					transfer.setAction(action);
					updateTransferOnAction(transfer, userID, row);
				}
			}); // TODO Auto-generated method stub

		}

	}

	private JTextField getSettlementAmountTextField() {

		if (settlementAmountTextField == null) {
			settlementAmountTextField = new JTextField();
		}
		return settlementAmountTextField;
	}

	protected void updateTransferOnAction(Transfer transfer, int userID, int row) {
		// TODO Auto-generated method stub
		try {

			remoteBO.updateTransferAndPublish(transfer, userID);
			model.udpateValueAt(transfer, row, jTable0.getSelectedColumn());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private JButton getJButton0() {
		if (jButton0 == null) {
			jButton0 = new JButton();
			jButton0.setText("Settlement");
		}
		return jButton0;
	}

	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getJTable1());
		}
		return jScrollPane1;
	}

	private JTable getJTable1() {
		if (jTable1 == null) {
			jTable1 = new JTable();
			jTable1.setAutoCreateRowSorter(true);

		}
		return jTable1;
	}

	private JLabel getJLabel18() {
		if (jLabel18 == null) {
			jLabel18 = new JLabel();
			jLabel18.setText("Attributes");
		}
		return jLabel18;
	}

	private JTextField getJTextField10() {
		if (jTextField10 == null) {
			jTextField10 = new JTextField();
			// jTextField10.setText("jTextField10");
		}
		return jTextField10;
	}

	private JTextField getJTextField9() {
		if (jTextField9 == null) {
			jTextField9 = new JTextField();
			// jTextField9.setText("jTextField9");
		}
		return jTextField9;
	}

	private JTextField getJTextField8() {
		if (jTextField8 == null) {
			jTextField8 = new JTextField();
			// / jTextField8.setText("jTextField8");
		}
		return jTextField8;
	}

	private JLabel getJLabel10() {
		if (jLabel10 == null) {
			jLabel10 = new JLabel();
			jLabel10.setText("ValueDate");
		}
		return jLabel10;
	}

	private JLabel getJLabel9() {
		if (jLabel9 == null) {
			jLabel9 = new JLabel();
			jLabel9.setText("Status");
			//jLabel9.setFont(f);
		}
		return jLabel9;
	}

	private JLabel getJLabel8() {
		if (jLabel8 == null) {
			jLabel8 = new JLabel();
			jLabel8.setText("TransferType");
		}
		return jLabel8;
	}

	private JTextField getJTextField5() {
		if (jTextField5 == null) {
			jTextField5 = new JTextField();
			// jTextField5.setText("jTextField5");
		}
		return jTextField5;
	}

	private JTextField getJTextField6() {
		if (jTextField6 == null) {
			jTextField6 = new JTextField();
			// jTextField6.setText("jTextField6");
		}
		return jTextField6;
	}

	private JTextField getJTextField7() {
		if (jTextField7 == null) {
			jTextField7 = new JTextField();
			// jTextField7.setText("jTextField7");
		}
		return jTextField7;
	}

	private JTextField getJTextField4() {
		if (jTextField4 == null) {
			jTextField4 = new JTextField();
			// jTextField4.setText("jTextField4");
		}
		return jTextField4;
	}

	private JButton getRefreshButton() {

		if (RefreshButton == null) {
			RefreshButton = new JButton();
			RefreshButton.setText("Refresh");
		}
		RefreshButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				trade = getTrade();
				if (trade == null)
					return;
				try {
					data = (Vector) remoteBO.getTransferOnTrade(trade.getId());
					model = new TableModelUtil(data, col, remoteBO,
							remoteProduct, false);
					jTable0.setModel(model);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		return RefreshButton;
	}

	private JLabel getJLabel7() {
		if (jLabel7 == null) {
			jLabel7 = new JLabel("Receiver");
			
			//jLabel7.setf
		//	jLabel7.setText("Receiver");
		}
		return jLabel7;
	}

	private JLabel getJLabel6() {
		if (jLabel6 == null) {
			jLabel6 = new JLabel();
			jLabel6.setText("Currency");
		}
		return jLabel6;
	}

	private JLabel getJLabel5() {
		if (jLabel5 == null) {
			jLabel5 = new JLabel();
			jLabel5.setText("Product");
		}
		return jLabel5;
	}

	private JLabel getJLabel4() {
		if (jLabel4 == null) {
			jLabel4 = new JLabel();
			jLabel4.setText("EventType");
		}
		return jLabel4;
	}

	private JLabel getSettlementAmountLabel() {

		if (settlementAmountLabel == null) {
			settlementAmountLabel = new JLabel();
			settlementAmountLabel.setText("Settlement Amt");
		}
		return settlementAmountLabel;
	}

	private JPanel getJPanel0() {
		if (jPanel0 == null) {
			jPanel0 = new JPanel();
			jPanel0.setVisible(false);
			jPanel0.setLayout(new GroupLayout());
			jPanel0.add(getJTextField1(), new Constraints(new Leading(95, 71,
					12, 12), new Leading(14, 12, 12)));
			jPanel0.add(getAmountLabel(), new Constraints(
					new Leading(9, 49, 12, 12), new Leading(42, 12, 12)));
			jPanel0.add(getJTextField4(), new Constraints(new Leading(276, 137,
					10, 10), new Leading(14, 12, 12)));
			jPanel0.add(getJTextField7(), new Constraints(new Leading(276, 137,
					12, 12), new Leading(68, 12, 12)));
			jPanel0.add(getJTextField6(), new Constraints(new Leading(276, 137,
					12, 12), new Leading(96, 12, 12)));
			jPanel0.add(getJLabel8(), new Constraints(new Leading(435, 93, 10,
					10), new Leading(14, 12, 12)));
			jPanel0.add(getJTextField10(), new Constraints(new Leading(524,
					137, 12, 12), new Leading(66, 12, 12)));
			jPanel0.add(getJLabel2(), new Constraints(
					new Leading(7, 66, 10, 10), new Leading(72, 12, 12)));
			jPanel0.add(getJLabel10(), new Constraints(new Leading(435, 93, 12,
					12), new Leading(68, 12, 12)));
			jPanel0.add(getJLabel4(), new Constraints(new Leading(208, 65, 10,
					10), new Leading(20, 12, 12)));
			jPanel0.add(getJLabel5(), new Constraints(new Leading(206, 52, 12,
					12), new Leading(46, 12, 12)));
			jPanel0.add(getJTextField2(), new Constraints(new Leading(93, 106,
					10, 10), new Leading(68, 12, 12)));
			jPanel0.add(getAmountTextField(), new Constraints(new Leading(95, 106,
					10, 10), new Leading(42, 12, 12)));
			jPanel0.add(getJTextField3(), new Constraints(new Leading(93, 106,
					12, 12), new Leading(94, 12, 12)));
			jPanel0.add(getJLabel6(), new Constraints(new Leading(208, 56, 12,
					12), new Leading(70, 12, 12)));
			jPanel0.add(getJLabel7(), new Constraints(new Leading(206, 56, 12,
					12), new Leading(94, 20, 12, 12)));
			jPanel0.add(getJButton1(), new Constraints(new Leading(9, 46, 12,
					12), new Leading(159, 12, 12)));
			jPanel0.add(getJTextField11(), new Constraints(new Leading(91, 118,
					10, 10), new Leading(160, 12, 12)));
			jPanel0.add(getJScrollPane1(), new Constraints(new Leading(547,
					477, 10, 10), new Leading(124, 72, 12, 12)));
			jPanel0.add(getJLabel18(), new Constraints(
					new Leading(490, 12, 12), new Leading(126, 27, 12, 12)));
			jPanel0.add(getJButton0(), new Constraints(
					new Leading(942, 10, 10), new Leading(7, 12, 12)));
			jPanel0.add(getJTextField8(), new Constraints(new Leading(532, 137,
					10, 10), new Leading(14, 12, 12)));
			jPanel0.add(getJLabel3(), new Constraints(
					new Leading(7, 57, 12, 12), new Leading(99, 12, 12)));
			jPanel0.add(getJLabel0(), new Constraints(
					new Leading(9, 62, 12, 12), new Leading(12, 18, 12, 12)));
			jPanel0.add(getSettlementAmountLabel(), new Constraints(new Leading(12, 77, 10,
					10), new Leading(131, 12, 12)));
			jPanel0.add(getSettlementAmountTextField(), new Constraints(new Leading(93, 106,
					12, 12), new Leading(128, 12, 12)));
			jPanel0.add(getRefreshButton(), new Constraints(new Leading(942,
					85, 12, 12), new Leading(42, 12, 12)));
			jPanel0.add(getJTextField9(), new Constraints(new Leading(640, 137,
					10, 10), new Leading(40, 12, 12)));
			jPanel0.add(getJLabel9(), new Constraints(new Leading(586, 48, 12,
					12), new Leading(42, 19, 12, 12)));
			jPanel0.add(getJTextField5(), new Constraints(new Leading(276, 306,
					10, 10), new Leading(42, 12, 12)));
			jPanel0.addFocusListener(new FocusAdapter() {

				public void focusLost(FocusEvent event) {
				}

				public void focusGained(FocusEvent event) {
					try {
						fillJTabel((Vector) remoteBO.queryWhere("Transfer",
								"tradeId = " + trade.getId()));
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
				}
			});
		}
		return jPanel0;
	}

	private JLabel getJLabel2() {
		if (jLabel2 == null) {
			jLabel2 = new JLabel();
			jLabel2.setText("SettleDate");
		}
		return jLabel2;
	}

	private JLabel getAmountLabel() {
		if (amountLabel == null) {
			amountLabel = new JLabel();
			amountLabel.setText("Amt");
		}
		return amountLabel;
	}

	private JTextField getJTextField3() {
		if (jTextField3 == null) {
			jTextField3 = new JTextField();
			// jTextField3.setText("jTextField3");
		}
		return jTextField3;
	}

	private JLabel getJLabel3() {
		if (jLabel3 == null) {
			jLabel3 = new JLabel();
			jLabel3.setText("Payer");
		}
		return jLabel3;
	}

	private JTextField getJTextField2() {
		if (jTextField2 == null) {
			jTextField2 = new JTextField();
			// jTextField2.setText("jTextField2");
		}
		return jTextField2;
	}

	private JTextField getJTextField1() {
		if (jTextField1 == null) {
			jTextField1 = new JTextField();
			// jTextField1.setText("jTextField1");
		}
		return jTextField1;
	}

	private JTextField getAmountTextField() {
		if (amountTextField == null) {
			amountTextField = new JTextField();
			// amountTextField.setText("amountTextField");
		}
		return amountTextField;
	}
	
	private JLabel getJLabel0() {
		if (jLabel0 == null) {
			jLabel0 = new JLabel();
			jLabel0.setText("TradeID");
		}
		return jLabel0;
	}

	private JScrollPane getJScrollPane0() {
		if (jScrollPane0 == null) {
			jScrollPane0 = new JScrollPane();
			jScrollPane0.setViewportView(getJTable0());
		}
		return jScrollPane0;
	}

	public RemoteProduct getRemoteProduct() {
		return remoteProduct;
	}

	public void setRemoteProduct(RemoteProduct remoteProduct) {
		this.remoteProduct = remoteProduct;
	}

	@Override
	public void fillJTabel(Vector data) {
		if(data == null)
			return;
		this.data = data;
		model = new TableModelUtil(data, col, remoteBO, remoteProduct, false);
		trade = getTrade();
		NettingConfig config = getNettingConfig(trade.getCpID(),
				trade.getProductType());
		try {
			if (config != null) {
				settlementdata = (Vector) remoteBO.getNettedTransfers(config);
				settlementmodel = new TableModelUtil(settlementdata, col,
						remoteBO, remoteProduct, true);
				
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			commonUTIL.displayError("TransferPanel", "fillJTabel", e);
		}
		// model = new TableModelUtil(data,col,remoteBO,remoteProduct);

		

		jTabbedPane0.removeAll();
		jTabbedPane0.addTab("Transfer Details", getJPanel0());
		jTabbedPane0.addTab("SettlementPanel", getJPanel1());
		if(model != null)
		jTable0.setModel(model);
		if(settlementmodel != null)
		jTable2.setModel(settlementmodel);

	}

	Trade trade = null;
	RemoteBOProcess remoteBO;
	RemoteReferenceData remoteRef;
	private JButton jButton1;
	private JTextField jTextField11;
	private JTabbedPane jTabbedPane0;
	private JTable jTable2;
	private JScrollPane jScrollPane2;
	private JPanel jPanel1;
	private JPanel jPanel2;
	private JPanel jPanel3;
	private JTable jTable3;
	private JScrollPane jScrollPane3;
	private JPanel jPanel4;
	private static final String PREFERRED_LOOK_AND_FEEL = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";

	private NettingConfig getNettingConfig(int counterParyID, String productType) {
		String sql = "leid = " + counterParyID + " and productType ='"
				+ productType + "'";
		NettingConfig netConfig = null;
		try {
			Vector<NettingConfig> netConfigV = (Vector) remoteRef
					.getNettingConfigOnWhere(sql);
			if (netConfigV == null || netConfigV.isEmpty())
				return netConfig;
			else
				netConfig = netConfigV.elementAt(0);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return netConfig;

	}

	public RemoteBOProcess getRemoteBO() {
		return remoteBO;
	}

	public void setRemoteBO(RemoteBOProcess remoteBO) {
		this.remoteBO = remoteBO;
	}

	class TableModelUtil extends AbstractTableModel {
		
		boolean isSettled = false;
		final String[] columnNames;
		java.util.Hashtable<Integer, String> productNames = new Hashtable();
		Vector<Transfer> data;
		RemoteBOProcess remoteBO;
		RemoteProduct remoteProduct;

		public TableModelUtil(Vector<Transfer> myData, String col[],
				RemoteBOProcess remoteAcc, RemoteProduct remoteProduct,
				boolean isSettled) {
			this.columnNames = col;
			this.data = myData;
			this.remoteBO = remoteAcc;
			this.remoteProduct = remoteProduct;
			this.isSettled = isSettled;
		}

		public void setModel(Vector<Transfer> data) {
			this.data = data;
		}

		public int getColumnCount() {
			return columnNames.length;
		}

		public int getRowCount() {
			if (data != null)
				return data.size();
			return 0;
		}

		public String getColumnName(int col) {
			return columnNames[col];
		}

		public Object getValueAt(int row, int col) {
			Object value = null;
		//	"Transfer ID", "Trade id","EventType","TranferType", "AMOUNT","Settlement","Quantity","Product","Status","SettlementDate","Currency","ValueDate","Payer","Receiver"
			Transfer transfer = (Transfer) data.get(row);

			switch (col) {
			case 0:
				value = transfer.getId();
				break;
			case 1:
				value = transfer.getTradeId();
				break;
			case 2:
				value = transfer.getEventType();
				break;
			case 3:
				value = transfer.getTransferType();
				break;

			case 4:
				if (isSettled)
					value = transfer.getSettleAmount();
				else
					value = transfer.getAmount();
				break;
			case 5:
				value = transfer.getSettleAmount();
				break;
			case 6:

				value = transfer.getQuantity();
				break;
			case 7:
				value = getProductName(transfer.getProductId());
				break;
			case 8:
				value = transfer.getStatus();
				break;
			case 9:
				value = transfer.getDeliveryDate();
				break;
			case 10:
				value = transfer.getSettlecurrency();
				break;
			case 11:
				value = transfer.getValueDate();
				break;
			case 12:
				value = transfer.getPayerCode();
				break;
			case 13:
				value = transfer.getReceiverCode();
				break;
			case 14:
				value = transfer.getMethod();
				break;
			case 15:
				value = transfer.getPayerInst();
				break;
			case 16:
				value = transfer.getReceiverInst();
				break;
			
			}
			return value;
		}

		public String getProductName(int productID) {

			String productName = "";
			if (productID == 0)
				return productName;
			try {
				synchronized (productNames) {
					if (productNames.containsKey(productID)) {
						productName = (String) productNames.get(productID);
					} else {
						Product product = (Product) remoteProduct
								.selectProduct(productID);
						productName = product.getProductname();
						productNames.put(product.getId(), productName);

					}
				}
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return productName;

		}

		public boolean isCellEditable(int row, int col) {
			return false;
		}

		public void setValueAt(Object value, int row, int col) {
			
			if (value instanceof Transfer) {
				data.set(row, (Transfer) value);
				this.fireTableDataChanged();
			//	System.out.println("New value of data:");
			}

		}

		public void setValueAt(Object value, int row) {
		
			if (value instanceof Transfer) {
				data.set(row, (Transfer) value);
				this.fireTableDataChanged();
			//	System.out.println("New value of data:");
			}

		}

		public void addRow(Object value) {

			data.add((Transfer) value);
			this.fireTableDataChanged();

		}

		public void delRow(int row) {
			if (row != -1) {
				data.remove(row);
				this.fireTableDataChanged();
			}

		}

		public void udpateValueAt(Object value, int row, int col) {
			data.set(row, (Transfer) value);
			for (int i = 0; i < columnNames.length; i++)
				fireTableCellUpdated(row, i);

		}

		public void removeALL() {
			if (data != null) {
				data.removeAllElements();
			}
			data = null;
			this.fireTableDataChanged();
		}
	}

	public RemoteReferenceData getRefData() {
		return remoteRef;
	}

	public void setRefData(RemoteReferenceData remoteReference) {
		// TODO Auto-generated method stub
		remoteRef = remoteReference;
	}

	public void setUser(Users userName) {
		// TODO Auto-generated method stub
		this.users = userName;
	}

	RemoteTrade remoteTrade = null;

	public void setRemoteTrade(RemoteTrade remoteTrade) {
		// TODO Auto-generated method stub
		this.remoteTrade = remoteTrade;

	}

	public synchronized void addtaskData(TaskEventProcessor task) {
		// TODO Auto-generated method stub
		try {
		if(task == null)
			return;
		Transfer transfer = task.getTransfer();
		if(transfer == null)
			return;
		Trade ntrade = task.getTrade();
		if(ntrade == null)
			return;
		if(ntrade.getId() != trade.getId())
			return;
		
		trade = ntrade;
	
			
			Vector newdata = (Vector) remoteBO.queryWhere("Transfer",
					"tradeId = " + trade.getId());
			if(commonUTIL.isEmpty(newdata))
				return;
			
					model = new TableModelUtil(newdata, col, remoteBO, remoteProduct, false);
					jTable0.setModel(model);
		NettingConfig config = getNettingConfig(trade.getCpID(),
				trade.getProductType());
			if (config != null) {
				settlementdata = (Vector) remoteBO.getNettedTransfers(config);
				settlementmodel = new TableModelUtil(settlementdata, col,
						remoteBO, remoteProduct, true);
				jTable2.setModel(settlementmodel);
			}
		
		// model = new TableModelUtil(data,col,remoteBO,remoteProduct);
		if(transfer == null)
			return ;
		JTable dyTable = (JTable)dynTables.get(transfer.getDeliveryDate());
	
		Vector nettedTransfers = null;
		
			nettedTransfers = (Vector) remoteBO
					.getNettedTransfers(transfer.getNettedTransferID());
		
		if(nettedTransfers != null) {
		TableModelUtil mutil = new TableModelUtil(nettedTransfers, col,
				remoteBO, remoteProduct, false);
		if(dyTable != null) {
		dyTable.repaint();
		dyTable.setModel(mutil);
		}
		}
		if(dyTable != null)
		dynTables.put(transfer.getDeliveryDate(),dyTable);
		}catch (NullPointerException e) {
			commonUTIL.displayError("TransferPanel", " addtaskData ", e);
		
	} catch (RemoteException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
         
          
			 
		
		
		}

}

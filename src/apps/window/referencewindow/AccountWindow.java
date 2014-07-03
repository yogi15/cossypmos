package apps.window.referencewindow;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

import org.dyno.visual.swing.layouts.Bilateral;
import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;
import org.dyno.visual.swing.layouts.Trailing;

import util.commonUTIL;
import apps.window.utilwindow.JDialogBoxForChoice;
import apps.window.utilwindow.JDialogTable;
import beans.Account;
import beans.LegalEntity;
import beans.StartUPData;

import com.standbysoft.component.date.swing.JDatePicker;

import constants.CommonConstants;

import dsServices.RemoteAccount;
import dsServices.RemoteProduct;
import dsServices.RemoteReferenceData;
import dsServices.ServerConnectionUtil;

//VS4E -- DO NOT REMOVE THIS LINE!
public class AccountWindow extends JPanel {
	public static ServerConnectionUtil de = null;
	RemoteReferenceData remoteBORef;
	RemoteAccount remoteAccount;
	RemoteProduct remoteProduct;
	private static final long serialVersionUID = 1L;
	private JPanel jPanel0;
	private JTabbedPane jTabbedPane0;
	private JPanel jPanel1;
	private JPanel jPanel2;
	private JPanel jPanel3;
	private JLabel accountIdLabel;
	private JLabel accountNameLabel;
	private JLabel currencyLabel;
	private JTextField accountIdtextField;
	private JTextField jTextField1;
	private JTextField currencyTextField;
	private JLabel typeLabel;
	private JLabel poLabel;
	private JLabel descriptionLabel;
	private JTextField descriptionTextField;
	private JComboBox accTypeComboBox;
	private JButton currencyButton;
	private JLabel legalEntityLabel;
	private JLabel creationDateLabel;
	private JLabel closingDateLabel;
	private JButton saveButton;
	private JButton deleteButton;
	private JButton clearButton;
	private JButton saveAsNewButton;
	private JButton legalEntityButton;
	private JDatePicker jTextField4;
	private JDatePicker jTextField5;
	private JLabel parenctACLabel;
	private JTextField parentAccTextField;
	private JButton parentIdButton;
	private JTable jTable0;
	private JScrollPane jScrollPane0;
	private JLabel attributesLabel;
	private JButton attributesButton;
	TableModelUtil model;
	Account account = null;
	Vector data;
	Vector currencyVec;
	String accountColumnName[] = { "Acc id", "Account Name", "AccountType",
			"LE Name", "PO Name", "Parent Account", "Closing Date",
			"Creation Date", "Currency", "Description" };
	javax.swing.DefaultComboBoxModel accountTypeModel = new javax.swing.DefaultComboBoxModel();
	Hashtable po = new Hashtable();
	Hashtable le = new Hashtable();
	int leID = 0;
	int poID = 0;
	int parentID = 0;
	DefaultListModel currencyList = new DefaultListModel();
	javax.swing.DefaultComboBoxModel accType = new javax.swing.DefaultComboBoxModel();
	DefaultListModel leList = new DefaultListModel();
	String s[] = { "id", "LegalEntityName" };
	DefaultTableModel letablemodel = new DefaultTableModel(s, 0);
	String poCOL[] = { "id", "POName" };
	DefaultTableModel potablemodel = new DefaultTableModel(poCOL, 0);
	String accCOL[] = { "Accountid", "Account Name" };
	DefaultTableModel accounttablemodel = new DefaultTableModel(accCOL, 0);

	private JTextField legalEntityTextField;
	private JTextField jTextField7;
	private JButton showPOButton;
	private JTable jTable1;
	private JScrollPane jScrollPane1;
	private JLabel jLabel12;
	private JComboBox jComboBox1;
	private JTextField jTextField9;
	private JLabel jLabel11;
	private JButton jButton9;
	private JLabel jLabel13;
	private JTextField jTextField10;
	private JButton jButton10;
	private JPanel jPanel4;
	private static final String PREFERRED_LOOK_AND_FEEL = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";

	String col[] = { "Currency" };
	DefaultTableModel currencyTableModel = new DefaultTableModel(col, 0);

	public AccountWindow() {
		initData();
		initComponents();
	}

	private void initComponents() {
		setLayout(new GroupLayout());
		add(getJTabbedPane0(), new Constraints(new Leading(14, 716, 10, 10),
				new Leading(12, 511, 10, 10)));
		setSize(761, 532);

		processlistchoice(currencyTableModel, currencyVec);
		currencyJTDialogTable.jTable1
		.addMouseListener(new java.awt.event.MouseAdapter() {

			public void mouseClicked(MouseEvent e) {

				String currencyName = currencyJTDialogTable.jTable1.getValueAt(
						currencyJTDialogTable.jTable1.getSelectedRow(), 0)
						.toString();

				currencyTextField.setText(currencyName);

				currencyJTDialogTable.dispose();

			}

		});
	}
	
	
	private JPanel getJPanel4() {
		if (jPanel4 == null) {
			jPanel4 = new JPanel();
			jPanel4.setLayout(new GroupLayout());
			jPanel4.add(getJScrollPane1(), new Constraints(new Bilateral(13,
					12, 22), new Bilateral(102, 12, 26, 403)));
			jPanel4.add(getJLabel12(), new Constraints(
					new Leading(148, 12, 12), new Leading(22, 44, 437)));
			jPanel4.add(getJComboBox1(), new Constraints(new Leading(250, 153,
					10, 10), new Leading(13, 44, 437)));
			jPanel4.add(getJTextField9(), new Constraints(new Leading(78, 40,
					10, 10), new Leading(18, 44, 437)));
			jPanel4.add(getJLabel11(), new Constraints(new Leading(57, 10, 10),
					new Leading(20, 44, 437)));
			jPanel4.add(getJButton9(), new Constraints(new Leading(39, 10, 10),
					new Leading(71, 27, 10, 10)));
			jPanel4.add(getJLabel13(), new Constraints(new Leading(148, 54, 12,
					12), new Leading(61, 10, 10)));
			jPanel4.add(getJTextField10(), new Constraints(new Leading(250, 63,
					12, 12), new Leading(57, 24, 44, 437)));
			jPanel4.add(getJButton10(), new Constraints(new Leading(333, 38,
					10, 10), new Leading(57, 24, 44, 437)));
		}
		return jPanel4;
	}

	private JButton getJButton10() {
		if (jButton10 == null) {
			jButton10 = new JButton();
			jButton10.setText("jButton10");
		}
		return jButton10;
	}

	private JTextField getJTextField10() {
		if (jTextField10 == null) {
			jTextField10 = new JTextField();
			jTextField10.setEditable(false);
			// jTextField10.setText("jTextField10");
		}
		return jTextField10;
	}

	private JLabel getJLabel13() {
		if (jLabel13 == null) {
			jLabel13 = new JLabel();
			jLabel13.setText("Currency");
		}
		return jLabel13;
	}

	private JButton getJButton9() {
		if (jButton9 == null) {
			jButton9 = new JButton();
			jButton9.setText("LOAD");
		}
		return jButton9;
	}

	private JLabel getJLabel11() {
		if (jLabel11 == null) {
			jLabel11 = new JLabel();
			jLabel11.setText("ID");
		}
		return jLabel11;
	}

	private JTextField getJTextField9() {
		if (jTextField9 == null) {
			jTextField9 = new JTextField();
			// jTextField9.setText("jTextField9");
		}
		return jTextField9;
	}

	private JComboBox getJComboBox1() {
		if (jComboBox1 == null) {
			jComboBox1 = new JComboBox();
			jComboBox1.setModel(new DefaultComboBoxModel(new Object[] {
					"item0", "item1", "item2", "item3" }));
			jComboBox1.setDoubleBuffered(false);
			jComboBox1.setBorder(null);
		}
		return jComboBox1;
	}

	private JLabel getJLabel12() {
		if (jLabel12 == null) {
			jLabel12 = new JLabel();
			jLabel12.setText("AccountType");
		}
		return jLabel12;
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
			if (data != null) {
				model = new TableModelUtil(data, accountColumnName, remoteBORef);

				jTable1.setModel(model);
			}
			jTable1.addMouseListener(new java.awt.event.MouseAdapter() {

				@Override
				public void mouseClicked(MouseEvent e) {

					account = (Account) data.get(jTable1.getSelectedRow());

					openAccount(account);
					jTabbedPane0.setSelectedIndex(0);

				}

			});
		}
		return jTable1;
	}

	private JButton getShowPOButton() {
		if (showPOButton == null) {
			showPOButton = new JButton();
			showPOButton.setText("showPOButton");

			final JDialogTable showPO = new JDialogTable(potablemodel);
			getLEDataCombo1(potablemodel, "PO");
			showPO.setLocationRelativeTo(this);
			showPOButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {

					showPO.setVisible(true);

				}
			});
			showPO.jTable1.addMouseListener(new java.awt.event.MouseAdapter() {

				@Override
				public void mouseClicked(MouseEvent e) {
					int id = ((Integer) showPO.jTable1.getValueAt(
							showPO.jTable1.getSelectedRow(), 0)).intValue();

					String ss = (String) showPO.jTable1.getValueAt(
							showPO.jTable1.getSelectedRow(), 1);
					jTextField7.setText(ss);
					poID = id;
					showPO.dispose();
				}

			});

		}
		return showPOButton;
	}

	private JTextField getJTextField7() {
		if (jTextField7 == null) {
			jTextField7 = new JTextField();
			// jTextField7.setText("    ");
			jTextField7.setEditable(false);
		}
		return jTextField7;
	}

	private JTextField getLegalEntityTextField() {
		if (legalEntityTextField == null) {
			legalEntityTextField = new JTextField();
			// legalEntityTextField.setText("    ");
			legalEntityTextField.setEditable(false);
		}
		return legalEntityTextField;
	}

	private JButton getAttributesButton() {
		if (attributesButton == null) {
			attributesButton = new JButton();
			attributesButton.setText("attribues");
		}
		return attributesButton;
	}

	private JLabel getAttributesLabel() {
		if (attributesLabel == null) {
			attributesLabel = new JLabel();
			attributesLabel.setText("Attributes");
		}
		return attributesLabel;
	}

	private JScrollPane getJScrollPane0() {
		if (jScrollPane0 == null) {
			jScrollPane0 = new JScrollPane();
			jScrollPane0
					.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
			// jScrollPane0.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

			jScrollPane0.setViewportView(getJTable0());
		}
		return jScrollPane0;
	}

	private JTable getJTable0() {
		if (jTable0 == null) {
			jTable0 = new JTable();

		}
		jTable0.addMouseListener(new java.awt.event.MouseAdapter() {

		});
		return jTable0;
	}

	private JButton getParentIdButton() {
		if (parentIdButton == null) {
			parentIdButton = new JButton();
			parentIdButton.setText("parentID");

			final JDialogTable showAcc = new JDialogTable(accounttablemodel);
			getAccountsData(accounttablemodel);
			showAcc.setLocationRelativeTo(this);
			parentIdButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {

					showAcc.setVisible(true);

				}
			});
			showAcc.jTable1.addMouseListener(new java.awt.event.MouseAdapter() {

				@Override
				public void mouseClicked(MouseEvent e) {
					int id = ((Integer) showAcc.jTable1.getValueAt(
							showAcc.jTable1.getSelectedRow(), 0)).intValue();

					String ss = (String) showAcc.jTable1.getValueAt(
							showAcc.jTable1.getSelectedRow(), 1);
					parentAccTextField.setText(ss);
					parentID = id;
					showAcc.dispose();
				}

			});
		}
		return parentIdButton;
	}

	private JTextField getParentAccTextField() {
		if (parentAccTextField == null) {
			parentAccTextField = new JTextField();
			// parentAccTextField.setText("      ");
			parentAccTextField.setEditable(false);
		}
		return parentAccTextField;
	}

	private JLabel getParenctACLabel() {
		if (parenctACLabel == null) {
			parenctACLabel = new JLabel();
			parenctACLabel.setText("Parent A/C");
		}
		return parenctACLabel;
	}

	private JDatePicker getJTextField5() {
		if (jTextField5 == null) {
			jTextField5 = new JDatePicker();
			jTextField5.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
		}
		return jTextField5;
	}

	private JDatePicker getJTextField4() {
		if (jTextField4 == null) {
			jTextField4 = new JDatePicker();
			SimpleDateFormat date1 = new SimpleDateFormat("dd/MM/yyyy");
			jTextField4.setDateFormat(date1);
		}
		return jTextField4;
	}

	private JButton getLegalEntityButton() {
		if (legalEntityButton == null) {
			legalEntityButton = new JButton();
			legalEntityButton.setText("lebutton");
			getLEDataCombo1(letablemodel, "CounterParty");
			final JDialogTable showLE = new JDialogTable(letablemodel);
			showLE.setLocationRelativeTo(this);
			legalEntityButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {

					showLE.setVisible(true);

				}
			});
			showLE.jTable1.addMouseListener(new java.awt.event.MouseAdapter() {

				@Override
				public void mouseClicked(MouseEvent e) {
					int id = ((Integer) showLE.jTable1.getValueAt(
							showLE.jTable1.getSelectedRow(), 0)).intValue();

					String ss = (String) showLE.jTable1.getValueAt(
							showLE.jTable1.getSelectedRow(), 1);
					legalEntityTextField.setText(ss);
					leID = id;
					showLE.dispose();
				}

			});
		}
		return legalEntityButton;
	}

	private JButton getSaveAsNewButton() {
		if (saveAsNewButton == null) {
			saveAsNewButton = new JButton();
			saveAsNewButton.setText("SAVE AS NEW");

			saveAsNewButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub

					Account account = new Account();
					account.setId(0);
				boolean isFilled =	fillAccountData(account);
					
				if (isFilled) {
					
					try {
						int id = remoteAccount.saveAccount(account);
						commonUTIL.showAlertMessage("Account Save with ID "
								+ id);
						account.setId(id);
						accountIdtextField.setText(new Integer(account.getId())
								.toString());
						model.addRow(account);
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
				}

			});
		}
		return saveAsNewButton;
	}

	private JButton getClearButton() {
		if (clearButton == null) {
			clearButton = new JButton();
			clearButton.setText("CLEAR");
		}
		clearButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				clearALL();

			}

		});
		return clearButton;
	}

	private JButton getDeleteButton() {
		if (deleteButton == null) {
			deleteButton = new JButton();
			deleteButton.setText("DELETE");
		}
		deleteButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				// fillAccountData(account);
				try {
					remoteAccount.deleteAccount(account);

					model.delRow(jTable1.getSelectedRow());
					clearALL();
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}

		});
		return deleteButton;
	}

	private JButton getSaveButton() {
		if (saveButton == null) {
			saveButton = new JButton();
			saveButton.setText("SAVE");
		}
		saveButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				fillAccountData(account);
				try {
					if (remoteAccount.updateAccount(account)) {

						commonUTIL.showAlertMessage("Account  Updated");

						model.setValueAt(account, jTable1.getSelectedRow(),
								jTable1.getSelectedColumn());
					}
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}

		});
		return saveButton;
	}

	private JLabel getClosingDateLabel() {
		if (closingDateLabel == null) {
			closingDateLabel = new JLabel();
			closingDateLabel.setText("Closing Date");
		}
		return closingDateLabel;
	}

	private JLabel getCreationDateLabel() {
		if (creationDateLabel == null) {
			creationDateLabel = new JLabel();
			creationDateLabel.setText("Creation Date");
		}
		return creationDateLabel;
	}

	private JLabel getLegalEntityLabel() {
		if (legalEntityLabel == null) {
			legalEntityLabel = new JLabel();
			legalEntityLabel.setText("LegalEntity");
		}
		return legalEntityLabel;
	}

	final JDialogTable currencyJTDialogTable = new JDialogTable(
			currencyTableModel);

	private JButton getCurrencyButton() {
		if (currencyButton == null) {
			currencyButton = new JButton();
			currencyButton.setText("Currency");
		}
		/*
		 * processlistchoice(currencyList,"Currency"); final JDialogBoxForChoice
		 * choice12 = new JDialogBoxForChoice(currencyList);
		 * currencyButton.addActionListener(new ActionListener() {
		 * 
		 * @Override public void actionPerformed(ActionEvent e) { // TODO
		 * Auto-generated method stub
		 * 
		 * choice12.jList3.setModel(currencyList);
		 * choice12.setLocationRelativeTo(choice12);
		 * //choice12.setSize(200,200); choice12.setVisible(true);
		 * 
		 * }
		 * 
		 * }); choice12.addWindowListener(new WindowAdapter() { public void
		 * windowClosing(WindowEvent e) { //
		 * System.out.println("Window closing"); try { String ss = ""; Object
		 * obj [] = choice12.cmodList2.toArray(); for(int i =0;i<obj.length;i++)
		 * ss = ss + (String) obj[i] + ","; if(ss.trim().length() > 0)
		 * currencyTextField.setText(ss.substring(0, ss.length()-1)); } catch
		 * (Throwable t) { t.printStackTrace(); } } });
		 */

		currencyButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				currencyJTDialogTable.jTable1.setModel(currencyTableModel);
				currencyJTDialogTable.setVisible(true);
				// legalEntityJTDialogTable.setLocation(this);

			}

		});
		return currencyButton;
	}

	private JComboBox getAccTypeComboBox() {
		if (accTypeComboBox == null) {
			accTypeComboBox = new JComboBox();
			accTypeComboBox.setModel(accType);
			accTypeComboBox.setDoubleBuffered(false);
			accTypeComboBox.setBorder(null);
			// accTypeComboBox.setSelectedIndex(0);
		}
		return accTypeComboBox;
	}

	private JTextField getDescriptionTextField() {
		if (descriptionTextField == null) {
			descriptionTextField = new JTextField();
			descriptionTextField.setText("   ");
		}
		return descriptionTextField;
	}

	private JLabel getDescriptionLabel() {
		if (descriptionLabel == null) {
			descriptionLabel = new JLabel();
			descriptionLabel.setText("Description");
		}
		return descriptionLabel;
	}

	private JLabel getPoLabel() {
		if (poLabel == null) {
			poLabel = new JLabel();
			poLabel.setText("PO Name");
		}
		return poLabel;
	}

	private JLabel getTypeLabel() {
		if (typeLabel == null) {
			typeLabel = new JLabel();
			typeLabel.setText("TYPE");
		}
		return typeLabel;
	}

	private JTextField getCurrencyTextField() {
		if (currencyTextField == null) {
			currencyTextField = new JTextField();
			currencyTextField.setText("INR");
			currencyTextField.setEditable(false);
		}
		return currencyTextField;
	}

	private JTextField getJTextField1() {
		if (jTextField1 == null) {
			jTextField1 = new JTextField();
			// jTextField1.setText("   ");
		}
		return jTextField1;
	}

	private JTextField getAccountIdtextField() {
		if (accountIdtextField == null) {
			accountIdtextField = new JTextField();
			accountIdtextField.setText("0 ");
			accountIdtextField.setEditable(false);
		}
		return accountIdtextField;
	}

	private JLabel getCurrencyLabel() {
		if (currencyLabel == null) {
			currencyLabel = new JLabel();
			currencyLabel.setText("Currency");
		}
		return currencyLabel;
	}

	private JLabel getAccountNameLabel() {
		if (accountNameLabel == null) {
			accountNameLabel = new JLabel();
			accountNameLabel.setText("Account Name");
		}
		return accountNameLabel;
	}

	private JLabel getAccountIdLabel() {
		if (accountIdLabel == null) {
			accountIdLabel = new JLabel();
			accountIdLabel.setText("Account ID");
		}
		return accountIdLabel;
	}

	private JPanel getJPanel0() {
		if (jPanel0 == null) {
			jPanel0 = new JPanel();
			jPanel0.setLayout(new GroupLayout());
			jPanel0.add(getJPanel3(), new Constraints(new Bilateral(32, 12, 0),
					new Leading(414, 10, 10)));
			jPanel0.add(getJPanel2(), new Constraints(new Leading(17, 676, 12,
					12), new Leading(144, 247, 10, 10)));
			jPanel0.add(getJPanel1(), new Constraints(new Leading(15, 680, 10,
					10), new Leading(12, 120, 12, 12)));
		}
		return jPanel0;
	}

	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jPanel3 = new JPanel();
			jPanel3.setLayout(new GroupLayout());
			jPanel3.add(getSaveButton(), new Constraints(
					new Leading(141, 10, 10), new Trailing(12, 12, 12)));
			jPanel3.add(getDeleteButton(), new Constraints(new Leading(240, 81, 12,
					12), new Trailing(12, 26, 10, 14)));
			jPanel3.add(getClearButton(), new Constraints(new Leading(345, 81, 10,
					10), new Trailing(12, 26, 12, 12)));
			jPanel3.add(getSaveAsNewButton(), new Constraints(new Leading(15, 104, 10,
					10), new Trailing(12, 12, 12)));
		}
		return jPanel3;
	}

	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setLayout(new GroupLayout());
			jPanel2.add(getLegalEntityLabel(), new Constraints(new Leading(22, 61, 10,
					10), new Leading(19, 12, 12)));
			jPanel2.add(getCreationDateLabel(), new Constraints(new Leading(22, 80, 10,
					10), new Leading(62, 12, 12)));
			jPanel2.add(getJScrollPane0(), new Constraints(new Leading(458,
					200, 10, 10), new Leading(43, 150, 10, 10)));
			jPanel2.add(getAttributesLabel(), new Constraints(new Leading(456, 80, 10,
					10), new Leading(15, 12, 12)));
			jPanel2.add(getAttributesButton(), new Constraints(new Leading(543, 38, 10,
					10), new Leading(12, 20, 12, 12)));
			jPanel2.add(getLegalEntityButton(), new Constraints(new Leading(288, 38, 10,
					10), new Leading(17, 20, 12, 12)));
			jPanel2.add(getLegalEntityTextField(), new Constraints(new Leading(107, 157,
					12, 12), new Leading(15, 25, 12, 12)));
			jPanel2.add(getJTextField4(), new Constraints(new Leading(107, 144,
					12, 12), new Leading(55, 27, 12, 12)));
			jPanel2.add(getParenctACLabel(), new Constraints(new Leading(22, 80, 12,
					12), new Leading(148, 10, 10)));
			jPanel2.add(getParentIdButton(), new Constraints(new Leading(258, 38, 10,
					10), new Leading(144, 20, 10, 10)));
			jPanel2.add(getParentAccTextField(), new Constraints(new Leading(107, 145,
					12, 12), new Leading(137, 27, 10, 10)));
			jPanel2.add(getClosingDateLabel(), new Constraints(new Leading(24, 80, 10,
					10), new Leading(105, 12, 12)));
			jPanel2.add(getJTextField5(), new Constraints(new Leading(108, 144,
					12, 12), new Leading(94, 29, 12, 12)));
		}
		return jPanel2;
	}

	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setBorder(new LineBorder(Color.black, 2, false));
			jPanel1.setForeground(Color.lightGray);
			jPanel1.setLayout(new GroupLayout());
			jPanel1.add(getAccountIdLabel(), new Constraints(new Leading(21, 10, 10),
					new Leading(10, 10, 10)));
			jPanel1.add(getTypeLabel(), new Constraints(new Leading(369, 61, 10,
					10), new Leading(10, 12, 12)));
			jPanel1.add(getAccountNameLabel(), new Constraints(new Leading(21, 86, 12,
					12), new Leading(44, 12, 12)));
			jPanel1.add(getDescriptionLabel(), new Constraints(new Leading(369, 76, 10,
					10), new Leading(44, 12, 12)));
			jPanel1.add(getAccTypeComboBox(), new Constraints(new Leading(444, 128,
					12, 12), new Leading(12, 23, 12, 12)));
			jPanel1.add(getAccountIdtextField(), new Constraints(new Leading(146, 28,
					10, 10), new Leading(8, 12, 12)));
			jPanel1.add(getCurrencyLabel(), new Constraints(new Leading(21, 86, 12,
					12), new Leading(81, 12, 12)));
			jPanel1.add(getPoLabel(), new Constraints(new Leading(369, 61, 12,
					12), new Leading(81, 12, 12)));
			jPanel1.add(getCurrencyButton(), new Constraints(new Leading(244, 38, 10,
					10), new Leading(78, 20, 12, 12)));
			jPanel1.add(getJTextField7(), new Constraints(new Leading(442, 174,
					12, 12), new Leading(78, 28, 12, 12)));
			jPanel1.add(getShowPOButton(), new Constraints(new Leading(628, 38, 12,
					12), new Leading(78, 22, 12, 12)));
			jPanel1.add(getCurrencyTextField(), new Constraints(new Leading(146, 48,
					10, 10), new Leading(78, 28, 12, 12)));
			jPanel1.add(getJTextField1(), new Constraints(new Leading(146, 189,
					12, 12), new Leading(40, 26, 12, 12)));
			jPanel1.add(getDescriptionTextField(), new Constraints(new Leading(444, 174,
					10, 10), new Leading(43, 29, 12, 12)));
		}
		return jPanel1;
	}

	public void clearALL() {
		accountIdtextField.setText("0");

		jTextField1.setText("");
		currencyTextField.setText("INR");
		jTextField5.setSelectedDate(commonUTIL.getCurrentDate());
		jTextField4.setSelectedDate(commonUTIL.getCurrentDate());
		descriptionTextField.setText("");
		accTypeComboBox.setSelectedItem(0);
		parentID = 0;
		leID = 0;
		poID = 0;
		jTextField7.setText("");
		legalEntityTextField.setText("");
		parentAccTextField.setText("");
		account = new Account();

	}

	public void initData() {
		de = ServerConnectionUtil.connect("localhost", 1099,
				commonUTIL.getServerIP());
		try {
			remoteBORef = (RemoteReferenceData) de
					.getRMIService("ReferenceData");
			remoteAccount = (RemoteAccount) de.getRMIService("Account");
			remoteProduct = (RemoteProduct) de.getRMIService("Product");
			// acctype = (Vector) remoteBORef.getStartUPData("AccType");
			data = (Vector) remoteAccount.getAllAccounts();

			currencyVec = (Vector) remoteBORef.getStartUPData("Currency");

			getMasterDataOnComboBox(accType, "AccType");

		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	private JTabbedPane getJTabbedPane0() {
		if (jTabbedPane0 == null) {
			jTabbedPane0 = new JTabbedPane();
			jTabbedPane0.addTab("Set Up", getJPanel0());
			jTabbedPane0.addTab("Browse ", getJPanel4());
		}

		return jTabbedPane0;
	}

	private boolean fillAccountData(Account account) {
		
		
		if (commonUTIL.isEmpty(jTextField1.getText())) {
			
			commonUTIL.showAlertMessage("Enter Account name");
			return false;
			
		}
		
		if (poID == 0) {
			
			commonUTIL.showAlertMessage("Please select PO ");
			return false;

		}
		
		if (leID == 0) {
			
			commonUTIL.showAlertMessage("Please select Legal Entity ");
			return false;
			
		}
		
		if (currencyTextField.getText().trim().equals(CommonConstants.BLANKSTRING)) {
			
			commonUTIL.showAlertMessage("Please select Currency ");
			return false;
			
		}
		
		account.setAccountName(jTextField1.getText().trim());
		account.setCurrency(currencyTextField.getText().trim());
		account.setClosingDate(jTextField5.getSelectedDateAsText().trim());
		account.setCreationDate(jTextField4.getSelectedDateAsText().trim());
		account.setDesc(descriptionTextField.getText().trim());
		
		if (accTypeComboBox.getSelectedIndex() == -1) {
			commonUTIL.showAlertMessage("Select Account Type");
			return false;
		}
		
		account.setType(accTypeComboBox.getSelectedItem().toString().trim());

		account.setParentID(parentID);
		account.setCpId(leID);
		account.setPoid(poID);
		
		return true;

	}

	private void openAccount(Account account) {
		if (account != null) {
			accountIdtextField.setText(new Integer(account.getId()).toString());
			jTextField1.setText(account.getAccountName());
			currencyTextField.setText(account.getCurrency());
			if (account.getClosingDate() != null)
				jTextField5.setSelectedDate(commonUTIL
						.convertStringtoSQLDate(account.getClosingDate()));
			if (account.getCreationDate() != null)
				jTextField4.setSelectedDate(commonUTIL
						.convertStringtoSQLDate(account.getCreationDate()));
			descriptionTextField.setText(account.getDesc());
			accType.setSelectedItem((String) account.getType());
			parentID = account.getParentID();
			leID = account.getCpId();
			poID = account.getPoid();
			if (poID == 0)
				jTextField7.setText("0");
			else
				jTextField7.setText(((LegalEntity) getLeName(poID)).getName());
			if (leID == 0)
				legalEntityTextField.setText("0");
			else
				legalEntityTextField.setText(((LegalEntity) getLeName(leID)).getName());
			if (parentID == 0)
				parentAccTextField.setText("");
			else
				parentAccTextField.setText(((Account) getAccount(parentID))
						.getAccountName());
		}

	}

	private Account getAccount(int accountID) {
		Account account = null;
		try {
			account = remoteAccount.getAccount(accountID);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return account;

	}

	private LegalEntity getLeName(int leID) {
		LegalEntity le = null;
		try {
			le = remoteBORef.selectLE(leID);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return le;
	}

	private final void getMasterDataOnComboBox(
			javax.swing.DefaultComboBoxModel combodata, String name) {
		Vector vector = null;
		try {
			vector = (Vector) remoteBORef.getStartUPData(name);

			if (vector.size() > 0) {
				Iterator it = vector.iterator();
				int i = 0;
				// combodata.insertElementAt(" ", 0);

				while (it.hasNext()) {

					StartUPData data = (StartUPData) it.next();

					combodata.insertElementAt(data.getName(), i);
					i++;
				}

			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block

			commonUTIL.displayError("AccountWindow", "getMasterDataOnComboBox",
					e);
		}
	}

	public void getAccountsData(DefaultTableModel model) {
		Vector vector;
		try {

			vector = (Vector) remoteAccount.getAllAccounts();
			if (vector == null)
				return;
			Iterator it = vector.iterator();
			int i = 0;
			while (it.hasNext()) {

				Account account = (Account) it.next();
				model.insertRow(
						i,
						new Object[] { account.getId(),
								account.getAccountName() });
				i++;
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			commonUTIL.displayError("AccountWindow", "getAccountsData", e);
		}

	}

	public void getLEDataCombo1(DefaultTableModel model, String leRole) {
		Vector vector;
		try {

			vector = (Vector) remoteBORef.selectLEonWhereClause(" role = '"
					+ leRole + "'");
			Iterator it = vector.iterator();
			int i = 0;
			while (it.hasNext()) {

				LegalEntity le = (LegalEntity) it.next();
				model.insertRow(i, new Object[] { le.getId(), le.getName() });
				i++;
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			commonUTIL.displayError("AccountWindow", "getLEDataCombo1", e);
		}

	}

	public void processlistchoice(DefaultListModel list, String name) {
		Vector vector;
		try {
			vector = (Vector) remoteBORef.getStartUPData(name);

			if (vector.size() > 0) {
				Iterator it = vector.iterator();
				int i = 0;
				while (it.hasNext()) {

					StartUPData data = (StartUPData) it.next();

					list.addElement(data.getName());

					i++;
				}
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (Exception e) {
			commonUTIL.displayError("SDIWINDOW", "getMasterDataOnComboBox", e);
		}

	}

	public void processlistchoice(DefaultTableModel model, Vector currencyVec) {
		
		int i = 0;
		
		try {
			
			Iterator it = currencyVec.iterator();
			
			while (it.hasNext()) {
				
				StartUPData roleData = (StartUPData) it.next();

				model.insertRow(i, new Object[] { roleData.getName() });

				i++;
			}

		}  catch (Exception e) {
			commonUTIL.displayError("SDIWINDOW", "getMasterDataOnComboBox", e);
		}

	}

}

class TableModelUtil extends AbstractTableModel {

	final String[] columnNames;

	Vector<Account> data;
	RemoteReferenceData remoteRef;

	public TableModelUtil(Vector<Account> myData, String col[],
			RemoteReferenceData remoteRef) {
		this.columnNames = col;
		this.data = myData;
		this.remoteRef = remoteRef;
	}

	public void setData(Vector<Account> data) {
		this.data = data;
	}

	public int getColumnCount() {
		return columnNames.length;
	}

	public int getRowCount() {
		return data.size();
	}

	public String getColumnName(int col) {
		return columnNames[col];
	}

	public Object getValueAt(int row, int col) {
		Object value = null;

		Account account = (Account) data.get(row);
		// {"AccounID","Account Name",
		// "AccountType","LE Name","PO Name","Parent Account","Closing Date","Creation Date","Currency","Description"};
		switch (col) {
		case 0:
			value = account.getId();
			break;
		case 1:
			value = account.getAccountName();
			break;
		case 2:
			value = account.getType();
			break;
		case 3:
			LegalEntity le = (LegalEntity) getLeName(account.getCpId());
			if (le == null)
				value = "";
			else
				value = le.getName();
			break;
		case 4:
			LegalEntity po = (LegalEntity) getLeName(account.getPoid());
			if (po == null)
				value = "";
			else
				value = po.getName();
			break;
		case 5:
			value = account.getParentID();
			break;
		case 6:
			value = account.getClosingDate();
			break;
		case 7:
			value = account.getCreationDate();
			break;
		case 8:
			value = account.getCurrency();
			break;
		case 9:
			value = account.getDesc();
			break;
		}
		return value;
	}

	public boolean isCellEditable(int row, int col) {
		return false;
	}

	public void setValueAt(Object value, int row, int col) {
		System.out.println("Setting value at " + row + "," + col + " to "
				+ value + " (an instance of " + value.getClass() + ")");
		if (value instanceof Account) {
			data.set(row, (Account) value);
			this.fireTableDataChanged();
			System.out.println("New value of data:");
		}

	}

	public void addRow(Object value) {

		data.add((Account) value);
		this.fireTableDataChanged();

	}

	public void delRow(int row) {

		data.remove(row);
		this.fireTableDataChanged();

	}

	public void udpateValueAt(Object value, int row, int col) {

		data.set(row, (Account) value);
		fireTableCellUpdated(row, col);

	}

	private LegalEntity getLeName(int leID) {
		LegalEntity le = null;
		try {
			le = remoteRef.selectLE(leID);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return le;
	}

	public void removeALL() {
		data.removeAllElements();
		this.fireTableDataChanged();
	}
}

package apps.window.referencewindow;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import org.dyno.visual.swing.layouts.Bilateral;
import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;

import util.RemoteServiceUtil;
import util.commonUTIL;
import beans.CurrencySplitConfig;
import beans.LeContacts;
import beans.LegalEntity;
import beans.StartUPData;
import dsServices.RemoteReferenceData;
import apps.window.utilwindow.JDialogTable;

//VS4E -- DO NOT REMOVE THIS LINE!
public class LEcontactWindow extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable jTable0;
	private JScrollPane jScrollPane0;
	private JButton saveAsNewButton;
	private JButton newButton;
	private JButton saveButton;
	private JButton deleteButton;
	private JPanel jPanel1;
	private JPanel jPanel0;
	private JLabel roleLabel;
	private JTextField roleField;
	private JLabel legalEntityLabel;
	private JLabel contactTypeLabel;
	private JTextField legalEntityField;
	private JButton roleListButton;
	private JTextField contactTypeField;
	private JButton contactTypeListButton;
	private JPanel jPanel2;
	private JPanel jPanel3;
	private JTextField addressfield;
	private JLabel addressLabel;
	private JLabel firstNameLabel;
	private JLabel lastNameLabel;
	private JLabel cityLabel;
	private JLabel countryLabel;
	private JTextField firstNameField;
	private JTextField lastNameField;
	private JTextField cityField;
	private JComboBox countryComboBox;
	private JLabel stateLabel;
	private JLabel zipCodeLabel;
	private JTextField stateField;
	private JTextField zipCodeField;
	private JTextField phoneField;
	private JLabel phoneLabel;
	private JLabel telexLabel;
	private JTextField telexField;
	private JTextField faxField;
	private JLabel faxLabel;
	private JLabel swiftLabel;
	private JTextField swiftField;
	private JLabel emailLabel;
	private JTextField emailIdField;
	private JLabel codeLabel;
	private JComboBox codeComboBox;
	private JButton jButton7;
	private JTextField commentsField;
	private JLabel commentsLabel;
	RemoteReferenceData remoteBORef;

	javax.swing.DefaultComboBoxModel countryVal = new javax.swing.DefaultComboBoxModel();

	// Vector roles = null;
	Vector attributes = null;
	Vector contactTypes = null;
	Vector countryVec = null;

	Vector<LegalEntity> legalEntitys = new Vector<LegalEntity>();
	Vector<LeContacts> leContactDetails = new Vector<LeContacts>();

	DefaultListModel rolesList = new DefaultListModel();
	DefaultListModel listModel = new DefaultListModel();
	//-DefaultTableModel leContactModel = null;

	TableModelUtil leContactDetailsTable = null;

	LegalEntity le = null;
	// private JList jRoleList;

	String leContactDetailsTableCol[] = { "CpId", "Name ", "Role",
			"ContactCategory" };

	Hashtable countryId = new Hashtable();

	public LEcontactWindow(LegalEntity le) {

		this.le = le;
		initComponents();
	}

	String sql = "";

	@SuppressWarnings("unchecked")
	private void initComponents() {

		remoteBORef = (RemoteReferenceData) RemoteServiceUtil
				.getRemoteReferenceDataService();

		sql = "LE_ID = " + le.getId();

		try {

			// roles = (Vector) remoteBORef.getStartUPData("Roles");
			attributes = (Vector) remoteBORef.getStartUPData("LEAttributes");
			legalEntitys = (Vector<LegalEntity>) remoteBORef.selectAllLs();
			leContactDetails = (Vector<LeContacts>) remoteBORef
					.selectLEContactOnWhereClause(sql);
			contactTypes = (Vector) remoteBORef.getStartUPData("LEContacts");
			countryVec = (Vector) remoteBORef.getStartUPData("Country");

		} catch (RemoteException e) {

			e.printStackTrace();

		}
		// final JDialogTable legalEntityJTDialogTable = new
		// JDialogTable(legalEntityTableModel);

		leContactDetailsTable = new TableModelUtil(leContactDetails,
				leContactDetailsTableCol);

		// processLEDataCombo(legalEntityTableModel, legalEntitys);

		legalEntityJTDialogTable.jTable1
				.addMouseListener(new java.awt.event.MouseAdapter() {

					public void mouseClicked(MouseEvent e) {
						String legalEntityName = legalEntityJTDialogTable.jTable1
								.getValueAt(
										legalEntityJTDialogTable.jTable1
												.getSelectedRow(), 1)
								.toString();

						legalEntityField.setText(legalEntityName);

						legalEntityJTDialogTable.dispose();

					}

				});

		processRoleDataCombo(roleTableModel, le.getRole());

		roleJTDialogTable.jTable1
				.addMouseListener(new java.awt.event.MouseAdapter() {

					public void mouseClicked(MouseEvent e) {

						String roleName = roleJTDialogTable.jTable1.getValueAt(
								roleJTDialogTable.jTable1.getSelectedRow(), 0)
								.toString();

						roleField.setText(roleName);

						roleJTDialogTable.dispose();

					}

				});

		processDataCombo(contactTypeTableModel, contactTypes);

		contactTypeJTDialogTable.jTable1
				.addMouseListener(new java.awt.event.MouseAdapter() {

					public void mouseClicked(MouseEvent e) {

						String contactType = contactTypeJTDialogTable.jTable1
								.getValueAt(
										contactTypeJTDialogTable.jTable1
												.getSelectedRow(), 0)
								.toString();

						contactTypeField.setText(contactType);

						contactTypeJTDialogTable.dispose();

					}

				});

		setLayout(new GroupLayout());
		add(getJPanel0(), new Constraints(new Leading(5, 960, 10, 10),
				new Bilateral(6, 12, 0)));
		setSize(971, 513);
	}

	private JLabel getCommentsLabel() {
		if (commentsLabel == null) {
			commentsLabel = new JLabel();
			commentsLabel.setText("Comments");
		}
		return commentsLabel;
	}

	private JTextField getCommentsField() {
		if (commentsField == null) {
			commentsField = new CustomTextField(10);
			commentsField.setText("");
		}
		return commentsField;
	}

	private JButton getJButton7() {
		if (jButton7 == null) {
			jButton7 = new JButton();
			jButton7.setText("jButton7");
		}
		return jButton7;
	}

	private JComboBox getCodeComboBox() {
		if (codeComboBox == null) {
			codeComboBox = new JComboBox();
			codeComboBox.setModel(new DefaultComboBoxModel(new Object[] {
					"item0", "item1", "item2", "item3" }));
		}
		return codeComboBox;
	}

	private JLabel getCodeLabel() {
		if (codeLabel == null) {
			codeLabel = new JLabel();
			codeLabel.setText("Code");
		}
		return codeLabel;
	}

	private JTextField getEmailIdField() {
		if (emailIdField == null) {
			emailIdField = new JTextField();
			emailIdField.setText("");
		}
		return emailIdField;
	}

	private JLabel getEmailLabel() {
		if (emailLabel == null) {
			emailLabel = new JLabel();
			emailLabel.setText("Email");
		}
		return emailLabel;
	}

	private JTextField getSwiftField() {
		if (swiftField == null) {
			swiftField = new JTextField();
			swiftField.setText("");
		}
		return swiftField;
	}

	private JLabel getSwiftLabel() {
		if (swiftLabel == null) {
			swiftLabel = new JLabel();
			swiftLabel.setText("Swift");
		}
		return swiftLabel;
	}

	private JLabel getFaxLabel() {
		if (faxLabel == null) {
			faxLabel = new JLabel();
			faxLabel.setText("Fax");
		}
		return faxLabel;
	}

	private JTextField getFaxField() {
		if (faxField == null) {
			faxField = new JTextField();
			faxField.setText("");
		}
		return faxField;
	}

	private JTextField getTelexField() {
		if (telexField == null) {
			telexField = new JTextField();
			telexField.setText("");
		}
		return telexField;
	}

	private JLabel getTelexLabel() {
		if (telexLabel == null) {
			telexLabel = new JLabel();
			telexLabel.setText("Telex");
		}
		return telexLabel;
	}

	private JLabel getPhoneLabel() {
		if (phoneLabel == null) {
			phoneLabel = new JLabel();
			phoneLabel.setText("Phone");
		}
		return phoneLabel;
	}

	private JTextField getPhoneField() {
		if (phoneField == null) {
			phoneField = new JTextField();
			phoneField.setText("");
		}
		return phoneField;
	}

	private JTextField getZipCodeField() {
		if (zipCodeField == null) {
			zipCodeField = new JTextField();
			zipCodeField.setText("");
		}
		return zipCodeField;
	}

	private JTextField getStateField() {
		if (stateField == null) {
			stateField = new JTextField();
			stateField.setText("");
		}
		return stateField;
	}

	private JLabel getZipCodeLabel() {
		if (zipCodeLabel == null) {
			zipCodeLabel = new JLabel();
			zipCodeLabel.setText("ZipCode");
		}
		return zipCodeLabel;
	}

	private JLabel getStateLabel() {
		if (stateLabel == null) {
			stateLabel = new JLabel();
			stateLabel.setText("State");
		}
		return stateLabel;
	}

	private JComboBox getCountryComboBox() {
		if (countryComboBox == null) {
			countryComboBox = new JComboBox();

			processJComboBox(countryVal, countryVec, countryId);

			countryComboBox.setModel(countryVal);

		}
		return countryComboBox;
	}

	private JTextField getCityField() {
		if (cityField == null) {
			cityField = new JTextField();
			cityField.setText("");
		}
		return cityField;
	}

	private JTextField getLastNameField() {
		if (lastNameField == null) {
			lastNameField = new JTextField();
			lastNameField.setText("");
		}
		return lastNameField;
	}

	private JTextField getFirstNameField() {
		if (firstNameField == null) {
			firstNameField = new JTextField();
			firstNameField.setText("");
		}
		return firstNameField;
	}

	private JLabel getCountryLabel() {
		if (countryLabel == null) {
			countryLabel = new JLabel();
			countryLabel.setText("Country");
		}
		return countryLabel;
	}

	private JLabel getCityLabel() {
		if (cityLabel == null) {
			cityLabel = new JLabel();
			cityLabel.setText("City");
		}
		return cityLabel;
	}

	private JLabel getLastNameLabel() {
		if (lastNameLabel == null) {
			lastNameLabel = new JLabel();
			lastNameLabel.setText("LastName");
		}
		return lastNameLabel;
	}

	private JLabel getFirstNameLabel() {
		if (firstNameLabel == null) {
			firstNameLabel = new JLabel();
			firstNameLabel.setText("FirstName");
		}
		return firstNameLabel;
	}

	private JLabel getAddressLabel() {
		if (addressLabel == null) {
			addressLabel = new JLabel();
			addressLabel.setText("Address");
		}
		return addressLabel;
	}

	private JTextField getAddressfield() {
		if (addressfield == null) {
			addressfield = new CustomTextField(10);
			addressfield.setText("");
		}
		return addressfield;
	}

	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jPanel3 = new JPanel();
			jPanel3.setBorder(BorderFactory.createEtchedBorder(
					EtchedBorder.LOWERED, null, null));
			jPanel3.setLayout(new GroupLayout());
			jPanel3.add(getSaveAsNewButton(), new Constraints(new Leading(53,
					10, 10), new Leading(252, 25, 10, 10)));
			jPanel3.add(getAddressLabel(), new Constraints(new Leading(6, 10,
					10), new Leading(20, 10, 10)));
			jPanel3.add(getLastNameLabel(), new Constraints(new Leading(4, 70,
					10, 10), new Leading(159, 10, 10)));
			jPanel3.add(getCityLabel(), new Constraints(new Leading(4, 70, 12,
					12), new Leading(187, 10, 10)));
			jPanel3.add(getCountryLabel(), new Constraints(new Leading(4, 70,
					12, 12), new Leading(219, 12, 12)));
			jPanel3.add(getFirstNameField(), new Constraints(new Leading(64,
					176, 12, 12), new Leading(116, 26, 12, 12)));
			jPanel3.add(getAddressfield(), new Constraints(new Leading(64, 177,
					10, 10), new Leading(12, 98, 12, 12)));
			jPanel3.add(getLastNameField(), new Constraints(new Leading(66,
					172, 12, 12), new Leading(148, 26, 12, 12)));
			jPanel3.add(getCityField(), new Constraints(new Leading(66, 172,
					12, 12), new Leading(179, 25, 12, 12)));
			jPanel3.add(getCountryComboBox(), new Constraints(new Leading(66,
					172, 12, 12), new Leading(213, 25, 12, 12)));
			jPanel3.add(getFirstNameLabel(), new Constraints(new Leading(6, 52,
					12, 12), new Leading(122, 10, 10)));
			jPanel3.add(getStateLabel(), new Constraints(new Leading(253, 52,
					10, 10), new Leading(18, 22, 10, 10)));
			jPanel3.add(getZipCodeLabel(), new Constraints(new Leading(253, 52,
					12, 12), new Leading(48, 22, 10, 10)));
			jPanel3.add(getStateField(), new Constraints(new Leading(299, 176,
					10, 10), new Leading(12, 26, 12, 12)));
			jPanel3.add(getZipCodeField(), new Constraints(new Leading(299,
					176, 12, 12), new Leading(50, 26, 12, 12)));
			jPanel3.add(getPhoneField(), new Constraints(new Leading(298, 176,
					12, 12), new Leading(82, 26, 12, 12)));
			jPanel3.add(getPhoneLabel(), new Constraints(new Leading(250, 12,
					12), new Leading(80, 22, 10, 10)));
			jPanel3.add(getTelexLabel(), new Constraints(new Leading(250, 43,
					12, 12), new Leading(119, 22, 12, 12)));
			jPanel3.add(getTelexField(), new Constraints(new Leading(298, 176,
					12, 12), new Leading(118, 26, 12, 12)));
			jPanel3.add(getFaxField(), new Constraints(new Leading(299, 176,
					12, 12), new Leading(153, 26, 12, 12)));
			jPanel3.add(getFaxLabel(), new Constraints(new Leading(250, 43, 12,
					12), new Leading(151, 22, 12, 12)));
			jPanel3.add(getSwiftLabel(), new Constraints(new Leading(250, 43,
					12, 12), new Leading(183, 22, 12, 12)));
			jPanel3.add(getSwiftField(), new Constraints(new Leading(298, 176,
					12, 12), new Leading(185, 26, 12, 12)));
			jPanel3.add(getEmailLabel(), new Constraints(new Leading(250, 43,
					12, 12), new Leading(217, 22, 12, 12)));
			jPanel3.add(getEmailIdField(), new Constraints(new Leading(298,
					284, 10, 10), new Leading(217, 26, 12, 12)));
			jPanel3.add(getJButton7(), new Constraints(new Leading(486, 32, 10,
					10), new Leading(185, 12, 12)));
			jPanel3.add(getCommentsLabel(), new Constraints(new Leading(493,
					52, 12, 12), new Leading(16, 22, 12, 12)));
			jPanel3.add(getCommentsField(), new Constraints(new Bilateral(497,
					12, 6), new Leading(35, 140, 10, 10)));
			jPanel3.add(getDeleteButton(), new Constraints(new Leading(359, 10,
					10), new Leading(254, 12, 12)));
			jPanel3.add(getSaveButton(), new Constraints(new Leading(268, 10,
					10), new Leading(254, 12, 12)));
			jPanel3.add(getNewButton(), new Constraints(
					new Leading(188, 10, 10), new Leading(254, 12, 12)));
		}
		return jPanel3;
	}

	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setBorder(BorderFactory.createEtchedBorder(
					EtchedBorder.LOWERED, null, null));
			jPanel2.setLayout(new GroupLayout());
			jPanel2.add(getRoleLabel(), new Constraints(new Leading(10, 88, 10,
					10), new Leading(53, 22, 10, 10)));
			jPanel2.add(getRoleField(), new Constraints(new Leading(7, 142, 12,
					12), new Leading(77, 29, 10, 10)));
			jPanel2.add(getLegalEntityLabel(), new Constraints(new Leading(7,
					55, 12, 12), new Leading(2, 22, 10, 10)));
			jPanel2.add(getContactTypeLabel(), new Constraints(new Leading(7,
					88, 12, 12), new Leading(114, 22, 32, 40)));
			jPanel2.add(getLegalEntityField(), new Constraints(new Leading(9,
					163, 10, 10), new Leading(25, 29, 10, 10)));
			/*
			 * jPanel2.add(getLegalEntityButton(), new Constraints(new Leading(
			 * 180, 27, 10, 10), new Leading(30, 12, 12)));
			 */
			jPanel2.add(getRoleListButton(), new Constraints(new Leading(161,
					27, 12, 12), new Leading(79, 12, 12)));
			jPanel2.add(getContactTypeField(), new Constraints(new Leading(7,
					163, 12, 12), new Leading(138, 28, 10, 10)));
			jPanel2.add(getContactTypeListButton(), new Constraints(
					new Leading(180, 27, 12, 12), new Leading(141, 12, 12)));
			jPanel2.add(getCodeLabel(), new Constraints(new Leading(10, 45, 10,
					10), new Leading(178, 22, 12, 12)));
			jPanel2.add(getCodeComboBox(), new Constraints(new Leading(10, 166,
					10, 10), new Leading(202, 28, 10, 10)));
		}
		return jPanel2;
	}

	String conTactTypeCol[] = { "ContactType" };
	DefaultTableModel contactTypeTableModel = new DefaultTableModel(
			conTactTypeCol, 0) {
		@Override
		public boolean isCellEditable(int row, int column) {
			// all cells false
			return false;

		}

	};

	final JDialogTable contactTypeJTDialogTable = new JDialogTable(
			contactTypeTableModel);

	private JButton getContactTypeListButton() {
		if (contactTypeListButton == null) {
			contactTypeListButton = new JButton();
			contactTypeListButton.setText("contactTypeListButton");
		}

		contactTypeListButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				contactTypeJTDialogTable.jTable1
						.setModel(contactTypeTableModel);
				contactTypeJTDialogTable.setVisible(true);
				// legalEntityJTDialogTable.setLocation(this);

			}

		});

		return contactTypeListButton;
	}

	private JTextField getContactTypeField() {
		if (contactTypeField == null) {
			contactTypeField = new JTextField();
			contactTypeField.setText("");
		}
		return contactTypeField;
	}

	String r[] = { "Role" };
	DefaultTableModel roleTableModel = new DefaultTableModel(r, 0) {
		@Override
		public boolean isCellEditable(int row, int column) {
			// all cells false
			return false;
		}

	};

	final JDialogTable roleJTDialogTable = new JDialogTable(roleTableModel);

	private JButton getRoleListButton() {
		if (roleListButton == null) {
			roleListButton = new JButton();
			roleListButton.setText("roleListButton");
		}

		/*
		 * processlistchoice(rolesList, roles);
		 * 
		 * final JDialogBoxForChoice roleListChoice = new JDialogBoxForChoice(
		 * rolesList); roleListButton.addActionListener(new ActionListener() {
		 * 
		 * public void actionPerformed(ActionEvent e) { // TODO Auto-generated
		 * method stub
		 * 
		 * roleListChoice.jList3.setModel(rolesList);
		 * roleListChoice.setLocationRelativeTo(roleListChoice); //
		 * choice12.setSize(200,200); roleListChoice.setVisible(true);
		 * 
		 * }
		 * 
		 * }); roleListChoice.addWindowListener(new WindowAdapter() { public
		 * void windowClosing(WindowEvent e) { //
		 * System.out.println("Window closing");
		 * 
		 * String ss = ""; Object obj[] = roleListChoice.getObj(); for (int i =
		 * 0; i < obj.length; i++) { ss = (String) obj[i]; if
		 * (!checkRoleExists(ss)) { roleField.setText(ss); }
		 * 
		 * }
		 * 
		 * } });
		 */
		roleListButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				roleJTDialogTable.jTable1.setModel(roleTableModel);
				roleJTDialogTable.setVisible(true);
				// legalEntityJTDialogTable.setLocation(this);

			}

		});

		return roleListButton;
	}

	String s[] = { "Id", "LegalEntityName" };
	DefaultTableModel legalEntityTableModel = new DefaultTableModel(s, 0) {
		@Override
		public boolean isCellEditable(int row, int column) {
			// all cells false
			return false;
		}

	};
	final JDialogTable legalEntityJTDialogTable = new JDialogTable(
			legalEntityTableModel);

	private static final String PREFERRED_LOOK_AND_FEEL = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";


	private JTextField getLegalEntityField() {
		if (legalEntityField == null) {
			legalEntityField = new JTextField();
			legalEntityField.setText(le.getName());
		}
		return legalEntityField;
	}

	private JLabel getContactTypeLabel() {
		if (contactTypeLabel == null) {
			contactTypeLabel = new JLabel();
			contactTypeLabel.setText("ContactType");
		}
		return contactTypeLabel;
	}

	private JLabel getLegalEntityLabel() {
		if (legalEntityLabel == null) {
			legalEntityLabel = new JLabel();
			legalEntityLabel.setText("LealEntity");
		}
		return legalEntityLabel;
	}

	private JTextField getRoleField() {
		if (roleField == null) {
			roleField = new JTextField();
			roleField.setText("");
		}
		return roleField;
	}

	private JLabel getRoleLabel() {
		if (roleLabel == null) {
			roleLabel = new JLabel();
			roleLabel.setText("Role");
		}
		return roleLabel;
	}

	private JPanel getJPanel0() {
		if (jPanel0 == null) {
			jPanel0 = new JPanel();
			jPanel0.setBorder(BorderFactory.createEtchedBorder(
					EtchedBorder.LOWERED, null, null));
			jPanel0.setLayout(new GroupLayout());
			jPanel0.add(getJScrollPane0(), new Constraints(new Leading(2, 932,
					10, 10), new Leading(331, 150, 10, 10)));
			jPanel0.add(getJPanel1(), new Constraints(new Leading(6, 940, 10,
					10), new Leading(4, 315, 12, 12)));
		}
		return jPanel0;
	}

	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
			jPanel1.setLayout(new GroupLayout());
			jPanel1.add(getJPanel2(), new Constraints(new Leading(4, 218, 10,
					10), new Leading(7, 301, 10, 10)));
			jPanel1.add(getJPanel3(), new Constraints(new Leading(228, 704, 10,
					10), new Leading(6, 302, 12, 12)));
		}
		return jPanel1;
	}

	private JButton getDeleteButton() {
		if (deleteButton == null) {
			deleteButton = new JButton();
			deleteButton.setText("DELETE");
		}
		deleteButton.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent evt) {

				String leName = "";

				if (jTable0.getSelectedRow() != -1) {

					LeContacts leContacts = new LeContacts();

					leName = legalEntityField.getText();
					leContacts.setLeId(getLEId(leName));
					leContacts.setLeRole(roleField.getText());
					leContacts.setContactCategory(contactTypeField.getText());
					// leContacts.setCode(codeComboBox.getSelectedItem()
					// .toString());
					leContacts.setMailingAddress(addressfield.getText());
					leContacts.setLeFirstName(firstNameField.getText());
					leContacts.setLeLastName(lastNameField.getText());
					leContacts.setCity(cityField.getText());
					leContacts.setState(stateField.getText());
					leContacts.setCountry(countryComboBox.getSelectedItem()
							.toString());
					leContacts.setZipCode(zipCodeField.getText());
					leContacts.setPhone(phoneField.getText());
					leContacts.setTelex(telexField.getText());
					leContacts.setFax(faxField.getText());
					leContacts.setSwift(swiftField.getText());
					leContacts.setEmailAddresss(emailIdField.getText());
					leContacts.setComments(commentsField.getText());

					try {

						boolean isDeleted = remoteBORef
								.deleteLeContacts(leContacts);

						// String mess = process.newRecordProcess(book);
						if (!isDeleted) {

							commonUTIL
									.showAlertMessage("Le Contact not deleted ");

						} else {

							commonUTIL.showAlertMessage("Le Contact deleted");

							leContactDetailsTable.delRow(jTable0.getSelectedRow());
							clearData();

						}

					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} else {

					commonUTIL
							.showAlertMessage("Please select a Legal Entity to delete");

					return;

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
		saveButton.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent evt) {

				if (validateData()) {

					LeContacts leContacts = new LeContacts();
					leContacts.setLeId(getLEId(legalEntityField.getText()));
					leContacts.setLeRole(roleField.getText());
					leContacts.setContactCategory(contactTypeField.getText());
					// leContacts.setCode(codeComboBox.getSelectedItem()
					// .toString());
					leContacts.setMailingAddress(addressfield.getText());
					leContacts.setLeFirstName(firstNameField.getText());
					leContacts.setLeLastName(lastNameField.getText());
					leContacts.setCity(cityField.getText());
					leContacts.setState(stateField.getText());
					leContacts.setCountry(countryComboBox.getSelectedItem()
							.toString());
					leContacts.setZipCode(zipCodeField.getText());
					leContacts.setPhone(phoneField.getText());
					leContacts.setTelex(telexField.getText());
					leContacts.setFax(faxField.getText());
					leContacts.setSwift(swiftField.getText());
					leContacts.setEmailAddresss(emailIdField.getText());
					leContacts.setComments(commentsField.getText());

					try {

						boolean isSaved = remoteBORef
								.updateLeContacts(leContacts);

						// String mess = process.newRecordProcess(book);
						if (!isSaved) {

							commonUTIL
									.showAlertMessage("Le Contact not updated ");

						} else {

							commonUTIL.showAlertMessage("Le Contact updated");
							
							int rowindex = jTable0.getSelectedRow();
							leContactDetailsTable.setValueAt(leContacts, rowindex, 1);
							jTable0.repaint();

						}

					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			}

		});
		return saveButton;
	}

	private JButton getNewButton() {
		if (newButton == null) {
			newButton = new JButton();
			newButton.setText("NEW");
		}
		newButton.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent evt) {

				clearData();

			}

		});
		return newButton;
	}

	private void clearData() {

		legalEntityField.setText("");
		roleField.setText("");
		contactTypeField.setText("");
		codeComboBox.setSelectedIndex(0);
		addressfield.setText("");
		firstNameField.setText("");
		lastNameField.setText("");
		cityField.setText("");
		stateField.setText("");
		countryComboBox.setSelectedIndex(0);
		// countryField.setText("");
		zipCodeField.setText("");
		phoneField.setText("");
		telexField.setText("");
		faxField.setText("");
		swiftField.setText("");
		emailIdField.setText("");
		commentsField.setText("");
	}

	private JButton getSaveAsNewButton() {
		if (saveAsNewButton == null) {
			saveAsNewButton = new JButton();
			saveAsNewButton.setText("SAVE AS NEW");

		}
		saveAsNewButton.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent evt) {
				LeContacts leContacts = null;
				if (validateData()) {

					leContacts = new LeContacts();
					leContacts.setLeId(getLEId(legalEntityField.getText()));
					leContacts.setLeRole(roleField.getText());
					leContacts.setContactCategory(contactTypeField.getText());
					// leContacts.setCode(codeComboBox.getSelectedItem()
					// .toString());
					leContacts.setMailingAddress(addressfield.getText());
					leContacts.setLeFirstName(firstNameField.getText());
					leContacts.setLeLastName(lastNameField.getText());
					leContacts.setCity(cityField.getText());
					leContacts.setState(stateField.getText());
					leContacts.setCountry(countryComboBox.getSelectedItem()
							.toString());
					// leContacts.setCountry(countryField.getText());
					leContacts.setZipCode(zipCodeField.getText());
					leContacts.setPhone(phoneField.getText());
					leContacts.setTelex(telexField.getText());
					leContacts.setFax(faxField.getText());
					leContacts.setSwift(swiftField.getText());
					leContacts.setEmailAddresss(emailIdField.getText());
					leContacts.setComments(commentsField.getText());
					leContacts.setProductType("BOND");
					leContacts.setExternalRef("123");

					try {

						int iSaved = remoteBORef.saveLeContacts(leContacts);

						// String mess = process.newRecordProcess(book);
						if (iSaved > 0) {

							commonUTIL.showAlertMessage("LE Contact saved ");

						/*--	leContactModel.insertRow(
									leContactModel.getRowCount(),
									new Object[] { leContacts.getLeId(),
											getLEName(leContacts.getLeId()),
											leContacts.getLeRole(),
											leContacts.getContactCategory() });
							repaint();*/
							
							leContactDetailsTable.addRow(leContacts);
							repaint();
							

						} else if (iSaved < 0) {

							commonUTIL
									.showAlertMessage("LE Contact is already present");

						} else {

							commonUTIL.showAlertMessage("LE Contact not saved");

						}

					} catch (RemoteException e) {

						e.printStackTrace();

					}

				}

			}

		});

		return saveAsNewButton;
	}

	private JScrollPane getJScrollPane0() {
		if (jScrollPane0 == null) {
			jScrollPane0 = new JScrollPane();
			jScrollPane0.setViewportView(getJTable0());
		}
		return jScrollPane0;
	}

	private JTable getJTable0() {
		if (jTable0 == null) {

			jTable0 = new JTable();

			/*leContactModel = new DefaultTableModel(leContactDetailsTableCol, 0);
			processLEContactDetails(leContactModel);*/

			jTable0.setModel(leContactDetailsTable);
			jTable0.setAutoscrolls(true);
			jTable0.setAutoscrolls(true);

		}

		jTable0.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {

				int rowindex = jTable0.getSelectedRow();

				LeContacts leContact = (LeContacts) leContactDetails
						.get(rowindex);

				/*
				 * Iterator it = leContactDetails.iterator();
				 * 
				 * LeContacts leContact = new LeContacts();
				 * 
				 * while (it.hasNext()) {
				 * 
				 * LeContacts data = (LeContacts) it.next();
				 * 
				 * if (data.getLeId() == le.getId() ) {
				 * 
				 * leContact = data; break; }
				 * 
				 * }
				 */

				legalEntityField.setText(String.valueOf(getLEName(leContact
						.getLeId())));
				roleField.setText(leContact.getLeRole());
				contactTypeField.setText(leContact.getContactCategory());
				codeComboBox.setSelectedIndex(0);
				addressfield.setText(leContact.getMailingAddress());
				firstNameField.setText(leContact.getLeFirstName());
				lastNameField.setText(leContact.getLeLastName());
				cityField.setText(leContact.getCity());
				stateField.setText(leContact.getState());
				countryComboBox.setSelectedIndex(0);
				// countryField.setText(leContact.getCountry());
				zipCodeField.setText(leContact.getZipCode());
				phoneField.setText(leContact.getPhone());
				telexField.setText(leContact.getTelex());
				faxField.setText(leContact.getFax());
				swiftField.setText(leContact.getSwift());
				emailIdField.setText(leContact.getEmailAddresss());
				commentsField.setText(leContact.getComments());
			}
		});

		return jTable0;
	}

	public void processlistchoice(DefaultListModel list, Vector roles) {
		Vector vector;

		if (roles.size() > 0) {
			Iterator it = roles.iterator();
			int i = 0;
			while (it.hasNext()) {

				StartUPData data = (StartUPData) it.next();

				list.addElement(data.getName());

				i++;
			}
		}
	}

	/*
	 * private boolean checkRoleExists(String role) { boolean flag = false; for
	 * (int i = 0; i < listModel.size(); i++) { String lrole = (String)
	 * listModel.elementAt(i); if (lrole.equalsIgnoreCase(role)) { flag = true;
	 * break; } } return flag;
	 * 
	 * }
	 */

	private int getLEId(String leName) {
		int id = 0;
		for (int i = 0; i < legalEntitys.size(); i++) {

			LegalEntity legalEntity = (LegalEntity) legalEntitys.get(i);

			if (leName.equals(legalEntity.getName())) {

				id = legalEntity.getId();
				break;

			}
		}
		return id;

	}

	private void processLEDataCombo(DefaultTableModel model,
			Vector<LegalEntity> legalEntitys) {

		Iterator it = legalEntitys.iterator();
		int i = 0;
		while (it.hasNext()) {
			LegalEntity legalEntity = (LegalEntity) it.next();

			model.insertRow(i,
					new Object[] { legalEntity.getId(), legalEntity.getName() });

			i++;
		}

	}

	/*
	 * private void processRoleDataCombo(DefaultTableModel model, Vector
	 * roleList) {
	 * 
	 * Iterator it = roleList.iterator(); int i = 0; while (it.hasNext()) {
	 * StartUPData roleData = (StartUPData) it.next();
	 * 
	 * model.insertRow(i, new Object[] { roleData.getName() });
	 * 
	 * i++; }
	 * 
	 * }
	 */

	private void processDataCombo(DefaultTableModel model, Vector roleList) {

		Iterator it = roleList.iterator();
		int i = 0;

		while (it.hasNext()) {

			StartUPData data = (StartUPData) it.next();

			model.insertRow(i, new Object[] { data.getName() });

			i++;

		}

	}

	private void processRoleDataCombo(DefaultTableModel model, String role) {

		List<String> roleList = Arrays.asList(role.split("\\s*;\\s*"));

		Iterator it = roleList.iterator();
		int i = 0;
		while (it.hasNext()) {

			String roleData = (String) it.next();

			model.insertRow(i, new Object[] { roleData });

			i++;
		}

	}

	class CustomTextField extends JTextField {

		private int length = 0;

		// Creates a TextField with a fixed length of string input.
		public CustomTextField(int length) {
			super(new FixedLengthPlainDocument(length), "", length);
		}
	}

	class FixedLengthPlainDocument extends PlainDocument {

		private int maxlength;

		// This creates a Plain Document with a maximum length
		// called maxlength.
		FixedLengthPlainDocument(int maxlength) {

			this.maxlength = maxlength;

		}

		// This is the method used to insert a string to a Plain Document.
		public void insertString(int offset, String str, AttributeSet a)
				throws BadLocationException {

			// If the current length of the string
			// + the length of the string about to be entered
			// (through typing or copy and paste)
			// is less than the maximum length passed as an argument..
			// We call the Plain Document method insertString.
			// If it isn't, the string is not entered.

			if (!((getLength() + str.length()) > maxlength)) {
				super.insertString(offset, str, a);

			} else {

				commonUTIL.showAlertMessage("Maximum character allowed is: "
						+ maxlength);

			}

		}

	}

	private boolean validateData() {

		String leName = legalEntityField.getText();
		if (leName.equals("")) {

			commonUTIL.showAlertMessage("Please select Legal Entity");
			return false;

		}

		return true;
	}

	class TableModelUtil extends AbstractTableModel {

		final String[] columnNames;

		Vector<LeContacts> data;
		RemoteReferenceData remoteRef;

		public TableModelUtil(Vector<LeContacts> myData, String col[]) {

			this.columnNames = col;
			this.data = myData;

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
		
		public void addRow(Object value) {

			data.add((LeContacts) value);
			this.fireTableDataChanged();

		}

		public void delRow(int row) {
			if (row != -1) {
				data.remove(row);
				this.fireTableDataChanged();
			}

		}
		
		public void setValueAt(Object value, int row, int col) {
			
			System.out.println("Setting value at " + row + "," + col + " to "
					+ value + " (an instance of " + value.getClass() + ")");
			
			if (value instanceof CurrencySplitConfig) {
				
				data.set(row, (LeContacts) value);
				this.fireTableDataChanged();
				System.out.println("New value of data:");
				
			}

		}
		
		public void udpateValueAt(Object value, int row, int col) {

			data.set(row, (LeContacts) value);
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
		
		public Object getValueAt(int row, int col) {

			Object value = null;

			LeContacts currSplit = (LeContacts) data.get(row);

			switch (col) {

			case 0:
				value = currSplit.getLeId();
				break;

			case 1:
				value = getLEName(currSplit.getLeId());
				break;

			case 2:
				value = currSplit.getLeRole();
				break;
				
			case 3:
				value = currSplit.getContactCategory();
				break;

			}

			return value;

		}

	}
	

	private String getLEName(int leId) {
		String leName = "";
		for (int i = 0; i < legalEntitys.size(); i++) {

			LegalEntity legalEntity = (LegalEntity) legalEntitys.get(i);

			if (leId == legalEntity.getId()) {

				leName = legalEntity.getName();
				break;

			}
		}
		return leName;

	}

/*	private void processLEContactDetails(DefaultTableModel model) {
		// TODO Auto-generated method stub

		Iterator it = leContactDetails.iterator();
		int i = 0;
		while (it.hasNext()) {

			LeContacts leContacts = (LeContacts) it.next();

			model.insertRow(i, new Object[] { leContacts.getLeId(),
					getLEName(leContacts.getLeId()), leContacts.getLeRole(),
					leContacts.getContactCategory() });

			i++;
		}
	}*/

	private void processJComboBox(javax.swing.DefaultComboBoxModel combodata,
			Vector<String> countryVec, Hashtable id) {

		Iterator it = countryVec.iterator();
		int i = 0;
		while (it.hasNext()) {

			StartUPData country = (StartUPData) it.next();
			combodata.insertElementAt(country.getName(), i);
			id.put(i, country.getName());
			i++;
		}

	}

}

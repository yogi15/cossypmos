package apps.window.referencewindow;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.dyno.visual.swing.layouts.Bilateral;
import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;
import org.dyno.visual.swing.layouts.Trailing;

import util.RemoteServiceUtil;
import util.commonUTIL;
import apps.window.utilwindow.JDialogBoxForChoice;
import beans.Book;
import beans.Country;
import beans.CurrencySplitConfig;
import beans.LegalEntity;
import beans.StartUPData;
import dsServices.RemoteReferenceData;
import dsServices.ServerConnectionUtil;

//VS4E -- DO NOT REMOVE THIS LINE!
public class CounterPartyWindow extends JPanel {

	private static final long serialVersionUID = 1L;

	private JLabel idLabel;
	private JTextField idTextField;
	private JLabel roleLabel;
	private JTextArea jComboBox0;
	private JTextField nameTextField;
	private JLabel aliasLabel;
	private JLabel nameLabel;
	private JTextField aliasTextField;
	private JTable attributesTable;
	private JScrollPane attributesScrollPane;
	private JPanel jPanel0;
	private JPanel jPanel1;
	private JButton saveButton;
	private JButton saveAsNewButton;
	private JButton deleteButton;
	private JButton searchButton;
	private JPanel jPanel2;
	private JTable detailsTable;
	JScrollPane jScrollPaneT0;
	Vector les = new Vector();
	private JScrollPane jScrollPane1;
	public static ServerConnectionUtil de = null;
	DefaultTableModel attributemodel = null;
	RemoteReferenceData remoteBORef;
	public DefaultTableModel model = null;
	DefaultListModel rolesList = new DefaultListModel();
	String col[] = { "CpId", "Name ", "Role", "COntactType" };
	String co11[] = { "AttributeName", "AttributeValue" };
	Vector roles = null;
	Vector attributes = null;
	Vector countryVec = null;
	private JButton roleListButton;
	private JList roleList;
	DefaultListModel listModel = new DefaultListModel();
	Vector<LegalEntity> legalEntitys = new Vector<LegalEntity>();
	TableModelUtil modelt = null;
	private JScrollPane jScrollPane3;
	javax.swing.DefaultComboBoxModel countryVal = new javax.swing.DefaultComboBoxModel();

	public CounterPartyWindow() {
		init();
		initComponents();
	}

	private void init() {
		// TODO Auto-generated method stub

		// de =ServerConnectionUtil.connect("localhost",
		// 1099,commonUTIL.getServerIP() );

		remoteBORef = (RemoteReferenceData) RemoteServiceUtil
				.getRemoteReferenceDataService();

		try {
			// remoteBORef = (RemoteReferenceData)
			// de.getRMIService("ReferenceData");
			roles = (Vector) remoteBORef.getStartUPData("Roles");
			attributes = (Vector) remoteBORef.getStartUPData("LEAttributes");
			legalEntitys = (Vector) remoteBORef.selectAllLs();
			modelt = new TableModelUtil(legalEntitys, col);
			countryVec = (Vector) remoteBORef.getStartUPData("Country");
			// JComboBox comboBox14 = new JComboBox(
			// convertVectortoSringArray(countryVec) );
			// System.out.println(remoteBORef);
			// processTableData()

		} catch (RemoteException e) {

			e.printStackTrace();

		}
	}

	private void initComponents() {
		setBorder(new LineBorder(Color.black, 1, false));
		setLayout(new GroupLayout());
		add(getJPanel2(), new Constraints(new Bilateral(12, 16, 860),
				new Trailing(15, 10, 505)));
		add(getJPanel1(), new Constraints(new Bilateral(12, 16, 860),
				new Trailing(71, 280, 10, 10)));
		add(getJPanel0(), new Constraints(new Bilateral(10, 14, 862),
				new Bilateral(5, 363, 214)));
		setSize(890, 658);
	}

	private JButton getJButton0() {
		if (newButton == null) {
			newButton = new JButton();
			newButton.setText("New");
			commonUTIL.setLabelFont(newButton);
		}
		newButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {

				clearData();
			}

		});

		return newButton;
	}

	private JComboBox getCountryComboBox() {
		if (countryComboBox == null) {
			countryComboBox = new JComboBox();

			processCountry(countryVal);
			countryComboBox.setModel(countryVal);
		}
		return countryComboBox;
	}

	private JLabel getCountryLabel() {
		if (countryLabel == null) {
			countryLabel = new JLabel();
			countryLabel.setText("Country");
			commonUTIL.setLabelFont(countryLabel);

		}
		return countryLabel;
	}

	private JScrollPane getJScrollPane3() {
		if (jScrollPane3 == null) {
			jScrollPane3 = new JScrollPane();
			jScrollPane3.setViewportView(getRoleList());
		}
		return jScrollPane3;
	}

	private JList getRoleList() {
		if (roleList == null) {
			roleList = new JList();

			roleList.setModel(listModel);
		}
		return roleList;
	}

	private JButton getRoleListButton() {
		if (roleListButton == null) {
			roleListButton = new JButton();
			roleListButton.setText("...");
		}

		processlistchoice(rolesList, roles);

		final JDialogBoxForChoice choice12 = new JDialogBoxForChoice(rolesList);
		roleListButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				choice12.jList3.setModel(rolesList);
				choice12.setLocationRelativeTo(choice12);
				// choice12.setSize(200,200);
				choice12.setVisible(true);

			}

		});
		choice12.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				// System.out.println("Window closing");

				String ss = "";
				Object obj[] = choice12.getObj();
				for (int i = 0; i < obj.length; i++) {
					ss = (String) obj[i];
					if (!checkRoleExists(ss)) {
						listModel.addElement(ss);
					}

				}

			}
		});

		return roleListButton;
	}

	private boolean checkRoleExists(String role) {
		boolean flag = false;
		for (int i = 0; i < listModel.size(); i++) {
			String lrole = (String) listModel.elementAt(i);
			if (lrole.equalsIgnoreCase(role)) {
				flag = true;
				break;
			}
		}
		return flag;

	}

	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getDetailsTable());
		}
		return jScrollPane1;
	}

	private JTable getDetailsTable() {
		if (detailsTable == null) {
			detailsTable = new JTable();

			detailsTable.setModel(modelt);
			detailsTable.setAutoscrolls(true);
			// detailsTable.setAutoCreateRowSorter(true);
			detailsTable.setAutoscrolls(true);

		}
		detailsTable.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {

				int rowindex = detailsTable.getSelectedRow();

				LegalEntity le = (LegalEntity) legalEntitys.get(rowindex);

				// jTextField4.setText(new Integer(book.getPrice()).toString());

				nameTextField.setText(le.getName());
				idTextField.setText(new Integer(le.getId()).toString());
				aliasTextField.setText(le.getAlias());
				setAttributesValues(le.getAttributes());
				roleList.removeAll();
				listModel.clear();
				setRoles(le.getRole());
				countryComboBox.setSelectedItem(le.getCountry());
			}

		});
		return detailsTable;
	}

	private void setAttributesValues(String values) {
		if (values != null && values.length() > 0) {
			String atttoken[] = values.trim().split(";");

			for (int i = 0; i < atttoken.length; i++) {
				String att = (String) atttoken[i];

				if (att.contains("=")) {
					String attvalue = att.substring(att.indexOf('=') + 1,
							att.length());
					String attnameName = att.substring(0, att.indexOf('='));
					for (int r = 0; r < attributemodel.getRowCount(); r++) {
						String atteName = (String) attributemodel.getValueAt(r,
								0);
						if (atteName.equalsIgnoreCase(attnameName)) {
							attributemodel.setValueAt(attvalue, r, 1);
						}

					}
				}
			}
		} else {
			for (int r = 0; r < attributemodel.getRowCount(); r++) {

				attributemodel.setValueAt("", r, 1);
			}
		}

	}

	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setLayout(new GroupLayout());
			jPanel2.add(getSearchButton(), new Constraints(new Leading(472, 10,
					10), new Leading(12, 12, 12)));
			jPanel2.add(getContactButton(), new Constraints(new Leading(574,
					10, 10), new Leading(12, 12, 12)));
			jPanel2.add(getDeleteButton(), new Constraints(new Leading(370, 10,
					10), new Leading(12, 12, 12)));
			jPanel2.add(getSaveButton(), new Constraints(new Leading(290, 10,
					10), new Leading(12, 12, 12)));
			jPanel2.add(getSaveAsNewButton(), new Constraints(new Leading(157,
					12, 12), new Leading(12, 12, 12)));
			jPanel2.add(getJButton0(), new Constraints(new Leading(66, 67, 10,
					10), new Leading(12, 12, 12)));
		}
		return jPanel2;
	}

	JButton contactButton;
	private JLabel countryLabel;
	private JComboBox countryComboBox;
	private JButton newButton;

	private static final String PREFERRED_LOOK_AND_FEEL = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";

	private Component getContactButton() {

		// TODO Auto-generated method stub
		if (contactButton == null) {
			contactButton = new JButton();
			contactButton.setText("Contact");
			commonUTIL.setLabelFont(contactButton);
		}
		contactButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				if (detailsTable.getSelectedRow() == -1) {
					commonUTIL.showAlertMessage("Select LegalEntity");
					return;
				}
				
				int rowindex = detailsTable.getSelectedRow();
				LegalEntity le = legalEntitys.get(rowindex);
				
				JFrame leContact = new JFrame();
				LEcontactWindow contact = new LEcontactWindow(le);
				
				leContact.add(contact);
				leContact.setVisible(true);
				leContact.setSize(960, 650);
				leContact.setResizable(false);

			}

		});
		return contactButton;
	}

	private JButton getSearchButton() {
		if (searchButton == null) {
			searchButton = new JButton();
			searchButton.setText("Search");
			commonUTIL.setLabelFont(searchButton);
		}
		return searchButton;
	}

	private JButton getDeleteButton() {
		if (deleteButton == null) {
			deleteButton = new JButton();
			deleteButton.setText("Delete");
			commonUTIL.setLabelFont(deleteButton);
		}

		deleteButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {

				int rowindex = detailsTable.getSelectedRow();
				LegalEntity le = legalEntitys.get(rowindex);

				boolean isDeleted = false;

				try {

					isDeleted = remoteBORef.deleteLE(le);

				} catch (RemoteException e) {

					e.printStackTrace();

				}

				if (isDeleted) {

					clearData();
					modelt.delRow(rowindex);
					commonUTIL
					.showAlertMessage("Counter Party Deleted");

				} else {

					// log
					commonUTIL
							.showAlertMessage("There was an error while deleting the Counter Party");
				}

				detailsTable.repaint();
				clearData();

			}

		});
		return deleteButton;
	}

	private JButton getSaveAsNewButton() {
		if (saveAsNewButton == null) {
			saveAsNewButton = new JButton();
			saveAsNewButton.setText("Save As New");
			commonUTIL.setLabelFont(saveAsNewButton);
		}

		saveAsNewButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {

				LegalEntity le = null;
				boolean isValid = validateLegalEntity();

				if (isValid) {

					le = new LegalEntity();
					le.setName(nameTextField.getText());
					le.setRole(getRoles());
					le.setAttributes(getAttributes());
					le.setAlias(aliasTextField.getText());
					le.setCountry(countryComboBox.getSelectedItem().toString());
					
					try {

						int id = remoteBORef.saveLe(le);
						
						if (id > 0) {

							//TableModel mo = detailsTable.getModel();
							le.setId(id);
							modelt.addRow(le);
							repaint();
							commonUTIL
									.showAlertMessage("Counter Party  saved with id "
											+ le.getId());
						
						} else if (id == -1) {

							commonUTIL.showAlertMessage("Counter Party already exists ");

						} else if (id == -2) {
							
							commonUTIL.showAlertMessage("Counter Party attributes not saved ");
							
						} else {
							
							commonUTIL.showAlertMessage("Counter Party not saved ");
							
						}

					} catch (RemoteException e) {

						e.printStackTrace();

					}

				} 
				
			}

		});

		return saveAsNewButton;

	}

	private JButton getSaveButton() {
		if (saveButton == null) {
			saveButton = new JButton();
			saveButton.setText("Save");
			commonUTIL.setLabelFont(saveButton);
		}
		saveButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {

				try {
					LegalEntity le = new LegalEntity();

					le.setId(checkNumberFormat(" ID ", idTextField.getText()
							.trim()));

					le.setName(nameTextField.getText());
					le.setRole(getRoles());
					le.setAttributes(getAttributes());
					le.setAlias(aliasTextField.getText());
					le.setCountry(countryComboBox.getSelectedItem().toString());

					le.setStatus("");

					boolean falg = false;

					falg = remoteBORef.updateLe(le);

					if (!falg) {

						commonUTIL.showAlertMessage("edit problem");

					} else {

						int rowindex = detailsTable.getSelectedRow();
						modelt.udpateValueAt(le, rowindex, 1);

						commonUTIL.showAlertMessage("Legal Entity Updated ");

						detailsTable.repaint();

					}

				} catch (RemoteException e) {

					e.printStackTrace();

				}
			}

		});

		return saveButton;

	}

	private boolean validateLegalEntity() {

		boolean isValid = true;

		if (nameTextField.getText().equals("")) {
			
			isValid = false;
			commonUTIL.showAlertMessage("Please enter Name");
			
		}
		
		if (getRoles().equals("")) {
			
			isValid = false;
			commonUTIL.showAlertMessage("Please select Roles");
			
		}
		
		if ( aliasTextField.getText().equals("")) {
			
			isValid = false;
			commonUTIL.showAlertMessage("Please enter Alias");
			
		}
		
		if (countryComboBox.getSelectedItem().toString().equals("")) {
			
			isValid = false;
			commonUTIL.showAlertMessage("Please select Country");
			
		}
		return isValid;

	}

	private String getAttributes() {
		String value = "";
		for (int i = 0; i < attributesTable.getRowCount(); i++) {
			String attributeN = attributesTable.getValueAt(i, 0).toString();
			String attributeV = attributesTable.getValueAt(i, 1).toString();
			if (!((attributeV == null) || attributeV.isEmpty() || attributeV.equals(""))) {
				value = value.trim() + attributeN + "=" + attributeV + ";";
				// value = ValidateData(attributeN,value);

			}
		}
		return value;
	}

	private String getRoles() {

		String value = "";

		if (listModel.getSize() < 1) {

			value = (String) listModel.getElementAt(0);
			return value;

		}

		for (int i = 0; i < listModel.getSize(); i++) {

			String newRole = (String) listModel.getElementAt(i);
			value = value.trim() + newRole + ";";

		}

		return value.substring(0, value.length() - 1);

	}

	private void setRoles(String roles) {
		String value = "";
		String rolest[] = roles.trim().split(";");
		if (rolest.length == 0)
			listModel.addElement(roles);
		for (int i = 0; i < rolest.length; i++) {
			String att = (String) rolest[i];
			listModel.add(i, att);
			// value = ValidateData(attributeN,value);

		}

	}

	private void processCountry(javax.swing.DefaultComboBoxModel combodata) {

		for (int i = 0; i < countryVec.size(); i++) {

			StartUPData country = (StartUPData) countryVec.get(i);

			combodata.insertElementAt(country.getName(), i);

		}

	}

	private void ValidateData(String attributeN, String value) {
		// TODO Auto-generated method stub

		if (attributeN.startsWith("Phone")) {
			if (!value.isEmpty()) {
				if (!commonUTIL.validatePhoneNumberField(value))
					commonUTIL.showAlertMessage("Enter Valid Number");

			}
		}
	}

	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setBorder(BorderFactory.createEtchedBorder(
					EtchedBorder.LOWERED, null, null));
			jPanel1.setLayout(new GroupLayout());
			jPanel1.add(getJScrollPane1(), new Constraints(new Leading(8, 843,
					10, 10), new Leading(6, 211, 10, 10)));
		}
		return jPanel1;
	}

	private JPanel getJPanel0() {
		if (jPanel0 == null) {
			jPanel0 = new JPanel();
			jPanel0.setBorder(BorderFactory.createEtchedBorder(
					EtchedBorder.LOWERED, null, null));
			jPanel0.setLayout(new GroupLayout());
			jPanel0.add(getNameTextField(), new Constraints(new Leading(90,
					266, 33, 464), new Leading(84, 28, 85, 88)));
			jPanel0.add(getAliasTextField(), new Constraints(new Leading(90,
					72, 33, 464), new Leading(47, 28, 85, 88)));
			jPanel0.add(getIdTextField(), new Constraints(new Leading(90, 63,
					10, 10), new Leading(8, 28, 85, 88)));
			jPanel0.add(getAttributesScrollPane(), new Constraints(
					new Bilateral(411, 5, 444), new Leading(6, 192, 12, 12)));
			jPanel0.add(getNameLabel(), new Constraints(
					new Leading(17, 40, 469), new Leading(97, 39, 48)));
			jPanel0.add(getAliasLabel(), new Constraints(new Leading(17, 33,
					40, 469), new Leading(60, 39, 48)));
			jPanel0.add(getIdLabel(), new Constraints(new Leading(17, 28, 40,
					469), new Leading(15, 21, 39, 48)));
			jPanel0.add(getRoleLabel(), new Constraints(new Leading(17, 48, 12,
					12), new Trailing(46, 124, 124)));
			jPanel0.add(getRoleListButton(), new Constraints(new Leading(310,
					40, 12, 12), new Leading(216, 12, 12)));
			jPanel0.add(getJScrollPane3(), new Constraints(new Leading(90, 200,
					10, 10), new Leading(120, 119, 10, 10)));
			jPanel0.add(getCountryComboBox(), new Constraints(new Leading(90,
					116, 10, 10), new Trailing(10, 154, 250)));
			jPanel0.add(getCountryLabel(), new Constraints(new Leading(17, 10,
					10), new Trailing(7, 23, 123, 123)));
		}
		return jPanel0;
	}

	private JScrollPane getAttributesScrollPane() {
		if (attributesScrollPane == null) {
			attributesScrollPane = new JScrollPane();
			attributesScrollPane.setViewportView(getAttributesTable());
		}
		return attributesScrollPane;
	}

	private JTable getAttributesTable() {
		if (attributesTable == null) {
			attributesTable = new JTable();
			attributemodel = new DefaultTableModel(co11, 0);
			attributesTable.setModel(attributemodel);
			attributesTable.setAutoscrolls(true);
			attributesTable.setAutoCreateRowSorter(true);
			attributesTable.setAutoscrolls(true);
			// attributesTable.seta
			if (attributes != null) {
				for (int i = 0; i < attributes.size(); i++) {
					StartUPData attributs = (StartUPData) attributes.get(i);
					attributemodel.insertRow(i,
							new Object[] { attributs.getName(), "" });
				}
			}

		}
		return attributesTable;
	}

	private JTextField getAliasTextField() {
		if (aliasTextField == null) {
			aliasTextField = new JTextField();
			// aliasTextField.setText("aliasTextField");
		}
		return aliasTextField;
	}

	private JLabel getNameLabel() {
		if (nameLabel == null) {
			nameLabel = new JLabel();
			nameLabel.setText("Name");
			commonUTIL.setLabelFont(nameLabel);
		}
		return nameLabel;
	}

	private JLabel getAliasLabel() {
		if (aliasLabel == null) {
			aliasLabel = new JLabel();
			aliasLabel.setText("Alias");
			commonUTIL.setLabelFont(aliasLabel);

		}
		return aliasLabel;
	}

	private JTextField getNameTextField() {
		if (nameTextField == null) {
			nameTextField = new JTextField();
			// nameTextField.setText("nameTextField");
		}
		return nameTextField;
	}

	private JLabel getRoleLabel() {
		if (roleLabel == null) {
			roleLabel = new JLabel();
			roleLabel.setText("Role");
			commonUTIL.setLabelFont(roleLabel);
		}
		return roleLabel;
	}

	private JTextField getIdTextField() {
		if (idTextField == null) {
			idTextField = new JTextField();
			idTextField.setEditable(false);
			idTextField.setText("0");
		}
		return idTextField;
	}

	private JLabel getIdLabel() {
		if (idLabel == null) {
			idLabel = new JLabel();
			idLabel.setText("ID");
			commonUTIL.setLabelFont(idLabel);
		}
		return idLabel;
	}

	public static int checkNumberFormat(String fieldName, String s) {
		try {
			Integer ii = new Integer(s);
			return ii.intValue();
		} catch (Exception e) {
			commonUTIL.showAlertMessage(" enter number ");
			return 0;
		}
	}

	private void clearData() {
		roleList.removeAll();
		aliasTextField.setText("");
		nameTextField.setText("");
		idTextField.setText("");
		roleList.removeAll();
		countryComboBox.setSelectedIndex(0);
		listModel.removeAllElements();
		clearTable(attributesTable);
		repaint();
		// processTableData(model);
	}

	public static void clearTable(final JTable table) {
		for (int i = 0; i < table.getRowCount(); i++) {
			table.setValueAt("", i, 1);
		}
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

	class TableModelUtil extends AbstractTableModel {

		final String[] columnNames;

		Vector<LegalEntity> data;
		RemoteReferenceData remoteRef;
		Hashtable<Integer, Book> books;

		public TableModelUtil(Vector<LegalEntity> myData, String col[]) {
			this.columnNames = col;
			this.data = myData;
			this.books = books;
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

			LegalEntity currSplit = (LegalEntity) data.get(row);

			switch (col) {
			case 0:
				value = currSplit.getId();
				break;
			case 1:
				value = currSplit.getName();
				break;
			case 2:
				value = currSplit.getRole();
				break;
			case 3:
				value = currSplit.getstatus();
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
			if (value instanceof CurrencySplitConfig) {
				data.set(row, (LegalEntity) value);
				this.fireTableDataChanged();
				System.out.println("New value of data:");
			}

		}

		public void addRow(Object value) {

			data.add((LegalEntity) value);
			this.fireTableDataChanged();

		}

		public void delRow(int row) {
			if (row != -1) {
				data.remove(row);
				this.fireTableDataChanged();
			}

		}

		public void udpateValueAt(Object value, int row, int col) {

			data.set(row, (LegalEntity) value);
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

}

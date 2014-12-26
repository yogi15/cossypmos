package apps.window.referencewindow;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;

import util.commonUTIL;
import beans.Book;
import beans.Folder;
import beans.LegalEntity;
import beans.StartUPData;
import dsServices.RemoteReferenceData;
import dsServices.ServerConnectionUtil;

//VS4E -- DO NOT REMOVE THIS LINE!
public class BookWindow extends JPanel {
	public static ServerConnectionUtil de = null;
	RemoteReferenceData remoteBORef;
	private static final long serialVersionUID = 1L;
	private JLabel bookIdLabel;
	private JLabel bookNameLabel;
	private JLabel leLabel;
	private JTextField bookIdTextField;
	private JTextField bookNameTextField;
	private JComboBox leComboBox;
	private JLabel folderLabel;
	private JComboBox folderComboBox;
	private JTable attributesTable;
	private JScrollPane jScrollPane0;
	private JTable detailsTable;
	private JScrollPane jScrollPane1;
	private JLabel jLabel5;
	private JButton saveAsNewButton;
	private JButton saveButton;
	private JButton deleteButton;
	private JButton clearButton;
	Vector<Book> data = null;
	DefaultTableModel attributemodel = null;
	Vector attributes = null;
	String co11[] = { "AttributeName", "AttributeValue" };
	javax.swing.DefaultComboBoxModel counterPartyCom = new javax.swing.DefaultComboBoxModel();
	Hashtable counterPartyID = new Hashtable();
	Hashtable folderID = new Hashtable();
	javax.swing.DefaultComboBoxModel folderData = new javax.swing.DefaultComboBoxModel();
	TableModelBookUtil model;
	String bookcol[] = { "BookNo", "Book Name ", "Legal Entity", "Folder Name" };
	String attr[] = { "AttributeName", "AttributeValue" };
	private static final String PREFERRED_LOOK_AND_FEEL = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
	public BookWindow() {
		initData();
		initComponents();
	}

	private void initComponents() {
		setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
		setLayout(new GroupLayout());
		add(getBookIdLabel(), new Constraints(new Leading(37, 99, 10, 10), new Leading(72, 10, 10)));
		add(getBookNameLabel(), new Constraints(new Leading(37, 99, 12, 12), new Leading(102, 10, 10)));
		add(getLeLabel(), new Constraints(new Leading(37, 99, 12, 12), new Leading(134, 10, 10)));
		add(getBookIdTextField(), new Constraints(new Leading(144, 45, 10, 10), new Leading(68, 12, 12)));
		add(getBookNameTextField(), new Constraints(new Leading(144, 147, 12, 12), new Leading(98, 12, 12)));
		add(getLeComboBox(), new Constraints(new Leading(146, 142, 12, 12), new Leading(130, 12, 12)));
		add(getFolderLabel(), new Constraints(new Leading(37, 99, 12, 12), new Leading(168, 12, 12)));
		add(getFolderComboBox(), new Constraints(new Leading(146, 142, 12, 12), new Leading(165, 10, 10)));
		add(getJScrollPane0(), new Constraints(new Leading(440, 277, 10, 10), new Leading(61, 150, 10, 10)));
		add(getJLabel5(), new Constraints(new Leading(443, 120, 10, 10), new Leading(34, 12, 12)));
		add(getSaveAsNewButton(), new Constraints(new Leading(80, 10, 10), new Leading(229, 20, 12, 12)));
		add(getSaveButton(), new Constraints(new Leading(204, 84, 12, 12), new Leading(229, 20, 12, 12)));
		add(getDeleteButton(), new Constraints(new Leading(306, 84, 12, 12), new Leading(229, 20, 12, 12)));
		add(getClearButton(), new Constraints(new Leading(406, 84, 10, 10), new Leading(229, 20, 10, 10)));
		add(getJScrollPane1(), new Constraints(new Leading(15, 1199, 10, 10), new Leading(262, 191, 10, 10)));
		setSize(1222, 469);
	}

	private JButton getClearButton() {
		if (clearButton == null) {
			clearButton = new JButton();
			clearButton.setText("Clear");
		}
		clearButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				clearData();

			}
		});
		return clearButton;
	}

	private JButton getDeleteButton() {
		if (deleteButton == null) {
			deleteButton = new JButton();
			deleteButton.setText("Delete");
		}
		deleteButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int rowIndex = detailsTable.getSelectedRow();
				// feesData.remove(rowIndex);

				Book book = data.get(rowIndex);
				boolean isDeleted = false;

				try {

					isDeleted = remoteBORef.deleteBook(book);

				} catch (RemoteException e1) {

					e1.printStackTrace();

				}

				// String mess = process.newRecordProcess(book);
				if (!isDeleted) {

					commonUTIL.showAlertMessage("Book could not be deleted ");

				} else {

					commonUTIL.showAlertMessage("Book deleted");

					model.delRow(rowIndex);
					clearData();

				}

			}
		});
		return deleteButton;
	}

	private JButton getSaveButton() {
		if (saveButton == null) {
			saveButton = new JButton();
			saveButton.setText("Save");
		}
		saveButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String name = bookIdTextField.getText().trim();
				if (validateDetails()) {
					
					Book book = new Book();
					
					fillBookdata(book);
					
					try {
						boolean flag = remoteBORef.updateBook(book);

						if (flag) {

							commonUTIL.showAlertMessage("Book updated");
							int rowColNo = data.indexOf(book.getFolderID()) + 1;
							model.setValueAt(book, rowColNo, rowColNo);

						}
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}

			}

		});
		return saveButton;
	}

	private JButton getSaveAsNewButton() {
		if (saveAsNewButton == null) {
			saveAsNewButton = new JButton();
			saveAsNewButton.setText("Save As New");
		}
		saveAsNewButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				if (validateDetails()) {
					Book book = new Book();
					fillBookdata(book);
					try {
						book.setBookno(0);
						int i = remoteBORef.saveBook(book);

						if (i > 0) {

							commonUTIL.showAlertMessage("New Book saved");
							book.setBookno(i);
							model.addRow(book);

						} else if (i < 0) {

							commonUTIL.showAlertMessage("Book already exists");

						}

					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}

			}

		});
		return saveAsNewButton;
	}

	private boolean validateDetails() {

		boolean flag = true;

		if (bookNameTextField.getText().length() == 0) {

			commonUTIL.showAlertMessage("Please enter Book Name");
			flag = false;

		} else if (leComboBox.getSelectedIndex() == -1) {

			commonUTIL.showAlertMessage("Please select Legal Entity");
			flag = false;

		} else if (folderComboBox.getSelectedIndex() == -1) {

			commonUTIL.showAlertMessage("Please select Folder");
			flag = false;

		}

		return flag;

	}
	
	private String getAttributes() {
		String value = "";
		int rowCount = attributesTable.getRowCount();
		
		for (int i = 0; i < rowCount; i++) {
			String attributeN = attributesTable.getValueAt(i, 0).toString();
			String attributeV = attributesTable.getValueAt(i, 1).toString();
			if (!((attributeV == null) || attributeV.isEmpty() || attributeV.equals(""))) {
				value = value.trim() + attributeN + "=" + attributeV + ";";
				// value = ValidateData(attributeN,value);

			}
		}
		return value;
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
	
	private JLabel getJLabel5() {
		if (jLabel5 == null) {
			jLabel5 = new JLabel();
			jLabel5.setFont(new Font("Dialog", Font.BOLD, 14));
			jLabel5.setText("Attributes");
		}
		return jLabel5;
	}

	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1
					.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			jScrollPane1
					.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

			jScrollPane1.setViewportView(getDetailsTable());
		}
		return jScrollPane1;
	}

	private JTable getDetailsTable() {
		if (detailsTable == null) {
			detailsTable = new JTable();
			model = new TableModelBookUtil(data, bookcol, remoteBORef,
					counterPartyID, folderID);
			detailsTable.setModel(model);
		}
		detailsTable.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				Book book = (Book) data.get(detailsTable.getSelectedRow());
				bookNameTextField.setText(book.getBook_name());

				bookIdTextField.setText(new Integer(book.getBookno())
						.toString());
				Enumeration leg = counterPartyID.elements();
				int l = 0;
				while (leg.hasMoreElements()) {
					LegalEntity le = (LegalEntity) leg.nextElement();
					if (le.getId() == book.getLe_id()) {
						counterPartyCom.setSelectedItem(le.getName());
						break;
					}
					l++;
				}
				Enumeration fenu = folderID.elements();
				int f = 0;
				while (fenu.hasMoreElements()) {
					Folder le = (Folder) fenu.nextElement();
					if (le.getId() == book.getFolderID()) {
						folderData.setSelectedItem(le.getFolder_name());
						break;
					}
					f++;
				}
				setAttributesValues(book.getAttributes());

			}

		});

		return detailsTable;
	}

	private JScrollPane getJScrollPane0() {
		if (jScrollPane0 == null) {
			jScrollPane0 = new JScrollPane();
			jScrollPane0.setViewportView(getAttributesTable());
		}
		return jScrollPane0;
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

	private JComboBox getFolderComboBox() {
		if (folderComboBox == null) {
			folderComboBox = new JComboBox();
			folderComboBox.setModel(folderData);
			folderComboBox.setDoubleBuffered(false);
			folderComboBox.setBorder(null);
		}
		return folderComboBox;
	}

	private JLabel getFolderLabel() {
		if (folderLabel == null) {
			folderLabel = new JLabel();
			folderLabel.setText("Folder ");
		}
		return folderLabel;
	}

	private JComboBox getLeComboBox() {
		if (leComboBox == null) {
			leComboBox = new JComboBox();
			leComboBox.setModel(counterPartyCom);
			leComboBox.setDoubleBuffered(false);
			leComboBox.setBorder(null);
		}
		return leComboBox;
	}

	private JTextField getBookNameTextField() {
		if (bookNameTextField == null) {
			bookNameTextField = new JTextField();
			bookNameTextField.setText("   ");
		}
		return bookNameTextField;
	}

	private JTextField getBookIdTextField() {
		if (bookIdTextField == null) {
			bookIdTextField = new JTextField();
			bookIdTextField.setText("0");
			bookIdTextField.setEditable(false);
		}
		return bookIdTextField;
	}

	private JLabel getLeLabel() {
		if (leLabel == null) {
			leLabel = new JLabel();
			leLabel.setText("Legal Entity");
		}
		return leLabel;
	}

	private JLabel getBookNameLabel() {
		if (bookNameLabel == null) {
			bookNameLabel = new JLabel();
			bookNameLabel.setText("Book Name");
		}
		return bookNameLabel;
	}

	private JLabel getBookIdLabel() {
		if (bookIdLabel == null) {
			bookIdLabel = new JLabel();
			bookIdLabel.setText("Book ID");
			commonUTIL.setLabelFont(bookIdLabel);
		}
		return bookIdLabel;
	}

	public void initData() {
		de = ServerConnectionUtil.connect("localhost", 1099,
				commonUTIL.getServerIP());
		try {
			remoteBORef = (RemoteReferenceData) de
					.getRMIService("ReferenceData");
			data = (Vector) remoteBORef.selectALLBooks();
			
			attributes = (Vector) remoteBORef.getStartUPData("BookAttributes");
			
			processLEDataCombo1(counterPartyCom, counterPartyID);
			processFolderDataCombo1(folderData, folderID);

		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	private void fillBookdata(Book book) {
		
		book.setBook_name( bookNameTextField.getText());
		book.setBookno(Integer.parseInt( bookIdTextField.getText()) );
		book.setLe_id(returnCounteryPartyID( leComboBox.getSelectedIndex(), counterPartyID) );
		book.setFolderID(returnFolderID( folderComboBox.getSelectedIndex(), folderID) );
		book.setAttributes(getAttributes());

	}

	private int returnCounteryPartyID(int indexid, Hashtable h) {

		if (!h.containsKey(indexid)) {

			return -1;

		} else {
			
			LegalEntity le = (LegalEntity) h.get(indexid);
			return le.getId();
			
		}

	}

	private int returnFolderID(int indexid, Hashtable h) {

		if (!h.containsKey(indexid)) {

			return -1;

		} else {
			
			Folder le = (Folder) h.get(indexid);
			return le.getId();
			
		}

	}

	private void processLEDataCombo1(javax.swing.DefaultComboBoxModel combodata, Hashtable ids) {
		
		Vector ledata;
		
		try {
			
			String roleType = " role like '%PO%' ";
			ledata = (Vector) remoteBORef.selectLEonWhereClause(roleType);

			Iterator it = ledata.iterator();
			int p = 0;
			
			while (it.hasNext()) {

				LegalEntity le = (LegalEntity) it.next();

				combodata.insertElementAt(le.getName(), p);
				ids.put(p, le);
				// System.out.println(" i == "+ p + "  == "+ le.getName() +
				// " id " + le.getId());
				p++;
			
			}
			// System.out.println("pppppppppppp");

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void processFolderDataCombo1(javax.swing.DefaultComboBoxModel combodata, Hashtable ids) {
		
		Vector folderData;
		
		try {

			folderData = (Vector) remoteBORef.selectALLFolders();

			Iterator it = folderData.iterator();
			int p = 0;
		
			while (it.hasNext()) {

				Folder le = (Folder) it.next();

				combodata.insertElementAt(le.getFolder_name(), p);
				ids.put(p, le);
				// System.out.println(" i == "+ p + "  == "+ le.getName() +
				// " id " + le.getId());
				p++;
			
			}

		} catch (RemoteException e) {
			
			e.printStackTrace();
			
		}

	}

	public void setLEComobox(int i) {
		leComboBox.setSelectedIndex(i);
	}

	private void clearData() {

		bookIdTextField.setText("0");
		bookNameTextField.setText("");
		leComboBox.setSelectedIndex(0);
		folderComboBox.setSelectedIndex(0);
		
		int rowCount = attributesTable.getRowCount();
		
		for (int i = 0; i < rowCount; i++) {
			
			attributesTable.setValueAt("", i, 1);

		}
	}

class TableModelBookUtil extends AbstractTableModel {

	final String[] columnNames;

	final Vector<Book> data;
	RemoteReferenceData remoteRF;
	Hashtable counterParty;
	Hashtable folder;

	public TableModelBookUtil(Vector<Book> myData, String col[],
			RemoteReferenceData remoteRf, Hashtable counterParty,
			Hashtable folder) {
		this.columnNames = col;
		this.data = myData;
		this.remoteRF = remoteRf;
		this.counterParty = counterParty;
		this.folder = folder;
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

		Book book = (Book) data.get(row);
		switch (col) {
		case 0:
			value = book.getBookno();
			break;
		case 1:
			value = book.getBook_name();
			break;
		case 2:
			LegalEntity le = getLE(book.getLe_id());
			value = le.getName();
			break;
		case 3:
			Folder fol = getFolder(book.getFolderID());
			value = fol.getFolder_name();
			break;

		}
		return value;
	}

	private Folder getFolder(int i) {
		Folder fold1 = null;
		Enumeration fenu = folder.elements();
		while (fenu.hasMoreElements()) {
			fold1 = (Folder) fenu.nextElement();
			if (fold1.getId() == i) {
				break;
			}
		}
		return fold1;

	}

	private LegalEntity getLE(int i) {
		LegalEntity le = null;
		int id = 0;
		Enumeration fenu = counterParty.elements();

		while (fenu.hasMoreElements()) {
			le = (LegalEntity) fenu.nextElement();
			if (le.getId() == i) {

				break;
			}
			id++;
		}
		return le;

	}

	public boolean isCellEditable(int row, int col) {
		return false;
	}

	public void setValueAt(Object value, int row, int col) {

		if (value instanceof Book) {
			data.set(row, (Book) value);
			this.fireTableDataChanged();
		}

	}

	public void addRow(Object value) {

		data.add((Book) value);
		this.fireTableDataChanged();

	}

	public void delRow(int row) {

		data.remove(row);
		this.fireTableDataChanged();

	}

	public void udpateValueAt(Object value, int row, int col) {

		data.set(row, (Book) value);
		fireTableCellUpdated(row, col);
		System.out.println("New value of data:");

	}
 }
}

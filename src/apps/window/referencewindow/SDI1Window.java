package apps.window.referencewindow;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.dyno.visual.swing.layouts.Bilateral;
import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;

import util.RemoteServiceUtil;
import util.commonUTIL;
import apps.window.utilwindow.JDialogTable;
import beans.Account;
import beans.LegalEntity;
import beans.Sdi;
import beans.StartUPData;
import dsServices.RemoteAccount;
import dsServices.RemoteReferenceData;

//VS4E -- DO NOT REMOVE THIS LINE!
public class SDI1Window extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel sdiIdLabel;
	private JLabel roleLabel;
	private JLabel leLabel;
	private JLabel poLabel;
	private JLabel contactLabel;
	private JPanel jPanel0;
	private JTabbedPane jTabbedPane0;
	private JTextField leTextField;
	private JTextField poTextField;
	private JComboBox roleComboBox;
	private JTextField sdiIdTextField;
	private JComboBox contactComboBox;
	private JComboBox CurrencyComboBox;
	private JLabel currencyLabel;
	private JLabel jLabel6;
	private JLabel Pay;
	private JLabel methodLabel;
	private JComboBox methodComboBox;
	private JComboBox payReceiveComboBox;
	private JComboBox cashSecurityComboBox;
	private JLabel cashSecurityLabel;
	private JComboBox productsComboBox;
	private JLabel productsLabel;
	private JButton poButton;
	private JButton leButton;
	private JLabel codeLabel;
	private JLabel agentContactLabel;
	private JTextField codeTextField;
	private JButton codeButton;
	private JLabel acLabel;
	private JTextField acTextField;
	private JButton glButton;
	private JTextField glTextField;
	private JComboBox agentContactComboBox;
	private JLabel glLabel;
	private JPanel jPanel1;
	private JTabbedPane jTabbedPane1;
	private JButton newButton;
	private JButton saveAsNewButton;
	private JButton saveButton;
	private JButton deleteButton;
	
	int leID = 0;
	int poID =0;
	int agentID =0;
	int accountId =0;
	int partyId = 0;
	
	Vector roleVec = null;
	Vector currencyVec = null;
	Vector productVec = null;
	Vector contactVec = null;
	Vector sdiMethodVec = null;
	Vector sdiAttributesVec = null;
	
	private JButton roleListButton;
	private JList roleList;
	
	DefaultListModel listModel = new DefaultListModel();
	Vector<LegalEntity> legalEntitys = new Vector<LegalEntity>();
	//TableModelUtil modelt = null;
	
	
	
	String s [] = {"id","LegalEntityName"};
	DefaultTableModel tradertablemodel = new DefaultTableModel(s,0);
	String po [] = {"id","POName"};
	DefaultTableModel potablemodel = new DefaultTableModel(po,0);
	DefaultTableModel browserPO = new DefaultTableModel(po,0);
	String agent [] = {"id","AgentName"};
	DefaultTableModel agenttablemodel = new DefaultTableModel(agent,0);
	String gl [] = {"id","Account"};
	DefaultTableModel glTablemodel = new DefaultTableModel(gl,0);
	String attributeColumnName [] =    {"Attribute Name ", "Attribute  Value "};
	DefaultTableModel attributeModel = null;
	String browserCol[] = { "SDI ID", "MessageType", "Pay/Rec", "Format","Party Name", "Role", "AGent", "Key","Cash", "Product", "Currency"  };
	DefaultTableModel browserModel = null;
	
	javax.swing.DefaultComboBoxModel roleVal = new javax.swing.DefaultComboBoxModel();
	javax.swing.DefaultComboBoxModel currencyVal = new javax.swing.DefaultComboBoxModel();
	javax.swing.DefaultComboBoxModel productVal = new javax.swing.DefaultComboBoxModel();
	javax.swing.DefaultComboBoxModel contactVal = new javax.swing.DefaultComboBoxModel();
	javax.swing.DefaultComboBoxModel  payRec = new javax.swing.DefaultComboBoxModel();
	javax.swing.DefaultComboBoxModel cashMethod = new javax.swing.DefaultComboBoxModel();
	javax.swing.DefaultComboBoxModel method = new javax.swing.DefaultComboBoxModel();
	javax.swing.DefaultComboBoxModel agentContactVal = new javax.swing.DefaultComboBoxModel();
	
	javax.swing.DefaultComboBoxModel browserRoleVal = new javax.swing.DefaultComboBoxModel();
	javax.swing.DefaultComboBoxModel browserCurrencyVal = new javax.swing.DefaultComboBoxModel();
	javax.swing.DefaultComboBoxModel browserProductVal = new javax.swing.DefaultComboBoxModel();
	javax.swing.DefaultComboBoxModel browserPartyVal = new javax.swing.DefaultComboBoxModel();
	
	JDialogTable showLE = null;
	JDialogTable showAgent = null;
	JDialogTable showPO = null;
	JDialogTable showGLAccount = null;
	JDialogTable showBrowserPO = null;
	
	
	RemoteReferenceData remoteBORef;
	RemoteAccount remoteAccount;
	private JLabel sdiLabel;
	private JLabel browserRoleLabel;
	private JLabel browserPartyLabel;
	private JTextField browserSdiIdTextField;
	private JComboBox browserRoleCombobox;
	private JLabel browserProductsLabel;
	private JLabel browserCCYLabel;
	private JComboBox browserProductsCombobox;
	private JPanel jPanel2;
	private JButton loadButton;
	private JTable jTable1;
	private JScrollPane jScrollPane0;
	private JScrollPane jScrollPane2;
	private JTable browserTable;
	private JTable jTable0;
	private JComboBox browserCCYComboBox;
	private JTextField browserPartyTextField;
	private JButton browserPartyButton;
	private static final String PREFERRED_LOOK_AND_FEEL = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
	
	public SDI1Window() {
		initComponents();
	}

	private void initComponents() {
		
        
        //processTableData(attributeModel);
        
		remoteBORef = (RemoteReferenceData) RemoteServiceUtil
		.getRemoteReferenceDataService();
		
		remoteAccount = (RemoteAccount) RemoteServiceUtil.getRemoteAccountService();

		try {
			
			roleVec = (Vector) remoteBORef.getStartUPData("Roles");
			sdiMethodVec = (Vector) remoteBORef.getStartUPData("SDIMethod");
			legalEntitys = (Vector) remoteBORef.selectAllLs();
			currencyVec = (Vector) remoteBORef.getStartUPData("Currency");
			productVec = (Vector) remoteBORef.getStartUPData("ProductType");
			contactVec = (Vector) remoteBORef.getStartUPData("LEContacts");
			sdiAttributesVec = (Vector) remoteBORef.getStartUPData("SDIAttributes");
			
		} catch (RemoteException e) {
		
			e.printStackTrace();
		
		}

		setLayout(new GroupLayout());
		add(getJTabbedPane0(), new Constraints(new Leading(9, 784, 10, 10), new Bilateral(8, 12, 215)));
		setSize(807, 407);
		setVisible(true);
		
			    
	    showAgent = new JDialogTable(agenttablemodel);
        getLEDataCombo1(agenttablemodel,"Agent");
        showAgent.setLocationRelativeTo(this);
        
        showAgent.jTable1.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				
				int id  = ((Integer)	showAgent.jTable1.getValueAt(showAgent.jTable1.getSelectedRow(),0)).intValue();
			
				String ss = (String)	showAgent.jTable1.getValueAt(showAgent.jTable1.getSelectedRow(),1);
				codeTextField.setText(ss);
				agentID = id;
				showAgent.dispose();
			
			}
   	
        });   
        
        showPO = new JDialogTable(potablemodel);
        getLEDataCombo1(potablemodel,"PO");
        
        showPO.setLocationRelativeTo(this);
        
        showPO.jTable1.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				
				 int id  = ((Integer)	showPO.jTable1.getValueAt(showPO.jTable1.getSelectedRow(),0)).intValue();
			
				 String ss = (String)	showPO.jTable1.getValueAt(showPO.jTable1.getSelectedRow(),1);
				 System.out.println("id " + id);
				 System.out.println("ss " + ss);
				 poTextField.setText(ss);
				 poID = id;
				 showPO.dispose();
			}
        });   
        
        showGLAccount = new JDialogTable(glTablemodel);
        getGLDataCombo(glTablemodel);
        showGLAccount.setLocationRelativeTo(this);
        
        showGLAccount.jTable1.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				
				int id  = ((Integer)	showGLAccount.jTable1.getValueAt(showGLAccount.jTable1.getSelectedRow(),0)).intValue();
			
				String ss = (String)	showGLAccount.jTable1.getValueAt(showGLAccount.jTable1.getSelectedRow(),1);
				
				glTextField.setText(ss);
				accountId = id;
				showGLAccount.dispose();
				
			}
        });   
        
        showBrowserPO = new JDialogTable(browserPO);
        getLEDataCombo1(potablemodel,"PO");
        showBrowserPO.setLocationRelativeTo(this);
        
        showBrowserPO.jTable1.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				
				int id  = ((Integer)	showBrowserPO.jTable1.getValueAt(showBrowserPO.jTable1.getSelectedRow(),0)).intValue();
			
				String ss = (String)	showBrowserPO.jTable1.getValueAt(showBrowserPO.jTable1.getSelectedRow(),1);
				
				browserPartyTextField.setText(ss);
				partyId = id;
				showBrowserPO.dispose();
				
			}
        });   
        
	}

	private JButton getBrowserPartyButton() {
		if (browserPartyButton == null) {
			browserPartyButton = new JButton();
			browserPartyButton.setText("browserPartyButton");
			
			browserPartyButton.addActionListener(new java.awt.event.ActionListener() {
		        public void actionPerformed(java.awt.event.ActionEvent evt) {

		        	//showGLAccount.setVisible(true);
		        	
		        }
		    }); 
			
		}
		return browserPartyButton;
	}

	private JTextField getBrowserPartyTextField() {
		
		if (browserPartyTextField == null) {
			browserPartyTextField = new JTextField();
			browserPartyTextField.setText(" ");
		}
		return browserPartyTextField;
	}

	private JComboBox getBrowserCCYComboBox() {
		
		if (browserCCYComboBox == null) {
			
			browserCCYComboBox = new JComboBox();
			browserCCYComboBox.setDoubleBuffered(false);
			browserCCYComboBox.setBorder(null);
			
			processComboBox(browserCurrencyVal, "CurrencyList");
			browserCCYComboBox.setModel(browserCurrencyVal);
			
		}
		
		return browserCCYComboBox;
		
	}

	private JTable getJTable0() {
		if (jTable0 == null) {
			if (jTable0 == null) {
				jTable0 = new JTable();
				
				browserModel = new DefaultTableModel(browserCol,0);
				
				jTable0.setAutoscrolls(true);

				jTable0.setModel(browserModel);
				
			}
			
		}
		
		return jTable0;
	}
		

	private JTable getBrowerTable() {
		if (browserTable == null) {
			browserTable = new JTable();
			
			browserModel = new DefaultTableModel(browserCol,0);
			
			browserTable.setAutoscrolls(true);
						
			browserTable.setModel(browserModel);
			
		}
		return browserTable;
	}

	private JScrollPane getJScrollPane2() {
		if (jScrollPane2 == null) {
			jScrollPane2 = new JScrollPane();
			jScrollPane2.setViewportView(getBrowerTable());
		}
		return jScrollPane2;
	}

	private JScrollPane getJScrollPane0() {
		if (jScrollPane0 == null) {
			jScrollPane0 = new JScrollPane();
			jScrollPane0.setViewportView(getAttributesTable());
		}
		return jScrollPane0;
	}

	private JTable getAttributesTable() {
		if (jTable1 == null) {
			jTable1 = new JTable();
			
			attributeModel = new DefaultTableModel(attributeColumnName,0);
			
			jTable1.setAutoscrolls(true);
			
			
			
			if (sdiAttributesVec != null) {
				for (int i = 0; i < sdiAttributesVec.size(); i++) {
					StartUPData attributs = (StartUPData) sdiAttributesVec.get(i);
					attributeModel.insertRow(i,
							new Object[] { attributs.getName(), "" });
				}
			}
			
			jTable1.setModel(attributeModel);
			
		}
		return jTable1;
	}

	private JButton getLoadButton() {
		if (loadButton == null) {
			loadButton = new JButton();
			loadButton.setText("Load");
		}
		
		loadButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				
				
				DefaultTableModel browserModel = (DefaultTableModel) browserTable.getModel();
				browserModel.setRowCount(0);
			    
				String query =	getQuery();
				
				if (query.equals(" ")) {
					
					commonUTIL.showAlertMessage("Please select atleast one criteria");
					return;
					
				} else {
					
					Vector sdis = getSDIs(query);
				    
					if (!sdis.isEmpty()) {

						loadSDIs(sdis);
					    
					} else {
						
						commonUTIL.showAlertMessage("SDI not found");
						return;
					}
					
				    
				}

			}			
		});
		return loadButton;
	}

	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setLayout(new GroupLayout());
			jPanel2.add(getSdiLabel(), new Constraints(new Leading(21, 62, 10, 10), new Leading(14, 26, 10, 10)));
			jPanel2.add(getBrowserRoleCombobox(), new Constraints(new Leading(85, 99, 10, 10), new Leading(44, 12, 12)));
			jPanel2.add(getBrowserProductsLabel(), new Constraints(new Leading(229, 60, 10, 10), new Leading(14, 24, 12, 12)));
			jPanel2.add(getBrowserCCYLabel(), new Constraints(new Leading(229, 12, 12), new Leading(50, 12, 12)));
			jPanel2.add(getBrowserProductsCombobox(), new Constraints(new Leading(297, 113, 10, 10), new Leading(12, 12, 12)));
			jPanel2.add(getLoadButton(), new Constraints(new Leading(16, 12, 12), new Leading(321, 10, 10)));
			jPanel2.add(getBrowserRoleLabel(), new Constraints(new Leading(19, 52, 12, 12), new Leading(46, 23, 12, 12)));
			jPanel2.add(getBrowserPartyLabel(), new Constraints(new Leading(19, 43, 12, 12), new Leading(82, 25, 12, 12)));
			jPanel2.add(getJScrollPane2(), new Constraints(new Leading(16, 746, 10, 10), new Leading(129, 166, 10, 10)));
			jPanel2.add(getBrowserSdiIdTextField(), new Constraints(new Leading(86, 30, 10, 10), new Leading(12, 12, 12)));
			jPanel2.add(getBrowserCCYComboBox(), new Constraints(new Leading(297, 70, 10, 10), new Leading(47, 12, 12)));
			jPanel2.add(getBrowserPartyTextField(), new Constraints(new Leading(85, 98, 12, 12), new Leading(83, 18, 12, 12)));
			jPanel2.add(getBrowserPartyButton(), new Constraints(new Leading(197, 29, 10, 10), new Leading(83, 20, 12, 12)));
		}
		return jPanel2;
	}

	private JComboBox getBrowserProductsCombobox() {
		
		if (browserProductsCombobox == null) {
				
			browserProductsCombobox = new JComboBox();
			browserProductsCombobox.setDoubleBuffered(false);
			browserProductsCombobox.setBorder(null);
			
			processComboBox(browserProductVal, "ProductList");
			browserProductsCombobox.setModel(browserProductVal);
			

		}
		
		return browserProductsCombobox;
	}

	private JLabel getBrowserCCYLabel() {
		if (browserCCYLabel == null) {
			browserCCYLabel = new JLabel();
			browserCCYLabel.setText("CCY");
		}
		return browserCCYLabel;
	}

	private JLabel getBrowserProductsLabel() {
		if (browserProductsLabel == null) {
			browserProductsLabel = new JLabel();
			browserProductsLabel.setText("Products");
		}
		return browserProductsLabel;
	}

	private JComboBox getBrowserRoleCombobox() {
		
		if (browserRoleCombobox == null) {
	
			browserRoleCombobox = new JComboBox();
			browserRoleCombobox.setDoubleBuffered(false);
			browserRoleCombobox.setBorder(null);
			
			processComboBox(browserRoleVal, "RoleList");
			browserRoleCombobox.setModel(browserRoleVal);
				
		}

		return browserRoleCombobox;
		
	}

	private JTextField getBrowserSdiIdTextField() {
		if (browserSdiIdTextField == null) {
			browserSdiIdTextField = new JTextField();
			browserSdiIdTextField.setText("");
		}
		return browserSdiIdTextField;
	}

	private JLabel getBrowserPartyLabel() {
		if (browserPartyLabel == null) {
			browserPartyLabel = new JLabel();
			browserPartyLabel.setText("Party");
		}
		return browserPartyLabel;
	}

	private JLabel getBrowserRoleLabel() {
		if (browserRoleLabel == null) {
			browserRoleLabel = new JLabel();
			browserRoleLabel.setText("Role");
		}
		return browserRoleLabel;
	}

	private JLabel getSdiLabel() {
		if (sdiLabel == null) {
			sdiLabel = new JLabel();
			sdiLabel.setText("SDI Id");
		}
		return sdiLabel;
	}

	private JButton getDeleteButton() {
		if (deleteButton == null) {
			deleteButton = new JButton();
			deleteButton.setText("Delete");
		}
		
		deleteButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {

				boolean isValid = validateData();

				if (isValid) {

					Sdi sdi = new Sdi();
					
					sdi.setRole(roleComboBox.getSelectedItem().toString()); 
					sdi.setCpId(leID);  			
					sdi.setPoId(poID); 
					sdi.setLeContacts(contactComboBox.getSelectedItem().toString()); 	
					sdi.setCurrency(CurrencyComboBox.getSelectedItem().toString());
					sdi.setPayrec(payReceiveComboBox.getSelectedItem().toString());
					sdi.setMessageType(methodComboBox.getSelectedItem().toString()); 
					sdi.setCash(cashSecurityComboBox.getSelectedItem().toString()); 
					sdi.setProducts(productsComboBox.getSelectedItem().toString()); 
					//sdi.setcodeTextField.getText().equals("")
					sdi.setAgentContacts(agentContactComboBox.getSelectedItem().toString());
					sdi.setAttributes(getAttributeValue());
					
					if ( roleComboBox.getSelectedItem().toString().equals("PO") ) {
						
						sdi.setAccountID(accountId);
					
					} 
					
					String key = sdi.getAgentId()+"/"+sdi.getsdiformat() +"/"+sdi.getProducts()+"/"+sdi.getCpId()+"/"+sdi.getPoId()+"/"+sdi.getCurrency();
					sdi.setkey(key);
					sdi.setId(Integer.parseInt(sdiIdTextField.getText().toString()));
					
					
					try {
						
						remoteBORef.removeSDI(sdi);
												
												
					} catch (Exception e){
						
						 commonUTIL.displayError("SDIWindow", "Update " , e);
						 commonUTIL.showAlertMessage("SDI not deleted");
						   
					}
					
					 commonUTIL.showAlertMessage("SDI deleted");
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
		
		saveButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {

				boolean isValid = validateData();

				if (isValid) {

					Sdi sdi = new Sdi();
					
					 if( poID == -1) 
						   poID =0;
					 
					sdi.setRole(roleComboBox.getSelectedItem().toString()); 
					sdi.setCpId(leID);  			
					sdi.setPoId(poID); 
					sdi.setAgentId(agentID);
					sdi.setLeContacts(contactComboBox.getSelectedItem().toString()); 	
					sdi.setCurrency(CurrencyComboBox.getSelectedItem().toString());
					sdi.setPayrec(payReceiveComboBox.getSelectedItem().toString());
					sdi.setMessageType(methodComboBox.getSelectedItem().toString()); 
					sdi.setCash(cashSecurityComboBox.getSelectedItem().toString()); 
					sdi.setProducts(productsComboBox.getSelectedItem().toString()); 
					//sdi.setcodeTextField.getText().equals("")
					sdi.setAgentContacts(agentContactComboBox.getSelectedItem().toString());
					sdi.setAttributes(getAttributeValue());
					sdi.setsdiformat(acTextField.getText().toString().trim());
					
					if ( roleComboBox.getSelectedItem().toString().equals("PO") ) {
						
						sdi.setAccountID(accountId);
					
					} 
					
					String key = sdi.getAgentId()+"/"+sdi.getsdiformat() +"/"+sdi.getProducts()+"/"+sdi.getCpId()+"/"+sdi.getPoId()+"/"+sdi.getCurrency();
					sdi.setkey(key);
					sdi.setId(Integer.parseInt(sdiIdTextField.getText().toString()));
					
					try {
						
						sdi =	(Sdi) remoteBORef.saveSDI(sdi);
						poID = sdi.getPoId();
						
						if (sdi != null) {
							
							commonUTIL.showAlertMessage("SDI updated with id " + sdi.getId() );
						
						} else {
							
							commonUTIL.showAlertMessage("SDI not updated" );
							
						}
					    
						
					} catch (Exception e){
						
						 commonUTIL.displayError("SDIWindow", "Update " , e);
						 commonUTIL.showAlertMessage("SDI not updated");
						   
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
		
		saveAsNewButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {

				boolean isValid = validateData();

				if (isValid) {

					Sdi sdi = new Sdi();
					
					 if( poID == -1) 
						   poID =0;
					 
					sdi.setRole(roleComboBox.getSelectedItem().toString()); 
					sdi.setCpId(leID);  			
					sdi.setPoId(poID); 
					sdi.setAgentId(agentID);
					sdi.setLeContacts(contactComboBox.getSelectedItem().toString()); 	
					sdi.setCurrency(CurrencyComboBox.getSelectedItem().toString());
					sdi.setPayrec(payReceiveComboBox.getSelectedItem().toString());
					sdi.setMessageType(methodComboBox.getSelectedItem().toString()); 
					sdi.setCash(cashSecurityComboBox.getSelectedItem().toString()); 
					sdi.setProducts(productsComboBox.getSelectedItem().toString()); 
					sdi.setAgentContacts(agentContactComboBox.getSelectedItem().toString());
					sdi.setAttributes(getAttributeValue());
					sdi.setsdiformat(acTextField.getText().toString().trim());
					
					if ( roleComboBox.getSelectedItem().toString().equals("PO") ) {
						
						sdi.setAccountID(accountId);
					
					} 
					
					String key = sdi.getAgentId()+"/"+sdi.getsdiformat() +"/"+sdi.getProducts()+"/"+sdi.getCpId()+"/"+sdi.getPoId()+"/"+sdi.getCurrency();
					sdi.setkey(key);
					sdi.setId(0);
					
					try {

						if(!remoteBORef.checkSDIKey(sdi)) {
							
							sdi =	(Sdi) remoteBORef.saveSDI(sdi);	
						
							sdiIdTextField.setText(new Integer(sdi.getId()).toString());
							
							
							if (sdi.getId() > 0) {
								
								commonUTIL.showAlertMessage("SDi save with id " + sdi.getId() );
							
							} else {
								
								commonUTIL.showAlertMessage("SDI not saved" );
								
							}
							
						} else {
							
							commonUTIL.showAlertMessage("SDI already exists" );
							
						}
					
					} catch (RemoteException e) {
					
					   commonUTIL.displayError("SDIWindow", "Save " , e);
					   commonUTIL.showAlertMessage("SDI not saved");
					   
					}

				}
			}
		});
		
		return saveAsNewButton;
	}

	private JButton getNewButton() {
		if (newButton == null) {
			newButton = new JButton();
			newButton.setText("New");
		}
		return newButton;
	}

	private JTabbedPane getJTabbedPane1() {
		if (jTabbedPane1 == null) {
			jTabbedPane1 = new JTabbedPane();
			jTabbedPane1.addTab("Agent Info", getJPanel1());
		}
		return jTabbedPane1;
	}

	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(new GroupLayout());
			jPanel1.add(getCodeLabel(), new Constraints(new Leading(12, 73, 10, 10), new Leading(10, 10, 10)));
			jPanel1.add(getAgentContactLabel(), new Constraints(new Leading(12, 12, 12), new Leading(53, 12, 12)));
			jPanel1.add(getCodeTextField(), new Constraints(new Leading(80, 118, 12, 12), new Leading(8, 12, 12)));
			jPanel1.add(getCodeButton(), new Constraints(new Leading(218, 29, 10, 10), new Leading(8, 20, 12, 12)));
			jPanel1.add(getAcLabel(), new Constraints(new Leading(270, 10, 10), new Leading(10, 12, 12)));
			jPanel1.add(getACTextField(), new Constraints(new Leading(323, 92, 10, 10), new Leading(8, 12, 12)));
			jPanel1.add(getGLButton(), new Constraints(new Leading(455, 29, 10, 10), new Leading(49, 20, 12, 12)));
			jPanel1.add(getGLTextField(), new Constraints(new Leading(323, 118, 12, 12), new Leading(51, 12, 12)));
			jPanel1.add(getAgentContactComboBox(), new Constraints(new Leading(80, 119, 12, 12), new Leading(47, 22, 12, 12)));
			jPanel1.add(getGLLabel(), new Constraints(new Leading(271, 12, 12), new Leading(53, 12, 12)));
		}
		return jPanel1;
	}

	private JLabel getGLLabel() {
		if (glLabel == null) {
			glLabel = new JLabel();
			glLabel.setText("GL");
		}
		return glLabel;
	}

	private JComboBox getAgentContactComboBox() {
		
		if (agentContactComboBox == null) {
			
			agentContactComboBox = new JComboBox();
			agentContactComboBox.setModel(new DefaultComboBoxModel(new Object[] { "item0", "item1", "item2", "item3" }));
			agentContactComboBox.setDoubleBuffered(false);
			agentContactComboBox.setBorder(null);
			
			processComboBox(agentContactVal, "ContactList");
			agentContactComboBox.setModel(agentContactVal);
		}
		
		return agentContactComboBox;
	}

	private JTextField getGLTextField() {
		if (glTextField == null) {
			glTextField = new JTextField();
			glTextField.setText("");
		}
		return glTextField;
	}

	private JButton getGLButton() {
		if (glButton == null) {
			glButton = new JButton();
			glButton.setText("glButton");
		}
		
		glButton.addActionListener(new java.awt.event.ActionListener() {
	        public void actionPerformed(java.awt.event.ActionEvent evt) {

	        	showGLAccount.setVisible(true);
	        	
	        }
	    }); 
		
		return glButton;
	}

	private JTextField getACTextField() {
		
		if (acTextField == null) {
			acTextField = new JTextField();
		}
		return acTextField;
	}

	private JLabel getAcLabel() {
		if (acLabel == null) {
			acLabel = new JLabel();
			acLabel.setText("AC");
		}
		return acLabel;
	}

	private JButton getCodeButton() {
		
		if (codeButton == null) {
			
			codeButton = new JButton();
			codeButton.setText("codeButton");
			
		}
		
		codeButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
            	
            	showAgent.setVisible(true);
            	
            }
        }); 
		 
		return codeButton;
	}

	private JTextField getCodeTextField() {
		
		if (codeTextField == null) {
			codeTextField = new JTextField();
		}
		return codeTextField;
	}

	private JLabel getAgentContactLabel() {
		
		if (agentContactLabel == null) {
			agentContactLabel = new JLabel();
			agentContactLabel.setText("Contact");
		}
		return agentContactLabel;
	}

	private JLabel getCodeLabel() {
		
		if (codeLabel == null) {
			codeLabel = new JLabel();
			codeLabel.setText("Code");
		}
		return codeLabel;
	}

	private JButton getLeButton() {
		
		if (leButton == null) {
			leButton = new JButton();
			leButton.setText("leButton");
		}
		
		leButton.addActionListener(new java.awt.event.ActionListener() {
	        public void actionPerformed(java.awt.event.ActionEvent evt) {

	        	showLE.setVisible(true);
	        	
	        }
	    }); 
				
		return leButton;
	}
	
	
	private JButton getPoButton() {
		
		if (poButton == null) {
			poButton = new JButton();
			poButton.setText("poButton");
			
			poButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {        
		            	
					showPO.setVisible(true);
		            	
		        }
		    }); 
			 
		}
		return poButton;
	}

	private JLabel getProductsLabel() {
		if (productsLabel == null) {
			productsLabel = new JLabel();
			productsLabel.setText("Products");
		}
		return productsLabel;
	}

	private JComboBox getProductsComboBox() {
		
		if (productsComboBox == null) {
			
			productsComboBox = new JComboBox();
			//productsComboBox.setModel(new DefaultComboBoxModel(new Object[] { "item0", "item1", "item2", "item3" }));
			productsComboBox.setDoubleBuffered(false);
			productsComboBox.setBorder(null);
			
			processComboBox(productVal, "ProductList");
			productsComboBox.setModel(productVal);
		
		}
		
		return productsComboBox;
		
	}

	private JLabel getCashSecurityLabel() {
		
		if (cashSecurityLabel == null) {
			
			cashSecurityLabel = new JLabel();
			cashSecurityLabel.setText("Cash/ Security");
			
		}
		
		return cashSecurityLabel;
		
	}

	private JComboBox getCashSecurityComboBox() {
		
		if (cashSecurityComboBox == null) {
			
			cashSecurityComboBox = new JComboBox();
			cashSecurityComboBox.setDoubleBuffered(false);
			cashSecurityComboBox.setBorder(null);
			
			cashMethod.insertElementAt("BOTH", 0);
	        cashMethod.insertElementAt("CASH", 1);
	        cashMethod.insertElementAt("SECURITY", 2);
	        cashMethod.setSelectedItem(0);
	
	        cashSecurityComboBox.setModel(cashMethod);
	        cashSecurityComboBox.setSelectedIndex(0);
		}
		
		return cashSecurityComboBox;
	}

	private JComboBox getPayReceiveComboBox() {
		
		if (payReceiveComboBox == null) {
			
			payReceiveComboBox = new JComboBox();
			//payReceiveComboBox.setModel(new DefaultComboBoxModel(new Object[] { "item0", "item1", "item2", "item3" }));
			payReceiveComboBox.setDoubleBuffered(false);
			payReceiveComboBox.setBorder(null);
			
			payRec.insertElementAt("PAY", 0);
	        payRec.insertElementAt("REC", 1);
	        payRec.insertElementAt("BOTH", 1);
	        
	        payReceiveComboBox.setModel(payRec);
		
		}
		
		return payReceiveComboBox;
	}

	private JComboBox getMethodComboBox() {
		
		if (methodComboBox == null) {
			
			methodComboBox = new JComboBox();
			//methodComboBox.setModel(new DefaultComboBoxModel(new Object[] { "item0", "item1", "item2", "item3" }));
			methodComboBox.setDoubleBuffered(false);
			methodComboBox.setBorder(null);
			
			processComboBox(method, "MethodList");
			methodComboBox.setModel(method);
			
		}
		
		return methodComboBox;
	}

	private JLabel getPay() {
		if (Pay == null) {
			
			Pay = new JLabel();
			Pay.setText("Pay/ Receive");
			
		}
		return Pay;
	}

	private JLabel getMethodLabel() {
		if (methodLabel == null) {
			
			methodLabel = new JLabel();
			methodLabel.setText("Method");
			
		}
		return methodLabel;
	}

	private JLabel getJLabel6() {
		if (jLabel6 == null) {
			
			jLabel6 = new JLabel();
			
		}
		return jLabel6;
	}

	private JLabel getCurrencyLabel() {
		
		if (currencyLabel == null) {
			
			currencyLabel = new JLabel();
			currencyLabel.setText("Currency");
			
		}
		
		return currencyLabel;
	}

	private JComboBox getCurrencyComboBox() {
		
		if (CurrencyComboBox == null) {
			
			CurrencyComboBox = new JComboBox();
			//CurrencyComboBox.setModel(new DefaultComboBoxModel(new Object[] { "item0", "item1", "item2", "item3" }));
			CurrencyComboBox.setDoubleBuffered(false);
			CurrencyComboBox.setBorder(null);
			
			processComboBox(currencyVal, "CurrencyList");
			CurrencyComboBox.setModel(currencyVal);
			
		}
		
		return CurrencyComboBox;
		
	}

	private JComboBox getContactComboBox() {
		
		if (contactComboBox == null) {
			
			contactComboBox = new JComboBox();
			//contactComboBox.setModel(new DefaultComboBoxModel(new Object[] { "item0", "item1", "item2", "item3" }));
			contactComboBox.setDoubleBuffered(false);
			contactComboBox.setBorder(null);
			
			processComboBox(contactVal, "ContactList");
			contactComboBox.setModel(contactVal);
			
		}
		
		return contactComboBox;
		
	}

	private JTextField getSdiIdTextField() {
		
		if (sdiIdTextField == null) {
			
			sdiIdTextField = new JTextField();
			sdiIdTextField.setText("0");
			sdiIdTextField.setEditable(false);
			
		}
		
		return sdiIdTextField;
	}

	private JComboBox getRoleComboBox() {
		
		if (roleComboBox == null) {
			
			roleComboBox = new JComboBox();
			//roleComboBox.setModel(new DefaultComboBoxModel(new Object[] { "item0", "item1", "item2", "item3" }));
			roleComboBox.setDoubleBuffered(false);
			roleComboBox.setBorder(null);
			
			processComboBox(roleVal, "RoleList");
			roleComboBox.setModel(roleVal);
			
			roleComboBox.addItemListener( new ItemListener() {

	        	@Override
	        	public void itemStateChanged(ItemEvent e) {
	        	
	        		for(int i=0;i<tradertablemodel.getRowCount();i++) {
	        			
	        			tradertablemodel.removeRow(i);
	        			
	        		}
	        		//showLE.clearRolesTables();
	        	
	        		String role = roleComboBox.getSelectedItem().toString();
	        		getLEDataCombo1(tradertablemodel,role);
	        		
	        		if (!leTextField.getText().toString().equals("")) {
	        			
	        			leTextField.setText("");
	        			
	        		}
	        		
	        		setLE(role);
	        		
	        		if(role.equalsIgnoreCase("PO")) {
	        		
	        			 poTextField.setEnabled(false);
	        			 poButton.setEnabled(false);
	        			 poID = -1;
	        			 
	        		 } else {
	        			 
	        			 poTextField.setEnabled(true);
	        			 poButton.setEnabled(true);
	        			 poID =0;
	        			 
	        		 }
	        		
	        	}
	        	   
	         });
		}
		
		return roleComboBox;
	}

	private JTextField getPoTextField() {
		
		if (poTextField == null) {
			
			poTextField = new JTextField();
			poTextField.setText("");
			
		}
		
		return poTextField;
	}

	private JTextField getLeTextField() {
		
		if (leTextField == null) {
			
			leTextField = new JTextField();
			leTextField.setText("");
			
		}
		
		return leTextField;
	}

	private JTabbedPane getJTabbedPane0() {
		if (jTabbedPane0 == null) {
			jTabbedPane0 = new JTabbedPane();
			jTabbedPane0.addTab("SDI Main", getJPanel0());
			jTabbedPane0.addTab("SDI Browser", getJPanel2());
		}
		return jTabbedPane0;
	}

	private JPanel getJPanel0() {
		if (jPanel0 == null) {
			jPanel0 = new JPanel();
			jPanel0.setLayout(new GroupLayout());
			jPanel0.add(getLeTextField(), new Constraints(new Leading(94, 129, 10, 10), new Leading(83, 21, 12, 12)));
			jPanel0.add(getPoLabel(), new Constraints(new Leading(6, 12, 12), new Leading(125, 12, 12)));
			jPanel0.add(getPoTextField(), new Constraints(new Leading(94, 128, 12, 12), new Leading(117, 22, 10, 10)));
			jPanel0.add(getRoleComboBox(), new Constraints(new Leading(94, 128, 12, 12), new Leading(49, 22, 12, 12)));
			jPanel0.add(getRoleLabel(), new Constraints(new Leading(6, 53, 12, 12), new Leading(55, 21, 12, 12)));
			jPanel0.add(getLeLabel(), new Constraints(new Leading(6, 76, 12, 12), new Leading(88, 19, 12, 12)));
			jPanel0.add(getContactComboBox(), new Constraints(new Leading(94, 119, 10, 10), new Leading(153, 22, 12, 12)));
			jPanel0.add(getContactLabel(), new Constraints(new Leading(6, 12, 12), new Leading(159, 12, 12)));
			jPanel0.add(getSdiIdTextField(), new Constraints(new Leading(94, 71, 12, 12), new Leading(16, 12, 12)));
			jPanel0.add(getSdiIdLabel(), new Constraints(new Leading(6, 65, 12, 12), new Leading(21, 12, 12)));
			jPanel0.add(getJLabel6(), new Constraints(new Leading(264, 86, 10, 10), new Leading(57, 12, 12)));
			jPanel0.add(getPoButton(), new Constraints(new Leading(231, 29, 10, 10), new Leading(119, 20, 12, 12)));
			jPanel0.add(getLeButton(), new Constraints(new Leading(229, 29, 12, 12), new Leading(84, 20, 12, 12)));
			jPanel0.add(getPay(), new Constraints(new Leading(278, 73, 12, 12), new Leading(55, 12, 12)));
			jPanel0.add(getCurrencyComboBox(), new Constraints(new Leading(375, 106, 10, 10), new Leading(14, 20, 12, 12)));
			jPanel0.add(getPayReceiveComboBox(), new Constraints(new Leading(375, 106, 12, 12), new Leading(46, 22, 12, 12)));
			jPanel0.add(getMethodComboBox(), new Constraints(new Leading(377, 106, 12, 12), new Leading(80, 21, 12, 12)));
			jPanel0.add(getCashSecurityComboBox(), new Constraints(new Leading(377, 107, 12, 12), new Leading(116, 21, 12, 12)));
			jPanel0.add(getCurrencyLabel(), new Constraints(new Leading(278, 81, 12, 12), new Leading(21, 12, 12)));
			jPanel0.add(getMethodLabel(), new Constraints(new Leading(279, 72, 12, 12), new Leading(86, 12, 12)));
			jPanel0.add(getCashSecurityLabel(), new Constraints(new Leading(281, 90, 12, 12), new Leading(120, 12, 12)));
			jPanel0.add(getProductsLabel(), new Constraints(new Leading(278, 86, 12, 12), new Leading(157, 12, 12)));
			jPanel0.add(getJTabbedPane1(), new Constraints(new Bilateral(8, 12, 5), new Leading(197, 116, 10, 10)));
			jPanel0.add(getNewButton(), new Constraints(new Leading(60, 95, 10, 10), new Leading(327, 22, 12, 12)));
			jPanel0.add(getSaveAsNewButton(), new Constraints(new Leading(194, 119, 10, 10), new Leading(327, 22, 12, 12)));
			jPanel0.add(getSaveButton(), new Constraints(new Leading(344, 96, 10, 10), new Leading(327, 22, 12, 12)));
			jPanel0.add(getDeleteButton(), new Constraints(new Leading(472, 98, 10, 10), new Leading(327, 22, 12, 12)));
			jPanel0.add(getProductsComboBox(), new Constraints(new Leading(379, 107, 12, 12), new Leading(151, 19, 10, 10)));
			jPanel0.add(getJScrollPane0(), new Constraints(new Leading(507, 264, 10, 10), new Leading(18, 150, 10, 10)));
		}
		return jPanel0;
	}

	private JLabel getContactLabel() {
		if (contactLabel == null) {
			contactLabel = new JLabel();
			contactLabel.setText("Contact");
		}
		return contactLabel;
	}

	private JLabel getPoLabel() {
		if (poLabel == null) {
			poLabel = new JLabel();
			poLabel.setText("Pro Org.");
		}
		return poLabel;
	}

	private JLabel getLeLabel() {
		if (leLabel == null) {
			leLabel = new JLabel();
			leLabel.setText("Legal Entity");
		}
		return leLabel;
	}

	private JLabel getRoleLabel() {
		if (roleLabel == null) {
			roleLabel = new JLabel();
			roleLabel.setText("Role");
		}
		return roleLabel;
	}

	private JLabel getSdiIdLabel() {
		if (sdiIdLabel == null) {
			sdiIdLabel = new JLabel();
			sdiIdLabel.setText("SDI Id");
		}
		return sdiIdLabel;
	}
	
	private void processComboBox(javax.swing.DefaultComboBoxModel combodata, String criteria) {
		
		if (criteria.equals("RoleList")) {
			
			for (int i = 0; i < roleVec.size(); i++) {

				StartUPData roles = (StartUPData) roleVec.get(i);

				combodata.insertElementAt(roles.getName(), i);
				
			}
			
		} else if (criteria.equals("CurrencyList")) {
			
			for (int i = 0; i < currencyVec.size(); i++) {

				StartUPData currency = (StartUPData) currencyVec.get(i);

				combodata.insertElementAt(currency.getName(), i);
				
			}
			
		} else if (criteria.equals("ProductList")) {
			
			for (int i = 0; i < productVec.size(); i++) {

				StartUPData product = (StartUPData) productVec.get(i);

				combodata.insertElementAt(product.getName(), i);
				
			}
			
		} else if (criteria.equals("ContactList")) {
			
			for (int i = 0; i < contactVec.size(); i++) {

				StartUPData contact = (StartUPData) contactVec.get(i);

				combodata.insertElementAt(contact.getName(), i);
				
			}
			
		}  else if (criteria.equals("MethodList")) {
			
			for (int i = 0; i < sdiMethodVec.size(); i++) {

				StartUPData sdiMethod = (StartUPData) sdiMethodVec.get(i);

				combodata.insertElementAt(sdiMethod.getName(), i);
				
			}
			
		} else if (criteria.equals("LegalEntity")) {
			
			for (int i = 0; i < legalEntitys.size(); i++) {

				LegalEntity le = (LegalEntity) legalEntitys.get(i);

				combodata.insertElementAt(le.getName(), i);
				
			}
			
		}
		
	}
	
	private void getLEDataCombo1(DefaultTableModel model,String leRole) {
		
		Vector vector;
		
		try {
			
			vector = (Vector) remoteBORef.selectLEonWhereClause(" role like '%"+leRole + "%'");
			Iterator it = vector.iterator();
	    	int i =0;
	    	
	    	while(it.hasNext()) {
	    		
	    		LegalEntity le =	(LegalEntity) it.next();
	    		model.insertRow(i, new Object[]{le.getId(),le.getName()});
	    		i++;
	    	
	    	}	
		
		} catch (RemoteException e) {
		
			//commonUTIL.displayError("JFrameReportApplicatoin","getLEDataCombo1", e);
		
		}
		
		
	}
	
	private void getGLDataCombo(DefaultTableModel model) {
		
		Vector vector;
		
		try {
			
			vector = (Vector) remoteAccount.getAllAccounts();
			Iterator it = vector.iterator();
	    	int i =0;
	    	
	    	while(it.hasNext()) {
	    		
	    		Account account =	(Account) it.next();
	    		model.insertRow(i, new Object[]{account.getId(),account.getAccountName()});
	    		i++;
	    	
	    	}	
		
		} catch (RemoteException e) {
		
			//commonUTIL.displayError("JFrameReportApplicatoin","getLEDataCombo1", e);
			
		}
				
	}
	
	private boolean validateData(){
		
		boolean isValid = true;
		
		if((roleComboBox.getSelectedItem() == null) ) {
			
			commonUTIL.showAlertMessage("Please select Role");
			return false;
		
		} 
		
		if (leTextField.getText().equals("") ) {
			
			commonUTIL.showAlertMessage("Please select Legal Entity");
			return false;
			
		} 
		
		if (!roleComboBox.getSelectedItem().equals("PO") && poTextField.getText().equals("") ) {
			
			commonUTIL.showAlertMessage("Please select Processing Organization");
			return false;
			
		} 
		
		if (contactComboBox.getSelectedItem() == null) {
			
			commonUTIL.showAlertMessage("Please select Contact");
			return false;
			
		} 
		
		if (CurrencyComboBox.getSelectedItem() == null ) {
			
			commonUTIL.showAlertMessage("Please select Currency");
			return false;
			
		} 
		
		if (payReceiveComboBox.getSelectedItem() == null) {
			
			commonUTIL.showAlertMessage("Please select Pay/ Receive");
			return false;
			
		} 
		
		if (methodComboBox.getSelectedItem() == null) {
			
			commonUTIL.showAlertMessage("Please select Method");
			return false;
			
		} 
		
		if (cashSecurityComboBox.getSelectedItem() == null) {
			
			commonUTIL.showAlertMessage("Please select Cash/ Security");
			return false;
			
		} 
		
		if (productsComboBox.getSelectedItem() == null) {
			
			commonUTIL.showAlertMessage("Please select a Product");
			return false;
			
		} 
		
		if (codeTextField.getText().equals("")) {
			
			commonUTIL.showAlertMessage("Please select a Code");
			return false;
			
		} 
		
		if (agentContactComboBox.getSelectedItem() == null) {
			
			commonUTIL.showAlertMessage("Please select a Contact for an Agent");
			return false;
			
		} 
		
		if (roleComboBox.getSelectedItem().toString().equals("PO") && glTextField.getText().equals("")) {
			
			commonUTIL.showAlertMessage("Please select a GL Account");
			return false;
			
		}
		
		return isValid;
	}
	
	private String getAttributeValue() {
		
		String attributesV  = "";
		
		for(int i=0;i<  jTable1.getRowCount();i++ ) {
			
			String attributename = ((String)  jTable1.getValueAt(i, 0)).trim();
			String attributeValue = ((String)  jTable1.getValueAt(i, 1)).trim();
			
			if(attributeValue != null && attributeValue.length() > 0)
				attributesV = attributesV.trim() + attributename+ "=" + attributeValue + ";";
			
		}
		
		if(attributesV.trim().length() > 0)
		return attributesV.substring(0, attributesV.length()-1);
		return attributesV;
	
	}
	
	private void setAttribute(String attributesV) {
		String attributeColumnName [] =    {"Attribute Name ", "Attribute Value "};
	    
	    DefaultTableModel attributeModel = new DefaultTableModel(attributeColumnName,0);
	    
	    
			if(attributesV != null && attributesV.length() > 0) {
			String atttoken [] = attributesV.trim().split(";"); 
			
			
			for(int i =0;i<atttoken.length;i++) {
				String att = (String) atttoken[i];
				
				if(att.contains("=")) {
				String attvalue = att.substring(att.indexOf('=')+1, att.length());
				String attnameName = att.substring(0, att.indexOf('='));
				attributeModel.insertRow(i, new Object[]{attnameName.trim(),attvalue.trim()});
				}
			}
			jTable1.removeAll();
			jTable1.setModel(attributeModel);
			}
		
	    
		
	}
	
	private void clearData() {
		
		sdiIdTextField.setText("0");
		roleComboBox.setSelectedIndex(0); 
		leTextField.setText("");  			
		poTextField.setText("");
		contactComboBox.setSelectedIndex(0);
		CurrencyComboBox.setSelectedIndex(0);
		payReceiveComboBox.setSelectedIndex(0);
		methodComboBox.setSelectedIndex(0);
		cashSecurityComboBox.setSelectedIndex(0);
		productsComboBox.setSelectedIndex(0);
		agentContactComboBox.setSelectedIndex(0);
		
		
		//sdi.setAgentContacts(agentContactComboBox.getSelectedItem().toString());
		//sdi.setAttributes(getAttributeValue());
	}
	
	
	 public void openSDI(Sdi sdi) {
	
			sdi.setAttributes(getAttributeValue());
			
			/*if ( browserRoleCombobox.getSelectedItem().toString().equals("PO") ) {
				
				sdi.setAccountID(accountId);
			
			} */
			
			String key = sdi.getAgentId()+"/"+sdi.getsdiformat() +"/"+sdi.getProducts()+"/"+sdi.getCpId()+"/"+sdi.getPoId()+"/"+sdi.getCurrency();
			sdi.setkey(key);
			
			String role = (String)sdi.getRole();
						
			sdiIdTextField.setText(new Integer(sdi.getId()).toString());
  	   	 	roleComboBox.setSelectedItem(role);
  	   	 	methodComboBox.setSelectedItem((String) sdi.getMessageType());
  	   	 	agentContactComboBox.setSelectedItem((String) sdi.getAgentContacts());
  	   	 	contactComboBox.setSelectedItem((String) sdi.getLeContacts());
  	   	 	productsComboBox.setSelectedItem((String) sdi.getProducts());
  	   	 	cashSecurityComboBox.setSelectedItem((String) sdi.getCash());
  	   	 	CurrencyComboBox.setSelectedItem((String) sdi.getCurrency());
  	   	 	payReceiveComboBox.setSelectedItem((String) sdi.getPayrec());
  	   	 	//codeTextField.setText((String) sdi.getC
  	   	 	acTextField.setText((String) sdi.getSdiformat());
  	   	 	glTextField.setText(getAccountName(sdi.getAccountID()));
  	   	 	leID = sdi.getCpId();
     	   	poID = sdi.getPoId();
  	   	 	agentID = sdi.getAgentId();
  	   	 	
  	   	 	leTextField.setText(getLEName(sdi.getCpId())); 	   	 	
  	   	 	poTextField.setText(getLEName(sdi.getPoId()));
  	   	 	codeTextField.setText(getLEName(sdi.getAgentId()));
  	   	 	
  	   	
  	   	 	jTabbedPane0.setSelectedIndex(0);
  	   	 	if(poID ==0) {
  	   	 		
  	   	 		poTextField.setEnabled(false);
  	   	 		poButton.setEnabled(false);
  	   	 		poID = -1; 
  	   	 	
  	   	 	} else {
  	   	 		
  	   	 		poTextField.setEnabled(true);
  	   	 		poButton.setEnabled(true);
  	   	 	}
	 }
	 
	 
	 private String getAccountName(int id) {
		 
		 String name = "";
		 
		 try {
		
			 if(id ==0)
				   return name;
			
			 
			 Account account = (Account) remoteAccount.getAccount(id);
			 name = account.getAccountName();
			 
		} catch (RemoteException e) {
			
			e.printStackTrace();
			
		}
		
		
		return name;
	   
	 }
	 
	 private String getLEName(int id) {
		 
		 String name = "";
		 
		 try {
		
			 if(id ==0)
				   return name;
			
			 LegalEntity le = (LegalEntity )remoteBORef.selectLE(id);
			 name = le.getName();
			 
		} catch (RemoteException e) {
			
			e.printStackTrace();
			
		}
		
		
		return name;
	   
	 }
	 
	 private void processTableData(DefaultTableModel model) {
			// TODO Auto-generated method stub
			Vector vector;
			try {
			   
				vector = (Vector) remoteBORef.getStartUPData("SDIAttributes");
				Iterator it = vector.iterator();
		    	int i =0;
		    	while(it.hasNext()) {
		    		
		    		StartUPData sdiAttributes = (StartUPData) it.next();
		    	   
		    		   model.insertRow(i, new Object[]{sdiAttributes.getName(),"0"});
		    	    
		    		i++;
		    		}
		    		
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	 
	 private String getQuery(){
		 
		 int i = 0;
		 
		 StringBuffer query = new StringBuffer();
		 
		 Hashtable<String, String> criteriaTable = new Hashtable<String, String>();
				 
		 if (!browserSdiIdTextField.getText().toString().equals("")) {
			 
			criteriaTable.put("id", browserSdiIdTextField.getText().toString());
		 
		 }
		 
		 if(browserRoleCombobox.getSelectedItem() != null) {
			 
			 criteriaTable.put("Role", browserRoleCombobox.getSelectedItem().toString());
			 
		 } 
		 
		 if (browserPartyTextField.getText().toString().equals("")) {
			 
			 String leRole = browserRoleCombobox.getSelectedItem().toString();
			 int id = 0;
			 
			 if (leRole.equals("CounterParty")) {
				 
				 leRole  = "CPID";
				 
			 } else if  (leRole.equals("PO")) {
				 
				 leRole  = "POID";
				 
			 } else if (leRole.equals("Agent")) {
				 
				 leRole  = "AgentId";
				 
			 }
			 
			 criteriaTable.put(leRole, partyId+"");
		 
		 }
		 
		 if (browserProductsCombobox.getSelectedItem() != null) {
			 
			 			 
			 criteriaTable.put("products", browserProductsCombobox.getSelectedItem().toString());
		 
		 } 
		 
		 if (browserCCYComboBox.getSelectedItem() != null) {
			 	
			  criteriaTable.put("currency", browserCCYComboBox.getSelectedItem().toString());
		 
		 }
		 
		 int size = criteriaTable.size();
		 
		 if ( size != 0 ) {
			 
			 Set<String> keys = criteriaTable.keySet();
		     
			 for(String key: keys){
		        
				 if ( i == 0) { 
					 
					 query
					 .append(key)
					 .append(" = ")
					 .append("'")
					 .append(criteriaTable.get(key))
					 .append("'");
				 
					 i =+1;
				
				 } else {
					 
					 query
					 .append(" and ")
					 .append(key)
					 .append(" = ")
					 .append("'")
					 .append(criteriaTable.get(key))
					 .append("'");
				 }
			 }
			 
		 } 
		 
		 return query.toString();
		 
	 }
	 
	 private Vector getSDIs(String sql) {
	    	// remoteBORef.SDIWhere
	    	 Vector sdis = null;
	    	 try {
	    		 sdis = (Vector) remoteBORef.SDIWhere(sql);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	 return sdis;
	     }
	 
	 private void loadSDIs(final Vector<Sdi> sdis) {
		 
		browserModel = new DefaultTableModel(browserCol,0);
			
		browserTable.setAutoscrolls(true);
		
		Sdi sdi = null;

		Iterator it = sdis.iterator();
  	   	
		int i =0;
  	   	
  	   	while(it.hasNext()) {
  	   		
  	   		sdi = (Sdi) it.next();
  	   		browserModel.insertRow(i,
				new Object[] { sdi.getId(), sdi.getMessageType(), sdi.getPayrec(), sdi.getSdiformat(), getLEName(sdi.getCpId()),
				sdi.getRole(), getLEName(sdi.getAgentId()),	sdi.getkey(), sdi.getCash(), sdi.getProducts(), sdi.getCurrency()});		
  	   		
  	   		i++;
	
  	   	}
  	   	
  	   	
		browserTable.setModel(browserModel);
		browserTable.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {

				openSDI(sdis.get(browserTable.getSelectedRow()));
			}

		});
					 
	 }
	 
	 private void setLE(String role){
		 
			showLE = new JDialogTable(tradertablemodel);
			getLEDataCombo1(tradertablemodel, role);
    	    showLE.setLocationRelativeTo(this);
    	    showLE.jTable1.addMouseListener(new java.awt.event.MouseAdapter() {

    			@Override
    			public void mouseClicked(MouseEvent e) {
    				
    				int id  = ((Integer) showLE.jTable1.getValueAt(showLE.jTable1.getSelectedRow(),0)).intValue();
    				
    				String ss = (String) showLE.jTable1.getValueAt(showLE.jTable1.getSelectedRow(),1);
    				
    				leTextField.setText(ss);
    				leID = id;
    				showLE.dispose();
    				
    			}
       	
    	    });  
	 }
}


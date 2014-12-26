package apps.window.referencewindow;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.RemoteException;
import java.text.ParseException;

import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.table.AbstractTableModel;

import util.ClassInstantiateUtil;
import util.NumericTextField;
import util.RemoteServiceUtil;
import util.commonUTIL;
import apps.window.referencewindow.searchPanel.sdiSearchPanel;
import apps.window.utilwindow.propertypane.SDIPropertyTable;
import beans.Account;
import beans.Book;
import beans.FutureProduct;
import beans.LegalEntity;
import beans.Sdi;

import com.jidesoft.dialog.ButtonPanel;
import com.jidesoft.docking.DockContext;
import com.jidesoft.docking.DockableFrame;
import com.jidesoft.grid.RowStripeTableStyleProvider;
import com.jidesoft.swing.JideBoxLayout;
import com.jidesoft.swing.JideScrollPane;
import constants.CommonConstants;
import constants.SDIConstants;
import dsServices.RemoteAccount;
import dsServices.RemoteReferenceData;

public class SDIWindow extends JPanel {
	
	// Vector<Sdi> data = new Vector<Sdi>();
	 String col [] = { "SDI ID", "Benificary", "Role","PO","Agent","Currency","ProductType" ,"Benificary Contact","Account Name","GL Account Name","InterMediary1","InterMediarty2" };
	// private JPanel sdiStatusBar = new JPanel();
	 Sdi  sdi = null;
	 LegalEntity beneficiary; 
	 Vector<Sdi> data = new Vector<Sdi>();

		DockableFrame searchframe;
	    private JPanel sdiStatusBar = new JPanel();
	    private JLabel sdiLabel = new JLabel("SDI");
	    private NumericTextField selectSDI = null;
	    ActionListener selectSDIActionListener = null;
	    private JPanel oldPropertyTablePanel = null;
		private JPanel sdiTablePanel = new JPanel(new BorderLayout());
		private JPanel sdiPropertiesPanel = new JPanel(new BorderLayout());
		TableModelSDIUtil model = null;
		protected JButton loadButton = new JButton("Load...");
		protected JButton newButton = new JButton("New");
		protected JButton deleteButton = new JButton("Delete");
		protected JButton saveButton = new JButton("Save");
		protected JButton saveAsNewButton = new JButton("Save As New");
		protected JButton closeButton = new JButton("Close");
		RemoteAccount remoteAccount = null;
		RemoteReferenceData remoteRef = RemoteServiceUtil.getRemoteReferenceDataService();
		FutureProduct futureProduct = null;
		protected JLabel dateLabel = new JLabel("Currency");
		protected JTextField dateText = new JTextField();
		
	    protected sdiSearchPanel sdiSearch = null;
		private JPanel buttonsPanel = new JPanel();
		
		private JScrollPane scrollPane = null;
		JTable sdiTables = new JTable();
		 
		private SDIPropertyTable propertyTable = null;
		
		private SDIInternalPanel sdiInternal = new SDIInternalPanel(remoteRef);
		
		protected DockableFrame createDockableFrame(String key, Icon icon) {
			DockableFrame frame = new DockableFrame(key, icon);
			frame.setPreferredSize(new Dimension(200, 200));
			frame.add(new JideScrollPane(new JTextArea()));

			return frame;
		}
		protected DockableFrame createSampleServerFrame() {
			String iconPath = "/resources/icon/report_filter.png";
			Icon icon = getIcon(iconPath);

			searchframe = createDockableFrame("Search", icon);
	    
			searchframe.getContext().setInitMode(DockContext.STATE_AUTOHIDE);
			searchframe.getContext().setInitSide(DockContext.DOCK_SIDE_WEST);
			searchframe.getContext().setInitIndex(0);
			sdiSearch = new sdiSearchPanel(remoteRef);
			
			searchframe.add(sdiSearch);
			searchframe.setToolTipText("Search Template ");
			sdiSearch.setSdiWindow(this);
			return searchframe;
		}
		private Icon getIcon(String path) {
			java.net.URL imgURL = getClass().getResource(path);
			if (imgURL != null) {
				return new ImageIcon(imgURL, "");
			}
			return null;

		}
		 private void createPropertyTablesForFutureContract() {

			JPanel propertyTablePanel = getSDIPropertyTablePanel();
			if (oldPropertyTablePanel != null) {
				sdiTablePanel.remove(oldPropertyTablePanel);
			}
			oldPropertyTablePanel = propertyTablePanel;
			sdiPropertiesPanel.add(oldPropertyTablePanel, BorderLayout.CENTER);
		}
		 
		 public JPanel getSDIPropertyTablePanel() {

				// FutureContractPropertyTable contractPropertyTable = new
				// FutureContractPropertyTable("FUTURES_FX");
			
				propertyTable = new SDIPropertyTable(SDIConstants.SDI_BENEFICARY_PROPERTIES);
				propertyTable.setTableStyleProvider(new RowStripeTableStyleProvider());
				propertyTable.getSDIPropertyTable();
				
				
	         for(int i=0;i< propertyTable.getRowCount();i++) {
	        	 propertyTable.setValueAt("", i, 1);
	         }
				JPanel basePanel = new JPanel();
				JScrollPane tableScrollPane = new JScrollPane();
				tableScrollPane.getViewport().add(propertyTable);
				basePanel.setLayout(new BorderLayout());
				basePanel.add(tableScrollPane, BorderLayout.CENTER);

				return basePanel;

			}
		 
		 private void createButtonsPanel() {

				//setButtonDetails();
			 JPanel buttonPanel = new ButtonPanel(SwingConstants.RIGHT);

				buttonPanel.add(buttons2Column(loadButton, newButton));
				buttonPanel.add(buttons2Column(saveAsNewButton, saveButton));
				buttonPanel.add(buttons2Column(deleteButton, closeButton));

				initBottomButtonsActionListeners();

			//	getRootPane().setDefaultButton(loadButton);
				buttonsPanel.add(buttonPanel, JideBoxLayout.FLEXIBLE);

			}
		 
		 
		 
		 private ButtonPanel buttons2Column(JButton topButton, JButton botButton) {

				ButtonPanel buttonPanel = new ButtonPanel(SwingConstants.TOP);
				buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
				buttonPanel.add(topButton, ButtonPanel.AFFIRMATIVE_BUTTON);
				buttonPanel.add(botButton, ButtonPanel.AFFIRMATIVE_BUTTON);
				return buttonPanel;

			}
		 
		 
	//	public 
		 public void setSelectSDIData(Vector<Sdi> selectSDIs) {
			 if (data != null || data.size() > 0) {
				 data.clear();
			 }
			 
			 sdiTables.removeAll();
				for(int i=0;i<model.getRowCount();i++) 
					model.delRow(i);
				data = selectSDIs;
				   model = new TableModelSDIUtil(selectSDIs,col);
				   sdiTables.setModel(model);
				
		 }
	 private void createLayout() {
	
	      model = new TableModelSDIUtil(data,col);
	      sdiTables.setModel(model);
	      int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS; 
			int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS;
	    
			sdiTables.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	   //   scrollPane.getViewport().add();
	      scrollPane  = new JScrollPane(sdiTables,v,h);
	 //     scrollPane.setAutoscrolls(true);
	      JPanel centerPanel = new JPanel(new BorderLayout());
	      centerPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null,
					null));
	      centerPanel.add(scrollPane, BorderLayout.CENTER);
	  
	       
	       JPanel tLeftPanel = new JPanel();
	       tLeftPanel.setLayout(new BorderLayout());
	       tLeftPanel.add(sdiStatusBar, BorderLayout.NORTH);
	       JPanel tLeftPanel2 = new JPanel();
	       tLeftPanel2.setLayout(new BorderLayout());
	       tLeftPanel2.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null,
					null));
	       tLeftPanel2.add(sdiInternal, BorderLayout.CENTER);
	       tLeftPanel.add(tLeftPanel2);
	       tLeftPanel.add(buttonsPanel, BorderLayout.SOUTH);
	       
	      createButtonsPanel();
	      createPropertyTablesForFutureContract();
	    
	      setupMainComponents();
	   
	     
			JPanel panel = new JPanel(new BorderLayout()); 
			panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 
			panel.add(createSampleServerFrame(), BorderLayout.BEFORE_FIRST_LINE);
			panel.add(centerPanel,BorderLayout.CENTER);
			JPanel mainPanel = new JPanel();
			mainPanel.setLayout(new BorderLayout());   
			mainPanel.add(tLeftPanel, BorderLayout.BEFORE_LINE_BEGINS); 
			mainPanel.add(panel, BorderLayout.CENTER); 
			
			 sdiTables.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseReleased(MouseEvent arg0) {				
					
				}
				
				@Override
				public void mousePressed(MouseEvent arg0) {					
					
				}
				
				@Override
				public void mouseExited(MouseEvent arg0) {					
					
				}
				
				@Override
				public void mouseEntered(MouseEvent arg0) {					
					
				}
				
				@Override
				public void mouseClicked(MouseEvent event) {
					
					if(sdiTables.getSelectedRow() == -1)
						return;
					
					int rowindex = sdiTables.getSelectedRow();

					Sdi sdi = (Sdi) data.get(rowindex);
					setAndShowSDI(sdi);
					selectSDI.setText(String.valueOf(sdi.getId()));
					
				}
			});
			 setLayout(new BorderLayout()); 
			 add(mainPanel,BorderLayout.CENTER);
			//return mainPanel;
	 }
	 
	 
	 
	 
	
	 protected ActionListener getContractSelectorComboBoxActionListener(){
	    	if(selectSDIActionListener == null){
	    		selectSDIActionListener =new  selectSDIActionListener();
	    	}
	    	return selectSDIActionListener;
	    }
	 class selectSDIActionListener implements ActionListener{
	    	public void actionPerformed(java.awt.event.ActionEvent event) {
	    		Object object=event.getSource();
	    		if (!(object instanceof NumericTextField)) return;
	    		NumericTextField obj = (NumericTextField)object;
	    		int i =0;
				try {
					i = obj.getDoubleValue().intValue();
					if(sdi == null)
						sdi = new Sdi();
					sdi.setId(i);
					sdi = remoteRef.selectSDI(sdi);
					if(sdi != null)
					setAndShowSDI(sdi);
					selectSDI.setText(String.valueOf(sdi.getId()));
				} catch (ParseException e1) {					
					e1.printStackTrace();
				}  catch(RemoteException r) {
					r.printStackTrace();
				}

	    	//	setAndShowFutureContract(futcon);
	    	}

			
	    }
	 public String getLegalEntity(int legalEntityid,int setIDAt) {
		 String name = "";
		try {
			if(legalEntityid == 0)
				return "";
			LegalEntity le = (LegalEntity) remoteRef.selectLE(legalEntityid);
			if(setIDAt == 2)
				propertyTable.setBeneficiary(le);
			if(setIDAt ==9)
				propertyTable.setPo(le);
			if(setIDAt ==13)
				propertyTable.setAgent(le);
			if(setIDAt ==17)
				propertyTable.setInterAgent1(le);
			if(setIDAt ==21)
				propertyTable.setInterAgent2(le);
			
			name = le.getName();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return name;
	 }
	 public String getAccountName(int accountID,int setIDAt) {
		 String name = "";
		try {
			Account acc = (Account) remoteAccount.getAccount(accountID);
			if(setIDAt ==14)
				propertyTable.setAccount(acc);
			if(setIDAt ==18)
				propertyTable.setInter1Account(acc);
			if(setIDAt ==22)
				propertyTable.setInter2Account(acc);
			name = acc.getAccountName();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return name;
	 }
	 private void setAndShowSDI(Sdi sdi) {
		 	
		 sdiInternal.rolesData.setSelectedItem(sdi.getRole());
		 sdiInternal.beneficiaryData.setSelectedItem((sdiInternal.getLeName(sdi.getCpId())));
		 sdiInternal.currencyData.setSelectedItem(sdi.getCurrency()); 
		 //sdiInternal.payrecData.setSelectedIndex(-1);
		 sdiInternal.productTypeData.setSelectedItem(sdi.getProducts());
		 sdiInternal.beneficiaryLecontactsData.setSelectedItem(sdi.getLeContacts());
		 sdiInternal.cashsecurityData.setSelectedItem(sdi.getCash());
		 sdiInternal.jPreferred.setSelected(commonUTIL.getBooleanValue(sdi.getPreferred()));
		 sdiInternal.priorityTextField.setText(String.valueOf(sdi.getPriority()));
		 sdiInternal.messsgeMethodData.setSelectedItem(sdi.getMessageType());
		 //sdiInternal.jPreferred.setSelected(commonUTIL.getBooleanValue(sdi.getPreferred()));
		 if (!sdi.getRole().equals("PO")) {
			 sdiInternal.poData.setSelectedItem(sdiInternal.getLeName(sdi.getPoId()));
			 sdiInternal.poContactData.setSelectedItem(sdi.getPoContact());
		 } else {
			 sdiInternal.poData.enable(false);
		 }
		 sdiInternal.glAccountData.setSelectedItem(sdi.getGlName().trim());		 			 
		 sdiInternal.agentNameData.setSelectedItem(sdiInternal.getLeName(sdi.getAgentId()));		
		 sdiInternal.agentAcTextField.setText(String.valueOf(sdi.getAccountID()));
		 sdiInternal.agentContactData.setSelectedItem(sdi.getAgentContacts());
		 sdiInternal.InterM1Data.setSelectedItem(sdiInternal.getLeName(sdi.getInterMid1()));
		 sdiInternal.InterM1Contacts.setSelectedItem(sdi.getInterMid1Contact());
		 sdiInternal.im1AccountTextField.setText(sdi.getInterMid1glName());
		 sdiInternal.InterM2Data.setSelectedItem(sdiInternal.getLeName(sdi.getInterMid2()));
		 sdiInternal.InterM2Contacts.setSelectedItem(sdi.getInterMid2Contact());
		 sdiInternal.im2AccountTextField.setText(sdi.getInterMid2glName());
		 
			
	 }
	 
	 private void setupMainComponents() {
	        //String TITLE = "SDI Definition";
		//	setTitle(TITLE);
	        setupContractStatusBar();
	     //   setupFutureContractStatusBar();
	      
	    }
	 public SDIWindow() {
			
	        try {
	            jbInit();
	        } catch (Exception e) {
	        //    Log.error(this, e);
	        }
	      //  initDomains();
	    }
	
	 
	 private void initBottomButtonsActionListeners() {

			saveButton.setToolTipText(saveButton.getName());
			saveButton.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent evt) {
					
					Sdi sdi = new Sdi();
				
					boolean isProper = validateAndFillSDI(sdi);
					
					if (isProper) {
												
						sdi.setId(Integer.parseInt(selectSDI.getText().toString()));
						
						try {
							sdi = (Sdi)  remoteRef.saveSDI(sdi)	;
							
							if(sdi.getId() > 0 ) {
								commonUTIL.showAlertMessage("SDI Saved With ID: " + sdi.getId());
								selectSDI.setText(String.valueOf(sdi.getId()));
							}
							
						} catch (RemoteException e) {
							e.printStackTrace();
						}
						
					}

				}

			});

			closeButton.setToolTipText(closeButton.getName());
			closeButton.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent evt) {
					System.out.println("Close");
				}

			});

			saveAsNewButton.setToolTipText(saveAsNewButton.getName());
			saveAsNewButton.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent evt) {
		
					Sdi sdi = new Sdi();
					
					boolean fieldsOK = validateAndFillSDI(sdi);
					
					if (fieldsOK) {						
						try {
							sdi = (Sdi)  remoteRef.saveSDI(sdi)	;
							if(sdi.getId() > 0 ) {
								commonUTIL.showAlertMessage("SDI Saved With ID" + sdi.getId());
								selectSDI.setText(String.valueOf(sdi.getId()));
							}
						} catch (RemoteException e) {
							e.printStackTrace();
						}
					} else {						
						return;
					}
										
				}

			});

			deleteButton.setToolTipText(deleteButton.getName());
			deleteButton.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent evt) {
					
					Sdi sdi = new Sdi();
					int id  = Integer.parseInt(selectSDI.getText());
					
					sdi.setId(id);
					 
					 try {
							boolean isRemoved = remoteRef.removeSDI(sdi)	;
							
							if( isRemoved ) {																
								commonUTIL.showAlertMessage("SDI deleted with ID: " + id);
								clearSDIWindow();
								model.delRow(sdiTables.getSelectedRow());
							}
							
						} catch (RemoteException e) {
							e.printStackTrace();
						}
				
				}

			});

			newButton.setToolTipText(newButton.getName());
			newButton.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent evt) {
					clearSDIWindow();
				}

			});

			loadButton.setToolTipText(loadButton.getName());
			loadButton.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent evt) {
				//	System.out.println("Load" +propertyTable.getBeneficiary().getId());
				}

			});

		}
	 public static void main(String args[]) {
		// SDIWindowNewWindow c1 = new SDIWindowNewWindow();
		 //c1.setSize(900, 900);
		 //c1.setVisible(true);
	 }
	 protected void jbInit() throws Exception {
	     //   super();
		 remoteAccount = RemoteServiceUtil.getRemoteAccountService();
		 remoteRef = RemoteServiceUtil.getRemoteReferenceDataService();
 	          createLayout();
	       
	    }
	 private void setupContractStatusBar(){
		// sdiStatusBar.setLayout(FlowLayout.FlowLayout);
		 FlowLayout layout = new FlowLayout();
	    	layout.setAlignment(FlowLayout.LEFT);
	    	sdiStatusBar.setLayout(layout);
	        sdiStatusBar.add(sdiLabel);
	        if (selectSDI == null){
	        	selectSDI = new NumericTextField();
	        }
	        selectSDI.disable();
	        selectSDI.addActionListener(getContractSelectorComboBoxActionListener());
	        selectSDI.setPreferredSize(new Dimension(100, selectSDI.getPreferredSize().height));
	    	sdiStatusBar.add(selectSDI);
	      //  contractStatusBar.add(futureButtons.get(FutureButtonType.NEW));
	    }

	
	 
	 class TableModelSDIUtil extends AbstractTableModel {   
		    
		 final String[] columnNames;  
		    
		 final Vector<Sdi> data;   
		
		
		        
		 public TableModelSDIUtil( Vector<Sdi> myData,String col [] ) {   
		 	this.columnNames = col;
		this.data = myData;   
		
		}   

		    
		 public int getColumnCount() {   
		     return columnNames.length;   
		 }   
		    
		 public int getRowCount() {   
		    if (data != null) {
		    	return data.size();
		    } else {
		    	return 0;
		    }
			    
		 }   
		 public String getColumnName(int col) {   
		     return columnNames[col];   
		 }  
		
		 public Object getValueAt(int row, int col) {   
		     Object value = null;  	 
		      
		     Sdi fc = (Sdi) data.get(row);
			 switch (col) {
		     case 0:
		         value =   fc.getId();
		         break;
		     case 1:
		         value =  getLEName(fc.getCpId());
		         break;
		     case 2:
			    	
		         value = fc.getRole();
		         break;
		     case 3:
		    	
		         value = getLEName( fc.getPoId());
		         break;
		    
			 
		 case 4:
	         value =  getLEName( fc.getAgentId());
	         break;
	     case 5:
	         value =  fc.getCurrency();
	         break;
	     case 6:
	    	
	         value =  fc.getProducts();
	         break;
	     case 7:
	         value =   fc.getLeContacts();
	         break;
	     case 8:
	         value =  fc.getAccountID();
	         break;
	     case 9:
	    	
	         value =  fc.getGlName();
	         break;
	     case 10:
		    	
	         value =  getLEName(fc.getInterMid1());
	         break;
	     case 11:
		    	
	         value =  getLEName(fc.getInterMid2());
	         break;
		 }
		     return value;
		 }   
		 
		 
		   
		 
		 
		 public boolean isCellEditable(int row, int col) {   
		 return false;   
		 }   
		 public void setValueAt(Object value, int row, int col) {   		       
			 if(value instanceof Book) {
				 data.set(row,(Sdi) value) ;
				 this.fireTableDataChanged();   
		     }      
		 }   
		    
		 public void addRow(Object value) {     
			 data.add((Sdi) value) ;
			 this.fireTableDataChanged();   		   
		 }   
		    
		 public void delRow(int row) {   		    
			 data.remove(row);          
			 this.fireTableDataChanged();   		    
		 }   
		 
		 public void udpateValueAt(Object value, int row, int col) {   
		     data.set(row,(Sdi) value) ;
		     fireTableCellUpdated(row, col);   
		     System.out.println("New value of data:");   	    
		}   
	}
	 public String getLEName(int id) {
		   String name = "";
		   try {
			   if(id ==  0) 
				   return name;
			   LegalEntity le = (LegalEntity )remoteRef.selectLE(id);
			   name = le.getName();
		   } catch (RemoteException e) {			
			   e.printStackTrace();
		   }
		   
		   return name;
	   }
	 
	 protected JPanel makeSearchPanel(String name) {
	        String productWindowName = "apps.window.referencewindow.searchPanel." + name + "SearchPanel";

	        JPanel productWindow = null;
	        try {
	        Class class1 =    ClassInstantiateUtil.getClass(productWindowName,true);
	        return (JPanel) class1.newInstance();
	           //  productWindow = (BondPanel) 
	        } catch (Exception e) {
	            System.out.println( e);
	        }

	        return productWindow;
	    }
	 private void clearSDIWindow() {
		 
		 selectSDI.setText("");
		 
		 sdiInternal.rolesData.setSelectedIndex(-1);
		 sdiInternal.beneficiaryData.setSelectedIndex(-1);
		 sdiInternal.currencyData.setSelectedIndex(-1); 
		 //sdiInternal.payrecData.setSelectedIndex(-1);
		 sdiInternal.productTypeData.setSelectedIndex(-1);
		 sdiInternal.beneficiaryLecontactsData.setSelectedIndex(-1);
		 sdiInternal.cashsecurityData.setSelectedIndex(-1);
		 sdiInternal.jPreferred.setSelected(false);
		 sdiInternal.priorityTextField.setText(CommonConstants.BLANKSTRING);
		 sdiInternal.messsgeMethodData.setSelectedIndex(-1);
		 sdiInternal.poData.enable(true);			 
		 sdiInternal.poData.setSelectedItem(null);
		 sdiInternal.glAccountData.setSelectedIndex(-1);
		 sdiInternal.poContactData.setSelectedIndex(-1);
		 sdiInternal.agentNameData.setSelectedItem(null);		
		 sdiInternal.agentAcTextField.setText(CommonConstants.BLANKSTRING);
		 sdiInternal.agentContactData.setSelectedItem(null);
		 sdiInternal.InterM1Data.setSelectedItem(null);
		 sdiInternal.InterM1Contacts.setSelectedItem(null);
		 sdiInternal.im1AccountTextField.setText(CommonConstants.BLANKSTRING);
		 sdiInternal.InterM2Data.setSelectedItem(null);
		 sdiInternal.InterM2Contacts.setSelectedItem(null);
		 sdiInternal.im2AccountTextField.setText(CommonConstants.BLANKSTRING);
		
	 }
		 
		 private boolean validateAndFillSDI(Sdi sdi) {
			 
			 boolean flag = false;
			 
			 if (sdiInternal.rolesData.getSelectedIndex() == -1) {
				 
				 commonUTIL.showAlertMessage("Please select Role");
				 return flag;
				 
			 }
			 sdi.setRole(sdiInternal.rolesData.getSelectedItem().toString());
			 		 
			 if (sdiInternal.beneficiaryData.getSelectedIndex() == -1) {
				 
				 commonUTIL.showAlertMessage("Please select Beneficiary");
				 return flag;
				 
			 }
			 sdi.setCpId(sdiInternal.getLeId(sdiInternal.beneficiaryData.getSelectedItem().toString()));
			 
			 if (sdiInternal.currencyData.getSelectedIndex() == -1) {
				 
				 commonUTIL.showAlertMessage("Please select Currency");
				 return flag;
				 
			 }
			 sdi.setCurrency(sdiInternal.currencyData.getSelectedItem().toString());
			 
			 if (sdiInternal.payrecData.getSelectedIndex() == -1) {
				 
				 commonUTIL.showAlertMessage("Please select Pay/Rec");
				 return flag;
				 
			 }
			 sdi.setPayrec(sdiInternal.payrecData.getSelectedItem().toString());
			 
			 if (sdiInternal.productTypeData.getSelectedIndex() == -1) {
				 
				 commonUTIL.showAlertMessage("Please select ProductType");
				 return flag;
				 
			 }
			 sdi.setProducts(sdiInternal.productTypeData.getSelectedItem().toString());
			 
			 if (sdiInternal.beneficiaryLecontactsData.getSelectedIndex() > -1) {
				 
				 sdi.setLeContacts(sdiInternal.beneficiaryLecontactsData.getSelectedItem().toString());
				 
			 }
			 
			 if (sdiInternal.cashsecurityData.getSelectedIndex() > -1) {
				 
				 sdi.setCash(sdiInternal.cashsecurityData.getSelectedItem().toString());
				 
			 }
			 
			 if (sdiInternal.messsgeMethodData.getSelectedIndex() > -1) {
				 
				 sdi.setMessageType(sdiInternal.messsgeMethodData.getSelectedItem().toString());
				 
			 }
			 
			 if (!sdiInternal.priorityTextField.getText().toString().equals(CommonConstants.BLANKSTRING)) {
					
					sdi.setPriority((Integer.parseInt(sdiInternal.priorityTextField.getText().toString())));
			 }
			 
			 if (sdiInternal.jPreferred.isSelected()) {
				 sdi.setPreferred(0);
			 }
			 
			 if (sdiInternal.messsgeMethodData.getSelectedIndex() > -1) {
				 sdi.setMessageType(sdiInternal.messsgeMethodData.getSelectedItem().toString());
			 }
			 if (sdiInternal.poData.isEnabled()) {
				 
				 if (sdiInternal.poData.getSelectedItem() == null) {
					 
					 commonUTIL.showAlertMessage("Please select Processing Organization");
					 return flag;
					 
				 }
				 sdi.setPoId(sdiInternal.getLeId(sdiInternal.poData.getSelectedItem().toString()));
				 
				 if (sdiInternal.poContactData.getSelectedItem() == null) {
					 
					 return flag;
					 
				 }					 
				sdi.setPoContact(sdiInternal.poContactData.getSelectedItem().toString());				 

			 }
				 
			 if (sdiInternal.glAccountData.getSelectedIndex() > -1) {
				 				 
				 sdi.setGlName(sdiInternal.glAccountData.getSelectedItem().toString());
				 
			 }
			 
			 if (sdiInternal.agentNameData.getSelectedItem() == null){
				 
				 commonUTIL.showAlertMessage("Please select Agent");
				 return flag;
				 
			 }
			 sdi.setAgentId(sdiInternal.getLeId(sdiInternal.agentNameData.getSelectedItem().toString()));
			 
			if (!sdiInternal.agentAcTextField.getText().toString().equals(CommonConstants.BLANKSTRING)) {
				
				sdi.setAccountID(Integer.parseInt(sdiInternal.agentAcTextField.getText().toString()));
			}
			 
			 if (sdiInternal.agentContactData.getSelectedIndex() > -1){
				 
				 sdi.setAgentContacts(sdiInternal.agentContactData.getSelectedItem().toString());
				 
			 }
			 
			 if (sdiInternal.InterM1Data.getSelectedIndex() > -1){
				 
				 sdi.setInterMid1(sdiInternal.getLeId(sdiInternal.InterM1Data.getSelectedItem().toString()));
				 
					if (sdiInternal.InterM1Contacts.getSelectedIndex() > -1) {
						
						sdi.setInterMid1Contact(sdiInternal.InterM1Contacts.getSelectedItem().toString());
					}
					 
					 if (!sdiInternal.im1AccountTextField.getText().toString().equals(CommonConstants.BLANKSTRING)){
						 
						 sdi.setInterMid1glName(sdiInternal.im1AccountTextField.getText().toString());
						 
					 }
				 
			 }
			 
			 if (sdiInternal.InterM2Data.getSelectedIndex() > -1){
				 
				 sdi.setInterMid2(sdiInternal.getLeId((sdiInternal.InterM2Data.getSelectedItem().toString())));
				 
					if (sdiInternal.InterM2Contacts.getSelectedIndex() > -1) {
						
						sdi.setInterMid2Contact(sdiInternal.InterM2Contacts.getSelectedItem().toString());
					}
					 
					 if (!sdiInternal.im2AccountTextField.getText().toString().equals(CommonConstants.BLANKSTRING)){
						 
						 sdi.setInterMid2glName(sdiInternal.im2AccountTextField.getText().toString());
						 
					 }
				 
			 }
			 
			 return true;
		 
		 }
		
}

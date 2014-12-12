package apps.window.referencewindow;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
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
import apps.window.utilwindow.propertypane.SDIProperty;
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
import com.jidesoft.utils.Lm;
import com.jidesoft.utils.SystemInfo;

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
		RemoteReferenceData remoteRef = null;
		FutureProduct futureProduct = null;
		protected JLabel dateLabel = new JLabel("Currency");
		protected JTextField dateText = new JTextField();
		protected JButton loadSDI = new JButton("Load SDI");
		protected JButton saveSDI = new JButton("Save SDI");
		protected JButton deleteSDI = new JButton("Delete SDI");
	    protected sdiSearchPanel sdiSearch = null;
		private JPanel buttonsPanel = new JPanel();
		
		private JScrollPane scrollPane = null;
		JTable sdiTables = new JTable();
		 
		private SDIPropertyTable propertyTable = null;
		
		
		
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
				propertyTable.getSDIPropertyTable(beneficiary);
				
				
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
			 data.clear();
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
	      scrollPane  = new JScrollPane(v,h);
	      scrollPane.setAutoscrolls(true);
	      scrollPane.getViewport().add(sdiTables);
	      JPanel centerPanel = new JPanel(new BorderLayout());
	      centerPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null,
					null));
	      centerPanel.add(scrollPane, BorderLayout.CENTER);
	  
	       
	       JPanel tLeftPanel = new JPanel();
	       tLeftPanel.setLayout(new BorderLayout());
	       tLeftPanel.add(sdiStatusBar, BorderLayout.NORTH);
	       tLeftPanel.add(sdiPropertiesPanel);
	       tLeftPanel.add(buttonsPanel, BorderLayout.SOUTH);
	       
	      createButtonsPanel();
	      createPropertyTablesForFutureContract();
	    
	      setupMainComponents();
	   
	      final JFrame frame = new JFrame(" - JIDE " + Lm.getProductVersion() + " on JDK " + SystemInfo.getJavaVersion());      
			frame.addWindowListener(new WindowAdapter() {        
				@Override        
				public void windowClosing(WindowEvent e) {         
				//	if (demo instanceof AnimatedDemo) {                
					//	((AnimatedDemo) demo).stopAnimation();      
						//}               
					//demo.dispose();    
					}       
				});
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
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mousePressed(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseExited(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseEntered(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseClicked(MouseEvent event) {
					// TODO Auto-generated method stub
					if(sdiTables.getSelectedRow() == -1)
						return;
					int rowindex = sdiTables.getSelectedRow();

					Sdi sdi = (Sdi) data.get(rowindex);
					setAndShowSDI(sdi);
					
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
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}  catch(RemoteException r) {
					// TODO Auto-generated catch block
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
			// TODO Auto-generated catch block
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return name;
	 }
	 private void setAndShowSDI(Sdi sdidata) {
			// TODO Auto-generated method stub
		 
		
		
			
	//		 Sdi   sdidata = (Sdi) remoteRef.selectSDI(sdi);
			// futureProduct =(FutureProduct) fproduct.get(0);
			 propertyTable.setValueAt(sdidata.getRole(), 1, 1);
			 
			 propertyTable.setValueAt(getLegalEntity(sdidata.getCpId(),2), 2, 1);
			
			 propertyTable.setValueAt(sdidata.getCurrency(), 3, 1);
			 propertyTable.setValueAt(sdidata.getProducts(), 4, 1);
			 propertyTable.setValueAt(sdidata.getLeContacts(), 5, 1);
			 propertyTable.setValueAt(sdidata.getMessageType(), 6, 1);
			 propertyTable.setValueAt(sdidata.getCash(), 7, 1);
			 propertyTable.setValueAt(sdidata.getPayrec(), 8, 1);
			 propertyTable.setValueAt(getLegalEntity(sdidata.getPoId(),9), 9, 1);
			 if(sdidata.getPreferred() == 1) {
				 
				SDIProperty propertym =  (SDIProperty) propertyTable.getPropertyTableModel().getPropertyAt(11);
				System.out.println(propertym.getValue());
			 propertyTable.setValueAt(propertym, 11, 1);
			 } else  {
				 SDIProperty propertym =  (SDIProperty) propertyTable.getPropertyTableModel().getPropertyAt(11);
				 System.out.println(propertym.getValue());
					propertym.setValue(Boolean.TRUE);
					 propertyTable.setValueAt(propertym, 11, 1);
			 }
			 
			 propertyTable.setValueAt(sdidata.getPriority(), 12, 1);
			 propertyTable.setValueAt(getLegalEntity(sdidata.getAgentId(),13), 13, 1);
			 propertyTable.setValueAt(getAccountName(sdidata.getAccountID(),14), 14, 1);
			 propertyTable.setValueAt(sdidata.getGlName(), 15, 1);
			 propertyTable.setValueAt(sdidata.getAgentContacts(), 16, 1);
		     if(sdidata.getInterMid1() != 0) {
			 propertyTable.setValueAt(getLegalEntity(sdidata.getInterMid1(),17), 17, 1);
			 propertyTable.setValueAt(getAccountName(sdidata.getInterMid1account(),18), 18, 1);
			 propertyTable.setValueAt(sdidata.getInterMid1Contact(), 19, 1);
			 propertyTable.setValueAt(sdidata.getInterMid1glName(), 20, 1);
		     } 
		     if(sdidata.getInterMid2() != 0) {
			 propertyTable.setValueAt(getLegalEntity(sdidata.getInterMid2(),21), 21, 1);
			 propertyTable.setValueAt(getAccountName(sdidata.getInterMid2account(),22), 22, 1);
			 propertyTable.setValueAt(sdidata.getInterMid2Contact(), 23, 1);
			 propertyTable.setValueAt(sdidata.getInterMid2glName(), 24, 1);
		     }
		   
			
		}
	 
	 private void setupMainComponents() {
	        String TITLE = "SDI Definition";
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
					System.out.println("Save");

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
					//System.out.println("Save As New");
					System.out.println(propertyTable.getValueAt(1, 1) + "++++++");
					System.out.println(propertyTable.getValueAt(2, 1) +"----");
					Sdi sdi = new Sdi();
					sdi.setCpId(propertyTable.getBeneficiary().getId());
					sdi.setPoId(propertyTable.getPo().getId());
					sdi.setAgentId(propertyTable.getAgent().getId());
					
					sdi.setRole((String) propertyTable.getValueAt( 1, 1));
			 
			
			sdi.setCurrency((String) propertyTable.getValueAt(3, 1));
			sdi.setProducts((String) propertyTable.getValueAt(4, 1));
			sdi.setLeContacts((String) propertyTable.getValueAt(5, 1));
			sdi.setMessageType((String) propertyTable.getValueAt(6, 1));
			sdi.setCash((String) propertyTable.getValueAt(7, 1));
			sdi.setPayrec((String) propertyTable.getValueAt(8, 1));
			if(propertyTable.isPreferredFlag())
				 sdi.setPreferred(1);
			else 
				sdi.setPreferred(0);
			sdi.setPriority( propertyTable.getPriority());
		//	sdi.setPayrec((String) propertyTable.getValueAt(14, 1));
		//	sdi.setPayrec((String) propertyTable.getValueAt(15, 1));
			sdi.setGlName((String) propertyTable.getValueAt(15, 1));
			sdi.setAgentContacts((String) propertyTable.getValueAt(16, 1));
			if(propertyTable.getAccount() != null)
                          sdi.setAccountID(propertyTable.getAccount().getCpId());
			 if(propertyTable.getInterAgent1() != null) {
				 sdi.setInterMid1Contact((String) propertyTable.getValueAt(19, 1));
				 sdi.setInterMid1glName((String) propertyTable.getValueAt(20, 1));
				
				 sdi.setInterMid1(propertyTable.getInterAgent1().getId());
			     } 
			 if(propertyTable.getInterAgent2() != null) {
				 sdi.setInterMid2Contact((String) propertyTable.getValueAt(21, 1));
				 sdi.setInterMid2glName((String) propertyTable.getValueAt(23, 1));
				
				 sdi.setInterMid2(propertyTable.getInterAgent2().getId());
			     } 
			
					// remoteProduct = RemoteServiceUtil.getRemoteProductService();
			try {
				sdi = (Sdi)  remoteRef.saveSDI(sdi)	;
				if(sdi.getId() > 0 ) {
					commonUTIL.showAlertMessage("SDI Saved With ID" + sdi.getId());
					selectSDI.setText(String.valueOf(sdi.getId()));
				}
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
					
				}

			});

			deleteButton.setToolTipText(deleteButton.getName());
			deleteButton.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent evt) {
					System.out.println("delete");
				}

			});

			newButton.setToolTipText(newButton.getName());
			newButton.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent evt) {
					System.out.println("New");
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
		     return data.size();   
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
			// TODO Auto-generated catch block
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
	 
}

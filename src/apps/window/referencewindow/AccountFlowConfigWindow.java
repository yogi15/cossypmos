package apps.window.referencewindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;

import com.standbysoft.component.date.swing.JDatePicker;

import util.commonUTIL;

import apps.window.utilwindow.JDialogBoxForChoice;
import apps.window.utilwindow.JDialogTable;
import beans.AccConfigRule;
import beans.Account;
import beans.LegalEntity;
import beans.StartUPData;
import beans.Trade;
import dsServices.RemoteAccount;
import dsServices.RemoteProduct;
import dsServices.RemoteReferenceData;
import dsServices.ServerConnectionUtil;

//VS4E -- DO NOT REMOVE THIS LINE!
public class AccountFlowConfigWindow extends JPanel {

	private static final long serialVersionUID = 1L;
	public static  ServerConnectionUtil de = null;
	RemoteReferenceData remoteBORef; 
	RemoteAccount remoteAccount;
	RemoteProduct remoteProduct;
	AccConfigRule rule = null;
	FolderLink folderLink;
	int poID =0;
	private JPanel jPanel3;
	private JButton jButton0;
	private JButton jButton1;
	private JButton jButton2;
	private JButton jButton4;
	private JButton jButton3;
	private JPanel jPanel5;
	private JTable jTable0;
	private JScrollPane jScrollPane0;
	private JLabel jLabel0;
	private JLabel jLabel1;
	private JLabel jLabel2;
	private JLabel jLabel3;
	private JTextField jTextField0;
	private JTextField jTextField2;
	private JDatePicker jTextField3;
	private JTextField jTextField1;
	private JPanel jPanel4;
	private JPanel jPanel0;
	private JLabel jLabel4;
	private JLabel jLabel5;
	private JTextField jTextField4;
	private JTextField jTextField5;
	private JLabel jLabel6;
	private JButton jButton5;
	private JButton jButton6;
	private JComboBox jComboBox0;
	private JTabbedPane jTabbedPane0;
	AccEventLinkPanel accEventLink;
	DefaultListModel currencyList = new DefaultListModel();
	javax.swing.DefaultComboBoxModel accConfigType = new javax.swing.DefaultComboBoxModel();
	javax.swing.DefaultComboBoxModel poModel = new javax.swing.DefaultComboBoxModel();
	
	public AccountFlowConfigWindow() {
		initData();
		initComponents();
	}

	private void initComponents() {
		setLayout(new GroupLayout());
		add(getJTabbedPane0(), new Constraints(new Leading(12, 847, 10, 10), new Leading(10, 509, 10, 10)));
		setSize(873, 529);
	}

	private JTabbedPane getJTabbedPane0() {
		if (jTabbedPane0 == null) {
			jTabbedPane0 = new JTabbedPane();
			jTabbedPane0.addTab("RULE Details", getJPanel0());
		jTabbedPane0.addTab("Folder Link", getJPanel1());
			jTabbedPane0.addTab("AccEvent Link", getJPanel2());
		}
		return jTabbedPane0;
	}
	
	
private JPanel getJPanel2() {
		
		
		
		
		return accEventLink;
		
		
		
	}

	private JPanel getJPanel1() {
		
		
		
		
		return folderLink;
		
		
		
	}

	private JComboBox getJComboBox0() {
		if (jComboBox0 == null) {
			jComboBox0 = new JComboBox();
			jComboBox0.setModel(accConfigType);
			jComboBox0.setDoubleBuffered(false);
			jComboBox0.setBorder(null);
			jComboBox0.setSelectedIndex(0);
		}
		return jComboBox0;
	}

	private JButton getJButton6() {
		if (jButton6 == null) {
			jButton6 = new JButton();
			jButton6.setText("POBUT");
		}
		String poCOL [] = {"id","POName"};
    	DefaultTableModel potablemodel = new DefaultTableModel(poCOL,0);
		final  JDialogTable showPO = new JDialogTable(potablemodel);
       
        showPO.setLocationRelativeTo(this);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	
            	String poCOL [] = {"id","POName"};
            	DefaultTableModel potablemodel = new DefaultTableModel(poCOL,0);
            	 getLEDataCombo1(potablemodel,"PO");
            	 showPO.jTable1.setModel(potablemodel);
            	showPO.setVisible(true);
            	
            }
        }); 
        showPO.jTable1.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				int id  = ((Integer)	showPO.jTable1.getValueAt(showPO.jTable1.getSelectedRow(),0)).intValue();
			
				 String ss = (String)	showPO.jTable1.getValueAt(showPO.jTable1.getSelectedRow(),1);
				 jTextField1.setText(ss);
				 poID = id;
				 showPO.dispose();
			}
			
    
    	
    });
		return jButton6;
	}

	private JButton getJButton5() {
		if (jButton5 == null) {
			jButton5 = new JButton();
			jButton5.setText("curr");
		}processlistchoice(currencyList,"Currency");
       	final JDialogBoxForChoice choice12 = new JDialogBoxForChoice(currencyList);
       	jButton5.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				choice12.jList3.setModel(currencyList);
				choice12.setLocationRelativeTo(choice12);
				//choice12.setSize(200,200);
				choice12.setVisible(true);
				
			}
   		
   	});
    	choice12.addWindowListener(new WindowAdapter() {            
               public void windowClosing(WindowEvent e) {
                  // System.out.println("Window closing");
                   try {
                   	String ss = "";
                     Object obj [] =  choice12.cmodList2.toArray();
                    for(int i =0;i<obj.length;i++)
                   	 ss = ss + (String) obj[i] + ",";
                    if(ss.trim().length() > 0)
                    	jTextField0.setText(ss.substring(0, ss.length()-1));
                   } catch (Throwable t) {
                       t.printStackTrace();
                   }                
               }
       	});
		return jButton5;
	}

	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jPanel3 = new JPanel();
			jPanel3.setLayout(new GroupLayout());
			jPanel3.add(getJLabel4(), new Constraints(new Leading(25, 52, 10, 10), new Leading(15, 10, 10)));
			jPanel3.add(getJLabel5(), new Constraints(new Leading(27, 52, 10, 10), new Leading(49, 12, 12)));
			jPanel3.add(getJTextField4(), new Constraints(new Leading(95, 33, 10, 10), new Leading(12, 22, 12, 12)));
			jPanel3.add(getJTextField5(), new Constraints(new Leading(97, 123, 10, 10), new Leading(49, 22, 12, 12)));
			jPanel3.add(getJLabel6(), new Constraints(new Leading(349, 112, 10, 10), new Leading(15, 12, 12)));
			jPanel3.add(getJComboBox0(), new Constraints(new Leading(434, 126, 10, 10), new Leading(15, 30, 10, 10)));
		}
		return jPanel3;
	}

	private JLabel getJLabel6() {
		if (jLabel6 == null) {
			jLabel6 = new JLabel();
			jLabel6.setText("Rule Type");
		}
		return jLabel6;
	}

	private JTextField getJTextField5() {
		if (jTextField5 == null) {
			jTextField5 = new JTextField();
			//jTextField5.setText("   ");
		}
		return jTextField5;
	}

	private JTextField getJTextField4() {
		if (jTextField4 == null) {
			jTextField4 = new JTextField();
			jTextField4.setText("0");
			jTextField4.setEditable(false);
		}
		return jTextField4;
	}

	private JLabel getJLabel5() {
		if (jLabel5 == null) {
			jLabel5 = new JLabel();
			jLabel5.setText("Name");
		}
		return jLabel5;
	}

	private JLabel getJLabel4() {
		if (jLabel4 == null) {
			jLabel4 = new JLabel();
			jLabel4.setText("ID");
		}
		return jLabel4;
	}

	private JPanel getJPanel0() {
		if (jPanel0 == null) {
			jPanel0 = new JPanel();
			jPanel0.setLayout(new GroupLayout());
			jPanel0.add(getJPanel3(), new Constraints(new Leading(21, 798, 10, 10), new Leading(18, 100, 10, 10)));
			jPanel0.add(getJPanel5(), new Constraints(new Leading(26, 795, 10, 10), new Leading(426, 46, 10, 10)));
			jPanel0.add(getJPanel4(), new Constraints(new Leading(12, 704, 10, 10), new Leading(139, 10, 10)));
		}
		return jPanel0;
	}

	private JPanel getJPanel4() {
		if (jPanel4 == null) {
			jPanel4 = new JPanel();
			jPanel4.setLayout(new GroupLayout());
			jPanel4.add(getJLabel0(), new Constraints(new Leading(26, 10, 10), new Leading(30, 10, 10)));
			jPanel4.add(getJLabel1(), new Constraints(new Leading(24, 52, 10, 10), new Leading(67, 10, 10)));
			jPanel4.add(getJLabel2(), new Constraints(new Leading(24, 52, 12, 12), new Leading(107, 10, 10)));
			jPanel4.add(getJLabel3(), new Constraints(new Leading(24, 86, 10, 10), new Leading(145, 10, 10)));
			jPanel4.add(getJTextField0(), new Constraints(new Leading(128, 75, 10, 10), new Leading(30, 22, 12, 12)));
			jPanel4.add(getJTextField2(), new Constraints(new Leading(130, 38, 10, 10), new Leading(105, 26, 12, 12)));
			jPanel4.add(getJTextField3(), new Constraints(new Leading(128, 103, 10, 10), new Leading(143, 25, 10, 10)));
			jPanel4.add(getJTextField1(), new Constraints(new Leading(128, 138, 10, 10), new Leading(67, 26, 12, 12)));
			jPanel4.add(getJButton5(), new Constraints(new Leading(219, 40, 10, 10), new Leading(29, 12, 12)));
			jPanel4.add(getJButton6(), new Constraints(new Leading(286, 40, 10, 10), new Leading(67, 12, 12)));
			jPanel4.add(getJScrollPane0(), new Constraints(new Leading(401, 272, 10, 10), new Leading(2, 236, 12, 12)));
		}
		return jPanel4;
	}

	private JTextField getJTextField1() {
		if (jTextField1 == null) {
			jTextField1 = new JTextField();
			//jTextField1.setText("");
			jTextField1.setEditable(false);
			
		}
		return jTextField1;
	}

	private JDatePicker getJTextField3() {
		if (jTextField3 == null) {
			jTextField3 = new JDatePicker();
			jTextField3.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
		}
		return jTextField3;
	}

	private JTextField getJTextField2() {
		if (jTextField2 == null) {
			jTextField2 = new JTextField();
			jTextField2.setText("0");
		}
		return jTextField2;
	}

	private JTextField getJTextField0() {
		if (jTextField0 == null) {
			jTextField0 = new JTextField();
		//	jTextField0.setText("curr");
			jTextField0.setEditable(false);
			jTextField0.setText("INR");
		}
		return jTextField0;
	}

	private JLabel getJLabel3() {
		if (jLabel3 == null) {
			jLabel3 = new JLabel();
			jLabel3.setText("DateStartup");
		}
		return jLabel3;
	}

	private JLabel getJLabel2() {
		if (jLabel2 == null) {
			jLabel2 = new JLabel();
			jLabel2.setText("Days Adj");
		}
		return jLabel2;
	}

	private JLabel getJLabel1() {
		if (jLabel1 == null) {
			jLabel1 = new JLabel();
			jLabel1.setText("PO");
		}
		return jLabel1;
	}

	private JLabel getJLabel0() {
		if (jLabel0 == null) {
			jLabel0 = new JLabel();
			jLabel0.setText("Currency");
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

	private JTable getJTable0() {
		if (jTable0 == null) {
			jTable0 = new JTable();
			jTable0.setModel(new DefaultTableModel(new Object[][] { { "0x0", "0x1", }, { "1x0", "1x1", }, }, new String[] { "Title 0", "Title 1", }) {
				private static final long serialVersionUID = 1L;
				Class<?>[] types = new Class<?>[] { Object.class, Object.class, };
	
				public Class<?> getColumnClass(int columnIndex) {
					return types[columnIndex];
				}
			});
		}
		return jTable0;
	}

	private JPanel getJPanel5() {
		if (jPanel5 == null) {
			jPanel5 = new JPanel();
			jPanel5.setLayout(new GroupLayout());
			jPanel5.add(getJButton0(), new Constraints(new Leading(19, 10, 10), new Leading(12, 12, 12)));
			jPanel5.add(getJButton1(), new Constraints(new Leading(125, 12, 12), new Leading(12, 12, 12)));
			jPanel5.add(getJButton2(), new Constraints(new Leading(225, 12, 12), new Leading(12, 12, 12)));
			jPanel5.add(getJButton4(), new Constraints(new Leading(368, 10, 10), new Leading(12, 12, 12)));
			jPanel5.add(getJButton3(), new Constraints(new Leading(460, 10, 10), new Leading(12, 12, 12)));
		}
		return jPanel5;
	}

	private JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new JButton();
			jButton3.setText("DELETE");
		}jButton3.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent arg0) {
	        	
	             try {
					if(remoteAccount.deleteAccConfigRule(rule)) {
						 commonUTIL.showAlertMessage(" Rule Deleted ");
					 }
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	      	 
	           
	        }
	    });
		return jButton3;
	}

	private JButton getJButton4() {
		if (jButton4 == null) {
			jButton4 = new JButton();
			jButton4.setText("LOAD");
		}
		
		
   	
		String s [] = {"ID","RuleName"};
	final	DefaultTableModel tablemodel = new DefaultTableModel(s,0) {
   		 @Override
   		    public boolean isCellEditable(int row, int column) {
   		       //all cells false
   		       return false;
   		    }

   	};
   	final  JDialogTable showAllrules = new JDialogTable(tablemodel);
   	showAllrules.setLocationRelativeTo(this);
    	
    		

    		
    		showAllrules.jTable1.addMouseListener(new java.awt.event.MouseAdapter() {

    			@Override
    			public void mouseClicked(MouseEvent e) {
    				String ruleid = showAllrules.jTable1.getValueAt(showAllrules.jTable1.getSelectedRow(),0).toString();
    				int id  =new  Integer(ruleid).intValue();
    				try {
    					
    						 rule = (AccConfigRule) remoteAccount.getaccConfigRule(id);
    						 openAccConfigRule(rule);
    						 showAllrules.setVisible(false);
    				} catch (RemoteException e1) {
    					// TODO Auto-generated catch block
    					e1.printStackTrace();
    				
    				}
    			}
    			  });
    		jButton4.addActionListener(new ActionListener() {
    	        public void actionPerformed(ActionEvent arg0) {
    	        	
    	      	showAllrules.jTable1.removeAll();
    	      	String s [] = {"ID","RuleName"};
    	    	final	DefaultTableModel tablemodel = new DefaultTableModel(s,0) {
    	       		 @Override
    	       		    public boolean isCellEditable(int row, int column) {
    	       		       //all cells false
    	       		       return false;
    	       		    }

    	       	};
    	      	processTableDataOpen(tablemodel);
    	        	//processTableDataOpen(tablemodel);
    	        	showAllrules.jTable1.setModel(tablemodel);
    	        	showAllrules.setVisible(true);
    	      	 
    	           
    	        }
    	    });
    	
		return jButton4;
	}

	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setText("SAVE AS NEW");
		}jButton2.addActionListener(new ActionListener() {
			
			@Override
			

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				
					
				AccConfigRule rule = new AccConfigRule();
				rule.setId(0);
				fillAccConfigRule(rule);
				try {
					int id = remoteAccount.saveAccConfigRule(rule);
					rule.setId(id);
					jTextField4.setText(new Integer(rule.getId()).toString());
					
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			
		}); 
		return jButton2;
	}

	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("SAVE");
		}jButton1.addActionListener(new ActionListener() {
			
			@Override
			

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				
					
				AccConfigRule rule = new AccConfigRule();
				rule.setId(new Integer(jTextField4.getText()).intValue());
				fillAccConfigRule(rule);
				try {
					boolean flag = remoteAccount.updateAccConfigRule(rule);
					if(flag)
						commonUTIL.showAlertMessage("Rule Updated");
					
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			
		}); 
		return jButton1;
	}

	private JButton getJButton0() {
		if (jButton0 == null) {
			jButton0 = new JButton();
			jButton0.setText("CLEAR");
		}
		return jButton0;
	}
	
	public void initData() {
		de =ServerConnectionUtil.connect("localhost", 1099,commonUTIL.getServerIP() );
     	 try {
     		remoteBORef = (RemoteReferenceData) de.getRMIService("ReferenceData");
     		remoteAccount = (RemoteAccount)  de.getRMIService("Account");
     		remoteProduct = (RemoteProduct)  de.getRMIService("Product");
     		 folderLink = new FolderLink(de);
     		accEventLink = new AccEventLinkPanel(de);
     		getMasterDataOnComboBox(accConfigType,"AccConfigType");
     		
     		 		  
     		
  	  	 } catch (RemoteException e) {
  			e.printStackTrace();
  	}
	}
	
	public void fillAccConfigRule(AccConfigRule rule) {
		if(commonUTIL.isEmpty(jTextField5.getText())) {
			commonUTIL.showAlertMessage("Enter Flow Name" );
			return;
		}
		
		if(poID == 0) {
			commonUTIL.showAlertMessage("Select legal Entity ");
			return;
		}
		rule.setRuleName(jTextField5.getText());
		rule.setCurrency(jTextField0.getText());
		rule.setPoID(poID);
		rule.setCalDate(jTextField3.getSelectedDateAsText());
		rule.setDayAdj(new Integer(jTextField2.getText()).intValue());
	//	rule.setDailyClosing();
		rule.setRuleTYpe(accConfigType.getSelectedItem().toString());
	}
	
	public void openAccConfigRule(AccConfigRule rule)  {
		jTextField5.setText(rule.getRuleName());
		jTextField0.setText(rule.getCurrency());
		 jTextField1.setText(((LegalEntity)getLeName(rule.getPoID())).getName());
		 poID = rule.getPoID();
		 jTextField3.setSelectedDate(commonUTIL.stringToDate(rule.getCalDate(), true));
		 accConfigType.setSelectedItem(rule.getRuleTYpe());
		 jTextField2.setText(new Integer(rule.getDayAdj()).toString());
		 jTextField4.setText(new Integer(rule.getId()).toString());
		 this.rule = rule;
		
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
	    
	private final void getMasterDataOnComboBox( javax.swing.DefaultComboBoxModel combodata,String name) {
		Vector vector = null;
		try {
			vector = (Vector) remoteBORef.getStartUPData(name);
			
			if(vector.size() > 0) {
			Iterator it = vector.iterator();
	    	int i =0;
	    	//combodata.insertElementAt(" ", 0);
	    	
	    	while(it.hasNext()) {
	    		
	    		StartUPData data = (StartUPData) it.next();
	    	
			
				
			combodata.insertElementAt(data.getName(), i);
			i++;
		}	
	    	
			}
		}catch (RemoteException e) {
					// TODO Auto-generated catch block
				
		commonUTIL.displayError("SDIWIndow","getMasterDataOnComboBox", e);
		}
	}
	
	private void processTableDataOpen(DefaultTableModel model) {
		// TODO Auto-generated method stub
    	Vector vector;
		try {
			
			
			vector = (Vector) remoteAccount.getAllAccountConfigRules();
			Iterator it = vector.iterator();
	    	int i =0;
	    	while(it.hasNext()) {
	    		
	    		AccConfigRule rule = (AccConfigRule) it.next();
	    	
	    		model.insertRow(i, new Object[]{rule.getId(),rule.getRuleName()});
	    		i++;
	    		}
	    		
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

public void processlistchoice(DefaultListModel list,String name ) {
	Vector vector;
	try {
		vector = (Vector) remoteBORef.getStartUPData(name);
		
		if(vector.size() > 0) {
		Iterator it = vector.iterator();
    	int i =0;
    	while(it.hasNext()) {
    		
    		StartUPData data = (StartUPData) it.next();
    	
		
			
    		list.addElement(data.getName());

		i++;
	}	
		}
	}catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			
}catch(Exception e) {
	commonUTIL.displayError("SDIWINDOW","getMasterDataOnComboBox", e);
}
	
}


public void getLEDataCombo1(DefaultTableModel model,String leRole) {
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
	}catch (RemoteException e) {
				// TODO Auto-generated catch block
		commonUTIL.displayError("AccountWindow","getLEDataCombo1", e);
			}
	
	
}

}

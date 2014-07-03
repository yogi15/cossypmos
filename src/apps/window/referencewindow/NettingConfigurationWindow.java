package apps.window.referencewindow;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;
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
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

import org.dyno.visual.swing.layouts.Bilateral;
import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;

import apps.window.utilwindow.JDialogBoxForChoice;
import beans.Fees;
import beans.Folder;
import beans.LegalEntity;
import beans.NettingConfig;
import beans.StartUPData;

import com.standbysoft.component.date.swing.JDatePicker;

import util.commonUTIL;
import dsServices.RemoteReferenceData;
import dsServices.ServerConnectionUtil;

//VS4E -- DO NOT REMOVE THIS LINE!
public class NettingConfigurationWindow extends JPanel {

	private static final long serialVersionUID = 1L;
	public static  ServerConnectionUtil de = null;
	RemoteReferenceData remoteBORef;
	private JPanel jPanel0;
	private JButton jButton0;
	private JButton jButton1;
	private JButton jButton2;
	private JButton jButton3;
	private JButton jButton4;
	private JPanel jPanel2;
	private JLabel jLabel0;
	private JComboBox jComboBox0;
	private JPanel jPanel3;
	private JLabel jLabel1;
	private JLabel jLabel2;
	private JComboBox jComboBox1;
	private JLabel jLabel3;
	private JDatePicker jTextField0;
	private JTextField jTextField1;
	private JButton jButton5;
	private JLabel jLabel4;
	private JTable jTable1;
	private JScrollPane jScrollPane2;
	private JPanel jPanel5;
	private static final String PREFERRED_LOOK_AND_FEEL = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
	javax.swing.DefaultComboBoxModel  leEntity;
	javax.swing.DefaultComboBoxModel  productType; 
	DefaultListModel currencyList;
	TableModelUtil dataModel;
	Vector<NettingConfig> myData = new Vector<NettingConfig> ();
	String col [] = {"NettingID","ProductType","CounterParty","Currency","EffectiveData"};
	Hashtable leID = null;
	public NettingConfigurationWindow() {
		initComponents();
	}

	private void initComponents() {
		
		leID = new Hashtable();
		productType = new javax.swing.DefaultComboBoxModel();
		leEntity = new javax.swing.DefaultComboBoxModel();
		currencyList = new DefaultListModel();
		init();
		setLayout(new GroupLayout());
		add(getJPanel0(), new Constraints(new Leading(5, 956, 10, 10), new Leading(6, 502, 10, 10)));
		setSize(969, 517);
	}

	private JPanel getJPanel5() {
		if (jPanel5 == null) {
			jPanel5 = new JPanel();
			jPanel5.setBorder(new LineBorder(Color.lightGray, 1, false));
			jPanel5.setLayout(new GroupLayout());
			jPanel5.add(getJScrollPane2(), new Constraints(new Leading(11, 907, 10, 10), new Leading(8, 255, 10, 10)));
		}
		return jPanel5;
	}

	private JScrollPane getJScrollPane2() {
		if (jScrollPane2 == null) {
			jScrollPane2 = new JScrollPane();
			jScrollPane2.setViewportView(getJTable1());
		}
		return jScrollPane2;
	}

	private JTable getJTable1() {
		if (jTable1 == null) {
			jTable1 = new JTable();
			dataModel = new TableModelUtil(myData, col, remoteBORef);
			jTable1.setModel(dataModel);
		}jTable1.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				int selectRow = jTable1.getSelectedRow();
				NettingConfig nt = (NettingConfig) myData.get(selectRow);
				//Integer ss = (Integer) jTable0.getValueAt(rowindex, 0);
				
				jComboBox0.setSelectedIndex(getCounterPartyName(nt.getLeid()));
				jComboBox1.setSelectedItem((String) nt.getProductType());
				jTextField0.setSelectedDate(commonUTIL.stringToDate(nt.getEffectiveDate(), false));
				jTextField1.setText(nt.getCurrency());
				
				
			}
        
        	
        });
		return jTable1;
	}

	private JLabel getJLabel4() {
		if (jLabel4 == null) {
			jLabel4 = new JLabel();
			jLabel4.setText("Currency");
		}
		return jLabel4;
	}

	private JButton getJButton5() {
		if (jButton5 == null) {
			jButton5 = new JButton();
			jButton5.setText("...");
		} processlistchoice(currencyList,"Currency");
     	final JDialogBoxForChoice choice12 = new JDialogBoxForChoice(currencyList);
     	jButton5.addActionListener(new ActionListener() {

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
                  Object obj [] =   choice12.getObj();
                 for(int i =0;i<obj.length;i++)
                	 ss = ss + (String) obj[i] + ",";
                 if(ss.trim().length() > 0)
                 	jTextField1.setText(ss.substring(0, ss.length()-1));
                } catch (Throwable t) {
                    t.printStackTrace();
                }                
            }
    	});
	
	
		return jButton5;
	}

	private JTextField getJTextField1() {
		if (jTextField1 == null) {
			jTextField1 = new JTextField();
			jTextField1.setText("INR");
			jTextField1.setEditable(false);
		}
		return jTextField1;
	}

	private JDatePicker getJTextField0() {
		if (jTextField0 == null) {
			jTextField0  = new JDatePicker();
			jTextField0.setDateFormat(commonUTIL.getDateFormat());
			jTextField0.setSelectedDate(commonUTIL.getCurrentDate());
		}
		return jTextField0;
	}

	private JLabel getJLabel3() {
		if (jLabel3 == null) {
			jLabel3 = new JLabel();
			jLabel3.setText("Effective Date");
		}
		return jLabel3;
	}

	private JComboBox getJComboBox1() {
		if (jComboBox1 == null) {
			jComboBox1 = new JComboBox();
			jComboBox1.setModel(productType);
		}
		return jComboBox1;
	}

	private JLabel getJLabel2() {
		if (jLabel2 == null) {
			jLabel2 = new JLabel();
			jLabel2.setText("ProductType");
		}
		return jLabel2;
	}

	private JLabel getJLabel1() {
		if (jLabel1 == null) {
			jLabel1 = new JLabel();
			jLabel1.setText("Currency");
		}
		return jLabel1;
	}

	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jPanel3 = new JPanel();
			jPanel3.setLayout(new GroupLayout());
			jPanel3.add(getJLabel0(), new Constraints(new Leading(10, 98, 10, 10), new Leading(12, 24, 10, 10)));
			jPanel3.add(getJComboBox0(), new Constraints(new Leading(126, 199, 10, 10), new Leading(14, 25, 10, 10)));
			jPanel3.add(getJLabel1(), new Constraints(new Leading(343, 12, 12), new Leading(19, 12, 12)));
			jPanel3.add(getJLabel2(), new Constraints(new Leading(10, 98, 12, 12), new Leading(59, 24, 10, 10)));
			jPanel3.add(getJComboBox1(), new Constraints(new Leading(124, 199, 10, 10), new Leading(59, 25, 12, 12)));
			jPanel3.add(getJLabel3(), new Constraints(new Leading(10, 98, 12, 12), new Leading(98, 24, 10, 10)));
			jPanel3.add(getJTextField0(), new Constraints(new Leading(126, 198, 12, 12), new Leading(99, 22, 12, 12)));
			jPanel3.add(getJTextField1(), new Constraints(new Leading(389, 69, 10, 10), new Leading(17, 22, 12, 12)));
			jPanel3.add(getJButton5(), new Constraints(new Leading(489, 54, 10, 10), new Leading(16, 12, 12)));
			jPanel3.add(getJLabel4(), new Constraints(new Leading(339, 53, 10, 10), new Leading(60, 24, 12, 12)));
		}
		return jPanel3;
	}

	private JComboBox getJComboBox0() {
		if (jComboBox0 == null) {
			jComboBox0 = new JComboBox();
			jComboBox0.setModel(leEntity);
		}
		return jComboBox0;
	}

	private JLabel getJLabel0() {
		if (jLabel0 == null) {
			jLabel0 = new JLabel();
			jLabel0.setText("CounterParty");
		}
		return jLabel0;
	}

	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setBorder(new LineBorder(Color.lightGray, 1, true));
			jPanel2.setForeground(Color.white);
			jPanel2.setLayout(new GroupLayout());
			jPanel2.add(getJButton0(), new Constraints(new Leading(12, 12, 12), new Leading(10, 10, 10)));
			jPanel2.add(getJButton1(), new Constraints(new Leading(81, 55, 10, 10), new Leading(10, 12, 12)));
			jPanel2.add(getJButton2(), new Constraints(new Leading(148, 73, 10, 10), new Leading(10, 12, 12)));
			jPanel2.add(getJButton3(), new Constraints(new Leading(233, 12, 12), new Leading(10, 12, 12)));
			jPanel2.add(getJButton4(), new Constraints(new Leading(344, 10, 10), new Leading(10, 12, 12)));
		}
		return jPanel2;
	}

	private JButton getJButton4() {
		if (jButton4 == null) {
			jButton4 = new JButton();
			jButton4.setText("SAVE");
		}jButton4.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int rowSelected = -1;
				rowSelected = jTable1.getSelectedRow();
				if(rowSelected == -1) {
					return;
				}
				NettingConfig netConfig = myData.get(rowSelected);
				
				fillData(netConfig);
				try {
					remoteBORef.updateNettingConfig(netConfig);
					dataModel.setValueAt(netConfig, jTable1.getSelectedRow(), jTable1.getSelectedColumn());
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					commonUTIL.displayError("NettingConfigWindow", "Save ", e1);
				}
			}
		});
		return jButton4;
	}

	
	private int getCounterPartyName(int id) {
		int selectIndex = 0;
		for(int i=0;i<leID.size();i++) {
			LegalEntity le = (LegalEntity) leID.get(i);
			if(le.getId() == id)  {
				selectIndex = i;
				break;
			}
		}
		return selectIndex;
		
	}
	private JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new JButton();
			jButton3.setText("SAVE AS NEW");
		}jButton3.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				NettingConfig netConfig = new NettingConfig();
				netConfig.setId(0);
				fillData(netConfig);
				try {
					int i = remoteBORef.saveNettingConfig(netConfig);
					netConfig.setId(i);
					dataModel.addRow(netConfig);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					commonUTIL.displayError("NettingConfigWindow", "Save as New", e1);
				}
				
					
				
				
			}
		});
		return jButton3;
	}

	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setText("DELETE");
		}jButton2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				int rowSelected = -1;
				rowSelected = jTable1.getSelectedRow();
				if(rowSelected == -1) {
					return;
				}
				NettingConfig netConfig = myData.get(rowSelected);
				
				fillData(netConfig);
				try {
					if(remoteBORef.deleteNettingConfig(netConfig.getId()))
						dataModel.delRow(rowSelected);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					commonUTIL.displayError("NettingConfigWindow", "Delete ", e1);
				}
			}
		});
		return jButton2;
	}

	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("ADD");
		}
		return jButton1;
	}

	private JButton getJButton0() {
		if (jButton0 == null) {
			jButton0 = new JButton();
			jButton0.setText("NEW");
		}
		return jButton0;
	}

	private JPanel getJPanel0() {
		if (jPanel0 == null) {
			jPanel0 = new JPanel();
			jPanel0.setLayout(new GroupLayout());
			jPanel0.add(getJPanel3(), new Constraints(new Bilateral(12, 12, 553), new Leading(9, 128, 10, 10)));
			jPanel0.add(getJPanel2(), new Constraints(new Leading(18, 721, 10, 10), new Leading(440, 10, 10)));
			jPanel0.add(getJPanel5(), new Constraints(new Leading(12, 934, 12, 12), new Leading(155, 273, 12, 12)));
		}
		return jPanel0;
	}
	
	public void fillData(NettingConfig config ) {
		if(jComboBox0.getSelectedIndex() == -1 ) {
			commonUTIL.showAlertMessage("Select CounterParty");
			return;
		}
		if(jComboBox1.getSelectedIndex() == -1 ) {
			commonUTIL.showAlertMessage("Select ProductType");
			return;
		}
		LegalEntity le = (LegalEntity) leID.get(jComboBox0.getSelectedIndex());
		
		config.setLeid(le.getId());
		config.setProductType((String) jComboBox1.getSelectedItem());
		config.setCurrency(jTextField1.getText());
		config.setEffectiveDate(jTextField0.getSelectedDateAsText());
		
		
	}
	
	public void init() {
		Vector roles = null;
    	de =ServerConnectionUtil.connect("localhost", 1099,commonUTIL.getServerIP() );
      	 try {
      		 remoteBORef = (RemoteReferenceData) de.getRMIService("ReferenceData");
      		 myData = (Vector) remoteBORef.getALLtNettingConfig();
      		 
      		
      	//	jTable1.setModel(dataModel);
      		//  Vector productTypes = (Vector)  remoteBORef.getStartUPData("ProductType");
      		getMasterDataOnComboBox(productType,"ProductType");
      		getLEDataCombo1(leEntity,"CounterParty");
   	  	 } catch (RemoteException e) {
   			e.printStackTrace();
   	}
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
				
		commonUTIL.displayError("NettingConfigurationWindow","getMasterDataOnComboBox", e);
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
	commonUTIL.displayError("NettingConfigurationWindow","processlistchoice", e);
}
	
}
	public void getLEDataCombo1(DefaultComboBoxModel model,String leRole) {
		Vector vector;
		try {
			for(int l=0;l<leID.size();l++)
				leID.remove(l);
					
				vector = (Vector) remoteBORef.selectLEonWhereClause(" role like '%"+leRole + "%'");
			   Iterator it = vector.iterator();
	    	   int i =0;
	    	while(it.hasNext()) {
	    		
	    		LegalEntity le =	(LegalEntity) it.next();
	    		
	    		leID.put(i, le);
	    	
	    		model.insertElementAt(le.getName(), i);
	    		i++;
			
		}	
		}catch (RemoteException e) {
					// TODO Auto-generated catch block
			commonUTIL.displayError("NettingConfigurationWindow","getLEDataCombo1", e);
				}
		
		
	}


class TableModelUtil extends AbstractTableModel {   
    
	 final String[] columnNames;  
	    
	 Vector<NettingConfig> data;   
	 RemoteReferenceData remoteRef ;
	        
	 public TableModelUtil( Vector<NettingConfig> myData,String col [],RemoteReferenceData remoteRef ) {   
	 	this.columnNames = col;
	this.data = myData;   
	this.remoteRef = remoteRef;
	}   

	    
	 public int getColumnCount() {   
	     return columnNames.length;   
	 }   
	    
	 public int getRowCount() {   
		 if(data != null)
	     return data.size();   
		 return 0;
	 }   
	 public String getColumnName(int col) {   
	     return columnNames[col];   
	 }   
	 public Object getValueAt(int row, int col) {   
	     Object value = null;  	 
	 
	     NettingConfig netConfig = (NettingConfig) data.get(row);
	    
		 switch (col) {
	     case 0:
	         value = netConfig.getId();
	         break;
	     case 1:
	         value =netConfig.getProductType();
	         break;
	     case 2:
	    	LegalEntity le = (LegalEntity) getLeName(netConfig.getLeid());
	         value =  le.getName();
	         break;
	     case 3:
	         value =netConfig.getCurrency();
	         break;
	     case 4:
	         value =netConfig.getEffectiveDate();
	         break;
	     
		 }
	     return value;
	 }   
	   
	 public boolean isCellEditable(int row, int col) {   
	 return false;   
	 }   
	 public void setValueAt(Object value, int row, int col) {   
	         System.out.println("Setting value at " + row + "," + col   
	                            + " to " + value   
	                            + " (an instance of "    
	                            + value.getClass() + ")");  
	         if(value instanceof Folder) {
	     data.set(row,(NettingConfig) value) ;
	     this.fireTableDataChanged();   
	         System.out.println("New value of data:");   
	         }
	        
	 }   
	    
	 public void addRow(Object value) {   
	    
		 data.add((NettingConfig) value) ;
	 this.fireTableDataChanged();   
	   
	 }   
	    
	 public void delRow(int row) {   
	    if(row != -1) {
	 data.remove(row);          
	 this.fireTableDataChanged();   
	    }
	    
	 }   
	 
	 public void udpateValueAt(Object value, int row, int col) {   
	     
	  
	     data.set(row,(NettingConfig) value) ;
	     for(int i=0;i<columnNames.length;i++)
	    	 	fireTableCellUpdated(row, i);   
	    
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
	    	if(data != null) {
	  	  data.removeAllElements();
	    	} 
	    data = null;
	  	 this.fireTableDataChanged();  
	    }
}


}

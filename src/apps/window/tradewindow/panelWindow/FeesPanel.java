package apps.window.tradewindow.panelWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.util.Hashtable;
import java.util.Iterator;
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

import org.dyno.visual.swing.layouts.Bilateral;
import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;

import util.NumericTextField;
import util.commonUTIL;
import apps.window.tradewindow.BackOfficePanel;
import apps.window.utilwindow.JDialogBoxForChoice;
import beans.Fees;
import beans.Folder;
import beans.LegalEntity;
import beans.StartUPData;
import beans.Trade;

import com.standbysoft.component.date.swing.JDatePicker;

import dsServices.RemoteReferenceData;
import dsServices.RemoteTrade;
import dsServices.ServerConnectionUtil;

//VS4E -- DO NOT REMOVE THIS LINE!
public class FeesPanel  extends BackOfficePanel  {

	private static final long serialVersionUID = 1L;
	RemoteTrade remotetrade = null;
	private JLabel jLabel1;
	private JLabel jLabel4;
	private JDatePicker jTextField0;
	private JComboBox jComboBox0;
	private JLabel jLabel0;
	private NumericTextField jTextField4;
	private JTextField jTextField3;
	private JComboBox jComboBox1;
	private JDatePicker jTextField1;
	private JDatePicker  jTextField2;
	private JLabel jLabel2;
	private JLabel jLabel5;
	private JLabel jLabel6;
	private JComboBox jComboBox2;
	private JLabel jLabel3;
	private JButton jButton0;
	private JTable jTable0;
	private JScrollPane jScrollPane0;
	private JButton jButton1;
  public Vector feesDataV = new Vector();
	 TableModelUtil model = null;
	public Vector getFeesDataV() {
		feesDataV.removeAllElements();
		for(int i=0;i<feesData.size();i++)  {
			Fees fee =(Fees) feesData.get(i);
			feesDataV.addElement(fee);
		}
		//System.out.println(feesDataV.size());
		return feesDataV;
	}

	
	private JButton jButton2;
	javax.swing.DefaultComboBoxModel  payRec = new javax.swing.DefaultComboBoxModel();
	javax.swing.DefaultComboBoxModel  curre = new javax.swing.DefaultComboBoxModel();
	javax.swing.DefaultComboBoxModel  role = new javax.swing.DefaultComboBoxModel();
	javax.swing.DefaultComboBoxModel  feesTyp = new javax.swing.DefaultComboBoxModel();
	javax.swing.DefaultComboBoxModel  leEntity = new javax.swing.DefaultComboBoxModel();
	 String feesColumnName [] =    {"FeesType ", "Pay/Rec","LE Name","Amount","Fee Date","Start Date","End Date","Currency","LERole"};
	 LegalEntity  entityID = null;
	 Vector<Fees> feesData = new Vector();
     
	 DefaultTableModel   feeseModel = new DefaultTableModel(feesColumnName,0);
	DefaultListModel currencyList = new DefaultListModel();

	Hashtable leID = new Hashtable();
	   DefaultTableModel tmodel; 
	    RemoteReferenceData referenceData;
	    public static  ServerConnectionUtil de = null;
		private JComboBox jComboBox3;
		private JButton jButton3;
		private JPanel jPanel0;
		private JPanel jPanel1;
		private JPanel jPanel2;
		private static final String PREFERRED_LOOK_AND_FEEL = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
		public FeesPanel() {
		initData();
		initComponents();
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
			add(getJPanel0(), new Constraints(new Bilateral(12, 12, 695), new Leading(5, 179, 10, 10)));
			add(getJPanel1(), new Constraints(new Bilateral(13, 12, 396), new Leading(190, 54, 12, 12)));
			add(getJPanel2(), new Constraints(new Bilateral(13, 13, 48), new Leading(252, 251, 10, 10)));
			setSize(1178, 533);
		}

	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			jPanel2.setLayout(new GroupLayout());
			jPanel2.add(getJScrollPane0(), new Constraints(new Bilateral(9, 12, 23), new Leading(14, 226, 10, 10)));
		}
		return jPanel2;
	}

	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			jPanel1.setLayout(new GroupLayout());
			jPanel1.add(getJButton0(), new Constraints(new Leading(87, 80, 10, 10), new Bilateral(7, 12, 23)));
			jPanel1.add(getJButton2(), new Constraints(new Leading(296, 86, 10, 10), new Bilateral(8, 12, 23)));
			jPanel1.add(getJButton1(), new Constraints(new Leading(198, 66, 10, 10), new Bilateral(7, 12, 23)));
		}
		return jPanel1;
	}

	private JPanel getJPanel0() {
		if (jPanel0 == null) {
			jPanel0 = new JPanel();
			jPanel0.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			jPanel0.setLayout(new GroupLayout());
			jPanel0.add(getJTextField0(), new Constraints(new Leading(490, 139, 10, 10), new Leading(29, 25, 10, 10)));
			jPanel0.add(getJTextField3(), new Constraints(new Leading(490, 139, 10, 10), new Leading(121, 25, 10, 10)));
			jPanel0.add(getJTextField1(), new Constraints(new Leading(490, 139, 10, 10), new Leading(59, 25, 10, 10)));
			jPanel0.add(getJTextField2(), new Constraints(new Leading(490, 139, 10, 10), new Leading(91, 25, 12, 12)));
			jPanel0.add(getJButton3(), new Constraints(new Leading(639, 42, 10, 10), new Leading(121, 10, 10)));
			jPanel0.add(getJTextField4(), new Constraints(new Leading(77, 139, 10, 10), new Leading(72, 25, 12, 12)));
			jPanel0.add(getJLabel3(), new Constraints(new Leading(22, 10, 10), new Leading(111, 12, 12)));
			jPanel0.add(getJComboBox0(), new Constraints(new Leading(77, 139, 10, 10), new Leading(29, 29, 12, 12)));
			jPanel0.add(getJLabel5(), new Constraints(new Leading(25, 56, 12, 12), new Leading(77, 10, 10)));
			jPanel0.add(getJComboBox1(), new Constraints(new Leading(225, 10, 10), new Leading(33, 29, 12, 12)));
			jPanel0.add(getJLabel0(), new Constraints(new Leading(20, 10, 10), new Leading(37, 12, 12)));
			jPanel0.add(getJComboBox2(), new Constraints(new Leading(77, 139, 10, 10), new Leading(106, 26, 12, 12)));
			jPanel0.add(getJComboBox3(), new Constraints(new Leading(230, 87, 10, 10), new Leading(108, 24, 10, 10)));
			jPanel0.add(getJLabel1(), new Constraints(new Leading(388, 90, 10, 10), new Leading(29, 18, 10, 10)));
			jPanel0.add(getJLabel2(), new Constraints(new Leading(388, 90, 12, 12), new Leading(61, 18, 12, 12)));
			jPanel0.add(getJLabel6(), new Constraints(new Leading(388, 90, 12, 12), new Leading(93, 18, 10, 10)));
			jPanel0.add(getJLabel4(), new Constraints(new Leading(388, 90, 12, 12), new Leading(121, 18, 10, 10)));
			fillData();
		}
		return jPanel0;
	}

	private JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new JButton();
			jButton3.setText("...");
			 processlistchoice(currencyList,"Currency");
         	final JDialogBoxForChoice choice12 = new JDialogBoxForChoice(currencyList);
         	jButton3.addActionListener(new ActionListener() {

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
                      Object obj [] =  choice12.getObj();
                     for(int i =0;i<obj.length;i++)
                    	 ss = ss + (String) obj[i] + ",";
                     if(ss.trim().length() > 0)
                     	jTextField3.setText(ss.substring(0, ss.length()-1));
                    } catch (Throwable t) {
                        t.printStackTrace();
                    }                
                }
        	});
  	
		}
		return jButton3;
	}

	private JComboBox getJComboBox3() {
		if (jComboBox3 == null) {
			jComboBox3 = new JComboBox();
		//	jComboBox3.setModel(new DefaultComboBoxModel(new Object[] { "item0", "item1", "item2", "item3" }));
			jComboBox3.setDoubleBuffered(false);
			jComboBox3.setBorder(null);
		}
		jComboBox3.addItemListener( new ItemListener() {

        	@Override
        	public void itemStateChanged(ItemEvent e) {
        		// TODO Auto-generated method stub
        		
        		
        	//	clearRolesTables();
        		jComboBox2.removeAllItems();
        		leEntity.removeAllElements();
        		
        		String role = jComboBox3.getSelectedItem().toString();
        		
        		 getLEDataCombo1(leEntity,role);
        		 jComboBox2.setModel(leEntity);
        		 jComboBox2.setSelectedIndex(0);
        		 entityID = (LegalEntity)  leID.get(jComboBox2.getSelectedIndex());
        	}
        	   
           });
		return jComboBox3;
	}

	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setText("MODIFY");
		}
		jButton2.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
			if(commonUTIL.isEmpty(jTextField4.getText())) {
				commonUTIL.showAlertMessage("Enter Value in amount");
				jTextField4.setFocusable(true);
				return;
			} else {
				int rowindex = jTable0.getSelectedRow(); 
				//Integer ss = (Integer) jTable0.getValueAt(rowindex, 0);
				if(rowindex == -1)
					return;
				Fees fee = (Fees) feesData.get(rowindex);
				
			
				fillFeesData(fee);
				
				//feesData.set(rowindex, fee);
				model.udpateValueAt(fee, jTable0.getSelectedRow(), jTable0.getSelectedColumn());	
				
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
		jButton1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				if(commonUTIL.isEmpty(jTextField4.getText())) {
					commonUTIL.showAlertMessage("Enter Value in amount");
					jTextField4.setFocusable(true);
					return;
				} else {
					Fees fee = new Fees();
					fee.setId(0);
					
					
					fillFeesData(fee);
					if(model == null) {
						model = new TableModelUtil(feesData,feesColumnName,referenceData);
						 
						jTable0.setModel(model);
					}
				
					model.addRow(fee);
					
				}
				
			}

			
		});
		return jButton1;
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
			model = new TableModelUtil(feesData,feesColumnName,referenceData);
			 
			jTable0.setModel(model);
		}
		
		jTable0.addMouseListener(new java.awt.event.MouseAdapter() {

				@Override
				public void mouseClicked(MouseEvent e) {
					int selectRow = jTable0.getSelectedRow();
					Fees fee = (Fees) feesData.get(selectRow);
					//Integer ss = (Integer) jTable0.getValueAt(rowindex, 0);
					
					jComboBox0.setSelectedItem(fee.getFeeType());
					jComboBox1.setSelectedItem(fee.getPayrec());
					jTextField3.setText(fee.getCurrency());
					jTextField4.setValue(fee.getAmount());
					jTextField0.setSelectedDate(commonUTIL.stringToDate(fee.getFeeDate(), false));
					jTextField1.setSelectedDate(commonUTIL.stringToDate(fee.getStartDate(), false));
					jTextField2.setSelectedDate(commonUTIL.stringToDate(fee.getEndDate(), false));
					
				}
	        
	        	
	        });
		return jTable0;
	}

	private JButton getJButton0() {
		if (jButton0 == null) {
			jButton0 = new JButton();
			jButton0.setText("DELETE");
		}jButton0.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				int rowIndex = jTable0.getSelectedRow();
				if(rowIndex == -1)
					return;
				//	feesData.remove(rowIndex);
					model.delRow(rowIndex);
					
				
					
				
				
			}

			
		});
		return jButton0;
	}

	private JLabel getJLabel3() {
		if (jLabel3 == null) {
			jLabel3 = new JLabel();
			jLabel3.setText("ENTITY");
		}
		return jLabel3;
	}

	private JComboBox getJComboBox2() {
		if (jComboBox2 == null) {
			jComboBox2 = new JComboBox();
		//	jComboBox2.setModel(new DefaultComboBoxModel(new Object[] { "item0", "item1", "item2", "item3" }));
			jComboBox2.setDoubleBuffered(false);
			jComboBox2.setBorder(null);
		}
		jComboBox2.addItemListener( new ItemListener() {

        	@Override
        	public void itemStateChanged(ItemEvent e) {
        		// TODO Auto-generated method stub
        		
        		
        	
        		entityID = (LegalEntity)  leID.get(jComboBox2.getSelectedIndex());
        	}
        	   
           });
		return jComboBox2;
	}

	private JLabel getJLabel6() {
		if (jLabel6 == null) {
			jLabel6 = new JLabel();
			jLabel6.setText("END DATE");
		}
		return jLabel6;
	}

	private JLabel getJLabel5() {
		if (jLabel5 == null) {
			jLabel5 = new JLabel();
			jLabel5.setText("AMOUNT");
		}
		return jLabel5;
	}

	private JLabel getJLabel2() {
		if (jLabel2 == null) {
			jLabel2 = new JLabel();
			jLabel2.setText("START DATE");
		}
		return jLabel2;
	}

	private JDatePicker getJTextField2() {
		if (jTextField2 == null) {
			jTextField2 = new JDatePicker();
			jTextField2.setDateFormat(commonUTIL.getDateFormat());
			
		}
		return jTextField2;
	}

	private JDatePicker getJTextField1() {
		if (jTextField1 == null) {
			jTextField1 = new JDatePicker();
			jTextField1.setDateFormat(commonUTIL.getDateFormat());
		}
		return jTextField1;
	}

	private JComboBox getJComboBox1() {
		if (jComboBox1 == null) {
			jComboBox1 = new JComboBox();
		//	jComboBox1.setModel(new DefaultComboBoxModel(new Object[] { "item0", "item1", "item2", "item3" }));
			jComboBox1.addItem("PAY");
			jComboBox1.addItem("REC");
			jComboBox1.setSelectedIndex(0);
		//	jComboBox1.setDoubleBuffered(false);
			//jComboBox1.setBorder(null);
		}
		return jComboBox1;
	}

	private JTextField getJTextField3() {
		if (jTextField3 == null) {
			jTextField3 = new JTextField();
			jTextField3.setText("INR");
			jTextField3.setEditable(false);
		}
		return jTextField3;
	}

	private NumericTextField getJTextField4() {
		if (jTextField4 == null) {
			jTextField4 = new NumericTextField();
			jTextField4.setText("0");
		}
		return jTextField4;
	}

	private JLabel getJLabel0() {
		if (jLabel0 == null) {
			jLabel0 = new JLabel();
			jLabel0.setText("FEETYPE");
		}
		return jLabel0;
	}

	private JComboBox getJComboBox0() {
		if (jComboBox0 == null) {
			jComboBox0 = new JComboBox();
		//	jComboBox0.setModel(new DefaultComboBoxModel(new Object[] { "item0", "item1", "item2", "item3" }));
			jComboBox0.setDoubleBuffered(false);
			jComboBox0.setBorder(null);
		}
		return jComboBox0;
	}

	private JDatePicker getJTextField0() {
		if (jTextField0 == null) {
			jTextField0 = new JDatePicker();
			jTextField0.setDateFormat(commonUTIL.getDateFormat());
		}
		return jTextField0;
	}

	private JLabel getJLabel4() {
		if (jLabel4 == null) {
			jLabel4 = new JLabel();
			jLabel4.setText("CURRENCY");
		}
		return jLabel4;
	}

	private JLabel getJLabel1() {
		if (jLabel1 == null) {
			jLabel1 = new JLabel();
			jLabel1.setText("FEE DATE");
		}
		return jLabel1;
	}

	@Override
	public void fillJTabel(Vector data) {
		// TODO Auto-generated method stub
		
	}
	
	public void initData() {
		de =ServerConnectionUtil.connect("localhost", 1099,commonUTIL.getServerIP() );
     	 try {
     		referenceData = (RemoteReferenceData) de.getRMIService("ReferenceData");
     		remotetrade = (RemoteTrade) de.getRMIService("Trade");
     		
     		
     		  
     		  
  	  	 } catch (RemoteException e) {
  			e.printStackTrace();
  	}
	}
	public void fillData() {
		 getMasterDataOnComboBox(role, "Roles");
			jComboBox3.setModel(role);
		 getMasterDataOnComboBox(feesTyp, "FEETYPE");
		jComboBox0.setModel(feesTyp);
		getMasterDataOnComboBox(curre, "Currency");
		jComboBox0.setSelectedIndex(0);
		//jComboBox3.setSelectedIndex(0);
	}
	private final void getMasterDataOnComboBox( javax.swing.DefaultComboBoxModel combodata,String name) {
		Vector vector = null;
		try {
			vector = (Vector) referenceData.getStartUPData(name);
			
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
				
		commonUTIL.displayError("FeesPanel","getMasterDataOnComboBox", e);
		}
	}
	
	public void getLEDataCombo1(DefaultComboBoxModel model,String leRole) {
		Vector vector;
		try {
			for(int l=0;l<leID.size();l++)
				leID.remove(l);
					
				vector = (Vector) referenceData.selectLEonWhereClause(" role like '%"+leRole + "%'");
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
			commonUTIL.displayError("FeesPanel","getLEDataCombo1", e);
				}
		
		
	}
	
	
	public void refreshFees() {
		model.removeALL();
		// TODO Auto-generated method stub
		
	}
	public void fillFeesData(Fees fee) {
		fee.setFeeType(jComboBox0.getSelectedItem().toString());
		fee.setPayrec(jComboBox1.getSelectedItem().toString());
		fee.setCurrency(jTextField3.getText());
		try {
			double dd = jTextField4.getDoubleValue();
			fee.setAmount(dd);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			commonUTIL.displayError("FeePanel", "fillFeesData", e1);
		}
		
		fee.setFeeDate(jTextField0.getSelectedDateAsText());
		fee.setStartDate(jTextField1.getSelectedDateAsText());
		fee.setEndDate(jTextField2.getSelectedDateAsText());
		fee.setLeID(entityID.getId());
		fee.setLeRole(jComboBox3.getSelectedItem().toString());
		//feesData.add(feesData.size(), fee);
		
		
		
	}

public void processlistchoice(DefaultListModel list,String name ) {
	Vector vector;
	try {
		vector = (Vector) referenceData.getStartUPData(name);
		
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
	commonUTIL.displayError("FeesPanel","getMasterDataOnComboBox", e);
}
	
}

public Trade getTrade() {
	
	return trade;
}

public void setTrade(Trade trade) {
	this.trade = trade;
	
		try {
		
			feesData = (Vector) remotetrade.selectFeesonTrade(trade.getId());
			refreshFees();
			if(feesData == null || feesData.isEmpty()) {
				feesData = new Vector<Fees>();
			}
			model = new TableModelUtil(feesData,feesColumnName,referenceData);
		//	model.data = feesData;
			jTable0.setModel(model);
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			commonUTIL.displayError("FeesPanel","setTrade", e);
		}

}



class TableModelUtil extends AbstractTableModel {   
    
	 final String[] columnNames;  
	    
	 Vector<Fees> myData;   
	 RemoteReferenceData remoteRef ;
	        
	 public TableModelUtil( Vector<Fees> myData,String col [],RemoteReferenceData remoteRef ) {   
	 	this.columnNames = col;
	this.myData = myData;   
	this.remoteRef = remoteRef;
	}   

	    
	 public int getColumnCount() {   
	     return columnNames.length;   
	 }   
	    
	 public int getRowCount() {   
		 if(myData != null)
	     return myData.size();   
		 return 0;
	 }   
	 public String getColumnName(int col) {   
	     return columnNames[col];   
	 }   
	 public Object getValueAt(int row, int col) {   
	     Object value = null;  	 
	 
	     Fees fee = (Fees) myData.get(row);
	    
		 switch (col) {
	     case 0:
	         value = fee.getFeeType();
	         break;
	     case 1:
	         value =fee.getPayrec();
	         break;
	     case 2:
	    	LegalEntity le = (LegalEntity) getLeName(fee.getLeID());
	         value =  le.getName();
	         break;
	     case 3:
	         value =fee.getAmount();
	         break;
	     case 4:
	         value = fee.getFeeDate();
	         break;
	     case 5:
	         value =fee.getStartDate();
	         break;
	     case 6:
	         value = fee.getEndDate();
	         break;
	     case 7:
	         value =fee.getCurrency();
	         break;
	     case 8:
	         value =fee.getLeRole();
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
	        	 myData.set(row,(Fees) value) ;
	     this.fireTableDataChanged();   
	         System.out.println("New value of data:");   
	         }
	        
	 }   
	    
	 public void addRow(Object value) {   
	    if(myData != null) {
	    	
	    	myData.add((Fees) value) ;
	 this.fireTableDataChanged();   
	    }
	   
	 }   
	    
	 public void delRow(int row) {   
	    if(row != -1) {
	    	myData.remove(row);          
	 this.fireTableDataChanged();   
	    }
	    
	 }   
	 
	 public void udpateValueAt(Object value, int row, int col) {   
	     
	  
		 myData.set(row,(Fees) value) ;
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
	    	if(myData != null) {
	    		myData.removeAllElements();
	    	} 
	    	myData = null;
	  	 this.fireTableDataChanged();  
	    }
}


  

	}   

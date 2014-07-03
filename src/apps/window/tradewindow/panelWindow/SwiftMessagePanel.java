package apps.window.tradewindow.panelWindow;

import java.rmi.RemoteException;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.table.AbstractTableModel;

import org.dyno.visual.swing.layouts.Bilateral;
import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;

import beans.Folder;
import beans.LegalEntity;
import bo.swift.SwiftFieldMessage;
import dsServices.RemoteReferenceData;


//VS4E -- DO NOT REMOVE THIS LINE!
public class SwiftMessagePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JScrollPane jScrollPane1;
	private JTable jTable0;
	private JScrollPane jScrollPane0;
	private JLabel jLabel0;
	public JTextField jTextField0;
	private JPanel jPanel0;
	public JTextField jTextField2;
	public JTextField jTextField1;
	private JLabel jLabel1;
	private JLabel jLabel2;
	TableModelUtil model = null;
	 String swiftColum [] =    {"FieldName ", "TAG "," Field Value "};
    Vector<SwiftFieldMessage> swiftFids = new  Vector<SwiftFieldMessage>();
	public JTextArea jTextArea0;
	private static final String PREFERRED_LOOK_AND_FEEL = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
	public SwiftMessagePanel() {
		initComponents();
	}

	private void initComponents() {
		setLayout(new GroupLayout());
		add(getJPanel0(), new Constraints(new Leading(7, 704, 10, 10), new Leading(8, 485, 10, 10)));
		setSize(721, 503);
	}

	private JTextArea getJTextArea0() {
		if (jTextArea0 == null) {
			jTextArea0 = new JTextArea();
			jTextArea0.setText("jTextArea0");
		}
		return jTextArea0;
	}

	private JLabel getJLabel2() {
		if (jLabel2 == null) {
			jLabel2 = new JLabel();
			jLabel2.setText("Sender");
		}
		return jLabel2;
	}

	private JLabel getJLabel1() {
		if (jLabel1 == null) {
			jLabel1 = new JLabel();
			jLabel1.setText("Receiver");
		}
		return jLabel1;
	}

	private JTextField getJTextField1() {
		if (jTextField1 == null) {
			jTextField1 = new JTextField();
			jTextField1.setEditable(false);
			jTextField1.setEnabled(false);
		}
		return jTextField1;
	}

	private JTextField getJTextField2() {
		if (jTextField2 == null) {
			jTextField2 = new JTextField();
			jTextField2.setEditable(false);
			jTextField2.setEnabled(false);
		}
		return jTextField2;
	}

	private JPanel getJPanel0() {
		if (jPanel0 == null) {
			jPanel0 = new JPanel();
			jPanel0.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			jPanel0.setLayout(new GroupLayout());
			jPanel0.add(getJScrollPane1(), new Constraints(new Bilateral(6, 12, 22), new Leading(239, 196, 10, 10)));
			jPanel0.add(getJScrollPane0(), new Constraints(new Leading(5, 687, 10, 10), new Leading(8, 225, 12, 12)));
			jPanel0.add(getJTextField2(), new Constraints(new Leading(503, 181, 10, 10), new Leading(447, 27, 12, 12)));
			jPanel0.add(getJTextField1(), new Constraints(new Leading(221, 170, 10, 10), new Leading(447, 26, 12, 12)));
			jPanel0.add(getJTextField0(), new Constraints(new Leading(41, 69, 10, 10), new Leading(447, 26, 12, 12)));
			jPanel0.add(getJLabel0(), new Constraints(new Leading(4, 31, 12, 12), new Leading(449, 22, 12, 12)));
			jPanel0.add(getJLabel1(), new Constraints(new Leading(134, 84, 10, 10), new Leading(449, 22, 12, 12)));
			jPanel0.add(getJLabel2(), new Constraints(new Leading(413, 84, 12, 12), new Leading(449, 22, 12, 12)));
		}
		return jPanel0;
	}

	private JTextField getJTextField0() {
		if (jTextField0 == null) {
			jTextField0 = new JTextField();
			jTextField0.setEditable(false);
			jTextField0.setEnabled(false);
		}
		return jTextField0;
	}

	private JLabel getJLabel0() {
		if (jLabel0 == null) {
			jLabel0 = new JLabel();
			jLabel0.setText("TYPE");
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
			model =new  TableModelUtil(swiftFids,swiftColum);
			jTable0.setModel(model);
		}
		return jTable0;
	}

	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getJTextArea0());
		}
		return jScrollPane1;
	}

	public void setSwiftData(Vector<SwiftFieldMessage> swiftFields) {
		jTable0.removeAll();
		model = null;		model =new  TableModelUtil(swiftFields,swiftColum);
		jTable0.setModel(model);
	}
	class TableModelUtil extends AbstractTableModel {   
	    
		 final String[] columnNames;  
		    
		 Vector<SwiftFieldMessage> myData;   
		 RemoteReferenceData remoteRef ;
		        
		 public TableModelUtil( Vector<SwiftFieldMessage> myData,String col []) {   
		 	this.columnNames = col;
		this.myData = myData;   
		
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
		 
		     SwiftFieldMessage swift = (SwiftFieldMessage) myData.get(row);
		    
			 switch (col) {
		     case 0:
		         value = swift.getName();
		         break;
		     case 1:
		         value =swift.getTAG();
		         break;
		     case 2:
		    	
		         value =  swift.getValue();
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
		        	 myData.set(row,(SwiftFieldMessage) value) ;
		     this.fireTableDataChanged();   
		         System.out.println("New value of data:");   
		         }
		        
		 }   
		    
		 public void addRow(Object value) {   
		    if(myData != null) {
		    	
		    	myData.add((SwiftFieldMessage) value) ;
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
		     
		  
			 myData.set(row,(SwiftFieldMessage) value) ;
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

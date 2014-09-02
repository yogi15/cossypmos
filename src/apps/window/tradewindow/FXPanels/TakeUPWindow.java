package apps.window.tradewindow.FXPanels;

import java.text.DecimalFormat;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.table.AbstractTableModel;

import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;

import util.NumericTextField;
import beans.Trade;

import com.standbysoft.component.date.swing.JDatePicker;


//VS4E -- DO NOT REMOVE THIS LINE!
public class TakeUPWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jPanel0;
	public JLabel jLabel0;
	public JLabel jLabel1;
	public JLabel jLabel4;
	public JLabel jLabel5;
	public NumericTextField jTextField0 = null;
	public NumericTextField jTextField1 = null;
	public JLabel jLabel10;
	public JLabel jLabel11;
	public NumericTextField jTextField2;
	public NumericTextField jTextField3;
	private JLabel jLabel12;
	private JLabel jLabel13;
	public JTextField jTextField5;
	public JTextField jTextField4;
	public JLabel jLabel14;
	public JDatePicker jTextField6;
	public JTextField jTextField7;
	public JLabel jLabel2;
	public JButton jButton0;
	public JButton jButton1;
	public JTable jTable0;
	public JScrollPane jScrollPane0;
	DecimalFormat format = new DecimalFormat("##,###,#######");
	public Vector<Trade> tableData = new Vector<Trade>();
    String col [] = {"TradeID","PrimaryCurr","QuotingCurr","Quantity","Nomnial","TradeDate","SettleDate"};
   public TableModelUtil model = null;
private static final String PREFERRED_LOOK_AND_FEEL = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
public TakeUPWindow() {
		initComponents();
	}

	private void initComponents() {
		setLayout(new GroupLayout());
		add(getJPanel0(), new Constraints(new Leading(6, 970, 10, 10), new Leading(6, 418, 10, 10)));
		setSize(987, 433);
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
			model = new TableModelUtil(tableData, col);
			jTable0.setModel(model);
			
		}
		return jTable0;
	}

	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("Edit");
		}
		return jButton1;
	}

	private JButton getJButton0() {
		if (jButton0 == null) {
			jButton0 = new JButton();
			jButton0.setText("Save");
		}
		return jButton0;
	}

	private JLabel getJLabel2() {
		if (jLabel2 == null) {
			jLabel2 = new JLabel();
			jLabel2.setText("FWD Option EndDate");
		}
		return jLabel2;
	}

	private JTextField getJTextField7() {
		if (jTextField7 == null) {
			jTextField7 = new JTextField();
			jTextField7.setEditable(false);
			jTextField7.setEnabled(false);
			//jTextField7.setText("jTextField7");
		}
		return jTextField7;
	}

	private JDatePicker getJTextField6() {
		if (jTextField6 == null) {
			jTextField6 = new com.standbysoft.component.date.swing.JDatePicker();
		//	jTextField6.setText("settleDate");
		}
		return jTextField6;
	}

	private JLabel getJLabel14() {
		if (jLabel14 == null) {
			jLabel14 = new JLabel();
			jLabel14.setText("Settle Date");
		}
		return jLabel14;
	}

	private JTextField getJTextField4() {
		if (jTextField4 == null) {
			jTextField4 =  new JTextField();
			//jTextField4.setText("StartDate");
			jTextField4.setEditable(false);
			jTextField4.setEnabled(false);
		}
		return jTextField4;
	}

	private JTextField getJTextField5() {
		if (jTextField5 == null) {
			jTextField5 = new JTextField(); 
		//	jTextField5.setText("Trade Date");
			//jTextField5.setEditable(false);
			//jTextField5.setEnabled(false);
		}
		return jTextField5;
	}

	private JLabel getJLabel13() {
		if (jLabel13 == null) {
			jLabel13 = new JLabel();
			jLabel13.setText("TakeUP Trade Date");
		}
		return jLabel13;
	}

	private JLabel getJLabel12() {
		if (jLabel12 == null) {
			jLabel12 = new JLabel();
			jLabel12.setText("FWD Option Start Date");
		}
		return jLabel12;
	}

	private NumericTextField getJTextField3() {
		if (jTextField3 == null) {
			jTextField3 = new NumericTextField(10,format);
			jTextField3.setText("0");
		}
		return jTextField3;
	}

	private NumericTextField getJTextField2() {
		if (jTextField2 == null) {
			jTextField2 = new NumericTextField(10,format);
			jTextField2.setText("0");
			jTextField2.setEditable(false);
		}
		return jTextField2;
	}

	private JLabel getJLabel11() {
		if (jLabel11 == null) {
			jLabel11 = new JLabel();
			jLabel11.setText("AMT2"); // curr2
		}
		return jLabel11;
	}

	private JLabel getJLabel10() {
		if (jLabel10 == null) {
			jLabel10 = new JLabel();
			jLabel10.setText("AMT2"); // curr2
		}
		return jLabel10;
	}

	private JLabel getJLabel1() {
		if (jLabel1 == null) {
			jLabel1 = new JLabel();
			jLabel1.setText("TakeUP");
		}
		return jLabel1;
	}

	private JLabel getJLabel0() {
		if (jLabel0 == null) {
			jLabel0 = new JLabel();
			jLabel0.setText("OutStanding");
		}
		return jLabel0;
	}

	private NumericTextField getJTextField1() {
		if (jTextField1 == null) {
			jTextField1 = new NumericTextField(10,format);
			jTextField1.setText("0");
		}
		return jTextField1;
	}

	private NumericTextField getJTextField0() {
		if (jTextField0 == null) {
			jTextField0 = new NumericTextField(10,format);
			//jTextField0.setText("Curr11");
			jTextField0.setEditable(false);
		}
		return jTextField0;
	}

	private JPanel getJPanel0() {
		if (jPanel0 == null) {
			jPanel0 = new JPanel();
			jPanel0.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			jPanel0.setLayout(new GroupLayout());
			jPanel0.add(getJLabel5(), new Constraints(new Leading(100, 12, 12), new Leading(21, 23, 12, 12)));
			jPanel0.add(getJLabel0(), new Constraints(new Leading(17, 73, 12, 12), new Leading(21, 23, 12, 12)));
			jPanel0.add(getJLabel1(), new Constraints(new Leading(17, 58, 12, 12), new Leading(64, 23, 12, 12)));
			jPanel0.add(getJLabel4(), new Constraints(new Leading(102, 10, 10), new Leading(64, 23, 12, 12)));
			jPanel0.add(getJTextField1(), new Constraints(new Leading(153, 152, 12, 12), new Leading(59, 29, 12, 12)));
			jPanel0.add(getJTextField0(), new Constraints(new Leading(151, 154, 12, 12), new Leading(14, 30, 12, 12)));
			jPanel0.add(getJLabel10(), new Constraints(new Leading(320, 10, 10), new Leading(21, 23, 12, 12)));
			jPanel0.add(getJLabel11(), new Constraints(new Leading(317, 12, 12), new Leading(64, 23, 12, 12)));
			jPanel0.add(getJTextField2(), new Constraints(new Leading(374, 154, 10, 10), new Leading(14, 30, 12, 12)));
			jPanel0.add(getJTextField3(), new Constraints(new Leading(374, 154, 12, 12), new Leading(59, 30, 12, 12)));
			jPanel0.add(getJButton0(), new Constraints(new Leading(285, 10, 10), new Leading(146, 12, 12)));
			jPanel0.add(getJButton1(), new Constraints(new Leading(367, 62, 10, 10), new Leading(146, 12, 12)));
			jPanel0.add(getJScrollPane0(), new Constraints(new Leading(6, 955, 10, 10), new Leading(183, 229, 10, 10)));
			jPanel0.add(getJLabel13(), new Constraints(new Leading(12, 12, 12), new Leading(106, 23, 12, 12)));
			jPanel0.add(getJTextField5(), new Constraints(new Leading(155, 150, 12, 12), new Leading(101, 30, 12, 12)));
			jPanel0.add(getJLabel14(), new Constraints(new Leading(311, 62, 10, 10), new Leading(107, 23, 12, 12)));
			jPanel0.add(getJTextField6(), new Constraints(new Leading(377, 148, 12, 12), new Leading(101, 30, 12, 12)));
			jPanel0.add(getJTextField4(), new Constraints(new Leading(678, 162, 10, 10), new Leading(14, 30, 12, 12)));
			jPanel0.add(getJLabel12(), new Constraints(new Leading(540, 124, 10, 10), new Leading(21, 23, 12, 12)));
			jPanel0.add(getJLabel2(), new Constraints(new Leading(540, 119, 10, 10), new Leading(64, 23, 12, 12)));
			jPanel0.add(getJTextField7(), new Constraints(new Leading(681, 156, 12, 12), new Leading(62, 30, 12, 12)));
		}
		return jPanel0;
	}

	private JLabel getJLabel5() {
		if (jLabel5 == null) {
			jLabel5 = new JLabel();
			jLabel5.setText("AMT1"); // // curr1
		}
		return jLabel5;
	}

	private JLabel getJLabel4() {
		if (jLabel4 == null) {
			jLabel4 = new JLabel();
			jLabel4.setText("AMT1"); // curr1
		}
		return jLabel4;
	}

public class TableModelUtil extends AbstractTableModel {   
    
	 final String[] columnNames;  
	    
	 Vector<Trade> data;   
	
	        
	 public TableModelUtil( Vector<Trade> myData,String col [] ) {   
	 	this.columnNames = col;
	this.data = myData;   
	
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
	 
	     Trade trade = (Trade) data.get(row);
	    
		 switch (col) {
		 case 0:
	         value = trade.getId();
	         break;
	     case 1:
	         value = trade.getTradedesc().substring(0, 3);
	         break;
	     case 2:
	         value =trade.getCurrency();
	         break;
	     case 3:
	    	
	         value = trade.getQuantity();
	         break;
	     case 4:
	         value =trade.getNominal();
	         break;
	     case 5:
	         value = trade.getTradeDate();
	         break;
	     case 6:
	         value =trade.getDelivertyDate();
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
	         if(value instanceof Trade) {
	     data.set(row,(Trade) value) ;
	     this.fireTableDataChanged();   
	         System.out.println("New value of data:");   
	         }
	        
	 }   
	    
	 public void addRow(Object value) {   
	    
		 data.add((Trade) value) ;
	 this.fireTableDataChanged();   
	   
	 }   
	    
	 public void delRow(int row) {   
	    if(row != -1) {
	 data.remove(row);          
	 this.fireTableDataChanged();   
	    }
	    
	 }   
	 
	 public void udpateValueAt(Object value, int row, int col) {   
	     
	  
	     data.set(row,(Trade) value) ;
	     for(int i=0;i<columnNames.length;i++)
	    	 	fireTableCellUpdated(row, i);   
	    
	}   
	    
	    
	    
	    public void removeALL() {
	    	if(data != null) {
	  	  data.removeAllElements();
	    	} 
	    data = null;
	  	 this.fireTableDataChanged();  
	    }


		public void setData(Vector childTrades) {
			if(childTrades.isEmpty() || childTrades == null)
				return;
			tableData = childTrades;
			model = new TableModelUtil(tableData,col);
			jTable0.setModel(model);
			// TODO Auto-generated method stub
			
		}
}


}

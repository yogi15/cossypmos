package apps.window.positionwindow;

import java.awt.Color;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

import org.dyno.visual.swing.layouts.Bilateral;
import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;

import util.commonUTIL;

import beans.Liquidation;
import beans.Openpos;
import beans.Position;
import beans.Task;
public class OpenPositionPanel extends JFrame {
	
	private JPanel jPanel0;
	private JInternalFrame jInternalFrame0;
	private JTable jTable0;
	private JScrollPane jScrollPane0;
	 String col [] = {"PositionID","Book","TradeID","TradeDate","TradeQuantity","OpenQantity","Price","Amount","SettleDate","OpenPositionOnDate"}; 
	Vector<Openpos> Openposs = new Vector<Openpos>();
	TableModelUtil model = null;
	public Vector<Openpos> getOpenposs() {
		return Openposs;
	}

	public void setOpenposs(Vector<Openpos> openposs) {
		Openposs = openposs;
	}

	public OpenPositionPanel(Vector<Openpos> Openposs) {
		initComponents(Openposs);
	}
	
	private void initComponents(Vector<Openpos> Openposs) {
		setLayout(new GroupLayout());
		add(getJPanel0(Openposs), new Constraints(new Bilateral(5, 12, 856), new Bilateral(4, 6, 10)));
		setSize(873, 332);
	}

	private JPanel getJPanel0(Vector<Openpos> Openposs) {
		if (jPanel0 == null) {
			jPanel0 = new JPanel();
		//	jPanel0.setBorder(new LineBorder(Color.black, 1, false));
			jPanel0.setLayout(new GroupLayout());
			jPanel0.add(getJScrollPane0(Openposs), new Constraints(new Bilateral(3, 4, 831), new Bilateral(4, 7, 10, 262)));
		}
		return jPanel0;
	}

	private JScrollPane getJScrollPane0(Vector<Openpos> Openposs) {
		if (jScrollPane0 == null) {
			jScrollPane0 = new JScrollPane();
			jScrollPane0.setViewportView(getJTable0( Openposs));
		}
		return jScrollPane0;
	}

	private JTable getJTable0(Vector<Openpos>  Openposs) {
		if (jTable0 == null) {
			jTable0 = new JTable();
			model = new TableModelUtil(Openposs, col);
			jTable0.setModel(model);
			
		}
		return jTable0;
	}
	public void refreshPosition(Vector<Openpos> Openposs) {
		model.removeALL();
		jTable0.removeAll();
		Openposs.removeAllElements();
		if(Openposs == null) 
			return;
		for(int i =0;i<Openposs.size();i++) 
			model.addRow(Openposs.get(i));
	}
	private JInternalFrame getJInternalFrame0(Vector<Openpos> Openposs) {
		if (jInternalFrame0 == null) {
			jInternalFrame0 = new JInternalFrame();
			jInternalFrame0.setVisible(true);
			jInternalFrame0.setLayout(new GroupLayout());
			jInternalFrame0.setTitle("Open Position");
			jInternalFrame0.add(getJPanel0( Openposs), new Constraints(new Bilateral(2, 4, 840), new Bilateral(4, 10, 10, 225)));
		}
		return jInternalFrame0;
	}

	class TableModelUtil extends AbstractTableModel {   
	    
		 final String[] columnNames;  
		    
		 Vector<Openpos> data;   
		 PositionFilterValues remoteRef ;
		  
		 public TableModelUtil( Vector<Openpos> myData,String col [] ) {   
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
		 
		     Openpos task = (Openpos) data.get(row);
		    
			 switch (col) {
		     case 0:
		         value = task.getPositionId();
		         break;
		     case 1:
		         value =task.getBookId();
		         break;
		     case 2:
		    	 value =task.getTradeId();
		         break;
		     case 3:
		         value =task.getTradeDate();
		         break;
		     case 4:
		         value = commonUTIL.getStringFromDoubleExp(task.getQuantity());
		         break;
		     case 5:
		         value = commonUTIL.getStringFromDoubleExp(task.getOpenQuantity());
		         break;
		     case 6:
		         value =  commonUTIL.getStringFromDoubleExp(task.getPrice());
		         break;
		     case 7:
		         value =  commonUTIL.getStringFromDoubleExp(task.getTradeAmt());
		         break;
		     case 8:
		         value =task.getSettleDate();
		         break;
		     case 9:
		         value =task.getOpenpositionDate();
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
		         if(value instanceof Task) {
		     data.set(row,(Openpos) value) ;
		     this.fireTableDataChanged();   
		         System.out.println("New value of data:");   
		         }
		        
		 }   
		    
		 public void addRow(Object value) {   
		    
			 data.add((Openpos) value) ;
			
		 this.fireTableDataChanged();   
		   
		 }   
		    
		 public void delRow(int row) {   
		    if(row != -1) {
		 data.remove(row);          
		 this.fireTableDataChanged();   
		    }
		    
		 }   
		 
		 public void setValueAt(Object value, int row) {   
	         System.out.println("Setting value at " + row + ","  + value   
	                            + " (an instance of "    
	                            + value.getClass() + ")");  
	         if(value instanceof Position) {
	     data.set(row,(Openpos) value) ;
	     this.fireTableDataChanged();   
	         System.out.println("New value of data:");   
	         }
	        
	 }   
		 
		 public void udpateValueAt(Object value, int row, int col) {   
		     
		  
		     data.set(row,(Openpos) value) ;
		     for(int i=0;i<columnNames.length;i++)
		    	 	fireTableCellUpdated(row, i);   
		    
		}   
		    
		  /*  private LegalEntity getLeName(int leID) {
		    	LegalEntity le = null;
		    	try {
				//	le = filterV.
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	return le;
		    } */
		    
		    public void removeALL() {
		    	if(data != null) {
		  	  data.removeAllElements();
		    	} 
		    data = null;
		  	 this.fireTableDataChanged();  
		    }
	}
	
	

}

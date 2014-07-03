package apps.window.positionwindow;
import java.awt.Color;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import org.dyno.visual.swing.layouts.Bilateral;
import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;

import beans.Liquidation;
import beans.Position;
import beans.Task;


public class LiquidationPanel  extends JFrame {
	
	private JPanel jPanel0;
	private JInternalFrame jInternalFrame0;
	private JTable jTable0;
	private JScrollPane jScrollPane0;
	 String col [] = {"PositionID","Book","First TradeID","Second TradeID ","Type","LiquidQantity","First Price","Second Price","RealisedPNL","Liquidation Date"}; 
	Vector<Liquidation> liqPos = new Vector<Liquidation>();
	TableModelUtil model = null;
	public Vector<Liquidation> getliqPos() {
		return liqPos;
	}

	public void setliqPos(Vector<Liquidation> liqPos) {
		this.liqPos = liqPos;
	}

	public LiquidationPanel(Vector<Liquidation> liqPos) {
		initComponents(liqPos);
	}
	
	private void initComponents(Vector<Liquidation> liqPos) {
		setLayout(new GroupLayout());
		add(getJPanel0(liqPos), new Constraints(new Bilateral(5, 12, 856), new Bilateral(4, 6, 10)));
		setSize(873, 332);
	}

	private JPanel getJPanel0(Vector<Liquidation> liqPos) {
		if (jPanel0 == null) {
			jPanel0 = new JPanel();
			//jPanel0.setBorder(new LineBorder(Color.black, 1, false));
			jPanel0.setLayout(new GroupLayout());
			jPanel0.add(getJScrollPane0(liqPos), new Constraints(new Bilateral(3, 4, 831), new Bilateral(4, 7, 10, 262)));
		}
		return jPanel0;
	}

	private JScrollPane getJScrollPane0(Vector<Liquidation> liqPos) {
		if (jScrollPane0 == null) {
			jScrollPane0 = new JScrollPane();
			jScrollPane0.setViewportView(getJTable0( liqPos));
		}
		return jScrollPane0;
	}

	private JTable getJTable0(Vector<Liquidation>  liqPos) {
		if (jTable0 == null) {
			jTable0 = new JTable();
			model = new TableModelUtil(liqPos, col);
			jTable0.setModel(model);
		}
		return jTable0;
	}

	
	public void refreshPosition(Vector<Liquidation> liqPos) {
		model.removeALL();
		jTable0.removeAll();
		liqPos.removeAllElements();
		if(liqPos == null) 
			return;
		for(int i =0;i<liqPos.size();i++) 
			model.addRow(liqPos.get(i));
	}
	private JInternalFrame getJInternalFrame0(Vector<Liquidation> liqPos) {
		if (jInternalFrame0 == null) {
			jInternalFrame0 = new JInternalFrame();
			jInternalFrame0.setVisible(true);
			jInternalFrame0.setLayout(new GroupLayout());
			jInternalFrame0.setTitle("Liquidated Position");
			jInternalFrame0.add(getJPanel0( liqPos), new Constraints(new Bilateral(2, 4, 840), new Bilateral(4, 10, 10, 225)));
		}
		return jInternalFrame0;
	}

	class TableModelUtil extends AbstractTableModel {   
	    
		 final String[] columnNames;  
		    
		 Vector<Liquidation> data;   
		 PositionFilterValues remoteRef ;
		  
		 public TableModelUtil( Vector<Liquidation> myData,String col [] ) {   
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
		 
		     Liquidation task = (Liquidation) data.get(row);
		    
			 switch (col) {
		     case 0:
		         value = task.getPositionId();
		         break;
		     case 1:
		         value =task.getBookId();
		         break;
		     case 2:
		    	 value =task.getfTradeId();
		         break;
		     case 3:
		         value =task.getsTradeId();
		         break;
		     case 4:
		         value = task.getTradeType();
		         break;
		     case 5:
		         value =task.getQuantity();
		         break;
		     case 6:
		         value = task.getfPrice();
		         break;
		     case 7:
		         value =task.getsPrice();
		         break;
		      case 8:
		         value =task.getRealisedPNL();
		         break;
		     case 9:
		         value =task.getLidDate();
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
		     data.set(row,(Liquidation) value) ;
		     this.fireTableDataChanged();   
		         System.out.println("New value of data:");   
		         }
		        
		 }   
		    
		 public void addRow(Object value) {   
		    
			 data.add((Liquidation) value) ;
			
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
	     data.set(row,(Liquidation) value) ;
	     this.fireTableDataChanged();   
	         System.out.println("New value of data:");   
	         }
	        
	 }   
		 
		 public void udpateValueAt(Object value, int row, int col) {   
		     
		  
		     data.set(row,(Liquidation) value) ;
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

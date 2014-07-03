package apps.window.utilwindow;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import beans.Folder;

public class TableModelUtil extends AbstractTableModel {   
    
 final String[] columnNames;  
    
 final Vector<Folder> data;   
        
        
 public TableModelUtil( Vector<Folder> myData,String col [] ) {   
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
 
	 Folder folder = (Folder) data.get(row);
	 switch (col) {
     case 0:
         value = folder.getId();
         break;
     case 1:
         value = folder.getFolder_name();
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
     data.set(row,(Folder) value) ;
     this.fireTableDataChanged();   
         System.out.println("New value of data:");   
         }
        
 }   
    
 public void addRow(Object value) {   
    
	 data.add((Folder) value) ;
 this.fireTableDataChanged();   
   
 }   
    
 public void delRow(int row) {   
    
 data.remove(row);          
 this.fireTableDataChanged();   
    
 }   
 
 public void udpateValueAt(Object value, int row, int col) {   
     System.out.println("Setting value at " + row + "," + col   
                        + " to " + value   
                        + " (an instance of "    
                        + value.getClass() + ")");   
  
     data.set(row,(Folder) value) ;
 fireTableCellUpdated(row, col);   
     System.out.println("New value of data:");   
    
}   
    
    
    
}   

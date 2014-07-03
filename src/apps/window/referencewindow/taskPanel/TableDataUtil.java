package apps.window.referencewindow.taskPanel;

import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;


import dsServices.RemoteReferenceData;

import util.commonUTIL;

import beans.Fees;
import beans.Folder;
import beans.StartUPData;

public class TableDataUtil {
	RemoteReferenceData remoteBORef;
	TableModelUtil model;
	
	
	
	
	
	
	
	private JComboBox getComboxData(String name) {
		JComboBox jdata = new JComboBox( convertVectortoSringArray(getStaticData(name) ) );
		return jdata;
	}
	
	
	private Vector getStaticData(String name) {
		Vector v6 =null;
		try {
				v6 = (Vector)	  remoteBORef.getStartUPData(name);
		} catch (RemoteException e) {
		commonUTIL.displayError("TaskJobs TableDataUtil" , "  getStaticData" , e);
	   }
		return v6;
	}
	
	
	 private String []  convertVectortoSringArray(Vector v) {
	    	String name [] = null;
	    	int i=0;
	    	if(v != null ) {
	    		name = new String[v.size()];
	    		Iterator its = v.iterator();
	    		while(its.hasNext()) {
	    			name [i] = ( (StartUPData) its.next()).getName();
	    			i++;
	    		}
	    	}
			return name;                                           
	        // TODO add your handling code here:
	    } 
	
	
	
	
	
	
	 class TableModelUtil extends AbstractTableModel {   
		    
		 final String[] columnNames;  
		    
		 Vector<FilterBean> data;   
		 RemoteReferenceData remoteRef ;
		        
		 public TableModelUtil( Vector<FilterBean> myData,String col [],RemoteReferenceData remoteRef ) {   
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
		 
		     FilterBean dataF = (FilterBean) data.get(row);
		    
			 switch (col) {
		     case 0:
		         value = dataF.getColumnName();
		         break;
		     case 1:
		         value =dataF.getFilterCriteria();
		         break;
		     case 2:
		         value =dataF.getColumnData();
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
		     data.set(row,(FilterBean) value) ;
		     this.fireTableDataChanged();   
		         System.out.println("New value of data:");   
		         }
		        
		 }   
		    
		 public void addRow(Object value) {   
		    
			 data.add((FilterBean) value) ;
		 this.fireTableDataChanged();   
		   
		 }   
		    
		 public void delRow(int row) {   
		    if(row != -1) {
		 data.remove(row);          
		 this.fireTableDataChanged();   
		    }
		    
		 }   
		 
		 public void udpateValueAt(Object value, int row, int col) {   
		     
		  
		     data.set(row,(FilterBean) value) ;
		     for(int i=0;i<columnNames.length;i++)
		    	 	fireTableCellUpdated(row, i);   
		    
		}  
	}
    


}

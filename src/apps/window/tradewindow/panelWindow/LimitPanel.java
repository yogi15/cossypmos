package apps.window.tradewindow.panelWindow;

import java.awt.Color;
import java.awt.Component;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EtchedBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

import org.dyno.visual.swing.layouts.Bilateral;
import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;

import util.commonUTIL;
import apps.window.tradewindow.BackOfficePanel;
import beans.Folder;
import dsEventProcessor.EventProcessor;
import dsEventProcessor.LimitEventProcessor;
import dsServices.RemoteReferenceData;

//VS4E -- DO NOT REMOVE THIS LINE!
public class LimitPanel extends BackOfficePanel  {

	private static final long serialVersionUID = 1L;
	private JScrollPane jScrollPane0;
	private JPanel jPanel0;
	String hostName ="";
	String cols [] = {"LimitID","LimitType","LimitName","LimitMax","LimitMin","AmountUsed","StarDate","ExpiryDate","Warning","Threshold"};
	Vector<LimitEventProcessor> data = new Vector<LimitEventProcessor>();
	TableModelUtil model = null;
	private JTable jTable1;
	private static final String PREFERRED_LOOK_AND_FEEL = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
	public LimitPanel() {
		initComponents();
		  hostName = commonUTIL.getLocalHostName();
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
		add(getJPanel0(), new Constraints(new Bilateral(6, 12, 749), new Bilateral(6, 12, 456)));
		setSize(1178, 560);
	}

	private JTable getJTable1() {
		if (jTable1 == null) {
			jTable1 = new JTable();
			model = new TableModelUtil(data, cols);
			 jTable1 = new JTable( model )
				{
					public Component prepareRenderer(TableCellRenderer renderer, int row, int column)
					{
						Component c = super.prepareRenderer(renderer, row, column);

						//  Color row based on a cell value

						if (!isRowSelected(row))
						{
							c.setBackground(getBackground());
							int modelRow = convertRowIndexToModel(row);
							double maxamt =  Double.parseDouble((getModel().getValueAt(modelRow, 3)).toString());
							double amountUsed = Double.parseDouble((getModel().getValueAt(modelRow, 5)).toString());
							double warning = Double.parseDouble((getModel().getValueAt(modelRow, 8)).toString());
							warning = maxamt * (warning /100);
							double threshold = Double.parseDouble((getModel().getValueAt(modelRow, 9)).toString());
							threshold = maxamt * (threshold/100);
							
							if (amountUsed > warning )  {
								Color co = new Color(230,245,24);
								c.setBackground(co);
							}
							if (amountUsed > threshold  ) {
								Color co = new Color(245,24,149);
								c.setBackground( co);
							}
						//	if ("Sell".equals(type)) c.setBackground(Color.YELLOW);
						}

						return c;
					}
				};
		}
		return jTable1;
	}

	private JPanel getJPanel0() {
		if (jPanel0 == null) {
			jPanel0 = new JPanel();
			jPanel0.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			jPanel0.setLayout(new GroupLayout());
			jPanel0.add(getJScrollPane0(), new Constraints(new Bilateral(3, 7, 1047), new Leading(3, 526, 10, 10)));
		}
		return jPanel0;
	}

	private JScrollPane getJScrollPane0() {
		if (jScrollPane0 == null) {
			jScrollPane0 = new JScrollPane();
			jScrollPane0.setViewportView(getJTable1());
		}
		return jScrollPane0;
	}

	private void refreshPositionTable(EventProcessor event) {
		// TODO Auto-generated method stub
		if(event instanceof LimitEventProcessor) {
			System.out.println("Limit Listening");
		}
	}

	

	@Override
	public void fillJTabel(Vector data) {
		// TODO Auto-generated method stub
		data = data;
		model = new TableModelUtil(data, cols);
		jTable1.setModel(model);
		
	}


class TableModelUtil extends AbstractTableModel {   
    
	 final String[] columnNames;  
	    
	 Vector<LimitEventProcessor> myData;   
	 RemoteReferenceData remoteRef ;
	        
	 public TableModelUtil(  Vector<LimitEventProcessor> myData,String col []) {   
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
	 
	     LimitEventProcessor limitEvnt = (LimitEventProcessor) myData.get(row);
	    
		 switch (col) {
	     case 0:
	         value = limitEvnt.getLimitID();
	         break;
	     case 1:
	         value =limitEvnt.getLimitType();
	         break;
	     case 2:
	    	
	         value =  limitEvnt.getLimitName();
	         break;
	     case 3:
	         value =limitEvnt.getlimitMaxValue();
	         break;
	     case 4:
	         value =limitEvnt.getlimitMinValue();
	         break;
	     case 5:
	         value =limitEvnt.getLimitUsage();
	         break;
	     case 6:
	         value = limitEvnt.getLimitStartDate();
	         break;
	     case 7:
	         value =limitEvnt.getLimitExpiryDate();
	         break;
	     case 8:
	         value = limitEvnt.getLimitWarning();
	         break;
	     case 9:
	         value =limitEvnt.getLimitThreshold();
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
	        	 myData.set(row,(LimitEventProcessor) value) ;
	     this.fireTableDataChanged();   
	         System.out.println("New value of data:");   
	         }
	        
	 }   
	    
	 public void addRow(Object value) {   
	    if(myData != null) {
	    	
	    	myData.add((LimitEventProcessor) value) ;
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
	     
	  
		 myData.set(row,(LimitEventProcessor) value) ;
	     for(int i=0;i<columnNames.length;i++)
	    	 	fireTableCellUpdated(row, i);   
	    
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

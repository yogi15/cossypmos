package apps.window.referencewindow;

 
import java.rmi.RemoteException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

import marketquotes.FeedListener;
import marketquotes.RandomQuotes;

import org.dyno.visual.swing.layouts.Bilateral;
import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;

import dsManager.QuoteManager;
import dsServices.RemoteReferenceData;
import apps.window.referencewindow.NettingConfigurationWindow.TableModelUtil;
import beans.Folder;
import beans.LegalEntity;
import beans.NettingConfig;
import beans.QuoteData;


//VS4E -- DO NOT REMOVE THIS LINE!
public class MarketQuoteWindow extends JPanel implements FeedListener {

	private static final long serialVersionUID = 1L;
	private JTable jTable0;
	private JScrollPane jScrollPane0;
	private JButton jButton0;
	private JPanel jPanel0;
	private JPanel jPanel1;
	private JSplitPane jSplitPane0; 
	private JTable jTable1;
	private JScrollPane jScrollPane1;
    String lefCol [] = {"Name ","Reference Name"};
    String rightCol [] = {"Name ","Date","Open","Bid","Ask","Close"};
    TableModelUtil dataModel;
	Vector<QuoteData> myData = new Vector<QuoteData> ();
    RandomQuotes randQ = null;
   String random = "Random";
    
	public MarketQuoteWindow() {
		initComponents();
	
	}

	private void initComponents() {
		setLayout(new GroupLayout());
		add(getJSplitPane0(), new Constraints(new Bilateral(8, 12, 202), new Bilateral(6, 8, 454)));
		setSize(1232, 468);
		randQ  = new RandomQuotes();
		Thread t = new Thread(randQ);
	
		t.start();
	}

	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getJTable1());
			
		}
		return jScrollPane1;
	}

	private JTable getJTable1() {
		if (jTable1 == null) {
			jTable1 = new JTable();
			dataModel = new TableModelUtil(myData, rightCol);
			jTable1.setModel(dataModel);
		}
		return jTable1;
	}

	private JSplitPane getJSplitPane0() {
		if (jSplitPane0 == null) {
			jSplitPane0 = new JSplitPane();
			jSplitPane0.setDividerLocation(467);
			jSplitPane0.setLeftComponent(getJPanel0());
			jSplitPane0.setRightComponent(getJPanel1());
		}
		return jSplitPane0;
	}

	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(new GroupLayout());
			jPanel1.add(getJScrollPane1(), new Constraints(new Bilateral(10, 4, 800), new Bilateral(56, 6, 10, 390)));
		}
		return jPanel1;
	}

	private JPanel getJPanel0() {
		if (jPanel0 == null) {
			jPanel0 = new JPanel();
			jPanel0.setLayout(new GroupLayout());
			jPanel0.add(getJScrollPane0(), new Constraints(new Bilateral(8, 5, 373), new Bilateral(50, 5, 10, 397)));
			jPanel0.add(getJButton0(), new Constraints(new Leading(8, 12, 12), new Leading(12, 37, 434)));
		}
		return jPanel0;
	}

	private JButton getJButton0() {
		if (jButton0 == null) {
			jButton0 = new JButton();
			jButton0.setText("Publish >>");
		}jButton0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	QuoteData data = new QuoteData();
            	if(jTable0.getSelectedRow() == -1) 
            		return;
            	String quoteName = (String) jTable0.getValueAt(jTable0.getSelectedRow(), 1);
            	data.setQuoteName(quoteName);
            	randQ.addQuoteSubsribeList(quoteName, data);
            	QuoteData qdata = new QuoteData();
            	qdata.setQuoteName(quoteName);
            	dataModel.addRow(qdata);
            	
            	
            }
		});
		return jButton0;
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
			DefaultTableModel dmodel = new DefaultTableModel(lefCol, 0);
			jTable0.setModel(dmodel);
			dmodel.insertRow(0, new Object[]{"FX.USD.INR","FX_USD_INR"});
			dmodel.insertRow(0, new Object[]{"FX.USD.JPY","FX_USD_INR"});
			dmodel.insertRow(0, new Object[]{"FX.USD.GBP","FX_USD_INR"});
			dmodel.insertRow(0, new Object[]{"CASH.LOAN.QTR.26/01/2014.26/01/2015.INR.0.03.Fi","CASH.LOAN.QTR.26/01/2014.26/01/2015.INR.0.03.Fi"});
			dmodel.insertRow(0, new Object[]{"BOND.GSEC.SA.01.16.2016.2Y.India.INR2016","BOND.GSEC.SA.01.16.2016.2Y.India.INR2016"});
			dmodel.insertRow(0, new Object[]{"BOND.GSEC.QTR.01.16.2015.1Y.India.INR2015","BOND.GSEC.QTR.01.16.2015.1Y.India.INR2015"});
			
		}
		return jTable0;
	}

	@Override
	public void listenFeed(QuoteData quoteData) {
		// TODO Auto-generated method stub
	//	System.out.println(quoteData + " ))))))))))))))))) ");
		for(int i=0;i<myData.size();i++) {
			QuoteData data = myData.get(i);
			if(data.getQuoteName().equalsIgnoreCase(quoteData.getQuoteName())) {
				dataModel.delRow(i);
				dataModel.addRow(quoteData);
				break;
			}
		}
		
	}

class TableModelUtil extends AbstractTableModel {   
    
	 final String[] columnNames;  
	    
	 Vector<QuoteData> data;   
	 RemoteReferenceData remoteRef ;
	
	 public TableModelUtil( Vector<QuoteData> myData,String col [] ) {   
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
	 
	     QuoteData quoteData = (QuoteData) data.get(row);
	    
		 switch (col) {
	     case 0:
	         value = quoteData.getQuoteName();
	         break;
	     case 1:
	         value =quoteData.getDatetime();
	         break;
	     case 2:
	    	
	         value =  quoteData.getOpen();
	         break;
	     case 3:
	         value =quoteData.getBid();
	         break;
	     case 4:
	         value =quoteData.getAsk();
	         break;
	     case 5:
	         value =quoteData.getClose();
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
	         if(value instanceof QuoteData) {
	     data.set(row,(QuoteData) value) ;
	     this.fireTableDataChanged();   
	         System.out.println("New value of data:");   
	         }
	        
	 }   
	    
	 public void addRow(Object value) {   
	    
		 data.add((QuoteData) value) ;
	 this.fireTableDataChanged();   
	   
	 }   
	    
	 public void delRow(int row) {   
	    if(row != -1) {
	 data.remove(row);          
	 this.fireTableDataChanged();   
	    }
	    
	 }   
	 
	 public void udpateValueAt(Object value, int row, int col) {   
	     
	  
	     data.set(row,(QuoteData) value) ;
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

package apps.window.positionwindow;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import mo.positionmang.PositionEvtListener;
import util.ReferenceDataCache;
import util.commonUTIL;
import util.common.DateU;
import dsEventProcessor.PositionEventProcessor;
import dsEventProcessor.TaskEventProcessor;
import dsManager.TaskManager;
import dsServices.RemoteBOProcess;
import dsServices.RemoteMO;
import dsServices.RemoteReferenceData;
import dsServices.RemoteTask;
import dsServices.RemoteTrade;
import apps.window.tradewindow.JFrameTradeWindowApplication;
import beans.Book;
import beans.FilterBean;
import beans.Liquidation;
import beans.Openpos;
import beans.Position;
import beans.Task;
import beans.Trade;
import beans.Transfer;
import beans.Users;
import beans.WFConfig;

public class DataPanel extends JPanel {
	
	JPanel jPanel6 = null;
	JScrollPane jScrollPane2 = null;
	JTable  jTable2 = null;
	JPopupMenu  jPopupMenu0 = null;
	JMenuItem jMenuItem0  = null;
	
	JMenuItem jMenuItem1  = null;
	JMenuItem jMenuItem2  = null;
	JMenuItem jMenuItem3  = null;
	JMenuItem jMenuItem4  = null;
	JMenuItem jMenuItem5  = null;
	Vector<Position> myData = null;
	
	DateU startDate = null;
	RemoteTrade remoteTrade = null;
	RemoteBOProcess remoteTranfer = null;
	RemoteTask remoteTask = null;
    PositionEvtListener positionEvt = null;
	DateU endDate = null;
	Vector<FilterBean> jobdetails = null;
	LiquidationPanel liqPanel = null;
	OpenPositionPanel openPosPanel = null;
	
	TableModelUtil model = null;
	boolean lock = false;
	PositionFilterValues filter = null;
	String col [] = {"PositionID","Sell Qty","Buy Qty" ,"Unrealised","Realiased","Sell Amt","Buy Amt","PNLRealised","Book","ProductType"};
	Users user = null;
	public DataPanel(Vector<Position> myData,PositionFilterValues filter) {
		positionEvt = new PositionEvtListener("localhost",commonUTIL.getLocalHostName(),"");
		positionEvt.start(positionEvt);
		positionEvt.setPositionEvtListener(this);
		this.filter = filter;
		this.myData = myData; 
		jPopupMenu0 = getJPopupMenu0();
		
	//	this.remoteTrade = remoteTrade;
	//	this.
		//setFilter(filter);
		//setMyData(myData);
	}
	
	public JPanel getJPanel6() {
		if (jPanel6 == null) {
			
			jPanel6 = new JPanel();
		//	jPanel6.setBorder(new LineBorder(Color.black, 1, false));
			jPanel6.setLayout(new BorderLayout());
			jPanel6.add(getJScrollPane2(),BorderLayout.CENTER);
		
		}
		return jPanel6;
	}
	private JScrollPane getJScrollPane2() {
		
		if (jScrollPane2 == null) {
			jScrollPane2 = new JScrollPane();
			jScrollPane2.setViewportView(getJTable2());
		}
		return jScrollPane2;
	}
	private JTable getJTable2() {
		
		if (jTable2 == null) {
			jTable2 = new JTable();
				
			


			jTable2.getTableHeader().setReorderingAllowed(false);
			jTable2.getTableHeader().setResizingAllowed(true);
			model = new TableModelUtil(myData, col, filter);
			
			jTable2.setModel(model);
		//	jTable2.setComponentPopupMenu(getJPopupMenu0());
		}jTable2.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				
				if(SwingUtilities.isRightMouseButton(e) == true) 	 {
					
					
	            	jPopupMenu0.show(e.getComponent(), e.getX(), e.getY());	
				}
				
			}
		});
		return jTable2;
	}
private JPopupMenu getJPopupMenu0() {
		
		if (jPopupMenu0 == null) {
			jPopupMenu0 = new JPopupMenu();
			jPopupMenu0.add(getJMenuItem0());
			jPopupMenu0.add(getJMenuItem1());
			jPopupMenu0.add(getJMenuItem2());
			
			jPopupMenu0.add(getJMenuItem4());
			jPopupMenu0.add(getJMenuItem5());
		//	jPopupMenu0.add(getJMenuItem6());
		}
		return jPopupMenu0;
	}
	private JMenuItem getJMenuItem0() {
		
		if (jMenuItem0 == null) {
			jMenuItem0 = new JMenuItem();
			jMenuItem0.setText("OpenPosition");
			
		//	jMenuItem0.setOpaque(false);
		}jMenuItem0.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent arg0) {
	        	int selectRow = jTable2.getSelectedRow();
	        	Position position = myData.get(selectRow);
	        	
	        	if(position.getProductType().equalsIgnoreCase("FX")) {
	        		String col [] = {"TradeID","Settlement","PrimaryCurrency("+position.getProductsubType().substring(0, 3) +")","QuotingCurrency("+position.getProductsubType().substring(4, 7)+")","Price"};
	        		int positionID = (Integer) model.getValueAt(selectRow, 0);
					Vector<Openpos> openPos = filter.getOpenPosition(positionID);
					FXOpenPositionPanel fxOpenPanel = new FXOpenPositionPanel(openPos,col);
					fxOpenPanel.setVisible(true);
					fxOpenPanel.setTitle("OpenPosition");
				    URL imgURL = this.getClass().getResource("/resources/icon/sql.jpg");
					 
				    fxOpenPanel.setIconImage(Toolkit.getDefaultToolkit()
			      		   .getImage(imgURL));
				    fxOpenPanel.setSize(900, 200);
	        	} else {
	        	int positionID = (Integer) model.getValueAt(selectRow, 0);
				Vector<Openpos> openPos = filter.getOpenPosition(positionID);
				openPosPanel = new OpenPositionPanel(openPos);
				openPosPanel.setVisible(true);
			    openPosPanel.setTitle("OpenPosition");
			    URL imgURL = this.getClass().getResource("/resources/icon/sql.jpg");
				 
			    openPosPanel.setIconImage(Toolkit.getDefaultToolkit()
		      		   .getImage(imgURL));
				openPosPanel.setSize(900, 200);
	        	}
				
	        }
});		
		return jMenuItem0;
	}

private JMenuItem getJMenuItem1() {
		
		if (jMenuItem1 == null) {
			jMenuItem1 = new JMenuItem();
			jMenuItem1.setText("---");
			
		//	jMenuItem0.setOpaque(false);
		}
		return jMenuItem1;
	}
private JMenuItem getJMenuItem2() {
	
	if (jMenuItem2 == null) {
		jMenuItem2 = new JMenuItem();
		jMenuItem2.setText("Liquidated Position");
		
	//	jMenuItem0.setOpaque(false);
	}jMenuItem2.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent arg0) {
        	int selectRow = jTable2.getSelectedRow();
        	int positionID = (Integer) model.getValueAt(selectRow, 0);
			Vector<Liquidation> liqPosition = filter.getLiqOpenPosition(positionID);
			liqPanel = new LiquidationPanel(liqPosition);
			liqPanel.setTitle("LiquidatedPosition ");
			
			URL imgURL = this.getClass().getResource("/resources/icon/sql.jpg");
			 
			liqPanel.setIconImage(Toolkit.getDefaultToolkit()
	      		   .getImage(imgURL));
			liqPanel.setVisible(true);
			liqPanel.setSize(900, 200);
			
        }
});		
	return jMenuItem2;
}
private JMenuItem getJMenuItem4() {
	
	if (jMenuItem4 == null) {
		jMenuItem4 = new JMenu();
		jMenuItem4.setText("By SettleDate");
		
	//	jMenuItem0.setOpaque(false);
	}
	
	return jMenuItem4;
}
private JMenuItem getJMenuItem5() {
	
	if (jMenuItem5 == null) {
		jMenuItem5 = new JMenu();
		jMenuItem5.setText("SettleDate By OverAll Position");
		
	//	jMenuItem0.setOpaque(false);
	}
	
	return jMenuItem5;
}
	
	
	private boolean checkonBasicCritera(String values,String value) {
		boolean flag = false;
		if(values.contains(","))  {
			String s [] = values.split(",");
			for(int v=0;v<s.length;v++) {
				if(s[v].trim().equalsIgnoreCase(value.trim()))  {
					flag = true;
					break;
				}
			}
		} else  {
		
		    if(values.equalsIgnoreCase(value))
		    	flag = true;
		}
		    
		    return flag;
		
	}
	
	
	private boolean checkonBook(String values,int bookID) {
		boolean flag = false;
		if(values.contains(","))  {
			String s [] = values.split(",");
			for(int v=0;v<s.length;v++) {
				int val =  new Integer(s[v].trim()).intValue();
				if(val == bookID)  {
					flag = true;
					break;
				}
			}
		} else  {
			int i = new Integer(values).intValue();
		    if(i == bookID)
		    	flag = true;
		}
		    
		    return flag;
		
	}
	
	public DateU getStartDate() {
		return startDate;
	}

	public void setStartDate(DateU startDate) {
		this.startDate = startDate;
	}

	public DateU getEndDate() {
		return endDate;
	}

	public void setEndDate(DateU endDate) {
		this.endDate = endDate;
	}

	public Vector<FilterBean> getJobdetails() {
		return jobdetails;
	}

	public void setJobdetails(Vector<FilterBean> jobdetails) {
		this.jobdetails = jobdetails;
	}
class TableModelUtil extends AbstractTableModel {   
    
	 final String[] columnNames;  
	    
	 Vector<Position> data;   
	 PositionFilterValues remoteRef ;
	        
	 public TableModelUtil( Vector<Position> myData,String col [],PositionFilterValues filterV ) {   
	 	this.columnNames = col;
	this.data = myData;   
	this.remoteRef = filterV;
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
	 
	     Position task = (Position) data.get(row);
	    
		 switch (col) {
	     case 0:
	         value = task.getPositionId();
	         break;
	     case 1:
	         value =task.getSell_quantity();
	         break;
	     case 2:
	    	 value =task.getBuy_quantity();
	         break;
	     case 3:
	         value =task.getUnrealized();
	         break;
	     case 4:
	         value = task.getRealized();
	         break;
	     case 5:
	         value =task.getSell_amount();
	         break;
	     case 6:
	         value = task.getBuy_amount();
	         break;
	     case 7:
		    	
		         value = getRealisedPNL(task.getPositionId());
		         break;   
	     case 8:
	    	Book book = getBookName(task.getBookId());
	         value = book.getBook_name();
	         break;
	     case 9:
	         value =task.getProductsubType();
	         break;
	    
		 }
	     return value;
	 }   
	   
	 private String getRealisedPNL(int positionID) {
		return filter.getPNLRealisedOnPosition(positionID);
	 }
	 public Book getBookName(int booKid) {
		return   filter.getBook(booKid);
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
	     data.set(row,(Position) value) ;
	     this.fireTableDataChanged();   
	         System.out.println("New value of data:");   
	         }
	        
	 }   
	    
	 public void addRow(Object value) {   
	    
		 data.add((Position) value) ;
		
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
     data.set(row,(Position) value) ;
     this.fireTableDataChanged();   
         System.out.println("New value of data:");   
         }
        
 }   
	 
	 public void udpateValueAt(Object value, int row, int col) {   
	     
	  
	     data.set(row,(Position) value) ;
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
public Vector<Position> getMyData() {
	return myData;
}
public void setMyData(Vector<Position> myData) {
	this.myData = myData;
}
public PositionFilterValues getFilter() {
	return filter;
}
public void setFilter(PositionFilterValues filter) {
	this.filter = filter;
}
public void setDataCreteria(Vector<Position> data, PositionFilterValues filtersValues) {
	
	setMyData(data);
	setFilter(filtersValues);
	model = new TableModelUtil(data, col, filtersValues);
	jTable2.setModel(model);
	// TODO Auto-generated method stub
	
}

public void setDBDataCreteria(Vector<Position> data, PositionFilterValues filtersValues) {
	
	setMyData(data);
	setFilter(filtersValues);

	// TODO Auto-generated method stub
	
}

public synchronized void addtaskData(PositionEventProcessor task) {
	// TODO Auto-generated method stub
	Position t1 = task.getPosition();
	 int id = checkExistsTask(t1);
		
	if(id != -1) {
			model.setValueAt(t1, id);
			model.fireTableDataChanged();
			jTable2.repaint();
			 if(openPosPanel != null)  {
				 Vector<Openpos> openPos = filter.getOpenPosition(id);
				 openPosPanel.refreshPosition(openPos);
			 }
			 if(liqPanel != null)  {
				 Vector<Liquidation> liqPosition = filter.getLiqOpenPosition(id);
				 liqPanel.refreshPosition(liqPosition);
				
			 }
		}
		 
	
	
	}
	
	


private int checkExistsTask(Position task) {
	int checkID = -1;
	for(int i=0;i<model.data.size();i++) {
		Position t = model.data.get(i);
		//System.out.println(t.getPositionId());
		if(t.getPositionId() == task.getPositionId()) {
			checkID = i;
			break;
		}
	}
	return checkID;
}

public void setUser(Users user2) {
	this.user = user2;
	// TODO Auto-generated method stub
	
}
public Users getUser() {
	return user;
	// TODO Auto-generated method stub
	
}

}

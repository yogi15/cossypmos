package apps.window.positionwindow;

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

import util.commonUTIL;

import apps.window.positionwindow.OpenPositionPanel.TableModelUtil;
import beans.Openpos;
import beans.Position;
import beans.Task;

public class FXOpenPositionPanel extends JFrame {
	private JPanel jPanel0;
	private JInternalFrame jInternalFrame0;
	private JTable jTable0;
	private JScrollPane jScrollPane0;
	 String col [] = {"TradeID","TradeDate","TradePrimaryQuantity","PrimaryOpenQantity","TradeQuoteQuantity","QuoteOpenQantity","Price","Amount","SettleDate","OpenPositionOnDate"}; 
	Vector<Openpos> Openposs = new Vector<Openpos>();
	TableModelUtil model = null;
	public Vector<Openpos> getOpenposs() {
		return Openposs;
	}

	public void setOpenposs(Vector<Openpos> openposs) {
		Openposs = openposs;
	}

	public FXOpenPositionPanel(Vector<Openpos> Openposs,String [] col) {
		this.col = col;
		initComponents(Openposs);
	}
	
	private void initComponents(Vector<Openpos> Openposs) {
		setLayout(new GroupLayout());
		add(getJPanel0(Openposs), new Constraints(new Bilateral(5, 12, 856), new Bilateral(4, 6, 10)));
		setSize(873, 332);
	}

	private void breakOpenPositionInsettleDate(Vector<Openpos> Openposs) {
		Vector<OpenPostionModel> opModel = new Vector<OpenPostionModel>();
	
		for(int i=0;i<Openposs.size();i++) {
			Openpos oppos = Openposs.get(i) ;
			OpenPostionModel popenModel = new OpenPostionModel();
		//	popenModel.setType(getTradeType(oppos.getSign()));
			popenModel.setProductType(oppos.getProductSubType());
			popenModel.setSettlementDate(oppos.getSettleDate());
			popenModel.setTradeid(oppos.getTradeId());
			popenModel.setPrimaryCurr(oppos.getPrimaryCurr());
			popenModel.setOpenQuantity(oppos.getOpenQuantity());
			opModel.add(popenModel);
			OpenPostionModel qpenModel = new OpenPostionModel();
			qpenModel.setSettlementDate(oppos.getSettleDate());
		//	popenModel.setType(getTradeType(oppos.getSign()));
			popenModel.setTradeid(oppos.getTradeId());
			qpenModel.setPrimaryCurr(oppos.getQuotingCurr());
			qpenModel.setOpenQuantity(oppos.getOpenNominal());
			popenModel.setProductType(oppos.getProductSubType());
			opModel.add(qpenModel);
		}
	}
	
	
	private String getTradeType(int i) {
		if(i == 1) 
			return "BUY";
		return "SELL";
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
		 //    {"PositionID","Book","TradeID","TradeDate","TradePrimaryQuantity","PrimaryOpenQantity","TradeQuoteQuantity","QuoteOpenQantity","Price","Amount","SettleDate","OpenPositionOnDate"};
		     Openpos task = (Openpos) data.get(row);
		    
			 switch (col) {
		     case 0:
		         value = task.getTradeId();
		         break;
		     case 1:
		         value =task.getSettleDate();
		         break;
		     case 2:
		    	 value = commonUTIL.getStringFromDoubleExp(task.getOpenQuantity());
		         break;
		     case 3:
		         value = commonUTIL.getStringFromDoubleExp(task.getOpenNominal());
		         break;
		     case 4:
		         value =  commonUTIL.getStringFromDoubleExp(task.getPrice());
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
	
	class OpenPostionModel {
		String quotingCurr;
		String type;
		/**
		 * @return the type
		 */
		private String getType() {
			return type;
		}
		/**
		 * @param type the type to set
		 */
		private void setType(String type) {
			this.type = type;
		}
		/**
		 * @return the productType
		 */
		private String getProductType() {
			return productType;
		}
		/**
		 * @param productType the productType to set
		 */
		private void setProductType(String productType) {
			this.productType = productType;
		}
		String productType;
		
		/**
		 * @return the quotingCurr
		 */
		private String getQuotingCurr() {
			return quotingCurr;
		}
		/**
		 * @param quotingCurr the quotingCurr to set
		 */
		private void setQuotingCurr(String quotingCurr) {
			this.quotingCurr = quotingCurr;
		}
		/**
		 * @return the primaryCurr
		 */
		private String getPrimaryCurr() {
			return primaryCurr;
		}
		/**
		 * @param primaryCurr the primaryCurr to set
		 */
		private void setPrimaryCurr(String primaryCurr) {
			this.primaryCurr = primaryCurr;
		}
		/**
		 * @return the settlementDate
		 */
		private String getSettlementDate() {
			return SettlementDate;
		}
		/**
		 * @param settlementDate the settlementDate to set
		 */
		private void setSettlementDate(String settlementDate) {
			SettlementDate = settlementDate;
		}
		/**
		 * @return the tradeid
		 */
		private int getTradeid() {
			return tradeid;
		}
		/**
		 * @param tradeid the tradeid to set
		 */
		private void setTradeid(int tradeid) {
			this.tradeid = tradeid;
		}
		/**
		 * @return the openQuantity
		 */
		private double getOpenQuantity() {
			return OpenQuantity;
		}
		/**
		 * @param openQuantity the openQuantity to set
		 */
		private void setOpenQuantity(double openQuantity) {
			OpenQuantity = openQuantity;
		}
		String primaryCurr;
		String SettlementDate;
		int tradeid;
		double OpenQuantity;
		
	}
	

}

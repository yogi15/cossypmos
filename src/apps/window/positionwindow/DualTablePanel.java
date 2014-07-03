package apps.window.positionwindow;

import beans.OpenTrade;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.AbstractButton;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

import util.ReferenceDataCache;
import util.commonUTIL;


import apps.window.utilwindow.JDialogBoxJListSingleSelection;
import beans.Position;
import beans.Task;
import beans.UserJob;

import com.jidesoft.grid.DefaultExpandableRow;
import com.jidesoft.grid.DualTable;
import com.jidesoft.grid.TableModelAdapter;
import com.jidesoft.list.DualList;
import com.jidesoft.list.DualListModel;

public class DualTablePanel   {
	public DualTable _dualTable;
	Vector data;
	java.util.List taskList = null;
	public JMenuItem jMenuItem0 = null;
    OpenTrade firstTradeSelected = null;
    public JMenuItem jMenuItem1 = null;
    public JMenuItem jMenuItem2 = null;
    public JMenuItem jMenuItem3 = null;
    public JMenuItem jMenuItem4 = null;
	JPopupMenu jPopupMenu0 = null;
	public Vector<OpenTrade> dataOpenTrades = new Vector<OpenTrade>();
    String col [] = {"TradeID","Position","Book","CounterParty","SELL/BUY","ProductType","CurrencyPair","Price","Quantity","Nominal","OpenQuantity","OpenNominal"};
	public JPanel getDualTablePanel(Vector data) {
		
		TaskTableModelAdapter tableModelAdapter = new TaskTableModelAdapter();
		jPopupMenu0 = getJPopupMenu0();
		 taskList = createTaskList(data);
		_dualTable = new DualTable(taskList, tableModelAdapter);
	//	_dualTable._originalTablePane.set
		jPopupMenu0 = getJPopupMenu0();
		_dualTable = new DualTable(taskList , tableModelAdapter);
		SharedListSelectionHandler ss = new SharedListSelectionHandler();
		 _dualTable.getSelectedTable().getModel().addTableModelListener(ss);
		_dualTable.getSelectedTable().addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				
				if(SwingUtilities.isRightMouseButton(e) == true) 	 {
					
					
	            	jPopupMenu0.show(e.getComponent(), e.getX(), e.getY());	
				}
				
			}
		});
		//TableModelUtil modelt = new TableModelUtil(opentrades, col);
	//	_dualTable.getSelectedTable().setModel(modelt);
		_dualTable.setButtonVisible(DualList.COMMAND_MOVE_RIGHT, false);
		_dualTable.setButtonVisible(DualList.COMMAND_MOVE_ALL_RIGHT, false);
		_dualTable.setButtonVisible(DualList.COMMAND_MOVE_LEFT, false);
		_dualTable.setButtonVisible(DualList.COMMAND_MOVE_ALL_LEFT, false);
		_dualTable.setButtonVisible(DualList.COMMAND_MOVE_DOWN, false);
		_dualTable.setButtonVisible(DualList.COMMAND_MOVE_UP, false);
		_dualTable.setButtonVisible(DualList.COMMAND_MOVE_TO_TOP, false);
		_dualTable.setButtonVisible(DualList.COMMAND_MOVE_TO_BOTTOM, false);
		//_dualTable.
		
		  Container contentPane = createComponent();
		  _dualTable.getSelectedTable().setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		  _dualTable.addPane(contentPane);
		
	//	_dualTable.getSelectedTable().getColumnModel().getColumn(0).setPreferredWidth(50); 
		//_dualTable.getSelectedTable().getColumnModel().getColumn(1).setPreferredWidth(50); 
		//_dualTable.getSelectedTable().getColumnModel().getColumn(2).setPreferredWidth(30); 
		_dualTable.getSelectedTable().getColumnModel().getColumn(3).setPreferredWidth(150); 
		_dualTable.getSelectedTable().getColumnModel().getColumn(4).setPreferredWidth(150); 
		
		JPanel panel = new JPanel(new BorderLayout(4, 4));
		//_dualTable.
		panel.add(_dualTable, BorderLayout.CENTER);
		
		return panel;
	}
private JMenuItem getJMenuItem0() {
		
		if (jMenuItem0 == null) {
			jMenuItem0 = new JMenuItem();
			jMenuItem0.setText("OpenPosition");
			
		//	jMenuItem0.setOpaque(false);
		}
		return jMenuItem0;
}
private JMenuItem getJMenuItem1() {
	
	if (jMenuItem1 == null) {
		jMenuItem1= new JMenuItem();
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
	}
	return jMenuItem2;
}
private JPopupMenu getJPopupMenu0() {
		
		if (jPopupMenu0 == null) {
			jPopupMenu0 = new JPopupMenu();
			jPopupMenu0.add(getJMenuItem0());
			jPopupMenu0.add(getJMenuItem1());
			jPopupMenu0.add(getJMenuItem2());
			
		//	jPopupMenu0.add(getJMenuItem4());
		//	jPopupMenu0.add(getJMenuItem5());
		//	jPopupMenu0.add(getJMenuItem6());
		}
		return jPopupMenu0;
	}

	private Container createComponent() {
		Container panel = DualListAction.createButtonPanel();
		for (DualListAction action : DualListAction.values()) {
			AbstractButton button = action.createButton(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// Action!
					OpenTrade opentrades = null;
					Object []  op1  = _dualTable.getSelectedValues();
					for(int i =0;i<op1.length;i++) {
						DudalTableModel op = (DudalTableModel) op1[i];
						opentrades = op.tradeOP;
					}
					if(e.getActionCommand().equalsIgnoreCase("moveLeft")) {
						_dualTable.moveLeft();
					} 
					if(e.getActionCommand().equalsIgnoreCase("moveAllRight")) {
						System.out.println(e.getSource() + " " + e.toString());
						String type = "";
						boolean flag = true;
						if(_dualTable.getSelectedTable().getRowCount() > 0) {
							for(int i=0;i<_dualTable.getSelectedTable().getColumnCount();i++)
				    	 	if(_dualTable.getSelectedTable().getColumnName(i).equalsIgnoreCase("SELL/BUY"))  {
				    	 		type =(String) _dualTable.getSelectedTable().getValueAt(0, i);
				    	 		break;
				    	 	}
							
				    	}
						Object [] o = _dualTable.getSelectedValues();
						for(int i =0;i<o.length;i++) {
							DudalTableModel op = (DudalTableModel) o[i];
							String t1 = op.tradeOP.getType().trim();
							if(t1.equalsIgnoreCase(type.trim())) {
							//	_dualTable.setSelectionMode(0);
								flag = false;
								return;
							}
						}
						
				    	//_dualTable.moveRight();
						if(flag)
							_dualTable.moveAllRight();
						    
						}
						
					
					if(e.getActionCommand().equalsIgnoreCase("moveToBottom")) {
						_dualTable.moveToBottom();
					} 
					
					if(e.getActionCommand().equalsIgnoreCase("moveToTop")) {
						_dualTable.moveToTop();
					}
					if(e.getActionCommand().equalsIgnoreCase("moveLeft")) {
						_dualTable.moveLeft();
						int row = _dualTable.getSelectedTable().getSelectedRow();
						_dualTable.moveLeft();
						dataOpenTrades.remove(row-1);
						System.out.println("On Move Left ");
						for(int i=0;i<dataOpenTrades.size();i++) {
						   OpenTrade 	opentrades2 = dataOpenTrades.get(i);
						  System.out.println( opentrades2.getTradeid());
						}
					}
					if(e.getActionCommand().equalsIgnoreCase("moveToBottom")) {
						
						
					}
					if(e.getActionCommand().equalsIgnoreCase("moveDown")) {
						//_dualTable.moveDown();
					} 					
					if(e.getActionCommand().equalsIgnoreCase("moveRight")) {
					
					if(_dualTable.getSelectedTable().getRowCount() > 2) 
						return;
					
						System.out.println(_dualTable.getSelectedValues().length);
						_dualTable.moveRight();
						
						System.out.println(_dualTable.getSelectedValues().length);
						
						System.out.println("On Move Right ");
						Object [] f =	_dualTable.getSelectedValues();
						if(f.length > 1) {
							DudalTableModel op = (DudalTableModel) f[0];
							DudalTableModel op2 = (DudalTableModel) f[1];
							if(op.tradeOP.getType().equalsIgnoreCase(op2.tradeOP.getType())) {
								  _dualTable.getModel().removeSelectionInterval(1, 1);
							} else {
								dataOpenTrades.addElement(op2.tradeOP);
							}
							
							
						} else {
							DudalTableModel op = (DudalTableModel) f[0];
							dataOpenTrades.addElement(op.tradeOP);
						}
					}
				}
			});
			panel.add(DualListAction.createButtonSpacePanel(button));
		}
		return panel;
	}
	
	private String getBookName(int bookID) {
		             return  ReferenceDataCache.getBook(bookID).getBook_name();
	}
	private String getLEName(int leid) {
        return  ReferenceDataCache.getLegalEntity(leid).getName();
}
	
	private java.util.List createTaskList(Vector positions) {
		java.util.List<DudalTableModel> rows = new ArrayList<DudalTableModel>();
		for(int i=0;i<positions.size();i++) {
			Vector data = (Vector) positions.get(i);
			OpenTrade oTrade = new OpenTrade();
			oTrade.setTradeid((Integer) data.get(0));
			oTrade.setBookid((Integer) data.get(1));
			oTrade.setBookName(getBookName(oTrade.getBookid()));
			oTrade.setCpid((Integer) data.get(2));
			oTrade.setCpName(getLEName(oTrade.getCpid()));
			oTrade.setType((String) data.get(3));
			oTrade.setProductType((String) data.get(4));
			oTrade.setProductSubType((String) data.get(5));
			oTrade.setCurrencyPair((String) data.get(6));
			oTrade.setPrice((Double) data.get(7));
			oTrade.setQuantity((Double) data.get(8));
			oTrade.setNominal((Double) data.get(9));
			oTrade.setOpenQuantity((Double) data.get(10));
			oTrade.setOpenNominal((Double) data.get(11));
			oTrade.setOpenID((Integer) data.get(12));
			oTrade.setPositionid((Integer) data.get(13));
			oTrade.setDeliveryDate((String) data.get(14));
			DudalTableModel model = new DudalTableModel(oTrade);
			rows.add(model);
		}
		return rows;
	}
	class D1 implements DualListModel {

		@Override
		public int getSize() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public Object getElementAt(int index) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void addListDataListener(ListDataListener l) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void removeListDataListener(ListDataListener l) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void addFreezeIndex(int arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void addListSelectionListener(ListSelectionListener arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void addPropertyChangeListener(PropertyChangeListener arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void addSelectionInterval(int arg0, int arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void clearSelection() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public int[] getFrozenIndices() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int[] getSelectedIndices() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int getSelectionMode() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public boolean getValueIsAdjusting() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isFreezeIndex(int arg0) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isSelectedIndex(int arg0) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isSelectionEmpty() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void moveSelection(int arg0, int arg1, int arg2, boolean arg3) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void removeFreezeIndex(int arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void removeListSelectionListener(ListSelectionListener arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void removePropertyChangeListener(PropertyChangeListener arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void removeSelectionInterval(int arg0, int arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void selectAll() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void setSelectionMode(int arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void setValueIsAdjusting(boolean arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
	class ddd implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {
			// TODO Auto-generated method stub
			
			
		}
		
	}
	
	class TableModelUtil   extends AbstractTableModel {
       Vector<OpenTrade> data = null;
		String cols [] = null;
		TableModelUtil(Vector<OpenTrade> datas,String col []) {
			this.data = datas;
			cols = col;
		}
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public int getRowCount() {
			// TODO Auto-generated method stub
			return data.size();
		}

		@Override
		public int getColumnCount() {
			// TODO Auto-generated method stub
			return cols.length;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			// TODO Auto-generated method stub
			OpenTrade tradeOP = data.get(rowIndex);
			  Object value = null;  	 
			 switch (columnIndex) {
			 case 0:
		         value = tradeOP.getTradeid();
		         break;
			 case 1:
		         value =tradeOP.getPositionid();
		         break;
		     case 2:
		         value =tradeOP.getBookName();
		         break;
		     case 3:
		    	 value =tradeOP.getCpName();
		         break;
		     case 4:
		         value =tradeOP.getType();
		         break;
		     case 5:
		         value = tradeOP.getProductType();
		         break;
		     case 6:
		         value =tradeOP.getCurrencyPair();
		         break;
		     case 7:
		         value = tradeOP.getProductSubType();
		         break;
		       case 8:
		         value =tradeOP.getPrice();
		         break;
		       case 9:
			         value =tradeOP.getQuantity();
			         break;
		       case 10:
			         value =tradeOP.getNominal();
			         break;
		       case 11:
			         value =tradeOP.getOpenNominal();
			         break;
		       case 12:
			         value =tradeOP.getOpenNominal();
			         break;
		       case 13:
			         value =tradeOP.getDeliveryDate();
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
			     data.set(row,(OpenTrade) value) ;
			     this.fireTableDataChanged();   
			         System.out.println("New value of data:");   
			         }
			        
			 }   
			    
			 public void addRow(Object value) {   
			    
				 data.add((OpenTrade) value) ;
				
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
		     data.set(row,(OpenTrade) value) ;
		     this.fireTableDataChanged();   
		         System.out.println("New value of data:");   
		         }
		        
		 }   
			 
			 public void udpateValueAt(Object value, int row, int col) {   
			     
			  
			     data.set(row,(OpenTrade) value) ;
			     for(int i=0;i<cols.length;i++)
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
	class DudalTableModel extends DefaultExpandableRow {
		OpenTrade tradeOP = null;
		   public DudalTableModel(OpenTrade openTrade) {
			   this.tradeOP = openTrade;
		   }
		@Override
		public Object getValueAt(int arg0) {
			// TODO Auto-generated method stub
			 Object value = null;
				
			  switch (arg0) {
			  case 0:
			         value = tradeOP.getTradeid();
			         break;
				 case 1:
			         value =tradeOP.getPositionid();
			         break;
			     case 2:
			         value =tradeOP.getBookName();
			         break;
			     case 3:
			    	 value =tradeOP.getCpName();
			         break;
			     case 4:
			         value =tradeOP.getType();
			         break;
			     case 5:
			         value = tradeOP.getProductType();
			         break;
			     case 6:
			         value =tradeOP.getCurrencyPair();
			         break;
			     case 7:
			         value = tradeOP.getProductSubType();
			         break;
			       case 8:
			         value =tradeOP.getPrice();
			         break;
			       case 9:
				         value =tradeOP.getQuantity();
				         break;
			       case 10:
				         value =tradeOP.getNominal();
				         break;
			       case 11:
				         value =tradeOP.getOpenNominal();
				         break;
			       case 12:
				         value =tradeOP.getOpenNominal();
				         break;
			       case 13:
				         value =tradeOP.getDeliveryDate();
				         break;
				
		}	
			  return value;
		}
		
		public Object getRow(int i) {
			return ((DudalTableModel) taskList.get(i)).tradeOP;
		}
		public Object getSelectedRow() {
			return ((DudalTableModel) getSelectedRow()).tradeOP;
		}
		
		
	}
	
	
	class TaskTableModelAdapter implements TableModelAdapter {
		public TaskTableModelAdapter() {
		}

		public int getColumnCount() {
			return 13;
		}

		public Class<?> getColumnClass(int columnIndex) {
			switch (columnIndex) {
			case 0:
				return Integer.class;
			case 1:
				return Integer.class;
			case 2:
				return String.class;
			case 3:
				return String.class;
			case 4:
				return String.class;
			case 5:
				return String.class;
			case 6:
				return String.class;
			case 7:
				return String.class;
			case 8:
				return Double.class;				
			case 9:
				return Double.class;
			case 10:
				return Double.class;
			case 11:
				return Double.class;
			case 12:
				return Double.class;
			case 13:
				return String.class;
				
			}
			return Object.class;
		}
		// select t.id,t.bookid,t.cpid,t.type,t.producttype,t.tradedesc,t.tradedesc1,t.price,t.quantity,
		//t.nominal,o.openquantity,o.opennominal from trade t,openpos o
		
	//	"PositionID","Sell Qty","Buy Qty" ,"Unrealised","Realiased","Sell Amt","Buy Amt","PNLRealised","Book","ProductType"
		public String getColumnName(int column) {
			switch (column) {
			case 0:
				return "TradeID";
			case 1:
				return "PositionID";
			case 2:
				return "Book";
			case 3:
				return "CounterParty";
			case 4:
				return "SELL/BUY";
			case 5:
				return "ProductType";
			case 6:
				return "ProductSubType";
			case 7:
				return "CurrencyPair";
			case 8:
				return "Price";
			case 9:
				return "Quantity";
			case 10:
				return "Nominal";
			case 11:
				return "OpenQuantity";
			case 12:
				return "OpenNominal";
			case 13:
				return "DeliveryDate";
			}
			return null;
		}
		
	}
	
	
	 class SharedListSelectionHandler implements TableModelListener {
	       
			@Override
			public void tableChanged(TableModelEvent e) {
				// TODO Auto-generated method stub
				if(_dualTable.getSelectedTable().getRowCount() == e.getFirstRow()) {
				//	Object [] f =	_dualTable.getSelectedValues();
				//	DudalTableModel op = (DudalTableModel) f[0];
					//firstTradeSelected = op.tradeOP;
					
				}
			
				
				
			}

	    }
}

package apps.window.referencewindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
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
import org.dyno.visual.swing.layouts.Trailing;

import util.commonUTIL;

import beans.B2BConfig;
import beans.Book;
import beans.LiquidationConfig;
import beans.StartUPData;
import dsServices.RemoteReferenceData;
import dsServices.ServerConnectionUtil;

//VS4E -- DO NOT REMOVE THIS LINE!
public class LiquidationConfigWindow extends JPanel {

	private static final long serialVersionUID = 1L;
	RemoteReferenceData referenceData;
	 public static  ServerConnectionUtil de = null;
	private JTable jTable0;
	private JScrollPane jScrollPane0;
	private JButton jButton0;
	private JButton jButton1;
	private JButton jButton2;
	private JButton jButton3;
	private JLabel jLabel0;
	private JLabel jLabel1;
	private JLabel jLabel2;
	private JPanel jPanel0;
	private JComboBox jComboBox0;
	private JComboBox jComboBox2;
	private JLabel jLabel4;
	private JLabel jLabel3;
	private JTextField jTextField0;
	private JLabel jLabel5;
	private JComboBox jComboBox4;
	private JCheckBox jCheckBox0;
	private JCheckBox jCheckBox1;
	String cols [] = {"id","Book","ProductType","ProductSubType","Method","SortType"};
	 TableModelUtilLiquConfig liqConfigModel = null;
	 Hashtable<Integer,Book> books = new Hashtable<Integer,Book>();
	 Vector<LiquidationConfig> data = new Vector<LiquidationConfig>();
	 javax.swing.DefaultComboBoxModel bookModel = new javax.swing.DefaultComboBoxModel();
	 javax.swing.DefaultComboBoxModel LiquidmethodModel = new javax.swing.DefaultComboBoxModel();
	 javax.swing.DefaultComboBoxModel productModel = new javax.swing.DefaultComboBoxModel();
	 javax.swing.DefaultComboBoxModel productSubModel = new javax.swing.DefaultComboBoxModel();
	private JComboBox jComboBox1;
	private JCheckBox jCheckBox2;
	private static final String PREFERRED_LOOK_AND_FEEL = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
	public LiquidationConfigWindow() {
		init();
		initComponents();
		setSize(1050,700);
	}

	private void init() {
		// TODO Auto-generated method stub
		de =ServerConnectionUtil.connect("localhost", 1099,commonUTIL.getServerIP());
	   	 try {
	   	     referenceData = (RemoteReferenceData) de.getRMIService("ReferenceData");
	   		Vector books = (Vector) referenceData.selectALLBooks();
	   		Vector prdoucts = (Vector) referenceData.getStartUPData("ProductType");
	   		Vector liqMethod = (Vector) referenceData.getStartUPData("LiquidationMethod");
	   		data = (Vector) referenceData.selectALLLiqConfigs();
	   		processBookData(books,bookModel,true);
	   		processBookData(prdoucts,productModel,false);
	   		processBookData(liqMethod,LiquidmethodModel,false);
	 	} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void initComponents() {
		setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
		setLayout(new GroupLayout());
		add(getJScrollPane0(), new Constraints(new Leading(5, 1062, 10, 10), new Leading(246, 219, 10, 10)));
		add(getJPanel0(), new Constraints(new Leading(5, 1062, 12, 12), new Leading(6, 237, 10, 10)));
		setSize(1081, 700);
	}

	private JCheckBox getJCheckBox2() {
		if (jCheckBox2 == null) {
			jCheckBox2 = new JCheckBox();
			jCheckBox2.setSelected(true);
			jCheckBox2.setText("Ccy Based Position");
		}
		return jCheckBox2;
	}

	private JComboBox getJComboBox1() {
		if (jComboBox1 == null) {
			jComboBox1 = new JComboBox();
			jComboBox1.setModel(new DefaultComboBoxModel(new Object[] { "item0", "item1", "item2", "item3" }));
		}
		return jComboBox1;
	}

	private JCheckBox getJCheckBox1() {
		if (jCheckBox1 == null) {
			jCheckBox1 = new JCheckBox();
			jCheckBox1.setText("Settle Date");
		}jCheckBox1.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				
				if( jCheckBox1.isSelected()) {
					if(jComboBox2.getSelectedIndex() == -1) {
						jCheckBox1.setSelected(false);
						commonUTIL.showAlertMessage("Select Product Type");
						
						return;
					}
					String productType = jComboBox2.getSelectedItem().toString();
					if(!productType.equalsIgnoreCase("FX"))  {
						jCheckBox1.setSelected(false);
						commonUTIL.showAlertMessage("Select Product Type as FX");
						
						return;
						
					}
				}
			}
				
			});
		return jCheckBox1;
	}

	private JCheckBox getJCheckBox0() {
		if (jCheckBox0 == null) {
			jCheckBox0 = new JCheckBox();
			jCheckBox0.setSelected(true);
			jCheckBox0.setText("Trade Date");
		}
		return jCheckBox0;
	}

	private JComboBox getJComboBox4() {
		if (jComboBox4 == null) {
			jComboBox4 = new JComboBox();
			jComboBox4.setModel(bookModel);
		}
		return jComboBox4;
	}

	private JLabel getJLabel5() {
		if (jLabel5 == null) {
			jLabel5 = new JLabel();
			jLabel5.setText("Book");
		}
		return jLabel5;
	}

	private JTextField getJTextField0() {
		if (jTextField0 == null) {
			jTextField0 = new JTextField();
			jTextField0.setText("0");
			
			jTextField0.setEnabled(false);
		}
		return jTextField0;
	}

	private JLabel getJLabel3() {
		if (jLabel3 == null) {
			jLabel3 = new JLabel();
			jLabel3.setText("Product");
		}
		return jLabel3;
	}

	private JLabel getJLabel4() {
		if (jLabel4 == null) {
			jLabel4 = new JLabel();
			jLabel4.setText("ProductSubType");
		}
		return jLabel4;
	}

	private JComboBox getJComboBox2() {
		if (jComboBox2 == null) {
			jComboBox2 = new JComboBox();
			jComboBox2.setModel(productModel);
		}jComboBox2.addItemListener( new ItemListener() {

        	public void itemStateChanged(ItemEvent e) {
        		// TODO Auto-generated method stub
        		
        	
        	String productType = jComboBox2.getSelectedItem().toString();
        	productSubModel.removeAllElements();
        	productSubModel = null;
        	Vector subTpe;
			try {
				 productSubModel = new javax.swing.DefaultComboBoxModel();
				subTpe = (Vector)	  referenceData.getStartUPData(productType+".subType");
				
				  processBookData(subTpe, productSubModel, false);
				  jComboBox1.setModel(productSubModel);
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
          
            
        		
        		
        	}
        	   
           });
		return jComboBox2;
	}

	private JComboBox getJComboBox0() {
		if (jComboBox0 == null) {
			jComboBox0 = new JComboBox();
			jComboBox0.setModel(LiquidmethodModel);
		}
		return jComboBox0;
	}

	private JPanel getJPanel0() {
		if (jPanel0 == null) {
			jPanel0 = new JPanel();
			jPanel0.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			jPanel0.setLayout(new GroupLayout());
			jPanel0.add(getJButton0(), new Constraints(new Leading(217, 66, 10, 10), new Trailing(12, 27, 12, 12)));
			jPanel0.add(getJButton1(), new Constraints(new Leading(317, 79, 10, 10), new Trailing(12, 27, 12, 12)));
			jPanel0.add(getJButton2(), new Constraints(new Leading(430, 79, 10, 10), new Trailing(12, 27, 12, 12)));
			jPanel0.add(getJButton3(), new Constraints(new Leading(543, 79, 10, 10), new Trailing(12, 27, 12, 12)));
			jPanel0.add(getJLabel0(), new Constraints(new Leading(17, 71, 10, 10), new Leading(23, 22, 12, 12)));
			jPanel0.add(getJComboBox2(), new Constraints(new Leading(392, 170, 10, 10), new Leading(17, 28, 47, 51)));
			jPanel0.add(getJLabel3(), new Constraints(new Leading(302, 90, 10, 10), new Leading(23, 22, 47, 51)));
			jPanel0.add(getJLabel2(), new Constraints(new Leading(20, 44, 10, 10), new Leading(118, 22, 10, 10)));
			jPanel0.add(getJLabel5(), new Constraints(new Leading(299, 71, 12, 12), new Leading(118, 22, 47, 51)));
			jPanel0.add(getJComboBox4(), new Constraints(new Leading(390, 170, 12, 12), new Leading(112, 28, 47, 51)));
			jPanel0.add(getJLabel1(), new Constraints(new Leading(17, 71, 12, 12), new Leading(72, 22, 12, 12)));
			jPanel0.add(getJCheckBox1(), new Constraints(new Leading(199, 12, 12), new Leading(71, 10, 10)));
			jPanel0.add(getJComboBox1(), new Constraints(new Leading(392, 167, 12, 12), new Leading(66, 28, 47, 51)));
			jPanel0.add(getJLabel4(), new Constraints(new Leading(299, 81, 12, 12), new Leading(72, 22, 10, 10)));
			jPanel0.add(getJComboBox0(), new Constraints(new Leading(92, 171, 10, 10), new Leading(17, 28, 47, 51)));
			jPanel0.add(getJCheckBox0(), new Constraints(new Leading(89, 10, 10), new Leading(71, 12, 12)));
			jPanel0.add(getJTextField0(), new Constraints(new Leading(92, 53, 12, 12), new Leading(117, 24, 12, 12)));
			jPanel0.add(getJCheckBox2(), new Constraints(new Leading(170, 119, 10, 10), new Leading(115, 47, 51)));
		}
		return jPanel0;
	}

	private JLabel getJLabel2() {
		if (jLabel2 == null) {
			jLabel2 = new JLabel();
			jLabel2.setText("ID");
		}
		return jLabel2;
	}

	private JLabel getJLabel1() {
		if (jLabel1 == null) {
			jLabel1 = new JLabel();
			jLabel1.setText("By Date");
		}
		return jLabel1;
	}

	private JLabel getJLabel0() {
		if (jLabel0 == null) {
			jLabel0 = new JLabel();
			jLabel0.setText("Liquid Method");
		}
		return jLabel0;
	}

	private JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new JButton();
			jButton3.setText("UPDATE");
		}
		return jButton3;
	}

	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setText("DELETE");
		}jButton2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				B2BConfig splitConfig = new B2BConfig();
				int selectID = jTable0.getSelectedRow();
				LiquidationConfig  liqConfig = (LiquidationConfig)	data.get(jTable0.getSelectedRow());
				if(liqConfig != null) {
					try {
						if(referenceData.deleteLiqConfig(liqConfig)) {
							liqConfigModel.delRow(selectID);
						}
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
				
			}
		});
		return jButton2;
	}

	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("ADD");
		}jButton1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				LiquidationConfig liqtConfig = new LiquidationConfig();
				liqtConfig.setId(0);
					fillCLiquidationConfigConfig(liqtConfig);
					
				try {
					liqtConfig = (LiquidationConfig) referenceData.saveLiqConfig(liqtConfig);
					liqConfigModel.addRow(liqtConfig);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
					
					
				
				
			}

			
		});
		return jButton1;
	}

	private JButton getJButton0() {
		if (jButton0 == null) {
			jButton0 = new JButton();
			jButton0.setText("NEW");
		}
		return jButton0;
	}

	private JScrollPane getJScrollPane0() {
		if (jScrollPane0 == null) {
			jScrollPane0 = new JScrollPane();
			jScrollPane0.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			jScrollPane0.setViewportView(getJTable0());
		}
		return jScrollPane0;
	}

	private JTable getJTable0() {
		if (jTable0 == null) {
			jTable0 = new JTable();
			liqConfigModel = new TableModelUtilLiquConfig(data, cols, books);
			jTable0.setModel(liqConfigModel);
		}jTable0.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				int selectID = jTable0.getSelectedRow();
				LiquidationConfig liqConfig = (LiquidationConfig)	data.get(jTable0.getSelectedRow());
				
				
				jComboBox2.setSelectedItem(liqConfig.getProducttype());
				jComboBox0.setSelectedItem(liqConfig.getLiqmethod());
				String bookName = getBook(liqConfig.getBookid()).getBook_name();
				jComboBox4.setSelectedItem(bookName);
				
				if(liqConfig.getDatetype().equalsIgnoreCase("TRADEDATE")) {
					jCheckBox0.setSelected(true);
					jCheckBox1.setSelected(false);
				} else {
					jCheckBox1.setSelected(true);
					jCheckBox0.setSelected(false);
				}
			
				
			}
			  });
		return jTable0;
	}

	private void processBookData(Vector book,DefaultComboBoxModel cmodel,boolean flag) {
		// TODO Auto-generated method stub
		
	    	if(flag) {
	    		Vector vector;
				Iterator it = book.iterator();
		    	int i =0;
	    			while(it.hasNext()) {			
	    					Book boo = (Book) it.next();
	    					cmodel.addElement(boo.getBook_name());
	    					books.put(i, boo);
	    					i++;
			
	    			}
	    	} else {
	    		Iterator it = book.iterator();
	    		int i =0;
    			while(it.hasNext()) {			
    				StartUPData boo = (StartUPData) it.next();
    					cmodel.addElement(boo.getName());
    					
    				
		
    			}
	    		
	    	}
	}
	public Book getBook( int bookID) {
		Book le = null;
    	
    	le = books.get(bookID);
    	
    	return le;
		
	}
		
	    	public void fillCLiquidationConfigConfig(LiquidationConfig liqConfig) {
	    		if(jComboBox2.getSelectedIndex() == -1 )  {
	    			commonUTIL.showAlertMessage("Select Product Type");
	    			return;
	    		}
	    		
	    		if(jComboBox4.getSelectedIndex() == -1 )  {
	    			commonUTIL.showAlertMessage("Select Book ");
	    			return;
	    		}
	    		if(jComboBox1.getSelectedIndex() == -1 )  {
	    			commonUTIL.showAlertMessage("Select ProductSubyType ");
	    			return;
	    		}
	    		if(jComboBox0.getSelectedIndex() == -1 )  {
	    			commonUTIL.showAlertMessage("Select Liquidation Method");
	    			return;
	    		}
	    		if( jCheckBox1.isSelected()) {
					
					String productType = jComboBox2.getSelectedItem().toString();
					if(!productType.equalsIgnoreCase("FX"))  {
						jCheckBox1.setSelected(false);
						commonUTIL.showAlertMessage("Select Product Type as FX");
						
						return;
						
					} else {
						liqConfig.setAvaliableLiquidation(false);
					}
				}
	    		liqConfig.setProducttype(jComboBox2.getSelectedItem().toString());
	    		liqConfig.setBookid(getBook(jComboBox4.getSelectedIndex()).getBookno());
	    		liqConfig.setProductsubtype(jComboBox1.getSelectedItem().toString());
	    		liqConfig.setLiqmethod(jComboBox0.getSelectedItem().toString());
	    		
	    		if(jCheckBox0.isSelected()) 
	    		   liqConfig.setDatetype("TRADEDATE");
	    		else 
	    			liqConfig.setDatetype("SETTLEDATE");
	    	}
		
	
class TableModelUtilLiquConfig extends AbstractTableModel {   
    
	 final String[] columnNames;  
	    
	 Vector<LiquidationConfig> data;   
	 RemoteReferenceData remoteRef ;
	 Hashtable<Integer,Book> books;
	 public TableModelUtilLiquConfig( Vector<LiquidationConfig> myData,String col [],Hashtable<Integer,Book> books) {   
	 	this.columnNames = col;
	this.data = myData;   
	this.books = books;
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
	     String cols [] = {"id","Book","ProductType","ProductSubType","Method","SortType"};
	     LiquidationConfig b2bConfig = (LiquidationConfig) data.get(row);
	    
		 switch (col) {
	     case 0:
	         value = b2bConfig.getId();
	         break;
	     case 1:
	    		Book le = (Book) getBook(b2bConfig.getBookid());
		         value =  le.getBook_name();
		         break;
	     case 2:
	         value =b2bConfig.getProducttype();
	         break;
	     case 3:
	    	 value =b2bConfig.getProductsubtype();
	         break;
	         
	     case 4:
	    		
	         value =b2bConfig.getLiqmethod();
	         break;
	     case 5:
	    		
	         value =b2bConfig.getDatetype();
	         break;
	    
		 }
	     return value;
	 }   
	 private Book getBook(int leID) {
	    	Book le = null;
	    	Enumeration boos = books.keys();
	    	while(boos.hasMoreElements()) {
	    		int id = ((Integer) boos.nextElement()).intValue();
	    		Book book = books.get(id);
	    		if(book.getBookno() == leID) {
	    			le = book;
	    			break;
	    		}
	    	}
	    	return le;
	    }
	 public boolean isCellEditable(int row, int col) {   
		 return false;   
		 }   
		 public void setValueAt(Object value, int row, int col) {   
		         System.out.println("Setting value at " + row + "," + col   
		                            + " to " + value   
		                            + " (an instance of "    
		                            + value.getClass() + ")");  
		         if(value instanceof LiquidationConfig) {
		     data.set(row,(LiquidationConfig) value) ;
		     this.fireTableDataChanged();   
		         System.out.println("New value of data:");   
		         }
		        
		 }   
		    
		 public void addRow(Object value) {   
		    
			 data.add((LiquidationConfig) value) ;
		 this.fireTableDataChanged();   
		   
		 }   
		    
		 public void delRow(int row) {   
		    if(row != -1) {
		 data.remove(row);          
		 this.fireTableDataChanged();   
		    }
		    
		 }   
		 
		 public void udpateValueAt(Object value, int row, int col) {   
		     
		  
		     data.set(row,(LiquidationConfig) value) ;
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
}
	
}

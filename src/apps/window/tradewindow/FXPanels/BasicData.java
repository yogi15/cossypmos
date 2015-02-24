package apps.window.tradewindow.FXPanels;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.RemoteException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;

import apps.window.tradewindow.FXTradePanel;
import beans.Book;
import beans.CurrencyPair;
import beans.LegalEntity;

import com.jidesoft.combobox.TableExComboBox;
import com.jidesoft.combobox.TableExComboBoxSearchable;
import com.jidesoft.grid.SortableTable;

import dsServices.RemoteReferenceData;


//VS4E -- DO NOT REMOVE THIS LINE!
public class BasicData extends JPanel {

	private static final long serialVersionUID = 1L;
	Vector<LegalEntity>_vectorLEs = null;
	public JLabel jLabel1;
	//public JTextField currencyPair;
	public JLabel jLabel2;
	public JRadioButton jRadioButton1;
	RemoteReferenceData remoteRef = null;
	public JLabel jLabel3;
	//public JTextField book;
	public JTextField buysell;
	public JRadioButton jRadioButton2;
	public JRadioButton jRadioButton3;
	public JLabel jLabel4;
	//public JTextField counterPary;
	public JRadioButton jRadioButton4;
	public JRadioButton jRadioButton5;
	public JRadioButton jRadioButton6;
	public JRadioButton jRadioButton7;
	public JRadioButton jRadioButton0;
	//public JTextField jTextField7;
	private JLabel jLabel0;
	TableModelUtil leModel = null;
	TableBookModelUtil bookModel = null;
	TableCpModelUtil cpModel = null;
	TableTraderModelUtil traderModel = null;
	public TableExComboBox counterPary = null;
	public TableExComboBox book = null;
	public TableExComboBox currencyPair = null;
	Vector<Book> _vectorBooks= null;
	//Vector<Fa> _vectorBooks= null;
	public TableExComboBox jTextField7 = null;
	Vector<LegalEntity> _vectorTraders= null;
	Vector<String> _vectorCp = null;
	private static final String PREFERRED_LOOK_AND_FEEL = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
	
	public BasicData() {
		initComponents();
	}

	public BasicData(RemoteReferenceData remoteReference) {
		// TODO Auto-generated constructor stub
		this.remoteRef = remoteReference;
		
			String col[] = { "CpId", "Name " };
			String cols[] = { "Bookid", "Name "  };
			String currp [] = {"CurrencyPair" };
			//remoteRef.getLegalEntityDataOnRole("CounterParty");
			leModel = new TableModelUtil(getAllLegalEntity(),col);
			bookModel = new TableBookModelUtil(getAllBooks(),cols);
			traderModel = new TableTraderModelUtil(getAllTraderEntity(),cols);
			cpModel = new TableCpModelUtil(getAllCurrencyPair(), currp);
		
		initComponents();
	}

	private void initComponents() {
		setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
		setLayout(new GroupLayout());
		add(getJLabel1(), new Constraints(new Leading(14, 75, 10, 10), new Leading(12, 18, 12, 12)));
		add(getJLabel2(), new Constraints(new Leading(99, 57, 10, 10), new Leading(12, 18, 12, 12)));
		add(getBook(), new Constraints(new Leading(99, 147, 12, 12), new Leading(40, 23, 12, 12)));
		add(getJLabel3(), new Constraints(new Leading(256, 64, 10, 10), new Leading(12, 12, 12)));
		add(getBuysell(), new Constraints(new Leading(258, 65, 12, 12), new Leading(40, 23, 12, 12)));
		add(getJLabel4(), new Constraints(new Leading(338, 121, 12, 12), new Leading(12, 12, 12)));
		add(getCounterPary(), new Constraints(new Leading(337, 178, 10, 10), new Leading(40, 23, 12, 12)));
		add(getJTextField7(), new Constraints(new Leading(523, 152, 10, 10), new Leading(40, 23, 12, 12)));
		add(getJLabel0(), new Constraints(new Leading(523, 121, 89, 96), new Leading(12, 12, 12)));
		add(getJRadioButton7(), new Constraints(new Leading(756, 12, 12), new Leading(32, 12, 12)));
		add(getJRadioButton6(), new Constraints(new Leading(758, 64, 12, 12), new Leading(5, 12, 12)));
		add(getJRadioButton1(), new Constraints(new Leading(677, 10, 10), new Leading(3, 17, 12, 12)));
		add(getJRadioButton2(), new Constraints(new Leading(677, 10, 10), new Leading(24, 16, 10, 10)));
		add(getJRadioButton0(), new Constraints(new Leading(677, 12, 12), new Leading(44, 19, 12, 12)));
		add(getJRadioButton5(), new Constraints(new Leading(677, 12, 12), new Leading(65, 15, 10, 10)));
		add(getCurrencyPair(), new Constraints(new Leading(12, 83, 10, 10), new Leading(40, 23, 12, 12)));
		setSize(864, 106);
	}

	private JLabel getJLabel0() {
		if (jLabel0 == null) {
			jLabel0 = new JLabel();
			jLabel0.setText("Trader");
		}
		return jLabel0;
	}

	private TableExComboBox getJTextField7() {
		if (jTextField7 == null) {
		//	jTextField7 = new TableExComboBox();
			//jTextField7.setText("jTextField0");
			jTextField7 = new TableExComboBox(
					traderModel) {
				@Override
				protected JTable createTable(TableModel model) {
					return new SortableTable(model);
				}
				
				
			};
			jTextField7.setEditable(false);
			jTextField7.setBorder(null);
			jTextField7.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					int leid = jTextField7.getSelectedIndex();
					// @ yogesh 24/02/2015
					// returns if -1
					if(leid == 0 || leid == -1)
						return;
					LegalEntity le = _vectorTraders.get(leid);
					jTextField7.setName(String.valueOf(((le)).getId()));
					//System.out.println(counterPary.getName());
					
				}
			});
			jTextField7.setValueColumnIndex(1);
//			
			//counterPary.setSelectedItem("ALCOA INC");
			
			jTextField7.setEditable(false);
			new TableExComboBoxSearchable(counterPary);
			return jTextField7;
		}
		return jTextField7;
	}

	private JRadioButton getJRadioButton0() {
		if (jRadioButton0 == null) {
			jRadioButton0 = new JRadioButton();
			jRadioButton0.setSelected(true);
			jRadioButton0.setText("TakeUP");
			jRadioButton0.setEnabled(false);
		}
		return jRadioButton0;
	}

	private JRadioButton getJRadioButton7() {
		if (jRadioButton7 == null) {
			jRadioButton7 = new JRadioButton();
			jRadioButton7.setSelected(true);
			jRadioButton7.setText("RollOver");
			jRadioButton7.setEnabled(false);
		}
		return jRadioButton7;
	}

	private JRadioButton getJRadioButton6() {
		if (jRadioButton6 == null) {
			jRadioButton6 = new JRadioButton();
			jRadioButton6.setSelected(true);
			jRadioButton6.setText("RollBack");
			jRadioButton6.setEnabled(false);
		}
		return jRadioButton6;
	}

	private JRadioButton getJRadioButton4() {
		if (jRadioButton4 == null) {
			jRadioButton4 = new JRadioButton();
			jRadioButton4.setSelected(true);
			jRadioButton4.setText("Non Deliverable");
		}
		return jRadioButton4;
	}

	private JRadioButton getJRadioButton5() {
		if (jRadioButton5 == null) {
			jRadioButton5 = new JRadioButton();
			jRadioButton5.setSelected(true);
			jRadioButton5.setText("Merchant");
		}
		return jRadioButton5;
	}

	private TableExComboBox getCounterPary() {
		//if (counterPary == null) {
		//	counterPary = new JTextField();
		//	counterPary.setText("Counterparty");
		//}
	
		counterPary = new TableExComboBox(
				leModel) {
			@Override
			protected JTable createTable(TableModel model) {
				return new SortableTable(model);
			}
			
			
		};
		counterPary.setEditable(false);
		counterPary.setBorder(null);
		counterPary.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				int leid = counterPary.getSelectedIndex();
				// @ yogesh 24/02/2015
				// returns if -1
				if(leid == 0 || leid == -1)
					return;
				LegalEntity le = _vectorLEs.get(leid);
				counterPary.setName(String.valueOf(((le)).getId()));
				//System.out.println(counterPary.getName());
				
			}
		});
		counterPary.setValueColumnIndex(1);
//		
		//counterPary.setSelectedItem("ALCOA INC");
		
		counterPary.setEditable(false);
		new TableExComboBoxSearchable(counterPary);
		return counterPary;
	}

	private JLabel getJLabel4() {
		if (jLabel4 == null) {
			jLabel4 = new JLabel();
			jLabel4.setText("CounterParty");
		}
		return jLabel4;
	}

	private JRadioButton getJRadioButton3() {
		if (jRadioButton3 == null) {
			jRadioButton3 = new JRadioButton();
			jRadioButton3.setSelected(true);
			jRadioButton3.setText("Deliverable");
		}
		return jRadioButton3;
	}
boolean keyPress = true;

	/**
 * @return the keyPress
 */
public boolean isKeyPress() {
	return keyPress;
}

/**
 * @param keyPress the keyPress to set
 */
public void setKeyPress(boolean keyPress) {
	this.keyPress = keyPress;
}

	private JRadioButton getJRadioButton2() {
		if (jRadioButton2 == null) {
			jRadioButton2 = new JRadioButton();
			//jRadioButton2.setSelected(true);
			jRadioButton2.setText("Swap");
		}
		return jRadioButton2;
	}

	private JTextField getBuysell() {
		if (buysell == null) {
			buysell = new JTextField();
			buysell.setText("BUY");
			buysell.setBackground(Color.green);
			buysell.addKeyListener(new KeyListener() {
				
				@Override
				public void keyTyped(KeyEvent arg0) {
					// TODO Auto-generated method stub
					char c = arg0.getKeyChar();
					if(c == 'B' || c == 'S' || c == 's' || c == 'b')  {
						setKeyPress(true);
					}else {
						setKeyPress(false);
						//buysell.setText(buysell.getText());
						//buysell.setBackground(Color.green);
						return;
					}
				}
				
				@Override
				public void keyReleased(KeyEvent arg0) {
					// TODO Auto-generated method stub
					char c = arg0.getKeyChar();
					
				    boolean isBuysellP = false;
					if(c == 'B' || c == 'b') {
						isBuysellP = true;
						if(jRadioButton2.isSelected()) {
							buysell.setText("BUY/SELL");
							buysell.setBackground(Color.green);
						} else {
							buysell.setText("BUY");
							buysell.setBackground(Color.green);
						}
					} else 
					if(c == 'S' ||  c == 's') {
						isBuysellP = true;
						if(jRadioButton2.isSelected()) {
							buysell.setText("SELL/BUY");
							buysell.setBackground(Color.red);
						} else {
							buysell.setText("SELL");
							buysell.setBackground(Color.red);
						}
					} else {
						if(!isKeyPress()) {
						if(jRadioButton2.isSelected()) {
							buysell.setText("BUY/SELL");
							buysell.setBackground(Color.green);
						} else {
							buysell.setText("BUY");
							buysell.setBackground(Color.green);
						}
						}	
					}
					
				}
				
				@Override
				public void keyPressed(KeyEvent arg0) {
					// TODO Auto-generated method stub
					char c = arg0.getKeyChar();
					if(c == 'B' || c == 'S' || c == 's' || c == 'b')  {
						setKeyPress(true);
					}else {
						setKeyPress(false);
					//	buysell.setText(buysell.getText());
						//buysell.setBackground(Color.green);
						return;
					}
				}
			});
			
		}
		return buysell;
	}

	private TableExComboBox getBook() {
		if (book == null) {
		//	book = new TableExComboBox();
		//	book.setText("Book");
			book = new TableExComboBox(
					bookModel) {
				@Override
				protected JTable createTable(TableModel model) {
					return new SortableTable(model);
				}
				
				
			};
			book.setEditable(false);
			book.setBorder(null);
			book.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					int leid = book.getSelectedIndex();
					// @ yogesh 24/02/2015
					// returns if -1
					if(leid == 0 || leid == -1)
						return;
					Book le = _vectorBooks.get(leid);
					book.setName(String.valueOf(((le)).getBookno()));
					//System.out.println(counterPary.getName());
					
				}
			});
			book.setValueColumnIndex(1);
//			
			//counterPary.setSelectedItem("ALCOA INC");
		
			
			new TableExComboBoxSearchable(book);
			book.setEditable(false);
			return book;
		}
		return book;
	}

	private JLabel getJLabel3() {
		if (jLabel3 == null) {
			jLabel3 = new JLabel();
			jLabel3.setText("BUY/SELL");
			 jLabel3.addMouseListener(new MouseListener() {
				
				
				@Override
				public void mouseClicked(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mousePressed(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
					String buysellt = buysell.getText().toString();
					if(buysellt.equalsIgnoreCase("BUY") || buysellt.equalsIgnoreCase("BUY/SELL")) 
						buysell.setBackground(Color.red);
						if(buysellt.equalsIgnoreCase("SELL") || buysellt.equalsIgnoreCase("SELL/BUY")) 
							buysell.setBackground(Color.green);
						
						
					
				}
			});

				
		} 
		
		return jLabel3;
	}

	private JRadioButton getJRadioButton1() {
		if (jRadioButton1 == null) {
			jRadioButton1 = new JRadioButton();
			jRadioButton1.setSelected(true);
			jRadioButton1.setText("OutRight");
		}
		return jRadioButton1;
	}

	private JLabel getJLabel2() {
		if (jLabel2 == null) {
			jLabel2 = new JLabel();
			jLabel2.setText("Book");
		}
		return jLabel2;
	}

	private TableExComboBox getCurrencyPair() {
		if (currencyPair == null) {
			//currencyPair = new TableExComboBox();
		//	currencyPair.setText("CurrencyPair");
			currencyPair = new TableExComboBox(
					cpModel) {
				@Override
				protected JTable createTable(TableModel model) {
					return new SortableTable(model);
				}
				
				
			};
			currencyPair.setEditable(false);
			currencyPair.setBorder(null);
			currencyPair.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					int leid = currencyPair.getSelectedIndex();
					if(leid == 0)
						return;
					//String le = _vectorCp.get(leid);
					currencyPair.setName((String) currencyPair.getSelectedItem());
					
					//System.out.println(counterPary.getName());
					
				}
			});
			currencyPair.setValueColumnIndex(0);
//			
			//counterPary.setSelectedItem("ALCOA INC");
		
			
			new TableExComboBoxSearchable(currencyPair);
			currencyPair.setEditable(false);
			return currencyPair;
		}
		return currencyPair;
		
	}

	private JLabel getJLabel1() {
		if (jLabel1 == null) {
			jLabel1 = new JLabel();
			jLabel1.setText("Currency Pair");
		}
		return jLabel1;
	}
	
	
	protected Vector<LegalEntity> getAllLegalEntity(){ 
		try {
			_vectorLEs = (Vector) remoteRef.getLegalEntityDataOnRole("CounterParty");
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return _vectorLEs;
		
	}
	protected Vector<String> getAllCurrencyPair(){
		Vector<String> _vectorCPs  = new Vector<String>();
		try {
			
			Vector cps = (Vector) remoteRef.selectALLCurrencyPair();
			Iterator it = cps.iterator();
	 		
	 		int p = 0;
			
			while (it.hasNext()) {
				CurrencyPair data = (CurrencyPair) it.next();
				_vectorCPs.add(new String(data.getPrimary_currency()+"/"+data.getQuoting_currency()));	
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return _vectorCPs;
		
	}
	protected Vector<LegalEntity> getAllTraderEntity(){ 
		try {
			_vectorTraders = (Vector) remoteRef.getLegalEntityDataOnRole("Trader");
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return _vectorTraders;
		
	}
	protected Vector<Book> getAllBooks(){ 
		try {
			_vectorBooks = (Vector) remoteRef.selectALLBooks();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return _vectorBooks;
		
	}
	
	
	 class TableBookModelUtil extends AbstractTableModel {

			final String[] columnNames;

			Vector<Book> data;
			
			

			public TableBookModelUtil(Vector<Book> myData, String col[]) {
				this.columnNames = col;
				this.data = myData;
			
			}

			public int getColumnCount() {
				return columnNames.length;
			}

			public int getRowCount() {
				if (data != null)
					return data.size();
				return 0;
			}

			public String getColumnName(int col) {
				return columnNames[col];
			}

			public Object getValueAt(int row, int col) {
				Object value = null;

				Book book = (Book) data.get(row);

				switch (col) {
				case 0:
					value = book.getBookno();
					break;
				case 1:
					value = book.getBook_name();
					break;
				

				}
				return value;
			}

			public boolean isCellEditable(int row, int col) {
				return false;
			}

			public void setValueAt(Object value, int row, int col) {
				
					data.set(row, (Book) value);
					this.fireTableDataChanged();
				
				

			}

			public void addRow(Object value) {

				data.add((Book) value);
				this.fireTableDataChanged();

			}

			public void delRow(int row) {
				if (row != -1) {
					data.remove(row);
					this.fireTableDataChanged();
				}

			}

			public void udpateValueAt(Object value, int row, int col) {

				data.set(row, (Book) value);
				for (int i = 0; i < columnNames.length; i++)
					fireTableCellUpdated(row, i);

			}

			public void removeALL() {
				if (data != null) {
					data.removeAllElements();
				}
				data = null;
				this.fireTableDataChanged();
			}
		}
	  class TableTraderModelUtil extends AbstractTableModel {

			final String[] columnNames;

			Vector<LegalEntity> data;
			RemoteReferenceData remoteRef;
			Hashtable<Integer, Book> books;

			public TableTraderModelUtil(Vector<LegalEntity> myData, String col[]) {
				this.columnNames = col;
				this.data = myData;
				this.books = books;
			}

			public int getColumnCount() {
				return columnNames.length;
			}

			public int getRowCount() {
				if (data != null)
					return data.size();
				return 0;
			}

			public String getColumnName(int col) {
				return columnNames[col];
			}

			public Object getValueAt(int row, int col) {
				Object value = null;

				LegalEntity currSplit = (LegalEntity) data.get(row);

				switch (col) {
				case 0:
					value = currSplit.getId();
					break;
				case 1:
					value = currSplit.getName();
					break;
				

				}
				return value;
			}

			public boolean isCellEditable(int row, int col) {
				return false;
			}

			public void setValueAt(Object value, int row, int col) {
				
					data.set(row, (LegalEntity) value);
					this.fireTableDataChanged();
				
				

			}

			public void addRow(Object value) {

				data.add((LegalEntity) value);
				this.fireTableDataChanged();

			}

			public void delRow(int row) {
				if (row != -1) {
					data.remove(row);
					this.fireTableDataChanged();
				}

			}

			public void udpateValueAt(Object value, int row, int col) {

				data.set(row, (LegalEntity) value);
				for (int i = 0; i < columnNames.length; i++)
					fireTableCellUpdated(row, i);

			}

			public void removeALL() {
				if (data != null) {
					data.removeAllElements();
				}
				data = null;
				this.fireTableDataChanged();
			}
		}
	  class   TableCpModelUtil  extends AbstractTableModel {

			final String[] columnNames;

			Vector<String> data;
			RemoteReferenceData remoteRef;
			Hashtable<Integer, Book> books;

			public TableCpModelUtil(Vector<String> myData, String col[]) {
				this.columnNames = col;
				this.data = myData;
				
			}

			public int getColumnCount() {
				return columnNames.length;
			}

			public int getRowCount() {
				if (data != null)
					return data.size();
				return 0;
			}

			public String getColumnName(int col) {
				return columnNames[col];
			}

			public Object getValueAt(int row, int col) {
				Object value = null;

				String currSplit = (String) data.get(row);

				switch (col) {
				case 0:
					value = currSplit;
					break;
				
				

				}
				return value;
			}

			public boolean isCellEditable(int row, int col) {
				return false;
			}

			public void setValueAt(Object value, int row, int col) {
				
					data.set(row, (String) value);
					this.fireTableDataChanged();
				
				

			}

			public void addRow(Object value) {

				data.add((String) value);
				this.fireTableDataChanged();

			}

			public void delRow(int row) {
				if (row != -1) {
					data.remove(row);
					this.fireTableDataChanged();
				}

			}

			public void udpateValueAt(Object value, int row, int col) {

				data.set(row, (String) value);
				for (int i = 0; i < columnNames.length; i++)
					fireTableCellUpdated(row, i);

			}

			public void removeALL() {
				if (data != null) {
					data.removeAllElements();
				}
				data = null;
				this.fireTableDataChanged();
			}
		}
	
	   class TableModelUtil extends AbstractTableModel {

			final String[] columnNames;

			Vector<LegalEntity> data;
			RemoteReferenceData remoteRef;
			Hashtable<Integer, Book> books;

			public TableModelUtil(Vector<LegalEntity> myData, String col[]) {
				this.columnNames = col;
				this.data = myData;
				this.books = books;
			}

			public int getColumnCount() {
				return columnNames.length;
			}

			public int getRowCount() {
				if (data != null)
					return data.size();
				return 0;
			}

			public String getColumnName(int col) {
				return columnNames[col];
			}

			public Object getValueAt(int row, int col) {
				Object value = null;

				LegalEntity currSplit = (LegalEntity) data.get(row);

				switch (col) {
				case 0:
					value = currSplit.getId();
					break;
				case 1:
					value = currSplit.getName();
					break;
				

				}
				return value;
			}

			public boolean isCellEditable(int row, int col) {
				return false;
			}

			public void setValueAt(Object value, int row, int col) {
				
					data.set(row, (LegalEntity) value);
					this.fireTableDataChanged();
				
				

			}

			public void addRow(Object value) {

				data.add((LegalEntity) value);
				this.fireTableDataChanged();

			}

			public void delRow(int row) {
				if (row != -1) {
					data.remove(row);
					this.fireTableDataChanged();
				}

			}

			public void udpateValueAt(Object value, int row, int col) {

				data.set(row, (LegalEntity) value);
				for (int i = 0; i < columnNames.length; i++)
					fireTableCellUpdated(row, i);

			}

			public void removeALL() {
				if (data != null) {
					data.removeAllElements();
				}
				data = null;
				this.fireTableDataChanged();
			}
		}
	

}
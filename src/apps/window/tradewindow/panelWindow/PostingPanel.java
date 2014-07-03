package apps.window.tradewindow.panelWindow;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

import org.dyno.visual.swing.layouts.Bilateral;
import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;
import org.dyno.visual.swing.layouts.Trailing;

import swingUtils.TableColumnAdjuster;
import apps.window.tradewindow.BackOfficePanel;
import beans.Account;
import beans.Posting;
import dsServices.RemoteAccount;
import dsServices.RemoteReferenceData;

//VS4E -- DO NOT REMOVE THIS LINE!
public class PostingPanel  extends BackOfficePanel {

	private static final long serialVersionUID = 1L;
	private JTable jTable0;
	private JScrollPane jScrollPane0;
	private JLabel idLabel;
	private JTextField tradeTextField;
	private JTextField idTextField;
	private JTextField transferIdText;
	private JLabel linkIdLabel;
	private JTextField linkIdTextField;
	private JLabel tradeIdLabel;
	private JLabel transferIdLabel;
	private JPanel jPanel0;
	private JLabel eventTypeLabel;
	private JLabel accEventTypeLabel;
	private JLabel typeLabel;
	private JLabel ruleNameLabel;
	private JTextField eventTypeTextField;
	private JTextField typeTextField;
	private JTextField ruleNameTextField;
	private JTextField accEventtypeTextField;
	private JLabel bookingDateLabel;
	private JLabel creationDateLabel;
	private JLabel effectiveDateLabel;
	private JLabel statusLabel;
	private JTextField bookingDateTextField;
	private JTextField creationDateTextField;
	private JTextField effectiveDateTextField;
	private JTextField statusTextField;
	private JLabel debitAccLabel;
	private JTextField debitAccTextField;
	private JLabel debitAmtLabel;
	private JTextField debitAmtTextField;
	private JLabel creditAccLabel;
	private JTextField creditAccTextField;
	private JLabel creditAmtLabel;
	private JTextField creditAmtTextField;
	private RemoteAccount remoteAccount;
	Vector<Posting> data = new Vector<Posting>();
	 java.util.Hashtable<Integer,String> accontName = new Hashtable();
	 TableModelUtil model = null;
	  String cols [] = {  "Posting ID", "tradeID","transferId","EventType","AccEventType","Type","linkId","Currency","EffectiveDate","BookingDate","CreationDate","Status","RuleName","DebitAcc","creditAcc","DebitAmt","CreditAmt"};
	private RemoteReferenceData remoteRef;
	private JPanel jPanel1;
	private JButton refreshButton;
	private JLabel currencyLabel;
	private JTextField currencyTextField;
	public PostingPanel() {
		initComponents();
	}

	private void initComponents() {
		setFocusable(true);
		setEnabled(true);
		setVisible(true);
		setVerifyInputWhenFocusTarget(true);
		setDoubleBuffered(true);
		setRequestFocusEnabled(true);
		setOpaque(true);
		setLayout(new GroupLayout());
		add(getJPanel0(), new Constraints(new Bilateral(8, 9, 0), new Trailing(8, 309, 329, 329)));
		add(getJPanel1(), new Constraints(new Bilateral(8, 8, 1129), new Bilateral(7, 323, 10, 282)));
		setSize(1353, 513);
	}

	private JTextField getCurrencyTextField() {
		if (currencyTextField == null) {
			currencyTextField = new JTextField();
			currencyTextField.setEditable(false);
		}
		return currencyTextField;
	}

/*	private JLabel getJLabel16() {
		if (currencyLabel == null) {
			currencyLabel = new JLabel();
			currencyLabel.setText("Currency");
		}
		return currencyLabel;
	}*/

	private JButton getRefreshButton() {
		if (refreshButton == null) {
			refreshButton = new JButton();
			refreshButton.setText("Refresh");
		}refreshButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				trade = getTrade();
				if(trade == null)
					 return;
				try {
			
				String where = " tradeid = " + getTrade().getId() + " and linkTo = 0 order by type,acceventtype  ";
				data = (Vector)	remoteAccount.getPostingonWhereClause(where);
				model = null;
				model = new TableModelUtil(data,cols,remoteAccount);
				jTable0.setModel(model);
				jTable0.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

				TableColumnAdjuster tca = new TableColumnAdjuster(jTable0);
				tca.adjustColumns();
				
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		
		});
		
		return refreshButton;
	}

	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setBorder(new LineBorder(Color.black, 1, false));
			jPanel1.setLayout(new GroupLayout());
			jPanel1.add(getJScrollPane0(), new Constraints(new Leading(6, 1299, 10, 10), new Bilateral(10, 9, 10, 259)));
		}
		return jPanel1;
	}

	private JTextField getCreditAmtTextField() {
		if (creditAmtTextField == null) {
			creditAmtTextField = new JTextField();
			creditAmtTextField.setEditable(false);
			//creditAmtTextField.setText("creditAmtTextField");
		}
		return creditAmtTextField;
	}

	private JLabel getCreditAmtLabel() {
		if (creditAmtLabel == null) {
			creditAmtLabel = new JLabel();
			creditAmtLabel.setText("CreditAmt");
		}
		return creditAmtLabel;
	}

	private JTextField getCreditAccTextField() {
		if (creditAccTextField == null) {
			creditAccTextField = new JTextField();
			creditAccTextField.setEditable(false);
		//	creditAccTextField.setText("creditAccTextField");
		}
		return creditAccTextField;
	}

	private JLabel getCreditAccLabel() {
		if (creditAccLabel == null) {
			creditAccLabel = new JLabel();
			creditAccLabel.setText("CreditA/C");
		}
		return creditAccLabel;
	}

	private JTextField getDebitAmtTextField() {
		if (debitAmtTextField == null) {
			debitAmtTextField = new JTextField();
			debitAmtTextField.setEditable(false);
			//debitAmtTextField.setText("debitAmtTextField");
		}
		return debitAmtTextField;
	}

	private JLabel getDebitAmtLabel() {
		if (debitAmtLabel == null) {
			debitAmtLabel = new JLabel();
			debitAmtLabel.setText("DebitAmt");
		}
		return debitAmtLabel;
	}

	private JTextField getDebitAccTextField() {
		if (debitAccTextField == null) {
			debitAccTextField = new JTextField();
			debitAccTextField.setEditable(false);
		//	debitAccTextField.setText("debitAccTextField");
		}
		return debitAccTextField;
	}

	private JLabel getDebitAccLabel() {
		if (debitAccLabel == null) {
			debitAccLabel = new JLabel();
			debitAccLabel.setText("DebitA/C");
		}
		return debitAccLabel;
	}

	private JTextField getStatusTextField() {
		if (statusTextField == null) {
			statusTextField = new JTextField();
			statusTextField.setEditable(false);
		//	statusTextField.setText("statusTextField");
		}
		return statusTextField;
	}

	private JTextField getEffectiveDateTextField() {
		if (effectiveDateTextField == null) {
			effectiveDateTextField = new JTextField();
			effectiveDateTextField.setEditable(false);
		//	effectiveDateTextField.setText("effectiveDateTextField");
		}
		return effectiveDateTextField;
	}

	private JTextField getCreationDateTextField() {
		if (creationDateTextField == null) {
			creationDateTextField = new JTextField();
			creationDateTextField.setEditable(false);
	//		creationDateTextField.setText("creationDateTextField");
		}
		return creationDateTextField;
	}

	private JTextField getBookingDateTextField() {
		if (bookingDateTextField == null) {
			bookingDateTextField = new JTextField();
			bookingDateTextField.setEditable(false);
		//	bookingDateTextField.setText("bookingDateTextField");
		}
		return bookingDateTextField;
	}

	private JLabel getStatusLabel() {
		if (statusLabel == null) {
			statusLabel = new JLabel();
			statusLabel.setText("Status");
		}
		return statusLabel;
	}

	private JLabel getEffectiveDateLabel() {
		if (effectiveDateLabel == null) {
			effectiveDateLabel = new JLabel();
			effectiveDateLabel.setText("EffectiveDate");
		}
		return effectiveDateLabel;
	}

	private JLabel getCreationDateLabel() {
		if (creationDateLabel == null) {
			creationDateLabel = new JLabel();
			creationDateLabel.setText("CreationDate");
		}
		return creationDateLabel;
	}

	private JLabel getBookingDateLabel() {
		if (bookingDateLabel == null) {
			bookingDateLabel = new JLabel();
			bookingDateLabel.setText("BookingDate");
		}
		return bookingDateLabel;
	}

	private JTextField getAccEventtypeTextField() {
		if (accEventtypeTextField == null) {
			accEventtypeTextField = new JTextField();
			accEventtypeTextField.setEditable(false);
			//accEventtypeTextField.setText("accEventtypeTextField");
		}
		return accEventtypeTextField;
	}

	private JTextField getRuleNameTextField() {
		if (ruleNameTextField == null) {
			ruleNameTextField = new JTextField();
			ruleNameTextField.setEditable(false);
		//	ruleNameTextField.setText("ruleNameTextField");
		}
		return ruleNameTextField;
	}

	private JTextField getTypeTextField() {
		if (typeTextField == null) {
			typeTextField = new JTextField();
			typeTextField.setEditable(false);
		//	typeTextField.setText("typeTextField");
		}
		return typeTextField;
	}

	private JTextField getEventTypeTextField() {
		if (eventTypeTextField == null) {
			eventTypeTextField = new JTextField();
			eventTypeTextField.setEditable(false);
		//	eventTypeTextField.setText("eventTypeTextField");
		}
		return eventTypeTextField;
	}

	private JLabel getRuleNameLabel() {
		if (ruleNameLabel == null) {
			ruleNameLabel = new JLabel();
			ruleNameLabel.setText("RuleName");
		}
		return ruleNameLabel;
	}

	private JLabel getTypeLabel() {
		if (typeLabel == null) {
			typeLabel = new JLabel();
			typeLabel.setText("Type");
		}
		return typeLabel;
	}

	private JLabel getAccEventTypeLabel() {
		if (accEventTypeLabel == null) {
			accEventTypeLabel = new JLabel();
			accEventTypeLabel.setText("AccEventType");
		}
		return accEventTypeLabel;
	}

	private JLabel getEventTypeLabel() {
		if (eventTypeLabel == null) {
			eventTypeLabel = new JLabel();
			eventTypeLabel.setText("EventType");
		}
		return eventTypeLabel;
	}

	private JPanel getJPanel0() {
		if (jPanel0 == null) {
			jPanel0 = new JPanel();
			jPanel0.setLayout(new GroupLayout());
			jPanel0.add(getTradeTextField(), new Constraints(new Leading(93, 71, 12, 12), new Leading(33, 10, 10)));
			jPanel0.add(getIdTextField(), new Constraints(new Leading(93, 71, 12, 12), new Leading(5, 12, 12)));
			jPanel0.add(getTransferIdText(), new Constraints(new Leading(91, 71, 10, 10), new Leading(59, 10, 10)));
			jPanel0.add(getLinkIdTextField(), new Constraints(new Leading(91, 71, 10, 10), new Leading(84, 10, 10)));
			jPanel0.add(getTransferIdLabel(), new Constraints(new Leading(5, 66, 10, 10), new Leading(64, 12, 12)));
			jPanel0.add(getDebitAccTextField(), new Constraints(new Leading(89, 166, 12, 12), new Leading(119, 12, 12)));
			jPanel0.add(getCreditAccTextField(), new Constraints(new Leading(91, 166, 12, 12), new Leading(146, 12, 12)));
			jPanel0.add(getCreditAmtTextField(), new Constraints(new Leading(393, 137, 12, 12), new Leading(145, 12, 12)));
			jPanel0.add(getRuleNameTextField(), new Constraints(new Leading(395, 176, 12, 12), new Leading(86, 12, 12)));
			jPanel0.add(getDebitAmtTextField(), new Constraints(new Leading(393, 137, 12, 12), new Leading(116, 12, 12)));
			jPanel0.add(getDebitAmtLabel(), new Constraints(new Leading(290, 66, 12, 12), new Leading(122, 12, 12)));
			jPanel0.add(getRuleNameLabel(), new Constraints(new Leading(290, 81, 12, 12), new Leading(91, 12, 12)));
			jPanel0.add(getEventTypeLabel(), new Constraints(new Leading(290, 93, 12, 12), new Leading(12, 12, 12)));
			jPanel0.add(getTypeTextField(), new Constraints(new Leading(393, 137, 12, 12), new Leading(58, 12, 12)));
			jPanel0.add(getTypeLabel(), new Constraints(new Leading(288, 93, 12, 12), new Leading(63, 12, 12)));
			jPanel0.add(getTradeIdLabel(), new Constraints(new Leading(9, 49, 12, 12), new Leading(37, 12, 12)));
			jPanel0.add(getIdLabel(), new Constraints(new Leading(15, 10, 10), new Leading(9, 12, 12)));
			jPanel0.add(getLinkIdLabel(), new Constraints(new Leading(9, 66, 12, 12), new Leading(88, 12, 12)));
			jPanel0.add(getCreditAccLabel(), new Constraints(new Leading(5, 66, 12, 12), new Leading(151, 14, 12, 12)));
			jPanel0.add(getDebitAccLabel(), new Constraints(new Leading(5, 66, 12, 12), new Leading(123, 12, 12)));
			jPanel0.add(getAccEventtypeTextField(), new Constraints(new Leading(393, 176, 12, 12), new Leading(34, 12, 12)));
			jPanel0.add(getEventTypeTextField(), new Constraints(new Leading(393, 137, 12, 12), new Leading(5, 12, 12)));
			jPanel0.add(getStatusLabel(), new Constraints(new Leading(662, 54, 10, 10), new Leading(7, 14, 12, 12)));
			jPanel0.add(getStatusTextField(), new Constraints(new Leading(753, 137, 10, 10), new Leading(5, 12, 12)));
			jPanel0.add(getAccEventTypeLabel(), new Constraints(new Leading(288, 93, 12, 12), new Leading(40, 12, 12)));
			jPanel0.add(getBookingDateTextField(), new Constraints(new Leading(753, 137, 12, 12), new Leading(31, 12, 12)));
			jPanel0.add(getBookingDateLabel(), new Constraints(new Leading(662, 75, 12, 12), new Leading(37, 12, 12)));
			jPanel0.add(getCreationDateTextField(), new Constraints(new Leading(753, 137, 12, 12), new Leading(62, 12, 12)));
			jPanel0.add(getCreationDateLabel(), new Constraints(new Leading(664, 77, 12, 12), new Leading(64, 12, 12)));
			jPanel0.add(getEffectiveDateTextField(), new Constraints(new Leading(755, 137, 12, 12), new Leading(88, 12, 12)));
			jPanel0.add(getCurrencyTextField(), new Constraints(new Leading(753, 90, 10, 10), new Leading(116, 12, 12)));
			jPanel0.add(getEffectiveDateLabel(), new Constraints(new Leading(664, 83, 12, 12), new Leading(91, 19, 12, 12)));
			jPanel0.add(getRefreshButton(), new Constraints(new Leading(662, 12, 12), new Leading(159, 10, 10)));
			jPanel0.add(getCurrencyLabel(), new Constraints(new Leading(667, 10, 10), new Leading(120, 12, 12)));
			jPanel0.add(getCreditAmtLabel(), new Constraints(new Leading(290, 66, 12, 12), new Leading(151, 12, 12)));
		}
		return jPanel0;
	}

	private JLabel getCurrencyLabel() {
		if (currencyLabel == null) {
			currencyLabel = new JLabel();
			currencyLabel.setText("Currency");
		}
		return currencyLabel;
	}

	private JLabel getTransferIdLabel() {
		if (transferIdLabel == null) {
			transferIdLabel = new JLabel();
			transferIdLabel.setText("Transfer ID");
		}
		return transferIdLabel;
	}

	private JLabel getTradeIdLabel() {
		if (tradeIdLabel == null) {
			tradeIdLabel = new JLabel();
			tradeIdLabel.setText("Trade Id");
		}
		return tradeIdLabel;
	}

	private JTextField getLinkIdTextField() {
		if (linkIdTextField == null) {
			linkIdTextField = new JTextField();
			linkIdTextField.setEditable(false);
		//	linkIdTextField.setText("linkIdTextField");
		}
		return linkIdTextField;
	}

	private JLabel getLinkIdLabel() {
		if (linkIdLabel == null) {
			linkIdLabel = new JLabel();
			linkIdLabel.setText("Link ID");
		}
		return linkIdLabel;
	}

	private JTextField getTransferIdText() {
		if (transferIdText == null) {
			transferIdText = new JTextField();
			transferIdText.setEditable(false);
		//	transferIdText.setText("transferIdText");
		}
		return transferIdText;
	}

	private JTextField getIdTextField() {
		if (idTextField == null) {
			idTextField = new JTextField();
			idTextField.setEditable(false);
			//idTextField.setText("idTextField");
		}
		return idTextField;
	}

	private JTextField getTradeTextField() {
		if (tradeTextField == null) {
			tradeTextField = new JTextField();
			tradeTextField.setEditable(false);
		//	tradeTextField.setText("tradeTextField");
		}
		return tradeTextField;
	}

	private JLabel getIdLabel() {
		if (idLabel == null) {
			idLabel = new JLabel();
			idLabel.setText("ID");
		}
		return idLabel;
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
			
			
			 
			  model = new TableModelUtil(data,cols,remoteAccount);
			  jTable0 = new JTable( model )
				{
					public Component prepareRenderer(TableCellRenderer renderer, int row, int column)
					{
						Component c = super.prepareRenderer(renderer, row, column);

						//  Color row based on a cell value

						if (!isRowSelected(row))
						{
							c.setBackground(getBackground());
							int modelRow = convertRowIndexToModel(row);
							String type = (String)getModel().getValueAt(modelRow, 5);
							if ("REVERSAL".equals(type)) c.setBackground(Color.orange);
						//	if ("Sell".equals(type)) c.setBackground(Color.YELLOW);
						}

						return c;
					}
				};

	//		jTable0.setPreferredScrollableViewportSize(new Dimension(500, 70));
		//	jTable0.setFillsViewportHeight(true);
		//	jTable0.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		//-	jTable0.getTableHeader().setReorderingAllowed(false);
		//-	jTable0.getTableHeader().setResizingAllowed(true);
		//	for(int i=0;i<cols.length;i++) 
			/* jTable0.getColumnModel().getColumn(0).setPreferredWidth(240); 
			 jTable0.getColumnModel().getColumn(1).setPreferredWidth(240); 
			 jTable0.getColumnModel().getColumn(2).setPreferredWidth(240); 
			   jTable0.getColumnModel().getColumn(3).setPreferredWidth(440); 
			   jTable0.getColumnModel().getColumn(4).setPreferredWidth(640); 
			   jTable0.getColumnModel().getColumn(5).setPreferredWidth(340); 
			   jTable0.getColumnModel().getColumn(6).setPreferredWidth(440); 
			   jTable0.getColumnModel().getColumn(7).setPreferredWidth(440); 
			   jTable0.getColumnModel().getColumn(8).setPreferredWidth(470); 
			   jTable0.getColumnModel().getColumn(9).setPreferredWidth(470); 
			   jTable0.getColumnModel().getColumn(10).setPreferredWidth(470); 
			   jTable0.getColumnModel().getColumn(11).setPreferredWidth(440); 
			   jTable0.getColumnModel().getColumn(12).setPreferredWidth(10); 
			   jTable0.getColumnModel().getColumn(13).setPreferredWidth(10); 
			   jTable0.getColumnModel().getColumn(14).setPreferredWidth(10); 
			   jTable0.getColumnModel().getColumn(15).setPreferredWidth(2); */
			 
				jTable0.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

				TableColumnAdjuster tca1 = new TableColumnAdjuster(jTable0);
				tca1.adjustColumns();
			 
			
		}jTable0.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				int selectRow = jTable0.getSelectedRow();
				Posting posting = (Posting) data.get(selectRow);
				idTextField.setText(((Integer) posting.getId()).toString());
				eventTypeTextField.setText((String) posting.getEventType());
				bookingDateTextField.setText((String) posting.getBookingDate());
				tradeTextField.setText(((Integer)posting.getTradeID()).toString());
				accEventtypeTextField.setText((String) posting.getAccEventType());
				creationDateTextField.setText((String) posting.getCreationDate());
				transferIdText.setText(((Integer)posting.getTransferId()).toString());
				typeTextField.setText((String) posting.getType());
				effectiveDateTextField.setText((String) posting.getEffectiveDate());
				linkIdTextField.setText(((Integer) posting.getLinkId()).toString());
				ruleNameTextField.setText((String) posting.getRuleName());
				statusTextField.setText((String) posting.getStatus());
				debitAccTextField.setText(((String) getAccName(posting.getDebitAccId())).toString());
				debitAmtTextField.setText(((Double)  posting.getDebitAmount()).toString());
				creditAccTextField.setText(((String)getAccName(posting.getCreditAccId())).toString());
				creditAmtTextField.setText(((Double) posting.getCreditAmount()).toString());
				currencyTextField.setText(posting.getCurrency());
				
				
			
				
			}
        
        	
        });
		
		return jTable0;
	}@Override
	public void fillJTabel(Vector data) {
		// TODO Auto-generated method stub
		this.data = data;
		model = new TableModelUtil(data,cols,remoteAccount);
		jTable0.setModel(model);
		
	}
	public RemoteAccount getRemoteAccount() {
		return remoteAccount;
	}

	public void setRemoteAccount(RemoteAccount remoteAccount) {
		this.remoteAccount = remoteAccount;
	}

	public RemoteReferenceData getRemoteRef() {
		return remoteRef;
	}

	public void setRemoteRef(RemoteReferenceData remoteRef) {
		this.remoteRef = remoteRef;
	}
		
	private String getAccName(int accountID) {
    	String accName = null;
    	try {
    		 synchronized(accontName) {
    			 if(accontName.containsKey(accountID)) {
    				 accName = (String) accontName.get(accountID);
    			 } else {
    				 Account acc = (Account) remoteAccount.getAccount(accountID);
    			 	accName = acc.getAccountName();
    			 	accontName.put(acc.getId(), acc.getAccountName());
    			 }
			
    		 }
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return accName;
    }
    
		
		
	
	 
	 class TableModelUtil extends AbstractTableModel {   
		    
		 final String[] columnNames;  
		  
		 Vector<Posting> data;   
		 RemoteAccount remoteRef ;
		     
		 public TableModelUtil( Vector<Posting> myData,String col [],RemoteAccount remoteAcc ) {   
		 	this.columnNames = col;
		this.data = myData;   
		this.remoteRef = remoteAcc;
		}   
		 
	
        public void setModel(Vector<Posting> data) {
        	this.data = data;
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
		 
		     Posting posting = (Posting) data.get(row);
		   		    
			 switch (col) {
		     case 0:
		         value = posting.getId();
		         break;
		     case 1:
		         value =posting.getTradeID();
		         break;
		     case 2:
		         value =  posting.getTransferId();
		         break;
		     case 3:
		         value =posting.getEventType();
		         break;
		     case 4:
		         value = posting.getAccEventType();
		         break;
		     case 5:
		         value =posting.getType();
		         break;
		     case 6:
		         value =posting.getLinkId();
		         break;
		     case 7:
		         value = posting.getCurrency();
		         break;
		     case 8:
		         value =posting.getEffectiveDate();
		         break;
		     case 9:
		         value =posting.getBookingDate();
		         break;
		     case 10:
		         value =posting.getCreationDate();
		         break;
		     case 11:
		         value =posting.getStatus();
		         break;
		     case 12:
		         value =posting.getRuleName();
		         break;
		     case 13:
		         value =getAccName(posting.getDebitAccId());
		         break;  
		     case 14:
		         value =getAccName(posting.getCreditAccId());
		         break;
		     case 15:
		         value =posting.getDebitAmount();
		         break;
		     case 16:
		         value =posting.getCreditAmount();
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
		         if(value instanceof Posting) {
		     data.set(row,(Posting) value) ;
		     this.fireTableDataChanged();   
		         System.out.println("New value of data:");   
		         }
		        
		 }   
		    
		 public void addRow(Object value) {   
		    
			 data.add((Posting) value) ;
		 this.fireTableDataChanged();   
		   
		 }   
		    
		 public void delRow(int row) {   
		    if(row != -1) {
		 data.remove(row);          
		 this.fireTableDataChanged();   
		    }
		    
		 }   
		 
		 public void udpateValueAt(Object value, int row, int col) {   
		     
		  
		     data.set(row,(Posting) value) ;
		     for(int i=0;i<columnNames.length;i++)
		    	 	fireTableCellUpdated(row, i);   
		    
		}   
		    
		    private String getAccName(int accountID) {
		    	String accName = null;
		    	try {
		    		 synchronized(accontName) {
		    			 if(accontName.containsKey(accountID)) {
		    				 accName = (String) accontName.get(accountID);
		    			 } else {
		    				 if(accountID == 0) {
		    					
				    			 	accontName.put(0, "NONE");
		    				 } else {
		    				 Account acc = (Account) remoteAccount.getAccount(accountID);
		    			 	accName = acc.getAccountName();
		    			 	accontName.put(acc.getId(), acc.getAccountName());
		    				 } 
		    			 }
					
		    		 }
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	return accName;
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

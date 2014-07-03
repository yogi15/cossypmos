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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

import org.dyno.visual.swing.layouts.Bilateral;
import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;
import org.dyno.visual.swing.layouts.Trailing;

import apps.window.tradewindow.BackOfficePanel;
import beans.Account;
import beans.DocumentInfo;
import beans.Message;
import beans.Posting;
import beans.Trade;
import beans.Transfer;
import beans.Users;
import beans.WFConfig;
import bo.message.bomessagehandler.MessageFormat;
import bo.message.bomessagehandler.MessageFormatterUtil;
import bo.swift.SwiftFieldMessage;
import bo.swift.SwiftMessage;
import dsServices.RemoteAccount;
import dsServices.RemoteBOProcess;
import dsServices.RemoteReferenceData;
import dsServices.RemoteTrade;

//VS4E -- DO NOT REMOVE THIS LINE!
public class MessagePanel  extends BackOfficePanel {

	private static final long serialVersionUID = 1L;
	JPopupMenu popupMenu = new JPopupMenu();
	private JTable jTable0;
	private JScrollPane jScrollPane0;
	private JLabel jLabel0;
	private JTextField jTextField0;
	private JTextField jTextField1;
	private JTextField jTextField2;
	private JLabel jLabel3;
	private JTextField jTextField3;
	private JLabel jLabel1;
	private JLabel jLabel2;
	private JPanel jPanel0;
	private JLabel jLabel4;
	private JLabel jLabel5;
	private JLabel jLabel6;
	private JLabel jLabel7;
	private JTextField jTextField4;
	private JTextField jTextField7;
	private JTextField jTextField6;
	private JTextField jTextField5;
	private JLabel jLabel8;
	private JLabel jLabel9;
	private JLabel jLabel10;
	private JLabel jLabel11;
	private JTextField jTextField8;
	private JTextField jTextField9;
	private JTextField jTextField10;
	private JTextField jTextField11;
	private JLabel jLabel12;
	private JTextField jTextField12;
	private JLabel jLabel13;
	private JTextField jTextField13;
	private JLabel jLabel14;
	private JTextField jTextField14;
	private JLabel jLabel15;
	private JTextField jTextField15;
	private RemoteBOProcess remoteBO;
	 public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}







	Users users = null;
	Vector<Message> data = new Vector<Message>();
	 java.util.Hashtable<Integer,String> accontName = new Hashtable();
	 TableModelUtil model = null;
	  String cols [] = {  "Message ID", "tradeID","transferId","EventType","TradeDate","MessageType","linkId","ProductType","ProductSubType","MessageDate","Sender","Receiver","Status","Action","Template","Format","GateWay","AddressType"};
	private RemoteReferenceData remoteRef;
	private JPanel jPanel1;
	private JButton jButton0;
	JMenu actions = new JMenu("Action");
	JMenu other = new JMenu("Show");
	public MessagePanel() {
		initComponents();
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
		add(getJPanel0(), new Constraints(new Bilateral(8, 9, 0), new Trailing(8, 309, 329, 329)));
		add(getJPanel1(),  new Constraints(new Bilateral(8, 8, 1129), new Bilateral(7, 323, 10, 282)));
		setSize(1193, 513);
		 popupMenu.add(actions);
		 JMenuItem showMessage = new JMenuItem("Message");
		 other.add(showMessage);
		 popupMenu.add( other);
		 showMessage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(jTable0.getSelectedRow() == -1)
				return;
				
				Message message = data.get(jTable0.getSelectedRow());
				MessageFormat format = MessageFormatterUtil.getFormatter(message,trade);
				Trade trade = getTrade();
				Transfer transfer = null;
				try {
					transfer = (Transfer) remoteBO.getTransfer(message.getTransferId());
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				DocumentInfo docInfo = format.generate(message, trade, transfer, true,getRemoteTrade(),remoteRef);
			
				if(docInfo == null) 
					return;
				if(!message.getFormat().equalsIgnoreCase("SWIFT")) {
					format.display(message, docInfo, remoteTrade, remoteRef);
				} else {
						SwiftMessage swiftM = docInfo.getSwiftMessage();
						SwiftMessagePanel swiftPanel = new SwiftMessagePanel();
						
						Vector<SwiftFieldMessage> fields = swiftM.getFields();
						swiftPanel.setSwiftData(fields);
						swiftPanel.jTextField0.setText(swiftM.get_type());
						swiftPanel.jTextField1.setText(swiftM.get_receiver());
						swiftPanel.jTextField2.setText(swiftM.get_sender());
						 StringBuffer buff = new StringBuffer(swiftM.getSwiftText());
						 swiftM.stripExtraInfo(buff);
						swiftPanel.jTextArea0.setText(buff.toString() );
						JFrame frame = new JFrame();
						 
						frame.setTitle("SWIFTMessage");
						frame.add(swiftPanel);
						frame.setVisible(true);
						frame.setResizable(false);
						frame.setSize(760, 550);
				}
				
				
				
			}
			
		 });
		 
	}

	
	private JButton getJButton0() {
		if (jButton0 == null) {
			jButton0 = new JButton();
			jButton0.setText("Refresh");
		}jButton0.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				trade = getTrade();
				if(trade == null)
					 return;
				try {
			
				String where = " tradeid = " + getTrade().getId() + " and linkTo = 0 order by id  ";
				data = (Vector)	remoteBO.getMessageOnTrade(getTrade().getId());
				model = null;
				model = new TableModelUtil(data,cols,remoteBO);
				jTable0.setModel(model);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		
		});
		
		return jButton0;
	}

	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setBorder(new LineBorder(Color.black, 1, false));
			jPanel1.setLayout(new GroupLayout());
			jPanel1.add(getJScrollPane0(), new Constraints(new Bilateral(6, 9, 1150), new Bilateral(10, 9, 10, 259)));
		}
		return jPanel1;
	}

	private JTextField getJTextField15() {
		if (jTextField15 == null) {
			jTextField15 = new JTextField();
			//jTextField15.setText("jTextField15");
		}
		return jTextField15;
	}

	private JLabel getJLabel15() {
		if (jLabel15 == null) {
			jLabel15 = new JLabel();
			jLabel15.setText("CreditAmt");
		}
		return jLabel15;
	}

	private JTextField getJTextField14() {
		if (jTextField14 == null) {
			jTextField14 = new JTextField();
		//	jTextField14.setText("jTextField14");
		}
		return jTextField14;
	}

	private JLabel getJLabel14() {
		if (jLabel14 == null) {
			jLabel14 = new JLabel();
			jLabel14.setText("CreditA/C");
		}
		return jLabel14;
	}

	private JTextField getJTextField13() {
		if (jTextField13 == null) {
			jTextField13 = new JTextField();
			//jTextField13.setText("jTextField13");
		}
		return jTextField13;
	}

	private JLabel getJLabel13() {
		if (jLabel13 == null) {
			jLabel13 = new JLabel();
			jLabel13.setText("DebitAmt");
		}
		return jLabel13;
	}

	private JTextField getJTextField12() {
		if (jTextField12 == null) {
			jTextField12 = new JTextField();
		//	jTextField12.setText("jTextField12");
		}
		return jTextField12;
	}

	private JLabel getJLabel12() {
		if (jLabel12 == null) {
			jLabel12 = new JLabel();
			jLabel12.setText("DebitA/C");
		}
		return jLabel12;
	}

	private JTextField getJTextField11() {
		if (jTextField11 == null) {
			jTextField11 = new JTextField();
		//	jTextField11.setText("jTextField11");
		}
		return jTextField11;
	}

	private JTextField getJTextField10() {
		if (jTextField10 == null) {
			jTextField10 = new JTextField();
		//	jTextField10.setText("jTextField10");
		}
		return jTextField10;
	}

	private JTextField getJTextField9() {
		if (jTextField9 == null) {
			jTextField9 = new JTextField();
	//		jTextField9.setText("jTextField9");
		}
		return jTextField9;
	}

	private JTextField getJTextField8() {
		if (jTextField8 == null) {
			jTextField8 = new JTextField();
		//	jTextField8.setText("jTextField8");
		}
		return jTextField8;
	}

	private JLabel getJLabel11() {
		if (jLabel11 == null) {
			jLabel11 = new JLabel();
			jLabel11.setText("Status");
		}
		return jLabel11;
	}

	private JLabel getJLabel10() {
		if (jLabel10 == null) {
			jLabel10 = new JLabel();
			jLabel10.setText("EffectiveDate");
		}
		return jLabel10;
	}

	private JLabel getJLabel9() {
		if (jLabel9 == null) {
			jLabel9 = new JLabel();
			jLabel9.setText("CreationDate");
		}
		return jLabel9;
	}

	private JLabel getJLabel8() {
		if (jLabel8 == null) {
			jLabel8 = new JLabel();
			jLabel8.setText("BookingDate");
		}
		return jLabel8;
	}

	private JTextField getJTextField5() {
		if (jTextField5 == null) {
			jTextField5 = new JTextField();
			//jTextField5.setText("jTextField5");
		}
		return jTextField5;
	}

	private JTextField getJTextField6() {
		if (jTextField6 == null) {
			jTextField6 = new JTextField();
		//	jTextField6.setText("jTextField6");
		}
		return jTextField6;
	}

	private JTextField getJTextField7() {
		if (jTextField7 == null) {
			jTextField7 = new JTextField();
		//	jTextField7.setText("jTextField7");
		}
		return jTextField7;
	}

	private JTextField getJTextField4() {
		if (jTextField4 == null) {
			jTextField4 = new JTextField();
		//	jTextField4.setText("jTextField4");
		}
		return jTextField4;
	}

	private JLabel getJLabel7() {
		if (jLabel7 == null) {
			jLabel7 = new JLabel();
			jLabel7.setText("RuleName");
		}
		return jLabel7;
	}

	private JLabel getJLabel6() {
		if (jLabel6 == null) {
			jLabel6 = new JLabel();
			jLabel6.setText("Type");
		}
		return jLabel6;
	}

	private JLabel getJLabel5() {
		if (jLabel5 == null) {
			jLabel5 = new JLabel();
			jLabel5.setText("AccEventType");
		}
		return jLabel5;
	}

	private JLabel getJLabel4() {
		if (jLabel4 == null) {
			jLabel4 = new JLabel();
			jLabel4.setText("EventType");
		}
		return jLabel4;
	}

	private JPanel getJPanel0() {
		if (jPanel0 == null) {
			jPanel0 = new JPanel();
			jPanel0.setLayout(new GroupLayout());
			jPanel0.add(getJLabel12(), new Constraints(new Leading(5, 66, 10, 10), new Leading(122, 10, 10)));
			jPanel0.add(getJLabel14(), new Constraints(new Leading(5, 66, 10, 10), new Leading(146, 10, 10)));
			jPanel0.add(getJLabel0(), new Constraints(new Leading(9, 12, 12), new Leading(8, 10, 10)));
			jPanel0.add(getJTextField0(), new Constraints(new Leading(93, 71, 12, 12), new Leading(33, 10, 10)));
			jPanel0.add(getJTextField1(), new Constraints(new Leading(93, 71, 12, 12), new Leading(5, 12, 12)));
			jPanel0.add(getJTextField2(), new Constraints(new Leading(91, 71, 10, 10), new Leading(59, 10, 10)));
			jPanel0.add(getJTextField3(), new Constraints(new Leading(91, 71, 10, 10), new Leading(84, 10, 10)));
			jPanel0.add(getJLabel1(), new Constraints(new Leading(9, 49, 12, 12), new Leading(34, 12, 12)));
			jPanel0.add(getJLabel4(), new Constraints(new Leading(180, 93, 10, 10), new Leading(6, 12, 12)));
			jPanel0.add(getJLabel5(), new Constraints(new Leading(178, 93, 10, 10), new Leading(29, 10, 10)));
			jPanel0.add(getJLabel6(), new Constraints(new Leading(174, 93, 10, 10), new Leading(60, 12, 12)));
			jPanel0.add(getJLabel7(), new Constraints(new Leading(174, 81, 10, 10), new Leading(85, 12, 12)));
			jPanel0.add(getJTextField4(), new Constraints(new Leading(275, 137, 10, 10), new Leading(5, 12, 12)));
			jPanel0.add(getJTextField7(), new Constraints(new Leading(275, 137, 10, 10), new Leading(60, 12, 12)));
			jPanel0.add(getJLabel2(), new Constraints(new Leading(5, 66, 10, 10), new Leading(64, 12, 12)));
			jPanel0.add(getJLabel3(), new Constraints(new Leading(9, 66, 12, 12), new Leading(88, 12, 12)));
			jPanel0.add(getJLabel8(), new Constraints(new Leading(609, 75, 10, 10), new Leading(6, 12, 12)));
			jPanel0.add(getJLabel11(), new Constraints(new Leading(413, 54, 10, 10), new Leading(8, 14, 12, 12)));
			jPanel0.add(getJTextField8(), new Constraints(new Leading(696, 137, 10, 10), new Leading(8, 12, 12)));
			jPanel0.add(getJLabel9(), new Constraints(new Leading(607, 77, 10, 10), new Leading(34, 12, 12)));
			jPanel0.add(getJTextField9(), new Constraints(new Leading(696, 137, 10, 10), new Leading(30, 12, 12)));
			jPanel0.add(getJTextField10(), new Constraints(new Leading(696, 137, 10, 10), new Leading(56, 12, 12)));
			jPanel0.add(getJLabel10(), new Constraints(new Leading(608, 83, 12, 12), new Leading(58, 14, 12, 12)));
			jPanel0.add(getJTextField5(), new Constraints(new Leading(275, 176, 12, 12), new Leading(32, 12, 12)));
			jPanel0.add(getJTextField6(), new Constraints(new Leading(273, 176, 12, 12), new Leading(88, 12, 12)));
			jPanel0.add(getJLabel13(), new Constraints(new Leading(278, 66, 10, 10), new Leading(120, 12, 12)));
			jPanel0.add(getJTextField13(), new Constraints(new Leading(352, 137, 10, 10), new Leading(120, 12, 12)));
			jPanel0.add(getJTextField12(), new Constraints(new Leading(89, 166, 12, 12), new Leading(119, 12, 12)));
			jPanel0.add(getJTextField14(), new Constraints(new Leading(91, 166, 12, 12), new Leading(146, 12, 12)));
			jPanel0.add(getJTextField11(), new Constraints(new Leading(460, 137, 12, 12), new Leading(8, 12, 12)));
			jPanel0.add(getJLabel15(), new Constraints(new Leading(275, 66, 12, 12), new Leading(148, 12, 12)));
			jPanel0.add(getJTextField15(), new Constraints(new Leading(349, 137, 10, 10), new Leading(144, 12, 12)));
			jPanel0.add(getJButton0(), new Constraints(new Leading(918, 10, 10), new Leading(8, 12, 12)));
		}
		return jPanel0;
	}

	private JLabel getJLabel2() {
		if (jLabel2 == null) {
			jLabel2 = new JLabel();
			jLabel2.setText("Transfer ID");
		}
		return jLabel2;
	}

	private JLabel getJLabel1() {
		if (jLabel1 == null) {
			jLabel1 = new JLabel();
			jLabel1.setText("Trade Id");
		}
		return jLabel1;
	}

	private JTextField getJTextField3() {
		if (jTextField3 == null) {
			jTextField3 = new JTextField();
		//	jTextField3.setText("jTextField3");
		}
		return jTextField3;
	}

	private JLabel getJLabel3() {
		if (jLabel3 == null) {
			jLabel3 = new JLabel();
			jLabel3.setText("Link ID");
		}
		return jLabel3;
	}

	private JTextField getJTextField2() {
		if (jTextField2 == null) {
			jTextField2 = new JTextField();
		//	jTextField2.setText("jTextField2");
		}
		return jTextField2;
	}

	private JTextField getJTextField1() {
		if (jTextField1 == null) {
			jTextField1 = new JTextField();
			//jTextField1.setText("jTextField1");
		}
		return jTextField1;
	}

	private JTextField getJTextField0() {
		if (jTextField0 == null) {
			jTextField0 = new JTextField();
		//	jTextField0.setText("jTextField0");
		}
		return jTextField0;
	}

	private JLabel getJLabel0() {
		if (jLabel0 == null) {
			jLabel0 = new JLabel();
			jLabel0.setText("ID");
		}
		return jLabel0;
	}

	private JScrollPane getJScrollPane0() {
		if (jScrollPane0 == null) {
			
			int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS; 
			int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS;
      		
      		jScrollPane0 = new JScrollPane(getJTable0(),v,h);
		}
		return jScrollPane0;
	}

	private JTable getJTable0() {
		if (jTable0 == null) {
			
			
			 
			  model = new TableModelUtil(data,cols,null);
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
			jTable0.getTableHeader().setReorderingAllowed(false);
			jTable0.getTableHeader().setResizingAllowed(true);
		//	for(int i=0;i<cols.length;i++) 
			 jTable0.getColumnModel().getColumn(0).setPreferredWidth(240); 
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
			   jTable0.getColumnModel().getColumn(15).setPreferredWidth(2); 
			   
			 
			
		}jTable0.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				int selectRow = jTable0.getSelectedRow();
				Message message = (Message) data.get(selectRow);
				/*jTextField1.setText(((Integer) posting.getId()).toString());
				jTextField4.setText((String) posting.getEventType());
				jTextField8.setText((String) posting.getBookingDate());
				jTextField0.setText(((Integer)posting.getTradeID()).toString());
				jTextField5.setText((String) posting.getAccEventType());
				jTextField9.setText((String) posting.getCreationDate());
				jTextField2.setText(((Integer)posting.getTransferId()).toString());
				jTextField7.setText((String) posting.getType());
				jTextField10.setText((String) posting.getEffectiveDate());
				jTextField3.setText(((Integer) posting.getLinkId()).toString());
				jTextField6.setText((String) posting.getRuleName());
				jTextField11.setText((String) posting.getStatus());
				jTextField12.setText(((String) getAccName(posting.getDebitAccId())).toString());
				jTextField13.setText(((Double)  posting.getDebitAmount()).toString());
				jTextField14.setText(((String)getAccName(posting.getCreditAccId())).toString());
				jTextField15.setText(((Double) posting.getCreditAmount()).toString()); */
				if(SwingUtilities.isRightMouseButton(e) == true) 	 {				
					int row = jTable0.rowAtPoint(e.getPoint()); 
					int selectrow [] = jTable0.getSelectedRows();
					
					try {
							fillActioninPopupMenu((Vector) remoteBO.getOnlyAction(message),message,users.getId(),row);
							popupMenu.show(e.getComponent(), e.getX(), e.getY());
					} catch (RemoteException e1) {
				// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		
			
			}
    
				
			}
			
        });
		
		return jTable0;
	}
	
	
	/*protected void updateTransferOnAction(Message message, int userID,int row) {
		// TODO Auto-generated method stub
		try {
			
		//	remoteBO.updateTransferAndPublish(message,userID);
			model.udpateValueAt(message, row, jTable0.getSelectedColumn());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	} */

	protected void fillActioninPopupMenu(Vector onlyAction, final Message message,final int userID,final int row) {
		// TODO Auto-generated method stub
		
			actions.removeAll();
			if(onlyAction == null || onlyAction.isEmpty())
				return;
			JMenuItem actionItem [] = new JMenuItem[onlyAction.size()];
			for(int a=0;a<onlyAction.size();a++) {
				WFConfig wf = (WFConfig)onlyAction.get(a);
				actionItem[a] = new JMenuItem(wf.getAction());
				actions.add(actionItem[a]);
				actionItem[a].addActionListener(new ActionListener() {
				        public void actionPerformed(ActionEvent arg0) {
				        	 String action = arg0.getActionCommand().toString();
				        	 message.setAction(action);
				      //	updateTransferOnAction(message, userID, row);
				        }
			});			// TODO Auto-generated method stub
			
			} 
    
    	
    }
	
	@Override
	public void fillJTabel(Vector data) {
		// TODO Auto-generated method stub
		this.data = data;
		model = new TableModelUtil(data,cols,null);
		jTable0.setModel(model);
		
	}
	public RemoteBOProcess getRemoteBOProcess() {
		return remoteBO;
	}

	public void RemoteBOProcess(RemoteBOProcess remoteBO) {
		this.remoteBO = remoteBO;
	}

	public RemoteReferenceData getRemoteRef() {
		return remoteRef;
	}

	public void setRemoteRef(RemoteReferenceData remoteRef) {
		this.remoteRef = remoteRef;
	}
		
	
    
		
		
	
	 
	 class TableModelUtil extends AbstractTableModel {   
		    
		 final String[] columnNames;  
		  
		 Vector<Message> data;   
		 RemoteBOProcess remoteRef ;
		     
		 public TableModelUtil( Vector<Message> myData,String col [],RemoteBOProcess remoteAcc ) {   
		 	this.columnNames = col;
		this.data = myData;   
		this.remoteRef = remoteAcc;
		}   
		 
	
        public void setModel(Vector<Message> data) {
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
		    // "Message ID", "tradeID","transferId","EventType","TradeDate","MessageType","linkId","ProductType","ProductSubType","MessageDate","Sender","Receiver","Status","Action","Template","Format","GateWay"
		     Message message = (Message) data.get(row);
		   		    
			 switch (col) {
		     case 0:
		         value = message.getId();
		         break;
		     case 1:
		         value =message.getTradeId();
		         break;
		     case 2:
		         value =  message.getTransferId();
		         break;
		     case 3:
		         value =message.getEventType();
		         break;
		     case 4:
		         value = message.getTradeDate();
		         break;
		     case 5:
		         value =message.getMessageType();
		         break;
		     case 6:
		         value =message.getLinkId();
		         break;
		     case 7:
		         value = message.getProductType();
		         break;
		     case 8:
		         value =message.getProductSubType();
		         break;
		     case 9:
		         value =message.getMessageDate();
		         break;
		     case 10:
		         value =message.getSenderName();
		         break;
		     case 11:
		         value =message.getReceiverName();
		         break;
		     case 12:
		         value =message.getStatus();
		         break;
		     case 13:
		         value =message.getTemplateName();
		         break;
		     case 14:
		         value =message.getFormat();
		         break;  
		     case 15:
		         value =message.getMessageGateway();
		         break;
		     case 16:
		         value =message.getAddressType();
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
		     data.set(row,(Message) value) ;
		     this.fireTableDataChanged();   
		         System.out.println("New value of data:");   
		         }
		        
		 }   
		    
		 public void addRow(Object value) {   
		    
			 data.add((Message) value) ;
		 this.fireTableDataChanged();   
		   
		 }   
		    
		 public void delRow(int row) {   
		    if(row != -1) {
		 data.remove(row);          
		 this.fireTableDataChanged();   
		    }
		    
		 }   
		 
		 public void udpateValueAt(Object value, int row, int col) {   
		     
		  
		     data.set(row,(Message) value) ;
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







	public void setRemoteBO(RemoteBOProcess boremote) {
		this.remoteBO = boremote;
		// TODO Auto-generated method stub
		
	}
RemoteTrade remoteTrade = null;
	public void setRemoteTrade(RemoteTrade remoteTrade) {
		// TODO Auto-generated method stub
		this.remoteTrade = remoteTrade;
		
	}
	public RemoteTrade getRemoteTrade() {
		// TODO Auto-generated method stub
		return remoteTrade;
		
	}
}

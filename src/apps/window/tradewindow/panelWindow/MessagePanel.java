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

import swingUtils.TableColumnAdjuster;
import apps.window.tradewindow.BackOfficePanel;
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
	private JTextField tradeIdField;
	private JTextField id;
	private JTextField transferId;
	private JLabel jLabel3;
	private JTextField linkId;
	private JLabel jLabel1;
	private JLabel jLabel2;
	private JPanel jPanel0;
	private JLabel jLabel4;
	private JLabel jLabel5;
	private JLabel jLabel6;
	private JLabel jLabel7;
	private JTextField eventtTypeField;
	private JTextField productSubTypeField;
	private JTextField messageDateField;
	private JTextField actionField;
	private JLabel jLabel8;
	private JLabel jLabel9;
	private JLabel jLabel10;
	private JLabel jLabel11;
	private JTextField templateField;
	private JTextField formatField;
	private JTextField gatewayField;
	private JTextField statusField;
	private JLabel jLabel12;
	private JTextField tradeDateField;
	private JLabel jLabel13;
	private JTextField senderField;
	private JLabel jLabel14;
	private JTextField productTypeField;
	private JLabel jLabel15;
	private JTextField receiverField;
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

	private JTextField getAddressField() {
		if (addressField == null) {
			addressField = new JTextField();
			addressField.setText("addressField");
		}
		return addressField;
	}

	private JLabel getJLabel16() {
		if (jLabel16 == null) {
			jLabel16 = new JLabel();
			jLabel16.setText("Address");
		}
		return jLabel16;
	}

	private void getPopupMenu() {
		
	}
	
	private void getActions() {
		
	}
	
	private void getOther() {
		
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

	private JTextField getReceiverField() {
		if (receiverField == null) {
			receiverField = new JTextField();
			//receiverField.setText("receiverField");
		}
		return receiverField;
	}

	private JLabel getJLabel15() {
		if (jLabel15 == null) {
			jLabel15 = new JLabel();
			jLabel15.setText("Receiver");
		}
		return jLabel15;
	}

	private JTextField getProductTypeField() {
		if (productTypeField == null) {
			productTypeField = new JTextField();
		//	productTypeField.setText("productTypeField");
		}
		return productTypeField;
	}

	private JLabel getJLabel14() {
		if (jLabel14 == null) {
			jLabel14 = new JLabel();
			jLabel14.setText("ProductType");
		}
		return jLabel14;
	}

	private JTextField getSenderField() {
		if (senderField == null) {
			senderField = new JTextField();
			//senderField.setText("senderField");
		}
		return senderField;
	}

	private JLabel getJLabel13() {
		if (jLabel13 == null) {
			jLabel13 = new JLabel();
			jLabel13.setText("Sender");
		}
		return jLabel13;
	}

	private JTextField getTradeDateField() {
		if (tradeDateField == null) {
			tradeDateField = new JTextField();
		//	tradeDateField.setText("tradeDateField");
		}
		return tradeDateField;
	}

	private JLabel getJLabel12() {
		if (jLabel12 == null) {
			jLabel12 = new JLabel();
			jLabel12.setText("TradeDate");
		}
		return jLabel12;
	}

	private JTextField getStatusField() {
		if (statusField == null) {
			statusField = new JTextField();
		//	statusField.setText("statusField");
		}
		return statusField;
	}

	private JTextField getGatewayField() {
		if (gatewayField == null) {
			gatewayField = new JTextField();
		//	gatewayField.setText("gatewayField");
		}
		return gatewayField;
	}

	private JTextField getFormatField() {
		if (formatField == null) {
			formatField = new JTextField();
	//		formatField.setText("formatField");
		}
		return formatField;
	}

	private JTextField getTemplateField() {
		if (templateField == null) {
			templateField = new JTextField();
		//	templateField.setText("templateField");
		}
		return templateField;
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
			jLabel10.setText("Gateway");
		}
		return jLabel10;
	}

	private JLabel getJLabel9() {
		if (jLabel9 == null) {
			jLabel9 = new JLabel();
			jLabel9.setText("Format");
		}
		return jLabel9;
	}

	private JLabel getJLabel8() {
		if (jLabel8 == null) {
			jLabel8 = new JLabel();
			jLabel8.setText("Template");
		}
		return jLabel8;
	}

	private JTextField getActionField() {
		if (actionField == null) {
			actionField = new JTextField();
			//actionField.setText("actionField");
		}
		return actionField;
	}

	private JTextField getMessageDateField() {
		if (messageDateField == null) {
			messageDateField = new JTextField();
		//	messageDateField.setText("messageDateField");
		}
		return messageDateField;
	}

	private JTextField getProductSubTypeField() {
		if (productSubTypeField == null) {
			productSubTypeField = new JTextField();
		//	productSubTypeField.setText("productSubTypeField");
		}
		return productSubTypeField;
	}

	private JTextField getEventtTypeField() {
		if (eventtTypeField == null) {
			eventtTypeField = new JTextField();
		//	eventtTypeField.setText("eventtTypeField");
		}
		return eventtTypeField;
	}

	private JLabel getJLabel7() {
		if (jLabel7 == null) {
			jLabel7 = new JLabel();
			jLabel7.setText("MessageDate");
		}
		return jLabel7;
	}

	private JLabel getJLabel6() {
		if (jLabel6 == null) {
			jLabel6 = new JLabel();
			jLabel6.setText("ProductSubType");
		}
		return jLabel6;
	}

	private JLabel getJLabel5() {
		if (jLabel5 == null) {
			jLabel5 = new JLabel();
			jLabel5.setText("Action");
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
			jPanel0.add(getJLabel0(), new Constraints(new Leading(9, 12, 12), new Leading(8, 10, 10)));
			jPanel0.add(getTradeIdField(), new Constraints(new Leading(93, 71, 12, 12), new Leading(33, 10, 10)));
			jPanel0.add(getId(), new Constraints(new Leading(93, 71, 12, 12), new Leading(5, 12, 12)));
			jPanel0.add(getJLabel1(), new Constraints(new Leading(9, 49, 12, 12), new Leading(34, 12, 12)));
			jPanel0.add(getTemplateField(), new Constraints(new Leading(696, 137, 10, 10), new Leading(8, 12, 12)));
			jPanel0.add(getJButton0(), new Constraints(new Leading(918, 10, 10), new Leading(8, 12, 12)));
			jPanel0.add(getTransferId(), new Constraints(new Leading(93, 71, 12, 12), new Leading(59, 12, 12)));
			jPanel0.add(getLinkId(), new Constraints(new Leading(95, 71, 10, 10), new Leading(88, 12, 12)));
			jPanel0.add(getJLabel3(), new Constraints(new Leading(9, 66, 12, 12), new Leading(92, 12, 12)));
			jPanel0.add(getEventtTypeField(), new Constraints(new Leading(95, 103, 10, 10), new Leading(119, 12, 12)));
			jPanel0.add(getJLabel4(), new Constraints(new Leading(9, 66, 12, 12), new Leading(121, 12, 12)));
			jPanel0.add(getJLabel2(), new Constraints(new Leading(9, 66, 12, 12), new Leading(64, 12, 12)));
			jPanel0.add(getTradeDateField(), new Constraints(new Leading(377, 105, 10, 10), new Leading(6, 12, 12)));
			jPanel0.add(getJLabel12(), new Constraints(new Leading(250, 78, 10, 10), new Leading(6, 12, 12)));
			jPanel0.add(getReceiverField(), new Constraints(new Leading(377, 137, 12, 12), new Leading(145, 22, 12, 12)));
			jPanel0.add(getJLabel14(), new Constraints(new Leading(250, 80, 12, 12), new Leading(59, 12, 12)));
			jPanel0.add(getJLabel7(), new Constraints(new Leading(250, 81, 12, 12), new Leading(36, 12, 12)));
			jPanel0.add(getJLabel15(), new Constraints(new Leading(250, 66, 12, 12), new Leading(151, 12, 12)));
			jPanel0.add(getActionField(), new Constraints(new Leading(95, 113, 12, 12), new Leading(147, 12, 12)));
			jPanel0.add(getJLabel5(), new Constraints(new Leading(6, 93, 10, 10), new Leading(151, 12, 12)));
			jPanel0.add(getJLabel8(), new Constraints(new Leading(589, 75, 12, 12), new Leading(10, 12, 12)));
			jPanel0.add(getJLabel10(), new Constraints(new Leading(589, 83, 12, 12), new Leading(59, 14, 12, 12)));
			jPanel0.add(getJLabel9(), new Constraints(new Leading(589, 77, 12, 12), new Leading(34, 12, 12)));
			jPanel0.add(getJLabel16(), new Constraints(new Leading(589, 72, 12, 12), new Leading(85, 12, 12)));
			jPanel0.add(getJLabel11(), new Constraints(new Leading(589, 54, 12, 12), new Leading(113, 14, 12, 12)));
			jPanel0.add(getAddressField(), new Constraints(new Leading(696, 131, 12, 12), new Leading(81, 12, 12)));
			jPanel0.add(getStatusField(), new Constraints(new Leading(696, 137, 12, 12), new Leading(107, 12, 12)));
			jPanel0.add(getFormatField(), new Constraints(new Leading(696, 137, 12, 12), new Leading(33, 18, 12, 12)));
			jPanel0.add(getGatewayField(), new Constraints(new Leading(696, 137, 12, 12), new Leading(58, 18, 12, 12)));
			jPanel0.add(getJLabel13(), new Constraints(new Leading(250, 66, 12, 12), new Leading(119, 12, 12)));
			jPanel0.add(getSenderField(), new Constraints(new Leading(377, 137, 12, 12), new Leading(116, 12, 12)));
			jPanel0.add(getJLabel6(), new Constraints(new Leading(250, 10, 10), new Leading(89, 18, 12, 12)));
			jPanel0.add(getProductSubTypeField(), new Constraints(new Leading(377, 137, 12, 12), new Leading(88, 12, 12)));
			jPanel0.add(getProductTypeField(), new Constraints(new Leading(377, 111, 12, 12), new Leading(58, 22, 12, 12)));
			jPanel0.add(getMessageDateField(), new Constraints(new Leading(377, 110, 12, 12), new Leading(32, 12, 12)));
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

	private JTextField getLinkId() {
		if (linkId == null) {
			linkId = new JTextField();
		//	linkId.setText("linkId");
		}
		return linkId;
	}

	private JLabel getJLabel3() {
		if (jLabel3 == null) {
			jLabel3 = new JLabel();
			jLabel3.setText("Link ID");
		}
		return jLabel3;
	}

	private JTextField getTransferId() {
		if (transferId == null) {
			transferId = new JTextField();
		//	transferId.setText("transferId");
		}
		return transferId;
	}

	private JTextField getId() {
		if (id == null) {
			id = new JTextField();
			//id.setText("id");
		}
		return id;
	}

	private JTextField getTradeIdField() {
		if (tradeIdField == null) {
			tradeIdField = new JTextField();
		//	tradeIdField.setText("tradeIdField");
		}
		return tradeIdField;
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
			
			/*int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS; 
			int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS;
      		
      		jScrollPane0 = new JScrollPane(getJTable0(),v,h);*/
			jScrollPane0 = new JScrollPane(getJTable0());
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
			   
			   TableColumnAdjuster tca = new TableColumnAdjuster(jTable0);
			   tca.adjustColumns();
			
		}
		
		jTable0.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				int selectRow = jTable0.getSelectedRow();
				
				Message message = (Message) data.get(selectRow);
				
				id.setText(((Integer) message.getId()).toString());
				tradeIdField.setText(((Integer) message.getTradeId()).toString());
				transferId.setText(((Integer) message.getTransferId()).toString());
				linkId.setText(((Integer) message.getLinkId()).toString());
				eventtTypeField.setText((String) message.getEventType());
				actionField.setText((String) message.getAction());
				tradeDateField.setText((String) message.getTradeDate());
				messageDateField.setText((String) message.getMessageDate());
				productTypeField.setText((String) message.getProductType());
				productSubTypeField.setText((String) message.getProductSubType());
				senderField.setText((String) message.getSenderName());
				receiverField.setText((String) message.getReceiverName());
				templateField.setText((String) message.getTemplateName());
				formatField.setText((String) message.getFormat());
				gatewayField.setText((String) message.getMessageGateway());
				addressField.setText((String) message.getAddressType());
				statusField.setText((String) message.getStatus());
		
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
				      	updateMessageOnAction(message, userID, row);
				        }
			});			// TODO Auto-generated method stub
			
			} 
    
    	
    }
	protected void updateMessageOnAction(Message message, int userID, int row) {
		// TODO Auto-generated method stub
		try {

			remoteBO.updateMessageAndPublish(message, userID);
			model.udpateValueAt(message, row, jTable0.getSelectedColumn());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
private JLabel jLabel16;
private JTextField addressField;
	public void setRemoteTrade(RemoteTrade remoteTrade) {
		// TODO Auto-generated method stub
		this.remoteTrade = remoteTrade;
		
	}
	public RemoteTrade getRemoteTrade() {
		// TODO Auto-generated method stub
		return remoteTrade;
		
	}
}

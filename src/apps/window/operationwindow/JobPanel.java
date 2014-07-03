package apps.window.operationwindow;

import javax.jms.*;   
import javax.naming.*;   

import org.apache.activemq.ActiveMQConnectionFactory;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import util.commonUTIL;

import beans.StartUPData;
import beans.Task;
import beans.Trade;
import beans.Users;

import apps.window.tradewindow.JFrameTradeWindowApplication;
import apps.window.utilwindow.Login;

import dsServices.RemoteProduct;
import dsServices.RemoteReferenceData;
import dsServices.RemoteTask;
import dsServices.RemoteTrade;
import dsServices.ServerConnectionUtil;

public class JobPanel extends JPanel implements Runnable, ExceptionListener  {
	 private javax.swing.JPanel jPanel1;
	 private javax.swing.JPanel jPanel2;
	 private javax.swing.JPanel jPanel3;
	 
	 public static  ServerConnectionUtil de = null;
		RemoteReferenceData remoteBORef;
		RemoteTask remoteTask;
	 DefaultTableModel tmodel;
	 private javax.swing.JTable jTable;
	 private javax.swing.JScrollPane jScrollPane1;
	 javax.swing.JButton jbuttonPasson;
	 javax.swing.JButton jbuttonShowTrade;
	 javax.swing.JButton jbuttonReresh;
	 String username ="";
	 String groupName = "";
	 private javax.swing.JLabel ldate;
	 private javax.swing.JTextField  tDate;
	 javax.swing.JButton jbuttonGo;
	 Vector userStatusGroup = null;
	 JPopupMenu popupMenu = new JPopupMenu();
	 RemoteProduct remoteproduct;
	 RemoteTrade remoteTrade;
	 Users user = null;
String hostName = "";
	public JobPanel(String name,String groupname) {
		
		username = name;
		groupName = groupname;
		de =ServerConnectionUtil.connect("localhost", 1099,commonUTIL.getServerIP());
	   	 try {
	   		hostName = commonUTIL.getLocalHostName();
	   		 remoteBORef = (RemoteReferenceData) de.getRMIService("ReferenceData");
	   		remoteTask = (RemoteTask) de.getRMIService("Task");
	   		remoteproduct = (RemoteProduct) de.getRMIService("Product");
	   		remoteTrade = (RemoteTrade) de.getRMIService("Trade");
	   		userStatusGroup = (Vector) remoteBORef.getStartUPData(groupName+"Status");
	   		 
	   		
				System.out.println(remoteBORef);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			ldate = new javax.swing.JLabel("Date");
			tDate = new javax.swing.JTextField(10);
			tDate.setText(util.commonUTIL.getCurrentDate(new Date()));
		jPanel1 = new javax.swing.JPanel();
		jPanel3 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jbuttonPasson = new javax.swing.JButton("PassOn");
        jbuttonShowTrade = new javax.swing.JButton("ShowTrade");
        jbuttonGo  = new javax.swing.JButton("LoadTask..");
        jScrollPane1 = new javax.swing.JScrollPane();

        jbuttonReresh  = new javax.swing.JButton("Refresh");
        JMenuItem Trade = new JMenuItem("Trade");
        JMenuItem Action = new JMenuItem("Action");
        
        popupMenu.add(Trade);
        popupMenu.add(Action);
       

		
		jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(username));

        String col[] = {"task Id ", "Trade id ", "Product","TradeDate","Trade Status","Action","Exception"};
        tmodel = new DefaultTableModel (col,0);
        jTable = new javax.swing.JTable();
        jTable.setModel(tmodel);
        insertRowinTable();
        jScrollPane1.setViewportView(jTable);
        
        jbuttonReresh.addActionListener(new ActionListener() {
            

    		@Override
    		public void actionPerformed(ActionEvent e) {
    			int r= tmodel.getRowCount();
    			for(int rows =r;rows > 0;rows--)  {
    				   tmodel.removeRow(rows-1);
    				}
    			jTable.repaint();
    			 insertRowinTable();
    		}
        });
        
        Trade.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent arg0) {
	        	try {
	        	int rowindex = jTable.getSelectedRow(); 
			//	TableModel model = jTable.getModel();
				String ss = (String) jTable.getValueAt(rowindex, 2);
				Integer tradeId = (Integer) jTable.getValueAt(rowindex, 1);
				Trade trade = null;
				try {
					trade = (Trade)  remoteTrade.selectTrade(tradeId.intValue());
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	JFrameTradeWindowApplication tradeWindow = new JFrameTradeWindowApplication(ss,user);
	        	
	        	tradeWindow.setUserName(user);
	        	tradeWindow.setOpenTrade(trade);
	        //	tradeWindow
	           tradeWindow.setVisible(true);
	        	
	            
	        }
	        	catch(Exception e1) {
				 commonUTIL.displayError("JobPanel", " Popaction()", e1);
			 }

	        }
	    });
        jTable.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				try{ 
				int rowindex = jTable.getSelectedRow(); 
				TableModel model = jTable.getModel();
				Integer ss = (Integer) model.getValueAt(rowindex, 0);
				if(SwingUtilities.isRightMouseButton(e) == true)
				{
				int row = jTable.rowAtPoint(e.getPoint());
				popupMenu.show(e.getComponent(), e.getX(), e.getY());
				
				}
			}catch(Exception e1) {
				 commonUTIL.displayError("JobPanel", " mouseClicked()", e1);
			 }
				
			}

			
        
        	
        });
        
        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
                    .addContainerGap())
            );
            jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                    .addContainerGap())
            );
        
            
            javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
            jPanel2.setLayout(jPanel2Layout);
            jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                    .addContainerGap(192, Short.MAX_VALUE)
                    .addComponent(jbuttonPasson)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                     .addComponent(jbuttonReresh)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                   
                    
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jbuttonShowTrade)
                    .addContainerGap())
            );
            jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jbuttonPasson)
                        .addComponent(jbuttonReresh)
                       
                        
                        .addComponent(jbuttonShowTrade)))
            ); 
            
            javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
            jPanel3.setLayout(jPanel3Layout);
            jPanel3Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                    .addContainerGap(192, Short.MAX_VALUE)
                    .addComponent(ldate)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                   
                    
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(tDate)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jbuttonGo)
                    .addContainerGap())
            );
            jPanel3Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(ldate)
                       
                        
                        .addComponent(tDate)
                        .addComponent(jbuttonGo)))
            ); 
            
            
            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
            setLayout(layout);
            layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    		.addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    		  .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addContainerGap())
            );
            layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    
                   .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );
		}
	
	
 public synchronized void insertRowinTable() {
		 try {
	 if(userStatusGroup != null) {
		 Iterator groups = userStatusGroup.iterator(); 
		 while(groups.hasNext()) {
			 StartUPData data =	(StartUPData) groups.next();
			 String sql = " taskDate ='" + tDate.getText() + "' and type ='" + data.getName() + "'";
			 
			 Vector v1;
			try {
				v1 = (Vector) remoteTask.selectTaskWhere(sql);
				if(v1 !=  null) {
					
					 Iterator it = v1.iterator();
			    	 int i =0;
			    	 while(it.hasNext()) {
			    		 
			    		 Task task = (Task) it.next();
			    		 if(task.getStatusDone() == null)
			    		 if(checkAlreadyExists(task))
			    		    tmodel.insertRow(tmodel.getRowCount(), new Object[]{task.getId(),task.getTradeID(),(remoteproduct.selectProduct(task.getProductID())).getProductType(),task.getTaskDate(),task.getStatus(),task.getAction(), " "});
			    	 }
				}
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
	    	repaint();
				 
		 }	 
				 
	 }		
		 }catch(Exception e) {
			 commonUTIL.displayError("JobPanel", "insertRowinTable()", e);
		 }
	 
			 
	 }
 public  synchronized  boolean checkAlreadyExists(Task task) {
	 boolean flag = true;
	 for(int i = 0;i < tmodel.getRowCount();i++) {
		 Integer s = (Integer) tmodel.getValueAt(i, 0);
		 int ss = s.intValue();
		 if(task.getId() == ss) {
			 if((task.getStatusDone() != null) && ( task.getStatusDone().equalsIgnoreCase("Done"))) 
				 tmodel.removeRow(i);
			  flag = false;
		 }
		 
		
	 }
	 return flag;
 }


@Override
public synchronized void run() {
	for( ; ; ) {
		try {
			
			  ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://"+hostName+":61616");

	            // Create a Connection
	            Connection connection = connectionFactory.createConnection();
	            connection.start();

	            connection.setExceptionListener(this);

	            // Create a Session
	            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

	            // Create the destination (Topic or Queue)
	            Destination destination = session.createQueue("TRADE");

	            // Create a MessageConsumer from the Session to the Topic or Queue
	            MessageConsumer consumer = session.createConsumer(destination);
	            Message message = consumer.receive(12000);

	            if (message instanceof TextMessage) {
	                TextMessage textMessage = (TextMessage) message;
	               System.out.println("received  as TEXT ** ");
	                String text = textMessage.getText();
	             //   System.out.println("Received: " + text);
	                if(text.contains("NEWTRADE")) {
	                int r= tmodel.getRowCount();
	    			for(int rows =r;rows > 0;rows--)  {
	    				   tmodel.removeRow(rows-1);
	    				}
	    			jTable.repaint();
	    			insertRowinTable();
	                }
	    			//Thread.sleep(8000);
	            } else {
	                System.out.println("Received: " + message);
	            }

	            consumer.close(); 
	            session.close();
	            connection.close();
	        
			
		} catch (JMSException j) {
			// TODO Auto-generated catch block
			j.printStackTrace();
		} catch(Exception e) {
			 commonUTIL.displayError("JobPanel", "run()", e);
		 }
	}
	
}


public Users getUser() {
	return user;
}


public void setUser(Users user) {
	this.user = user;
}


@Override
public void onException(JMSException arg0) {
	// TODO Auto-generated method stub
	System.out.println(arg0);
	
}
	
 
}

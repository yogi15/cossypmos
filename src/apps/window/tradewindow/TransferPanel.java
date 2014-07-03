package apps.window.tradewindow;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import dsServices.RemoteBOProcess;

import beans.Task;
import beans.Trade;
import beans.Transfer;

public class TransferPanel extends BackOfficePanel {
	private javax.swing.JPanel jPanel2;
	public javax.swing.JPanel getjPanel2() {
		return jPanel2;
	}
	private javax.swing.JPanel jPanel1;
	public javax.swing.JPanel getjPanel1() {
		return jPanel1;
	}

	private javax.swing.JScrollPane jScrollPane1;
	public javax.swing.JScrollPane getjScrollPane1() {
		return jScrollPane1;
	}

	private javax.swing.JTable jTable1;
	public javax.swing.JTable getjTable1() {
		return jTable1;
	}

	private javax.swing.JLabel jLabel;
	public javax.swing.JLabel getjLabel() {
		return jLabel;
	}

	DefaultTableModel model;
	Trade trade = null;
	RemoteBOProcess remoteBO;

	public RemoteBOProcess getRemoteBO() {
		return remoteBO;
	}

		public void setRemoteBO(RemoteBOProcess remoteBO) {
			this.remoteBO = remoteBO;
		}

	public TransferPanel() {
	        initComponents();
	    }
	 
	 private void initComponents() {

	        jPanel2 = new javax.swing.JPanel();
	        jLabel = new javax.swing.JLabel();
	       
	        jPanel1 = new javax.swing.JPanel();
	        jScrollPane1 = new javax.swing.JScrollPane();
	        jTable1 = new javax.swing.JTable();

		// setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		jPanel2.setBorder(javax.swing.BorderFactory
				.createTitledBorder("Transfer"));
		jPanel1.setBorder(javax.swing.BorderFactory
				.createTitledBorder("Transfer"));
		String col[] = { "Transfer ID", "Trade id", "EventType", "TranferType",
				"AMOUNT", "SettlementAmount", "Quantity", "Product", "Status",
				"SettlementDate", "Currency", "ValueDate", "Payer", "Receiver" };
		model = new DefaultTableModel(col, 0);

		jPanel1.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
			}

			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub

				try {
					fillJTabel((Vector) remoteBO.queryWhere("Transfer",
							"tradeId = " + trade.getId()));
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}

		});

           
            
            
	        jTable1.setModel(model);
	        jScrollPane1.setViewportView(jTable1);
	        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
	        jPanel1.setLayout(jPanel1Layout);
	        jPanel1Layout.setHorizontalGroup(
	                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                .addGroup(jPanel1Layout.createSequentialGroup()
	                    .addContainerGap()
	                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
	                    .addContainerGap())
	            );
	            jPanel1Layout.setVerticalGroup(
	                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                .addGroup(jPanel1Layout.createSequentialGroup()
	                    .addContainerGap()
	                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
	                    .addContainerGap())
	            );
	        
	            
	            
	           
	            
	            
	            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
	            setLayout(layout);
	            layout.setHorizontalGroup(
	                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
	                    .addContainerGap()
	                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
	                        .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
	                    //    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
	                    .addContainerGap())
	            );
	            layout.setVerticalGroup(
	                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                .addGroup(layout.createSequentialGroup()
	                    .addContainerGap()
	                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
	                  //  .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
	            );
	        
	 }
	
	@Override
	public void fillJTabel(Vector data ) {
		// TODO Auto-generated method stub
		if(data != null) {
		Iterator<Transfer> it = data.iterator(); 
		int r = model.getRowCount();
		
		System.out.println();
		
		for(int rows =r;rows > 0;rows--)  {
			   model.removeRow(rows-1);
			}
			jTable1.repaint();
			int i = 0;
			while (it.hasNext()) {

				Transfer transfer = (Transfer) it.next();
				model.insertRow(
						i,
						new Object[] { transfer.getId(), transfer.getTradeId(),
								transfer.getEventType(),
								transfer.getTransferType(),
								transfer.getAmount(),
								transfer.getSettleAmount(),
								trade.getQuantity(), transfer.getProductId(),
								transfer.getTransferStatus(),
								transfer.getDeliveryDate(),
								transfer.getSettlecurrency(),
								transfer.getValueDate(),
								transfer.getPayerCode(),
								transfer.getReceiverCode() });
				i++;
			}
			jTable1.repaint();
		}

	}

	public boolean checkAlreadyExists(Transfer task) {
		 boolean flag = true;
		 for(int i = 0;i < model.getRowCount();i++) {
			 Integer s = (Integer) model.getValueAt(i, 0);
			 int ss = s.intValue();
			 if(task.getId() == ss) {
				 
				 model.removeRow(i);
				 flag = false;
			 }
			 
			
		 }
		 return flag;
	 }	
	
	

}

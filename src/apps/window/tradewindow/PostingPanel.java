package apps.window.tradewindow;

import javax.swing.table.DefaultTableModel;

import java.awt.Color;
import java.awt.FlowLayout;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JTable;

import beans.Posting;

public class PostingPanel extends BackOfficePanel {
	private javax.swing.JPanel jPanel2;
	 private javax.swing.JPanel jPanel1;
	    private javax.swing.JScrollPane jScrollPane1;
	    private javax.swing.JTable jTable1;
	    private javax.swing.JLabel jLabel;
	    DefaultTableModel model; 
	 public PostingPanel() {
	        initComponents();
	    }
	 
	 private void initComponents() {

	        jPanel2 = new javax.swing.JPanel();
	        jLabel = new javax.swing.JLabel();
	       
	        jPanel1 = new javax.swing.JPanel();
	        jScrollPane1 = new javax.swing.JScrollPane();
	        jTable1 = new javax.swing.JTable();

	       // setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
	        jLabel.setText("Posting");
	        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Posting"));
	        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Posting"));
            String cols [] = {  "Posting ID", "tradeID","transferId","creditAmount","debitAmount","accEventType","linkId","eventType","debitAcc","creditAcc","ruleName","currency","type","status","BookingDate","CreationDate","EffectiveDate"};
	        model = new DefaultTableModel(cols,0);
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
	public void fillJTabel(Vector v ) {
		// TODO Auto-generated method stub
		//model = new DefaultTableModel(col,2);
		if(v != null && (!v.isEmpty())) {
			int r = model.getRowCount();
			for(int rows =r;rows > 0;rows--)  {
				model.removeRow(rows-1);
			}
			jTable1.repaint();
			Iterator it = v.iterator();
			int i =0;
			while(it.hasNext()) {
				Posting posting = (Posting) it.next();
			//	"Posting ID", "tradeID","transferId","creditAmount","debitAmount","accEventType","linkId","eventType","debitAcc","creditAcc","ruleName","currency","type","status","BookingDate","CreationDate","EffectiveDate"	
				
				model.insertRow(i, new Object[]{ posting.getId(),posting.getTradeID(),posting.getTransferId(),posting.getCreditAmount(),posting.getDebitAmount(),posting.getAccEventType(),posting.getLinkId(),posting.getEventType(),posting.getDebitAccId(),posting.getCreditAccId(),posting.getRuleName(),posting.getType(),posting.getStatus(),posting.getBookingDate(),posting.getCreationDate(),posting.getEffectiveDate()});
						i++;
				}
			
		}
		
	
		
		
		jTable1.setModel(model);
		
	}

	

}

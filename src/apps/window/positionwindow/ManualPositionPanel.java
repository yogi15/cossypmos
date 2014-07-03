package apps.window.positionwindow;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.Vector;

import javax.swing.JPanel;

import dsServices.RemoteTask;

public class ManualPositionPanel extends apps.window.reportwindow.ReportPanel {

	//String sql = " select t.id,t.type,t.t.producttype,t.tradedesc,t.tradedesc1,t.quantity,t.nominal,t.bookid,t.c    "
	DualTablePanel panel = new DualTablePanel();
	JPanel panelR = new JPanel();
	Vector data = new Vector();
	
	/**
	 * @return the data
	 */
	private Vector getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	private void setData(Vector data) {
		this.data = data;
	}

	@Override
	public void populateReportData(String sql, boolean replaceColumns) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setColumnSQL(String sql1) {
		// TODO Auto-generated method stub
		
		sql = sql1;
		
	}

	
	
	@Override
	public JPanel loadreport(Vector data) {
		// TODO Auto-generated method stub
		 panelR.setLayout(new BorderLayout());
		 panelR.add(panel.getDualTablePanel(data),BorderLayout.CENTER);
		 panel.jMenuItem2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					
					int size =	panel.dataOpenTrades.size();
					if(size != 2) 
						return;
					try {
						getRemoteMO().processManualLiquidation(panel.dataOpenTrades);
						panel._dualTable.clearSelection();
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
						
					}
				});
		 return panelR;
		
	}

	@Override
	public JPanel loadreport() {
		// TODO Auto-generated method stub
		
		return panelR;
	}

	
   
	
	

}

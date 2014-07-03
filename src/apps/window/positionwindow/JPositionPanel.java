package apps.window.positionwindow;

import java.awt.BorderLayout;
import java.awt.Color;
import java.rmi.RemoteException;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import org.dyno.visual.swing.layouts.Bilateral;
import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;

import beans.Position;

public class JPositionPanel extends apps.window.reportwindow.ReportPanel {

	
	DataPanel jobdataPanel = null;
	
     Vector data = new Vector();
     JPanel jPanel5 = null;
	
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
	public void setColumnSQL(String sql) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public JPanel loadreport(Vector data) {
		// TODO Auto-generated method stub
		
		
		
		if(jobdataPanel == null) {
			jobdataPanel = new DataPanel(data,getPositionFilterValues());
			jobdataPanel.setUser(getUser());
			//jobdataPanel.setJobdetails(getUserJobsDetails());
			//getJPanel5(jobdataPanel);
		} else {
			jobdataPanel.setDataCreteria(data,getPositionFilterValues());
			return jPanel5;
		}
		return getJPanel5(jobdataPanel);
	}

	@Override
	public JPanel loadreport() {
		// TODO Auto-generated method stub
		return null;
	}
	private void initialStart() {
	
	}
	
	
private JPanel getJPanel5(DataPanel jobdataPanel) {
		
			if (jPanel5 == null) {
				jPanel5 = new JPanel();
				jPanel5.setBorder(new LineBorder(Color.black, 1, false));
				jPanel5.setLayout(new BorderLayout());
			    jPanel5.add(jobdataPanel.getJPanel6(),BorderLayout.CENTER);
			
			} 
				
		
			
		return jPanel5;
	}
}

package apps.window.reportwindow;

import java.awt.BorderLayout;
import java.awt.Color;
import java.rmi.RemoteException;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import beans.UserJob;

import apps.window.reportwindow.jideReport.PivotReport;
import apps.window.reportwindow.jideReport.SwingReportDemo;

public class TransferPanel extends ReportPanel {
	
	
	 String mainsql = " SELECT PRODUCTTYPE FROM TRANSFER transfer";
	 String mainSQLKey = "Transfer transfer"; 
	 String searchCriteria = "";
	 SwingReportDemo  demo = null;
     PivotReport pReport = null;
	 JPanel panelViewReportdata = null;
	 private javax.swing.JPanel jPanel1 = null;
	 ReportSearchPanel reportPanel;
	public JPanel loadreport() {
		jPanel1  = getDemo().run();
		 jPanel1. setLayout(new BorderLayout());
	     DatePanel datep = new DatePanel("Transfer");
	        JPanel pand = new JPanel();
	        pand.setLayout(new BorderLayout());
	        pand.add(getDemo().getControlPanel(datep), BorderLayout.WEST);
	        jPanel1. add(pand, BorderLayout.NORTH );
	         panelViewReportdata =  getDemo().getView().getJidePanel();
	        panelViewReportdata.setBorder(javax.swing.BorderFactory.createTitledBorder(" "));
	        jPanel1. add(panelViewReportdata, BorderLayout.CENTER);
	        populateReportData(mainsql,false);
	        setColumnSQL(mainsql);
	       JPanel jPanel3 = new JPanel();
	       jPanel3.setBorder(new LineBorder(Color.black, 1, false));
	       jPanel3.setLayout(new BorderLayout());
	       jPanel3.add(jPanel1,BorderLayout.CENTER);
	        return jPanel3;
		
	}
	public void populateReportData(String sql,boolean replaceColumns) {
		Vector v1;
		try {
			String sq = "";
			if(replaceColumns) {
				if(mainsql.contains("where"))
				    sq = mainsql + " and " +  searchCriteria;
				else 
					sq = mainsql + " where " +  searchCriteria;
			}	else  {
				sq = mainsql;
			}
			v1 = (Vector) remoteTrade.getTradesforReport(sq);
			if(v1 != null && v1.size() > 0) {
			getpReport().setHeader((Vector) v1.get(0)); 
			getpReport().setdatatype((Vector) v1.get(1));
			getpReport().setData((Vector) v1.get(2));
			getDemo().setReport(getpReport());
			}
			
		        
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    }
	@Override
	public void setColumnSQL(String sql1) {
		// TODO Auto-generated method stub
		sql = sql1;
		
	}
	

}

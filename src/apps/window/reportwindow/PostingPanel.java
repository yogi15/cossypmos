package apps.window.reportwindow;

import java.awt.BorderLayout;
import java.rmi.RemoteException;
import java.util.Vector;

import javax.swing.JPanel;

import beans.UserJob;

import apps.window.reportwindow.jideReport.PivotReport;
import apps.window.reportwindow.jideReport.SwingReportDemo;

public class PostingPanel extends ReportPanel {
	
	
	 String mainsql = "select ID "+
		
	   "  FROM Posting posting";
	 String mainSQLKey = "Posting posting"; 
	 String searchCriteria = "";
	 SwingReportDemo  demo = null;
    PivotReport pReport = null;
	 JPanel panelViewReportdata = null;
	 private javax.swing.JPanel jPanel1 = null;
	 ReportSearchPanel reportPanel;
	public JPanel loadreport() {
		 pReport = new PivotReport(null); 
		
		 demo = new SwingReportDemo(pReport);
		 jPanel1  = demo.run();
		 jPanel1. setLayout(new BorderLayout());
	        DatePanel datep = new DatePanel("Posting");
	        JPanel pand = new JPanel();
	        pand.setLayout(new BorderLayout());
	        pand.add(demo.getControlPanel(datep), BorderLayout.WEST);
	        jPanel1. add(pand, BorderLayout.NORTH );
	         panelViewReportdata =  demo.getView().getJidePanel();
	        panelViewReportdata.setBorder(javax.swing.BorderFactory.createTitledBorder(" "));
	        jPanel1. add(panelViewReportdata, BorderLayout.CENTER);
	      //  populateReportData(mainsql,false);
	  //      UserJob job = jobs.elementAt(0);
	 	//	  Vector detailsJob = job.getDetailsJobs();
	      //  reportPanel = new ReportSearchPanel("Transfer",searchCriteriaA,searchColumn,filterValues,job,detailsJob,remoteTask,remoteTrade,getUser(),getReferenceData());
	       //setRemoteRef(getReferenceData());
	        // reportPanel.addReportPanel(jPanel1);
	         //reportPanel.setColumnSQL(mainsql);
	        // reportPanel.setDatePanel(datep);
	        // reportPanel.setDemo(demo);
	        // reportPanel.setpReport(pReport);
	         //reportPanel.setReportType("posting"); // in small case will used to create where through search criteria.
	   //      setLayout(new BorderLayout());
	        return jPanel1;
		
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
				pReport.setHeader((Vector) v1.get(0)); 
				pReport.setdatatype((Vector) v1.get(1));
				pReport.setData((Vector) v1.get(2));
				demo.setReport(pReport);
				}
				
			        
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
   }
	@Override
	public void setColumnSQL(String sql) {
		// TODO Auto-generated method stub
		reportPanel.setColumnSQL(sql);
		
	}
	

}

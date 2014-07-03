package apps.window.tradewindow.panelWindow;
import java.awt.Color;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import org.dyno.visual.swing.layouts.Bilateral;
import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;

import productPricing.pricingUtil.frequencyUtil;
import util.commonUTIL;
import util.common.DateU;


//VS4E -- DO NOT REMOVE THIS LINE!
public class ScheduleAmort extends JFrame {

	private static final long serialVersionUID = 1L;
	public JTable jTable0;
	private JScrollPane jScrollPane0;
	private JPanel jPanel0;
	public String col [] = {" Date ", " Amount" };
	public DefaultTableModel model = null;
	String frq = "";
    public String getFrq() {
		return frq;
	}

	public void setFrq(String frq) {
		this.frq = frq;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	String startDate = "";
    String endDate = "";
	public ScheduleAmort() {
		initComponents();
	}

	private void initComponents() {
		setLayout(new GroupLayout());
		add(getJPanel0(), new Constraints(new Leading(3, 283, 10, 10), new Leading(4, 464, 10, 10)));
		setSize(296, 475);
	}

	private JPanel getJPanel0() {
		if (jPanel0 == null) {
			jPanel0 = new JPanel();
			jPanel0.setBorder(new LineBorder(Color.black, 1, false));
			jPanel0.setLayout(new GroupLayout());
			jPanel0.add(getJScrollPane0(), new Constraints(new Leading(3, 274, 10, 10), new Bilateral(5, 0, 26, 403)));
		}
		return jPanel0;
	}

	private JScrollPane getJScrollPane0() {
		if (jScrollPane0 == null) {
			jScrollPane0 = new JScrollPane();
			jScrollPane0.setViewportView(getJTable0());
		}
		return jScrollPane0;
	}

	public JTable getJTable0() {
		if (jTable0 == null) {
			jTable0 = new JTable();
			model = new DefaultTableModel(col,0);
			jTable0.setModel(model);
		}
		return jTable0;
	}
	
	private void fillTable2(DateU startD,DateU endD ,int f) {
		
	
		
			
		//	System.out.println(startD.getDate() +  " " + endD.getDate());
			if(startD.getDate().after(endD.getDate())) {
				return;
			} else {
				//System.out.println()
				startD.addDays(f);
				if(startD.getDate().after(endD.getDate())) {
					return;
				}
				model.insertRow(model.getRowCount(), new Object [] {commonUTIL.getDateDefaultFormat(startD.getDate()), ""});
				
				fillTable2(startD,endD,f);
			}
			
		
			
		
	}

	public void fillTable() {
		// TODO Auto-generated method stub
		if(model == null) {
			model = new DefaultTableModel(col,0);
			jTable0.setModel(model);
		}
		if((!getStartDate().trim().isEmpty()) && (!getEndDate().trim().isEmpty()) && (!getFrq().trim().isEmpty())) {
			DateU startD = new DateU(commonUTIL.stringToDate(getStartDate(), true));
		
			int f = frequencyUtil.fromString(getFrq());
			startD.addDays(f);
			 
			 
				DateU endD = new DateU(commonUTIL.stringToDate(getEndDate(), true));
				if(startD.getDate().after(endD.getDate())) {
					return;
			// fillTable2(startD,endD,f);
			 } else {
				 model.insertRow(model.getRowCount(), new Object [] {commonUTIL.getDateDefaultFormat(startD.getDate()), ""});
				 fillTable2(startD,endD,f);
			// commonUTIL.showAlertMessage(startD.getDate().toString() + " " + endD.getDate().toString());
			}
			
		}
		
	}

}

package apps.window.tradewindow.FXPanels;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;

import util.NumericTextField;
import util.commonUTIL;
import util.common.DateU;

import com.standbysoft.component.date.swing.JDatePicker;


//VS4E -- DO NOT REMOVE THIS LINE!
public class Swap extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel jLabel0;
	private JPanel jPanel0;
	private JLabel jLabel1;
	public NumericTextField jTextField1;
	private JLabel jLabel2;
	public NumericTextField jTextField2;
	private JLabel jLabel3;
	//public JDatePicker swapDate;
	public com.jidesoft.combobox.DateComboBox  swapDate;
	public NumericTextField jTextField4;
	DecimalFormat format = new DecimalFormat("##,###,#######.####");
	//protected Date swapDate;
	private static final String PREFERRED_LOOK_AND_FEEL = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
	public Swap() {
		initComponents();
	}

	private void initComponents() {
		setLayout(new GroupLayout());
		add(getJPanel0(), new Constraints(new Leading(5, 455, 10, 10), new Leading(5, 81, 10, 10)));
		setSize(474, 96);
	}

	private JTextField getJTextField4() {
		if (jTextField4 == null) {
			jTextField4 = new NumericTextField(10,format);
			jTextField4.setText("0");
		}
		return jTextField4;
	}

	private com.jidesoft.combobox.DateComboBox getJTextField3() {
		if (swapDate == null) {
			swapDate = new com.jidesoft.combobox.DateComboBox();
			swapDate.setFormat(new SimpleDateFormat("dd/MM/yyyy"));
			Calendar currentDate = Calendar.getInstance();
			swapDate.setDate(currentDate.getTime());
			swapDate.setName("SwapRight");
			swapDate.addKeyListener(new KeyListener() {
					
					@Override
					public void keyTyped(KeyEvent arg0) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void keyReleased(KeyEvent arg0) {
						// TODO Auto-generated method stub
						if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
							swapDate.setDate(commonUTIL.getCurrentDate());
							String dateTxt = getDateText();
							setDateTextEmpty();
							Date date = swapDate.getDate();
							if(date == null)
								date = commonUTIL.getCurrentDate();
							if(!commonUTIL.isEmpty(dateTxt)) {
								swapDate.setDate(checkDate(dateTxt.trim(),date));
							} else {
								swapDate.setDate(commonUTIL.getCurrentDate());
							}
							
						/*	if(dateTxt.equalsIgnoreCase("1m")) {
				   			 Date date = dateSpinner.getDate();
				   			DateU dated  = 	DateU.valueOf(date);
				   			dated.addMonths(1);
				   			 dateSpinner.setDate(dated.getDate()); */
				   			 
				   		// }
							
							
						}
					}
					
					@Override
					public void keyPressed(KeyEvent arg0) {
						// TODO Auto-generated method stub
						if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
							String dateT = getDateText();
							setDateTextEmpty();
						} else {
							setDateText(arg0.getKeyChar());
						}
					//	System.out.println(arg0.getKeyChar());
						
					//	dateSpinner.setName(dateSpinner.getName()+arg0.getKeyChar());
					//	System.out.println(dateSpinner.getName());
						//setDateText(arg0.getKeyChar());
					}
				    	
				    });
		    
		   
			//jTextField3.setText("jTextField2");
		}
		return swapDate;
	}
	 public String getDateText() {
			
		   return dateText;
		  
	   }
	String dateText = "";
	public String setDateText(Character kk) {
		  if(kk.isSpaceChar(kk))
			  return dateText;
		  if(commonUTIL.isEmpty(dateText))
		   dateText = dateText.trim()  + String.valueOf(kk);
		  else 
			  dateText = dateText.trim() + String.valueOf(kk);
		   return dateText;
	  }
	  public void setDateTextEmpty() {
		   dateText = "";
	  }
	private JLabel getJLabel3() {
		if (jLabel3 == null) {
			jLabel3 = new JLabel();
			jLabel3.setText("FAR RATE");
		}
		return jLabel3;
	}

	private JTextField getJTextField2() {
		if (jTextField2 == null) {
			jTextField2 = new  NumericTextField(10,format);
			jTextField2.setText("0.0");
		}
		return jTextField2;
	}

	private JLabel getJLabel2() {
		if (jLabel2 == null) {
			jLabel2 = new JLabel();
			jLabel2.setText("FAR DATE");
		}
		return jLabel2;
	}

	private JTextField getJTextField1() {
		if (jTextField1 == null) {
			jTextField1 = new  NumericTextField(10,format);
			jTextField1.setText("0.0");
		}
		return jTextField1;
	}

	private JLabel getJLabel1() {
		if (jLabel1 == null) {
			jLabel1 = new JLabel();
			jLabel1.setText("FAR AMT2");
		}
		return jLabel1;
	}

	private JPanel getJPanel0() {
		if (jPanel0 == null) {
			jPanel0 = new JPanel();
			jPanel0.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			jPanel0.setLayout(new GroupLayout());
			jPanel0.add(getJLabel0(), new Constraints(new Leading(9, 12, 12), new Leading(16, 12, 12)));
			jPanel0.add(getJTextField1(), new Constraints(new Leading(69, 166, 12, 12), new Leading(2, 28, 12, 12)));
			jPanel0.add(getJLabel2(), new Constraints(new Leading(241, 52, 30, 160), new Leading(53, 12, 12)));
			jPanel0.add(getJTextField3(), new Constraints(new Leading(299, 136, 12, 12), new Leading(39, 28, 12, 12)));
			jPanel0.add(getJTextField4(), new Constraints(new Leading(299, 136, 12, 12), new Leading(3, 28, 12, 12)));
			jPanel0.add(getJLabel3(), new Constraints(new Leading(241, 57, 12, 12), new Leading(16, 12, 12)));
			jPanel0.add(getJLabel1(), new Constraints(new Leading(6, 10, 10), new Leading(53, 12, 12)));
			jPanel0.add(getJTextField2(), new Constraints(new Leading(69, 166, 12, 12), new Leading(39, 28, 12, 12)));
		}
		return jPanel0;
	}

	private JLabel getJLabel0() {
		if (jLabel0 == null) {
			jLabel0 = new JLabel();
			jLabel0.setText("FAR AMT1");
		}
		return jLabel0;
	}
	
	static public Date checkDate(String s1,Date date) {
		  String s = s1.trim();
		  try {
			  int idx = s.indexOf("d");
			  if (idx == -1)
				  idx = s.indexOf("D");
			  if (idx > 0) {
				  int days = commonUTIL.converStringToInteger(s.substring(0, idx));
	             			DateU dated  = 	DateU.valueOf(date);
	             			dated.addDays(days);
	   			             //return Util.numberToString(m*1000.);
	             			return dated.getDate();
			  }
			  idx = s.indexOf("w");
			  if (idx == -1)
				  idx = s.indexOf("W");
			  if (idx > 0) {
				  	int days = commonUTIL.converStringToInteger(s.substring(0, idx));
	          			DateU dated  = 	DateU.valueOf(date);
	          			dated.addDays(days * 7);
	   			
	              //return Util.numberToString(m*1000.);
	          			return dated.getDate();
			  }
	      idx = s.indexOf("m");
	      if (idx == -1)
	          idx = s.indexOf("M");
	      if (idx > 0) {
	    	  int days = commonUTIL.converStringToInteger(s.substring(0, idx));
	          
	        	 
	   			DateU dated  = 	DateU.valueOf(date);
	   			dated.addMonths(days);
	   			
	              //return Util.numberToString(m*1000.);
	          return dated.getDate();
	      }
	      idx = s.indexOf("y");
	      if (idx == -1)
	          idx = s.indexOf("Y");
	      if (idx > 0) {
	    	  int days = commonUTIL.converStringToInteger(s.substring(0, idx));
	          
	        	 
	   			DateU dated  = 	DateU.valueOf(date);
	   			dated.addYears(days);
	   			
	              //return Util.numberToString(m*1000.);
	          return dated.getDate();
	      }
	      
		  } catch(NumberFormatException m) {
			  return date;
	     }
	      return date;
	  }

}

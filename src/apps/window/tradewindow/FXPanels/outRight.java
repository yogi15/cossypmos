package apps.window.tradewindow.FXPanels;

import java.awt.Color;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;

import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;

import com.jidesoft.combobox.DateComboBox;

import util.NumericTextField;
import util.commonUTIL;
import util.common.DateU;

//VS4E -- DO NOT REMOVE THIS LINE!
public class outRight extends JPanel {
	private static final long serialVersionUID = 1L;
	public JLabel jLabel1;
	public JPanel jPanel0;
	public JLabel jLabel2;
	public JLabel jLabel3;
	public JLabel jLabel4;
	public JLabel jLabel5;
	public JTextField jTextField5;
	DecimalFormat format1 = new DecimalFormat("##,###,#######.######");
	DecimalFormat format = new DecimalFormat("##,###,#######.##");
	
	public NumericTextField jTextField1;
	public NumericTextField jTextField2;
	public JLabel jLabel6;
	public JLabel jLabel7;
	public NumericTextField jTextField4;
	public JLabel jLabel8;
	public JTextField jTextField7;
	public JLabel jLabel9;
	public JTextField jTextField6;
	//public com.standbysoft.component.date.swing.JDatePicker outRightDate;
	public com.jidesoft.combobox.DateComboBox  outRightDate;
	
	//public JCheckBox jCheckBox1;
	public JLabel jLabel10;
	public JComboBox jComboBox1;
	public JCheckBox jCheckBox2;
	public JCheckBox jCheckBox3;
	public JCheckBox jCheckBox0;
	private static final String PREFERRED_LOOK_AND_FEEL = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
	public outRight() {
		initComponents();
	}

	private void initComponents() {
		setLayout(new GroupLayout());
		add(getJPanel0(), new Constraints(new Leading(6, 829, 10, 10), new Leading(5, 80, 10, 10)));
		setSize(848, 93);
	}

	private JCheckBox getJCheckBox0() {
		if (jCheckBox0 == null) {
			jCheckBox0 = new JCheckBox();
			jCheckBox0.setText("Float");
			jCheckBox0.setEnabled(false);
		}
		return jCheckBox0;
	}

	private JCheckBox getJCheckBox3() {
		if (jCheckBox3 == null) {
			jCheckBox3 = new JCheckBox();
			jCheckBox3.setText("B2b");
		}
		return jCheckBox3;
	}

	private JCheckBox getJCheckBox2() {
		if (jCheckBox2 == null) {
			jCheckBox2 = new JCheckBox();
			jCheckBox2.setText("Split Curr");
		}
		return jCheckBox2;
	}

	private JComboBox getJComboBox1() {
		if (jComboBox1 == null) {
			jComboBox1 = new JComboBox();
			//jComboBox1.setModel(new DefaultComboBoxModel(new Object[] { "item0", "item1", "item2", "item3" }));
		}
		return jComboBox1;
	}

	private JLabel getJLabel10() {
		if (jLabel10 == null) {
			jLabel10 = new JLabel();
			jLabel10.setText("Action");
		}
		return jLabel10;
	}
	
	// Today checkbox commented.
	/*private JCheckBox getJCheckBox0() {
		if (jCheckBox1 == null) {
			jCheckBox1 = new JCheckBox();
			jCheckBox1.setSelected(true);
			jCheckBox1.setText("Today");
		}
		return jCheckBox1;
	}*/
 
	// this commented by Pankaj 
	/*private com.standbysoft.component.date.swing.JDatePicker getJTextField0() {
		if (outRightDate == null) {
			outRightDate = new com.standbysoft.component.date.swing.JDatePicker();
			outRightDate.setName("outRight");
		//	jTextField0.setText("jTextField0");
		} 
		return outRightDate;
	} */
	private com.jidesoft.combobox.DateComboBox getJTextField0() {
		if (outRightDate == null) {
			outRightDate = new com.jidesoft.combobox.DateComboBox();
			outRightDate.setFormat(new SimpleDateFormat("dd/MM/yyyy"));
				Calendar currentDate = Calendar.getInstance();
				outRightDate.setDate(currentDate.getTime());
			    outRightDate.setName("outRight");
			    
			   
			/*    outRightDate.addKeyListener(new KeyListener() {
				
				@Override
				public void keyTyped(KeyEvent arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void keyReleased(KeyEvent arg0) {
					// TODO Auto-generated method stub
					if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
						outRightDate.setDate(commonUTIL.getCurrentDate());
						String dateTxt = getDateText();
						setDateTextEmpty();
						Date date = outRightDate.getDate();
						if(date == null)
							date = commonUTIL.getCurrentDate();
						if(!commonUTIL.isEmpty(dateTxt)) {
					//	date =	checkDate(dateTxt.trim(),date);
						outRightDate.setDate(checkDate(dateTxt.trim(),date));
						} else {
							outRightDate.setDate(commonUTIL.getCurrentDate());
						}
						
					
			   			 
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
			    	
			    });*/
		//	jTextField0.setText("jTextField0");
		} 
		
		return outRightDate;
	} 
	

	private JTextField getJTextField6() {
		if (jTextField6 == null) {
			jTextField6 =  new  JTextField();
			jTextField6.setBackground(new Color(128, 255, 255));
			jTextField6.setEditable(false);
			 jTextField6.setText("NONE");
			//jTextField6.setText("jTextField0");
		}
		return jTextField6;
	}
	public String dateText = "";
	 public String getDateText() {
		
		   return dateText;
		  
	   }
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

	private JLabel getJLabel9() {
		if (jLabel9 == null) {
			jLabel9 = new JLabel();
			jLabel9.setText("Status");
		}
		return jLabel9;
	}

	private JTextField getJTextField7() {
		if (jTextField7 == null) {
			jTextField7 = new JTextField();
			jTextField7.setVisible(false);
		}
		return jTextField7;
	}

	private JLabel getJLabel8() {
		if (jLabel8 == null) {
			jLabel8 = new JLabel();
			jLabel8.setText("Trader");
		}
		return jLabel8;
	}

	private JTextField getJTextField4() {
		if (jTextField4 == null) {
			jTextField4 = new  NumericTextField(10,format1);
		//	jTextField4.setText("4");
			jTextField4.addActionListener(new java.awt.event.ActionListener() {
			    public void actionPerformed(java.awt.event.ActionEvent e) {
			    //	double dd = new Double(jTextField1.getText()).doubleValue();
			    //	double dd4 = new Double(jTextField4.getText()).doubleValue();
			    //	jTextField2.setText(commonUTIL.getStringFromDoubleExp(dd * dd4));
			    }
			});
		}
		return jTextField4;
	}

	private JLabel getJLabel7() {
		if (jLabel7 == null) {
			jLabel7 = new JLabel();
			jLabel7.setText("Rate");
		}
		return jLabel7;
	}

	private JLabel getJLabel6() {
		if (jLabel6 == null) {
			jLabel6 = new JLabel();
			jLabel6.setText("Date");
		}
		return jLabel6;
	}

	private JTextField getJTextField2() {
		if (jTextField2 == null) {
			jTextField2 = new NumericTextField(10,format);
		//	jTextField2.setText("jTextField1");
		}
		return jTextField2;
	}

	private JTextField getJTextField1() {
		if (jTextField1 == null) {
			jTextField1 = new NumericTextField(10,format);
		//	jTextField1.setText("0");
		}
		return jTextField1;
	}

	private JTextField getJTextField5() {
		if (jTextField5 == null) {
			jTextField5 = new JTextField();
			//jTextField5.setText("5");
		}
		return jTextField5;
	}

	private JLabel getJLabel5() {
		if (jLabel5 == null) {
			jLabel5 = new JLabel();
			jLabel5.setText("AMT2");
		}
		return jLabel5;
	}

	private JLabel getJLabel4() {
		if (jLabel4 == null) {
			jLabel4 = new JLabel();
			jLabel4.setText("AMT1");
		}
		return jLabel4;
	}

	private JLabel getJLabel3() {
		if (jLabel3 == null) {
			jLabel3 = new JLabel();
			jLabel3.setText("ID");
		}
		return jLabel3;
	}

	private JLabel getJLabel2() {
		if (jLabel2 == null) {
			jLabel2 = new JLabel();
			jLabel2.setBorder(new LineBorder(Color.black, 1, false));
		//	jLabel2.setForeground(Color.orange);
		}
		return jLabel2;
	}

	private JPanel getJPanel0() {
		if (jPanel0 == null) {
			jPanel0 = new JPanel();
			jPanel0.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			jPanel0.setLayout(new GroupLayout());
			jPanel0.add(getJLabel3(), new Constraints(new Leading(4, 17, 193, 288), new Leading(41, 24, 12, 12)));
			jPanel0.add(getJLabel0(), new Constraints(new Leading(6, 10, 10), new Leading(5, 24, 12, 12)));
			jPanel0.add(getJLabel2(), new Constraints(new Leading(32, 40, 179, 184), new Leading(6, 23, 12, 12)));
			jPanel0.add(getJTextField5(), new Constraints(new Leading(31, 72, 193, 288), new Leading(41, 24, 12, 12)));
			jPanel0.add(getJLabel4(), new Constraints(new Leading(112, 32, 10, 10), new Leading(6, 24, 12, 12)));
			jPanel0.add(getJLabel5(), new Constraints(new Leading(112, 32, 10, 10), new Leading(41, 24, 12, 12)));
			jPanel0.add(getJLabel10(), new Constraints(new Leading(548, 32, 10, 10), new Leading(9, 24, 10, 10)));
			jPanel0.add(getJLabel9(), new Constraints(new Leading(545, 10, 10), new Leading(41, 24, 12, 12)));
			jPanel0.add(getJTextField6(), new Constraints(new Leading(586, 149, 12, 12), new Leading(41, 25, 12, 12)));
			jPanel0.add(getJComboBox1(), new Constraints(new Leading(586, 148, 12, 12), new Leading(10, 25, 12, 12)));
			jPanel0.add(getJTextField0(), new Constraints(new Leading(355, 168, 10, 10), new Leading(41, 25, 12, 12)));
			jPanel0.add(getJLabel7(), new Constraints(new Leading(313, 34, 12, 12), new Leading(9, 24, 12, 12)));
			jPanel0.add(getJLabel6(), new Constraints(new Leading(314, 12, 12), new Leading(41, 24, 12, 12)));
			jPanel0.add(getJTextField1(), new Constraints(new Leading(148, 150, 10, 10), new Leading(5, 25, 12, 12)));
			jPanel0.add(getJTextField2(), new Constraints(new Leading(148, 150, 12, 12), new Leading(41, 25, 12, 12)));
			jPanel0.add(getJCheckBox2(), new Constraints(new Leading(746, 12, 12), new Leading(16, 19, 12, 12)));
			jPanel0.add(getJTextField4(), new Constraints(new Leading(353, 159, 10, 10), new Leading(5, 25, 12, 12)));
			//jPanel0.add(getJCheckBox0(), new Constraints(new Leading(746, 12, 12), new Leading(39, 12, 12)));
		}
		return jPanel0;
	}

	private JLabel getJLabel0() {
		if (jLabel1 == null) {
			jLabel1 = new JLabel();
			jLabel1.setText("CCY");
		}
		return jLabel1;
	}
	
	public static void main(String args[]) {
		JFrame frame = new JFrame();
		outRight out = new outRight();
		frame.add(out.getJPanel0());
		frame.setVisible(true);
		frame.setSize(100, 400);
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

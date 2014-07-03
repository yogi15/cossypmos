package apps.window.referencewindow;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.table.AbstractTableModel;

import org.dyno.visual.swing.layouts.Bilateral;
import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;

import util.ReferenceDataCache;
import util.RemoteServiceUtil;
import util.commonUTIL;
import util.common.DateU;
import apps.window.utilwindow.JDialogBoxForChoice;
import beans.CurrencySplitConfig;
import beans.DateRule;
import beans.StartUPData;
import dsServices.RemoteReferenceData;

//VS4E -- DO NOT REMOVE THIS LINE!
public class DateRuleWindow extends JPanel {

	private static final long serialVersionUID = 1L;
	Vector<DateRule> dateRules = new Vector<DateRule>();
	private JPanel jPanel11;
	private JPanel jPanel0;
	private JPanel jPanel2;
	private JTable jTable0;
	private JScrollPane jScrollPane0;
	private JButton jButton1;
	private JButton jButton0;
	private JButton jButton4;
	private JButton jButton3;
	private JButton jButton2;
	DateRule rule = null;
	 Vector<Integer> months = new  Vector<Integer>();
		private JLabel jLabel0;
		private JPanel jPanel1;
		private JLabel jLabel1;
		private JLabel jLabel2;
		private JLabel jLabel3;
		private JLabel jLabel4;
		private JLabel jLabel5;
		private JRadioButton jRadioButton0;
		private JRadioButton jRadioButton1;
		private JLabel jLabel6;
		private JLabel jLabel7;
		private JTextField jTextField1;
		private JTextField jTextField2;
		private JTextField jTextField0;
		private JLabel jLabel8;
		DefaultComboBoxModel dataRulemodel = new DefaultComboBoxModel();
		DefaultComboBoxModel weekDaysmodel = new DefaultComboBoxModel();
		DefaultComboBoxModel rankmodel = new DefaultComboBoxModel();
		DefaultComboBoxModel datarollsmodel = new DefaultComboBoxModel();
		DefaultListModel currencyList = new DefaultListModel();
		private JComboBox jComboBox0;
		private JComboBox jComboBox1;
		private JComboBox jComboBox2;
		private JComboBox jComboBox3;
		private JTextField jTextField3;
		private JButton jButton01;
		private JCheckBox jCheckBox0;
		private JCheckBox jCheckBox1;
		private JCheckBox jCheckBox2;
		private JCheckBox jCheckBox3;
		private JCheckBox jCheckBox4;
		private JCheckBox jCheckBox5;
		private JCheckBox jCheckBox6;
		private JCheckBox jCheckBox7;
		private JCheckBox jCheckBox8;
		private JPanel jPanel21;
		private JCheckBox jCheckBox9;
		private JCheckBox jCheckBox10;
		private JCheckBox jCheckBox11;
		private JButton jButton11;
		private JLabel jLabel9;
		private JTextField jTextField4;
		private JTextField jTextField5;
		private JLabel jLabel10;
		private JButton jButton21;
		private JTextArea jTextArea0;
		private JScrollPane jScrollPane01;
		RemoteReferenceData remoteBORef;
		TableModelUtil modelT = null;
		
	public DateRuleWindow() {
		init1();
		initComponents();
	}

	private void initComponents() {
		init();
		setLayout(new GroupLayout());
		add(getJPanel11(), new Constraints(new Leading(7, 932, 12, 12), new Leading(6, 285, 12, 12)));
		add(getJPanel0(), new Constraints(new Leading(4, 935, 12, 12), new Leading(345, 190, 12, 12)));
		add(getJPanel1(), new Constraints(new Leading(7, 929, 12, 12), new Leading(297, 42, 12, 12)));
		setSize(947, 541);
	}

	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			jPanel1.setLayout(new GroupLayout());
			jPanel1.add(getJButton1(), new Constraints(new Leading(110, 53, 10, 10), new Leading(6, 12, 12)));
			jPanel1.add(getJButton0(), new Constraints(new Leading(39, 12, 12), new Leading(6, 12, 12)));
			jPanel1.add(getJButton4(), new Constraints(new Leading(326, 67, 10, 10), new Leading(6, 12, 12)));
			jPanel1.add(getJButton3(), new Constraints(new Leading(255, 61, 10, 10), new Leading(6, 12, 12)));
			jPanel1.add(getJButton2(), new Constraints(new Leading(178, 12, 12), new Leading(6, 12, 12)));
		}
		return jPanel1;
	}

	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setText("UPDATE");
		}jButton2.addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent event) {
			  boolean flag = 	fillDateRule(rule);
			  if(flag) {
				  try {
					remoteBORef.updateDateRule(rule);
					int rowindex = jTable0.getSelectedRow();
					modelT.udpateValueAt(rule, rowindex, 1);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				  
			  }
			}
		});
		return jButton2;
	}

	private JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new JButton();
			jButton3.setText("NEW");
		}
		return jButton3;
	}

	private JButton getJButton4() {
		if (jButton4 == null) {
			jButton4 = new JButton();
			jButton4.setText("CLEAR");
		}
		return jButton4;
	}

	private JButton getJButton0() {
		if (jButton0 == null) {
			jButton0 = new JButton();
			jButton0.setText("ADD");
		}
		jButton0.addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent event) {
				DateRule dateR = new DateRule();
				dateR.setId(0);
				boolean flag = fillDateRule(dateR);
				if(flag) {
					try {
						int i = remoteBORef.saveDateRule(dateR);
						dateR.setId(i);
					//	dateRules.add(dateR);
					 
						modelT.addRow(dateR);
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});

		return jButton0;
	}

	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("DEL");
		}jButton1.addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent event) {
				int rowIndex = jTable0.getSelectedRow();
				try {
					if(remoteBORef.deleteDateRules(rule)) {
						
						modelT.delRow(rowIndex);
					}
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		return jButton1;
	}

	private JScrollPane getJScrollPane0() {
		if (jScrollPane0 == null) {
			jScrollPane0 = new JScrollPane();
			jScrollPane0.setViewportView(getJTable0());
		}
		return jScrollPane0;
	}

	private JTable getJTable0() {
		if (jTable0 == null) {
			jTable0 = new JTable();
			String col [] = {"ID","Name","Type","WeekDays","DateRoll","Rank","Day","Days"};
			modelT = new TableModelUtil(dateRules, col);
			jTable0.setModel(modelT);
		}  jTable0.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {

				int rowindex = jTable0.getSelectedRow();
			 rule = dateRules.get(rowindex);
				jComboBox0.setSelectedItem(rule.getType());
				jComboBox1.setSelectedItem(rule.getWeekdaysType());
				jComboBox2.setSelectedItem(rule.getRank());
				jComboBox3.setSelectedItem(rule.getDateROLL());
				jTextField0.setText(rule.getName());
				jTextField3.setText(rule.getHolidayCurr());
				end_beg = rule.getEnd_beg();
				mon_year_day_week = rule.getMon_year_day_week();
				monthEnabled(rule.getMonths());
				day = rule.getDay();
				days = rule.getDays();
				
			}
		});
		return jTable0;
	}

	

	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			jPanel2.setLayout(new GroupLayout());
		}
		return jPanel2;
	}

	private JPanel getJPanel0() {
		if (jPanel0 == null) {
			jPanel0 = new JPanel();
			jPanel0.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			jPanel0.setLayout(new GroupLayout());
			jPanel0.add(getJScrollPane0(), new Constraints(new Leading(2, 923, 10, 10), new Leading(6, 171, 10, 10)));
		}
		return jPanel0;
	}
	
	private JLabel getJLabel0() {
		if (jLabel0 == null) {
			jLabel0 = new JLabel();
			jLabel0.setText("Name");
		}
		return jLabel0;
	}
	
	private JScrollPane getJScrollPane01() {
		if (jScrollPane01 == null) {
			jScrollPane01 = new JScrollPane();
			jScrollPane01.setViewportView(getJTextArea0());
		}
		return jScrollPane01;
	}

	private JTextArea getJTextArea0() {
		if (jTextArea0 == null) {
			jTextArea0 = new JTextArea();
			//jTextArea0.setText("jTextArea0");
		}
		return jTextArea0;
	}
	public static void main(String args[]) {
		DateRuleWindow wind = new DateRuleWindow();
		JFrame frame = new JFrame();
		frame.add(wind);
		frame.setVisible(true);
		frame.setSize(500,500);
		
	}
	private JButton getJButton21() {
		if (jButton21 == null) {
			jButton21 = new JButton();
			jButton21.setText("Generate");
			jButton21.addMouseListener(new MouseAdapter() {
	
				public void mouseClicked(MouseEvent event) {
					jButton2MouseMouseClicked(event);
				}
			});
		}
		return jButton21;
	}

	private JLabel getJLabel10() {
		if (jLabel10 == null) {
			jLabel10 = new JLabel();
			jLabel10.setText("To Date");
		}
		return jLabel10;
	}

	private JTextField getJTextField5() {
		if (jTextField5 == null) {
			jTextField5 = new JTextField();
		jTextField5.setText("12/01/2014");
		}
		return jTextField5;
	}

	private JTextField getJTextField4() {
		if (jTextField4 == null) {
			jTextField4 = new JTextField();
			jTextField4.setText("12/01/2013");
		}
		return jTextField4;
	}

	private JLabel getJLabel9() {
		if (jLabel9 == null) {
			jLabel9 = new JLabel();
			jLabel9.setText("From Date");
		}
		return jLabel9;
	}

	private JButton getJButton11() {
		if (jButton11 == null) {
			jButton11 = new JButton();
			jButton11.setText("SELECT ALL");
			jButton11.addMouseListener(new MouseAdapter() {
	
				public void mouseClicked(MouseEvent event) {
					jButton1MouseMouseClicked(event);
				}
			});
		}
		return jButton11;
	}

	private JCheckBox getJCheckBox11() {
		if (jCheckBox11 == null) {
			jCheckBox11 = new JCheckBox();
			jCheckBox11.setText("DEC");
		}jCheckBox11.addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent event) {
				jCheckBox11MouseMouseClicked(event);
			}
		});
		return jCheckBox11;
	}

	private JCheckBox getJCheckBox10() {
		if (jCheckBox10 == null) {
			jCheckBox10 = new JCheckBox();
			jCheckBox10.setText("NOV");
		}jCheckBox10.addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent event) {
				jCheckBox10MouseMouseClicked(event);
			}
		});
		return jCheckBox10;
	}

	private JCheckBox getJCheckBox9() {
		if (jCheckBox9 == null) {
			jCheckBox9 = new JCheckBox();
			jCheckBox9.setText("OCT");
		}jCheckBox9.addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent event) {
				jCheckBox9MouseMouseClicked(event);
			}
		});
		return jCheckBox9;
	}
	private JPanel getJPanel11() {
		if (jPanel11 == null) {
			
			jPanel11 = new JPanel();
			jPanel11.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			jPanel11.setLayout(new GroupLayout());
			jPanel11.add(getJLabel6(), new Constraints(new Leading(319, 33, 10, 10), new Leading(8, 22, 10, 10)));
			jPanel11.add(getJLabel7(), new Constraints(new Leading(441, 70, 10, 10), new Leading(8, 22, 12, 12)));
			jPanel11.add(getJTextField1(), new Constraints(new Leading(355, 46, 10, 10), new Leading(4, 26, 12, 12)));
			jPanel11.add(getJTextField2(), new Constraints(new Leading(521, 80, 10, 10), new Leading(4, 26, 12, 12)));
			jPanel11.add(getJTextField0(), new Constraints(new Leading(116, 194, 10, 10), new Leading(4, 26, 12, 12)));
			jPanel11.add(getJLabel8(), new Constraints(new Leading(4, 52, 12, 12), new Leading(8, 22, 12, 12)));
			jPanel11.add(getJComboBox0(), new Constraints(new Leading(116, 130, 10, 10), new Leading(42, 26, 12, 12)));
			jPanel11.add(getJComboBox1(), new Constraints(new Leading(116, 130, 12, 12), new Leading(76, 26, 12, 12)));
			jPanel11.add(getJLabel1(), new Constraints(new Leading(4, 52, 12, 12), new Leading(44, 22, 12, 12)));
			jPanel11.add(getJLabel2(), new Constraints(new Leading(4, 52, 12, 12), new Leading(78, 22, 12, 12)));
			jPanel11.add(getJComboBox2(), new Constraints(new Leading(116, 130, 12, 12), new Leading(108, 26, 12, 12)));
			jPanel11.add(getJLabel3(), new Constraints(new Leading(4, 52, 12, 12), new Leading(108, 22, 12, 12)));
			jPanel11.add(getJLabel4(), new Constraints(new Leading(2, 62, 10, 10), new Leading(142, 22, 12, 12)));
			jPanel11.add(getJComboBox3(), new Constraints(new Leading(116, 130, 12, 12), new Leading(140, 26, 12, 12)));
			jPanel11.add(getJRadioButton0(), new Constraints(new Leading(2, 12, 12), new Leading(205, 10, 10)));
			jPanel11.add(getJLabel5(), new Constraints(new Leading(4, 62, 12, 12), new Leading(182, 22, 12, 12)));
			jPanel11.add(getJTextField3(), new Constraints(new Leading(116, 68, 10, 10), new Leading(176, 26, 12, 12)));
			jPanel11.add(getJButton01(), new Constraints(new Leading(193, 36, 10, 10), new Leading(178, 12, 12)));
			jPanel11.add(getJPanel21(), new Constraints(new Leading(319, 165, 10, 10), new Leading(38, 128, 12, 12)));
			jPanel11.add(getJButton11(), new Constraints(new Leading(496, 12, 12), new Leading(38, 12, 12)));
			jPanel11.add(getJLabel9(), new Constraints(new Leading(316, 58, 10, 10), new Leading(179, 12, 12)));
			jPanel11.add(getJTextField4(), new Constraints(new Leading(380, 118, 10, 10), new Leading(176, 25, 12, 12)));
			jPanel11.add(getJTextField5(), new Constraints(new Leading(380, 118, 12, 12), new Leading(207, 25, 12, 12)));
			jPanel11.add(getJLabel10(), new Constraints(new Leading(316, 58, 12, 12), new Leading(212, 12, 12)));
			jPanel11.add(getJButton21(), new Constraints(new Leading(380, 12, 12), new Leading(238, 12, 12)));
			jPanel11.add(getJScrollPane01(), new Constraints(new Leading(509, 126, 10, 10), new Bilateral(175, 12, 23)));
			jPanel11.add(getJRadioButton1(), new Constraints(new Leading(81, 65, 10, 10), new Leading(210, 12, 12)));
		}
		return jPanel11;
	}
	private JPanel getJPanel21() {
		if (jPanel21 == null) {
			jPanel21 = new JPanel();
			jPanel21.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			jPanel21.setLayout(new GroupLayout());
			jPanel21.add(getJCheckBox0(), new Constraints(new Leading(7, 10, 10), new Leading(7, 15, 10, 10)));
			jPanel21.add(getJCheckBox1(), new Constraints(new Leading(61, 48, 10, 10), new Leading(7, 15, 12, 12)));
			jPanel21.add(getJCheckBox3(), new Constraints(new Leading(7, 48, 10, 10), new Leading(34, 15, 12, 12)));
			jPanel21.add(getJCheckBox4(), new Constraints(new Leading(61, 48, 10, 10), new Leading(34, 15, 12, 12)));
			jPanel21.add(getJCheckBox5(), new Constraints(new Leading(108, 48, 10, 10), new Leading(34, 15, 12, 12)));
			jPanel21.add(getJCheckBox6(), new Constraints(new Leading(7, 48, 12, 12), new Leading(61, 15, 12, 12)));
			jPanel21.add(getJCheckBox7(), new Constraints(new Leading(61, 48, 12, 12), new Leading(61, 15, 10, 10)));
			jPanel21.add(getJCheckBox8(), new Constraints(new Leading(108, 48, 10, 10), new Leading(61, 15, 10, 10)));
			jPanel21.add(getJCheckBox9(), new Constraints(new Leading(7, 48, 12, 12), new Leading(90, 15, 12, 12)));
			jPanel21.add(getJCheckBox10(), new Constraints(new Leading(61, 48, 12, 12), new Leading(90, 15, 12, 12)));
			jPanel21.add(getJCheckBox11(), new Constraints(new Leading(108, 48, 12, 12), new Leading(90, 15, 10, 10)));
			jPanel21.add(getJCheckBox2(), new Constraints(new Leading(108, 48, 12, 12), new Leading(7, 19, 12, 12)));
		}
		return jPanel21;
	}

	private JCheckBox getJCheckBox8() {
		if (jCheckBox8 == null) {
			jCheckBox8 = new JCheckBox();
			jCheckBox8.setSelected(false);
			jCheckBox8.setText("SEP");
		}jCheckBox8.addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent event) {
				jCheckBox8MouseMouseClicked(event);
			}
		});
		return jCheckBox8;
	}

	private JCheckBox getJCheckBox7() {
		if (jCheckBox7 == null) {
			jCheckBox7 = new JCheckBox();
			jCheckBox7.setText("AUG");
		}jCheckBox7.addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent event) {
				jCheckBox7MouseMouseClicked(event);
			}
		});
		return jCheckBox7;
	}

	private JCheckBox getJCheckBox6() {
		if (jCheckBox6 == null) {
			jCheckBox6 = new JCheckBox();
			jCheckBox6.setText("JUL");
		}jCheckBox6.addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent event) {
				jCheckBox6MouseMouseClicked(event);
			}
		});
		return jCheckBox6;
	}

	private JCheckBox getJCheckBox5() {
		if (jCheckBox5 == null) {
			jCheckBox5 = new JCheckBox();
			jCheckBox5.setText("JUN");
		}jCheckBox5.addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent event) {
				jCheckBox5MouseMouseClicked(event);
			}
		});
		return jCheckBox5;
	}

	private JCheckBox getJCheckBox4() {
		if (jCheckBox4 == null) {
			jCheckBox4 = new JCheckBox();
			jCheckBox4.setText("MAY");
		}jCheckBox4.addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent event) {
				jCheckBox4MouseMouseClicked(event);
			}
		});
		return jCheckBox4;
	}

	private JCheckBox getJCheckBox3() {
		if (jCheckBox3 == null) {
			jCheckBox3 = new JCheckBox();
			jCheckBox3.setText("APR");
		}jCheckBox3.addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent event) {
				jCheckBox3MouseMouseClicked(event);
			}
		});
		return jCheckBox3;
	}

	private JCheckBox getJCheckBox2() {
		if (jCheckBox2 == null) {
			jCheckBox2 = new JCheckBox();
			jCheckBox2.setText("MAR");
		}jCheckBox2.addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent event) {
				jCheckBox2MouseMouseClicked(event);
			}
		});
		return jCheckBox2;
	}

	private JCheckBox getJCheckBox1() {
		if (jCheckBox1 == null) {
			jCheckBox1 = new JCheckBox();
			jCheckBox1.setText("FEB");
			jCheckBox1.addMouseListener(new MouseAdapter() {
				
				public void mouseClicked(MouseEvent event) {
					jCheckBox1MouseMouseClicked(event);
				}
			});
		}
		return jCheckBox1;
	}

	private JCheckBox getJCheckBox0() {
		if (jCheckBox0 == null) {
			jCheckBox0 = new JCheckBox();
			jCheckBox0.setText("JAN");
			jCheckBox0.addMouseListener(new MouseAdapter() {
	
				public void mouseClicked(MouseEvent event) {
					jCheckBox0MouseMouseClicked(event);
				}
			});
		}
		return jCheckBox0;
	}

	private JButton getJButton01() {
		if (jButton01 == null) {
			jButton01 = new JButton();
			jButton01.setText("jButton0");
		} processlistchoice(currencyList,"Currency");
     	final JDialogBoxForChoice choice12 = new JDialogBoxForChoice(currencyList);
     	jButton01.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				choice12.jList3.setModel(currencyList);
				choice12.setLocationRelativeTo(choice12);
				//choice12.setSize(200,200);
				choice12.setVisible(true);
				
			}
   		
   	});
    	choice12.addWindowListener(new WindowAdapter() {            
            public void windowClosing(WindowEvent e) {
               // System.out.println("Window closing");
                try {
                	String ss = "";
                  Object obj [] =  choice12.getObj();
                 for(int i =0;i<obj.length;i++)
                	 ss = ss + (String) obj[i] + ",";
                 if(ss.trim().length() > 0)
                 	jTextField3.setText(ss.substring(0, ss.length()-1));
                } catch (Throwable t) {
                    t.printStackTrace();
                }                
            }
    	});
	
		return jButton01;
	}

	private JTextField getJTextField3() {
		if (jTextField3 == null) {
			jTextField3 = new JTextField();
			//jTextField3.setText("jTextField3");
		}
		return jTextField3;
	}

	private JComboBox getJComboBox3() {
		if (jComboBox3 == null) {
			jComboBox3 = new JComboBox();
			jComboBox3.setModel(datarollsmodel);
		}jComboBox3.addItemListener(new ItemListener() {
			
			public void itemStateChanged(ItemEvent event) {
				jComboBox3ItemItemStateChanged(event);
			}
		});
		return jComboBox3;
	}

	private JComboBox getJComboBox2() {
		if (jComboBox2 == null) {
			jComboBox2 = new JComboBox();
			jComboBox2.setModel(rankmodel);
		}jComboBox2.addItemListener(new ItemListener() {
			
			public void itemStateChanged(ItemEvent event) {
				jComboBox2ItemItemStateChanged(event);
			}
		});
		return jComboBox2;
	}

	private JComboBox getJComboBox1() {
		if (jComboBox1 == null) {
			jComboBox1 = new JComboBox();
			jComboBox1.setModel(new DefaultComboBoxModel(new Object[] { "NONE", "MONDAY", "SUNDAY", "FRIDAY", "THRUSDAY", "WEDNESDAY" }));
			jComboBox1.addItemListener(new ItemListener() {
	
				public void itemStateChanged(ItemEvent event) {
					jComboBox1ItemItemStateChanged(event);
				}
			});
		}
		return jComboBox1;
	}

	private JComboBox getJComboBox0() {
		if (jComboBox0 == null) {
			jComboBox0 = new JComboBox();
			jComboBox0.setModel(dataRulemodel);
			jComboBox0.addItemListener(new ItemListener() {
	
				public void itemStateChanged(ItemEvent event) {
					jComboBox0ItemItemStateChanged(event);
				}
			});
		}
		return jComboBox0;
	}

	private JLabel getJLabel8() {
		if (jLabel8 == null) {
			jLabel8 = new JLabel();
			jLabel8.setText("Name");
		}
		return jLabel8;
	}

	private JTextField getJTextField0() {
		if (jTextField0 == null) {
			jTextField0 = new JTextField();
		//	jTextField0.setText("jTextField0");
		}
		return jTextField0;
	}

	private JTextField getJTextField2() {
		if (jTextField2 == null) {
			jTextField2 = new JTextField();
		//	jTextField2.setText("jTextField2");
		}
		return jTextField2;
	}

	private JTextField getJTextField1() {
		if (jTextField1 == null) {
			jTextField1 = new JTextField();
			//jTextField1.setText("jTextField1");
		}
		return jTextField1;
	}

	private JLabel getJLabel7() {
		if (jLabel7 == null) {
			jLabel7 = new JLabel();
			jLabel7.setText("Add Days");
		}
		return jLabel7;
	}

	private JLabel getJLabel6() {
		if (jLabel6 == null) {
			jLabel6 = new JLabel();
			jLabel6.setText("Day");
		}
		return jLabel6;
	}

	private JRadioButton getJRadioButton1() {
		if (jRadioButton1 == null) {
			jRadioButton1 = new JRadioButton();
			jRadioButton1.setSelected(true);
			jRadioButton1.setText("jRadioButton1");
		}
		return jRadioButton1;
	}

	private JRadioButton getJRadioButton0() {
		if (jRadioButton0 == null) {
			jRadioButton0 = new JRadioButton();
			jRadioButton0.setSelected(true);
			jRadioButton0.setText("Bus Day");
		}
		return jRadioButton0;
	}

	private JLabel getJLabel5() {
		if (jLabel5 == null) {
			jLabel5 = new JLabel();
			jLabel5.setText("Holiday");
		}
		return jLabel5;
	}

	private JLabel getJLabel4() {
		if (jLabel4 == null) {
			jLabel4 = new JLabel();
			jLabel4.setText("Roll Date TO");
		}
		return jLabel4;
	}

	private JLabel getJLabel3() {
		if (jLabel3 == null) {
			jLabel3 = new JLabel();
			jLabel3.setText("Rank");
		}
		return jLabel3;
	}

	private JLabel getJLabel2() {
		if (jLabel2 == null) {
			jLabel2 = new JLabel();
			jLabel2.setText("WeekDays");
		}
		return jLabel2;
	}

	private JLabel getJLabel1() {
		if (jLabel1 == null) {
			jLabel1 = new JLabel();
			jLabel1.setText("Type");
		}
		return jLabel1;
	}

	private boolean isMonthExistsInVector(int month) {
		boolean flag = false;
		for(int i=0;i<months.size();i++) {
			int mon = months.get(i);
			if(mon > 0 && mon == month) {
				flag = true;
			    break;
			}
		}
		return flag;
	}
public void init() {
	months.add(0,new Integer(0));
	months.add(1,new Integer(0));
	months.add(2,new Integer(0));
	months.add(3,new Integer(0));
	months.add(4,new Integer(0));
	months.add(5,new Integer(0));
	months.add(6,new Integer(0));
	months.add(7,new Integer(0));
	months.add(8,new Integer(0));
	months.add(9,new Integer(0));
	months.add(10,new Integer(0));
	months.add(11,new Integer(0));

	
}
public void setValues() {
	months.set(0,new Integer(1));
	months.set(1,new Integer(2));
	months.set(2,new Integer(3));
	months.set(3,new Integer(4));
	months.set(4,new Integer(5));
	months.set(5,new Integer(6));
	months.set(6,new Integer(7));
	months.set(7,new Integer(8));
	months.set(8,new Integer(9));
	months.set(9,new Integer(10));
	months.set(10,new Integer(11));
	months.set(11,new Integer(12));

	
}
protected void monthEnabled(String months2) {
	// TODO Auto-generated method stub
	String mons [] = months2.split(",");
	  months.clear();
	  init();
	// setZero();
	 jCheckBox0.setSelected(false);
		jCheckBox1.setSelected(false);
		jCheckBox2.setSelected(false);
		jCheckBox3.setSelected(false);
		jCheckBox4.setSelected(false);
		jCheckBox5.setSelected(false);
		jCheckBox6.setSelected(false);
		jCheckBox6.setSelected(false);
		jCheckBox7.setSelected(false);
		jCheckBox8.setSelected(false);
		jCheckBox9.setSelected(false);
		jCheckBox10.setSelected(false);
		jCheckBox11.setSelected(false);
	for(int i=0;i<mons.length;i++) {
		int mon = new Integer(mons[i]).intValue();
		if(mon == 1) {
			months.setElementAt(mon,0);
			 jCheckBox0.setSelected(true);
		}
		if(mon == 2) {
			months.setElementAt( mon,1);
			jCheckBox1.setSelected(true);
		}
		if(mon == 3) {
			months.setElementAt(mon,2);
			jCheckBox2.setSelected(true);
		}
		if(mon == 4) {
			months.setElementAt(mon,3);
			jCheckBox3.setSelected(true);
		}
		if(mon == 5) {
			months.setElementAt( mon,4);
			jCheckBox4.setSelected(true);
		}
		if(mon == 6) {
			months.setElementAt( mon,5);
			jCheckBox5.setSelected(true);
		}
		if(mon == 7) {
			months.setElementAt( mon,6);
			jCheckBox6.setSelected(true);
		}
		if(mon == 8) {
			months.setElementAt(mon,7);
			jCheckBox7.setSelected(true);
		}
		if(mon == 9) {
			months.setElementAt( mon,8);
			jCheckBox8.setSelected(true);
		}
		if(mon == 10) {
			months.setElementAt( mon,9);
			jCheckBox9.setSelected(true);
		}
		if(mon == 11) {
			months.setElementAt(mon,10);
			jCheckBox10.setSelected(true);
		}
		if(mon == 12) {
			months.setElementAt( mon,11);
			jCheckBox11.setSelected(true);
		}
		
		
	}
	
}

DateU end1U = null;
public void setZero() {
	months.set(0,new Integer(0));
	months.set(1,new Integer(0));
	months.set(2,new Integer(0));
	months.set(3,new Integer(0));
	months.set(4,new Integer(0));
	months.set(5,new Integer(0));
	months.set(6,new Integer(0));
	months.set(7,new Integer(0));
	months.set(8,new Integer(0));
	months.set(9,new Integer(0));
	months.set(10,new Integer(0));
	months.set(11,new Integer(0));

	
}
private boolean fillDateRule(DateRule dr) {
	boolean flag = false;
	String name = jTextField0.getText();
	if(commonUTIL.isEmpty(name)) {
		commonUTIL.showAlertMessage("Enter DateRule Name Type");
		return flag;
	}
	if(commonUTIL.isEmpty(type)) {
		commonUTIL.showAlertMessage("select DateRule Type");
		return flag;
	}
	
	if(commonUTIL.isEmpty(dateRolltype)) {
		commonUTIL.showAlertMessage("select DateRoll Type");
		return flag;
	}
	//String day = j
	if(!commonUTIL.isEmpty(jTextField1.getText())) {
		try{
		day = new Integer(jTextField1.getText()).intValue();
	}catch(NumberFormatException n) {
		commonUTIL.showAlertMessage("Enter Number ONly");
	}
	}
	if(!commonUTIL.isEmpty(jTextField2.getText())) {
		try {
		days = new Integer(jTextField2.getText()).intValue();
		}catch(NumberFormatException n) {
			commonUTIL.showAlertMessage("Enter Number ONly");
		}
	}
	flag = true;
	dr.setName(name);
	dr.setType(type);
	dr.setEnd_beg(end_beg);
	dr.setMon_year_day_week(mon_year_day_week);
	dr.setWeekdaysType(weekDays);
	dr.setRank(rank);
	dr.setDateROLL(dateRolltype);
	dr.setHolidayCurr(jTextField3.getText());
	String mon = getMonthsSelected();
	if(commonUTIL.isEmpty(mon)) {
		commonUTIL.showAlertMessage("Select Months");
		return flag;
	}
	mon = mon.substring(0,mon.length()-1);
	
	dr.setMonths(mon);
	dr.setDay(day);
	dr.setDays(days);
	
	return flag;
	   
}

private String getMonthsSelected() {
	String mons  = "";
	for(int i=0;i<months.size();i++) {
		int mon = months.get(i);
		if(mon > 0) {
			mons = mons + mon +",";
		}
	}
	
	//mons = mons.substring(0,mons.length()-1);
	return mons;
}

private void addMissingMonths(int month,DateU startU) {
	for(int i=0;i<months.size();i++) {
		int mon = months.get(i);
		if(mon > month) {
			int actualMon = startU.getMonth();
			if(mon < end1U.get_month()) {
			startU.addMonths(mon -actualMon  );
			String year = Integer.valueOf(startU.getYear()).toString();
			if(!containDuplicateYear(year)) 
				
			dates.add(commonUTIL.dateToString(startU.getDate()));
			}
			
		}
	}
}
private void getMonthsselected(int month,DateU startU) {
	
	int initalM = month;
	int y1 = startU.get_year();
	Vector<Integer> mon1 = new Vector<Integer>();
	for(int i=0;i<months.size();i++) {
		int mon = months.get(i);		
		if(mon >= month) {
			initalM = mon  - initalM;
		//	// // System.out.println(month + "  " + mon + "  "+(initalM));
			if(initalM != 0) 
			startU.addMonths(initalM);
			initalM = startU.getMonth() ;
		//	// // System.out.println(initalM);
			
			//jTextArea0.append(commonUTIL.dateToString(startU.getDate()));
			//jTextArea0.append("\n");
			String year = Integer.valueOf(startU.getYear()).toString();
			if(!containDuplicateYear(year)) 
			dates.add(commonUTIL.dateToString(startU.getDate()));
			
		} else {
			if(mon > 0)
			  mon1.add(mon);
		}
	}
	for(int i=0;i<mon1.size();i++) {
		int mon = mon1.get(i);
		int y2 = startU.getYear();
		int y = startU.getYear();
		if(y1 == y2) {
			Date d = startU.getlastDayofYear();
			startU = DateU.valueOf(d);
		}
		if(startU.getMonth() == 12) {
		startU.addMonths(mon);
		} else  {
			startU.addMonths( mon - startU.getMonth());
		}
		String year = Integer.valueOf(startU.getYear()).toString();
		if(!containDuplicateYear(year)) 
			dates.add(commonUTIL.dateToString(startU.getDate()));
	}
	addMissingMonths(startU.getMonth(),startU);
	System.out.println(dates.size());
	
}

private boolean containDuplicateYear(String year) {
	
	boolean flag = false;
	if(mon_year_day_week.equalsIgnoreCase("YEAR")) {
	for(int i=0;i<dates.size();i++) {
		String date = dates.get(i);
	    String yyyy = date.substring(6, date.length());
	    if(yyyy.equalsIgnoreCase(year)) {
	    	flag = true;
	    	break;
	    }
	}
	}
	return flag;
}

private void addToText() {
	boolean sameYear = false;
	for(int d=0;d<dates.size();d++) {
		String date = dates.get(d);
		Date dateD = commonUTIL.stringToDate(date,true);
		DateU dateU = DateU.valueOf(dateD);
		if(mon_year_day_week.equalsIgnoreCase("MON")) {
				if(end_beg.equalsIgnoreCase("END")) {
					int day = dateU.getDayOfMonth();
					int lastDay = dateU.getMonthLength();
					dateU.addDays(lastDay-day);
				}
				if(end_beg.equalsIgnoreCase("BEG")) {
					int day = dateU.getDayOfMonth();
					//int lastDay = dateU.getMonthLength();
					dateU.addDays((day-1) * -1);
				}
		} 
		if(mon_year_day_week.equalsIgnoreCase("YEAR")) {
			
			if(end_beg.equalsIgnoreCase("END")) {
				Date dd = dateU.getlastDayofYear();
				dateU = DateU.valueOf(dd);	
				//dateU.addDays(lastDay-day);
			}
			if(end_beg.equalsIgnoreCase("BEG")) {
				int day = dateU.getdayOftheYear();
				int lastDay = dateU.getMonthLength();
				dateU.addDays((day-1) * -1);
			}
		}
		if(!commonUTIL.isEmpty(jTextField1.getText())) {
			try{
			day = new Integer(jTextField1.getText()).intValue();
		}catch(NumberFormatException n) {
			commonUTIL.showAlertMessage("Enter Number ONly");
		}
		}
		if(!commonUTIL.isEmpty(jTextField2.getText())) {
			try {
			days = new Integer(jTextField2.getText()).intValue();
			}catch(NumberFormatException n) {
				commonUTIL.showAlertMessage("Enter Number ONly");
			}
		}
		dateU.addDays(day);
		dateU.addDays(days);
		// yogesh
		/* add logic for Date Roll
		 * 
		 * 
		 * 
		 * 
		 */
  if (dateRolltype.equals("PRECEEDING")) {
			
			dateU.addDays(-1);
			
			if(dateU.isWeekEndDay() ) {
				
				dateU.addDays(-1);
				
			}
			
		} else if (dateRolltype.equals("FOLLOWING")) {
			
			dateU.addDays(1);
			
			if(dateU.isWeekEndDay()) {
				
				dateU.addDays(1);
				
			}
			
		}
		
		jTextArea0.append(commonUTIL.dateToString(dateU.getDate()));
		jTextArea0.append("\n");
	}
}
private void getMonthsselected(int month,DateU startU,double noofYears) {
	int mon = month;
	int actualYear = startU.getYear();
	for(int i=0;i<noofYears;i++) {
			getMonthsselected(mon,startU);				
			int year = startU.getYear();
			
			
			Date d = startU.getlastDayofYear();
			startU = DateU.valueOf(d);		
			startU.addMonths(1);
			//// // System.out.println(startU.getDate());
			mon = 1;
			actualYear = startU.getYear();
			//} else {
			//	actualYear =startU.getYear();
			//}
	}
	
	
}
private void jButton2MouseMouseClicked(MouseEvent event) {
	dates.clear();
	dates.removeAllElements();
	jTextArea0.setText("");
	String starDate = jTextField4.getText();
	
	String endDate = jTextField5.getText();
	Date startD = commonUTIL.stringToDate(starDate,true);
	
	Date endD = commonUTIL.stringToDate(endDate,true);
	
	DateU startU = DateU.valueOf(startD);
	 int month = startU.getMonth();
	DateU endU = DateU.valueOf(endD);
	end1U = endU;
	//jTextArea0.append(commonUTIL.dateToString(startU.getDate()));
	//jTextArea0.append("\n"+commonUTIL.dateToString(endU.getDate()));
	
	double diff = startU.diff(startU, endU) / 365;
	startU =  DateU.valueOf(startD);
	if(diff < 1)
		 getMonthsselected(month,startU);
	else			
	 getMonthsselected(month,startU,diff);
	addToText();
	
	 
}
boolean selectALL = false;
private void jButton1MouseMouseClicked(MouseEvent event) {
	if(!selectALL) {
	selectALL = true;
	setZero();
	setValues();
	jCheckBox0.setSelected(true);
	jCheckBox1.setSelected(true);
	jCheckBox2.setSelected(true);
	jCheckBox3.setSelected(true);
	jCheckBox4.setSelected(true);
	jCheckBox5.setSelected(true);
	jCheckBox6.setSelected(true);
	jCheckBox6.setSelected(true);
	jCheckBox7.setSelected(true);
	jCheckBox8.setSelected(true);
	jCheckBox9.setSelected(true);
	jCheckBox10.setSelected(true);
	jCheckBox11.setSelected(true);
	} else {
		setZero();
		selectALL  = false;
		jCheckBox0.setSelected(false);
		jCheckBox1.setSelected(false);
		jCheckBox2.setSelected(false);
		jCheckBox3.setSelected(false);
		jCheckBox4.setSelected(false);
		jCheckBox5.setSelected(false);
		jCheckBox6.setSelected(false);
		jCheckBox6.setSelected(false);
		jCheckBox7.setSelected(false);
		jCheckBox8.setSelected(false);
		jCheckBox9.setSelected(false);
		jCheckBox10.setSelected(false);
		jCheckBox11.setSelected(false);
	}
	
	
	
	
}

String type = "";
int day = 0;
int days = 0;
String end_beg = "";
String mon_year_day_week = "";
String weekDays = "";
String dateRolltype = "";
String rank = "";
Vector<String> dates = new Vector<String>();
private static final String PREFERRED_LOOK_AND_FEEL = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
private void jComboBox0ItemItemStateChanged(ItemEvent event) {
	if(jComboBox0.getSelectedIndex() > -1)
	type =(String) jComboBox0.getSelectedItem();
	if(type.contains("DAY")) {
		jTextField1.setText("");
		jTextField1.setEnabled(true);
	} else {
		jTextField1.setText("");
		jTextField1.setEnabled(false);
	}
	if(type.contains("BEG")) {
		end_beg = "BEG";
	}
	if(type.contains("END")) {
		end_beg = "END";
	}
	if(type.contains("MONTH")) {
		mon_year_day_week = "MON";
	}
	if(type.contains("YEAR")) {
		mon_year_day_week = "YEAR";
	}
	if(type.contains("DAY")) {
		mon_year_day_week = "DAY";
	}
	if(type.contains("WEEKLY")) {
		
		jComboBox1.setEnabled(true);
		jComboBox2.setEnabled(true);
		
	} else {
		jComboBox1.setEnabled(false);
		jComboBox2.setEnabled(false);
	}
	
}

private void jCheckBox0MouseMouseClicked(MouseEvent event) {
	if(jCheckBox0.isSelected()) {
		if(!(isMonthExistsInVector(1)))
		months.setElementAt(new Integer(1),0);
	} else {
	//	months.removeElementAt(0);
		months.set(0,new Integer(0));
	}
}
private void jCheckBox0ChangeStateChanged(ChangeEvent event) {
	if(jCheckBox0.isSelected()) {
		if(!(isMonthExistsInVector(1)))
		months.setElementAt(new Integer(1),0);
	} else {
	//	months.removeElementAt(0);
		months.set(0,new Integer(0));
	}
}
private void jCheckBox1MouseMouseClicked(MouseEvent event) {
	if(jCheckBox1.isSelected()) {
		if(!(isMonthExistsInVector(2)))
		months.setElementAt(new Integer(2),1);
	} else {
	//	months.removeElementAt(1);
		months.set(1,new Integer(0));
	}
}
private void jCheckBox1ChangeStateChanged(ChangeEvent event) {
	if(jCheckBox1.isSelected()) {
		if(!(isMonthExistsInVector(2)))
		months.setElementAt(new Integer(2),1);
	} else {
	//	months.removeElementAt(1);
		months.set(1,new Integer(0));
	}
}
private void jCheckBox2MouseMouseClicked(MouseEvent event) {
	if(jCheckBox2.isSelected()) {
		if(!(isMonthExistsInVector(3)))
		months.setElementAt(new Integer(3),2);
	} else {
	//	months.removeElementAt(2);
		months.set(2,new Integer(0));
	}
}
private void jCheckBox2ChangeStateChanged(ChangeEvent event) {
	if(jCheckBox2.isSelected()) {
		if(!(isMonthExistsInVector(3)))
		months.setElementAt(new Integer(3),2);
	} else {
	//	months.removeElementAt(2);
		months.set(2,new Integer(0));
	}
}
private void jCheckBox3MouseMouseClicked(MouseEvent event) {
	if(jCheckBox3.isSelected()) {
		if(!(isMonthExistsInVector(4)))
		months.setElementAt(new Integer(4),3);
	} else {
	//	months.removeElementAt(3);
		months.set(3,new Integer(0));
	}
}
private void jCheckBox3ChangeStateChanged(ChangeEvent event) {
	if(jCheckBox3.isSelected()) {
		if(!(isMonthExistsInVector(4)))
		months.setElementAt(new Integer(4),3);
	} else {
	//	months.removeElementAt(3);
		months.set(3,new Integer(0));
	}
}
private void jCheckBox4MouseMouseClicked(MouseEvent event) {
	if(jCheckBox4.isSelected()) {
		if(!(isMonthExistsInVector(5)))
		months.setElementAt(new Integer(5),4);
	} else {
	//	months.removeElementAt(4);
	//	// // System.out.println("Remoove 5 "+ months.size());
		months.set(4,new Integer(0));
		// // System.out.println();
	}
}
private void jCheckBox4ChangeStateChanged(ChangeEvent event) {
	if(jCheckBox4.isSelected()) {
		if(!(isMonthExistsInVector(5)))
		months.setElementAt(new Integer(5),4);
	} else {
	//	months.removeElementAt(4);
		// // System.out.println("Remoove 5 "+ months.size());
		months.set(4,new Integer(0));
		// // System.out.println();
	}
}
private void jCheckBox5MouseMouseClicked(MouseEvent event) {
	if(jCheckBox5.isSelected()) {
		if(!(isMonthExistsInVector(6)))
		months.setElementAt(new Integer(6),5);
	} else {
	///	months.removeElementAt(5);
		// // System.out.println("Remoove 6 "+ months.size());
		months.set(5,new Integer(0));
		// // System.out.println();
	}
}
private void jCheckBox5ChangeStateChanged(ChangeEvent event) {
	if(jCheckBox5.isSelected()) {
		if(!(isMonthExistsInVector(6)))
		months.setElementAt(new Integer(6),5);
	} else {
	///	months.removeElementAt(5);
		// // System.out.println("Remoove 6 "+ months.size());
		months.set(5,new Integer(0));
		// // System.out.println();
	}
}
private void jCheckBox6MouseMouseClicked(MouseEvent event) {
	if(jCheckBox6.isSelected()) {
		if(!(isMonthExistsInVector(7)))
		months.setElementAt(new Integer(7),6);
	} else {
	//	months.removeElementAt(6);
		months.set(6,new Integer(0));
	}
}private void jCheckBox6ChangeStateChanged(ChangeEvent event) {
	if(jCheckBox6.isSelected()) {
		if(!(isMonthExistsInVector(7)))
		months.setElementAt(new Integer(7),6);
	} else {
	//	months.removeElementAt(6);
		months.set(6,new Integer(0));
	}
}private void jCheckBox7MouseMouseClicked(MouseEvent event) {
	if(jCheckBox7.isSelected()) {
		if(!(isMonthExistsInVector(8)))
		months.setElementAt(new Integer(8),7);
	} else {
	//	months.removeElementAt(7);
		months.set(7,new Integer(0));
	}
}
private void jCheckBox7ChangeStateChanged(ChangeEvent event) {
	if(jCheckBox7.isSelected()) {
		if(!(isMonthExistsInVector(8)))
		months.setElementAt(new Integer(8),7);
	} else {
	//	months.removeElementAt(7);
		months.set(7,new Integer(0));
	}
}
private void jCheckBox8MouseMouseClicked(MouseEvent event) {
	if(jCheckBox8.isSelected()) {
		if(!(isMonthExistsInVector(9)))
		months.setElementAt(new Integer(9),8);
	} else {
	//	months.removeElementAt(8);
		months.set(8,new Integer(0));
	}
}
private void jCheckBox8ChangeStateChanged(ChangeEvent event) {
	if(jCheckBox8.isSelected()) {
		if(!(isMonthExistsInVector(9)))
		months.setElementAt(new Integer(9),8);
	} else {
	//	months.removeElementAt(8);
		months.set(8,new Integer(0));
	}
}

private void jCheckBox9MouseMouseClicked(MouseEvent event) {
	if(jCheckBox9.isSelected()) {
		if(!(isMonthExistsInVector(10)))
		months.setElementAt(new Integer(10),9);
	} else {
		//months.removeElementAt(9);
		months.set(9,new Integer(0));
	}
}

private void jCheckBox9ChangeStateChanged(ChangeEvent event) {
	if(jCheckBox9.isSelected()) {
		if(!(isMonthExistsInVector(10)))
		months.setElementAt(new Integer(10),9);
	} else {
		//months.removeElementAt(9);
		months.set(9,new Integer(0));
	}
}
private void jCheckBox10MouseMouseClicked(MouseEvent event) {
	if(jCheckBox10.isSelected()) {
		if(!(isMonthExistsInVector(11)))
		months.setElementAt(new Integer(11),10);
	} else {
		//months.removeElementAt(10);
		months.set(10,new Integer(0));
	}
}
private void jCheckBox10ChangeStateChanged(ChangeEvent event) {
	if(jCheckBox10.isSelected()) {
		if(!(isMonthExistsInVector(11)))
		months.setElementAt(new Integer(11),10);
	} else {
		//months.removeElementAt(10);
		months.set(10,new Integer(0));
	}
}
private void jCheckBox11MouseMouseClicked(MouseEvent event) {
	if(jCheckBox11.isSelected()) {
		if(!(isMonthExistsInVector(12)))
		months.setElementAt(new Integer(12),11);
	} else {
		//months.removeElementAt(11);
		months.set(11,new Integer(0));
	}
}
private void jCheckBox11ChangeStateChanged(ChangeEvent event) {
	if(jCheckBox11.isSelected()) {
		if(!(isMonthExistsInVector(12)))
		months.setElementAt(new Integer(12),11);
	} else {
		//months.removeElementAt(11);
		months.set(11,new Integer(0));
	}
}

private void processModel(javax.swing.DefaultComboBoxModel combodata,Vector<StartUPData> data) {

	for (int i = 0; i < data.size(); i++) {

		StartUPData data1 = (StartUPData) data.get(i);

		combodata.insertElementAt(data1.getName(), i);

	}

}
public void processlistchoice(DefaultListModel list,String name ) {
	Vector vector;
	try {
		vector = (Vector) remoteBORef.getStartUPData(name);
		
		if(vector.size() > 0) {
		Iterator it = vector.iterator();
    	int i =0;
    	while(it.hasNext()) {
    		
    		StartUPData data = (StartUPData) it.next();
    	
		
			
    		list.addElement(data.getName());

		i++;
	}	
		}
	}catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			
}catch(Exception e) {
	commonUTIL.displayError("FeesPanel","getMasterDataOnComboBox", e);
}
	
}

public void init1() {
	
		// TODO Auto-generated method stub

		// de =ServerConnectionUtil.connect("localhost",
		// 1099,commonUTIL.getServerIP() );

		remoteBORef = (RemoteReferenceData) RemoteServiceUtil
				.getRemoteReferenceDataService();

		try {
			// remoteBORef = (RemoteReferenceData)
			// de.getRMIService("ReferenceData");
		Vector	dateroles = (Vector) remoteBORef.getStartUPData("DateRoll");
		Vector	ranks = (Vector) remoteBORef.getStartUPData("Rank");
		Vector	weekDays = (Vector) remoteBORef.getStartUPData("WeekDays");
		Vector	dateRUleType = (Vector) remoteBORef.getStartUPData("DateRuleType");
		Vector  holidays = (Vector) remoteBORef.selectALLHolidays();
		
			dateRules = (Vector) remoteBORef.getallDateRules();
			////.getStarupData("Quote)
		processModel(dataRulemodel,dateRUleType);
		processModel(rankmodel,ranks);
		processModel(datarollsmodel ,dateroles);
		processModel(weekDaysmodel,weekDays);
		
		//	modelt = new TableModelUtil(legalEntitys, col);
			//countryVec = (Vector) remoteBORef.selectALLCountry();
			// JComboBox comboBox14 = new JComboBox(
			// convertVectortoSringArray(countryVec) );
			// System.out.println(remoteBORef);
			// processTableData()

		} catch (RemoteException e) {

			e.printStackTrace();

		
	}
}

class TableModelUtil extends AbstractTableModel {

	final String[] columnNames;

	Vector<DateRule> data;
	RemoteReferenceData remoteRef;
	
	public TableModelUtil(Vector<DateRule> myData, String col[]) {
		this.columnNames = col;
		this.data = myData;
		
	}
	public int getColumnCount() {
		return columnNames.length;
	}

	public int getRowCount() {
		if (data != null)
			return data.size();
		return 0;
	}

	public String getColumnName(int col) {
		return columnNames[col];
	}

	public Object getValueAt(int row, int col) {
		Object value = null;
		DateRule dr = (DateRule) data.get(row);

		switch (col) {
		case 0:
			value = dr.getId();
			break;
		case 1:
			value = dr.getName();
			break;
		case 2:
			value = dr.getType();
			break;
		case 3:
			value = dr.getWeekdaysType();
			break;
		case 4:
			value = dr.getDateROLL();
			break;
		case 5:
			value = dr.getRank();
			break;
		case 6:
			value = dr.getDay();
			break;
		case 7:
			value = dr.getDays();
			break;
		}
		return value;
	}
	public boolean isCellEditable(int row, int col) {
		return false;
	}

	public void setValueAt(Object value, int row, int col) {
		System.out.println("Setting value at " + row + "," + col + " to "
				+ value + " (an instance of " + value.getClass() + ")");
		if (value instanceof CurrencySplitConfig) {
			data.set(row, (DateRule) value);
			this.fireTableDataChanged();
			System.out.println("New value of data:");
		}

	}

	public void addRow(Object value) {

		data.add((DateRule) value);
		this.fireTableDataChanged();

	}

	public void delRow(int row) {
		if (row != -1) {
			data.remove(row);
			this.fireTableDataChanged();
		}

	}

	public void udpateValueAt(Object value, int row, int col) {

		data.set(row, (DateRule) value);
		for (int i = 0; i < columnNames.length; i++)
			fireTableCellUpdated(row, i);

	}

	public void removeALL() {
		if (data != null) {
			data.removeAllElements();
		}
		data = null;
		this.fireTableDataChanged();
	}
}

private void jComboBox1ItemItemStateChanged(ItemEvent event) {
	if(jComboBox1.getSelectedIndex() != -1) {
		weekDays = (String) jComboBox1.getSelectedItem();
	}
}
private void jComboBox2ItemItemStateChanged(ItemEvent event) {
	if(jComboBox2.getSelectedIndex() != -1) {
		rank = (String) jComboBox2.getSelectedItem();
	}
}
private void jComboBox3ItemItemStateChanged(ItemEvent event) {
	if(jComboBox3.getSelectedIndex() != -1) {
		dateRolltype = (String) jComboBox3.getSelectedItem();
	}
}
}

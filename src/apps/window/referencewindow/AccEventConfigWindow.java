package apps.window.referencewindow;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;

import org.dyno.visual.swing.layouts.Bilateral;
import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;

import util.commonUTIL;
import apps.window.utilwindow.JDialogBoxForChoice;
import beans.AccEventConfig;
import beans.AccTriggerEvent;
import beans.StartUPData;
import dsServices.RemoteAccount;
import dsServices.RemoteReferenceData;
import dsServices.ServerConnectionUtil;

//VS4E -- DO NOT REMOVE THIS LINE!
public class AccEventConfigWindow extends JPanel {

	private static final long serialVersionUID = 1L;
	public static  ServerConnectionUtil de = null;
	RemoteReferenceData remoteBORef; 
	RemoteAccount remoteaccount;
	private JButton jButton0;
	private JButton jButton1;
	private JList jList0;
	private JScrollPane jScrollPane0;
	private JList jList1;
	private JScrollPane jScrollPane1;
	private JLabel jLabel1;
	private JList jList2;
//	AccEventLink accEventLink;
	private JScrollPane jScrollPane2;
	private JButton jButton2;
	private JComboBox jComboBox1;
	private JLabel jLabel2;
	private JLabel jLabel3;
	private JComboBox jComboBox2;
	private JLabel jLabel0;
	private JComboBox jComboBox0;
	private JButton jButton3;
	private JButton jButton4;
	private JButton jButton5;
	DefaultListModel accEvtTyp1 = new DefaultListModel();
	DefaultListModel accEvtTyp2 = new DefaultListModel();
	DefaultListModel triggerEvent = new DefaultListModel();
	DefaultListModel triggerEvent2 = new DefaultListModel();
	
	javax.swing.DefaultComboBoxModel productType = new javax.swing.DefaultComboBoxModel();
	javax.swing.DefaultComboBoxModel paymentType = new javax.swing.DefaultComboBoxModel();
	javax.swing.DefaultComboBoxModel reverType = new javax.swing.DefaultComboBoxModel();
	private static final String PREFERRED_LOOK_AND_FEEL = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
	public AccEventConfigWindow() {
		initData();
		initComponents();
	}

	private void initComponents() {
		setBorder(new LineBorder(Color.gray, 1, false));
		setLayout(new GroupLayout());
		add(getJButton0(), new Constraints(new Leading(151, 69, 12, 12), new Leading(115, 10, 10)));
		add(getJButton1(), new Constraints(new Leading(153, 69, 12, 12), new Leading(159, 12, 12)));
		add(getJLabel1(), new Constraints(new Leading(386, 93, 10, 10), new Leading(88, 12, 12)));
		add(getJScrollPane2(), new Constraints(new Leading(389, 145, 10, 10), new Leading(120, 193, 10, 10)));
		add(getJButton2(), new Constraints(new Leading(483, 44, 10, 10), new Leading(85, 10, 10)));
		add(getJLabel2(), new Constraints(new Leading(559, 117, 10, 10), new Leading(30, 12, 12)));
		add(getJLabel3(), new Constraints(new Leading(559, 117, 12, 12), new Leading(101, 12, 12)));
		add(getJLabel0(), new Constraints(new Leading(18, 83, 10, 10), new Leading(20, 26, 46, 275)));
		add(getJComboBox0(), new Constraints(new Leading(118, 134, 12, 12), new Leading(20, 26, 46, 275)));
		add(getJScrollPane0(), new Constraints(new Leading(11, 128, 78, 78), new Leading(70, 251, 10, 10)));
		add(getJScrollPane1(), new Constraints(new Leading(234, 125, 10, 10), new Leading(72, 251, 10, 10)));
		add(getJButton3(), new Constraints(new Leading(34, 10, 10), new Leading(356, 10, 10)));
		add(getJButton5(), new Constraints(new Leading(217, 10, 10), new Leading(356, 12, 12)));
		add(getJButton4(), new Constraints(new Leading(121, 76, 10, 10), new Leading(356, 12, 12)));
		add(getJComboBox1(), new Constraints(new Bilateral(559, 12, 134), new Leading(58, 28, 10, 10)));
		add(getJComboBox2(), new Constraints(new Leading(559, 134, 10, 10), new Leading(135, 29, 10, 10)));
		setSize(708, 420);
	}

	private JButton getJButton5() {
		if (jButton5 == null) {
			jButton5 = new JButton();
			jButton5.setText("Load");
		}
		return jButton5;
	}

	private JButton getJButton4() {
		if (jButton4 == null) {
			jButton4 = new JButton();
			jButton4.setText("Delete");
		}jButton4.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				AccEventConfig eventConfig = new AccEventConfig();
				eventConfig.setId(-1);
				fillAccEventConfig(eventConfig);
				triggerEvent2.remove(jList2.getSelectedIndex());
				jList2.removeAll();
				jList2.setModel(triggerEvent2);
			}
		});
		return jButton4;
	}

	private JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new JButton();
			jButton3.setText("Save");
		}jButton3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				AccEventConfig eventConfig = new AccEventConfig();
				eventConfig.setId(0);
				fillAccEventConfig(eventConfig);
			}
		});
		
		return jButton3;
	}

	private JComboBox getJComboBox0() {
		if (jComboBox0 == null) {
			jComboBox0 = new JComboBox();
			jComboBox0.setModel(productType);
			jComboBox0.setDoubleBuffered(false);
			jComboBox0.setBorder(null);
		} 
		jComboBox0.addItemListener( new ItemListener() {

        	public void itemStateChanged(ItemEvent e) {	
        		// TODO Auto-generated method stub
        		String productType = jComboBox0.getSelectedItem().toString();
        		String sql = " productType = '" + productType + "'";
        		try {
				Vector accEvents = (Vector)	remoteaccount.getAccEventConfigWhereClause(sql);
				accEvtTyp2.removeAllElements();
				if(accEvents != null) {
					for(int i=0;i<accEvents.size();i++)  {
						String accountType = ( (AccEventConfig) accEvents.get(i)).getAccEvtType();
						accEvtTyp2.add( accEvtTyp2.getSize(),accountType);
						
					}
					
				}
				
				
			//	jList1.setModel(accEvtTyp2);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        		
        		 
        		
        	}
        	   
           });
		return jComboBox0;
	}

	private JLabel getJLabel0() {
		if (jLabel0 == null) {
			jLabel0 = new JLabel();
			jLabel0.setText("Product Name");
		}
		return jLabel0;
	}

	private JComboBox getJComboBox2() {
		if (jComboBox2 == null) {
			jComboBox2 = new JComboBox();
			jComboBox2.setModel(paymentType);
			jComboBox2.setDoubleBuffered(false);
			jComboBox2.setBorder(null);
		}
		return jComboBox2;
	}

	private JLabel getJLabel3() {
		if (jLabel3 == null) {
			jLabel3 = new JLabel();
			jLabel3.setText("Event Class");
		}
		return jLabel3;
	}

	private JLabel getJLabel2() {
		if (jLabel2 == null) {
			jLabel2 = new JLabel();
			jLabel2.setText("Reversal Type");
		}
		return jLabel2;
	}

	private JComboBox getJComboBox1() {
		if (jComboBox1 == null) {
			jComboBox1 = new JComboBox();
			jComboBox1.setModel(reverType);
			jComboBox1.setDoubleBuffered(false);
			jComboBox1.setBorder(null);
		}
		return jComboBox1;
	}

	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setText("jButton2");
		}  	
		final JDialogBoxForChoice choice12 = new JDialogBoxForChoice(triggerEvent);
		jButton2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				choice12.jList3.setModel(triggerEvent);
				choice12.setLocationRelativeTo(choice12);
				//choice12.setSize(200,200);
				choice12.setVisible(true);
				
			}
   		
   	});
    	choice12.addWindowListener(new WindowAdapter() {            
               public void windowClosing(WindowEvent e) {
                  // System.out.println("Window closing");
                  
                   	String ss = "";
                     Object obj [] =  choice12.getObj();
               //      jList2.removeAll();
                  //   triggerEvent2.removeAllElements();
                     
                     for(int i=0;i<obj.length;i++) {
                    	 if(!triggerEvent2.contains((String) obj[i]))
                    	 triggerEvent2.add(triggerEvent2.getSize(),(String) obj[i]);
                     }
                  
                   //  jList2.setModel(triggerEvent2);
                     
                     
               }
               
       	}); 
      
		return jButton2;
	}

	private JScrollPane getJScrollPane2() {
		if (jScrollPane2 == null) {
			jScrollPane2 = new JScrollPane();
			jScrollPane2.setViewportView(getJList2());
		}
		return jScrollPane2;
	}

	private JList getJList2() {
		if (jList2 == null) {
			jList2 = new JList();
			
		jList2.setModel(triggerEvent2);
		}
		return jList2;
	}

	private JLabel getJLabel1() {
		if (jLabel1 == null) {
			jLabel1 = new JLabel();
			jLabel1.setText("Triggering EVT");
		}
		return jLabel1;
	}

	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getJList1());
		}
		return jScrollPane1;
	}

	private JList getJList1() {
		if (jList1 == null) {
			jList1 = new JList();
			
			jList1.setModel(accEvtTyp2);
			jList1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		}
		jList1.addMouseListener( new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				String productType = jComboBox0.getSelectedItem().toString();
				String acceventType = (String) jList1.getSelectedValue();
				String sql = " productType = '" + productType + "' and accEventType = '" + acceventType + "'";
				try {
					Vector v1 = (Vector) remoteaccount.getAccEventConfigWhereClause(sql);
					triggerEvent2.removeAllElements();
					if(v1 != null && (!v1.isEmpty())) {
						AccEventConfig accEvent = (AccEventConfig) v1.elementAt(0);
						String sqlt = " acceventConfigID = " + accEvent.getId();
						Vector existingTriggerEvents = (Vector) remoteaccount.getAccountTriggerEvtsWhereClause(sqlt);
						
						if(existingTriggerEvents != null) {
							for(int i=0;i<existingTriggerEvents.size();i++) {
								String tEvent = (String) existingTriggerEvents.get(i);
								triggerEvent2.add(triggerEvent2.getSize(),tEvent);
							}
						} else {
							
						}
						jComboBox1.setSelectedItem(accEvent.getReversalType());
						jComboBox2.setSelectedItem(accEvent.getPaymentType());
						
					}
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		
				
		});
        	   
          
		return jList1;
	}

	private JScrollPane getJScrollPane0() {
		if (jScrollPane0 == null) {
			jScrollPane0 = new JScrollPane();
			jScrollPane0.setViewportView(getJList0());
		}
		return jScrollPane0;
	}

	private JList getJList0() {
		if (jList0 == null) {
			jList0 = new JList();
			
		jList0.setModel(accEvtTyp1);
		}
		return jList0;
	}

	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("<<");
		}jButton1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
			
        
				if(jList1.getSelectedIndex() >= 0) { 
					int i = jList1.getSelectedIndex();
					accEvtTyp1.addElement(jList1.getSelectedValue());
					jList0.removeAll();
					jList0.setModel(accEvtTyp1);
					jList1.removeAll();
					accEvtTyp2.remove(i);
					
					jList1.setModel(accEvtTyp2);
					}
				
			}
    		
    	});
		return jButton1;
	}
	
	public void fillAccEventConfig(AccEventConfig config) {
		String reversalType = null;
		String productType = null;
		String eventClass = null;
		if(jComboBox0.getSelectedItem() != null)
				productType	 = jComboBox0.getSelectedItem().toString();
		if(jComboBox1.getSelectedItem() != null)
			reversalType = jComboBox1.getSelectedItem().toString();
		if(jComboBox2.getSelectedItem() != null)
		 eventClass = jComboBox2.getSelectedItem().toString();
		String acceventType = (String) jList1.getSelectedValue();
		if(commonUTIL.isEmpty(acceventType)) {
			commonUTIL.showAlertMessage("Select acceventType ");
			return;
		}
		if(commonUTIL.isEmpty(productType)) {
			commonUTIL.showAlertMessage("Select productType ");
			return;
		}
		if(commonUTIL.isEmpty(reversalType)) {
			commonUTIL.showAlertMessage("Select reversalType ");
			return;
		}
		if(commonUTIL.isEmpty(eventClass)) {
			commonUTIL.showAlertMessage("Select eventClass ");
			return;
		}
	
		config.setProductType(productType);
		config.setAccEvtType(acceventType);
		config.setPaymentType(eventClass);
		config.setReversalType(reversalType);
		Object values [] = jList2.getSelectedValues();
		if(values.length == 0)  {
			commonUTIL.showAlertMessage("Select atleast on TriggerEvent ");
			return;
		}
			
		if(config.getId() == 0) {
			try {
			int configID =	remoteaccount.saveAccountEvtConfig(config);
			String sql = " acceventConfigID = " + configID;
			Vector existingTriggerEvents = (Vector) remoteaccount.getAccountTriggerEvtsWhereClause(sql);
			Vector temp = null; 
			if(existingTriggerEvents != null) {
				temp = new Vector<String>();
				for(int i=0;i<existingTriggerEvents.size();i++) {
					String tEvent = (String) existingTriggerEvents.get(i);
					temp.addElement(tEvent);
				}
					
			}
			for(int i=0;i<values.length;i++) {
				String trrigerevent = (String) values[i];
				AccTriggerEvent evts = new AccTriggerEvent();
				evts.setAccEventConfigID(configID);
				
				evts.setTriggerEvent(trrigerevent);
				if(temp != null && (!temp.contains(trrigerevent))) {
					 remoteaccount.saveAccountTriggerEvts(evts);
				} else {
				   remoteaccount.saveAccountTriggerEvts(evts);
				}
			}
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				commonUTIL.displayError("AccEventConfigWindow", "FillData", e);
			}
		} 
		if(config.getId() == -1 ) {
			try {
			int configID =	remoteaccount.getAccountEvtConfig(config);
			Object tvalues [] = jList2.getSelectedValues();
			for(int i=0;i<tvalues.length;i++) {
				String trrigerevent = (String) tvalues[i];
				AccTriggerEvent evts = new AccTriggerEvent();
				evts.setAccEventConfigID(configID);
				
				evts.setTriggerEvent(trrigerevent);
				remoteaccount.deleteAccountTriggerEvts(evts);
			}
			if(tvalues.length == 0)  {
				remoteaccount.deleteAccountEvtConfig(config);
			}
			//	remoteaccount.deleteAccountEvtConfig(config);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				commonUTIL.displayError("AccEventConfigWindow", "FillData on delete ", e);
			}
		}
	}

	private JButton getJButton0() {
		if (jButton0 == null) {
			jButton0 = new JButton();
			jButton0.setText(">>");
		}jButton0.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(jList0.getSelectedIndex() >= 0) { 
				int i = jList0.getSelectedIndex();
				if(!accEvtTyp2.contains(jList0.getSelectedValue()))
				accEvtTyp2.add(accEvtTyp2.getSize(),jList0.getSelectedValue());
			//	jList1.removeAll();
				jList1.setModel(accEvtTyp2);
			//	jList0.removeAll();
				//accEvtTyp1.remove(i);
				
				//jList0.setModel(accEvtTyp1);
				}
			}
    		
    	});
		return jButton0;
	}
	public void initData() {
		de =ServerConnectionUtil.connect("localhost", 1099,commonUTIL.getServerIP() );
     	 try {
     		remoteBORef = (RemoteReferenceData) de.getRMIService("ReferenceData");
     		remoteaccount = (RemoteAccount) de.getRMIService("Account");
     		processlistchoice(accEvtTyp1,"accEvent");
     		processlistchoice(triggerEvent,"TriggerEvent");
     		getMasterDataOnComboBox(productType,"ProductType");
     		getMasterDataOnComboBox(paymentType,"accPaymentType");
     		getMasterDataOnComboBox(reverType,"accReversalType");
     		 		  
     		
  	  	 } catch (RemoteException e) {
  			e.printStackTrace();
  	}
	}

	
	private final void getMasterDataOnComboBox( javax.swing.DefaultComboBoxModel combodata,String name) {
		Vector vector = null;
		try {
			vector = (Vector) remoteBORef.getStartUPData(name);
			
			if(vector.size() > 0) {
			Iterator it = vector.iterator();
	    	int i =0;
	    	//combodata.insertElementAt(" ", 0);
	    	
	    	while(it.hasNext()) {
	    		
	    		StartUPData data = (StartUPData) it.next();
	    	
			
				
			combodata.insertElementAt(data.getName(), i);
			i++;
		}	
	    	
			}
		}catch (RemoteException e) {
					// TODO Auto-generated catch block
				
		commonUTIL.displayError("SDIWIndow","getMasterDataOnComboBox", e);
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
			commonUTIL.displayError("SDIWINDOW","getMasterDataOnComboBox", e);
		}
			
}
}

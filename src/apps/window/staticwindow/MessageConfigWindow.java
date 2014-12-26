package apps.window.staticwindow;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EtchedBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

import org.dyno.visual.swing.layouts.Bilateral;
import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;

import swingUtils.TableColumnAdjuster;
import util.commonUTIL;
import apps.window.utilwindow.JDialogBoxForChoice;
import apps.window.utilwindow.JDialogTable;
import beans.LegalEntity;
import beans.MessageConfig;
import beans.StartUPData;
import dsServices.RemoteReferenceData;
import dsServices.ServerConnectionUtil;


//VS4E -- DO NOT REMOVE THIS LINE!
public class MessageConfigWindow extends JPanel {

	private static final long serialVersionUID = 1L;
	RemoteReferenceData referenceData;
	public static  ServerConnectionUtil de = null;
	private JPanel jPanel0;
	private JComboBox jProductType;
	private JButton productbut;
	private JLabel jLabel0;
	private JComboBox jSubType;
	private JLabel jLabel1;
	private JButton subtypebut;
	private JLabel jLabel2;
	private JList jList0;
	private JScrollPane jScrollPane0;
	private JPanel jPanel1;
	private JSplitPane jSplitPane0;
	private JPanel jPanel3;
	private JTable jTable0;
	private JScrollPane jScrollPane1;
	private JPanel jPanel2;
	private JButton jButton2;
	private JButton jButton3;
	private JButton jButton4;
	private JLabel jLabel3;
	private JLabel jLabel4;
	private JLabel jLabel5;
	private JLabel jLabel6;
	private JLabel jLabel7;
	private JComboBox jTextField2;
	private JTextField jTextField3;
	private JComboBox messageType;
	private JTextField PO;
	private JComboBox POContactType;
	private JLabel jLabel8;
	private JLabel jLabel9;
	private JLabel jLabel11;
	private JComboBox jFormatType;
	private JComboBox jGateWay;
	private JTextField jTemplateName;
	private JLabel jLabel10;
	private JLabel jLabel12;
	private JTextField jReceiver;
	private JComboBox jComboBox1;
	private JComboBox jComboBox2;
	Vector formatTemplateTypes = null;
	javax.swing.DefaultComboBoxModel productModel = new javax.swing.DefaultComboBoxModel();
	 javax.swing.DefaultComboBoxModel productSubModel = new javax.swing.DefaultComboBoxModel();
	 javax.swing.DefaultComboBoxModel MessageTemplateModel = new javax.swing.DefaultComboBoxModel();
	 javax.swing.DefaultComboBoxModel messageTypeModel = new javax.swing.DefaultComboBoxModel();
	 javax.swing.DefaultComboBoxModel MessageGatewayModel = new javax.swing.DefaultComboBoxModel();
	 javax.swing.DefaultComboBoxModel MessageFormateModel = new javax.swing.DefaultComboBoxModel();
	 javax.swing.DefaultComboBoxModel podefaulatContactModel = new javax.swing.DefaultComboBoxModel();
	 javax.swing.DefaultComboBoxModel ledefaulatContactModel = new javax.swing.DefaultComboBoxModel();
	 javax.swing.DefaultComboBoxModel addressdefaulatContactModel = new javax.swing.DefaultComboBoxModel();
	 javax.swing.DefaultComboBoxModel rolesdefaulatModel = new javax.swing.DefaultComboBoxModel();
	 DefaultListModel templates = new DefaultListModel();
	 /**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}
	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}


	String role = "CounterParty";
	 DefaultListModel<String> triggerEvts = new DefaultListModel<String>();
	 Hashtable<Integer,LegalEntity> messLeg = new Hashtable<Integer,LegalEntity>();
	 Vector<MessageConfig> data = new Vector<MessageConfig>();
	 
	 TableModelUtil model = null;
	private JButton jButton5;
	int receiverID =0;
		 int poid = 0;
		 String cols [] = {"id","ProductType","ProductSubType","TriggerEvent","MessageType","PO","PO Contact","Receiver","Receiver Contact","FormatType","GateWay","TemplateName"};
	 String s [] = {"id","LegalEntityName"};
		DefaultTableModel letablemodel = new DefaultTableModel(s,0);
		String poCOL [] = {"id","POName"};
		DefaultTableModel potablemodel = new DefaultTableModel(poCOL,0);
		private JButton jButton6;
		private JButton jButton7;
		private JButton jButton8;
		private JLabel jLabel13;
		private JComboBox rolesData;
		private static final String PREFERRED_LOOK_AND_FEEL = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
		public MessageConfigWindow() {
		init();
		initComponents();
	}
	private void init() {
		// TODO Auto-generated method stub
		de =ServerConnectionUtil.connect("localhost", 1099,commonUTIL.getServerIP());
	   	 try {
	   	       referenceData = (RemoteReferenceData) de.getRMIService("ReferenceData");
	   		Vector legalentitys = (Vector) referenceData.selectAllLs();  // will be causing a performance problem. 
	   		Vector prdoucts = (Vector) referenceData.getStartUPData("ProductType");
	   		Vector MessageType = (Vector) referenceData.getStartUPData("MessageType");
	   		Vector MessageFormateType = (Vector) referenceData.getStartUPData("MessageFormateType");
	   		Vector MessageGateway = (Vector) referenceData.getStartUPData("MessageGateway");
	   		Vector MessageTemplate = (Vector) referenceData.getStartUPData("MessageTemplate");
	   		Vector lecontacts = (Vector) referenceData.getStartUPData("LEContacts");
	   		Vector triggerEvs = (Vector) referenceData.getStartUPData("TriggerEvent");
	   		Vector messageAddres = (Vector) referenceData.getStartUPData("MessageAddressType");
	   		Vector receiverRoles = (Vector) referenceData.getStartUPData("Roles");
	   		formatTemplateTypes  = (Vector) referenceData.getStartUPData("HTMLTemplate");
	   		data = (Vector) referenceData.selectALLMessageConfigs();
	   		getLEDataCombo1(legalentitys,messLeg);
	   		model = new TableModelUtil(data, cols, messLeg);
	   		processBookData(MessageType,messageTypeModel,true);
	   		processBookData(prdoucts,productModel,false);
	   		processBookData(MessageFormateType,MessageFormateModel,false);
	 //  		processBookData(MessageTemplate,MessageTemplateModel,false);
	   		processBookData(MessageGateway,MessageGatewayModel,false);
	   		processBookData(lecontacts,podefaulatContactModel,false);
	   		processBookData(lecontacts,ledefaulatContactModel,false);
	   		processBookData(messageAddres,addressdefaulatContactModel,false);
	   		processBookData(receiverRoles,rolesdefaulatModel,false);
	   		processlistchoice(triggerEvts,triggerEvs);
	   	  processlistchoice(templates,formatTemplateTypes);
	   		
	 	} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void initComponents() {
		setLayout(new GroupLayout());
		add(getJPanel0(), new Constraints(new Leading(7, 1323, 10, 10), new Leading(6, 623, 10, 10)));
		setSize(1338, 634);
	}
	private JComboBox getJComboBox0() {
		if (rolesData == null) {
			rolesData = new JComboBox();
			rolesData.setModel(rolesdefaulatModel);
		}
		rolesData.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if(rolesData.getSelectedIndex() != -1) {
				role = rolesData.getSelectedItem().toString();
				setRole(role);
				getLEDataCombo1(letablemodel,getRole());
				}
				
			}
		});
		return rolesData;
	}
	private JLabel getJLabel13() {
		if (jLabel13 == null) {
			jLabel13 = new JLabel();
			jLabel13.setText("Receiver Role");
		}
		return jLabel13;
	}
	private JButton getJButton8() {
		if (jButton8 == null) {
			jButton8 = new JButton();
			jButton8.setText("Load");
			
			jButton8.addActionListener(new java.awt.event.ActionListener() {
				 public void actionPerformed(java.awt.event.ActionEvent evt) {
					 try {
						 String productType = (String) jProductType.getSelectedItem().toString();
						 String productsubType = (String) jSubType.getSelectedItem().toString();
						 data =  (Vector) referenceData.getMessageConfigsonProductype(productType, productsubType);
						 model = new TableModelUtil(data, cols, messLeg);
							jTable0.setModel(model);
						jTable0.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
						 jTable0.getColumnModel().getColumn(0).setPreferredWidth(100); 
						 jTable0.getColumnModel().getColumn(1).setPreferredWidth(150); 
						 jTable0.getColumnModel().getColumn(2).setPreferredWidth(150); 
						 jTable0.getColumnModel().getColumn(3).setPreferredWidth(150); 
						 jTable0.getColumnModel().getColumn(4).setPreferredWidth(150); 
						 jTable0.getColumnModel().getColumn(5).setPreferredWidth(180); 
						 jTable0.getColumnModel().getColumn(6).setPreferredWidth(130); 
						 jTable0.getColumnModel().getColumn(7).setPreferredWidth(130); 
						 jTable0.getColumnModel().getColumn(8).setPreferredWidth(230); 
						 jTable0.getColumnModel().getColumn(9).setPreferredWidth(200); 
						 jTable0.getColumnModel().getColumn(10).setPreferredWidth(140); 
						 jTable0.getColumnModel().getColumn(11).setPreferredWidth(300); 
						 TableColumnAdjuster tca = new TableColumnAdjuster(jTable0);
							tca.adjustColumns();
						
					 } catch (RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
				 }
			});
		}
		return jButton8;
	}
	private JButton getJButton7() {
		if (jButton7 == null) {
			jButton7 = new JButton();
			jButton7.setText("jButton7");
		}
		 jButton7.addActionListener(new java.awt.event.ActionListener() {
			 public void actionPerformed(java.awt.event.ActionEvent evt) {
				 try {
						
						String formatType = jFormatType.getSelectedItem().toString();
						 formatTemplateTypes = (Vector)	  referenceData.getStartUPData(formatType.toUpperCase()+"Template");
						  processlistchoice(templates,formatTemplateTypes);
						  
						 
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		          
		            
			 final JDialogBoxForChoice choice12 = new JDialogBoxForChoice(templates);
				choice12.setLocationRelativeTo(choice12);
				//choice12.setSize(200,200);
				choice12.jList3.setModel(templates);
				choice12.setVisible(true);
				choice12.addWindowListener(new WindowAdapter() {            
		            public void windowClosing(WindowEvent e) {
		               // System.out.println("Window closing");
		               
		                	String ss = "";
		                  Object obj [] =  choice12.getObj();
		                  if(obj != null && obj.length == 0) 
		                	  return;
		                  if(obj != null || obj.length > 0) 
		                	jTemplateName.setText((String) obj[0]) ;
		                  choice12.cmodList2.clear();
		                  choice12.cmodList3.clear();
		            } 
		            
		    	});
			 }
		 });
	
		return jButton7;
	}
	private JButton getJButton6() {
		if (jButton6 == null) {
			jButton6 = new JButton();
			jButton6.setText("jButton6");
		}getLEDataCombo1(letablemodel,getRole());
        final  JDialogTable showLE = new JDialogTable(letablemodel);
        showLE.setLocationRelativeTo(this);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	showLE.setVisible(true);
            	}
        }); showLE.jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
        	@Override
			public void mouseClicked(MouseEvent e) {
				int id  = ((Integer)	showLE.jTable1.getValueAt(showLE.jTable1.getSelectedRow(),0)).intValue();
				String ss = (String)	showLE.jTable1.getValueAt(showLE.jTable1.getSelectedRow(),1);
				 jReceiver.setText(ss);
				 receiverID= id;
				 showLE.dispose();
			}
			
    
    	
    });   
		return jButton6;
	}
	private JButton getJButton5() {
		if (jButton5 == null) {
			jButton5 = new JButton();
			jButton5.setText("PO");
		}
		return jButton5;
		
	}
	private JComboBox getJComboBox2() {
		if (jComboBox2 == null) {
			jComboBox2 = new JComboBox();
			jComboBox2.setModel(new DefaultComboBoxModel(new Object[] { "TRADE", "TRANSFER" }));
			jComboBox2.setSelectedIndex(0);
		}
		return jComboBox2;
	}

	private JComboBox getJComboBox1() {
		if (jComboBox1 == null) {
			jComboBox1 = new JComboBox();
			jComboBox1.setModel(ledefaulatContactModel);
		}
		return jComboBox1;
	}

	private JTextField getJReceiver() {
		if (jReceiver == null) {
			jReceiver = new JTextField();
			jReceiver.setText("");
		}
		return jReceiver;
	}

	private JComboBox getJProductType() {
		if (jProductType == null) {
			jProductType = new JComboBox();
			jProductType.setModel(productModel);
			//jTextField0.setText("producttype0");
		}jProductType.addItemListener( new ItemListener() {

        	public void itemStateChanged(ItemEvent e) {
        		// TODO Auto-generated method stub
        		
        	
        	String productType = jProductType.getSelectedItem().toString();
        	productSubModel.removeAllElements();
        	productSubModel = null;
        	Vector subTpe;
			try {
				 productSubModel = new javax.swing.DefaultComboBoxModel();
				subTpe = (Vector)	  referenceData.getStartUPData(productType+".subType");
				
				  processBookData(subTpe, productSubModel, false);
				  jSubType.setModel(productSubModel);
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
          
            
        		
        		
        	}
        	   
           });
		return jProductType;
	}

	private JLabel getJLabel12() {
		if (jLabel12 == null) {
			jLabel12 = new JLabel();
			jLabel12.setText("Receiver Contact Type");
		}
		return jLabel12;
	}

	private JLabel getJLabel10() {
		if (jLabel10 == null) {
			jLabel10 = new JLabel();
			jLabel10.setText("Template Name");
		}
		return jLabel10;
	}

	private JTextField getJTemplateName() {
		if (jTemplateName == null) {
			jTemplateName = new JTextField();
			//jTemplateName.setModel(MessageTemplateModel);
		}
		return jTemplateName;
	}

	private JComboBox getJGateWay() {
		if (jGateWay == null) {
			jGateWay = new JComboBox();
			jGateWay.setModel(MessageGatewayModel);
		}
		return jGateWay;
	}

	private JComboBox getJFormatType() {
		if (jFormatType == null) {
			jFormatType = new JComboBox();
			jFormatType.setModel(MessageFormateModel);
		}jFormatType.addItemListener( new ItemListener() {

        	public void itemStateChanged(ItemEvent e) {
        		// TODO Auto-generated method stub
        		
        	
        	
        	
			jTemplateName.setText("");
        		
        		
        	}
        	   
           });
		return jFormatType;
	}

	private JLabel getJLabel11() {
		if (jLabel11 == null) {
			jLabel11 = new JLabel();
			jLabel11.setText("Receiver");
		}
		return jLabel11;
	}

	private JLabel getJLabel9() {
		if (jLabel9 == null) {
			jLabel9 = new JLabel();
			jLabel9.setText("GateWay");
		}
		return jLabel9;
	}

	private JLabel getJLabel8() {
		if (jLabel8 == null) {
			jLabel8 = new JLabel();
			jLabel8.setText("Format Type");
		}
		return jLabel8;
	}

	private JComboBox getPOContactType() {
		if (POContactType == null) {
			POContactType = new JComboBox();
			POContactType.setModel(podefaulatContactModel);
		}
		return POContactType;
	}

	private JTextField getPO() {
		if (PO == null) {
			PO = new JTextField();
			PO.setText("");
		}
		return PO;
	}
		

	private JComboBox getMessageType() {
		if (messageType == null) {
			messageType = new JComboBox();
			messageType.setModel(messageTypeModel);
		}
		return messageType;
	}

	private JTextField getJTextField3() {
		if (jTextField3 == null) {
			jTextField3 = new JTextField();
			jTextField3.setText("subtype");
		}
		return jTextField3;
	}

	private JComboBox getJTextField2() {
		if (jTextField2 == null) {
			jTextField2 = new JComboBox();
			jTextField2.setModel(addressdefaulatContactModel);
		}
		return jTextField2;
	}

	private JLabel getJLabel7() {
		if (jLabel7 == null) {
			jLabel7 = new JLabel();
			jLabel7.setText("PO Contact Type");
		}
		return jLabel7;
	}

	private JLabel getJLabel6() {
		if (jLabel6 == null) {
			jLabel6 = new JLabel();
			jLabel6.setText("PO");
		}
		return jLabel6;
	}

	private JLabel getJLabel5() {
		if (jLabel5 == null) {
			jLabel5 = new JLabel();
			jLabel5.setText("MessageType");
		}
		return jLabel5;
	}

	private JLabel getJLabel4() {
		if (jLabel4 == null) {
			jLabel4 = new JLabel();
			jLabel4.setText("Sub Type");
		}
		return jLabel4;
	}

	private JLabel getJLabel3() {
		if (jLabel3 == null) {
			jLabel3 = new JLabel();
			jLabel3.setText("Address Type");
		}
		return jLabel3;
	}
	private JButton getJButton4() {
		if (jButton4 == null) {
			jButton4 = new JButton();
			jButton4.setText("DELETE");
		}
		return jButton4;
	}

	private JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new JButton();
			jButton3.setText("EDIT");
		}
		return jButton3;
	}

	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setText("ADD");
		}jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	
                 MessageConfig messConfig = new MessageConfig();
                 messConfig.setId(0);
                if( fillConfig(messConfig)) {
                	try {
						int id = referenceData.saveMessageConfig(messConfig);
						messConfig.setId(id);
					    model.addRow(messConfig);
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                }
            	
            	            	
            }
        }); 
		return jButton2;
	}

	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			jPanel2.setLayout(new GroupLayout());
			jPanel2.add(getJScrollPane1(), new Constraints(new Bilateral(3, 11, 25), new Leading(278, 322, 10, 10)));
			jPanel2.add(getJPanel3(), new Constraints(new Bilateral(5, 12, 642), new Leading(6, 258, 10, 10)));
		}
		return jPanel2;
	}
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			jScrollPane1.setViewportView(getJTable0());
		}
		return jScrollPane1;
	}

	private JTable getJTable0() {
		if (jTable0 == null) {
			jTable0 = new JTable();
			jTable0.setModel(model);
			jTable0.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			 jTable0.getColumnModel().getColumn(0).setPreferredWidth(100); 
			 jTable0.getColumnModel().getColumn(1).setPreferredWidth(150); 
			 jTable0.getColumnModel().getColumn(2).setPreferredWidth(150); 
			 jTable0.getColumnModel().getColumn(3).setPreferredWidth(150); 
			 jTable0.getColumnModel().getColumn(4).setPreferredWidth(150); 
			 jTable0.getColumnModel().getColumn(5).setPreferredWidth(180); 
			 jTable0.getColumnModel().getColumn(6).setPreferredWidth(130); 
			 jTable0.getColumnModel().getColumn(7).setPreferredWidth(130); 
			 jTable0.getColumnModel().getColumn(8).setPreferredWidth(230); 
			 jTable0.getColumnModel().getColumn(9).setPreferredWidth(200); 
			 jTable0.getColumnModel().getColumn(10).setPreferredWidth(140); 
			 jTable0.getColumnModel().getColumn(11).setPreferredWidth(300); 
			// jTable0.getColumnModel().getColumn(12).setPreferredWidth(500); 
			// jTable0.getColumnModel().getColumn(13).setPreferredWidth(190); 

		
			TableColumnAdjuster tca = new TableColumnAdjuster(jTable0);
			tca.adjustColumns();
		}jTable0.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				int selectID = jTable0.getSelectedRow();
				MessageConfig messConfig = (MessageConfig)	data.get(jTable0.getSelectedRow());
				jSubType.setSelectedItem(messConfig.getProductSubType());
				jProductType.setSelectedItem(messConfig.getProductType());
				jList0.setSelectedValue(messConfig.getEventType(), true);
				messageType.setSelectedItem(messConfig.getMessageType());
				jFormatType.setSelectedItem(messConfig.getFormatType());
				jTemplateName.setText(messConfig.getTemplateName());
				jGateWay.setSelectedItem(messConfig.getGateWay());
				PO.setText(messLeg.get(messConfig.getPoid()).getName());
				poid = messConfig.getPoid();
				receiverID = messConfig.getReceiverID();
				jReceiver.setText(messLeg.get(messConfig.getPoid()).getName());
				POContactType.setSelectedItem(messConfig.getPoContactType());
				jComboBox1.setSelectedItem(messConfig.getReceiverContactType());
				
			
				
			}
			  });
		return jTable0;
	}

	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jPanel3 = new JPanel();
			jPanel3.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			jPanel3.setLayout(new GroupLayout());
			jPanel3.add(getJLabel5(), new Constraints(new Leading(7, 89, 12, 12), new Leading(73, 24, 12, 12)));
			jPanel3.add(getJLabel4(), new Constraints(new Leading(10, 89, 12, 12), new Leading(41, 24, 12, 12)));
			jPanel3.add(getJLabel6(), new Constraints(new Leading(7, 89, 12, 12), new Leading(114, 24, 12, 12)));
			jPanel3.add(getJButton0(), new Constraints(new Leading(278, 41, 10, 10), new Leading(113, 12, 12)));
			jPanel3.add(getJLabel9(), new Constraints(new Leading(352, 10, 10), new Leading(49, 12, 12)));
			jPanel3.add(getJLabel10(), new Constraints(new Leading(352, 105, 12, 12), new Leading(81, 12, 12)));
			jPanel3.add(getJButton2(), new Constraints(new Leading(165, 12, 12), new Leading(222, 26, 12, 12)));
			jPanel3.add(getJButton3(), new Constraints(new Leading(248, 68, 10, 10), new Leading(222, 26, 12, 12)));
			jPanel3.add(getJLabel12(), new Constraints(new Leading(352, 132, 10, 10), new Leading(200, 12, 12)));
			jPanel3.add(getJGateWay(), new Constraints(new Leading(495, 166, 10, 10), new Leading(38, 27, 12, 12)));
			jPanel3.add(getJTemplateName(), new Constraints(new Leading(495, 192, 10, 10), new Leading(76, 27, 12, 12)));
			jPanel3.add(getJComboBox1(), new Constraints(new Leading(496, 166, 12, 12), new Leading(192, 29, 12, 12)));
			jPanel3.add(getJButton7(), new Constraints(new Leading(706, 42, 12, 12), new Leading(77, 12, 12)));
			jPanel3.add(getJTextField3(), new Constraints(new Leading(94, 180, 12, 12), new Leading(38, 27, 12, 12)));
			jPanel3.add(getMessageType(), new Constraints(new Leading(94, 180, 12, 12), new Leading(68, 29, 10, 10)));
			jPanel3.add(getPO(), new Constraints(new Leading(94, 180, 12, 12), new Leading(109, 29, 12, 12)));
			jPanel3.add(getPOContactType(), new Constraints(new Leading(97, 180, 10, 10), new Leading(187, 29, 12, 12)));
			jPanel3.add(getJLabel7(), new Constraints(new Leading(10, 75, 12, 12), new Leading(192, 24, 12, 12)));
			jPanel3.add(getJLabel11(), new Constraints(new Leading(352, 96, 12, 12), new Leading(159, 12, 12)));
			jPanel3.add(getJReceiver(), new Constraints(new Leading(495, 190, 12, 12), new Leading(153, 29, 12, 12)));
			jPanel3.add(getJButton6(), new Constraints(new Leading(706, 43, 12, 12), new Leading(157, 12, 12)));
			jPanel3.add(getJLabel13(), new Constraints(new Leading(352, 96, 12, 12), new Leading(125, 12, 12)));
			jPanel3.add(getJComboBox0(), new Constraints(new Leading(498, 166, 10, 10), new Leading(111, 29, 12, 12)));
			jPanel3.add(getJLabel3(), new Constraints(new Leading(10, 89, 12, 12), new Leading(5, 24, 12, 12)));
			jPanel3.add(getJLabel8(), new Constraints(new Leading(352, 80, 12, 12), new Leading(13, 12, 12)));
			jPanel3.add(getJFormatType(), new Constraints(new Leading(495, 166, 12, 12), new Leading(2, 27, 12, 12)));
			jPanel3.add(getJTextField2(), new Constraints(new Leading(94, 180, 12, 12), new Leading(2, 27, 12, 12)));
			jPanel3.add(getJButton4(), new Constraints(new Leading(342, 81, 10, 10), new Leading(222, 26, 12, 12)));
		}
		return jPanel3;
	}
	private JPanel getJPanel0() {
		if (jPanel0 == null) {
			jPanel0 = new JPanel();
			jPanel0.setLayout(new GroupLayout());
			jPanel0.add(getJSplitPane0(), new Constraints(new Bilateral(2, 0, 1266), new Leading(-4, 619, 10, 10)));
		}
		return jPanel0;
	}
	private JSplitPane getJSplitPane0() {
		if (jSplitPane0 == null) {
			jSplitPane0 = new JSplitPane();
			jSplitPane0.setBorder(null);
			jSplitPane0.setDividerLocation(316);
			jSplitPane0.setLeftComponent(getJPanel1());
			jSplitPane0.setRightComponent(getJPanel2());
		}
		return jSplitPane0;
	}
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			jPanel1.setLayout(new GroupLayout());
			jPanel1.add(getJProductType(), new Constraints(new Leading(85, 166, 10, 10), new Leading(10, 27, 10, 10)));
			jPanel1.add(getJLabel0(), new Constraints(new Leading(5, 77, 10, 10), new Leading(13, 22, 12, 12)));
			jPanel1.add(getJSubtype(), new Constraints(new Leading(85, 166, 12, 12), new Leading(47, 27, 12, 12)));
			jPanel1.add(getJLabel1(), new Constraints(new Leading(5, 76, 12, 12), new Leading(47, 22, 12, 12)));
			jPanel1.add(getJComboBox2(), new Constraints(new Leading(86, 164, 12, 12), new Leading(82, 26, 41, 148)));
			jPanel1.add(getJLabel2(), new Constraints(new Leading(5, 76, 12, 12), new Leading(86, 22, 41, 148)));
			jPanel1.add(getJScrollPane0(), new Constraints(new Leading(14, 246, 10, 10), new Leading(164, 438, 10, 10)));
			jPanel1.add(getJButton8(), new Constraints(new Leading(8, 10, 10), new Leading(123, 10, 10)));
		}
		return jPanel1;
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
			jList0.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			
			
			jList0.setModel(triggerEvts);
		}
		return jList0;
	}
	private JLabel getJLabel2() {
		if (jLabel2 == null) {
			jLabel2 = new JLabel();
			jLabel2.setText("Event Type");
		}
		return jLabel2;
	}

	private JButton getJButton1() {
		if (subtypebut == null) {
			subtypebut = new JButton();
			subtypebut.setText("jButton1");
		}
		return subtypebut;
	}

	private JLabel getJLabel1() {
		if (jLabel1 == null) {
			jLabel1 = new JLabel();
			jLabel1.setText("SubType");
		}
		return jLabel1;
	}

	private JComboBox getJSubtype() {
		if (jSubType == null) {
			jSubType = new JComboBox();
			jSubType.setModel(productSubModel);
		}
		return jSubType;
	}

	private JLabel getJLabel0() {
		if (jLabel0 == null) {
			jLabel0 = new JLabel();
			jLabel0.setText("ProductType");
		}
		return jLabel0;
	}

	private JButton getJButton0() {
		if (productbut == null) {
			productbut = new JButton();
			productbut.setText("REC");
		} 
		getLEDataCombo1(potablemodel,"PO");
        final  JDialogTable showPO = new JDialogTable(potablemodel);
        showPO.setLocationRelativeTo(this);
        productbut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	
            
            	
            	showPO.setVisible(true);
            	
            }
        }); showPO.jTable1.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				int id  = ((Integer)	showPO.jTable1.getValueAt(showPO.jTable1.getSelectedRow(),0)).intValue();
			
				 String ss = (String)	showPO.jTable1.getValueAt(showPO.jTable1.getSelectedRow(),1);
				 PO.setText(ss);
				 poid= id;
				 showPO.dispose();
			}
			
    
    	
    });   
		return productbut;
	}
	
	
	
	public boolean fillConfig(MessageConfig messConfig) {
		boolean flag = false;
		String productType = jProductType.getSelectedItem().toString();
		String addressType = jTextField2.getSelectedItem().toString();
	   messConfig.setAddressType(addressType);
		if(jSubType.getSelectedIndex() == -1) {
			commonUTIL.showAlertMessage("Select Product SubType");
			return flag;
		}
		messConfig.setProductType(productType);
		
		messConfig.setProductSubType(jSubType.getSelectedItem().toString());
		if(jList0.isSelectionEmpty()) {
			commonUTIL.showAlertMessage("Select Triggering Event");
			return flag;
		}
		messConfig.setEventType(jList0.getSelectedValue().toString());
		if(messageType.getSelectedIndex() == -1) {
			commonUTIL.showAlertMessage("Select messageType ");
			return flag;
		}
		messConfig.setMessageType(messageType.getSelectedItem().toString());
		if(jFormatType.getSelectedIndex() == -1) {
			commonUTIL.showAlertMessage("Select Format Type ");
			return flag;
		}
		messConfig.setFormatType(jFormatType.getSelectedItem().toString());
		if(commonUTIL.isEmpty(jTemplateName.getText())) {
			commonUTIL.showAlertMessage("Select  TemplateName ");
			return flag;
		}
		messConfig.setTemplateName(jTemplateName.getText());
		if(jGateWay.getSelectedIndex() == -1) {
			commonUTIL.showAlertMessage("Select  GateWay ");
			return flag;
		}
		messConfig.setGateWay(jGateWay.getSelectedItem().toString());
		if(poid == 0) {
			commonUTIL.showAlertMessage("Select  Po ");
			return flag;
			
		}
		messConfig.setPoid(poid);
		if(receiverID == 0) {
			commonUTIL.showAlertMessage("Select  Receiver ");
			return flag;
			
		}
		messConfig.setReceiverID(receiverID);
		messConfig.setPoContactType(POContactType.getSelectedItem().toString());
	      messConfig.setReceiverContactType(jComboBox1.getSelectedItem().toString());
		String productSubType =  jSubType.getSelectedItem().toString();
		String eventType =  jList0.getSelectedValue().toString();
		flag = true;
		return flag;
	}
	
	
	private void processBookData(Vector book,DefaultComboBoxModel cmodel,boolean flag) {
		// TODO Auto-generated method stub
		
	    	
	    		Iterator it = book.iterator();
	    		int i =0;
    			while(it.hasNext()) {			
    				StartUPData boo = (StartUPData) it.next();
    					cmodel.addElement(boo.getName());
    					
    				
		
    			
	    		
	    	}
	}
	public void processlistchoice(DefaultListModel list,Vector roles ) {
    	Vector vector;
    	if(roles.size() > 0) {
    		Iterator it = roles.iterator();
        	int i =0;
        	while(it.hasNext()) {
        		StartUPData data = (StartUPData) it.next();
        		list.addElement(data.getName());
        		i++;
    	}	
    		}
    }
	public void getLEDataCombo1(DefaultTableModel model,String leRole) {
		Vector vector;
		try {
			
				vector = (Vector) referenceData.selectLEonWhereClause(" role like '%"+leRole + "%'");
			   Iterator it = vector.iterator();
	    	   int i =0;
	    	while(it.hasNext()) {
	    		
	    		LegalEntity le =	(LegalEntity) it.next();
	    		model.insertRow(i, new Object[]{le.getId(),le.getName()});
	    		i++;
		}	
		}catch (RemoteException e) {
					// TODO Auto-generated catch block
			commonUTIL.displayError("AccountWindow","getLEDataCombo1", e);
				}
		
		
	}
	
	public void getLEDataCombo1(Vector ledata,Hashtable<Integer,LegalEntity> legalEntitys) {
		Vector vector;
		
			
			
			   Iterator it = ledata.iterator();
	    	   int i =0;
	    	while(it.hasNext()) {
	    		
	    		LegalEntity le =	(LegalEntity) it.next();
	    		legalEntitys.put(le.getId(),le);
	    	}
		
		
		
	}
	

class TableModelUtil extends AbstractTableModel {   
    
	 final String[] columnNames;  
	    
	 Vector<MessageConfig> data;   
	 RemoteReferenceData remoteRef ;
	 Hashtable<Integer,LegalEntity> legalentitys = null;
	 
	
	 public TableModelUtil( Vector<MessageConfig> myData,String col [],Hashtable<Integer,LegalEntity> messConfigs) {   
	 	this.columnNames = col;
	this.data = myData;   
	this.legalentitys = messConfigs;
	}   

	    
	 public int getColumnCount() {   
	     return columnNames.length;   
	 }   
	    
	 public int getRowCount() {   
		 if(data != null)
	     return data.size();   
		 return 0;
	 }   
	 public String getColumnName(int col) {   
	     return columnNames[col];   
	 }   
	 public Object getValueAt(int row, int col) {   
	     Object value = null;  	 
	  // "id","ProductType","ProductSubType","TriggerEvent","MessageType","PO","PO Contact","Receiver","ReceiverRole","FormatType","GateWay","TemplateName"
	     MessageConfig currSplit = (MessageConfig) data.get(row);
	    
		 switch (col) {
	     case 0:
	         value = currSplit.getId();
	         break;
	     case 1:
	         value =currSplit.getProductType();
	         break;
	     case 2:
	         value =currSplit.getProductSubType();
	         break;
	     case 3:
	    	
	         value =  currSplit.getEventType();
	         break;
	     case 4:
	         value =currSplit.getMessageType();
	         break;
	     case 5:
	    	 LegalEntity le = getLegalEntity(currSplit.getPoid());
	         value = le.getName();
	         break;
	     case 6:
	    		
	         value =currSplit.getPoContactType();
	         break;
	     case 7:
	    	
	    	 LegalEntity reciver = getLegalEntity(currSplit.getReceiverID());
	         value = reciver.getName();
	         break;
	     case 8:
		    	
	         value =currSplit.getReceiverContactType();
	         break;
	     case 9:
		    	
	         value =currSplit.getFormatType();
	         break;
	     case 10:
		    	
	         value =currSplit.getGateWay();
	         break;
	     case 11:
		    	
	         value =currSplit.getTemplateName();
	         break;
	    
		 }
	     return value;
	 }   
	   
	 public LegalEntity getLegalEntity(int id) {
		return legalentitys.get(id);
	 }
	 public boolean isCellEditable(int row, int col) {   
	 return false;   
	 }   
	 public void setValueAt(Object value, int row, int col) {   
	         System.out.println("Setting value at " + row + "," + col   
	                            + " to " + value   
	                            + " (an instance of "    
	                            + value.getClass() + ")");  
	         if(value instanceof MessageConfig) {
	     data.set(row,(MessageConfig) value) ;
	     this.fireTableDataChanged();   
	         System.out.println("New value of data:");   
	         }
	        
	 }   
	    
	 public void addRow(Object value) {   
	    
		 data.add((MessageConfig) value) ;
	 this.fireTableDataChanged();   
	   
	 }   
	    
	 public void delRow(int row) {   
	    if(row != -1) {
	 data.remove(row);          
	 this.fireTableDataChanged();   
	    }
	    
	 }   
	 

public void processlistchoice(DefaultListModel list,String name ) {
	Vector vector;
	try {
		vector = (Vector) referenceData.getStartUPData(name);
		
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

	 public void udpateValueAt(Object value, int row, int col) {   
	     
	  
	     data.set(row,(MessageConfig) value) ;
	     for(int i=0;i<columnNames.length;i++)
	    	 	fireTableCellUpdated(row, i);   
	    
	}   
	    
	   
	    
	    public void removeALL() {
	    	if(data != null) {
	  	  data.removeAllElements();
	    	} 
	    data = null;
	  	 this.fireTableDataChanged();  
	    }
}
}

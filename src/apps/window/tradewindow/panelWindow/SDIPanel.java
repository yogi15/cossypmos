package apps.window.tradewindow.panelWindow;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.AbstractTableModel;

import org.dyno.visual.swing.layouts.Bilateral;
import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;

import swingUtils.TableColumnAdjuster;
import util.commonUTIL;
import apps.window.referencewindow.JFrameReferenceWindow;
import apps.window.tradewindow.BackOfficePanel;
import beans.Book;
import beans.LegalEntity;
import beans.Sdi;
import beans.StartUPData;
import beans.Task;
import beans.Trade;
import beans.TransferRule;
import bo.transfer.rule.ProductTransferRule;
import bo.util.SDISelectorUtil;
import constants.SDIConstants;
import dsServices.RemoteReferenceData;
import dsServices.ServerConnectionUtil;

//VS4E -- DO NOT REMOVE THIS LINE!
public class SDIPanel extends BackOfficePanel {

	private static final long serialVersionUID = 1L;
	private JTable jTable0;
	private JScrollPane jScrollPane0;
	private JLabel jLabel0;
	private JTextField jTextField0;
	private JTextField jTextField1;
	private JTextField jTextField2;
	private JLabel jLabel3;
	private JTextField jTextField3;
	private JLabel jLabel1;
	private JLabel jLabel2;
	private JPanel jPanel0;
	private JLabel jLabel4;
	private JLabel jLabel5;
	private JLabel jLabel6;
	private JLabel jLabel7;
	private JTextField jTextField4;
	private JTextField jTextField7;
	private JTextField jTextField6;
	private JTextField jTextField5;
	private JLabel jLabel8;
	private JLabel jLabel9;
	private JLabel jLabel10;
	private JLabel jLabel11;
	private JTextField jTextField8;
	private JTextField jTextField9;
	private JTextField jTextField10;
	private JTextField jTextField11;
	private JButton jButton0;
	Vector<Sdi> payerPreferredSDIs = null;
	Vector<Sdi> receiverPreferredSDIs = null;
	boolean customChangeApply = false;
	DefaultComboBoxModel<String> payerModel = new DefaultComboBoxModel<String>();
	DefaultComboBoxModel payerRolemodel =	new DefaultComboBoxModel<String>();
	DefaultComboBoxModel<String> receiverRolemodel =	new DefaultComboBoxModel<String>();
	DefaultComboBoxModel<String> receiverModel = new DefaultComboBoxModel<String>();
	String col[] = { "Pay/Rec", "TransferType        ", "Currency", "ProductType        ",
			"PayerName    ", "Payer Role", "Payer Agent    ", "Receiver     ",
			"ReceiverRole", "ReceiverAgent      ", "Method", "TradeID",
			"Security", "SettleDate       " };
	public SDIPanel() {
		initComponents();
		init();
	}
	public void clearALL() {
		jCheckBox0.setSelected(false);
		payerInstr.removeAllItems();
		payerModel.removeAllElements();
		receiverInstr.removeAllItems();
		receiverModel.removeAllElements();
		customChangeApply = false;
		jTable0.removeAll();
		payerPreferredSDIs = null;
		receiverPreferredSDIs = null;
		originalRules = null;
		rules = null;
		
	}

	private void initComponents() {
		setFocusable(true);
		setEnabled(true);
		setVisible(true);
		setVerifyInputWhenFocusTarget(true);
		setDoubleBuffered(true);
		setRequestFocusEnabled(true);
		setOpaque(true);
		setLayout(new GroupLayout());
		add(getJPanel1(), new Constraints(new Bilateral(8, 10, 1220), new Leading(7, 257, 10, 10)));
		add(getJPanel0(), new Constraints(new Bilateral(8, 9, 0), new Bilateral(276, 12, 229)));
		setSize(1234, 567);
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
			}
		});
	}

	private JLabel getJLabel19() {
		if (jLabel19 == null) {
			jLabel19 = new JLabel();
			jLabel19.setText("Role");
		}
		return jLabel19;
	}

	private JLabel getJLabel18() {
		if (jLabel18 == null) {
			jLabel18 = new JLabel();
			jLabel18.setText("Role");
		}
		return jLabel18;
	}

	private JComboBox getPayerRole() {
		if (payerRole == null) {
			payerRole = new JComboBox();
			payerRole.addItemListener(new ItemListener() {
				
				@Override
				public void itemStateChanged(ItemEvent arg0) {
					// TODO Auto-generated method stub
					int r = receiverRole.getSelectedIndex() ;
					if(r!= -1) {
						String role = (String) payerRole.getSelectedItem();
						int leid = trade.getCpID();
					    if(role.equalsIgnoreCase(SDIConstants.PO)) {
					    	Book book =  getBook(trade.getBookId());
					    	leid = book.getLe_id();
					    }
						String payerKey =  role+"|"+trade.getCurrency()+"|"+trade.getProductType()+"|"+String.valueOf(leid);
						payerPreferredSDIs = SDISelectorUtil.getPreferredSdisOnKey(payerKey);
						payerInstr.removeAllItems();
						payerModel.removeAllElements();
						processComboxData(payerPreferredSDIs,payerModel);
					}
					
				}
			});			
		}
		return payerRole;
	}

	private JComboBox getReceiverRole() {
		if (receiverRole == null) {
			receiverRole = new JComboBox();
			receiverRole.addItemListener(new ItemListener() {
				
				@Override
				public void itemStateChanged(ItemEvent arg0) {
					// TODO Auto-generated method stub
					int r = receiverRole.getSelectedIndex() ;
					if(r!= -1) {
						String role = (String) receiverRole.getSelectedItem();
						int leid = trade.getCpID();
						    if(role.equalsIgnoreCase(SDIConstants.PO)) {
						    	Book book =  getBook(trade.getBookId());
						    	leid = book.getLe_id();
						    }
								String receiverKey =  role+"|"+trade.getCurrency()+"|"+trade.getProductType()+"|"+String.valueOf(leid);
								receiverPreferredSDIs = SDISelectorUtil.getPreferredSdisOnKey(receiverKey);
								receiverInstr.removeAllItems();
								receiverModel.removeAllElements();
								processComboxData(receiverPreferredSDIs,receiverModel);
					}
					
				}
			});
			
		}
		return receiverRole;
	}
	public Book getBook(int id) {
		Book book = new Book(); 
		book.setBookno(id);
		try {
			book =  (Book)  referenceData.selectBook(book);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return book;
	}

	private JTextField getReceiverSDI() {
		if (receiverSDI == null) {
			receiverSDI = new JTextField();
			receiverSDI.setEditable(false);
		}
		return receiverSDI;
	}

	private JTextField getPayerSDI() {
		if (payerSDI == null) {
			payerSDI = new JTextField();
			payerSDI.setEditable(false);
		}
		return payerSDI;
	}

	private JLabel getJLabel17() {
		if (jLabel17 == null) {
			jLabel17 = new JLabel();
			jLabel17.setText("Receiver");
		}
		return jLabel17;
	}

	private JLabel getJLabel16() {
		if (jLabel16 == null) {
			jLabel16 = new JLabel();
			jLabel16.setText("Payer");
		}
		return jLabel16;
	}

	private JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new JButton();
			jButton3.setText("Apply");
			jButton3.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					customChangeApply = true;
					TransferRule rule1 = null;
					// TODO Auto-generated method stub
					int payerInt = payerInstr.getSelectedIndex();
					int receiverInt = receiverInstr.getSelectedIndex();
					Sdi  payersdi = null; 
					Sdi  recievers = null;
					
					
					TransferRule rule = rules.get(jTable0.getSelectedRow());
					
					try {
						rule1 = (TransferRule) rule.clone();
						payersdi = (Sdi) payerPreferredSDIs.get(payerInt).clone();
						recievers =  (Sdi)  receiverPreferredSDIs.get(receiverInt).clone();
						//System.out.println("PPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPP"+originalRules.get(1).get_payerAgentID() + " "+ rule1.get_payerAgentID() + " " + rule.get_payerAgentID());
						originalRules.set(jTable0.getSelectedRow(), rule1);
					} catch (CloneNotSupportedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					rule.set_payerSDId(payersdi.getId());
					rule.set_receiverSDId(recievers.getId());
					rule.set_payerAgentID(payersdi.getAgentId());
					rule.set_receiverAgentID(payersdi.getAgentId());
					// System.out.println("PPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPP"+originalRules.get(1).get_payerAgentID() + " "+ rule1.get_payerAgentID() + " " + rule.get_payerAgentID());
					String payerKey =  rule.get_payerLegalEntityRole()+"|"+trade.getCurrency()+"|"+trade.getProductType()+"|"+String.valueOf(payersdi.getCpId());
					String receiverKey =  rule.get_receiverLegalEntityRole()+"|"+trade.getCurrency()+"|"+trade.getProductType()+"|"+String.valueOf(recievers.getCpId());
					payersdi.setkey(payerKey);
					recievers.setkey(receiverKey);
					SDISelectorUtil.updateSDIPreferredKeys(payersdi,referenceData,payerKey);
					SDISelectorUtil.updateSDIPreferredKeys(recievers,referenceData,receiverKey);
					rule.set_payerAgentID(payersdi.getAgentId());
					rule.set_receiverAgentID(recievers.getAgentId());
					
					tmodel.udpateValueAt(rule, jTable0.getSelectedRow(), 0);
					// System.out.println(originalRules.get(1).get_payerAgentID());
					
				}
			});
		}
		return jButton3;
	}

	private JCheckBox getJCheckBox0() {
		if (jCheckBox0 == null) {
			jCheckBox0 = new JCheckBox();
			jCheckBox0.setText("Custom Transfer Rule");
			jCheckBox0.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub

					if(jCheckBox0.isSelected()) {
						int selectRow = jTable0.getSelectedRow();
						if(selectRow == -1) {
							commonUTIL.showAlertMessage("Select TransferRule ");
							jCheckBox0.setSelected(false);
							return;
						} 
						
					}
					if(customChangeApply) {
				 	rules.clear();
				 	commonUTIL.showAlertMessage(" Setting to Default ");
					
						tmodel = new TableModelUtil(originalRules, col);
						rules = originalRules;
						jTable0.setModel(tmodel);
						/*jTable0.getColumnModel().getColumn(0).setPreferredWidth(150); 
						 jTable0.getColumnModel().getColumn(1).setPreferredWidth(150); 
						 jTable0.getColumnModel().getColumn(2).setPreferredWidth(250); 
						 jTable0.getColumnModel().getColumn(3).setPreferredWidth(250); 
						 jTable0.getColumnModel().getColumn(4).setPreferredWidth(250); 
						 jTable0.getColumnModel().getColumn(5).setPreferredWidth(280); 
						 jTable0.getColumnModel().getColumn(6).setPreferredWidth(230); 
						 jTable0.getColumnModel().getColumn(7).setPreferredWidth(230); 
						 jTable0.getColumnModel().getColumn(8).setPreferredWidth(230); 
						 jTable0.getColumnModel().getColumn(9).setPreferredWidth(200); 
						 jTable0.getColumnModel().getColumn(10).setPreferredWidth(240); 
						 jTable0.getColumnModel().getColumn(11).setPreferredWidth(200); 
						 jTable0.getColumnModel().getColumn(12).setPreferredWidth(200); 
						 jTable0.getColumnModel().getColumn(13).setPreferredWidth(190); */
						 
						 tca.adjustColumns();
						 customChangeApply = false;
						
						
					}
					
				}
			});
		}
		return jCheckBox0;
	}

	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			jPanel2.setLayout(new GroupLayout());
			jPanel2.add(getReceiverInstr(), new Constraints(new Leading(63, 212, 12, 12), new Leading(130, 27, 10, 10)));
			jPanel2.add(getJButton2(), new Constraints(new Leading(279, 24, 12, 12), new Leading(130, 27, 10, 10)));
			jPanel2.add(getPayerInstr(), new Constraints(new Leading(65, 212, 12, 12), new Leading(44, 27, 10, 10)));
			jPanel2.add(getJButton1(), new Constraints(new Leading(283, 24, 12, 12), new Leading(44, 27, 12, 12)));
			jPanel2.add(getJLabel14(), new Constraints(new Leading(6, 65, 12, 12), new Leading(52, 10, 10)));
			jPanel2.add(getJLabel15(), new Constraints(new Leading(6, 12, 12), new Leading(140, 12, 12)));
			jPanel2.add(getJLabel16(), new Constraints(new Leading(8, 65, 10, 10), new Leading(17, 12, 12)));
			jPanel2.add(getJLabel17(), new Constraints(new Leading(4, 65, 10, 10), new Leading(104, 12, 12)));
			jPanel2.add(getJCheckBox0(), new Constraints(new Leading(65, 139, 10, 10), new Leading(163, 10, 10)));
			jPanel2.add(getPayerSDI(), new Constraints(new Leading(65, 210, 12, 12), new Leading(12, 22, 12, 12)));
			jPanel2.add(getReceiverSDI(), new Constraints(new Leading(65, 210, 12, 12), new Leading(96, 22, 12, 12)));
			jPanel2.add(getJButton3(), new Constraints(new Leading(426, 12, 12), new Leading(160, 10, 10)));
			jPanel2.add(getPayerRole(), new Constraints(new Leading(351, 141, 10, 10), new Leading(12, 27, 12, 12)));
			jPanel2.add(getJComboBox1(), new Constraints(new Leading(351, 141, 10, 10), new Leading(44, 27, 12, 12)));
			jPanel2.add(getReceiverRole(), new Constraints(new Leading(351, 141, 12, 12), new Leading(96, 27, 12, 12)));
			jPanel2.add(getJComboBox3(), new Constraints(new Leading(352, 140, 12, 12), new Leading(130, 27, 10, 10)));
			jPanel2.add(getJLabel18(), new Constraints(new Leading(312, 12, 12), new Leading(18, 12, 12)));
			jPanel2.add(getJLabel19(), new Constraints(new Leading(312, 21, 12, 12), new Leading(100, 12, 12)));
		}
		return jPanel2;
	}

	private JComboBox getJComboBox3() {
		if (jComboBox3 == null) {
			jComboBox3 = new JComboBox();
			jComboBox3.setModel(new DefaultComboBoxModel(new Object[] { "Default","Assign" }));
			
		}
		return jComboBox3;
	}

	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setText("jButton2");
			jButton2.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					JFrameReferenceWindow ref = new JFrameReferenceWindow("SDI");
					ref.setVisible(true);
					
				}
			});
		}
		return jButton2;
	}

	private JComboBox getReceiverInstr() {
		if (receiverInstr == null) {
			receiverInstr = new JComboBox();
		//	receiverInstr.setModel(new DefaultComboBoxModel(new Object[] { "item0", "item1", "item2", "item3" }));
		}
		return receiverInstr;
	}

	private JComboBox getJComboBox1() {
		if (jComboBox1 == null) {
			jComboBox1 = new JComboBox();
			jComboBox1.setModel(new DefaultComboBoxModel(new Object[] { "Default", "Assign"}));
		}
		return jComboBox1;
	}

	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("SDI1");
            jButton1.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					JFrameReferenceWindow ref = new JFrameReferenceWindow("SDI");
					ref.setVisible(true);
					
				}
			});
		}
		return jButton1;
	}

	private JComboBox getPayerInstr() {
		if (payerInstr == null) {
			payerInstr = new JComboBox();
			//payerInstr.setModel(new DefaultComboBoxModel(new Object[] { "Default","Assign" }));
		}
		return payerInstr;
	}

	private JLabel getJLabel15() {
		if (jLabel15 == null) {
			jLabel15 = new JLabel();
			jLabel15.setText("Rec Inst");
		}
		return jLabel15;
	}

	private JLabel getJLabel14() {
		if (jLabel14 == null) {
			jLabel14 = new JLabel();
			jLabel14.setText("Payer Inst");
		}
		return jLabel14;
	}

	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setBorder(new LineBorder(Color.black, 1, false));
			jPanel1.setLayout(new GroupLayout());
			jPanel1.add(getJScrollPane0(), new Constraints(new Leading(7, 1201, 10, 10), new Leading(7, 232, 10, 10)));
		}
		return jPanel1;
	}

	private JTextField getJTextField13() {
		if (jTextField13 == null) {
			jTextField13 = new JTextField();
			// /jTextField13.setText("jTextField13");
		}
		return jTextField13;
	}

	private JLabel getJLabel13() {
		if (jLabel13 == null) {
			jLabel13 = new JLabel();
			jLabel13.setText("SettlementDate");
		}
		return jLabel13;
	}

	private JTextField getJTextField12() {
		if (jTextField12 == null) {
			jTextField12 = new JTextField();
			// jTextField12.setText("jTextField12");
		}
		return jTextField12;
	}

	private JLabel getJLabel12() {
		if (jLabel12 == null) {
			jLabel12 = new JLabel();
			jLabel12.setText("Security");
		}
		return jLabel12;
	}
	private Color getColors() {
		// TODO Auto-generated method stub
		return new Color(232, 230, 215);
	}
	private JTable getJTable0() {
		if (jTable0 == null) {
			jTable0 = new JTable();
		//	jTable0.setAutoCreateRowSorter(false);
			jTable0.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	//.set
			
			tmodel = new TableModelUtil(rules,col);
			jTable0.setModel(tmodel);
			 /*jTable0.getColumnModel().getColumn(0).setPreferredWidth(250); 
			 jTable0.getColumnModel().getColumn(1).setPreferredWidth(350); 
			 jTable0.getColumnModel().getColumn(2).setPreferredWidth(350); 
			 jTable0.getColumnModel().getColumn(3).setPreferredWidth(550); 
			 jTable0.getColumnModel().getColumn(4).setPreferredWidth(250); 
			 jTable0.getColumnModel().getColumn(5).setPreferredWidth(580); 
			 jTable0.getColumnModel().getColumn(6).setPreferredWidth(230); 
			 jTable0.getColumnModel().getColumn(7).setPreferredWidth(230); 
			 jTable0.getColumnModel().getColumn(8).setPreferredWidth(530); 
			 jTable0.getColumnModel().getColumn(9).setPreferredWidth(500); 
			 jTable0.getColumnModel().getColumn(10).setPreferredWidth(140); 
			 jTable0.getColumnModel().getColumn(11).setPreferredWidth(500); 
			 jTable0.getColumnModel().getColumn(12).setPreferredWidth(500); 
			 jTable0.getColumnModel().getColumn(13).setPreferredWidth(190); */
		
			tca = new TableColumnAdjuster(jTable0);
			tca.adjustColumns();
			
		}
		jTable0.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				int selectRow = jTable0.getSelectedRow();
				if(selectRow == -1)
					return;
				jTextField1.setText((String) jTable0.getValueAt(selectRow, 0));
				jTextField4.setText((String) jTable0.getValueAt(selectRow, 1));
				jTextField8.setText((String) jTable0.getValueAt(selectRow, 2));
				jTextField0.setText((String) jTable0.getValueAt(selectRow, 3));
				jTextField5.setText((String) jTable0.getValueAt(selectRow, 4));
				jTextField9.setText((String) jTable0.getValueAt(selectRow, 5));
				jTextField2.setText((String) jTable0.getValueAt(selectRow, 6));
				jTextField7.setText((String) jTable0.getValueAt(selectRow, 7));
				jTextField10.setText((String) jTable0.getValueAt(selectRow, 8));
				jTextField3.setText((String) jTable0.getValueAt(selectRow, 9));
				jTextField6.setText((String) jTable0.getValueAt(selectRow, 10));
				jTextField12.setText((String) jTable0.getValueAt(selectRow, 12));
				jTextField13.setText((String) jTable0.getValueAt(selectRow, 13));
				jTextField11.setText(new Integer((Integer) jTable0.getValueAt(
						selectRow, 11)).toString());
				if(jCheckBox0.isSelected()) {
				if(selectRow == -1)
					return;
				TransferRule rule = rules.get(selectRow);
				receiverSDI.setText(getLEName(rule.get_receiverLegalEntityId()));
				payerSDI.setText(getLEName(rule.get_payerLegalEntityId()));
				String payKey = rule.get_payerLegalEntityRole()+"|"+rule.get_settlementCurrency()+"|"+rule.get_productType()+"|"+String.valueOf(rule.get_payerLegalEntityId());
				String recKey = rule.get_receiverLegalEntityRole()+"|"+rule.get_settlementCurrency()+"|"+rule.get_productType()+"|"+String.valueOf(rule.get_receiverLegalEntityId());
				payerPreferredSDIs	 = SDISelectorUtil.getPreferredSdisOnKey(payKey);
			    receiverPreferredSDIs = SDISelectorUtil.getPreferredSdisOnKey(recKey);
			  //   payerInstr.removeAllItems();
			//     receiverInstr.removeAllItems();
			     payerModel.removeAllElements();
			     receiverModel.removeAllElements();
			     
			     processComboxData(payerPreferredSDIs,payerModel);
			     processComboxData(receiverPreferredSDIs,receiverModel);
			     payerInstr.setModel(payerModel);
			     receiverInstr.setModel(receiverModel);
			     payerRole.setSelectedItem(rule.get_payerLegalEntityRole());
			     receiverRole.setSelectedItem(rule.get_receiverLegalEntityRole());
			    
				}
			     
				//processpayerCo

			}

		});
		return jTable0;
	}

	private JButton getJButton0() {
		if (jButton0 == null) {
			jButton0 = new JButton();
			jButton0.setText("Route");
		}
		return jButton0;
	}

	private JTextField getJTextField11() {
		if (jTextField11 == null) {
			jTextField11 = new JTextField();
			// jTextField11.setText("jTextField11");
		}
		return jTextField11;
	}

	private JTextField getJTextField10() {
		if (jTextField10 == null) {
			jTextField10 = new JTextField();
			// jTextField10.setText("jTextField10");
		}
		return jTextField10;
	}

	private JTextField getJTextField9() {
		if (jTextField9 == null) {
			jTextField9 = new JTextField();
			// jTextField9.setText("jTextField9");
		}
		return jTextField9;
	}

	private JTextField getJTextField8() {
		if (jTextField8 == null) {
			jTextField8 = new JTextField();
			// jTextField8.setText("jTextField8");
		}
		return jTextField8;
	}

	private JLabel getJLabel11() {
		if (jLabel11 == null) {
			jLabel11 = new JLabel();
			jLabel11.setText("TradeID");
		}
		return jLabel11;
	}

	private JLabel getJLabel10() {
		if (jLabel10 == null) {
			jLabel10 = new JLabel();
			jLabel10.setText("ReceiverRole");
		}
		return jLabel10;
	}

	private JLabel getJLabel9() {
		if (jLabel9 == null) {
			jLabel9 = new JLabel();
			jLabel9.setText("PayerRole");
		}
		return jLabel9;
	}

	private JLabel getJLabel8() {
		if (jLabel8 == null) {
			jLabel8 = new JLabel();
			jLabel8.setText("Currency");
		}
		return jLabel8;
	}

	private JTextField getJTextField5() {
		if (jTextField5 == null) {
			jTextField5 = new JTextField();
			// jTextField5.setText("jTextField5");
		}
		return jTextField5;
	}

	private JTextField getJTextField6() {
		if (jTextField6 == null) {
			jTextField6 = new JTextField();
			// jTextField6.setText("jTextField6");
		}
		return jTextField6;
	}

	private JTextField getJTextField7() {
		if (jTextField7 == null) {
			jTextField7 = new JTextField();
			// jTextField7.setText("jTextField7");
		}
		return jTextField7;
	}

	private JTextField getJTextField4() {
		if (jTextField4 == null) {
			jTextField4 = new JTextField();
			// jTextField4.setText("jTextField4");
		}
		return jTextField4;
	}

	private JLabel getJLabel7() {
		if (jLabel7 == null) {
			jLabel7 = new JLabel();
			jLabel7.setText("Method");
		}
		return jLabel7;
	}

	private JLabel getJLabel6() {
		if (jLabel6 == null) {
			jLabel6 = new JLabel();
			jLabel6.setText("Receiver");
		}
		return jLabel6;
	}

	private JLabel getJLabel5() {
		if (jLabel5 == null) {
			jLabel5 = new JLabel();
			jLabel5.setText("PayerName");
		}
		return jLabel5;
	}

	private JLabel getJLabel4() {
		if (jLabel4 == null) {
			jLabel4 = new JLabel();
			jLabel4.setText("TransferType");
		}
		return jLabel4;
	}

	private JPanel getJPanel0() {
		if (jPanel0 == null) {
			jPanel0 = new JPanel();
			jPanel0.setBorder(new LineBorder(Color.black, 1, false));
			jPanel0.setLayout(new GroupLayout());
			jPanel0.add(getJLabel0(), new Constraints(new Leading(9, 10, 10), new Leading(16, 10, 10)));
			jPanel0.add(getJTextField1(), new Constraints(new Leading(101, 71, 12, 12), new Leading(14, 12, 12)));
			jPanel0.add(getJLabel4(), new Constraints(new Leading(181, 93, 10, 10), new Leading(16, 12, 12)));
			jPanel0.add(getJTextField4(), new Constraints(new Leading(276, 137, 10, 10), new Leading(14, 12, 12)));
			jPanel0.add(getJLabel8(), new Constraints(new Leading(435, 93, 10, 10), new Leading(14, 12, 12)));
			jPanel0.add(getJLabel9(), new Constraints(new Leading(435, 93, 12, 12), new Leading(40, 12, 12)));
			jPanel0.add(getJLabel10(), new Constraints(new Leading(435, 93, 12, 12), new Leading(68, 12, 12)));
			jPanel0.add(getJLabel11(), new Constraints(new Leading(435, 93, 12, 12), new Leading(96, 12, 12)));
			jPanel0.add(getJLabel2(), new Constraints(new Leading(7, 78, 12, 12), new Leading(72, 12, 12)));
			jPanel0.add(getJLabel13(), new Constraints(new Leading(435, 93, 10, 10), new Leading(122, 12, 12)));
			jPanel0.add(getJTextField13(), new Constraints(new Leading(540, 137, 10, 10), new Leading(122, 12, 12)));
			jPanel0.add(getJTextField8(), new Constraints(new Leading(540, 137, 12, 12), new Leading(14, 12, 12)));
			jPanel0.add(getJTextField9(), new Constraints(new Leading(540, 137, 12, 12), new Leading(38, 12, 12)));
			jPanel0.add(getJTextField10(), new Constraints(new Leading(540, 137, 12, 12), new Leading(66, 12, 12)));
			jPanel0.add(getJTextField11(), new Constraints(new Leading(540, 137, 12, 12), new Leading(94, 12, 12)));
			jPanel0.add(getJPanel2(), new Constraints(new Leading(695, 513, 10, 10), new Leading(8, 209, 10, 10)));
			jPanel0.add(getJButton0(), new Constraints(new Leading(7, 12, 12), new Leading(209, 10, 10)));
			jPanel0.add(getJLabel12(), new Constraints(new Leading(9, 74, 12, 12), new Leading(177, 10, 10)));
			jPanel0.add(getJTextField12(), new Constraints(new Leading(101, 320, 12, 12), new Leading(174, 12, 12)));
			jPanel0.add(getJLabel6(), new Constraints(new Leading(7, 64, 12, 12), new Leading(106, 12, 12)));
			jPanel0.add(getJTextField7(), new Constraints(new Leading(101, 235, 12, 12), new Leading(102, 12, 12)));
			jPanel0.add(getJLabel3(), new Constraints(new Leading(9, 12, 12), new Leading(132, 10, 10)));
			jPanel0.add(getJTextField6(), new Constraints(new Leading(538, 137, 10, 10), new Leading(152, 12, 12)));
			jPanel0.add(getJTextField3(), new Constraints(new Leading(101, 235, 12, 12), new Leading(130, 12, 12)));
			jPanel0.add(getJLabel7(), new Constraints(new Leading(435, 81, 12, 12), new Leading(158, 13, 12, 12)));
			jPanel0.add(getJTextField2(), new Constraints(new Leading(101, 235, 12, 12), new Leading(72, 12, 12)));
			jPanel0.add(getJLabel5(), new Constraints(new Leading(9, 93, 12, 12), new Leading(45, 12, 12)));
			jPanel0.add(getJTextField5(), new Constraints(new Leading(101, 234, 12, 12), new Leading(44, 12, 12)));
			jPanel0.add(getJTextField0(), new Constraints(new Leading(546, 129, 12, 12), new Leading(186, 12, 12)));
			jPanel0.add(getJLabel1(), new Constraints(new Leading(438, 74, 12, 12), new Leading(189, 20, 12, 12)));
		}
		return jPanel0;
	}
	private JLabel getJLabel2() {
		if (jLabel2 == null) {
			jLabel2 = new JLabel();
			jLabel2.setText("PayerAgent");
		}
		return jLabel2;
	}

	private JLabel getJLabel1() {
		if (jLabel1 == null) {
			jLabel1 = new JLabel();
			jLabel1.setText("ProductType");
		}
		return jLabel1;
	}

	private JTextField getJTextField3() {
		if (jTextField3 == null) {
			jTextField3 = new JTextField();
			// jTextField3.setText("jTextField3");
		}
		return jTextField3;
	}

	private JLabel getJLabel3() {
		if (jLabel3 == null) {
			jLabel3 = new JLabel();
			jLabel3.setText("Receiver Agent");
		}
		return jLabel3;
	}
	private JTextField getJTextField2() {
		if (jTextField2 == null) {
			jTextField2 = new JTextField();
			// jTextField2.setText("jTextField2");
		}
		return jTextField2;
	}

	private JTextField getJTextField1() {
		if (jTextField1 == null) {
			jTextField1 = new JTextField();
			// jTextField1.setText("jTextField1");
		}
		return jTextField1;
	}

	private JTextField getJTextField0() {
		if (jTextField0 == null) {
			jTextField0 = new JTextField();
			// jTextField0.setText("jTextField0");
		}
		return jTextField0;
	}

	private JLabel getJLabel0() {
		if (jLabel0 == null) {
			jLabel0 = new JLabel();
			jLabel0.setText("PayRec");
		}
		return jLabel0;
	}

	private JScrollPane getJScrollPane0() {
		if (jScrollPane0 == null) {
			jScrollPane0 = new JScrollPane();
			int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS; 
			int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS;
      		
      		jScrollPane0 = new JScrollPane(getJTable0(),v,h);
      		jScrollPane0.setBackground(getColors());
		}
		return jScrollPane0;
	}

	@Override
	public void fillJTabel(Vector data) {
		if (data != null && (!data.isEmpty())) {
			jCheckBox0.setSelected(false);
			customChangeApply = false;

			Hashtable<String, TransferRule> checkDuplicate = new Hashtable<String, TransferRule>();
			setSdis(data);
			if(trade == null)
				return;
			if(commonUTIL.isEmpty(data))
				return;
			if(trade == null)
				return;
			rules = rule.generateRules(trade);

			Iterator it = rules.iterator();
			int r = tmodel.getRowCount();
			
			jTable0.repaint();
			tca.adjustColumns();
			int i = 0;
			while (it.hasNext()) {
				TransferRule trule = (TransferRule) it.next();
				Sdi sdi = trule.get__sMethod();
				String messageType = sdi.getMessageType();
				String Format = sdi.getsdiformat();
				String agentName = getLEName(sdi.getAgentId());
				int account = sdi.getAccountID();
				String key = trule.get_payReceive() + trule.get_transferType()
						+ trule.get_settlementCurrency()
						+ trule.get_productType()
						+ trule.get_payerLegalEntityId()
						+ trule.get_payerLegalEntityRole() + agentName
						+ trule.get_receiverLegalEntityId()
						+ trule.get_receiverLegalEntityRole() + sdi.getkey();
				if (!checkDuplicate.containsKey(key)) {
					checkDuplicate.put(key, trule);
					
					i++;
				}

			}
		} else {
			commonUTIL.showAlertMessage(" SDI missing for " + trade.getId());
		}

	}

	public void setSdis(Vector sdis) {
		this.sdis = sdis;
		
	}

	private DefaultComboBoxModel processComboxData(Vector<Sdi> preferredSDis,DefaultComboBoxModel model) {
      //  model.removeAllElements();
		
		if(preferredSDis != null) {
			for(int i=0;i<preferredSDis.size();i++) {
				Sdi sdi = preferredSDis.get(i);
				String key = "";
				/*
				if(commonUTIL.isEmpty(sdi.getGlName()))
					key = sdi.getCurrency()+"/"+sdi.getProducts();
				else
				 key = sdi.getCurrency()+"/"+sdi.getGlName()+"/"+sdi.getProducts();
				*/
				key = getLEName(sdi.getAgentId()) + "/"+sdi.getGlName();
				
				model.addElement(key);
				/*if (!sdi.getkey().equals("")) {
					model.setSelectedItem(key);
				}*/
			}
		}
		return model;
	}
	public static ServerConnectionUtil de = null;
//	DefaultTableModel tmodel;
	RemoteReferenceData referenceData;

	public ProductTransferRule getRule() {
		return rule;
	}

	public void setRule(ProductTransferRule rule) {
		this.rule = rule;
	}

	ProductTransferRule rule;
	Trade trade = null;
	Vector<TransferRule> rules = null;
	Vector<TransferRule> originalRules = null;//new Vector<TransferRule>();
	/**
	 * @return the originalRules
	 */
	

	
	Vector<Sdi> sdis = new Vector<Sdi>();
	private JLabel jLabel12;
	private JTextField jTextField12;
	private JLabel jLabel13;
	private JTextField jTextField13;
	private JPanel jPanel1;
	private JLabel jLabel14;
	private JLabel jLabel15;
	private JComboBox payerInstr;
	private JButton jButton1;
	private JComboBox jComboBox1;
	private JComboBox receiverInstr;
	private JButton jButton2;
	private JComboBox jComboBox3;
	private JPanel jPanel2;
	private JCheckBox jCheckBox0;
	private JButton jButton3;
	private JLabel jLabel16;
	private JLabel jLabel17;
	private JTextField payerSDI;
	private JTextField receiverSDI;
	TableModelUtil tmodel = null;
	private JComboBox receiverRole;
	private JComboBox payerRole;
	private JLabel jLabel18;
	private JLabel jLabel19;
	private  TableColumnAdjuster tca = null;
	private static final String PREFERRED_LOOK_AND_FEEL = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
	public Vector getSdis() {
		return sdis;
	}

	public Trade getTrade() {
		return trade;
	}

	public void setTrade(Trade trade) {
		this.trade = trade;
		rules = rule.generateRules(trade);
		
		if(rules == null)
			return;
		tmodel = new TableModelUtil(rules,col);
		originalRules = new Vector(rules);
		// System.out.println("PPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPP"+originalRules.get(1).get_payerAgentID());
		jTable0.setModel(tmodel);
		/*jTable0.getColumnModel().getColumn(0).setPreferredWidth(150); 
		 jTable0.getColumnModel().getColumn(1).setPreferredWidth(150); 
		 jTable0.getColumnModel().getColumn(2).setPreferredWidth(250); 
		 jTable0.getColumnModel().getColumn(3).setPreferredWidth(250); 
		 jTable0.getColumnModel().getColumn(4).setPreferredWidth(250); 
		 jTable0.getColumnModel().getColumn(5).setPreferredWidth(280); 
		 jTable0.getColumnModel().getColumn(6).setPreferredWidth(230); 
		 jTable0.getColumnModel().getColumn(7).setPreferredWidth(230); 
		 jTable0.getColumnModel().getColumn(8).setPreferredWidth(230); 
		 jTable0.getColumnModel().getColumn(9).setPreferredWidth(200); 
		 jTable0.getColumnModel().getColumn(10).setPreferredWidth(240); 
		 jTable0.getColumnModel().getColumn(11).setPreferredWidth(200); 
		 jTable0.getColumnModel().getColumn(12).setPreferredWidth(200); 
		 jTable0.getColumnModel().getColumn(13).setPreferredWidth(190); */
		 //tca = new TableColumnAdjuster(jTable0);
		 tca.adjustColumns();
		 
		 TransferRule rule = rules.get(0); // for first time it will be first record only. 
			receiverSDI.setText(getLEName(rule.get_receiverLegalEntityId()));
			payerSDI.setText(getLEName(rule.get_payerLegalEntityId()));
			String payKey = rule.get_payerLegalEntityRole()+"|"+rule.get_settlementCurrency()+"|"+rule.get_productType()+"|"+String.valueOf(rule.get_payerLegalEntityId());
			String recKey = rule.get_receiverLegalEntityRole()+"|"+rule.get_settlementCurrency()+"|"+rule.get_productType()+"|"+String.valueOf(rule.get_receiverLegalEntityId());
			payerPreferredSDIs	 = SDISelectorUtil.getPreferredSdisOnKey(payKey);
		    receiverPreferredSDIs = SDISelectorUtil.getPreferredSdisOnKey(recKey);
		      payerInstr.removeAllItems();
		      payerModel.removeAllElements();
		      receiverInstr.removeAllItems();
		      receiverModel.removeAllElements();
		     processComboxData(payerPreferredSDIs,payerModel);
		     processComboxData(receiverPreferredSDIs,receiverModel);
		     payerInstr.setModel(payerModel);
		     receiverInstr.setModel(receiverModel);
		     processStartUpData(receiverRolemodel);
		     processStartUpData(payerRolemodel);
		     payerRole.setModel(payerRolemodel);
		     receiverRole.setModel(receiverRolemodel);
        }
	

	public String getProductName(int id) {
		String productName = "";
		if (id > 0) {
			productName = trade.getTradedesc();
		}
		return productName;
	}

	public boolean checkAlreadyExists(Task task) {
		boolean flag = true;
		for (int i = 1; i < tmodel.getRowCount(); i++) {
			String s = (String) tmodel.getValueAt(i, 0);
			int ss = new Integer(s).intValue();
			if (task.getId() == ss) {

				flag = false;
			}

		}
		return flag;
	}

	private Sdi getSdi(int sdid) {
		Sdi sd = null;
		if ((sdis != null) && (!sdis.isEmpty())) {
			for (int i = 0; i < sdis.size(); i++) {
				Sdi s = (Sdi) sdis.elementAt(i);
				if (s.getId() == sdid) {
					sd = s;
					break;
				}
			}

		}
		return sd;
	}

	public String getLEName(int id) {
		String name = "";
		try {
			if (id == 0)
				return name;
			LegalEntity le = (LegalEntity) referenceData.selectLE(id);
			name = le.getName();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return name;
	}
	public String getLERole(int id) {
		String name = "";
		try {
			if (id == 0)
				return name;
			LegalEntity le = (LegalEntity) referenceData.selectLE(id);
			name = le.getRole();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return name;
	}
	public void processStartUpData(DefaultComboBoxModel list) {
		Vector<StartUPData> roles = null;
		try {
			roles = (Vector<StartUPData>) referenceData.getStartUPData("Roles");
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		if (roles.size() > 0) {
			Iterator it = roles.iterator();
			int i = 0;
			while (it.hasNext()) {

				StartUPData data = (StartUPData) it.next();

				list.addElement(data.getName());

				i++;
			}
		}
	}

	public void init() {
		de = ServerConnectionUtil.connect("localhost", 1099,
				commonUTIL.getServerIP());
		try {
			referenceData = (RemoteReferenceData) de
					.getRMIService("ReferenceData");

			jTable0.setModel(tmodel);
			tca.adjustColumns();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	class TableModelUtil extends AbstractTableModel {

		final String[] columnNames;

		Vector<TransferRule> data;
		RemoteReferenceData remoteRef;
		Hashtable<Integer, Book> books;

		public TableModelUtil(Vector<TransferRule> myData, String col[]) {
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

			TransferRule trule = (TransferRule) data.get(row);
			Sdi sdi = trule.get__sMethod();
				switch (col) {
			case 0:
				value = trule.get_payReceive();
				break;
			case 1:
				value = trule.get_transferType();
				break;
			case 2:
				value =trule.get_settlementCurrency();
				break;
			case 3:
				value = trule.get_productType();
				break;
			case 4:
				value = getLEName(trule.get_payerLegalEntityId());
				break;
			case 5:
				value = trule.get_payerLegalEntityRole();
				break;
			case 6:
				if(sdi != null) 
				value =getLEName(trule.get_payerAgentID());
				else 
					value = "";
				break;
			case 7:
				value = getLEName(trule.get_receiverLegalEntityId());
				break;
			case 8:
				value = trule.get_receiverLegalEntityRole();
				break;
			case 9:
				if(sdi != null) 
					value =getLEName(trule.get_receiverAgentID());
					else 
						value = "";
				break;
			case 10:
				if(sdi != null) 
					value =sdi.getMessageType();
					else 
						value = "";
				break;
			case 11:
				value = trade.getId();
				break;
			case 12:
				value =getProductName(trule.get_productId());
				break;
			case 13:
				value =commonUTIL.dateToString(trule.get_settleDate().getDate());
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
			if (value instanceof TransferRule) {
				data.set(row, (TransferRule) value);
				this.fireTableDataChanged();
				System.out.println("New value of data:");
			}

		}

		public void addRow(Object value) {

			data.add((TransferRule) value);
			this.fireTableDataChanged();

		}

		public void delRow(int row) {
			if (row != -1) {
				data.remove(row);
				this.fireTableDataChanged();
			}

		}

		public void udpateValueAt(Object value, int row, int col) {

			data.set(row, (TransferRule) value);
			for (int i = 0; i < columnNames.length; i++)
				fireTableCellUpdated(row, i);

		}

		public void removeALL() {
			if (data != null) {
				data.removeAllElements();
			}
			//data = null;
			this.fireTableDataChanged();
		}
	}

}

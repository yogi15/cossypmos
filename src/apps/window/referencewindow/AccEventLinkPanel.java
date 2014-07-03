package apps.window.referencewindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.Position;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;
import org.dyno.visual.swing.layouts.Trailing;

import util.commonUTIL;
import apps.window.utilwindow.JDialogTable;
import beans.AccConfigRule;
import beans.AccEventLink;
import beans.Account;
import beans.StartUPData;
import dsServices.RemoteAccount;
import dsServices.RemoteProduct;
import dsServices.RemoteReferenceData;
import dsServices.ServerConnectionUtil;

//VS4E -- DO NOT REMOVE THIS LINE!
public class AccEventLinkPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JPanel jPanel0;
	private JPanel jPanel1;
	private JLabel jLabel0;
	private JComboBox jComboBox0;
	private JRadioButton jRadioButton0;
	private JRadioButton jRadioButton1;
	private JTree jTree0;
	private JScrollPane jScrollPane0;
	private JButton jButton0;
	private JLabel jLabel1;
	private JTextField jTextField0;
	private JComboBox jComboBox1;
	private JTextField jTextField1;
	private JButton jButton1;
	private JLabel jLabel2;
	private JComboBox jComboBox2;
	private JButton jButton2;
	private JButton jButton3;
	private JButton jButton4;
	private JButton jButton9;
	private JButton jButton6;
	private JButton jButton7;
	private JButton jButton5;
	private JButton jButton8;
	Hashtable checkDuplicteNode = new Hashtable();
	String selectedKey = "";
	RemoteReferenceData remoteBORef; 
	RemoteAccount remoteAccount;
	RemoteProduct remoteProduct;
	AccEventLink  accEventLink ;
	Hashtable<Integer, Account> accounts = new Hashtable<Integer, Account>();
	  public static MutableTreeNode mNode;
	Account account1;
	Account account2;
	 DefaultMutableTreeNode _selectedName  = null;
	    DefaultMutableTreeNode _selectedValue = null;
	    boolean refereshTree = false;
	 javax.swing.DefaultComboBoxModel accType1 = new  javax.swing.DefaultComboBoxModel();
	 javax.swing.DefaultComboBoxModel accType2 = new  javax.swing.DefaultComboBoxModel();
	 javax.swing.DefaultComboBoxModel accEventModel = new  javax.swing.DefaultComboBoxModel();
	 String aCol [] = {"AccountID","AccountName"};
	 DefaultTableModel account1Model1 = new DefaultTableModel(aCol,0);
	 DefaultTableModel account1Model2 = new DefaultTableModel(aCol,0);
	 DefaultMutableTreeNode root =  new DefaultMutableTreeNode("AccEventLink");
	 DefaultMutableTreeNode parent, leaf;
	 DefaultTreeModel treeModel = new DefaultTreeModel(root);
	 AccConfigRule rule = null;
	private JTextField jTextField2;
	private JButton jButton10; 
	 
	public AccEventLinkPanel(ServerConnectionUtil de) {
		
 	initData(de);
		initComponents();
	}

	private void initComponents() {
		setLayout(new GroupLayout());
		add(getJButton2(), new Constraints(new Leading(306, 64, 10, 10), new Leading(281, 10, 10)));
		add(getJButton3(), new Constraints(new Leading(382, 84, 10, 10), new Leading(281, 12, 12)));
		add(getJButton4(), new Constraints(new Leading(475, 92, 10, 10), new Leading(281, 10, 10)));
		add(getJRadioButton1(), new Constraints(new Leading(518, 32, 10, 10), new Leading(64, 8, 8)));
		add(getJRadioButton0(), new Constraints(new Leading(468, 10, 10), new Leading(64, 8, 8)));
		add(getJLabel0(), new Constraints(new Leading(293, 92, 12, 12), new Leading(26, 22, 12, 12)));
		add(getJPanel0(), new Constraints(new Leading(18, 10, 10), new Leading(16, 375, 10, 10)));
		add(getJPanel1(), new Constraints(new Trailing(12, 668, 12, 12), new Leading(397, 54, 12, 12)));
		add(getJButton0(), new Constraints(new Leading(632, 44, 10, 10), new Leading(132, 10, 10)));
		add(getJLabel2(), new Constraints(new Leading(419, 35, 12, 12), new Leading(168, 10, 10)));
		add(getJLabel1(), new Constraints(new Leading(417, 35, 12, 12), new Leading(98, 12, 12)));
		add(getJComboBox0(), new Constraints(new Leading(414, 211, 10, 10), new Leading(26, 28, 12, 12)));
		add(getJComboBox1(), new Constraints(new Leading(297, 108, 12, 12), new Leading(132, 26, 12, 12)));
		add(getJTextField0(), new Constraints(new Leading(417, 212, 10, 10), new Leading(132, 26, 12, 12)));
		add(getJButton1(), new Constraints(new Leading(630, 44, 10, 10), new Leading(202, 12, 12)));
		add(getJTextField1(), new Constraints(new Leading(417, 204, 12, 12), new Leading(202, 26, 12, 12)));
		add(getJComboBox2(), new Constraints(new Leading(297, 108, 12, 12), new Leading(202, 26, 12, 12)));
		add(getJTextField2(), new Constraints(new Leading(453, 212, 10, 10), new Leading(338, 26, 10, 10)));
		add(getJButton10(), new Constraints(new Leading(304, 124, 10, 10), new Leading(340, 12, 12)));
		setSize(700, 471);
	}

	private JButton getJButton10() {
		if (jButton10 == null) {
			jButton10 = new JButton();
			jButton10.setText("Load Rule");
		}
		String s [] = {"ID","RuleName"};
		final	DefaultTableModel tablemodel = new DefaultTableModel(s,0) {
	   		 @Override
	   		    public boolean isCellEditable(int row, int column) {
	   		       //all cells false
	   		       return false;
	   		    }

	   	};
	   	final  JDialogTable showAllrules = new JDialogTable(tablemodel);
	   	showAllrules.setLocationRelativeTo(this);
	    	
	    		

	    		
	    		showAllrules.jTable1.addMouseListener(new java.awt.event.MouseAdapter() {

	    			@Override
	    			public void mouseClicked(MouseEvent e) {
	    				String ruleid = showAllrules.jTable1.getValueAt(showAllrules.jTable1.getSelectedRow(),0).toString();
	    				int id  =new  Integer(ruleid).intValue();
	    				try {
	    					
	    						 rule = (AccConfigRule) remoteAccount.getaccConfigRule(id);
	    						 jTextField2.setText(rule.getRuleName());
	    						 showAllrules.setVisible(false);
	    						 root = new DefaultMutableTreeNode(rule.getRuleName());
	    						 
	    						 Vector <AccEventLink>	accEventLink = (Vector) remoteAccount.getAccEventLinkonrule(rule.getId());
	    						 removeALL();
	    						 
	    						
	    						 if(accEventLink != null && (!accEventLink.isEmpty())) {
	    							 jTextField1.setText("");
		    						 jTextField1.setText("");
		    						 account1 = null;
		    						 account2 = null;
	    		    	            loadDataFromDB(accEventLink,root); 
	    						 }
	    						 treeModel = new DefaultTreeModel(root);
	    		    	  		 jTree0.setModel(treeModel);
	    		    	  		 _selectedName = root;
	    				} catch (RemoteException e1) {
	    					// TODO Auto-generated catch block
	    					e1.printStackTrace();
	    				
	    				}
	    			}

					private void loadDataFromDB( Vector <AccEventLink>	accEventLink,
							DefaultMutableTreeNode root) {
						// TODO Auto-generated method stub
						
						AccEventLink aEventLink = accEventLink.elementAt(0);
						DefaultMutableTreeNode accEvent = new DefaultMutableTreeNode(aEventLink.getAccEvent());
						int plusm =aEventLink.getPlusmius();
						String accEvt = aEventLink.getAccEvent();
						DefaultMutableTreeNode pulsminus = null;
						String key = "";
						String value = "";
						if(plusm == 0) 
							pulsminus = new DefaultMutableTreeNode("+"); 
						else 
							pulsminus = new DefaultMutableTreeNode("-"); 
						for(int i=0;i<accEventLink.size();i++) {
							AccEventLink aEvent = accEventLink.get(i);
							key = aEvent.getAccEvent()+"="+pulsminus.toString()+"=";//+accountDebit.toString()+"="+accountcredit.toString();
							if(accEvt.equalsIgnoreCase(aEvent.getAccEvent())) {
								
								if(aEvent.getPlusmius() == plusm) {
									DefaultMutableTreeNode debit = new DefaultMutableTreeNode("DEBIT("+ aEvent.getDebitaccType() +") " + getAccount(aEvent.getDebitaccount()).getAccountName());
									DefaultMutableTreeNode credit = new DefaultMutableTreeNode("CREDIT("+ aEvent.getCreaditaccType() +") " + getAccount(aEvent.getCreditaccount()).getAccountName());
									pulsminus.add(debit);
									pulsminus.add(credit);
									key = key + debit.toString()+"="+credit.toString();
									value = "debit="+aEvent.getDebitaccount()+":credit="+aEvent.getCreditaccount();
									checkDuplicteNode.put(key, value);
									//accEvent.add(pulsminus);
								}
								  
								if(aEvent.getPlusmius() != plusm) {
									accEvent.add(pulsminus);
									plusm = aEvent.getPlusmius();
									 pulsminus = null;
										if(plusm == 0) 
											pulsminus = new DefaultMutableTreeNode("+"); 
										else 
											pulsminus = new DefaultMutableTreeNode("-"); 
										
									DefaultMutableTreeNode debit = new DefaultMutableTreeNode("DEBIT("+ aEvent.getDebitaccType() +") " +  getAccount(aEvent.getDebitaccount()).getAccountName());
									DefaultMutableTreeNode credit = new DefaultMutableTreeNode("DEBIT("+ aEvent.getCreaditaccType() +") " +  getAccount(aEvent.getCreditaccount()).getAccountName());
									pulsminus.add(debit);
									pulsminus.add(credit);
									key = aEvent.getAccEvent()+"="+pulsminus.toString()+"=";
									key = key + debit.toString()+"="+credit.toString();
									value = "debit="+aEvent.getDebitaccount()+":credit="+aEvent.getCreditaccount();
									checkDuplicteNode.put(key, value);
									
								}
							} else { 
								accEvent.insert(pulsminus,accEvent.getChildCount());
								root.insert(accEvent, root.getChildCount());
								accEvt = aEvent.getAccEvent();
								accEvent = null;
								accEvent = new DefaultMutableTreeNode(aEvent.getAccEvent());
								plusm = aEvent.getPlusmius();
								 pulsminus = null;
									if(plusm == 0) 
										pulsminus = new DefaultMutableTreeNode("+"); 
									else 
										pulsminus = new DefaultMutableTreeNode("-"); 
								DefaultMutableTreeNode debit = new DefaultMutableTreeNode("DEBIT("+ aEvent.getDebitaccType() +") " + getAccount(aEvent.getDebitaccount()).getAccountName());
								DefaultMutableTreeNode credit = new DefaultMutableTreeNode("DEBIT("+ aEvent.getCreaditaccType() +") " + getAccount(aEvent.getCreditaccount()).getAccountName());
								pulsminus.add(debit);
								pulsminus.add(credit);
								key = aEvent.getAccEvent()+"="+pulsminus.toString()+"=";
								key = key + debit.toString()+"="+credit.toString();
								value = "debit="+aEvent.getDebitaccount()+":credit="+aEvent.getCreditaccount();
								checkDuplicteNode.put(key, value);
								
								
							}
							
							
						}
						accEvent.insert(pulsminus, accEvent.getChildCount());
						root.insert(accEvent, root.getChildCount());
					 
						
					}
	    			  });
	    		jButton10.addActionListener(new ActionListener() {
	    	        public void actionPerformed(ActionEvent arg0) {
	    	        	
	    	      	showAllrules.jTable1.removeAll();
	    	      	String s [] = {"ID","RuleName"};
	    	    	final	DefaultTableModel tablemodel = new DefaultTableModel(s,0) {
	    	       		 @Override
	    	       		    public boolean isCellEditable(int row, int column) {
	    	       		       //all cells false
	    	       		       return false;
	    	       		    }

	    	       	};
	    	      	processTableDataOpen(tablemodel);
	    	        	//processTableDataOpen(tablemodel);
	    	        	showAllrules.jTable1.setModel(tablemodel);
	    	        	showAllrules.setVisible(true);
	    	        	
	    	      	 
	    	           
	    	        }
	    	    });
		return jButton10;
	}

	
	private void removeALL() {
		Enumeration keys =  checkDuplicteNode.keys();
		while(keys.hasMoreElements()) {
			checkDuplicteNode.remove(keys.nextElement());
		}
	}
	
	
	private JTextField getJTextField2() {
		if (jTextField2 == null) {
			jTextField2 = new JTextField();
			jTextField2.setEditable(false);
			//jTextField2.setText("jTextField2");
		}
		return jTextField2;
	}

	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(new GroupLayout());
			jPanel1.add(getJButton9(), new Constraints(new Leading(416, 77, 10, 10), new Leading(12, 10, 26)));
			jPanel1.add(getJButton6(), new Constraints(new Leading(104, 121, 12, 12), new Leading(12, 12, 12)));
			jPanel1.add(getJButton7(), new Constraints(new Leading(243, 74, 12, 12), new Leading(12, 10, 26)));
			jPanel1.add(getJButton8(), new Constraints(new Leading(327, 10, 10), new Leading(12, 12, 12)));
		}
		return jPanel1;
	}

	private JButton getJButton8() {
		if (jButton8 == null) {
			jButton8 = new JButton();
			jButton8.setText("DELETE");
		}
		return jButton8;
	}

	private JButton getJButton5() {
		if (jButton5 == null) {
			jButton5 = new JButton();
			jButton5.setText("LOAD");
		}
		return jButton5;
	}

	private JButton getJButton7() {
		if (jButton7 == null) {
			jButton7 = new JButton();
			jButton7.setText("SAVE");
		}
		return jButton7;
	}

	private JButton getJButton6() {
		if (jButton6 == null) {
			jButton6 = new JButton();
			jButton6.setText("SAVE AS NEW");
		}
		return jButton6;
	}

	private JButton getJButton9() {
		if (jButton9 == null) {
			jButton9 = new JButton();
			jButton9.setText("CLOSE");
		}
		return jButton9;
	}

	private JButton getJButton4() {
		if (jButton4 == null) {
			jButton4 = new JButton();
			jButton4.setText("REMOVE");
		} jButton4.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(jTextField2.getText());
				 TreePath path =jTree0.getNextMatch(accEventModel.getSelectedItem().toString(), 0, Position.Bias.Forward);
				 TreePath path1 =jTree0.getNextMatch(accEventModel.getSelectedItem().toString(), 1, Position.Bias.Forward);
            	//  mNode = (MutableTreeNode)path1.getLastPathComponent();
            	//  treeModel.removeNodeFromParent(mNode);
            	  TreePath selPath =jTree0.getSelectionPath();
  			    if(selPath == null) {
  			    	commonUTIL.showAlertMessage("Select Row Please to remove row");
  			    	return;
  			    }
  			    String key = selectedKey;
  			  fillData();
  			    System.out.println(key);
  			  checkDuplicteNode.remove(key);
  		mNode =	(MutableTreeNode) selPath.getLastPathComponent();
  		TreeNode node = mNode.getParent();
  		MutableTreeNode cnode = null;
  		for(int i=0;i<node.getChildCount();i++) {
  			cnode = (MutableTreeNode) node.getChildAt(i);
  			String name = cnode.toString();
  			if((!name.equalsIgnoreCase("+")) || (!name.equalsIgnoreCase("-"))) {
  			if(key.contains(name)) {
  				break;
  			}
  			}
  		}
  	  if(cnode != null) {
  		  treeModel.removeNodeFromParent(mNode);
  			  treeModel.removeNodeFromParent(cnode);
  			  cnode = null;
  			   
  	  }
      if(node.getChildCount() == 0) {
    	  node = node.getParent();
    	  for(int i=0;i<node.getChildCount();i++) {
    		  cnode = (MutableTreeNode) node.getChildAt(i);
    		  String name = cnode.toString();
    			if(key.contains(name)) {
    				break;
    			}
    	  }
    	  treeModel.removeNodeFromParent(cnode);
    	 
      }
      if(node.getChildCount() == 0) {
    	  node = node.getParent();
    	  for(int i=0;i<node.getChildCount();i++) {
    		  cnode = (MutableTreeNode) node.getChildAt(i);
    		  String name = cnode.toString();
    			if(key.contains(name)) {
    				break;
    			}
    	  }
    	  treeModel.removeNodeFromParent(cnode);
    	 
      }
  			   
  			   mNode = null;
  			 jTextField1.setText("");
  			 jTextField0.setText("");
  			 account1 = null;
  			 account2=null;
  			 deleteDataFromAccLink(accEventLink);
  			accEventLink = null;
  			selectedKey = "";
  			
  			    
	            
				
				// TODO Auto-generated method stub
				
			}
        	
        });
		return jButton4;
	}

	private JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new JButton();
			jButton3.setText("UPDATE");
		}jButton3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
TreePath treePath = jTree0.getLeadSelectionPath();
	        	
	        	String name = "";
	        
    	        //    TreePath selPath = jTree.getNewLeadSelectionPath();
	        	if(treePath == null) {
	        	 	commonUTIL.showAlertMessage("Please select Debit/credit to update ");
	            	return;
	        	}
	        	
    	            if(_selectedName == null) {
    	            	commonUTIL.showAlertMessage("Select Rule Name");
    	            	return;
    	            }
    	            if(commonUTIL.isEmpty(selectedKey)) {
    	            	
    	            	return;
    	            }
    			    
    			  
	        	fillData();
	        	System.out.println("SelectKey == "+ selectedKey);
	        	if(rule != null ) {
	        	String accEventTypeName =   accEventLink.getAccEvent();
	            String plusMinus = "-";
	        	if(accEventLink.getPlusmius() == 0) {
	        		plusMinus = "+";
	        		
	        	}
	        			String ukey = "";
	        			String value = "";
	        			DefaultMutableTreeNode accountDebit = null;
	        			DefaultMutableTreeNode accountcredit = null;
	        			DefaultMutableTreeNode childNode = null;
	        			if(account1.getId() > 0) {
	        				 
		        			 accountDebit = new DefaultMutableTreeNode(("DEBIT("+ accEventLink.getDebitaccType() +") " + account1.getAccountName() ));
		        			 accountcredit = new DefaultMutableTreeNode(("CREDIT("+ accEventLink.getCreaditaccType() +") " + account2.getAccountName() ));
		        			 ukey = accEventTypeName+"="+plusMinus+"="+accountDebit.toString()+"="+accountcredit.toString();
		        			 value = "debit="+account1.getId()+":credit="+account2.getId();
		        			 
		        		} else {
		        			 accountDebit = new DefaultMutableTreeNode(("DEBIT( "+ accEventLink.getDebitaccType() +" ) " ));
		        			 accountcredit = new DefaultMutableTreeNode(("CREDIT( "+ accEventLink.getCreaditaccType() +" ) "  ));
		        			 ukey = accEventTypeName+"="+plusMinus+"="+accountDebit.toString()+"="+accountcredit.toString();
		        			 value = "debit="+account1.getId()+":credit="+account2.getId();
		        		}
	        			 if(checkChildNode(root, accEventTypeName) == null) {
	        				 DefaultMutableTreeNode accEvtNode = new DefaultMutableTreeNode(accEventTypeName);	
	        				 DefaultMutableTreeNode  plusMi = new DefaultMutableTreeNode(plusMinus);	
	 	        			plusMi.add(accountDebit);
		        			plusMi.add(accountcredit);
		        			accEvtNode.add(plusMi);
		        		
		        			root.add(accEvtNode);
		        			if(!checkOnselectedKey(ukey) && checkonNode(checkDuplicteNode, ukey)) {
		        				
		        			    checkDuplicteNode.remove(selectedKey);
		        			    checkDuplicteNode.put(ukey,value);
		        			    selectedKey = ukey;
		        			    
		        			}
		        			
		        			treeModel.insertNodeInto(accEvtNode, root, 0);
		        			
	        			 } else {
	        				 
	        				DefaultMutableTreeNode accEvent = checkChildNode(root, accEventTypeName);
	        					if((checkChildNode(accEvent, plusMinus)  == null)) {
	        						
	        						DefaultMutableTreeNode  plusMi = new DefaultMutableTreeNode(plusMinus);	
	    	 	        			plusMi.add(accountDebit);
	    		        			plusMi.add(accountcredit);
	    		        			if(!checkOnselectedKey(ukey) && checkonNode(checkDuplicteNode, ukey)) {
	    		        				
	    		        			    checkDuplicteNode.remove(selectedKey);
	    		        			    checkDuplicteNode.put(ukey,value);
	    		        			    selectedKey = ukey;
	    		        			    
	    		        			}
	    		        			treeModel.insertNodeInto(plusMi, accEvent, 0);
	        					} else {
	        						
	        						DefaultMutableTreeNode plusmi = checkChildNode(accEvent, plusMinus);
	        						if((!checkOnselectedKey(ukey) && !checkonNode(checkDuplicteNode,ukey))) {
	        							 TreePath path =  jTree0.getSelectionPath();
	        							 int i = jTree0.getSelectionCount();
	        		        			   mNode = (MutableTreeNode)path.getLastPathComponent();
	        		        			   
	        		        			   if(mNode.toString().contains("DEBIT")) {
	        		        				   treeModel.insertNodeInto(accountDebit, plusmi, jTree0.getSelectionCount());
	        			                	  treeModel.removeNodeFromParent(mNode);
	        			                	  plusmi.add(accountDebit);
	        							     
	        		        			   } else {
	        		        				   treeModel.insertNodeInto(accountcredit, plusmi, jTree0.getSelectionCount());
	        		        				   treeModel.removeNodeFromParent(mNode);
	        							plusmi.add(accountcredit);
	        							
	        							
	        		        			   }
	        		        			   jTree0.setModel(treeModel);
	        		        			    checkDuplicteNode.remove(selectedKey);
	        		        			    checkDuplicteNode.put(ukey,value);
	        		        			    selectedKey = ukey;
	        		        			  
	        		        			
	        						}
	        						
	        					
	        						
	        					}
	        				 
	        			 }
	    	            System.out.println(selectedKey);
	    	            System.out.println(ukey);
	    	            
	        	}
				
			}
			private boolean checkonNode(Hashtable checkDuplicteNode,String 
					key) {
			//	String key = accEventTypeName+"_"+plusMinus+"_"+debitAcc+"_"+creditAcc;
			
				return checkDuplicteNode.containsKey(key);
				// TODO Auto-generated method stub
				
			}
			
			private boolean checkOnselectedKey(String 
					key) {
			//	String key = accEventTypeName+"_"+plusMinus+"_"+debitAcc+"_"+creditAcc;
			     boolean flag = false;
				if(selectedKey.trim().equalsIgnoreCase(key.trim()))
						flag = true;
				else 
						flag = false;
				return flag;
				// TODO Auto-generated method stub
				
			}
	        	
        	
        });
		return jButton3;
	} 

	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setText("ADD");
		}jButton2.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent arg0) {
	        	//root.g
	        	TreePath treePath = jTree0.getLeadSelectionPath();
	        	
	        	String name = "";
	        
    	        //    TreePath selPath = jTree.getNewLeadSelectionPath();
    	            if(_selectedName == null) {
    	            	commonUTIL.showAlertMessage("Select Rule Name");
    	            	return;
    	            }
    			    
    			  
	        	fillData();
	        	
	        	if(rule != null ) {
	        	String accEventTypeName =   accEventLink.getAccEvent();
	            String plusMinus = "-";
	        	if(accEventLink.getPlusmius() == 0) {
	        		plusMinus = "+";
	        		
	        	}
	        			String key = "";
	        			String value = "";
	        			DefaultMutableTreeNode accountDebit = null;
	        			DefaultMutableTreeNode accountcredit = null;
	        			DefaultMutableTreeNode childNode = null;
	        			if(account1.getId() > 0) {
	        				 
		        			 accountDebit = new DefaultMutableTreeNode(("DEBIT("+ accEventLink.getDebitaccType() +") " + account1.getAccountName() ));
		        			 accountcredit = new DefaultMutableTreeNode(("CREDIT("+ accEventLink.getCreaditaccType() +") " + account2.getAccountName() ));
		        			 key = accEventTypeName+"="+plusMinus+"="+accountDebit.toString()+"="+accountcredit.toString();
		        			 value = "debit="+account1.getId()+":credit="+account2.getId();
		        			 
		        		} else {
		        			 accountDebit = new DefaultMutableTreeNode(("DEBIT( "+ accEventLink.getDebitaccType() +" ) " ));
		        			 accountcredit = new DefaultMutableTreeNode(("CREDIT( "+ accEventLink.getCreaditaccType() +" ) "  ));
		        			 key = accEventTypeName+"="+plusMinus+"="+accountDebit.toString()+"="+accountcredit.toString();
		        			 value = "debit="+account1.getId()+":credit="+account2.getId();
		        		}
	        			 if(checkChildNode(root, accEventTypeName) == null) {
	        				 DefaultMutableTreeNode accEvtNode = new DefaultMutableTreeNode(accEventTypeName);	
	        				 DefaultMutableTreeNode  plusMi = new DefaultMutableTreeNode(plusMinus);	
	 	        			plusMi.add(accountDebit);
		        			plusMi.add(accountcredit);
		        			accEvtNode.add(plusMi);
		        		
		        			root.add(accEvtNode);
		        			checkDuplicteNode.put(key,value);
		        			treeModel.insertNodeInto(accEvtNode, root, 0);
		        			
	        			 } else {
	        				 
	        				DefaultMutableTreeNode accEvent = checkChildNode(root, accEventTypeName);
	        					if((checkChildNode(accEvent, plusMinus)  == null)) {
	        						
	        						DefaultMutableTreeNode  plusMi = new DefaultMutableTreeNode(plusMinus);	
	    	 	        			plusMi.add(accountDebit);
	    		        			plusMi.add(accountcredit);
	    		        			checkDuplicteNode.put(key,value);
	    		        			treeModel.insertNodeInto(plusMi, accEvent, 0);
	        					} else {
	        						
	        						DefaultMutableTreeNode plusmi = checkChildNode(accEvent, plusMinus);
	        						if(!checkonNode(checkDuplicteNode,key)) {
	        						 plusmi.add(accountDebit);
	        							treeModel.insertNodeInto(accountDebit, plusmi, 0);
	        							plusmi.add(accountcredit);
	        							treeModel.insertNodeInto(accountcredit, plusmi, 0);
	        							checkDuplicteNode.put(key,value);
	        						}
	        						
	        					
	        						
	        					}
	        				 
	        			 }
	    	            
	    	            System.out.println(key);
	    	         
	    	            accEventLink.setId(0);
	    	            fillDataForSave(accEventLink);
	    	            
	        	}
	    	         //   jTree0.
	        }

			private boolean checkonNode(Hashtable checkDuplicteNode,String 
					key) {
			//	String key = accEventTypeName+"_"+plusMinus+"_"+debitAcc+"_"+creditAcc;
			
				return checkDuplicteNode.containsKey(key);
				// TODO Auto-generated method stub
				
			}
	        	
	        	
	        	
	        	
	           
	        
	    });
		return jButton2;
	}
	
		public DefaultMutableTreeNode  checkChildNode(DefaultMutableTreeNode node,String name) {
			DefaultMutableTreeNode child = null ;
			
			for(int i=0;i<node.getChildCount();i++) {
			    child = (DefaultMutableTreeNode) node.getChildAt(i);
			     if(child.toString().equalsIgnoreCase(name)) {
			    	 
			    	break;
			     } else {
			    	 child = null;
			     }
			}
            

			return child;
		}
			
		
	
	private JComboBox getJComboBox2() {
		if (jComboBox2 == null) {
			jComboBox2 = new JComboBox();
			jComboBox2.setModel(accType1);
			jComboBox2.setDoubleBuffered(false);
			jComboBox2.setBorder(null);
		}
		return jComboBox2;
	}

	private JLabel getJLabel2() {
		if (jLabel2 == null) {
			jLabel2 = new JLabel();
			jLabel2.setText("Credit");
		}
		return jLabel2;
	}

	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("jButton1");
		}final  JDialogTable showAllaccount2 = new JDialogTable(account1Model2);
		showAllaccount2.setLocationRelativeTo(this);
    	
		

		
		showAllaccount2.jTable1.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				String accountID = showAllaccount2.jTable1.getValueAt(showAllaccount2.jTable1.getSelectedRow(),0).toString();
				int id  =new  Integer(accountID).intValue();
				try {
					     account2 = new Account();
					     account2.setId(id);
					     account2 = (Account) remoteAccount.getAccount(id);
						 jTextField1.setText(account2.getAccountName());
						
						 showAllaccount2.setVisible(false);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				
				}
			}
			  });
		jButton1.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent arg0) {
	        	if(rule == null) {
					commonUTIL.showAlertMessage("Select Rule First");
					return;
				}
	        	showAllaccount2.jTable1.removeAll();
	        	String aCol [] = {"AccountID","AccountName"};
	        	account1Model2 = new DefaultTableModel(aCol,0);
	        	if(accType1.getSelectedItem() != null)
	        	       getAccountdata(account1Model2,accType1.getSelectedItem().toString());
	        	else 
	        		getAccountdata(account1Model2,null);
	        	showAllaccount2.jTable1.setModel(account1Model2);
	        	showAllaccount2.setVisible(true);
	    		
	    		
	        	
	           
	        }
	    });
		return jButton1;
	}

	private JTextField getJTextField1() {
		if (jTextField1 == null) {
			jTextField1 = new JTextField();
			 jTextField1.setEditable(false);
		}
		return jTextField1;
	}

	private JComboBox getJComboBox1() {
		if (jComboBox1 == null) {
			jComboBox1 = new JComboBox();
			jComboBox1.setModel(accType2);
			jComboBox1.setDoubleBuffered(false);
			jComboBox1.setBorder(null);
		}
		return jComboBox1;
	}

	private JTextField getJTextField0() {
		if (jTextField0 == null) {
			jTextField0 = new JTextField();
			 jTextField0.setEditable(false); 
		}
		return jTextField0;
	}

	private JLabel getJLabel1() {
		if (jLabel1 == null) {
			jLabel1 = new JLabel();
			jLabel1.setText("Debit");
		}
		return jLabel1;
	}

	private JButton getJButton0() {
		if (jButton0 == null) {
			jButton0 = new JButton();
			jButton0.setText("acco1");
		}final  JDialogTable showAllaccount1 = new JDialogTable(account1Model1);
		showAllaccount1.setLocationRelativeTo(this);
    	
		

		
		showAllaccount1.jTable1.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				
				String accountID = showAllaccount1.jTable1.getValueAt(showAllaccount1.jTable1.getSelectedRow(),0).toString();
				int id  =new  Integer(accountID).intValue();
				try {
					     account1 = new Account();
					     account1.setId(id);
					     account1 = (Account) remoteAccount.getAccount(id);
						 jTextField0.setText(account1.getAccountName());
						 showAllaccount1.setVisible(false);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				
				}
			}
			  });
		jButton0.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent arg0) {
	        	if(rule == null) {
					commonUTIL.showAlertMessage("Select Rule First");
					return;
				}
	        	showAllaccount1.jTable1.removeAll();
	        	String aCol [] = {"AccountID","AccountName"};
	        	account1Model1 = new DefaultTableModel(aCol,0);
	        	if(accType2.getSelectedItem() != null)
	        		getAccountdata(account1Model1,accType2.getSelectedItem().toString());
	        	else 
	        		getAccountdata(account1Model1,null);
	        
	        	showAllaccount1.jTable1.setModel(account1Model1);
	        	showAllaccount1.setVisible(true);
	    		
	    		
	        	
	           
	        }
	    });
		return jButton0;
	}

	private JPanel getJPanel0() {
		if (jPanel0 == null) {
			jPanel0 = new JPanel();
			jPanel0.setLayout(new GroupLayout());
			jPanel0.add(getJScrollPane0(), new Constraints(new Leading(9, 242, 10, 10), new Leading(11, 351, 10, 10)));
		}
		return jPanel0;
	}

	private JScrollPane getJScrollPane0() {
		if (jScrollPane0 == null) {
			jScrollPane0 = new JScrollPane();
			jScrollPane0.setViewportView(getJTree0());
		}
		return jScrollPane0;
	}

	private JTree getJTree0() {
		if (jTree0 == null) {
			jTree0 = new JTree();
		
			
			jTree0.setModel(treeModel);
		}
		jTree0.addTreeSelectionListener(new TreeSelectionListener() {

			@Override
			public void valueChanged(TreeSelectionEvent e){
				// TODO Auto-generated method stub
			
			    TreePath selPath =jTree0.getSelectionPath();
			    if(selPath == null)
			    	return;
			    if(selPath.getPathCount() == 2) {
			   System.out.println(selPath.getLastPathComponent().toString());
			   jComboBox0.setSelectedItem(selPath.getLastPathComponent().toString());
			    }
			    
			   // System.out.println(selPath.getPathCount());
			    if(selPath.getPathCount() > 3) {
			    	System.out.println(selPath.getLastPathComponent().toString());
			    	String accEveType =  ((DefaultMutableTreeNode)selPath.getPathComponent(1)).toString();
			    	String plusminus =  ((DefaultMutableTreeNode)selPath.getPathComponent(2)).toString();
			    	String debitcredit =  ((DefaultMutableTreeNode)selPath.getPathComponent(3)).toString();
			    	String debitAcc = "";
			    	String creditAcc = "";
			    	String daccType = "";
			    	String caccType = "";
			    	Enumeration enu = checkDuplicteNode.keys();
			    	String nodeis = "";
			    	String accounts = "";
			    	 String accs [] = null;
			    	while(enu.hasMoreElements()) {
			    	  String key =   (String) enu.nextElement();
			    	  String splitskeys [] = key.split("=");
			    	  boolean flag = false;
			    	  if((splitskeys[0].equalsIgnoreCase(accEveType)) && splitskeys[1].equalsIgnoreCase(plusminus)) {
			    		  if((splitskeys[2].equalsIgnoreCase(debitcredit)) || (splitskeys[3].equalsIgnoreCase(debitcredit))) {
			    			  flag = true;
			    			  selectedKey = key;
			    			  if(splitskeys[2].contains("CREDIT")) {
			    				  caccType = splitskeys[2].substring( splitskeys[2].indexOf("(")+1, splitskeys[2].indexOf(")"));
			    				  creditAcc = splitskeys[2].substring( splitskeys[2].indexOf(")"), splitskeys[2].length());
			    			  } else {
			    				  daccType = splitskeys[2].substring( splitskeys[2].indexOf("(")+1, splitskeys[2].indexOf(")"));
			    				  debitAcc = splitskeys[2].substring( splitskeys[2].indexOf(")")+1, splitskeys[2].length());
			    			  }
			    			  if(splitskeys[3].contains("CREDIT")) {
			    				  caccType = splitskeys[3].substring( splitskeys[3].indexOf("(")+1, splitskeys[3].indexOf(")"));
			    				  creditAcc = splitskeys[3].substring( splitskeys[3].indexOf(")")+1, splitskeys[3].length());
			    			  } else {
			    				  daccType = splitskeys[3].substring( splitskeys[3].indexOf("(")+1, splitskeys[3].indexOf(")"));
			    				  debitAcc = splitskeys[3].substring( splitskeys[3].indexOf(")")+1, splitskeys[3].length());
			    			  }
			    			  
			    			  String values = (String) checkDuplicteNode.get(key);
			    			  accs   = values.split(":");
			    			break;
			    		  }
						}
			    	 
			    	  
			    	}
			    	if(accs != null) {
			    		String acc = accs[0].substring(accs[0].indexOf("=") +1);
			    	    int debitId = new Integer(accs[0].substring(accs[0].indexOf("=") +1)).intValue();
			    	    int creditId = new Integer(accs[1].substring(accs[1].indexOf("=") +1)).intValue();
			    	    try {
							account1 = remoteAccount.getAccount(debitId);
							 account2 = remoteAccount.getAccount(creditId);
							 
							 if(plusminus.equalsIgnoreCase("+"))  {
								  jRadioButton0.setSelected(true);
							 jRadioButton1.setSelected(false);
							 }  else  {
								 jRadioButton0.setSelected(false);
							 jRadioButton1.setSelected(true);
							 }
							 accType2.setSelectedItem(daccType.trim());
							 accType1.setSelectedItem(caccType.trim());
							 jTextField0.setText(account1.getAccountName());
							 jTextField1.setText(account2.getAccountName());
							 accEventModel.setSelectedItem(accEveType);
						} catch (RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
			    	   
			    	}
			    	fillData();
			         System.out.println("nodeis selected  " +nodeis )	;
			         System.out.println("accounts selected  " +accounts )	;
			         
			    
			    }
			 //  System.out.println( selPath.getPathCount());
			   
			    
				
				
				    
				    
			
        	
        	
        	
			   }});
		return jTree0;
	}

	private void fillDataForSave(AccEventLink accEventLink) {
		try {
			remoteAccount.saveAccEventLink(accEventLink);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			commonUTIL.displayError("AccEventLinkPanel", "fillDataForSave", e);
		}
	}
	private void deleteDataFromAccLink(AccEventLink accEventLink) {
		try {
			remoteAccount.deleteAccEventLink(accEventLink);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			commonUTIL.displayError("AccEventLinkPanel", "fillDataForSave", e);
		}
	}
	private void fillData() {
		
	/*	if(accType1.getSelectedItem() == null) {
			commonUTIL.showAlertMessage("Debit Account Type");
			return;
		}
		if(accType2.getSelectedItem() == null) {
			commonUTIL.showAlertMessage("Credit Account Type");
			return;
		} */
		  accEventLink = new AccEventLink();
		accEventLink.setAccEvent(accEventModel.getSelectedItem().toString());
		accEventLink.setRuleID(rule.getId());
		if(jRadioButton0.isSelected())
			accEventLink.setPlusmius(0);
		else 
			accEventLink.setPlusmius(1);
		/*if(account1 == null) {
			commonUTIL.display("AccEventLink","Select Debit A/c");
			return;
		}
		if(account2== null) {
			commonUTIL.display("AccEventLink","Select Credit A/c");
			return;
		} */
		
		
		//accEventLink.setCreditaccount(account2.getId());
		if(account1 == null) {
			account1 = new Account();
			account1.setId(0);
			account1.setAccountName("NONE");
			accEventLink.setDebitaccount(0);
			accEventLink.setDebitaccType("NONE");
		} else {
			accEventLink.setDebitaccount(account1.getId());
			accEventLink.setDebitaccType(jComboBox1.getSelectedItem().toString());
			
		}
		
		if(account2 == null) {
			account2= new Account();
			account2.setId(0);
			account2.setAccountName("NONE");
			accEventLink.setCreditaccount(0);
			accEventLink.setCreaditaccType("NONE");
		} else {
			accEventLink.setCreditaccount(account2.getId());
			if(jComboBox2.getSelectedItem() == null) 
				accEventLink.setCreaditaccType("NONE"); 
			else 
			accEventLink.setCreaditaccType(jComboBox2.getSelectedItem().toString());
			
		}
		
		
		
	}
	
	
	private JRadioButton getJRadioButton1() {
		if (jRadioButton1 == null) {
			jRadioButton1 = new JRadioButton();
			jRadioButton1.setSelected(true);
			jRadioButton1.setText("-");
		}jRadioButton1.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent arg0) {
	        	jRadioButton0.setSelected(false);
	        	jRadioButton1.setSelected(true);
	        	
	           
	        }
	    });
		
		return jRadioButton1;
	}

	private JRadioButton getJRadioButton0() {
		if (jRadioButton0 == null) {
			jRadioButton0 = new JRadioButton();
			jRadioButton0.setSelected(false);
			jRadioButton0.setText("+");
		}jRadioButton0.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent arg0) {
	        	jRadioButton1.setSelected(false);
	        	jRadioButton0.setSelected(true);
	        	
	           
	        }
	    });
		return jRadioButton0;
	}

	private JComboBox getJComboBox0() {
		if (jComboBox0 == null) {
			jComboBox0 = new JComboBox();
			jComboBox0.setModel(accEventModel);
			jComboBox0.setDoubleBuffered(true);
			jComboBox0.setSelectedIndex(0);
		//	jComboBox0.setBorder(true);
		}
		return jComboBox0;
	}

	private JLabel getJLabel0() {
		if (jLabel0 == null) {
			jLabel0 = new JLabel();
			jLabel0.setText("AccEventName");
		}
		return jLabel0;
	}
		public void initData(ServerConnectionUtil de) {
		try {
     		remoteBORef = (RemoteReferenceData) de.getRMIService("ReferenceData");
     		remoteAccount = (RemoteAccount)  de.getRMIService("Account");
     		remoteProduct = (RemoteProduct)  de.getRMIService("Product");
     		account1 = new Account();
     		account1.setId(0);
     		account2 = new Account();
     		account2.setId(0);
     		
     	
     		
     		 		  
     		
  	  	 } catch (RemoteException e) {
  			e.printStackTrace();
  	}
		getMasterDataOnComboBox(accType1,"AccType");
		getMasterDataOnComboBox(accType2,"AccType");
		getMasterDataOnComboBox(accEventModel,"accEvent");
		//	getAccountdata(account1Model1,null);
			//getAccountdata(account1Model2,null);
			
			
		
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
	
	
	private Account getAccount(int accountID) {
		Account account;
		try {
			synchronized(accounts) {
				account = accounts.get(new Integer(accountID));
			}
			if(account == null) {
			   account = (Account) remoteAccount.getAccount(accountID);
			   accounts.put(new Integer(accountID), account);
		  }
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return account;
	}
	
	
	private void getAccountdata(DefaultTableModel model,String accountType) {
		// TODO Auto-generated method stub
    	Vector vector;
		try {
			
			if(accountType == null) {
					vector = (Vector) remoteAccount.getAllAccounts();
			} else  {
				String sql = " type = '"+accountType+"'";
				vector = (Vector) remoteAccount.getAccountonWhereClause(sql);
				
			}
			Iterator it = vector.iterator();
	    	int i =0;
	    	while(it.hasNext()) {
	    		
	    		Account account = (Account) it.next();
	    	
	    		model.insertRow(i, new Object[]{account.getId(),account.getAccountName()});
	    		i++;
	    		}
	    		
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	private void processTableDataOpen(DefaultTableModel model) {
		// TODO Auto-generated method stub
    	Vector vector;
		try {
			
			
			vector = (Vector) remoteAccount.getAllAccountConfigRules();
			Iterator it = vector.iterator();
	    	int i =0;
	    	while(it.hasNext()) {
	    		
	    		AccConfigRule rule = (AccConfigRule) it.next();
	    	
	    		model.insertRow(i, new Object[]{rule.getId(),rule.getRuleName()});
	    		i++;
	    		}
	    		
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}

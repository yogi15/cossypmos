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
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;

import org.dyno.visual.swing.layouts.Bilateral;
import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;

import util.commonUTIL;
import apps.window.utilwindow.JDialogTable;
import beans.AccConfigRule;
import beans.Account;
import beans.Folder;
import beans.LinkFolder;
import beans.StartUPData;

import dsServices.RemoteAccount;
import dsServices.RemoteProduct;
import dsServices.RemoteReferenceData;
import dsServices.ServerConnectionUtil;

//VS4E -- DO NOT REMOVE THIS LINE!
public class FolderLink extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTree jTree0;
	private JScrollPane jScrollPane0;
	private JPanel jPanel0;
	private JLabel jLabel0;
	private JLabel jLabel1;
	private JLabel jLabel2;
	private JPanel jPanel1;
	private JButton jButton0;
	private JButton jButton1;
	private JButton jButton2;
	private JButton jButton3;
	private JCheckBox jCheckBox0;
	Folder folder = null;
	String selectedKey = "";
	AccConfigRule rule= null;
    LinkFolder linkfolder = null;
	RemoteReferenceData remoteBORef; 
	RemoteAccount remoteAccount;
	RemoteProduct remoteProduct;
	private JTextField jTextField0;
	private JTextField jTextField1;
	private JTextField jTextField2;
	private JButton jButton4;
	private JButton jButton5;
	private JButton jButton6;
	String folderCOL [] = {"id","FolderName"};
	DefaultTableModel folderModel = new DefaultTableModel(folderCOL,0);
	String ruleName [] = {"id","RuleName"};
	DefaultTableModel ruleMode = new DefaultTableModel(ruleName,0);
	String productName [] = {"ProductType"};
	DefaultTableModel productModel = new DefaultTableModel(productName,0);
	Hashtable<String, LinkFolder> selectkey = new Hashtable<String, LinkFolder>();
	 DefaultMutableTreeNode root =  new DefaultMutableTreeNode("FolderLinks");
	 DefaultMutableTreeNode parent, leaf;
	 DefaultTreeModel treeModel = new DefaultTreeModel(root);
	 Hashtable<Integer,Folder> folders = new Hashtable<Integer,Folder>();
	 Hashtable<Integer, AccConfigRule> rules = new Hashtable<Integer, AccConfigRule>();
	 Hashtable<String, String> productType = new Hashtable<String, String>();
	
	
	public FolderLink(ServerConnectionUtil de) {
		
		initData(de);
		initComponents();
	}

	private void initComponents() {
		setLayout(new GroupLayout());
		add(getJPanel0(), new Constraints(new Leading(14, 237, 10, 10), new Leading(15, 421, 10, 10)));
		add(getJPanel1(), new Constraints(new Bilateral(263, 12, 0), new Leading(26, 395, 10, 10)));
		setSize(634, 451);
	}

	private JButton getJButton6() {
		if (jButton6 == null) {
			jButton6 = new JButton();
			jButton6.setText("Folder");
		}
		final  JDialogTable showAllRules = new JDialogTable(ruleMode);
		showAllRules.setLocationRelativeTo(this);
		showAllRules.jTable1.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				String ruleid = showAllRules.jTable1.getValueAt(showAllRules.jTable1.getSelectedRow(),0).toString();
				int id  =new  Integer(ruleid).intValue();
				try {
					   
						rule = (AccConfigRule) remoteAccount.getaccConfigRule(id);
						jTextField1.setText(rule.getRuleName());
						showAllRules.dispose();
						
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				
				}
			}
			  });
		jButton6.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent arg0) {
	        	showAllRules.jTable1.removeAll();
	        	String ruleName [] = {"id","RuleName"};
	        	DefaultTableModel ruleMode = new DefaultTableModel(ruleName,0);
	    		getAccountRuledata(ruleMode);
	        	//processTableDataOpen(tablemodel);
	    		showAllRules.jTable1.setModel(ruleMode);
	    		showAllRules.setVisible(true);
	      	 
	           
	        }
	    });
		return jButton6;
	}

	private JButton getJButton5() {
		if (jButton5 == null) {
			jButton5 = new JButton();
			jButton5.setText("ProductB");
		}final  JDialogTable showAllProductType = new JDialogTable(productModel);
		showAllProductType.setLocationRelativeTo(this);
		showAllProductType.jTable1.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				String productType = showAllProductType.jTable1.getValueAt(showAllProductType.jTable1.getSelectedRow(),0).toString();
				
				jTextField2.setText(productType);
				showAllProductType.dispose();
			}
			  });
		jButton5.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent arg0) {
	        	showAllProductType.jTable1.removeAll();
	        	String productName [] = {"ProductType"};
	        	DefaultTableModel productModel = new DefaultTableModel(productName,0);
	        	
	    		getMasterData(productModel,"ProductType");
	        	//processTableDataOpen(tablemodel);
	    		showAllProductType.jTable1.setModel(productModel);
	    		showAllProductType.setVisible(true);
	      	 
	           
	        }
	    });
		
		return jButton5;
	}

	private JButton getJButton4() {
		if (jButton4 == null) {
			jButton4 = new JButton();
			jButton4.setText("RUleB");
			
		}final  JDialogTable showAllfolder = new JDialogTable(folderModel);
		showAllfolder.setLocationRelativeTo(this);
    	
		

		
		showAllfolder.jTable1.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				String ruleid = showAllfolder.jTable1.getValueAt(showAllfolder.jTable1.getSelectedRow(),0).toString();
				int id  =new  Integer(ruleid).intValue();
				try {
					     folder = new Folder();
					     folder.setId(id);
						 folder = (Folder) remoteBORef.selectFolder(folder);
						 jTextField0.setText(folder.getFolder_name());
						 showAllfolder.setVisible(false);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				
				}
			}
			  });
		jButton4.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent arg0) {
	        	showAllfolder.jTable1.removeAll();
	        	String folderCOL [] = {"id","FolderName"};
	        	DefaultTableModel folderModel = new DefaultTableModel(folderCOL,0);
	        	getFolderdata(folderModel);
	        	showAllfolder.jTable1.setModel(folderModel);
	    		showAllfolder.setVisible(true);
	    		
	    		
	        	
	           
	        }
	    });
		return jButton4;
	}

	private JTextField getJTextField2() {
		if (jTextField2 == null) {
			jTextField2 = new JTextField();
			//jTextField2.setText(""");
			jTextField2.setEditable(false);
		}
		return jTextField2;
	}

	private JTextField getJTextField1() {
		if (jTextField1 == null) {
			jTextField1 = new JTextField();
		//	jTextField1.setText("FolderT");
			jTextField1.setEditable(false);
		}
		return jTextField1;
	}

	private JTextField getJTextField0() {
		if (jTextField0 == null) {
			jTextField0 = new JTextField();
		//	jTextField0.setText("   ");
			jTextField0.setEditable(false);
		}
		return jTextField0;
	}

	private JCheckBox getJCheckBox0() {
		if (jCheckBox0 == null) {
			jCheckBox0 = new JCheckBox();
			jCheckBox0.setSelected(true);
			jCheckBox0.setText("Expand");
		}
		return jCheckBox0;
	}

	private JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new JButton();
			jButton3.setText("DELETE");
		}
		return jButton3;
	}
	private void replaceAllkeys(String Oldstring, String newString,	int id) {
		// TODO Auto-generated method stub
			Enumeration keys =	selectkey.keys();
			Vector oldkeys = new Vector();
			while(keys.hasMoreElements()) {
				String key = (String) keys.nextElement();
				LinkFolder linkFolder = (LinkFolder) selectkey.get(key);
				if(key.contains(Oldstring)) {
					String newKey = key.replace(Oldstring, newString);
					linkFolder.setFolderId(id);
					selectkey.put(newKey, linkFolder);
					oldkeys.add(key);
					
					
				}
			}
			for(int i=0;i<oldkeys.size();i++) {
			   String oldkey = (String)	oldkeys.get(i);
			   selectkey.remove(oldkey);
			}
			Enumeration  keyss =	selectkey.keys();
			 while(keyss.hasMoreElements()) {
					String key = (String) keyss.nextElement();
					System.out.println(" udat are key are " + key);
			 }
		
	}
	
	
	private void replaceAllkeys(String parentString,String Oldstring, String newString,	int id) {
		// TODO Auto-generated method stub
			Enumeration keys =	selectkey.keys();
			Vector oldkeys = new Vector();
			while(keys.hasMoreElements()) {
				String key = (String) keys.nextElement();
				LinkFolder linkFolder = (LinkFolder) selectkey.get(key);
				if(key.contains(parentString) && key.contains(Oldstring)) {
					String newKey = key.replace(Oldstring, newString);
					System.out.println(newKey);
					linkFolder.setRuleid(id);
					selectkey.put(newKey, linkFolder);
					oldkeys.add(key);
					
					
				}
			}
			for(int i=0;i<oldkeys.size();i++) {
			   String oldkey = (String)	oldkeys.get(i);
			   selectkey.remove(oldkey);
			}
			Enumeration  keyss =	selectkey.keys();
			 while(keyss.hasMoreElements()) {
					String key = (String) keyss.nextElement();
					System.out.println(" udat are key are " + key);
			 }
		
	}
	
	
	
	
	private boolean checkOnselectedKey(String key) {
	//	String key = accEventTypeName+"_"+plusMinus+"_"+debitAcc+"_"+creditAcc;
	     boolean flag = false;
		if(selectedKey.trim().equalsIgnoreCase(key.trim()))
				flag = true;
		else 
				flag = false;
		return flag;
		// TODO Auto-generated method stub
		
	}
	
	private DefaultMutableTreeNode checkChildExists(DefaultMutableTreeNode node,String name) {
		
		boolean flag = false;
		int childCount = node.getChildCount();
		
		DefaultMutableTreeNode child = null;
		for(int i=0;i<childCount;i++) {
			child =	(DefaultMutableTreeNode) node.getChildAt(i);
			if(child.toString().equalsIgnoreCase(name)) {
				break;
			}
			
		}
		
		return child;
	}
	
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setText("UPDATE");
			jButton2.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					
					
					TreePath treePath = jTree0.getLeadSelectionPath();
					
		        
	    	        //    TreePath selPath = jTree.getNewLeadSelectionPath();
		        	if(treePath == null) {
		        	 	commonUTIL.showAlertMessage("Select Product Type or Rule ");
		            	return;
		        	}
		        	fillData();
		        	String skey = selectedKey;
		        	String ukey =  "ruleName="+jTextField1.getText().trim()+";folderName="+folder.getFolder_name()+";productName="+jTextField2.getText();
		        	
		        //	if(checkonNode(selectkey, ukey)) {
		        	//	return;
		        	//}
		        	DefaultMutableTreeNode folderselected = null;
		        	DefaultMutableTreeNode ruleselected = null;
		        	DefaultMutableTreeNode folderToNewUpdate = null;
		        	 
		        	if(treePath.getPathCount() >= 2) {
		        		folderselected =  ((DefaultMutableTreeNode)treePath.getPathComponent(1));
		        	} if(treePath.getPathCount() >= 4) {
		        		folderselected =  ((DefaultMutableTreeNode)treePath.getPathComponent(1));
		        		ruleselected =  ((DefaultMutableTreeNode)treePath.getPathComponent(2));
		        		
		        	}
		        	
		        	if(folderselected.toString().equalsIgnoreCase(jTextField0.getText().trim()))	{
		        		
		        	}
		        	
		        	
		        	
					        if(folderselected.toString().equalsIgnoreCase(jTextField0.getText().trim()))	{
					        		DefaultMutableTreeNode folderToupdate = checkChildNode(root,folder.getFolder_name());
					        		
						        	if(folderToupdate != null) {
						        		 folderToNewUpdate = new DefaultMutableTreeNode(folder.getFolder_name());
						        		 addChildToNewNode(folderToNewUpdate,folderselected);
						        		 addChildToNewNode(folderToNewUpdate,folderToupdate);
						        		 treeModel.removeNodeFromParent(folderToupdate);
						        		
						        		
						        		
						        	} else {
						        		folderToNewUpdate= new DefaultMutableTreeNode(folder.getFolder_name());
									        		DefaultMutableTreeNode child = null;
												   	  for(int i=0;i<folderselected.getChildCount();i++) {
												   			child =	(DefaultMutableTreeNode) folderselected.getChildAt(i);
												   			
												   			treeModel.insertNodeInto(child, folderToNewUpdate, folderToNewUpdate.getChildCount());
												   			
												   		}
						        		
						        	}
						        	treeModel.insertNodeInto(folderToNewUpdate, root, root.getChildCount());
						    		if((!checkOnselectedKey(ukey)) && (!checkonNode(selectkey, ukey))) {
				        				replaceAllkeys(folderselected.toString(),folder.getFolder_name(),folder.getId());
						    		//	selectkey.remove(selectedKey);
						    			selectkey.put(ukey,linkfolder);
				        			    selectedKey = ukey;
				        			    TreePath path =  jTree0.getSelectionPath();
				        			    MutableTreeNode mNode = (MutableTreeNode)path.getLastPathComponent();
				        			    treeModel.removeNodeFromParent(mNode);
				        			    jTree0.setModel(treeModel);
				        			 }
		        				
					        } 
		        	
		        	
		        	
		        	
		        	
				}
		        	
			});
			}
		
		
	
		return jButton2;
	}

	public void addChildToNewNode(DefaultMutableTreeNode newNode,DefaultMutableTreeNode oldNode) {
           for(int i=0;i<oldNode.getChildCount();i++) {
        	   DefaultMutableTreeNode childnode = (DefaultMutableTreeNode) oldNode.getChildAt(i);
        	   DefaultMutableTreeNode checkChildExists =  checkChildNode(newNode,childnode.toString());
        	   if(checkChildExists  == null) {
        		   newNode.insert(childnode, newNode.getChildCount());
        	   } else {
        		   addChildToNewNode(checkChildExists,childnode);
        	   }
           }
	}
	
	
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("REMOVE");
		}
		return jButton1;
	}

	private JButton getJButton0() {
		if (jButton0 == null) {
			jButton0 = new JButton();
			jButton0.setText("Add");
			jButton0.addActionListener(new ActionListener() {
		        public void actionPerformed(ActionEvent arg0) {
		        	LinkFolder linkfolder = new LinkFolder();
		        	linkfolder.setId(0);
		        	fillFolderLinkData(linkfolder);
		        	selectedKey = "ruleName="+jTextField1.getText().trim()+";folderName="+folder.getFolder_name().trim()+";productName="+jTextField2.getText().trim();
		        	System.out.println(selectedKey);
		        	if(checkonNode(selectkey, selectedKey)) {
		        		commonUTIL.showAlertMessage(selectedKey + "  Already exists");
		        		return;
		        	}
		        	selectkey.put(selectedKey, linkfolder);
		        	int e = 0;
		        
		       if( checkChildNode(root, folder.getFolder_name()) == null) {
		    	   DefaultMutableTreeNode  folderName = new DefaultMutableTreeNode(folder.getFolder_name());	
		    		DefaultMutableTreeNode  ruleName = new DefaultMutableTreeNode(rule.getRuleName());
		    		DefaultMutableTreeNode  productName = new DefaultMutableTreeNode(jTextField2.getText(),false);
		    		//ruleName.add(productName);
		    		//folderName.add(ruleName);
		    		//root.insert(folderName, 0);
		    		
		    		treeModel.insertNodeInto(folderName, root, root.getChildCount());
		    		treeModel.insertNodeInto(ruleName, folderName, folderName.getChildCount());
		    		treeModel.insertNodeInto(productName, ruleName, ruleName.getChildCount());
		    		e = ruleName.getChildCount();
		    		
		       } else {
		    	   DefaultMutableTreeNode  folderName =   checkChildNode(root, folder.getFolder_name());
		    	   if( checkChildNode(folderName,rule.getRuleName()) == null) {
		    		   DefaultMutableTreeNode  ruleName = new DefaultMutableTreeNode(rule.getRuleName());
			    		DefaultMutableTreeNode  productName = new DefaultMutableTreeNode(jTextField2.getText(),false);
			    		ruleName.add(productName);
			    		treeModel.insertNodeInto(ruleName, folderName, folderName.getChildCount());
			    		e = folderName.getChildCount();
		    	   } else {
		    	   DefaultMutableTreeNode  ruleName =   checkChildNode(folderName, rule.getRuleName());
		    	   if( checkChildNode(folderName,jTextField2.getText()) == null) {
		    		   DefaultMutableTreeNode  productName = new DefaultMutableTreeNode(jTextField2.getText(),false);
			    		
			    	//	ruleName.add(productName);
			    		treeModel.insertNodeInto(productName, ruleName, ruleName.getChildCount());
			    		e = ruleName.getChildCount();
		    	   }
		    	   }
		    	   
		       }
		       
		       
		        	
		       jTree0.collapseRow(e);
		        }
		       
			});
			
		}
	//	linkfolder = new LinkFolder();
		//linkfolder.setId(0);
	//	fillFolderLinkData(linkfolder);
		return jButton0;
	}

	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(new GroupLayout());
			jPanel1.add(getJButton0(), new Constraints(new Leading(12, 12, 12), new Leading(319, 10, 10)));
			jPanel1.add(getJButton1(), new Constraints(new Leading(82, 10, 10), new Leading(319, 10, 10)));
			jPanel1.add(getJButton2(), new Constraints(new Leading(177, 12, 12), new Leading(319, 12, 12)));
			jPanel1.add(getJButton3(), new Constraints(new Leading(269, 80, 12, 12), new Leading(319, 12, 12)));
			jPanel1.add(getJLabel0(), new Constraints(new Leading(5, 81, 10, 10), new Leading(31, 10, 10)));
			jPanel1.add(getJCheckBox0(), new Constraints(new Leading(10, 8, 8), new Leading(186, 10, 10)));
			jPanel1.add(getJLabel2(), new Constraints(new Leading(3, 12, 12), new Leading(127, 10, 10)));
			jPanel1.add(getJTextField0(), new Constraints(new Leading(98, 166, 10, 10), new Leading(31, 24, 10, 10)));
			jPanel1.add(getJLabel1(), new Constraints(new Leading(5, 81, 12, 12), new Leading(75, 10, 10)));
			jPanel1.add(getJTextField1(), new Constraints(new Leading(98, 166, 12, 12), new Leading(75, 24, 12, 12)));
			jPanel1.add(getJTextField2(), new Constraints(new Leading(100, 166, 10, 10), new Leading(123, 24, 12, 12)));
			jPanel1.add(getJButton4(), new Constraints(new Leading(276, 36, 10, 10), new Leading(31, 12, 12)));
			jPanel1.add(getJButton5(), new Constraints(new Leading(280, 36, 10, 10), new Leading(121, 12, 12)));
			jPanel1.add(getJButton6(), new Constraints(new Leading(280, 36, 12, 12), new Leading(73, 12, 12)));
		}
		return jPanel1;
	}

	private JLabel getJLabel2() {
		if (jLabel2 == null) {
			jLabel2 = new JLabel();
			jLabel2.setText("Product Name");
		}
		return jLabel2;
	}

	private JLabel getJLabel1() {
		if (jLabel1 == null) {
			jLabel1 = new JLabel();
			jLabel1.setText("Rule Name");
		}
		return jLabel1;
	}

	private JLabel getJLabel0() {
		if (jLabel0 == null) {
			jLabel0 = new JLabel();
			jLabel0.setText("Folder Name ");
		}
		return jLabel0;
	}

	private JPanel getJPanel0() {
		if (jPanel0 == null) {
			jPanel0 = new JPanel();
			jPanel0.setLayout(new GroupLayout());
			jPanel0.add(getJScrollPane0(), new Constraints(new Leading(11, 218, 10, 10), new Bilateral(11, 12, 22)));
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
			getALLLinkFolder(treeModel);
		} 
		jTree0.addTreeSelectionListener(new TreeSelectionListener() {

			@Override
			public void valueChanged(TreeSelectionEvent e){
				   TreePath selPath =jTree0.getSelectionPath();
				    if(selPath == null)
				    	return;
				
				if(selPath.getPathCount() > 3) {
			    	System.out.println(selPath.getLastPathComponent().toString());
			    	String FolderName =  ((DefaultMutableTreeNode)selPath.getPathComponent(1)).toString();
			    	String ruleName =  ((DefaultMutableTreeNode)selPath.getPathComponent(2)).toString();
			    	String productType =  ((DefaultMutableTreeNode)selPath.getPathComponent(3)).toString();
			    	selectedKey = "ruleName="+ruleName.trim()+";folderName="+FolderName+";productName="+productType;
			    	System.out.println(" key to seletct  ****** getKey +++   "  + selectedKey);
			    	Enumeration<String> keys =	selectkey.keys();
					 while(keys.hasMoreElements()) {
							String key = (String) keys.nextElement();
							System.out.println(" select  " + key);
					 }
			    	linkfolder = (LinkFolder) selectkey.get(selectedKey);
			    	
						rule = rules.get(linkfolder.getRuleid());
						jTextField1.setText(rule.getRuleName().trim());
						folder = folders.get(linkfolder.getFolderId());
						
						jTextField0.setText(folder.getFolder_name().trim());
						jTextField2.setText(linkfolder.getProductType());
						
					
			    	fillData();
				}
				
			}
		});
		
		return jTree0;
	}
	
	
	private boolean checkonNode(Hashtable checkDuplicteNode,String 
			key) {
	//	String key = accEventTypeName+"_"+plusMinus+"_"+debitAcc+"_"+creditAcc;
	
		return checkDuplicteNode.containsKey(key);
		// TODO Auto-generated method stub
		
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
	public void fillData() {
		linkfolder = new LinkFolder();
		linkfolder.setSdifilterid(0);
		if(rule == null) {
			commonUTIL.showAlertMessage("Select Rule to Link to Folder ");
			return;
		}
		linkfolder.setRuleid(rule.getId());
	    if(folder.getId() == 0) {
	    	commonUTIL.showAlertMessage("Select Folder to Link ");
		return;
	}
		linkfolder.setFolderId(folder.getId());
		if(commonUTIL.isEmpty(jTextField2.getText()))  {
			commonUTIL.showAlertMessage("Select ProductType to Link to rule and Folder ");
			return;
		}
		linkfolder.setProductType(jTextField2.getText())	;	
		
		
		
	}
	
	public void initData(ServerConnectionUtil de) {
		try {
     		remoteBORef = (RemoteReferenceData) de.getRMIService("ReferenceData");
     		remoteAccount = (RemoteAccount)  de.getRMIService("Account");
     		remoteProduct = (RemoteProduct)  de.getRMIService("Product");
     		
     		
     	
     		
     		 		  
     		
  	  	 } catch (RemoteException e) {
  			e.printStackTrace();
  	}
			getMasterData(productModel,"ProductType");
			getFolderdata(folderModel);
			getAccountRuledata(ruleMode);
			
		
	}
	
	private void getALLLinkFolder(DefaultTreeModel treeModel2) {
		// TODO Auto-generated method stub
		try {
			Vector folderLinks = (Vector) remoteAccount.getAllLinkFolders();
			if(folderLinks != null) {
				for(int i=0;i<folderLinks.size();i++) {
					LinkFolder newlinkFolder = (LinkFolder) folderLinks.get(i);
					Folder folder = folders.get(newlinkFolder.getFolderId());
					AccConfigRule rule = rules.get(newlinkFolder.getRuleid());
					String key =  "ruleName="+rule.getRuleName().trim()+";folderName="+folder.getFolder_name().trim()+";productName="+newlinkFolder.getProductType().trim();
					selectkey.put(key, newlinkFolder);
					int e = 0;
					 if( checkChildNode(root, folder.getFolder_name()) == null) {
				    	   DefaultMutableTreeNode  folderName = new DefaultMutableTreeNode(folder.getFolder_name());	
				    		DefaultMutableTreeNode  ruleName = new DefaultMutableTreeNode(rule.getRuleName());
				    		DefaultMutableTreeNode  productName = new DefaultMutableTreeNode(newlinkFolder.getProductType(),false);
				    		//ruleName.add(productName);
				    		//folderName.add(ruleName);
				    		//root.insert(folderName, 0);
				    		
				    		treeModel.insertNodeInto(folderName, root, root.getChildCount());
				    		treeModel.insertNodeInto(ruleName, folderName, folderName.getChildCount());
				    		treeModel.insertNodeInto(productName, ruleName, ruleName.getChildCount());
				    		e = ruleName.getChildCount();
				    		
				       } else {
				    	   DefaultMutableTreeNode  folderName =   checkChildNode(root, folder.getFolder_name());
				    	   if( checkChildNode(folderName,rule.getRuleName()) == null) {
				    		   DefaultMutableTreeNode  ruleName = new DefaultMutableTreeNode(rule.getRuleName());
					    		DefaultMutableTreeNode  productName = new DefaultMutableTreeNode(newlinkFolder.getProductType(),false);
					    		ruleName.add(productName);
					    		treeModel.insertNodeInto(ruleName, folderName, folderName.getChildCount());
					    		e = folderName.getChildCount();
				    	   } else {
				    	   DefaultMutableTreeNode  ruleName =   checkChildNode(folderName, rule.getRuleName());
				    	   if( checkChildNode(folderName,newlinkFolder.getProductType()) == null) {
				    		   DefaultMutableTreeNode  productName = new DefaultMutableTreeNode(newlinkFolder.getProductType(),false);
					    		
					    	//	ruleName.add(productName);
					    		treeModel.insertNodeInto(productName, ruleName, ruleName.getChildCount());
					    		e = ruleName.getChildCount();
				    	   }
				    	   }
				    	   
				       }
					 jTree0.expandRow(e);
				       
					 
				}
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void fillFolderLinkData(LinkFolder linkFolder) {
		
		
		if(commonUTIL.isEmpty(	jTextField2.getText())) {
			commonUTIL.showAlertMessage( "Select Product Type");
			return;
		}
		if(rule == null && rule.getId() ==0) {
			commonUTIL.showAlertMessage("Select Rule to Link to Folder ");
			return;
		}
		if(folder == null && folder.getId() == 0) {
			commonUTIL.showAlertMessage("Select Rule to Link to Folder ");
			return;
		}
		linkFolder.setFolderId(folder.getId());
		linkFolder.setRuleid(rule.getId());
		linkFolder.setProductType(	jTextField2.getText());
		linkFolder.setSdifilterid(0);
		try {
			remoteAccount.saveLinkFolder(linkFolder);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			commonUTIL.displayError("FolderLink", "fillData", e);
		}
		this.linkfolder = linkFolder;
		
	}
	
public void openFolderLinkData(LinkFolder linkFolder) {
		
		this.linkfolder = linkFolder;
		folder = new Folder();
		folder.setId(linkFolder.getFolderId());
		
		try {
			folder = remoteBORef.selectFolder(folder);
			jTextField0.setText(folder.getFolder_name());
			rule = remoteAccount.getaccConfigRule(linkFolder.getRuleid());
			jTextField1.setText(rule.getRuleName());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		linkFolder.setFolderId(folder.getId());
		linkFolder.setRuleid(rule.getId());
		
		jTextField2.setText(linkFolder.getProductType());
		linkFolder.setSdifilterid(0);
		
	}
	
	private final void getMasterData( DefaultTableModel model,String name) {
		Vector vector = null;
		try {
			remoteBORef = getRemoteBORef();
			vector = (Vector) remoteBORef.getStartUPData(name);
			
			if(vector.size() > 0) {
			Iterator it = vector.iterator();
	    	int i =0;
	    	//combodata.insertElementAt(" ", 0);
	    	
	    	while(it.hasNext()) {
	    		
	    		StartUPData data = (StartUPData) it.next();
	    	
			
				
	    		model.insertRow(i, new Object[]{data.getName()});
	    		productType.put(data.getName(), data.getName());
			i++;
		}	
	    	
			}
		}catch (RemoteException e) {
					// TODO Auto-generated catch block
				
		commonUTIL.displayError("AccountWindow","getMasterDataOnComboBox", e);
		}
	}
	
	public void getFolderdata(DefaultTableModel model) {
		 Vector<Folder> vector;
			try {
				remoteBORef = getRemoteBORef();
					vector = (Vector<Folder>) remoteBORef.selectALLFolders();
					if(vector == null)
						return;
				   Iterator it = vector.iterator();
		    	   int i =0;
		    	while(it.hasNext()) {
		    		
		    		Folder folder =	(Folder) it.next();
		    		
		    		model.insertRow(i, new Object[]{folder.getId(),folder.getFolder_name()});
		    		folders.put(folder.getId(), folder);
		    		i++;
			}	
			}catch (RemoteException e) {
						// TODO Auto-generated catch block
				commonUTIL.displayError("AccountWindow","getAccountsData", e);
					}
			
			
		}
	
	
	
	public void getAccountRuledata(DefaultTableModel model) {
		 Vector vector;
			try {
				
					vector = (Vector) remoteAccount.getAllAccountConfigRules();
					if(vector == null)
						return;
				   Iterator it = vector.iterator();
		    	   int i =0;
		    	while(it.hasNext()) {
		    		
		    		AccConfigRule rule =	(AccConfigRule) it.next();
		    		
		    		model.insertRow(i, new Object[]{rule.getId(), rule.getRuleName()});
		    		rules.put(rule.getId(), rule);
		    		i++;
			}	
			}catch (RemoteException e) {
						// TODO Auto-generated catch block
				commonUTIL.displayError("AccountWindow","getAccountsData", e);
					}
			
			
		}
	
	
	
	public RemoteReferenceData getRemoteBORef() {
		return remoteBORef;
	}

	public void setRemoteBORef(RemoteReferenceData remoteBORef) {
		this.remoteBORef = remoteBORef;
	}

	public RemoteAccount getRemoteAccount() {
		return remoteAccount;
	}

	public void setRemoteAccount(RemoteAccount remoteAccount) {
		this.remoteAccount = remoteAccount;
	}

	public RemoteProduct getRemoteProduct() {
		return remoteProduct;
	}

	public void setRemoteProduct(RemoteProduct remoteProduct) {
		this.remoteProduct = remoteProduct;
	}
}

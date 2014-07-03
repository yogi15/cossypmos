package apps.window.staticwindow;


import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.omg.CORBA.REBIND;

import beans.LegalEntity;
import beans.StartUPData;
import beans.Users;
import beans.WFConfig;

import util.ComparatorFactory;
import util.commonUTIL;

import dsServices.Remote;
import dsServices.RemoteReferenceData;
import dsServices.ServerConnectionUtil;

public class WorkFlowSetupWindow123 extends javax.swing.JPanel  {
	private javax.swing.JPanel jPanel2;
	 private javax.swing.JPanel jPanel1;
	 private javax.swing.JPanel jPanel3;
	 private javax.swing.JPanel jPanel5;
	 private javax.swing.JPanel jPanel4;
	 private javax.swing.JButton jadd;
	 private javax.swing.JButton jremove;
	 private javax.swing.JButton jsave;
	 private javax.swing.JComboBox cstatus;
	 public static  ServerConnectionUtil de = null;
		RemoteReferenceData remoteBORef;
	 private javax.swing.JComboBox action;
	 private javax.swing.JComboBox newstatus;
	 private javax.swing.JLabel jLname;
	 private javax.swing.JLabel jLvalue;
	 private javax.swing.JLabel jLdes;
	 private javax.swing.JLabel jLproductType;
	 private javax.swing.JLabel jLproductSubType;
	 private javax.swing.JLabel jLE;
	 private javax.swing.JComboBox cproductType;
	 private javax.swing.JComboBox cproductSubType;
	 private javax.swing.JComboBox cLE;
	 private javax.swing.JComboBox cUSer;
	 private javax.swing.JLabel lUSer;
	 
	 private javax.swing.JLabel jLAuto;
	 private javax.swing.JCheckBox cAuto;
	 private javax.swing.JLabel jLRule;
	 private javax.swing.JTextField  cRule;
	 private javax.swing.JButton  jbuttonrule;
	 DefaultTableModel tmodel;
	 javax.swing.DefaultComboBoxModel startData; 
	    private javax.swing.JScrollPane jScrollPane1;
	    private javax.swing.JTable jTable;
	   
	    javax.swing.DefaultComboBoxModel LEDat = new javax.swing.DefaultComboBoxModel();
		 Hashtable LEData = new Hashtable();
		 javax.swing.DefaultComboBoxModel userData = new javax.swing.DefaultComboBoxModel();
		 Hashtable userDatah = new Hashtable();
	    boolean refereshTree = false;
	 public WorkFlowSetupWindow123() {
	        initComponents();
	    }
	 
	 private void initComponents() {
		 de =ServerConnectionUtil.connect("localhost", 1099,commonUTIL.getServerIP());
	   	 try {
	   		 remoteBORef = (RemoteReferenceData) de.getRMIService("ReferenceData");
				
				System.out.println(remoteBORef);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			processLEDataCombo(userData, userDatah,"users");
			cUSer = new javax.swing.JComboBox();
			lUSer = new javax.swing.JLabel("USER");
			cUSer.setModel(userData);
			
			jPanel4 = new javax.swing.JPanel();
	        jPanel2 = new javax.swing.JPanel();
	        jPanel3 = new javax.swing.JPanel();
	        jPanel5 = new javax.swing.JPanel();
	        jLAuto = new javax.swing.JLabel("Auto");
	        cAuto = new javax.swing.JCheckBox("",true);
	   	 jLRule = new  javax.swing.JLabel();
	     cRule = new javax.swing.JTextField(20);
	   	  jbuttonrule = new javax.swing.JButton("....");
	        jLname = new javax.swing.JLabel();
	        jLvalue = new javax.swing.JLabel("Action");
	        jLdes = new javax.swing.JLabel("New Status");
	        startData = new javax.swing.DefaultComboBoxModel();
		     
		      getDataOnComboBox(startData, "Status");
		      
		     
		      cstatus = new javax.swing.JComboBox();
		      cstatus.setModel(startData);
		      startData = new javax.swing.DefaultComboBoxModel();
			     
		      getDataOnComboBox(startData, "Action");
	        action = new javax.swing.JComboBox();
	        action.setModel(startData);
	        newstatus = new javax.swing.JComboBox();
	        startData = new javax.swing.DefaultComboBoxModel();
		     
		      getDataOnComboBox(startData, "Status");
		      newstatus.setModel(startData);
	        jadd = new javax.swing.JButton("ADD");
	        jremove  = new javax.swing.JButton("REMOVE");
	        jsave = new javax.swing.JButton("SAVE");
	        jPanel1 = new javax.swing.JPanel();
	        jScrollPane1 = new javax.swing.JScrollPane();
	        
	        
	        jLproductType = new  javax.swing.JLabel("Product Type");
	        jLproductSubType = new javax.swing.JLabel("Product Sub Type");
	        jLE = new javax.swing.JLabel("Legal Entity");
	        startData = new javax.swing.DefaultComboBoxModel();
	      cproductType = new javax.swing.JComboBox ();
	      getDataOnComboBox(startData, "ProductType");
	      cproductType.setModel(startData);
	     
	     
	   	cproductSubType = new javax.swing.JComboBox ();
	   	processLEDataCombo(LEDat, LEData,"LEs");
	     
	   	cproductSubType = new javax.swing.JComboBox ();
	   	 cLE = new javax.swing.JComboBox ();
	   	cLE.setModel(LEDat);
	   	jsave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                WFConfig config = new WFConfig();
                config.setProductType(cproductType.getSelectedItem().toString());
                config.setProductSubType(cproductSubType.getSelectedItem().toString());
               if( cAuto.isSelected())
                config.setAuto(1);
               else 
            	   config.setAuto(0);
                config.setCurrentStatus(cstatus.getSelectedItem().toString());
                config.setAction(action.getSelectedItem().toString());
                config.setOrgStatus(newstatus.getSelectedItem().toString());
                config.setRule(cRule.getText());
                config.setLe(((LegalEntity)LEData.get(cLE.getSelectedIndex())).getId());
                config.setUsers(((Users)userDatah.get(cUSer.getSelectedIndex())).getId());
                try {
					remoteBORef.saveWF(config);
					filltable(config,tmodel.getRowCount());
					
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                
            }

			
        });
	   	
	   	cproductType.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				String selectProduct = (String ) (cproductType.getItemAt(cproductType.getSelectedIndex()));
				startData = new javax.swing.DefaultComboBoxModel();
				getDataOnComboBox(startData, selectProduct+".subType");
				cproductSubType.setModel(startData);
			}
			
		});
	       // setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
	        jLname.setText("Current Status");
	        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
	        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
	        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
	        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
	        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("WorkFlow"));

	        String col[] = {"Current status", "Action ", "New Status","User","Auto","Rules","Product","SubProductType","LE"};
	        tmodel = new DefaultTableModel (col,0);
	        jTable = new javax.swing.JTable();
	        jTable.setModel(tmodel);
	        insertRowinTable();
	        jScrollPane1.setViewportView(jTable);
	        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
	        jPanel1.setLayout(jPanel1Layout);
	        jPanel1Layout.setHorizontalGroup(
	                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                .addGroup(jPanel1Layout.createSequentialGroup()
	                    .addContainerGap()
	                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
	                    .addContainerGap())
	            );
	            jPanel1Layout.setVerticalGroup(
	                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                .addGroup(jPanel1Layout.createSequentialGroup()
	                    .addContainerGap()
	                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
	                    .addContainerGap())
	            );
	        
	            
	           javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
	            jPanel3.setLayout(jPanel3Layout);
	            jPanel3Layout.setHorizontalGroup(
	                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
	                    .addContainerGap(192, Short.MAX_VALUE)
	                    .addComponent(jLname)
	                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
	                    .addComponent(cstatus)
	                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
	                    .addComponent(jLvalue)
	                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
	                    .addComponent(action)
	                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
	                    .addComponent(jLdes)
	                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
	                    .addComponent(newstatus)
	                    .addContainerGap())
	            );
	            jPanel3Layout.setVerticalGroup(
	                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
	                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                        .addComponent(jLname)
	                        .addComponent(cstatus)
	                        .addComponent(jLvalue)
	                        .addComponent(action)
	                        .addComponent(jLdes)
	                        .addComponent(newstatus)))
	            ); 
	            javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
	            jPanel5.setLayout(jPanel5Layout);
	            jPanel5Layout.setHorizontalGroup(
	                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
	                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
	                    .addContainerGap(192, Short.MAX_VALUE)
	                    .addComponent(jLAuto)
	                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
	                    .addComponent(cAuto)
	                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
	                    .addComponent(jLRule)
	                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
	                    .addComponent(cRule)
	                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
	                    .addComponent(jbuttonrule)
	                     .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
	                    .addComponent(lUSer)
	                     .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
	                    .addComponent(cUSer)
	                    
	                    .addContainerGap())
	            );
	            jPanel5Layout.setVerticalGroup(
	                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
	                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
	                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
	                        .addComponent(jLAuto)
	                        .addComponent(cAuto)
	                        .addComponent(jLRule)
	                        .addComponent(cRule)
	                        .addComponent(jbuttonrule)
	                        .addComponent(lUSer)
	                        .addComponent(cUSer)))
	               
	            ); 
	            
	            
	            javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
	            jPanel4.setLayout(jPanel4Layout);
	            jPanel4Layout.setHorizontalGroup(
	                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
	                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
	                    .addContainerGap(192, Short.MAX_VALUE)
	                    .addComponent(jLproductType)
	                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
	                    .addComponent(cproductType)
	                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
	                    .addComponent(jLproductSubType)
	                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
	                    .addComponent(cproductSubType)
	                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
	                    .addComponent(jLE)
	                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
	                    .addComponent(cLE)
	                    .addContainerGap())
	            );
	            jPanel4Layout.setVerticalGroup(
	                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
	                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
	                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                        .addComponent(jLproductType)
	                        .addComponent(cproductType)
	                        .addComponent(jLproductSubType)
	                        .addComponent(cproductSubType)
	                        .addComponent(jLE)
	                        .addComponent(cLE)))
	            ); 
	            
	            
	            
	            
	            
	            
	            javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
	            jPanel2.setLayout(jPanel2Layout);
	            jPanel2Layout.setHorizontalGroup(
	                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
	                    .addContainerGap(192, Short.MAX_VALUE)
	                    .addComponent(jadd)
	                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
	                    .addComponent(jremove)
	                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
	                    .addComponent(jsave)
	                    .addContainerGap())
	            );
	            jPanel2Layout.setVerticalGroup(
	                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
	                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                        .addComponent(jadd)
	                        .addComponent(jremove)
	                        .addComponent(jsave)))
	            );
	            
	            
	            
	            
	            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
	            setLayout(layout);
	            layout.setHorizontalGroup(
	                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
	                    .addContainerGap()
	                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
	                    		.addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                    		.addComponent(jPanel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                        .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                        .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                        .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
	                    .addContainerGap())
	            );
	            layout.setVerticalGroup(
	                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
	                .addGroup(layout.createSequentialGroup()
	                    .addContainerGap()
	                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                          .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
	                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
	                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
	            );
	            
	            jadd.addActionListener(new java.awt.event.ActionListener() {
	                public void actionPerformed(java.awt.event.ActionEvent evt) {
	                	
	                	
	    	            
	    	            }
	                

	    			
	            });
	            
	            
	            	
	          
	            
	        
	 }
	 
	 private void getDataOnComboBox( javax.swing.DefaultComboBoxModel combodata,String name) {
			Vector vector;
			try {
				vector = (Vector) remoteBORef.getStartUPData(name);
				if(vector.size() > 0) {
				Iterator it = vector.iterator();
		    	int i =0;
		    	while(it.hasNext()) {
		    		
		    		StartUPData data = (StartUPData) it.next();
		    	
	    		
	    			
	    		combodata.insertElementAt(data.getName(), i);
	    		i++;
	    	}	
				}
			}catch (RemoteException e) {
	    				// TODO Auto-generated catch block
	    				e.printStackTrace();
	    			}
	    	
	    	
	    }
		
	 private void processLEDataCombo( javax.swing.DefaultComboBoxModel combodata,Hashtable ids,String dataName) {
			Vector vector = null;
			try {
				if(dataName.equalsIgnoreCase("LEs"))
				vector = (Vector) remoteBORef.selectAllLs();
				if(dataName.equalsIgnoreCase("users"))
					vector = (Vector) remoteBORef.selectALLUsers();
				
				if(vector.size() > 0) {
				Iterator it = vector.iterator();
		    	int i =0;
		    	while(it.hasNext()) {
		    		if(dataName.equalsIgnoreCase("LEs")) {
		    			LegalEntity le = (LegalEntity) it.next();
		    		    combodata.insertElementAt(le.getName(), i);
		    		    ids.put(i, le);
		    		}
						if(dataName.equalsIgnoreCase("users")) {
							Users users= (Users) it.next();
							combodata.insertElementAt(users.getUser_name(), i);
			    		    ids.put(i, users);
							
						}
		    		
		    	
	    		
	    			
	    		
	    		
	    		i++;
	    	}	
				}
			}catch (RemoteException e) {
	    				// TODO Auto-generated catch block
	    				e.printStackTrace();
	    			}
	    	
	    	
	    }

	 
	 public void filltable(WFConfig config,int i) {
		 
		 try {
			tmodel.insertRow(i, new Object[]{config.getCurrentStatus(),config.getAction(),config.getOrgStatus(),((Users)remoteBORef.selectUser(config.getUsers())).getUser_name(),config.getAuto(),config.getRule(),config.getProductType(),config.getProductSubType(),((LegalEntity)remoteBORef.selectLE(config.getLe())).getName()});
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	 }
	 public void insertRowinTable() {
		 
		 try {
			 
			 Vector v1 = (Vector) remoteBORef.selectAllWF();
			 if(v1 == null)
				 return;
			 Iterator it = v1.iterator();
		    	int i =0;
		    	
	    	while(it.hasNext()) {
               WFConfig config = (WFConfig) it.next();
			tmodel.insertRow(i, new Object[]{config.getCurrentStatus(),config.getAction(),config.getOrgStatus(),((Users)remoteBORef.selectUser(config.getUsers())).getUser_name(),config.getAuto(),config.getRule(),config.getProductType(),config.getProductSubType(),((LegalEntity)remoteBORef.selectLE(config.getLe())).getName()});
	    	}
	    	repaint();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	 }
	 
			 
			 public static void main(String args[]) {
		WorkFlowSetupWindow123 test = new WorkFlowSetupWindow123();
	    	JFrame testing = new JFrame();
	    	testing.add(test);
	    	testing.setSize(700,500);
	    	testing.setVisible(true);
	    	
	    }
}


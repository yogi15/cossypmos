package apps.window.referencewindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import util.commonUTIL;
import beans.LegalEntity;
import beans.Sdi;
import beans.StartUPData;

import dsServices.RemoteReferenceData;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import apps.window.utilwindow.JDialogBoxForChoice;

/*
 * SDITab1.java
 *
 * Created on 28 Sep, 2013, 6:04:48 PM
 */

/**
 *
 * @author MPankaj
 */
public class SDITab1 extends javax.swing.JPanel {

    /** Creates new form SDITab1 */
	RemoteReferenceData remoteBORef ;
	final DefaultListModel currencyList = new DefaultListModel();
	DefaultComboBoxModel productTypeCombox = new DefaultComboBoxModel();
	DefaultComboBoxModel leModel = new DefaultComboBoxModel();
	Hashtable lekeys = new Hashtable();
	Vector sqlWhere =  new Vector();
	Vector roles = null;
	String s [] = {"id","LegalEntityName"};
	String sql = "";
	DefaultTableModel tradertablemodel = new DefaultTableModel(s,0);
	SDI1Window sdi1w = null;
	  String col [] = { "SDI ID", "MessageType", "Pay/Rec", "Format","Party Name", "Role", "AGent", "Key","Cash", "Product", "Currency"  };
      javax.swing.table.DefaultTableModel tmodel = new DefaultTableModel(col,0);
    public SDI1Window getSdi1w() {
		return sdi1w;
	}

	public void setSdi1w(SDI1Window sdi1w) {
		this.sdi1w = sdi1w;
	}

	public SDITab1(RemoteReferenceData remoteBORef,Vector roles) {
        initComponents(remoteBORef,roles);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    
    
    private void initComponents( RemoteReferenceData remoteBORef,Vector roles) {
    	this.remoteBORef = remoteBORef;
    	this.roles = roles;
    	 processlistchoice(currencyList,"Currency");
    		getMasterDataOnComboBox(productTypeCombox, "ProductType");
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jTextField1 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jButton3 = new javax.swing.JButton();
        jComboBox2 = new javax.swing.JComboBox();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        if(roles != null) {
        	Iterator its = roles.iterator();
        	jComboBox2.addItem(" ");
        	while(its.hasNext()) {
        		StartUPData role = (StartUPData) its.next();
        		jComboBox2.addItem(role.getName());
        		
        	}
        	jComboBox2.setSelectedIndex(0);
        }
        jComboBox2.addItemListener( new ItemListener() {

        	@Override
        	public void itemStateChanged(ItemEvent e) {
        		// TODO Auto-generated method stub
        		
        		for(int i=0;i<tradertablemodel.getRowCount();i++) {
        			tradertablemodel.removeRow(i);
        			
        		}
        		
        		String role = jComboBox2.getSelectedItem().toString();
        		if(!commonUTIL.isEmpty(role)) {
        		 getLEDataCombo1(tradertablemodel,role);
        		 
        		}
        		 
        		
        	}
        	   
           });
       // jTextField3.setModel(tradertablemodel)
        jTable1 = new javax.swing.JTable();

        jLabel1.setText("SD id");

        jLabel2.setText("ROLE");

        jLabel3.setText("Party");

       
        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });

        jLabel4.setText("CCY");

        jTextField4.setText("ANY");
        jTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4ActionPerformed(evt);
            }
        });

        
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				
				int rowindex = jTable1.getSelectedRow(); 
				TableModel model = jTable1.getModel();
				String sql = " id = " +  model.getValueAt(rowindex, 0).toString();
			    Vector sdis = getSDIs(sql);
			    Sdi sdi = (Sdi) sdis.elementAt(0);
			    sdi1w.openSDI(sdi);
			    
				}
        });

        jTextField1.setText("     ");
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jLabel10.setText("Products");

       jComboBox1.setModel(productTypeCombox);
     //  jComboBox1.setSelectedIndex(0);
       jComboBox1.addItemListener( new ItemListener() {

       	@Override
       	public void itemStateChanged(ItemEvent e) {
       		// TODO Auto-generated method stub
       		
       		
       		String productType = jComboBox1.getSelectedItem().toString();
       		if(!commonUTIL.isEmpty(productType)) {
       		sql = " products ='" + productType +"'";
       		 
       		}
       		 
       		
       	}
       	   
          });

        jButton3.setText("Load");
      
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	jTable1.removeAll();
            
            	DefaultTableModel tmode1t =  new DefaultTableModel(col,0);
               if(!commonUTIL.isEmpty(sql)) {
            	   sql =  " products ='" + jComboBox1.getSelectedItem().toString() + "'";
            	   sql = sql + " and currency ='"+jTextField4.getText() +"'";
            	 Vector sdis =   getSDIs(sql);
            	 if(sdis != null) {
            		 
            		 Iterator it = sdis.iterator();
              	   int i =0;
              	while(it.hasNext()) {
              		
              		Sdi sdi =	(Sdi) it.next();
              		tmode1t.insertRow(i, new Object[]{ sdi.getId(),sdi.getMessageType(),sdi.getPayrec(),sdi.getsdiformat(),getLEName(sdi.getCpId()),sdi.getRole(),getLEName(sdi.getAgentId()),sdi.getkey(),sdi.getCash(),sdi.getProducts(),sdi.getCurrency()});
              		i++;
          		
          	}	
            	 }
            	   
               }
               jTable1.setModel(tmode1t);
            }

			
        });
    
      //  jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
  jTextField3.setModel(leModel);
        jButton1.setText("jButton1");
       
        jTable1.setModel(tmodel);
       
        jScrollPane1.setViewportView(jTable1);
        final JDialogBoxForChoice choice12 = new JDialogBoxForChoice(currencyList);
        jButton1.addActionListener(new ActionListener() {

    		@Override
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
                  Object obj [] =   choice12.getObj();
                 for(int i =0;i<obj.length;i++)
                	 ss = ss + (String) obj[i] + ",";
                 if(ss.trim().length() > 0)
                 	jTextField4.setText(ss.substring(0, ss.length()-1));
                } catch (Throwable t) {
                    t.printStackTrace();
                }                
            }
    	});
    	

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField4)
                            .addComponent(jComboBox1, 0, 116, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25)))
                .addGap(481, 481, 481))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(107, 107, 107)
                .addComponent(jButton3)
                .addContainerGap(745, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 796, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 839, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>

private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {
// TODO add your handling code here:
}

private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {
// TODO add your handling code here:
}

private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {
// TODO add your handling code here:
}

private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {
// TODO add your handling code here:
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


public void getLEDataCombo1(DefaultTableModel model,String leRole) {
	Vector vector;
	try {			
		
		lekeys = null;
		lekeys = new Hashtable();
		leModel = null;
		leModel = new DefaultComboBoxModel();
			vector = (Vector) remoteBORef.selectLEonWhereClause(" role like '%"+leRole + "%'");
			
		   Iterator it = vector.iterator();
    	   int i =0;
    	while(it.hasNext()) {
    		
    		LegalEntity le =	(LegalEntity) it.next();
    		leModel.addElement(le.getName());//.insertRow(i, new Object[]{le.getId(),le.getName()});
    		lekeys.put(new Integer(i), le);
    		i++;
		
	}	
    	
	}catch (RemoteException e) {
				// TODO Auto-generated catch block
		commonUTIL.displayError("SDITab1","getLEDataCombo1", e);
			}
	jTextField3.setModel(leModel);
	
}
   public String getLEName(int id) {
	   String name = "";
	   try {
		   if(id ==  0) 
			   return name;
		LegalEntity le = (LegalEntity )remoteBORef.selectLE(id);
		name = le.getName();
	} catch (RemoteException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   return name;
   }

     public Vector getSDIs(String sql) {
    	// remoteBORef.SDIWhere
    	 Vector sdis = null;
    	 try {
    		 sdis = (Vector) remoteBORef.SDIWhere(sql);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	 return sdis;
     }
     
     public void refreshTable(String sql ) {
    	  String col [] = { "SDI ID", "MessageType", "Pay/Rec", "Format","LE", "PO", "AGent", "Key","Cash", "Product", "Currency"  };
           tmodel = new DefaultTableModel(col,0);
           
     }
    // Variables declaration - do not modify
    public javax.swing.JButton jButton1;
    public javax.swing.JButton jButton3;
    public javax.swing.JComboBox jComboBox1;
    public javax.swing.JComboBox jComboBox2;
    public javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel10;
    public javax.swing.JLabel jLabel2;
    public javax.swing.JLabel jLabel3;
    public javax.swing.JLabel jLabel4;
    public javax.swing.JPanel jPanel2;
    public javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTable jTable1;
    public javax.swing.JTextField jTextField1;
    public javax.swing.JComboBox jTextField3;
    public javax.swing.JTextField jTextField4;
    // End of variables declaration
}

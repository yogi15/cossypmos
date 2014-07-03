package apps.window.operationwindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import java.util.Iterator;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;

import javax.swing.JOptionPane;


import util.commonUTIL;

import apps.window.tradewindow.JFrameTradeWindowApplication;

import beans.StartUPData;
import beans.Trade;
import beans.Users;

import dsServices.RemoteReferenceData;
import dsServices.RemoteTrade;
import dsServices.ServerConnectionUtil;



/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * NewJFrame3.java
 *
 * Created on 29 Mar, 2013, 1:05:30 AM
 */
/**
 *
 * @author MPankaj
 */
public class SearchTrade extends javax.swing.JDialog {

    /** Creates new form NewJFrame3 */
	public boolean displagflag = false;
	public static  ServerConnectionUtil de = null;
	RemoteReferenceData remoteRef;
	RemoteTrade remoteTrade;
	String username = "";
	Users user = null;
	DefaultComboBoxModel attributes = null; 
	public SearchTrade() {
        initComponents();
    }
	/** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {
    	de =ServerConnectionUtil.connect("localhost", 1099,commonUTIL.getServerIP() );
	   	 try {
	   		remoteRef = (RemoteReferenceData) de.getRMIService("ReferenceData");
	   		remoteTrade = (RemoteTrade)	de.getRMIService("Trade");
				
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			attributes	= new javax.swing.DefaultComboBoxModel();
			processAttributeData(attributes);
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        jLabel1.setText("Trade ID");

        jTextField1.setText(" ");

        jLabel2.setText("Attribute");

        jComboBox1.setModel(attributes);

        jButton1.setText("TRADE ID");

        jButton2.setText("Attribues");

        jButton3.setText("  ");

        
        jButton1.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent arg0) {
	        	int tradeID = 0;
	        	try {
	        		if(jTextField1.getText().length() > 0)
	        		tradeID = Integer.parseInt(jTextField1.getText());
			//	TableModel model = jTable.getModel();
				
				Trade trade = null;
				try {
					trade = (Trade)  remoteTrade.selectTrade(tradeID);
					if(trade == null) {
						JOptionPane.showMessageDialog(null,"Trade Does not Exist" ,null,
	               				JOptionPane.INFORMATION_MESSAGE);
					} else {
						JFrameTradeWindowApplication tradeWindow = new JFrameTradeWindowApplication(trade.getProductType(),user);
			        	
			        	tradeWindow.setUserName(user);
			        	tradeWindow.setOpenTrade(trade);
			        //	tradeWindow
			           tradeWindow.setVisible(true);
			       //    dispose();
					}
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(trade != null)
					dispose();
	        	
	            
	        } catch(NumberFormatException e) {
	        	JOptionPane.showMessageDialog(null,"Please enter numbers only " ,null,
           				JOptionPane.INFORMATION_MESSAGE);
	        }
	        	catch(Exception e1) {
				 commonUTIL.displayError("SearchTrade", " Trade ID Search", e1);
			 }

	        }
	    });
        
        jButton2.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent arg0) {
	        	String attributes = "";
	        	String attributeName = "";
	        	String attributeValue = "";
			//	TableModel model = jTable.getModel();
	        	Trade trade = null;
				try {
					
		        		if(jTextField1.getText().length() > 0 && jComboBox1.getSelectedIndex() != -1) {
		        			attributeValue = jTextField1.getText();
		        			attributeName =  jComboBox1.getSelectedItem().toString();
		        			attributes = " attributes like '"+ attributeName+"="+attributeValue+"'";
		        		}
		        		if(attributes.length() > 0)
					trade = (Trade)  remoteTrade.selectWhere(attributes);
					if(trade == null) {
						JOptionPane.showMessageDialog(null,"Trade Does not Exist for this Attributes" ,null,
	               				JOptionPane.INFORMATION_MESSAGE);
					} else {
						JFrameTradeWindowApplication tradeWindow = new JFrameTradeWindowApplication(trade.getProductType(),user);
			        	
			        	tradeWindow.setUserName(user);
			        	tradeWindow.setOpenTrade(trade);
			        //	tradeWindow
			           tradeWindow.setVisible(true);
			       //    dispose();
					}
				
				if(trade != null)
					dispose();
		        		
	            
	        } 
	        	catch(Exception e1) {
				 commonUTIL.displayError("SearchTrade", " Trade ID Search", e1);
			 

	        }
				
	        }
	    });
        
        
        
        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField1)
                    .addComponent(jComboBox1, 0, 112, Short.MAX_VALUE)))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButton1, jButton2});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton2)
                        .addComponent(jButton3)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel1, jLabel2, jTextField1});

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButton1, jButton2});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>
    // Variables declaration - do not modify
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextField1;
    
    private void processAttributeData(DefaultComboBoxModel model) {
		// TODO Auto-generated method stub
    	Vector vector;
		try {
			vector = (Vector) remoteRef.getStartUPData("TradeAttribute");
			Iterator it = vector.iterator();
	    	int i =0;
	    	while(it.hasNext()) {
	    		
	    		StartUPData tradeAttributes = (StartUPData) it.next();
	    	
	    	
	    		model.insertElementAt(tradeAttributes.getName(), i);
	    		i++;
	    		}
	    		
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public void dispose() {
    	//this.dispose();
    }
    // End of variables declaration
	public Users getUser() {
		return user;
	}
	public void setUser(Users user) {
		this.user = user;
	}
}

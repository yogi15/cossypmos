package apps.window.staticwindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.rmi.RemoteException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;

import util.commonUTIL;

import beans.Book;
import beans.CurrencyPair;
import beans.Favorities;
import beans.LegalEntity;
import beans.StartUPData;
import beans.Users;

import dsServices.RemoteReferenceData;
import dsServices.ServerConnectionUtil;

public class FavoritiesWindow extends JFrame  {
	RemoteReferenceData remoteReference;
	 public static  ServerConnectionUtil de = null;
	 String name = "";
	 public DefaultListModel first = new DefaultListModel();
	   public DefaultListModel second = new DefaultListModel();
	   Users user = new Users();
	   
	   public Users getUser() {
		return user;
	}
	public void setUser(Users user) {
		this.user = user;
	}
	Vector firstData = new Vector();
	   Vector secondData = new Vector();
	 public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public FavoritiesWindow(String name,Users user) {
		setName(name);
		setUser(user);
        initComponents();
    }
	public FavoritiesWindow() {
	        initComponents();
	    }
	  // <editor-fold defaultstate="collapsed" desc="Generated Code">
	private void initComponents() {
		de =ServerConnectionUtil.connect("localhost", 1099,commonUTIL.getServerIP());
	   	 try {
	   		
	   		remoteReference = (RemoteReferenceData) de.getRMIService("ReferenceData");
	   		
			//	System.out.println(remoteTrade);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        firstLi = new javax.swing.JList();
        jPanel6 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        secondLi = new javax.swing.JList();

    //    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        processlistchoice2(second,getName());
        secondLi.setModel(second);
        processlistchoice(first,getName());
        firstLi.setModel(first);

    	this.addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				saveFav(getName(),secondData);
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
		
    	});
        jScrollPane3.setViewportView(firstLi);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton1.setText(">>");

        jButton2.setText("<<");
        
        jButton1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(firstLi.getSelectedIndex() >= 0) { 
				int i = firstLi.getSelectedIndex();
				second.addElement(firstLi.getSelectedValue());
			
secondLi.removeAll();
secondLi.setModel(second);
firstLi.removeAll();
				first.remove(i);
				//hashData1.put(i, value);
				firstLi.setModel(first);
			//	System.out.println(second.size());
				secondData.insertElementAt(firstData.get(i),second.size()-1);
			//	System.out.println(((LegalEntity) secondData.get(second.size()-1)).getName());
				firstData.removeElementAt(i);
			
        
				}
			}
    		
    	});
        
        jButton2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
			
        
				if(secondLi.getSelectedIndex() >= 0) { 
					int i = secondLi.getSelectedIndex();
					first.add(first.size(), secondLi.getSelectedValue());
					firstLi.removeAll();
					firstLi.setModel(first);
					secondLi.removeAll();
					second.remove(i);
					
					secondLi.setModel(second);
				//	System.out.println(cmodList3.size());
					firstData.insertElementAt(secondData.get(i), first.getSize()-1);
					//System.out.println(((LegalEntity) firstData.get(first.getSize()-1)).getName());
					secondData.removeElementAt(i);
				
					}
				
			}
    		
    	});
    	
        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 46, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 46, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addContainerGap(96, Short.MAX_VALUE))
        );

       
        jScrollPane2.setViewportView(secondLi);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
                            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        pack();
    }
	    /**
	     * @param args the command line arguments
	     */
	    public static void main(String args[]) {
	        /* Set the Nimbus look and feel */
	        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
	        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
	         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
	         */
	        try {
	            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
	                if ("Nimbus".equals(info.getName())) {
	                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
	                    break;
	                }
	            }
	        } catch (ClassNotFoundException ex) {
	            java.util.logging.Logger.getLogger(FavoritiesWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	        } catch (InstantiationException ex) {
	            java.util.logging.Logger.getLogger(FavoritiesWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	        } catch (IllegalAccessException ex) {
	            java.util.logging.Logger.getLogger(FavoritiesWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
	            java.util.logging.Logger.getLogger(FavoritiesWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	        }
	        //</editor-fold>

	        /* Create and display the form */
	        java.awt.EventQueue.invokeLater(new Runnable() {

	            public void run() {
	                new FavoritiesWindow().setVisible(true);
	            }
	        });
	    }
	    // Variables declaration - do not modify
	    private javax.swing.JButton jButton1;
	    private javax.swing.JButton jButton2;
	    private javax.swing.JList secondLi;
	    private javax.swing.JList firstLi;
	    private javax.swing.JPanel jPanel4;
	    private javax.swing.JPanel jPanel5;
	    private javax.swing.JPanel jPanel6;
	    private javax.swing.JScrollPane jScrollPane2;
	    private javax.swing.JScrollPane jScrollPane3;
	    // End of variables declaration
	    
	    
	    public void saveFav(String name,Vector favData) {
	    	if(favData != null ) {
	    	 Iterator its =	favData.iterator();
	    	 while(its.hasNext()) {
	    		 Favorities fav = new Favorities();
	    	 
	    		 if(name.trim().equalsIgnoreCase("Book")) {
	    			 fav.setType(name);
	    			 Book book =  (Book) its.next();
	    			 fav.setTypeName(book.getBook_name());
	    			 fav.setTypeValue(new Integer(book.getBookno()).toString());
	    			 fav.setUserId(user.getId());
	    		 }else  if(name.trim().equalsIgnoreCase("CounterParty") || name.trim().equalsIgnoreCase("Trader")) {
	    			 fav.setType(name);
	    			 LegalEntity le =  (LegalEntity) its.next();
	    			 fav.setTypeName(le.getName());
	    			 fav.setTypeValue(new Integer(le.getId()).toString());
	    			 fav.setUserId(user.getId());
	    		 }else if(name.trim().equalsIgnoreCase("CurrencyPair")) {
	    			 fav.setType(name);
	    			 CurrencyPair cp =  (CurrencyPair) its.next();
	    			 fav.setTypeName(cp.getPrimary_currency()+"/"+cp.getQuoting_currency());
	    			 fav.setTypeValue(cp.getPrimary_currency()+"/"+cp.getQuoting_currency());
	    			 fav.setUserId(user.getId());
	    		 } else { 
	    			 fav.setType(name);
	    			 StartUPData tenor =  (StartUPData) its.next();
	    			 fav.setTypeName(tenor.getName());
	    			 fav.setTypeValue(tenor.getName());
	    			 fav.setUserId(user.getId());
	    		 }
	    		 try {
					remoteReference.saveFavourites(fav);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	 }
	    		
	    	}
	    }
	    
	    
	    public void processlistchoice(DefaultListModel list,String name ) {
	    	Vector vector = null;
			try {
			 if(name.trim().equalsIgnoreCase("Book")) {
				vector = (Vector) remoteReference.selectALLBooks();
			 }else  if(name.trim().equalsIgnoreCase("CounterParty") || name.trim().equalsIgnoreCase("Trader")) {
				 vector = (Vector) remoteReference.selectLEonWhereClause(" Role LIKE '%" +name+"%'");
			 } else if(name.trim().equalsIgnoreCase("CurrencyPair")) {
				 vector = (Vector) remoteReference.selectALLCurrencyPair();
			 } else {
				 vector = (Vector) remoteReference.getStartUPData("Tenor");
			 }
				
				if(vector.size() > 0) {
				Iterator it = vector.iterator();
		    	int i =0;
		    	while(it.hasNext()) {
		    		 if(name.trim().equalsIgnoreCase("Book")) {
		    			 Book data = (Book) it.next();
			 				list.addElement(data.getBook_name());
			 			
			 				firstData.insertElementAt(data,i);
			 				
		 			 }else  if(name.trim().equalsIgnoreCase("CounterParty") || name.trim().equalsIgnoreCase("Trader"))  {
		 				LegalEntity data = (LegalEntity) it.next();
		 				list.addElement(data.getName());
		 				firstData.insertElementAt(data,i);	
		 			 } else if(name.trim().equalsIgnoreCase("CurrencyPair")) {
		 				CurrencyPair data = (CurrencyPair) it.next();
		 				list.addElement(data.getPrimary_currency()+"/"+data.getQuoting_currency());
		 				firstData.insertElementAt(data,i);
		 			 } else {
		 				StartUPData data = (StartUPData) it.next();
		 				list.addElement(data.getName());
		 				firstData.insertElementAt(data,i);
		 			 }
		    		
		    	
	    		
	    			
		    		

	    		i++;
	    	}	
				}
			}catch (RemoteException e) {
	    				// TODO Auto-generated catch block
	    				e.printStackTrace();
	    			
		}catch(Exception e) {
			commonUTIL.displayError("JFrameReportApplication","processlistchoice", e);
		}
	    	
	    }
	    
	    public void processlistchoice2(DefaultListModel list,String name ) {
	    	Vector vector = null;
			try {
				Favorities Fav = new Favorities();
			Fav.setType(name);
			Fav.setUserId(user.getId());
				vector = (Vector) remoteReference.selectFavourites(Fav);
			 if(vector.size() > 0) {
				Iterator it = vector.iterator();
		    	int i =0;
		    	while(it.hasNext()) {
		    		     	 Favorities data = (Favorities) it.next();
		    		     	 if(name.trim().equalsIgnoreCase("CounterParty") || name.trim().equalsIgnoreCase("Trader"))  {
			 				            list.addElement(data.getTypeName());
			 				            LegalEntity le = new LegalEntity();
			 				            le.setId(new Integer(data.getTypeValue()).intValue());
			 				            le.setName(data.getTypeName());
			 				            secondData.add(i, le);
		    		     	 }if(name.trim().equalsIgnoreCase("Book"))  {
		 				            list.addElement(data.getTypeName());
		 				            Book book = new Book();
		 				            book.setBookno(new Integer(data.getTypeValue()).intValue());
		 				            book.setBook_name(data.getTypeName());
		 				           secondData.add(i, book);
	    		     	 }if(name.trim().equalsIgnoreCase("Tenor"))  {
	 				            list.addElement(data.getTypeName());
	 				            StartUPData sdata = new StartUPData();
	 				           sdata.setName(data.getTypeValue());
	 				          sdata.setValue(data.getTypeName());
	 				           secondData.add(i, sdata);
 		     	 }if(name.trim().equalsIgnoreCase("CurrencyPair"))  {
			            list.addElement(data.getTypeName());
			            CurrencyPair cpair = new CurrencyPair();
			            cpair.setPrimary_currency(data.getTypeValue().substring(0, 3));
			            cpair.setQuoting_currency(data.getTypeName().substring(4, data.getTypeName().length()));
			           secondData.add(i, cpair);
   	 }
			 		  	}	
				}
			
	    			
		}catch(Exception e) {
			commonUTIL.displayError("JFrameReportApplication","processlistchoice2  ", e);
		}
	    	
	    }
	}

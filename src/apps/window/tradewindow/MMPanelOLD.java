package apps.window.tradewindow;




import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

import productPricing.MMPricing;
import productPricing.Pricer;
import productPricing.pricingUtil.MMCashFlow;
import productPricing.pricingUtil.frequencyUtil;

import java.util.Collection;

import util.NumericTextField;
import util.commonUTIL;
import util.common.DateU;
import dsServices.RemoteProduct;
import dsServices.RemoteReferenceData;
import dsServices.ServerConnectionUtil;
import apps.window.referencewindow.DateCellEditor12;
import apps.window.utilwindow.AmortizationPanel;
import beans.Coupon;
import beans.Product;
import beans.StartUPData;
import beans.Trade;


public class MMPanelOLD extends CommonPanel {
   
	RemoteProduct remoteProduct;
	public static  ServerConnectionUtil de = null;
	
	 javax.swing.DefaultComboBoxModel productData = new javax.swing.DefaultComboBoxModel();
	 RemoteReferenceData remoteBORef;
	 DefaultTableModel t1model;
	 DefaultTableModel t2model;
	 DefaultTableModel couponModel;
	 DefaultTableModel amorModel;
	 MMTradePanel tradevalue = null;
	 Hashtable productID = new Hashtable();
	 DecimalFormat format = new DecimalFormat("##,###,#######.##");
	 Product product = null;
	 Coupon coupon = null;
	 RemoteReferenceData referenceData;
	 Vector cashFlows = null;
	 MMPricing pricing =  new MMPricing();
	 DefaultTableModel tradePrice = null;
	 Vector v1 = null;
 	Vector ve1 = null;
 	Vector ve2 = null;
 	Vector ve3 = null;
 	Vector ve4 = null;
 	Vector ve5 = null;
 	Vector ve6 = null;
 	Vector dateRoll = null;
 	Vector ve7 = null;
 	 AmortizationPanel amortizatwindow = null;
 	JComboBox comboBox11 = null;
	 public MMPanelOLD() {
	        initComponents();
	    }

	 private void initComponents() {
		 coupon = new Coupon();
		 product = new Product();
	    	jPanel1 = new javax.swing.JPanel();
	        jPanel3 = new javax.swing.JPanel();
	        buysell = new javax.swing.JLabel();
	        bondProduct = new javax.swing.JTextField();
	        PriceButton = new javax.swing.JButton();
	        Quantity = new javax.swing.JLabel();
	       
	        nominal = new NumericTextField(10,format);
			nominal.setValue(0);
	        jPanel4 = new javax.swing.JPanel();
	        jPanel5 = new javax.swing.JPanel();
	        jPanel6 = new javax.swing.JPanel();
	        jPanel7 = new javax.swing.JPanel();
	        jScrollPane1 = new javax.swing.JScrollPane();
	        jTable1 = new javax.swing.JTable();
	        jScrollPane2 = new javax.swing.JScrollPane();
	        jTable2 = new javax.swing.JTable();
	        jScrollPane3 = new javax.swing.JScrollPane();
	        jScrollPane4 = new javax.swing.JScrollPane();
	        amortization = new javax.swing.JTable();
	        jTable3 = new javax.swing.JTable();
	        jTabbedPane3 = new javax.swing.JTabbedPane();
	        bondProduct.setEditable(false);
	        buysell.setText("LOAN");
	        de =ServerConnectionUtil.connect("localhost", 1099,commonUTIL.getServerIP());
		   	 try {
		   		remoteProduct = (RemoteProduct) de.getRMIService("Product");
		   	 referenceData = (RemoteReferenceData) de.getRMIService("ReferenceData");
		 
					
					//System.out.println(remoteProduct);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        processDataCombo1(productData,productID);
	       // bondProduct.setModel(productData);
	        PriceButton.setText("Price");
	       
	        Quantity.setText("Nominal");

	       // bondProduct.setModel(productData);
	        PriceButton.setText("Price");
	       
	        Quantity.setText("Nominal");
            nominal.setText("0");
	        
	        
	        String col1[] = {"Product Details    ", "Value      "};
	        String amorcol[] = {"Field Details    ", "Value      "};
	        amorModel = new DefaultTableModel (amorcol,0);
	        t1model = new DefaultTableModel (col1,0);
	        String col2[] = {"Column Name    ", "Value      "};
	        
	        String priceName [] =    {"TradeLeg ", " Value "};
            
	         tradePrice = new DefaultTableModel(priceName,0);
	       
	        String col3[] = {" Floating Data   ", "Value      "};
	        couponModel = new DefaultTableModel (col3,0);
	        final DateCellEditor12 startDateC = new DateCellEditor12();
	    	  final DateCellEditor12 endDateC = new DateCellEditor12();
	    	  
	    	 
	    	  
	    	  try {
	  			v1 = (Vector)	  referenceData.getStartUPData("Currency");
	  			ve2=(Vector)	  referenceData.getStartUPData("DayCount");
	  			ve3=(Vector)	  referenceData.getStartUPData("Period");
	  			ve4=(Vector)	  referenceData.getStartUPData("Amortizing");
	  			ve5=(Vector)	  referenceData.getStartUPData("PaymentFRQ");
	  			ve6=(Vector)	  referenceData.getStartUPData("Tenor");
	  			ve7=(Vector)	  referenceData.getStartUPData("INDEX");
	  			dateRoll=(Vector)	  referenceData.getStartUPData("DateRoll");
	    	  }catch (RemoteException e) {
	  			// TODO Auto-generated catch block
	  			e.printStackTrace();
	  		}
	        //jTable1.setModel(t1model);
	    	  JComboBox currencycomboBox11 = new JComboBox( convertVectortoSringArray(v1) );
	    	  JComboBox dayCountcomboBox11 = new JComboBox( convertVectortoSringArray(ve2) );
	    	final  JComboBox tenorcomboBox11 = new JComboBox( convertVectortoSringArray(ve6) );
	    	  JComboBox indexcomboBox11 = new JComboBox( convertVectortoSringArray(ve7) );
	    	   comboBox11 =  new JComboBox( convertVectortoSringArray(ve5) );
	      	JComboBox comboBox21 = new JComboBox( convertVectortoSringArray(dateRoll) );
	    	JComboBox Period = new JComboBox( convertVectortoSringArray(ve3) );
	    	JComboBox Amortizing = new JComboBox( convertVectortoSringArray(ve4) );
	    	JCheckBox termAsDate = new JCheckBox("Term As Date");
	    	termAsDate.setSelected(true);
	    	JCheckBox termAsLoan = new JCheckBox("Term As Loan");
	    	
	    	 JComboBox couponType = new JComboBox();
		  		couponType.addItem(new String("Fixed"));
		  		couponType.addItem(new String("Float"));
		  		
	        final DefaultCellEditor dce12 = new DefaultCellEditor( comboBox11 );
	    	  final DefaultCellEditor dce13 = new DefaultCellEditor( comboBox21 );
	    	  final DefaultCellEditor perioddce13 = new DefaultCellEditor( Period );
	    	 
	    	  final DefaultCellEditor amortizingdce13 = new DefaultCellEditor( Amortizing );
	    	  final DefaultCellEditor couponTypedce13 = new DefaultCellEditor( couponType );
	    	  final DefaultCellEditor currencydce13 = new DefaultCellEditor( currencycomboBox11 );
	    	  final DefaultCellEditor dayCountdce13 = new DefaultCellEditor( dayCountcomboBox11 );
	    	  final DefaultCellEditor tenorce13 = new DefaultCellEditor( tenorcomboBox11 );
	    	  final DefaultCellEditor indexdce13 = new DefaultCellEditor( indexcomboBox11 );
	    	  final DefaultCellEditor termAsDatece13 = new DefaultCellEditor( termAsDate );
	    	  final DefaultCellEditor termAsLoandce13 = new DefaultCellEditor( termAsLoan );
	    	  
	    	  comboBox11.addItemListener(new ItemListener() {

		  			@Override
		  			public void itemStateChanged(ItemEvent e) {
		  				
		  			}
	    	  });
	    	  tenorcomboBox11.addItemListener(new ItemListener() {

	  			@Override
	  			public void itemStateChanged(ItemEvent e) {
	  				// TODO Auto-generated method stub
	  				String tenor = (String) tenorcomboBox11.getSelectedItem();
	  			//	Period period = Period.valueOf(tenor);
	  				 String date = "";
	  				if( startDateC.getCellEditorValue() != null) {
	  					date = startDateC.getCellEditorValue().toString();
	  				} else  {
	  					date = (String) jTable1.getValueAt( 0, 1);
	  					if(date == null || (date.trim().isEmpty()) || date.length() <= 0)  {
	  						commonUTIL.showAlertMessage("Enter Issue Date ");
	  						return;
	  					}
	  				}
	 
	  				DateU dateissueDate = DateU.valueOf(commonUTIL.stringToDate(date, false));
	  				dateissueDate.convertToCode(tenor);
	  			    endDateC.setCellEditorValue(dateissueDate.getDate());
	  			    jTable1.setValueAt(commonUTIL.getDateFormat(dateissueDate.getDate()), 1, 1);
	  			}
	  			
	  		});
	    	 
				
				
		        jTable3  = new javax.swing.JTable(couponModel)    	{
							      		//  Determine editor to be used by row
					public TableCellEditor getCellEditor(int row, int column)
					{
						int modelColumn = convertColumnIndexToModel( column );	    			
					if (modelColumn == 1 && (row == 1)) {
						 TableCellEditor t1 = ((TableCellEditor) indexdce13);     
						return (TableCellEditor)t1;
					}
					else   {
						return super.getCellEditor(1, 0);
					}
					
					}
				};
		        jTable2  = new javax.swing.JTable(tradePrice)    	{
		          			      		//  Determine editor to be used by row
		      		public TableCellEditor getCellEditor(int row, int column)
		      		{
		      			int modelColumn = convertColumnIndexToModel( column );	    			
		    			if (modelColumn == 1 && (row == 0 )) {
		    				 TableCellEditor t1 = ((TableCellEditor) couponTypedce13);     
		   				return (TableCellEditor)t1;
		    			}if (modelColumn == 1 && (row == 1)) {
		    				 TableCellEditor t1 = ((TableCellEditor) currencydce13);
		    			return (TableCellEditor)t1;
		    			}if (modelColumn == 1 && (row == 3)) {
		    				 TableCellEditor t1 = ((TableCellEditor) dayCountdce13);
		    			return (TableCellEditor)t1;
		    			} if (modelColumn == 1 && (row == 6)) {
							 TableCellEditor t1 = ((TableCellEditor) tenorce13);
								return (TableCellEditor)t1;
								}
		    			else   {
		    				return super.getCellEditor(1, 0);
		    			}
		    			
		      		}
		      		
		      		
		      	};
		      	
		      	
		      	
	    	  jTable1  = new javax.swing.JTable(t1model)
	          
	      	{
	          	
	              
	              
	      		//  Determine editor to be used by row
	      		public TableCellEditor getCellEditor(int row, int column)
	      		{
	      			int modelColumn = convertColumnIndexToModel( column );
	      			
	    			
	    			if (modelColumn == 1 && row == 0 ) {
	    				 TableCellEditor t1 = ((TableCellEditor) startDateC);
	    				 
   			        
	   			       
	   				return (TableCellEditor)t1;
	   				
	    			}if (modelColumn == 1 && (row == 1)) {
	    				 TableCellEditor t1 = ((TableCellEditor) endDateC);
	    			        
		   			       
	   				return (TableCellEditor)t1;
	    			}
	    			if (modelColumn == 1 && (row == 2)) {
	    				 TableCellEditor t1 = ((TableCellEditor) dce12);
	    			        
		   			       
	   				return (TableCellEditor)t1;
	    			}if (modelColumn == 1 && (row == 3)) {
	    				 TableCellEditor t1 = ((TableCellEditor) dce13);
	    			        
		   			       
	   				return (TableCellEditor)t1;
	    			} if (modelColumn == 1 && (row == 4)) {
	    				 TableCellEditor t1 = ((TableCellEditor) perioddce13);
	    			        
		   			       
	   				return (TableCellEditor)t1;
	    			} if (modelColumn == 1 && (row == 5)) {
	    				 TableCellEditor t1 = ((TableCellEditor) amortizingdce13);
	    			        
		   			       
	   				return (TableCellEditor)t1;
	    			} 
	    			else   {
	    				 
	    				return super.getCellEditor(1, 0);
	    			}
	    			    			
	    			
	    		
	      		}
	      	};
	      
	        
	                
	       
	        jScrollPane1.setViewportView(jTable1);
	        jScrollPane2.setViewportView(jTable2);
	        jScrollPane3.setViewportView(jTable3);
	        jScrollPane4.setViewportView(amortization);
	       amortizatwindow = new AmortizationPanel(ve5,ve6);
	        jTabbedPane3.add("ProductDetails",jScrollPane1);
	        jTabbedPane3.add("AmortizeDetails",amortizatwindow);
	        
	     
	        
	        
	        
PriceButton.addActionListener(new ActionListener() {

				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					if(product != null) {
						Trade trade = new Trade();
						//commonUTIL.showAlertMessage(amortizatwindow.getAmoritization());
						trade.setTradeDate(tradevalue.tradeDate.getText());
				        trade.setDelivertyDate(tradevalue.tsettlement.getText());
				        trade.setQuantity(product.getQuantity());// in cash we have trade nominal equal to product nominal;
				        trade.setNominal(product.getQuantity());
				        	validateProductData();
				        	trade.setAmoritizationData(amortizatwindow.getAmoritization());
						calculatePrice(pricing,trade,product,coupon);
				      
				       
					} else {
						product = new Product();
						coupon = new Coupon();
						if(!validateProductData()) {
							Trade trade = new Trade();
						//commonUTIL.showAlertMessage(amortizatwindow.getAmoritization());
						trade.setTradeDate(tradevalue.tradeDate.getText());
				        trade.setDelivertyDate(tradevalue.tsettlement.getText());
				        trade.setQuantity(product.getQuantity());// in cash we have trade nominal equal to product nominal;
				        trade.setNominal(product.getQuantity());
				        	
				        	trade.setAmoritizationData(amortizatwindow.getAmoritization());
						calculatePrice(pricing,trade,product,coupon);
						}
							
					}
					
				}
				
			});
	        

buysell.addMouseListener(new MouseListener()  {

	@Override
	public void mouseClicked(MouseEvent e) {
		if(buysell.getText().equalsIgnoreCase("LOAN")) 
			buysell.setText("DEPOSIT");
			else 
				buysell.setText("LOAN");
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	

	
	
});
setTableValuesBlank();
	        
	        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
	        jPanel5.setLayout(jPanel5Layout);
	        jPanel5Layout.setHorizontalGroup(
	        		jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(jPanel5Layout.createSequentialGroup()
	                .addContainerGap()
	                .addComponent(jTabbedPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 534, Short.MAX_VALUE)
	                .addGap(18, 18, 18))
	        );
	        jPanel5Layout.setVerticalGroup(
	        		jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(jPanel5Layout.createSequentialGroup()
	                .addGap(22, 22, 22)
	                .addComponent(jTabbedPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
	                .addContainerGap())
	        );

	        
	        
	        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
	        jPanel6.setLayout(jPanel6Layout);
	        jPanel6Layout.setHorizontalGroup(
	        		jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
	                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
	                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
	        );
	        jPanel6Layout.setVerticalGroup(
	        		jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(jPanel6Layout.createSequentialGroup()
	                .addContainerGap()
	                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
	                .addContainerGap(21, Short.MAX_VALUE))
	        );


	     //   jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Price"));
	        
	        

	        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
	        jPanel7.setLayout(jPanel7Layout);
	        jPanel7Layout.setHorizontalGroup(
	        		jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(jPanel7Layout.createSequentialGroup()
	                .addContainerGap()
	                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
	                .addContainerGap())
	        );
	        jPanel7Layout.setVerticalGroup(
	        		jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(jPanel7Layout.createSequentialGroup()
	                .addContainerGap()
	                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE)
	                .addContainerGap())
	        );
	        
	  //      jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Coupon Data"));
	        
	        JPanel jPanel10 = new JPanel();
			javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
	        jPanel10.setLayout(jPanel10Layout);
	        jPanel10Layout.setHorizontalGroup(
	            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(jPanel10Layout.createSequentialGroup()
	                .addGap(10, 10, 10)
	                .addComponent(buysell)
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
	                .addComponent(bondProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                .addComponent(PriceButton)
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
	                .addComponent(Quantity)
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                .addComponent(nominal, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
	                .addContainerGap())
	        );
	        jPanel10Layout.setVerticalGroup(
	            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
	                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                    .addComponent(bondProduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                    .addComponent(PriceButton)
	                    .addComponent(Quantity)
	                    .addComponent(nominal, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
	                    .addComponent(buysell))
	                .addContainerGap())
	        );
	        
	        
	      
	           

	        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(this);
	        this.setLayout(jPanel4Layout);
	        jPanel4Layout.setHorizontalGroup(
	            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(jPanel4Layout.createSequentialGroup()
	                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                    .addGroup(jPanel4Layout.createSequentialGroup()
	                        .addGap(16, 16, 16)
	                        .addComponent(buysell)
	                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                        .addComponent(bondProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE)
	                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                        .addComponent(PriceButton, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
	                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
	                        .addComponent(Quantity)
	                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                        .addComponent(nominal, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE))
	                    .addGroup(jPanel4Layout.createSequentialGroup()
	                        .addContainerGap()
	                        .addComponent(jTabbedPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 578, Short.MAX_VALUE)))
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
	                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
	                .addGap(175, 175, 175))
	            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                .addGroup(jPanel4Layout.createSequentialGroup()
	                    .addGap(756, 756, 756)
	                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
	                    .addContainerGap(20, Short.MAX_VALUE)))
	        );
	        jPanel4Layout.setVerticalGroup(
	            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(jPanel4Layout.createSequentialGroup()
	                .addContainerGap()
	                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
	                    .addGroup(jPanel4Layout.createSequentialGroup()
	                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                            .addComponent(bondProduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                            .addComponent(PriceButton)
	                            .addComponent(Quantity)
	                            .addComponent(nominal, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
	                            .addComponent(buysell))
	                        .addGap(18, 18, 18)
	                        .addComponent(jTabbedPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)))
	                .addContainerGap())
	            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                .addGroup(jPanel4Layout.createSequentialGroup()
	                    .addContainerGap()
	                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
	                    .addContainerGap()))
	        );

	 }
	 public int getProductKey(Hashtable t , int id) {
			int i = 0;
			Set set = t.entrySet();
		    Iterator it = set.iterator();
		    while (it.hasNext()) {
		      Map.Entry entry = (Map.Entry) it.next();
		      Product le =  ((Product) entry.getValue());
		     if(id == le.getId())
		    	 i = ((Integer) entry.getKey()).intValue();
		    }
	       return i;	    

		
			
		}
		public void buildTrade1(Trade trade,String actionType) {
			
			
		}
		private Product returnProducID(int indexid,Hashtable h) {
	    	return ((Product) h.get(indexid));
	    	
	   
	    }
		private Product getProdcutToOpenTrade(int prouductID,Hashtable products) {
			Enumeration keys;
		    keys=products.elements();
		    product = null;
		    while(keys.hasMoreElements()) {
		    	Product prod = (Product) keys.nextElement();
		    	if(prod.getId() == prouductID) {
		    		product = prod;
		    		break;
		    	}
		    		
		    }
		    if(product == null ) {
		    	try {
					product = (Product) remoteProduct.selectProduct(prouductID);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	 productID.put(productID.size()+1, product);
				
				
		    }
	    	return product;
	   
	    }
		
		private void processTableData(DefaultTableModel model) {
			// TODO Auto-generated method stub
	    	Vector vector;
			try {
				vector = (Vector) referenceData.getStartUPData("TradePrice");
				Iterator it = vector.iterator();
		    	int i =0;
		    	while(it.hasNext()) {
		    		
		    		StartUPData tradeAttributes = (StartUPData) it.next();
		    	
		    		model.insertRow(i, new Object[]{tradeAttributes.getName()," "});
		    		i++;
		    		}
		    		
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	 
	 private void processDataCombo1( javax.swing.DefaultComboBoxModel combodata,Hashtable ids) {
			Vector vector;
			try {
				String sql = " producttype ='MM' and   productname like 'CASH%'";
				vector = (Vector) remoteProduct.selectProductWhereClaus(sql);
				Iterator it = vector.iterator();
		    	int i =0;
		    	while(it.hasNext()) {
	    		Product product = (Product) it.next();
	    		combodata.insertElementAt(product.getProductname(), i);
	    		ids.put(i, product);
	    		i++;
	    	}	
			}catch (RemoteException e) {
	    				// TODO Auto-generated catch block
	    				e.printStackTrace();
	    			}
	    	
	    	
	    }
		
	 
	@Override
	public void buildTrade(Trade trade, String actionType) {
		if(actionType.equalsIgnoreCase("NEW")) {
			product = new Product();
			coupon = new Coupon();
			///bondProduct.setSelectedIndex(-1);
			trade.setProductId(0);
			trade.setQuantity(0);
			trade.setType(buysell.getText());
			trade.setDelivertyDate("");
			trade.setEffectiveDate("");
		    trade.setTradedesc("");
		    trade.setTradedesc1("");
		    nominal.setText("0");
		    jTable1.setValueAt(new String(commonUTIL.dateToString(commonUTIL.getCurrentDate())), 0, 1);
		    jTable1.setValueAt(new String(""), 1, 1);
		    jTable1.setValueAt(new String("0"), 2, 1);
		    jTable1.setValueAt(new String("PA"), 3, 1);
		    jTable1.setValueAt(new String(""), 4, 1);
		    jTable1.setValueAt(new String(""), 5, 1);
		    
		    jTable2.setValueAt(new String(""), 0, 1);
		    jTable2.setValueAt(new String(""), 1, 1);
		    jTable2.setValueAt(new String(""), 2, 1);
		    jTable2.setValueAt(new String(""), 3, 1);
		    jTable2.setValueAt(new String(""), 4, 1);
		    jTable2.setValueAt(new String(""), 5, 1);
		    jTable2.setValueAt(new String(""), 6, 1);
		    
		    jTable3.setValueAt(new String("0"), 0, 1);
		    jTable3.setValueAt(new String(""), 1, 1);
		    jTable3.setValueAt(new String(""), 3, 1);
		    jTable3.setValueAt(new String("0"), 4, 1);
		    
		    
		    
		    
			
		} else {
			if(!validateProductData()) {
				trade.setProductId(0);
				return;
			}
		
		
		try {
			int	productId = 0;
			if(actionType.equalsIgnoreCase("SAVE"))	{
						
								productId = product.getId();
							
								remoteProduct.updateProduct(product, coupon);
						
			
			} else {
				        product.setId(0);
						productId  = remoteProduct.saveProduct(product,coupon);
			}
			if(productId > 0) {
				 product.setId(productId);
			     bondProduct.removeAll();
			     productData.insertElementAt(product.getProductname(),productData.getSize());
			     bondProduct.setText(product.getProductname());
			     trade.setProductId(product.getId());
				 trade.setNominal(product.getQuantity());
				 trade.setQuantity(product.getQuantity());
				 if(buysell.getText().equalsIgnoreCase("LOAN")) {
					 trade.setType("SELL");
				}else  {
						trade.setType("BUY");
				}
						
				trade.setDelivertyDate(jTable1.getValueAt(0, 1).toString());
				trade.setEffectiveDate(trade.getTradeDate());
				trade.setTradedesc(product.getProductname());
				trade.setTradedesc1(product.getProdcutShortName());
				if(!commonUTIL.isEmpty(amortizatwindow.getAmoritization())) {
				    	trade.setAmoritizationData(amortizatwindow.getAmoritization());
				    }
				   
			    }
			 
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   
		
	  
		}
		
	}

	@Override
	public void openTrade(Trade trade) {
		// TODO Auto-generated method stub
		
			
			
			if(trade.getType().equalsIgnoreCase("SELL")) {
				buysell.setText("LOAN");
				}else  {
					buysell.setText("DEPOSIT");
				}
		//	System.out.println(trade.getQuantity());
		//	startDate.setText(trade);   //ppppppppppppppppppppppp
			nominal.setText(new Double(trade.getNominal()).toString());
			setTableValues(getProdcutToOpenTrade(trade.getProductId(),productID));
			bondProduct.setText(product.getProductname());
			amortizatwindow.setvalues(trade.getAmoritizationData());
		
		
		
	}
	public void setTableValues(Product product) {
		Vector coupons = null;
		
		try {
			coupons = (Vector) remoteProduct.getCoupon(product.getId());
				coupon = (Coupon) coupons.elementAt(0);
				//System.out.println(remoteProduct);
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		 jTable1.setValueAt(product.getIssueDate(), 0, 1);
		
		    jTable1.setValueAt(product.getMarturityDate(), 1, 1);
		    jTable1.setValueAt(coupon.getCouponFrequency(), 2, 1);
		    jTable1.setValueAt(coupon.getBusinessDayConvention(), 3, 1);
		    jTable1.setValueAt(coupon.getActivefrom(), 4, 1);
		    jTable1.setValueAt(product.getAttributes(), 5, 1);
		    jTable2.setValueAt(coupon.getCouponType(), 0, 1);
		    jTable2.setValueAt(coupon.getCCY(), 1, 1);
		    jTable2.setValueAt(product.getTenor(), 6, 1);
		    jTable2.setValueAt(coupon.getFixedRate(), 2, 1);
		    jTable2.setValueAt(coupon.getDayCount(), 3, 1);
		    jTable2.setValueAt(new String(""), 4, 1);
		    jTable2.setValueAt(new String(""), 5, 1);
		 
		    jTable3.setValueAt(coupon.getYieldDecimals(), 0, 1);
		    jTable3.setValueAt(coupon.getYieldMethod(), 1,1);
		   
		    jTable3.setValueAt(new String("0"), 2, 1);
		    nominal.setText(new Double(product.getQuantity()).toString());
		    
	}
	@Override
	public void setPanelValue(CommonPanel tradeValue) {
		// TODO Auto-generated method stub
		tradevalue = (MMTradePanel) tradeValue;
	}

	public MMPricing getPricing() {
		return pricing;
	}

	public void setPricing(MMPricing pricing) {
		this.pricing = pricing;
	}

	@Override
	public Pricer getPricer() {
		// TODO Auto-generated method stub
		return getPricing();
	}
	
	@Override
	public void setTradePanelValue(CommonPanel tradeValue) {
		// TODO Auto-generated method stub
		tradeValue = (MMTradePanel) tradeValue;
	}

	@Override
	public Collection getCashFlows() {
		// TODO Auto-generated method stub
		return cashFlows;
	}

	
	private void setTableValuesBlank() {
		t1model.insertRow(t1model.getRowCount(),  new Object[]{ "Issue Date", ""});
		
		t1model.insertRow(t1model.getRowCount(),  new Object[]{ "End Date",""});
		
		t1model.insertRow(t1model.getRowCount(),  new Object[]{ "Frequency",""});
		t1model.insertRow(t1model.getRowCount(),  new Object[]{ "Date Roll",""});
		t1model.insertRow(t1model.getRowCount(),  new Object[]{ "Period",""});
		t1model.insertRow(t1model.getRowCount(),  new Object[]{ "Amortizing",""});
		
		tradePrice.insertRow(tradePrice.getRowCount(),  new Object[]{ "Type", ""});
		tradePrice.insertRow(tradePrice.getRowCount(),  new Object[]{ "Ccy", ""});
		tradePrice.insertRow(tradePrice.getRowCount(),  new Object[]{ "Rate", ""});
		tradePrice.insertRow(tradePrice.getRowCount(),  new Object[]{ "DayCount", ""});
		tradePrice.insertRow(tradePrice.getRowCount(),  new Object[]{ "SalesMargin", ""});
		tradePrice.insertRow(tradePrice.getRowCount(),  new Object[]{ "Cmp Frq", ""});
		tradePrice.insertRow(tradePrice.getRowCount(),  new Object[]{ "Tenor", ""});
		
		couponModel.insertRow(couponModel.getRowCount(),  new Object[]{ "Spread", ""});
		couponModel.insertRow(couponModel.getRowCount(),  new Object[]{ "Index", ""});
		
		couponModel.insertRow(couponModel.getRowCount(),  new Object[]{ "points", ""});
		
		amorModel.insertRow(amorModel.getRowCount(),  new Object[]{ "Amortization", ""});
		amorModel.insertRow(amorModel.getRowCount(),  new Object[]{ "Base Amt", ""});
		amorModel.insertRow(amorModel.getRowCount(),  new Object[]{ "Term As Date", ""});
		amorModel.insertRow(amorModel.getRowCount(),  new Object[]{ "Start Date", ""});
		amorModel.insertRow(amorModel.getRowCount(),  new Object[]{ "End Date", ""});
		amorModel.insertRow(amorModel.getRowCount(),  new Object[]{ "Rate", ""});
		amorModel.insertRow(amorModel.getRowCount(),  new Object[]{ "Frequency", ""});
		amorModel.insertRow(amorModel.getRowCount(),  new Object[]{ "Loan as Term", ""});
		
		jTable1.setValueAt(commonUTIL.getDateFormat(commonUTIL.getCurrentDate()), 0, 1);
		
	}
	
	 private String []  convertVectortoSringArray(Vector v) {
	    	String name [] = null;
	    	int i=0;
	    	if(v != null ) {
	    		name = new String[v.size()];
	    		Iterator its = v.iterator();
	    		while(its.hasNext()) {
	    			name [i] = ( (StartUPData) its.next()).getName();
	    			i++;
	    		}
	    	}
			return name;                                           
	        // TODO add your handling code here:
	    } 
	
	public boolean validateProductData() {
		boolean flag = false;
		String issueDate = (String) jTable1.getValueAt(0, 1);
		if(commonUTIL.isEmpty(issueDate) || (!commonUTIL.isStringDate(issueDate)))  {
			commonUTIL.showAlertMessage("Select Issue Date  " );
	        return flag;
	   }
		String endDate = jTable1.getValueAt(1, 1).toString();
		if(commonUTIL.isEmpty(endDate) || (!commonUTIL.isStringDate(endDate)))  {
			commonUTIL.showAlertMessage("Select End Date  " );
			 return flag;
	   }
		String freq = (String) comboBox11.getSelectedItem();
		
		if(commonUTIL.isEmpty(freq))  {
			commonUTIL.showAlertMessage("Select frequency " );
			 return flag;
	   }
		coupon.setCouponFrequency((String) comboBox11.getSelectedItem());
		String DateRoll = jTable1.getValueAt(3, 1).toString();
		if(commonUTIL.isEmpty(DateRoll))  {
			commonUTIL.showAlertMessage("Select DateRoll " );
			 return flag;
	   }
		coupon.setBusinessDayConvention(DateRoll);
		
		String period = jTable1.getValueAt(4, 1).toString();
		if(commonUTIL.isEmpty(period))  {
			commonUTIL.showAlertMessage("Select period " );
			 return flag;
	   }
		coupon.setActivefrom(period);
		String amorType = jTable1.getValueAt(5, 1).toString();
		if(commonUTIL.isEmpty(amorType))  {
			commonUTIL.showAlertMessage("Select amorType " );
			 return flag;
	   }
		 product.setAttributes(amorType);
		if(!commonUTIL.isEmpty(amortizatwindow.getAmoritization())) {
		//   product.setAttributes(amortizatwindow.getAmoritization()); 
		} 
			
		String couponType = jTable2.getValueAt(0, 1).toString();
		if(commonUTIL.isEmpty(couponType))  {
			commonUTIL.showAlertMessage("Select Leg Type " );
			 return flag;
	   }
		coupon.setCouponType(couponType);
		String ccy = jTable2.getValueAt(1, 1).toString();
		if(commonUTIL.isEmpty(ccy))  {
			commonUTIL.showAlertMessage("Select Currency " );
			 return flag;
	   }
		coupon.setCCY(ccy);
		product.setIssueCurrency(ccy);
		String rate = jTable2.getValueAt(2, 1).toString();
		if(commonUTIL.isEmpty(rate) || (!commonUTIL.isNumeric(rate)) )  {
			commonUTIL.showAlertMessage("Select Rate in numbers" );
			 return flag;
	   }
	   	
		coupon.setFixedRate(new Double(rate).doubleValue());
		String dc = jTable2.getValueAt(3, 1).toString();
		if(commonUTIL.isEmpty(dc)  )  {
			commonUTIL.showAlertMessage("Select Daycount in numbers" );
			 return flag;
	   }
		coupon.setDayCount(dc);
		String nominalnumber = nominal.getText();
		if(commonUTIL.isEmpty(nominalnumber) || (!commonUTIL.isNumeric(nominalnumber)) )  {
			commonUTIL.showAlertMessage("Select Nominal in numbers" );
			 return flag;
	   }
		product.setQuantity(new Double(nominalnumber).doubleValue());
		if(!couponType.equalsIgnoreCase("Fixed")) {
			String spread = jTable3.getValueAt(0, 1).toString();
			if(commonUTIL.isEmpty(spread) || (!commonUTIL.isNumeric(spread)) )  {
				commonUTIL.showAlertMessage("Select spread in numbers" );
				 return flag;
		   }
			coupon.setYieldDecimals(new Double(spread).doubleValue()); // yieldDecimals treated as spread as don't want to create unneccessary columns. 
			String index = jTable3.getValueAt(1, 1).toString();
			if(commonUTIL.isEmpty(index) )  {
				commonUTIL.showAlertMessage("Select Index" );
				 return flag;
		   }
			coupon.setYieldMethod(index);
			
		  
		}
		String tenor = jTable2.getValueAt(6, 1).toString();
		if(commonUTIL.isEmpty(tenor) )  {
			commonUTIL.showAlertMessage("Select tenor" );
			 return flag;
	   }
		util.common.Period periods = new util.common.Period(tenor);
		   
			int f = frequencyUtil.fromString(freq);
			int peri= periods.getCode();
			int count = peri/f;
			if(count <= 0 ) {
				commonUTIL.showAlertMessage("Frequency not in Tenor Range");
				jTable1.setValueAt("", 2, 1);
				
				return flag;
			}
		product.setTenor(tenor);
		
		product.setIssueDate(issueDate);
		product.setMarturityDate(endDate);
		product.setProductType("MM");
		product.setProdcutShortName("CASH."+buysell.getText());
		product.setProductname(product.getProdcutShortName()+"."+coupon.getCouponFrequency()+"."+product.getIssueDate()+"."+product.getMarturityDate()+"."+product.getIssueCurrency()+"."+(coupon.getFixedRate()/100)+"."+coupon.getCouponType().substring(0, 2));
	//	product.setId(0);
		productName = product.getProductname();
		flag = true;
		return flag;
		//product.set
		
		
		
	}
	public void setCashFlows(Vector cashFlows) {
		this.cashFlows = cashFlows;
			}
	public void calculatePrice(MMPricing price,Trade trade,Product product,Coupon coupon) {
		
		pricing.price(trade, product, coupon);
         MMCashFlow cashFlow = pricing.generateCashFlow();
         
         setCashFlows(cashFlow.getFlows());
         setPricing(pricing);
         
       
	}
	private javax.swing.JButton PriceButton;
    private javax.swing.JTextField bondProduct;
    private javax.swing.JLabel buysell;
    private javax.swing.JLabel Quantity;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable amortization;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane3;
    private NumericTextField nominal;
    private String productName = "";
}

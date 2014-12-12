package apps.window.referencewindow.searchPanel;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;

import dsServices.RemoteReferenceData;

import util.commonUTIL;

import apps.window.referencewindow.SDIWindow;
import apps.window.utilwindow.propertypane.Combox.ContractSelectorComboBox;
import apps.window.utilwindow.propertypane.Combox.LESelectionPropertyCombox;
import beans.LegalEntity;
import beans.Product;
import beans.StartUPData;

//VS4E -- DO NOT REMOVE THIS LINE!
public class sdiSearchPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel jLabel0;
	private JLabel jLabel1;
	private JLabel jLabel2;
	private LESelectionPropertyCombox beneficiary;
	private LESelectionPropertyCombox po;
	private LESelectionPropertyCombox agent;
	private JLabel jLabel3;
	private JLabel jLabel4;
	private JComboBox currency;
	private JComboBox productType;
	private JButton jButton0;
	private static final String PREFERRED_LOOK_AND_FEEL = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
	RemoteReferenceData remoteBORef = null;
	LegalEntity beneficiaryl;
	SDIWindow sdiWindow =null;
	/**
	 * @return the sdiWindow
	 */
	public SDIWindow getSdiWindow() {
		return sdiWindow;
	}

	/**
	 * @param sdiWindow the sdiWindow to set
	 */
	public void setSdiWindow(SDIWindow sdiWindow) {
		this.sdiWindow = sdiWindow;
	}

	/**
	 * @return the beneficiaryl
	 */
	public LegalEntity getBeneficiaryl() {
		return beneficiaryl;
	}

	/**
	 * @param beneficiaryl the beneficiaryl to set
	 */
	public void setBeneficiaryl(LegalEntity beneficiaryl) {
		this.beneficiaryl = beneficiaryl;
	}

	/**
	 * @return the pol
	 */
	public LegalEntity getPol() {
		return pol;
	}

	/**
	 * @param pol the pol to set
	 */
	public void setPol(LegalEntity pol) {
		this.pol = pol;
	}

	/**
	 * @return the agentl
	 */
	public LegalEntity getAgentl() {
		return agentl;
	}

	/**
	 * @param agentl the agentl to set
	 */
	public void setAgentl(LegalEntity agentl) {
		this.agentl = agentl;
	}

	LegalEntity pol;
	LegalEntity agentl;
	
/**
	 * @return the remoteBORef
	 */
	public RemoteReferenceData getRemoteBORef() {
		return remoteBORef;
	}

	/**
	 * @param remoteBORef the remoteBORef to set
	 */
	public void setRemoteBORef(RemoteReferenceData remoteBORef) {
		this.remoteBORef = remoteBORef;
	}

	//	private LESelectionPropertyCombox beneficiary;
	public sdiSearchPanel(RemoteReferenceData remoteRef) {
		this.remoteBORef = remoteRef;
		initComponents();
	}

	private void initComponents() {
		setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		setLayout(new GroupLayout());
		add(getJLabel3(), new Constraints(new Leading(293, 10, 10), new Leading(14, 12, 12)));
		add(getJLabel4(), new Constraints(new Leading(291, 10, 10), new Leading(57, 12, 12)));
		add(getProductType(), new Constraints(new Leading(398, 152, 12, 12), new Leading(57, 26, 12, 12)));
		add(getJLabel0(), new Constraints(new Leading(16, 73, 10, 10), new Leading(14, 24, 12, 12)));
		add(getJLabel1(), new Constraints(new Leading(20, 12, 12), new Leading(57, 12, 12)));
		add(getBeneficiary(), new Constraints(new Leading(103, 152, 12, 12), new Leading(14, 26, 10, 10)));
		add(getPO(), new Constraints(new Leading(105, 152, 12, 12), new Leading(57, 26, 12, 12)));
		add(getCurrency(), new Constraints(new Leading(398, 152, 12, 12), new Leading(14, 26, 12, 12)));
		add(getAgent(), new Constraints(new Leading(105, 152, 12, 12), new Leading(95, 26, 12, 12)));
		add(getJLabel2(), new Constraints(new Leading(20, 12, 12), new Leading(101, 12, 12)));
		add(getJButton0(), new Constraints(new Leading(633, 10, 10), new Leading(110, 10, 10)));
		setSize(741, 147);
	}

	private JButton getJButton0() {
		if (jButton0 == null) {
			jButton0 = new JButton();
			jButton0.setText("Load");
			jButton0.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	            	String sql = "";
	            	
	            	sql =  whereOnLes(beneficiaryl,sql.trim(),"cpid");
	            	sql = whereOnLes(pol,sql.trim(),"poid");
	            	sql =  whereOnLes(agentl,sql.trim(),"agentid");
	            	sql = whereOnLes(productType,sql.trim(),"products");
	            	sql =  whereOnLes(currency,sql.trim(),"currency");
	            	System.out.println(sql);
	            	 Vector sdis =   getSDIs(sql);
	            	 sdiWindow.setSelectSDIData(sdis);
	            }
			});
		
		}  
		return jButton0;
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
	private String whereOnLes(LegalEntity le,String sql,String columnName) {
		if(le != null) {
			if(sql.isEmpty())
			return sql +columnName + " =" + String.valueOf(le.getId());
			else 
				return sql + " and " +columnName + " ="  + String.valueOf(le.getId());
		}
		return sql;
	}
	private String whereOnLes(JComboBox<String> comb,String sql,String columnName) {
		if(comb.getSelectedIndex() == -1) 
			return sql;
	
			if(sql.isEmpty())
			return sql +columnName + " ='" +  ((String) comb.getSelectedItem()).toString()  +"'";
			else 
			  return sql + " and " +columnName + " ='" +   ((String) comb.getSelectedItem()).toString()  +"'";
			
	}
	private JComboBox getProductType() {
		if (productType == null) {
			productType = new JComboBox();
			DefaultComboBoxModel productmode1 = new DefaultComboBoxModel();
			getMasterDataOnComboBox(productmode1,"ProductType");
			productType.setModel(productmode1);
		}
		return productType;
	}

	private JComboBox getCurrency() {
		if (currency == null) {
			currency = new JComboBox();
			DefaultComboBoxModel currencymodel = new DefaultComboBoxModel();
			getMasterDataOnComboBox(currencymodel,"Currency");
			currency.setModel(currencymodel);
		}
		return currency;
	}

	private JLabel getJLabel4() {
		if (jLabel4 == null) {
			jLabel4 = new JLabel();
			jLabel4.setText("Product Type");
		}
		return jLabel4;
	}

	private JLabel getJLabel3() {
		if (jLabel3 == null) {
			jLabel3 = new JLabel();
			jLabel3.setText("Currency");
		}
		return jLabel3;
	}

	private LESelectionPropertyCombox getAgent() {
		if (agent == null) {
			agent = new LESelectionPropertyCombox("Agent");
			agent.addActionListener(new agentComboxSelecterComboBoxListener());
			agent.setPreferredSize(new Dimension(200, agent.getPreferredSize().height));
		//	jComboBox2.setModel(new DefaultComboBoxModel(new Object[] { "item0", "item1", "item2", "item3" }));
		}
		return agent;
	}

	private LESelectionPropertyCombox getPO() {
		if (po == null) {
			po = new LESelectionPropertyCombox("PO");
			po.addActionListener(new poComboxSelecterComboBoxListener());
			po.setPreferredSize(new Dimension(200, po.getPreferredSize().height));
		}
		return po;
	}

	private LESelectionPropertyCombox getBeneficiary() {
		if (beneficiary == null) {
			beneficiary = new LESelectionPropertyCombox("ALL");
			beneficiary.addActionListener(new beneficiaryComboxSelecterComboBoxListener());
			beneficiary.setPreferredSize(new Dimension(200, beneficiary.getPreferredSize().height));
		//	jComboBox2.setModel(new DefaultComboBoxModel(new Object[] { "item0", "item1", "item2", "item3" }));
		}
		return beneficiary;
	}

	private JLabel getJLabel2() {
		if (jLabel2 == null) {
			jLabel2 = new JLabel();
			jLabel2.setText("Agent");
		}
		return jLabel2;
	}

	private JLabel getJLabel1() {
		if (jLabel1 == null) {
			jLabel1 = new JLabel();
			jLabel1.setText("PO");
		}
		return jLabel1;
	}

	private JLabel getJLabel0() {
		if (jLabel0 == null) {
			jLabel0 = new JLabel();
			jLabel0.setText("Beneficiary");
		}
		return jLabel0;
	}
	class beneficiaryComboxSelecterComboBoxListener implements ActionListener{
    	public void actionPerformed(java.awt.event.ActionEvent event) {
    		Object object=event.getSource();
    		if (!(object instanceof LESelectionPropertyCombox)) return;
    		LESelectionPropertyCombox obj = (LESelectionPropertyCombox)object;
    		 beneficiaryl = (LegalEntity)obj.getSelectedItem();
                  
    		
    		   	}

		
    }
	class agentComboxSelecterComboBoxListener implements ActionListener{
    	public void actionPerformed(java.awt.event.ActionEvent event) {
    		Object object=event.getSource();
    		if (!(object instanceof LESelectionPropertyCombox)) return;
    		LESelectionPropertyCombox obj = (LESelectionPropertyCombox)object;
    		agentl = (LegalEntity)obj.getSelectedItem();

    		
    		   	}

		
    }
	class poComboxSelecterComboBoxListener implements ActionListener{
    	public void actionPerformed(java.awt.event.ActionEvent event) {
    		Object object=event.getSource();
    		if (!(object instanceof LESelectionPropertyCombox)) return;
    		LESelectionPropertyCombox obj = (LESelectionPropertyCombox)object;
    		pol = (LegalEntity)obj.getSelectedItem();

    		
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

}

package apps.window.referencewindow;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;

import util.commonUTIL;
import apps.window.utilwindow.propertypane.Combox.LESelectionPropertyCombox;
import beans.LegalEntity;
import beans.StartUPData;

import dsServices.RemoteReferenceData;

//VS4E -- DO NOT REMOVE THIS LINE!
public class SDIInternalPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	public JComboBox rolesData;
	public JComboBox<String> beneficiaryData;
	private JLabel jLabel1;
	private JLabel jLabel0;
	private JLabel jLabel2;
	private JLabel jLabel3;
	public JComboBox productTypeData;
	private JLabel jLabel4;
	public JComboBox beneficiaryLecontactsData;
	public JComboBox currencyData;
	private JLabel jLabel5;
	public JComboBox payrecData;
	private JLabel jLabel6;
	public JComboBox cashsecurityData;
	public JCheckBox jPreferred;
	private JLabel jLabel8;
	private JPanel jPanel0;
	private JLabel jLabel9;
	private JLabel jLabel10;
	public LESelectionPropertyCombox poData;
	public JComboBox poContactData;
	private JPanel jPanel1;
	private JPanel jPanel2;
	private JLabel jLabel12;
	private JLabel jLabel11;
	private JPanel jPanel3;
	private JLabel jLabel13;
	public JComboBox glAccountData;
	public LESelectionPropertyCombox agentNameData;
	public JTextField agentAcTextField;
	public JCheckBox jCheckBox0;
	public JTextField priorityTextField;
	private JLabel jLabel14;
	//private JComboBox jComboBox0;
	private JLabel jLabel15;
	public JComboBox agentContactData;
	public JComboBox messsgeMethodData;
	private JLabel jLabel16;
	public LESelectionPropertyCombox InterM1Data;
	public LESelectionPropertyCombox InterM2Data;
	private JPanel jPanel4;
	public JTabbedPane jTabbedPane0;
	private JLabel jLabel17;
	private JLabel jLabel19;
	private JLabel jLabel20;
	public JTextField im1AccountTextField;
	private JLabel jLabel18;
	private JLabel  jLabel21;
	public JComboBox InterM1Contacts;
	private JPanel jPanel5;
	public JComboBox InterM2Contacts;
	public JTextField im2AccountTextField;
	private static final String PREFERRED_LOOK_AND_FEEL = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
	
	DefaultComboBoxModel<String> leModel = new DefaultComboBoxModel<String>();
	Collection<LegalEntity> le = new Vector<LegalEntity>();
	
	public SDIInternalPanel(RemoteReferenceData remoteD) {
		remoteR = remoteD;
		initComponents();
	}

	private void initComponents() {
		add(getJPanel0());
		setSize(372, 574);
		
		try {
			le = remoteR.selectAllLs();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private JComboBox getInterM1Contacts() {
		if (InterM1Contacts == null) {
			InterM1Contacts = new JComboBox();
			DefaultComboBoxModel InterM1ContactsDatamodel = new DefaultComboBoxModel();
			getMasterDataOnComboBox(InterM1ContactsDatamodel,"LEContacts");
			InterM1Contacts.setModel(InterM1ContactsDatamodel);
		}
		return InterM1Contacts;
	}
	private JComboBox getInterM2Contacts() {
		
		if (InterM2Contacts == null) {
			InterM2Contacts = new JComboBox();
			DefaultComboBoxModel InterM2ContactsDatamodel = new DefaultComboBoxModel();
			getMasterDataOnComboBox(InterM2ContactsDatamodel,"LEContacts");
			InterM2Contacts.setModel(InterM2ContactsDatamodel);
		}
		return InterM2Contacts;
	}
	private JLabel getJLabel18() {
		if (jLabel18 == null) {
			jLabel18 = new JLabel();
			jLabel18.setText("InterM1Contact");
		}
		return jLabel18;
	}
	private JLabel getJLabel20() {
		if (jLabel20 == null) {
			jLabel20 = new JLabel();
			jLabel20.setText("InterM2Contact");
		}
		return jLabel20;
	}
	private JTextField getIm1AccountTextField() {
		if (im1AccountTextField == null) {
			im1AccountTextField = new JTextField();
			im1AccountTextField.setText("");
		}
		return im1AccountTextField;
	}
	private JTextField getIm2AccountTextField() {
		
		if (im2AccountTextField == null) {
			im2AccountTextField = new JTextField();
			im2AccountTextField.setText("");
		}
		return im2AccountTextField;
	}
	private JLabel getJLabel17() {
		if (jLabel17 == null) {
			jLabel17 = new JLabel();
			jLabel17.setText("InterM1 A/C");
		}
		return jLabel17;
	}

	private JTabbedPane getJTabbedPane0() {
		if (jTabbedPane0 == null) {
			jTabbedPane0 = new JTabbedPane();
			jTabbedPane0.addTab("Agent",getJPanel3());
			jTabbedPane0.addTab("InterM1", getJPanel4());
			jTabbedPane0.addTab("InterM2", getJPanel5());
		}
		return jTabbedPane0;
	}

	private JPanel getJPanel4() {
		if (jPanel4 == null) {
			jPanel4 = new JPanel();
			jPanel4.setLayout(new GroupLayout());
			jPanel4.add(getJLabel16(), new Constraints(new Leading(9, 108, 10, 10), new Leading(12, 12, 12)));
			jPanel4.add(getInterM1Data(), new Constraints(new Leading(137, 174, 10, 10), new Leading(8, 24, 12, 12)));
			jPanel4.add(getJLabel17(), new Constraints(new Leading(9, 85, 10, 10), new Leading(50, 14, 12, 12)));
			jPanel4.add(getIm1AccountTextField(), new Constraints(new Leading(137, 165, 12, 12), new Leading(46, 12, 12)));
			jPanel4.add(getJLabel18(), new Constraints(new Leading(16, 88, 10, 10), new Leading(80, 20, 12, 12)));
			jPanel4.add(getInterM1Contacts(), new Constraints(new Leading(137, 171, 10, 10), new Leading(82, 28, 10, 10)));
		}
		return jPanel4;
	}
	private JPanel getJPanel5() {
		if (jPanel5 == null) {
			jPanel5 = new JPanel();
			jPanel5.setLayout(new GroupLayout());
			jPanel5.add(getJLabel21(), new Constraints(new Leading(9, 108, 10, 10), new Leading(12, 12, 12)));
			jPanel5.add(getInterM2Data(), new Constraints(new Leading(137, 174, 10, 10), new Leading(8, 24, 12, 12)));
			jPanel5.add(getJLabel19(), new Constraints(new Leading(9, 85, 10, 10), new Leading(50, 14, 12, 12)));
			jPanel5.add(getIm2AccountTextField(), new Constraints(new Leading(137, 165, 12, 12), new Leading(46, 12, 12)));
			jPanel5.add(getJLabel20(), new Constraints(new Leading(16, 88, 10, 10), new Leading(80, 20, 12, 12)));
			jPanel5.add(getInterM2Contacts(), new Constraints(new Leading(137, 171, 10, 10), new Leading(82, 28, 10, 10)));
		}
		return jPanel5;
	}
	private JLabel getJLabel16() {
		if (jLabel16 == null) {
			jLabel16 = new JLabel();
			jLabel16.setText("InterM1 Agent");
		}
		return jLabel16;
	}
	private JLabel getJLabel21() {
		if (jLabel21 == null) {
			jLabel21 = new JLabel();
			jLabel21.setText("InterM2 Agent");
		}
		return jLabel21;
	}
	private JLabel getJLabel19() {
		if (jLabel19 == null) {
			jLabel19 = new JLabel();
			jLabel19.setText("InterM2 A/C");
		}
		return jLabel19;
	}
	private LESelectionPropertyCombox getInterM1Data() {
		if (InterM1Data == null) {
			
			InterM1Data = new LESelectionPropertyCombox("Agent");
			InterM1Data.addActionListener(new agentComboxSelecterComboBoxListener());
			InterM1Data.setPreferredSize(new Dimension(200, InterM1Data.getPreferredSize().height));
		}
		return InterM1Data;
	}
	private LESelectionPropertyCombox getInterM2Data() {
		if (InterM2Data == null) {
			InterM2Data = new LESelectionPropertyCombox("Agent");
			InterM2Data.addActionListener(new agentComboxSelecterComboBoxListener());
			InterM2Data.setPreferredSize(new Dimension(200, InterM2Data.getPreferredSize().height));
		}
		return InterM2Data;
	}
	private JComboBox getAgentContactData() {
		if (agentContactData == null) {
			agentContactData = new JComboBox();
			DefaultComboBoxModel agentContactDatamodel = new DefaultComboBoxModel();
			getMasterDataOnComboBox(agentContactDatamodel,"LEContacts");
			agentContactData.setModel(agentContactDatamodel);
		}
		return agentContactData;
	}

	private JLabel getJLabel15() {
		if (jLabel15 == null) {
			jLabel15 = new JLabel();
			jLabel15.setText("Contact");
		}
		return jLabel15;
	}

	private JLabel getJLabel14() {
		if (jLabel14 == null) {
			jLabel14 = new JLabel();
			jLabel14.setText("Method");
		}
		return jLabel14;
	}

	private JTextField getPriorityTextField() {
		if (priorityTextField == null) {
			priorityTextField = new JTextField();
			priorityTextField.setText("");
		}
		return priorityTextField;
	}

	private JCheckBox getJCheckBox0() {
		if (jCheckBox0 == null) {
			jCheckBox0 = new JCheckBox();
			jCheckBox0.setText("Message");
		}
		return jCheckBox0;
	}

	private JTextField getAgentAcTextField() {
		if (agentAcTextField == null) {
			agentAcTextField = new JTextField("");
		}
		return agentAcTextField;
	}

	private LESelectionPropertyCombox getAgentNameData() {
		if (agentNameData == null) {
			
			agentNameData = new LESelectionPropertyCombox("Agent");
			agentNameData.addActionListener(new agentComboxSelecterComboBoxListener());
			agentNameData.setPreferredSize(new Dimension(200, agentNameData.getPreferredSize().height));
		
		}
		return agentNameData;
	}

	private JComboBox getGlAccountData() {
		if (glAccountData == null) {
			glAccountData = new JComboBox();
			glAccountData.setModel(new DefaultComboBoxModel(new Object[] { "0" }));
		}
		return glAccountData;
	}

	private JLabel getJLabel13() {
		if (jLabel13 == null) {
			jLabel13 = new JLabel();
			jLabel13.setText("PO Org GL");
		}
		return jLabel13;
	}

	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jPanel3 = new JPanel();
			jPanel3.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			jPanel3.setLayout(new GroupLayout());
			jPanel3.add(getJLabel12(), new Constraints(new Leading(9, 10, 10), new Leading(13, 12, 12)));
			jPanel3.add(getJCheckBox0(), new Constraints(new Leading(245, 82, 10, 10), new Leading(79, 12, 12)));
			jPanel3.add(getAgentAcTextField(), new Constraints(new Leading(110, 165, 10, 10), new Leading(47, 12, 12)));
			jPanel3.add(getJLabel15(), new Constraints(new Leading(9, 60, 12, 12), new Leading(79, 20, 12, 12)));
			jPanel3.add(getJLabel11(), new Constraints(new Leading(9, 10, 10), new Leading(47, 14, 10, 10)));
			jPanel3.add(getAgentContactData(), new Constraints(new Leading(110, 133, 12, 12), new Leading(77, 28, 12, 12)));
			jPanel3.add(getAgentNameData(), new Constraints(new Leading(107, 174, 10, 10), new Leading(10, 28, 10, 10)));
		}
		return jPanel3;
	}

	private JLabel getJLabel11() {
		if (jLabel11 == null) {
			jLabel11 = new JLabel();
			jLabel11.setText(" Agent A/c");
		}
		return jLabel11;
	}

	private JLabel getJLabel12() {
		if (jLabel12 == null) {
			jLabel12 = new JLabel();
			jLabel12.setText("Agent Name");
		}
		return jLabel12;
	}

	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			jPanel1.setLayout(new GroupLayout());
			jPanel1.add(getJLabel9(), new Constraints(new Leading(6, 75, 10, 10), new Leading(9, 17, 10, 10)));
			jPanel1.add(getJLabel10(), new Constraints(new Leading(9, 75, 10, 10), new Leading(41, 17, 10, 10)));
			jPanel1.add(getPoData(), new Constraints(new Leading(107, 177, 10, 10), new Leading(4, 27, 12, 12)));
			jPanel1.add(getPoContactData(), new Constraints(new Leading(107, 177, 12, 12), new Leading(38, 27, 12, 12)));
			jPanel1.add(getJLabel13(), new Constraints(new Leading(6, 86, 12, 12), new Leading(76, 12, 12)));
			jPanel1.add(getGlAccountData(), new Constraints(new Leading(110, 176, 10, 10), new Leading(73, 29, 10, 10)));
		}
		return jPanel1;
	}

	private JComboBox getPoContactData() {
		if (poContactData == null) {
			poContactData = new JComboBox();
			
			DefaultComboBoxModel poContactDataDatamodel = new DefaultComboBoxModel();
			getMasterDataOnComboBox(poContactDataDatamodel,"LEContacts");
			poContactData.setModel(poContactDataDatamodel);
		}
		return poContactData;
	}

	private LESelectionPropertyCombox getPoData() {
		if (poData == null) {
			poData = new LESelectionPropertyCombox("PO");
			poData.addActionListener(new agentComboxSelecterComboBoxListener());
			poData.setPreferredSize(new Dimension(200, poData.getPreferredSize().height));
		}
		return poData;
	}

	private JLabel getJLabel10() {
		if (jLabel10 == null) {
			jLabel10 = new JLabel();
			jLabel10.setText("PRO Contact");
		}
		return jLabel10;
	}

	private JLabel getJLabel9() {
		if (jLabel9 == null) {
			jLabel9 = new JLabel();
			jLabel9.setText("PR ORG");
		}
		return jLabel9;
	}
RemoteReferenceData remoteR = null;
	public JPanel getJPanel0() {
		if (jPanel0 == null) {
			jPanel0 = new JPanel();
			jPanel0.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			jPanel0.setLayout(new GroupLayout());
			jPanel0.add(getJLabel1(), new Constraints(new Leading(3, 75, 12, 12), new Leading(55, 17, 10, 10)));
			jPanel0.add(getJLabel0(), new Constraints(new Leading(3, 43, 12, 12), new Leading(14, 17, 12, 12)));
			jPanel0.add(getJLabel2(), new Constraints(new Leading(3, 75, 12, 12), new Leading(90, 17, 10, 10)));
			jPanel0.add(getJLabel4(), new Constraints(new Leading(3, 75, 12, 12), new Leading(154, 17, 12, 12)));
			
			jPanel0.add(getJLabel6(), new Constraints(new Leading(3, 86, 12, 12), new Leading(203, 17, 12, 12)));
			jPanel0.add(getRolesData(), new Constraints(new Leading(96, 177, 10, 10), new Leading(9, 27, 12, 12)));
			jPanel0.add(getProductTypeData(), new Constraints(new Leading(96, 177, 12, 12), new Leading(113, 27, 12, 12)));
			jPanel0.add(getCashsecurityData(), new Constraints(new Leading(95, 57, 12, 12), new Leading(191, 27, 12, 12)));
			jPanel0.add(getBeneficiaryData(), new Constraints(new Leading(95, 177, 12, 12), new Leading(43, 27, 12, 12)));
			jPanel0.add(getBeneficiaryLecontactsData(), new Constraints(new Leading(96, 177, 12, 12), new Leading(149, 27, 12, 12)));
			jPanel0.add(getPayrecData(), new Constraints(new Leading(266, 66, 10, 10), new Leading(76, 27, 12, 12)));
			jPanel0.add(getJLabel5(), new Constraints(new Leading(199, 51, 10, 10), new Leading(82, 17, 12, 12)));
			jPanel0.add(getCurrencyData(), new Constraints(new Leading(96, 91, 12, 12), new Leading(76, 27, 12, 12)));
			jPanel0.add(getJLabel3(), new Constraints(new Leading(3, 75, 12, 12), new Leading(121, 17, 12, 12)));
			jPanel0.add(getJPanel1(), new Constraints(new Leading(6, 339, 12, 12), new Leading(269, 114, 12, 12)));
			jPanel0.add(getJLabel8(), new Constraints(new Leading(7, 48, 12, 12), new Leading(232, 17, 12, 12)));
			jPanel0.add(getJLabel7(), new Constraints(new Leading(181, 86, 12, 12), new Leading(195, 17, 12, 12)));
			jPanel0.add(getJLabel14(), new Constraints(new Leading(183, 10, 10), new Leading(232, 17, 12, 12)));
			jPanel0.add(getMessageData(), new Constraints(new Leading(230, 12, 12), new Leading(232, 27, 12, 12)));
			jPanel0.add(getPriorityTextField(), new Constraints(new Leading(93, 70, 10, 10), new Leading(230, 12, 12)));
			jPanel0.add(getJTabbedPane0(), new Constraints(new Leading(7, 337, 12, 12), new Leading(387, 163, 10, 10)));
		}
		return jPanel0;
	}

	private JLabel getJLabel8() {
		if (jLabel8 == null) {
			jLabel8 = new JLabel();
			jLabel8.setText("Priority");
		}
		return jLabel8;
	}

	private JCheckBox getJLabel7() {
		if (jPreferred == null) {
			jPreferred = new JCheckBox("Preferred");
		
		}
		return jPreferred;
	}

	private JComboBox getCashsecurityData() {
		if (cashsecurityData == null) {
			cashsecurityData = new JComboBox();
			DefaultComboBoxModel cashsecuritymodel = new DefaultComboBoxModel();
			getMasterDataOnComboBox(cashsecuritymodel,"CASH_SECURITY");
			cashsecurityData.setModel(cashsecuritymodel);
		}
		return cashsecurityData;
	}

	private JLabel getJLabel6() {
		if (jLabel6 == null) {
			jLabel6 = new JLabel();
			jLabel6.setText("Cash/Security");
		}
		return jLabel6;
	}

	private JComboBox getPayrecData() {
		if (payrecData == null) {
			payrecData = new JComboBox();
			
			Vector<String> payRec = new Vector<String>();
			payrecData.addItem("PAY");
			payrecData.addItem("REC");
			
		}
		return payrecData;
	}

	private JLabel getJLabel5() {
		if (jLabel5 == null) {
			jLabel5 = new JLabel();
			jLabel5.setText("Pay/Rec");
		}
		return jLabel5;
	}

	private JComboBox getCurrencyData() {
		if (currencyData == null) {
			currencyData = new JComboBox();
			DefaultComboBoxModel currencymodel = new DefaultComboBoxModel();
			getMasterDataOnComboBox(currencymodel,"Currency");
			currencyData.setModel(currencymodel);
		}
		return currencyData;
	}

	private JComboBox getBeneficiaryLecontactsData() {
		if (beneficiaryLecontactsData == null) {
			beneficiaryLecontactsData = new JComboBox();
			//beneficiaryLecontactsData.setModel(new DefaultComboBoxModel(new Object[] { "item0", "item1", "item2", "item3" }));
			DefaultComboBoxModel beneficiaryLecontactsDatamodel = new DefaultComboBoxModel();
			getMasterDataOnComboBox(beneficiaryLecontactsDatamodel,"LEContacts");
			beneficiaryLecontactsData.setModel(beneficiaryLecontactsDatamodel);
		}
		return beneficiaryLecontactsData;
	}
	private JComboBox getMessageData() {
		if (messsgeMethodData == null) {
			messsgeMethodData = new JComboBox();
			//beneficiaryLecontactsData.setModel(new DefaultComboBoxModel(new Object[] { "item0", "item1", "item2", "item3" }));
			DefaultComboBoxModel messsgeMethodDatamodel = new DefaultComboBoxModel();
			getMasterDataOnComboBox(messsgeMethodDatamodel,"MessageFormateType");
			messsgeMethodData.setModel(messsgeMethodDatamodel);
		}
		return messsgeMethodData;
	}
	
	private JLabel getJLabel4() {
		if (jLabel4 == null) {
			jLabel4 = new JLabel();
			jLabel4.setText("LeContacts");
		}
		return jLabel4;
	}

	private JComboBox getProductTypeData() {
		if (productTypeData == null) {
			productTypeData = new JComboBox();
			DefaultComboBoxModel productTypemodel = new DefaultComboBoxModel();
			getMasterDataOnComboBox(productTypemodel,"ProductType");
			productTypeData.setModel(productTypemodel);
		}
		return productTypeData;
	}

	private JLabel getJLabel3() {
		if (jLabel3 == null) {
			jLabel3 = new JLabel();
			jLabel3.setText("ProductType");
		}
		return jLabel3;
	}

	private JLabel getJLabel2() {
		if (jLabel2 == null) {
			jLabel2 = new JLabel();
			jLabel2.setText("Currency");
		}
		return jLabel2;
	}

	private JLabel getJLabel0() {
		if (jLabel0 == null) {
			jLabel0 = new JLabel();
			jLabel0.setText("Roles");
		}
		return jLabel0;
	}

	private JLabel getJLabel1() {
		if (jLabel1 == null) {
			jLabel1 = new JLabel();
			jLabel1.setText("Beneficiary");
		}
		return jLabel1;
	}

	private JComboBox<String> getBeneficiaryData() {
		if (beneficiaryData == null) {
			/*beneficiaryData = new LESelectionPropertyCombox("CounterParty");
			beneficiaryData.addActionListener(new agentComboxSelecterComboBoxListener());
			beneficiaryData.setPreferredSize(new Dimension(200, beneficiaryData.getPreferredSize().height));*/
			
			beneficiaryData = new JComboBox<String>();
			getLeOnComboBox(leModel,"CounterParty");
			beneficiaryData.setModel(leModel);		
			
		}
		return beneficiaryData;
	}

	private JComboBox getRolesData() {
		if (rolesData == null) {
			rolesData = new JComboBox();
			DefaultComboBoxModel rolesTypemodel = new DefaultComboBoxModel();
			getMasterDataOnComboBox(rolesTypemodel,"Roles");
			rolesData.setModel(rolesTypemodel);
			rolesData.addItemListener(new ItemListener() {
				
				@Override
				public void itemStateChanged(ItemEvent arg0) {
					// TODO Auto-generated method stub
					if (rolesData.getSelectedIndex() > -1) {
						
						String roledata = rolesData.getSelectedItem().toString();
						
						if (beneficiaryData == null) {
							beneficiaryData = new JComboBox<String>();
						}
						leModel.removeAllElements();
						getLeOnComboBox(leModel,roledata);
						beneficiaryData.setModel(leModel);
						
					} 					
					
					//beneficiaryData.addActionListener(new agentComboxSelecterComboBoxListener());
					//beneficiaryData.setPreferredSize(new Dimension(200, beneficiaryData.getPreferredSize().height));					
				}
			});
		}
		return rolesData;
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
	LegalEntity beneficiaryl;

	LegalEntity pol;
	LegalEntity agentl;
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
	 private final void getMasterDataOnComboBox( javax.swing.DefaultComboBoxModel combodata,String name) {
			Vector vector = null;
			try {
				vector = (Vector) remoteR.getStartUPData(name);
				
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
					
			commonUTIL.displayError("SDIWIndow","getMasterDataOnComboBox", e);
			}
		}

	 private final void getLeOnComboBox(javax.swing.DefaultComboBoxModel combodata,String role) {
			
			Iterator<LegalEntity> it = le.iterator();
			
			int i =0;
			
			while(it.hasNext()) {
				
				LegalEntity data = (LegalEntity) it.next();
				
				if (data.getRole().equals(role)) {
					
					combodata.insertElementAt(data.getAlias(), i++);
					
				}
				
				data = null;
			}	
			
			it = null;
		}	
	 
	 public int getLeId(String leName){
		 
		 Iterator<LegalEntity> it = le.iterator();
			
			int id =0;
			
			while(it.hasNext()) {
				
				LegalEntity data = (LegalEntity) it.next();
				
				if (data.getName().equals(leName)) {
					
					id= data.getId();
					break;
				}
				
			}	
			
			it = null;
			
			return id;
	 }
}

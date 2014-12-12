package apps.window.utilwindow.propertypane.panel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import apps.window.utilwindow.Frame12;
import apps.window.utilwindow.propertydialog.ContractSelectorDialog;
import apps.window.utilwindow.propertydialog.DateRulePropertyDialog;
import apps.window.utilwindow.propertydialog.LESelectionPropertyDialog;
import apps.window.utilwindow.propertypane.Combox.ContractSelectorComboBox;
import apps.window.utilwindow.propertypane.Combox.DateRulePropertyCombox;
import apps.window.utilwindow.propertypane.Combox.LESelectionPropertyCombox;
import beans.DateRule;
import beans.LegalEntity;
import beans.Product;

import com.jidesoft.combobox.PopupPanel;

public class LESelectionPropertyPanel extends PopupPanel {
	private LESelectionPropertyCombox  _comboBox = null;
	 private String _displayObjClass = null;
	 
	public LESelectionPropertyPanel(LESelectionPropertyCombox comboBox,String displayableObject) {
		 setTitle("DateRule");
		 _comboBox = comboBox;
        _displayObjClass = displayableObject;
        initComponent();
 } 
       
	
	private LESelectionPropertyDialog _dialog = null;
	
	protected void initComponent() {
        setLayout(new BorderLayout());
        add(createLESelectionPanel(_displayObjClass), BorderLayout.CENTER);
        setRequestFocusEnabled(true);
        setFocusable(true);
    }
	 private JComponent createLESelectionPanel(String displayableObjectClass ) {
		 if(_dialog == null)
			 _dialog =  new LESelectionPropertyDialog(Frame12.getFrame(), false , displayableObjectClass);
		        JPanel returnPanel = _dialog.getMainPanel();
		        JTable futcontable = _dialog.getFutureContractSelectorTabel();
		        ListSelectionModel rowSM = futcontable.getSelectionModel();
		        rowSM.addListSelectionListener(new ListSelectionListener() {
		        	public void newSelection(int min, int max) {
				           LegalEntity businessobj =_dialog.getContractAtRow(min);
				           setSelectedObject(businessobj);
				       }
		            public void valueChanged(ListSelectionEvent e) {
		                //Ignore extra messages.
		                if (e.getValueIsAdjusting()) return;
		                
		                ListSelectionModel lsm = (ListSelectionModel)e.getSource();
		                if (lsm.isSelectionEmpty()) {
		                    return;
		                } else {
		                    newSelection(lsm.getMinSelectionIndex(), lsm.getMaxSelectionIndex());
		                }
		            }
		        });
		   //  JPanel returnPanel = _dialog.getMainPanel();
	        
		 return returnPanel;
	 }
	 
	 public int getSelectLegalEntityID() {
		return ((LegalEntity) getSelectedObject()).getId();
	 }
	//private DateRule selectedDateRuleobj = null;
	public void reloadData(String _selectedObjType, int productID) {
		// TODO Auto-generated method stub
		 
		    	_dialog.reloadData(_selectedObjType, productID);
		    
		    
	}

}

package apps.window.utilwindow.propertypane.Editor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import apps.window.utilwindow.propertypane.Combox.ContractSelectorComboBox;
import apps.window.utilwindow.propertypane.Combox.DateRulePropertyCombox;
import apps.window.utilwindow.propertypane.Combox.LESelectionPropertyCombox;
import apps.window.utilwindow.propertypane.Combox.LastTradeTimePropertyComboxBox;
import beans.LegalEntity;
import beans.Product;

import com.jidesoft.combobox.AbstractComboBox;
import com.jidesoft.grid.AbstractComboBoxCellEditor;

public class LESelectionCellEditor extends AbstractComboBoxCellEditor {

	
	
private String _role = "";
	
	public LESelectionCellEditor(String role){
		_role = role;
		customizeAbstractComboBox();
	}
	
	public String getRole(){
		return _role;
	}
	
	public AbstractComboBox createAbstractComboBox() {
		return new  LESelectionPropertyCombox(this);
	}

	

	 
}

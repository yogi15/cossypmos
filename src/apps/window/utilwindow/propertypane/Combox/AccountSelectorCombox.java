package apps.window.utilwindow.propertypane.Combox;

import apps.window.utilwindow.propertypane.Editor.AccountSelectorCellEditor;
import apps.window.utilwindow.propertypane.Editor.LESelectionCellEditor;
import apps.window.utilwindow.propertypane.panel.AccountSelectionPropertyPanel;
import apps.window.utilwindow.propertypane.panel.LESelectionPropertyPanel;
import beans.Account;
import beans.LegalEntity;

import com.jidesoft.combobox.AbstractComboBox;
import com.jidesoft.combobox.PopupPanel;
import com.jidesoft.combobox.AbstractComboBox.DefaultTextFieldEditorComponent;
import com.jidesoft.combobox.AbstractComboBox.EditorComponent;

public class AccountSelectorCombox  extends AbstractComboBox {
	String _selectedObjType = null;
	LegalEntity accountHolder = null;
	String currency = null;
	AccountSelectorCellEditor  _accountSelectCellEditor = null;
	@Override
	public EditorComponent createEditorComponent() {
		return new DefaultTextFieldEditorComponent(Account.class) {
			protected String convertElementToString(Object value) {
				String _stringFutCon = null;
				if(value!= null){
					if(value instanceof Account)  {
						setAccount((Account)value);
				_stringFutCon = String.valueOf(((Account)value).getAccountName());
					}
				}
				System.out.println(_stringFutCon);
			return 	_stringFutCon;
			}

			
		};
		
		// TODO Auto-generated method stub
	
	}
	private void setAccount(Account value) {
		// TODO Auto-generated method stub
	//	this._
	}
	 public void showPopupPanelAsPopup(boolean show) {
	        super.showPopupPanelAsPopup(show);
	    }
	@Override
	public PopupPanel createPopupComponent() {
		// TODO Auto-generated method stub
		return new AccountSelectionPropertyPanel(this,_accountSelectCellEditor.get_currency(),_accountSelectCellEditor.getBeneficiary());
	}
	public AccountSelectorCombox(String displayObj){
		_selectedObjType = displayObj;
		setEditable(false);
		initComponent();
	}
	public AccountSelectorCombox(AccountSelectorCellEditor accountSelectCellEditor) {
		// TODO Auto-generated constructor stub
		_accountSelectCellEditor = accountSelectCellEditor;
		_selectedObjType = _accountSelectCellEditor.get_currency();
		accountHolder = _accountSelectCellEditor.getBeneficiary();
		setEditable(false);
		initComponent();
	}
}

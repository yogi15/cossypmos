package apps.window.utilwindow.propertypane.Combox;

import util.commonUTIL;
import apps.window.utilwindow.propertypane.Editor.LESelectionCellEditor;
import apps.window.utilwindow.propertypane.panel.LESelectionPropertyPanel;
import beans.LegalEntity;
import beans.Product;

import com.jidesoft.combobox.AbstractComboBox;
import com.jidesoft.combobox.PopupPanel;

public class LESelectionPropertyCombox    extends AbstractComboBox {
	String _selectedObjType = null;
	LegalEntity le = null;
	LESelectionCellEditor _lESelectionCellEditor = null;
	/**
	 * @return the le
	 */
	public LegalEntity getLe() {
		return le;
	}
	/**
	 * @param le the le to set
	 */
	public void setLe(LegalEntity le) {
		this.le = le;
	}
	public LESelectionPropertyCombox(String role){
		_selectedObjType = role;
		
		setEditable(false);
		initComponent();
	}
	public String getRole() {
		if(_lESelectionCellEditor != null) 
			return _lESelectionCellEditor.getRole();
		return _selectedObjType;
	}
	public LESelectionPropertyCombox(LESelectionCellEditor leSelectionCellEditor) {
		// TODO Auto-generated constructor stub
		_lESelectionCellEditor = leSelectionCellEditor;
		_selectedObjType = _lESelectionCellEditor.getRole();
		setEditable(false);
		initComponent();
	}
	
	@Override
	public EditorComponent createEditorComponent() {
		return new DefaultTextFieldEditorComponent(LegalEntity.class) {
			protected String convertElementToString(Object value) {
				String _stringFutCon = null;
				if(value!= null){
					if(value instanceof LegalEntity)  {
						setLe((LegalEntity)value);
				_stringFutCon = String.valueOf(((LegalEntity)value).getName());
					}
				}
				System.out.println(_stringFutCon);
			return 	_stringFutCon;
			}
		};
		
		// TODO Auto-generated method stub
	
	}
	
	public LegalEntity getSelectLegalEntity() {
		return getLe();
	}
	 public void reloadData(int productID){
	    	if(getPopupPanel() != null)
	    		((LESelectionPropertyPanel)getPopupPanel()).reloadData(_selectedObjType,productID);
	    }
    public void showPopupPanelAsPopup(boolean show) {
        super.showPopupPanelAsPopup(show);
    }
	@Override
	public PopupPanel createPopupComponent() {
		
		return new LESelectionPropertyPanel(this,getRole());
		
	}
	
    
}

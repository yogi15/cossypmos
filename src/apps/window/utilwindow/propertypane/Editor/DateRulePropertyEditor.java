package apps.window.utilwindow.propertypane.Editor;


import apps.window.utilwindow.propertypane.Combox.DateRulePropertyCombox;

import com.jidesoft.combobox.AbstractComboBox;
import com.jidesoft.grid.AbstractComboBoxCellEditor;

public class DateRulePropertyEditor extends AbstractComboBoxCellEditor {

	@Override
	public AbstractComboBox createAbstractComboBox() {
		// TODO Auto-generated method stub
		return new DateRulePropertyCombox();
	}

}

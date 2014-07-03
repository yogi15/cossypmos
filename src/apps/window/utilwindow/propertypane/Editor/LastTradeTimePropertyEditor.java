package apps.window.utilwindow.propertypane.Editor;

import apps.window.utilwindow.propertypane.Combox.LastTradeTimePropertyComboxBox;

import com.jidesoft.combobox.AbstractComboBox;
import com.jidesoft.grid.AbstractComboBoxCellEditor;

public class LastTradeTimePropertyEditor extends AbstractComboBoxCellEditor{
	private int _timeInMinutes = -1;
	
	public LastTradeTimePropertyEditor(int timeInMinutes){
		_timeInMinutes = timeInMinutes;
		customizeAbstractComboBox();
	}
	
	public int getTimeinMinutes(){
		return _timeInMinutes;
	}
	
	public AbstractComboBox createAbstractComboBox() {
		return new LastTradeTimePropertyComboxBox(this);
	}

}

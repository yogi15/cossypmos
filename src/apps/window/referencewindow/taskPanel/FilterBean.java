package apps.window.referencewindow.taskPanel;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;

public class FilterBean {

	String ColumnName;
	
	DefaultCellEditor filterCriteria;
	DefaultCellEditor columnData;
	
	public String getColumnName() {
		return ColumnName;
	}
	public void setColumnName(String columnName) {
		ColumnName = columnName;
	}
	public DefaultCellEditor getFilterCriteria() {
		return filterCriteria;
	}
	public void setFilterCriteria(JComboBox  filterJComboBox) {
		filterCriteria = new DefaultCellEditor(filterJComboBox);
		this.filterCriteria = filterCriteria;
	}
	public DefaultCellEditor getColumnData() {
		return columnData;
	}
	public void setColumnData(JComboBox  columnDataJComboBox) {
		columnData = new DefaultCellEditor(columnDataJComboBox);
		this.columnData = columnData;
	}
	
	
}

package apps.window.adminmonitor;

import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;
import javax.swing.table.AbstractTableModel;

import org.dyno.visual.swing.layouts.Bilateral;
import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;

import apps.window.adminmonitor.EngineViewerPanel.EngineName;

//VS4E -- DO NOT REMOVE THIS LINE!
public class MonitorExceptionPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable jTable0;
	private JScrollPane jScrollPane0;
	private JPanel jPanel0;
	TableModelUtil model = null;
	public MonitorExceptionPanel() {
		initComponents();
	}

	private void initComponents() {
		setLayout(new GroupLayout());
		add(getJPanel0(), new Constraints(new Leading(6, 1397, 10, 10), new Leading(7, 578, 10, 10)));
		setSize(1410, 594);
	}

	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getJTextArea0());
		}
		return jScrollPane1;
	}

	private JTextArea getJTextArea0() {
		if (jTextArea0 == null) {
			jTextArea0 = new JTextArea();
		}
		return jTextArea0;
	}

	private JPanel getJPanel0() {
		if (jPanel0 == null) {
			jPanel0 = new JPanel();
			jPanel0.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			jPanel0.setLayout(new GroupLayout());
			jPanel0.add(getJScrollPane0(), new Constraints(new Leading(4, 1380, 10, 10), new Leading(5, 344, 10, 10)));
			jPanel0.add(getJScrollPane1(), new Constraints(new Bilateral(5, 12, 27), new Leading(354, 216, 10, 10)));
		}
		return jPanel0;
	}

	private JScrollPane getJScrollPane0() {
		if (jScrollPane0 == null) {
			jScrollPane0 = new JScrollPane();
			jScrollPane0.setViewportView(getJTable0());
		}
		return jScrollPane0;
	}

	private JTable getJTable0() {
		String usercols [] = {"Application","Level","ErrorType","Message","HostName","Time"};
		if (jTable0 == null) {
			jTable0 = new JTable();
			model = new TableModelUtil(exceptionData,usercols);
			jTable0.setModel(model);
		}
		return jTable0;
	}
	
	Vector<ExceptionM> exceptionData = new Vector<ExceptionM>();
	private JTextArea jTextArea0;
	private JScrollPane jScrollPane1;
	private static final String PREFERRED_LOOK_AND_FEEL = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
	
}

class TableModelUtil extends AbstractTableModel {
	final String[] columnNames;

	Vector<ExceptionM> data;
	public TableModelUtil(Vector<ExceptionM> myData, String col[]) {
		this.columnNames = col;
		this.data = myData;
		
	}

	public int getColumnCount() {
		return columnNames.length;
	}

	public int getRowCount() {
		if (data != null)
			return data.size();
		return 0;
	}

	public String getColumnName(int col) {
		return columnNames[col];
	}

	public Object getValueAt(int row, int col) {
		Object value = null;

		ExceptionM datae = (ExceptionM) data.get(row);

		switch (col) {
		case 0:
			value = datae.getApplication();
			break;
		case 1:
			value = datae.getLevel();
			break;
		case 2:
			value = datae.getErrorType();
			break;
		case 3:
			value = datae.getMessage();
		case 4:
			value = datae.getHostName();
			break;
		case 5:
			value = datae.getTime();
			break;

		}
		return value;
	}

	public boolean isCellEditable(int row, int col) {
		return false;
	}

	public void setValueAt(Object value, int row, int col) {
		
		if (value instanceof EngineName) {
			data.set(row, (ExceptionM) value);
			this.fireTableDataChanged();
			
		}

	}

	public void addRow(Object value) {

		data.add((ExceptionM) value);
		this.fireTableDataChanged();

	}

	public void delRow(int row) {
		if (row != -1) {
			data.remove(row);
			this.fireTableDataChanged();
		}

	}

	public void udpateValueAt(Object value, int row, int col) {

		data.set(row, (ExceptionM) value);
		for (int i = 0; i < columnNames.length; i++)
			fireTableCellUpdated(row, i);

	}

	public void removeALL() {
		if (data != null) {
			data.removeAllElements();
		}
		data = null;
		this.fireTableDataChanged();
	}
}



class ExceptionM {
	String application;
	/**
	 * @return the application
	 */
	public String getApplication() {
		return application;
	}
	/**
	 * @param application the application to set
	 */
	public void setApplication(String application) {
		this.application = application;
	}
	/**
	 * @return the level
	 */
	public String getLevel() {
		return level;
	}
	/**
	 * @param level the level to set
	 */
	public void setLevel(String level) {
		this.level = level;
	}
	/**
	 * @return the errorType
	 */
	public String getErrorType() {
		return errorType;
	}
	/**
	 * @param errorType the errorType to set
	 */
	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * @return the hostName
	 */
	public String getHostName() {
		return hostName;
	}
	/**
	 * @param hostName the hostName to set
	 */
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}
	/**
	 * @param time the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}
	String level;
	String errorType;
	String message;
	String hostName;
	String time;
	
}


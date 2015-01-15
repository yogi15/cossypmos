package apps.window.adminmonitor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

import org.dyno.visual.swing.layouts.Bilateral;
import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;

import apps.window.adminmonitor.EngineViewerPanel.EngineName;
import beans.StartUPData;

import dsEventProcessor.EventProcessor;
import dsServices.RemoteEvent;

//VS4E -- DO NOT REMOVE THIS LINE!
public class EventCleanUpPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JList jList0;
	private JScrollPane jScrollPane0;
	private JButton jButton0;
	private JButton jButton1;
	private JButton jButton2;
	private JTable jTable0;
	private JScrollPane jScrollPane1;
	private JPanel jPanel0;
	private JButton jButton3;
	private JButton jButton4;
	RemoteEvent remoteEvent;
	DefaultListModel<String> listModel = new DefaultListModel<String>();
	TableModelUtil model = null;
	Vector<EventProcessor> eventData = new Vector<EventProcessor>();
	String serviceName = "";
	String cols [] = {"EventID","EventType","ObjectType","ObjectID","ObjectVersionID","Comments","EventDate","EventTime"};
	/**
	 * @return the remoteEvent
	 */
	public RemoteEvent getRemoteEvent() {
		return remoteEvent;
	}

	/**
	 * @param remoteEvent the remoteEvent to set
	 */
	public void setRemoteEvent(RemoteEvent remoteEvent) {
		this.remoteEvent = remoteEvent;
	}

	private static final String PREFERRED_LOOK_AND_FEEL = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
	public EventCleanUpPanel(Vector<StartUPData> serviceManagers) {
		
		initComponents();
		if(serviceManagers != null) {
		for(int i=0;i<serviceManagers.size();i++) {
		   listModel.addElement(((StartUPData) serviceManagers.get(i)).getName());
		}
		}
	}

	private void initComponents() {
		setLayout(new GroupLayout());
		add(getJPanel0(), new Constraints(new Bilateral(6, 12, 5), new Bilateral(8, 12, 5)));
		setSize(1112, 523);
	}

	private JButton getJButton4() {
		if (jButton4 == null) {
			jButton4 = new JButton();
			jButton4.setText("Clear unconsumed Event");
			jButton4.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					try {
						if(jTable0.getSelectedRow() != -1) {
							EventProcessor evet = eventData.get(jTable0.getSelectedRow());
							evet.isClearedByAdmin(true);
							evet.setAdminClearedEventType(serviceName);
							remoteEvent.updateClearEvents(evet);
							eventData.remove(jTable0.getSelectedRow());
							model.delRow(jTable0.getSelectedRow());
						}
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			
		}
		return jButton4;
	}

	private JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new JButton();
			jButton3.setText("Clear");
		}
		return jButton3;
	}

	private JPanel getJPanel0() {
		if (jPanel0 == null) {
			jPanel0 = new JPanel();
			jPanel0.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			jPanel0.setLayout(new GroupLayout());
			jPanel0.add(getJScrollPane1(), new Constraints(new Leading(210, 1150, 12, 12), new Leading(57, 630,10, 10)));
			jPanel0.add(getJButton1(), new Constraints(new Leading(372, 10, 10), new Leading(16, 12, 12)));
			jPanel0.add(getJButton0(), new Constraints(new Leading(217, 10, 10), new Leading(16, 12, 12)));
			jPanel0.add(getJScrollPane0(), new Constraints(new Leading(8, 190, 10, 10), new Leading(58, 630, 12, 12)));
			jPanel0.add(getJButton3(), new Constraints(new Leading(12, 12, 12), new Leading(16, 12, 12)));
			jPanel0.add(getJButton2(), new Constraints(new Leading(740, 10, 10), new Leading(16, 10, 10)));
			jPanel0.add(getJButton4(), new Constraints(new Leading(537, 12, 12), new Leading(16, 12, 12)));
		}
		return jPanel0;
	}

	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			jScrollPane1.setViewportView(getJTable0());
		}
		return jScrollPane1;
	}

	private JTable getJTable0() {
		if (jTable0 == null) {
			jTable0 = new JTable();
			TableModelUtil model = new TableModelUtil(eventData,cols);
			jTable0.setModel(model);
		}
		return jTable0;
	}

	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setText("Archive All Completed Events");
		}
		return jButton2;
	}

	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("Unconsumed Events");
			jButton1.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					try {
						eventData = (Vector<EventProcessor>) remoteEvent.getEventNotProcessed(serviceName);
						model = new  TableModelUtil(eventData,cols);
						jTable0.setModel(model);
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		}
		return jButton1;
	}

	private JButton getJButton0() {
		if (jButton0 == null) {
			jButton0 = new JButton();
			jButton0.setText("Consumed Events");
jButton0.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					try {
						eventData = (Vector<EventProcessor>) remoteEvent.getEventProcessed(serviceName);
						model = new  TableModelUtil(eventData,cols);
						jTable0.setModel(model);
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		}
		return jButton0;
	}

	private JScrollPane getJScrollPane0() {
		if (jScrollPane0 == null) {
			jScrollPane0 = new JScrollPane();
			jScrollPane0.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			jScrollPane0.setViewportView(getJList0());
		}
		return jScrollPane0;
	}

	private JList getJList0() {
		if (jList0 == null) {
			jList0 = new JList();
			
			
			jList0.setModel(listModel);
		     jList0.addListSelectionListener( new ListSelectionListener() {
				
				@Override
				public void valueChanged(ListSelectionEvent arg0) {
					// TODO Auto-generated method stub
					if( jList0.getSelectedIndex() != -1) {
						String service1 = jList0.getSelectedValue().toString();
						serviceName = service1.replace("ServiceManager", "");
								
					}
				}
			});
		}
		return jList0;
	}
	
class TableModelUtil extends AbstractTableModel {
	final String[] columnNames;

	Vector<EventProcessor> data;
	public TableModelUtil(Vector<EventProcessor> myData, String col[]) {
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

		
		EventProcessor datae = (EventProcessor) data.get(row);

		switch (col) {
		case 0:
			value = datae.getEventid();
			break;
		case 1:
			value = datae.getEventType();
			break;
		case 2:
			value = datae.getType();
			break;
		case 3:
			value = datae.getObjectID();
		case 4:
			value = datae.getObjectVersionID();
			break;
		case 5:
			value = datae.getComments();
			break;
		case 6:
			value = datae.getOccurrenceDate();
			break;
		case 7:
			value = datae.getOccurrenceTime();
			break;
		}
		return value;
	}

	public boolean isCellEditable(int row, int col) {
		return false;
	}

	public void setValueAt(Object value, int row, int col) {
		
		if (value instanceof EngineName) {
			data.set(row, (EventProcessor) value);
			this.fireTableDataChanged();
			
		}

	}

	public void addRow(Object value) {

		data.add((EventProcessor) value);
		this.fireTableDataChanged();

	}

	public void delRow(int row) {
		if (row != -1) {
			data.remove(row);
			this.fireTableDataChanged();
		}

	}

	public void udpateValueAt(Object value, int row, int col) {

		data.set(row, (EventProcessor) value);
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


}

package apps.window.adminmonitor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EtchedBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;

import util.commonUTIL;
import beans.Users;
import dsEventProcessor.AdminEventProcessor;
import dsServices.RemoteAdminManager;
import dsServices.RemoteTrade;
import dsServices.ServiceInit;

//VS4E -- DO NOT REMOVE THIS LINE!
public class EngineViewerPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JButton jButton0;
	private JButton jButton1;
	private JTable jTable0;
	private JScrollPane jScrollPane0;
	private JLabel jLabel0;
	RemoteAdminManager remoteAdmin;
	RemoteTrade remoteTrade;
	private JPanel jPanel0;
	
	Vector<EngineName> serviceData  = new Vector<EngineName>();
	Vector<userData> usersDataUsingApp  = new Vector<userData>();
	TableModelUtil model = null;
	TableModelUtil1  model1 =null; 	
	private JButton jButton2;
	private JTable jTable1;
	private JScrollPane jScrollPane2;
	private JLabel jLabel1;
	private static final String PREFERRED_LOOK_AND_FEEL = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
	public EngineViewerPanel(RemoteAdminManager remoteAdmin,RemoteTrade remoteTrade) {
		this.remoteAdmin = remoteAdmin;
		this.remoteTrade = remoteTrade;
		initComponents();
		//model = new TableModelUtil(serviceData, col);
	}

	private void initComponents() {
		setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
		setLayout(new GroupLayout());
		add(getJPanel0(), new Constraints(new Leading(3, 918, 10, 10), new Leading(3, 407, 10, 10)));
		setSize(928, 421);
	}

	private JLabel getJLabel1() {
		if (jLabel1 == null) {
			jLabel1 = new JLabel();
			jLabel1.setText("Total User Login");
		}
		return jLabel1;
	}

	private JScrollPane getJScrollPane2() {
		if (jScrollPane2 == null) {
			jScrollPane2 = new JScrollPane();
			jScrollPane2.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			jScrollPane2.setViewportView(getJTable1());
		}
		return jScrollPane2;
	}

	private JTable getJTable1() {
		
		String usercols [] = {"ClientID","UserName" ,"ApplicationName","HostName","Instance Name"};
		if (jTable1 == null) {
			jTable1 = new JTable();
			usersDataUsingApp.clear();
			Vector<String> userData1 = new Vector<String>();
			try {
				userData1 = remoteAdmin.getALLUserConnected();
				for(int i=0;i<userData1.size();i++) {
					String userD = userData1.get(i);
					userData user = new userData();
					String data [] = userD.split("_");
					
						user.setApplicationName(data[0]);
						user.setClientID(data[1]);
						user.setName(data[2]);
						user.setHostName(data[3]);
						usersDataUsingApp.add(user);
					}
				
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			model1 = new TableModelUtil1(usersDataUsingApp,usercols);
			jTable1.setModel(model1);
		};
		return jTable1;
	}

	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setText("Refresh");

			jButton2.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					Vector<String> engineruning = null;
					String col [] = {"ServiceManagerName","Status"};
					try {
						engineruning = remoteAdmin.getALLRunningEngines();
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					serviceData.clear();
					
					for(int i=0;i<engineruning.size();i++) {
						String engineName = engineruning.get(i);
						String engineData [] = engineName.split("_");
						EngineName egin = new EngineName();
						egin.setEngineName(engineData[0]);
						egin.setIsStartEd(engineData[1]);
						egin.setClientID(engineData[2]);
						serviceData.add(egin);
					}
					model = new TableModelUtil(serviceData, col);
					

					
					jTable0.setModel(model);
				
				
				}
			});
		}
			
		return jButton2;
	}

	public JPanel getJPanel0() {
		if (jPanel0 == null) {
			jPanel0 = new JPanel();
			jPanel0.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			jPanel0.setLayout(new GroupLayout());
			jPanel0.add(getJButton0(), new Constraints(new Leading(25, 10, 10), new Leading(34, 12, 12)));
			jPanel0.add(getJScrollPane0(), new Constraints(new Leading(19, 402, 10, 10), new Leading(69, 328, 10, 10)));
			jPanel0.add(getJLabel0(), new Constraints(new Leading(498, 10, 10), new Leading(41, 12, 12)));
			jPanel0.add(getJButton1(), new Constraints(new Leading(92, 12, 12), new Leading(34, 12, 12)));
			jPanel0.add(getJButton2(), new Constraints(new Leading(169, 12, 12), new Leading(34, 12, 12)));
			jPanel0.add(getJScrollPane2(), new Constraints(new Leading(497, 699, 10, 10), new Leading(75, 266, 10, 10)));
			jPanel0.add(getJLabel1(), new Constraints(new Leading(497, 167, 10, 10), new Leading(368, 10, 10)));
		}
		return jPanel0;
	}

	private JLabel getJLabel0() {
		if (jLabel0 == null) {
			jLabel0 = new JLabel();
			jLabel0.setText("User Logined IN");
		}
		return jLabel0;
	}

	

	private JScrollPane getJScrollPane0() {
		if (jScrollPane0 == null) {
			jScrollPane0 = new JScrollPane();
			jScrollPane0.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			jScrollPane0.setViewportView(getJTable0());
		}
		return jScrollPane0;
	}

	private JTable getJTable0() {
		if (jTable0 == null) {
			jTable0 = new JTable();
			String col [] = {"ServiceManagerName","Status"};
			Vector<String> engineruning = null;
			try {
				engineruning = remoteAdmin.getALLRunningEngines();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for(int i=0;i<engineruning.size();i++) {
				String engineName = engineruning.get(i);
				String engineData [] = engineName.split("_");
				EngineName egin = new EngineName();
				egin.setEngineName(engineData[0]);
				egin.setIsStartEd(engineData[1]);
				egin.setClientID(engineData[2]);
				serviceData.add(egin);
			}
			model = new TableModelUtil(serviceData, col);
			
			
			jTable0.setModel(model);
		}
		return jTable0;
	}
	

	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("Stop");
			jButton1.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					int i = jTable0.getSelectedRow();
					if(i != -1) {
						String sigin = (String) model.getValueAt(i, 1);
						EngineName eng = serviceData.get(i);
						eng.setIsStartEd("Stopped");
						model.setValueAt(eng, i, 0);
						AdminEventProcessor adminEvent = new AdminEventProcessor();
						adminEvent.setEngineStartedSignal(eng.getEngineName()+"_"+eng.getIsStartEd()+"_"+eng.getClientID());
						adminEvent.setEngineViewerPanel(true);
						adminEvent.setSavetoDB(false);
						adminEvent.setisSignalByFrontEnd(true);
						adminEvent.setClientID(Integer.valueOf(eng.getClientID()));
						try {
							
							remoteTrade.publishnewTrade("TRANS_NEWTRANSFER","TRADE",adminEvent);
						} catch (RemoteException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						//serviceData
					}
					
				}
			});
		} 
		return jButton1;
	}

	private JButton getJButton0() {
		if (jButton0 == null) {
			jButton0 = new JButton();
			jButton0.setText("Start");
		}
		return jButton0;
	}

	public synchronized void addtaskData(AdminEventProcessor adminEvent) {
		try {
			if(adminEvent.isSignalByFrontEnd())
				return;
		if(!commonUTIL.isEmpty(adminEvent.getEngineStartedSignal())) {
			String engineData [] = adminEvent.getEngineStartedSignal().split("_");
			String engineName = engineData[0];// adminEvent.getEngineStartedSignal().substring(0, adminEvent.getEngineStartedSignal().indexOf("_"));
			String siginal = engineData[1]; //adminEvent.getEngineStartedSignal().substring(adminEvent.getEngineStartedSignal().indexOf("_")+1,adminEvent.getEngineStartedSignal().length() );
			String clientID = engineData[2];
			boolean isExists = false;
			int row = 0;
			for(int i=0;i<jTable0.getRowCount();i++) {
				String engineName1 = (String) jTable0.getValueAt(i, 0);
				if(engineName1.equalsIgnoreCase(engineName)) {
					isExists = true; 
					row = i;
					break;
				}
			}
			   EngineName egin = new EngineName();
				egin.setEngineName(engineName);
				egin.setIsStartEd(siginal);
				egin.setClientID(clientID);
				if(engineName.contains("Manager")) {
				if(isExists)
				model.udpateValueAt(egin, row, 0);
				else 
					model.addRow(egin);
				}
			
			 int id = adminEvent.getClientID();
			 boolean found = false;
			// System.out.println("I am getting event for client id  ************** " + adminEvent.getClientID());
			 int idToRemove = 0;
			 for(int i=0;i<jTable1.getRowCount();i++) {
				String idC = (String) model1.getValueAt(i, 0);
				if(id == Integer.valueOf(idC).intValue()) {
					if(siginal.equalsIgnoreCase("Stopped")){
						
						found = true;
					//	System.out.println("I am going to remove event for client id  ************** " + adminEvent.getClientID());
						idToRemove = i;
						
						break;
					}
				}
				
			 
			 }
			if(found) {
			 model1.delRow(idToRemove);
			// System.out.println("I  removed **** event for client id  ************** " + adminEvent.getClientID());
				
				//usersDataUsingApp.remove(idToRemove);
			}
			 if(!found) {
					userData newData = new userData();
					newData.setApplicationName(engineName);
					newData.setClientID(String.valueOf(adminEvent.getClientID()));
					if( adminEvent.getUser() == null)  {
						commonUTIL.display("EngineViewPanel ", " Users not Set fro AdminEvent ");
						return;
					}
					newData.setName(adminEvent.getUser().getUser_name());
					newData.setHostName(adminEvent.getUser().getHostName());
					model1.addRow(newData);
				//	usersDataUsingApp.add(newData);
				}
				
		}
		}catch(NullPointerException e) {
			commonUTIL.displayError("EngineViewerPanel", "addtaskData", e);
		}catch(ArrayIndexOutOfBoundsException e) {
			commonUTIL.displayError("EngineViewerPanel", "addtaskData", e);
		}
			
		}
		
		

	
	class EngineName {
		String engineName;
		String ClientID; 
		public String getClientID() {
			return ClientID;
		}
		/**
		 * @param engineName the engineName to set
		 */
		public void setClientID(String ClientID) {
			this.ClientID = ClientID;
		}
		/**
		 * @return the engineName
		 */
		public String getEngineName() {
			return engineName;
		}
		/**
		 * @param engineName the engineName to set
		 */
		public void setEngineName(String engineName) {
			this.engineName = engineName;
		}
		/**
		 * @return the isStartEd
		 */
		public String getIsStartEd() {
			return isStartEd;
		}
		/**
		 * @param isStartEd the isStartEd to set
		 */
		public void setIsStartEd(String isStartEd) {
			this.isStartEd = isStartEd;
		}
		public boolean isSame(String engineStatus) {
			if(isStartEd.equalsIgnoreCase(engineStatus))
				return true;
			return false;
		}
		public boolean isSameKey(String engineName) {
			String eng = getEngineName()+"_"+getIsStartEd();
			if(eng.equalsIgnoreCase(engineName))
				return true;
						return false;
		}
		String isStartEd;
		
	}
	class TableModelUtil extends AbstractTableModel {
		final String[] columnNames;

		Vector<EngineName> data;
		public TableModelUtil(Vector<EngineName> myData, String col[]) {
			this.columnNames = col;
			this.data = myData;
			
		}
        public int getDataAT(EngineName name) {
        	int rowid =0;
        	for(int i=0;i<data.size();i++) {
        		EngineName e = data.get(i);
        		if(e.getEngineName().equalsIgnoreCase(name.getEngineName())) {
        			rowid = i;
        			break;
        		}
        	}
        	return rowid;
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

			EngineName datae = (EngineName) data.get(row);

			switch (col) {
			case 0:
				value = datae.getEngineName();
				break;
			case 1:
				value = datae.getIsStartEd();
				break;
			

			}
			return value;
		}

		public boolean isCellEditable(int row, int col) {
			return false;
		}

		public void setValueAt(Object value, int row, int col) {
			
			if (value instanceof EngineName) {
				data.set(row, (EngineName) value);
				this.fireTableDataChanged();
				
			}

		}

		public void addRow(Object value) {

			data.add((EngineName) value);
			this.fireTableDataChanged();

		}

		public void delRow(int row) {
			if (row != -1) {
				data.remove(row);
				this.fireTableDataChanged();
			}

		}

		public void udpateValueAt(Object value, int row, int col) {

			data.set(row, (EngineName) value);
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
	
	class TableModelUtil1 extends AbstractTableModel {
		final String[] columnNames;

		Vector<userData> data;
		public TableModelUtil1(Vector<userData> myData, String col[]) {
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

			userData datae = (userData) data.get(row);

			switch (col) {
			case 0:
				value = datae.getClientID();
				break;
			case 1:
				value = datae.getName();
				break;
			case 2:
				value = datae.getApplicationName();
				break;
			case 3:
				value = datae.getHostName();
				
				break;

			}
			return value;
		}

		public boolean isCellEditable(int row, int col) {
			return false;
		}

		public void setValueAt(Object value, int row, int col) {
			
			if (value instanceof EngineName) {
				data.set(row, (userData) value);
				this.fireTableDataChanged();
				
			}

		}

		public void addRow(Object value) {

			data.add((userData) value);
			this.fireTableDataChanged();

		}

		public void delRow(int row) {
			if (row != -1) {
				data.remove(row);
				this.fireTableDataChanged();
			}

		}

		public void udpateValueAt(Object value, int row, int col) {

			data.set(row, (userData) value);
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
	
	class userData {
		String name;
		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}
		/**
		 * @param name the name to set
		 */
		public void setName(String name) {
			this.name = name;
		}
		/**
		 * @return the clientID
		 */
		public String getClientID() {
			return clientID;
		}
		/**
		 * @param clientID the clientID to set
		 */
		public void setClientID(String clientID) {
			this.clientID = clientID;
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
		 * @return the applicationName
		 */
		public String getApplicationName() {
			return applicationName;
		}
		/**
		 * @param applicationName the applicationName to set
		 */
		public void setApplicationName(String applicationName) {
			this.applicationName = applicationName;
		}
		/**
		 * @return the ipAddress
		 */
		public String getIpAddress() {
			return ipAddress;
		}
		/**
		 * @param ipAddress the ipAddress to set
		 */
		public void setIpAddress(String ipAddress) {
			this.ipAddress = ipAddress;
		}
		String clientID;
		String hostName;
		String applicationName;
		String ipAddress;
		
	}
	
	
}

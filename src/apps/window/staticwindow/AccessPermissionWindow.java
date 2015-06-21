package apps.window.staticwindow;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.ActionMapUIResource;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;

import util.RemoteServiceUtil;
import util.commonUTIL;
import apps.window.referencewindow.staticReferencePanel;
import beans.AccessFunction;
import beans.AccessWindow;
import beans.StartUPData;
import dsServices.RemoteAccessData;
import dsServices.RemoteReferenceData;

//VS4E -- DO NOT REMOVE THIS LINE!
public class AccessPermissionWindow extends staticReferencePanel {

	private static final long serialVersionUID = 1L;
	private JLabel jLabel0;
	private JPanel jPanel0;
	private JList<String> windowList;
	private JScrollPane jScrollPane0;
	private JLabel jLabel1;
	private JList<String>  functionList;
	private JScrollPane jScrollPane1;
	private JPanel jPanel1;
	private JLabel jLabel2;
	private JComboBox<String> groupNameComboBox2;
	private JCheckBox readWriteCheckbox;
	private JButton saveButton;
	private JButton clearButton;
	private JPanel jPanel2;
	private JPanel jPanel3;
	private JComboBox<String> groupNameComboBox;
	private JComboBox<String> windowNameComboBox;
	private JButton loadButton;
	//private JCheckBox readDisplayCheckbox;
	private JCheckBox readWriteDisplayCheckbox;
	private JTable jTable0;
	private JScrollPane jScrollPane2;
	private JLabel jLabel3;
	private JLabel jLabel4;
	ActionMap actionMap =null;
	private RemoteReferenceData referenceData;
	private RemoteAccessData remoteAccessData;
	private Collection<StartUPData> groupNames;
	private Collection<StartUPData> windowNames;
	private Collection<StartUPData> functionNames;
	private Vector<String> groupNamesVec = new Vector<String>();
	private Vector<String> windowNamesVec = new Vector<String>();
	private Vector<String> functionNamesVec = new Vector<String>();
	private Hashtable<String, DefaultListModel<String>> functionTable = new Hashtable<String, DefaultListModel<String>>();
	private Collection<AccessFunction> accessFunctionsVec = new Vector<AccessFunction>();
	private Collection<AccessWindow> accessWindowVec = new Vector<AccessWindow>();
	private Color savedColor = Color.decode("#FFF68F");
	private TableModelUtil loadModel;
	private String [] loadCol = {"Function Name", "Read/ Write"};
	private Collection <AccessWindow> loadData = new Vector<AccessWindow>();
	private Hashtable<String, Vector<String>> detailsTable = new Hashtable<String, Vector<String>>();
	private JButton deleteButton;
	private boolean isActionListenerActive = false;
	//private static final String PREFERRED_LOOK_AND_FEEL = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
	public AccessPermissionWindow() {
		initData();
		initComponents();
	}
	
	private void initData() {		
		referenceData = RemoteServiceUtil.getRemoteReferenceDataService();
		remoteAccessData = RemoteServiceUtil.getRemoteAccess();
		try {
			groupNames = referenceData.getStartUPData("UserGroup");
			windowNames = referenceData.getStartUPData("WindowName");
			functionNames = referenceData.getStartUPData("FunctionName");	
			
			extractStartUpData(groupNames, groupNamesVec);
			extractStartUpData(windowNames, windowNamesVec);
			extractStartUpData(functionNames, functionNamesVec);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	private void initComponents() {
		setLayout(new GroupLayout());
		add(getJPanel0(), new Constraints(new Leading(6, 1200, 10, 10), new Leading(6, 433, 10, 10)));
		setSize(1215, 449);
		
		actionMap = new ActionMapUIResource();
	    actionMap.put("action_save", new AbstractAction() {	      
			private static final long serialVersionUID = 6310007784499905596L;

			@Override
	        public void actionPerformed(ActionEvent e) {
	        	validate("saveAsNew");
	        }
	    });
	    actionMap.put("action_new", new AbstractAction() {
			private static final long serialVersionUID = 7756144778755434990L;

			@Override
	        public void actionPerformed(ActionEvent e) {
	        	validate("NEW");
	        }
	    });
	    actionMap.put("action_del", new AbstractAction() {	    
			private static final long serialVersionUID = 6504926045456885002L;

			@Override
	        public void actionPerformed(ActionEvent e) {
	        	validate("delete");
	        }
	    });
	    actionMap.put("action_saveasnew", new AbstractAction() {
			private static final long serialVersionUID = 5592065285301683965L;

			@Override
	        public void actionPerformed(ActionEvent e) {
	        	validate("saveAsNew");	   
	        }
	    });
	    actionMap.put("action_exit", new AbstractAction() {
			private static final long serialVersionUID = 8107626427946681956L;

			@Override
	        public void actionPerformed(ActionEvent e) {
	        	validate("exit");
	        }
	    });
	}
	
	private void validate(String action) {
		
		if (validateDetails(action)) {
			
			if (action.equals("saveAsNew")){
				
				fillDetails("saveAsNew");
				int save = 0;
				try {							
					if (!functionList.isSelectionEmpty()) {
						save = remoteAccessData.saveAccessFuntion(accessFunctionsVec);
					} else {
						save = remoteAccessData.saveAccessWindow(accessWindowVec);
					}
				
					if (save == 1) {
						commonUTIL.showAlertMessage("Saved");
					} else {
						commonUTIL.showAlertMessage("Not Saved");
					}
				} catch (RemoteException e1) {		
					commonUTIL.showAlertMessage("Error at Server side");
					e1.printStackTrace();
				}
			} else if (action.equals("delete")){
				fillDetails("delete");
				boolean deleted = false;
				try {							
					if (!functionList.isSelectionEmpty()) {
						deleted = remoteAccessData.deleteAccessFuntion(accessFunctionsVec);
						
						if (deleted) {
							loadModel.removeALL();
							commonUTIL.showAlertMessage("Functions Deleted");
						} else {
							commonUTIL.showAlertMessage("Functions Not Deleted");
						}													
						
					} else {
						deleted = remoteAccessData.deleteAccessWindow(accessWindowVec, accessFunctionsVec);
						
						if (deleted) {							
							deleted = remoteAccessData.deleteAllAccessFuntion(accessFunctionsVec);
							commonUTIL.showAlertMessage("Window and its Function Deleted");
						} else {
							commonUTIL.showAlertMessage("Not Deleted");
						}															
					}
			
				} catch (RemoteException e1) {		
					commonUTIL.showAlertMessage("Error at Server side");
					e1.printStackTrace();
				}
			}
		}
	}
	private JButton getDeleteButton() {
		if (deleteButton == null) {
			deleteButton = new JButton();
			deleteButton.setText("Delete");
			
			deleteButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					validate("delete");					
				}				
			});			
		}
		return deleteButton;
	}

	private JLabel getJLabel4() {
		if (jLabel4 == null) {
			jLabel4 = new JLabel();
			jLabel4.setText("WindowName");
		}
		return jLabel4;
	}

	private JLabel getJLabel3() {
		if (jLabel3 == null) {
			jLabel3 = new JLabel();
			jLabel3.setText("GroupName");
		}
		return jLabel3;
	}

	private JScrollPane getJScrollPane2() {
		if (jScrollPane2 == null) {
			jScrollPane2 = new JScrollPane();
			jScrollPane2.setViewportView(getJTable0());
		}
		return jScrollPane2;
	}

	private JTable getJTable0() {
		if (jTable0 == null) {
			jTable0 = new JTable();
			loadModel = new TableModelUtil(null, loadCol);
			jTable0.setModel(new DefaultTableModel());
			
			jTable0.addMouseListener(new java.awt.event.MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					AccessFunction row = loadModel.getValueAt(jTable0.getSelectedRow());
					if (row.getIsAccessable() == 1) {
						readWriteDisplayCheckbox.setSelected(true);
					} else {
						readWriteDisplayCheckbox.setSelected(false);
					}
				}
			});
		}
		return jTable0;
	}

	private JCheckBox getReadWriteDisplayCheckbox() {
		if (readWriteDisplayCheckbox == null) {
			readWriteDisplayCheckbox = new JCheckBox();
			readWriteDisplayCheckbox.setText("Read/ Write");
		}
		return readWriteDisplayCheckbox;
	}

	/*private JCheckBox getReadDisplayCheckbox() {
		if (readDisplayCheckbox == null) {
			readDisplayCheckbox = new JCheckBox();
			readDisplayCheckbox.setSelected(true);
			readDisplayCheckbox.setText("Read");
		}
		return readDisplayCheckbox;
	}*/

	private JButton getLoadButton() {
		if (loadButton == null) {
			loadButton = new JButton();
			loadButton.setText("Load");
			loadButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
				}

			});
		}
		return loadButton;
	}
	
	private void processData(Collection<AccessWindow> loadData, Vector<String> windowNameVec) {
		
		Iterator<AccessWindow> itr = loadData.iterator();
		
		while(itr.hasNext()){
			AccessWindow data = itr.next();
			windowNameVec.add(data.getWindowName());
		}
	}
	private JComboBox<String> getWindowNameComboBox() {
		if (windowNameComboBox == null) {
			windowNameComboBox = new JComboBox<String>();	
			windowNameComboBox.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if (isActionListenerActive) {
						if (windowNameComboBox.getSelectedIndex() >= 0 ) {
							
							String windowName = windowNameComboBox.getSelectedItem().toString();
							String where = "groupName like '" +  groupNameComboBox.getSelectedItem().toString() +"' and windowName like '" + windowName +"'";
							Vector<AccessFunction> loadFunction = new Vector<AccessFunction>();
							try {
								loadFunction = (Vector<AccessFunction>) remoteAccessData.selectAccessFunction(where);						
							} catch (RemoteException e1) {
								commonUTIL.showAlertMessage("Error at Server side");
								e1.printStackTrace();
							}	
							if (!loadFunction.isEmpty()) {
								loadModel = new TableModelUtil(loadFunction, loadCol);
								jTable0.setModel(loadModel);
							} else {								
								commonUTIL.showAlertMessage("No Function Access found for Window: " + windowName);
							}
						} else {
							loadModel.removeALL();
						}
					}					
				}				
			});
		}
		return windowNameComboBox;
	}

	private JComboBox<String>  getGroupNameComboBox() {
		if (groupNameComboBox == null) {
			groupNameComboBox =  new JComboBox<String>(groupNamesVec);
			groupNameComboBox.setSelectedIndex(-1);
			groupNameComboBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					Vector<String> windowNameVec =  new Vector<String>();
					String groupName = groupNameComboBox.getSelectedItem().toString();
					String where = "groupName like '" +  groupName +"'";
					//if (!detailsTable.containsKey(groupName)) {	
					if (detailsTable.containsKey(groupName)) {	
						detailsTable.remove(groupName);
					}
						try {
							loadData = remoteAccessData.selectAccessWindow(where);						
						} catch (RemoteException e1) {
							commonUTIL.showAlertMessage("Error at Server side");
							loadModel.removeALL();
							e1.printStackTrace();
						}	
						
						if (!loadData.isEmpty()) {
							windowNameVec.clear();
							processData(loadData, windowNameVec);
							detailsTable.put(groupName, windowNameVec);	
						} else {
							loadModel.removeALL();
							windowNameComboBox.removeAllItems();
							commonUTIL.showAlertMessage("No Window access given to: " + groupName);							
						}
					//}
					
					if (detailsTable.containsKey(groupName)){
						windowNameComboBox.removeAllItems();
						Vector<String> groupVec = detailsTable.get(groupName);
						isActionListenerActive = false;
						for (String name : groupVec) {
							windowNameComboBox.addItem(name);
	                    }
						windowNameComboBox.setSelectedIndex(-1);
					} 	isActionListenerActive = true;			
				}

			});
		}
		return groupNameComboBox;
	}

	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jPanel3 = new JPanel();
			jPanel3.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			jPanel3.setLayout(new GroupLayout());
			jPanel3.add(getWindowNameComboBox(), new Constraints(new Leading(91, 227, 10, 10), new Leading(58, 28, 12, 12)));
			jPanel3.add(getGroupNameComboBox(), new Constraints(new Leading(91, 226, 12, 12), new Leading(6, 28, 12, 12)));
			jPanel3.add(getJScrollPane2(), new Constraints(new Leading(4, 426, 10, 10), new Leading(98, 262, 10, 10)));
			jPanel3.add(getJLabel3(), new Constraints(new Leading(4, 12, 12), new Leading(20, 12, 12)));
			jPanel3.add(getJLabel4(), new Constraints(new Leading(4, 12, 12), new Leading(69, 10, 10)));
			jPanel3.add(getReadWriteDisplayCheckbox(), new Constraints(new Leading(323, 12, 12), new Leading(65, 12, 12)));
			//jPanel3.add(getReadDisplayCheckbox(), new Constraints(new Leading(327, 10, 10), new Leading(17, 12, 12)));
			jPanel3.add(getLoadButton(), new Constraints(new Leading(9, 12, 12), new Leading(378, 12, 12)));
		}
		return jPanel3;
	}

	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			jPanel2.setLayout(new GroupLayout());
			jPanel2.add(getSaveButton(), new Constraints(new Leading(12, 12, 12), new Leading(12, 12, 12)));
			jPanel2.add(getClearButton(), new Constraints(new Leading(213, 84, 10, 10), new Leading(12, 12, 12)));
			jPanel2.add(getDeleteButton(), new Constraints(new Leading(110, 85, 12, 12), new Leading(12, 12, 12)));
		}
		return jPanel2;
	}

	private JButton getClearButton() {
		if (clearButton == null) {
			clearButton = new JButton();
			clearButton.setText("Clear");
			clearButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					windowList.clearSelection();
					functionList.clearSelection();
					readWriteCheckbox.setSelected(true);
				}

			});
		}
		return clearButton;
	}

	private JButton getSaveButton() {
		if (saveButton == null) {
			saveButton = new JButton();
			saveButton.setText("SaveAs");
			saveButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					validate("saveAsNew");
				}

			});
		}
		return saveButton;
	}
	
	private void fillDetails(String action) {
		
		if (action.equals("saveAsNew")) {
			accessFunctionsVec = new Vector<AccessFunction>();
			
			if (!functionList.isSelectionEmpty()) {
				int[] selectedIx = functionList.getSelectedIndices();
				ListModel<String> model = functionList.getModel();
				
			    for (int i = 0; i < selectedIx.length; i++) {	    	
			    	String functionName = model.getElementAt(selectedIx[i]).toString();
			    	
			    	AccessFunction accessFunction = new AccessFunction();
					accessFunction.setGroupname(groupNameComboBox2.getSelectedItem().toString());
					accessFunction.setWindowName(windowList.getSelectedValue());
					accessFunction.setFunctionName(functionName);
					if (readWriteCheckbox.isSelected()) {
						accessFunction.setIsAccessable(1);
					} else{
						accessFunction.setIsAccessable(0);
					}
					accessFunctionsVec.add(accessFunction);
			    }
			} else {
				accessWindowVec = new Vector<AccessWindow>();
				int[] selectedIx = windowList.getSelectedIndices();
				ListModel<String> model = windowList.getModel();
				
			    for (int i = 0; i < selectedIx.length; i++) {
			    	String windowName = model.getElementAt(selectedIx[i]).toString();
			    	
					AccessWindow accessWindow = new AccessWindow();
					accessWindow.setGroupname(groupNameComboBox2.getSelectedItem().toString());
					accessWindow.setWindowName(windowName);
					if (readWriteCheckbox.isSelected()) {
						accessWindow.setIsAccessable(1);
					} else{
						accessWindow.setIsAccessable(0);
					}
					accessWindowVec.add(accessWindow);
			    }
			}	
		} else if (action.equals("delete")) {
			accessFunctionsVec = new Vector<AccessFunction>();
			
			if (!functionList.isSelectionEmpty()) {
				int[] selectedIx = functionList.getSelectedIndices();
				ListModel<String> model = functionList.getModel();
				
			    for (int i = 0; i < selectedIx.length; i++) {	    	
			    	String functionName = model.getElementAt(selectedIx[i]).toString();
			    	
			    	AccessFunction accessFunction = new AccessFunction();
					accessFunction.setGroupname(groupNameComboBox2.getSelectedItem().toString());
					accessFunction.setWindowName(windowList.getSelectedValue());
					accessFunction.setFunctionName(functionName);
					if (readWriteCheckbox.isSelected()) {
						accessFunction.setIsAccessable(1);
					} else{
						accessFunction.setIsAccessable(0);
					}
					accessFunctionsVec.add(accessFunction);
			    }
			} else {
				accessWindowVec = new Vector<AccessWindow>();
				accessFunctionsVec = new Vector<AccessFunction>();
				int[] selectedIx = windowList.getSelectedIndices();
				ListModel<String> model = windowList.getModel();
				
			    for (int i = 0; i < selectedIx.length; i++) {
			    	String windowName = model.getElementAt(selectedIx[i]).toString();
			    	String groupName = groupNameComboBox2.getSelectedItem().toString();
			    	
					AccessWindow accessWindow = new AccessWindow();
					accessWindow.setGroupname(groupName);
					accessWindow.setWindowName(windowName);
					if (readWriteCheckbox.isSelected()) {
						accessWindow.setIsAccessable(1);
					} else{
						accessWindow.setIsAccessable(0);
					}
					accessWindowVec.add(accessWindow);
					
					AccessFunction accessFunction = new AccessFunction();
					accessFunction.setGroupname(groupName);
					accessFunction.setWindowName(windowName);
					accessFunctionsVec.add(accessFunction);
			    }
			}	
		}
	}
	private boolean validateDetails(String action){
		
		if (groupNameComboBox2.getSelectedIndex() == -1){
			commonUTIL.showAlertMessage("Select Group Name");
			return false;
		}
		
		if (windowList.isSelectionEmpty()) {
			commonUTIL.showAlertMessage("Select Window Name");
			return false;
		}
		
		int [] selectedWindowIndex = windowList.getSelectedIndices();
		if (action.equals("saveAsNew")) {
			if (selectedWindowIndex.length > 1 && !functionList.isSelectionEmpty()) {
				commonUTIL.showAlertMessage("Functions can be added for one Window at a time");
				return false;
			}
		} else if(action.equals("delete")) {
			if (selectedWindowIndex.length > 1 && !functionList.isSelectionEmpty()) {
				commonUTIL.showAlertMessage("Functions can be deleted for one Window at a time");
				return false;
			}		
		}
		
		/*if (functionList.isSelectionEmpty()) {
			commonUTIL.showAlertMessage("Select Function Name");
			return false;
		}*/
		
		return true;
	}
	
	private JCheckBox getReadWriteCheckbox() {
		if (readWriteCheckbox == null) {
			readWriteCheckbox = new JCheckBox();
			readWriteCheckbox.setSelected(true);
			readWriteCheckbox.setText("Read/ Write");
		}
		return readWriteCheckbox;
	}

/*	private JCheckBox getReadCheckbox() {
		if (readCheckbox == null) {
			readCheckbox = new JCheckBox();
			readCheckbox.setSelected(true);
			readCheckbox.setText("Read");
		}
		return readCheckbox;
	}*/

	private JComboBox<String>  getGroupNameComboBox2() {
		if (groupNameComboBox2 == null) {
			groupNameComboBox2 = new JComboBox<String>(groupNamesVec);				
		}
		return groupNameComboBox2;
	}

	private JLabel getJLabel2() {
		if (jLabel2 == null) {
			jLabel2 = new JLabel();
			jLabel2.setText("Group Name");
		}
		return jLabel2;
	}

	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			jPanel1.setLayout(new GroupLayout());
			jPanel1.add(getJScrollPane0(), new Constraints(new Leading(5, 276, 10, 10), new Leading(28, 330, 10, 10)));
			jPanel1.add(getJLabel0(), new Constraints(new Leading(5, 207, 207), new Leading(9, 12, 12)));
			jPanel1.add(getJScrollPane1(), new Constraints(new Leading(293, 236, 10, 10), new Leading(27, 326, 10, 10)));
			jPanel1.add(getJLabel1(), new Constraints(new Leading(293, 218, 218), new Leading(9, 12, 12)));
			jPanel1.add(getGroupNameComboBox2(), new Constraints(new Leading(538, 195, 10, 10), new Leading(27, 29, 12, 12)));
			jPanel1.add(getReadWriteCheckbox(), new Constraints(new Leading(538, 12, 12), new Leading(62, 12, 12)));
			jPanel1.add(getJLabel2(), new Constraints(new Leading(538, 12, 12), new Leading(5, 12, 12)));
		}
		return jPanel1;
	}

	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getFunctionList());
		}
		return jScrollPane1;
	}

	private JList<String> getFunctionList() {
		if (functionList == null) {
			functionList = new JList<String>();
			functionList.setLayoutOrientation(JList.VERTICAL);
			functionList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			//functionList.setCellRenderer(new SelectedListCellRenderer());
			
			/*functionList.addListSelectionListener(new ListSelectionListener() {
			    @Override
			    public void valueChanged(ListSelectionEvent e)
			    {
			        if(!e.getValueIsAdjusting()) {
			        	final List<String> selectedValuesList = functionList.getSelectedValuesList();	
			        	System.out.println("---" + selectedValuesList.size());
			        }
			    }
			});*/
		}
		return functionList;
	}
	

	public class SelectedListCellRenderer extends DefaultListCellRenderer {
	 
		private static final long serialVersionUID = 6547540276648896782L;

		@Override
	     public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
	         Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
	         String where = "functionName like '"+ value +"'";
	         List<AccessFunction> accessFunctions = Collections.emptyList();
			try {
				accessFunctions = (List<AccessFunction>) remoteAccessData.selectAccessFunction(where);
				if (!accessFunctions.isEmpty()) {
		        	if (accessFunctions.get(0) != null) {
			             c.setBackground(savedColor);
			             c.setForeground(Color.black);
			         }
		        }
			} catch (RemoteException e) {
				e.printStackTrace();
			}
	        return c;
	     }
	}
	
	private JLabel getJLabel1() {
		if (jLabel1 == null) {
			jLabel1 = new JLabel();
			jLabel1.setText("Function Name");
		}
		return jLabel1;
	}

	private JScrollPane getJScrollPane0() {
		if (jScrollPane0 == null) {
			jScrollPane0 = new JScrollPane();
			jScrollPane0.setViewportView(getWindowList());
		}
		return jScrollPane0;
	}

	private JList<String>  getWindowList() {
		if (windowList == null) {
			windowList = new JList<String> ();
			DefaultListModel<String>  listModel = new DefaultListModel<String> ();
			int size = windowNamesVec.size();
			for (int i=0; i < size; i++) {
				listModel.addElement(windowNamesVec.get(i));	
			}			
			windowList.setModel(listModel);
			windowList.setLayoutOrientation(JList.VERTICAL);
			windowList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			windowList.addListSelectionListener(new ListSelectionListener() {
			    @Override  		    
			    public void valueChanged(ListSelectionEvent e)
			    {
			        if(!e.getValueIsAdjusting()) {
			        	if (!windowList.getSelectedValuesList().isEmpty()) {
			        		 String windowName = windowList.getSelectedValuesList().get(0);
					           // System.out.println(windowName);
					            setFunctions(windowName);
			        	}
			        }
			    }
			});
		}
		return windowList;
	}
	
	private void setFunctions(String windowName) {
			
		if (!functionTable.containsKey(windowName)) {
			DefaultListModel<String>  listModel = new DefaultListModel<String>();
						
			Iterator<StartUPData> itr = functionNames.iterator();
			while(itr.hasNext()) {
				StartUPData data = itr.next();
				String fucntion = data.getName();
				if (fucntion.contains(windowName)) {
					listModel.addElement(fucntion);
				}
			}
			
			functionTable.put(windowName, listModel);
		}
		
		functionList.setModel(functionTable.get(windowName));
		
	}
	
	private JPanel getJPanel0() {
		if (jPanel0 == null) {
			jPanel0 = new JPanel();
			jPanel0.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
			jPanel0.setLayout(new GroupLayout());
			jPanel0.add(getJPanel1(), new Constraints(new Leading(456, 735, 10, 10), new Leading(3, 368, 10, 10)));
			jPanel0.add(getJPanel2(), new Constraints(new Leading(456, 734, 12, 12), new Leading(375, 50, 10, 10)));
			jPanel0.add(getJPanel3(), new Constraints(new Leading(5, 439, 12, 12), new Leading(7, 415, 10, 10)));
		}
		return jPanel0;
	}

	private JLabel getJLabel0() {
		if (jLabel0 == null) {
			jLabel0 = new JLabel();
			jLabel0.setText("Window Name");
		}
		return jLabel0;
	}

	@Override
	public ActionMap getHotKeysActionMapper() {		
		return actionMap;
	}

	@Override
	public JPanel getHotKeysPanel() {		
		return jPanel2;
	}

	@Override
	public ArrayList<Component> getFocusOrderList() {		
		ArrayList<Component> list = new ArrayList<Component>();
		return list;
	}
	
	private void extractStartUpData(Collection<StartUPData> extractCollection, Vector<String> vec) {
		
		Iterator<StartUPData> itr = extractCollection.iterator();
		
		while(itr.hasNext()) {			
			StartUPData data = itr.next();
			vec.addElement(data.getName());
		}
	}
	
	class TableModelUtil extends AbstractTableModel {
	    /**
		 * 
		 */
		private static final long serialVersionUID = -6780329573684594913L;

		final String[] columnNames;

	    Vector<AccessFunction> data;

	    public TableModelUtil(Vector<AccessFunction> myData, String col[]) {
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

	        AccessFunction dataVec = (AccessFunction) data.get(row);

	        switch (col) {

	        	case 0:
	                value = dataVec.getFunctionName();
	                break;
	        	case 1:
	        		value = dataVec.getFunctionName();
	        		if (dataVec.getIsAccessable() == 1) {
	        			value = "Read/ Write";
					} else {
						value = "Read";
					}
	                break;
	        }
	        return value;
	    }
	    public AccessFunction getValueAt(int row) {

	        return (AccessFunction) data.get(row);
	    }
	    
	    public boolean isCellEditable(int row, int col) {   
	    	  if (col == 0 || col ==1)          
	              return false;
	        
	        return true;            
	    }
	    
	    public void setValueAt(Object value, int row, int col) {
	    	if(row == -1)
	    		return;
	        if (value == null)
	                return;

	        AccessFunction ff = (AccessFunction)data.get(row);
	        ff.setFunctionName((String) value);

	        data.set(row, ff);
	        this.fireTableDataChanged();
         
	    }

	    public void addRow(Object value) {
	        data.add((AccessFunction) value);
	        this.fireTableDataChanged();
	    }

	    public void delRow(int row) {
	        if (row != -1) {
	            data.remove(row);
	            this.fireTableDataChanged();
	        }
	    }

	    public void udpateValueAt(Object value, int row, int col) {
	        data.set(row, (AccessFunction) value);
	        for (int i = 0; i < columnNames.length; i++)
	            fireTableCellUpdated(row, i);
	    }
	    public void removeALL() {
	        if (data != null) {
	            data.removeAllElements();
	        }
	        // data = null;
	        this.fireTableDataChanged();
	    }
	}
}

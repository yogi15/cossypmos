package apps.window.staticwindow;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.table.AbstractTableModel;

import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;

import util.commonUTIL;
import beans.Book;
import beans.Folder;
import dsServices.RemoteReferenceData;
import dsServices.ServerConnectionUtil;

//VS4E -- DO NOT REMOVE THIS LINE!
public class FolderWindow extends JPanel {
	public static  ServerConnectionUtil de = null;
	RemoteReferenceData remoteBORef; 
	
	
	private static final long serialVersionUID = 1L;
	private JTable jTable0;
	private JScrollPane jScrollPane0;
	private JLabel folderMasterLabel;
	private JButton saveButton;
	private JButton deleteButton;
	private JButton saveAsNewButton;
	private JLabel folderNameLabel;
	private JTextField folderNameTextField;
	 TableModelUtil model;
	Vector data = null;
	private JButton clearButton;
	private static final String PREFERRED_LOOK_AND_FEEL = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";

	public FolderWindow() {
		initData();
		initComponents();
	}

	private void initComponents() {
		setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
		setLayout(new GroupLayout());
		add(getFolderMasterLabel(), new Constraints(new Leading(27, 416, 12, 12), new Leading(14, 40, 10, 10)));
		add(getSaveButton(), new Constraints(new Leading(24, 10, 10), new Leading(447, 10, 10)));
		add(getDeleteButton(), new Constraints(new Leading(113, 10, 10), new Leading(447, 10, 10)));
		add(getSaveAsNewButton(), new Constraints(new Leading(204, 10, 10), new Leading(447, 10, 10)));
		add(getJScrollPane0(), new Constraints(new Leading(27, 423, 12, 12), new Leading(139, 290, 12, 12)));
		add(getFolderNameLabel(), new Constraints(new Leading(31, 99, 10, 10), new Leading(90, 22, 10, 10)));
		add(getFolderNameTextField(), new Constraints(new Leading(135, 242, 10, 10), new Leading(92, 22, 12, 12)));
		add(getClearButton(), new Constraints(new Leading(333, 10, 10), new Leading(447, 12, 12)));
		setSize(466, 497);
	}

	private JButton getClearButton() {
		
		if (clearButton == null) {
			
			clearButton = new JButton();
			clearButton.setText("CLEAR");
		
		} clearButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				
				  clearData();
			
			}			
		}); 
		
		return clearButton;
		
	}

	private JTextField getFolderNameTextField() {
		if (folderNameTextField == null) {
			folderNameTextField = new JTextField();
			folderNameTextField.setText("   ");
		}
		return folderNameTextField;
	}

	private JLabel getFolderNameLabel() {
		if (folderNameLabel == null) {
			folderNameLabel = new JLabel();
			folderNameLabel.setText("Folder Name");
		}
		return folderNameLabel;
	}

	private JButton getSaveAsNewButton() {
		if (saveAsNewButton == null) {
			saveAsNewButton = new JButton();
			saveAsNewButton.setText("SAVE AS NEW");
		}saveAsNewButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				  if (validateData()) {
					  
					   Folder folder = new Folder();
				       folder.setFolder_name(folderNameTextField.getText().trim());
				       folder.setId(0);
					 
				       try {
				    	   
				    	   int i =	remoteBORef.saveFolder(folder);
						
							if(i > 0) {
								
								commonUTIL.showAlertMessage("New Folder Saved");
								folder.setId(i);
								model.addRow(folder);
							
							} else if ( i < 0 ) {
								
								commonUTIL.showAlertMessage("Folder already exists");
								
							} else {
								
								commonUTIL.showAlertMessage("There was an error whike saving a Folder");
								
							}
								
					
				       } catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				  }				
			}			
		}); 
		return saveAsNewButton;
	}

	private JButton getDeleteButton() {
		
		if (deleteButton == null) {
			
			deleteButton = new JButton();
			deleteButton.setText("DELETE");
			
		}deleteButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				
				boolean isDeleted = false;
				
				 if (validateData()) {
					 
					 Folder folder = new Folder();
				     folder.setFolder_name(folderNameTextField.getText().trim());
				       
					 try {

							isDeleted = remoteBORef.deleteFolder(folder);

						} catch (RemoteException e1) {

							e1.printStackTrace();

						}

						// String mess = process.newRecordProcess(book);
						if (!isDeleted) {

							commonUTIL.showAlertMessage("Folder could not be deleted ");

						} else {

							commonUTIL.showAlertMessage("Folder deleted");

							model.delRow(jTable0.getSelectedRow());
							clearData();

						}
					 
				 }
				

			}
		});
		return deleteButton;
	}

	private JButton getSaveButton() {
		
		if (saveButton == null) {
			
			saveButton = new JButton();
			saveButton.setText("SAVE");
		
		}saveButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
		
				  if(validateData()) {
					 
					  Folder folder = (Folder) data.get(jTable0.getSelectedRow());
					  
					 try {
						 
						 folder.setFolder_name(folderNameTextField.getText().trim());
						 boolean flag =	remoteBORef.updateFolder(folder);
						 
						 if(flag) {
							
							 commonUTIL.showAlertMessage("Folder name updated");
							 model.udpateValueAt(folder, jTable0.getSelectedRow(), jTable0.getSelectedColumn());		
						
						 } else {
							 
							 commonUTIL.showAlertMessage("There was an error while updating the Folder name");
							 
						 }
					
					 } catch (RemoteException e1) {
					
						e1.printStackTrace();
					
					 }
					
				  }
				
			}

			
		}); 
		
		return saveButton;
		
	}
	
	private boolean validateData() {
		
		boolean flag = true;
		
		String name = folderNameTextField.getText().trim();
		  
		if( name.length() == 0 ) {
			
			commonUTIL.showAlertMessage("Please enter Folder name");
			flag = false;
			
		}
		
		return flag;
	}
	
	private void clearData() {
			
		folderNameTextField.setText("");
		
	}
	
	private JLabel getFolderMasterLabel() {
		
		if (folderMasterLabel == null) {
			
			folderMasterLabel = new JLabel();
			folderMasterLabel.setFont(new Font("Dialog", Font.BOLD, 24));
			folderMasterLabel.setText("FOLDER MASTER");
			
		}
		
		return folderMasterLabel;
		
	}

	private JScrollPane getJScrollPane0() {
		if (jScrollPane0 == null) {
			jScrollPane0 = new JScrollPane();
			jScrollPane0.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			jScrollPane0.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			jScrollPane0.setViewportView(getJTable0());
		}
		return jScrollPane0;
	}

	private JTable getJTable0() {
		if (jTable0 == null) {
			jTable0 = new JTable();
			
			String col []  = {"FolderNo","Folder Name"};
			model = new TableModelUtil(data,col);
		 
			jTable0.setModel(model);
			
			
		}jTable0.addMouseListener(new java.awt.event.MouseAdapter()  {
			
			
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				Folder folder = (Folder)	data.get(jTable0.getSelectedRow());
				folderNameTextField.setText(folder.getFolder_name());
			}
		

			
		}); 
		return jTable0;
	}
	public void initData() {
		de =ServerConnectionUtil.connect("localhost", 1099,commonUTIL.getServerIP() );
     	 try {
     		remoteBORef = (RemoteReferenceData) de.getRMIService("ReferenceData");
     		data = (Vector) remoteBORef.selectALLFolders();
     	
     		 		  
     		
  	  	 } catch (RemoteException e) {
  			e.printStackTrace();
  	}
	}
	
}
class TableModelUtil extends AbstractTableModel {   
    
	 final String[] columnNames;  
	    
	 final Vector<Folder> data;   
	        
	        
	 public TableModelUtil( Vector<Folder> myData,String col [] ) {   
	 	this.columnNames = col;
	this.data = myData;   
	}   

	    
	 public int getColumnCount() {   
	     return columnNames.length;   
	 }   
	    
	 public int getRowCount() {   
	     return data.size();   
	 }   
	 public String getColumnName(int col) {   
	     return columnNames[col];   
	 }   
	 public Object getValueAt(int row, int col) {   
	     Object value = null;  	 
	 
		 Folder folder = (Folder) data.get(row);
		 switch (col) {
	     case 0:
	         value = folder.getId();
	         break;
	     case 1:
	         value = folder.getFolder_name();
	         break;
		 }
	     return value;
	 }   
	   
	 public boolean isCellEditable(int row, int col) {   
	 return false;   
	 }   
	 public void setValueAt(Object value, int row, int col) {   
	         System.out.println("Setting value at " + row + "," + col   
	                            + " to " + value   
	                            + " (an instance of "    
	                            + value.getClass() + ")");  
	         if(value instanceof Folder) {
	     data.set(row,(Folder) value) ;
	     this.fireTableDataChanged();   
	         System.out.println("New value of data:");   
	         }
	        
	 }   
	    
	 public void addRow(Object value) {   
	    
		 data.add((Folder) value) ;
	 this.fireTableDataChanged();   
	   
	 }   
	    
	 public void delRow(int row) {   
	    
	 data.remove(row);          
	 this.fireTableDataChanged();   
	    
	 }   
	 
	 public void udpateValueAt(Object value, int row, int col) {   
	     System.out.println("Setting value at " + row + "," + col   
	                        + " to " + value   
	                        + " (an instance of "    
	                        + value.getClass() + ")");   
	  
	     data.set(row,(Folder) value) ;
	 fireTableCellUpdated(row, col);   
	     System.out.println("New value of data:");   
	    
	}   
	    
	    
	    
	}   

package apps.window.tradewindow.FXPanels;

import java.awt.Component;
import java.awt.KeyboardFocusManager;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Date;
import java.util.EventObject;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;

import util.RemoteServiceUtil;
import util.commonUTIL;

import com.jidesoft.combobox.MultiSelectListExComboBox;
import com.jidesoft.grid.DateCellEditor;

import dsServices.RemoteReferenceData;
import apps.window.referencewindow.DateCellEditor12;
import beans.Attribute;
import beans.StartUPData;



//VS4E -- DO NOT REMOVE THIS LINE!
public class TradeAttributesD extends JPanel {

        private static final long serialVersionUID = 1L;
        public JTable jTable1;
        private JScrollPane jScrollPane0;
        private JTabbedPane jTabbedPane0;
        public  String tradeAction = "";
        public  boolean isfwOpTrade= false;
        
        private static final String PREFERRED_LOOK_AND_FEEL = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
        FWDOptionPanel fwdOp;
        RemoteReferenceData remoteReferenceData;
        Collection instrumenTypeVal = new Vector();
        Collection instrumenTypeStartupData = new Vector();
        
        Vector<Attribute> data = new Vector<Attribute>();
         /**
         * @return the data
         */
        public Vector<Attribute> getData() {
                return data;
        }

		public void changeXccySplitRate(String name,String value) {
			for(int i=0;i<mod.getRowCount();i++) {
				String columnName = (String) mod.getValueAt(i, 0);
				if(columnName.equalsIgnoreCase(name)) {
					mod.setValueAt(value, i, 1);
					break;
				}
			}
		}

        /**
         * @param data the data to set
         */
        public void setData(Vector<Attribute> data) {
                this.data = data;
        }
        TableModelUtil mod;
        String col[] = { "Attributes", "Values" };
        public EachRowEditor rowEditor;

        public TradeAttributesD() {
        	initComponents();
        }
        private void getInstrumentVal() {
        	Iterator it = instrumenTypeStartupData.iterator();
        	
        	while (it.hasNext()) {
        		StartUPData data = (StartUPData)it.next();
        		instrumenTypeVal.add(data.getName());
        	}
        }
        private void initComponents() {        	
        	remoteReferenceData = RemoteServiceUtil.getRemoteReferenceDataService();
        	try {
        		instrumenTypeStartupData = remoteReferenceData.getStartUPData("InstrumentTypeVal");
        		getInstrumentVal();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			
        	mod = new TableModelUtil(data,col);
            Attribute bean = new Attribute();
            jTable1  = new JTable() ;
            rowEditor = new EachRowEditor(jTable1);
            jTable1.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
            // mod.addRow(bean);
            jTable1.setModel(mod);
            setLayout(new GroupLayout());
            add(getJTabbedPane0(), new Constraints(new Leading(5, 309, 10, 10), new Leading(4, 470, 10, 10)));
            setSize(327, 484);               
        }
        private JTabbedPane getJTabbedPane0() {
            if (jTabbedPane0 == null) {
                    jTabbedPane0 = new JTabbedPane();
                    jTabbedPane0.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, null, null));
                    jTabbedPane0.addTab("Attributes ", getJScrollPane0());
            }
            return jTabbedPane0;
        }
        private JScrollPane getJScrollPane0() {
            if (jScrollPane0 == null) {
                    jScrollPane0 = new JScrollPane();
                    jScrollPane0.setViewportView(getJTable0());
            }
            return jScrollPane0;
        }
        private JTable getJTable0() {
	        if (jTable1 == null) {
	                jTable1 = new JTable();
	                jTable1.setModel(new DefaultTableModel(new Object[][] { { "0x0", "0x1", }, { "1x0", "1x1", }, }, new String[] { "Title 0", "Title 1", }) {
	                        private static final long serialVersionUID = 1L;
	                        Class<?>[] types = new Class<?>[] { Object.class, Object.class, };
	
	                        public Class<?> getColumnClass(int columnIndex) {
	                                return types[columnIndex];
	                        }
	                });
	        }
	        return jTable1;
        }
        public JTextField getJTextFieldBox() {
            final JTextField values = new JTextField(100);
            values.addKeyListener(new KeyAdapter() {
            	@Override
		        public void keyTyped(KeyEvent e) {
		            if (e.getKeyChar() == KeyEvent.VK_ENTER) { 
		            	String attributeName = values.getText();
		                int i = jTable1.getSelectedRow();
		                Attribute attribute = getFilterBeanAtRow(i);
		                if(attribute != null) {
		                	attribute.setValue(attributeName);
		                    removeFilterBeanAtRow(i);
		                    addFilterBeanAt(i, attribute);
		                }
		            }
		        }
             });
            values.addFocusListener(new FocusAdapter() {       
            	public void FocusLost(FocusEvent e) {
            		String attributeName = values.getText();
            		int i = jTable1.getSelectedRow();                                    
                    Attribute attribute = getFilterBeanAtRow(i);
                    
                    if(attribute != null) {
                            attribute.setValue(attributeName);
                            removeFilterBeanAtRow(i);
                            addFilterBeanAt(i, attribute);
                    }
                }
            });
            return values;
        }        
        public void addNewRow(Attribute bean) {
        	if(mod != null) 
        		mod.addRow(bean);
        }
        public Attribute getFilterBeanAtRow(int row) {
            if(row >= 0)
            	return ( Attribute) data.get(row);
            return null;
        }
        public void removeFilterBeanAtRow(int row) {
        	data.remove(row);
        }        
        public void addFilterBeanAt(int row,Attribute filterBean) {
            data.add(row,filterBean);
        }
        public int getTableRowCount() {
            return jTable1.getRowCount();
        }
        public void addRowEditor(int row,MultiSelectListExComboBox multiCombox,String columnName) {
            final MultiSelectionRenderer ed = new MultiSelectionRenderer(multiCombox,null);
            rowEditor.setEditorAt(row,ed);
            jTable1.getColumn(columnName).setCellEditor(rowEditor);
        }
      
        public void addRowEditor(int row,int col,MultiSelectListExComboBox multiCombox,String columnName) {
            final MultiSelectionRenderer ed = new MultiSelectionRenderer(multiCombox,null);
            rowEditor.setEditorForRowCol(row,col,ed);
            jTable1.getColumn(columnName).setCellEditor(rowEditor);
        }
        public void addRowEditor(int row,int col,JTextField jtextField,String columnName) {
            MyTableCellEditor1 ed = new MyTableCellEditor1(jtextField,null);
            rowEditor.setEditorForRowCol(row,col,ed);
            jTable1.getColumn(columnName).setCellEditor(rowEditor);
        }
        public void addRowEditor(int row,int col,DateCellEditor multiCombox,String columnName) {
    //       final MultiSelectionRenderer ed = new MultiSelectionRenderer(multiCombox,null);
            rowEditor.setEditorForRowCol(row,col,multiCombox);
            jTable1.getColumn(columnName).setCellEditor(rowEditor);
        }
        public void addRowEditor(int row,JComboBox combox,String columnName) {
            DefaultCellEditor ed = new DefaultCellEditor(combox);
            rowEditor.setEditorAt(row,ed);
            jTable1.getColumn(columnName).setCellEditor(rowEditor);
        }
        public void addRowEditor(int row,int col,JComboBox combox,String columnName) {
            DefaultCellEditor ed = new DefaultCellEditor(combox);
            rowEditor.setEditorForRowCol(row,col,ed);
            jTable1.getColumn(columnName).setCellEditor(rowEditor);
        }
        
        //@yogesh 08/02/2015
        // this will add dateCellEditior in jtable cell
        public void addRowEditor(int row, String columnName) {
            final DateCellEditor12 ed = new DateCellEditor12();
            rowEditor.setEditorAt(row,ed);
            jTable1.getColumn(columnName).setCellEditor(rowEditor);
        }
        private EachRowEditor getRowEditor() {
            return rowEditor;
        }
        /**
         * @param rowEditor the rowEditor to set
         */
        private void setRowEditor(EachRowEditor rowEditor) {
            this.rowEditor = rowEditor;
        }
	     public JComboBox getJComboxBox(final String [] criteria) {
	        final JComboBox criteriaC = new JComboBox(criteria){ 
	            public void processFocusEvent(FocusEvent fe) { 
	                super.processFocusEvent(fe); 
	                Component focusOwner = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner(); 
	      
	               if (isDisplayable() && fe.getID()==FocusEvent.FOCUS_GAINED 
	                        && focusOwner==this && !isPopupVisible()) { 
	                            showPopup(); 
	                } 
	          } 
	     }; 
	        criteriaC.addItemListener(new ItemListener() {
	        	@Override
            	public void itemStateChanged(ItemEvent e) {
                    if (criteriaC.getSelectedIndex() != -1) {
	                    String attributeName = criteriaC.getSelectedItem().toString();
	                    int i = jTable1.getSelectedRow();
	                    
	                    if(i == -1)
	                            return;
	                    
	                    jTable1.setValueAt(attributeName, i, 1);	                   
	                    
	                    if ((jTable1.getValueAt(i,0)).toString().equalsIgnoreCase("InstrumentType") && 
	                                    (instrumenTypeVal.contains(String.valueOf(jTable1.getValueAt(i,1))))){
	                    	setCheckBox(true);
	                    	fwdOp.startDate.setDate((commonUTIL.getCurrentDate()));
	                        	                            
	                    } else {
	                    	//fwdOp.startDate.setSelectedItem(commonUTIL.convertDateTOString(commonUTIL.getCurrentDate()));
	                        setCheckBox(false);
	                    }
                                    
                    }
                }
	        });
           return criteriaC;
	                     
	     }
         
         public void setFXPanelObjs(FWDOptionPanel fwdOp) {                 
                 this.fwdOp = fwdOp;
         }
         private void setCheckBox(boolean enabled) {        	 
                 fwdOp.startDate.setEnabled(enabled);             
         }
         
        Hashtable<String,String[]> stringarray = new Hashtable<String,String[]>();
        public String []  convertVectortoSringArray(Vector v,String sname) {
                 
	        String name [] = null;
	        int i=0;
	        name = stringarray.get(sname);
	        if(name != null) 
	                return name;
	        else 
	        if(v != null ) {
	                name = new String[v.size()]; 
	               /* name[i] = "Select Value";
	                i = 1;*/
	                Iterator its = v.iterator();
	                while(its.hasNext()) {
	                        
	                        name [i] = ( (StartUPData) its.next()).getName();
	                        i++;
	                }
	        }
	        stringarray.put(sname, name);
	        return name;                                           
        } 
        public void clearllCriterial() {
	        mod = null;
	        mod = new TableModelUtil(data,col);
	        jTable1.setModel(mod);
	        data.clear();         
        }
        
        public void clearllCriteriaModel() {
		    mod.removeALL();                
		    data.clear();    
        }
        public void deleteRowCriteria(int row) {
	        mod.delRow(row);
	        jTable1.repaint();
	        mod.fireTableDataChanged();
        }
        class TableModelUtil extends AbstractTableModel {
                final String[] columnNames;

                Vector<Attribute> data;

                public TableModelUtil(Vector<Attribute> myData, String col[]) {
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

                    Attribute attribute = (Attribute) data.get(row);

                    switch (col) {

                    case 0:
                            value = attribute.getName();
                            break;
                    case 1:
                            value = attribute.getValue()==null?"":attribute.getValue();
                            break;
                    
                    }
                    return value;
                }

                public boolean isCellEditable(int row, int col) {                        
                    String attrName = getValueAt(row, 0).toString();
                        
                    if ((attrName.equals("Trade Date") || attrName.equals("TradeModifiedDateTime"))  
                    		&& !tradeAction.equals("New")) {                                
                    	return false;
                    } 
                    
                    //@yogesh 01/02/2015
                    // if it is fwdOption and takeupTrade then it cant change the Instrumenttype
                    else if (isfwOpTrade && attrName.equals("InstrumentType")) {
                    	return false;
                    } else if (isfwOpTrade && attrName.equals("ParentID")) {
                    	return false;
                    }
                    return true;            
                }
                
                public void setValueAt(Object value, int row, int col) {
                	if(row == -1)
                		return;
                    if (value == null)
                            return;
                    System.out.println("Setting value at " + row + "," + col + " to "
                                    + value + " (an instance of " + value.getClass() + ")");
                    Attribute ff = (Attribute)      data.get(row);
                    ff.setValue((String) value);
  
                    data.set(row, ff);
                    this.fireTableDataChanged();
                        //System.out.println("New value of data:");           
                }

                public void addRow(Object value) {
	                data.add((Attribute) value);
	                this.fireTableDataChanged();
                }

                public void delRow(int row) {
                    if (row != -1) {
                        data.remove(row);
                        this.fireTableDataChanged();
                    }
                }

                public void udpateValueAt(Object value, int row, int col) {
                    data.set(row, (Attribute) value);
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

        class EachRowEditor implements TableCellEditor {
                protected Hashtable editors;

                protected TableCellEditor editor, defaultEditor;
                
                protected DateCellEditor12 dateCellEditor12; 
                
                
                JTable table;

                /**
                 * Constructs a EachRowEditor. create default editor
                 * 
                 * @see TableCellEditor
                 * @see DefaultCellEditor
                 */
                public EachRowEditor(JTable table) {
                    this.table = table;
                    editors = new Hashtable();
                    defaultEditor = new DefaultCellEditor(new JTextField());
                }

                /**
                 * @param row
                 *            table row
                 * @param editor
                 *            table cell editor
                 */
                public void setEditorAt(int row, TableCellEditor editor) {
                    editors.put(new Integer(row), editor);
                }
                 public void setEditorForRowCol(int row, int col,TableCellEditor e )
                 {
                	//System.out.println("From roweditors adding row " + row + " col " + col + " "+ e.getCellEditorValue());
	                Vector columns = (Vector) editors.get(new Integer(row));
	                if(columns != null) {
	                        columns.add(col-1, e);
	                } else {
	                        columns = new Vector();
	                        columns.add(col-1, e);
	                }
  
	                editors.put(new Integer(row), columns);
             }
                 public TableCellEditor getEditor(int row,int col)
             {
                //System.out.println("From getEditor row " + row + " col " + col);
                TableCellEditor c1 = null;
                if(row >= 0 && col >= 0) {
                Vector columns = (Vector) editors.get(new Integer(row)); 
                if(!commonUTIL.isEmpty(columns))
                	return (TableCellEditor)columns.get(col-1);
                	return c1;
                }
                 return c1;
             }
                public Component getTableCellEditorComponent(JTable table,
                                Object value, boolean isSelected, int row, int column) {
                    // editor = (TableCellEditor)editors.get(new Integer(row));
                    // if (editor == null) {
                    // editor = defaultEditor;
                    // }
                    return editor.getTableCellEditorComponent(table, value, isSelected,
                                    row, column);
                }

                public Object getCellEditorValue() {
                    if(editor.getCellEditorValue() instanceof String) 
                            return (String) editor.getCellEditorValue();
                    if(editor.getCellEditorValue() instanceof Date) 
                            return (String) commonUTIL.dateToString((Date)editor.getCellEditorValue());
                    return null;
                }

                public boolean stopCellEditing() {
                    return editor.stopCellEditing();
                }

                public void cancelCellEditing() {
                    editor.cancelCellEditing();
                }

                public boolean isCellEditable(EventObject anEvent) {
                    if(anEvent instanceof KeyEvent) {
                            selectEditor((KeyEvent) anEvent);
                    } else {
                    	selectEditor((MouseEvent) anEvent);
                    }
                    if(editor == null)
                            return false;
                    return editor.isCellEditable(anEvent);
                }

                public void addCellEditorListener(CellEditorListener l) {
                        editor.addCellEditorListener(l);
                }

                public void removeCellEditorListener(CellEditorListener l) {
                    editor.removeCellEditorListener(l);
                }

                public boolean shouldSelectCell(EventObject anEvent) {
                    if(anEvent instanceof KeyEvent) {
                        selectEditor((KeyEvent) anEvent);
                    } else {
                    	selectEditor((MouseEvent) anEvent);
                    }
                    return editor.shouldSelectCell(anEvent);
                }
                
                protected void selectEditor(MouseEvent e) {
                    int row =0;
                    int col =0;
                    String value = "";
                    if (e == null) {
                        row = table.getSelectionModel().getAnchorSelectionIndex();
                        col = table.getSelectedColumn();
                        
                    } else {
                        row = table.rowAtPoint(e.getPoint());
                        col = table.columnAtPoint(e.getPoint());
                    }
                    //System.out.println("From selectEditor row == " + row + " col == " + col);
                    
                    //@yogesh 08/02/2015
                    // if column is trade date and trademodifiedTime then we need to return DateCellEditor12
                    // else other editor
                    value =table.getModel().getValueAt(row, 0).toString();
                    
                    if (value.equalsIgnoreCase("Trade Date") || value.equalsIgnoreCase("TradeModifiedDateTime")) {
                    	dateCellEditor12 = new DateCellEditor12();
                    	//dateCellEditor12.datetimeFormat = true;
                    	dateCellEditor12.setTimeDisplayed(true);
                    	dateCellEditor12.addCellEditorListener(new CellEditorListener() {

                			@Override
                			public void editingCanceled(ChangeEvent e) {
                				// TODO Auto-generated method stub
                				// Systemout.println(e.getSource());
                				// Systemout.println(e.toString());
                				
                			}

                			@Override
                			public void editingStopped(ChangeEvent e) {                				
                				DateCellEditor12 dd =  (DateCellEditor12)e.getSource();
                				// to avoid null pointer when a date window is opened by esc is used to not select value
                				//we have used if condition below
                				if (dd.getCellEditorValue()!=null) {
                					table.getModel().setValueAt((dd.getCellEditorValue().toString()),table.getSelectionModel().getAnchorSelectionIndex(), 0); 
                				}
                				               		                				
                			}                        	
                        });
                    	editor = dateCellEditor12;
                    	
                    } else {
                    	 if(col == -1) {
                             editor = defaultEditor;
                         } else if(col == 0) {
                             editor = defaultEditor;
                         } else {                    
     		                Vector cols = (Vector) editors.get(new Integer(row));
     		                
     		                if(!commonUTIL.isEmpty(cols)) 
     		                	editor = (TableCellEditor) cols.get(col-1);
     		            }
                    }
   
	                if (editor == null) {
	                	editor = defaultEditor;
	                }
                }
                protected void selectEditor(KeyEvent e) {
	                int row =0;
	                int col =0;
                       
                    row = table.getSelectionModel().getAnchorSelectionIndex();
                    col = table.getSelectedColumn();
            
                    //System.out.println("From selectEditor row == " + row + " col == " + col);
                    if(col == -1) {
                        editor = defaultEditor;
                    } else if(col == 0) {
                        editor = defaultEditor;
                    } else {                    
                    	Vector cols = (Vector) editors.get(new Integer(row));
                    	editor = (TableCellEditor) cols.get(col-1);
                    }
                    
                    if (editor == null) {
                        editor = defaultEditor;
                    }
                }
        }

        class MultiSelectionRenderer extends DefaultCellEditor {

                public MultiSelectionRenderer(final MultiSelectListExComboBox comboBox,
                                CellEditorListener listener) {
                        super(comboBox);
                        _listener = listener;
                        if (listener != null)
                                addCellEditorListener(listener);
                        comboBox.addItemListener(new ItemListener() {

                                @Override
                                public void itemStateChanged(ItemEvent e) {
                                        if (comboBox.getSelectedObjects().length > 0) {
                                                // TODO Auto-generated method stub
                                                String ss = "";
                                                Object obj[] = comboBox.getSelectedObjects();
                                                for (int i = 0; i < comboBox.getSelectedObjects().length; i++) {
                                                        ss = ss + (String) obj[i] + ",";
                                                }
                                                set_value(ss);

                                        }
                                }
                        });
                        // TODO Auto-generated constructor stub
                }

                Object _value = null;

                /**
                 * @return the _value
                 */
                private Object get_value() {
                        return _value;
                }

                /**
                 * @param _value
                 *            the _value to set
                 */
                private void set_value(Object _value) {
                        this._value = _value;
                }

                int _row = -1;
                int _column = -1;
                CellEditorListener _listener = null;

                @Override
                public Object getCellEditorValue() {
                        // TODO Auto-generated method stub
                        return (String) get_value();
                }

        }
        
        class MyTableCellEditor1 extends AbstractCellEditor implements TableCellEditor {

                  JComponent component = null;
                  public MyTableCellEditor1(JTextField jtextF,CellEditorListener listener) {
                          component = (JTextField) jtextF;
                         
                  }
                  public Component getTableCellEditorComponent(final JTable table, Object value, boolean isSelected,
                      int rowIndex, int vColIndex) {

                    ((JTextField) component).setText((String) value);
                    if(component instanceof JTextField) {
                    component.addKeyListener(new KeyAdapter() {

                          @Override
                          public void keyTyped(KeyEvent e) {
                                  //System.out.println(e.getKeyChar());
                    
                          //  if(e.getKeyChar() == KeyEvent.VK_ENTER) { 
                                        
                            /*  String attributeName = ((JTextField) component).getText().toString();
                              
                                int i = table.getSelectedRow();
                                FilterBean filler =getFilterBeanAtRow(i);
                                                if(filler != null) {
                                                        filler.setAnd_or(attributeName);
                                                        removeFilterBeanAtRow(i);
                                                        addFilterBeanAt(i, filler); 
                                                }*/

                           // }
                                        }
                          
                                });
                    component.addFocusListener(new FocusAdapter() {
                          public void focusGained(FocusEvent e) { 
                        
                                
                                                
                                                
                                                
                                                //      searchPanel.setFocusable(true);
                          }
                                                
                                        
                          });
                    }
                    return component;
                  }

                  public Object getCellEditorValue() {
                    return ((JTextField) component).getText();
                  }
                }

class MyTableCellEditor  extends DefaultCellEditor { 

        JTextField component = new JTextField();
    Object value  = null;
    CellEditorListener _listener = null;
 public MyTableCellEditor(JTextField jtextF,CellEditorListener listener) {
         
          super(jtextF);
          component = jtextF;
          _listener = listener;
          if (listener != null)
                        addCellEditorListener(listener);
          component.addKeyListener(new KeyAdapter() {

          @Override
          public void keyTyped(KeyEvent e) {
                  System.out.println(e.getKeyChar());
    
            
                        
                                        // TODO Auto-generated method stub
                  //System.out.println("Coming from ruennenenenene ");
                
                                
                                //      searchPanel.setFocusable(true);

                                
                        }
          
                });
          component.addFocusListener(new FocusAdapter() {
          public void focusGained(FocusEvent e) { 
        
                
                                String attributeName = component.getText();
                setValue(attributeName);
                int i = jTable1.getSelectedRow();
                Attribute filler =getFilterBeanAtRow(i);
                                if(filler != null) {
                                        filler.setName(attributeName);
                                        removeFilterBeanAtRow(i);
                                        addFilterBeanAt(i, filler);
                                }
                                //      searchPanel.setFocusable(true);
          }
                                
                        
          });
  }
   
 public void setValue(String value1) {
         value = value1;
 }
 public Object getValue() {
        return (String) value;
 }
 @Override
  public Object getCellEditorValue() {
    return getValue();
  }

}

public void isCellEditable(int t, int i) {
        // TODO Auto-generated method stub
        
}


}
package apps.window.limitdashboardpanel;
import javax.swing.*;
import javax.swing.table.*;

import java.awt.Component;
import java.util.Vector;

 public class JTableX extends JTable
 {
     protected RowEditorModel rm;

     public JTableX()
     {
         super();
         rm = null;
     }

     public JTableX(TableModel tm)
     {
         super(tm);
         rm = null;
     }

     public JTableX(TableModel tm, TableColumnModel cm)
     {
         super(tm,cm);
         rm = null;
     }

     public JTableX(TableModel tm, TableColumnModel cm,
      ListSelectionModel sm)
     {
         super(tm,cm,sm);
         rm = null;
     }
     public void changeSelection(    int row, int column, boolean toggle, boolean extend) {    
    	 super.changeSelection(row, column, toggle, extend);    
    	 if (editCellAt(row, column))    {       
    		 Component editor = getEditorComponent();      
    		 editor.requestFocusInWindow();   
    		 }
    	 }

     public JTableX(int rows, int cols)
     {
         super(rows,cols);
         rm = null;
     }

     public JTableX(final Vector rowData, final Vector columnNames)
     {
         super(rowData, columnNames);
         rm = null;
     }

     public JTableX(final Object[][] rowData, final Object[] colNames)
     {
         super(rowData, colNames);
         rm = null;
     }

     // new constructor
     public JTableX(TableModel tm, RowEditorModel rm)
     {
         super(tm,null,null);
         this.rm = rm;
     }

     public void setRowEditorModel(RowEditorModel rm)
     {
         this.rm = rm;
    }
     public RowEditorModel getRowEditorModel()
     {
         return rm;
     }

     public TableCellEditor getCellEditor(int row, int col)
     {
    	 System.out.println("From JTablex getting row " + row + " col " + col);
         TableCellEditor tmpEditor = null;
         if (rm!=null)
             tmpEditor = rm.getEditor(row,col-1);
         if (tmpEditor!=null)
             return tmpEditor;
         return super.getCellEditor(row,col);
     }
 }



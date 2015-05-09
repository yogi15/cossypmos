package apps.window.tradewindow.cashflowpanel;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

import com.jidesoft.converter.BooleanConverter;
import com.jidesoft.converter.DateConverter;
import com.jidesoft.converter.DoubleConverter;
import com.jidesoft.converter.IntegerConverter;
import com.jidesoft.converter.MonthNameConverter;
import com.jidesoft.converter.ObjectConverterManager;
import com.jidesoft.converter.PercentConverter;
import com.jidesoft.converter.QuarterNameConverter;
import com.jidesoft.converter.YearNameConverter;
import com.jidesoft.grid.CellEditorManager;
import com.jidesoft.grid.CellRendererManager;
import com.jidesoft.grouper.ObjectGrouperManager;
import com.jidesoft.plaf.LookAndFeelFactory;

import util.commonUTIL;

import beans.Flows;

public abstract class CashFlowPanel extends JPanel  {
	
	
    public abstract void setCashFlows(Vector cashFlows);
    public abstract DefaultTableModel getCashFlows(Vector cashFlows);
	public void setCashFlows2(Vector cashFlows2) {
		// TODO Auto-generated method stub
		
	}

}

package apps.window.tradewindow;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

import beans.Trade;

public abstract class BackOfficePanel extends JPanel {
	
	public Trade trade = null;
	public abstract void fillJTabel(Vector data);
	 public Trade getTrade() {
			
			return trade;
		}

		public void setTrade(Trade trade) {
			this.trade = trade;
		}

}

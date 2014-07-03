package apps.window.tradewindow;

import java.util.Collection;
import java.util.Vector;

import javax.swing.JPanel;

import productPricing.Pricer;

import beans.Trade;

public abstract class CommonPanel extends JPanel {
	
	
	abstract public void buildTrade(Trade trade,String actionType);
	abstract public void openTrade(Trade trade);
	abstract public void setPanelValue(CommonPanel tradeValue);
	abstract public void setTradePanelValue(CommonPanel tradeValue);
	abstract public Collection getCashFlows();
	abstract public Pricer getPricer();
     public Collection getCashFlows2() {
		return null;
	}
	

}

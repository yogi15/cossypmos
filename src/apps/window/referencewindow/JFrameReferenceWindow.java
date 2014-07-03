package apps.window.referencewindow;

import javax.swing.JFrame;
import javax.swing.JPanel;

import marketquotes.FeedListener;
import dsManager.QuoteManager;
import util.ClassInstantiateUtil;
import util.commonUTIL;

public class JFrameReferenceWindow extends JFrame  {
	 QuoteManager manager = null;
	 FeedListener feedListener = null;
	public JFrameReferenceWindow(String name) {
		setTitle("Cosmos " + name + " Window");
		JPanel staticDataPanel = makeProductPanel(name);
		setSize(700,700);
		add(staticDataPanel);
		if(name.equalsIgnoreCase("MarketQuote")) {
		   manager = new QuoteManager("localhost",commonUTIL.getLocalHostName(),"QuoteManager");
		   manager.start(manager);
		   if(staticDataPanel instanceof FeedListener) {
			   feedListener = (FeedListener) staticDataPanel;
		      manager.addListeners("Random", feedListener);
		   }
	}
		
		
	}
	
	
	protected JPanel makeProductPanel(String name) {
        String productWindowName = "apps.window.referencewindow." + name + "Window";

        JPanel productWindow = null;
        try {
        Class class1 =    ClassInstantiateUtil.getClass(productWindowName,true);
        return (JPanel) class1.newInstance();
           //  productWindow = (BondPanel) 
        } catch (Exception e) {
            System.out.println( e);
        }

        return productWindow;
    }
  //  
}

package apps.window.staticwindow;

import javax.swing.JFrame;

import javax.swing.JFrame;
import javax.swing.JPanel;

import util.ClassInstantiateUtil;

public class JFrameStaticWindow  extends JFrame {
	
	
	
	public JFrameStaticWindow(String name) {
		setTitle( "Cosmos " + name + " Window");
		JPanel staticDataPanel = makeStaticPanel(name);
		setSize(1200,1500);
		add(staticDataPanel);
		
	}
	
	
	protected JPanel makeStaticPanel(String name) {
        String productWindowName = "apps.window.staticwindow." + name + "Window";

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

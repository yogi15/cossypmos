package apps.window.referencewindow;

import javax.swing.JFrame;
import javax.swing.JPanel;

import beans.Users;

import util.ClassInstantiateUtil;

public class JFrameAccountingWindow extends JFrame {
	
	
	
	public JFrameAccountingWindow(String name) {
		setTitle(name + " Window");
		JPanel accountDataPanel = makeAccountPanel(name);
		setSize(1200,1500);
		add(accountDataPanel);
		
	}
	public JFrameAccountingWindow(String name,Users user) {
		setName(name);
		setUser(user);
		setTitle(name + "   Window  " + user.getUser_name());
		JPanel accountDataPanel = makeAccountPanel(name);
		setSize(1200,1500);
		add(accountDataPanel);
     //   initComponents();
    }
	
	private void setUser(Users user) {
		// TODO Auto-generated method stub
		
	}
	protected JPanel makeAccountPanel(String name) {
        String accountWindowName = "apps.window.referencewindow." + name + "Window";

        JPanel productWindow = null;
        try {
        Class class1 =    ClassInstantiateUtil.getClass(accountWindowName,true);
        return (JPanel) class1.newInstance();
           //  productWindow = (BondPanel) 
        } catch (Exception e) {
            System.out.println( e);
        }

        return productWindow;
    }
}
package apps.window.operationwindow;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.rmi.RemoteException;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

import dsManager.TaskManager;
import dsServices.RemoteBOProcess;
import dsServices.RemoteReferenceData;
import dsServices.RemoteTask;
import dsServices.RemoteTrade;
import dsServices.ServerConnectionUtil;
import beans.Users;
import apps.window.operationwindow.jobpanl.TaskMainPanel;
import apps.window.utilwindow.Login;

import util.ClassInstantiateUtil;
import util.commonUTIL;

public class JFrameJobApplication extends JFrame {
	JTabbedPane jobTabs;
	Users user = null;
	 public static  ServerConnectionUtil de = null;
		RemoteReferenceData remoteBORef;
		RemoteTask remoteTask;
		RemoteTrade remoteTrade;
		RemoteBOProcess remoteBo;
		TaskManager taskManager = null;
	public JFrameJobApplication(String name,Users user)  {
		super(name);
		setUser(user);
					de =ServerConnectionUtil.connect("localhost", 1099,commonUTIL.getServerIP() );
					taskManager = new TaskManager("localhost",commonUTIL.getLocalHostName(),"TaskManager");
				    
					taskManager.start(taskManager);
					try {
						remoteBORef = (RemoteReferenceData) de.getRMIService("ReferenceData");
						remoteTask = (RemoteTask) de.getRMIService("Task");
						remoteTrade = (RemoteTrade)  de.getRMIService("Trade");
						remoteBo = (RemoteBOProcess) de.getRMIService("BOProcess");
						
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
						
			TaskMainPanel taskMain = new TaskMainPanel(remoteBORef,remoteTask,remoteTrade,remoteBo,user);
			taskManager.setJobPanels(taskMain);
		//	taskMain.setUser(user);
			add(taskMain);
		this.addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowIconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeiconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeactivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosing(WindowEvent arg0) {
				// TODO Auto-generated method stub
				if(taskManager != null)
					taskManager.stop();
					taskManager = null;
			}
			
			@Override
			public void windowClosed(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowActivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
					
			}
		});
    
			    
	}
		public static void main(String args[]) {
	//	JFrameJobApplication jobs = new JFrameJobApplication("Jobs");
		//jobs.setVisible(true);
		//jobs.setSize(900,700);
		
		}
		public Users getUser() {
			return user;
		}
		public void setUser(Users user) {
			this.user = user;
		}
	

}

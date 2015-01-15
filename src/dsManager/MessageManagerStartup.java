package dsManager;

import java.io.InterruptedIOException;

import beans.Users;

import util.commonUTIL;
import dsServices.ServiceManager;

public class MessageManagerStartup extends ServiceManager {

	MessageManager amanager = null;
    Thread t = null;
	String managerName = "MessageManager";
	Users user = null;
	/**
	 * @return the user
	 */
	public Users getUser() {
		return user;
	}


	/**
	 * @param user the user to set
	 */
	public void setUser(Users user) {
		this.user = user;
	}
	public static void main(String args[]) {
		MessageManagerStartup startMessageService = new MessageManagerStartup();
		startMessageService.start();
		
		
		
	}
	@Override
	public void start() {
		// TODO Auto-generated method stub
		user = new Users();
		user.setUser_name("DummyManager");
		user.setHostName(commonUTIL.getServerIP());
		user.setApplicattionNameLoginOn("MessageApplication");
		setUser(user);
		String hostName = commonUTIL.getLocalHostName();
		String localHost = "localhost";
		amanager	= new MessageManager(localHost,hostName,managerName,this);
		amanager.publishStartEvent(managerName,"Started",getClientID());
		amanager.start(amanager);
		
		
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		try {
			amanager.publishStartEvent(managerName,"Stopped",getClientID());
			
			amanager.stop();
			
			throw new InterruptedIOException();
		} catch (InterruptedIOException e) {
			// TODO Auto-generated catch block
			commonUTIL.display("MessageManager", "MessageManager is stop");
			System.exit(0);
		} finally {
			amanager = null;
			System.exit(0);
		}
	
		
	}
	

}

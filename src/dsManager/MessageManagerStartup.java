package dsManager;

import java.io.InterruptedIOException;

import util.commonUTIL;
import dsServices.ServiceManager;

public class MessageManagerStartup implements ServiceManager {

	MessageManager amanager = null;
	Thread t = null;
	
	@Override
	public void start() {
		// TODO Auto-generated method stub
		String managerName = "MessageManager";
		String hostName = commonUTIL.getLocalHostName();
		String localHost = "localhost";
		amanager	= new MessageManager(localHost,hostName,managerName);
		
		amanager.start(amanager);
		
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		try {
			amanager.stop();
			throw new InterruptedIOException();
		} catch (InterruptedIOException e) {
			// TODO Auto-generated catch block
			commonUTIL.display("MessageManager", "MessageManager is stop");
		}
	
		
	}

}

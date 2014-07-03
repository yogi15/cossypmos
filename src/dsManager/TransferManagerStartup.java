package dsManager;

import java.io.InterruptedIOException;

import dsServices.ServiceManager;
import util.commonUTIL;

public class TransferManagerStartup implements ServiceManager {
	TransferManager amanager = null;
	Thread t = null;
	@Override
	public void start() {
		// TODO Auto-generated method stub
		String managerName = "TransferManager";
		String hostName = commonUTIL.getLocalHostName();
		String localHost = "localhost";
		amanager = new TransferManager(localHost,hostName,managerName);
	
		amanager.start(amanager);
		
	}
	
	
	public static void main(String args[]) {
		TransferManagerStartup startTransferService = new TransferManagerStartup();
		startTransferService.start();
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
//		System.out.println(t.getId());
		
		try {
			amanager.stop();
			throw new InterruptedIOException();
		} catch (InterruptedIOException e) {
			// TODO Auto-generated catch block
			commonUTIL.display("TransferManager", "TransferManager is stop");
		}
	
	//	System.out.println(t.getId());
		
	}


}
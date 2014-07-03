package dsServices;

import java.rmi.Remote;
import java.rmi.RemoteException;

import beans.DealBean;
import beans.ServerBean;

public interface RemoteDeal extends Remote {
	
	public ServerBean connect() throws RemoteException;
	public ServerBean connect(String username,String password) throws RemoteException;
	public int publishEvent(String messageIndicator,String queueName,String messageType,Object object) throws RemoteException;

}

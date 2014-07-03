package dsServices;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Properties;

import util.commonUTIL;

import mqServices.Broker.StartMQBroker;

import dbSQL.UsersSQL;
import dbSQL.dsSQL;

import dsEventProcessor.EventProducerServer;

import beans.DealBean;
import beans.ServerBean;
import beans.Users;


public class ServerControllerImp  implements RemoteDeal ,ServiceManager
{
	 
   int      thisPort;
   String   thisAddress;
   Registry registry; 
   String username;
   String password;
   ServerConnectionUtil sconn = null;
   EventProducerServer eventServer = null;

   public void start()     {
     // get the address of this host.
   		
           
          
           
          
       
        
       System.out.println("this address="+thisAddress+",port="+thisPort);
       try{
    	   thisPort=1099; 
    	   	
           try {
			thisAddress = (InetAddress.getLocalHost()).toString();
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
           sconn = new ServerConnectionUtil();
           sconn.setdefault(sconn);
           sconn._dataServerName = "localhost";
           sconn._rmiPort = 1099;
           sconn._hostName = thisAddress;
           String host = thisAddress.substring(0, thisAddress.indexOf('/'));
           System.out.println("Server starting at port "+ sconn._rmiPort + " hostName " + sconn._hostName );
     
       registry = LocateRegistry.createRegistry( thisPort );
   
       getInputStream();
       startRMIServices(props,registry);
      
       StartMQBroker startMQ = new StartMQBroker(host,61616);
       
       
   //    registry.rebind("rmiServer", this); 
      
      
       }
       catch(RemoteException e){
    	   commonUTIL.displayError("Server","Error in Starting Server  <<<<<<<<<<<<<  " , e);
       }

   	
   }
 
   public void stop() {
   	try {
   		
				try {
					registry.unbind("rmiServer");
					System.out.println(" rmiServer going down");
				} catch (AccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
   	
   	
   }
   public static String Path = null;
	public static String getPath() {
		return Path;
	}

	public static void setPath(String path) {
		Path = path;
	}
	 Properties props = new Properties();
   private static Properties loadProperties(){
       Properties props = new Properties();
       FileInputStream fis = null;
       try {
    	   String path = util.PropertiesUtil.getInstance().getPropertyList();
    	   commonUTIL.setPath(path);
    	    
    	   fis = new FileInputStream(new java.io.File("..\\resources\\RMIServices.properties"));
			 //fis = new FileInputStream(new java.io.File("E:\\eclipse\\workspace\\sbiCCIL\\resources\\" + "RMIServices.properties"));
    	//   fis = new FileInputStream(new java.io.File(path + "RMIServices.properties"));
			//props.load(getInputStream());
			fis.close();
       }catch(FileNotFoundException e) {
			System.out.println(e);
			System.exit(0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}
       return props;
   }
   
   private void getInputStream() {
	      try {
			props.load(this.getClass().getClassLoader().getResourceAsStream("resources/RMIServices.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
   }
   private void startRMIServices(Properties props,Registry regist) {
   	String serviceList = props.getProperty("services");
       String[] services = serviceList.split(",");
       for (int i = 0; i < services.length; i++) {

           String service = services[i];
           try {
               System.out.println( "Starting service: "
                       + service);
               ConfigService serviceConfig = ServerConnectionUtil.loadServiceConfiguration(service,
                                                                             props);
               ServiceInit.startService(serviceConfig,regist);
           } catch (Exception e) {
           	System.out.println( " Error starting service " + service + " "+ e);
               throw new RuntimeException("Error starting service "
                       + service, e);
           }
       }
   }
   static public void main(String args[])
   
   {
	   ServerControllerImp s = null;
       try{

       s=new ServerControllerImp();
      
       s.start();
     

   } 

   catch (Exception e) {

          e.printStackTrace();
       //   s.stop();
         // System.exit(1);

   } finally {
   	//s.stop();
   	System.out.println("coming");
   }

    

}

@Override
public ServerBean connect(String username,String password) throws RemoteException {
	// TODO Auto-generated method stub
	ServerBean sbean = new ServerBean();
	sbean.set_dataServerName(sconn.getdefault()._dataServerName);
	sbean.setRmiServices(sconn.getdefault().getRmiServices());
	sbean.set_rmiPort(sconn.getdefault()._rmiPort);
	sbean.set_hostName(sconn.getdefault()._hostName);
	sbean.setWindowSetting("String");
	Users user = (Users) UsersSQL.selectUsers(username, username, dsSQL.getConn());
	System.out.println("Connected ... " + sconn.getdefault()._hostName  + " with user " + user.getUser_name());
	
	
	return sbean;
}


@Override
public ServerBean connect() throws RemoteException {
	// TODO Auto-generated method stub
	ServerBean sbean = new ServerBean();
	sbean.set_dataServerName(sconn.getdefault()._dataServerName);
	sbean.setRmiServices(sconn.getdefault().getRmiServices());
	sbean.set_rmiPort(sconn.getdefault()._rmiPort);
	sbean.set_hostName(sconn.getdefault()._hostName);
	sbean.setWindowSetting("String");
	
	System.out.println("Connected ... " + sconn.getdefault()._hostName);
	
	
	return sbean;
}

public String getUsername() {
	return username;
}

public void setUsername(String username) {
	this.username = username;
}

public String getPassword() {
	return password;
}

public void setPassword(String password) {
	this.password = password;
}

@Override
public int publishEvent(String messageIndicator,String queueName, String messageType,
		Object object) throws RemoteException {
	// TODO Auto-generated method stub
	if(eventServer == null)
	 eventServer = new EventProducerServer(commonUTIL.getLocalHostName()+":61616");
	
	if(!eventServer.isFlagStartup()) {
	Thread sendMessage =  new Thread(eventServer);
	// setNewMessage(messageProducer);
	 sendMessage.start();
	} else {
	eventServer.produceNewMessage(messageIndicator, messageType, messageType,(Serializable) object,null);
	}
	return 0;
}



	




}
package dsManager;

import java.io.InterruptedIOException;
import java.io.Serializable;
import java.rmi.RemoteException;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;

import logAppender.TransferServiceAppenderLog;

import org.apache.activemq.ActiveMQConnectionFactory;

import dsEventProcessor.EventProcessor;
import dsEventProcessor.TaskEventProcessor;
import dsEventProcessor.TransferEventProcessor;
import dsServices.RemoteBOProcess;
import dsServices.RemoteMO;
import dsServices.RemoteReferenceData;
import dsServices.RemoteTrade;
import dsServices.ServerConnectionUtil;
import beans.Trade;
import beans.Transfer;
import beans.Users;
import util.commonUTIL;

public abstract class ControllerManager  implements Runnable , ExceptionListener {
	
	 static private String hostName = "";
	 public static  ServerConnectionUtil de = null;
	 public String managerName = "";
	 public String queueName = "TRADE";
	 Thread managerThread = null;
	 public String getQueueName() {
		return queueName;
	}
	

	ControllerManager manager = null;
	 public String getManagerName() {
		return managerName;
	}

			
		
	public static ServerConnectionUtil getDe() {
		return de;
	}
	 
	 public ControllerManager(String host,String hostName,String managerName) {
		 de =ServerConnectionUtil.connect(host, 1099,commonUTIL.getServerIP());
		 this.hostName = hostName;
		 this.managerName = managerName;
	 }
	 public ControllerManager(String host,String hostName,String managerName,String userName,String Password) {
		 de =ServerConnectionUtil.connectServer(host, 1099,commonUTIL.getServerIP(),managerName,userName,Password);
		 this.hostName = hostName;
		 this.managerName = managerName;
	 }
	 public ControllerManager(String host,String hostName,String managerName,Users user) {
		 de =ServerConnectionUtil.connectServer(host, 1099,commonUTIL.getServerIP(),managerName,user);
		
		 this.hostName = hostName;
		 this.managerName = managerName;
	 }
	@Override
		public void onException(JMSException e) {
			commonUTIL.displayError( getManagerName(),"Error in listening" , e);
			
		}

		@Override
		public synchronized void run() {
			  while (!isInterrupted()) {

				try {
					
					  ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://"+hostName+":61616");

			            // Create a Connection
			            Connection connection = connectionFactory.createConnection();
			            connection.start();

			            connection.setExceptionListener(this);

			            // Create a Session
			            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			            // Create the destination (Topic or Queue)
			           
			            
			            Destination destination =  session.createTopic("TRADE");

			            // Create a MessageConsumer from the Session to the Topic or Queue
			            MessageConsumer consumer = session.createConsumer(destination);
			            Message message = consumer.receive(19000);

			            if (message instanceof ObjectMessage) {
			            	ObjectMessage oMessage = (ObjectMessage) message;
			            	//System.out.println(manager.managerName + "       >>>>>>>>>>>>>>>   " + ((EventProcessor)oMessage.getObject()).getClassName());
			            
			            	//if(checkEvents((EventProcessor)  oMessage.getObject())) {
			            	manager.handleEvent((EventProcessor) oMessage.getObject());
			            	//}
			            	
			               // pframe.refresh();
			    			//Thread.sleep(8000);
			            } 

			            consumer.close();
			            session.close();
			            connection.close();
			            Thread.sleep(800);
				 } catch (java.lang.NullPointerException e) {
						// TODO Auto-generated catch block
					   commonUTIL.displayError("ControllerManager " +  getManagerName(), "run()", e);
						//System.exit(0);
					      
					
				} catch (java.lang.InterruptedException e) {
					// TODO Auto-generated catch block
					commonUTIL.display(manager.getManagerName(), manager.getManagerName() +"  is stop");
					//System.exit(0);
				} catch (JMSException j) {
					// TODO Auto-generated catch block
					commonUTIL.display(manager.getManagerName(), manager.getManagerName() +"  is stop");
					//System.exit(0);
				} catch(Exception e) {
					 commonUTIL.displayError( getManagerName(), "run()", e);
					// System.exit(0);
				 
				}

			}
			
		}
		
        private boolean checkEvents(EventProcessor event) {
        	boolean flag = true;
        	if(event instanceof TaskEventProcessor) 
        		flag = false;
        	return flag;
        }
		private boolean isInterrupted() {
			// TODO Auto-generated method stub
			if(managerThread != null)
			 return managerThread.isInterrupted();
			return true;
		}


		public void publishEvent(String messageIndicator,String queueName,String messageType, EventProcessor event) {
			
			de.publishEvent(messageIndicator, queueName, messageType, (Serializable) event);
		}
	public void handleEvent(EventProcessor event)	 {
		//System.out.println(event.getOccurrenceTime());
	}
		
	
	public void start(ControllerManager manager) {
		this.manager = manager;
		managerThread = new Thread(manager);
		
		System.out.println("Starting >>>  " + manager.getManagerName() + " processor ");
		managerThread.start();
		
	}
	
	public void stop() {
	//	commonUTIL.display(manager.getManagerName(), "Stop <<<<<<<<<  " +manager.getManagerName());
		try {
			
			managerThread.interrupt();
			throw new InterruptedIOException();
			
			
		} catch (InterruptedIOException e) {
			// TODO Auto-generated catch block
if(managerThread.isInterrupted()) {
	System.out.println(manager.getManagerName() + " stop");
	TransferServiceAppenderLog.printLog("DEBUG", "TransferService is stop ");
	
	commonUTIL.display(manager.getManagerName(), manager.getManagerName() +"  is stop");
	//System.exit(0);
			}
			
		}
		
	//manager
		
		
	}
}

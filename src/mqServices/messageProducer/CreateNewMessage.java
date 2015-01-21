package mqServices.messageProducer;


import java.io.Serializable;

import javax.jms.*;   
import javax.naming.*;   

import org.apache.activemq.ActiveMQConnectionFactory;

import util.commonUTIL;


public class CreateNewMessage implements Runnable {
        String hostname;
        String message = null;
        String queueName = null;
        String messageType = null;
        Serializable object = null;
        Connection connection = null;
        String flagonStartup = "false";
         ActiveMQConnectionFactory connectionFactory =null;
        public CreateNewMessage(String hostname) {
                this.hostname = hostname;
                connectionFactory = new ActiveMQConnectionFactory("tcp://"+hostname);
                try {
                        connection = connectionFactory.createConnection();
                         connection.start();
                } catch (JMSException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
        }
        public void produceNewMessage(String message,String queueName,String messageType,Serializable object,String atStartup) {
                //this.hostname = hostname;
                
        
                this.message = message;
                this.queueName = queueName;
                this.messageType = messageType;
                this.object = object;
                if(atStartup == null)
                   run();
        }
    public synchronized void run() {
        
        try {
                if(messageType == null)
                        return;
                
            if(messageType.equalsIgnoreCase("Text") && messageType != null) {
                sendTextMessage();
            } else {
                
                sendObjectMessage();
                Thread.sleep(100);
            }
        }
        catch (Exception e) {
          commonUTIL.displayError("CreateNewMessage", "Run", e);
        }
        
    }
    
    private void sendObjectMessage() {
                // TODO Auto-generated method stub
        Session session = null;
        try {
                
           

            // Create a Connection
                

            // Create a Session
                if(queueName != null) {
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Create the destination (Topic or Queue)
            
            Destination destination = session.createTopic(queueName);
      //      System.out.println("TTTTTTTTTTTTTTTTTTTTTTTTTT producer " +    queueName);
                  
            // Create a MessageProducer from the Session to the Topic or Queue
            MessageProducer producer = session.createProducer(destination);
            
            producer.setDeliveryMode(DeliveryMode.PERSISTENT);

            // Create a messages
           // String text = message;//
          //  commonUTIL.display("CreateNewMessage"," = sendObjectMessage  Message send on QueueName  " + queueName +  "  " +   Thread.currentThread().getName() + " : " + message);
            ObjectMessage message = session.createObjectMessage(object);
           

            // Tell the producer to send the message
           // System.out.println("Message send " + Thread.currentThread().getName() + " : " + message.getObject());
       //     System.out.println("Sent message: "+ message.hashCode() + " : " + Thread.currentThread().getName());
            producer.send(message);
           // producer.send(destination, message);
                }
            // Clean up
          //  session.close();
            //connection.close();
            
                }catch(Exception e) {
                        commonUTIL.displayError("CreateNewMessage", "sendObjectMessage", e);
                         try {
                                        session.close();
                                        connection.close();
                                } catch (JMSException e1) {
                                        // TODO Auto-generated catch block
                                        e1.printStackTrace();
                                }
                 
                }
        }
        public void sendTextMessage() {
                Session session = null;
        
        // Create a ConnectionFactory
        try {
        
                if(queueName != null) {
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // Create the destination (Topic or Queue)
        Destination destination = session.createQueue(queueName);

        // Create a MessageProducer from the Session to the Topic or Queue
        MessageProducer producer = session.createProducer(destination);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

        // Create a messages
        String text = message;//
        commonUTIL.display("CreateNewMessage","Message send " + Thread.currentThread().getName() + " : " + message);
        TextMessage message = session.createTextMessage(text);
        

        // Tell the producer to send the message
      //  System.out.println("Message send as TEXT ** " + Thread.currentThread().getName() + " : " + message);
   //     System.out.println("Sent message: "+ message.hashCode() + " : " + Thread.currentThread().getName());
        producer.send(message);

                }
        
        }catch(Exception e) {
                try {
                                session.close();
                                connection.close();
                        } catch (JMSException e1) {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                        }
                commonUTIL.displayError("CreateNewMessage", "sendTextMessage", e);
        }
        
    }
        public String getFlagonStartup() {
                return flagonStartup;
        }
        public void setFlagonStartup(String flagonStartup) {
                this.flagonStartup = flagonStartup;
        } 
   
}

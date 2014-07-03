package dsEventProcessor;

import java.io.Serializable;

import util.commonUTIL;

import beans.Users;

public class EventProcessor implements Serializable,Cloneable {
	
	  private long occurrenceTime;
	int eventid = 0;
	private String occrrenceDate;
	int tradeID = 0;
	int taskID = 0;
	boolean isEventProcess = false;
	
	
	
	
	
	
	public boolean isEventProcess() {
		return isEventProcess;
	}
	public void setEventProcess(boolean isEventProcess) {
		this.isEventProcess = isEventProcess;
	}
	public int getTaskID() {
		return taskID;
	}
	public void setTaskID(int taskID) {
		this.taskID = taskID;
	}
	public final int getTradeID() {
		return tradeID;
	}
	int transferID = 0;
	public final int getTransferID() {
		return transferID;
	}
	public int getEventid() {
		return eventid;
	}

	public void setEventid(int eventid) {
		this.eventid = eventid;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	String eventType = "";
	String processName = "";
	Users user = null;
	String ServerName = "";
	
	public EventProcessor() {
		eventid = 0;
    	occurrenceTime = System.currentTimeMillis();
    	occrrenceDate = commonUTIL.dateToString(commonUTIL.getCurrentDate());
	}
	public String getOccurrenceDate()
    {
    	return occrrenceDate;
    }
	public long getOccurrenceTime()
    {
    	return occurrenceTime;
    }
			
	int tradeVersion =0;
	public int getTradeVersion() {
		return tradeVersion;
	}
	public void setTradeVersion(int tradeVersion) {
		this.tradeVersion = tradeVersion;
	}
	public int getTransferVerson() {
		return transferVerson;
	}
	public void setTransferVerson(int transferVerson) {
		this.transferVerson = transferVerson;
	}
	int transferVerson =0;
	
	public String toString() {
		return "Event (" + getClass().getName() + ") : " + eventid;
	    }
	
	public String getEventType() {
		return eventType;
	    }
	public String getName() {
		return getClassName();
	}
	final public String getClassName() {
		String className = getClass().getName();
		String subStr = className.substring(className.lastIndexOf('.')+1);
		return subStr;
	    }
	
	final public String getServerName() { return ServerName;}
    final public void setServerName(String s) { ServerName=s;}

}

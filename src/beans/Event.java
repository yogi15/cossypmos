package beans;

public class Event {
	

	int eventID;
	public int getEventID() {
		return eventID;
	}
	public void setEventID(int eventID) {
		this.eventID = eventID;
	}
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getTradeID() {
		return tradeID;
	}
	public void setTradeID(int tradeID) {
		this.tradeID = tradeID;
	}
	public int getTransferID() {
		return transferID;
	}
	public void setTransferID(int transferID) {
		this.transferID = transferID;
	}
	public boolean isConsumedFlag() {
		return consumedFlag;
	}
	public void setConsumedFlag(boolean consumedFlag) {
		this.consumedFlag = consumedFlag;
	}
	public String getSqlType() {
		return sqlType;
	}
	public void setSqlType(String sqlType) {
		this.sqlType = sqlType;
	}
	String eventType;
	String type;
	int tradeID;
	int transferID;
	boolean consumedFlag = false;
	String sqlType;

}

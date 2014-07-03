package beans;

import java.io.Serializable;

public class WFConfig implements Serializable {
	
	int id;
	String productType;
	String productSubType;
	String Action;
	String orgStatus;
	String currentStatus;
	int Auto;
	String rule;
	int le;
	int usid;
	String type;
	boolean task=false;
	
	public boolean isTask() {
		return task;
	}
	public void setTask(boolean task) {
		this.task = task;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getProductSubType() {
		return productSubType;
	}
	public void setProductSubType(String productSubType) {
		this.productSubType = productSubType;
	}
	public String getAction() {
		return Action;
	}
	public void setAction(String action) {
		Action = action;
	}
	public String getOrgStatus() {
		return orgStatus;
	}
	public void setOrgStatus(String orgStatus) {
		this.orgStatus = orgStatus;
	}
	public String getCurrentStatus() {
		return currentStatus;
	}
	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}
	public int getAuto() {
		return Auto;
	}
	public void setAuto(int auto) {
		Auto = auto;
	}
	public String getRule() {
		return rule;
	}
	public void setRule(String rule) {
		this.rule = rule;
	}
	public int getLe() {
		return le;
	}
	public void setLe(int le) {
		this.le = le;
	}
	public int getUsers() {
		return usid;
	}
	public void setUsers(int id) {
		this.usid = id;
	}

}


package beans;

import java.io.Serializable;

public class Users implements Serializable {
	
	String user_groups;
	String user_name;
	String password;
	int id;
	public String getUser_groups() {
		return user_groups;
	}
	public void setUser_groups(String user_groups) {
		this.user_groups = user_groups;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	


	
	

}

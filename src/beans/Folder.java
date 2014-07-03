package beans;

import java.io.Serializable;

public class Folder implements Serializable {
	
	int id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFolder_name() {
		return Folder_name;
	}
	public void setFolder_name(String folderName) {
		this.Folder_name = folderName;
	}
	String Folder_name;
	
	
	

}

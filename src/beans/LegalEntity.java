package beans;

import java.io.Serializable;

import util.commonUTIL;

public class LegalEntity implements Serializable {

	int id;
	String Status = null;
	String name;
	String role;
	String attributes = "";
	String alias = "";
	String contact = "";
	String country = "";
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getstatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getAttributes() {
		return attributes;
	}
	public void setAttributes(String attributes) {
		this.attributes = attributes;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	public String getAttributeValue(String attributeDataName) {
		
		String attr1 = getAttributes();
		String attributes [] = attr1.split(";");
		String value = "";
		
		for(int i=0;i<attributes.length;i++) {
			String attribute = 	attributes[i];
			
			if(attribute.contains(attributeDataName)) {
				value = attribute.substring(attribute.indexOf("=")+1, attribute.length());
				if(!commonUTIL.isEmpty(value))
					break;
			}
		}
		
		return value;
		
	}
}

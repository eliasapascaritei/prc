package com.daniel.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.daniel.dao.UserDao;

public class User {

	private int userid;
	private String firstName;
	private String lastName;
	private String cnp;
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/*public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}*/
	public String getCnp() {
		return this.cnp;
	}
	public void setCnp(String userCnp) {
		this.cnp = userCnp;
	}
	@Override
	public String toString() {
		return "{ \"userid\":" + userid + ",\"firstName\":\"" + firstName
				+ "\", \"lastName\":\"" + lastName + "\", \"CNP\":\"" + cnp + "\"}";
	}
	public String JsToString(){
		List<User> users = new ArrayList<User>();
		UserDao dao = new UserDao();
		users = dao.getAllUsers();
		String s = "[";
		for(User elem : users){
			s += elem.toString() + ",";
			
		}
		s += "]";
		return s;
				
	}
	
	
}

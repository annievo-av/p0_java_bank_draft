package com.to;

public class UserType {
	private String username;
	private String usertype;

	public UserType() {
	}

	public UserType(String username, String usertype) {
		super();
		this.username = username;
		this.usertype = usertype;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}

}

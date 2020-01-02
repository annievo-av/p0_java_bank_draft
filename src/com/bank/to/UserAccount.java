package com.bank.to;

public class UserAccount {
	private String username;
	private String password;
	private String usertype;

	private UserCard card;

	public UserAccount() {
	}

	public UserAccount(String username, String password, String usertype) {
		super();
		this.username = username;
		this.password = password;
		this.usertype = usertype;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}

	public UserCard getCard() {
		return card;
	}

	public void setCard(UserCard card) {
		this.card = card;
	}

	@Override
	public String toString() {
		return "username=" + username + ", password=" + password + ", usertype=" + usertype + ", card=" + card;
	}

}

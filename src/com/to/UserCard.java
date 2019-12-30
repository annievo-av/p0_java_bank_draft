package com.to;

public class UserCard {
	private int cardNumber;
	private String type;
	private double balance;
	private String username;

	private String receiver;
	private String sender;

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public UserCard() {
	}

	public UserCard(int cardNumber, String type, double balance, String username) {
		super();
		this.cardNumber = cardNumber;
		this.type = type;
		this.balance = balance;
		this.username = username;
	}

	public int getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(int cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getType() {
		return type;
	}

	public void setType(String cardType) {
		this.type = cardType;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "UserCard [cardNumber=" + cardNumber + ", type=" + type + ", balance=" + balance + ", username="
				+ username + "]";
	}

}

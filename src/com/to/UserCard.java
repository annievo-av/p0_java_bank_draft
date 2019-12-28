package com.to;

public class UserCard {
	private int cardNumber;
	private String type;
	private int balance;

	public UserCard() {
	}

	public UserCard(int cardNumber, String type, int balance) {
		super();
		this.cardNumber = cardNumber;
		this.type = type;
		this.balance = balance;
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

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

}

package com.to;

public class UserCard {
	private int cardNumber;
	private String type;
	private double balance;

	public UserCard() {
	}

	public UserCard(int cardNumber, String type, double balance) {
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

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		return "UserCard [cardNumber=" + cardNumber + ", type=" + type + ", balance=" + balance + "]";
	}

}

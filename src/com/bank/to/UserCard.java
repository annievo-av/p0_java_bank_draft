package com.bank.to;

public class UserCard {
	private int cardNumber;
	private double balance;
	private String cardType;
	private String username;

	private String receiver;
	private String sender;
	
	private String transaction_message;
	private String transaction_time;
	
	public UserCard() {
	}

	public UserCard(int cardNumber, String cardType, double balance, String username) {
		super();
		this.cardNumber = cardNumber;
		this.cardType = cardType;
		this.balance = balance;
		this.username = username;
	}

	public int getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(int cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
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

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getTransactionMessage() {
		return transaction_message;
	}

	public void setTransactionMessage(String transaction_message) {
		this.transaction_message = transaction_message;
	}

	public String getTransactionTime() {
		return transaction_time;
	}

	public void setTransactionTime(String transaction_time) {
		this.transaction_time = transaction_time;
	}

	@Override
	public String toString() {
		return "cardNumber=" + cardNumber + ", cardType=" + cardType + ", balance=" + balance + ", username="
				+ username;
	}

}

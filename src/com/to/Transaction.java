package com.to;

public class Transaction {
	private int cardNumber;
	private double amount;
	private String sender;
	private String receiver;

	public Transaction() {
	}

	public Transaction(int cardNumber, double amount, String sender, String receiver) {
		super();
		this.cardNumber = cardNumber;
		this.amount = amount;
		this.sender = sender;
		this.receiver = receiver;
	}

	public int getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(int cardNumber) {
		this.cardNumber = cardNumber;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

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

}

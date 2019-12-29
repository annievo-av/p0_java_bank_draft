package com.to;

public class Transaction {
	private int cardNumber;
	private double withdraw;
	private double deposit;

	public Transaction() {
	}

	public Transaction(int cardNumber, double withdraw, double deposit) {
		super();
		this.cardNumber = cardNumber;
		this.withdraw = withdraw;
		this.deposit = deposit;
	}

	public int getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(int cardNumber) {
		this.cardNumber = cardNumber;
	}

	public double getWithdraw() {
		return withdraw;
	}

	public void setWithdraw(double withdraw) {
		this.withdraw = withdraw;
	}

	public double getDeposit() {
		return deposit;
	}

	public void setDeposit(double deposit) {
		this.deposit = deposit;
	}

}

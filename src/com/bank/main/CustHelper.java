package com.bank.main;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.bank.bo.CustBo;
import com.bank.bo.CustBoImpl;
import com.bank.bo.EmpBo;
import com.bank.bo.EmpBoImpl;
import com.bank.bo.UserBo;
import com.bank.bo.UserBoImpl;
import com.bank.exception.BusinessException;
import com.bank.to.UserAccount;
import com.bank.to.UserCard;

public class CustHelper {

	final static Logger log = Logger.getLogger(CustHelper.class);

	Scanner input = new Scanner(System.in);
	UserBo userBo = new UserBoImpl();
	CustBo custBo = new CustBoImpl();
	EmpBo empBo = new EmpBoImpl();
	UserCard userCard = new UserCard();

	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
	Date date = new Date(System.currentTimeMillis());

	public void customerEntryLogic(UserAccount userAccount) throws BusinessException {
		log.info("Enter task number according to the below criteria:");
		log.info("1. View Account Balance");
		log.info("2. Deposit");
		log.info("3. Withdraw");
		log.info("4. Transfer");
		log.info("5. Pending money");
		log.info("6. Apply for a new bank account");
		log.info("Enter any key to exit!");
		String option = input.nextLine();
		switch (option) {
		case "1":
			viewAccountBalance(userAccount);
			customerEntryLogic(userAccount);
			break;
		case "2":
			deposit(userAccount);
			customerEntryLogic(userAccount);
			break;
		case "3":
			withdraw(userAccount);
			customerEntryLogic(userAccount);
			break;
		case "4":
			transfer(userAccount);
			customerEntryLogic(userAccount);
			break;
		case "5":
			moneyPendingInfo(userAccount, userCard);
			customerEntryLogic(userAccount);
			break;
		case "6":
			applyNewCard(userAccount, userCard);
			customerEntryLogic(userAccount);
			break;
		default:
			logout();
		}
	}

	public void viewAccountBalance(UserAccount userAccount) throws BusinessException {
		List<UserCard> cardBalanceInfoList = custBo.cardBalanceInfoList(userAccount);
		log.info("Your card(s) balance: ");
		for (UserCard card : cardBalanceInfoList) {
			if (card.getUsername().equals(userAccount.getUsername())) {
				log.info("Card Number: " + card.getCardNumber() + "\tBalance: " + card.getBalance());
			}
		}
	}

	public void deposit(UserAccount userAccount) throws BusinessException {
		viewAccountBalance(userAccount);
		List<UserCard> cardBalanceInfoList = custBo.cardBalanceInfoList(userAccount);
		UserCard myCard = new UserCard();
		int cardNumberForDeposit = 0;
		double depositAmount;
		cardBalanceInfoList.size();
		if (cardBalanceInfoList.size() == 0) {
			log.info("You currently don't own any card!");
		} else if (cardBalanceInfoList.size() == 1) {
			do {
				log.info("Enter amount: ");

				while (input.nextLine().matches("^[a-zA-Z]*$")) {
					log.info("Invalid input. Please enter number only!");
					log.info("Enter amount: ");
				}

				depositAmount = Double.parseDouble(input.nextLine());

				if (depositAmount < 0) {
					log.info("Deposit amount cannot be negative. Try again!");
				}
			} while (depositAmount < 0);

			if (cardBalanceInfoList.get(0).getCardNumber() == cardNumberForDeposit) {
				myCard = cardBalanceInfoList.get(0);
				myCard.setBalance(myCard.getBalance() + depositAmount);
				custBo.updateCardBalance(myCard);
				log.info("Succeeded.");
				myCard.setTransactionTime(formatter.format(date));
				myCard.setTransactionMessage("Deposited $" + depositAmount + " to " + myCard.getCardNumber());
				custBo.transactionMessage(myCard);
			}
		} else {
			do {
				log.info("You have more than one card, please enter the card number that you want to deposit to: ");
				cardNumberForDeposit = Integer.parseInt(input.nextLine());
				for (int i = 0; i < cardBalanceInfoList.size(); i++) {
					if (cardBalanceInfoList.get(i).getCardNumber() == cardNumberForDeposit) {
						myCard = cardBalanceInfoList.get(i);
						do {
							log.info("Enter amount: ");
							String answer = input.nextLine();
							while (answer.matches("^[a-zA-Z]*$")) {
								log.info("Invalid input. Please enter number only!");
								log.info("Enter amount: ");
								answer = input.nextLine();
							}

							depositAmount = Double.parseDouble(answer);
							if (depositAmount < 0) {
								log.info("The amount cannot be negative. Try again!");
							}
						} while (depositAmount < 0);

						UserCard card = cardBalanceInfoList.get(i);
						card.setBalance(card.getBalance() + depositAmount);
						custBo.updateCardBalance(card);
						log.info("Succeeded.");
						card.setTransactionTime(formatter.format(date));
						card.setTransactionMessage("Deposited $" + depositAmount + " to " + card.getCardNumber());
						custBo.transactionMessage(card);
					}
				}
				if (myCard.getCardNumber() != cardNumberForDeposit) {
					log.info("Invalid input. Try again!");
				}
			} while (myCard.getCardNumber() != cardNumberForDeposit);
		}
	}

	public void withdraw(UserAccount userAccount) throws BusinessException {
		viewAccountBalance(userAccount);
		List<UserCard> cardBalanceInfoList = custBo.cardBalanceInfoList(userAccount);

		UserCard myCard = new UserCard();
		int cardNumberForWithdraw = 0;
		double withdrawAmount, newBalance;

		if (cardBalanceInfoList.size() == 0) {
			log.info("You currently don't own any card!");
		} else if (cardBalanceInfoList.size() == 1) {
			do {
				log.info("Enter amount: ");
				withdrawAmount = Double.parseDouble(input.nextLine());
				if (withdrawAmount < 0) {
					log.info("The amount cannot be negative. Try again!");
				}
			} while (withdrawAmount < 0);

			if (cardBalanceInfoList.get(0).getCardNumber() == cardNumberForWithdraw) {
				UserCard card = cardBalanceInfoList.get(0);
				newBalance = card.getBalance() - withdrawAmount;
				if (newBalance < 0) {
					do {
						log.info("Invalid amount. Your current balance is " + card.getBalance());
						log.info("Enter amount: ");
						withdrawAmount = Double.parseDouble(input.nextLine());
					} while (newBalance < 0);
				} else {
					card.setBalance(newBalance);
					custBo.updateCardBalance(card);
					log.info("Succeeded.");
					card.setTransactionTime(formatter.format(date));
					card.setTransactionMessage("Withdrew $" + withdrawAmount + " from " + card.getCardNumber());
					custBo.transactionMessage(card);
				}
			}
		} else {
			do {
				log.info("You have more than one card, please enter the card number that you want to withdraw from: ");
				cardNumberForWithdraw = Integer.parseInt(input.nextLine());
				for (int i = 0; i < cardBalanceInfoList.size(); i++) {
					if (cardBalanceInfoList.get(i).getCardNumber() == cardNumberForWithdraw) {
						myCard = cardBalanceInfoList.get(i);
						UserCard card = cardBalanceInfoList.get(i);
						do {
							log.info("Enter amount: ");
							withdrawAmount = Double.parseDouble(input.nextLine());
							newBalance = card.getBalance() - withdrawAmount;
							if (withdrawAmount < 0) {
								log.info("The amount cannot be negative. Try again!");
							}
							if (newBalance < 0) {
								log.info("Invalid amount. Your current balance is " + card.getBalance());
							}
						} while (withdrawAmount < 0 || newBalance < 0);

						card.setBalance(newBalance);
						custBo.updateCardBalance(card);
						log.info("Succeeded.");
						card.setTransactionTime(formatter.format(date));
						card.setTransactionMessage("Withdrew $" + withdrawAmount + " from " + card.getCardNumber());
						custBo.transactionMessage(card);
					}
				}
				if (myCard.getCardNumber() != cardNumberForWithdraw) {
					log.info("Invalid input. Try again!");
				}
			} while (myCard.getCardNumber() != cardNumberForWithdraw);
		}
	}

	public void transfer(UserAccount userAccount) throws BusinessException {
		List<UserCard> myCardList = custBo.cardBalanceInfoList(userAccount);
		List<UserAccount> systemCardList = empBo.accountInfoList();
		double transferAmount, myBalance, myNewBalance;
		int cardNumberTransferTo;

		if (myCardList.size() == 0) {
			log.info("You currently don't own any card for transferring!");
		} else if (myCardList.size() == 1) {

			log.info("Enter the card number that you want to transfer to: ");
			cardNumberTransferTo = Integer.parseInt(input.nextLine());
			myBalance = myCardList.get(0).getBalance();

			for (UserAccount systemCard : systemCardList) {
				if (systemCard.getCard().getCardNumber() == cardNumberTransferTo) {
					do {
						log.info("Enter amount: ");
						transferAmount = Double.parseDouble(input.nextLine());
						myNewBalance = myBalance - transferAmount;
						if (transferAmount < 0) {
							log.info("The amount cannot be negative. Try again!");
						}
						if (myNewBalance < 0) {
							log.info("Invalid amount. Your current balance is " + myBalance);
						}
					} while (transferAmount < 0 || myNewBalance < 0);

					UserCard card = systemCard.getCard();
					card.setBalance(transferAmount);
					card.setReceiver(card.getUsername());
					card.setSender(myCardList.get(0).getUsername());
					custBo.transferMoney(card);

					UserCard myUpdatedCard = myCardList.get(0);
					myUpdatedCard.setBalance(myNewBalance);
					custBo.updateCardBalance(myUpdatedCard);

					log.info("Succeeded.");
					myUpdatedCard.setTransactionTime(formatter.format(date));
					myUpdatedCard
							.setTransactionMessage("Transferred $" + transferAmount + " to " + card.getCardNumber());
					custBo.transactionMessage(myUpdatedCard);
				}
			}
		} else {
			log.info("Enter the card number that you want to transfer to: ");
			cardNumberTransferTo = Integer.parseInt(input.nextLine());

			log.info("Your cards details: ");
			viewAccountBalance(userAccount);
			log.info("Enter the card number that you want to withdraw from: ");
			int myInputCardNumber = Integer.parseInt(input.nextLine());

			for (UserAccount systemCard : systemCardList) {
				if (systemCard.getCard().getCardNumber() == cardNumberTransferTo) {
					for (UserCard myCard : myCardList) {
						if (myCard.getCardNumber() == myInputCardNumber) {
							myBalance = myCard.getBalance();
							do {
								log.info("Enter amount: ");
								transferAmount = Double.parseDouble(input.nextLine());
								myNewBalance = myBalance - transferAmount;
								if (transferAmount < 0) {
									log.info("The amount cannot be negative. Try again!");
								}
								if (myNewBalance < 0) {
									log.info("Invalid amount. Your current balance is " + myBalance);
								}
							} while (transferAmount < 0 || myNewBalance < 0);

							UserCard card = systemCard.getCard();
							card.setBalance(transferAmount);
							card.setReceiver(card.getUsername());
							card.setSender(myCard.getUsername());
							custBo.transferMoney(card);

							myCard.setBalance(myNewBalance);
							custBo.updateCardBalance(myCard);

							log.info("Succeeded.");
							myCard.setTransactionTime(formatter.format(date));
							myCard.setTransactionMessage(
									"Transferred $" + transferAmount + " to " + card.getCardNumber());
							custBo.transactionMessage(myCard);
						}
					}
				}
			}
		}
	}

	public void applyNewCard(UserAccount userAccount, UserCard card) throws BusinessException {
		log.info("Enter 1: Debit Card");
		log.info("Enter 2: Credit Card");
		log.info("Enter any key to exit!");
		String option = input.nextLine();
		switch (option) {
		case "1":
			card.setCardType("Debit Card");
			break;
		case "2":
			card.setCardType("Credit Card");
			break;
		default:
			logout();
		}
		
		log.info("Enter your customized card number: ");
		card.setCardNumber(Integer.parseInt(input.nextLine()));
		card.setUsername(userAccount.getUsername());

		double startingBalance;
		do {
			log.info("Enter amount as starting balance: ");
			startingBalance = Double.parseDouble(input.nextLine());
			if (startingBalance < 0) {
				log.info("The amount cannot be negative. Try again!");
			}
		} while (startingBalance < 0);
		card.setBalance(startingBalance);

		card = new UserCard(card.getCardNumber(), card.getCardType(), card.getBalance(), card.getUsername());
		custBo.applyNewCard(card);
		log.info("Congratulation, your new bank card has been created. Currently pending for approval!");
	}

	public void moneyPendingInfo(UserAccount userAccount, UserCard userCard) throws BusinessException {
		List<UserCard> reviewMoneyPendingList = custBo.reviewMoneyPendingList();
		List<UserCard> currentCardList = custBo.cardBalanceInfoList(userAccount);

		if (reviewMoneyPendingList.size() == 0 || currentCardList.size() == 0) {
			log.info("You either don't own any card or your pending list is empty!");
		} else {

			log.info("Enter your card number: ");
			int cardNumberForReview = Integer.parseInt(input.nextLine());
			double currentBalance = 0;

			for (UserCard card : reviewMoneyPendingList) {
				if (userAccount.getUsername().equals(card.getReceiver())
						&& cardNumberForReview == card.getCardNumber()) {
					log.info("Card Number: " + card.getCardNumber() + "\tAmount: " + card.getBalance() + "\tSender: "
							+ card.getSender());
				}
			}

			for (UserCard card : currentCardList) {
				if (userAccount.getUsername().equals(card.getUsername())
						&& cardNumberForReview == card.getCardNumber()) {
					currentBalance = card.getBalance();
				}
			}

			double reviewBalance = reviewMoneyPendingList.get(0).getBalance();
			if (reviewMoneyPendingList.size() > 1) {
				log.info("Enter the amount regarding to the card number you want to make decision on: ");
				reviewBalance = Double.parseDouble(input.nextLine());
			}
			log.info("Enter 1: Approve");
			log.info("Enter 2: Deny");
			log.info("Enter any key to exit!");
			String option = input.nextLine();
			switch (option) {
			case "1":
				for (UserCard card : reviewMoneyPendingList) {
					if (card.getBalance() == reviewBalance) {
						card.setBalance(reviewBalance + currentBalance);

						card = new UserCard(card.getCardNumber(), card.getCardType(), card.getBalance(),
								card.getUsername());
						custBo.updateCardBalance(card);

						card = new UserCard(card.getCardNumber(), card.getCardType(), reviewBalance,
								card.getUsername());
						custBo.removeAmountPending(card);
						log.info("Amount Approved.");
						card.setUsername(userAccount.getUsername());
						card.setTransactionTime(formatter.format(date));
						card.setTransactionMessage("Approved $" + reviewBalance + " from " + card.getSender());
						custBo.transactionMessage(card);
					}
				}
				break;
			case "2":
				for (UserCard card : reviewMoneyPendingList) {
					if (card.getBalance() == reviewBalance) {
						card = new UserCard(card.getCardNumber(), card.getCardType(), card.getBalance(),
								card.getUsername());
						custBo.removeAmountPending(card);
						log.info("Amount Denied.");
						card.setUsername(userAccount.getUsername());
						card.setTransactionTime(formatter.format(date));
						card.setTransactionMessage("Denied $" + reviewBalance + " from " + card.getSender());
						custBo.transactionMessage(card);
					}
				}
				break;
			default:
				logout();
			}
		}
	}

	public void logout() {
		log.info("Thank you for banking with us. See you again!");
		System.exit(0);
	}

}

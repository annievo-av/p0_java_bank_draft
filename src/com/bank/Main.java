package com.bank;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.dao.CustDao;
import com.dao.CustDaoImpl;
import com.dao.EmpDao;
import com.dao.EmpDaoImpl;
import com.dao.UserAccountDao;
import com.dao.UserAccountDaoImpl;
import com.to.UserAccount;
import com.to.UserCard;

public class Main {

	public static void main(String[] args) {
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		UserAccount acct = new UserAccount();
		UserCard card = new UserCard();
		UserAccountDao acctDao = new UserAccountDaoImpl();
		EmpDao empDao = new EmpDaoImpl();
		CustDao custDao = new CustDaoImpl();

		String option;
		do {
			System.out.println("##### Welcome to Java Bank #######");
			System.out.println("----------------------------------");
			System.out.println("Enter 1: Member Login");
			System.out.println("Enter 2: Sign Up");
			System.out.println("Enter any key to exit.");
			option = input.nextLine();

			switch (option) {
			case "1":
				System.out.println("Enter username: ");
				String username = input.nextLine();
				System.out.println("Enter password: ");
				String password = input.nextLine();

				try {
					List<UserAccount> listUsers = acctDao.login();
					for (UserAccount u : listUsers) {
						if (username.equals(u.getUsername()) && password.equals(u.getPassword())) {
							System.out.println("Successfully logged in!");

							if (u.getUsertype() == null) {
								System.out.println("Please enter your task number with below criteria: ");
								System.out.println("1: View Account Balance");
								System.out.println("2: Deposit");
								System.out.println("3: Withraw");
								System.out.println("4: Transfer Money");
								System.out.println("5: Apply for New Card");
								System.out.println("6: Pending Money");
								System.out.println("Enter any key to logout.");
								option = input.nextLine();
								switch (option) {
								case "1":
									List<UserCard> cardBalance = custDao.cardBalanceInfoList();
									for (UserCard listCardInfo : cardBalance) {
										if (listCardInfo.getUsername().equals(u.getUsername())) {
											System.out.println("Card Number: " + listCardInfo.getCardNumber()
													+ "\tBalance: " + listCardInfo.getBalance());
										}
									}
									break;

								case "2":
									List<UserCard> cardForDeposit = custDao.cardBalanceInfoList();
									System.out.println("Your card(s) details:");
									for (UserCard depositList : cardForDeposit) {
										if (depositList.getUsername().equals(u.getUsername())) {
											System.out.println(depositList);
										}
									}
									System.out.println("Enter card number that you want to deposit: ");
									int cardNumberForDeposit = input.nextInt();
									// check for correction
									System.out.println("Enter amount: ");
									double depositAmount = input.nextDouble();
									if (depositAmount < 0) {
										System.out.println("Deposit amt cannot be negative."); // do while to re-enter
																								// amt
									} else {
										for (UserCard dlist : cardForDeposit) {
											if (dlist.getCardNumber() == cardNumberForDeposit) {
												dlist.setBalance(dlist.getBalance() + depositAmount);
												custDao.updateCardBalance(dlist);
												System.out.println("Succeeded.");
											}
										}
									}

									break;

								case "3":

									List<UserCard> cardForWithdraw = custDao.cardBalanceInfoList();
									System.out.println("Your card(s) details:");
									for (UserCard withdrawList : cardForWithdraw) {
										if (withdrawList.getUsername().equals(u.getUsername())) {
											System.out.println(withdrawList);
										}
									}
									System.out.println("Enter card number that you want to withdraw: ");
									int cardNumberForWithdraw = input.nextInt();
									// check for correction
									System.out.println("Enter amount: ");
									double withdrawAmount = input.nextDouble();
									if (withdrawAmount < 0) {
										System.out.println("Amt cannot be negative."); // do while to re-enter amt
									} else {
										for (UserCard wlist : cardForWithdraw) {
											if (wlist.getCardNumber() == cardNumberForWithdraw) {
												double newBalance = wlist.getBalance() - withdrawAmount;

												if (newBalance < 0) {
													System.out.println("Invalid amount. Your current balance is "
															+ wlist.getBalance());
												} else {
													wlist.setBalance(newBalance);
													custDao.updateCardBalance(wlist);
													System.out.println("Succeeded.");
												}
											}
										}
									}

									break;

								case "4":
									List<UserCard> transferList = custDao.cardBalanceInfoList();
									System.out.println("Enter card number that you want to transfer to: ");
									int cardForTransfer = input.nextInt();

									System.out.println("Enter amount for transfer: ");
									double transferamt = input.nextDouble();

									if (transferamt < 0) {
										System.out.println("Amt cannot be negative."); // do while to re-enter amt
									} else {
										for (UserCard transfer : transferList) {
											if (cardForTransfer == transfer.getCardNumber()) {
												transfer.setBalance(transferamt);
												transfer.setReceiver(transfer.getUsername());
												transfer.setUsername(u.getUsername());
												custDao.transferMoney(transfer);
												System.out.println("Succeeded.");
											}
										}
									}
									break;

								case "5":
									System.out.println("Enter 1: Apply for Debit Card");
									System.out.println("Enter 2: Apply for Credit Card");
									option = input.nextLine();

									switch (option) {
									case "1":
										card.setType("Debit Card");
										System.out.println("Enter your fav card number: ");
										card.setCardNumber(input.nextInt());
										System.out.println("Enter amount: ");
										card.setBalance(input.nextDouble());
										card.setUsername(u.getUsername());
//										System.out.println("Enter username of the card that you are applying for");
//										card.setUsername(input.nextLine());
										try {
											card = new UserCard(card.getCardNumber(), card.getType(), card.getBalance(),
													card.getUsername());
											;
											custDao.registerCard(card);
											System.out.println(
													"Congratulation, your new bank card has been created. Currently pending for approval!");
											// re-launch the welcome() after modifying
										} catch (Exception e) {
											System.out.println(e.getMessage());
										}

										break;

									case "2":
										card.setType("Credit Card");
										System.out.println("Enter your fav card number: ");
										card.setCardNumber(input.nextInt());
										System.out.println("Enter amount: ");
										card.setBalance(input.nextDouble());
//										System.out.println("Enter username of the card that you are applying for");
										card.setUsername(acct.getUsername());
										try {
											card = new UserCard(card.getCardNumber(), card.getType(), card.getBalance(),
													card.getUsername());
											;
											custDao.registerCard(card);
											System.out.println(
													"Congratulation, your new bank card has been created. Currently pending for approval!");
											// re-launch the welcome() after modifying
										} catch (Exception e) {
											System.out.println(e.getMessage());
										}

										break;

									default:
										System.out.println("Wrong input");
										break;
									} // end ccc vs. db card case

									break;

								case "6":
									List<UserCard> amountPendingList = custDao.reviewingMoneyList();
									List<UserCard> oldCardList = custDao.cardBalanceInfoList();
									System.out.println(
											"Enter card number to check if you have anything pending for approval: ");
									int cardForApproval = input.nextInt();
									double oldBalance = 0;
									for (UserCard alist : amountPendingList) {
										if (u.getUsername().equals(alist.getReceiver())
												&& cardForApproval == alist.getCardNumber()) {
											System.out.println("Card Number: " + alist.getCardNumber() + "\tAmount: "
													+ alist.getBalance() + "\tSender: " + alist.getSender());
											// username aka sender
										}
									}

									for (UserCard o : oldCardList) {
										if (u.getUsername().equals(o.getUsername())
												&& cardForApproval == o.getCardNumber()) {
											oldBalance = o.getBalance();
										}
									}

									System.out.println("Enter the amount above to make transaction: ");
									double reviewingAmount = input.nextDouble();

									// Error for entering the wrong stuffs
									System.out.println("Enter 1: Approve");
									System.out.println("Enter 2: Deny");
									option = input.next();

									switch (option) {
									case "1":
										for (UserCard ca : amountPendingList) {
											if (ca.getBalance() == reviewingAmount) {
												ca.setBalance(reviewingAmount + oldBalance);

												card = new UserCard(ca.getCardNumber(), ca.getType(), ca.getBalance(),
														ca.getUsername());
												custDao.updateCardBalance(card);

												card = new UserCard(ca.getCardNumber(), ca.getType(), reviewingAmount,
														ca.getUsername());
												custDao.removeAmountPending(card);
												System.out.println("Amount Approved.");
											}
										}
										break;
									case "2":
										for (UserCard amt : amountPendingList) {
											if (amt.getBalance() == reviewingAmount) {
												card = new UserCard(amt.getCardNumber(), amt.getType(),
														amt.getBalance(), amt.getUsername());
												custDao.removeAmountPending(card);
												System.out.println("Amount Denied.");
											}
										}
										break;

									default:
										break;
									}

									break;

								default:
//									acctDao.logout();
									break;
								} // end customers activities after loging in

							} // end users activities after loging in

							if (u.getUsertype().equals("employee")) {
								System.out.println("Please enter your task number with below criteria: ");
								System.out.println("1: View Customer's Account");
								System.out.println("2: View Pending User Account List");
								System.out.println("3: View Pending Bank Account List");
								System.out.println("4: View All Transaction");
								System.out.println("Enter any key to logout.");
								option = input.nextLine();
								switch (option) {
								case "1":
									List<UserAccount> list = empDao.displayAcctInfoByUsername();
									if (list.size() == 0) {
										System.out.println("Account list is empty!");
									} else {
										int i = 1;
										for (UserAccount a : list) {
											System.out.println(i + ". username: " + a.getUsername());
											i++;
										}
										System.out.println("Enter account's username for details: ");
										String infoByUsername = input.nextLine();
										// Error for entering the wrong stuffs
										for (UserAccount a : list) {
											if (infoByUsername.equals(a.getUsername())) {
												System.out.println(a);
											}
										}
									}
									break;
								case "2":
									List<UserAccount> pendingList = empDao.acctApprovalList();
									if (pendingList.size() == 0) {
										System.out.println("Approval list is empty!");
									} else {
										int i = 1;
										for (UserAccount p : pendingList) {
											System.out.println(i + ". username: " + p.getUsername() + ", password: "
													+ p.getPassword());
											i++;
										}
										System.out.println("Enter username for details: ");
										String approveByUsername = input.nextLine();
										// Error for entering the wrong stuffs
										System.out.println("Enter 1: Approve");
										System.out.println("Enter 2: Deny");
										option = input.nextLine();
										switch (option) {
										case "1":
											for (UserAccount p : pendingList) {
												if (approveByUsername.equals(p.getUsername())) {
													acct = new UserAccount(p.getUsername(), p.getPassword(), null);
													empDao.approveUserAccount(acct);
													empDao.removeAccountPending(acct);
													System.out.println("Account Approved.");
												}
											}
											break;
										case "2":
											for (UserAccount p : pendingList) {
												if (approveByUsername.equals(p.getUsername())) {
													acct = new UserAccount(p.getUsername(), p.getPassword(), null);
													empDao.removeAccountPending(acct);
													System.out.println("Account Denied.");
												}
											}
											break;

										default:
											break;
										}
									}
//									List Empty, provide option for go back
//									System.out.println("Invalid input. Please try again!");
									break; // end case 2 of creating acct

								case "3": // case 3 of if employee
									List<UserCard> cardPendingList = empDao.newCardApprovalList();
									if (cardPendingList.size() == 0) {
										System.out.println("Approval list is empty!");
									} else {
										int i = 1;
										for (UserCard c : cardPendingList) {
											System.out.println(i + ". " + c);
											i++;
										}
										System.out.println("Enter card number to make decision: ");
										int approveCardByCardNumber = input.nextInt();
										// Error for entering the wrong stuffs
										System.out.println("Enter 1: Approve");
										System.out.println("Enter 2: Deny");
										option = input.next();
										switch (option) {
										case "1":
											for (UserCard uc : cardPendingList) {
												if (approveCardByCardNumber == uc.getCardNumber()) {
													card = new UserCard(uc.getCardNumber(), uc.getType(),
															uc.getBalance(), uc.getUsername());
													empDao.approveUserCard(card);
													empDao.removeCardPending(card);
													System.out.println("Account Approved.");
												}
											}
											break;
										case "2":
											for (UserCard uc : cardPendingList) {
												if (approveCardByCardNumber == uc.getCardNumber()) {
													card = new UserCard(uc.getCardNumber(), uc.getType(),
															uc.getBalance(), uc.getUsername());
													empDao.removeCardPending(card);
													System.out.println("Account Denied.");
												}
											}
											break;

										default:
											break;
										}
									}
//									List Empty, provide option for go back
//									System.out.println("Invalid input. Please try again!");
									break; // end case 2 of creating acct

								case "4": // case 3 of if employee
									System.out.println("Enter account's username: ");
									String transByCardNumber = input.nextLine();
//									empDao.printAcctInfoByUsername();
									break;

								default: // default of if employee
//									acctDao.logout();
									break;
								} // SWITCH of emp
//								return;
							}

						} else if (listUsers.size() == 0) { // failed login
							System.out.println("This account does not exist!");
							// re-launch the welcome() after modifying
						}
					} // end case 1: sign in

				} catch (Exception e) { // null pointer exc
					System.out.println(e.getMessage());
				}
				break;

			case "2":
				System.out.println("Enter username: ");
				acct.setUsername(input.nextLine());
				System.out.println("Enter password: ");
				acct.setPassword(input.nextLine());
				try {
					acct = new UserAccount(acct.getUsername(), acct.getPassword(), null);
					acctDao.registerUser(acct);
					System.out
							.println("Congratulation, your account has been created. Currently pending for approval!");
					// re-launch the welcome() after modifying
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				break;
			default:
				acctDao.logout();
				break;
			}
		} while (option == "2" || option == "1");
	}
}

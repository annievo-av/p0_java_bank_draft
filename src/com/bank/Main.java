package com.bank;

import java.util.List;
import java.util.Scanner;

import com.dao.EmpDao;
import com.dao.EmpDaoImpl;
import com.dao.UserAccountDao;
import com.dao.UserAccountDaoImpl;
import com.to.UserAccount;

public class Main {

	public static void main(String[] args) {
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		UserAccount acct = new UserAccount();
		UserAccountDao acctDao = new UserAccountDaoImpl();
		EmpDao empDao = new EmpDaoImpl();

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

							if (u.getUsertype().equals("employee")) {
								System.out.println("Please enter your task number with below criteria: ");
								System.out.println("1: View Customer's Account");
								System.out.println("2: View Pending Account List");
								System.out.println("3: View All Transaction");
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
													empDao.approveUser(acct);
													empDao.removePending(acct);
													System.out.println("Account Approved.");
												}
											}
											break;
										case "2":
											for (UserAccount p : pendingList) {
												if (approveByUsername.equals(p.getUsername())) {
													acct = new UserAccount(p.getUsername(), p.getPassword(), null);
													empDao.removePending(acct);
													System.out.println("Account Denied.");
												}
											}
											break;
										}
									}
//									List Empty, provide option for go back
//									System.out.println("Invalid input. Please try again!");
									break;
								case "3":
									System.out.println("Enter account's username: ");
									String transByCardNumber = input.nextLine();
//									empDao.printAcctInfoByUsername();
									break;
								default:
//									acctDao.logout();
									break;
								}
							} else {
								System.out.println("Please enter your task number with below criteria: ");
								System.out.println("1: View Account Balance");
								System.out.println("2: Withraw");
								System.out.println("3: Deposit");
								System.out.println("4: Transfer");
								System.out.println("5: Apply for New Card");
								System.out.println("6: Pending Money");
								option = input.nextLine();

							}
						} else if (listUsers.size() == 0) {
							System.out.println("This account does not exist!");
							// re-launch the welcome() after modifying
						}
					}
				} catch (Exception e) {
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

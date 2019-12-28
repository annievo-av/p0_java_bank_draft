package com.bank;

import java.util.List;
import java.util.Scanner;

import com.dao.UserAccountDao;
import com.dao.UserAccountDaoImpl;
import com.to.UserAccount;

public class Main {

	public static void main(String[] args) {
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		UserAccount acct = new UserAccount();
		UserAccountDao acctDao = new UserAccountDaoImpl();

		String option;
		do {
			System.out.println("Welcome to Java Bank!");
			System.out.println("---------------------");
			System.out.println("Enter 1: Member Login");
			System.out.println("Enter 2: Sign Up");
			System.out.println("Enter any key to exit.");
			option = input.nextLine();

			switch (option) {
			case "1":
				System.out.println("Enter username: ");
				String username = input.next();
				System.out.println("Enter password: ");
				String password = input.next();

				try {
					List<UserAccount> listUsers = acctDao.verifyAcct();
					for (UserAccount u : listUsers) {
						if (username.equals(u.getUsername()) && password.equals(u.getPassword())) {
							System.out.println("Successfully logged in!");
							// list of things customers/employees can do
						} else if (listUsers.size() == 0){
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
				acct.setUsername(input.next());
				System.out.println("Enter password: ");
				acct.setPassword(input.next());
				try {
					acct = new UserAccount(acct.getUsername(), acct.getPassword());
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

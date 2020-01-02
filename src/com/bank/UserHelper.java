package com.bank;

import java.util.List;
import java.util.Scanner;

import com.bank.bo.UserBo;
import com.bank.bo.UserBoImpl;
import com.bank.exception.BusinessException;
import com.bank.to.UserAccount;

public class UserHelper {
	Scanner input = new Scanner(System.in);
	UserBo userBo = new UserBoImpl();
	CustHelper custHelper = new CustHelper();
	EmpHelper empHelper = new EmpHelper();

	public void userLoginEntryLogic(UserAccount userAccount) throws BusinessException {
		System.out.println("Enter username: ");
		String username = input.nextLine();
		System.out.println("Enter password: ");
		String password = input.nextLine();
		List<UserAccount> accountListForLogin = userBo.accountListForLogin();
		for (UserAccount account : accountListForLogin) {
			userAccount = account;
			if (account.getUsername().equals(username) && account.getPassword().equals(password)) {
				if (account.getUsertype() == null) {
					custHelper.customerEntryLogic(userAccount);
				} else if (account.getUsertype().equals("employee")) {
					empHelper.employeeEntryLogic();
				}
			}
		}
		if (!(userAccount.getUsername().equals(username) && userAccount.getPassword().equals(password))) {
			System.out.println("Your enter values are not matched with any account in our system. Please try again!");
			userLoginEntryLogic(userAccount);
		}
	}
	
	public void signupLogic(UserAccount userAccount) throws BusinessException {
		System.out.println("Enter username: ");
		userAccount.setUsername(input.nextLine());
		System.out.println("Enter password: ");
		userAccount.setPassword(input.nextLine());
		System.out.println("Your account details: ");
		System.out.println("username: " + userAccount.getUsername() + "\tpassword: " + userAccount.getPassword());
		System.out.println("Enter 1: Submit");
		System.out.println("Enter 2: Re-enter");
		System.out.println("Enter any key to exit!");
		String option = input.nextLine();
		switch (option) {
		case "1":
			userAccount = new UserAccount(userAccount.getUsername(), userAccount.getPassword(), null);
			userBo.signup(userAccount);
			System.out.println("Congratulation, your account has been created. Currently pending for approval!");
			System.out.println("Enter any key to exit!");
			option = input.nextLine();
			userBo.logout();
			break;
		case "2":
			signupLogic(userAccount);
			break;
		default:
			userBo.logout();
		}
	}

}

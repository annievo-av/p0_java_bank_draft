package com.bank.main;

import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.bank.bo.UserBo;
import com.bank.bo.UserBoImpl;
import com.bank.exception.BusinessException;
import com.bank.to.UserAccount;

public class UserHelper {

	final static Logger log = Logger.getLogger(UserHelper.class);

	Scanner input = new Scanner(System.in);
	UserBo userBo = new UserBoImpl();
	CustHelper custHelper = new CustHelper();
	EmpHelper empHelper = new EmpHelper();

	public void logout() {
		log.info("Thank you for banking with us. See you again!");
		System.exit(0);
	}

	public void userLoginEntryLogic(UserAccount userAccount) throws BusinessException {
		log.info("Enter username: ");
		String username = input.nextLine();
		log.info("Enter password: ");
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
			log.info("Your enter values are not matched with any account in our system. Please try again!");
			userLoginEntryLogic(userAccount);
		}
	}

	public void signupLogic(UserAccount userAccount) throws BusinessException {
		log.info("Enter username: ");
		userAccount.setUsername(input.nextLine());
		log.info("Enter password: ");
		userAccount.setPassword(input.nextLine());
		log.info("Your account details: ");
		log.info("username: " + userAccount.getUsername() + "\tpassword: " + userAccount.getPassword());
		log.info("Enter 1: Submit");
		log.info("Enter 2: Re-enter");
		log.info("Enter any key to exit!");
		String option = input.nextLine();
		switch (option) {
		case "1":
			userAccount = new UserAccount(userAccount.getUsername(), userAccount.getPassword(), null);
			userBo.signup(userAccount);
			log.info("Congratulation, your account has been created. Currently pending for approval!");
			log.info("Enter any key to exit!");
			option = input.nextLine();
			logout();
			break;
		case "2":
			signupLogic(userAccount);
			break;
		default:
			logout();
		}
	}

}

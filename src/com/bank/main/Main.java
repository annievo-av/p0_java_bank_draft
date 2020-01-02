package com.bank.main;

import java.util.Scanner;

import org.apache.log4j.Logger;

import com.bank.UserHelper;
import com.bank.exception.BusinessException;
import com.bank.to.UserAccount;

public class Main {

	final static Logger log = Logger.getLogger(Main.class);

	public static void main(String[] args) {
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		UserHelper userHelper = new UserHelper();
		UserAccount userAccount = new UserAccount();

		welcomeBanner();

		log.info("Enter 1: Member Login");
		log.info("Enter 2: Sign Up");
		log.info("Enter any key to exit!");
		String option = input.nextLine();
		switch (option) {
		case "1":
			try {
				userHelper.userLoginEntryLogic(userAccount);
			} catch (BusinessException e) {
				log.info(e.getMessage());
			}
			break;
		case "2":
			try {
				userHelper.signupLogic(userAccount);
			} catch (BusinessException e) {
				log.info(e.getMessage());
			}
			break;
		default:
			System.exit(0);
		}
	}

	public static void welcomeBanner() {
		log.info("#####################################################################################");
		log.info("#####################################################################################");
		log.info("###########-------------" + ConsoleColors.BLUE_BOLD
				+ "Welcome to Noncreative-Naming Java Bank" + ConsoleColors.RESET_BLACK + "-----------###########");
		log.info("#####################################################################################");
		log.info("#####################################################################################");
	}

	public class ConsoleColors {
		public static final String RESET_BLACK = "\033[0;30m";
		public static final String BLUE_BOLD = "\033[1;34m";
	}

}

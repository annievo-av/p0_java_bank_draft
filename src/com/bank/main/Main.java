package com.bank.main;

import java.util.Scanner;

import com.bank.UserHelper;
import com.bank.exception.BusinessException;
import com.bank.to.UserAccount;

public class Main {

	public static void main(String[] args) {
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		UserHelper userHelper = new UserHelper();
		UserAccount userAccount = new UserAccount();

		welcomeBanner();

		System.out.println("Enter 1: Member Login");
		System.out.println("Enter 2: Sign Up");
		System.out.println("Enter any key to exit!");
		String option = input.nextLine();
		switch (option) {
		case "1":
			try {
				userHelper.userLoginEntryLogic(userAccount);
			} catch (BusinessException e) {
				System.out.println(e.getMessage());
			}
			break;
		case "2":
			try {
				userHelper.signupLogic(userAccount);
			} catch (BusinessException e) {
				System.out.println(e.getMessage());
			}
			break;
		default:
			System.exit(0);
		}
	}

	public static void welcomeBanner() {
		System.out.println("#####################################################################################");
		System.out.println("#####################################################################################");
		System.out.println("###########-------------" + ConsoleColors.BLUE_BOLD
				+ "Welcome to Noncreative-Naming Java Bank" + ConsoleColors.RESET_BLACK + "-----------###########");
		System.out.println("#####################################################################################");
		System.out.println("#####################################################################################");
	}

	public class ConsoleColors {
		public static final String RESET_BLACK = "\033[0;30m";
		public static final String BLUE_BOLD = "\033[1;34m";
	}

}

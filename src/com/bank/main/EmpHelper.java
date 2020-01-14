package com.bank.main;

import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.bank.bo.EmpBo;
import com.bank.bo.EmpBoImpl;
import com.bank.bo.UserBo;
import com.bank.bo.UserBoImpl;
import com.bank.exception.BusinessException;
import com.bank.to.UserAccount;
import com.bank.to.UserCard;

public class EmpHelper {

	final static Logger log = Logger.getLogger(EmpHelper.class);

	Scanner input = new Scanner(System.in);
	UserBo userBo = new UserBoImpl();
	EmpBo empBo = new EmpBoImpl();
	String option;

	public void employeeEntryLogic() throws BusinessException {
		log.info("Enter task number according to the below criteria: ");
		log.info("1. View Customer Account");
		log.info("2. New pending user account");
		log.info("3. New pending customer card");
		log.info("4. Transaction Log");
		log.info("Enter any key to exit!");
		String option = input.nextLine();
		switch (option) {
		case "1":
			viewCustomerAccount();
			employeeEntryLogic();
			break;
		case "2":
			pendindAccount();
			employeeEntryLogic();
			break;
		case "3":
			pendindCard();
			employeeEntryLogic();
			break;
		case "4":
			viewTransactionLog();
			employeeEntryLogic();
			break;
		default:
			logout();
		}
	}

	public void viewCustomerAccount() throws BusinessException {
		List<UserAccount> accountInfoList = empBo.accountInfoList();
		if (accountInfoList.size() == 0) {
			log.info("This list is empty!");
		} else {
			int i = 1;
			for (UserAccount a : accountInfoList) {
				log.info(i + ". username: " + a.getUsername());
				i++;
			}
			log.info("Enter account's username for details: ");
			String infoByUsername = input.nextLine();
			for (UserAccount a : accountInfoList) {
				if (infoByUsername.equals(a.getUsername())) {
					log.info(a);
				}
			}
		}
	}

	public void pendindAccount() throws BusinessException {
		UserAccount userAccount = null;
		List<UserAccount> newAccountPendingList = empBo.newAccountPendingList();
		if (newAccountPendingList.size() == 0) {
			log.info("Approval list is empty!");
		} else {
			int i = 1;
			for (UserAccount account : newAccountPendingList) {
				log.info(i + ". username: " + account.getUsername() + ", password: " + account.getPassword());
				i++;
			}
			log.info("Enter username for details: ");
			String pendingUsername = input.nextLine();
			log.info("Enter 1: Approve");
			log.info("Enter 2: Deny");
			log.info("Enter any key to exit!");
			String option = input.nextLine();
			switch (option) {
			case "1":
				for (UserAccount account : newAccountPendingList) {
					if (pendingUsername.equals(account.getUsername())) {
						userAccount = new UserAccount(account.getUsername(), account.getPassword(), null);
						empBo.approveNewAccount(userAccount);
						empBo.removeAccountPending(userAccount);
						log.info("Account Approved.");
					}
				}
				break;
			case "2":
				for (UserAccount account : newAccountPendingList) {
					if (pendingUsername.equals(account.getUsername())) {
						userAccount = new UserAccount(account.getUsername(), account.getPassword(), null);
						empBo.removeAccountPending(userAccount);
						log.info("Account Denied.");
					}
				}
				break;
			default:
				logout();
			}
		}
	}

	public void pendindCard() throws BusinessException {
		UserCard userCard = null;
		List<UserCard> newCardPendingList = empBo.newCardPendingList();
		if (newCardPendingList.size() == 0) {
			log.info("Approval list is empty!");
		} else {
			int i = 1;
			for (UserCard card : newCardPendingList) {
				log.info(i + ". " + card);
				i++;
			}
			log.info("Enter card number for details: ");
			int pendingCard = input.nextInt();
			log.info("Enter 1: Approve");
			log.info("Enter 2: Deny");
			log.info("Enter any key to exit!");
			String option = input.next();
			switch (option) {
			case "1":
				for (UserCard card : newCardPendingList) {
					if (pendingCard == card.getCardNumber()) {
						userCard = new UserCard(card.getCardNumber(), card.getCardType(), card.getBalance(),
								card.getUsername());
						empBo.approveNewCard(userCard);
						empBo.removeCardPending(userCard);
						log.info("Account Approved.");
					}
				}
				break;
			case "2":
				for (UserCard card : newCardPendingList) {
					if (pendingCard == card.getCardNumber()) {
						userCard = new UserCard(card.getCardNumber(), card.getCardType(), card.getBalance(),
								card.getUsername());
						empBo.removeCardPending(userCard);
						log.info("Account Denied.");
					}
				}
				break;
			default:
				logout();
			}
		}
	}

	public void viewTransactionLog() throws BusinessException {
		List<UserCard> viewTransactionLog = empBo.transactionLogList();
		if (viewTransactionLog.size() == 0) {
			log.info("This list is empty!");
		} else {
			int i = 1;
			for (UserCard card : viewTransactionLog) {
				log.info(i + ". " + card.getUsername() + "\t" + card.getTransactionMessage() + "\t"
						+ card.getTransactionTime());
				i++;
			}
		}
	}

	public void logout() {
		log.info("Thank you for banking with us. See you again!");
		System.exit(0);
	}

}

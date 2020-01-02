package com.bank.bo;

import java.util.List;
import java.util.Scanner;

import com.bank.dao.EmpDao;
import com.bank.dao.EmpDaoImpl;
import com.bank.exception.BusinessException;
import com.bank.to.UserAccount;
import com.bank.to.UserCard;

public class EmpBoImpl implements EmpBo {

	Scanner input = new Scanner(System.in);
	private EmpDao empDao;

	@Override
	public List<UserAccount> newAccountPendingList() throws BusinessException {
		List<UserAccount> userAccount = null;
		userAccount = getEmpDao().newAccountPendingList();
		return userAccount;
	}

	@Override
	public List<UserCard> newCardPendingList() throws BusinessException {
		List<UserCard> userCard = null;
		userCard = getEmpDao().newCardPendingList();
		return userCard;
	}

	@Override
	public void approveNewAccount(UserAccount userAccount) throws BusinessException {
		getEmpDao().approveNewAccount(userAccount);
	}

	@Override
	public void removeAccountPending(UserAccount userAccount) throws BusinessException {
		getEmpDao().removeAccountPending(userAccount);
	}

	@Override
	public void approveNewCard(UserCard card) throws BusinessException {
		getEmpDao().approveNewCard(card);
	}

	@Override
	public void removeCardPending(UserCard card) throws BusinessException {
		getEmpDao().removeCardPending(card);
	}

	@Override
	public List<UserAccount> accountInfoList() throws BusinessException {
		List<UserAccount> userAccount = null;
		userAccount = getEmpDao().accountInfoList();
		return userAccount;
	}

	@Override
	public List<UserCard> transactionLogList() throws BusinessException {
		List<UserCard> userCard = null;
		userCard = getEmpDao().transactionLogList();
		return userCard;
	}

	public EmpDao getEmpDao() {
		if (empDao == null) {
			empDao = new EmpDaoImpl();
		}
		return empDao;
	}
	
	public void logout() {
		System.out.println("Thank you. See you again!");
		System.exit(0);
	}

	@Override
	public void viewCustomerAccount() throws BusinessException {
		List<UserAccount> accountInfoList = accountInfoList();
		if (accountInfoList.size() == 0) {
			System.out.println("This list is empty!");
		} else {
			int i = 1;
			for (UserAccount a : accountInfoList) {
				System.out.println(i + ". username: " + a.getUsername());
				i++;
			}
			System.out.println("Enter account's username for details: ");
			String infoByUsername = input.nextLine();
			for (UserAccount a : accountInfoList) {
				if (infoByUsername.equals(a.getUsername())) {
					System.out.println(a);
				}
			}
		}
	}

	@Override
	public void pendindAccount() throws BusinessException {
		UserAccount userAccount = null;
		List<UserAccount> newAccountPendingList = newAccountPendingList();
		if (newAccountPendingList.size() == 0) {
			System.out.println("Approval list is empty!");
		} else {
			int i = 1;
			for (UserAccount account : newAccountPendingList) {
				System.out.println(i + ". username: " + account.getUsername() + ", password: " + account.getPassword());
				i++;
			}
			System.out.println("Enter username for details: ");
			String pendingUsername = input.nextLine();
			System.out.println("Enter 1: Approve");
			System.out.println("Enter 2: Deny");
			System.out.println("Enter any key to exit!");
			String option = input.nextLine();
			switch (option) {
			case "1":
				for (UserAccount account : newAccountPendingList) {
					if (pendingUsername.equals(account.getUsername())) {
						userAccount = new UserAccount(account.getUsername(), account.getPassword(), null);
						approveNewAccount(userAccount);
						removeAccountPending(userAccount);
						System.out.println("Account Approved.");
					}
				}
				break;
			case "2":
				for (UserAccount account : newAccountPendingList) {
					if (pendingUsername.equals(account.getUsername())) {
						userAccount = new UserAccount(account.getUsername(), account.getPassword(), null);
						removeAccountPending(userAccount);
						System.out.println("Account Denied.");
					}
				}
				break;
			default:
				System.exit(0);
			}
		}
	}

	@Override
	public void pendindCard() throws BusinessException {
		UserCard userCard = null;
		List<UserCard> newCardPendingList = newCardPendingList();
		if (newCardPendingList.size() == 0) {
			System.out.println("Approval list is empty!");
		} else {
			int i = 1;
			for (UserCard card : newCardPendingList) {
				System.out.println(i + ". " + card);
				i++;
			}
			System.out.println("Enter card number for details: ");
			int pendingCard = input.nextInt();
			System.out.println("Enter 1: Approve");
			System.out.println("Enter 2: Deny");
			System.out.println("Enter any key to exit!");
			String option = input.next();
			switch (option) {
			case "1":
				for (UserCard card : newCardPendingList) {
					if (pendingCard == card.getCardNumber()) {
						userCard = new UserCard(card.getCardNumber(), card.getCardType(), card.getBalance(),
								card.getUsername());
						approveNewCard(userCard);
						removeCardPending(userCard);
						System.out.println("Account Approved.");
					}
				}
				break;
			case "2":
				for (UserCard card : newCardPendingList) {
					if (pendingCard == card.getCardNumber()) {
						userCard = new UserCard(card.getCardNumber(), card.getCardType(), card.getBalance(),
								card.getUsername());
						removeCardPending(userCard);
						System.out.println("Account Denied.");
					}
				}
				break;
			default:
				logout();
			}
		}
	}

	@Override
	public void viewTransactionLog() throws BusinessException {
		List<UserCard> viewTransactionLog = transactionLogList();
		if (viewTransactionLog.size() == 0) {
			System.out.println("This list is empty!");
		} else {
			int i = 1;
			for (UserCard card : viewTransactionLog) {
				System.out.println(i + ". " + card.getUsername() + "\t" + card.getTransactionMessage() + "\t"
						+ card.getTransactionTime());
				i++;
			}
		}
	}

}

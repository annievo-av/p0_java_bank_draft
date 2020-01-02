package com.bank.bo;

import java.util.List;
import java.util.Scanner;

import com.bank.dao.UserDao;
import com.bank.dao.UserDaoImpl;
import com.bank.exception.BusinessException;
import com.bank.to.UserAccount;

public class UserBoImpl implements UserBo {

	Scanner input = new Scanner(System.in);
	private UserDao userDao;

	@Override
	public void signup(UserAccount userAccount) throws BusinessException {
		getUserDao().signup(userAccount);
	}
	
	@Override
	public void logout() {
		System.out.println("Thank you for banking with us. See you again!");
		System.exit(0);
	}
	
	@Override
	public List<UserAccount> accountListForLogin() throws BusinessException {
		List<UserAccount> userAccount = null;
		userAccount = getUserDao().accountListForLogin();
		return userAccount;
	}

	public UserDao getUserDao() {
		if (userDao == null) {
			userDao = new UserDaoImpl();
		}
		return userDao;
	}
	
}

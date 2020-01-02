package com.bank.bo;

import java.util.List;

import com.bank.dao.UserDao;
import com.bank.dao.UserDaoImpl;
import com.bank.exception.BusinessException;
import com.bank.to.UserAccount;

public class UserBoImpl implements UserBo {

	private UserDao userDao;

	@Override
	public void signup(UserAccount userAccount) throws BusinessException {
		getUserDao().signup(userAccount);
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

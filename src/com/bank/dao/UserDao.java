package com.bank.dao;

import java.util.List;

import com.bank.exception.BusinessException;
import com.bank.to.UserAccount;

public interface UserDao {

	public void signup(UserAccount userAccount) throws BusinessException;

	public List<UserAccount> accountListForLogin() throws BusinessException;
}

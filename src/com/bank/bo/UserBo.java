package com.bank.bo;

import java.util.List;

import com.bank.exception.BusinessException;
import com.bank.to.UserAccount;

public interface UserBo {

	public void logout();
	
	public void signup(UserAccount userAccount) throws BusinessException;

	public List<UserAccount> accountListForLogin() throws BusinessException;
}

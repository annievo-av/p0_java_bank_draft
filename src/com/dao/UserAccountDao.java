package com.dao;

import java.util.List;

import com.to.UserAccount;

public interface UserAccountDao {
	
	public void logout();
	public void registerUser(UserAccount userAccount) throws Exception;
	public List<UserAccount> verifyAcct() throws Exception;
}

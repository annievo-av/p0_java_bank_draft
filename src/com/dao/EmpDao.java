package com.dao;

import java.util.List;

import com.to.Transaction;
import com.to.UserAccount;

public interface EmpDao {

	public void approveUser(UserAccount userAccount) throws Exception;

	public void removePending(UserAccount userAccount) throws Exception;

	public List<UserAccount> displayAcctInfoByUsername() throws Exception;

	public List<UserAccount> acctApprovalList() throws Exception;

	public List<Transaction> logOfTransactionByCardNumber() throws Exception;
}

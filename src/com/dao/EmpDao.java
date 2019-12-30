package com.dao;

import java.util.List;

import com.to.Transaction;
import com.to.UserAccount;
import com.to.UserCard;

public interface EmpDao {

	public void approveUserAccount(UserAccount userAccount) throws Exception;

	public void removeAccountPending(UserAccount userAccount) throws Exception;
	
	public void approveUserCard(UserCard card) throws Exception;

	public void removeCardPending(UserCard card) throws Exception;

	public List<UserAccount> displayAcctInfoByUsername() throws Exception;

	public List<UserAccount> acctApprovalList() throws Exception;
	
	public List<UserCard> newCardApprovalList() throws Exception;

	public List<Transaction> logOfTransactionByCardNumber() throws Exception; // left over
}

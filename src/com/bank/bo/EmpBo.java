package com.bank.bo;

import java.util.List;

import com.bank.exception.BusinessException;
import com.bank.to.UserAccount;
import com.bank.to.UserCard;

public interface EmpBo {

	public List<UserAccount> newAccountPendingList() throws BusinessException;
	
	public List<UserCard> newCardPendingList() throws BusinessException;
	
	public void approveNewAccount(UserAccount userAccount) throws BusinessException;

	public void removeAccountPending(UserAccount userAccount) throws BusinessException;
	
	public void approveNewCard(UserCard card) throws BusinessException;

	public void removeCardPending(UserCard card) throws BusinessException;

	public List<UserCard> transactionLogList() throws BusinessException;
	
	public List<UserAccount> accountInfoList() throws BusinessException;
	
	public void viewCustomerAccount() throws BusinessException;
	
	public void pendindAccount() throws BusinessException;
	
	public void pendindCard() throws BusinessException;
	
	public void viewTransactionLog() throws BusinessException;
}

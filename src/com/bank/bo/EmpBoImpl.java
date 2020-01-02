package com.bank.bo;

import java.util.List;

import com.bank.dao.EmpDao;
import com.bank.dao.EmpDaoImpl;
import com.bank.exception.BusinessException;
import com.bank.to.UserAccount;
import com.bank.to.UserCard;

public class EmpBoImpl implements EmpBo {

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

}

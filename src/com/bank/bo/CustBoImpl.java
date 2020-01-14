package com.bank.bo;

import java.util.List;

import com.bank.dao.CustDao;
import com.bank.dao.CustDaoImpl;
import com.bank.exception.BusinessException;
import com.bank.to.UserAccount;
import com.bank.to.UserCard;

public class CustBoImpl implements CustBo {

	private CustDao custDao;

	@Override
	public void applyNewCard(UserCard card) throws BusinessException {
		getCustDao().applyNewCard(card);
	}

	@Override
	public List<UserCard> cardBalanceInfoList(UserAccount userAccount) throws BusinessException {
		List<UserCard> userCard = null;
		userCard = getCustDao().cardBalanceInfoList(userAccount);
		return userCard;
	}

	@Override
	public void updateCardBalance(UserCard card) throws BusinessException {
		getCustDao().updateCardBalance(card);
	}

	@Override
	public void removeAmountPending(UserCard card) throws BusinessException {
		getCustDao().removeAmountPending(card);
	}

	@Override
	public void transferMoney(UserCard card) throws BusinessException {
		getCustDao().transferMoney(card);
	}

	@Override
	public List<UserCard> reviewMoneyPendingList() throws BusinessException {
		List<UserCard> userCard = null;
		userCard = getCustDao().displayMoneyPendingList();
		return userCard;
	}

	@Override
	public void transactionMessage(UserCard card) throws BusinessException {
		getCustDao().transactionMessage(card);
	}

	public CustDao getCustDao() {
		if (custDao == null) {
			custDao = new CustDaoImpl();
		}
		return custDao;
	}

}

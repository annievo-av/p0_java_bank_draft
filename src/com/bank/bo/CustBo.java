package com.bank.bo;

import java.util.List;

import com.bank.exception.BusinessException;
import com.bank.to.UserCard;

public interface CustBo {
	
	public void applyNewCard(UserCard card) throws BusinessException;

	public List<UserCard> cardBalanceInfoList() throws BusinessException;

	public void updateCardBalance(UserCard card) throws BusinessException;
	
	public void removeAmountPending(UserCard card) throws BusinessException;

	public void transferMoney(UserCard card) throws BusinessException;

	public List<UserCard> reviewMoneyPendingList() throws BusinessException;
	
	public void transactionMessage(UserCard card) throws BusinessException;
}

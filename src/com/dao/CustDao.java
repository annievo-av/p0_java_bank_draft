package com.dao;

import java.util.List;

import com.to.UserCard;

public interface CustDao {
	
	public void registerCard(UserCard card) throws Exception;

	public List<UserCard> cardBalanceInfoList() throws Exception;

	public void updateCardBalance(UserCard card) throws Exception;
	
	public void removeAmountPending(UserCard card) throws Exception;

	public void transferMoney(UserCard card) throws Exception;

	public List<UserCard> reviewingMoneyList() throws Exception;
}

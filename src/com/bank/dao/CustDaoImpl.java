package com.bank.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bank.dbutil.OracleConnection;
import com.bank.exception.BusinessException;
import com.bank.to.UserAccount;
import com.bank.to.UserCard;

public class CustDaoImpl implements CustDao {

	@Override
	public void applyNewCard(UserCard card) throws BusinessException {
		try (Connection connection = OracleConnection.getConnection()) {
			String sql = "insert into bank_card_pending(cardnumber, balance, cardtype, username) values (?, ?, ?, ?)";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, card.getCardNumber());
			preparedStatement.setDouble(2, card.getBalance());
			preparedStatement.setString(3, card.getCardType());
			preparedStatement.setString(4, card.getUsername());
			preparedStatement.executeUpdate();
		} catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("Internal error occured");
		}
	}

	@Override
	public List<UserCard> cardBalanceInfoList(UserAccount userAccount) throws BusinessException {
		List<UserCard> cardBalanceInfoList = new ArrayList<>();
		try (Connection connection = OracleConnection.getConnection()) {
			String sql = "select cardnumber, cardtype, balance, username from bank_user_card where username = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, userAccount.getUsername());
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				UserCard card = new UserCard();
				card.setCardNumber(resultSet.getInt("cardnumber"));
				card.setBalance(resultSet.getDouble("balance"));
				card.setCardType(resultSet.getString("cardtype"));
				card.setUsername(resultSet.getString("username"));
				cardBalanceInfoList.add(card);
			}
		} catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("Internal error occured");
		}
		return cardBalanceInfoList;
	}

	@Override
	public void updateCardBalance(UserCard card) throws BusinessException {
		try (Connection connection = OracleConnection.getConnection()) {
			String sql = "update bank_user_card set balance = ? where cardnumber = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setDouble(1, card.getBalance());
			preparedStatement.setInt(2, card.getCardNumber());
			preparedStatement.executeUpdate();
		} catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("Internal error occured");
		}
	}

	@Override
	public void transferMoney(UserCard card) throws BusinessException {
		try (Connection connection = OracleConnection.getConnection()) {
			String sql = "insert into bank_amount_pending(cardnumber, amount, receiver, sender) values (?, ?, ?, ?)";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, card.getCardNumber());
			preparedStatement.setDouble(2, card.getBalance());
			preparedStatement.setString(3, card.getReceiver());
			preparedStatement.setString(4, card.getSender());
			preparedStatement.executeUpdate();
		} catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("Internal error occured");
		}
	}

	@Override
	public List<UserCard> displayMoneyPendingList() throws BusinessException {
		List<UserCard> reviewingMoneyList = new ArrayList<>();
		try (Connection connection = OracleConnection.getConnection()) {
			String sql = "select cardnumber, amount, receiver, sender from bank_amount_pending";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				UserCard card = new UserCard();
				card.setCardNumber(resultSet.getInt("cardnumber"));
				card.setBalance(resultSet.getDouble("amount"));
				card.setReceiver(resultSet.getString("receiver"));
				card.setSender(resultSet.getString("sender"));
				reviewingMoneyList.add(card);
			}
		} catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("Internal error occured");
		}
		return reviewingMoneyList;
	}

	@Override
	public void removeAmountPending(UserCard card) throws BusinessException {
		try (Connection connection = OracleConnection.getConnection()) {
			String sql = "delete from bank_amount_pending where cardnumber = ? AND amount = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, card.getCardNumber());
			preparedStatement.setDouble(2, card.getBalance());
			preparedStatement.executeUpdate();
		} catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("Internal error occured");
		}
	}

	@Override
	public void transactionMessage(UserCard card) throws BusinessException {
		try (Connection connection = OracleConnection.getConnection()) {
			String sql = "{call INSERT_TRANSACTION_LOG(?, ?, ?)}";
			CallableStatement callableStatement = connection.prepareCall(sql);
			callableStatement.setString(1, card.getUsername());
			callableStatement.setString(2, card.getTransactionMessage());
			// util date to sql date
			callableStatement.setString(3, card.getTransactionTime());
			callableStatement.execute();
		} catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("Internal error " + e);
		}
	}

}

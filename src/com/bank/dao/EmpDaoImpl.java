package com.bank.dao;

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

public class EmpDaoImpl implements EmpDao {

	@Override
	public List<UserAccount> newAccountPendingList() throws BusinessException {
		List<UserAccount> newAccountPendingList = new ArrayList<>();
		try (Connection connection = OracleConnection.getConnection()) {
			String sql = "select username, password from bank_user_pending";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				UserAccount user = new UserAccount();
				user.setUsername(resultSet.getString("username"));
				user.setPassword(resultSet.getString("password"));
				newAccountPendingList.add(user);
			}
		} catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("Internal error occured");
		}
		return newAccountPendingList;
	}

	@Override
	public List<UserCard> newCardPendingList() throws BusinessException {
		List<UserCard> newCardPendingList = new ArrayList<>();
		try (Connection connection = OracleConnection.getConnection()) {
			String sql = "select cardnumber, balance, cardtype, username from bank_card_pending";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				UserCard card = new UserCard();
				card.setCardNumber(resultSet.getInt("cardnumber"));
				card.setBalance(resultSet.getDouble("balance"));
				card.setCardType(resultSet.getString("cardtype"));
				card.setUsername(resultSet.getString("username"));
				newCardPendingList.add(card);
			}
		} catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("Internal error occured");
		}
		return newCardPendingList;
	}

	@Override
	public void approveNewAccount(UserAccount userAccount) throws BusinessException {
		try (Connection connection = OracleConnection.getConnection()) {
			String sql = "insert into bank_user_account(username, password, usertype) values (?, ?, ?)";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, userAccount.getUsername());
			preparedStatement.setString(2, userAccount.getPassword());
			preparedStatement.setString(3, userAccount.getUsertype());
			preparedStatement.executeUpdate();
		} catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("Internal error occured");
		}
	}

	@Override
	public void removeAccountPending(UserAccount userAccount) throws BusinessException {
		try (Connection connection = OracleConnection.getConnection()) {
			String sql = "delete from bank_user_pending where username = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, userAccount.getUsername());
			preparedStatement.executeUpdate();
		} catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("Internal error occured");
		}
	}

	@Override
	public void approveNewCard(UserCard card) throws BusinessException {
		try (Connection connection = OracleConnection.getConnection()) {
			String sql = "insert into bank_user_card(cardnumber, balance, cardtype, username) values (?, ?, ?, ?)";
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
	public void removeCardPending(UserCard card) throws BusinessException {
		try (Connection connection = OracleConnection.getConnection()) {
			String sql = "delete from bank_card_pending where cardnumber = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, card.getCardNumber());
			preparedStatement.executeUpdate();
		} catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("Internal error occured");
		}
	}

	@Override
	public List<UserAccount> accountInfoList() throws BusinessException {
		List<UserAccount> accountInfoList = new ArrayList<>();
		try (Connection connection = OracleConnection.getConnection()) {
			String sql = "select a.username, a.password, c.cardnumber, c.cardtype, c.balance "
					+ "from bank_user_account a join bank_user_card c " + "on a.username = c.username";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				UserAccount user = new UserAccount();
				user.setUsername(resultSet.getString("username"));
				user.setPassword(resultSet.getString("password"));
				user.setCard(new UserCard(resultSet.getInt("cardnumber"), resultSet.getString("cardtype"),
						resultSet.getDouble("balance"), resultSet.getString("username")));
				accountInfoList.add(user);
			}
		} catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("Internal error occured");
		}
		return accountInfoList;
	}

	public List<UserCard> transactionLogList() throws BusinessException {
		List<UserCard> viewTransactionLog = new ArrayList<>();
		try (Connection connection = OracleConnection.getConnection()) {
			String sql = "select username, transaction_message, transaction_time from bank_transaction_log order by transaction_time";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				UserCard card = new UserCard();
				card.setUsername(resultSet.getString("username"));
				card.setTransactionMessage(resultSet.getString("transaction_message"));
				card.setTransactionTime(resultSet.getString("transaction_time"));
				viewTransactionLog.add(card);
			}
		} catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("Internal error occured");
		}
		return viewTransactionLog;
	}
}

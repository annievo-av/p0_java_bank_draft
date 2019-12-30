package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dbutil.OracleConnection;
import com.to.Transaction;
import com.to.UserAccount;
import com.to.UserCard;

public class EmpDaoImpl implements EmpDao {

	@Override
	public List<UserAccount> acctApprovalList() throws Exception {
		List<UserAccount> pendingList = new ArrayList<>();
		try (Connection connection = OracleConnection.getConnection()) {
			String sql = "select username, password from bank_user_pending";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				UserAccount user = new UserAccount();
				user.setUsername(resultSet.getString("username"));
				user.setPassword(resultSet.getString("password"));
				pendingList.add(user);
			}
		} catch (ClassNotFoundException | SQLException e) {
			throw new Exception("Internal error occured");
		}
		return pendingList;
	}

	@Override
	public void approveUserAccount(UserAccount userAccount) throws Exception {
		try (Connection connection = OracleConnection.getConnection()) {
			String sql = "insert into bank_user_account(username, password, usertype) values (?, ?, ?)";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, userAccount.getUsername());
			preparedStatement.setString(2, userAccount.getPassword());
			preparedStatement.setString(3, userAccount.getUsertype());
			preparedStatement.executeUpdate();
		} catch (ClassNotFoundException | SQLException e) {
			throw new Exception("Internal error occured");
		}
	}

	@Override
	public void removeAccountPending(UserAccount userAccount) throws Exception {
		try (Connection connection = OracleConnection.getConnection()) {
			String sql = "delete from bank_user_pending where username = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, userAccount.getUsername());
			preparedStatement.executeUpdate();
		} catch (ClassNotFoundException | SQLException e) {
			throw new Exception("Internal error occured");
		}
	}

	@Override
	public List<UserAccount> displayAcctInfoByUsername() throws Exception {
		List<UserAccount> acctList = new ArrayList<>();
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
				acctList.add(user);
			}
		} catch (ClassNotFoundException | SQLException e) {
			throw new Exception("Internal error occured");
		}
		return acctList;
	}

	@Override
	public List<Transaction> logOfTransactionByCardNumber() throws Exception { // left over
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserCard> newCardApprovalList() throws Exception {
		List<UserCard> pendingList = new ArrayList<>();
		try (Connection connection = OracleConnection.getConnection()) {
			String sql = "select cardnumber, balance, type, username from bank_card_pending";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				UserCard card = new UserCard();
				card.setCardNumber(resultSet.getInt("cardnumber"));
				card.setBalance(resultSet.getDouble("balance"));
				card.setType(resultSet.getString("type"));
				card.setUsername(resultSet.getString("username"));
				pendingList.add(card);
			}
		} catch (ClassNotFoundException | SQLException e) {
			throw new Exception("Internal error occured");
		}
		return pendingList;
	}
	
	@Override
	public void approveUserCard(UserCard card) throws Exception {
		try (Connection connection = OracleConnection.getConnection()) {
			String sql = "insert into bank_user_card(cardnumber, balance, cardtype, username) values (?, ?, ?, ?)";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, card.getCardNumber());
			preparedStatement.setDouble(2, card.getBalance());
			preparedStatement.setString(3, card.getType());
			preparedStatement.setString(4, card.getUsername());
			preparedStatement.executeUpdate();
		} catch (ClassNotFoundException | SQLException e) {
			throw new Exception("Internal error occured");
		}
	}

	@Override
	public void removeCardPending(UserCard card) throws Exception {
		try (Connection connection = OracleConnection.getConnection()) {
			String sql = "delete from bank_card_pending where cardnumber = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, card.getCardNumber());
			preparedStatement.executeUpdate();
		} catch (ClassNotFoundException | SQLException e) {
			throw new Exception("Internal error occured");
		}
	}

}

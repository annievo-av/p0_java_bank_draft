package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dbutil.OracleConnection;
import com.to.UserAccount;
import com.to.UserCard;

public class CustDaoImpl implements CustDao {

	@Override
	public void registerCard(UserCard card) throws Exception {
		try (Connection connection = OracleConnection.getConnection()) {
			String sql = "insert into bank_card_pending(cardnumber, balance, type, username) values (?, ?, ?, ?)";
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
	public List<UserCard> cardBalanceInfoList() throws Exception {
		List<UserCard> cardBalance = new ArrayList<>();
		try (Connection connection = OracleConnection.getConnection()) {
			String sql = "select cardnumber, cardtype, balance, username from bank_user_card";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				UserCard card = new UserCard();
				card.setCardNumber(resultSet.getInt("cardnumber"));
				card.setBalance(resultSet.getDouble("balance"));
				card.setType(resultSet.getString("cardtype"));
				card.setUsername(resultSet.getString("username"));
				cardBalance.add(card);
			}
		} catch (ClassNotFoundException | SQLException e) {
			throw new Exception("Internal error occured");
		}
		return cardBalance;
	}

	@Override
	public void updateCardBalance(UserCard card) throws Exception {
		try (Connection connection = OracleConnection.getConnection()) {
			String sql = "update bank_user_card set balance = ? where cardnumber = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setDouble(1, card.getBalance());
			preparedStatement.setInt(2, card.getCardNumber());
			preparedStatement.executeUpdate();
		} catch (ClassNotFoundException | SQLException e) {
			throw new Exception("Internal error occured");
		}
	}

	@Override
	public void transferMoney(UserCard card) throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public void reviewingMoney() throws Exception {
		// TODO Auto-generated method stub

	}

}

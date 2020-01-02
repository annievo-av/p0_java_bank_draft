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

public class UserDaoImpl implements UserDao {

	@Override
	public void signup(UserAccount userAccount) throws BusinessException {
		try (Connection connection = OracleConnection.getConnection()) {
			String sql = "insert into bank_user_pending(username, password) values (?, ?)";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, userAccount.getUsername());
			preparedStatement.setString(2, userAccount.getPassword());
			preparedStatement.executeUpdate();
		} catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("Internal error occured");
		}
	}

	@Override
	public List<UserAccount> accountListForLogin() throws BusinessException {
		List<UserAccount> listUsers = new ArrayList<>();
		try (Connection connection = OracleConnection.getConnection()) {
			String sql = "select username, password, usertype from bank_user_account";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				UserAccount user = new UserAccount();
				user.setUsername(resultSet.getString("username"));
				user.setPassword(resultSet.getString("password"));
				user.setUsertype(resultSet.getString("usertype"));
				listUsers.add(user);
			}
		} catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("Internal error occured");
		}
		return listUsers;
	}

}

package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dbutil.OracleConnection;
import com.to.UserAccount;

public class UserAccountDaoImpl implements UserAccountDao {

	@Override
	public void registerUser(UserAccount userAccount) throws Exception {
		try (Connection connection = OracleConnection.getConnection()) {
			String sql = "insert into bank_user_pending(username, password) values (?, ?)";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, userAccount.getUsername());
			preparedStatement.setString(2, userAccount.getPassword());
			preparedStatement.executeUpdate();
		} catch (ClassNotFoundException | SQLException e) {
			throw new Exception("Internal error occured");
		}
	}

	@Override
	public void logout() {
		System.out.println("Thank you for banking with us. See you again!");
		System.exit(0);
	}

	@Override
	public List<UserAccount> login() throws Exception {
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

			if (listUsers.size() == 0) {
				throw new Exception();
			}

		} catch (ClassNotFoundException | SQLException e) {
			throw new Exception("Internal error occured");
		}
		return listUsers;
	}

}

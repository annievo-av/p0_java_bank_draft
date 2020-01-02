package com.bank.dbutil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class OracleConnection {
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName("oracle.jdbc.OracleDriver");
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String us = "java_rev";
		String pw = "Texas1993";
		return DriverManager.getConnection(url, us, pw);
	}
}

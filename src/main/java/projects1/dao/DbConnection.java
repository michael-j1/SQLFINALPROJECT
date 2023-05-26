package projects1.dao;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;
import projects1.exception.DbException;

public class DbConnection {	
	private static String SCHEMA = "projects1";
	private static String USER = "projects1";
	private static String PASSWORD = "projects1";
    private static String HOST = "localhost";
	private static int PORT = 3306;
	
	public static Connection getConnection() {
		String url = String.format("jdbc:mysql://%s:%d/%s?user=%s&password=%s&useSSL=false",
				HOST, PORT, SCHEMA, USER, PASSWORD);
		System.out.println("Connecting with url=" + url);

	try {
		Connection conn = DriverManager.getConnection(url);
		System.out.println("Successfully obtained connection!" + SCHEMA);
		return conn;
} catch (SQLException e) {
	System.err.println("Error" + url);

		throw new DbException(e);
	}
	
	
	}
	
	}

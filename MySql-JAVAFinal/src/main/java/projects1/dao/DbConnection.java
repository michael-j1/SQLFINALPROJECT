package projects1.dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import projects1.exception.DbException;

public class DbConnection {	
    private static String HOST = "localhost";
	private static int PORT = 3306;
    private static String SCHEMA = "projects1";
	private static String USER = "projects1";
	private static String PASSWORD = "projects1";
	
	public static Connection getConnection() {
		String uri = String.format("jdbc:mysql://%s:%d/%s?user=%s&password=%s&useSSL=false",
				HOST, PORT, SCHEMA, USER, PASSWORD);
		System.out.println("Connecting with url=" + uri);

	try {
		Connection conn = DriverManager.getConnection(uri);
		System.out.println("Successfully obtained connection to schema! " + SCHEMA + "' is success");
		return conn;
} catch (SQLException e) {
	System.out.println("Unable to get connection at " + uri);

		throw new DbException("Unable to get connection at \" + uri");
	}
	
	
	}
	
	}

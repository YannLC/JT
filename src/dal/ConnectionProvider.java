package dal;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

public abstract class ConnectionProvider {
	
	private static String dbName = "TutorialDB";
	
	public static Connection getConnection() throws SQLException {
		String dbURL = "jdbc:sqlserver://127.0.0.1\\JTDB;databaseName=" + dbName + ";Integrated Security=true;"; 
		return DriverManager.getConnection(dbURL, "User", "dumblogin1234");
	}

}

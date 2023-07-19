package in.grs.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JdbcUtil {

	private JdbcUtil() {
		// TODO Auto-generated constructor stub
	}

//Step 1-> Load the driver

	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException ce) {
			// TODO: handle exception
			ce.printStackTrace();
		}
	}

	// Step 2-> Establish the Connection
	/*
	 * public static Connection getJdbcConnection() throws SQLException {
	 * FileInputStream fis = new
	 * FileInputStream("JDBC-DynamicInput/src/application.properties"); Properties p
	 * = new Properties(); p.load(fis); Connection
	 * connection=DriverManager.getConnection(p.getProperty("url"),
	 * p.getProperty("user"), p.getProperty("password")); return Connection; }
	 */
	public static Connection getJdbcConnection() throws SQLException, IOException {

		// Take the data from properties file
		FileInputStream fis = new FileInputStream("src\\\\in\\\\grs\\\\properties\\\\application.properties");
		Properties properties = new Properties();
		properties.load(fis);

		// Step2. Establish the Connection
		Connection connection = DriverManager.getConnection(properties.getProperty("url"),
				properties.getProperty("username"), properties.getProperty("password"));
		System.out.println("connection object created...");
		return connection;
	}

	public static void cleanUp(Connection con, Statement statement, ResultSet resultSet) throws SQLException {
		// Step6. Close the resources
		if (con != null) {
			con.close();
		}

		if (statement != null) {
			statement.close();
		}
		if (resultSet != null) {
			resultSet.close();
		}

	}

}

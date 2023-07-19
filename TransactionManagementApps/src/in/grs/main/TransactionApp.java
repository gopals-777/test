package in.grs.main;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import in.grs.util.JdbcUtil;

public class TransactionApp {
	public static void main(String[] args) {
		Connection connection = null;
		Statement stmt = null;
		ResultSet resultset = null;
		ResultSet resultset2 = null;

		try {
			connection = JdbcUtil.getJdbcConnection();
			stmt = connection.createStatement();
			String sql = "select * from accounts";
			resultset = stmt.executeQuery(sql);
			System.out.println("ID\tNAME\tBALANCE");
			while (resultset.next()) {
				if (resultset != null) {
					int id = resultset.getInt(1);
					String name = resultset.getString(2);
					double balance = resultset.getDouble(3);
					System.out.println(id + "\t" + name + "\t" + balance);
				} else {
					System.out.println("no data/record found");
				}
			}
			// Transaction begins
			connection.setAutoCommit(false);
			stmt.executeUpdate("update accounts set balance=balance+2000 where name='dhoni'");
			System.out.println("confirm the 2000rs transaction YES/NO");
			Scanner sc = new Scanner(System.in);
			String option = sc.next();
			if (option.equalsIgnoreCase("yes")) {
				connection.commit();
			} else {
				connection.rollback();
			}
			System.out.println("Data after transaction");
			if (resultset2 != null) {
				System.out.println("ID\tNAME\tBALANCE");
				while (resultset2.next()) {
					System.out.println(
							resultset2.getInt(1) + "\t" + resultset.getString(2) + "\t" + resultset.getString(3));
				}
			}
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {

				JdbcUtil.cleanUp(connection, stmt, resultset);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}

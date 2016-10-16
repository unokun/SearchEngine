package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLite {

	Connection connection = null;

	public SQLite() throws SQLException {
		connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
	}

	public Statement createStatement() throws SQLException {
		return connection.createStatement();
	}

	public void close() throws SQLException {
		if (connection == null) {
			return;
		}
		connection.close();
	}

}

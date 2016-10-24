package db;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLite {

	Connection connection = null;
	String dbName;
	public Connection getConnection() {
		return connection;
	}
	
	public SQLite() throws SQLException {
		connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
	}
	public SQLite(String dbName) throws SQLException {
		this(dbName, false);
	}
	public SQLite(String dbName, boolean drop) throws SQLException {
		this.dbName = dbName;
		if (drop) {
			dropDatabase();
		}
		connection = DriverManager.getConnection("jdbc:sqlite:" + dbName + ".db");
	}
	
	public void dropDatabase() throws SQLException {
		File dbFile = new File(dbName + ".db");
		
		dbFile.delete();
//		System.out.println(dbFile.getName() + " exists " + dbFile.exists());
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

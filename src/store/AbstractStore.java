package store;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class AbstractStore {
	String tableName;
	
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	boolean isTableExists(Connection conn) throws SQLException {
		try(Statement statement = conn.createStatement()) {

			boolean isExists = false;
			StringBuilder builder = new StringBuilder();
			builder.append("select count(*) from sqlite_master ")
				   .append("where type='table' and name='")
				   .append(tableName)
				   .append("'");
//			System.out.println(builder.toString());
			ResultSet rs = statement.executeQuery(builder.toString());
			if (rs.next()) {
				isExists =  (rs.getInt(1) > 0);
			}
			rs.close();
			return isExists;
		}
	}
	
	public int count(Connection conn) throws SQLException {
		try(Statement statement = conn.createStatement()) {

			int count = 0;
			StringBuilder builder = new StringBuilder();
			builder.append("select count(*) from ")
				   .append(tableName);
//			System.out.println(builder.toString());
			ResultSet rs = statement.executeQuery(builder.toString());
			if (rs.next()) {
				count =  rs.getInt(1);
			}
			rs.close();
			return count;
		}
	}
	void deleteAll(Connection conn) throws SQLException {
		try(Statement statement = conn.createStatement()) {
			statement.executeUpdate("drop table if exists " + tableName);
		}
	}

}

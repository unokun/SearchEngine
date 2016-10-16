package db;

import static org.junit.Assert.fail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SQLiteTest {

	SQLite sqlite;

	@Before
	public void setUp() throws Exception {
		sqlite = new SQLite();
	}

	@After
	public void tearDown() throws Exception {
		if (sqlite == null) {
			return;
		}
		sqlite.close();
	}

	@Test
	public void testCreateTable() {
		Statement statement;
		try {
			statement = sqlite.createStatement();
			statement.executeUpdate("create table if not exists  person (id integer, name string)");
			statement.executeUpdate("insert into person values(1, 'leo')");
			statement.executeUpdate("insert into person values(2, 'yui')");
			ResultSet rs = statement.executeQuery("select * from person");
			while (rs.next()) {
				// read the result set
				System.out.println("name = " + rs.getString("name"));
				System.out.println("id = " + rs.getInt("id"));
			}
			statement.executeUpdate("drop table if exists person");
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
			fail();
		}
	}

}

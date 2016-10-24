package store;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.Term;

public class TermStore extends AbstractStore {
	private static final String TABLE_TERM = "term";
	private static final String COLUMN_ID = "id";
	private static final String COLUMN_SURFACE = "surface";
	private static final String COLUMN_READING = "reading";
	
	TermStore() {
		setTableName(TABLE_TERM);
	}
	void createTable(Connection conn) throws SQLException {
		try(Statement statement = conn.createStatement()) {
			statement.executeUpdate("create table if not exists term (id string, surface string, reading string)");
		}
	}

	void insert(Connection conn, Term term) throws SQLException {
		if (isExists(conn, term.getId())) {
			return;
		}
		
		try(Statement statement = conn.createStatement()) {
			StringBuilder builder = new StringBuilder();
			builder.append("insert into ")
				   .append(TABLE_TERM)
				   .append(" values(");
			builder.append("'")
				   .append(term.getId())
				   .append("', '")
				   .append(term.getSurface())
				   .append("', '")
				   .append(term.getReading())
				   .append("'") ;
			builder.append(")");
//			System.out.println(builder.toString());
			statement.executeUpdate(builder.toString());
		}

	}
	
	boolean isExists(Connection conn, String id) throws SQLException {
		Term term = find(conn, id);
		return (term != null);
	}
	
	Term find(Connection conn, String id) throws SQLException {
		try(Statement statement = conn.createStatement()) {
			Term doc = null;
			StringBuilder builder = new StringBuilder();
			builder.append("select id, surface, reading from ")
				   .append(TABLE_TERM)
				   .append(" where id = '")
				   .append(id)
				   .append("'");
			ResultSet rs = statement.executeQuery(builder.toString());
			if (rs.next()) {
				doc =  createTerm(rs);
			}
			rs.close();
			return doc;
		}
	}
	Term createTerm(ResultSet rs) throws SQLException {
		Term term = new Term();
		term.setId(rs.getString(COLUMN_ID));
		term.setSurface(rs.getString(COLUMN_SURFACE));
		term.setReading(rs.getString(COLUMN_READING));
		return term;
	}
	
	void delete(Connection conn, Term term) {
		
	}

}

package store;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Document;
import model.Term;

public class TermDocStore extends AbstractStore {
	private static final String TABLE_TERMDOC = "termdoc";
	
	TermDocStore() {
		setTableName(TABLE_TERMDOC);
	}
	void createTable(Connection conn) throws SQLException {
		try(Statement statement = conn.createStatement()) {
			statement.executeUpdate("create table if not exists termdoc (termid string, docid string)");
		}
	}

	void insert(Connection conn, Term term, Document doc) throws SQLException {
		
		try(Statement statement = conn.createStatement()) {
			StringBuilder builder = new StringBuilder();
			builder.append("insert into ")
				   .append(TABLE_TERMDOC)
				   .append(" values(");
			builder.append("'")
				   .append(term.getId())
				   .append("', '")
				   .append(doc.getId())
				   .append("'") ;
			builder.append(")");
//			System.out.println(builder.toString());
			statement.executeUpdate(builder.toString());
		}

	}
	List<String> find(Connection conn, String termid) throws SQLException {
		try(Statement statement = conn.createStatement()) {
			List<String> docidList = new ArrayList<String>();
			StringBuilder builder = new StringBuilder();
			builder.append("select docid from ")
				   .append(TABLE_TERMDOC)
				   .append(" where termid = '")
				   .append(termid)
				   .append("'");
			ResultSet rs = statement.executeQuery(builder.toString());
			if (rs.next()) {
				docidList.add(rs.getString(1));
			}
			rs.close();
			return docidList;
		}
	}
	void delete(Connection conn, Term term) {
		
	}

}

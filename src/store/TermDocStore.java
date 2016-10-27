package store;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Document;
import model.Term;

public class TermDocStore extends AbstractStore {
	private static final String TABLE_TERMDOC = "termdoc";
	private static final String TABLE_DOC = "doc";
	
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
	public int getDocFreq(Connection conn, String termid) throws SQLException {
		try(Statement statement = conn.createStatement()) {
			int count = 0;
			StringBuilder builder = new StringBuilder();
			builder.append("select count(docid) from ")
				   .append(TABLE_TERMDOC)
				   .append(" where termid = '")
				   .append(termid)
				   .append("'");
			ResultSet rs = statement.executeQuery(builder.toString());
			if (rs.next()) {
				count = rs.getInt(1);
			}
			rs.close();
			return count;
		}
	}
	public Map<Document, Integer> getTermFreq(Connection conn, String termid) throws SQLException {
		try(Statement statement = conn.createStatement()) {
			Map<Document, Integer> termFreq = new HashMap<>();
			StringBuilder builder = new StringBuilder();
			builder.append("select T1.docid, T2.title, count(T1.termid) from ")
				   .append(TABLE_TERMDOC + " AS T1 ")
				   .append(" inner join " + TABLE_DOC + " AS T2 ON T1.docid = T2.docid'")
				   .append(" where termid = '")
				   .append(termid)
				   .append("'");
			ResultSet rs = statement.executeQuery(builder.toString());
			if (rs.next()) {
				Document doc = new Document();
				doc.setId(rs.getString(1));
				doc.setTitle(rs.getString(2));
				termFreq.put(doc, rs.getInt(3));
			}
			rs.close();
			return termFreq;
		}
	}
	void delete(Connection conn, Term term) {
		
	}

}

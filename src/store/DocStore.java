package store;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.Document;

public class DocStore extends AbstractStore {
	private static final String TABLE_DOC = "document";
	private static final String COLUMN_ID = "id";
	private static final String COLUMN_TITLE = "title";
	private static final String COLUMN_CONTENT = "content";
	
	DocStore() {
		setTableName(TABLE_DOC);
	}
	
	void createTable(Connection conn) throws SQLException {
		try(Statement statement = conn.createStatement()) {
			statement.executeUpdate("create table if not exists document (id string, title string, content string)");
		}
	}
	void insert(Connection conn, Document doc) throws SQLException {
		
		try(Statement statement = conn.createStatement()) {
			StringBuilder builder = new StringBuilder();
			builder.append("insert into ")
				   .append(TABLE_DOC)
				   .append(" values(");
			builder.append("'")
				   .append(doc.getId())
				   .append("', '")
				   .append(doc.getTitle())
				   .append("', '")
				   .append(doc.getContent())
				   .append("'") ;
			builder.append(")");
//			System.out.println(builder.toString());

			statement.executeUpdate(builder.toString());
		}

	}
	
	Document find(Connection conn, String id) throws SQLException {
		try(Statement statement = conn.createStatement()) {
			Document doc = null;
			ResultSet rs = statement.executeQuery("select id, title, content from " + TABLE_DOC);
			if (rs.next()) {
				doc =  createDoc(rs);
			}
			rs.close();
			return doc;
		}
	}
	
	/**
	 * 文書数
	 */
	public int count(Connection conn) throws SQLException {
		try(Statement statement = conn.createStatement()) {
			int count = 0;
			ResultSet rs = statement.executeQuery("select count(*) from " + TABLE_DOC);
			if (rs.next()) {
				count =  rs.getInt(1);
			}
			rs.close();
			return count;
		}
	}
	Document createDoc(ResultSet rs) throws SQLException {
		Document doc = new Document();
		doc.setId(rs.getString(COLUMN_ID));
		doc.setTitle(rs.getString(COLUMN_TITLE));
		doc.setContent(rs.getString(COLUMN_CONTENT));
		return doc;
	}
	
	void delete(Connection conn, Document doc) {
		
	}
	
}

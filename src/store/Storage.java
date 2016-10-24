package store;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

import org.atilika.kuromoji.Token;

import db.SQLite;
import model.Document;
import model.Term;

public class Storage {
	private SQLite db;

	DocStore docStore;
	TermStore termStore;
	TermDocStore termDocStore;
	
	public TermDocStore getTermDocStore() {
		return termDocStore;
	}
	public DocStore getDocStore() {
		return docStore;
	}
	public TermStore getTermStore() {
		return termStore;
	}
	public void init(String dbName) throws SQLException {
		init(dbName, false);
	}
	public void init(String dbName, boolean drop) throws SQLException {
		db = new SQLite(dbName, drop);
		Connection conn = getConnection();
		docStore = new DocStore();
		docStore.createTable(conn);

		termStore = new TermStore();
		termStore.createTable(conn);
		
		termDocStore = new TermDocStore();
		termDocStore.createTable(conn);
	}

	void dropDatabase() throws SQLException {
		db.dropDatabase();
		
	}
	void close() throws SQLException {
		if (db != null) {
			db.close();
		}
	}
	public Connection getConnection() {
		return db.getConnection();
	}

	public void storeDocument(Document doc) throws SQLException {
		
		docStore.insert(getConnection(), doc);
	}
	
	public Document createDocument(File file) {
		Document doc = new Document();
		doc.setId(getId());
		doc.setTitle(getTitle(file.getName()));
		return doc;
	}

	// DBに書き込む
	public void storeTerm(Term term, Document doc) throws SQLException {
		termStore.insert(getConnection(), term);
		termDocStore.insert(getConnection(), term, doc);
	}
	
	public Term createTerm(Token token) {
		Term term = new Term();
		term.setId(getId());
		term.setSurface(token.getSurfaceForm());
		term.setReading(token.getReading());
		return term;
	}
	
	String getId() {
		return UUID.randomUUID().toString();
	}

	String getTitle(String fileName) {
		int index = fileName.lastIndexOf('.');
		if (index == -1) {
			return fileName;
		}
		return fileName.substring(0, index);
	}
}

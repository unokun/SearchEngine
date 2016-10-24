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
		docStore = new DocStore();
		docStore.createTable(getConnection());

		termStore = new TermStore();
		termStore.createTable(getConnection());
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

	public void storeDocument(File file) throws SQLException {
		
		docStore.insert(getConnection(), createDocument(file));
	}
	
	Document createDocument(File file) {
		Document doc = new Document();
		doc.setId(getId());
		doc.setTitle(getTitle(file.getName()));
		return doc;
	}

	// DBに書き込む
	public void storeToken(Token token) throws SQLException {
		termStore.insert(getConnection(), createTerm(token));
	}
	
	Term createTerm(Token token) {
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

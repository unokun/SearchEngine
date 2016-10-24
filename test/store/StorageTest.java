package store;

import static org.junit.Assert.*;
import static org.mockito.Mockito.spy;

import java.io.File;
import java.sql.Connection;

import org.atilika.kuromoji.Token;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import indexer.DocIndexer;
import model.Document;
import model.Term;

public class StorageTest {
	Storage storage;
	String dbName = "testdb";
	
	@Before
	public void setUp() throws Exception {

	}

	@After
	public void tearDown() throws Exception {
		storage.dropDatabase();
	}

	@Test
	public void testInit() {
		try {
			storage = new Storage();
			storage.init(dbName, true);


			// DBが存在していること
			File db = new File(dbName + ".db");
			assertTrue(db.exists());
			
			Connection conn = storage.getConnection();
			// document tableが存在していること
			// レコード数が0であること
			assertTrue(storage.docStore.isTableExists(conn));
			assertEquals(storage.docStore.count(conn), 0);

			// term tableが存在していること
			// レコード数が0であること
			assertTrue(storage.termStore.isTableExists(conn));
			assertEquals(storage.termStore.count(conn), 0);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
			
		}
	}
	@Test
	public void testStore() {
		try {
			storage = spy(new Storage());
			storage.init(dbName, true);

			Connection conn = storage.getConnection();
			
			// Documentの登録
			File file = new File("abc.txt");
			storage.storeDocument(file);
			assertEquals(storage.docStore.count(conn), 1);
			
			// タームの登録
			Term term = createTerm("1", "東京", "とうきょう");
			storage.termStore.insert(conn, term);
			assertEquals(storage.termStore.count(conn), 1);
			
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	Term createTerm(String id, String surface, String reading) {
		Term term = new Term();
		term.setId(id);
		term.setSurface(surface);
		term.setReading(reading);
		return term;
	}

}

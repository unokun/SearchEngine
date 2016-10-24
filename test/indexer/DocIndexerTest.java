package indexer;

import java.io.File;
import java.sql.Connection;

import org.atilika.kuromoji.Token;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.*;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

import model.Document;
import model.Term;
import store.Storage;

/**
 * Mockito 初めの一歩 - Qiita 
 * http://qiita.com/mstssk/items/98e597c13f12746c907d
 * 
 * @author unokun
 *
 */
public class DocIndexerTest {
//	@Mock
	DocIndexer docIndexer;
	
	@Before
	public void setUp() throws Exception {
//		MockitoAnnotations.initMocks(this);
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testProcessDocTokenizer() {

		try {
			Storage storage = Indexer.getStorage();
			storage.init("test", true);

//			docIndexer = mock(DocIndexer.class);
			docIndexer = spy(new DocIndexer());
			docIndexer.setStorage(storage);

			File file = new File("");

			doReturn("東京特許許可局").when(docIndexer).getText(ArgumentMatchers.any(File.class));
			doNothing().when(docIndexer).storeDocument(ArgumentMatchers.any(Document.class));
			doNothing().when(docIndexer).storeTerm(ArgumentMatchers.any(Term.class), ArgumentMatchers.any(Document.class));

	        docIndexer.processDoc(file);
//			assertEquals("東京特許許可局", docIndexer.getText(new File("")));
	        verify(docIndexer, times(1)).storeDocument(ArgumentMatchers.any(Document.class));
	        verify(docIndexer, times(4)).storeTerm(ArgumentMatchers.any(Term.class), ArgumentMatchers.any(Document.class));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
			
		}
	}
	@Test
	public void testProcessDoc() {
		try {

			Storage storage = Indexer.getStorage();
			storage.init("test", true);
			Connection conn = storage.getConnection();

			docIndexer = spy(new DocIndexer());
			docIndexer.setStorage(storage);
			
			doReturn("東京特許許可局").when(docIndexer).getText(ArgumentMatchers.any(File.class));

			File file = new File("");
			docIndexer.processDoc(file);
			
			assertEquals(1, storage.getDocStore().count(conn));
			assertEquals(4, storage.getTermStore().count(conn));
			assertEquals(4, storage.getTermDocStore().count(conn));


		} catch (Exception e) {
			fail();
		}
	}
}

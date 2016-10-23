package indexer;

import static org.junit.Assert.fail;

import java.io.File;

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
//			docIndexer = mock(DocIndexer.class);
			docIndexer = spy(new DocIndexer());
			File file = new File("");

			doReturn("東京特許許可局").when(docIndexer).getText(ArgumentMatchers.any(File.class));
			doNothing().when(docIndexer).storeDocument(ArgumentMatchers.any(File.class));
			doNothing().when(docIndexer).storeToken(ArgumentMatchers.any(Token.class));

	        docIndexer.processDoc(file);
//			assertEquals("東京特許許可局", docIndexer.getText(new File("")));
	        verify(docIndexer, times(1)).storeDocument(ArgumentMatchers.any(File.class));
	        verify(docIndexer, times(4)).storeToken(ArgumentMatchers.any(Token.class));
		} catch (Exception e) {
			fail();
			
		}
	}

}

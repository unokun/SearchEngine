package indexer;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.atilika.kuromoji.Token;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import mockit.*;
import mockit.internal.expectations.TestOnlyPhase;

public class DocIndexerTest {
	@Mocked
	DocIndexer docIndexer;
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {

		try {
			new Expectations() {
				{
				docIndexer.getText((File)any);
				result = "東京特許許可局";
//				docIndexer.storeToken((Token)any);
//				times = 4;

				}
//				{
//					docIndexer.storeToken((Token)any);
//					times = 4;
//				}
			};

//			System.out.println(docIndexer.getText(new File("")));
//	        docIndexer.processDoc(new File(""));
			assertEquals("東京特許許可局", docIndexer.getText(new File("")));
		} catch (Exception e) {
			fail();
			
		}
	}

}

package indexer;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TextExtractorFactoryTest {
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCreate() {
		try {
			File file = new File("file.txt");
			TextExtractor extractor = TextExtractorFactory.create(file);
			assertTrue(extractor instanceof PlainTextExtractor);
			
		} catch (Exception e) {
			fail();
		}
	}
	@Test
	public void testGetExt() {
		try {
			String fileName = "file.txt";
			String actual = TextExtractorFactory.getExt(fileName);
			assertEquals(actual, "txt");
		} catch (Exception e) {
			fail();
		}
	}

}

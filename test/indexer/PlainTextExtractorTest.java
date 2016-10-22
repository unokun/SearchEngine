package indexer;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PlainTextExtractorTest {
	private static final String TMP_DIR = "tmp";

	PlainTextExtractor extractor;
	
	TestFiles testFiles;

	@Before
	public void setUp() throws Exception {
		extractor = new PlainTextExtractor();
		testFiles = new TestFiles(TMP_DIR);

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testExtract() {
		try {
			String expected = "東京特許許可局";
			String fileName = testFiles.createFile("file1.txt", expected);
			String actual = extractor.extract(fileName);
			assertEquals(expected, actual);
		} catch (Exception e) {
			fail();
			
		}
	}
	


}

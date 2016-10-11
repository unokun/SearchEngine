package indexer;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ScannerTest {
	Scanner scanner;
	
	@Before
	public void setUp() {
		scanner = new LFSScanner();
	}
	@Test
	public void testScan() {
		scanner.scan(".");
	}

}

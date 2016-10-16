package indexer;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LFSScannerTest implements ScanEventListener {

	Scanner scanner;
	List<File> fileList;
	File testDir = new File("tmp");
	List<File> testFileList;

	@Before
	public void setUp() throws Exception {
		scanner = new LFSScanner();
		fileList = new ArrayList<>();

		testDir.mkdir();
		testFileList = new ArrayList<>();

	}

	@After
	public void tearDown() throws Exception {
		// testDir.delete();
	}

	@Test
	public void testScan() {
		try {
			// テストファイルを作成する
			File f = new File(testDir + File.separator + "file1.txt");
			f.createNewFile();
			testFileList.add(f);
			
			scanner.addScanEventListener(this);
			scanner.scan(testDir);
			
			assertThat(fileList.size(), is(1));
			assertThat(fileList.get(0).getAbsolutePath(), is(testFileList.get(0).getAbsolutePath()));
		} catch (Exception e) {
			fail();
		}
	}

	@Override
	public void found(File file) {
		fileList.add(file);
	}

}

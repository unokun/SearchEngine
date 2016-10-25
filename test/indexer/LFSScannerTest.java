package indexer;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class LFSScannerTest implements ScanEventListener {

	private static final String TMP_DIR = "tmp";
	Scanner scanner;
	List<File> fileList;
	Set<String> testFileSet;

	TestFiles testFiles;
	
	@Before
	public void setUp() throws Exception {
		scanner = new LFSScanner();
		fileList = new ArrayList<>();
		testFileSet = new HashSet<>();
		
		testFiles = new TestFiles(TMP_DIR);

	}

	@After
	public void tearDown() throws Exception {
		try {
			testFiles.cleanUp();
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testEventListener() {
		try {
			assertEquals(0, scanner.countEventListener());
			scanner.addScanEventListener(this);
			assertEquals(1, scanner.countEventListener());
			
			scanner.removeScanEventListener(this);
			assertEquals(0, scanner.countEventListener());
			
		} catch (Exception e) {
			fail();
		}	
	}
	/**
	 * ファイル数が0の場合
	 */
	@Test
	public void testScan0() {
		try {
			
			assertEquals(0, scanner.countEventListener());
			scanner.addScanEventListener(this);
			assertEquals(1, scanner.countEventListener());
			
			scanner.scan(TMP_DIR);
			scanner.removeScanEventListener(this);
			assertEquals(0, scanner.countEventListener());
			
			// 作成したテストファイルがスキャンされていることを確認する
			for (File file : fileList) {
				System.out.println(file);
			}
			assertEquals(0, fileList.size());
		} catch (Exception e) {
			fail();
		}
	}
	/**
	 * ファイル数が1の場合
	 */
	@Test
	public void testScan1() {
		try {
			// テストファイルを作成する
			testFileSet = createTextFileSet(TMP_DIR, 1);
			
			scanner.addScanEventListener(this);
			scanner.scan(TMP_DIR);
			
			// 作成したテストファイルがスキャンされていることを確認する
			assertEquals(1, fileList.size());
			assertTrue(testFileSet.contains(fileList.get(0).getAbsolutePath()));
		} catch (Exception e) {
			fail();
		}
	}
	/**
	 * ファイル数が1の場合
	 * 引数がファイル名
	 */
	@Test
	public void testScan2() {
		try {
			// テストファイルを作成する
			testFileSet = createTextFileSet(TMP_DIR, 1);
			
			scanner.addScanEventListener(this);
			scanner.scan(TMP_DIR);
			
			// 作成したテストファイルがスキャンされていることを確認する
			assertEquals(1, fileList.size());
			assertTrue(testFileSet.contains(fileList.get(0).getAbsolutePath()));
		} catch (Exception e) {
			fail();
		}
	}
	/**
	 * サブディレクトリありの場合
	 * ファイル数が1の場合
	 * 
	 * tmp/tmp1/files
	 */
	@Test
	public void testScan10() {
		try {
			// テストファイルを作成する
			File subdir = new File(TMP_DIR + File.separator + "tmp1");
			subdir.mkdir();
			testFileSet = createTextFileSet(subdir.getAbsolutePath(), 1);
			
			scanner.addScanEventListener(this);
			scanner.scan(TMP_DIR);
			
			// 作成したテストファイルがスキャンされていることを確認する
			assertEquals(1, fileList.size());
			assertTrue(testFileSet.contains(fileList.get(0).getAbsolutePath()));
		} catch (Exception e) {
			fail();
		}
	}
	
	/**
	 * テスト用ファイルを作成します
	 * 
	 * @param parentDir
	 * @param n
	 * @return
	 * @throws IOException
	 */
	Set<String> createTextFileSet(String parentDir, int n) throws IOException {
		Set<String> testFileSet = new HashSet<>();

		for (int i = 0; i < n; i++) {
			File f = new File(parentDir + File.separator + "file" + i + ".txt");
			f.createNewFile();
			testFileSet.add(f.getAbsolutePath());
			
		}
		return testFileSet;
	}

	@Override
	public void found(File file) {
		fileList.add(file);
	}

}

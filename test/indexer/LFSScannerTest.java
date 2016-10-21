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

	Scanner scanner;
	List<File> fileList;
	File tmpDir = new File("tmp");
	Set<String> testFileSet;

	@Before
	public void setUp() throws Exception {
		scanner = new LFSScanner();
		fileList = new ArrayList<>();

		tmpDir.mkdir();
		testFileSet = new HashSet<>();

	}

	void deleteDir(File dir) {
		File[] files = dir.listFiles();
		for (File file: files) {
			if (file.isDirectory()) {
				deleteDir(file);
			}
			if (file.isFile()) {
				file.delete();
			}
		}	
	}
	@After
	public void tearDown() throws Exception {
		try {
			deleteDir(tmpDir);
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testEventListener() {
		try {
			assertSame(0, scanner.countEventListener());
			scanner.addScanEventListener(this);
			assertSame(1, scanner.countEventListener());
			
			scanner.removeScanEventListener(this);
			assertSame(0, scanner.countEventListener());
			
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
			
			assertSame(0, scanner.countEventListener());
			scanner.addScanEventListener(this);
			assertSame(1, scanner.countEventListener());
			
			scanner.scan(tmpDir);
			scanner.removeScanEventListener(this);
			assertSame(0, scanner.countEventListener());
			
			// 作成したテストファイルがスキャンされていることを確認する
			assertSame(0, fileList.size());
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
			testFileSet = createTextFileSet(tmpDir, 1);
			
			scanner.addScanEventListener(this);
			scanner.scan(tmpDir);
			
			// 作成したテストファイルがスキャンされていることを確認する
			assertSame(1, fileList.size());
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
			testFileSet = createTextFileSet(tmpDir, 1);
			
			scanner.addScanEventListener(this);
			scanner.scan(tmpDir.getName());
			
			// 作成したテストファイルがスキャンされていることを確認する
			assertSame(1, fileList.size());
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
			File subdir = new File(tmpDir.getName() + File.separator + "tmp1");
			subdir.mkdir();
			testFileSet = createTextFileSet(subdir, 1);
			
			scanner.addScanEventListener(this);
			scanner.scan(tmpDir);
			
			// 作成したテストファイルがスキャンされていることを確認する
			assertSame(1, fileList.size());
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
	Set<String> createTextFileSet(File parentDir, int n) throws IOException {
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

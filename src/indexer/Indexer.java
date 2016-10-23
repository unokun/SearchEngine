package indexer;

import java.util.ArrayList;
import java.util.List;

public class Indexer {
	LFSScanner scanner = new LFSScanner();
	
	List<Thread> scannerThreads = new ArrayList<Thread>();
	List<Thread> indexerThreads = new ArrayList<Thread>();

	/**
	 * Indexing開始
	 */
	void startIndex() {
		// キューを作成
		DocumentQueue queue = new DocumentQueue();
		
		// docScannerスレッド
		LFSScanner scanner = new LFSScanner();
		scanner.setQueue(queue);
		Thread scannerThread = new Thread(scanner);
		scannerThreads.add(scannerThread);
		scannerThread.start();
		
		// docIndexerスレッド
		DocIndexer indexer = new DocIndexer();
		indexer.setQueue(queue);
		Thread indexerThread = new Thread(indexer);
		indexerThreads.add(indexerThread);
		indexerThread.start();
		
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

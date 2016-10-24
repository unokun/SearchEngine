package indexer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import store.Storage;

public class Indexer {
	private final static String DB_NAME = "searchengine";
	
	LFSScanner scanner = new LFSScanner();
	public static Storage storage;
	
	List<Thread> scannerThreads = new ArrayList<Thread>();
	List<Thread> indexerThreads = new ArrayList<Thread>();

	public static Storage getStorage() throws SQLException {
		if (storage == null) {
			storage = new Storage();
			storage.init(DB_NAME);
		}
		return storage;
	}
	/**
	 * Indexing開始
	 */
	void startIndex() {
		try {
		
			// キューを作成
			DocumentQueue queue = new DocumentQueue();
			
			// docScannerスレッド
			LFSScanner scanner = new LFSScanner();
			scanner.setQueue(queue);
			Thread scannerThread = new Thread(scanner);
			scannerThreads.add(scannerThread);
			scannerThread.start();
			
			// docIndexerスレッド
			DocIndexer docIndexer = new DocIndexer();
			docIndexer.setQueue(queue);
			docIndexer.setStorage(Indexer.getStorage());
			Thread indexerThread = new Thread(docIndexer);
			indexerThreads.add(indexerThread);
			indexerThread.start();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

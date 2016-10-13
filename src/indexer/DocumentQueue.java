package indexer;

import java.io.File;
import java.util.LinkedList;
import java.util.Queue;

public class DocumentQueue implements ScanEventListener {
	Queue<String> queue = new LinkedList<String>();
	/**
	 * ファイルを見つけた
	 * @param file
	 */
	public void found(File file) {
		queue.add(file.getAbsolutePath());
		
	}
	
	public String pull() {
		return queue.poll();
	}
}

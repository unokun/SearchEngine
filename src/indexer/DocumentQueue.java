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
	/**
	 * キューに含まれているドキュメントパスの数を取得します
	 * @return
	 */
	int countQueue() {
		return queue.size();
	}
	
	/**
	 * 先頭のデータを取得します
	 * 
	 * @return
	 */
	public String poll() {
		return queue.poll();
	}
}

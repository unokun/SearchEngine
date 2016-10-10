package indexer;

import java.io.File;

public interface ScanEventListener {
	/**
	 * ファイルを見つけた
	 * @param file
	 */
	public void found(File file);
}

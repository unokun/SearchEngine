package indexer;

import java.io.File;

public class LFSScanner extends AbstractDocScanner {

	/**
	 * ファイルをスキャンします。
	 * 
	 * @param path
	 *            スキャンするディレクトリ
	 * 
	 */
	@Override
	public void scan(File path) {
		File[] files = path.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				scan(file);
			}
			if (file.isFile()) {
				for (ScanEventListener listener : listenerList) {
					listener.found(file);
				}
			}
		}
	}

}

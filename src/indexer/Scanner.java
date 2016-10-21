package indexer;

import java.io.File;

public interface Scanner {
	public void addScanEventListener(ScanEventListener listener);
	public void removeScanEventListener(ScanEventListener listener);
	public int countEventListener();
	public void scan(String path);
	public void scan(File path);
}

package indexer;

import java.io.File;

public interface Scanner {
	public void addScanEventListener(ScanEventListener listener);
	public void removeScanEventListener(ScanEventListener listener);
	public void scan(String path);
	public void scan(File path);
}

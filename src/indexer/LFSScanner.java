package indexer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LFSScanner implements Scanner {

	private List<ScanEventListener> listenerList = new ArrayList<>();
	
	@Override
	public void addScanEventListener(ScanEventListener lisnter) {
		listenerList.add(lisnter);
	}

	@Override
	public void scan(String path) {
		

	}

	@Override
	public void scan(File path) {
		File[] files = path.listFiles();
		for (File file: files) {
			if (file.isDirectory()) {
				scan(file);
				return;
			}
			
		}
	}
	
	
}

package indexer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDocScanner implements Scanner, Runnable {
	// ドキュメントキュー
	private DocumentQueue queue;

	public DocumentQueue getQueue() {
		return queue;
	}

	public void setQueue(DocumentQueue queue) {
		this.queue = queue;
	}

	protected List<ScanEventListener> listenerList = new ArrayList<>();

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void addScanEventListener(ScanEventListener listener) {
		listenerList.add(listener);

	}

	@Override
	public void removeScanEventListener(ScanEventListener listener) {
		listenerList.remove(listener);

	}

	@Override
	public int countEventListener() {
		return listenerList.size();
	}

	@Override
	public void scan(String path) {
		scan(new File(path));

	}


}

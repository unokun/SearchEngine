package indexer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class TestFiles {
	
	String path;
	
	TestFiles() {
		this("tmp");
	}
	
	TestFiles(String path) {
		new File(path).mkdir();
		
		this.path = path;
	}
	@Override
	protected void finalize() throws Throwable {
		cleanUp();
		super.finalize();
	}

	void cleanUp() {
		cleanUp(new File(path));
	}
	void cleanUp(File dir) {
		File[] files = dir.listFiles();
		for (File file: files) {
			if (file.isDirectory()) {
				cleanUp(file);
			}
			if (file.isFile()) {
				file.delete();
			}
		}	
	}
	String createFile(String fileName, String content) throws IOException {
		String target = path + File.separator + fileName;
		try (FileOutputStream os = new FileOutputStream(target)) {
			os.write(content.getBytes());
			os.flush();
		}
		return target;
	}
}

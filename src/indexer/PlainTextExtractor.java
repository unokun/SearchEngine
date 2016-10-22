package indexer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class PlainTextExtractor extends AbstractTextExtractor {

	@Override
	public String extract(File file) throws IOException {
		// FIXME
		// 複数文字コードに対応
		try (FileInputStream is = new FileInputStream(file)) {
			byte[] bytes = new byte[is.available()];
			is.read(bytes);
			return new String(bytes);

		}
	}

}

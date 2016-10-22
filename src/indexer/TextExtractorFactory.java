package indexer;

import java.io.File;

public class TextExtractorFactory {

	public static TextExtractor create(String file) {
		return create(new File(file));
	}
	public static TextExtractor create(File file) {
		return null;
	}
}

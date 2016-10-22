package indexer;

import java.io.File;
import java.io.IOException;

public interface TextExtractor {

	public String extract(String file) throws IOException;
	public String extract(File file) throws IOException;
}

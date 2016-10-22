package indexer;

import java.io.File;
import java.io.IOException;

public abstract class AbstractTextExtractor implements TextExtractor {

	@Override
	public String extract(String file) throws IOException {
		return extract(new File(file));
	}


}

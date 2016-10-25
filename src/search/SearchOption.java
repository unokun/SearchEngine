package search;

import java.util.List;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

public class SearchOption {
	enum Algorism {
			TFIDF
	}
	@Argument
	List<String> terms;
	Algorism algorism = Algorism.TFIDF;
	
	public boolean parse(String[] args) throws CmdLineException {
		CmdLineParser parser = new CmdLineParser(this);

		parser.parseArgument(args);
		if (terms.isEmpty()) {
			return false;
		}
		return true;
	}
}

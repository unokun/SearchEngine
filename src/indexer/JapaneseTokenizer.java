package indexer;

import java.util.List;

import org.atilika.kuromoji.Token;
import org.atilika.kuromoji.Tokenizer;

public class JapaneseTokenizer {

	Tokenizer tokenizer;
	
	JapaneseTokenizer() {
		tokenizer = Tokenizer.builder().build();
	}
	
	public List<Token> parse(String text) {
		return tokenizer.tokenize(text);
	}
}

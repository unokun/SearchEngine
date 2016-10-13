package indexer;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.*;

import static org.junit.Assert.fail;
import java.util.List;

import org.atilika.kuromoji.Token;
import org.junit.Before;
import org.junit.Test;

public class JapaneseTokenizerTest {

	JapaneseTokenizer tokenizer;

	@Before
	public void setUp() {
		tokenizer = new JapaneseTokenizer();
	}

	@Test
	public void testParse() {
		try {
			String text = "東京特許許可局";
			List<Token> tokenList = tokenizer.parse(text);
			assertThat(tokenList.size(), is(4));
			assertThat(tokenList.get(0).getSurfaceForm(), is("東京"));
			assertThat(tokenList.get(1).getSurfaceForm(), is("特許"));
			assertThat(tokenList.get(2).getSurfaceForm(), is("許可"));
			assertThat(tokenList.get(3).getSurfaceForm(), is("局"));
		} catch (Exception e) {
			fail();

		}
	}

}

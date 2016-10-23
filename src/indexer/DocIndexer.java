package indexer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.atilika.kuromoji.Token;

public class DocIndexer implements Runnable {
	// ドキュメントキュー
	DocumentQueue queue;
	// 形態素解析エンジン
	JapaneseTokenizer tokenizer = new JapaneseTokenizer();

	public void run() {
		while (true) {
			try {
				process();
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// FIXME
				e.printStackTrace();
			} catch (IOException e) {

			}
		}
	}
	/**
	 * 処理を実行します
	 * 
	 * @throws IOException
	 */
	void process() throws IOException {
		String path = queue.poll();
		if (path == null) {
			return;
		}
		File doc = new File(path);
		if (!doc.exists()) {
			return;
		}
		processDoc(doc);
	}

	/**
	 * 文書の処理を実行します
	 * 
	 * @param doc
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	void processDoc(File doc) throws FileNotFoundException, IOException {
		System.out.println("processDoc");
		String text = getText(doc);
		System.out.println("processDoc: " + text);
		List<Token> tokenList = tokenizer.parse(text);
		for (Token token : tokenList) {
			storeToken(token);
		}
		storeDocument(doc);
	}

	void storeDocument(File doc) {

	}

	void storeToken(Token token) {

	}

	/**
	 * ドキュメントのテキストを取得します
	 * 
	 * @param path
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public String getText(File path) throws FileNotFoundException, IOException {
		TextExtractor textExtractor = TextExtractorFactory.create(path);
		return textExtractor.extract(path);
		

	}
}

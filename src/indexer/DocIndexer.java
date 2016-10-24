package indexer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.atilika.kuromoji.Token;

import model.Document;
import model.Term;
import store.Storage;

public class DocIndexer implements Runnable {
	// ドキュメントキュー
	private DocumentQueue queue;

	// ストレージ
	private Storage storage;
	
	public Storage getStorage() {
		return storage;
	}
	public void setStorage(Storage storage) {
		this.storage = storage;
	}
	public DocumentQueue getQueue() {
		return queue;
	}
	public void setQueue(DocumentQueue queue) {
		this.queue = queue;
	}

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
				// FIXME

			} catch (SQLException e) {
				// FIXME

			}
		}
	}
	/**
	 * 処理を実行します
	 * 
	 * @throws IOException
	 * @throws SQLException 
	 */
	void process() throws IOException, SQLException {
		// キューからパスを取得します
		String path = queue.poll();
		if (path == null) {
			return;
		}
		// 文書が存在するかどうか?
		File doc = new File(path);
		if (!doc.exists()) {
			return;
		}
		// 文書を処理します
		processDoc(doc);
	}

	/**
	 * 文書の処理を実行します
	 * 
	 * @param file
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws SQLException 
	 */
	void processDoc(File file) throws FileNotFoundException, IOException, SQLException {
		//テキストを抽出する
		String text = getText(file);
		
		Document doc = storage.createDocument(file);
		// 形態素解析を実行する
		List<Token> tokenList = tokenizer.parse(text);
		for (Token token : tokenList) {
			Term term = storage.createTerm(token);
			storeTerm(term, doc);
		}
		storeDocument(doc);
	}
	public void storeDocument(Document doc) throws SQLException {
		storage.storeDocument(doc);
	}

	// DBに書き込む
	public void storeTerm(Term term, Document doc) throws SQLException {
		storage.storeTerm(term, doc);

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

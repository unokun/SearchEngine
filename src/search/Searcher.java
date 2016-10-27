package search;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Document;
import model.Term;
import store.DocStore;
import store.Storage;
import store.TermDocStore;
import store.TermStore;

public class Searcher {
	private final static String DB_NAME = "searchengine";
	public static Storage storage;

	public static Storage getStorage() throws SQLException {
		if (storage == null) {
			storage = new Storage();
			storage.init(DB_NAME);
		}
		return storage;
	}
	
	public Map<Document, Integer> getTermStatistic(String queryTermId) throws SQLException {
		
		Storage storage = Searcher.getStorage();
		TermDocStore termDocStore = storage.getTermDocStore();
		return termDocStore.getTermFreq(storage.getConnection(), queryTermId);

	}
	/**
	 * タームテーブルに登録されているタームIDを取得します
	 * 
	 * @param queryTerm
	 * @return
	 * @throws SQLException 
	 */
	String getTermId(String queryTerm) throws SQLException {
		Storage storage = Searcher.getStorage();
		TermStore termStore = storage.getTermStore();
		Term term = termStore.findBySurface(storage.getConnection(), queryTerm);
		return (term == null) ? null : term.getId();
	}

	/**
	 * クエリタームのドキュメントあたりの出現頻度(回数)を取得します
	 * 
	 * @param queryTerms
	 * @return
	 * @throws SQLException 
	 */
	public Map<String, Map<Document, Integer>> getTermStatistic(List<String> queryTerms) throws SQLException {
		Map<String, Map<Document, Integer>> statistics = new HashMap<>();
		for (String queryTerm : queryTerms) {
			String termId = getTermId(queryTerm);
			if (termId == null) {
				continue;
			}
			if (statistics.containsKey(termId)) {
				continue;
			}
			statistics.put(termId, getTermStatistic(termId));
		}
		return statistics;
	}
	/**
	 * 検索結果をスコアでソートします
	 * 
	 * @param results
	 * @return
	 */
	public List<SearchResult> sort(List<SearchResult> results) {
		Collections.sort(results, new Comparator<SearchResult>() {

			@Override
			public int compare(SearchResult o1, SearchResult o2) {
				Double s1 = o1.getScore();
				Double s2 = o2.getScore();
				if (s1 > s2) return -1;
				if (s1 < s2) return 1;
				return 0;
			}
			
		});
		return results;
	}

	Map<Document, Double> calculateScore(List<String> queryTerms) throws SQLException {
		Map<Document, Double> score = new HashMap<>();
		for (String queryTerm : queryTerms) {
			String termId = getTermId(queryTerm);
			Map<Document, Double> termScore = calculateScore(termId);
			for (Document doc : termScore.keySet()) {
				if (score.containsKey(doc)) {
					score.put(doc, termScore.get(doc) + score.get(doc));
				} else {
					score.put(doc, termScore.get(doc));
				}
			}
		}
		return score;
	}
	Map<Document, Double> calculateScore(String termId) throws SQLException {
		Map<Document, Double> score = new HashMap<>();
		
		Storage storage = Searcher.getStorage();
		Connection conn = storage.getConnection();
		TermDocStore termDocStore = storage.getTermDocStore();
		DocStore docStore = storage.getDocStore();
		int docCount = docStore.count(conn);
		
		 Map<Document, Integer> termFreq = termDocStore.getTermFreq (conn, termId);
		 for (Document doc : termFreq.keySet()) {
			 double idf = termFreq.size() / docCount;
			 score.put(doc, (double)(idf * termFreq.get(doc)));
		 }
		
		return score;
	}
	public List<SearchResult> search(SearchOption option) throws SQLException {

		// アルゴリズムを選択する
		// スコアを計算する
		Map<Document, Double> score = calculateScore(option.terms);
		
		// 
		List<SearchResult> searchResults = new ArrayList<>();
		for (Document doc : score.keySet()) {
			SearchResult result = new SearchResult();
			result.setTitle(doc.getTitle());
			result.setPath(doc.getPath());
			result.setScore(score.get(doc));
		}
		return sort(searchResults);
	}
	static void printUsage() {
		System.out.println("java search.Searcher term term ... [-algo algorism name]");
	}
	/**
	 * kohsuke/args4j: args4j 
	 * https://github.com/kohsuke/args4j
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			SearchOption option = new SearchOption();
			if (!option.parse(args)) {
				Searcher.printUsage();
				return;
			}
			Searcher searcher = new Searcher();
			List<SearchResult> results = searcher.search(option);
			for (SearchResult result : results) {
				String score = String.format("%.2f", result.getScore());
				System.out.print(score);
				System.out.println(result.getTitle());
			}
		} catch (Exception e) {
			// FIXME
			e.printStackTrace();
		}
	}
}

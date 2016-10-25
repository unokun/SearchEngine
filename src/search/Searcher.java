package search;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Searcher {
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
	public List<SearchResult> search(SearchOption option) {
		// termの統計情報を取得する
		// アルゴリズムを選択する
		// スコアを計算する
		// ソートする
		return null;
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

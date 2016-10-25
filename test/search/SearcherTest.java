package search;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SearcherTest {
	Searcher searcher;
	
	@Before
	public void setUp() throws Exception {
		searcher = new Searcher();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSort1() {
		try {
			List<SearchResult> results = createSearchResultList(1);
			searcher.sort(results);
			assertEquals(1,  results.size());
		} catch (Exception e) {
			fail();
		}
	}
	@Test
	public void testSort2() {
		try {
			List<SearchResult> results = createSearchResultList(2);
			results = searcher.sort(results);
			for (SearchResult result: results) {
				String score = String.format("%.2f", result.getScore());
				System.out.print(score + " : ");
				System.out.println(result.getTitle());

			}
			assertEquals("title1",  results.get(0).getTitle());
			assertEquals(Double.valueOf(2.0d),  Double.valueOf(results.get(0).getScore()));
		} catch (Exception e) {
			fail();
		}
	}
	List<SearchResult> createSearchResultList(int n) {
		List<SearchResult> results = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			results.add(createSearchResult("title" + String.valueOf(i), 1.0d + (double)i));
		}
		return results;
	}
	SearchResult createSearchResult(String title, double score) {
		SearchResult result = new SearchResult();
		result.setTitle(title);
		result.setScore(score);
		return result;
	}
}

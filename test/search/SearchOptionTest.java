package search;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SearchOptionTest {
	SearchOption option;
	
	@Before
	public void setUp() throws Exception {
		option = new SearchOption();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testParse() {
		try {
			String[] args = createArgs(1);
			
			boolean retv = option.parse(args);
			assertTrue(retv);
			assertEquals(1, option.terms.size());
			
		} catch (Exception e) {
			fail();
			
		}
	}
	String[] createArgs(int n) {
		List<String> args = new ArrayList<String>(n);
		for (int i = 0; i < n; i++) {
			args.add("term" + String.valueOf(n));
		}
		String[] array = new String[n];
		return args.toArray(array);
	}

}

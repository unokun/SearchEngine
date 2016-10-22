package indexer;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DocumentQueueTest {

	DocumentQueue queue;
	
	@Before
	public void setUp() throws Exception {
		queue = new DocumentQueue();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testQueue() {
		try {
			File file = new File("file.txt");
			
			queue.found(file);
			assertEquals(1, queue.countQueue());
			assertEquals(file.getAbsolutePath(), queue.poll());
			assertEquals(0, queue.countQueue());
			
		} catch (Exception e) {
			fail();
			
		}
	}

}

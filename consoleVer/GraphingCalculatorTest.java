import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class GraphingCalculatorTest {
	GraphingCalculator self = null;
	
	@Before
	public void setUp() throws Exception {
		self = new GraphingCalculator;
	}
	
	@Test
	public void testRecognizer1() {
		assertArrayEquals(self.recognizr("3 + 5"), ["3", "+", "5"]);
	}
	
	@Test
	public void testRecognizer2]() {
		assertArrayEquals(self.recognizr("3 + 5 * 2 / 8 + 4.5"), ["3", "+", "5", "*", "2", "/", "8", "+", "4.5"]);
	}
}
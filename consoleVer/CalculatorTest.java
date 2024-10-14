import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CalculatorTest {
	Calculator self = null;
	
	@Before
	public void setUp() throws Exception {
		self = new Calculator();
	}
	
	@Test
	public void testRecognizer1() {
		String[] answer = {"3", "+", "5"};
		assertArrayEquals(self.recognizer("3 + 5"), answer);
	}
	
	@Test
	public void testRecognizer2() {
		String[] answer = {"3", "+", "5", "*", "2", "/", "8", "+", "4.5"};
	    assertArrayEquals(self.recognizer("3 + 5 * 2 / 8 + 4.5"), answer);
		
	}
	
	@Test
	public void testRecognizer3() {
		String[] answer = {"3", "*", "3", "*", "3", "*", "3", "*", "3"};
		assertArrayEquals(self.recognizer("3 * 3 * 3 * 3 * 3"), answer);
	}

	@Test
	public void testRecognizer4() {
		String[] answer = {"3", "^", "5"};
		assertArrayEquals(self.recognizer("3 ^ 5"), answer);
	}
	
	@Test
	public void testRecognizer5() {
		String[] answer = {"(", "3", ")"};
		assertArrayEquals(self.recognizer("( 3 )"), answer);
	}

	@Test
	public void testMath1() {
		String[] answer = {"3", "+", "3", "+", "5"};
		assertEquals(11.0, self.evaluate(answer), 0.005);
	}
	
}

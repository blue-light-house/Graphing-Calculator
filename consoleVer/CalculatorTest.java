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
	//Currently not working as intended, likely test's fault, not calculator's
	//Actual assert is irrelevant, program's printout is 3.0 instead of expected 8.0
	public void testMath1() {
		int answer = 11;
		assertEquals(self.evaluate(self.recognizer("3 + 3 + 5")), answer);
	}
	
}

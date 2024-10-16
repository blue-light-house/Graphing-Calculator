import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


//Note: Tests with multiple sets of parenthesis currently fail
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
	public void testMathAddition() {
		String[] answer = {"3", "+", "3", "+", "5"};
		assertEquals(11.0, self.evaluate(answer), 0.005);
	}
	
	@Test
	public void testMathMultiplication() {
		String[] answer = {"3", "*", "3", "+", "5"};
		assertEquals(14.0, self.evaluate(answer), 0.005);
	}
	
	@Test
	public void testMathDivision() {
		String[] answer = {"3", "*", "3", "/", "5"};
		assertEquals(1.8, self.evaluate(answer), 0.005);
	}
	
	@Test
	public void testMathExponent() {
		String[] answer = {"3", "*", "3", "^", "2"};
		assertEquals(27.0, self.evaluate(answer), 0.005);
	}
	
	@Test
	public void testMathExponent2() {
		String[] answer = {"2", "^", "(", "3", "^", "2", ")"};
		assertEquals(512.0, self.evaluate(answer), 0.005);
	}
	
	@Test
	public void testMathSubtraction() {
		String[] answer = {"3", "-", "3", "*", "5"};
		assertEquals(-12.0, self.evaluate(answer), 0.005);
	}
	
	@Test
	public void testMathParenthesis() {
		String[] answer = {"3", "*", "(", "3", "+", "3", ")"};
		assertEquals(18.0, self.evaluate(answer), 0.005);
	}
	
	@Test
	public void testMathDoubleParenthesis() {
		String[] answer = {"1", "+", "(", "3", "*", "(", "2", "+", "3",")", ")"};
		assertEquals(16.0, self.evaluate(answer), 0.005);
	}
	
	@Test
	public void testMathDoubleParenthesis2() {
		String[] answer = {"3", "*", "(", "(", "3", "+", "3",")", ")"};
		assertEquals(18.0, self.evaluate(answer), 0.005);
	}
	
	
}

import static org.junit.Assert.*;
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
	public void testPemdas() {
		String[] answer = {"3", "*", "3", "-", "5", "-", "1", "*", "12"};
		assertEquals(-8.0, self.evaluate(answer), 0.005);
	}
	
	@Test
	public void testPemdas2() {
		String[] answer = {"2", "*", "(", "2", "-", "5", "^", "2", ")"};
		assertEquals(-46.0, self.evaluate(answer), 0.005);
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
	
	@Test
	public void testMathTripleParenthesis2() {
		String[] answer = {"2", "^", "(", "3", "+", "(", "10", "-", "(","2","^", "2", ")","+", "1",  ")", ")", "+", "9" };
		assertEquals(1033.0, self.evaluate(answer), 0.005);

	}
	
	@Test 
	public void testMathTripleParenthesis3() {
		String[] answer = {"(", "3", "+", "(", "10", "-", "(","2","^", "2", ")","+", "1",  ")", ")"};
		assertEquals(10, self.evaluate(answer), 0.005);
	}
	
	@Test
	public void testPi() {
		String[] answer ={"pi", "+", "0"};
		assertEquals((Math.PI), self.evaluate(answer), 0.005 );
	}
	
	@Test
	public void testPi2() {
		String[] answer ={"pi", "+", "1"};
		assertEquals((Math.PI+1), self.evaluate(answer), 0.005 );
	}
	
	@Test
	public void testE() {
		String[] answer ={"e", "+", "0"};
		assertEquals((Math.E), self.evaluate(answer), 0.005 );
	}
	
	@Test
	public void testE2() {
		String[] answer ={"e", "*", "2"};
		assertEquals((Math.E*2), self.evaluate(answer), 0.005 );
	}

	@Test
	public void testPiE() {
		String[] answer ={"e", "*", "pi"};
		assertEquals((Math.E * Math.PI), self.evaluate(answer), 0.005 );
	}
	
	@Test
	public void testManyParentheses() {
		String[] answer = {"(", "(", "(", "(", "(", "(", "(", "(", "(", "(", "(", "(", "(", "(", "(", "(", "(", "(", "(", "(", "(", "(", "1", "+", "1", ")", "+", "1", ")", "+", "1", ")", "+", "1", ")", "+", "1", ")", "+", "1", ")", "+", "1", ")", "+", "1", ")", "+", "1", ")", "+", "1", ")", "+", "1", ")", "+", "1", ")", "+", "1", ")", "+", "1", ")", "+", "1", ")", "+", "1", ")", "+", "1", ")", "+", "1", ")", "+", "1", ")", "+", "1", ")", "+", "1", ")", "+", "1", ")"};
		assertEquals(23, self.evaluate(answer), 0.005);
	}
	// evaluateAtX: <answer, x value that needs to be a string>
	
	@Test
	public void testAtX() {
		String[] answer = {"2", "*", "X"};
		assertEquals(8, self.evaluateAtX(answer, "4"), 0.05);	
	}
	
	@Test
	public void testAtX2() {
		String[] answer = {"2", "^", "(", "X", "+", "1", ")"};
		assertEquals(8, self.evaluateAtX(answer, "2"), 0.05);	
	}
	
	@Test
	public void testAtX3() {
		String[] answer = {"2","*","X"};
		assertEquals(Math.PI * 2, self.evaluateAtX(answer, "pi"), 0.05);	
	}
	
	@Test
	public void testReallyBigNumbers() {
		String[] answer = {"2", "^", "99999999999999","^", "9999999999999999999999999999999999"};
		assertNotEquals(2^9, self.evaluate(answer), 0.005);
	}
	
	@Test
	public void testReallySmallNumbers() {
		String[] answer = {"2", "^", "-99999999999999"};
		assertEquals(0, self.evaluate(answer), 0.00005);
	}
	
	@Test
	public void testAtX4() {
		String[] answer = {"(", "(", "(", "(", "(", "(", "(", "(", "(", "1", "+", "(", "X", ")", "-", "2", ")", "-", "1", ")", "-", "1", ")", "-", "1", ")", "-", "1", ")", "-", "1", ")", "-", "1", ")", "-", "1", ")", "-", "1", ")" };
		assertEquals(1, self.evaluateAtX(answer, "10"), 0.05);	
	}
	
	@Test
	public void testSine() {
		String[] answer = {"sin", "pi"};
		assertEquals(0, self.evaluate(answer), 0.005);
	}
	
	@Test
	public void testSine2() {
		String[] answer = {"sin", "(", "pi", "/", "2", ")"};
		assertEquals(1, self.evaluate(answer), 0.005);
	}
	
	@Test
	public void testSine3() {
		String[] answer = {"sin", "(", "3", "*", "pi", "/", "2", ")"};
		assertEquals(-1, self.evaluate(answer), 0.005);
	}
	
	@Test
	public void testCosine() {
		String[] answer = {"cos", "pi"};
		assertEquals(-1, self.evaluate(answer), 0.005);
	}
	
	@Test
	public void testCosine2() {
		String[] answer = {"cos", "(", "pi", "/", "2", ")"};
		assertEquals(0, self.evaluate(answer), 0.005);
	}
	
	@Test
	public void testCosine3() {
		String[] answer = {"cos", "(", "3", "*", "pi", "/", "2", ")"};
		assertEquals(0, self.evaluate(answer), 0.005);
	}
	
	@Test
	public void testTangent() {
		String[] answer = {"tan", "0"};
		assertEquals(0, self.evaluate(answer), 0.005);
	}
	
	@Test
	public void testTangent2() {
		String[] answer = {"tan", "pi"};
		assertEquals(0, self.evaluate(answer), 0.005);
	}
	
	@Test
	public void testSinCos() {
		String[] answer = {"sin", "cos", "pi"};
		assertEquals(-0.8414, self.evaluate(answer), 0.005);
	}
	
	@Test
	public void testCosSin() {
		String[] answer = {"cos", "sin", "pi"};
		assertEquals(1, self.evaluate(answer), 0.005);
	}
	
	@Test
	public void testCosSinTan() {
		String[] answer = {"cos", "sin", "tan", "pi"};
		assertEquals(1, self.evaluate(answer), 0.005);
	}
	
	
}

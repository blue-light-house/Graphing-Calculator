import java.lang.System;
import java.util.*;
import java.lang.Math.*;

/**
Calculator: handles mathematical operations. May be run through the command line, or in tandem with GraphingCalculator
@author blue-light-house
@version 0.8
Date Last Modfiied: 2024-11-08
*/
public class Calculator {
	// Define the symbols used in mathematical operations
	String ops[] = {"(", ")", "sin", "cos", "tan", "^", "*", "/", "+", "-"};
	// Define basic mathematical constants e, pi, and the golden ratio
	String constants[] = {"e", "pi", "gratio"};
	// These definitions are used for later recognition of the operations and constants within an expression
	
	/**
	* Recognizer: given a string representing a mathematical expression, return an array of strings, with each string representing a number, constant, or operation
	* @param expresssion: a string representing a mathematical expression
	* @return the expression string broken down into an array of mathematical operations
	*/
	public String[] recognizer(String expression) {
		// Trims the string of leading and trailing whitespace
		String trimmedExpression = expression.trim();
		int trimLength = trimmedExpression.length();
		
		String[] splitExpression = new String[trimmedExpression.length()];
		splitExpression[0] = trimmedExpression;
		int splitExpPos = 0;
		int lastBeginPos = 0;
		
		// Splits based on whitespace, or by delimiters between operations, numbers, and constants
		for (int i = 0; i < trimLength; i++) {
			String current = Character.toString(trimmedExpression.charAt(i));
			if (Arrays.asList(ops).contains(current) || current.equals(" ")) {
				String leftSubstring = trimmedExpression.substring(lastBeginPos, i);
					// System.out.println(leftSubstring);
					// System.out.println(current);
				String rightSubstring = trimmedExpression.substring(i+1, trimLength);
					// System.out.println(rightSubstring);
					// System.out.println("SPLITEXPPOS: " + splitExpPos + " LASTBEGINPOS: " + lastBeginPos);
				if (!leftSubstring.equals("")) {
					splitExpression[splitExpPos] = leftSubstring;
					splitExpPos++;
				}
				if (!current.equals(" ")) {
					splitExpression[splitExpPos] = current;
					splitExpPos++;
				}
				if (!rightSubstring.equals("")) {
					splitExpression[splitExpPos] = rightSubstring;
				}
				lastBeginPos = i+1;
					// System.out.println("CHANGED");
					// printExpression(splitExpression);
			}
		}
		// Finds the first spot where a gap is found, representing the end of the expression
		int limit = splitExpression.length;
		for (int i = 0; i < splitExpression.length; i++) {
			if (splitExpression[i] == null ) {
				limit = i;
				break;
			}
		}
		// Copies to a new array to avoid trailing nulls
		String[] returnExpression = new String[limit];
		for (int i = 0; i < limit; i++) {
			returnExpression[i] = splitExpression[i];
		}
			// System.out.println("FLAG");
			// printExpression(returnExpression);
		return returnExpression;
	}
	
	/**
	* evaluate: public facing expression for private evaluate() function. Takes an array of strings representing a mathematical expression and returns the value as a double
	* @param expression: an array of strings representing a mathematical expression
	* @return the value of the expression as a double
	*/
	public double evaluate (String[] expression) {
		// Makes a shallow copy of expression, as expression is modified by the evaluate() function
		String[] modifiableExpression = expression.clone();
		// Calls evaluate() over the entire expression
		return evaluate(modifiableExpression, 0, expression.length);
	}
	
	/**
	* evaluateWithReplacement: evalautes a given expression, with substitution of constants for double values
	* @param expression: an array of strings representing a mathematical expression
	* @param vars: a HashMap of user-defined variables. The keys represent variables in the expression, and the values represent the double values that will be substituted
	* @return the value of the expression as a double
	*/
	public double evaluateWithReplacement(String[] expression, HashMap<String, String> vars) {
		// Makes a shallow copy of expression, as expression is modified by the evaluate() function
		String[] modifiableExpression = expression.clone();
		// Substitutes the variables for double values
		for (int i = 0; i < modifiableExpression.length; i++) {
			if (vars.containsKey(modifiableExpression[i])) {
				modifiableExpression[i] = vars.get(modifiableExpression[i]);
			}
		}
		// Calls evaluate() over the entire expression
		return evaluate(modifiableExpression, 0, modifiableExpression.length);
	}
	
	/**
	* evaluateAtX: evalautes a given expression, with substitution of the variable X for a given value
	* @param expression: an array of strings representing a mathematical expression
	* @param xVal: the double value that will replace X in the expression
	* @return the value of the expression as a double
	*/
	public double evaluateAtX(String[] expression, String xVal) {
		// Creates a HashMap linking X to xVal, and calls evaluateWithReplacement
		HashMap<String, String> vars = new HashMap<String,String>();
		vars.put("X", xVal);
		return evaluateWithReplacement(expression, vars);
	}
	
	/**
	* evaluate: evaluates a given expression from the given start position (inclusive) to the given end position (exclusive)
	* @param expression: an array of strings representing a mathematical expression
	* @param startPos: the starting position to begin evaluation at (inclusive)
	* @param endPos: the ending position to cease evaluate at (exclusive)
	* @return the value of the expression as a double
	* @throws IllegalArgumentException if the given expression is invalid
	*/
	private double evaluate (String[] expression, int startPos, int endPos) throws IllegalArgumentException {
		// Defines ArrayLists to track the positions of operations
		
		// Tier 1 operations are addition and subtraction
		ArrayList<Integer> evaluatePositionsT1 = new ArrayList<Integer>();
		
		// Tier 2 operations are multiplication and division
		ArrayList<Integer> evaluatePositionsT2 = new ArrayList<Integer>();
		
		// Tier 3 operations are exponents
		ArrayList<Integer> evaluatePositionsT3 = new ArrayList<Integer>();
		
		// Tier 4 operations are trigonometric functions
		ArrayList<Integer> evaluatePositionsT4 = new ArrayList<Integer>();
		
			/*
			System.out.println("BEFORE T5");
			printExpression(expression);
			*/
		
		// Tier 5: Parentheses
		int openFound = 0;
		int closedFound = 0;
		int openFirst = 0;
		int closedLast = 0;
		
		// Iterates over the expression.
		// When an open and closed parentheses pair is found, the expression inside is evaluated recursively with evaluate()
		// The resulting value is substituted back in, and the rest of the space is replaced with whitespace
		for (int i = startPos; i < endPos; i++) {
				// System.out.println(expression[i]);
			if (expression[i].equals("(")) {
				if (openFound == 0) openFirst = i;
				openFound++;
					// System.out.println("FOUND OPEN AT " + i );
			} else if (expression[i].equals(")")) {
				closedFound++;
				closedLast = i;
					// System.out.println("FOUND CLOSED AT " + i );
			}
			
			if (openFound == closedFound && (openFound != 0)) {
					// System.out.println("EVALUATING FROM " + openFirst + " TO " + closedLast);
				double result = evaluate(expression, openFirst+1, closedLast);
					// LEAVING OPEN PARENTHESES BEHIND - CONSIDER FIXING
				for (int e = openFirst; e <= closedLast; e++) {
					expression[e] = "";
						// System.out.println("REPLACING");
				}
				expression[openFirst] = Double.toString(result);
				openFound = 0;
				closedFound = 0;
			}
		}
		
		// If there is a mismatch between the number of open and closed parentheses, throw an exception
		if (openFound != closedFound) {
			throw new IllegalArgumentException("Exception in evaluate (T4): Number of parentheses do not match!");
		}
		
			/*
			System.out.println("BEFORE T4");
			printExpression(expression);
			*/
			
		
		// Tier 4: Trigonometric functions
		
		// Iterates over the expression and locates all trigonometric functions
		for (int i = startPos; i < endPos; i++) {
			if (expression[i].equals("sin") || expression[i].equals("cos") || expression[i].equals("tan")) {
				evaluatePositionsT4.add(i);
			}
		}
		
		// Tier 4 Evaluation: Trigonometric Functions
		
		// Evaluates all trigonometric functions
		// Trig functions are evaluated right to left, allowing for nesting. This may cause unexpected behavior if parentheses are omitted
		while (!evaluatePositionsT4.isEmpty()) {
			int i = evaluatePositionsT4.remove(evaluatePositionsT4.size()-1);
			int itemPlace = findFirstNonEmpty(expression, i+1, startPos, endPos, 1);
			double dTemp = parseCharacter(expression, itemPlace);
			
			if (expression[i].toLowerCase().equals("sin")) {
				// System.out.println("SIN OF " + dTemp);
				dTemp = Math.sin(dTemp);
			} else if (expression[i].toLowerCase().equals("cos")) {
				// System.out.println("COS OF " + dTemp);
				dTemp = Math.cos(dTemp);
			} else if (expression[i].toLowerCase().equals("tan")) {
				// System.out.println("TAN OF " + dTemp);
				dTemp = Math.tan(dTemp);
			}
			
			for (int e = i; e <= itemPlace; e++) {
				expression[e] = "";
			}
			
			
			expression[i] = Double.toString(dTemp);
		}
		
			/*
			System.out.println("BEFORE T3");
			printExpression(expression);
			*/
		
		// Tier 3: Exponents
		
		// Iterates over the expression and locates all exponents
		for (int i = startPos; i < endPos; i++) {
			if (expression[i].equals("^")) {
				evaluatePositionsT3.add(i);
			}
		}
		
		// Tier 3 Evaluation: Exponents
		
		// Evaluates all exponents
		// Each triple a""^""b is replaced with the value of the exponent in the a position
		// All other positions are filled with whitespace
		// Whitespace may be present between a and ^, as well as ^ and b
		for (Integer i : evaluatePositionsT3) {
			double dTemp = 0.0;
			int iTemp = findFirstNonEmpty(expression, i, startPos, endPos, -1);
			dTemp = parseCharacter(expression, iTemp);
			expression[iTemp] = "";
			iTemp = findFirstNonEmpty(expression, i, startPos, endPos, 1);
			
			if (expression[i].equals("^")) {
				dTemp = Math.pow(dTemp, parseCharacter(expression, iTemp));
				expression[iTemp] = "";
			}
			
			expression[i] = "";
			expression[i-1] = Double.toString(dTemp);
		}
		
			/*
			System.out.println("BEFORE T2");
			printExpression(expression);
			*/
		
		// Tier 2: Multiplication, Division
		
		// Iterates over the expression and finds all tier 2 operations
		for (int i = startPos; i < endPos; i++) {
			if (expression[i].equals("*")) {
				evaluatePositionsT2.add(i);
			} else if (expression[i].equals("/")) {
				evaluatePositionsT2.add(i);
			}
		}
		
		// Tier 2 Evaluation: Multiplication, Division
		
		// Evaluates all multiplication and division
		// Each triple a""*""b is replaced with the value of the operation (may be division) in the a position
		// All other positions are filled with whitespace
		// Whitespace may be present between a and *, as well as * and b
		for (Integer i : evaluatePositionsT2) {
			double dTemp = 0.0;
			int iTemp = findFirstNonEmpty(expression, i, startPos, endPos, -1);
			dTemp = parseCharacter(expression, iTemp);
			expression[iTemp] = "";
			iTemp = findFirstNonEmpty(expression, i, startPos, endPos, 1);
			
			if (expression[i].equals("*")) {
				dTemp *= parseCharacter(expression, iTemp);
			} else if (expression[i].equals("/")) {
				dTemp /= parseCharacter(expression, iTemp);
			}
			
			expression[iTemp] = "";
			expression[i] = "";
			expression[i-1] = Double.toString(dTemp);
		}
		
			/*
			System.out.println("BEFORE T1");
			printExpression(expression);
			*/
		
		// Tier 1: Addition, Subtraction
		
		// Iterates over the expression and finds all tier 1 operations
		for (int i = startPos; i < endPos; i++) {
			if (expression[i].equals("+")) {
				evaluatePositionsT1.add(i);
			} else if (expression[i].equals("-")) {
				evaluatePositionsT1.add(i);
			}
		}
		
		// Tier 1 Evaluation: Addition, Subtraction
			// System.out.println("Len" + expression.length);
			
		// Evaluates all addition and subtraction
		// Each triple a""+""b is replaced with the value of the operation (may be subtraction) in the a position
		// All other positions are filled with whitespace
		// Whitespace may be present between a and +, as well as + and b
		int startPosT1 = findFirstNonEmpty(expression, 0, startPos, endPos, 1);
		double toReturn = parseCharacter(expression, startPosT1);
		for (Integer i : evaluatePositionsT1) {
			int iTemp = findFirstNonEmpty(expression, i, startPos, endPos, 1);
			
			if (expression[i].equals("+")) {
				toReturn += parseCharacter(expression, iTemp);
			} else if (expression[i].equals("-")) {
				toReturn -= parseCharacter(expression, iTemp);
			}
			
		}
		
		return toReturn;
	}
	
	/**
	* findFirstNonEmpty: given a start position, a direction, and bounds, iterates over all whitespace or invalid characters to find the first double value or constant, then returns its index.
	* @param expression: an array of strings representing a mathematical expression
	* @param startIndex: the index to begin the search at
	* @param startPos: the left bound on the search (inclusive)
	* @param endPos: the right bound on the search (exclusive)
	* @param dir: the step size to move in. Either 1 or -1, representing right and left, respectively
	* @return the index of the first non-operation or whitespace character that is recognized as a number
	*/
	private int findFirstNonEmpty(String[] expression, int startIndex, int startPos, int endPos, int dir) {
		// Sets the starting position to be the given startIndex; if invalid, defaults to the left or right bound
		int index = startIndex < startPos ? startPos : startIndex;
		index = index > endPos ? endPos : index;
			/*
			System.out.println("STARTING NONEMPTY SEARCH WITH FOLLOWING PARAMETERS:");
			System.out.print("EXPRESSION: ");
			printExpression(expression);
			System.out.println("START INDEX: " + startIndex);
			System.out.println("LEFT BOUND: " + startPos + " RIGHT BOUND: " + endPos);
			System.out.println("DIRECTION: " + dir);
			*/
		
		// While the string at the current position is whitepsace or an operation, iterate in the given direction
		String toReturn = expression[index];
		while ((toReturn.equals("") || Arrays.asList(ops).contains(toReturn.toLowerCase())) && (index < endPos && index >= startPos)) {
				/*
				System.out.println("STEP--------------------------------");
				System.out.println("CURRENT INDEX " + index);
				System.out.println("CURRENT VALUE " + toReturn);
				System.out.println("LEFT BOUND " + startPos + " STARTING AT " + startIndex + " RIGHT BOUND " + endPos);
				*/
			index+= dir;
			toReturn = expression[index];
		}
		
		
		// Ensure that the given item is either a number or a constant
		try {
				// System.out.println("FOUND " + toReturn + " AT INDEX " + index);
			parseCharacter(expression, index);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Exception in findFirstNonEmpty: Invalid character!");
		}
		
			// System.out.println("SUCCESSFULLY RETURNED " + toReturn + " AT INDEX " + index);
		return index;
	}
	
	/**
	* printExpression: prints the expression given to the console
	* @param expression: the expression to print
	*/
	private void printExpression(String[] expression) {
		System.out.println("Length: " + expression.length);
		for (String s : expression) {
			System.out.println(s);
		}
		System.out.println();
	}
	
	/**
	* parseCharacter: given an array and an index, checks if the character is a double or a constant
	* @param expression: an array of strings representing a mathematical expression
	* @param index: the index in the expression array to checks
	* @return the double value of expression[index], if it exists
	*/
	private double parseCharacter(String[] expression, int index) {
		String character = expression[index];
			// System.out.println("CHARACTER " + character);
		
		// Checks to see if the item is a given mathematical constant
		if (Arrays.asList(constants).contains(character.toLowerCase())) {
			if (character.toLowerCase().equals("e")) {
				return Math.E;
			} else if (character.toLowerCase().equals("pi")) {
				return Math.PI;
			} else if (character.toLowerCase().equals("gratio")) {
				return (1+ Math.sqrt(5))/2.0;
			}
		}
		
			// System.out.println("CHARACTER NOT IN CONSTANTS");
		
		// Otherwise, parse the item as a double and return it. This may throw an error, but it is caught in findFirstNonEmpty
		return Double.parseDouble(character);
	}
	
	/**
	* main: runs the calculator from the command line
	* @param args: an array of strings
	*/
    public static void main(String[] args) {
		Calculator self = new Calculator();
		if (args.length > 0) {
			// If given a mathematical expression evaluates and prints to console
			String[] test = self.recognizer(args[0]);
			System.out.print("Evaluating the following expression: ");
			self.printExpression(test);
			System.out.println("Result: " + self.evaluate(test, 0, test.length));
		}
    }
}
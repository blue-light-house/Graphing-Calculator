import java.lang.System;
import java.util.*;
import java.lang.Math.*;

public class Calculator {
	String chars[] = {"(", ")", "^", "*", "/", "+", "-"};
	
	public Calculator() {
		// test change
	}
	
	public String[] recognizer(String expression) {
		String[] splitExpression = expression.trim().split("\\s+");
		return splitExpression;
	}
	
	public double evaluate (String[] expression) {
		boolean evaluating = true;
		ArrayList<Integer> evaluatePositionsT4 = new ArrayList<Integer>();
		ArrayList<Integer> evaluatePositionsT3 = new ArrayList<Integer>();
		ArrayList<Integer> evaluatePositionsT2 = new ArrayList<Integer>();
		
		// Tier 4: Parentheses
		for (int i = 0; i < expression.length; i++) {
			if (expression[i].equals("(")) {
				evaluatePositionsT4.add(i);
			} else if (expression[i].equals(")")) {
				evaluatePositionsT4.add(i);
			}
		}
		
		// Tier 4 Evaluation: Parentheses
		for (int i = 0; i < evaluatePositionsT4.size(); i+=2) {
			int openParen = evaluatePositionsT4.get(i);
			int closeParen = evaluatePositionsT4.get(i+1);
			String[] innerParenthesesEvaluation = new String[closeParen - openParen - 1];
			int tempIndex = 0;
			for (int e = openParen+1; e < closeParen; e++) {
				innerParenthesesEvaluation[tempIndex] = expression[e];
				tempIndex+=1;
			}
			double dTemp = evaluate(innerParenthesesEvaluation);
			for (int e = openParen+1; e <= closeParen; e++) {
				expression[e] = "";
			}
			expression[openParen] = Double.toString(dTemp);
		}
		
		// Tier 3: Exponents
		for (int i = 0; i < expression.length; i++) {
			if (expression[i].equals("^")) {
				evaluatePositionsT3.add(i);
			}
		}
		
		// Tier 3 Evaluation: Exponents
		for (Integer i : evaluatePositionsT3) {
			double dTemp = 0.0;
			int iTemp = findFirstNonEmpty(expression, i, -1);
			String sTemp = expression[iTemp];
			expression[iTemp] = "";
			dTemp = Double.parseDouble(sTemp);
			iTemp = findFirstNonEmpty(expression, i, 1);
			sTemp = expression[iTemp];
			expression[iTemp] = "";
			
			if (expression[i].equals("^")) {
				dTemp = Math.pow(dTemp, Double.parseDouble(sTemp));
			}
			
			expression[i] = "";
			expression[i-1] = Double.toString(dTemp);
		}
		
		
		// Tier 2: Multiplication, Division
		for (int i = 0; i < expression.length; i++) {
			if (expression[i].equals("*")) {
				evaluatePositionsT2.add(i);
			} else if (expression[i].equals("/")) {
				evaluatePositionsT2.add(i);
			}
		}
		
		/*
		System.out.println("Finished reading in T2");
		for (Integer i : evaluatePositionsT2) {
			System.out.println((int) i);
		} */
		
		// Tier 2 Evaluation: Multiplication, Division
		for (Integer i : evaluatePositionsT2) {
			double dTemp = 0.0;
			int iTemp = findFirstNonEmpty(expression, i, -1);
			String sTemp = expression[iTemp];
			expression[iTemp] = "";
			dTemp = Double.parseDouble(sTemp);
			iTemp = findFirstNonEmpty(expression, i, 1);
			sTemp = expression[iTemp];
			expression[iTemp] = "";
			
			if (expression[i].equals("*")) {
				dTemp = dTemp * Double.parseDouble(sTemp);
			} else if (expression[i].equals("/")) {
				dTemp /= Double.parseDouble(sTemp);
			}
			
			expression[i] = "";
			expression[i-1] = Double.toString(dTemp);
		}
		
		// Tier 1 Evaluation: Addition, Subtraction
		
		double toReturn = 0;
		int index = 0;
		while (index < expression.length) {
			if (!expression[index].equals("")) {
				//System.out.println(expression[index]);
				toReturn = Double.parseDouble(expression[index]);
				break;
			} else {
				index+=2;
			}
		}
		index++;
		
		while (index < expression.length) {
			if (expression[index].equals("+")) {
				toReturn += Double.parseDouble(expression[findFirstNonEmpty(expression, index+1, 1)]);
				index +=2;
			} else if (expression[index].equals("-")) {
				toReturn -= Double.parseDouble(expression[findFirstNonEmpty(expression, index+1, 1)]);
				index +=2;
			} else if (expression[index].equals("")) {
				index +=2;
			} else {
				throw new IllegalArgumentException("Invalid operation in expression!");
			}
		}
		
		return toReturn;
	}
	
	private int findFirstNonEmpty(String[] expression, int startIndex, int dir) {
		int index = startIndex;
		String toReturn = expression[index];
		while ((toReturn.equals("") || Arrays.asList(chars).contains(toReturn))&& index < expression.length) {
			index+= dir;
			toReturn = expression[index];
		}
		return index;
	}
	
    public static void main(String[] args) {
        System.out.println("Hello World!");
		Calculator self = new Calculator();
		if (args.length > 0) {
			String[] test = self.recognizer(args[0]);
			for (String s : test) {
				System.out.print(s);
			}
			System.out.println();
			System.out.println(self.evaluate(test));
		}
    }
}

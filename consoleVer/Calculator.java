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
	
	public double evaluate (String[] expression, int startPos, int endPos) {
		boolean evaluating = true;
		ArrayList<Integer> evaluatePositionsT4Open = new ArrayList<Integer>();
		ArrayList<Integer> evaluatePositionsT4Closed = new ArrayList<Integer>();
		ArrayList<Integer> evaluatePositionsT4Together = new ArrayList<Integer>();
		ArrayList<Integer> evaluatePositionsT3 = new ArrayList<Integer>();
		ArrayList<Integer> evaluatePositionsT2 = new ArrayList<Integer>();
		ArrayList<Integer> evaluatePositionsT1 = new ArrayList<Integer>();
		
		/**
		JUST GO LEFT TO RIGHT, IF YOU FIND AN OPEN GO UNTIL A CLOSED, COUNT OPENS IN THE MIDDLE SO YOU DON'T MESS UP
		THEN EVALUATE, REPLACE, MOVE ON
		DON'T FIND THEM ALL AT ONCE AND EVALUATE LIKE THE OTHERS
		*/
		
		System.out.println("BEFORE T4");
		printExpression(expression);
		
		// Tier 4: Parentheses
		int openFound = 0;
		int closedFound = 0;
		int openFirst = 0;
		int closedLast = 0;
		for (int i = startPos; i < endPos; i++) {
			if (expression[i].equals("(")) {
				openFound++;
				openFirst = i;
				System.out.println("FOUND OPEN AT " + i );
			} else if (expression[i].equals(")")) {
				closedFound++;
				closedLast = i;
				System.out.println("FOUND CLOSED AT " + i );
			}
			
			if (openFound == closedFound && (openFound != 0)) {
				System.out.println("EVALUATING FROM " + openFirst + " TO " + closedLast);
				double result = evaluate(expression, openFirst+1, closedLast);
				for (int e = openFirst; e <= closedLast; e++) {
					expression[e] = "";
					System.out.println("REPLACING");
				}
				expression[openFirst] = Double.toString(result);
				openFound = 0;
				closedFound = 0;
			}
		}
		
		System.out.println("BEFORE T3");
		printExpression(expression);
		
		// Tier 3: Exponents
		for (int i = startPos; i < endPos; i++) {
			if (expression[i].equals("^")) {
				evaluatePositionsT3.add(i);
			}
		}
		
		// Tier 3 Evaluation: Exponents
		for (Integer i : evaluatePositionsT3) {
			double dTemp = 0.0;
			int iTemp = findFirstNonEmpty(expression, i, startPos, endPos, -1);
			String sTemp = expression[iTemp];
			expression[iTemp] = "";
			dTemp = Double.parseDouble(sTemp);
			iTemp = findFirstNonEmpty(expression, i, startPos, endPos, 1);
			sTemp = expression[iTemp];
			expression[iTemp] = "";
			
			if (expression[i].equals("^")) {
				dTemp = Math.pow(dTemp, Double.parseDouble(sTemp));
			}
			
			expression[i] = "";
			expression[i-1] = Double.toString(dTemp);
		}
		
		System.out.println("BEFORE T2");
		printExpression(expression);
		
		// Tier 2: Multiplication, Division
		for (int i = startPos; i < endPos; i++) {
			if (expression[i].equals("*")) {
				evaluatePositionsT2.add(i);
			} else if (expression[i].equals("/")) {
				evaluatePositionsT2.add(i);
			}
		}
		
		// Tier 2 Evaluation: Multiplication, Division
		for (Integer i : evaluatePositionsT2) {
			double dTemp = 0.0;
			int iTemp = findFirstNonEmpty(expression, i, startPos, endPos, -1);
			String sTemp = expression[iTemp];
			System.out.println(sTemp);
			System.out.println(iTemp);
			expression[iTemp] = "";
			dTemp = Double.parseDouble(sTemp);
			iTemp = findFirstNonEmpty(expression, i, startPos, endPos, 1);
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
		
		System.out.println("BEFORE T1");
		printExpression(expression);
		
		// Tier 1: Addition, Subtraction
		for (int i = startPos; i < endPos; i++) {
			if (expression[i].equals("+")) {
				evaluatePositionsT1.add(i);
			} else if (expression[i].equals("-")) {
				evaluatePositionsT1.add(i);
			}
		}
		
		// Tier 1 Evaluation: Addition, Subtraction
		System.out.println("Len" + expression.length);
		int startPosT1 = findFirstNonEmpty(expression, 0, startPos, endPos, 1);
		double toReturn = Double.parseDouble(expression[startPosT1]);
		for (Integer i : evaluatePositionsT1) {
			System.out.println(i);
			int iTemp = findFirstNonEmpty(expression, i, startPos, endPos, 1);
			String sTemp = expression[iTemp];
			
			if (expression[i].equals("+")) {
				toReturn += Double.parseDouble(sTemp);
			} else if (expression[i].equals("-")) {
				toReturn -= Double.parseDouble(sTemp);
			}
		}
		
		System.out.println("FLAG IN EVALUATE");
		
		return toReturn;
	}
	
	private int findFirstNonEmpty(String[] expression, int startIndex, int startPos, int endPos, int dir) {
		int index = startIndex < startPos ? startPos : startIndex;
		index = index > endPos ? endPos : index;
		System.out.println("STARTING NONEMPTY SEARCH WITH FOLLOWING PARAMETERS:");
		System.out.print("EXPRESSION: ");
		printExpression(expression);
		System.out.println("START INDEX: " + startIndex);
		System.out.println("LEFT BOUND: " + startPos + " RIGHT BOUND: " + endPos);
		System.out.println("DIRECTION: " + dir);
		String toReturn = expression[index];
		while ((toReturn.equals("") || Arrays.asList(chars).contains(toReturn))&& (index < endPos && index >= startPos)) {
			System.out.println("CURRENT INDEX " + index);
			System.out.println("CURRENT VALUE " + toReturn);
			System.out.println("LEFT BOUND " + startPos + " STARTING AT " + startIndex + " RIGHT BOUND " + endPos);
			index+= dir;
			toReturn = expression[index];
		}
		return index;
	}
	
	private void printExpression(String[] expression) {
		for (String s : expression) {
				System.out.print(s);
			}
			System.out.println();
	}
	
    public static void main(String[] args) {
		Calculator self = new Calculator();
		if (args.length > 0) {
			String[] test = self.recognizer(args[0]);
			System.out.println("BEFORE EVALUATE");
			self.printExpression(test);
			System.out.println(self.evaluate(test, 0, test.length));
		}
    }
}

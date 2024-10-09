import java.lang.System;
import java.util.*;

public class GraphingCalculator {
	
	public GraphingCalculator() {
	}
	
	private String[] recognizer(String expression) {
		String[] splitExpression = expression.trim().split("\\s+");
		return splitExpression;
	}
	
	private double evaluate (String[] expression) throws IllegalArgumentException {
		if(expression.length % 2 != 1) throw new IllegalArgumentException("Expression input is invalid!");
		boolean evaluating = true;
		ArrayList<Integer> evaluatePositionsT2 = new ArrayList<Integer>();
		
		// Tier 2: Multiplication, Division
		for (int i = 0; i < expression.length; i++) {
			if (expression[index].equals("*")) {
				evaluatePositionsT2.add(index);
			} else if (expression[index].equals("/")) {
				evaluatePositionsT2.add(index);
			} else {
				throw new IllegalArgumentException("Invalid operation in expression!");
			}
		}
		
		// Tier 2 Evaluation: Multiplication, Division
		for (Integer i : evaluatePositionsT2) {
			double dTemp = 0.0;
			String sTemp = expression[i - 1];
			expression[i-1] = "";
			if (sTemp.equals("")) {
				sTemp = expression[i-3];
				expression[i-3] = "";
			}
			dTemp = Double.parseDouble(sTemp);
			sTemp = expression[i + 1];
			expression[i+1] = "";
			if (sTemp.equals("")) {
				sTemp = expression[i+3];
				expression[i+3] = "";
			}
			
			if (expression[i] == "*") {
				dTemp *= Double.parseDouble(sTemp);
			} else {
				dTemp /= Double.parseDouble(sTemp);
			}
			
			expression[i] = 0;
			expression[i-1] = dTemp.toString;
		}
		
		// Tier 1 Evaluation: Addition, Subtraction
		
		int index = 0;
		while (index < expression.length) {
			if (expression[index].equals("")) {
				toReturn = Double.parseDouble(expression[index]);
				break;
			} else {
				
			}
		}
		while (index < expression.length - 1) {
			if (expression[index].equals("+")) {
				toReturn += Double.parseDouble(expression[index+1]);
				index +=2;
			} else if (expression[index].equals("-")) {
				toReturn -= Double.parseDouble(expression[index+1]);
				index +=2;
			} else if (expression[index].equals("^")) {
				index +=2;
				System.out.println("Coming soon!");
			} else {
				throw new IllegalArgumentException("Invalid operation in expression!");
			}
		}
		
		return toReturn;
	}
	
    public static void main(String[] args) {
        System.out.println("Hello World!");
		GraphingCalculator self = new GraphingCalculator();
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